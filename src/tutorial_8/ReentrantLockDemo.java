package src.tutorial_8;

import java.util.concurrent.locks.*;

public class ReentrantLockDemo {
    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    public static void increment() {

        //
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }

        //
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) increment();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final counter value: " + counter);
    }
}
