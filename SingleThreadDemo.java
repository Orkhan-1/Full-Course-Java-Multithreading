// Single-threaded example
public class SingleThreadDemo {
    public static void main(String[] args) {
        task("Task 1");
        task("Task 2");
    }

    static void task(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - step " + i);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}
