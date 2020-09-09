package constants.Programs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class CommodityIndex {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fr;
        String text_ = "";
        fr = new FileReader("wz/Etc.wz/Commodity.img.xml");
        BufferedReader buffer = new BufferedReader(fr);
        int i = 0;
        FileOutputStream fos = new FileOutputStream(new File("wz/Commodity_.img.xml"), false);
        while (text_ != null) {
            text_ = buffer.readLine();
            if (text_ != null) {
                if (text_.contains("<imgdir name=\"") && !text_.contains("<imgdir name=\"Commodity.img\">")) {
                    System.out.println("INDEX OF " + i);
                    text_ = "   <imgdir name=\"" + i + "\">";
                    i++;
                }
                fos.write((text_ + "\r\n").getBytes());
            }
        }
        buffer.close();
        fr.close();
        fos.close();
        System.out.println("Cash Shop Conversion is complete.");
    }
}
