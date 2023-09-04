package pl.globallogic.etsy.features;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.BaseLangingPageTest;

public class ItemDetailsPageVerificationTest extends BaseLangingPageTest {

    //LandingPage
    //SearchResultsPage
    //ItemDetailsPAge

    @BeforeMethod
    public void testSetUp() {
        //go to landing page
        //search for item
        //wait for search result to be loaded
        //go to 1st result item details view
    }

    @Test
    public void containsItemImage() {
        //verify item image element presence/visibility
    }

    public void containsItemDescription() {
        //verify item description text is not empty
    }

    @Test
    public void shouldHaveAddToCartButton() {
        //verify add to cart button is present/enabled
    }
}
