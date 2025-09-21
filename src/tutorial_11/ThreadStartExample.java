package src.tutorial_11;

public class ThreadStartExample {
    private static int data = 0;

    public static void main(String[] args) {
        data = 42;  // happens-before thread start

        Thread t = new Thread(() -> {
            // guaranteed to see data=42
            System.out.println("Thread sees data = " + data);
        });

        t.start();
    }
}

