import java.util.regex.*;

public class TemplateFiller {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = { "name", "id" }, values = { "Riya", "A07" };
        Matcher m = Pattern.compile("\\{(\\w+)\\}").matcher(template);
        StringBuilder out = new StringBuilder();
        int last = 0;
        while (m.find()) {
            out.append(template, last, m.start());
            String key = m.group(1), value = "[?]";
            for (int i = 0; i < names.length; i++)
                if (names[i].equals(key))
                    value = values[i];
            out.append(value);
            last = m.end();
        }
        out.append(template.substring(last));
        System.out.println(out);
    }
}
