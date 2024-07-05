package io;

import java.io.*;
import java.util.function.Predicate;

public class FileParser {
    private final File file;

    public FileParser(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) {
                char tmp = (char) data;
                if (filter.test(tmp)) {
                    stringBuilder.append(tmp);
                }
            }
        }
        return stringBuilder.toString();
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(ch -> ch < 0x80);
    }
}
