package server.Maps.MapSummon;

public enum SummonMovementType {
    STATIONARY(0), FOLLOW(1), WALK_STATIONARY(2),
    BIRD_FOLLOW(3),
    CIRCLE_FOLLOW(4), CIRCLE_STATIONARY(5), ZEROWEAPON(6),
    FLAME_SUMMON(7), SHADOW_SERVANT(8),
    SUMMON_JAGUAR(11), ±Í¹®Áø(13), ILIUME_CRISTAL(14), WALK_FOLLOW(16);

    private final int val;

    private SummonMovementType(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }
}
