import java.io.*;
import java.nio.charset.StandardCharsets;

public class Scannerr {
    private Reader reader;
    private int read;
    private char[] buffer = new char[1024];
    private StringBuilder sb = new StringBuilder();
    private int start = 0;
    private int lenSep = System.lineSeparator().length();

    public Scannerr(String string) {
        this(new StringReader(string));
    }

    public Scannerr(InputStream in) {
        this(new InputStreamReader(in));
    }

    public Scannerr(File file) {
        try {
            this.reader = new FileReader(file, StandardCharsets.UTF_8);
            readNextPart();
        } catch (IOException e) {
            System.err.println("Произошла ошибка, файл не найден: " + e.getMessage());
        }
    }

    private Scannerr(Reader reader) {
        this.reader = reader;
        readNextPart();
    }

    private static boolean isLineSeparator(StringBuilder s, int index) {
        String lineSep = System.lineSeparator();
        if (s.length() < lineSep.length() || index < 0) {
            return false;
        }
        return lineSep.equals(s.substring(index));
    }

    private void readNextPart() {
        try {
            if (read != -1) {
                read = reader.read(buffer);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении reader'a: " + e.getMessage());
        }
    }

    private void skipSpace() {
        while (Character.isWhitespace(buffer[start])) {
            start++;
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read == -1) {
                break;
            }
        }
    }

    private boolean wsppMod(char c) {
        return (Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'');
    }

    public String wspp_next() {
        skipSpace();
        while (read != -1 && !Character.isWhitespace(buffer[start]) && wsppMod(buffer[start])) {
            sb.append(buffer[start++]);
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read == -1) {
                break;
            }
        }
        while (read != -1 && (Character.isWhitespace(buffer[start]) || !wsppMod(buffer[start]))) {
            sb.append(buffer[start++]);
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read == -1) {
                break;
            }
        }
        String answer = sb.toString();
        sb.delete(0, sb.length());
        return answer;
    }

    public String next() {
        skipSpace();
        while (read != -1 && !Character.isWhitespace(buffer[start])) {
            sb.append(buffer[start++]);
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read == -1) {
                break;
            }
        }
        String answer = sb.toString();
        sb.delete(0, sb.length());
        return answer;
    }

    public boolean hasNext() {
        return read != -1;
    }

    public String nextLine() {
        while (!(isLineSeparator(sb, sb.length() - lenSep))) {
            if (start == read) {
                start = 0;
                readNextPart();
            }
            if (read == -1) {
                break;
            }
            sb.append(buffer[start++]);
        }
        if (isLineSeparator(sb, sb.length() - lenSep)) {
            sb.setLength(sb.length() - lenSep);
        }
        String answer = sb.toString();
        sb.delete(0, sb.length());
        return answer;
    }

    public void close() throws IOException {
        reader.close();
    }
}
