package src.tutorial_3;

import java.util.concurrent.*;

public class ThreadCreationDemo {
    public static void main(String[] args) throws Exception {
        new MyThread().start();

        new Thread(new MyRunnable()).start();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<String> myCallable = new MyCallable();
        Future<String> future = service.submit(myCallable);
        System.out.println(future.get());
        service.shutdown();
    }
}