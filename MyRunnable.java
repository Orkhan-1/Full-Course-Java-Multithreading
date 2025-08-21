// 2. Implementing Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Hello from Runnable: " + Thread.currentThread().getName());
    }
}