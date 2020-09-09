package client;

import connections.Database.MYSQL;

import java.sql.*;
import java.util.Calendar;

public class DailyGift {

    private int dailyCount;
    private int dailyDay;
    private String data;

    Calendar cal = Calendar.getInstance();
    int Month = cal.get(Calendar.MONTH) + 1;

    public int getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(int count) {
        this.dailyCount = count;
    }

    public int getDailyDay() {
        return dailyDay;
    }

    public void setDailyDay(int day) {
        this.dailyDay = day;
    }

    public String getDailyData() {
        return data;
    }

    public void setDailyData(String data) {
        this.data = data;
    }

    public boolean checkDailyGift(int accountid) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM dailyGift WHERE accountid = ?");
            query.setInt(1, accountid);
            result = query.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
                if (query != null) {
                    query.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public void InsertDailyData(int accountid, int dailyDay, int dailyCount) {
        Connection connect = null;
        PreparedStatement query = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("INSERT INTO dailyGift(accountid, dailyday, dailycount) VALUES (?, ?, ?)", MYSQL.RETURN_GENERATED_KEYS);
            query.setInt(1, accountid);
            query.setInt(2, dailyDay);
            query.setInt(3, dailyCount);
            query.executeUpdate();
            query.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDailyGift(int accountid) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM dailyGift WHERE accountid = ?");
            query.setInt(1, accountid);
            result = query.executeQuery();
            if (result.next()) {
                this.dailyDay = result.getInt("dailyday");
                this.dailyCount = result.getInt("dailycount");
                this.data = result.getString("dailyData");
            }
            result.close();
            query.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
                if (query != null) {
                    query.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetDailyGift(int accountid) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM dailyGift WHERE accountid = ?");
            query.setInt(1, accountid);
            result = query.executeQuery();
            if (result.next()) {
                query = connect.prepareStatement("UPDATE dailyGift SET dailyday = ?, dailyCount = ?, dailyData = ? WHERE accountid = ?");
                query.setInt(1, getDailyDay());
                query.setInt(2, 0);
                query.setString(3, cal.get(Calendar.YEAR) + (Month == 10 || Month == 11 || Month == 12 ? "" : "0") + Month + (cal.get(Calendar.DATE) < 10 ? "0" : "") + cal.get(Calendar.DATE));
                query.setInt(4, accountid);
                query.executeUpdate();
            }
            result.close();
            query.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
                if (query != null) {
                    query.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDailyGift(int accountid, int dailyDay, int dailyCount, String dailyData) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM dailyGift WHERE accountid = ?");
            query.setInt(1, accountid);
            result = query.executeQuery();
            if (result.next()) {
                query = connect.prepareStatement("UPDATE dailyGift SET dailyday = ?, dailycount = ?, dailyData = ? WHERE accountid = ?");
                query.setInt(1, dailyDay);
                query.setInt(2, dailyCount);
                query.setString(3, dailyData);
                query.setInt(4, accountid);
                query.executeUpdate();
            }
            result.close();
            query.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
                if (query != null) {
                    query.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
