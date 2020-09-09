package tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FileoutputUtil {

    public static final String AHT_Log = "log/Log_AntiHackTool.txt",
            커넥터로그 = "Action/LogFile/커넥터로그/" + getDCurrentTime() + ".txt",
            감지로그 = "Action/LogFile/감지로그/" + getDCurrentTime() + ".txt",
            택배로그 = "Action/LogFile/택배로그/" + getDCurrentTime() + ".txt",
            보스로그 = "Action/LogFile/보스로그/" + getDCurrentTime() + ".txt",
            마켓로그 = "Action/LogFile/마켓로그/" + getDCurrentTime() + ".txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd");

    static {
        sdfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    public static String getDCurrentTime() {
        Calendar calz = Calendar.getInstance(TimeZone.getTimeZone("KST"), Locale.KOREAN);
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleTimeFormat.format(calz.getTime());
//        if (calz.getTime().getHours() >= 12) {
//            time = "오후 "+time;
//        } else {
//            time = "오전 "+time;
//        }
        return time;
    }
    
    public static void log(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n")
                    .getBytes(Charset.forName("UTF-8")));
            out.write(msg.getBytes(Charset.forName("UTF-8")));
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    private static Object lock = new Object();

    public static void logToFile(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            synchronized (lock) {
                out = new FileOutputStream(file, true);
                out.write(("[" + CurrentReadable_Time() + "] ").getBytes(Charset.forName("UTF-8")));
                out.write(msg.getBytes(Charset.forName("UTF-8")));
                out.write(("\r\n").getBytes(Charset.forName("UTF-8")));
            }
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void logToFile_(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            synchronized (lock) {
                out = new FileOutputStream(file, true);
                out.write(msg.getBytes(Charset.forName("UTF-8")));
            }
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static void outputFileError(final String file, final Throwable t) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n")
                    .getBytes());
            out.write(getString(t).getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public static String CurrentReadable_Date() {
        return sdf_.format(Calendar.getInstance().getTime());
    }

    public static String CurrentReadable_Time() {
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String CurrentReadable_TimeGMT() {
        return sdfGMT.format(new Date());
    }

    public static String getString(final Throwable e) {
        e.printStackTrace();
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException ignore) {
            }
        }
        return retValue;
    }
}
