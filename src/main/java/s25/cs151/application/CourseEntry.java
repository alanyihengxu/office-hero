package s25.cs151.application;

public class CourseEntry {
    private String courseCode;
    private String courseName;
    private String sectionNumber;

    public CourseEntry(String courseCode, String courseName, String sectionNumber) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    // Compare method to check for duplicates
    public boolean compares(CourseEntry other) {
        return this.courseCode.equals(other.courseCode) &&
                this.courseName.equals(other.courseName) &&
                this.sectionNumber.equals(other.sectionNumber);
    }

    @Override
    public String toString() {
        return courseCode + "," + courseName + "," + sectionNumber + ",";
    }

    // Static method to parse an entry from CSV format
    public static CourseEntry fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        String courseCode = values[0];
        String courseName = values[1];
        String sectionNumber = values[2];
        return new CourseEntry(courseCode, courseName, sectionNumber);
    }
}
