package com.noorimat.cache.client;

import com.google.protobuf.ByteString;
import com.noorimat.cache.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class CacheClient {
    private static final Logger logger = LoggerFactory.getLogger(CacheClient.class);
    
    private final ManagedChannel channel;
    private final CacheServiceGrpc.CacheServiceBlockingStub blockingStub;

    public CacheClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = CacheServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void put(String key, String value) {
        put(key, value, null);
    }

    public void put(String key, String value, Long ttlSeconds) {
        PutRequest.Builder builder = PutRequest.newBuilder()
                .setKey(key)
                .setValue(ByteString.copyFrom(value, StandardCharsets.UTF_8));
        
        if (ttlSeconds != null) {
            builder.setTtlSeconds(ttlSeconds);
        }
        
        PutResponse response = blockingStub.put(builder.build());
        logger.info("PUT {} = {} (TTL: {}s) -> {}", key, value, ttlSeconds, response.getSuccess());
    }

    public String get(String key) {
        GetRequest request = GetRequest.newBuilder()
                .setKey(key)
                .build();
        
        GetResponse response = blockingStub.get(request);
        if (response.getFound()) {
            String value = response.getValue().toString(StandardCharsets.UTF_8);
            logger.info("GET {} -> {}", key, value);
            return value;
        } else {
            logger.info("GET {} -> NOT FOUND", key);
            return null;
        }
    }

    public boolean delete(String key) {
        DeleteRequest request = DeleteRequest.newBuilder()
                .setKey(key)
                .build();
        
        DeleteResponse response = blockingStub.delete(request);
        logger.info("DELETE {} -> {}", key, response.getSuccess());
        return response.getSuccess();
    }

    public void printStats() {
        StatsRequest request = StatsRequest.newBuilder().build();
        StatsResponse stats = blockingStub.getStats(request);
        
        System.out.println("\n=== Cache Statistics ===");
        System.out.println("Total Keys: " + stats.getTotalKeys());
        System.out.println("Hits: " + stats.getHits());
        System.out.println("Misses: " + stats.getMisses());
        System.out.println("Hit Rate: " + String.format("%.2f%%", stats.getHitRate() * 100));
        System.out.println("Evictions: " + stats.getEvictions());
        System.out.println("========================\n");
    }

    public static void main(String[] args) throws Exception {
        CacheClient client = new CacheClient("localhost", 8080);
        
        try {
            // Demo operations
            System.out.println("Starting cache client demo...\n");
            
            // Put some values
            client.put("user:1", "Alice");
            client.put("user:2", "Bob");
            client.put("user:3", "Charlie");
            client.put("session:abc", "active", 10L); // 10 second TTL
            
            // Get values
            client.get("user:1");
            client.get("user:2");
            client.get("user:999"); // Miss
            
            // Delete
            client.delete("user:2");
            client.get("user:2"); // Should be deleted
            
            // Print stats
            client.printStats();
            
            // Test TTL
            System.out.println("Testing TTL expiration (waiting 11 seconds)...");
            Thread.sleep(11000);
            client.get("session:abc"); // Should be expired
            
            client.printStats();
            
        } finally {
            client.shutdown();
        }
    }
}
