package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Tutor {

    private String name;
    private List<Subject> taughtSubjects = new ArrayList<>();

    public Tutor(String name, List<Subject> taughtSubjects) {
        this.name = name;
        for (Subject taughtSubject : taughtSubjects) {
            this.taughtSubjects.add(taughtSubject);
        }
    }

    public boolean tutorTeachingSubject(Subject subject) {
        boolean answer = false;
        for (Subject s : taughtSubjects) {
            if (s.getSubjectName().equals(subject.getSubjectName())) {
                answer = true;
            }
        }
        return answer;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getTaughtSubjects() {
        return taughtSubjects;
    }
}
