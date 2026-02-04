package db.core;

import db.exceptions.DatabaseStoppedException;
import db.exceptions.KeyNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDB<T> {

    private final ConcurrentHashMap<Integer, Entry<T>> store = new ConcurrentHashMap<>();
    private volatile boolean running = true;

    // ---------- Lifecycle ----------
    public void start() {
        running = true;
        System.out.println("DB STARTED");
    }

    public void stop() {
        running = false;
        System.out.println("DB STOPPED");
    }

    public boolean isRunning() {
        return running;
    }

    // ---------- Operations ----------
    public void put(Integer key, T value, Long ttl) {
        checkRunning();

        long expiryTime = (ttl == null)
                ? -1
                : System.currentTimeMillis() + ttl;

        store.put(key, new Entry<>(value, expiryTime));
        System.out.println("PUT OK");
    }

    public T get(Integer key) {
        Entry<T> entry = store.get(key);

        if (entry == null) {
            throw new KeyNotFoundException("Key not found");
        }

        if (entry.isExpired()) {
            store.remove(key);
            throw new KeyNotFoundException("Key expired");
        }

        return entry.value;
    }

    public void delete(Integer key) {
        checkRunning();

        if (store.remove(key) == null) {
            throw new KeyNotFoundException("Key not found");
        }

        System.out.println("DELETE OK");
    }

    // ---------- TTL Cleanup ----------
    public void cleanupExpiredKeys() {
        for (Integer key : store.keySet()) {
            Entry<T> entry = store.get(key);
            if (entry != null && entry.isExpired()) {
                store.remove(key);
            }
        }
    }

    // ---------- Helpers ----------
    private void checkRunning() {
        if (!running) {
            throw new DatabaseStoppedException("Database is stopped");
        }
    }
}
