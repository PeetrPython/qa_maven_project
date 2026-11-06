package utils;

import exceptions.TestDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;


public class CsvReader {

    public static <T> List<T> read(String path, Function<String[], T> mapper) {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            int lineNumber = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] row = line.split(",");
                    result.add(mapper.apply(row));
                } catch (RuntimeException e) {
                    throw new TestDataException(path + ", строка: " + lineNumber + ",  " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            throw new TestDataException("Не удалось прочитать файл " + path, e);
        }
        return result;
    }

    @Test
    void badNumberShouldThrowTestDataExceptionWithLineNumber() {
        TestDataException e = assertThrows(TestDataException.class,
                () -> CsvReader.read("src/test/resources/no-such-file.csv", row -> row[0]));
        Assertions.assertTrue(e.getMessage().contains("bad-number.csv"));
        Assertions.assertTrue(e.getMessage().contains("строка 3"));
        assertNotNull(e.getCause());
    }
}
