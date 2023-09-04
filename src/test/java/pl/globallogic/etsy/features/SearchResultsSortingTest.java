package pl.globallogic.etsy.features;

import org.testng.annotations.Test;

public class SearchResultsSortingTest {
    @Test
    public void pricesShouldBeOrderedAccordingToSortingCriteria() {
        //go to the landing page
        //search for item
        //wait for search result to be loaded
        //select required sorting order ( lowest price )
        //apply sorting
        //wait for search result to be sorted
        //verify item prices sorting order ( take 4 consecutive items and check if price[i] < price[i+1]
    }
}
