package server.Maps;

import java.awt.Point;
import java.awt.Rectangle;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.Skills.ISkill;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import connections.Packets.MainPacketCreator;
import server.LifeEntity.MobEntity.MobSkill;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.MapObject.AbstractHinaMapObject;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.MapleMapObjectType;

public class MapleMist extends AbstractHinaMapObject {

    private Rectangle mistPosition;
    private MapleCharacter owner = null;
    private MapleMonster mob = null;
    private SkillStatEffect source;
    private MobSkill skill;
    private boolean isMobMist, isPoisonMist, isShelter, isRecovery, isBurningRegion, isTimeCapsule;
    private int skillDelay, skilllevel;
    private int clockType; // ¹Ý¹Ý
    private boolean isUsed;
    private Point position;
    private long endTime = -1;
    private MapleMap map;

    public MapleMist(Rectangle mistPosition, MapleMonster mob, MobSkill skill, Point position) {
        this.mistPosition = mistPosition;
        this.mob = mob;
        this.skill = skill;
        skilllevel = skill.getSkillId();
        isMobMist = true;
        isPoisonMist = true;
        isShelter = true;
        isBurningRegion = false;
        isTimeCapsule = false;
        isRecovery = true;
        skillDelay = 0;
        clockType = -1;
        isUsed = false;
    }

    public MapleMist(Rectangle mistPosition, MapleCharacter owner, SkillStatEffect source, int skilllevel, Point position) {
        this.mistPosition = mistPosition;
        this.owner = owner;
        this.source = source;
        this.skilllevel = skilllevel;
        this.position = position;
        switch (source.getSourceId()) {
            case 4121015:
            case 4221006:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 8;
                break;
            case 1076:
            case 11076:
            case 2111003:
            case 2100010:
            case 14111006:
            case 61121105:
                isMobMist = false;
                isPoisonMist = true;
                isShelter = false;
                isRecovery = false;
                skillDelay = source.getSourceId() == 2100010 ? 6 : 0;
                break;
            case 32121006:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = true;
                isRecovery = false;
                skillDelay = 11;
                break;
            case 22161003:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = true;
                skillDelay = 8;
                break;
            case 100001261:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 2;
                break;
            case 12121005: // Burning Region
                skillDelay = 2;
                isBurningRegion = true;
                break;
            case 36121007: // Time capsule
                skillDelay = 15;
                isTimeCapsule = true;
                break;
            case 2201009: //Chilling Step
                isMobMist = true;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 3;
                break;
        }
    }

    public MapleMist(Rectangle mistPosition, MapleCharacter owner, SkillStatEffect source) {
        this.mistPosition = mistPosition;
        this.owner = owner;
        this.source = source;
        this.skillDelay = 8;
        this.isMobMist = false;
        this.skilllevel = owner.getSkillLevel(SkillFactory.getSkill(source.getSourceId() == 400051025 || source.getSourceId() == 400051026 ? 400051024 : source.getSourceId()));
        switch (source.getSourceId()) {
            case 24120055:
            case 24121052:
                this.isMobMist = false;
                this.isShelter = false;
                this.isPoisonMist = false;
                this.skillDelay = 16;
                break;
            case 4121015:
            case 4221006:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 8;
                break;
            case 14111006:
            case 1076:
            case 11076:
            case 2111003:
            case 61121105:
                isMobMist = false;
                isPoisonMist = true;
                isShelter = false;
                isRecovery = false;
                skillDelay = 8;
                break;
            case 32121006:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = true;
                isRecovery = false;
                skillDelay = 11;
                break;
            case 22161003:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = true;
                skillDelay = 8;
                break;
            case 2201009:
                isMobMist = true;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 3;
                break;
            case 100001261:
                isMobMist = false;
                isPoisonMist = false;
                isShelter = false;
                isRecovery = false;
                skillDelay = 2;
                break;
            case 12121005:
                skillDelay = 2;
                isBurningRegion = true;
                break;
            case 36121007:
                skillDelay = 15;
                isTimeCapsule = true;
                break;
            case 400031037:
            case 400031039:
            case 400031040: {
                skillDelay = 6;
                break;
        }
             
        }
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.MIST;
    }

    @Override
    public Point getPosition() {
        return mistPosition.getLocation();
    }

    public void setEndTime(int i) {
        endTime = System.currentTimeMillis() + i;
    }

    public long getEndTime() {
        return endTime;
    }

    public ISkill getSourceSkill() {
        if (source == null) {
            return null;
        }
        return SkillFactory.getSkill(source.getSourceId());
    }

    public SkillStatEffect getSource() {
        return source;
    }

    public boolean isMobMist() {
        return isMobMist;
    }

    public boolean isPoisonMist() {
        return isPoisonMist;
    }

    public boolean isShelter() {
        return isShelter;
    }

    public boolean isBurningRegion() {
        return isBurningRegion;
    }

    public boolean isTimeCapsule() {
        return isTimeCapsule;
    }

    public boolean isRecovery() {
        return isRecovery;
    }

    public void setMap(final MapleMap map) {
        this.map = map;
    }

    public void removeMist() {
        map.broadcastMessage(MainPacketCreator.removeMist(getObjectId(), false));
        map.removeMapObject(this);
        if (getOwner() != null) {
            getOwner().removeVisibleMapObject(this);
        }
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        this.isUsed = used;
    }

    public int getSkillDelay() {
        return skillDelay;
    }

    public int getSkillLevel() {
        return skilllevel;
    }

    public MapleMonster getMobOwner() {
        return mob;
    }

    public MapleCharacter getOwner() {
        return owner;
    }

    public MobSkill getMobSkill() {
        return this.skill;
    }

    public Rectangle getBox() {
        return mistPosition;
    }

    public int getClockType() {
        return clockType;
    }

    public void setClockType(int clockType) {
        this.clockType = clockType;
    }

    public boolean isClock() {
        return clockType != -1;
    }

    @Override
    public void setPosition(Point position) {
    }

    public byte[] fakeSpawnData(int level) {
        if (owner != null) {
            return MainPacketCreator.spawnMist(this);
        }
        return MainPacketCreator.spawnMist(this);
    }

    @Override
    public void sendSpawnData(final MapleClient c) {
        c.getSession().writeAndFlush(getClockType() > 0 ? MainPacketCreator.spawnClockMist(this) : MainPacketCreator.spawnMist(this));
    }

    @Override
    public void sendDestroyData(final MapleClient c) {
        c.getSession().writeAndFlush(MainPacketCreator.removeMist(getObjectId(), source.isMistEruption()));
    }

    public boolean makeChanceResult() {
        return source.makeChanceResult();
    }

    public Point getTruePosition() {
        return position;
    }
}
