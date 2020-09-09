package server.Maps.MapSummon;

import java.awt.Point;
import java.lang.ref.WeakReference;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import constants.GameConstants;
import connections.Packets.MainPacketCreator;
import server.Maps.MapObject.AnimatedHinaMapObjectExtend;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapleMapHandling.MapleMap;

public class MapleSummon extends AnimatedHinaMapObjectExtend {

    private final MapleCharacter owner;
    private final WeakReference<MapleCharacter> ownerchr;
    private int skillLevel;
    private int skill;
    private int hp;
    private int maelstromid;
    private SummonMovementType movementType;
    private byte Summon_tickResetCount;
    private long endTime;
    public long startTime;

    public MapleSummon(final MapleCharacter owner, final SkillStatEffect skill, final Point pos,
            final SummonMovementType movementType, long startTime) {
        this(owner, skill.getSourceId(), skill.getLevel(), pos, movementType, startTime);
    }

    public MapleSummon(final MapleCharacter owner, final int skill, final Point pos,
            final SummonMovementType movementType, long startTime) {
        super();
        this.owner = owner;
        this.ownerchr = new WeakReference<MapleCharacter>(owner);
        this.skill = skill;
        this.movementType = movementType;
        this.skillLevel = owner.getSummonLinkSkillLevel(SkillFactory.getSkill(GameConstants.getLinkedAttackSkill(skill)));
        this.endTime = startTime + SkillFactory.getSkill(skill).getEffect(skillLevel).getDuration();
        this.startTime = startTime;
        if (skillLevel == 0) {
            return;
        }
        setPosition(pos);
        Summon_tickResetCount = 0;
    }

    public MapleSummon(MapleCharacter owner, int skill, int duration, Point pos, SummonMovementType movementType,
            long startTime) {
        this.owner = owner;
        this.ownerchr = new WeakReference<MapleCharacter>(owner);
        this.skill = skill;
        int lkk = 0;
        if (owner.getSummonLinkSkillLevel(SkillFactory.getSkill(GameConstants.getLinkedAttackSkill(skill))) > 0) {
            lkk = owner.getSummonLinkSkillLevel(SkillFactory.getSkill(GameConstants.getLinkedAttackSkill(skill)));
        }
        this.skillLevel = (byte) lkk;
        this.movementType = movementType;
        this.startTime = startTime;
        this.endTime = startTime + duration;
        setPosition(pos);
    }

    @Override
    public final void sendSpawnData(final MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.spawnSummon(MapleSummon.this, skillLevel, false));
    }

    @Override
    public final void sendDestroyData(final MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.removeSummon(this, true));
    }

    public final void removeSummon(final MapleMap map, final boolean animation) {
        map.broadcastMessage(MainPacketCreator.removeSummon(this, animation));
        map.removeMapObject(this);
        if (getOwner() != null) {
            getOwner().removeVisibleMapObject(this);
            getOwner().removeSummon(getObjectId());
        }
    }

    public final void updateSummon(final MapleMap map, boolean remove) {
        if (remove) {
            map.broadcastMessage(MainPacketCreator.removeSummon(this, true));
        } else {
            map.broadcastMessage(MainPacketCreator.spawnSummon(MapleSummon.this, skillLevel, false));
        }
    }

    public final void removeSummon(final MapleMap map) {
        removeSummon(map, true);
    }

    public final int getSkill() {
        return skill;
    }

    public int BEFORE_SKILL = 0;
    public int BEFORE_LEVEL = 0;

    public final void setSkill(int skill, int skilllevel) {
        BEFORE_SKILL = this.skill;
        this.skill = skill;
        BEFORE_LEVEL = this.skillLevel;
        this.skillLevel = skilllevel;
    }

    public final int getHP() {
        return hp;
    }

    public final void addHP(final int delta) {
        this.hp += delta;
    }

    public final MapleCharacter getOwnerChr() {
        return ownerchr.get();
    }

    public final SummonMovementType getMovementType() {
        return movementType;
    }

    public final boolean isStaticSummon() {
        return SkillFactory.getSkill(getSkill()).getEffect(1).isStaticSummon();
    }

    public final boolean isSummon() {
        switch (skill) {
            case 12111004:
            case 1301013: // beholder
            case 2321003:
            case 2121005:
            case 35111011:
            case 2221005:
            case 2211011:
            case 5211001: // Pirate octopus summon
            case 5211002:
            case 5220002: // wrath of the octopi
            case 4341006: // Dummy effect
            case 61111002: // Petri Fid
            case 61111220:
            case 3221014:
            case 22171052: // Summon Onyx Dragon
            case 13111004:
            case 11001004:
            case 12001004:
            case 13001004:
            case 14001005:
            case 35111005:
            case 15001004:
            case 35121011:
            case 35121009: // Robo Factory
            case 35121010:
            case 14000027: // Shadow bat
            case 4111007:
            case 4211007:

                return true;
        }
        return false;
    }

    public final boolean isRemovableSummon() {
        switch (skill) {
            case 35111002:
                return true;
        }
        return false;
    }

    public final boolean isAngel() {
        return GameConstants.isAngel(this.skill);
    }

    public final boolean isPuppet() {
        switch (this.skill) {
            case 3111002:
            case 3120012:
            case 3211002:
            case 3220012:
            case 4341006:
            case 13111004:
            case 13111024:
            case 13120007:
            case 33111003:
                return true;
        }
        return isAngel();
    }

    public final int getSummonType() {
        if (((this.skill != 33111003) && (this.skill != 3120012) && (this.skill != 3220012) && (isPuppet()))
                || (this.skill == 33101008) || (this.skill == 35111002) || (this.skill == 35111008)
                || (this.skill == 35120002) || (this.skill == 400011005) || (this.skill == 400031007) || (this.skill == 400051011)) {
            return 0;
        }
        switch (skill) {
            case 152101008:
                return 1;
            case 35121010:
            case 14000027:
            case 14111024:
                return 0;
            case 1301013:
            case 36121014:
                return 2;
            case 23111008:
            case 23111009:
            case 23111010:
            case 35111001:
            case 35111009:
            case 35111010:
                return 3;
            case 35121009:
                return 5;
            case 400041038:
                return 13;
            case 400051009:
                return 15;
            case 35121003:
                return 7;
            case 152001003:
            case 152101000:
            case 400051017:
            case 152121006:
                return 7;
            case 33101010:
            case 33001011:
                return 10;
            case 5221022:
                return 12;
            case 400051038:
            case 400051052:
            case 400051053:
            case 400021073:
            case 400051046:
            case 400021071:
                return 17;
        }
        return 1;
    }

    public final boolean isGaviota() {
        return skill == 5211002;
    }

    public final int getSkillLevel() {
        return skillLevel;
    }

    @Override
    public final MapleMapObjectType getType() {
        return MapleMapObjectType.SUMMON;
    }

    public final void CheckSummonAttackFrequency(final MapleCharacter chr, final int tickcount) {
        Summon_tickResetCount++;
        if (Summon_tickResetCount > 4) {
            Summon_tickResetCount = 0;
        }
    }

    public final MapleCharacter getOwner() {
        return owner;
    }

    public final int getMaelstromId() {
        return maelstromid;
    }

    public final void setMaelstromId(int maelstromid) {
        this.maelstromid = maelstromid;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long i) {
        endTime = i;
    }
}
