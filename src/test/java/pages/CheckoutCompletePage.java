package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutCompletePage extends BasePage {

    private final SelenideElement confirmationHeader = $("[data-test='complete-header']");

    @Override
    protected String path() {
        return "/checkout-complete.html";
    }

    @Step ("Проверка перехоа на страницу успещного заказа")
    public CheckoutCompletePage shouldShowConfirmation(String expectedText) {
        log.info("Проверяю подтверждение: {}", expectedText);
        confirmationHeader.shouldHave(text(expectedText));
        return this;
    }

}
