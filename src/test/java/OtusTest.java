import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


public class OtusTest {

    protected static WebDriver driver;

    private Logger logger = LogManager.getLogger(OtusTest.class);

    String name = "Тестовый";
    String lastName = "Юзер";
    String birthday = "15.10.2003";
    String country = "Россия";
    String city = "Москва";
    String languageLevel = "Выше среднего (Upper Intermediate)";
    String contactType1 = "telegram";
    String contactValue1 = "@hurma";
    String contactType2 = "vk";
    String contactValue2 = "https://vk.com/feed";

    MainPage mainPage;
    Header header;
    LoginPage loginPage;
    LearningPage learningPage;
    PersonalPage personalPage;


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

    }

    @Test
    public void test() {

        registration();
        enteringData();
        startNewBrowser();

        registration();


        Assert.assertEquals(name, personalPage.getNameText());
        Assert.assertEquals(lastName, personalPage.getLastNameText());
        Assert.assertEquals(birthday, personalPage.getDayText());
        Assert.assertEquals(country, personalPage.getCountryText());
        Assert.assertEquals(city, personalPage.getCityText());
        Assert.assertEquals(languageLevel, personalPage.getLangLvlText());
        Assert.assertEquals(contactType1, personalPage.getContactTypeText(0));
        Assert.assertEquals(contactType2, personalPage.getContactTypeText(1));
        Assert.assertEquals(contactValue1, personalPage.getContactValueText(0));
        Assert.assertEquals(contactValue2, personalPage.getContactValueText(1));
        logger.info("asserts is done");
        deletingContacts();

    }

    @After
    public void close() {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }


    private void startNewBrowser() {
        driver.close();
        logger.info("close driver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        logger.info("new browser started");
    }

    private void registration() {
        mainPage = new MainPage(driver);
        mainPage.open();
        logger.info("open page");

        header = new Header(driver);
        loginPage = header.authBtnClick();
        logger.info("open Login Page");

        loginPage.auth("nrbttmuclrdiosyclb@niwghx.online", "zDwkLN7RW9@qGpn");
        logger.info("auth is done");

        learningPage = header.openLearningPage();
        logger.info("open lk page");

        personalPage = learningPage.openPersonalPage();
        logger.info("open personal page");
    }

    private void enteringData() {

        personalPage.enterName(name);
        personalPage.enterLastName(lastName);
        personalPage.enterDate(birthday);
        personalPage.enterCountry(country);
        personalPage.enterCity(city);
        personalPage.enterLanguageLevel(languageLevel);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(750,750)");
        personalPage.addContact("VK", "https://vk.com/feed");
        personalPage.addContact("Тelegram", "@hurma");
        personalPage.submit();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("entering data is done");
    }

    private void deletingContacts()
    {
        personalPage.deleteContacts(0);
        personalPage.deleteContacts(1);
        personalPage.submit();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("deleting is done");
    }
}

