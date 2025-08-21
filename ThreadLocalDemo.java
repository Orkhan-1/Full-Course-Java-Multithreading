public class ThreadLocalDemo {
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        Runnable task = () -> {
            int value = threadLocal.get();
            threadLocal.set(value + 1);
            System.out.println(Thread.currentThread().getName() + " value = " + threadLocal.get());
        };

        new Thread(task).start();
        new Thread(task).start();
    }
}
// This code demonstrates the use of ThreadLocal in Java.