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

/*

                      Thread t1:
Reads oldValue = 0
newValue = 1
Calls compareAndSet(0, 1) → succeeds, updates value to 1, returns true

                      Thread t2 (running at the same time):
Also read oldValue = 0
newValue = 1
Calls compareAndSet(0, 1) → fails, because value is now 1, returns false







Most CPUs (x86, ARM, etc.) have special instructions like:
CMPXCHG (x86) → Compare and Exchange
LDREX/STREX (ARM)

* */

