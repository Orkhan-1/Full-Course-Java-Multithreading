import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyDemo {
    private static final Queue<Integer> queue = new LinkedList<>();
    private static final int LIMIT = 5;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            int value = 0;
            while (true) {
                synchronized (queue) {
                    while (queue.size() == LIMIT) {
                        try { queue.wait(); } catch (InterruptedException e) {}
                    }
                    queue.add(value);
                    System.out.println("Produced " + value);
                    value++;
                    queue.notifyAll();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try { queue.wait(); } catch (InterruptedException e) {}
                    }
                    int val = queue.poll();
                    System.out.println("Consumed " + val);
                    queue.notifyAll();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
