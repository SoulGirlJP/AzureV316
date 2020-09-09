package client.Stats;

import java.math.BigInteger;

public enum DiseaseStats implements GlobalBuffStat {
    STUN(82), // OK
    POISON(83), // OK
    SEAL(84), // OK
    DARKNESS(85), // OK
    WEAKEN(97), // OK
    CURSE(98), // OK
    SLOW(99), // O
    SEDUCE(106), // OK
    BLIND(111), // OK
    REVERSE_DIRECTION(120), // OK
    POTION(140), // OK
    SHADOW(141), // OK
    FREEZE(148), // OK
    TELEPORT(311), // OK
    ZOMBIFY(601); // OK

    private BigInteger value;

    private DiseaseStats(String hex) {
        value = new BigInteger(hex, 16);
    }

    private DiseaseStats(int flag) {
        this.value = BigInteger.ONE.shiftLeft((flag / 32) * 32 + 0x1F - (flag & 0x1F));
    }

    @Override
    public BigInteger getBigValue() {
        return value;
    }
}
