package src.tutorial_17;

public class StarvationExample {
    public static void main(String[] args) {
        Thread highPriority = new Thread(() -> {
            while (true) {
                System.out.println("High priority running");
            }
        });
        Thread lowPriority = new Thread(() -> {
            while (true) {
                System.out.println("   Low priority running");
            }
        });

        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();
    }
}
