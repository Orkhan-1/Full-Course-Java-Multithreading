package src.tutorial_14;

import java.util.concurrent.atomic.AtomicInteger;

public class CASExample {
    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // Create 2 threads that try to increment the value 5 times each
        Thread t1 = new Thread(() -> increment());
        Thread t2 = new Thread(() -> increment());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Value: " + atomicInt.get());
    }

    private static void increment() {
        for (int i = 0; i < 5; i++) {
            boolean updated = false;
            while (!updated) {
                int oldValue = atomicInt.get();         // read current value
                int newValue = oldValue + 1;            // calculate new value
                updated = atomicInt.compareAndSet(oldValue, newValue); // CAS operation
                // If another thread changed the value in between, compareAndSet fails
            }
            System.out.println(Thread.currentThread().getName() + " incremented");
        }
    }
}
