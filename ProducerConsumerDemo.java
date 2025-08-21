public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Message message = new Message();

        // Consumer Thread (will wait first)
        new Thread(() -> {
            String result = message.take();
        }).start();

        // Small delay to ensure consumer starts first
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Producer Thread (will notify consumer)
        new Thread(() -> {
            message.put("Hello World!");
        }).start();
    }
}