public class PasswordChecker {
    public static String strength(String pw) {
        int score = 0;
        if (pw.length() >= 8)
            score++;
        if (pw.matches(".*[A-Z].*"))
            score++;
        if (pw.matches(".*\\d.*"))
            score++;
        if (pw.matches(".*[^A-Za-z0-9].*"))
            score++;
        return score <= 1 ? "Weak" : score <= 3 ? "Medium" : "Strong";
    }

    public static void main(String[] args) {
        for (String p : new String[] { "abc", "Abcd1234!", "hello123", "HELLO!!" })
            System.out.println(p + " -> " + strength(p));
    }
}
