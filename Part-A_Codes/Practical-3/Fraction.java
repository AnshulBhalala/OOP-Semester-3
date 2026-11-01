import java.util.Objects;

public class Fraction {
    private final int num, den;

    public Fraction(int num, int den) {
        if (den == 0)
            throw new IllegalArgumentException("Denominator cannot be zero");
        if (den < 0) {
            num = -num;
            den = -den;
        }
        int g = gcd(Math.abs(num), den);
        this.num = num / g;
        this.den = den / g;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : a;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Fraction f && num == f.num && den == f.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }

    public static void main(String[] args) {
        Fraction a = new Fraction(2, 4), b = new Fraction(1, 2);
        System.out.println(a);
        System.out.println("Equal: " + a.equals(b));
    }
}
