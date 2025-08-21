public class Message {
    private String content;
    private boolean empty = true; // Flag to indicate if message is available

    // Called by the Producer thread
    public synchronized void put(String message) {
        // 1. Wait until the message has been consumed
        while (!empty) {
            try {
                System.out.println("Producer waiting...");
                wait(); // Releases the lock and waits
            } catch (InterruptedException e) {}
        }
        // 2. Now produce the message
        this.content = message;
        this.empty = false;
        System.out.println("Produced: " + message);

        // 3. Notify the consumer that a message is ready
        notifyAll(); // Wakes up the Consumer thread
    }

    // Called by the Consumer thread
    public synchronized String take() {
        // 1. Wait until a message is produced
        while (empty) {
            try {
                System.out.println("Consumer waiting...");
                wait(); // Releases the lock and waits
            } catch (InterruptedException e) {}
        }
        // 2. Now consume the message
        this.empty = true;
        System.out.println("Consumed: " + this.content);

        // 3. Notify the producer that it can produce another message
        notifyAll(); // Wakes up the Producer thread

        // 4. Return the message
        return this.content;
    }
}