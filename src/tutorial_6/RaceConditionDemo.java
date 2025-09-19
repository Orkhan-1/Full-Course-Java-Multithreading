package src.tutorial_6;

public class RaceConditionDemo {
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final counter value: " + counter);
    }
}







// What you write: counter++
// What the compiler sees: counter = counter + 1
// What the CPU actually does: performs operations in Three Steps


// Three Steps:
// 1. Load the value of counter from memory into a CPU register
// 2. Increment the value in the CPU register
// 3. Store the incremented value back from the CPU register to memory

// ======================================================
//          Hardware Visualization
// ======================================================
//
//                                 |   RAM   |
//                                | counter=0 |
//
//               CPU Core 1                       CPU Core 2
//              +-----------+                    +-----------+
//              | Registers |                    | Registers |
//              |  regA=0   |                    |  regB=0   |
//              +-----------+                    +-----------+
//              | L1 Cache  |                    | L1 Cache  |
//              | counter=0 |                    | counter=0 |
//              +-----------+                    +-----------+
//              | L2 Cache  |                    | L2 Cache  |
//              | counter=0 |                    | counter=0 |
//              +-----------+                    +-----------+
//              | L3 Cache  |   (shared L3)      | L3 Cache  |
//              | counter=0 |                    | counter=0 |
//              +-----------+                    +-----------+

// Steps for incrementing a shared counter:
//  1. Load from RAM into CPU register (via cache)
//  2. Increment register
//  3. Write back to RAM (flush cache)  0 -> t1 = 1 => t2 = 2