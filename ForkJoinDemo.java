import java.util.concurrent.ForkJoinPool;

public class ForkJoinDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9};
        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(new SumTask(arr, 0, arr.length));
        System.out.println("Sum = " + result);
    }
}