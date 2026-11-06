package learning;

import models.Customer;
import models.Product;
import utils.CsvReader;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LambdaPracticeTest {

//    @Test
//    void shouldCheapCheapProducts() {
//        assertFalse(result.);
//
//
//    }


    @Test
    void parsePriceShouldRemoveDollarSign() {
        Function<String, Double> parsePrice = s -> Double.parseDouble(s.replace("$", ""));
        assertEquals(29.99, parsePrice.apply("$29.99"), 0.001);
        assertEquals(9.99, parsePrice.apply("$9.99"), 0.001);
    }

//    @Test
//    void predicatesShouldCombineCheapAndBackpackConditions() {
//        Predicate<Product> cheap = product -> product.getProductPrice() < 20;
//        Predicate<Product> isBackpack = product -> product.getProductName().contains("Backpack");
//        Predicate<Product> cheapBackpack = cheap.and(isBackpack);
//        Predicate<Product> cheapOrBackpack = cheap.or(isBackpack);
//        Predicate<Product> expensive = cheap.negate();
//
//        assertFalse(cheapBackpack.test(new Product("Sauce Labs Backpack", 29.99)));
//        assertFalse(cheapBackpack.test(new Product("Sauce Labs Bike Light", 9.99)));
//        assertTrue(cheapBackpack.test(new Product("Test Backpack", 5.00)));
//        assertTrue(cheapOrBackpack.test(new Product("Sauce Labs Bike Light", 9.99)));
//        assertTrue(cheapOrBackpack.test(new Product("Test Backpack", 5.00)));
//        assertTrue(cheapOrBackpack.test(new Product("Sauce Labs Backpack", 29.99)));
//        assertFalse(expensive.test(new Product("Test Backpack", 5.00)));
//        assertFalse(expensive.test(new Product("Sauce Labs Bike Light", 9.99)));
//        assertTrue(expensive.test(new Product("Sauce Labs Backpack", 29.99)));
//    }

    private final Function<String[], Product> productMapper = row -> new Product(row[0], Double.parseDouble(row[1]));
    private final Function<String[], Customer> customerMapper = row -> Customer.builder().firstName(row[0]).lastName(row[0]).postalCode(row[0]).build();


    @Test
    void readProductsShouldReturnAllRowsWithoutHeader() {
        List<Product> product = CsvReader.read("src/test/resources/products.csv", productMapper);
        List<Customer> customer = CsvReader.read("src/test/resources/customers.csv", customerMapper);
    }


    @Test
    void filterShouldKeepOnlyProductsCheaperThan20(){
        List<Product> products = CsvReader.read("src/test/resources/products.csv", productMapper);
        List<Product> cheapProducts = products.stream()
                .filter(product -> product.getProductPrice() < 20)
                .toList();

        assertEquals(1, cheapProducts.size());
        assertEquals("Sauce Labs Bike Light", cheapProducts.get(0).getProductName());
    }


    @Test
    void allProductsFromFileShouldHavePositivePrice() {
        List<Product> products = CsvReader.read("src/test/resources/products.csv", productMapper);
        for (Product product : products) {
            assertTrue(product.getProductPrice() > 0);
        }
    }
}
