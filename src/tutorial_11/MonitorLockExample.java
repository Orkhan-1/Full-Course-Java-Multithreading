package src.tutorial_11;

public class MonitorLockExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(counter::increment);
        Thread t2 = new Thread(() -> {
            System.out.println("Counter value: " + counter.get());
        });

        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }
}
