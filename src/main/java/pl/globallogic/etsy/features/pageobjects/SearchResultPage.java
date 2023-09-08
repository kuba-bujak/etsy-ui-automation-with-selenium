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
import java.util.Random;

public class SearchResultPage {
    protected Logger logger = LoggerFactory.getLogger(SearchResultPage.class);
    private final WebDriver driver;

    private static final String OPEN_FILTER_BUTTON = "//button[@id='search-filter-button']";
    private static final String INPUT_MIN_PRICE = "search-filter-min-price-input";
    private static final String INPUT_MAX_PRICE = "search-filter-max-price-input";
    private static final String SEARCHED_ITEMS = "//ol[@data-results-grid-container]";
    private static final String FREE_SHIPPING_CHECKBOX = "//label[@for='special-offers-free-shipping']";
    private static final String APPLY_FILTER_BUTTON = "//button[@aria-label='Zastosuj']";
    private static final String FILTERED_PRODUCTS_LIST = "//div[@data-search-results]//ol[@data-results-grid-container]";
    private static final int MIN_PRICE_VALUE = generateRandomPrice(10, 50);
    private static final int MAX_PRICE_VALUE = generateRandomPrice(MIN_PRICE_VALUE + 50, 150);

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

    public void setMinAndMaxPriceToFilter() {
        WebElement minPriceInput = driver.findElement(By.id(INPUT_MIN_PRICE));
        minPriceInput.sendKeys(MIN_PRICE_VALUE + "");
        WebElement maxPriceInput = driver.findElement(By.id(INPUT_MAX_PRICE));
        maxPriceInput.sendKeys(MAX_PRICE_VALUE + "" + Keys.ENTER);
        WebElement usedFiltersButtonVisibility = new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCHED_ITEMS)));
        logger.info("Price filter set correctly - searching between '{}' and '{}',", MIN_PRICE_VALUE, MAX_PRICE_VALUE);
    }

    public void selectFreeShipping() {
        WebElement freeShippingCheckbox = driver.findElement(By.xpath(FREE_SHIPPING_CHECKBOX));
        freeShippingCheckbox.click();
        logger.info("Free shipping correctly checked", MIN_PRICE_VALUE, MAX_PRICE_VALUE);
        WebElement applyFilterButton = driver.findElement(By.xpath(APPLY_FILTER_BUTTON));
        applyFilterButton.click();
        logger.info("Apllied filters", MIN_PRICE_VALUE, MAX_PRICE_VALUE);
    }

    public static int generateRandomPrice(int minPrice, int maxPrice) {
        if (minPrice >= maxPrice) {
        throw new IllegalArgumentException("Min price must be lower than max price");
        }
        Random random = new Random();
        return (int) (random.nextDouble(maxPrice - minPrice + 1) + minPrice);
    }

    public List<WebElement> getSearchedProductList() {
        WebElement filteredList = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(FILTERED_PRODUCTS_LIST))
        );
        List<WebElement> filteredItems = filteredList.findElements(By.tagName("li"));
        if (filteredItems.size() > 10) {
            filteredItems = filteredItems.subList(0, 10);
        }
        return filteredItems;
    }

    public boolean areItemsPriceBetweenMinAndMax() {
        List<WebElement> filteredItems = getSearchedProductList();
        for (WebElement item : filteredItems) {
            String currencyAmount = item.findElement(By.className("currency-value")).getText().replace(",", ".");
            double parsedAmount = Double.parseDouble(currencyAmount);
            if (parsedAmount > MAX_PRICE_VALUE || parsedAmount < MIN_PRICE_VALUE) {
                logger.info("Item number {} is out of range - item price is equal to '{}'", filteredItems.indexOf(item) + 1, currencyAmount);
                return false;
            }
            logger.info("Item number {} correct - item price is equal to '{}'", filteredItems.indexOf(item) + 1, currencyAmount);
        }
        return true;
    }

    public boolean areItemsFreeShippingFilteredCorrectly() {
        boolean hasFreeShipping = false;
        List<WebElement> filteredItems = getSearchedProductList();
        for (WebElement item : filteredItems) {
            String promotionBadgeLine = item.findElement(By.className("promotion-badge-line")).getText();
            if (promotionBadgeLine.contains("Bezpłatna wysyłka")) {
                logger.info("Item number {} has free shipping", filteredItems.indexOf(item) + 1);
                hasFreeShipping = true;
            } else {
                logger.info("Item number {} has no free shipping - shipping is {}", filteredItems.indexOf(item) + 1, promotionBadgeLine);
                hasFreeShipping = false;
                return hasFreeShipping;
            }
        }
        return hasFreeShipping;
    }


}
