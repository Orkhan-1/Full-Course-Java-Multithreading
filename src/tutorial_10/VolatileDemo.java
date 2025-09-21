package src.tutorial_10;

public class VolatileDemo {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (running) {
                // busy wait
            }
            System.out.println("Stopped!");
        });

        t.start();
        Thread.sleep(1000);
        running = false; // without volatile, thread may never stop
    }
}
