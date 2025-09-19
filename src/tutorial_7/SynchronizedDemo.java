package src.tutorial_7;

public class SynchronizedDemo {
    private static int counter = 0;

    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 40000; i++) {
                increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final counter value: " + counter);
    }
}
/*

Monitor {
    ownerThread   // thread that currently holds the lock
    entryQueue[]  // threads waiting to acquire the lock (ready to run)
    waitSet[]     // threads that called wait() on this monitor
}

1. t2 wants the lock → monitor owned by t1

2. JVM adds t2 to monitor.entryQueue

3. JVM calls futex_wait(&monitor) → OS puts t2 to blocked state

4. CPU core assigned to t2 is freed by OS scheduler

5. Later, t1 releases monitor → JVM picks t2 from entryQueue

6. JVM calls futex_wake(&monitor) → OS marks t2 as runnable

7. OS scheduler eventually schedules t2 on a CPU core

 */