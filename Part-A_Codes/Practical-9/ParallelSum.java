import java.util.*;

public class ParallelSum {
    public static void main(String[] args) throws InterruptedException {
        int[] values = new int[10000];
        Arrays.fill(values, 1);
        long[] sums = new long[4];
        Thread[] ts = new Thread[4];
        for (int i = 0; i < 4; i++) {
            final int k = i;
            ts[i] = new Thread(() -> {
                for (int j = k * 2500; j < (k + 1) * 2500; j++)
                    sums[k] += values[j];
            });
            ts[i].start();
        }
        for (Thread t : ts)
            t.join();
        System.out.println("Parallel sum: " + Arrays.stream(sums).sum());
    }
}
