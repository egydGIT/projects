package meetingrooms;

import java.util.Scanner;

public class Controller {

    private Office office = new Office();

//    public void readOffice() {
//        Scanner scanner = new Scanner(System.in);
//        int number;
//        System.out.println("Hány db tárgyalót szeretne rögzíteni? ");
//        int untill = scanner.nextInt();
//        for (int i = 0; i < untill; i++) {
//            Scanner scanner1 = new Scanner(System.in);
//            System.out.println("Kérem adja meg a " + number + ". tárgyaló nevét: ");
//            String name = scanner1.nextLine();
//            System.out.println("hosszát: ");
//            String length = scanner1.nextLine();
//            System.out.println("szélességét: ");
//            String width = scanner1.nextLine();
//
//            office.addMeetingRoom(new MeetingRoom(name, Integer.parseInt(length), Integer.parseInt(width)));
//            number++;
//        }
//    }

    public void readOffice() {
        System.out.println("MENU:\n" +
                "Új tárgyaló felvétele: enter\n" +
                "Kilépés: exit\n");
        Scanner scanner = new Scanner(System.in);
        int number = 1;
        for ( ; ; ) {
            System.out.println("Kérem adja meg a " + number + ". tárgyaló nevét: ");
            String name = scanner.nextLine();
            int length;
            int width;

            System.out.println("hosszát: ");
            try {
                length = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Számot írj a hosszúsághoz! ");
                continue;
            }

            System.out.println("szélességét: ");
            try {
                width = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Számot írj a szélességhez! ");
                continue;
            }

            office.addMeetingRoom(new MeetingRoom(name, length, width));
            number++;

            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                return;
            }
        }
    }

    public void printMenu() {
        System.out.println("MENU:\n" +
                "1. Tárgyalók sorrendben\n" +
                "2. Tárgyalók visszafele sorrendben\n" +
                "3. Minden második tárgyaló\n" +
                "4. Területek\n" +
                "5. Keresés pontos név alapján\n" +
                "6. Keresés névtöredék alapján\n" +
                "7. Keresés terület alapján");
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kérem adja meg a választott menü számát!");
        int number = 0;
        while (number < 9) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                executeMenu(number);
            } catch (NumberFormatException e) {
                System.out.println("Számot írj 1-7 között! ");
                continue;
            }
        }
    }

    private void executeMenu(int number) {
        Scanner scanner = new Scanner(System.in);
        switch (number) {
            case 1: {
                System.out.println(office.printNames());
                return;
            }
            case 2: {
                System.out.println(office.printNamesReverse());
                return;
            }
            case 3: {
                System.out.println(office.printEvenNames());
                return;
            }
            case 4: {
                System.out.println(office.printArea());
                return;
            }
            case 5: {
                System.out.println("Kérem adja meg a keresett tárgyaló nevét: ");
                String answer = scanner.nextLine();
                System.out.println(office.printMeetingRoomsWithName(answer));
                return;
            }
            case 6: {
                System.out.println("Kérem adja meg a keresett tárgyaló nevének ismert részletét: ");
                String answer2 = scanner.nextLine();
                System.out.println(office.printMeetingRoomsContains(answer2));
                return;
            }
            case 7: {
                System.out.println("Kérem adja meg a keresett tárgyaló minimális területét: ");
                int answer3 = Integer.parseInt(scanner.nextLine());
                System.out.println(office.printAreasLargerThan(answer3));
                return;
            }
        }
    }

    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.readOffice();
        controller.printMenu();
        controller.runMenu();

    }
}
