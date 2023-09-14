package pl.globallogic.etsy.features.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ItemDetails {
    protected Logger logger = LoggerFactory.getLogger(ItemDetails.class);
    private final WebDriver driver;
    private static final String FIRST_DISPLAYED_ITEM = "//ol[@data-results-grid-container]//li";
    private static final String ITEM_DESCRIPTION = "//h1[@data-buy-box-listing-title]";
    private static final String ADD_TO_CARD_BUTTON = "//div[@data-add-to-cart-button]//button";

    public ItemDetails(WebDriver driver) {
        this.driver = driver;
    }
    public void selectFirstDisplayedProduct() {
        WebElement itemImage = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_DISPLAYED_ITEM)));
        itemImage.click();
        logger.info("Product is displayed correctly");
    }
    public boolean isDescriptionVisible() {
        logger.info("Wait for the page load");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ITEM_DESCRIPTION)));
        logger.info(descriptionElement.getText());
        return descriptionElement.isDisplayed();
    }

    public boolean isAddToCardButtonVisible() {
        logger.info("Wait for the page load");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ADD_TO_CARD_BUTTON)));
        logger.info(addToCardButton.getText());
        return addToCardButton.isDisplayed();
    }


}
