package catalog;

import java.util.*;

public class AudioFeatures implements Feature {

    private String title;
    private int length;
    private List<String> performers;
    private List<String> composers;

    public AudioFeatures(String title, int length, List<String> performers, List<String> composers) {
        checkParam(title, length, performers);
        this.title = title;
        this.length = length;
        this.performers = performers;
        this.composers = composers;
    }

    private void checkParam(String title, int length, List<String> performers) {
        if (Validators.isBlank(title) || length <= 0 || Validators.isEmpty(performers)) {
            throw new IllegalArgumentException("Parameters must not be empty.");
        }
    }

    public AudioFeatures(String title, int length, List<String> performers) {
        checkParam(title, length, performers);
        this.title = title;
        this.length = length;
        this.performers = performers;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getContributors() {
        List<String> union = new ArrayList<>();
        addComposersIfExist(union);
        addPerformers(union);
        return union;
    }

    private void addPerformers(List<String> union) {
        for (String s : performers) {
            union.add(s);
        }
    }

    private void addComposersIfExist(List<String> union) {
        if (composers != null) {
            for (String s : composers) {
                union.add(s);
            }
        }
    }

    @Override
    public boolean isPrintedFeatures() {
        return false;
    }

    @Override
    public boolean isAudioFeatures() {
        return true;
    }

    @Override
    public String toString() {
        return "AudioFeatures{" +
                "title='" + title + '\'' +
                ", length=" + length +
                ", performers=" + performers +
                ", composers=" + composers +
                '}';
    }

    public int getLength() {
        return length;
    }

}
