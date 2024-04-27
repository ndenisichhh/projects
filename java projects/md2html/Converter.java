package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Converter {
    private final char[] bufferReader = new char[1024];
    private final StringBuilder line = new StringBuilder();
    private final String lineSeparator = System.lineSeparator();
    private final int lengthSeparator = lineSeparator.length();
    private Reader reader;
    private int read;
    private int start = 0;
    private BufferedWriter writer;

    public Converter(String in, String out) {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8));
            readNextPart();
        } catch (IOException e) {
            System.out.println("Файл для чтения не найден: " + e.getMessage());
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Файл для записи не найден: " + e.getMessage());
        }
        buildHtml();
    }

    private void readNextPart() {
        try {
            if (read != -1) {
                read = reader.read(bufferReader);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении reader'a: " + e.getMessage());
        }
    }

    // проверяет оканчивается ли строка на repeat сепараторов
    private boolean isLineSeparator(StringBuilder s, int index, int repeat) {
        String lineSep = lineSeparator.repeat(repeat);
        if (s.length() < lineSep.length() || index < 0) {
            return false;
        }
        return lineSep.equals(s.substring(index));
    }

    // удаляет сепараторы из начала буфера
    private void skipSeparators() {
        if (start == read) {
            start = 0;
            readNextPart();
        }
        while (read != -1) {
            for (int i = 0; i < lengthSeparator; i++) {
                if (lineSeparator.charAt(i) != bufferReader[start]) return;
                start++;
                if (start == read) {
                    start = 0;
                    readNextPart();
                }
            }
        }
    }

    // удаляет сепараторы из конца StringBuilder
    private void deleteLastSeparators(StringBuilder sb) {
        int c = 1;
        while (isLineSeparator(sb, sb.length() - lengthSeparator * c, 1)) {
            c++;
        }
        if (read == -1) c--;
        sb.setLength(sb.length() - lengthSeparator * c);
    }

    private boolean isMarkdown(char c) {
        return c == '*' || c == '_' || c == '`' || c == '-' || c == '%';
    }

    private boolean checkTwo(StringBuilder part, int l) {
        return l < (part.length() - 1) && (part.charAt(l) == part.charAt(l + 1));
    }

    private String escapeHtml(char c) {
        return switch (c) {
            case '<' -> "&lt;";
            case '>' -> "&gt;";
            case '&' -> "&amp;";
            default -> String.valueOf(c);
        };
    }

    // строит разметку в подстроке
    private void buildMd(StringBuilder line, StringBuilder part, int l, int r, String mk) {
        part.setLength(r);
        line.append("<").append(mk).append(">");
        md2html(line, part, l);
        line.append("</").append(mk).append(">");
    }

    // строит разметку в подстроке
    private void repl(StringBuilder line, StringBuilder part) {
        int l = 0, r = part.length() - 1;
        if (part.charAt(r) == '`' && part.charAt(l) == '`') {
            buildMd(line, part, l + 1, r, "code");
        } else if (part.charAt(r) == '%' && part.charAt(l) == '%') {
            buildMd(line, part, l + 1, r, "var");
        } else if (part.substring(r - 1, r + 1).equals("--") && part.substring(l, l + 2).equals("--")) {
            buildMd(line, part, l + 2, r - 1, "s");
        } else if (part.substring(r - 1, r + 1).equals(part.substring(l, l + 2))) {
            buildMd(line, part, l + 2, r - 1, "strong");
        } else {
            buildMd(line, part, l + 1, r, "em");
        }
    }

    // проверяет оказнчивается ли StringBuilder на count разметок startMk
    private boolean isEndMarkdown(StringBuilder s, int index, char startMk, int count) {
        if (count == 1) {
            return (s.charAt(index) == startMk) && ((s.length() - index) > 1 ? s.charAt(index) != s.charAt(index + 1) : true);
        }
        return (s.length() - index) >= 2 && s.charAt(index) == s.charAt(index + 1) && s.charAt(index) == startMk;
    }

    // строит внутреннюю часть линии
    private void md2html(StringBuilder line, StringBuilder part, int begin) {
        int l = begin, r = part.length() - 1, count = 1;
        if (r < l) return;
        if (isMarkdown(part.charAt(l))) {
            char startMk = part.charAt(l);
            if ((checkTwo(part, l) || (startMk == '-')) && (startMk != '`' || startMk != '%')) {
                count = 2;
            }
            if ((part.length() - begin) > count && !Character.isWhitespace(part.charAt(count + begin))) {
                int index = count + begin;
                while (index < part.length() && !isEndMarkdown(part, index, startMk, count)) {
                    index++;
                    if (index < part.length() && isMarkdown(part.charAt(index))
                        && (part.charAt(index) == part.charAt(index - 1))) index++;
                }
                if (index == part.length()) {
                    if (startMk == '-') count = 1;
                    line.append(String.valueOf(startMk).repeat(count));
                    md2html(line, part, count + begin);
                    return;
                }
                StringBuilder tmp = new StringBuilder();
                tmp.append(part, begin, index + count);
                repl(line, tmp);
                md2html(line, part, index + count);
                return;
            }
            line.append(String.valueOf(startMk).repeat(count));
            md2html(line, part, count + begin);
            return;
        }
        StringBuilder tempLeft = new StringBuilder();
        while (l < part.length() &&
               !isMarkdown(part.charAt(l))) {
            if (part.charAt(l) == '\\') l++;
            tempLeft.append(escapeHtml(part.charAt(l++)));
        }
        if (l == part.length()) {
            line.append(tempLeft);
            return;
        }
        line.append(tempLeft);
        md2html(line, part, l);
    }

    private StringBuilder buildLine() {
        int count = 0;
        if (start == read) {
            start = 0;
            readNextPart();
        }
        while (bufferReader[start] == '#') {
            count++;
            start++;
            if (start == read) {
                start = 0;
                readNextPart();
            }
        }
        if (count > 0 && !Character.isWhitespace(bufferReader[start])) {
            count = 0;
            start--;
        }
        if (count > 0) {
            line.append("<h").append(count).append(">");
            start++;
        } else {
            line.append("<p>");
        }
        StringBuilder temp = new StringBuilder();
        while (read != -1 && !isLineSeparator(temp, temp.length() - lengthSeparator * 2, 2)) {
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read != -1) {
                temp.append(bufferReader[start++]);
            }
        }
        deleteLastSeparators(temp);
        md2html(line, temp, 0);
        if (count > 0) {
            line.append("</h").append(count).append(">");
        } else {
            line.append("</p>");
        }
        return line;
    }

    private void buildHtml() {
        try {
            while (read != -1) {
                skipSeparators();
                StringBuilder s = buildLine();
                try {
                    writer.write(s + lineSeparator);
                } catch (IOException e) {
                    System.err.println("Ошибка при записи в файл: " + e.getMessage());
                }
                line.setLength(0);
            }
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла для чтения: " + e.getMessage());
            }
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла для записи: " + e.getMessage());
            }
        }
    }
}
