package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal CSV reader/writer using standard Java only.
 * Handles quotes and commas inside quoted fields.
 */
public final class CsvUtil {
    private CsvUtil() {}

    public static List<String[]> readAll(File file) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // skip empty
                if (line.trim().isEmpty()) continue;
                rows.add(parseLine(line));
            }
        }
        return rows;
    }

    public static void writeAll(File file, List<String[]> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (String[] row : rows) {
                bw.write(toLine(row));
                bw.newLine();
            }
        }
    }

    public static void appendRow(File file, String[] row) throws IOException {
        boolean exists = file.exists();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            if (exists && file.length() > 0) {
                // ensure newline before append if needed
                bw.newLine();
            }
            bw.write(toLine(row));
        }
    }

    private static String[] parseLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // escaped quote
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    private static String toLine(String[] row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(escape(row[i]));
        }
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        boolean mustQuote = s.contains(",") || s.contains("\n") || s.contains("\r") || s.contains("\"");
        String v = s.replace("\"", "\"\"");
        return mustQuote ? "\"" + v + "\"" : v;
    }
}
