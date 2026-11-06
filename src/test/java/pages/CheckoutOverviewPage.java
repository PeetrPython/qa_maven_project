package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutOverviewPage extends BasePage {

    private final SelenideElement finalButton = $("#finish");
    private final SelenideElement subtotalLabel = $("[data-test='subtotal-label']");


    @Override
    protected String path() {
        return "/checkout-step-two.html";
    }

    @Step ("Отображение добавленного товара в корзину {productName}")
    public CheckoutOverviewPage shouldContainProduct(String productName) {
        log.info("Проверка добавленного товара: {}", productName);
        $$(".inventory_item_name").findBy(text(productName)).shouldBe(visible);
        return this;
    }

    @Step ("Клик на кнопку Финиш")
    public CheckoutCompletePage clickFinish() {
        log.info("Нажимаю Finish");
        finalButton.click();
        return new CheckoutCompletePage();
    }

    @Step ("Проверка суммы заказа {expected}")
    public CheckoutOverviewPage shouldHaveItemTotal(double expected) {
        log.info("Проверяю сумму корзины: {} ", expected);
        String text = subtotalLabel.getText();
        double actual = Double.parseDouble(text.replace("Item total: $", ""));
        assertEquals(expected, actual, 0.001);
        return this;
    }


}
