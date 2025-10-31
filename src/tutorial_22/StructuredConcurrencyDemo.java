package src.tutorial_22;

import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyDemo {
    public static void main(String[] args) throws Exception {
        String result = fetchUserData();
        System.out.println("Final result: " + result);
    }

    public static String fetchUserData() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork two subtasks that run concurrently
            var userTask = scope.fork(() -> getUserName());
            var emailTask = scope.fork(() -> getUserEmail());

            // Wait for both tasks to complete (or one to fail)
            scope.join();
            scope.throwIfFailed(); // Throw exception if any task failed

            // Combine results
            return userTask.get() + " - " + emailTask.get();
        }
    }

    public static String getUserName() throws InterruptedException {
        Thread.sleep(1000); // Simulate work
        return "John Doe";
    }

    public static String getUserEmail() throws InterruptedException {
        Thread.sleep(800); // Simulate work
        return "john@example.com";
    }
}