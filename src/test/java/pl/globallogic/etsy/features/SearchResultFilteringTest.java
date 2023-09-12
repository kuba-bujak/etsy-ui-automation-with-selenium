package pl.globallogic.etsy.features;


import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.BaseLandingPageTest;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

import java.time.Duration;
import java.util.ArrayList;
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
        List<WebElement> itemsBeforeFiltering = searchResultPage.getSearchedProductList();
        ArrayList<String> itemsIdsBeforeFiltering = searchResultPage.getProductsId(itemsBeforeFiltering);
        searchResultPage.selectFreeShipping();
        searchResultPage.cancelSelectingFilters();
        List<WebElement> itemsAfterFiltering = searchResultPage.getSearchedProductList();
        ArrayList<String> itemsIdsAfterFiltering = searchResultPage.getProductsId(itemsAfterFiltering);
        Assert.assertTrue(searchResultPage.areItemListIsTheSameBeforeAndAfterCancelFiltering(itemsIdsBeforeFiltering, itemsIdsAfterFiltering));
    }
    @Test
    public void filteredColorSelectionOptionShouldBeAvailableInItemDetailsView() {
        searchResultPage.setColorFilters();
        Assert.assertTrue(searchResultPage.isColorFilteringSetCorrectly());
    }
    @Test
    public void filteredShippingCountryShouldBePresentInShippingDestinationOptions() {
        String countryDestination = searchResultPage.selectCountryShippingDestination();
        searchResultPage.setApplyFilterButton();
        Assert.assertTrue(searchResultPage.isCountryDestinationSetCorrectly(countryDestination));
    }
}
