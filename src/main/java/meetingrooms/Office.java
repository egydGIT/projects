package meetingrooms;

import java.util.ArrayList;
import java.util.List;

public class Office {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    public void addMeetingRoom(MeetingRoom meetingRoom) {
        meetingRooms.add(meetingRoom);
    }

    public String printNames() {
        StringBuilder sb = new StringBuilder();
        for (MeetingRoom room : meetingRooms) {
            sb.append(room.getName()).append(", ");
        }
        return sb.toString().substring(0, sb.lastIndexOf(","));
    }

    public String printNamesReverse() {
        StringBuilder sb = new StringBuilder();
        for (int i = meetingRooms.size()-1; i >= 0; i--) {
            sb.append(meetingRooms.get(i).getName()).append(", ");
        }
        return sb.toString().substring(0, sb.lastIndexOf(","));
    }

    public String printEvenNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < meetingRooms.size(); i+=2) {
            sb.append(meetingRooms.get(i).getName()).append(", ");
        }
        return sb.toString().substring(0, sb.lastIndexOf(","));
    }

    public String printArea() {
        StringBuilder sb = new StringBuilder();
        for (MeetingRoom room : meetingRooms) {
            sb.append("name: " + room.getName())
                    .append(", lenght: " + room.getLength())
                    .append(", width: " + room.getWidth())
                    .append(", area: " + room.getArea())
                    .append("\n");
        }
        return sb.toString().substring(0, sb.lastIndexOf("\n"));
    }

    public String printMeetingRoomsWithName(String name) {
        StringBuilder sb = new StringBuilder();
        for (MeetingRoom room : meetingRooms) {
            if (room.getName().equals(name)) {
                sb.append("lenght: " + room.getLength())
                        .append(", width: " + room.getWidth())
                        .append(", area: " + room.getArea());
            }
        }
        return sb.toString();
    }

    public String printMeetingRoomsContains(String part) {
        StringBuilder sb = new StringBuilder();
        for (MeetingRoom room : meetingRooms) {
            if (room.getName().contains(part)) {
                sb.append("lenght: " + room.getLength())
                        .append(", width: " + room.getWidth())
                        .append(", area: " + room.getArea());
            }
        }
        return sb.toString();
    }

    public String printAreasLargerThan(int area) {
        StringBuilder sb = new StringBuilder();
        for (MeetingRoom room : meetingRooms) {
            if (room.getArea() > area) {
                sb.append("name: " + room.getName())
                        .append(", lenght: " + room.getLength())
                        .append(", width: " + room.getWidth())
                        .append(", area: " + room.getArea())
                        .append("\n");
            }
        }
        return sb.toString().substring(0, sb.lastIndexOf("\n"));
    }

}
