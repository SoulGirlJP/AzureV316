package constants.Programs;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class LoadLine {

    public static void main(String[] args) {
        System.out.println("RewardScroll Loading.:::");
        try {
            FileReader fl = new FileReader("db.sql");
            BufferedReader br = new BufferedReader(fl);
            String[] readSplit = new String[2];
            String[] readSplit2 = new String[42];
            String readLine = null;
            FileOutputStream mhair = new FileOutputStream("db_.sql");
            // mhair.write((br.toString().replaceAll("\\),", "\\),\r\n")).getBytes());
            int i = 46568;
            while ((readLine = br.readLine()) != null) {
                readSplit = readLine.split("\\(");
                readSplit2 = readSplit[1].split(",");
                mhair.write((readLine.replaceFirst(readSplit2[0], "" + i) + "\r\n").getBytes());
                i++;
                // mhair.write((readLine.replaceAll("\\),", "\\),\r\n")).getBytes());
            }
            fl.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
