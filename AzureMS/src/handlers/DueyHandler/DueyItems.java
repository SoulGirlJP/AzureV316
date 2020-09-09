package handlers.DueyHandler;

import tools.RandomStream.Randomizer;

public class DueyItems {

    private byte upgradeSlots = 0, level = 0, vicioushammer = 0, itemLevel = 0, enhance = 0, state = 0, lines = 0,
            fire = -1, bossdmg = 0, alldmgp = 0, allstatp = 0, downlevel = 0;
    private short str = 0, dex = 0, _int = 0, luk = 0, hp = 0, mp = 0, watk = 0, matk = 0, wdef = 0, mdef = 0, acc = 0,
            avoid = 0, hands = 0, speed = 0, jump = 0, hpR = 0, mpR = 0, IgnoreWdef = 0, soulenchanter = 0,
            soulname = 0;
    private int soulskill = 0;
    private int job = 0, potential = 0, durability = -1, potential1 = 0, potential2 = 0, potential3 = 0, potential4 = 0,
            potential5 = 0, potential6 = 0, potential7 = 0, anvil = 0, itemEXP = 0;
    private boolean amazing = false;
    private int inventoryitemid = 0;
    private short quantity = 0;

    public DueyItems() {
        super();
        upgradeSlots = upgradeSlots;
        level = level;
        vicioushammer = vicioushammer;
        itemLevel = itemLevel;
        enhance = enhance;
        state = state;
        lines = lines;
        fire = fire;
        bossdmg = bossdmg;
        alldmgp = alldmgp;
        allstatp = allstatp;
        downlevel = downlevel;
        str = str;
        dex = dex;
        _int = _int;
        luk = luk;
        hp = hp;
        mp = mp;
        watk = watk;
        matk = matk;
        wdef = wdef;
        mdef = mdef;
        acc = acc;
        avoid = avoid;
        hands = hands;
        speed = speed;
        jump = jump;
        hpR = hpR;
        mpR = mpR;
        IgnoreWdef = IgnoreWdef;
        soulenchanter = soulenchanter;
        soulname = soulname;
        soulskill = soulskill;
        job = job;
        potential = potential;
        durability = durability;
        potential1 = potential1;
        potential2 = potential2;
        potential3 = potential3;
        potential4 = potential4;
        potential5 = potential5;
        potential6 = potential6;
        potential7 = potential7;
        anvil = anvil;
        itemEXP = itemEXP;
        amazing = amazing;
        inventoryitemid = inventoryitemid;
        quantity = quantity;
    }

    public byte getType() {
        return 1;
    }

    public byte getUpgradeSlots() {
        return upgradeSlots;
    }

    public short getStr() {
        return str;
    }

    public final int getSpirit() {
        return this.inventoryitemid;
    }

    public final void setSpirits(int i) {
        this.inventoryitemid = i;
    }

    public short getDex() {
        return dex;
    }

    public short getInt() {
        return _int;
    }

    public short getLuk() {
        return luk;
    }

    public short getHp() {
        return hp;
    }

    public short getMp() {
        return mp;
    }

    public short getWatk() {
        return watk;
    }

    public short getMatk() {
        return matk;
    }

    public short getWdef() {
        return wdef;
    }

    public short getMdef() {
        return mdef;
    }

    public short getAcc() {
        return acc;
    }

    public short getAvoid() {
        return avoid;
    }

    public short getHands() {
        return hands;
    }

    public short getSpeed() {
        return speed;
    }

    public short getJump() {
        return jump;
    }

    public int getJob() {
        return job;
    }

    public void setStr(short str) {
        if (str < 0) {
            str = 0;
        }
        this.str = str;
    }

    public void addStr(short str) {
        if (str < 0) {
            str = 0;
        }
        this.str += str;
    }

    public void setDex(short dex) {
        if (dex < 0) {
            dex = 0;
        }
        this.dex = dex;
    }

    public void addDex(short dex) {
        if (dex < 0) {
            dex = 0;
        }
        this.dex += dex;
    }

    public void setInt(short _int) {
        if (_int < 0) {
            _int = 0;
        }
        this._int = _int;
    }

    public void setLuk(short luk) {
        if (luk < 0) {
            luk = 0;
        }
        this.luk = luk;
    }

    public void setHp(short hp) {
        if (hp < 0) {
            hp = 0;
        }
        this.hp = hp;
    }

    public void setMp(short mp) {
        if (mp < 0) {
            mp = 0;
        }
        this.mp = mp;
    }

    public void setWatk(short watk) {
        if (watk < 0) {
            watk = 0;
        }
        this.watk = watk;
    }

    public void addWatk(short watk) {
        if (watk < 0) {
            watk = 0;
        }
        this.watk += watk;
    }

    public void setMatk(short matk) {
        if (matk < 0) {
            matk = 0;
        }
        this.matk = matk;
    }

    public void setWdef(short wdef) {
        if (wdef < 0) {
            wdef = 0;
        } else if (wdef > 255) {
            wdef = 255;
        }
        this.wdef = wdef;
    }

    public void addWdef(short wdef) {
        if (wdef < 0) {
            wdef = 0;
        } else if (wdef > 255) {
            wdef = 255;
        }
        this.wdef += wdef;
    }

    public void setMdef(short mdef) {
        if (mdef < 0) {
            mdef = 0;
        } else if (mdef > 255) {
            mdef = 255;
        }
        this.mdef = mdef;
    }

    public void addMdef(short mdef) {
        if (mdef < 0) {
            mdef = 0;
        } else if (mdef > 255) {
            mdef = 255;
        }
        this.mdef += mdef;
    }

    public void setAcc(short acc) {
        if (acc < 0) {
            acc = 0;
        }
        this.acc = acc;
    }

    public void addAcc(short acc) {
        if (acc < 0) {
            acc = 0;
        }
        this.acc += acc;
    }

    public void setAvoid(short avoid) {
        if (avoid < 0) {
            avoid = 0;
        }
        this.avoid = avoid;
    }

    public void setHands(short hands) {
        if (hands < 0) {
            hands = 0;
        }
        this.hands = hands;
    }

    public void setSpeed(short speed) {
        if (speed < 0) {
            speed = 0;
        }
        this.speed = speed;
    }

    public void setJump(short jump) {
        if (jump < 0) {
            jump = 0;
        }
        this.jump = jump;
    }

    public void setUpgradeSlots(byte upgradeSlots) {
        this.upgradeSlots = upgradeSlots;
    }

    public void addUpgradeSlots(byte upgradeSlots) {
        this.upgradeSlots += upgradeSlots;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public byte getViciousHammer() {
        return vicioushammer;
    }

    public void setViciousHammer(byte ham) {
        vicioushammer = ham;
    }

    public byte getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(byte itemLevel) {
        if (itemLevel < 0) {
            itemLevel = 0;
        }
        this.itemLevel = itemLevel;
    }

    public int getItemEXP() {
        return itemEXP;
    }

    public void setItemEXP(int itemEXP) {
        if (itemEXP < 0) {
            itemEXP = 0;
        }
        this.itemEXP = itemEXP;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(final int dur) {
        this.durability = dur;
    }

    public byte getEnhance() {
        return enhance;
    }

    public void setEnhance(final byte en) {
        this.enhance = en;
    }

    public int getPotential1() {
        return potential1;
    }

    public void setPotential1(final int en) {
        this.potential1 = en;
    }

    public int getPotential2() {
        return potential2;
    }

    public void setPotential2(final int en) {
        this.potential2 = en;
    }

    public int getPotential3() {
        return potential3;
    }

    public void setPotential3(final int en) {
        this.potential3 = en;
    }

    public int getPotential4() {
        return potential4;
    }

    public void setPotential4(final int en) {
        this.potential4 = en;
    }

    public int getPotential5() {
        return potential5;
    }

    public void setPotential5(final int en) {
        this.potential5 = en;
    }

    public int getPotential6() {
        return potential6;
    }

    public void setPotential6(final int en) {
        this.potential6 = en;
    }

    public int getPotential7() {
        return potential7;
    }

    public void setPotential7(final int en) {
        this.potential7 = en;
    }

    public int getanvil() {
        return anvil;
    }

    public void setanvil(final int en) {
        this.anvil = en;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public byte getLines() {
        return lines;
    }

    public void setLines(byte lines) {
        this.lines = lines;
    }

    public byte getFire() {
        return fire;
    }

    public void setFire(byte fire) {
        this.fire = fire;
    }

    public void renewPotential(boolean master) {
        int epic = master ? 7 : 5;
        int unique = master ? 5 : 3;
        if (getState() == 17 && Randomizer.nextInt(100) <= epic) {
            setState((byte) 2);
            return;
        } else if (getState() == 18 && Randomizer.nextInt(100) <= unique) {
            setState((byte) 3);
            return;
        }
        if (master) {
            if (getState() == 19 && Randomizer.nextInt(100) <= 2) {
                setState((byte) 4);
                return;
            }
        }
        setState((byte) (getState() - 16));
    }

    public void giveEpicAndRenew() {
        setPotential1(-18);
        setPotential2(0);
        setPotential3(0);
    }

    public short getHpR() {
        return hpR;
    }

    public void setHpR(final short hp) {
        this.hpR = hp;
    }

    public short getMpR() {
        return mpR;
    }

    public void setMpR(final short mp) {
        this.mpR = mp;
    }

    public byte getDownLevel() {
        return downlevel;
    }

    public void setDownLevel(byte down) {
        this.downlevel = down;
    }

    public byte getBossDamage() {
        return bossdmg;
    }

    public void setBossDamage(byte dmg) {
        this.bossdmg = dmg;
    }

    public void addBossDamage(byte dmg) {
        this.bossdmg += dmg;
    }

    public byte getAllDamageP() {
        return alldmgp;
    }

    public void setAllDamageP(byte percent) {
        this.alldmgp = percent;
    }

    public short getIgnoreWdef() {
        return IgnoreWdef;
    }

    public void setIgnoreWdef(short percent) {
        this.IgnoreWdef = percent;
    }

    public void addIgnoreWdef(short IgnoreWdef) {
        this.IgnoreWdef += IgnoreWdef;
    }

    public byte getAllStatP() {
        return allstatp;
    }

    public void setAllStatP(byte percent) {
        this.allstatp = percent;
    }

    public short getSoulEnchanter() {
        return soulenchanter;
    }

    public void setSoulEnchanter(final short soulenchanter) {
        this.soulenchanter = soulenchanter;
    }

    public short getSoulName() {
        return soulname;
    }

    public void setSoulName(final short soulname) {
        this.soulname = soulname;
    }

    public int getSoulSkill() {
        return soulskill;
    }

    public void setSoulSkill(final int soulskill) {
        this.soulskill = soulskill;
    }

    public boolean isAmazing() {
        return amazing;
    }

    public void setAmazing(boolean amazing) {
        this.amazing = amazing;
    }

    public void resetPotential_Fuse(boolean a, boolean nopotential) { // equip first receive
        int epic = a ? 20 : 10;
        int unique = a ? 10 : 5;
        if (Randomizer.nextInt(100) <= epic && !nopotential) {
            setState((byte) 2);
        } else if (Randomizer.nextInt(100) <= unique && !nopotential) {
            setState((byte) 3);
        } else if (!nopotential) {
            setState((byte) 1);
        }
    }
}
