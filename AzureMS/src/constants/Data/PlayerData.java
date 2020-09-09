package constants.Data;

public class PlayerData implements Comparable<PlayerData> {

    private int points;
    private String info;

    public PlayerData(int points, String info) {
        this.points = points;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public int getPoints() {
        return points;
    }

    public int compareTo(PlayerData o) {
        int thisVal = getPoints();
        int anotherVal = o.getPoints();
        return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }
}
