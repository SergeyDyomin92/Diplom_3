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

import static site.nomoreparties.stellarburgers.driver.WebDriverCreator.createWebDriver;

public class ConstructorTest {

    private static final String URL = Url.URL;

    private WebDriver driver;
    String name;
    String email;
    String password;
    User user;
    UserAPI userAPI = new UserAPI();
    String token;
    Response response;

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
    @DisplayName("Конструктор. Переход из личного кабинета в конструктор")
    @Description("Переход из личного кабинета в конструктор")
    public void redirectFromPersonalAccountToConstructorByHeaderItem() {
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
        header
                .clickHeaderItemWithText("Конструктор");

        homePage
                .burgerConstructorShouldBeVisible();
    }

    @Test
    @DisplayName("Конструктор. Переход в конструктор по логотипу")
    @Description("Переход из личного кабинета в конструктор по логотипу")
    public void redirectFromPersonalAccountToConstructorByLogo() {
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
        header
                .clickHeaderLogo();

        homePage
                .burgerConstructorShouldBeVisible();
    }

    @Test
    @DisplayName("Конструктор. Переходы к разделам")
    @Description("Переходы к разделам")
    public void redirectToBunsBlock() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

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
                .goToTabWIthText("Начинки")
                .tabWithTextShouldBeSelected("Начинки")
                .tabWithTextShouldNotBeSelected("Булки")
                .tabWithTextShouldNotBeSelected("Соусы");
        homePage
                .goToTabWIthText("Булки")
                .tabWithTextShouldBeSelected("Булки")
                .tabWithTextShouldNotBeSelected("Начинки")
                .tabWithTextShouldNotBeSelected("Соусы");
        homePage
                .goToTabWIthText("Соусы")
                .tabWithTextShouldBeSelected("Соусы")
                .tabWithTextShouldNotBeSelected("Начинки")
                .tabWithTextShouldNotBeSelected("Булки");
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
