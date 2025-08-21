// Multi-threaded example
public class MultiThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> task("Task 1"));
        Thread t2 = new Thread(() -> task("Task 2"));

        t1.start();
        t2.start();
    }

    static void task(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - step " + i);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}
