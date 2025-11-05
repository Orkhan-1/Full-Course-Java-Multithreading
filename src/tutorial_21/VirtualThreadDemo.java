package src.tutorial_21;


/*
============================================================
          Comparing Platform Threads vs Virtual Threads
============================================================
This example runs 10,000 simulated tasks using:

1.Traditional Fixed Thread Pool (Platform Threads)
2.Virtual Thread per Task Executor (Java 21+)

You’ll see:
- Traditional threads create limited concurrency (due to OS threads)
- Virtual threads easily scale to thousands of concurrent tasks
============================================================
*/

import java.util.concurrent.*;

public class VirtualThreadDemo {

    public static void main(String[] args) throws Exception {
        int taskCount = 10000;

        System.out.println("===========================================");
        System.out.println(" 1.Traditional Thread Pool (Platform Threads)");
        System.out.println("===========================================");
        runWithExecutor(Executors.newFixedThreadPool(200), taskCount);

        System.out.println("\n===========================================");
        System.out.println(" 2.Virtual Threads (Project Loom, Java 21+)");
        System.out.println("===========================================");
        runWithExecutor(Executors.newVirtualThreadPerTaskExecutor(), taskCount);
    }

    private static void runWithExecutor(ExecutorService executor, int taskCount)
            throws InterruptedException {
        long start = System.currentTimeMillis();

        try (executor) {
            for (int i = 0; i < taskCount; i++) {
                int id = i;
                executor.submit(() -> {
                    try {
                        // Simulate blocking I/O (e.g., DB call, HTTP call)
                        Thread.sleep(1000);
                        if (id % 1000 == 0) {
                            System.out.println("Task " + id + " done by " + Thread.currentThread());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } // Executor automatically waits for all tasks to complete

        long end = System.currentTimeMillis();
        System.out.printf("Completed %d tasks in %.2f seconds%n",
                taskCount, (end - start) / 1000.0);
    }
}



/*
============================================================
                     Project Loom (Java 21)
============================================================
What Is Project Loom?
------------------------------------------------------------
- Project Loom is a long-term OpenJDK effort to **simplify concurrency**
  and make **high-throughput, scalable applications** easier to write.
- Its main goal is to eliminate the complexity and overhead of traditional
  thread-based concurrency in Java.

Introduces two major features:
1. **Virtual Threads** – lightweight, JVM-managed threads.
2. **Structured Concurrency** – coordinated lifecycle management of threads.

Think of it as Java’s answer to async/await or coroutines,
but **keeping the familiar Thread API** — no new paradigm required.

============================================================
                 Virtual Threads (Core Feature)
============================================================
- Introduced in Java 21 (as a stable feature of Project Loom).
- Each Virtual Thread is a lightweight thread managed by the JVM.
- Thousands or even millions of Virtual Threads can run
  on a small number of OS-level threads (carrier threads).

============================================================
        Before Java 21 — Traditional Platform Threads
============================================================

+-----------------------------+
|        Operating System     |
|-----------------------------|
|   OS Thread #1              |
|   OS Thread #2              |
|   OS Thread #3              |
+-----------------------------+
         ▲       ▲       ▲
         │       │       │
+-----------------------------+
| Java Thread #1              |
| Java Thread #2              |
| Java Thread #3              |
+-----------------------------+

Each Java thread maps 1:1 to an OS thread.
   - Expensive to create (≈1 MB stack per thread)
   - Hard to scale beyond a few thousand threads
   - Blocking operations block the OS thread itself

============================================================
        After Java 21 — With Virtual Threads
============================================================

+-----------------------------+
|        JVM Scheduler        |
|-----------------------------|
|  Carrier Thread #1          | <─┐
|  Carrier Thread #2          |   │ executes
|  Carrier Thread #3          | <─┘
+-----------------------------+
         ▲       ▲
         │       │
+-----------------------------+
| Virtual Thread #1 (sleep)   |
| Virtual Thread #2 (running) |
| Virtual Thread #3 (waiting) |
| Virtual Thread #4 (runnable)|
+-----------------------------+

Many Virtual Threads share few Carrier Threads.
   - Each Carrier Thread = 1 OS Thread
   - Virtual Threads are parked/resumed by JVM
   - JVM handles scheduling, not the OS
   - Ideal for massive I/O-bound concurrency

============================================================
         How a Virtual Thread Is Parked and Resumed
============================================================

Step 1: Virtual Thread starts executing user code
-------------------------------------------------
VT#42 → Mounted on Carrier Thread #1 (OS thread)
    |
    |  executes user code
    v
[Running on CPU]

Step 2: Virtual Thread hits a blocking call (e.g., Thread.sleep())
-----------------------------------------------------------------
VT#42 blocks → JVM intercepts → saves stack frames on the heap
    |
    |  Carrier Thread #1 is now freed
    v
[Carrier Thread returns to JVM scheduler]

Step 3: Carrier Thread picks another runnable Virtual Thread
------------------------------------------------------------
Carrier Thread #1 → now runs VT#87

Step 4: When I/O completes or sleep time expires
------------------------------------------------
JVM Scheduler remounts VT#42 stack onto any free Carrier Thread
    |
    |  could be same or different OS thread
    v
[VT#42 resumes execution → continues user code]

Diagram Summary:
------------------------------------------------------------
   (Mount)        (Park)          (Resume)
   VT → Carrier → Heap → Carrier → Continue

Result:
- OS thread (carrier) is never idle waiting for I/O
- Virtual threads can scale into the millions efficiently

============================================================
                 Comparison Summary
============================================================
| Feature              | Platform Thread | Virtual Thread  |
|----------------------|-----------------|-----------------|
| Backed by            | OS Thread       | JVM-managed     |
| Stack Memory         | ~1 MB           | Few KB on heap  |
| Blocking Behavior    | Blocks OS thread| Parks/frees carrier |
| Scheduling           | OS Scheduler    | JVM Scheduler   |
| Scalability          | Thousands       | Millions        |
| Best For             | CPU-bound tasks | I/O-bound tasks |

============================================================
                 Key Takeaways
============================================================
- Project Loom makes high concurrency simple and natural.
- Virtual Threads are still real Java Threads — just cheaper.
- JVM handles parking/resuming, freeing OS threads for more work.
- “Many Virtual Threads → Few Carrier Threads → OS Threads.”
- Backed by continuations and stack parking in the JVM.
- Use Virtual Threads for I/O-heavy workloads and microservices.

============================================================
                 When to Use (and When Not)
============================================================

1.Use Virtual Threads when:
-Your app does a lot of I/O (network, DB calls, HTTP).
-You want massive concurrency without thread pools.

2.Avoid or be cautious when:
-You’re doing CPU-bound tasks (e.g. Image / Video Processing, Compression / Decompression)
 Virtual threads don’t make CPUs faster
-You’re using native blocking calls that don’t yield to the JVM scheduler.

*/
