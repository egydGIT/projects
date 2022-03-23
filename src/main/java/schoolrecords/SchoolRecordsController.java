package schoolrecords;

import java.util.*;

public class SchoolRecordsController {

    private final ClassRecords classRecords = new ClassRecords("6", new Random());
    private List<Subject> subjects;
    private List<Tutor> tutors;

    public void initSchool() {
        Subject biology = new Subject("biológia");
        Subject chemistry = new Subject("kémia");
        Subject literature = new Subject("irodalom");
        Subject history = new Subject("történelem");

        subjects = new ArrayList<>(Arrays.asList(
                biology, chemistry, literature, history));

        tutors = new ArrayList<>(Arrays.asList(
                new Tutor("Scele Tom", new ArrayList<>(Arrays.asList(biology, chemistry))),
                new Tutor("Jane Talent", new ArrayList<>(Arrays.asList(literature))),
                new Tutor("Mark Past", new ArrayList<>(Arrays.asList(history)))));
    }

    public void printMenu() {
        System.out.println(
            "Menü:\n" +
            "1. Diákok nevének listázása\n" +
            "2. Diák név alapján keresése\n" +
            "3. Diák létrehozása\n" +
            "4. Diák név alapján törlése\n" +
            "5. Diák feleltetése\n" +
            "6. Osztályátlag kiszámolása\n" +
            "7. Tantárgyi átlag kiszámolása\n" +
            "8. Diákok átlagának megjelenítése\n" +
            "9. Diák átlagának kiírása\n" +
            "10. Diák tantárgyhoz tartozó átlagának kiírása\n" +
            "11. Kilépés");
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kérem, válasszon a fenti menüpontok közül.");
        printMenu();
        int number = 0;
        while (number < 11) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                executeMenu(number);
            } catch (NumberFormatException nfe) {
                System.out.println("Kérem a választott menüpontok sorszámát írja be.");
            }
        }
    }

    public void executeMenu(int numberOfMenu) {
        Scanner scanner = new Scanner(System.in);
        switch (numberOfMenu) {
            case 1: {
                System.out.println(classRecords.listStudentNames());

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 2: {
                System.out.println("Kérem írja be a keresett diák nevét!");
                String answer = scanner.nextLine();
                System.out.println(classRecords.findStudentByName(answer));

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 3: {
                System.out.println("Kérem írja be az új diák nevét!");
                String answer = scanner.nextLine();
                classRecords.addStudent(new Student(answer));

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 4: {
                System.out.println("Kérem írja be a törölni kívánt diák nevét!");
                String answer = scanner.nextLine();
                Student found = classRecords.findStudentByName(answer);
                classRecords.removeStudent(found);

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 5: {
                Student foundStudent = classRecords.repetition();
                System.out.println("Az alábbi diákot kell feleltetni:\n" + foundStudent.getName());

                System.out.println("Kapott érdemjegy: ");
                int answerMark = Integer.parseInt(scanner.nextLine());
                MarkType markType = findMark(answerMark);

                System.out.println("Tantárgy: ");
                String answerSubject = scanner.nextLine();
                Subject subject = findSubject(answerSubject);

                System.out.println("Oktató: ");
                String answerTutor = scanner.nextLine();
                Tutor tutor = findTutor(answerTutor);

                Mark newMark = new Mark(markType, subject, tutor);
                foundStudent.grading(newMark);

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 6: {
                double result = classRecords.calculateClassAverage();
                System.out.println(result);

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 7: {
                System.out.println("Kérem adja meg a tantárgyat: ");
                String answerSubject = scanner.nextLine();
                Subject subject = findSubject(answerSubject);
                double result = classRecords.calculateClassAverageBySubject(subject);
                System.out.println(result);

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 8: {
                System.out.println(classRecords.listStudyResults());

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 9: {
                System.out.println("Kérem adja meg a diák nevét: ");
                String answerStudent = scanner.nextLine();
                Student student = classRecords.findStudentByName(answerStudent);
                System.out.println(student.calculateAverage());

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 10: {
                System.out.println("Kérem adja meg a diák nevét: ");
                String answerStudent = scanner.nextLine();
                Student student = classRecords.findStudentByName(answerStudent);

                System.out.println("Kérem adja meg a tantárgy nevét: ");
                String answerSubject = scanner.nextLine();
                Subject subject = findSubject(answerSubject);
                System.out.println(student.calculateSubjectAverage(subject));

                System.out.println("Kérem, válasszon a fenti menüpontok közül.");
                return;
            }
            case 11: {
                return;
            }
        }
    }

    private MarkType findMark(int answerMark) {
        for (MarkType markType : MarkType.values()) {
            if (markType.getValue() == answerMark) {
                return markType;
            }
        }
        throw new IllegalStateException("Can not find this mark.");
    }

    private Subject findSubject(String answerSubject) {
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(answerSubject)) {
                return s;
            }
        }
        throw new IllegalStateException("Can not find this subject: " + answerSubject);
    }

    private Tutor findTutor(String answerTutor) {
        for (Tutor tutor : tutors) {
            if (tutor.getName().equals(answerTutor)) {
                return tutor;
            }
        }
        throw new IllegalStateException("Can not find this tuto: " + answerTutor);
    }



    public static void main(String[] args) {

        SchoolRecordsController src = new SchoolRecordsController();
        src.initSchool();
        src.runMenu();

    }

}
