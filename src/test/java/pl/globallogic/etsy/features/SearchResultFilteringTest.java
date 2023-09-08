package pl.globallogic.etsy.features;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.BaseLandingPageTest;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchResultFilteringTest extends BaseLandingPageTest {

    @BeforeMethod
    public void testSetUp() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
    }

    //Filter search result by price -> price didn't is lower than price value for filtering
    @Test
    public void searchResultPriceFitPriceRangeAfterFiltering(){
        searchResultPage.setMinAndMaxPriceToFilter();
        Assert.assertTrue(searchResultPage.areItemsPriceBetweenMinAndMax());
    }


    //Filter search result by free shipping -> free shipping tag need to be present on all items
    @Test
    public void freeShippingTagNeedToBePresentAfterFiltering() {
        searchResultPage.selectFreeShipping();
        Assert.assertTrue(searchResultPage.areItemsFreeShippingFilteredCorrectly());
    }

    @Test
    public void searchResultPageContentNotChangedIfFilterApplicationCanceled() {

        //get 1st item in results title to be used in verification (after cancelling remain in-place)
        //expand filtering panel
        //cancel filter application
        //verify 1st item in result title
    }
    @Test
    public void filteredColorSelectionOptionShouldBeAvailableInItemDetailsView() {

        //select required filters ( by color )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify color selection element contains required color
    }
    @Test
    public void filteredShippingCountryShouldBePresentInShippingDestinationOptions() {

        //select required filters ( by shipping country )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify shipping destination selection element contains required country
    }
}
