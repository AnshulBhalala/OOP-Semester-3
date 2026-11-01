import java.util.*;

public class RPSLS {
    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    static int winner(Move a, Move b) {
        if (a == b)
            return 0;
        boolean beats = switch (a) {
            case ROCK -> b == Move.SCISSORS || b == Move.LIZARD;
            case PAPER -> b == Move.ROCK || b == Move.SPOCK;
            case SCISSORS -> b == Move.PAPER || b == Move.LIZARD;
            case LIZARD -> b == Move.SPOCK || b == Move.PAPER;
            case SPOCK -> b == Move.SCISSORS || b == Move.ROCK;
        };
        return beats ? 1 : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int player = 0, computer = 0;
        for (int i = 1; i <= 5; i++) {
            System.out.print("Round " + i + " (ROCK/PAPER/SCISSORS/LIZARD/SPOCK): ");
            Move p = Move.valueOf(sc.next().toUpperCase());
            Move c = Move.values()[random.nextInt(Move.values().length)];
            int result = winner(p, c);
            if (result > 0)
                player++;
            else if (result < 0)
                computer++;
            System.out.println("You: " + p + ", Computer: " + c + " -> "
                    + (result == 0 ? "Tie" : result > 0 ? "You win" : "Computer wins"));
        }
        System.out.println(player > computer ? "You win " + player + "–" + computer
                : player < computer ? "Computer wins " + computer + "–" + player
                        : "Overall tie " + player + "–" + computer);
    }
}
