package tests.unit;

import exceptions.TestDataException;
import models.Customer;
import models.Product;
import org.junit.jupiter.api.Test;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataTest {

    @Test
    void firstCustomerShouldHaveDataFromFile() {
        Customer customer = TestData.firstCustomer();
        assertEquals("Petr", customer.getFirstName());
        assertEquals("Ignatov", customer.getLastName());
        assertEquals("12345", customer.getPostalCode());
    }

    @Test
    void productByNameShouldReturnProductWithPrice() {
        Product product = TestData.productByName("Sauce Labs Bike Light");
        assertEquals("Sauce Labs Bike Light", product.getProductName());
        assertEquals(9.99, product.getProductPrice(), 0.001);
    }

    @Test
    void productsShouldReturnAllRowsFromFile() {
        assertEquals(2, TestData.products().size());
    }

    @Test
    void productByNameShouldThrowForMissingProduct() {
        String missingName = "Nonexistent Product";

        TestDataException e = assertThrows(TestDataException.class,
                () -> TestData.productByName(missingName));
        assertTrue(e.getMessage().contains(missingName));
    }
}
