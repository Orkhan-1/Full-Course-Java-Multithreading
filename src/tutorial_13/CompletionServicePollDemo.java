package src.tutorial_13;

import java.util.concurrent.*;
import java.util.List;

public class CompletionServicePollDemo {
    public static void main(String[] args) throws Exception {
        // Thread pool
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        // List of "URLs" to simulate crawling
        List<String> urls = List.of(
                "https://site1.com",
                "https://site2.com",
                "https://site3.com",
                "https://site4.com",
                "https://site5.com"
        );

        // Submit crawling tasks
        for (String url : urls) {
            service.submit(() -> {
                // Simulate network delay
                long delay = (long)(Math.random() * 3000 + 500);
                Thread.sleep(delay);
                return "Fetched data from " + url + " after " + delay + "ms";
            });
        }

        System.out.println("Submitted all crawling tasks. Processing results...");

        int received = 0;
        while (received < urls.size()) {
            // Non-blocking check for completed task
            Future<String> future = service.poll();
            if (future != null) {
                String data = future.get(); // get the result
                System.out.println("Processed: " + data);
                received++;
            } else {
                System.out.println("No task ready yet, doing other work...");
                Thread.sleep(200); // simulate doing other work
            }
        }

        executor.shutdown();
        System.out.println("All tasks completed.");
    }
}
