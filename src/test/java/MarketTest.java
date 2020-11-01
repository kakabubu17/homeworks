import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
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
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        logger.info("driver initialized");
        driver.get("https://market.yandex.ru/");
        logger.info("open page");

    }

    @Test
    public void compareTest() throws InterruptedException
    {
        By menuElectronic = By.xpath("//span[.='Электроника']");
        By menuSmartphones = By.xpath("//a[.='Смартфоны']");
        By filterSamsung = By.xpath("//span[.='Samsung']/..");
                //By.xpath("//span[.='Samsung']/ancestor::label/input");
        By filterXiaomi = By.xpath("//span[.='Xiaomi']/..");
        By sortPrice = By.xpath("//button[.='по цене']");

        /*new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.MILLISECONDS)
                .ignoring(ElementClickInterceptedException.class); */

        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf
                (driver.findElement(By.xpath("//*[contains(text(), 'Напишите, какой товар вам нужен')]"))));
        driver.findElement(menuElectronic).click();
        elementClick(menuSmartphones);
        logger.info("open smartphones");
        addFilterAndSort(filterSamsung, filterXiaomi, sortPrice);
        logger.info("add filters and sort");
        List<WebElement> phones =  driver.findElements(By.xpath(""));
        //два листа с самсунгами и сяоми и взять первый элемент из каждого
        for (WebElement element:phones)
        {
            boolean sams = element.getText().startsWith("Смартфон Samsung");

            if (sams = true) element.click(); //добавляем первый самсунг

        }
        //не забыть удалить телефоны из сравнения в конце
    }


    @After
    public void close()
    {
        if (driver != null) driver.quit();
        logger.info("close driver");
    }

    private void addFilterAndSort(By filter, By filter2, By sort) throws InterruptedException
    {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(filter));
        driver.findElement(filter).click();
        driver.findElement(filter2).click();
        elementClick(sort);
        Thread.sleep(10000);
    }

    private void elementClick(By element)
    {
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(driver.findElement(element)));
        driver.findElement(element).click();
    }
}

