package pl.globallogic.etsy.features;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.BaseLandingPageTest;
import pl.globallogic.etsy.features.pageobjects.ItemDetails;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

public class ItemDetailsPageVerificationTest extends BaseLandingPageTest{


    @BeforeMethod
    public void testSetUp() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        itemDetails = new ItemDetails(driver);
        itemDetails.selectFirstDisplayedProduct();
    }

    @Test
    public void containsItemDescription() {
        Assert.assertTrue(itemDetails.isDescriptionVisible());
        logger.info("Description is displayed correctly");
    }

    @Test
    public void shouldHaveAddToCartButton() {
        Assert.assertTrue(itemDetails.isAddToCardButtonVisible());
        logger.info("Add to card button is displayed correctly");
    }
}
