package pages;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;


public abstract class BasePage {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected abstract String path();

    @Step ("Проверяется открытие страницы")
    public void shouldBeOpenedPath() {
        log.info("Проверяю, что открыта страница: {}", path());
        webdriver().shouldHave(urlContaining(path()));
    }
}
