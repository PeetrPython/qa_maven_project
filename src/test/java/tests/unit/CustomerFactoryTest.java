package tests.unit;

import models.Customer;
import org.junit.jupiter.api.Test;
import utils.CustomerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerFactoryTest {

    @Test
    void randomCustomerShouldHaveAllFieldsFilled() {
        Customer customer = CustomerFactory.randomCustomer();

        assertFalse(customer.getFirstName().isEmpty());
        assertFalse(customer.getLastName().isEmpty());
        assertFalse(customer.getPostalCode().isEmpty());
    }

    @Test
    void overriddenPostalCodeShouldBeEmptyWhileOtherFieldsStayFilled() {
        Customer customer = CustomerFactory.customerBuilder().postalCode("").build();

        assertFalse(customer.getFirstName().isEmpty());
        assertFalse(customer.getLastName().isEmpty());
        assertTrue(customer.getPostalCode().isEmpty());
    }
}
