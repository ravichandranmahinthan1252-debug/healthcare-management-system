package util;

public final class IdGenerator {
    private IdGenerator() {}

    public static String nextWithPrefix(String prefix, int existingCount, int width) {
        int n = existingCount + 1;
        String num = String.valueOf(n);
        while (num.length() < width) num = "0" + num;
        return prefix + num;
    }
}
