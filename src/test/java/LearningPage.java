import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LearningPage extends AbstractPage {

    public LearningPage(WebDriver driver) { super(driver); }

    String personalTab = "//div[@class='nav__items']/a[@title='О себе']";
    String editBtn = "//a[contains(@title,'Редактировать')]";


    public PersonalPage openPersonalPage()
    {
        List<WebElement> list = driver.findElements(By.xpath(personalTab));
        list.get(0).click();
        return new PersonalPage(driver);
    }

    public LearningPage editCV()
    {
        List<WebElement> listBtn = driver.findElements(By.xpath(editBtn));
        listBtn.get(0).click();
        return this;
    }
}
