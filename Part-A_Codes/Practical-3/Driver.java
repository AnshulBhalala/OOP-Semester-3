import java.util.*;

public class Driver {
    public static void main(String[] args) {
        Point[] points = { new Point(1, 2), new Point(2, 3), new Point(1, 2), new Point(4, 5), new Point(2, 3) };
        List<Point> distinct = new ArrayList<>();
        for (Point p : points)
            if (!distinct.contains(p))
                distinct.add(p);
        System.out.println("Distinct: " + distinct.size());
    }
}
