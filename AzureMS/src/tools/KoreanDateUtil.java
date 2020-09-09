package tools;

import java.util.Date;
import java.util.SimpleTimeZone;

public class KoreanDateUtil {

    private final static int QUEST_UNIXAGE = 27111908;
    private final static long FT_UT_OFFSET = 116444736000000000L; // 100 nsseconds from 1/1/1601 -> 1/1/1970

    /**
     * Converts a Unix Timestamp into File Time
     *
     * @param realTimestamp The actual timestamp in milliseconds.
     * @return A 64-bit long giving a filetime timestamp
     */
    public static final long getTempBanTimestamp(final long realTimestamp) {
        // long time = (realTimestamp / 1000);//seconds
        return ((realTimestamp * 10000) + FT_UT_OFFSET);
    }

    /**
     * Gets a timestamp for item expiration.
     *
     * @param realTimestamp The actual timestamp in milliseconds.
     * @return The Korean timestamp for the real timestamp.
     */
    public static int getItemTimestamp(long realTimestamp) {
        int time = (int) ((realTimestamp - 946681229830L) / 1000L / 60L);
        return (int) (time * 35.765766999999997D) + -1085019342;
    }

    /**
     * Gets a timestamp for quest repetition.
     *
     * @param realTimestamp The actual timestamp in milliseconds.
     * @return The Korean timestamp for the real timestamp.
     */
    public static final int getQuestTimestamp(final long realTimestamp) {
        final int time = (int) (realTimestamp / 1000 / 60); // convert to minutes
        return (int) (time * 0.1396987) + QUEST_UNIXAGE;
    }

    public static boolean isDST() {
        return SimpleTimeZone.getDefault().inDaylightTime(new Date());
    }

    public static long getFileTimestamp(long timeStampinMillis, boolean roundToMinutes) {
        if (isDST()) {
            timeStampinMillis -= 3600000L;
        }
        long time;
        if (roundToMinutes) {
            time = (timeStampinMillis / 1000 / 60) * 600000000;
        } else {
            time = timeStampinMillis * 10000;
        }
        return time + FT_UT_OFFSET;
    }
}
