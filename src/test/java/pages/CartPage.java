package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CartPage extends BasePage {

    private final SelenideElement backpackAddCart = $("[data-test='inventory-item-name']");
    private final SelenideElement checkoutButton = $("#checkout");

    @Override
    protected String path() {
        return "/cart.html";
    }

    @Step ("Отображение продуктов")
    public CartPage shouldContainProduct(String expectedText) {
        backpackAddCart.shouldHave(text(expectedText));
        return this;
    }

    @Step ("Кликаем дальше")
    public CheckoutInfoPage clickCheckoutButton() {
        checkoutButton.click();
        return new CheckoutInfoPage();
    }

}
