package ysaak.imeji.utils;

public final class StringUtils {
    public static String substringAfterLast(String str, char character) {
        if (str == null) {
            return null;
        }

        int index = str.lastIndexOf(character);

        if (index == -1) {
            return str;
        }
        else {
            return str.substring(index + 1);
        }
    }
}
