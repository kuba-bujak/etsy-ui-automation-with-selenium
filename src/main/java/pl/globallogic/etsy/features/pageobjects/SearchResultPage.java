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
import java.util.*;

import static org.apache.commons.lang3.ThreadUtils.sleep;

public class SearchResultPage {
    protected Logger logger = LoggerFactory.getLogger(SearchResultPage.class);
    private final WebDriver driver;

    private static final String OPEN_FILTER_BUTTON = "//button[@id='search-filter-button']";
    private static final String INPUT_MIN_PRICE = "search-filter-min-price-input";
    private static final String INPUT_MAX_PRICE = "search-filter-max-price-input";
    private static final String SEARCHED_ITEMS = "//ol[@data-results-grid-container]";
    private static final String FREE_SHIPPING_CHECKBOX = "//label[@for='special-offers-free-shipping']";
    private static final String GREEN_COLOR_FILTER_CHECKBOX = "//label[@for='attr_1-4']";
    private static final String APPLY_FILTER_BUTTON = "//div[contains(@class, 'wt-bt-xs')]//button[2][contains(@class, 'wt-btn wt-btn--primary')]";
    private static final String CANCEL_FILTER_BUTTON = "search-filters-cancel-button";
    private static final String FILTERED_PRODUCTS_LIST = "//div[@data-search-results]//ol[@data-results-grid-container]";
    private static final int MIN_PRICE_VALUE = generateRandomPrice(10, 50);
    private static final int MAX_PRICE_VALUE = generateRandomPrice(MIN_PRICE_VALUE + 50, 150);
    private static final String COLOR_FILTERING_BUTTON = "//ul//li[@data-active-filter-tag]//a";
    private static final String COUNTRY_SHIPPING_DESTINATION = "//select[@name='ship_to']//optgroup";
    private static final String ITEMS_SORTING_THE_LOWEST_PRICE = "//div[@id='sortby']";

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
        setApplyFilterButton();
    }

    public void setApplyFilterButton() {
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
        logger.info("Correctly get list of elements");
        return filteredItems;
    }

    public boolean areItemsPriceBetweenMinAndMax() {
        List<WebElement> filteredItems = getSearchedProductList();
        for (WebElement item : filteredItems) {
            double parsedAmount = getProductAmount(item);
            logger.info(String.valueOf(parsedAmount));
            if (parsedAmount > MAX_PRICE_VALUE || parsedAmount < MIN_PRICE_VALUE) {
                logger.info("Item number {} is out of range - item price is equal to '{}'", filteredItems.indexOf(item) + 1, parsedAmount);
                return false;
            }
            logger.info("Item number {} correct - item price is equal to '{}'", filteredItems.indexOf(item) + 1, parsedAmount);
        }
        return true;
    }

    private double getProductAmount(WebElement item) {
        String currencyAmount = item.findElement(By.className("currency-value")).getText().replace(",", ".");
        return Double.parseDouble(currencyAmount);
    }

    public boolean areItemsFreeShippingFilteredCorrectly() {
        boolean hasFreeShipping = false;
        List<WebElement> filteredItems = getSearchedProductList();
        for (WebElement item : filteredItems) {
            WebElement promotionBadgeLine = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("promotion-badge-line"))
            );
            String promotionText = promotionBadgeLine.getText();
            if (promotionText.contains("Bezpłatna wysyłka") || promotionText.contains("Free shipping")) {
                logger.info("Item number {} has free shipping", filteredItems.indexOf(item) + 1);
                hasFreeShipping = true;
            } else {
                logger.info("Item number {} has no free shipping - shipping is {}", filteredItems.indexOf(item) + 1, promotionText);
                hasFreeShipping = false;
                return hasFreeShipping;
            }
        }
        return hasFreeShipping;
    }

    public void cancelSelectingFilters() {
        WebElement cancelButton = driver.findElement(By.id(CANCEL_FILTER_BUTTON));
        cancelButton.click();
        logger.info("Canceled selecting filters");
    }

    public boolean areItemListIsTheSameBeforeAndAfterCancelFiltering(ArrayList<String> itemListBeforeId, ArrayList<String> itemListAfterId) {
        if (itemListAfterId.size() != itemListBeforeId.size()) {
            logger.info("Product list before cancellation has not the same size as after cancellation");
            return false;
        }
        for (int i = 0; i < itemListAfterId.size(); i++) {
            if (itemListBeforeId.get(i).equals(itemListAfterId.get(i))) {
                logger.info("Product before cancellation is the same as after cancellation");
                return true;
            }
        }
        logger.info("Product list before cancellation is not the same as after cancellation");
        return false;
    }

    public ArrayList<String> getProductsId(List<WebElement> productList) {
        ArrayList<String> productListId = new ArrayList<>();
        for (WebElement productItem : productList) {
            List<WebElement> productIdContainer = productItem.findElements(By.xpath("//div[@data-logger-id]")).subList(0, 10);
            for (WebElement product : productIdContainer) {
                String productId = product.getAttribute("data-logger-id");
                logger.info(productId);
                productListId.add(productId);
            }
        }
        return productListId;
    }

    public void setColorFilters() {
        WebElement colorButton = driver.findElement(By.xpath(GREEN_COLOR_FILTER_CHECKBOX));
        colorButton.click();
        logger.info("Canceled selecting filters");
        setApplyFilterButton();
    }

    public boolean isColorFilteringSetCorrectly() {
        String colorFilteringText = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(COLOR_FILTERING_BUTTON))
        ).getText();
        logger.info("Filtered items have {} color", colorFilteringText);
        return colorFilteringText.contains("Green");
    }

    public String selectCountryShippingDestination() {
        List<WebElement> destinationSelectList = driver.findElements(By.xpath(COUNTRY_SHIPPING_DESTINATION));
        List<WebElement> shippingCountryList = new ArrayList<>();
        for (WebElement countries : destinationSelectList) {
            List<WebElement> optionElements = countries.findElements(By.tagName("option"));
            shippingCountryList.addAll(optionElements);
        }
        int randomNumber = (int) Math.floor(Math.random() * (shippingCountryList.size()));
        WebElement selectedCountry = shippingCountryList.get(randomNumber);
        selectedCountry.click();
        logger.info("Destination country set to {}", selectedCountry.getText());
        return selectedCountry.getText();
    }

    public boolean isCountryDestinationSetCorrectly(String setCountry) {
        String destinationFilteringText = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul//li//a[@data-filter-tag-close-link]"))
        ).getText();
        logger.info("Filtered items have {} destination", destinationFilteringText);
        return destinationFilteringText.contains(setCountry);
    }


    public void selectSortingOrderByTheLowestPrice() {
        driver.findElement(By.xpath(ITEMS_SORTING_THE_LOWEST_PRICE)).findElement(By.tagName("button")).click();
        driver.findElement(By.xpath(ITEMS_SORTING_THE_LOWEST_PRICE)).findElement(By.xpath("//a[@data-sort-by-price_asc]")).click();
        logger.info("Products are sorted by the lowest price");
    }

    public boolean areProductsSortByTheLowestPriceCorrectly(List<WebElement> sortedProducts) {
        double previousProductPrice = 0;
        for (int i = 0; i < sortedProducts.size(); i++) {
            logger.info(String.valueOf(getProductAmount(sortedProducts.get(i))));
            double currentProductPrice = getProductAmount(sortedProducts.get(i));
            if (currentProductPrice < previousProductPrice) {
                logger.info("Product prices are not the same - current product price is {}, - previous product price is {}", currentProductPrice, previousProductPrice);
                return false;
            }
            previousProductPrice = currentProductPrice;
        }
        logger.info("Products are sorted correctly");
        return true;
    }
}
