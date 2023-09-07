package pl.globallogic.etsy.features.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class SearchResultPage {
    protected Logger logger = LoggerFactory.getLogger(SearchResultPage.class);
    private final WebDriver driver;

    private static final String OPEN_FILTER_BUTTON = "//button[@id='search-filter-button']";
    private static final String FILTER_MIN_PRICE = "search-filter-min-price-input";
    private static final String FILTER_MAX_PRICE = "search-filter-max-price-input";
    private static final String SEARCHED_ITEMS = "//ol[@data-results-grid-container]";

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openFilterMenu() {
        WebElement openFilters = new WebDriverWait(driver, Duration.ofSeconds(3)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(OPEN_FILTER_BUTTON))
        );
        openFilters.click();
        logger.info("Filter menu opened correctly");
    }

    public void setMinAndMaxPriceToFilter(double minPrice, double maxPrice) {
        WebElement minPriceInput = driver.findElement(By.id(FILTER_MIN_PRICE));
        minPriceInput.sendKeys(minPrice + "");
        WebElement maxPriceInput = driver.findElement(By.id(FILTER_MAX_PRICE));
        maxPriceInput.sendKeys(maxPrice + "" + Keys.ENTER);
        WebElement usedFiltersButtonVisibility = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCHED_ITEMS)));
        logger.info("Price filter set correctly");
    }

    public boolean areItemsPriceBetweenMinAndMax(double minPrice, double maxPrice) {
        WebElement filteredList = driver.findElement(By.xpath("//div[@data-search-results]//ol[@data-results-grid-container]"));
        List<WebElement> filteredItems = filteredList.findElements(By.tagName("li"));
        if (filteredItems.size() > 10) {
            filteredItems = filteredItems.subList(0, 10);
        }
        for (WebElement item : filteredItems) {
            String currencyAmount = item.findElement(By.className("currency-value")).getText().replace(",", ".");
            double parsedAmount = Double.parseDouble(currencyAmount);
            if (parsedAmount > maxPrice || parsedAmount < minPrice) {
                logger.warn("Item price out of price range");
                return false;
            }
        }
        return true;
    }


}
