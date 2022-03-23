package activity;

public interface Activity {

    double getDistance();

    ActivityType getType();

    default boolean hasTrack() {
        return true;
    }

}
