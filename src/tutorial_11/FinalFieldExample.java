package src.tutorial_11;

public class FinalFieldExample {
    private static Config config;

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(() -> {
            config = new Config();  // safely publish with final field
            System.out.println("Writer: Config created with size=" + config.size);
        });

        Thread reader = new Thread(() -> {
            while (config == null) { } // wait until published
            System.out.println("Reader: Config size = " + config.size);
        });

        writer.start();
        reader.start();
    }
}