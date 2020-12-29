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

    @Before
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


    }

    @Test
    public void test()
    {

        registration();
        enteringData();
        startNewBrowser();

        registration();


        Assert.assertEquals(name, driver.findElement(By.xpath(PersonalPage.nameField)).getAttribute("value"));
        Assert.assertEquals(lastName, driver.findElement(By.xpath(PersonalPage.lastNameTb)).getAttribute("value"));
        Assert.assertEquals(birthday, driver.findElement(By.xpath(PersonalPage.dateTb)).getAttribute("value"));
        //Assert.assertEquals(country, driver.findElement(By.xpath("//p[contains(text(),'Страна')]/../following-sibling::div//label/div")).getText());
        Assert.assertEquals(country, driver.findElement(By.xpath(PersonalPage.countryTb)).getText());
        //Assert.assertEquals(city, driver.findElement(By.xpath("//p[contains(text(),'Город')]/../following-sibling::div//label/div")).getText());
        Assert.assertEquals(city, driver.findElement(By.xpath(PersonalPage.cityTb)).getText());
        Assert.assertEquals(languageLevel, driver.findElement(By.xpath(PersonalPage.langTb)).getText());
        //Assert.assertEquals(languageLevel, driver.findElement(By.xpath("//p[contains(text(),'Уровень')]/../following-sibling::div/div/label/div")).getText().trim());
    //TODO: сделать удаление контактов после проверки!

    }

    @After
    public void close()
    {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }


    private void startNewBrowser()
    {
        driver.quit();
        logger.info("close driver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        logger.info("new browser started");
    }

    private void registration()
    {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        logger.info("open page");

        Header header = new Header(driver);
        header.authBtnClick();
        logger.info("open Login Page");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.auth("nrbttmuclrdiosyclb@niwghx.online", "zDwkLN7RW9@qGpn");
        logger.info("auth is done");

        header.openLearningPage();
        logger.info("open lk page");

        LearningPage learningPage = new LearningPage(driver);
        learningPage.openPersonalPage();
        logger.info("open personal page");
    }

    private void enteringData()
    {
        PersonalPage personalPage = new PersonalPage(driver);
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
        new WebDriverWait(driver, 5).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        logger.info("entering data is done");
    }
}

