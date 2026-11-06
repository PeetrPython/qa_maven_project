package tests.unit;

import exceptions.TestDataException;
import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp(){
        product = new Product("productName", 21.33);
    }

    @Test
    void getProductNameShouldReturnPassedValue() {
        assertEquals("productName", product.getProductName());
    }

    @Test
    void getProductPriceShouldReturnPassedValue() {
        assertEquals(21.33, product.getProductPrice());
    }

    @Test
    void nullProductNameShouldThrowException() {
        TestDataException e = assertThrows(TestDataException.class, () -> {
            new Product(null, 21.33);
        });
        assertTrue(e.getMessage().contains("productName"));
        assertFalse(e.getMessage().contains("productPrice"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 50.0, 100.0})
    void nonNegativeProductPriceShouldNotThrowException(double productPrice) {
        assertDoesNotThrow(() -> new Product("productName", productPrice));
    }


    @ParameterizedTest
    @ValueSource(doubles = {-0.01, -50.0, -100.0})
    void negativeProductPriceShouldThrowException(double productPrice) {
        TestDataException e = assertThrows(TestDataException.class, () -> {
            new Product("productName", productPrice);
        });
        assertFalse(e.getMessage().contains("productName"));
        assertTrue(e.getMessage().contains("productPrice"));
    }
}
