import java.util.concurrent.*;

public class ThreadPoolRunner {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int n = i;
            pool.submit(() -> System.out.println("Task " + n + " on " + Thread.currentThread().getName()));
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
    }
}
