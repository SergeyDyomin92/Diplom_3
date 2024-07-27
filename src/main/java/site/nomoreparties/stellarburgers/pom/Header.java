package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;

public class Header {

    private final WebDriver driver;
    private final By headerRoot = By.xpath(".//header");
    private final String headerItemByText = ".//a[contains(@class,'AppHeader_header')][p[text()='%s']]";
    private final By logo = By.xpath(".//div[contains(@class,'AppHeader_header__logo')]//a");


    public Header(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Хэдер должен быть отображен")
    public Header headerShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(headerRoot)));
        return this;
    }

    @Step("Получить элемент хэдера по его названию")
    public By getHeaderItemByText(String text) {
        return By.xpath(format(headerItemByText, text));
    }

    @Step("Нажать на элемент хэдера")
    public Header clickHeaderItemWithText(String text) {
        driver.findElement(getHeaderItemByText(text)).click();
        return this;
    }

    @Step("Нажать на логотип")
    public Header clickHeaderLogo() {
        driver.findElement(logo).click();
        return this;
    }

}