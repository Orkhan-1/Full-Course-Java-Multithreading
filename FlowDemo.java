import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {
    public static void main(String[] args) throws Exception {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            public void onSubscribe(Flow.Subscription subscription) { subscription.request(1); }
            public void onNext(String item) {
                System.out.println("Received: " + item);
            }
            public void onError(Throwable throwable) {}
            public void onComplete() { System.out.println("Done"); }
        };

        publisher.subscribe(subscriber);
        publisher.submit("Hello Reactive Streams!");
        publisher.close();
        Thread.sleep(500);
    }
}
// This code demonstrates the use of the Flow API in Java to create a simple reactive stream.