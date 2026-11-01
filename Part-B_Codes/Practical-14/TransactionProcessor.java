package service;

import java.util.List;
import java.util.concurrent.*;

public class TransactionProcessor implements AutoCloseable {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void processBatch(List<Runnable> transactions) throws InterruptedException, ExecutionException {
        List<Future<?>> futures = executor.invokeAll(transactions);
        for (Future<?> future : futures)
            future.get();
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
