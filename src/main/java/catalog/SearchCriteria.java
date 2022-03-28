package catalog;

public class SearchCriteria {

    private static final String DEFAULT_CONTRIBUTOR = "Anonymus";
    private static final String DEFAULT_TITLE = "No title";

    private String title;
    private String contributor;

    public SearchCriteria(String title, String contributor) {
        this.title = title;
        this.contributor = contributor;
    }

    public static SearchCriteria createByBoth(String title, String contributor) {
        if (Validators.isBlank(title) || Validators.isBlank(contributor)) {
            throw new IllegalArgumentException("Fill param.");
        }
//        checkTitle(title);
//        checkContributor(contributor);
        return new SearchCriteria(title, contributor);
    }

    public static SearchCriteria createByContributor(String contributor) {
        if (Validators.isBlank(contributor)) {
            throw new IllegalArgumentException("Fill contributor.");
        }
//        checkContributor(contributor);
        return new SearchCriteria(DEFAULT_TITLE, contributor);
    }

//    private static void checkContributor(String contributor) {
//        if (contributor == null || contributor.isEmpty()) {
//            throw new IllegalArgumentException("Fill contributor.");
//        }
//    }

    public static SearchCriteria createByTitle(String title) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Fill title.");
        }
//        checkTitle(title);
        return new SearchCriteria(title, DEFAULT_CONTRIBUTOR);
    }

//    private static void checkTitle(String title) {
//        if (title == null || title.isEmpty()) {
//            throw new IllegalArgumentException("Fill title.");
//        }
//    }

    public boolean hasContributor() {
        if (contributor.equals(DEFAULT_CONTRIBUTOR)) {
            return false;
        }
        return true;
    }

    public boolean hasTitle() {
        if (title.equals(DEFAULT_TITLE)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "title='" + title + '\'' +
                ", contributor='" + contributor + '\'' +
                '}';
    }

    public String getContributor() {
        return contributor;
    }

    public String getTitle() {
        return title;
    }

}
