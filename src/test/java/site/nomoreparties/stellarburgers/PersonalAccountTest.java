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
import site.nomoreparties.stellarburgers.pom.Header;
import site.nomoreparties.stellarburgers.pom.HomePage;
import site.nomoreparties.stellarburgers.pom.LoginPage;
import site.nomoreparties.stellarburgers.pom.PersonalAccountPage;
import site.nomoreparties.stellarburgers.utils.Utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static site.nomoreparties.stellarburgers.driver.WebDriverCreator.createWebDriver;

public class PersonalAccountTest {
    private static final String URL = Url.URL;

    private WebDriver driver;
    String name;
    String email;
    String password;
    User user;
    String token;
    Response response;
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
    @DisplayName("Личный кабинет. Переход в личный кабинет и проверка личных данных пользователя")
    @Description("Переход в личный кабинет и проверка личных данных пользователя")
    public void openPersonalAccountAndCheckUserData() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        Header header = new Header(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();
        header
                .clickHeaderItemWithText("Личный Кабинет");
        personalAccountPage
                .personalAccountPageShouldBeVisible();

        assertEquals(user.getName(), personalAccountPage.getFieldValue("Имя"));
        assertEquals(user.getEmail(), personalAccountPage.getFieldValue("Логин"));
        assertEquals("*****", personalAccountPage.getFieldValue("Пароль"));
    }

    @Test
    @DisplayName("Личный кабинет. Выход из аккаунта в личном кабинете")
    @Description("Выход из личного кабинета")
    public void exitFromPersonalAccount() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        Header header = new Header(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        homePage
                .homePageShouldBeVisible()
                .homePageButtonWithTextShouldBeVisible("Войти в аккаунт")
                .clickHomePageButtonWithText("Войти в аккаунт");
        loginPage
                .loginPageShouldBeVisible()
                .fillLoginData(email, password)
                .clickEnterButton();
        header
                .clickHeaderItemWithText("Личный Кабинет");
        personalAccountPage
                .personalAccountPageShouldBeVisible()
                .clickExitButton();

        loginPage
                .loginPageShouldBeVisible();
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
