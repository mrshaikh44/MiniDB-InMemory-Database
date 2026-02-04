package db.core;

public class CleanerThread extends Thread {

    private final InMemoryDB<?> db;

    public CleanerThread(InMemoryDB<?> db) {
        this.db = db;
        setDaemon(true); // stops automatically when main thread exits
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (db.isRunning()) {
                    db.cleanupExpiredKeys();
                }
                Thread.sleep(1000); // cleanup every 1 second
            } catch (InterruptedException ignored) {
            }
        }
    }
}
