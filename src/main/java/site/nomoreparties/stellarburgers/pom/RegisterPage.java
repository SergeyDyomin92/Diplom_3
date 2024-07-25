package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private final WebDriver driver;
    private final By registerRoot = By.xpath(".//div[contains(@class,'Auth')][h2['Регистрация']]");
    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By loginField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By enterLink = By.linkText("Войти");
    private final By invalidPasswordHint = By.xpath(".//input[@name='Пароль']/ancestor::div[@class='input__container']/p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Страница регистрации должна быть отображена")
    public RegisterPage registerPageShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(registerRoot)));
        return this;
    }

    @Step("На странице регистрации ввести значение в поле Имя")
    public RegisterPage setNameFieldValue(String name) {
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    @Step("На странице регистрации ввести значение в поле Логин")
    public RegisterPage setLoginFieldValue(String login) {
        driver.findElement(loginField).sendKeys(login);
        return this;
    }

    @Step("На странице регистрации ввести значение в поле Пароль")
    public RegisterPage setPasswordFieldValue(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("На странице регистрации нажать на кнопку Зарегистрироваться")
    public RegisterPage clickRegistrationButton() {
        driver.findElement(registerButton).click();
        return this;
    }

    @Step("На странице регистрации нажать на ссылку Войти")
    public RegisterPage clickEnterLink() {
        driver.findElement(enterLink).click();
        return this;
    }

    @Step("На странице регистрации подсказка под полем Пароль должна быть отображена")
    public RegisterPage invalidPasswordHintShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(invalidPasswordHint)));
        return this;
    }
}
