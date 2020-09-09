package client.Stats;

import java.math.BigInteger;

import constants.GameConstants;
import connections.Packets.PacketProvider;

public enum MonsterStatus implements GlobalBuffStat {
    Laser("4000000000000000000"),
    DamagedElemAttr("8000000000000000"),
    AddDamSkill("40000000000000"),
    FixDamRBuff("800000000000"),
    WATK("80000000"),
    WDEF("40000000"),
    MATK("20000000"),
    MDEF("10000000"),
    ACC("8000000"),
    AVOID("4000000"),
    SPEED(22), // 1.2.316
    STUN(10), // 12
    FREEZE(11),
    MS_JaguarProvoke(40),
    POISON("400000"),
    SEAL("200000"),
    DARKNESS("100000"),
    WEAPON_ATTACK_UP("80000"),
    MAGIC_ATTACK_UP("40000"),
    WEAPON_DEFENSE_UP("20000"),
    MAGIC_DEFENSE_UP("10000"),
    WEAPON_IMMUNITY("8000"), // Force
    MAGIC_IMMUNITY("4000"), // Force
    SHADOW_WEB("2000"),
    VENOM("400"),
    WEAPON_DAMAGE_REFLECT("40"), // Reflect
    MAGIC_DAMAGE_REFLECT("20"), // Reflect
    BodyPressure("8"),
    SHOWDOWN("2"),
    MAGIC_CRASH("1"),
    Burned(90); // 1.2.316

    public static int BIT_COUNT = 128; // 32 * 3
    private BigInteger value;

    private MonsterStatus(String hex) {
        value = new BigInteger(hex, 16);
    }
    
    private MonsterStatus(int flag) {
        value = BigInteger.ONE.shiftLeft(flag);
    }
    
    public static MonsterStatus getStati(int stat1, int stat_) {
        for (MonsterStatus stat : values()) {
            if (stat.getValue(stat_) == stat1) {
                return stat;
            }
        }
        return null;
    }

    public int getValue(int s) {
        final byte[] by = PacketProvider.convertFromBigInteger(value, MonsterStatus.BIT_COUNT);
        final int stat = GameConstants.bint(by, s);
        for (int i = 31; i >= 0; i--) {
            if ((stat >>> i) == 1) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public BigInteger getBigValue() {
        return value;
    }
}
