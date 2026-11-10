package tests.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;

public class CartTest extends BaseUiTest {

    @Test
    @DisplayName("Добавленый товар отображается в корзине")
    void addedProductShouldAppearInCart() {
        CartPage cartPage = loginPage
                .loginAs(config.get("user.login"), config.get("user.password"))
                .addToCart("Sauce Labs Backpack")
                .openCart();

        cartPage.shouldBeOpenedPath();
        cartPage.shouldContainProduct("Sauce Labs Backpack");
    }
}
