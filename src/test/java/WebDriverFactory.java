import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class WebDriverFactory {

    public static WebDriver create(String browser, String option) {
        if (option != null) {
            switch (browser) {
                case "opera":
                    WebDriverManager.operadriver().setup();
                    OperaOptions optOpr = new OperaOptions();
                    optOpr.addArguments(option);
                    return new OperaDriver(optOpr);
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions optFF = new FirefoxOptions();
                    optFF.addArguments(option);
                    return new FirefoxDriver(optFF);
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions optChr = new ChromeOptions();
                    optChr.addArguments(option);
                    return new ChromeDriver(optChr);
                default:
                    System.out.println("browser is not found");
                    return null;
            }
        } else {
            switch (browser) {
                case "opera":
                    WebDriverManager.operadriver().setup();
                    return new OperaDriver();
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                default:
                    System.out.println("browser is not found");
                    return null;
            }
        }
    }
}
