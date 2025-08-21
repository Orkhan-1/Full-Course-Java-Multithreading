import java.util.concurrent.*;

public class ProducerConsumerDemoTwo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced " + i);
                }
            } catch (InterruptedException e) {}
        };

        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int val = queue.take();
                    System.out.println("Consumed " + val);
                }
            } catch (InterruptedException e) {}
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
