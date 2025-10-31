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
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }
            public void onNext(String item) {
                System.out.println("[Dashboard] New Price: " + item);
            }
            public void onError(Throwable throwable) {}
            public void onComplete() { System.out.println("[Dashboard] Stream Closed"); }
        };

        // Subscriber 2 — Log to database
        Flow.Subscriber<String> databaseLogger = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }
            public void onNext(String item) {
                System.out.println("[DB Logger] Saving price: " + item);
            }
            public void onError(Throwable throwable) {}
            public void onComplete() { System.out.println("[DB Logger] Stream Closed"); }
        };

        publisher.subscribe(dashboard);
        publisher.subscribe(databaseLogger);

        // Publisher sends stock price updates
        String[] prices = {"AAPL: 179.3", "AAPL: 180.1", "AAPL: 178.9", "AAPL: 181.0"};
        for (String price : prices) {
            publisher.submit(price);
            TimeUnit.MILLISECONDS.sleep(1000); // simulate live feed
        }

        publisher.close();
        TimeUnit.SECONDS.sleep(1); // let async threads finish
    }
}

// This code demonstrates the use of the Flow API in Java to create a simple reactive stream.