package src.tutorial_13;

import java.util.concurrent.*;

public class CallableFutureDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            Thread.sleep(1000);
            return 42;
        };

        Future<Integer> future = executor.submit(task);

        System.out.println("Doing other work...");
        Integer result = future.get(); // waits
        System.out.println("Result: " + result);

        executor.shutdown();
    }
}
