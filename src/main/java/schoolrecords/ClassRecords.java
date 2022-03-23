package schoolrecords;

import java.util.*;

public class ClassRecords {

    private String className;
    private Random random;
    private List<Student> students = new ArrayList<>();

    public ClassRecords(String className, Random random) {
        this.className = className;
        this.random = random;
    }

    public boolean addStudent(Student student) {
        if (alreadyContains(student)) {
//            throw new IllegalArgumentException("Student with this name already exists: " + student.getName());
            return false;
        }
        students.add(student);
        return true;
    }

    private boolean alreadyContains(Student student) {
        for (Student s : students) {
            if (s.getName().equals(student.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        if (!alreadyContains(student)) {
//            throw new IllegalArgumentException("There is no student with this name: " + student.getName());
            return false;
        }
        Iterator<Student> iter = students.iterator();
        while (iter.hasNext()) {
            Student actual = iter.next();
            if (actual.getName().equals(student.getName())) {
                iter.remove();
            }
        }
        return true;
    }

    public double calculateClassAverage() {
        if (students.isEmpty()) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        if (!hasMarks()) {
            throw new ArithmeticException("No marks present, average calculation aborted!");
        }
        double result = 0.0;
        int counter = 0;
        for (Student student : students) {
            double average = student.calculateAverage();
            if (average != 0.0) {
                result += average;
                counter++;
            }
        }
        double averageSum = result / counter;
        return Math.round(100 * averageSum) / 100.0;
    }

    private boolean hasMarks() {
        for (Student student : students) {
            if (student.getMarks().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public double calculateClassAverageBySubject(Subject subject) {
        if (students.isEmpty()) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        double result = 0.0;
        int counter = 0;
        for (Student student : students) {
            double average = student.calculateSubjectAverage(subject);
            if (average != 0.0) {
                result += average;
                counter++;
            }
        }
        double averageSum = result / counter;
        return Math.round(100 * averageSum) / 100.0;
    }

    public Student findStudentByName(String name) {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to search!");
        }
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        throw new IllegalArgumentException("Student by this name cannot be found! " + name);
    }

    public String listStudentNames() {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students.");
        }
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(student.getName()).append(", ");
        }
        return sb.substring(0, sb.toString().lastIndexOf(","));
    }

    public List<StudyResultByName> listStudyResults() {
        List<StudyResultByName> result = new ArrayList<>();
        String name = "";
        double average = 0.0;
        for (Student student : students) {
            name = student.getName();
            average = student.calculateAverage();
            StudyResultByName actualStudent = new StudyResultByName(name, average);
            result.add(actualStudent);
        }
        return result;
    }

    public Student repetition() {
        if (students.isEmpty()) {
            throw new IllegalStateException("No students to select for repetition!");
        }
//        Random random = new Random(5);              // emiatt menuben ClassCastExc.
//        int ordinal = random.nextInt(students.size());
        int ordinal = this.random.nextInt(students.size());
        return students.get(ordinal);
    }

    public String getClassName() {
        return className;
    }

    private boolean isEmpty(String name) {
        return name.isEmpty() || name.isBlank() || name == null;
    }
}
