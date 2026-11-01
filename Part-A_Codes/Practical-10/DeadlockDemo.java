public class DeadlockDemo {
    public static void main(String[] args) {
        Object a = new Object(), b = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (a) {
                sleep();
                synchronized (b) {
                    System.out.println("t1 acquired both");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (b) {
                sleep();
                synchronized (a) {
                    System.out.println("t2 acquired both");
                }
            }
        });
        t1.start();
        t2.start();
        System.out.println("Demo may deadlock; stop manually if both threads block.");
    }

    static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
