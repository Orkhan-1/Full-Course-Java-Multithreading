package src.tutorial_19;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) {

        CompletableFuture<String> userFlow =
                CompletableFuture.supplyAsync(() -> {
                            // Step 1: Fetch user info (simulate DB/API call)
                            System.out.println("Fetching user data from database...");
                            sleep(1000);
                            return "Orkhan Gasanov";
                        })
                        .thenCompose(user -> CompletableFuture.supplyAsync(() -> {
                            // Step 2: Fetch orders based on user (depends on previous result)
                            System.out.println("Fetching orders for user: " + user);
                            sleep(1500);
                            return "Order1, Order2, Order3";
                        }))
                        .thenApply(orders -> {
                            // Step 3: Format final output (transform result)
                            System.out.println("Formatting user and order data...");
                            return "User Summary:\n" +
                                    "Name: Orkhan Gasanov\n" +
                                    "Orders: " + orders;
                        });

        System.out.println("Main thread is free to do other work...");

        try {
            // Wait for the entire async pipeline to complete
            String result = userFlow.get();
            System.out.println("\nFinal Result:\n" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}

/*
============================================================
    Using Guava ListenableFuture (Google Guava Library)
============================================================
- Introduced before CompletableFuture existed.
- Extends Future by allowing callbacks when the task completes.
- Often used in distributed systems.

Example:
ListeningExecutorService service =
    MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));

ListenableFuture<String> future = service.submit(() -> "Hello from Guava");

Futures.addCallback(future, new FutureCallback<>() {
    public void onSuccess(String result) { System.out.println("Success: " + result); }
    public void onFailure(Throwable t) { t.printStackTrace(); }
}, service);

Notes:
- Predecessor of CompletableFuture.
- Good for integrating async callbacks.

============================================================
            Using Netty (Event-driven Framework)
============================================================
- Used for high-performance network applications (e.g., web servers).
- Entirely based on event loops and async callbacks.
- Uses its own abstraction: ChannelFuture.

Example:
ChannelFuture future = bootstrap.connect("localhost", 8080);
future.addListener(f -> {
    if (f.isSuccess()) {
        System.out.println("Connected successfully!");
    } else {
        System.out.println("Connection failed!");
    }
});

Notes:
- Very low-level and efficient.
- Backbone of frameworks like gRPC, Spring WebFlux, and Akka.

============================================================
Summary
============================================================
| Approach               | Chaining | Non-blocking | Modern Use  |
|------------------------|-----------|---------------|-------------|
| Thread                 | -         | + (manual)    | Rare        |
| Future                 | -         | - (blocks)    | Legacy      |
| CompletableFuture      | +         | +             | + Modern    |
| Guava ListenableFuture | +         | +             | Sometimes   |
| Netty (ChannelFuture)  | +         | +             | + High-perf |

💡 Recommendation:
- Use CompletableFuture for most async logic.
- Use Netty for network I/O and reactive systems.
*/


