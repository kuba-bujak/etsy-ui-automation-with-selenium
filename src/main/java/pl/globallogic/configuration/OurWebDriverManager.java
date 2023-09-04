package pl.globallogic.configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OurWebDriverManager {
    public static WebDriver getConfiguredWebDriver() {
        String browserType = System.getProperty("browser", "chrome");
        return switch (browserType){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                System.out.println("Chrome driver configuration completed");
                yield new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                System.out.println("Firefox driver configuration completed");
                yield new FirefoxDriver();
            default:
                yield new ChromeDriver();
        };
    }
}
