package pl.globallogic.etsy.features;


import org.testng.Assert;
import org.testng.annotations.Test;
import pl.globallogic.BaseLandingPageTest;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchResultFilteringTest extends BaseLandingPageTest {

    //Filter search result by price -> price didn't is lower than price value for filtering
    @Test
    public void searchResultPriceFitPriceRangeAfterFiltering(){
        String validQuery = "leather bag";
        double minPrice = generateRandomPrice(10, 50);
        double maxPrice = generateRandomPrice(minPrice, 100);
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
        searchResultPage.setMinAndMaxPriceToFilter(minPrice, maxPrice);
        Assert.assertTrue(searchResultPage.areItemsPriceBetweenMinAndMax(minPrice, maxPrice));
    }

    public int generateRandomPrice(double minPrice, double maxPrice) {if (minPrice >= maxPrice) {
        throw new IllegalArgumentException("Min price must be lower than max price");
    }

        Random random = new Random();
        return (int) (random.nextDouble(maxPrice - minPrice + 1) + minPrice);
    }

    //Filter search result by free shipping -> free shipping tag need to be present on all items
    @Test
    public void freeShippingTagNeedToBePresentAfterFiltering() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
        //select required filters ( by free shipping )
        //apply selected filters
        //verify free shipping tag/label is present on search result page
    }

    @Test
    public void searchResultPageContentNotChangedIfFilterApplicationCanceled() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
        //get 1st item in results title to be used in verification (after cancelling remain in-place)
        //expand filtering panel
        //cancel filter application
        //verify 1st item in result title
    }
    @Test
    public void filteredColorSelectionOptionShouldBeAvailableInItemDetailsView() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
        //select required filters ( by color )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify color selection element contains required color
    }
    @Test
    public void filteredShippingCountryShouldBePresentInShippingDestinationOptions() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
        searchResultPage.openFilterMenu();
        //select required filters ( by shipping country )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify shipping destination selection element contains required country
    }
}
