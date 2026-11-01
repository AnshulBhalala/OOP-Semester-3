import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {
}

class Account {
    @Id
    private final String accountNumber;
    private final String owner;
    private final long balance;

    Account(String n, String o, long b) {
        accountNumber = n;
        owner = o;
        balance = b;
    }

    public String toString() {
        return accountNumber + " | " + owner + " | " + balance;
    }
}

class Repository<T> {
    private final Map<String, T> data = new HashMap<>();

    public T save(T entity) {
        try {
            for (Field f : entity.getClass().getDeclaredFields())
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    data.put(String.valueOf(f.get(entity)), entity);
                    return entity;
                }
            throw new IllegalArgumentException("Missing @Id");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T x : items)
            save(x);
    }

    public T findById(String id) {
        return data.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public boolean delete(String id) {
        return data.remove(id) != null;
    }

    public static <N extends Number> double average(N[] v) {
        return Arrays.stream(v).mapToDouble(Number::doubleValue).average().orElse(0);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Repository<Account> r = new Repository<>();
        r.saveAll(new Account("AC0001", "Anshul", 5000), new Account("AC0002", "Riya", 2000));
        System.out.println(r.findById("AC0001"));
        r.findAll().forEach(System.out::println);
    }
}
