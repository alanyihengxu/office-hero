package s25.cs151.application;
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

            Comparator<String> customComparator = (o1, o2) -> {
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
}


