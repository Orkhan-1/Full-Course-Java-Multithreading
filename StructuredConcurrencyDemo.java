import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.*;

public class StructuredConcurrencyDemo {
    public static void main(String[] args) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<String> f1 = scope.fork(() -> "Result 1");
            Future<String> f2 = scope.fork(() -> "Result 2");

            scope.join(); // wait for tasks
            scope.throwIfFailed();

            System.out.println(f1.resultNow() + ", " + f2.resultNow());
        }
    }
}
