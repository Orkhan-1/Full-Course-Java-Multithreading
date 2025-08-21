public class Counter {
    private volatile int count = 0;

    public void increment() {
        count++; // This is the DANGER line!
    }
}