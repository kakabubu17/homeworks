import com.oracle.tools.packager.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    public LoginPage(WebDriver driver) { super(driver); }

    public LoginPage auth(String login, String password)
    {
        By loginTb = By.xpath("//div[contains(@class, 'new-input-line_relative')][2]/input[@placeholder='Электронная почта']");
        driver.findElement(loginTb).sendKeys(login);
        By passTb = By.cssSelector(".js-psw-input");
        driver.findElement(passTb).sendKeys(password);
        By submitBtn = By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)");
        driver.findElement(submitBtn).submit();
        return this;
    }
}
