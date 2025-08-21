public class MemoryModelDemo {
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(() -> {
            flag = true; // may not be seen by reader without sync
        });

        Thread reader = new Thread(() -> {
            while (!flag) {
                // busy wait
            }
            System.out.println("Flag is true!");
        });

        writer.start();
        reader.start();

        writer.join();
        reader.join();
    }
}
// This code demonstrates a potential visibility issue in a multithreaded environment.