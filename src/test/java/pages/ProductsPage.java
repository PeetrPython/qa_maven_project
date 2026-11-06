package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage extends BasePage {


    private final SelenideElement titleProducts = $("[data-test='title']");
    private final SelenideElement cartButton = $("[data-test='shopping-cart-link']");
    private final SelenideElement sortDropdown = $("[data-test='product-sort-container']");

    @Override
    protected String path() {
        return "/inventory.html";
    }

    @Step ("Проверка перехода на главную страницу {expectedText}")
    public ProductsPage shouldBeOpened(String expectedText) {
        log.info("Открываю {}", expectedText);
        titleProducts.shouldHave(text(expectedText));
        return this;
    }

    @Step ("Добавление товара в корзину {productName}")
    public ProductsPage addToCart(String productName) {
        log.info("Добавляю товар {}", productName);
        $$(".inventory_item").findBy(text(productName)).$("button").click();
        return this;
    }

    @Step ("Открытие корзины")
    public CartPage openCart() {
        cartButton.click();
        return new CartPage();
    }

    @Step ("Сортировка по цене")
    public ProductsPage sortByPrice() {
        sortDropdown.selectOption("Price (low to high)");
        return this;
    }

    @Step ("Проверка первого продукта после сортировки по цене {productName}")
    public ProductsPage shouldHaveFirstProduct(String productName) {
        $$(".inventory_item").first().shouldHave(text(productName));
        return this;
    }

}
