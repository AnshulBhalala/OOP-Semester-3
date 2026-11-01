class SampleResource implements AutoCloseable {
    public void use() {
        System.out.println("Using resource");
    }

    public void close() {
        System.out.println("Resource closed");
    }
}

public class ResourceDemo {
    public static void main(String[] args) {
        try (SampleResource r = new SampleResource()) {
            r.use();
            if (args.length > 0)
                throw new RuntimeException("Demo failure");
        } catch (RuntimeException e) {
            System.out.println("Reported: " + e.getMessage());
        }
    }
}
