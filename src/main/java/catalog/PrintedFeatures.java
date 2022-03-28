package catalog;

import java.util.List;

public class PrintedFeatures implements Feature{

    private String title;
    private int numberOfPages;
    private List<String> authors;

    public PrintedFeatures(String title, int numberOfPages, List<String> authors) {
        checkParam(title, numberOfPages, authors);
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.authors = authors;
    }

    private void checkParam(String title, int numberOfPages, List<String> authors) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Empty title");
        }
        if (numberOfPages <= 0) {
            throw new IllegalArgumentException("Number of pages must be larger, than 0.");
        }
        if (Validators.isEmpty(authors)) {
            throw new IllegalArgumentException("Fill autors as well.");
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getContributors() {
        return authors;
    }

    @Override
    public boolean isPrintedFeatures() {
        return true;
    }

    @Override
    public boolean isAudioFeatures() {
        return false;
    }

    @Override
    public String toString() {
        return "PrintedFeatures{" +
                "title='" + title + '\'' +
                ", numberOfPages=" + numberOfPages +
                ", authors=" + authors +
                '}';
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
