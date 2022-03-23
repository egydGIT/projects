package timesheet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.util.*;

public class Company {

    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<TimeSheetItem> timeSheetItems = new ArrayList<>();
//    private List<ReportLine> reportLines = new ArrayList<>();

    public Company(InputStream employeeFile, InputStream projectFile) {
        readEmployeeFile(employeeFile);
        readProjectFile(projectFile);
    }

    private void readEmployeeFile(InputStream employeeFile) {
        try (BufferedReader employeeReader = new BufferedReader(new InputStreamReader(employeeFile))) {
            String line;
            while ((line = employeeReader.readLine()) != null) {
                String[] temp = line.split(" ");
                employees.add(new Employee(temp[0], temp[1]));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file. ");
        }
    }

    private void readProjectFile(InputStream projectFile) {
        try (BufferedReader employeeReader = new BufferedReader(new InputStreamReader(projectFile))) {
            String line;
            while ((line = employeeReader.readLine()) != null) {
                projects.add(new Project(line));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read file. ");
        }
    }

    public void addTimeSheetItem(Employee employee, Project project, LocalDateTime beginDate, LocalDateTime endDate) {
        TimeSheetItem timeSheetItem = new TimeSheetItem(employee, project, beginDate, endDate);
        timeSheetItems.add(timeSheetItem);
    }

//    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {
//        if (!containsEmployee(employees, employeeName)) {
//            throw new IllegalArgumentException("There is no employee with this name. ");
//        }
//        Map<Project, ReportLine> filtered = new HashMap<>();
//        for (Project project : projects) {
//            filtered.put(project, new ReportLine(project, 0L));
//        }
//        for (TimeSheetItem item : timeSheetItems) {
//            if (item.getEmployee().getName().equals(employeeName)
//                && item.getBeginDate().getYear() == year
//                && item.getBeginDate().getMonthValue() == month) {
//                    ReportLine reportLine = filtered.get(item.getProject());
//                    reportLine.addTime(item.hoursBetweenDates());
//            }
//        }
//        List<ReportLine> result = new ArrayList<>(filtered.values());
////        System.out.println(result);
////        [ReportLine{project=Project{name='Java'}, time=10},
////        ReportLine{project=Project{name='C++'}, time=4},
////        ReportLine{project=Project{name='Haskell'}, time=0},
////        ReportLine{project=Project{name='C#'}, time=0}]
//        return result;                                        // project sorrend elveszik
//    }

    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {
        if (!containsEmployee(employees, employeeName)) {
            throw new IllegalArgumentException("There is no employee with this name. ");
        }
        List<ReportLine> reportLines = new ArrayList<>();
        for (Project project : projects) {
            ReportLine reportLine = new ReportLine(project, 0);
            reportLines.add(reportLine);
        }
        List<TimeSheetItem> filteredForParams = new ArrayList<>();
        for (TimeSheetItem item : timeSheetItems) {
            if (item.getEmployee().getName().equals(employeeName)
                && item.getBeginDate().getYear() == year
                && item.getBeginDate().getMonthValue() == month) {
                filteredForParams.add(item);
            }
        }
        for (TimeSheetItem item : filteredForParams) {
            for (ReportLine line : reportLines) {
                if (item.getProject().getName().equals(line.getProject().getName())) {
                    line.addTime(item.hoursBetweenDates());
                }
            }
        }
        return reportLines;
    }

    private boolean containsEmployee(List<Employee> employees, String employeeName) {
        boolean contains = false;
        for (Employee employee : employees) {
            if (employee.getName().equals(employeeName)) {
                contains = true;
            }
        }
        return contains;
    }

    public void printToFile(String employeeName, int year, int month, Path file) {
        if (!containsEmployee(employees, employeeName)) {
            throw new IllegalArgumentException("There is no employee with this name. ");
        }
        List<ReportLine> lines = calculateProjectByNameYearMonth(employeeName, year, month);
        long sum = sumHours(lines);

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            printFirstLine(employeeName, year, month, sum, writer);
            printOtherLines(lines, writer);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not write file. ");
        }
    }

    private long sumHours(List<ReportLine> lines) {
        long sum = 0L;
        for (ReportLine line : lines) {
            sum += line.getTime();
        }
        return sum;
    }

    private void printFirstLine(String employeeName, int year, int month, long sum, BufferedWriter writer) throws IOException {
        String haeder = String.format("%s\t%d-%02d\t%d\n", employeeName, year, month, sum);
        writer.write(haeder);
    }

    private void printOtherLines(List<ReportLine> lines, BufferedWriter writer) throws IOException {
        for (ReportLine line : lines)
            if (line.getTime() > 0) {
                writer.write(String.format("%s\t%d\n", line.getProject().getName(), line.getTime()));
            }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<TimeSheetItem> getTimeSheetItems() {
        return timeSheetItems;
    }

}
