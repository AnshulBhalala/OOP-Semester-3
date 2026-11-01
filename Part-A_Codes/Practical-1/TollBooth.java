import java.util.*;

public class TollBooth {
    record Vehicle(String number, String type) {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0, bike = 0, car = 0, truck = 0;
        while (true) {
            System.out.print("Vehicle number (done to finish): ");
            String number = sc.next().trim();
            if (number.equalsIgnoreCase("done"))
                break;
            System.out.print("Type (bike/car/truck): ");
            String type = sc.next().trim().toLowerCase();
            Vehicle v = new Vehicle(number, type);
            int toll = switch (v.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> 0;
            };
            if (toll == 0) {
                System.out.println("Unknown vehicle type");
                continue;
            }
            total += toll;
            switch (type) {
                case "bike" -> bike++;
                case "car" -> car++;
                case "truck" -> truck++;
            }
        }
        String frequent = bike >= car && bike >= truck ? "bike" : car >= truck ? "car" : "truck";
        System.out.println("Total toll: " + total);
        System.out.println("Most frequent: " + frequent);
    }
}
