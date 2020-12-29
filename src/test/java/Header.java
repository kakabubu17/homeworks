import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header extends  AbstractPage{

    WebDriverWait wait = new WebDriverWait(driver, 5);

    public Header(WebDriver driver) { super(driver); }

    String authBtn = "//div[@class='header2__right']//button";

    String menuBtn = "//div[contains(@class, 'username')]";
    String lkBtn = "//div[contains(@class, 'dropdown_right')]/a[contains(@title, 'Личный кабинет')]";

    public Header authBtnClick()
    {
        clickElement(authBtn);
        return this; //LoginPage????
    }

    public Header openLearningPage()
    {
        clickElement(menuBtn);
        clickElement(lkBtn);
        return this;
    }
}
