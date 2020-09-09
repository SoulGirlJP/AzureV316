package client.ItemInventory;

import java.io.Serializable;

import constants.GameConstants;
import server.Items.ItemInformation;
import server.Items.MapleRing;
import tools.RandomStream.Randomizer;

public class Equip extends Item implements IEquip, Serializable {


    private MapleRing ring = null;
    private byte upgradeSlots, level, vicioushammer, itemLevel, enhance, state, lines, bossdmg, alldmgp, allstatp;
    private short str, dex, _int, luk, arc = 0, hp, mp, watk, matk, wdef, mdef, acc, avoid, hands, speed, jump, hpR,
            mpR, IgnoreWdef, downlevel, soulname, soulenchanter, soulpotential, itemtrace, charmExp;
    private int arcexp = 0, arclevel = 0, job, potential, durability = -1, potential1, potential2, potential3,
            potential4, potential5, potential6, potential7, anvil, itemEXP;
    private int fire = 0;
    private boolean amazing, amazingequipscroll;
    private int soulskill;
    private int[] fireStat = new int[16];

    public Equip(int id, short position, short flag) {
        super(id, position, (short) 1, flag);
    }

    public void set(Equip e) {
        upgradeSlots = e.upgradeSlots;
        level = e.level;
        vicioushammer = e.vicioushammer;
        itemLevel = e.itemLevel;
        enhance = e.enhance;
        state = e.state;
        lines = e.lines;
        fire = e.fire;
        bossdmg = e.bossdmg;
        alldmgp = e.alldmgp;
        allstatp = e.allstatp;
        downlevel = e.downlevel;
        str = e.str;
        dex = e.dex;
        _int = e._int;
        luk = e.luk;
        arc = e.arc;
        arclevel = e.arclevel;
        arcexp = e.arcexp;
        hp = e.hp;
        mp = e.mp;
        watk = e.watk;
        matk = e.matk;
        wdef = e.wdef;
        mdef = e.mdef;
        acc = e.acc;
        avoid = e.avoid;
        hands = e.hands;
        speed = e.speed;
        jump = e.jump;
        hpR = e.hpR;
        mpR = e.mpR;
        IgnoreWdef = e.IgnoreWdef;
        soulname = e.soulname;
        soulenchanter = e.soulenchanter;
        soulpotential = e.soulpotential;
        job = e.job;
        potential = e.potential;
        durability = e.durability;
        potential1 = e.potential1;
        potential2 = e.potential2;
        potential3 = e.potential3;
        potential4 = e.potential4;
        potential5 = e.potential5;
        potential6 = e.potential6;
        potential7 = e.potential7;
        anvil = e.anvil;
        itemEXP = e.itemEXP;
        amazing = e.amazing;
        soulskill = e.soulskill;
    }

    @Override
    public IItem copy() {
        Equip ret = new Equip(getItemId(), getPosition(), getFlag());
        ret.str = str;
        ret.dex = dex;
        ret._int = _int;
        ret.luk = luk;
        ret.arc = arc;
        ret.arcexp = arcexp;
        ret.arclevel = arclevel;
        ret.hp = hp;
        ret.mp = mp;
        ret.matk = matk;
        ret.mdef = mdef;
        ret.watk = watk;
        ret.wdef = wdef;
        ret.acc = acc;
        ret.avoid = avoid;
        ret.hands = hands;
        ret.speed = speed;
        ret.jump = jump;
        ret.enhance = enhance;
        ret.upgradeSlots = upgradeSlots;
        ret.level = level;
        ret.itemEXP = itemEXP;
        ret.itemLevel = itemLevel;
        ret.durability = durability;
        ret.vicioushammer = vicioushammer;
        ret.potential1 = potential1;
        ret.potential2 = potential2;
        ret.potential3 = potential3;
        ret.potential4 = potential4;
        ret.potential5 = potential5;
        ret.potential6 = potential6;
        ret.potential7 = potential7;
        ret.anvil = anvil;
        ret.hpR = hpR;
        ret.mpR = mpR;
        ret.state = state;
        ret.lines = lines;
        ret.fire = fire;
        ret.downlevel = downlevel;
        ret.bossdmg = bossdmg;
        ret.alldmgp = alldmgp;
        ret.allstatp = allstatp;
        ret.IgnoreWdef = IgnoreWdef;
        ret.soulenchanter = soulenchanter;
        ret.soulname = soulname;
        ret.soulskill = soulskill;
        ret.soulpotential = soulpotential;
        ret.amazingequipscroll = amazingequipscroll;
        ret.fireStat = fireStat;
        ret.arc = arc;
        ret.arcexp = arcexp;
        ret.arclevel = arclevel;
        ret.itemtrace = itemtrace;
        ret.setOwner(getOwner());
        ret.setQuantity(getQuantity());
        ret.setExpiration(getExpiration());
        ret.setUniqueId(getUniqueId());
        ret.setCash(isCash());
        return ret;
    }

    @Override
    public byte getType() {
        return 1;
    }

    @Override
    public byte getUpgradeSlots() {
        return upgradeSlots;
    }

    @Override
    public short getStr() {
        return str;
    }

    @Override
    public short getDex() {
        return dex;
    }

    @Override
    public short getInt() {
        return _int;
    }

    @Override
    public short getLuk() {
        return luk;
    }

    @Override
    public short getHp() {
        return hp;
    }

    @Override
    public short getMp() {
        return mp;
    }

    @Override
    public short getWatk() {
        return watk;
    }

    @Override
    public short getMatk() {
        return matk;
    }

    @Override
    public short getWdef() {
        return wdef;
    }

    @Override
    public short getMdef() {
        return mdef;
    }

    @Override
    public short getAcc() {
        return acc;
    }

    @Override
    public short getAvoid() {
        return avoid;
    }

    @Override
    public short getHands() {
        return hands;
    }

    @Override
    public short getSpeed() {
        return speed;
    }

    @Override
    public short getJump() {
        return jump;
    }

    @Override
    public short getArc() {
        return arc;
    }
    
    @Override
    public int getFire() {
        return fire;
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
    
    public void addInt(short _int) {
        if (_int < 0) {
            _int = 0;
        }
        this._int += _int;
    }

    public void setLuk(short luk) {
        if (luk < 0) {
            luk = 0;
        }
        this.luk = luk;
    }
    
    public void addLuk(short luk) {
        if (luk < 0) {
            luk = 0;
        }
        this.luk += luk;
    }

    public void setArc(short arc) {
        if (arc < 0) {
            arc = 0;
        }
        this.arc = arc;
    }

    public void setHp(short hp) {
        if (hp < 0) {
            hp = 0;
        }
        this.hp = hp;
    }
    
    public void addHp(short hp) {
        if (this.hp + hp < 0) {
        	this.hp = 0;
        }
        this.hp += hp;
    }

    public void setMp(short mp) {
        if (mp < 0) {
            mp = 0;
        }
        this.mp = mp;
    }
    
    public void addMp(short dex) {
        if (this.mp + dex < 0) {
            this.mp = 0;
        }
        this.mp += dex;
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
    
    public void addMatk(short watk) {
        if (this.matk + watk < 0) {
        	this.matk = 0;
        }
        this.matk += watk;
    }

    public void setWdef(short wdef) {
        if (wdef < 0) {
            wdef = 0;
        } 
        this.wdef = wdef;
    }
    

    public void addWdef(short wdef) {
        if (wdef < 0) {
            wdef = 0;
        }
        this.wdef += wdef;
    }

    public void setMdef(short mdef) {
        if (mdef < 0) {
            mdef = 0;
        } 
        this.mdef = mdef;
    }

    public void addMdef(short mdef) {
        if (mdef < 0) {
            mdef = 0;
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
    
    public void addSpeed(short speed) {
        if (speed + this.speed < 0) {
            this.speed = 0;
        }
        this.speed += speed;
    }

    public void setJump(short jump) {
        if (jump < 0) {
            jump = 0;
        }
        this.jump = jump;
    }
    
    public void addJump(short jump) {
        if (jump + this.jump < 0) {
            this.jump = 0;
        }
        this.jump += jump;
    }

    public void setUpgradeSlots(byte upgradeSlots) {
        this.upgradeSlots = upgradeSlots;
    }

    public void addUpgradeSlots(byte upgradeSlots) {
        this.upgradeSlots += upgradeSlots;
    }

    @Override
    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    @Override
    public byte getViciousHammer() {
        return vicioushammer;
    }

    public void setViciousHammer(byte ham) {
        vicioushammer = ham;
    }

    @Override
    public byte getItemLevel() {
        return 6;
    }

    public void setItemLevel(byte itemLevel) {
        if (itemLevel < 0) {
            itemLevel = 0;
        }
        this.itemLevel = itemLevel;
    }

    @Override
    public int getItemEXP() {
        return itemEXP;
    }

    public void setItemEXP(int itemEXP) {
        if (itemEXP < 0) {
            itemEXP = 0;
        }
        this.itemEXP = itemEXP;
    }

    @Override
    public void setQuantity(short quantity) {
        if (quantity < 0 || quantity > 1) {
            throw new RuntimeException(
                    "Setting the quantity to " + quantity + " on an equip (itemid: " + getItemId() + ")");
        }
        super.setQuantity(quantity);
    }

    public void setJob(int job) {
        this.job = job;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    public void setDurability(final int dur) {
        this.durability = dur;
    }

    @Override
    public byte getEnhance() {
        return enhance;
    }

    public void setEnhance(final byte en) {
        this.enhance = en;
    }

    @Override
    public int getPotential1() {
        return potential1;
    }

    public void setPotential1(final int en) {
        this.potential1 = en;
    }

    @Override
    public int getPotential2() {
        return potential2;
    }

    public void setPotential2(final int en) {
        this.potential2 = en;
    }

    @Override
    public int getPotential3() {
        return potential3;
    }

    public void setPotential3(final int en) {
        this.potential3 = en;
    }

    @Override
    public int getPotential4() {
        return potential4;
    }

    public void setPotential4(final int en) {
        this.potential4 = en;
    }

    @Override
    public int getPotential5() {
        return potential5;
    }

    public void setPotential5(final int en) {
        this.potential5 = en;
    }

    @Override
    public int getPotential6() {
        return potential6;
    }

    public void setPotential6(final int en) {
        this.potential6 = en;
    }

    @Override
    public int getPotential7() {
        return potential7;
    }

    public void setPotential7(final int en) {
        this.potential7 = en;
    }

    public void setPotential(Equip pOther, boolean pIncludeFlame) {
    	setPotential1(pOther.getPotential1());
    	setPotential2(pOther.getPotential2());
    	setPotential3(pOther.getPotential3());
    	setPotential4(pOther.getPotential4());
    	setPotential5(pOther.getPotential5());
    	setPotential6(pOther.getPotential6());
    	if (pIncludeFlame) {
    		setPotential7(pOther.getPotential7());
    	}
    }
    
    public void setPotential(IEquip pOther, boolean pIncludeFlame) {
    	setPotential1(pOther.getPotential1());
    	setPotential2(pOther.getPotential2());
    	setPotential3(pOther.getPotential3());
    	setPotential4(pOther.getPotential4());
    	setPotential5(pOther.getPotential5());
    	setPotential6(pOther.getPotential6());
    	if (pIncludeFlame) {
    		setPotential7(pOther.getPotential7());
    	}
    }
    
    @Override
    public int getanvil() {
        return anvil;
    }

    public void setanvil(final int en) {
        this.anvil = en;
    }

    @Override
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Override
    public byte getLines() {
        return lines;
    }

    public void setLines(byte lines) {
        this.lines = lines;
    }

    public void setFire(int fire) {
        this.fire = fire;
    }

    @Override
    public short getItemTrace() {
        return this.itemtrace;
    }

    public void setItemTrace(short trace) {
        this.itemtrace = trace;
    }
    
    public boolean isAmazingequipscroll() {
        return amazingequipscroll;
    }
    
    public void setAmazingequipscroll(boolean tt) {
        this.amazingequipscroll = tt;
    }

    public void renewPotential() {
        int epic = 7;
        int unique = 5;
        if (getState() == 17 && Randomizer.nextInt(100) <= epic) {
            setState((byte) 2);
            return;
        } else if (getState() == 18 && Randomizer.nextInt(100) <= unique) {
            setState((byte) 3);
            return;
        } else if (getState() == 19 && Randomizer.nextInt(100) <= 2) {
            setState((byte) 4);
            return;
        }
        setState((byte) (getState() - 16));
    }

    public void renewPotentialUniq() {
        int epic = 7;
        int unique = 5;
        if (getState() == 17 && Randomizer.nextInt(100) <= epic) {
            setState((byte) 2);
            return;
        } else if (getState() == 18 && Randomizer.nextInt(100) <= unique) {
            setState((byte) 3);
            return;
        }
        setState((byte) (getState() - 16));
    }

    public void giveEpicAndRenew() {
        setPotential1(-18);
        setPotential2(0);
        setPotential3(0);
    }

    @Override
    public short getHpR() {
        return hpR;
    }

    public void setHpR(final short hp) {
        this.hpR = hp;
    }

    @Override
    public short getMpR() {
        return mpR;
    }

    public void setMpR(final short mp) {
        this.mpR = mp;
    }

    @Override
    public short getDownLevel() {
        return downlevel;
    }

    public void setDownLevel(short down) {
        this.downlevel = down;
    }

    @Override
    public byte getBossDamage() {
        return bossdmg;
    }

    public void setBossDamage(byte dmg) {
        this.bossdmg = dmg;
    }

    public void addBossDamage(byte dmg) {
        if (this.bossdmg + dmg < 0) {
            this.bossdmg = 0;
        }
        this.bossdmg += dmg;
    }

    @Override
    public byte getAllDamageP() {
        return alldmgp;
    }

    public void setAllDamageP(byte percent) {
        this.alldmgp = percent;
    }

    @Override
    public short getIgnoreWdef() {
        return IgnoreWdef;
    }

    public void setIgnoreWdef(short percent) {
        this.IgnoreWdef = percent;
    }

    public void addIgnoreWdef(short IgnoreWdef) {
        if (this.IgnoreWdef + IgnoreWdef < 0) {
            this.IgnoreWdef = 0;
        }
        this.IgnoreWdef += IgnoreWdef;
    }

    @Override
    public byte getAllStatP() {
        return allstatp;
    }

    public void setAllStatP(byte percent) {
        this.allstatp = percent;
    }

    @Override
    public short getSoulEnchanter() {
        return soulenchanter;
    }

    public void setSoulEnchanter(final short soulenchanter) {
        this.soulenchanter = soulenchanter;
    }

    @Override
    public short getSoulPotential() {
        return this.soulpotential;
    }

    public void setSoulPotential(short soulpotential) {
        this.soulpotential = soulpotential;
    }

    @Override
    public short getSoulName() {
        return soulname;
    }

    public void setSoulName(final short soulname) {
        this.soulname = soulname;
    }

    @Override
    public int getSoulSkill() {
        return soulskill;
    }

    public void setFireStat(String t) {
        try {
            String[] s = t.split(",");
            for (int i = 0; i < s.length; i++) {
                fireStat[i] = Integer.parseInt(s[i]);
            }
        } catch (Exception ex) {
            fireStat = new int[16];
        }
    }

    public void setFireStat(int[] i) {
        fireStat = i;
    }

    @Override
    public int[] getFireStat() {
        return fireStat;
    }

    @Override
    public String getFireStatToString() {
        String t = "";
        for (int i = 0; i < fireStat.length; i++) {
            t += (t.equals("") ? "" : ",") + fireStat[i];
        }
        return t;
    }

    public void setSoulSkill(final int soulskill) {
        this.soulskill = soulskill;
    }

    public MapleRing getRing() {
        if (!GameConstants.isEffectRing(getItemId()) || getUniqueId() <= 0) {
            return null;
        }
        if (ring == null) {
            ring = MapleRing.loadFromDb(getUniqueId(), getPosition() < 0);
        }
        return ring;
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

    public short getCharmEXP() {
        return charmExp;
    }

    public void setCharmEXP(short s) {
        charmExp = s;
    }

    @Override
    public int getArcEXP() {
        return arcexp;
    }

    @Override
    public int getArcLevel() {
        return arclevel;
    }

    public void setArcEXP(int exp) {
        arcexp = exp;
    }

    public void setArcLevel(int lv) {
        arclevel = lv;
    }
    
    public void setReqLevel(byte reqLevel) {
        this.itemLevel = reqLevel;
    }
    public void setFire2(int fire) {
        this.fire = fire;
    }
    public void addTotalDamage(byte totalDamage) {
         if (this.alldmgp + totalDamage < 0) {
            this.alldmgp = 0;
        }
        this.alldmgp += totalDamage;
    }
    public void addAllStat(byte allStat) {
        if (this.allstatp + allStat < 0) {
            this.allstatp = 0;
        }
        this.allstatp += allStat;
    }
    
    
    
    public void resetRebirth(int reqLevel) {
//		Rings, Shields, Shoulders, Decorations, Hearts, Weapons, Emblems, Badges, Android, Pets, Symbols
        if (GameConstants.isRing(getItemId()) || (getItemId() / 1000) == 1092 || (getItemId() / 1000) == 1342 || (getItemId() / 1000) == 1712 || (getItemId() / 1000) == 1152 || (getItemId() / 1000) == 1143 || (getItemId() / 1000) == 1672 || GameConstants.isSecondaryWeapon(getItemId()) || (getItemId() / 1000) == 1190 || (getItemId() / 1000) == 1182 || (getItemId() / 1000) == 1662 || (getItemId() / 1000) == 1802) {
            return;
        }

        if (getFire() == 0) {
            return;
        }

        Equip ordinary = (Equip) ItemInformation.getInstance().getEquipById(getItemId());

        int ordinaryPad = ordinary.watk > 0 ? ordinary.watk : ordinary.matk;
        int ordinaryMad = ordinary.matk > 0 ? ordinary.matk : ordinary.watk;

        int[] rebirth = new int[4];
        String fire = String.valueOf(getFire());
        if (fire.length() == 12) {
            rebirth[0] = Integer.parseInt(fire.substring(0, 3));
            rebirth[1] = Integer.parseInt(fire.substring(3, 6));
            rebirth[2] = Integer.parseInt(fire.substring(6, 9));
            rebirth[3] = Integer.parseInt(fire.substring(9));
        } else if (fire.length() == 11) {
            rebirth[0] = Integer.parseInt(fire.substring(0, 2));
            rebirth[1] = Integer.parseInt(fire.substring(2, 5));
            rebirth[2] = Integer.parseInt(fire.substring(5, 8));
            rebirth[3] = Integer.parseInt(fire.substring(8));
        } else if (fire.length() == 10) {
            rebirth[0] = Integer.parseInt(fire.substring(0, 1));
            rebirth[1] = Integer.parseInt(fire.substring(1, 4));
            rebirth[2] = Integer.parseInt(fire.substring(4, 7));
            rebirth[3] = Integer.parseInt(fire.substring(7));
        } else {
            return;
        }

        for (int i = 0; i < 4; ++i) {
            int randomOption = rebirth[i] / 10;
            int randomValue = rebirth[i] - (rebirth[i] / 10 * 10);

            switch (randomOption) {
                case 0:
                    addStr((short) -((reqLevel / 20 + 1) * randomValue));
                    break;
                case 1:
                    addDex((short) -((reqLevel / 20 + 1) * randomValue));
                    break;
                case 2:
                    addInt((short) -((reqLevel / 20 + 1) * randomValue));
                    break;
                case 3:
                    addLuk((short) -((reqLevel / 20 + 1) * randomValue));
                    break;
                case 4:
                    addStr((short) -((reqLevel / 40 + 1) * randomValue));
                    addDex((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 5:
                    addStr((short) -((reqLevel / 40 + 1) * randomValue));
                    addInt((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 6:
                    addStr((short) -((reqLevel / 40 + 1) * randomValue));
                    addLuk((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 7:
                    addDex((short) -((reqLevel / 40 + 1) * randomValue));
                    addInt((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 8:
                    addDex((short) -((reqLevel / 40 + 1) * randomValue));
                    addLuk((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 9:
                    addInt((short) -((reqLevel / 40 + 1) * randomValue));
                    addLuk((short) -((reqLevel / 40 + 1) * randomValue));
                    break;
                case 10:
                    addHp((short) -(reqLevel * 3 * randomValue));
                    break;
                case 11:
                    addMp((short) -(reqLevel * 3 * randomValue));
                    break;
                case 13:
                    addWdef((short) -((reqLevel / 20 + 1) * randomValue));
                    break;
                case 17: {
                    if (GameConstants.isWeapon(getItemId())) {
                        switch (randomValue) {
                            case 3:
                                if (reqLevel <= 150) {
                                    addWatk((short) -(((ordinaryPad * 1200) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addWatk((short) -(((ordinaryPad * 1500) / 10000) + 1));
                                } else {
                                    addWatk((short) -(((ordinaryPad * 1800) / 10000) + 1));
                                }
                                break;
                            case 4:
                                if (reqLevel <= 150) {
                                    addWatk((short) -(((ordinaryPad * 1760) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addWatk((short) -(((ordinaryPad * 2200) / 10000) + 1));
                                } else {
                                    addWatk((short) -(((ordinaryPad * 2640) / 10000) + 1));
                                }
                                break;
                            case 5:
                                if (reqLevel <= 150) {
                                    addWatk((short) -(((ordinaryPad * 2420) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addWatk((short) -(((ordinaryPad * 3025) / 10000) + 1));
                                } else {
                                    addWatk((short) -(((ordinaryPad * 3630) / 10000) + 1));
                                }
                                break;
                            case 6:
                                if (reqLevel <= 150) {
                                    addWatk((short) -(((ordinaryPad * 3200) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addWatk((short) -(((ordinaryPad * 4000) / 10000) + 1));
                                } else {
                                    addWatk((short) -(((ordinaryPad * 4800) / 10000) + 1));
                                }
                                break;
                            case 7:
                                if (reqLevel <= 150) {
                                    addWatk((short) -(((ordinaryPad * 4100) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addWatk((short) -(((ordinaryPad * 5125) / 10000) + 1));
                                } else {
                                    addWatk((short) -(((ordinaryPad * 6150) / 10000) + 1));
                                }
                                break;
                        }
                    } else {
                        addWatk((short) -randomValue);
                    }
                    break;
                }
                case 18: {
                    if (GameConstants.isWeapon(getItemId())) {
                        switch (randomValue) {
                            case 3:
                                if (reqLevel <= 150) {
                                    addMatk((short) -(((ordinaryMad * 1200) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addMatk((short) -(((ordinaryMad * 1500) / 10000) + 1));
                                } else {
                                    addMatk((short) -(((ordinaryMad * 1800) / 10000) + 1));
                                }
                                break;
                            case 4:
                                if (reqLevel <= 150) {
                                    addMatk((short) -(((ordinaryMad * 1760) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addMatk((short) -(((ordinaryMad * 2200) / 10000) + 1));
                                } else {
                                    addMatk((short) -(((ordinaryMad * 2640) / 10000) + 1));
                                }
                                break;
                            case 5:
                                if (reqLevel <= 150) {
                                    addMatk((short) -(((ordinaryMad * 2420) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addMatk((short) -(((ordinaryMad * 3025) / 10000) + 1));
                                } else {
                                    addMatk((short) -(((ordinaryMad * 3630) / 10000) + 1));
                                }
                                break;
                            case 6:
                                if (reqLevel <= 150) {
                                    addMatk((short) -(((ordinaryMad * 3200) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addMatk((short) -(((ordinaryMad * 4000) / 10000) + 1));
                                } else {
                                    addMatk((short) -(((ordinaryMad * 4800) / 10000) + 1));
                                }
                                break;
                            case 7:
                                if (reqLevel <= 150) {
                                    addMatk((short) -(((ordinaryMad * 4100) / 10000) + 1));
                                } else if (reqLevel <= 160) {
                                    addMatk((short) -(((ordinaryMad * 5125) / 10000) + 1));
                                } else {
                                    addMatk((short) -(((ordinaryMad * 6150) / 10000) + 1));
                                }
                                break;
                        }
                    } else {
                        addMatk((short) -randomValue);
                    }
                    break;
                }
                case 19:
                    addSpeed((short) -randomValue);
                    break;
                case 20:
                    addJump((short) -randomValue);
                    break;
                case 21:
                    addBossDamage((byte) -(randomValue * 2));
                    break;
                case 22:
                    setReqLevel((byte) 0);
                    break;
                case 23:
                    addTotalDamage((byte) -randomValue);
                    break;
                case 24:
                    addAllStat((byte) -randomValue);
                    break;
            }
        }
        setFire(0);
    }
   public void setFireOption(int randomOption, int reqLevel, int randomValue, int ordinaryPad, int ordinaryMad) {
        switch (randomOption) {
            case 0:
                addStr((short) ((reqLevel / 20 + 1) * randomValue));
                break;
            case 1:
                addDex((short) ((reqLevel / 20 + 1) * randomValue));
                break;
            case 2:
                addInt((short) ((reqLevel / 20 + 1) * randomValue));
                break;
            case 3:
                addLuk((short) ((reqLevel / 20 + 1) * randomValue));
                break;
            case 4:
                addStr((short) ((reqLevel / 40 + 1) * randomValue));
                addDex((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 5:
                addStr((short) ((reqLevel / 40 + 1) * randomValue));
                addInt((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 6:
                addStr((short) ((reqLevel / 40 + 1) * randomValue));
                addLuk((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 7:
                addDex((short) ((reqLevel / 40 + 1) * randomValue));
                addInt((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 8:
                addDex((short) ((reqLevel / 40 + 1) * randomValue));
                addLuk((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 9:
                addInt((short) ((reqLevel / 40 + 1) * randomValue));
                addLuk((short) ((reqLevel / 40 + 1) * randomValue));
                break;
            case 10:
                addHp((short) (reqLevel * 3 * randomValue));
                break;
            case 11:
                addMp((short) (reqLevel * 3 * randomValue));
                break;
            case 13:
                addWdef((short) ((reqLevel / 20 + 1) * randomValue));
                break;
            case 17: {
                if (GameConstants.isWeapon(getItemId())) {
                    switch (randomValue) {
                        case 3:
                            if (reqLevel <= 150) {
                                addWatk((short) (((ordinaryPad * 1200) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addWatk((short) (((ordinaryPad * 1500) / 10000) + 1));
                            } else {
                                addWatk((short) (((ordinaryPad * 1800) / 10000) + 1));
                            }
                            break;
                        case 4:
                            if (reqLevel <= 150) {
                                addWatk((short) (((ordinaryPad * 1760) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addWatk((short) (((ordinaryPad * 2200) / 10000) + 1));
                            } else {
                                addWatk((short) (((ordinaryPad * 2640) / 10000) + 1));
                            }
                            break;
                        case 5:
                            if (reqLevel <= 150) {
                                addWatk((short) (((ordinaryPad * 2420) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addWatk((short) (((ordinaryPad * 3025) / 10000) + 1));
                            } else {
                                addWatk((short) (((ordinaryPad * 3630) / 10000) + 1));
                            }
                            break;
                        case 6:
                            if (reqLevel <= 150) {
                                addWatk((short) (((ordinaryPad * 3200) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addWatk((short) (((ordinaryPad * 4000) / 10000) + 1));
                            } else {
                                addWatk((short) (((ordinaryPad * 4800) / 10000) + 1));
                            }
                            break;
                        case 7:
                            if (reqLevel <= 150) {
                                addWatk((short) (((ordinaryPad * 4100) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addWatk((short) (((ordinaryPad * 5125) / 10000) + 1));
                            } else {
                                addWatk((short) (((ordinaryPad * 6150) / 10000) + 1));
                            }
                            break;
                    }
                } else {
                    addWatk((short) randomValue);
                }
                break;
            }
            case 18: {
                if (GameConstants.isWeapon(getItemId())) {
                    switch (randomValue) {
                        case 3:
                            if (reqLevel <= 150) {
                                addMatk((short) (((ordinaryMad * 1200) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addMatk((short) (((ordinaryMad * 1500) / 10000) + 1));
                            } else {
                                addMatk((short) (((ordinaryMad * 1800) / 10000) + 1));
                            }
                            break;
                        case 4:
                            if (reqLevel <= 150) {
                                addMatk((short) (((ordinaryMad * 1760) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addMatk((short) (((ordinaryMad * 2200) / 10000) + 1));
                            } else {
                                addMatk((short) (((ordinaryMad * 2640) / 10000) + 1));
                            }
                            break;
                        case 5:
                            if (reqLevel <= 150) {
                                addMatk((short) (((ordinaryMad * 2420) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addMatk((short) (((ordinaryMad * 3025) / 10000) + 1));
                            } else {
                                addMatk((short) (((ordinaryMad * 3630) / 10000) + 1));
                            }
                            break;
                        case 6:
                            if (reqLevel <= 150) {
                                addMatk((short) (((ordinaryMad * 3200) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addMatk((short) (((ordinaryMad * 4000) / 10000) + 1));
                            } else {
                                addMatk((short) (((ordinaryMad * 4800) / 10000) + 1));
                            }
                            break;
                        case 7:
                            if (reqLevel <= 150) {
                                addMatk((short) (((ordinaryMad * 4100) / 10000) + 1));
                            } else if (reqLevel <= 160) {
                                addMatk((short) (((ordinaryMad * 5125) / 10000) + 1));
                            } else {
                                addMatk((short) (((ordinaryMad * 6150) / 10000) + 1));
                            }
                            break;
                    }
                } else {
                    addMatk((short) randomValue);
                }
                break;
            }
            case 19:
                addSpeed((short) randomValue);
                break;
            case 20:
                addJump((short) randomValue);
                break;
            case 21:
                addBossDamage((byte) (randomValue * 2));
                break;
            case 22:
                setReqLevel((byte) (-5 * randomValue));
                break;
            case 23:
                addTotalDamage((byte) randomValue);
                break;
            case 24:
                addAllStat((byte) randomValue);
                break;
        }
   }
    public void setRebirth(int reqLevel, int scrollId) {
//		반지, 방패, 어깨장식, 훈장, 심장, 보조무기, 엠블렘, 뱃지, 안드로이드, 펫장비, 심볼은 추가옵션이 붙지 않음
        if (GameConstants.isRing(getItemId()) || (getItemId() / 1000) == 1092 || (getItemId() / 1000) == 1342 || (getItemId() / 1000) == 1712 || (getItemId() / 1000) == 1152 || (getItemId() / 1000) == 1142 || (getItemId() / 1000) == 1143 || (getItemId() / 1000) == 1672 || GameConstants.isSecondaryWeapon(getItemId()) || (getItemId() / 1000) == 1190 || (getItemId() / 1000) == 1182 || (getItemId() / 1000) == 1662 || (getItemId() / 1000) == 1802) {
            return;
        }

        int maxValue = 5;

        if (scrollId == 2048716 || scrollId == 2048720 || scrollId == 2048724 || scrollId == 2048745) { // A strong refund
            maxValue = 6;
        }

        if (scrollId == 2048717 || scrollId == 2048721 || scrollId == 2048723 || scrollId == 2048746 || scrollId == 2048747) { // Refund
            maxValue = 7;
        }

        Equip ordinary = (Equip) ItemInformation.getInstance().getEquipById(getItemId());

        int ordinaryPad = ordinary.watk > 0 ? ordinary.watk : ordinary.matk;
        int ordinaryMad = ordinary.matk > 0 ? ordinary.matk : ordinary.watk;

        ItemInformation ii = ItemInformation.getInstance();

        int[] rebirth = new int[4];

        for (int i = 0; i < 4; ++i) {
            int randomOption = Randomizer.nextInt(25);
            int randomValue = 0; // Usually 3 ~ 7-> 1 ~ 5 commonly called

            while (randomOption == 12 || randomOption == 14 || randomOption == 15 || randomOption == 16 || (!GameConstants.isWeapon(getItemId()) && (randomOption == 21 || randomOption == 23))) {
                randomOption = Randomizer.nextInt(25);
            }

            if (((randomOption == 17 || randomOption == 18) && !GameConstants.isWeapon(getItemId())) || randomOption == 21 || randomOption == 22 || randomOption == 23 || randomOption == 24) {
                randomValue = Randomizer.rand(1, maxValue);
            } else {
                randomValue = Randomizer.rand(3, maxValue);
            }

            rebirth[i] = (randomOption * 10 + randomValue);

            for (int j = 0; j < i; ++j) {
                rebirth[i] *= 1000;
            }

            setFireOption(randomOption, reqLevel, randomValue, ordinaryPad, ordinaryMad);
        }

        setFire(rebirth[0] + rebirth[1] + rebirth[2] + rebirth[3]);
    }


}

 