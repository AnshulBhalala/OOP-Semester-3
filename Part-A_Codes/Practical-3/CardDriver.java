import java.util.*;

public class CardDriver {
    public static void main(String[] args) {
        Card[] cards = { new Card("Ace", "Spades"), new Card("King", "Hearts"), new Card("Ace", "Spades") };
        List<Card> seen = new ArrayList<>();
        for (Card c : cards) {
            if (seen.contains(c)) {
                System.out.println("Duplicate found: " + c);
                break;
            }
            seen.add(c);
        }
    }
}
