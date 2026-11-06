package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.Customer;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutInfoPage extends BasePage {


    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");
    private final SelenideElement errorMessage = $("[data-test='error']");


    @Override
    protected String path() {
        return "/checkout-step-one.html";
    }

    @Step ("Заполнение формы заказа {Customer}")
    public CheckoutInfoPage fillForm(Customer customer) {
        log.info("Заполняю форму: Имя = {}, Фамилия = {}, Индекс = {}", customer.getFirstName(), customer.getLastName(), customer.getPostalCode());
        firstNameInput.setValue(customer.getFirstName());
        lastNameInput.setValue(customer.getLastName());
        postalCodeInput.setValue(customer.getPostalCode());
        return this;
    }

    @Step ("Клик по кнопке Продолжить")
    public CheckoutOverviewPage clickContinue() {
        log.info("Нажимаю Continue");
        continueButton.click();
        return new CheckoutOverviewPage();
    }

    @Step ("Появление сообщение с ошибкой о не заполненной форме {expectedText}")
    public CheckoutInfoPage shouldShowErrorMessage(String expectedText) {
        log.info("Проверяю текст ошибки: {}", expectedText);
        errorMessage.shouldHave(text(expectedText));
        return this;
    }
}
