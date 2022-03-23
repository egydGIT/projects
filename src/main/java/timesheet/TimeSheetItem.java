package timesheet;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeSheetItem {

    private Employee employee;
    private Project project;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    public TimeSheetItem(Employee employee, Project project, LocalDateTime beginDate, LocalDateTime endDate) {
        checkParam(beginDate, endDate);
        this.employee = employee;
        this.project = project;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    private void checkParam(LocalDateTime beginDate, LocalDateTime endDate) {
        if (endDate.isBefore(beginDate)) {
            throw new IllegalArgumentException("End date (" + endDate + ") can not be before begin date (" + beginDate + ")! ");
        }
        if (!beginDate.toLocalDate().equals(endDate.toLocalDate())) {
            throw new IllegalArgumentException("Begin date (" + beginDate + ") must be in the same day, as end date (" + endDate + ")! ");
        }
    }

    public long hoursBetweenDates() {
        return Duration.between(beginDate, endDate).toHours();
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "TimeSheetItem{" +
                "employee=" + employee +
                ", project=" + project +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
