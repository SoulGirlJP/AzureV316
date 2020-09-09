package tools;

import java.nio.charset.Charset;

public class StringUtil {

    /**
     * Gets a string padded from the left to <code>length</code> by
     * <code>padchar</code>.
     *
     * @param in The input string to be padded.
     * @param padchar The character to pad with.
     * @param length The length to pad to.
     * @return The padded string.
     */
    private static Charset maple = Charset.forName("MS949");

    public static final String getLeftPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int x = in.getBytes(maple).length; x < length; x++) {
            builder.append(padchar);
        }
        builder.append(in);
        return builder.toString();
    }

    /**
     * Gets a string padded from the right to <code>length</code> by
     * <code>padchar</code>.
     *
     * @param in The input string to be padded.
     * @param padchar The character to pad with.
     * @param length The length to pad to.
     * @return The padded string.
     */
    public static final String getRightPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(in);
        for (int x = in.getBytes(maple).length; x < length; x++) {
            builder.append(padchar);
        }
        return builder.toString();
    }

    /**
     * Joins an array of strings starting from string <code>start</code> with a
     * space.
     *
     * @param arr The array of strings to join.
     * @param start Starting from which string.
     * @return The joined strings.
     */
    public static final String joinStringFrom(final String arr[], final int start) {
        return joinStringFrom(arr, start, " ");
    }

    /**
     * Joins an array of strings starting from string <code>start</code> with
     * <code>sep</code> as a seperator.
     *
     * @param arr The array of strings to join.
     * @param start Starting from which string.
     * @return The joined strings.
     */
    public static final String joinStringFrom(final String arr[], final int start, final String sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(sep);
            }
        }
        return builder.toString();
    }

    /**
     * Makes an enum name human readable (fixes spaces, capitalization, etc)
     *
     * @param enumName The name of the enum to neaten up.
     * @return The human-readable enum name.
     */
    public static final String makeEnumHumanReadable(final String enumName) {
        StringBuilder builder = new StringBuilder(enumName.length() + 1);
        for (String word : enumName.split("_")) {
            if (word.length() <= 2) {
                builder.append(word); // assume that it's an abbrevation
            } else {
                builder.append(word.charAt(0));
                builder.append(word.substring(1).toLowerCase());
            }
            builder.append(' ');
        }
        return builder.substring(0, enumName.length());
    }

    /**
     * Counts the number of <code>chr</code>'s in <code>str</code>.
     *
     * @param str The string to check for instances of <code>chr</code>.
     * @param chr The character to check for.
     * @return The number of times <code>chr</code> occurs in <code>str</code>.
     */
    public static final int countCharacters(final String str, final char chr) {
        int ret = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == chr) {
                ret++;
            }
        }
        return ret;
    }
}
