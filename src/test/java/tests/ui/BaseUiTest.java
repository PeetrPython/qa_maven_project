package tests.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginPage;
import utils.Config;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.cookies;



@ExtendWith({ScreenShooterExtension.class})

public class BaseUiTest {

    protected LoginPage loginPage;
    protected static Config config;

    @BeforeAll
    static void setupConfig() {
        config = Config.load();
        Configuration.timeout = 4000;
        Configuration.reportsFolder = "./build/screenshots";
        SelenideLogger.addListener(
                "AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(false));
    }

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage();
        loginPage.openPage(config.get("base.url"));
        localStorage().clear();
        cookies().clear();
        refresh();
    }

}
