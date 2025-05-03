package s25.cs151.application.controller.sort;

import s25.cs151.application.model.TimeSlotEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimeSlotEntrySort implements EntrySort<TimeSlotEntry> {

    @Override
    public List<TimeSlotEntry> readAndSort(String filepath) {
        List<TimeSlotEntry> slots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                slots.add(TimeSlotEntry.fromCSV(line));
            }
            slots.sort(Comparator
                    .comparingInt(TimeSlotEntry::getFromHour)
                    .thenComparingInt(TimeSlotEntry::getFromMinute)
                    .thenComparingInt(TimeSlotEntry::getToHour)
                    .thenComparingInt(TimeSlotEntry::getToMinute)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to read timeslots.", e);
        }
        return slots;
    }
}
