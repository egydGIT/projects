package catalog;

import java.util.List;

public interface Feature {

    String getTitle();
    List<String> getContributors();

    boolean isPrintedFeatures();
    boolean isAudioFeatures();

}
