package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.String.format;

public class PersonalAccountPage {

    private final WebDriver driver;
    private final By personalAccountPageRoot = By.xpath(".//div[contains(@class,'Account_account')]");
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final String fieldXPath = ".//label[text()='%s']/following-sibling::input";


    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Страница профиля должна быть отображена")
    public PersonalAccountPage personalAccountPageShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(personalAccountPageRoot)));
        return this;
    }

    @Step("Получить элемент по его названию")
    public By getItemByText(String text) {
        return By.xpath(format(fieldXPath, text));
    }

    @Step("Получить значение поля")
    public String getFieldValue(String fieldName) {
        return driver.findElement(getItemByText(fieldName)).getAttribute("value");
    }

    @Step("Выйти из личного кабинета")
    public PersonalAccountPage clickExitButton() {
        driver.findElement(exitButton).click();
        return this;
    }

}
