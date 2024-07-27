package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import site.nomoreparties.stellarburgers.helper.Url;
import site.nomoreparties.stellarburgers.helper.User;
import site.nomoreparties.stellarburgers.helper.UserAPI;
import site.nomoreparties.stellarburgers.pom.*;
import site.nomoreparties.stellarburgers.utils.Utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static site.nomoreparties.stellarburgers.driver.WebDriverCreator.createWebDriver;

public class LoginTest {


    private static final String URL = Url.URL;

    private WebDriver driver;
    protected String name;
    private String email;
    private String password;
    protected User user;
    private String token;
    protected Response response;

    UserAPI userAPI = new UserAPI();
    Utils utils = new Utils();

    @Before
    public void setUp() {
        driver = createWebDriver();
        driver.get(URL);

        //Заполнение тестовых данных пользователя:
        name = utils.name;
        email = utils.email;
        password = utils.password;

        //Регистрация пользователя через API:
        user = new User().withName(this.name).withEmail(this.email).withPassword(this.password);
        response = userAPI.sendPostAPIAuthRegister(user);
        token = userAPI.getTokenFromResponse(response);
    }

    @Test
    @DisplayName("Вход. Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Вход по кнопке «Войти в аккаунт» на главной")
    public void successLoginByHomePageButton() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Оформить заказ");
    }

    @Test
    @DisplayName("Вход. Вход по кнопке «Личный кабинет» в хэдере")
    @Description("Вход по кнопке «Личный кабинет» в хэдере")
    public void successLoginByPersonalAccountButton() {
        HomePage homePage = new HomePage(driver);
        Header header = new Header(driver);
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible();
        header
                .headerShouldBeVisible()
                .clickHeaderItemWithText("Личный Кабинет");
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Оформить заказ");
    }

    @Test
    @DisplayName("Вход. Вход по кнопке в форме регистрации")
    @Description("Вход по кнопке в форме регистрации")
    public void successLoginByRegistrationForm() {
        HomePage homePage = new HomePage(driver);
        Header header = new Header(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible();
        header
                .headerShouldBeVisible()
                .clickHeaderItemWithText("Личный Кабинет");

        loginPage
                .clickRegistrationLink();
        registerPage
                .registerPageShouldBeVisible()
                .clickEnterLink();
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Оформить заказ");
    }

    @Test
    @DisplayName("Вход. Вход по кнопке в форме восстановления пароля")
    @Description("Вход по кнопке в форме восстановления пароля")
    public void successLoginByPasswordRecoveryForm() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RecoveryPasswordPage recoveryPasswordPage = new RecoveryPasswordPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .clickRecoveryPasswordLink();

        recoveryPasswordPage
                .recoveryPasswordPageShouldBeVisible()
                .clickEnterLink();
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Оформить заказ");
    }

    @After
    public void tearDown() {
        if (!Objects.equals(this.token, "")) {
            userAPI.sendDeleteAPIAuthUserByToken(token);
            System.out.println("Тестовый пользователь удален");
        }
        driver.quit();
    }
}
