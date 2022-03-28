package catalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalog {

    private List<CatalogItem> catalogItems = new ArrayList<>();

    public void addItem(CatalogItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Fill parameters.");
        }
        catalogItems.add(item);
    }

    public void deleteItemByRegistrationNumber(String regNumber) {
        Iterator<CatalogItem> iter = catalogItems.iterator();
        while (iter.hasNext()) {
            CatalogItem actual = iter.next();
            if (actual.getRegistrationNumber().equals(regNumber)) {
                iter.remove();
            }
        }
    }

    public List<CatalogItem> getAudioLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem item : catalogItems) {
            if (item.hasAudioFeature()) {
                result.add(item);
            }
        }
        return result;
    }

    public List<CatalogItem> getPrintedLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem item : catalogItems) {
            if (item.hasPrintedFeature()) {
                result.add(item);
            }
        }
        return result;
    }

    public int getAllPageNumber() {
        int sum = 0;
        for (CatalogItem item : catalogItems) {
            sum += item.numberOfPagesAtOneItem();
        }
        return sum;
    }

    public int getFullLength() {
        int sum = 0;
        for (CatalogItem item : catalogItems) {
            sum += item.fullLengthAtOneItem();
        }
        return sum;
    }

    public double averagePageNumberOver(int pageNumber) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page number must be positive");
        }
        double sum = 0.0;
        int counter = 0;
        for (CatalogItem item : catalogItems) {
            int actualPageNumber = item.numberOfPagesAtOneItem();
            if (actualPageNumber > pageNumber) {
                counter++;
                sum += actualPageNumber;
            }
        }
        if (sum == 0) {
            throw new IllegalArgumentException("No page");
        }
        return sum / counter;
    }

    public List<CatalogItem> findByCriteria(SearchCriteria criteria) {
        List<CatalogItem> result = new ArrayList<>();
        List<String> actualTitles;
        List<String> actualContributors;
        for (CatalogItem item : catalogItems) {
             actualTitles = item.getTitles();
             for (String title : actualTitles) {
                 if (title.equals(criteria.getTitle())) {
                     result.add(item);
                 }
             }
             actualContributors = item.getContributors();
             for (String contributor : actualContributors) {
                 if (contributor.equals(criteria.getContributor())) {
                     if(!result.contains(item)) {
                         result.add(item);
                     }
                 }
             }
        }
        return result;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }
}
