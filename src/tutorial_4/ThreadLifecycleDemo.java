package src.tutorial_4;

public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        });

        System.out.println("State before start: " + t.getState()); // NEW
        t.start();
        System.out.println("State after start: " + t.getState()); // RUNNABLE

        // JVM thread → mapped to OS thread → OS scheduler → assigned to CPU core → executes
        //                      |                                            (moves between cores)
        //                      |
        //                      V
        // JVM calls native code (using JNI)
        // Native code (pthread_create on Linux/CreateThread on Windows) → creates OS-level thread


        t.join();
        System.out.println("State after completion: " + t.getState()); // TERMINATED
    }
}
