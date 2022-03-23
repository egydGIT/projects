package activity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Track {

    private List<TrackPoint> trackPoints = new ArrayList<>();

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public void loadFromGpx(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            Coordinate coordinate = null;
            TrackPoint trackPoint = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("<trkpt")) {
                    double lat = Double.parseDouble(line.substring(15, 25));
                    double lon = Double.parseDouble(line.substring(32, 42));
                    coordinate = new Coordinate(lat, lon);
                }
                if (line.contains("<ele")) {
                    double tracP = Double.parseDouble(line.substring(9, 14));
                    trackPoint = new TrackPoint(coordinate, tracP);
                    addTrackPoint(trackPoint);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file. ");
        }

    }

    public Coordinate findMaximumCoordinate() {
        if (trackPoints.isEmpty()) {
            throw new IllegalStateException("No points.");
        }
        double maxLat = 0.0;
        double maxLon = 0.0;
        for (TrackPoint trackPoint : trackPoints) {
            double actualLat = trackPoint.getCoordinate().getLatitude();
            if (actualLat > maxLat) {
                maxLat = actualLat;
            }
        }
        for (TrackPoint trackPoint : trackPoints) {
            double actualLon = trackPoint.getCoordinate().getLongitude();
            if (actualLon > maxLon) {
                maxLon = actualLon;
            }
        }
        return new Coordinate(maxLat, maxLon);
    }

    public Coordinate findMinimumCoordinate() {
        if (trackPoints.isEmpty()) {
            throw new IllegalStateException("No points.");
        }
        double minLat = Integer.MAX_VALUE;
        double minLon = Integer.MAX_VALUE;
        for (TrackPoint trackPoint : trackPoints) {
            double actualLat = trackPoint.getCoordinate().getLatitude();
            if (actualLat < minLat) {
                minLat = actualLat;
            }
        }
        for (TrackPoint trackPoint : trackPoints) {
            double actualLon = trackPoint.getCoordinate().getLongitude();
            if (actualLon < minLon) {
                minLon = actualLon;
            }
        }
        return new Coordinate(minLat, minLon);
    }

    public double getRectangleArea() {
        double aSide = findMaximumCoordinate().getLatitude() - findMinimumCoordinate().getLatitude();
        double bSide = findMaximumCoordinate().getLongitude() - findMinimumCoordinate().getLongitude();
        return aSide * bSide;
    }

    public double getDistance() {                               // for
        double sum = 0.0;
        for (int i = 1; i < trackPoints.size(); i++) {
            TrackPoint prev = trackPoints.get(i-1);
            TrackPoint next = trackPoints.get(i);
            sum += prev.getDistanceFrom(next);
        }
        return sum;
    }

    public double getDistanceWithForEach() {                    // for-each
        double sum = 0.0;
        TrackPoint prev = trackPoints.get(0);
        for (TrackPoint actual : trackPoints) {
            sum += prev.getDistanceFrom(actual);
            prev = actual;
        }
        return sum;
    }

    public double getFullDecrease() {
        double sumDecrease = 0.0;
        for (int i = 1; i < trackPoints.size(); i ++) {
            double prev = trackPoints.get(i-1).getElevation();
            double next = trackPoints.get(i).getElevation();
            if (prev > next) {
                sumDecrease += prev - next;
            }
        }
        return sumDecrease;
    }

    public double getFullElevation() {
        double sumElevation = 0.0;
        for (int i = 1; i < trackPoints.size(); i ++) {
            double prev = trackPoints.get(i-1).getElevation();
            double next = trackPoints.get(i).getElevation();
            if (prev < next) {
                sumElevation += next - prev;
            }
        }
        return sumElevation;
    }

    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }
}
