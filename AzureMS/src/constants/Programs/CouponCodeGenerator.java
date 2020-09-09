package constants.Programs;

import java.io.Console;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connections.Database.MYSQL;
import tools.RandomStream.Randomizer;

public class CouponCodeGenerator {

    public static void main(String[] args) throws SQLException {
        Connection con = MYSQL.getConnection();
        Console c = System.console();

        String temp = "Maple";
        do {
            System.out.println("Coupon Type: 1-Nexon Cash, 2-Maple Point, 3-Meso");
            temp = c.readLine("Coupon type? = ");
            if (!(temp.equals("1") || temp.equals("2") || temp.equals("3"))) {
                continue;
            }
        } while (hasNumberFormatException(temp));
        final int type = Integer.parseInt(temp);

        temp = "Maple";
        do {
            temp = c.readLine("How much payment? = ");
        } while (hasNumberFormatException(temp));
        final int amount = Integer.parseInt(temp);

        temp = "Maple";
        do {
            temp = c.readLine("The number to make? = ");
        } while (hasNumberFormatException(temp));
        final int howMany = Integer.parseInt(temp);

        temp = "Maple";
        do {
            temp = c.readLine("Possible duplicates = ");
        } while (hasNumberFormatException(temp));
        final int canValid = Integer.parseInt(temp);

        final String format = String.format("%dCreate %dredeamable coupons %dtimes for %d caches. Is that correct?", type, amount,
                canValid, howMany);
        if (!c.readLine(format + " (y/n) = ").equals("n")) {
            int number = howMany;
            PreparedStatement ps = null;
            while (number > 0) {
                String code = randCouponCode();
                try {
                    ps = con.prepareStatement("INSERT INTO nxcode (`code`, valid, `type`, `item`) VALUES (?, ?, ?, ?)");
                    ps.setString(1, code);
                    ps.setInt(2, canValid);
                    ps.setInt(3, type);
                    ps.setInt(4, amount);
                    ps.executeUpdate();
                    printCouponInfo(code, amount, type, canValid, null);
                    number--;
                    System.out.println(code);
                } catch (SQLException ex) {
                    System.err.println("[Error] " + code + " : " + ex);
                    continue;
                }
            }
        } else {
            main(args);
        }
    }

    private static String randCouponCode() {
        final String part = "12345QWERTYUIOPASDFGHJKLZXCVBNM67890";
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 30) {
            sb.append(Character.toString(part.charAt(Randomizer.nextInt(part.length()))));
        }
        return sb.toString();
    }

    public static String printCouponInfo(String code, int item, int type, int valid, String user) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(code).append(" | ");
            sb.append(item).append(" ").append(type == 1 ? "Nexon Cash" : type == 2 ? "Maple Point" : "Meso").append(" | ");
            sb.append(valid).append("Available");
            sb.append("\r\n");
            FileOutputStream out = new FileOutputStream("ÄíÆù ÄÚµå.txt", true);
            out.write(sb.toString().getBytes());
            out.close();
            return sb.toString();
        } catch (Exception ex) {
        }
        return "Information does not exist";
    }

    private static boolean hasNumberFormatException(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }
}
