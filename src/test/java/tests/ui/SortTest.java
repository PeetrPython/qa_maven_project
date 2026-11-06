package tests.ui;

import org.junit.jupiter.api.Test;

public class SortTest extends BaseUiTest {

    @Test
    void sortByDescPrice() {
        loginPage.loginAs(config.get("user.login"), config.get("user.password"))
                .sortByPrice()
                .shouldHaveFirstProduct("Sauce Labs Onesie");
    }
}
