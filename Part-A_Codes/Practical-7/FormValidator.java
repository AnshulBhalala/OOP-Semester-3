import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    String name = " ";
    @MaxLength(10)
    String username = "verylongusername";

    SignupForm(String n, String u) {
        name = n;
        username = u;
    }
}

public class FormValidator {
    public static void main(String[] args) throws Exception {
        SignupForm form = new SignupForm(" ", "verylongusername");
        List<String> errors = new ArrayList<>();
        for (Field f : form.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object v = f.get(form);
            if (f.isAnnotationPresent(NotBlank.class) && (!(v instanceof String) || ((String) v).isBlank()))
                errors.add(f.getName() + " must not be blank");
            MaxLength max = f.getAnnotation(MaxLength.class);
            if (max != null && v instanceof String s && s.length() > max.value())
                errors.add(f.getName() + " exceeds " + max.value());
        }
        System.out.println(errors);
    }
}
