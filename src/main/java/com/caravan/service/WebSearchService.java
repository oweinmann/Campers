package com.caravan.service;

import com.caravan.model.SearchResult;
import com.caravan.model.SearchResultDetail;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WebSearchService {

    private static final int TIMEOUT_MS = 10000;
    private static final int MAX_RESULTS_PER_SITE = 10;

    /**
     * Perform automated web search for used caravans
     */
    public SearchResult performAutomatedSearch(String make, String model) {
        log.info("Starting automated search for: {} {}", make, model);
      make = "jayco";
      model = "";

        SearchResult searchResult = new SearchResult();
        searchResult.setMake(make);
        searchResult.setModel(model);
        searchResult.setSearchDate(LocalDateTime.now());
        searchResult.setSearchQuery(make + " " + model + " used caravan Australia");
        searchResult.setDetails(new ArrayList<>());

        List<SearchResultDetail> allDetails = new ArrayList<>();

        // Search Gumtree
        allDetails.addAll(searchGumtree(make, model, searchResult));

        // Search Trading Post
        allDetails.addAll(searchTradingPost(make, model, searchResult));

        // Search Caravan Sales
        allDetails.addAll(searchCaravanSales(make, model, searchResult));

        // Search Caravan Camping Sales
        allDetails.addAll(searchCaravanCampingSales(make, model, searchResult));

        searchResult.setDetails(allDetails);
        searchResult.setTotalResults(allDetails.size());

        String notes = String.format("Automated search completed at %s. Found %d total results.",
                LocalDateTime.now(), allDetails.size());
        searchResult.setNotes(notes);

        log.info("Automated search completed. Total results: {}", allDetails.size());
        return searchResult;
    }

    /**
     * Search Gumtree for listings
     */
    private List<SearchResultDetail> searchGumtree(String make, String model, SearchResult searchResult) {
        List<SearchResultDetail> results = new ArrayList<>();
        String searchQuery = make + " " + model;

        try {
            log.info("Searching Gumtree for: {}", searchQuery);
            String url = "https://www.gumtree.com.au/s-caravan/k0c18320?q=" + searchQuery.replace(" ", "+");

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                    .timeout(TIMEOUT_MS)
                    .get();

            Elements listings = doc.select("div.user-ad-row, div[class*=listing], article[class*=listing]");
            log.info("Found {} potential Gumtree listings", listings.size());

            int count = 0;
            for (Element listing : listings) {
                if (count >= MAX_RESULTS_PER_SITE) break;

                try {
                    SearchResultDetail detail = new SearchResultDetail();
                    detail.setSearchResult(searchResult);

                    // Extract title
                    Element titleElem = listing.selectFirst("a[class*=title], h3 a, .user-ad-row__title a");
                    if (titleElem != null) {
                        detail.setTitle(titleElem.text().trim());
                        String href = titleElem.attr("href");
                        if (href.startsWith("/")) {
                            href = "https://www.gumtree.com.au" + href;
                        }
                        detail.setListingUrl(href);
                    }

                    // Extract price
                    Element priceElem = listing.selectFirst("[class*=price], .user-ad-price__price");
                    if (priceElem != null) {
                        detail.setPrice(extractPrice(priceElem.text()));
                    }

                    // Extract location
                    Element locationElem = listing.selectFirst("[class*=location], .user-ad-row__location");
                    if (locationElem != null) {
                        detail.setLocation(locationElem.text().trim());
                    }

                    detail.setCondition("Used");
                    detail.setSeller("Gumtree Listing");
                    detail.setNotes("Found via automated Gumtree search");

                    if (detail.getTitle() != null && !detail.getTitle().isEmpty()) {
                        results.add(detail);
                        count++;
                        log.debug("Added Gumtree result: {}", detail.getTitle());
                    }
                } catch (Exception e) {
                    log.debug("Error parsing Gumtree listing: {}", e.getMessage());
                }
            }

            log.info("Successfully parsed {} Gumtree results", results.size());
        } catch (Exception e) {
            log.error("Error searching Gumtree: {}", e.getMessage());
        }

        return results;
    }

    /**
     * Search Trading Post for listings
     */
    private List<SearchResultDetail> searchTradingPost(String make, String model, SearchResult searchResult) {
        List<SearchResultDetail> results = new ArrayList<>();
        String searchQuery = make + " " + model;

        try {
            log.info("Searching Trading Post for: {}", searchQuery);
            String url = "https://www.tradingpost.com.au/search?keyword=" + searchQuery.replace(" ", "+") + "&category=caravans-campers-motorhomes";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                    .timeout(TIMEOUT_MS)
                    .get();

            Elements listings = doc.select("article[class*=listing], div[class*=ad-card], .listing-item");
            log.info("Found {} potential Trading Post listings", listings.size());

            int count = 0;
            for (Element listing : listings) {
                if (count >= MAX_RESULTS_PER_SITE) break;

                try {
                    SearchResultDetail detail = new SearchResultDetail();
                    detail.setSearchResult(searchResult);

                    // Extract title and URL
                    Element titleElem = listing.selectFirst("h3 a, h2 a, a[class*=title]");
                    if (titleElem != null) {
                        detail.setTitle(titleElem.text().trim());
                        String href = titleElem.attr("href");
                        if (href.startsWith("/")) {
                            href = "https://www.tradingpost.com.au" + href;
                        }
                        detail.setListingUrl(href);
                    }

                    // Extract price
                    Element priceElem = listing.selectFirst("[class*=price]");
                    if (priceElem != null) {
                        detail.setPrice(extractPrice(priceElem.text()));
                    }

                    detail.setCondition("Used");
                    detail.setSeller("Trading Post Listing");
                    detail.setNotes("Found via automated Trading Post search");

                    if (detail.getTitle() != null && !detail.getTitle().isEmpty()) {
                        results.add(detail);
                        count++;
                        log.debug("Added Trading Post result: {}", detail.getTitle());
                    }
                } catch (Exception e) {
                    log.debug("Error parsing Trading Post listing: {}", e.getMessage());
                }
            }

            log.info("Successfully parsed {} Trading Post results", results.size());
        } catch (Exception e) {
            log.error("Error searching Trading Post: {}", e.getMessage());
        }

        return results;
    }

    /**
     * Search CaravanSales.com.au for listings
     */
    private List<SearchResultDetail> searchCaravanSales(String make, String model, SearchResult searchResult) {
        List<SearchResultDetail> results = new ArrayList<>();
        String searchQuery = make + " " + model;

        try {
            log.info("Searching CaravanSales for: {}", searchQuery);
            String url = "https://www.caravansales.com.au/cars?q=" + searchQuery.replace(" ", "%20");

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                    .timeout(TIMEOUT_MS)
                    .get();

            Elements listings = doc.select("article, div[class*=listing], .vehicle-card");
            log.info("Found {} potential CaravanSales listings", listings.size());

            int count = 0;
            for (Element listing : listings) {
                if (count >= MAX_RESULTS_PER_SITE) break;

                try {
                    SearchResultDetail detail = new SearchResultDetail();
                    detail.setSearchResult(searchResult);

                    // Extract title and URL
                    Element titleElem = listing.selectFirst("h2 a, h3 a, a[class*=title]");
                    if (titleElem != null) {
                        detail.setTitle(titleElem.text().trim());
                        String href = titleElem.attr("href");
                        if (href.startsWith("/")) {
                            href = "https://www.caravansales.com.au" + href;
                        }
                        detail.setListingUrl(href);
                    }

                    // Extract price
                    Element priceElem = listing.selectFirst("[class*=price]");
                    if (priceElem != null) {
                        detail.setPrice(extractPrice(priceElem.text()));
                    }

                    // Extract year
                    String title = detail.getTitle();
                    if (title != null) {
                        Pattern yearPattern = Pattern.compile("\\b(19|20)\\d{2}\\b");
                        Matcher matcher = yearPattern.matcher(title);
                        if (matcher.find()) {
                            detail.setYear(Integer.parseInt(matcher.group()));
                        }
                    }

                    detail.setCondition("Used");
                    detail.setSeller("CaravanSales Listing");
                    detail.setNotes("Found via automated CaravanSales search");

                    if (detail.getTitle() != null && !detail.getTitle().isEmpty()) {
                        results.add(detail);
                        count++;
                        log.debug("Added CaravanSales result: {}", detail.getTitle());
                    }
                } catch (Exception e) {
                    log.debug("Error parsing CaravanSales listing: {}", e.getMessage());
                }
            }

            log.info("Successfully parsed {} CaravanSales results", results.size());
        } catch (Exception e) {
            log.error("Error searching CaravanSales: {}", e.getMessage());
        }

        return results;
    }

    /**
     * Search CaravanCampingSales.com.au for listings
     */
    private List<SearchResultDetail> searchCaravanCampingSales(String make, String model, SearchResult searchResult) {
        List<SearchResultDetail> results = new ArrayList<>();
        String searchQuery = make + " " + model;

        try {
            log.info("Searching CaravanCampingSales for: {}", searchQuery);
            String url = "https://www.caravancampingsales.com.au/search?q=" + searchQuery.replace(" ", "+") + "&category=caravans";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                    .timeout(TIMEOUT_MS)
                    .get();

            Elements listings = doc.select("div[class*=listing], article, .ad-card");
            log.info("Found {} potential CaravanCampingSales listings", listings.size());

            int count = 0;
            for (Element listing : listings) {
                if (count >= MAX_RESULTS_PER_SITE) break;

                try {
                    SearchResultDetail detail = new SearchResultDetail();
                    detail.setSearchResult(searchResult);

                    // Extract title and URL
                    Element titleElem = listing.selectFirst("h2 a, h3 a, a[class*=title]");
                    if (titleElem != null) {
                        detail.setTitle(titleElem.text().trim());
                        String href = titleElem.attr("href");
                        if (href.startsWith("/")) {
                            href = "https://www.caravancampingsales.com.au" + href;
                        }
                        detail.setListingUrl(href);
                    }

                    // Extract price
                    Element priceElem = listing.selectFirst("[class*=price]");
                    if (priceElem != null) {
                        detail.setPrice(extractPrice(priceElem.text()));
                    }

                    detail.setCondition("Used");
                    detail.setSeller("CaravanCampingSales Listing");
                    detail.setNotes("Found via automated CaravanCampingSales search");

                    if (detail.getTitle() != null && !detail.getTitle().isEmpty()) {
                        results.add(detail);
                        count++;
                        log.debug("Added CaravanCampingSales result: {}", detail.getTitle());
                    }
                } catch (Exception e) {
                    log.debug("Error parsing CaravanCampingSales listing: {}", e.getMessage());
                }
            }

            log.info("Successfully parsed {} CaravanCampingSales results", results.size());
        } catch (Exception e) {
            log.error("Error searching CaravanCampingSales: {}", e.getMessage());
        }

        return results;
    }

    /**
     * Extract price from text
     */
    private BigDecimal extractPrice(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        try {
            // Remove all non-digit characters except dots
            String priceStr = text.replaceAll("[^0-9.]", "");
            if (!priceStr.isEmpty()) {
                return new BigDecimal(priceStr);
            }
        } catch (NumberFormatException e) {
            log.debug("Failed to parse price from: {}", text);
        }

        return null;
    }
}
