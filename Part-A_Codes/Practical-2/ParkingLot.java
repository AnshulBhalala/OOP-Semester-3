public class ParkingLot {
    private int twoWheelers = 0, fourWheelers = 0;
    private final int twoCap, fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    public boolean park(String type) {
        if (type.equalsIgnoreCase("two") && twoWheelers < twoCap) {
            twoWheelers++;
            revenue += 20;
            return true;
        }
        if (type.equalsIgnoreCase("four") && fourWheelers < fourCap) {
            fourWheelers++;
            revenue += 40;
            return true;
        }
        System.out.println("Full");
        return false;
    }

    public void leave(String type) {
        if (type.equalsIgnoreCase("two") && twoWheelers > 0)
            twoWheelers--;
        else if (type.equalsIgnoreCase("four") && fourWheelers > 0)
            fourWheelers--;
    }

    public static void main(String[] args) {
        ParkingLot p = new ParkingLot(2, 1);
        p.park("two");
        p.park("four");
        p.park("two");
        p.park("two");
        p.leave("two");
        System.out.println(
                "Two-wheelers: " + p.twoWheelers + ", Four-wheelers: " + p.fourWheelers + ", Revenue: " + revenue);
    }
}
