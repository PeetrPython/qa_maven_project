package utils;

import exceptions.TestDataException;
import models.Customer;
import models.Product;

import java.util.List;
import java.util.function.Function;

public class TestData {

    private static final Function<String[], Customer> CUSTOMER_MAPPER =
            row -> Customer.builder().firstName(row[0]).lastName(row[1]).postalCode(row[2]).build();

    public static List<Customer> customers() {
        return CsvReader.read("src/test/resources/customers.csv", CUSTOMER_MAPPER);
    }

    public static Customer firstCustomer() {
        return customers().stream()
                .findFirst()
                .orElseThrow(() -> new TestDataException("В customers.csv нет ни одного покупателя" ));
    }


    private static final Function<String[], Product> PRODUCT_MAPPER =
            row -> new Product(row[0], Double.parseDouble(row[1]));

    public static List<Product> products() {
        return CsvReader.read("src/test/resources/products.csv", PRODUCT_MAPPER);
    }

    public static Product productByName(String productName) {
        return products().stream()
                .filter(product -> product.getProductName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new TestDataException("В products.csv не нашёл товар с таким названием: " + productName));
    }

}
