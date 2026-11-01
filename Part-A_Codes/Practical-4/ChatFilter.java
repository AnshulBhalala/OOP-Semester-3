import java.util.*;

public class ChatFilter {
    public static void main(String[] args) {
        String[] logs = { "10:05 alice Hello there", "10:06 bob How are you", "malformed", "10:07 cara HELLO again" };
        Scanner sc = new Scanner(System.in);
        System.out.print("Keyword: ");
        String key = sc.nextLine().toLowerCase();
        StringBuilder report = new StringBuilder();
        int matches = 0;
        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3)
                continue;
            if (parts[2].toLowerCase().contains(key)) {
                matches++;
                report.append(parts[0]).append(" ").append(parts[1]).append(": ").append(parts[2])
                        .append(System.lineSeparator());
            }
        }
        System.out.println("Matches: " + matches);
        System.out.print(report);
    }
}
