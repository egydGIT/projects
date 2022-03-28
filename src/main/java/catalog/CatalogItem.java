package catalog;

import java.util.ArrayList;
import java.util.List;

public class CatalogItem {

    private String registrationNumber;
    private int price;
    private List<Feature> features = new ArrayList<>();

    public CatalogItem(String registrationNumber, int price, Feature... feature) {
        if (Validators.isBlank(registrationNumber) || price <= 0 || feature == null) {
            throw new IllegalArgumentException("Fill every parameters!");
        }
        this.registrationNumber = registrationNumber;
        this.price = price;
        for (Feature f : feature) {
            features.add(f);
        }
    }

    public int numberOfPagesAtOneItem() {
        int sum = 0;
        for (Feature feature : features) {
            if (feature.isPrintedFeatures()) {
                PrintedFeatures actual = (PrintedFeatures) feature;
                sum += actual.getNumberOfPages();
            }
        }
        return sum;
    }

    public int fullLengthAtOneItem() {
        int sum = 0;
        for (Feature feature : features) {
            if (feature.isAudioFeatures()) {
                AudioFeatures actual = (AudioFeatures) feature;
                sum += actual.getLength();
            }
        }
        return sum;
    }

    public List<String> getContributors() {
        List<String> result = new ArrayList<>();
        for (Feature feature : features) {
            List<String> actual = feature.getContributors();
            for (String s : actual) {
                result.add(s);
            }
        }
        return result;
    }

    public List<String> getTitles() {
        List<String> result = new ArrayList<>();
        for (Feature feature : features) {
            result.add(feature.getTitle());
        }
        // Collections.sort(result);
        return result;
    }

    public boolean hasAudioFeature() {
        boolean contains = false;
        for (Feature feature : features) {
            if (feature.isAudioFeatures()) {
                contains = true;
            }
        }
        return contains;
    }

    public boolean hasPrintedFeature() {
        boolean contains = false;
        for (Feature feature : features) {
            if (feature.isPrintedFeatures()) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", price=" + price +
                ", features=" + features +
                '}';
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getPrice() {
        return price;
    }

    public List<Feature> getFeatures() {
        return features;
    }


}
