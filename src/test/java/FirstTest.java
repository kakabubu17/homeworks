import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;


public class FirstTest {

    protected static WebDriver driver;

    private Logger logger = LogManager.getLogger(FirstTest.class);

    @Before
    public void setUp()
    {
        String opt = "headless";
        opt = System.getProperty("option").trim().toLowerCase();
        driver = WebDriverFactory.create(System.getProperty("browser").trim().toLowerCase(), opt);
                //WebDriverFactory.create("chrome", opt);


        driver.manage().window().maximize();
        logger.info("driver initialized");

    }

    @Test
    public void isRightTitle()
    {
        driver.get("https://otus.ru");
        logger.info("open page");
        By heading = By.xpath("//h1");
        Assert.assertEquals("Авторские онлайн‑курсы для профессионалов",
                driver.findElement(heading).getText());
    }



    @After
    public void close()
    {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }
}

