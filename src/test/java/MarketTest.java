import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MarketTest {

    protected static WebDriver driver;

    private Logger logger = LogManager.getLogger(MarketTest.class);

    @Before
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("driver initialized");
        driver.get("https://market.yandex.ru/");
        logger.info("open page");

    }

    @Test
    public void compareTest() throws InterruptedException
    {
        By menuElectronic = By.xpath("//span[.='Электроника']");
        By menuSmartphones = By.xpath("//a[.='Смартфоны']");
        By filterSamsung = By.xpath("//span[.='Samsung']/ancestor::li//a");
        By filterXiaomi = By.xpath("//span[.='Xiaomi']/ancestor::li//a");
        By sortPrice = By.xpath("//button[.='по цене']");

        /*new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(ElementClickInterceptedException.class); */

        Thread.sleep(10000);
        driver.findElement(menuElectronic).click();
        elementClick(menuSmartphones);
        logger.info("open smartphones");
        addFilterAndSort(filterSamsung, filterXiaomi, sortPrice);
        logger.info("add filters and sort");

    }


    @After
    public void close()
    {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }

    private void addFilterAndSort(By filter, By filter2, By sort)
    {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(filter));
        elementClick(filter);
        elementClick(filter2);
        elementClick(sort);
    }

    private void elementClick(By element)
    {
        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(driver.findElement(element)));
        driver.findElement(element).click();
    }
}

