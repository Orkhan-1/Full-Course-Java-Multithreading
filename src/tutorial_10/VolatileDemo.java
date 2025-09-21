package src.tutorial_10;

public class VolatileDemo {
    private static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (running) {
                // busy wait
            }
            System.out.println("Stopped!");
        });

        t.start();
        Thread.sleep(1000);
        running = false; // without volatile, thread may never stop
    }
}




// ======================================================
//          Hardware Visualization for "running"
// ======================================================
//
//                                 |   RAM   |
//                                | running=true |
//
//               CPU Core 1 (Thread t)             CPU Core 2 (main thread)
//              +-----------+                    +-----------+
//              | Registers |                    | Registers |
//              | running=1 |   <--- cached      | running=1 |
//              +-----------+                    +-----------+
//              |   L1      |                    |   L1      |
//              | running=1 |   <--- cached      | running=1 |
//              +-----------+                    +-----------+
//              |   L2      |                    |   L2      |
//              | running=1 |                    | running=1 |
//              +-----------+                    +-----------+
//              | L3 (shared)                    | L3 (shared)
//              | running=1 |                    | running=1 |
//              +-----------+                    +-----------+
//
//
//   - Core 2 (main thread) updates `running=false` in its cache and RAM
//   - Core 1 (worker thread) may still read old cached value `running=true`
//   - => Thread may spin forever