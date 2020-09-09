package client.Stats;

public enum PlayerStatList {
    SKIN(0x1),
    FACE(0x2),
    HAIR(0x4),
    LEVEL(0x10),
    JOB(0x20),
    STR(0x40),
    DEX(0x80),
    INT(0x100),
    LUK(0x200),
    HP(0x400),
    MAXHP(0x800),
    MP(0x1000),
    MAXMP(0x2000),
    AVAILABLEAP(0x4000),
    AVAILABLESP(0x8000),
    EXP(0x10000),
    FAME(0x20000),
    MESO(0x40000),
    HYPER_STR(0x40000),
    FATIGUE(0x80000),
    CHARISMA(0x100000),
    INSIGHT(0x200000),
    WILLPOWER(0x400000),
    WILL(0x400000),
    CRAFT(0x800000),
    SENSE(0x1000000),
    CHARM(0x2000000),
    TRAIT_LIMIT(0x4000000),
    PROFESSIONAL_SKILLS(0x4100000),
    PET(0x180008),
    SAINT_SABER(0x80000000);

    private final int i;

    private PlayerStatList(int i) {
        this.i = i;
    }

    public int getValue() {
        return i;
    }

    public static final PlayerStatList getByValue(final int value) {
        for (final PlayerStatList stat : PlayerStatList.values()) {
            if (stat.i == value) {
                return stat;
            }
        }
        return null;
    }
}
