package tests.unit;

import exceptions.TestDataException;
import models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void nullFirstNameShouldThrowException() {
        TestDataException e = assertThrows(TestDataException.class, ()-> {
            Customer.builder().firstName(null).lastName("Ignatov").postalCode("121333").build();
        });
        assertTrue(e.getMessage().contains("firstName"));
        assertFalse(e.getMessage().contains("lastName"));
        assertFalse(e.getMessage().contains("postalCode"));
    }

    @Test
    void nullLastNameShouldThrowException() {
        TestDataException e = assertThrows(TestDataException.class, ()-> {
            Customer.builder().firstName("Petr").lastName(null).postalCode("121333").build();
        });
        assertTrue(e.getMessage().contains("lastName"));
        assertFalse(e.getMessage().contains("firstName"));
        assertFalse(e.getMessage().contains("postalCode"));
    }


    @Test
    void builderShouldSetEachField() {
        Customer customer = Customer.builder().firstName("Ksenya").lastName("UFC").postalCode("1984").build();

        assertEquals("Ksenya", customer.getFirstName());
        assertEquals("UFC", customer.getLastName());
        assertEquals("1984", customer.getPostalCode());
    }

    @Test
    void builderWithoutPostalCodeShouldThrowException() {
        TestDataException e = assertThrows(TestDataException.class, () -> Customer.builder().firstName("Kseenyua").lastName("UFC").postalCode(null).build());

        assertTrue(e.getMessage().contains("postalCode"));
        assertFalse(e.getMessage().contains("firstName"));
        assertFalse(e.getMessage().contains("lastName"));
    }

    @Test
    void builderShouldAllowEmptyStrings() {
        assertDoesNotThrow(() -> Customer.builder().firstName("").lastName("").postalCode("").build());
    }

}
