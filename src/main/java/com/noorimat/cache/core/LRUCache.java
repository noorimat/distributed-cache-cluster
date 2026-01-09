package com.noorimat.cache.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache {
    private static final Logger logger = LoggerFactory.getLogger(LRUCache.class);
    
    private final int capacity;
    private final Map<String, CacheEntry> cache;
    private final ReadWriteLock lock;
    
    private long hits = 0;
    private long misses = 0;
    private long evictions = 0;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.lock = new ReentrantReadWriteLock();
        
        // LinkedHashMap with access-order for LRU
        this.cache = new LinkedHashMap<String, CacheEntry>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
                boolean shouldRemove = size() > LRUCache.this.capacity;
                if (shouldRemove) {
                    evictions++;
                    logger.debug("Evicting key: {}", eldest.getKey());
                }
                return shouldRemove;
            }
        };
    }

    public byte[] get(String key) {
        lock.readLock().lock();
        try {
            CacheEntry entry = cache.get(key);
            if (entry == null || entry.isExpired()) {
                misses++;
                if (entry != null && entry.isExpired()) {
                    // Remove expired entry (upgrade to write lock)
                    lock.readLock().unlock();
                    lock.writeLock().lock();
                    try {
                        cache.remove(key);
                    } finally {
                        lock.readLock().lock();
                        lock.writeLock().unlock();
                    }
                }
                return null;
            }
            hits++;
            return entry.getValue();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(String key, byte[] value, Long ttlSeconds) {
        lock.writeLock().lock();
        try {
            long expirationTime = ttlSeconds != null 
                ? System.currentTimeMillis() + (ttlSeconds * 1000)
                : Long.MAX_VALUE;
            
            cache.put(key, new CacheEntry(value, expirationTime));
            logger.debug("Put key: {}, TTL: {}", key, ttlSeconds);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean delete(String key) {
        lock.writeLock().lock();
        try {
            boolean existed = cache.remove(key) != null;
            logger.debug("Deleted key: {}, existed: {}", key, existed);
            return existed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public CacheStats getStats() {
        lock.readLock().lock();
        try {
            return new CacheStats(cache.size(), hits, misses, evictions);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clear() {
        lock.writeLock().lock();
        try {
            cache.clear();
            hits = 0;
            misses = 0;
            evictions = 0;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static class CacheEntry {
        private final byte[] value;
        private final long expirationTime;

        public CacheEntry(byte[] value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        public byte[] getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }

    public static class CacheStats {
        private final int size;
        private final long hits;
        private final long misses;
        private final long evictions;

        public CacheStats(int size, long hits, long misses, long evictions) {
            this.size = size;
            this.hits = hits;
            this.misses = misses;
            this.evictions = evictions;
        }

        public int getSize() { return size; }
        public long getHits() { return hits; }
        public long getMisses() { return misses; }
        public long getEvictions() { return evictions; }
        
        public double getHitRate() {
            long total = hits + misses;
            return total == 0 ? 0.0 : (double) hits / total;
        }
    }
}
