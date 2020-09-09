package client;

import connections.Database.MYSQL;
import client.Character.MapleCharacter;

import java.sql.*;
import java.util.Calendar;

public class MonsterPark {

    public static int dungeon;
    public static int freedungeon;
    public static int dungeonmileage;
    public static String day;

    Calendar cal = Calendar.getInstance();
    int Month = cal.get(Calendar.MONTH) + 1;

    public int getDungeonCount() {
        return dungeon;
    }

    public void setDungeonCount(int count) {
        this.dungeon = count;
    }

    public void addDungeonCount(int count) {
        this.dungeon += count;
    }

    public void loseDungeonCount(int count) {
        this.dungeon -= count;
    }

    public int getFreeDungeonCount() {
        return freedungeon;
    }

    public void setFreeDungeonCount(int count) {
        this.freedungeon = count;
    }

    public void addFreeDungeonCount(int count) {
        this.freedungeon += count;
    }

    public void loseFreeDungeonCount(int count) {
        this.freedungeon -= count;
    }

    public int getDungeonMileage() {
        return dungeonmileage;
    }

    public void setDungeonMileage(int mileage) {
        this.dungeonmileage = mileage;
    }

    public void addDungeonMileage(int mileage) {
        this.dungeonmileage += mileage;
    }

    public void loseDungeonMileage(int mileage) {
        this.dungeonmileage -= mileage;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean checkMonsterPark(int cid) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM monsterpark WHERE cid = ?");
            query.setInt(1, cid);
            result = query.executeQuery();
            if (result.next()) {
                return true;
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
        return false;
    }

    public void InsertMonsterPark(int cid, int dungeon, int freedungeon, int dungeonmileage, String day) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("INSERT INTO monsterpark(cid, dungeon, freedungeon, dungeonmileage, day) VALUES (?, ?, ?, ?, ?)", MYSQL.RETURN_GENERATED_KEYS);
            query.setInt(1, cid);
            query.setInt(2, dungeon);
            query.setInt(3, freedungeon);
            query.setInt(4, dungeonmileage);
            query.setString(5, day);
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
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMonsterPark(int cid) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM monsterpark WHERE cid = ?");
            query.setInt(1, cid);
            result = query.executeQuery();
            if (result.next()) {
                this.dungeon = result.getInt("dungeon");
                this.freedungeon = result.getInt("freedungeon");
                this.dungeonmileage = result.getInt("dungeonmileage");
                this.day = result.getString("day");
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

    public void resetMonsterPark(int cid, int dungeon, int freedungeon, int dungeonmileage, String day) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM monsterpark WHERE cid = ?");
            query.setInt(1, cid);
            result = query.executeQuery();
            if (result.next()) {
                query = connect.prepareStatement("UPDATE monsterpark SET dungeon = ?, freedungeon = ?, dungeonmileage = ?, day = ? WHERE cid = ?");
                query.setInt(1, dungeon);
                query.setInt(2, freedungeon);
                query.setInt(3, dungeonmileage);
                query.setString(4, day);
                query.setInt(5, cid);
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

    public void saveMonsterPark(int cid, int dungeon, int freedungeon, int dungeonmileage) {
        Connection connect = null;
        PreparedStatement query = null;
        ResultSet result = null;
        try {
            connect = MYSQL.getConnection();
            query = connect.prepareStatement("SELECT * FROM monsterpark WHERE cid = ?");
            query.setInt(1, cid);
            result = query.executeQuery();
            if (result.next()) {
                query = connect.prepareStatement("UPDATE monsterpark SET dungeon = ?, freedungeon = ?, dungeonmileage = ? WHERE cid = ?");
                query.setInt(1, dungeon);
                query.setInt(2, freedungeon);
                query.setInt(3, dungeonmileage);
                query.setInt(4, cid);
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

    public String MonsterParkDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String dayName = "";
        switch (day) {
            case 1:
                return dayName = "성장의 일요일";
            case 2:
                return dayName = "창조의 월요일";
            case 3:
                return dayName = "강화의 화요일";
            case 4:
                return dayName = "성향의 수요일";
            case 5:
                return dayName = "명예의 목요일";
            case 6:
                return dayName = "황금의 금요일";
            case 7:
                return dayName = "성장의 일요일";
        }
        return dayName;
    }

    public String MonsterParkReword() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String dayReword = "";
        switch (day) {
            case 1:
                return dayReword = "#b#e요일 보상 : #i2434745##z2434745##n#k";
            case 2:
                return dayReword = "#b#e요일 보상 : #i2434746##z2434746##n#k";
            case 3:
                return dayReword = "#b#e요일 보상 : #i2434747##z2434747##n#k";
            case 4:
                return dayReword = "#b#e요일 보상 : #i2434748##z2434748##n#k";
            case 5:
                return dayReword = "#b#e요일 보상 : #i2434749##z2434749##n#k";
            case 6:
                return dayReword = "#b#e요일 보상 : #i2434750##z2434750##n#k";
            case 7:
                return dayReword = "#b#e요일 보상 : #i2434751##z2434751##n#k";
        }
        return dayReword;
    }

    public void gainMonsterParkReword(MapleCharacter player) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                player.gainItem(2434745, 1);
                break;
            case 2:
                player.gainItem(2434746, 1);
                break;
            case 3:
                player.gainItem(2434747, 1);
                break;
            case 4:
                player.gainItem(2434748, 1);
                break;
            case 5:
                player.gainItem(2434749, 1);
                break;
            case 6:
                player.gainItem(2434750, 1);
                break;
            case 7:
                player.gainItem(2434751, 1);
                break;
        }
    }
}
