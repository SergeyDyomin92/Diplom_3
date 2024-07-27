package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.helper.Url;
import site.nomoreparties.stellarburgers.helper.User;
import site.nomoreparties.stellarburgers.helper.UserAPI;
import site.nomoreparties.stellarburgers.pom.HomePage;
import site.nomoreparties.stellarburgers.pom.LoginPage;
import site.nomoreparties.stellarburgers.pom.RegisterPage;
import site.nomoreparties.stellarburgers.utils.Utils;

import java.util.concurrent.TimeUnit;

import static site.nomoreparties.stellarburgers.driver.WebDriverCreator.createWebDriver;

public class RegistrationTest {

    private static final String URL = Url.URL;

    private WebDriver driver;
    private String name;
    private String email;
    private String password;
    protected User user;
    protected String token;
    protected UserAPI userAPI = new UserAPI();
    protected Utils utils = new Utils();

    @Before
    public void setUp() {
        driver = createWebDriver();
        driver.get(URL);
        name = utils.name;
        email = utils.email;
        password = utils.password;
    }

    @Test
    @DisplayName("Регистрация. Успешная регистрация пользователя")
    @Description("Успешная регистрация пользователя")
    public void successRegistrationTest() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .clickRegistrationLink();
        registerPage
                .registerPageShouldBeVisible()
                .setNameFieldValue(name)
                .setLoginFieldValue(email)
                .setPasswordFieldValue(password)
                .clickRegistrationButton();
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();
        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Оформить заказ");
    }

    @Test
    @DisplayName("Регистрация. Регистрация пользователя с невалидным паролем")
    @Description("Регистрация пользователя с невалидным паролем")
    public void registrationWithInvalidPasswordTest() {
        this.password = "12345";
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .clickRegistrationLink();
        registerPage
                .registerPageShouldBeVisible()
                .setNameFieldValue(name)
                .setLoginFieldValue(email)
                .setPasswordFieldValue(password)
                .clickRegistrationButton()
                .invalidPasswordHintShouldBeVisible();
    }

    @After
    public void tearDown() {
        if (this.password.length() > 6) {
            user = new User().withName(this.name).withEmail(this.email).withPassword(this.password);
            token = userAPI.getTokenFromResponse(userAPI.sendPostRequestAPIAuthLogin(user));
            userAPI.sendDeleteAPIAuthUserByToken(token);
            System.out.println("Тестовый пользователь удален");
        }
        driver.quit();
    }
}
