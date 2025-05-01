package s25.cs151.application.model;

public class AppointmentEntry {
    private String name;
    private String date;
    private String timeSlot;
    private String course;
    private String reason;
    private String comment;

    public AppointmentEntry(String name, String date, String timeSlot, String course, String reason, String comment) {
        this.name = name;
        this.date = date;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public String getCourse() { return course; }
    public String getReason() { return reason; }
    public String getComment() { return comment; }

    public String toCSV() {
        return String.join(",", name, date, timeSlot, course, reason, comment);
    }

    public static AppointmentEntry fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        return new AppointmentEntry(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}
