import java.util.concurrent.*;

public class LambdaExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> System.out.println("Hello from lambda!"));
        executor.submit(() -> System.out.println("Another task"));

        executor.shutdown();
    }
}
