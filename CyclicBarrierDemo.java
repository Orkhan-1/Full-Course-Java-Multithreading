import java.util.concurrent.*;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("All threads reached barrier, proceeding...");
        });

        for (int i = 1; i <= 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " reached barrier");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " continuing");
                } catch (Exception e) {}
            }).start();
        }
    }
}
// Output will show that all threads reach the barrier and then continue after the barrier action is executed.