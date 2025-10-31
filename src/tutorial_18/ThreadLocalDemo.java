package src.tutorial_18;

public class ThreadLocalDemo {

    // Each thread will have its own copy of this variable
    private static ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();

    public static void main(String[] args) {
        // Thread 1
        Thread thread1 = new Thread(() -> {
            threadLocalValue.set(100);
            System.out.println(Thread.currentThread().getName() + " -> " + threadLocalValue.get());
        });

        // Thread 2
        Thread thread2 = new Thread(() -> {
            threadLocalValue.set(200);
            System.out.println(Thread.currentThread().getName() + " -> " + threadLocalValue.get());
        });

        thread1.start();
        thread2.start();
    }
}


/*
    ┌──────────────────────────┐
                │        JVM Heap          │
                │ ┌─────────────────────┐  │
                │ │ Thread t1 object    │  │  <-- Java object
                │ │  - name             │  │
                │ │  - priority         │  │
                │ │  - threadLocals → … │──┼───┐
                │ └─────────────────────┘  │   │
                └──────────────────────────┘   │
                                                │
                                                ▼
                                ┌────────────────────────┐
                                │ OS Thread / Stack Area │
                                │ ─ call frames          │
                                │ ─ local variables      │
                                │ ─ return addresses     │
                                └────────────────────────┘

          ┌────────────────────────────┐
          │        ThreadLocal@1       │
          └────────────┬───────────────┘
                       │
         ┌─────────────┴────────────────────────────┐
         │ Each thread has its own ThreadLocalMap   │
         └──────────────────────────────────────────┘
              ↓                 ↓                 ↓
        ┌────────────┐    ┌────────────┐    ┌────────────┐
        │ Thread t1  │    │ Thread t2  │    │ Thread t3  │
        │------------│    │------------│    │------------│
        │ Map:       │    │ Map:       │    │ Map:       │
        │ {          │    │ {          │    │ {          │
        │   local→10 │    │   local→20 │    │   local→30 │
        │ }          │    │ }          │    │ }          │
        └────────────┘    └────────────┘    └────────────┘

ThreadLocalExample.threadLocalValue
    |
    +---> Thread-0(ThreadLocalMap)
    |          └── (ThreadLocal@1 → 100)
    |
    +---> Thread-1(ThreadLocalMap)
               └── (ThreadLocal@1 → 200)

*/