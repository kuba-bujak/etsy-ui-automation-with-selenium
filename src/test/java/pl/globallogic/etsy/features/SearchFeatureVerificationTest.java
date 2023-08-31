package pl.globallogic.etsy.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pl.globallogic.etsy.features.pageobjects.InvalidSearchResultPage;
import pl.globallogic.etsy.features.pageobjects.LandingPage;
import pl.globallogic.utils.InvalidQueryGenerator;

public class SearchFeatureVerificationTest {

    WebDriver driver;
    LandingPage landingPage;
    InvalidSearchResultPage invalidSearchResultPage;

    @BeforeMethod
    public void globalSetUp() {
        driver = new ChromeDriver();
        landingPage = new LandingPage(driver);
    }

    @Test
    public void shouldDisplaySearchResultsForValidQuery() {
        String validQuery = "leather bag";
        landingPage.goTo();
        landingPage.acceptDefaultPrivacyPolicy();
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
    }

    @Test
    public void shouldDisplayNotFoundPageForInvalidQuery() {
        String queryForInvalidSearchResultPage = InvalidQueryGenerator.getRandomInvalidQuery();
        landingPage.goTo();
        landingPage.acceptDefaultPrivacyPolicy();
        landingPage.searchFor(queryForInvalidSearchResultPage);
        invalidSearchResultPage = new InvalidSearchResultPage(driver);
        Assert.assertTrue(invalidSearchResultPage.isVisible());
    }

    @AfterClass
    public void globalCleanUp() {
        System.out.println("***************************** Test suite execution completed");
    }

    @AfterMethod(alwaysRun = true)
    public void testCleanUp() {
        System.out.println("Selenium webdriver quit");
        driver.quit();
    }
}
