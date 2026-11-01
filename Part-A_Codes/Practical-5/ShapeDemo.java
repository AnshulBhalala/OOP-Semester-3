abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    double area() {
        return Math.PI * r * r;
    }
}

class Rectangle extends Shape {
    double w, h;

    Rectangle(double w, double h) {
        this.w = w;
        this.h = h;
    }

    double area() {
        return w * h;
    }
}

class Triangle extends Shape {
    double b, h;

    Triangle(double b, double h) {
        this.b = b;
        this.h = h;
    }

    double area() {
        return b * h / 2;
    }
}

public class ShapeDemo {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2), new Rectangle(3, 4), new Triangle(3, 5) };
        double total = 0, largest = 0;
        for (Shape s : shapes) {
            double a = s.area();
            total += a;
            largest = Math.max(largest, a);
            System.out.printf("%s area=%.2f%n", s.getClass().getSimpleName(), a);
        }
        System.out.printf("Total=%.2f, Largest=%.2f%n", total, largest);
    }
}
