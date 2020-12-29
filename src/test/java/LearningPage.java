import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LearningPage extends AbstractPage {

    public LearningPage(WebDriver driver) { super(driver); }

    String personalTab = "//div[@class='nav__items']/a[@title='О себе']";


    public LearningPage openPersonalPage()
    {
        List<WebElement> list = driver.findElements(By.xpath(personalTab));
        list.get(0).click();
        return this;
    }
}
