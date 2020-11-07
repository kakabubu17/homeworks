import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverFactory {

    WebDriver driver;

    public static WebDriver create(Browsers name, MutableCapabilities options)
    {
        switch (name)
        {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
            case IE:
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver(options);
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver(options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(options);

            default: WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
        }

    }

    public static WebDriver create(Browsers name)
    {
        switch (name)
        {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case IE:
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();

            default: WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }
}
