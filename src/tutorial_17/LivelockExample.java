package src.tutorial_17;

public class LivelockExample {

    static class Worker {
        private boolean active = true;
        public boolean isActive() { return active; }
        public void work(Worker other) {
            while (active) {
                if (other.isActive()) {
                    System.out.println(Thread.currentThread().getName()
                            + ": giving way to " + other.getClass().getSimpleName());
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + ": doing work");
                active = false;
            }
        }
    }

    public static void main(String[] args) {
        Worker w1 = new Worker();
        Worker w2 = new Worker();

        new Thread(() -> w1.work(w2), "Worker-1").start();
        new Thread(() -> w2.work(w1), "Worker-2").start();
    }
}

