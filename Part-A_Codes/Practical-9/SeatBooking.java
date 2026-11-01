public class SeatBooking {
    static class Cinema {
        int seatsLeft = 5;

        synchronized boolean book() {
            if (seatsLeft > 0) {
                seatsLeft--;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cinema c = new Cinema();
        int[] booked = { 0 };
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(() -> {
                if (c.book())
                    synchronized (booked) {
                        booked[0]++;
                    }
            });
            ts[i].start();
        }
        for (Thread t : ts)
            t.join();
        System.out.println("Booked: " + booked[0] + ", seats left: " + c.seatsLeft);
    }
}
