package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private List<Mark> marks = new ArrayList<>();
    private String name;

    public Student(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        this.name = name;
    }

    public void grading(Mark mark) {
        if (mark == null) {
            throw new NullPointerException("Mark must not be null!");
        }
        marks.add(mark);
    }

    public double calculateAverage() {
        return calculateAverageFromList(marks);
    }

    private double calculateAverageFromList(List<Mark> marksList) {
        if (marksList.isEmpty()) {
            return 0;
        }
        double sum = 0.0;
        for (Mark mark : marksList) {
            sum += mark.getMarkType().getValue();
        }
        double average = sum / marksList.size();
//        String result = String.format("%3.2f" + average);
//        return Double.parseDouble(result);
        return Math.round(average * 100) / 100.0;
    }

    public double calculateSubjectAverage(Subject subject) {
        List<Mark> filtered = new ArrayList<>();
        for (Mark mark : marks) {
            if (mark.getSubject().getSubjectName().equals(subject.getSubjectName())) {
                filtered.add(mark);
            }
        }
        return calculateAverageFromList(filtered);
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(marks, student.marks) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marks, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append(" marks: ");
        for (Mark mark : marks) {
            sb.append(mark.getSubject().getSubjectName() + ": ")
                    .append(mark.toString())
                    .append(" ");
        }
        return sb.toString().trim();
    }

    private boolean isEmpty(String name) {
        return name.isEmpty() || name.isBlank() || name == null;
    }
}
