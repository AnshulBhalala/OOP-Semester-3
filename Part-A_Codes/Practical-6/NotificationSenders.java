@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class EmailNotifier implements Notifier {
    public void send(String m) {
        System.out.println("Email: " + m);
    }
}

class SmsNotifier implements Notifier, Urgent {
    public void send(String m) {
        System.out.println("SMS: " + m);
    }
}

public class NotificationSenders {
    public static void main(String[] args) {
        Notifier[] senders = { new EmailNotifier(), new SmsNotifier() };
        for (Notifier n : senders) {
            n.send("System maintenance at 8 PM");
            if (n instanceof Urgent)
                n.send("URGENT: System maintenance at 8 PM");
        }
    }
}
