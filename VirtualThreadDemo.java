import java.util.concurrent.Executors;

public class VirtualThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                int id = i;
                executor.submit(() -> {
                    System.out.println("Running task " + id + " on " + Thread.currentThread());
                });
            }
        }
    }
}
// This code demonstrates the use of virtual threads in Java to run multiple tasks concurrently.