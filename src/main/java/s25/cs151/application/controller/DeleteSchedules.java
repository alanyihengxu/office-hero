package s25.cs151.application.controller;

import s25.cs151.application.model.entry.AppointmentEntry;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class DeleteSchedules {

    public void deleteSearch(String filepath, AppointmentEntry toRemove){
        String tempFile = "temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);

        String name = ""; String date = "";
        String timeSlot = "" ; String course = "";
        String reason = ""; String comment = "";

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            Scanner findName = new Scanner(new File(filepath));

            while(findName.hasNext()) {
                String line = findName.nextLine();
                String[] content = line.split(",");

                if(content.length >= 6) {
                    name = content[0];
                    date = content[1];
                    timeSlot = content[2];
                    course = content[3];
                    reason = content[4];
                    comment = content[5];
                }

                if(!(name.equals(toRemove.getName()) && date.equals(toRemove.getDate()) && timeSlot.equals(toRemove.getTimeSlot()) && course.equals(toRemove.getCourse()) && reason.equals(toRemove.getReason()) && comment.equals(toRemove.getComment()))){
                    pw.println(name + "," + date + "," + timeSlot + ","
                                + course + "," + reason + "," + comment);
                }
            }
            findName.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"FileNotFound");
        }
    }
}
