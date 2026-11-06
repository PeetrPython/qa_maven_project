package utils;

import models.Customer;
import net.datafaker.Faker;

public class CustomerFactory {

    private static final Faker FAKER = new Faker();

    public static Customer.Builder customerBuilder() {
        return Customer.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .postalCode(FAKER.address().zipCode());
    }

    public static Customer randomCustomer() {
        return customerBuilder().build();
    }
}
