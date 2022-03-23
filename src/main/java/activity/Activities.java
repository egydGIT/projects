package activity;

import java.util.*;

public class Activities {

    private List<Activity> activities;

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public int numberOfTrackActivities() {
        int counter = 0;
        for (Activity activity : activities) {
//            if (activity.getClass().equals(ActivityWithTrack.class)) {       // activity instanceof ActivityWithTrack
            if (activity.hasTrack()) {
                counter++;
            }
        }
        return counter;
    }

    public int numberOfWithoutTrackActivities() {
        int counter = 0;
        for (Activity activity : activities) {
            if (!activity.hasTrack()) {
                counter++;
            }
        }
        return counter;
    }

    public List<Report> distancesByTypes() {
        double[] temp = new double[ActivityType.values().length];
        for (Activity activity : activities) {
            temp[activity.getType().ordinal()] += activity.getDistance();
        }
//        System.out.println(Arrays.toString(temp));      // [0.0, 0.0, 2.722315925321637E7, 0.0]
        List<Report> reports = new ArrayList<>();
        int index = 0;
        for (ActivityType type : ActivityType.values()) {
            reports.add(new Report(type, temp[index]));
            index++;
        }
//        System.out.println(reports);
        // [Report{activityType=BIKING, distance=0.0}, Report{activityType=HIKING, distance=0.0},
        // Report{activityType=RUNNING, distance=2.722315925321637E7}, Report{activityType=BASKETBALL, distance=0.0}]
        return reports;
    }

/*
    // nem összegez, egyesével teszi bele

    public List<Report> distancesByTypes() {
        List<Report> reports = new ArrayList<>();
        for (Activity activity : activities) {
            reports.add(new Report(activity.getType(), activity.getDistance()));
        }
        System.out.println(reports);
        return reports;
    }

    // [Report{activityType=RUNNING, distance=1.3611579626608185E7}, Report{activityType=BASKETBALL, distance=0.0},
    // Report{activityType=RUNNING, distance=1.3611579626608185E7}]
 */

/*
    // nem tart sorrendet, összes tipus benne van

    public List<Report> distancesByTypes() {
        Map<ActivityType, Report> temp = new HashMap<>();
        for (ActivityType type : ActivityType.values()) {
            temp.put(type, new Report(type, 0.0));
        }
        for (Activity activity : activities) {
            Report report = temp.get(activity.getType());
            double distance = report.getDistance();
            distance += activity.getDistance();
            temp.put(activity.getType(), new Report(activity.getType(), distance));
        }
        List<Report> result = new ArrayList<>(temp.values());
        System.out.println(result);
        return result;
    }

    // [Report{activityType=HIKING, distance=0.0}, Report{activityType=BASKETBALL, distance=0.0},
    // Report{activityType=BIKING, distance=0.0}, Report{activityType=RUNNING, distance=2.722315925321637E7}]
*/

}
