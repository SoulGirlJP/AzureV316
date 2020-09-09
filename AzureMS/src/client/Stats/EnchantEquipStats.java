package client.Stats;

public enum EnchantEquipStats {
    WATK(0x1),
    MATK(0x2),
    STR(0x4),
    DEX(0x8),
    INT(0x10),
    LUK(0x20),
    WDEF(0x40),
    MDEF(0x80),
    HP(0x100),
    MP(0x200),
    ACC(0x400),
    AVOID(0x800),
    JUMP(0x1000),
    SPEED(0x2000);

    private final int i;

    private EnchantEquipStats(int i) {
        this.i = i;
    }

    public int getValue() {
        return this.i;
    }

    public static final EnchantEquipStats getByValue(int value) {
        for (EnchantEquipStats stat : EnchantEquipStats.values()) {
            if (stat.i == value) {
                return stat;
            }
        }
        return null;
    }
}
