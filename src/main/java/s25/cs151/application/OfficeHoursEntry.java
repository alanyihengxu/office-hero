package s25.cs151.application;

import java.util.List;

//This class holds entry information for office hours
public class OfficeHoursEntry {
    private String year;
    private String semester;
    private List<String> days;

    // Constructor
    public OfficeHoursEntry(String year, String semester, List<String> days) {
        this.year = year;
        this.semester = semester;
        this.days = days;
    }

    // Getters
    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public List<String> getDays() {
        return days;
    }
}

