import java.util.concurrent.*;

public class CompletionServiceDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        for (int i = 1; i <= 5; i++) {
            int id = i;
            service.submit(() -> {
                Thread.sleep(500 * id);
                return "Result " + id;
            });
        }

        for (int i = 0; i < 5; i++) {
            Future<String> f = service.take(); // get next completed
            System.out.println("Received: " + f.get());
        }

        executor.shutdown();
    }
}
