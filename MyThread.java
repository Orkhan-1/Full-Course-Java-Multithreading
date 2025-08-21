// 1. Extending Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Hello from Thread: " + Thread.currentThread().getName());
    }
}