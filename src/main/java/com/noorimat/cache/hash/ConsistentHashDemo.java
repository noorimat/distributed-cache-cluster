package com.noorimat.cache.hash;

import java.util.*;

public class ConsistentHashDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Consistent Hash Ring Demo ===\n");
        
        // Create ring with 150 virtual nodes per physical node
        ConsistentHashRing<String> ring = new ConsistentHashRing<>(150);
        
        // Add nodes
        System.out.println("Adding nodes to the ring...");
        ring.addNode("cache-node-1:8080");
        ring.addNode("cache-node-2:8080");
        ring.addNode("cache-node-3:8080");
        
        // Show statistics
        ring.printStatistics();
        
        // Test key distribution
        System.out.println("Testing key distribution with 10,000 keys...");
        testDistribution(ring, 10000);
        
        // Simulate node failure
        System.out.println("\n\nSimulating node failure...");
        System.out.println("Removing cache-node-2:8080");
        ring.removeNode("cache-node-2:8080");
        ring.printStatistics();
        
        // Test redistribution
        System.out.println("Testing redistribution after node removal...");
        testDistribution(ring, 10000);
        
        // Add new node
        System.out.println("\n\nAdding new node...");
        System.out.println("Adding cache-node-4:8080");
        ring.addNode("cache-node-4:8080");
        ring.printStatistics();
        
        // Test replication
        System.out.println("Testing replication (3 replicas per key)...");
        testReplication(ring);
    }
    
    private static void testDistribution(ConsistentHashRing<String> ring, int keyCount) {
        Map<String, Integer> distribution = new HashMap<>();
        
        for (int i = 0; i < keyCount; i++) {
            String key = "key-" + i;
            String node = ring.getNode(key);
            distribution.put(node, distribution.getOrDefault(node, 0) + 1);
        }
        
        System.out.println("\nKey Distribution:");
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / keyCount;
            System.out.printf("  %s: %d keys (%.2f%%)%n", 
                            entry.getKey(), entry.getValue(), percentage);
        }
        
        // Calculate standard deviation to show evenness
        double mean = (double) keyCount / distribution.size();
        double variance = 0;
        for (int count : distribution.values()) {
            variance += Math.pow(count - mean, 2);
        }
        double stdDev = Math.sqrt(variance / distribution.size());
        System.out.printf("\nDistribution Quality:");
        System.out.printf("\n  Mean: %.2f keys per node", mean);
        System.out.printf("\n  Standard Deviation: %.2f (lower is better)", stdDev);
        System.out.printf("\n  Coefficient of Variation: %.2f%%", (stdDev / mean) * 100);
    }
    
    private static void testReplication(ConsistentHashRing<String> ring) {
        String[] testKeys = {"user:123", "session:abc", "product:456"};
        
        System.out.println("\nReplica placement for sample keys:");
        for (String key : testKeys) {
            List<String> replicas = ring.getNodes(key, 3);
            System.out.printf("  %s -> %s%n", key, replicas);
        }
    }
}
