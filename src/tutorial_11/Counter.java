package src.tutorial_11;

public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // happens-before releasing the lock
    }

    public synchronized int get() {
        return count;  // happens-after acquiring the lock
    }
}
