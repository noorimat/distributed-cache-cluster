# Distributed Cache Cluster

A high-performance, production-grade distributed caching system built in Java with gRPC, featuring LRU eviction, TTL support, and thread-safe concurrent access.

## Features

- **LRU Eviction Policy**: Automatic eviction of least recently used entries
- **TTL Support**: Optional time-to-live for cache entries
- **Thread-Safe**: ReadWriteLock for concurrent access
- **gRPC Communication**: High-performance binary protocol
- **Statistics Tracking**: Hits, misses, evictions, and hit rate
- **Production-Ready**: Comprehensive error handling and logging

## Architecture
```text
┌──────────────────────────────────────────────────────────┐
│                     Cache Client                          │
│                   (gRPC Stub)                             │
└────────────────────┬─────────────────────────────────────┘
                     │ gRPC Protocol
                     ▼
┌──────────────────────────────────────────────────────────┐
│                   Cache Server                            │
│  ┌────────────────────────────────────────────────────┐  │
│  │              gRPC Service                          │  │
│  │  - Get(key) → value                                │  │
│  │  - Put(key, value, ttl?)                           │  │
│  │  - Delete(key) → success                           │  │
│  │  - GetStats() → statistics                         │  │
│  └────────────┬───────────────────────────────────────┘  │
│               ▼                                           │
│  ┌────────────────────────────────────────────────────┐  │
│  │          LRU Cache Core                            │  │
│  │  - LinkedHashMap (access-order)                    │  │
│  │  - ReadWriteLock (thread-safe)                     │  │
│  │  - TTL Expiration                                   │  │
│  │  - Metrics: hits/misses/evictions                  │  │
│  └────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────┘
```

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+

### Build
```bash
mvn clean compile
```

### Run Server
```bash
# Start server on port 8080 with capacity 1000
mvn exec:java -Dexec.mainClass="com.noorimat.cache.network.CacheServer"

# Or with custom settings
mvn exec:java -Dexec.mainClass="com.noorimat.cache.network.CacheServer" -Dexec.args="9090 5000"
```

### Run Client Demo
```bash
mvn exec:java -Dexec.mainClass="com.noorimat.cache.client.CacheClient"
```

## Usage Example
```java
// Connect to cache server
CacheClient client = new CacheClient("localhost", 8080);

// Put values
client.put("user:123", "Alice");
client.put("session:abc", "active", 300L); // 5 minute TTL

// Get values
String user = client.get("user:123");

// Delete
client.delete("user:123");

// View statistics
client.printStats();
```

## Performance

- **O(1) get/put/delete operations**
- **Thread-safe** with minimal lock contention (ReadWriteLock)
- **Concurrent reads** - multiple threads can read simultaneously
- **LRU eviction** - constant time with LinkedHashMap

## API Reference

### gRPC Service Definition
```protobuf
service CacheService {
  rpc Get(GetRequest) returns (GetResponse);
  rpc Put(PutRequest) returns (PutResponse);
  rpc Delete(DeleteRequest) returns (DeleteResponse);
  rpc GetStats(StatsRequest) returns (StatsResponse);
}
```

### Operations

- **Get**: Retrieve value by key
- **Put**: Store key-value pair with optional TTL
- **Delete**: Remove entry by key
- **GetStats**: Get cache statistics (hits, misses, evictions, hit rate)

## Project Structure
```text
distributed-cache-cluster/
├── src/main/
│   ├── java/com/noorimat/cache/
│   │   ├── core/
│   │   │   └── LRUCache.java         # Core cache implementation
│   │   ├── network/
│   │   │   └── CacheServer.java      # gRPC server
│   │   └── client/
│   │       └── CacheClient.java      # Client library
│   └── proto/
│       └── cache.proto                # gRPC service definition
├── pom.xml
└── README.md
```

## Future Enhancements

- [ ] Consistent hashing for multi-node distribution
- [ ] Replication across nodes
- [ ] Persistent storage backend
- [ ] Cluster membership & failure detection
- [ ] Performance benchmarks vs Redis
- [ ] Docker Compose multi-node setup
- [ ] Prometheus metrics endpoint

## Technologies

- **Java 17**: Modern Java features
- **gRPC**: High-performance RPC framework
- **Protocol Buffers**: Efficient serialization
- **SLF4J/Logback**: Logging
- **Maven**: Build tool

## License

MIT License

## Author

Noor Imat - [GitHub](https://github.com/noorimat)

## Performance

This cache delivers **production-grade performance** with millions of operations per second:

- **Single-threaded**: 1.5M PUT/sec, 2.5M GET/sec
- **Multi-threaded (16 threads)**: 8M combined ops/sec
- **Latency**: p50 < 0.5μs, p99 < 3μs
- **Scalability**: Near-linear scaling with thread count
- **TTL overhead**: Only 10%

**Only 3-4x slower than raw HashMap while adding thread-safety, LRU eviction, TTL support, and statistics tracking.**

For detailed benchmarks and comparisons, see [BENCHMARKS.md](BENCHMARKS.md).

### Run Benchmarks
```bash
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.CacheBenchmark"
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.BaselineComparison"
```

## Performance

This cache delivers **production-grade performance** with millions of operations per second:

- **Single-threaded**: 1.5M PUT/sec, 2.5M GET/sec
- **Multi-threaded (16 threads)**: 8M combined ops/sec
- **Latency**: p50 < 0.5μs, p99 < 3μs
- **Scalability**: Near-linear scaling with thread count
- **TTL overhead**: Only 10%

**Only 3-4x slower than raw HashMap while adding thread-safety, LRU eviction, TTL support, and statistics tracking.**

For detailed benchmarks and comparisons, see [BENCHMARKS.md](BENCHMARKS.md).

### Run Benchmarks
```bash
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.CacheBenchmark"
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.BaselineComparison"
```

## Performance

This cache delivers **production-grade performance** with millions of operations per second:

- **Single-threaded**: 1.5M PUT/sec, 2.5M GET/sec
- **Multi-threaded (16 threads)**: 8M combined ops/sec
- **Latency**: p50 < 0.5μs, p99 < 3μs
- **Scalability**: Near-linear scaling with thread count
- **TTL overhead**: Only 10%

**Only 3-4x slower than raw HashMap while adding thread-safety, LRU eviction, TTL support, and statistics tracking.**

For detailed benchmarks and comparisons, see [BENCHMARKS.md](BENCHMARKS.md).

### Run Benchmarks
```bash
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.CacheBenchmark"
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.BaselineComparison"
```

## Performance

This cache delivers **production-grade performance** with millions of operations per second:

- **Single-threaded**: 1.5M PUT/sec, 2.5M GET/sec
- **Multi-threaded (16 threads)**: 8M combined ops/sec
- **Latency**: p50 < 0.5μs, p99 < 3μs
- **Scalability**: Near-linear scaling with thread count
- **TTL overhead**: Only 10%

**Only 3-4x slower than raw HashMap while adding thread-safety, LRU eviction, TTL support, and statistics tracking.**

For detailed benchmarks and comparisons, see [BENCHMARKS.md](BENCHMARKS.md).

### Run Benchmarks
```bash
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.CacheBenchmark"
mvn exec:java -Dexec.mainClass="com.noorimat.cache.benchmark.BaselineComparison"
```
