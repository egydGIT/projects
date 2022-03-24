package timesheet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.text.Collator;
import java.time.LocalDateTime;
import java.util.*;

public class Company {

    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<TimeSheetItem> timeSheetItems = new ArrayList<>();

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

    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {     // with Map
        if (!containsEmployee(employees, employeeName)) {
            throw new IllegalArgumentException("There is no employee with this name. ");
        }
        Map<Project, ReportLine> temporaryMap = new HashMap<>();
        fillKeys(temporaryMap);
        fillValueWithFilteredData(employeeName, year, month, temporaryMap);
        List<ReportLine> result = new ArrayList<>(temporaryMap.values());
        List<ReportLine> orderedResult = orderResult(result);
        return orderedResult;
    }

    private List<ReportLine> orderResult(List<ReportLine> result) {
        List<ReportLine> orderedResult = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            for (ReportLine line : result) {
                if (projects.get(i).getName().equals(line.getProject().getName())) {
                    orderedResult.add(line);
                }
            }
        }
        return orderedResult;
    }

    private void fillValueWithFilteredData(String employeeName, int year, int month, Map<Project, ReportLine> temporaryMap) {
        for (TimeSheetItem item : timeSheetItems) {
            if (item.getEmployee().getName().equals(employeeName)
                && item.getBeginDate().getYear() == year
                && item.getBeginDate().getMonthValue() == month) {
                    ReportLine reportLine = temporaryMap.get(item.getProject());
                    reportLine.addTime(item.hoursBetweenDates());
            }
        }
    }

    private void fillKeys(Map<Project, ReportLine> temporaryMap) {
        for (Project project : projects) {
            temporaryMap.put(project, new ReportLine(project, 0L));
        }
    }

//    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {   // with List
//        if (!containsEmployee(employees, employeeName)) {
//            throw new IllegalArgumentException("There is no employee with this name. ");
//        }
//        List<ReportLine> reportLines = createInitialReportLines();
//        List<TimeSheetItem> filteredForParams = filterTimeSheetItemsForParam(employeeName, year, month);
//        fillInitialReportLinesWithData(reportLines, filteredForParams);
//        return reportLines;
//    }
//
//    private void fillInitialReportLinesWithData(List<ReportLine> reportLines, List<TimeSheetItem> filteredForParams) {
//        for (TimeSheetItem item : filteredForParams) {
//            for (ReportLine line : reportLines) {
//                if (item.getProject().getName().equals(line.getProject().getName())) {
//                    line.addTime(item.hoursBetweenDates());
//                }
//            }
//        }
//    }
//
//    private List<TimeSheetItem> filterTimeSheetItemsForParam(String employeeName, int year, int month) {
//        List<TimeSheetItem> filteredForParams = new ArrayList<>();
//        for (TimeSheetItem item : timeSheetItems) {
//            if (item.getEmployee().getName().equals(employeeName)
//                && item.getBeginDate().getYear() == year
//                && item.getBeginDate().getMonthValue() == month) {
//                filteredForParams.add(item);
//            }
//        }
//        return filteredForParams;
//    }
//
//    private List<ReportLine> createInitialReportLines() {
//        List<ReportLine> reportLines = new ArrayList<>();
//        for (Project project : projects) {
//            ReportLine reportLine = new ReportLine(project, 0);
//            reportLines.add(reportLine);
//        }
//        return reportLines;
//    }

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
