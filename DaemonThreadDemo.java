public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("Daemon running...");
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        });

        daemon.setDaemon(true);
        daemon.start();

        System.out.println("Main thread finished");
    }
}
