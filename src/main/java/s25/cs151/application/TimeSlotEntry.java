package s25.cs151.application;

public class TimeSlotEntry {
    private int fromHour;
    private int fromMinute;
    private int toHour;
    private int toMinute;

    public TimeSlotEntry(int fromHour, int fromMinute, int toHour, int toMinute) {
        this.fromHour = fromHour;
        this.fromMinute = fromMinute;
        this.toHour = toHour;
        this.toMinute = toMinute;
    }

    public int getFromHour() {
        return fromHour;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public int getToHour() {
        return toHour;
    }

    public int getToMinute() {
        return toMinute;
    }

   //Compare method to check for duplicates
    public boolean compares(TimeSlotEntry other) {
        return this.fromHour == other.fromHour &&
                this.fromMinute == other.fromMinute &&
                this.toHour == other.toHour &&
                this.toMinute == other.toMinute;
    }

    @Override
    public String toString() {
        return fromHour + "," + fromMinute + "," + toHour + "," + toMinute;
    }

    //static method to parse entry from CSV
    public static TimeSlotEntry fromCSV(String csv) {
        String[] parts = csv.split(",");
        int fromHour = Integer.parseInt(parts[0]);
        int fromMinute = Integer.parseInt(parts[1]);
        int toHour = Integer.parseInt(parts[2]);
        int toMinute = Integer.parseInt(parts[3]);

        return new TimeSlotEntry(fromHour, fromMinute, toHour, toMinute);
    }
}
