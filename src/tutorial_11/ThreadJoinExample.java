package src.tutorial_11;

public class ThreadJoinExample {
    private static int result = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            result = 99;  // happens-before join
        });

        t.start();
        t.join();  // wait until t finishes

        System.out.println("Main thread sees result = " + result);
    }
}

