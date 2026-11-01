public class ThreadLifecycle {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> System.out.println("Running task"));
        System.out.println(t.getState());
        t.start();
        t.join();
        System.out.println(t.getState());
    }
}
