import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Person {
    @Column(name = "name")
    String name;
    @Column(name = "age")
    int age;

    public String toString() {
        return name + " (" + age + ")";
    }
}

public class ColumnMapper {
    public static void main(String[] args) throws Exception {
        String[] header = { "age", "name" }, row = { "20", "Anshul" };
        Person p = new Person();
        for (Field f : Person.class.getDeclaredFields()) {
            Column c = f.getAnnotation(Column.class);
            for (int i = 0; i < header.length; i++)
                if (c.name().equals(header[i])) {
                    f.setAccessible(true);
                    if (f.getType() == int.class)
                        f.setInt(p, Integer.parseInt(row[i]));
                    else
                        f.set(p, row[i]);
                }
        }
        System.out.println(p);
    }
}
