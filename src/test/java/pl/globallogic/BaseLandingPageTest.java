package pl.globallogic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.globallogic.configuration.OurWebDriverManager;
import pl.globallogic.etsy.features.pageobjects.InvalidSearchResultPage;
import pl.globallogic.etsy.features.pageobjects.ItemDetails;
import pl.globallogic.etsy.features.pageobjects.LandingPage;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

public class BaseLandingPageTest {

    protected Logger logger = LoggerFactory.getLogger(BaseLandingPageTest.class);
    protected WebDriver driver;
    protected LandingPage landingPage;
    protected InvalidSearchResultPage invalidSearchResultPage;
    protected SearchResultPage searchResultPage;
    protected ItemDetails itemDetails;

    @BeforeMethod
    public void globalSetUp() {
        System.setProperty("browser", "chrome");
        driver = OurWebDriverManager.getConfiguredWebDriver();
        logger.info("Web driver instance have been configured");
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.acceptDefaultPrivacyPolicy();
    }
    @AfterClass
    public void globalCleanUp() {
        logger.info("Test suite execution completed");
    }

    @AfterMethod(alwaysRun = true)
    public void testCleanUp() {
        logger.info("Quitting Selenium webdriver");
        driver.quit();
    }
}
