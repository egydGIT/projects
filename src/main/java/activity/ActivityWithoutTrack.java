package activity;

public class ActivityWithoutTrack implements Activity {

    private ActivityType activityType;

    public ActivityWithoutTrack(ActivityType activityType) {
        this.activityType = activityType;
    }

    @Override
    public double getDistance() {
        return 0.0;
    }

    @Override
    public ActivityType getType() {
        return activityType;
    }

    @Override
    public boolean hasTrack() {
        return false;
    }

    @Override
    public String toString() {
        return "ActivityWithoutTrack{" +
                "activityType=" + activityType +
                '}';
    }
}
