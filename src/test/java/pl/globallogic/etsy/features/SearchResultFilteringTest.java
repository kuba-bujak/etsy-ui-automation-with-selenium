package pl.globallogic.etsy.features;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.globallogic.BaseLangingPageTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchResultFilteringTest extends BaseLangingPageTest {

    private static final String OPEN_FILTER_BUTTON = "//button[@id='search-filter-button']";
    private static final String FILTER_MIN_PRICE = "search-filter-min-price-input";
    private static final String FILTER_MAX_PRICE = "search-filter-max-price-input";
    private static final String SEARCHED_ITEMS = "//ol[@data-results-grid-container]";

    private static final String FILTERED_ITEMS = "wt-list-unstyled";

    //Filter search result by price -> price didn't is lower than price value for filtering
    @Test
    public void searchResultPriceFitPriceRangeAfterFiltering() {
        String validQuery = "leather bag";
        double minPrice = generateRandomPrice(10, 50);
        double maxPrice = generateRandomPrice(minPrice, 100);
        landingPage.searchFor(validQuery);
        Assert.assertTrue(landingPage.isSearchResultValidFor(validQuery));
        WebElement openFilters = new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(OPEN_FILTER_BUTTON))
        );
        openFilters.click();
        WebElement minPriceInput = driver.findElement(By.id(FILTER_MIN_PRICE));
        minPriceInput.sendKeys(minPrice + "");
        WebElement maxPriceInput = driver.findElement(By.id(FILTER_MAX_PRICE));
        maxPriceInput.sendKeys(maxPrice + "" + Keys.ENTER);
        WebElement usedFiltersButtonVisibility = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCHED_ITEMS)));
        Assert.assertTrue(usedFiltersButtonVisibility.isDisplayed());
        WebElement filteredList = driver.findElement(By.xpath("//div[@data-search-results]//ol[@data-results-grid-container]"));
        List<WebElement> filteredItems = filteredList.findElements(By.tagName("li"));
        if (filteredItems.size() > 10) {
            filteredItems = filteredItems.subList(0, 10);
        }
        for (WebElement item : filteredItems) {
            String currencyAmount = item.findElement(By.className("currency-value")).getText().replace(",", ".");
            double parsedAmount = Double.parseDouble(currencyAmount);
            if (parsedAmount > maxPrice || parsedAmount < minPrice) {
                System.out.println("Tak nie powinno być");
                Assert.assertTrue(false);
            }
        }
        Assert.assertTrue(true);
    }

    private int generateRandomPrice(double minPrice, double maxPrice) {
        if (minPrice >= maxPrice) {
            throw new IllegalArgumentException("Min price must be lower than max price");
        }

        Random random = new Random();
        return (int) (random.nextDouble(maxPrice - minPrice + 1) + minPrice);
    }

    //Filter search result by free shipping -> free shipping tag need to be present on all items
    @Test
    public void freeShippingTagNeedToBePresentAfterFiltering() {
        //go to the landing page
        //search for item
        //wait for search result to be loaded
        //expand filtering panel
        //select required filters ( by free shipping )
        //apply selected filters
        //verify free shipping tag/label is present on search result page
    }

    @Test
    public void searchResultPageContentNotChangedIfFilterApplicationCanceled() {
        //go to the landing page
        //search for item
        //wait for search result to be loaded
        //get 1st item in results title to be used in verification (after cancelling remain in-place)
        //expand filtering panel
        //cancel filter application
        //verify 1st item in result title
    }
    @Test
    public void filteredColorSelectionOptionShouldBeAvailableInItemDetailsView() {
        //go to the landing page
        //search for item
        //wait for search result to be loaded
        //expand filtering panel
        //select required filters ( by color )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify color selection element contains required color
    }
    @Test
    public void filteredShippingCountryShouldBePresentInShippingDestinationOptions() {
        //go to the landing page
        //search for item
        //wait for search result to be loaded
        //expand filtering panel
        //select required filters ( by shipping country )
        //apply selected filters
        //wait for search result to be filtered
        //go to 1st result item details view
        //verify shipping destination selection element contains required country
    }
}
