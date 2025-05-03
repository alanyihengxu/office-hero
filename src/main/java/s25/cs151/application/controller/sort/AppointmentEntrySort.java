package s25.cs151.application.controller.sort;

import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.model.entry.TimeSlotEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AppointmentEntrySort implements EntrySort<AppointmentEntry> {

    @Override
    public List<AppointmentEntry> readAndSort(String filepath) {
        List<AppointmentEntry> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appointments.add(AppointmentEntry.fromCSV(line));
            }

            // Sort first by date, then by timeslot starting hour/minute
            appointments.sort(Comparator
                    .comparing(AppointmentEntry::getDate)
                    .thenComparing((a1, a2) -> {
                        TimeSlotEntry t1 = TimeSlotEntry.fromString(a1.getTimeSlot());
                        TimeSlotEntry t2 = TimeSlotEntry.fromString(a2.getTimeSlot());
                        int cmp = Integer.compare(t1.getFromHour(), t2.getFromHour());
                        if (cmp != 0) return cmp;
                        cmp = Integer.compare(t1.getFromMinute(), t2.getFromMinute());
                        if (cmp != 0) return cmp;
                        return Integer.compare(t1.getToMinute(), t2.getToMinute());
                    })
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to read or sort appointments.", e);
        }
        return appointments;
    }
}
