package com.noorimat.cache.benchmark;

import com.noorimat.cache.core.LRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Comprehensive benchmark suite for the distributed cache.
 * Tests throughput, latency, concurrency, and compares against baseline.
 */
public class CacheBenchmark {
    private static final Logger logger = LoggerFactory.getLogger(CacheBenchmark.class);
    
    private static final int WARMUP_ITERATIONS = 10_000;
    private static final int BENCHMARK_ITERATIONS = 100_000;
    
    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     DISTRIBUTED CACHE CLUSTER - BENCHMARK SUITE           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Run all benchmarks
        runSingleThreadedBenchmark();
        runConcurrentBenchmark(4);
        runConcurrentBenchmark(8);
        runConcurrentBenchmark(16);
        runLatencyBenchmark();
        runMixedWorkloadBenchmark();
        runTTLBenchmark();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  BENCHMARK COMPLETE                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private static void runSingleThreadedBenchmark() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  TEST 1: Single-Threaded Performance");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        LRUCache cache = new LRUCache(10_000);
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
            cache.get("key-" + i);
        }
        
        // Benchmark PUT operations
        long startPut = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
        }
        long endPut = System.nanoTime();
        double putDuration = (endPut - startPut) / 1_000_000.0; // ms
        double putThroughput = BENCHMARK_ITERATIONS / (putDuration / 1000.0);
        
        // Benchmark GET operations
        long startGet = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            cache.get("key-" + i);
        }
        long endGet = System.nanoTime();
        double getDuration = (endGet - startGet) / 1_000_000.0; // ms
        double getThroughput = BENCHMARK_ITERATIONS / (getDuration / 1000.0);
        
        System.out.println("\n  Results:");
        System.out.printf("    PUT: %,.0f ops/sec (%.2f ms total)%n", putThroughput, putDuration);
        System.out.printf("    GET: %,.0f ops/sec (%.2f ms total)%n", getThroughput, getDuration);
        System.out.printf("    Avg PUT latency: %.3f μs%n", (putDuration * 1000) / BENCHMARK_ITERATIONS);
        System.out.printf("    Avg GET latency: %.3f μs%n", (getDuration * 1000) / BENCHMARK_ITERATIONS);
        System.out.println();
    }
    
    private static void runConcurrentBenchmark(int numThreads) throws Exception {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.printf("  TEST 2: Concurrent Performance (%d threads)%n", numThreads);
        System.out.println("═══════════════════════════════════════════════════════════");
        
        LRUCache cache = new LRUCache(10_000);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        int opsPerThread = BENCHMARK_ITERATIONS / numThreads;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(numThreads);
        AtomicInteger operations = new AtomicInteger(0);
        
        long startTime = System.nanoTime();
        
        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    
                    for (int i = 0; i < opsPerThread; i++) {
                        String key = "key-" + threadId + "-" + i;
                        cache.put(key, ("value-" + i).getBytes(), null);
                        cache.get(key);
                        operations.incrementAndGet();
                    }
                } catch (Exception e) {
                    logger.error("Thread error", e);
                } finally {
                    endLatch.countDown();
                }
            });
        }
        
        startLatch.countDown(); // Start all threads
        endLatch.await(); // Wait for completion
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0; // ms
        double throughput = operations.get() / (duration / 1000.0);
        
        executor.shutdown();
        
        System.out.println("\n  Results:");
        System.out.printf("    Total operations: %,d%n", operations.get());
        System.out.printf("    Throughput: %,.0f ops/sec%n", throughput);
        System.out.printf("    Duration: %.2f ms%n", duration);
        System.out.printf("    Avg latency: %.3f μs%n", (duration * 1000) / operations.get());
        System.out.println();
    }
    
    private static void runLatencyBenchmark() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  TEST 3: Latency Distribution");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        LRUCache cache = new LRUCache(10_000);
        List<Long> putLatencies = new ArrayList<>();
        List<Long> getLatencies = new ArrayList<>();
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
        }
        
        // Measure PUT latencies
        for (int i = 0; i < 10_000; i++) {
            long start = System.nanoTime();
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
            long end = System.nanoTime();
            putLatencies.add(end - start);
        }
        
        // Measure GET latencies
        for (int i = 0; i < 10_000; i++) {
            long start = System.nanoTime();
            cache.get("key-" + i);
            long end = System.nanoTime();
            getLatencies.add(end - start);
        }
        
        Collections.sort(putLatencies);
        Collections.sort(getLatencies);
        
        System.out.println("\n  PUT Latency Percentiles:");
        printPercentiles(putLatencies);
        
        System.out.println("\n  GET Latency Percentiles:");
        printPercentiles(getLatencies);
        System.out.println();
    }
    
    private static void runMixedWorkloadBenchmark() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  TEST 4: Mixed Workload (80% reads, 20% writes)");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        LRUCache cache = new LRUCache(10_000);
        Random random = new Random(42);
        
        // Pre-populate cache
        for (int i = 0; i < 5_000; i++) {
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
        }
        
        long start = System.nanoTime();
        int reads = 0, writes = 0;
        
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            if (random.nextDouble() < 0.8) {
                // 80% reads
                cache.get("key-" + random.nextInt(5_000));
                reads++;
            } else {
                // 20% writes
                cache.put("key-" + random.nextInt(10_000), ("value-" + i).getBytes(), null);
                writes++;
            }
        }
        
        long end = System.nanoTime();
        double duration = (end - start) / 1_000_000.0; // ms
        double throughput = BENCHMARK_ITERATIONS / (duration / 1000.0);
        
        System.out.println("\n  Results:");
        System.out.printf("    Reads: %,d (%.1f%%)%n", reads, (reads * 100.0) / BENCHMARK_ITERATIONS);
        System.out.printf("    Writes: %,d (%.1f%%)%n", writes, (writes * 100.0) / BENCHMARK_ITERATIONS);
        System.out.printf("    Throughput: %,.0f ops/sec%n", throughput);
        System.out.printf("    Duration: %.2f ms%n", duration);
        System.out.println();
    }
    
    private static void runTTLBenchmark() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  TEST 5: TTL Performance Impact");
        System.out.println("═══════════════════════════════════════════════════════════");
        
        LRUCache cache = new LRUCache(10_000);
        
        // Benchmark without TTL
        long startNoTTL = System.nanoTime();
        for (int i = 0; i < 50_000; i++) {
            cache.put("key-" + i, ("value-" + i).getBytes(), null);
        }
        long endNoTTL = System.nanoTime();
        double durationNoTTL = (endNoTTL - startNoTTL) / 1_000_000.0;
        
        // Benchmark with TTL
        long startWithTTL = System.nanoTime();
        for (int i = 0; i < 50_000; i++) {
            cache.put("key-ttl-" + i, ("value-" + i).getBytes(), 60L);
        }
        long endWithTTL = System.nanoTime();
        double durationWithTTL = (endWithTTL - startWithTTL) / 1_000_000.0;
        
        double overhead = ((durationWithTTL - durationNoTTL) / durationNoTTL) * 100;
        
        System.out.println("\n  Results:");
        System.out.printf("    Without TTL: %.2f ms%n", durationNoTTL);
        System.out.printf("    With TTL: %.2f ms%n", durationWithTTL);
        System.out.printf("    TTL overhead: %.2f%%%n", overhead);
        System.out.println();
    }
    
    private static void printPercentiles(List<Long> latencies) {
        double[] percentiles = {50, 75, 90, 95, 99, 99.9};
        
        for (double p : percentiles) {
            int index = (int) Math.ceil((p / 100.0) * latencies.size()) - 1;
            index = Math.max(0, Math.min(index, latencies.size() - 1));
            double latencyUs = latencies.get(index) / 1000.0; // Convert to microseconds
            System.out.printf("    p%.1f: %.3f μs%n", p, latencyUs);
        }
    }
}
