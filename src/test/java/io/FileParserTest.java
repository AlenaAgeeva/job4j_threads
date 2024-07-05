package io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;

class FileParserTest {
    private File tempFile;
    private BufferedWriter writer;

    @BeforeEach
    public void createTempFile() throws IOException {
        tempFile = File.createTempFile("testFile", ".txt");
    }

    @AfterEach
    public void deleteTempFile() {
        tempFile.delete();
    }

    public void putTestText() throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
        writer.write("Hello World7!");
        writer.write("Привет мир!");
        writer.close();
    }

    @Test
    public void testGetContentWithoutUnicode() throws IOException {
        putTestText();
        FileParser fileParser = new FileParser(tempFile);
        String content = fileParser.getContentWithoutUnicode();
        String expected = "Hello World7! !";
        assertThat(expected).isEqualTo(content);
    }

    @Test
    public void testGetContentWithCustomFilter() throws IOException {
        putTestText();
        FileParser fileParser = new FileParser(tempFile);
        Predicate<Character> digitFilter = Character::isDigit;
        String content = fileParser.getContent(digitFilter);
        String expected = "7";
        assertThat(expected).isEqualTo(content);
    }
}