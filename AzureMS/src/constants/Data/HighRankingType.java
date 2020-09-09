package constants.Data;

public enum HighRankingType {
    FirstAdvance,
    SecondAdvance,
    ThirdAdvance,
    ForthAdvance;

    public int getType() {
        return ordinal();
    }
}
