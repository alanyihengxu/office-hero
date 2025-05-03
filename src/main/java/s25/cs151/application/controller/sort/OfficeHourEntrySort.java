package s25.cs151.application.controller.sort;

import s25.cs151.application.model.OfficeHourEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OfficeHourEntrySort implements EntrySort<OfficeHourEntry> {

    @Override
    public List<OfficeHourEntry> readAndSort(String filepath) {
        List<OfficeHourEntry> officeHours = new ArrayList<>();
        List<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }

            Comparator<String> semesterComparator = (o1, o2) -> {
                List<String> order = Arrays.asList("Spring", "Summer", "Fall", "Winter");
                return Integer.compare(order.indexOf(o1), order.indexOf(o2));
            };

            data.sort((a, b) -> {
                int yearCompare = b[1].compareTo(a[1]);
                if (yearCompare != 0) return yearCompare;
                return semesterComparator.compare(b[0], a[0]);
            });

            for (String[] parts : data) {
                String semester = parts[0];
                int year = Integer.parseInt(parts[1]);
                List<String> days = List.of(parts).subList(2, parts.length);
                officeHours.add(new OfficeHourEntry(semester, year, days));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read office hours.", e);
        }
        return officeHours;
    }
}
