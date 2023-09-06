package pl.globallogic.etsy.features.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class InvalidSearchResultPage {

    protected Logger logger = LoggerFactory.getLogger(InvalidSearchResultPage.class);
    private static final String INVALID_MESSAGE_HEADER = "//p[contains(@class, 'wt-text-heading-02')]";
    private final WebDriver driver;

    public InvalidSearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isVisible() {
        logger.info("Verifying invalid search results page is visible");
        WebElement invalidResultMessageHeading = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath(INVALID_MESSAGE_HEADER)));
        return invalidResultMessageHeading.isDisplayed();
    }

}
