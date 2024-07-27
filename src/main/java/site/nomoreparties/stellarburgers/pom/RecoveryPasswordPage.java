package site.nomoreparties.stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница "Восстановление пароля"
 */
public class RecoveryPasswordPage {
    private final WebDriver driver;
    private final By recoveryPasswordRoot = By.xpath(".//div[contains(@class,'Auth')][h2['Восстановление пароля']]");
    private final By enterLink = By.linkText("Войти");

    public RecoveryPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Страница восстановления пароля должна быть отображена")
    public RecoveryPasswordPage recoveryPasswordPageShouldBeVisible() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(driver.findElement(recoveryPasswordRoot)));
        return this;
    }

    @Step("На странице восстановления пароля нажать на ссылку Войти")
    public RecoveryPasswordPage clickEnterLink() {
        driver.findElement(enterLink).click();
        return this;
    }
}

