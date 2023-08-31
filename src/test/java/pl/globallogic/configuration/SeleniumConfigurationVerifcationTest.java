package pl.globallogic.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeleniumConfigurationVerifcationTest {

    @Test
    public void shouldNavigateToGoogleComAndVerifyPageTitle() {
        //arrange
        WebDriver driver = new ChromeDriver();
        String expectedPageTitle = "Google";
        //act
        driver.get("https://www.google.com");
        String actualPageTitle = driver.getTitle();
        driver.quit();
        //assert
        Assert.assertEquals(expectedPageTitle, actualPageTitle);
    }
}
