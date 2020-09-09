package tools;

import java.util.Calendar;

public class ExpirationTool {

    private static Calendar cal = Calendar.getInstance();

    /**
     * 현재 시간에 <code>minute</code>분 만큼의 시간을 더합니다.
     *
     * @param minute 더할 분
     * @return 더해진 시간
     */
    public static long getExpirationM(long minute) {
        return System.currentTimeMillis() + (minute * 60 * 1000);
    }

    /**
     * 현재 시간에 <code>hour</code>시간 만큼의 시간을 더합니다.
     *
     * @param hour 더할 시간
     * @return 더해진 시간
     */
    public static long getExpirationH(long hour) {
        return getExpirationH(hour, false);
    }

    /**
     * 현재 시간에 <code>hour</code>시간 만큼의 시간을 더하고 <code>deafults</code>가 true일 경우 그
     * 이하의 시간을 초기화 합니다. <br>
     * 예를 들어 hour가 2일 경우 초기화 전 : 2010/2/12 12:52:48 초기화 후 : 2010/2/12 14:00:00
     * </br>
     *
     * @param hour 더할 시간
     * @param deafults 초기화 여부
     * @return 더해진 시간
     */
    public static long getExpirationH(long hour, boolean deafults) {
        return deafults
                ? (System.currentTimeMillis() + hour * 60 * 60 * 1000)
                - (((60 * cal.get(Calendar.MINUTE)) + cal.get(Calendar.SECOND)) * 1000)
                : System.currentTimeMillis() + hour * 60 * 60 * 1000;
    }

    /**
     * 현재 시간에 <code>day</code>일 만큼의 시간을 더합니다.
     *
     * @param day 더할 일
     * @return 더해진 시간
     */
    public static long getExpirationD(long day) {
        return getExpirationD(day, false);
    }

    /**
     * 현재 시간에 <code>day</code>일 만큼의 시간을 더하고 <code>deafults</code>가 true일 경우 그
     * 이하의 시간을 초기화 합니다. <br>
     * 예를 들어 day가 1일 경우 초기화 전 : 2010/2/12 12:52:48 초기화 후 : 2010/2/13 00:00:00
     * </br>
     *
     * @param hour 더할 시간
     * @param deafults 초기화 여부
     * @return 더해진 시간
     */
    public static long getExpirationD(long day, boolean deafults) {
        return deafults
                ? (System.currentTimeMillis() + day * 24 * 60 * 60 * 1000) - (((60 * 60 * cal.get(Calendar.HOUR_OF_DAY))
                + (60 * cal.get(Calendar.MINUTE)) + (cal.get(Calendar.SECOND))) * 1000)
                : System.currentTimeMillis() + (day * (24 * 60 * 60 * 1000));
    }

    /**
     * 현재 시간에 <code>hour</code>시간 <code>minute</code>분 만큼의 시간을 더합니다. 단, 초기화는 할 수
     * 없습니다.
     *
     * @param hour 더할 시간
     * @param minute 더할 분
     * @return 더해진 시간
     * @see getExpirationH
     */
    public static long getExpirationHM(long hour, long minute) {
        return System.currentTimeMillis() + ((60 * hour + minute) * 60 * 1000);
    }

    /**
     * 현재 시간에 <code>day</code>일 <code>hour</code>시간 만큼의 시간을 더합니다. 단, 초기화는 할 수
     * 없습니다.
     *
     * @param day 더할 시간
     * @param hour 더할 분
     * @return 더해진 시간
     * @see getExpirationD
     */
    public static long getExpirationDH(long day, long hour) {
        return System.currentTimeMillis() + ((24 * 60 * day + 60 * hour) * 60 * 1000);
    }

    /**
     * 현재 시간에 <code>day</code>일 <code>hour</code>시간 <code>minute</code>분 만큼의 시간을
     * 더합니다. 단, 초기화는 할 수 없습니다.
     *
     * @param day 더할 일
     * @param hour 더할 시간
     * @param minute 더할 분
     * @return 더해진 시간
     * @see getExpirationD
     */
    public static long getExpirationDHM(long day, long hour, long minute) {
        return System.currentTimeMillis() + ((24 * 60 * day + 60 * hour + minute) * 60 * 1000);
    }
}
