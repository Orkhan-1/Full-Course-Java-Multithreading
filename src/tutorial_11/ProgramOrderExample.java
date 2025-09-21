package src.tutorial_11;

public class ProgramOrderExample {
    public static void main(String[] args) {
        int a = 10;        // happens-before
        int b = a + 5;     // this line

        System.out.println("a = " + a + ", b = " + b);
    }
}

/*

The Java Memory Model (JMM) defines "happens-before" relationships to ensure visibility
and ordering of operations across threads.

1. Program Order Rule
Within a single thread, statements appear to run in order.

2. Monitor Lock Rule (synchronized)
Unlocking a monitor happens-before another thread locks it.

3. Volatile Variable Rule
Write to a volatile happens-before a subsequent read.

4. Thread Start Rule
Thread.start() happens-before actions in that thread.

5. Thread Join Rule
All actions in a thread happen-before another thread returns from join().

6. Final Field Rule
Writes to final fields in a constructor happen-before the reference is published.

*/