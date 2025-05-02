package s25.cs151.application.controller;
import s25.cs151.application.model.entry.AppointmentEntry;
import s25.cs151.application.model.entry.CourseEntry;
import s25.cs151.application.model.entry.OfficeHourEntry;
import s25.cs151.application.model.entry.TimeSlotEntry;

import java.util.*;
import java.io.*;
import java.util.Comparator;


public class EntrySort {
    /**
     * This method is used to help organized the stored data in the csv file,
     * it reparses the data and the uses its own comparator to sort it and
     * override the csv file with the sorted data
     *
     * @param: String (filename)
     * @return: List<OfficeHourEntry>
     *
     */

    public static List<OfficeHourEntry> readOfficeHourCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        //Parsing data from csv file and adding it to a list of String[]
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = in.readLine()) != null) {
                data.add(line.split(","));
            }

            //Setting new natural order for String for Semester

            Comparator<String> customComparator =   (o1, o2) -> {
                //Spring is the 1st semester (no need to swap)
                if ("Spring".equals(o1)) {
                    return -1;
                }
                else if ("Spring".equals(o2)) {
                    return 1;
                }
                else if ("Fall".equals(o1) && "Winter".equals(o2)) {
                    return -1;
                }
                else if ("Winter".equals(o1) && "Fall".equals(o2)) {
                    return 1;
                }
                else if ("Fall".equals(o1) && "Summer".equals(o2)) {
                    return 1;
                }
                else if ("Summer".equals(o1) && "Fall".equals(o2)) {
                    return -1;
                }
                else if ("Winter".equals(o1) && "Summer".equals(o2)) {
                    return 1;
                }
                else if ("Summer".equals(o1) && "Winter".equals(o2)) {
                    return -1;
                }
                else{
                    System.out.println("Wrong Iput added");
                }

                return 0;
            };
            //Compares first by year then by semester
            data.sort((a, b) -> {
                int yearCompare = b[1].compareTo(a[1]);
                if (yearCompare != 0) {
                    {
                        return yearCompare;
                    }

                }
                return customComparator.compare(b[0], a[0]);
            });

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //Reobtaining data from the List that has already been sorted
        //Adding that data to a List of List of Strings to be able to
        //Grab the index of the list giving  a list of Strings
        int loop = 0;
        List<List<String>> realList2 = new ArrayList<>();
        for(int j = 2; loop < data.size(); j++){
            String[] days = new String[data.get(loop).length - 2];
            for(int o = 0; o < days.length; o++ ) {
                days[o] = data.get(loop)[j];
                j++;
            }
            List<String> y = new ArrayList<>();
            for (String day : days) {
                y.add(day);
                j--;
            }

            realList2.add(y);
            loop++;
            j--;
        }

        List<OfficeHourEntry> x = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            x.add(new OfficeHourEntry(data.get(i)[0], Integer.parseInt(data.get(i)[1]), realList2.get(i)));
        }
        return x;
    }

    /**
     * This method is used to read and sort the stored course data in the csv file,
     *
     *
     * @param: String (filename)
     * @return: List<CourseEntry>
     *
     */
    public static List<CourseEntry> readCourseCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        //Parsing data from csv file and adding it to a list of String[]
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = in.readLine()) != null) {
                data.add(line.split(","));
            }

            //Compares by course code
            data.sort((a, b) -> b[0].compareTo(a[0]));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        List<CourseEntry> x = new ArrayList<>();

        for (String[] entry : data) {
            x.add(new CourseEntry(entry[0], entry[1], entry[2]));
        }
        return x;
    }

    /**
     * This method is used to read and sort the stored time slot data in the csv file,
     *
     *
     * @param: String (filename)
     * @return: List<TimeSlotEntry>
     *
     */
    public static List<TimeSlotEntry> readTimeSlotCSV(String filePath) {
        List<TimeSlotEntry> data = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = in.readLine()) != null) {
                TimeSlotEntry entry = TimeSlotEntry.fromCSV(line);
                data.add(entry);
            }

            // Sort ascending by fromHour → fromMinute → toHour → toMinute
            data.sort(Comparator
                    .comparingInt(TimeSlotEntry::getFromHour)
                    .thenComparingInt(TimeSlotEntry::getFromMinute)
                    .thenComparingInt(TimeSlotEntry::getToHour)
                    .thenComparingInt(TimeSlotEntry::getToMinute)
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    /**
     * This method is used to read and sort the stored appointment data in the csv file,
     *
     *
     * @param: String (filename)
     * @return: List<AppointmentEntry>
     *
     */
    public static List<AppointmentEntry> readAppointmentCSV(String filePath) {
        List<AppointmentEntry> data = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = in.readLine()) != null) {
                data.add(AppointmentEntry.fromCSV(line));
            }

            data.sort(Comparator
                    .comparing(AppointmentEntry::getDate)
                    .thenComparing((a1,a2)->{
                        TimeSlotEntry t1 = TimeSlotEntry.fromString(a1.getTimeSlot());
                        TimeSlotEntry t2 = TimeSlotEntry.fromString(a2.getTimeSlot());

                            int comparison = Integer.compare(t1.getFromHour(), t2.getFromHour());
                            if (comparison != 0) { return comparison;}

                            comparison = Integer.compare(t1.getFromMinute(), t2.getFromMinute());
                            if (comparison != 0) { return comparison;}

                            comparison = Integer.compare(t1.getFromHour(), t2.getFromHour());
                            if (comparison != 0) { return comparison;}

                            return Integer.compare(t1.getToMinute(), t2.getToMinute());
            })

            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to read or sort appointments.", e);
        }

        return data;
    }

    /**
     * Takes the AppointmentEntry List from method above and output it to the
     * existing csv file
     *
     * @param: List<AppointmentEntry>
     * @return: void
     *
     */
    public static void addSortedAppointmentData(List<AppointmentEntry> list) {
        File file = new File("data/appointments.csv");
        //Append the new entry to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (AppointmentEntry entry : list) {
                writer.write(entry.toCSV() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Takes the OfficeHourEntry List from method above and output it to the
     * existing csv file
     *
     * @param: List<OfficeHourEntry>
     * @return: void
     *
     */
    public static void addSortedOfficeHourData(List<OfficeHourEntry> l1) {
        File file = new File("data/office_hours.csv");
        // Append the new entry to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (OfficeHourEntry officeHourEntry : l1) {
                writer.write(officeHourEntry.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the CourseEntry List from readCourseCSV and writes to the
     * existing csv file
     *
     * @param: List<CourseEntry>
     * @return: void
     *
     */
    public static void addSortedCourseData(List<CourseEntry> l1) {
        File file = new File("data/courses.csv");
        // Append the new entry to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (CourseEntry courseEntry : l1) {
                writer.write(courseEntry.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the TimeSlotEntry List from readTimeSlotsCSV and writes to the
     * existing csv file
     *
     * @param: List<TimeSlotEntry>
     * @return: void
     *
     */
    public static void addSortedTimeSlotData(List<TimeSlotEntry> list) {
        File file = new File("data/semester_time_slots.csv");
        //Append the new entry to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (TimeSlotEntry timeSlotEntry : list) {
                writer.write(timeSlotEntry.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing sorted TimeSlot data", e);
        }
    }
}


