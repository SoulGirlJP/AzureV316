package server.Maps.MapField;

public enum FieldLimitType {

    Jump(0x1), MovementSkills(0x2), SummoningBag(0x04), MysticDoor(0x08), ChannelSwitch(0x10), RegularExpLoss(
            0x20), VipRock(0x40), Minigames(0x80), NoClue1(0x100),
    Mount(0x200), NoClue2(0x400),
    NoClue3(0x800),
    PotionUse(0x1000), NoClue4(0x2000),
    Unused(0x4000), NoClue5(0x8000),
    NoClue6(0x10000),
    DropDown(0x20000),
    ;
    private final int i;

    private FieldLimitType(int i) {
        this.i = i;
    }

    public final int getValue() {
        return i;
    }

    public final boolean check(int fieldlimit) {
        return (fieldlimit & i) == i;
    }
}
