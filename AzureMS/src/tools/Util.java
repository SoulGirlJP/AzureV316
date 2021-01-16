package tools;

import java.util.regex.Pattern;

public class Util {
    private static Pattern regexPattern = Pattern.compile("^\\$2[a-z]\\$.{56}$");

    public static boolean isStringBCrypt(String password) {
        return regexPattern.matcher(password).matches();
    }
}
