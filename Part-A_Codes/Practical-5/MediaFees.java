abstract class Media {
    abstract double lateFee(int days);
}

class Book extends Media {
    double lateFee(int d) {
        return Math.max(0, d) * 2.0;
    }
}

class DVD extends Media {
    double lateFee(int d) {
        return Math.max(0, d) * 5.0;
    }
}

class Game extends Media {
    double lateFee(int d) {
        return Math.max(0, d) * 8.0;
    }
}

public class MediaFees {
    public static void main(String[] args) {
        Media[] items = { new Book(), new DVD(), new Game() };
        double total = 0;
        for (Media m : items) {
            double f = m.lateFee(3);
            total += f;
            System.out.println(m.getClass().getSimpleName() + ": " + f);
        }
        System.out.println("Total fees: " + total);
    }
}
