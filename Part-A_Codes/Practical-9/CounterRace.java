public class CounterRace {
    static class Counter {
        int value = 0;

        synchronized void increment() {
            value++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    c.increment();
            });
            ts[i].start();
        }
        for (Thread t : ts)
            t.join();
        System.out.println("Final counter: " + c.value + " (expected 10000)");
    }
}
