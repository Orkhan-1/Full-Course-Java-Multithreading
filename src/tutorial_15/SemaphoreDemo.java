package src.tutorial_15;

import java.util.concurrent.*;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // only 2 permits

        Runnable task = () -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired permit");
                Thread.sleep(2000);
                semaphore.release();
            } catch (InterruptedException e) {}
        };

        for (int i = 1; i <= 5; i++) {
            new Thread(task).start();
        }
    }
}
