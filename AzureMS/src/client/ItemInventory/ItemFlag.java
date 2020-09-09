package client.ItemInventory;

public enum ItemFlag {

    LOCK(0x01),
    SPIKES(0x02),
    COLD(0x04),
    UNTRADEABLE(0x08),
    KARMA_EQ(0x10),
    KARMA_USE(0x20),
    CHARM_EQUIPPED(0x20),
    ANDROID_ACTIVATED(0x40),
    CRAFT(0x80),
    PROTECT(0x100), // Protect
    LUKCYDAY(0x200), // Lucky Day
    TRADE_ON_ACCOUNT_USE(0x400), // Ax Karma
    TRADE_ON_ACCOUNT(0x1000), // Ax Karma
    KARMA_ACC_USE(0x400),
    KARMA_ACC(0x1000),
    SAFETY(0x2000), // Safety
    RECOVERY(0x4000); // Recovery
    ;
	private final int i;

    private ItemFlag(int i) {
        this.i = i;
    }

    public final int getValue() {
        return i;
    }

    public final boolean check(int flag) {
        return (flag & i) == i;
    }
}
