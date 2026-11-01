interface Switchable {
    void on();

    void off();

    default void toggle() {
        off();
    }
}

class Fan implements Switchable {
    boolean state;

    public void on() {
        state = true;
        System.out.println("Fan ON");
    }

    public void off() {
        state = false;
        System.out.println("Fan OFF");
    }
}

class Light implements Switchable {
    boolean state;

    public void on() {
        state = true;
        System.out.println("Light ON");
    }

    public void off() {
        state = false;
        System.out.println("Light OFF");
    }
}

@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {
        Switchable[] devices = { new Fan(), new Light() };
        for (Switchable d : devices) {
            d.on();
            d.off();
        }
        SwitchPolicy policy = (device, hour) -> hour >= 6 && hour < 23;
        System.out.println("Allowed at 20: " + policy.maySwitchOn(devices[0], 20));
    }
}
