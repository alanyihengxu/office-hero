package s25.cs151.application;
import java.sql.SQLOutput;
import java.util.*;
import java.io.*;
import java.util.Comparator;
import java.util.Collections;


public class EntrySort {

    public static List<OfficeHourEntry> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        List<List<String>> realList = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = in.readLine()) != null) {
                data.add(line.split(","));
                String[] days = line.split(",");
                List<String> x = new ArrayList<>();
                for (int i = 2; i < days.length; i++) {
                    x.add(days[i]);
                }
                realList.add(x);
                //for (int i = 2; i < days.length; i++) {
                //    data.add(3, realList.get(i));
                //}

            }

            Comparator<String> customComparator = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
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
                }
            };
            //Compares first by year then by semester
            data.sort((a, b) -> {
                int yearCompare = a[1].compareTo(b[1]);
                if (yearCompare != 0) {
                    {
                        return yearCompare;
                    }

                }
                return customComparator.compare(a[0], b[0]);
            });

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        List<OfficeHourEntry> x = new ArrayList<>();




        for (int i = 0; i < data.size(); i++) {
            x.add(new OfficeHourEntry(data.get(i)[0], Integer.valueOf(data.get(i)[1]), realList.get(i)));
        }
        return x;
    }

    public static void addSortedData(List<OfficeHourEntry> l1) {
        File file = new File("data/office_hours.csv");
        // Append the new entry to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for(int i = 0; i < l1.size(); i++) {
                writer.write(l1.get(i).toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


