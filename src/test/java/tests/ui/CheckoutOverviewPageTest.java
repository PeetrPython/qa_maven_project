package tests.ui;

import models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import utils.CustomerFactory;
import utils.TestData;

import java.util.stream.Stream;

public class CheckoutOverviewPageTest extends BaseUiTest {

    private CheckoutOverviewPage overviewPage;
    private Product backpack;
    private Product bikeLight;

    @BeforeEach
    void openOverviewPage() {
        backpack = TestData.productByName("Sauce Labs Backpack");
        bikeLight = TestData.productByName("Sauce Labs Bike Light");
        overviewPage = loginPage
                .loginAs(config.get("user.login"), config.get("user.password"))
                .addToCart(backpack.getProductName())
                .addToCart(bikeLight.getProductName())
                .openCart()
                .clickCheckoutButton()
                .fillForm(CustomerFactory.randomCustomer())
                .clickContinue();
    }

    @Test
    @DisplayName("Заказ: сумма на странице равна сумме цен товаров")
    void itemTotalShouldEqualSumOfPrices() {
        double expected = Stream.of(backpack, bikeLight)
                .mapToDouble(Product::getProductPrice)
                .sum();
        overviewPage.shouldHaveItemTotal(expected);
    }

    @Test
    @DisplayName("Заказ отображается на странице оформления")
    void orderedProductShouldBeListedOnOverview() {
        overviewPage.shouldContainProduct(backpack.getProductName());
        overviewPage.shouldBeOpenedPath();
    }

    @Test
    @DisplayName("Заказ сделан успешно")
    void finishShouldShowOrderConfirmation() {
        CheckoutCompletePage completePage = overviewPage.clickFinish()
                .shouldShowConfirmation("Thank you for your order");
        completePage.shouldBeOpenedPath();
    }

}
