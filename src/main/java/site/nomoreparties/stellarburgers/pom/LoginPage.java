package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final By loginPageRoot = By.xpath(".//div[contains(@class,'Auth')][h2['Вход']]");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By enterButton = By.xpath(".//button[text()='Войти']");
    private final By registrationLink = By.linkText("Зарегистрироваться");
    private final By recoveryPasswordLink = By.linkText("Восстановить пароль");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Страница логина должна быть отображена")
    public LoginPage loginPageShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(loginPageRoot)));
        return this;
    }

    @Step("На странице логина заполнить имейл и пароль")
    public LoginPage fillLoginData(String email, String password) {
        //обработка доступности и кликабельности поля имейл, заполнение поля имейл:
        try {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(emailField)));
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(emailField)));
            driver.findElement(emailField).sendKeys(email);
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(emailField)));
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(emailField)));
            driver.findElement(emailField).sendKeys(email);
        }
        //обработка доступности и кликабельности поля пароль, заполнение поля пароль:
        try {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(passwordField)));
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(passwordField)));
            driver.findElement(passwordField).sendKeys(password);
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOf(driver.findElement(passwordField)));
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(passwordField)));
            driver.findElement(passwordField).sendKeys(password);
        }
        return this;
    }

    @Step("На странице логина нажать на кнопку Войти")
    public LoginPage clickEnterButton() {
        driver.findElement(enterButton).click();
        return this;
    }

    @Step("На странице логина нажать на ссылку Зарегистрироваться")
    public LoginPage clickRegistrationLink() {
        driver.findElement(registrationLink).click();
        return this;
    }

    @Step("На странице логина нажать на ссылку Востановить пароль")
    public LoginPage clickRecoveryPasswordLink() {
        driver.findElement(recoveryPasswordLink).click();
        return this;
    }
}
