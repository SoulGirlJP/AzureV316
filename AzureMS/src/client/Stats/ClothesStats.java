package client.Stats;

public enum ClothesStats {
    a(0x1, 3),
    b(0x2, 1),
    c(0x4, 2),
    d(0x8, 4),
    e(0x10, 9),
    f(0x20, 5),
    g(0x40, 8),
    h(0x80, 11),
    i(0x100, 6),
    j(0x200, 7),
    k(0x400, 12),
    l(0x800, 13),;

    private final int value, order;

    private ClothesStats(int value, int order) {
        this.value = value;
        this.order = order;
    }

    public static int getValueByOrder(int order) {
        for (ClothesStats cs : ClothesStats.values()) {
            if (cs.order == order) {
                return cs.value;
            }
        }
        return 0;
    }

}
