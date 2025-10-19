package src.tutorial_16;

import java.util.concurrent.*;

class SumTask extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int start;
    private final int end;

    SumTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    protected Integer compute() {
        if (end - start <= 3) { // base case
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        }
        int mid = (start + end) / 2;
        SumTask left = new SumTask(arr, start, mid); // asynchronously compute left
        SumTask right = new SumTask(arr, mid, end); // directly compute right
        left.fork();
        return right.compute() + left.join();
    }
}