package src.tutorial_9;

public class WaitNotifyDemo {
    public static void main(String[] args) {
        Message message = new Message();

        // Producer thread
        Thread producer = new Thread(() -> {
            String[] msgs = { "Hello", "from", "wait()", "notify()", "demo" };
            for (String m : msgs) {
                message.put(m);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                message.take();
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });

        producer.start();
        consumer.start();
    }
}

/*

Monitor (Lock) {
    ownerThread   // thread that currently holds the lock
    entryQueue[]  // threads waiting to acquire the lock (ready to run)
    waitSet[]     // threads that called wait() on this monitor
}

 */