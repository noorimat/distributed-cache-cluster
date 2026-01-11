package com.noorimat.cache.benchmark;

import java.util.HashMap;
import java.util.Map;

/**
 * Baseline comparison using simple HashMap (no thread safety, no LRU).
 * This shows the overhead of our production features.
 */
public class BaselineComparison {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           BASELINE COMPARISON (HashMap)                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        Map<String, byte[]> baseline = new HashMap<>(10_000);
        int iterations = 100_000;
        
        // Warmup
        for (int i = 0; i < 10_000; i++) {
            baseline.put("key-" + i, ("value-" + i).getBytes());
            baseline.get("key-" + i);
        }
        
        // Benchmark PUT
        long startPut = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            baseline.put("key-" + i, ("value-" + i).getBytes());
        }
        long endPut = System.nanoTime();
        double putDuration = (endPut - startPut) / 1_000_000.0;
        double putThroughput = iterations / (putDuration / 1000.0);
        
        // Benchmark GET
        long startGet = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            baseline.get("key-" + i);
        }
        long endGet = System.nanoTime();
        double getDuration = (endGet - startGet) / 1_000_000.0;
        double getThroughput = iterations / (getDuration / 1000.0);
        
        System.out.println("  Simple HashMap (NO thread-safety, NO LRU, NO TTL):");
        System.out.printf("    PUT: %,.0f ops/sec (%.2f ms)%n", putThroughput, putDuration);
        System.out.printf("    GET: %,.0f ops/sec (%.2f ms)%n", getThroughput, getDuration);
        System.out.printf("    Avg PUT latency: %.3f μs%n", (putDuration * 1000) / iterations);
        System.out.printf("    Avg GET latency: %.3f μs%n", (getDuration * 1000) / iterations);
        System.out.println();
        System.out.println("  Compare this to LRUCache results above!");
        System.out.println("  Our cache adds thread-safety + LRU + TTL with minimal overhead.");
        System.out.println();
    }
}
