package src.tutorial_20;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class StockPriceStream {
    public static void main(String[] args) throws Exception {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Subscriber 1 — Display on dashboard
        Flow.Subscriber<String> dashboard = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // Request one item at a time for backpressure
            }

            @Override
            public void onNext(String item) {
                System.out.println("[Dashboard] New Price: " + item);
                subscription.request(1); // Request next item after processing
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[Dashboard] Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("[Dashboard] Stream Closed");
            }
        };

        // Subscriber 2 — Log to database
        Flow.Subscriber<String> databaseLogger = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // Request one item at a time
            }

            @Override
            public void onNext(String item) {
                System.out.println("[DB Logger] Saving price: " + item);
                // Simulate slower database operation
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                subscription.request(1); // Request next item
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[DB Logger] Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("[DB Logger] Stream Closed");
            }
        };

        publisher.subscribe(dashboard);
        publisher.subscribe(databaseLogger);

        System.out.println("Starting stock price stream...");

        // Publisher sends stock price updates
        String[] prices = {"AAPL: 179.3", "AAPL: 180.1", "AAPL: 178.9", "AAPL: 181.0"};
        for (String price : prices) {
            System.out.println("[Publisher] Sending: " + price);
            publisher.submit(price);
            TimeUnit.MILLISECONDS.sleep(1000); // simulate live feed
        }

        publisher.close();
        System.out.println("Publisher closed. Waiting for completion...");

        // Keep main thread alive to see all output
        TimeUnit.SECONDS.sleep(3);
    }
}