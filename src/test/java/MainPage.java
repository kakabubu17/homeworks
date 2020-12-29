import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    public MainPage(WebDriver driver) { super(driver); }
    private String url = "https://otus.ru";

    public MainPage open()
    {
        driver.get(url);
        return this;
    }
}
