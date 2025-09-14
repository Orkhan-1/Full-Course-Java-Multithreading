package src.tutorial_7;

public class SynchronizedDemo {
    private static int counter = 0;

    // synchronized method
    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final counter value: " + counter);
    }
}
// Expected value = 2000, and actual value will always be 2000 due to synchronization