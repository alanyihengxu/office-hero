package s25.cs151.application.controller.sort;

import s25.cs151.application.model.CourseEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CourseEntrySort implements EntrySort<CourseEntry> {

    @Override
    public List<CourseEntry> readAndSort(String filepath) {
        List<CourseEntry> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                courses.add(CourseEntry.fromCSV(line));
            }
            courses.sort(Comparator.comparing(CourseEntry::getCourseCode));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read courses.", e);
        }
        return courses;
    }
}
