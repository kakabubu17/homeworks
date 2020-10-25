import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;


public class FirstTest {

    protected static WebDriver driver;

    private Logger logger = LogManager.getLogger(FirstTest.class);


    @Before
    public void setUp()
    {
        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = WebDriverFactory.create(BrowserNames.CHROME, options);
        */
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.get("https://otus.ru");
        logger.info("open page");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


    }

    @Ignore
    public void isRightTitle()
    {

        By heading = By.xpath("//h1");
        Assert.assertEquals("Авторские онлайн‑курсы для профессионалов",
                driver.findElement(heading).getText());
    }

    @Ignore
    public void cookieTest()
    {
        driver.manage().addCookie(new Cookie("Otus1", "value1"));
        driver.manage().addCookie(new Cookie("Otus2", "value2"));

        Cookie cook = new Cookie("Otus3", "value3");
        driver.manage().addCookie(cook);

        driver.manage().addCookie(new Cookie("Otus4", "value4"));
        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie i: allCookies) {

            System.out.println(i);
        }

        System.out.println(driver.manage().getCookieNamed("Otus1"));
        driver.manage().deleteCookieNamed("Otus2");
        driver.manage().deleteCookie(cook);
        driver.manage().deleteAllCookies();
        Assert.assertTrue(driver.manage().getCookies().isEmpty());
    }

    @Test
    public void test5()
    {
        auth("nrbttmuclrdiosyclb@niwghx.online", "zDwkLN7RW9@qGpn");
        logger.info("auth is done");

        By menuBtn = By.xpath("//div[contains(@class, 'username')]");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(menuBtn));
        driver.findElement(menuBtn).click();
        By lkBtn = By.xpath("//div[contains(@class, 'dropdown_right')]/a[contains(@title, 'Личный кабинет')]");
        driver.findElement(lkBtn).click();
        logger.info("open personal page");

        By personalBt = By.cssSelector("div.body-wrapper:nth-child(2) div.body.drawer.body_not-subscribed.drawer--right div.nav.nav_mobile-fix.no-print.js-overflow-scroll:nth-child(6) div.nav__scroll.js-overflow-scroll div.container.container-overflow-auto div.nav__items > a.nav__item:nth-child(3)");
        driver.findElement(personalBt).click();
        enterData("Тестовый", "Юзер", "15.10.2003");
        logger.info("entering data is done");

        startNewBrowser();
        logger.info("new browser started");
        auth("nrbttmuclrdiosyclb@niwghx.online", "zDwkLN7RW9@qGpn");
        logger.info("auth done");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(menuBtn));
        driver.findElement(menuBtn).click();
        driver.findElement(lkBtn).click();
        logger.info("open personal page");
        driver.findElement(personalBt).click();


        Assert.assertEquals("Тестовый", driver.findElement(By.xpath("//input[@name='fname']")).getAttribute("value"));
        Assert.assertEquals("Юзер", driver.findElement(By.xpath("//input[@name='lname']")).getAttribute("value"));
        Assert.assertEquals("15.10.2003", driver.findElement(By.xpath("//input[@name='date_of_birth']")).getAttribute("value"));
        Assert.assertEquals("Россия", driver.findElement(By.xpath("//p[contains(text(),'Страна')]/../following-sibling::div//label/div")).getText());
        Assert.assertEquals("Москва", driver.findElement(By.xpath("//p[contains(text(),'Город')]/../following-sibling::div//label/div")).getText());
        Assert.assertEquals("Выше среднего (Upper Intermediate)", driver.findElement(By.xpath("//p[contains(text(),'Уровень')]/../following-sibling::div/div/label/div")).getText().trim());

        //window.open()открывает новую вкладку
    }


    @Ignore
    public void testBootstrap() throws InterruptedException
    {
        By btn = By.xpath("//button[contains(text(),'Change message')]");
        driver.findElement(btn).click();
        logger.info("btn pressed");
        By message = By.xpath("//ngb-alert[contains(text(), 'Message successfully changed')]");
        new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(driver.findElement(message)));
        String mess = driver.findElement(message).getText();
        Thread.sleep(1500);
        logger.info("waiting is done");
        driver.findElement(btn).click();
        String mess1 = driver.findElement(message).getText();
        Assert.assertNotEquals(mess, mess1);

        //один метод который возвращает вебЭлемент с ожиданием




    }

    @After
    public void close()
    {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }

    private void auth(String login, String password)
    {
        By authBtn = By.cssSelector(".header2__auth-reg");
        driver.findElement(authBtn).click();
        By loginTb = By.xpath("//div[contains(@class, 'new-input-line_relative')][2]/input[@placeholder='Электронная почта']");
        driver.findElement(loginTb).sendKeys(login);
        By passTb = By.cssSelector(".js-psw-input");
        driver.findElement(passTb).sendKeys(password);
        By submitBtn = By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)");
        driver.findElement(submitBtn).submit();

    }

    private void enterData(String name, String lastName, String date)
    {
        By nameTb = By.xpath("//input[@name='fname']");
        By lastNameTb = By.xpath("//input[@name='lname']");
        By dateTb = By.xpath("//input[@name='date_of_birth']");
        By countryTb = By.xpath("//p[contains(text(),'Страна')]/../following-sibling::div/div");
        By countryName = By.xpath("//button[@title='Россия']");
        By cityTb = By.xpath("//p[contains(text(),'Город')]/../following-sibling::div/div");
        By langLvl = By.xpath("//p[contains(text(),'Уровень')]/../following-sibling::div/div");
        By btnSubmit = By.xpath("//button[@title='Сохранить и продолжить']");
        driver.findElement(nameTb).clear();
        driver.findElement(nameTb).sendKeys(name);
        driver.findElement(lastNameTb).clear();
        driver.findElement(lastNameTb).sendKeys(lastName);
        driver.findElement(dateTb).clear();
        driver.findElement(dateTb).sendKeys(date + "\n");
        driver.findElement(countryTb).click();
        driver.findElement(countryName).click();
        driver.findElement(cityTb).click();
        new WebDriverWait(driver, 7).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@title='Москва']"))));
        driver.findElement(By.xpath("//button[@title='Москва']")).click();
        driver.findElement(langLvl).click();
        driver.findElement(By.xpath("//div[contains(@class, 'scroll  js-custom-select-options')]/button[contains(@title, 'Upper')]")).click();
        driver.findElement(btnSubmit).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));

    }

    private void startNewBrowser()
    {
        driver.quit();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.get("https://otus.ru");
        logger.info("open page");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }
}

enum BrowserNames
{
    CHROME,
    FIREFOX,
    OPERA,
    YANDEX
}
