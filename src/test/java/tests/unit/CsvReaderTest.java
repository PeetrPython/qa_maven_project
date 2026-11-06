package tests.unit;

import exceptions.TestDataException;
import models.Customer;
import models.Product;
import utils.CsvReader;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderTest {

    @Test
    void readProductsShouldReturnAllRowsWithoutHeader() throws IOException {
        List<Product> productList = CsvReader.read("src/test/resources/products.csv",
                row -> new Product(row[0], Double.parseDouble(row[1])));
        assertEquals(2, productList.size());
        assertEquals("Sauce Labs Backpack", productList.get(0).getProductName());
        assertEquals("Sauce Labs Bike Light", productList.get(1).getProductName());
        assertEquals(29.99, productList.get(0).getProductPrice());
        assertEquals(9.99, productList.get(1).getProductPrice());
    }

    @Test
    void readCustomersShouldReturnAllRowsWithoutHeader() throws IOException {
        List<Customer> customerList = CsvReader.read("src/test/resources/customers.csv",
                row -> Customer.builder().firstName(row[0]).lastName(row[1]).postalCode(row[2]).build());
        assertEquals(1, customerList.size());
        assertEquals("Petr", customerList.get(0).getFirstName());
        assertEquals("Ignatov", customerList.get(0).getLastName());
        assertEquals("12345", customerList.get(0).getPostalCode());
    }

    @Test
    void missingFileShouldThrowIOException() {
        String missingFile = "src/test/resources/no-such.csv";
        TestDataException e = assertThrows(TestDataException.class,
                () -> CsvReader.read(missingFile, row -> row[0]));
        assertTrue(e.getMessage().contains(missingFile));
        assertInstanceOf(IOException.class, e.getCause());
    }

}
