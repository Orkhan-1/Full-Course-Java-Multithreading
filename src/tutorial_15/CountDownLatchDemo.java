package src.tutorial_15;

import java.util.concurrent.*;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // Make one or more threads wait until
        // a set of operations being performed by other threads completes
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " finished work");
                latch.countDown();
            }).start();
        }

        latch.await(); // wait until count reaches 0
        System.out.println("All workers finished!");
    }
}
