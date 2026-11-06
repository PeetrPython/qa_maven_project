package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage {

    private final SelenideElement inputLog = $("#user-name");
    private final SelenideElement inputPassword = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessageAutorization = $("[data-test='error']");


    @Override
    protected String path() {
        return "/inventory.html";
    }

    @Step ("Открытие страницы авторизации {url}")
    public LoginPage openPage(String url) {
        open(url);
        return this;
    }

    public ProductsPage loginAs(String login, String password) {
        log.info("Ввод логина и пароля: {}", login);
        return Allure.step("Вхожу под своим пользователем", () -> {
            inputLog.setValue(login);
            inputPassword.setValue(password);
            loginButton.click();
            return new ProductsPage();
        });
    }


    @Step("Проверяю текст ошибки: {expectedText}")
    public LoginPage shouldShowError(String expectedText) {
        log.info("Проверяю текст ошибки: {}", expectedText);
        errorMessageAutorization.shouldHave(text(expectedText));
        return this;
    }

}
