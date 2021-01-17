import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;
import static org.junit.gen5.api.Assertions.assertEquals;


public class OtusTest {

    protected static WebDriver driver;

    private static Logger logger = LogManager.getLogger(OtusTest.class);

    String login = "nrbttmuclrdiosyclb@niwghx.online";
    String pass = "zDwkLN7RW9@qGpn";
    String name = "Тестовый";
    String lastName = "Юзер";
    String birthday = "15.10.2003";
    String country = "Россия";
    String city = "Москва";
    String languageLevel = "Выше среднего (Upper Intermediate)";
    String contactType1 = "Skype";
    String contactValue1 = "hurma";
    String contactType2 = "VK";
    String contactValue2 = "https://vk.com/feed";

    MainPage mainPage;
    Header header;
    LoginPage loginPage;
    LearningPage learningPage;
    PersonalPage personalPage;


    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

    }

    @Epic(value = "OtusTest")
    @Story(value = "Заполнение личных данных на сайте Отуса")
    @Description(value = "Заполняем данные, сохраняем, закрываем браузер, открываем заново и проверяем данные")
    @Test
    public void test() {

        registration();
        enteringData();
        startNewBrowser();

        registration();


        assertEquals(name, personalPage.getNameText());
        assertEquals(lastName, personalPage.getLastNameText());
        assertEquals(birthday, personalPage.getDayText());
        assertEquals(country, personalPage.getCountryText());
        assertEquals(city, personalPage.getCityText());
        assertEquals(languageLevel, personalPage.getLangLvlText());
        assertEquals(contactType1.toLowerCase(), personalPage.getContactTypeText(0));
        assertEquals(contactType2.toLowerCase(), personalPage.getContactTypeText(1));
        assertEquals(contactValue1, personalPage.getContactValueText(0));
        assertEquals(contactValue2, personalPage.getContactValueText(1));
        logger.info("asserts is done");
        deletingContacts();

    }

    @AfterAll
    public static void close() {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }

    @Step("Открываем браузер заново")
    private void startNewBrowser() {
        driver.close();
        logger.info("close driver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        logger.info("new browser started");
    }
    @Step("Открываем форму авторизации")
    private void registration() {
        mainPage = new MainPage(driver);
        mainPage.open();
        logger.info("open page");

        header = new Header(driver);
        loginPage = header.authBtnClick();
        logger.info("open Login Page");

        loginPage.auth(login, pass);
        logger.info("auth is done");

        learningPage = header.openLearningPage();
        logger.info("open lk page");

        personalPage = learningPage.openPersonalPage();
        logger.info("open personal page");
    }

    @Step("Вводим данные")
    private void enteringData() {

        personalPage.enterName(name);
        personalPage.enterLastName(lastName);
        personalPage.enterDate(birthday);
        personalPage.enterCountry(country);
        personalPage.enterCity(city);
        personalPage.enterLanguageLevel(languageLevel);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(750,750)");
        personalPage.addContact(contactType2, contactValue2);
        personalPage.addContact(contactType1, contactValue1);
        personalPage.submit();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("entering data is done");
        Allure.addAttachment("EnteringData", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

    }

    @Step("Удаляем контакты")
    private void deletingContacts()
    {
        personalPage.deleteContacts(0);
        personalPage.deleteContacts(1);
        personalPage.submit();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("deleting is done");
    }
}

