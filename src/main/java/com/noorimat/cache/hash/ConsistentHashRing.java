package com.noorimat.cache.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Consistent hash ring implementation for distributing keys across nodes.
 * Uses virtual nodes to ensure even distribution even with small number of physical nodes.
 */
public class ConsistentHashRing<T> {
    private static final Logger logger = LoggerFactory.getLogger(ConsistentHashRing.class);
    
    private final int virtualNodesPerNode;
    private final ConcurrentSkipListMap<Long, T> ring;
    private final MessageDigest md5;
    
    public ConsistentHashRing(int virtualNodesPerNode) {
        this.virtualNodesPerNode = virtualNodesPerNode;
        this.ring = new ConcurrentSkipListMap<>();
        try {
            this.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
    
    /**
     * Add a node to the ring with virtual nodes for better distribution
     */
    public void addNode(T node) {
        for (int i = 0; i < virtualNodesPerNode; i++) {
            long hash = hash(node.toString() + "-vnode-" + i);
            ring.put(hash, node);
            logger.debug("Added virtual node {} at hash {}", node, hash);
        }
        logger.info("Added node {} with {} virtual nodes. Total ring size: {}", 
                    node, virtualNodesPerNode, ring.size());
    }
    
    /**
     * Remove a node and all its virtual nodes from the ring
     */
    public void removeNode(T node) {
        int removed = 0;
        for (int i = 0; i < virtualNodesPerNode; i++) {
            long hash = hash(node.toString() + "-vnode-" + i);
            if (ring.remove(hash) != null) {
                removed++;
            }
        }
        logger.info("Removed node {} ({} virtual nodes). Total ring size: {}", 
                    node, removed, ring.size());
    }
    
    /**
     * Get the node responsible for the given key
     */
    public T getNode(String key) {
        if (ring.isEmpty()) {
            return null;
        }
        
        long hash = hash(key);
        
        // Find the first node with hash >= key's hash (clockwise on ring)
        Map.Entry<Long, T> entry = ring.ceilingEntry(hash);
        
        // Wrap around if we've gone past the end
        if (entry == null) {
            entry = ring.firstEntry();
        }
        
        logger.debug("Key '{}' (hash: {}) mapped to node {}", key, hash, entry.getValue());
        return entry.getValue();
    }
    
    /**
     * Get N replicas for a key (for replication)
     */
    public List<T> getNodes(String key, int count) {
        if (ring.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<T> nodes = new ArrayList<>();
        Set<T> seen = new HashSet<>();
        
        long hash = hash(key);
        
        Iterator<Map.Entry<Long, T>> iterator = ring.tailMap(hash).entrySet().iterator();
        
        // Get nodes going clockwise
        while (nodes.size() < count && (iterator.hasNext() || !ring.headMap(hash).isEmpty())) {
            if (!iterator.hasNext()) {
                // Wrap around to beginning
                iterator = ring.entrySet().iterator();
            }
            
            if (iterator.hasNext()) {
                T node = iterator.next().getValue();
                if (!seen.contains(node)) {
                    nodes.add(node);
                    seen.add(node);
                }
            }
        }
        
        logger.debug("Key '{}' mapped to {} replicas: {}", key, nodes.size(), nodes);
        return nodes;
    }
    
    /**
     * Get all nodes in the ring (distinct physical nodes)
     */
    public Set<T> getAllNodes() {
        return new HashSet<>(ring.values());
    }
    
    /**
     * Get the number of nodes in the ring
     */
    public int getNodeCount() {
        return getAllNodes().size();
    }
    
    /**
     * Get distribution statistics (for debugging/monitoring)
     */
    public Map<T, Integer> getDistribution() {
        Map<T, Integer> distribution = new HashMap<>();
        for (T node : ring.values()) {
            distribution.put(node, distribution.getOrDefault(node, 0) + 1);
        }
        return distribution;
    }
    
    /**
     * Hash function using MD5 (good distribution, fast)
     */
    private long hash(String key) {
        md5.reset();
        md5.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md5.digest();
        
        // Use first 8 bytes of MD5 hash as long
        long hash = 0;
        for (int i = 0; i < 8; i++) {
            hash = (hash << 8) | (digest[i] & 0xFF);
        }
        
        return hash;
    }
    
    /**
     * Print ring statistics
     */
    public void printStatistics() {
        System.out.println("\n=== Consistent Hash Ring Statistics ===");
        System.out.println("Physical Nodes: " + getNodeCount());
        System.out.println("Virtual Nodes per Physical Node: " + virtualNodesPerNode);
        System.out.println("Total Ring Positions: " + ring.size());
        System.out.println("\nVirtual Node Distribution:");
        
        Map<T, Integer> distribution = getDistribution();
        for (Map.Entry<T, Integer> entry : distribution.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / ring.size();
            System.out.printf("  %s: %d virtual nodes (%.2f%%)%n", 
                            entry.getKey(), entry.getValue(), percentage);
        }
        System.out.println("========================================\n");
    }
}
