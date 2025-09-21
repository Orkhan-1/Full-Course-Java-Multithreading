package src.tutorial_11;

public class VolatileExample {
    private static volatile boolean ready = false;
    private static int number = 0;

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            number = 42;         // normal write
            ready = true;        // volatile write (happens-before read)
            System.out.println("Writer: set number=42 and ready=true");
        });

        Thread reader = new Thread(() -> {
            while (!ready) { }   // busy-wait until volatile read
            System.out.println("Reader: number is " + number);
        });

        writer.start();
        reader.start();
    }
}
