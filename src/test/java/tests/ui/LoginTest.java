package tests.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseUiTest {

    @DisplayName("Открытие главной страницы")
    @Test
    void validCredentialsShouldOpenProductsPage() {
        loginPage.loginAs(config.get("user.login"), config.get("user.password"))
                .shouldBeOpened("Products");
        loginPage.shouldBeOpenedPath();
    }

    @DisplayName("Отображение сообщение о неуспешной авторизации")
    @Test
    void wrongPasswordShouldShowErrorMessage() {
        loginPage.loginAs(config.get("user.login"), "wrong_password");
        loginPage.shouldShowError("Epic sadface: Username and password do not match any user in this service");
    }
}
