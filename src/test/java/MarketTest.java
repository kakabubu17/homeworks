import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        By filterXiaomi = By.xpath("//span[.='Xiaomi']/..");
        By sortPrice = By.xpath("//button[.='по цене']");

        //ждем плашку-подсказку
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*[contains(text(), 'Напишите, какой товар вам нужен')]"))));
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf
                (driver.findElement(By.xpath("//*[contains(text(), 'Напишите, какой товар вам нужен')]"))));
        //заходим в смартфоны
        elementClick(menuElectronic);
        elementClick(menuSmartphones);
        logger.info("open smartphones");

        //добавляем фильтры и сортировку
        addFilterAndSort(filterSamsung, filterXiaomi, sortPrice);
        logger.info("add filters and sort");
        //new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath("//article[1]//h3/a")));

        List<WebElement> samsungPhones =  driver.findElements(By.xpath("//article//h3/a[contains(@title,'Samsung')]/ancestor::article/div/div[contains(@aria-label,'сравнению')]"));
        List<WebElement> xiaomiPhones =  driver.findElements(By.xpath("//article//h3/a[contains(@title,'Xiaomi')]/ancestor::article/div/div[contains(@aria-label,'сравнению')]"));

        //добавляем первый Samsung и Xiaomi
        addFirstElem(samsungPhones);
        logger.info("add first samsung");
        addFirstElem(xiaomiPhones);
        logger.info("add first xiaomi");

        //переходим к сравнению
        driver.findElement(By.xpath("//div[contains(text(), 'добавлен к сравнению')]/../following-sibling::div/a")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("https://market.yandex.ru/compare/"));

        //проверяем, что элемента два
        List<WebElement> comparisonPhones =  driver.findElements(By.xpath("//a[contains(text(), 'Смартфон')]/.."));
        Assert.assertEquals(comparisonPhones.size(), 2);

        //очищаем список сравнения
        driver.findElement(By.xpath("//button[text()='Удалить список']")).click();
        logger.info("list is empty");
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
        Thread.sleep(5000);
    }

    private void elementClick(By element)
    {
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(driver.findElement(element)));
        driver.findElement(element).click();
    }

    private void addFirstElem(List<WebElement> list)
    {
        list.get(0).click();
        By plashka = By.xpath("//div[contains(text(), 'добавлен к сравнению')]");
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(driver.findElement(plashka)));
        Assert.assertTrue(driver.findElement(plashka).isDisplayed());
    }
}

