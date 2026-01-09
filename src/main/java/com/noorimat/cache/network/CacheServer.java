package com.noorimat.cache.network;

import com.google.protobuf.ByteString;
import com.noorimat.cache.core.LRUCache;
import com.noorimat.cache.grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheServer {
    private static final Logger logger = LoggerFactory.getLogger(CacheServer.class);
    
    private final int port;
    private final Server server;
    private final LRUCache cache;

    public CacheServer(int port, int cacheCapacity) {
        this.port = port;
        this.cache = new LRUCache(cacheCapacity);
        this.server = ServerBuilder.forPort(port)
                .addService(new CacheServiceImpl(cache))
                .build();
    }

    public void start() throws IOException {
        server.start();
        logger.info("Cache server started on port {}", port);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down cache server...");
            try {
                CacheServer.this.stop();
            } catch (InterruptedException e) {
                logger.error("Error during shutdown", e);
            }
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            logger.info("Cache server stopped");
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class CacheServiceImpl extends CacheServiceGrpc.CacheServiceImplBase {
        private final LRUCache cache;

        public CacheServiceImpl(LRUCache cache) {
            this.cache = cache;
        }

        @Override
        public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
            byte[] value = cache.get(request.getKey());
            
            GetResponse response = GetResponse.newBuilder()
                    .setFound(value != null)
                    .setValue(value != null ? ByteString.copyFrom(value) : ByteString.EMPTY)
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void put(PutRequest request, StreamObserver<PutResponse> responseObserver) {
            Long ttl = request.hasTtlSeconds() ? request.getTtlSeconds() : null;
            cache.put(request.getKey(), request.getValue().toByteArray(), ttl);
            
            PutResponse response = PutResponse.newBuilder()
                    .setSuccess(true)
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
            boolean deleted = cache.delete(request.getKey());
            
            DeleteResponse response = DeleteResponse.newBuilder()
                    .setSuccess(deleted)
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getStats(StatsRequest request, StreamObserver<StatsResponse> responseObserver) {
            LRUCache.CacheStats stats = cache.getStats();
            
            StatsResponse response = StatsResponse.newBuilder()
                    .setTotalKeys(stats.getSize())
                    .setHits(stats.getHits())
                    .setMisses(stats.getMisses())
                    .setHitRate(stats.getHitRate())
                    .setEvictions(stats.getEvictions())
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        int capacity = args.length > 1 ? Integer.parseInt(args[1]) : 1000;
        
        CacheServer server = new CacheServer(port, capacity);
        server.start();
        server.blockUntilShutdown();
    }
}
