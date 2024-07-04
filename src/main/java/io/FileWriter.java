package io;

import java.io.*;

public class FileWriter {
    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write(content);
        }
    }
}
