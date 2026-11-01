package util;

import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern MOBILE = Pattern.compile("[6-9][0-9]{9}");

    private Validator() {
    }

    public static boolean isValidMobile(String value) {
        return value != null && MOBILE.matcher(value).matches();
    }
}
