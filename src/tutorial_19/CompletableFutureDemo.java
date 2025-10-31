package src.tutorial_19;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        // Create a CompletableFuture that runs asynchronously
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate some long-running task
            try {
                Thread.sleep(2000); // Sleep for 2 seconds
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hello from CompletableFuture!";
        });

        System.out.println("Task started asynchronously...");
        System.out.println("Main thread can continue doing other work...");

        try {
            // Wait for the result (this blocks until the future completes)
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
