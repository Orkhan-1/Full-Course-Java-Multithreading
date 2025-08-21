// 3. Using Callable
import java.util.concurrent.*;

public class ThreadCreationDemo {
    public static void main(String[] args) throws Exception {
        new MyThread().start();
        new Thread(new MyRunnable()).start();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(() -> "Result from Callable");
        System.out.println(future.get());
        service.shutdown();
    }
}