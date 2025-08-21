public class ProcessThreadDemo {
    public static void main(String[] args) {
        System.out.println("Process ID: " + ProcessHandle.current().pid());
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
}
