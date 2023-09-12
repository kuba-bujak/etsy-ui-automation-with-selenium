package pl.globallogic.etsy.features;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.globallogic.BaseLandingPageTest;
import pl.globallogic.etsy.features.pageobjects.SearchResultPage;

import java.util.List;

public class SearchResultsSortingTest extends BaseLandingPageTest {

    @BeforeMethod
    public void testSetUp() {
        String validQuery = "leather bag";
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        searchResultPage = new SearchResultPage(driver);
    }

    @Test
    public void pricesShouldBeOrderedAccordingToSortingCriteria() {
        searchResultPage.selectSortingOrderByTheLowestPrice();
        List<WebElement> sortedProducts = searchResultPage.getSearchedProductList();
        Assert.assertTrue(searchResultPage.areProductsSortByTheLowestPriceCorrectly(sortedProducts));
    }
}
