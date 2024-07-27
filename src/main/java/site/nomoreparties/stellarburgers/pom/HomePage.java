package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;

public class HomePage {
    private final WebDriver driver;
    private final By homePageRoot = By.xpath(".//main");
    private final By burgerConstructorSection = By.xpath(".//section[contains(@class,'BurgerConstructor')]");
    private final String tab = ".//div[contains(@class,'tab_tab__1SPyG') and .='%s']";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Домашняя страница сайта должна быть отображена")
    public HomePage homePageShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(homePageRoot)));
        return this;
    }

    @Step("Секция Конструктора бургеров должна быть отображена")
    public HomePage burgerConstructorShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(burgerConstructorSection)));
        return this;
    }

    @Step("Получить элемент вкладки по её названию")
    public By getElementTabByText(String text) {
        return By.xpath(format(tab, text));
    }

    @Step("Перейти на вкладку с текстом")
    public HomePage goToTabWIthText(String text) {
        driver.findElement(getElementTabByText(text)).click();
        return this;
    }

    @Step("Вкладка с текстом должна быть выбрана")
    public HomePage tabWithTextShouldBeSelected(String text) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(getElementTabByText(text))));
        new WebDriverWait(driver, 3)
                .until(attributeContains(getElementTabByText(text), "class", "current"));
        return this;
    }

    @Step("Вкладка с текстом должна быть не выбрана")
    public HomePage tabWithTextShouldNotBeSelected(String text) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(getElementTabByText(text))));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.not(attributeContains(getElementTabByText(text), "class", "current")));
        return this;
    }

    @Step("На домашней странице получить элемент по его названию")
    public String getHomePageItemByText(String text) {
        String homePageButtonByText = ".//div[contains(@class,'BurgerConstructor_basket__container')]/button[text()='%s']";
        return format(homePageButtonByText, text);
    }

    @Step("На домашней странице кнопка с текстом должна быть отображена")
    public HomePage homePageButtonWithTextShouldBeVisible(String text) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(getHomePageItemByText(text)))));
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(getHomePageItemByText(text)))));
        return this;
    }

    @Step("На домашней странице нажать на кнопку с текстом")
    public HomePage clickHomePageButtonWithText(String text) {
        driver.findElement(By.xpath(getHomePageItemByText(text))).click();
        return this;
    }
}
