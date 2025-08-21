import java.util.*;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        int sum = numbers.parallelStream()
                .mapToInt(n -> n * n)
                .sum();

        System.out.println("Sum of squares = " + sum);
    }
}
// This code demonstrates how to use Java's parallel streams to compute the sum of squares of a list of integers.