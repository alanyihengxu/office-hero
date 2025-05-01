package s25.cs151.application.model;
import java.util.List;

public class OfficeHourEntry {
    private String semester;
    private int year;
    private List<String> days;

    public OfficeHourEntry(String semester, int year, List<String> days) {
        this.semester = semester;
        this.year = year;
        this.days = days;
    }

    public String getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public List<String> getDays() {
        return days;
    }

    // Compare method to check for duplicates
    public boolean compares(OfficeHourEntry other) {
        return this.semester.equals(other.semester) &&
                this.year == other.year;
    }

    @Override
    public String toString() {
        return semester + "," + year + "," + String.join(",", days);
    }

    // Static method to parse an entry from CSV format
    public static OfficeHourEntry fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        String semester = values[0];
        int year = Integer.parseInt(values[1]);
        List<String> days = List.of(values).subList(2, values.length);
        return new OfficeHourEntry(semester, year, days);
    }
}