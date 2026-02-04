package db.core;

public class Entry<T> {

    public T value;
    public long expiryTime; // -1 means no expiry

    public Entry(T value, long expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }

    public boolean isExpired() {
        return expiryTime != -1 && System.currentTimeMillis() > expiryTime;
    }
}

