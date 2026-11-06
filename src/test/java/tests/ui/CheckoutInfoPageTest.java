package tests.ui;

import models.Customer;
import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.CartPage;
import pages.CheckoutInfoPage;
import utils.TestData;

public class CheckoutInfoPageTest extends BaseUiTest {

    private CartPage cartPage;
    private Product backpack;
    private Product bikeLight;


    @BeforeEach
    void loginAddProduct() {
        backpack = TestData.productByName("Sauce Labs Backpack");
        bikeLight = TestData.productByName("Sauce Labs Bike Light");
        cartPage = loginPage.loginAs(config.get("user.login"), config.get("user.password"))
                .addToCart(backpack.getProductName())
                .openCart()
                .shouldContainProduct(backpack.getProductName());
    }


    @DisplayName("Появление ошибки на форме оформления, если пустое имя, фамилия или индек")
    @ParameterizedTest(name = "{index}: {3}")
    @CsvSource({
            "'Petr', 'Ignatov', '', 'Postal Code is required'",
            "'Petr',  '',  '12345', 'Last Name is required'",
            "'', 'Ignatov', '12345', 'First Name is required'"
    })
    void emptyFieldShouldShowRequiredError(String firstName, String lastName, String postalCode, String expectedError) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .postalCode(postalCode)
                .build();
        CheckoutInfoPage infoPage = cartPage.clickCheckoutButton();
        infoPage.shouldBeOpenedPath();
        infoPage.fillForm(customer);
        infoPage.clickContinue();
        infoPage.shouldShowErrorMessage(expectedError);
    }
}
