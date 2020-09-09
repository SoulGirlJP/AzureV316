package server.LifeEntity.MobEntity;

import client.Character.MapleCharacter;
import client.Stats.DiseaseStats;
import client.Stats.MonsterStatus;
import connections.Packets.BossPacket;
import connections.Packets.MobPacket;
import connections.Packets.UIPacket;
import constants.GameConstants;
import tools.ArrayMap;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.LifeEntity.MobEntity.BossEntity.ButterFly;
import server.LifeEntity.MobEntity.BossEntity.FairyDust;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.Maps.MapField.FieldLucid;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleMist;
import server.Maps.ObtacleAtom;
import tools.RandomStream.Randomizer;
import tools.Timer.MapTimer;

public class MobSkill {

    private int skillId, skillLevel, mpCon, spawnEffect, hp, x, y;
    private long duration, cooltime;
    private float prop;
    private short limit;
    private List<Integer> toSummon = new ArrayList<Integer>();
    private Point lt, rb;
    private List<Integer> monsterId = new ArrayList<>();
    private int skillAfter;

    public MobSkill(int skillId, int level) {
        this.skillId = skillId;
        this.skillLevel = level;
    }

    public void setMpCon(int mpCon) {
        this.mpCon = mpCon;
    }

    public void addSummons(List<Integer> toSummon) {
        this.toSummon = toSummon;
    }

    public void setSpawnEffect(int spawnEffect) {
        this.spawnEffect = spawnEffect;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setCoolTime(long cooltime) {
        this.cooltime = cooltime;
    }

    public void setProp(float prop) {
        this.prop = prop;
    }

    public void setLtRb(Point lt, Point rb) {
        this.lt = lt;
        this.rb = rb;
    }

    public void setSkillAfter(int i) {
        this.skillAfter = i;
    }

    public int getSkillAfter() {
        return this.skillAfter;
    }

    public void setMobSkillDelay(MapleCharacter chr, MapleMonster monster, int s, short option) {
        List<Rectangle> skillRectInfo = null;
        chr.getClient().sendPacket(MobPacket.MobSkillDelay(monster.getObjectId(), skillId, skillLevel, skillAfter, option, skillRectInfo));
    }

    public void setLimit(short limit) {
        this.limit = limit;
    }

    public void addMonster(int id) {
        this.monsterId.add(id);
    }

    public boolean checkCurrentBuff(MapleCharacter player, MapleMonster monster) {
        boolean stop = false;
        switch (skillId) {
            case 100:
            case 110:
                stop = monster.isBuffed(MonsterStatus.WEAPON_ATTACK_UP);
                break;
            case 101:
            case 111:
                stop = monster.isBuffed(MonsterStatus.MAGIC_ATTACK_UP);
                break;
            case 102:
            case 112:
                stop = monster.isBuffed(MonsterStatus.WEAPON_DEFENSE_UP);
                break;
            case 103:
            case 113:
                stop = monster.isBuffed(MonsterStatus.MAGIC_DEFENSE_UP);
                break;
            case 140:
            case 141:
            case 143:
            case 144:
            case 145:
                stop = monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) || monster.isBuffed(MonsterStatus.WEAPON_IMMUNITY);
                break;
            case 200:
                stop = player.getMap().getAllMonster().size() >= limit;
                break;
        }
        return stop;
    }

    public void SkillMessage(MapleMonster monster) {
        switch (skillId) {
            case 238:
                switch (this.skillLevel) {
                    case 7:
                        monster.getMap().broadcastMessage(UIPacket.enforceMSG("Lucid summoned a powerful servitor!", 222, 2000));
                        break;
                }
                break;
        }
    }

    public void applyEffect(MapleCharacter player, MapleMonster monster, boolean skill) {
        DiseaseStats disease = null;
        Map<MonsterStatus, Integer> stats = new ArrayMap<MonsterStatus, Integer>();
        List<Integer> reflection = new LinkedList<Integer>();
        switch (skillId) {
            case 100:
            case 110:
                stats.put(MonsterStatus.WEAPON_ATTACK_UP, Integer.valueOf(x));
                break;
            case 101:
            case 111:
                stats.put(MonsterStatus.MAGIC_ATTACK_UP, Integer.valueOf(x));
                break;
            case 102:
            case 112:
                stats.put(MonsterStatus.WEAPON_DEFENSE_UP, Integer.valueOf(x));
                break;
            case 103:
            case 113:
                stats.put(MonsterStatus.MAGIC_DEFENSE_UP, Integer.valueOf(x));
                break;
            case 114:
                if (lt != null && rb != null && skill) {
                    List<MapleMapObject> objects = getObjectsInRange(monster, MapleMapObjectType.MONSTER);
                    final int hp = (getX() / 1000) * (int) (950 + 1050 * Math.random());
                    for (MapleMapObject mons : objects) {
                        ((MapleMonster) mons).heal(hp, getY(), true);
                    }
                } else {
                    monster.heal(getX(), getY(), true);
                }
                break;
            case 120: // a
                disease = DiseaseStats.SEAL;
                break;
            case 121: // a
                disease = DiseaseStats.DARKNESS;
                break;
            case 122: // a
                disease = DiseaseStats.WEAKEN;
                break;
            case 123: // a
                disease = DiseaseStats.STUN;
                break;
            case 124:
                disease = DiseaseStats.CURSE;
                break;
            case 125:
                disease = DiseaseStats.POISON;
                break;
            case 126:
                disease = DiseaseStats.SLOW;
                break;
            case 127:
                if (lt != null && rb != null && skill) {
                    for (MapleCharacter character : getPlayersInRange(monster, player)) {
                        character.dispel();
                    }
                } else {
                    player.dispel();
                }
                break;
            case 128:
                disease = DiseaseStats.SEDUCE;
                break;
            case 129:
                final BanishInfo info = monster.getStats().getBanishInfo();
                if (lt != null && rb != null && skill) {
                    for (MapleCharacter chr : getPlayersInRange(monster, player)) {
                        chr.changeMapBanish(info.getMap(), info.getPortal(), info.getMsg());
                    }
                } else {
                    player.changeMapBanish(info.getMap(), info.getPortal(), info.getMsg());
                }
                break;
            case 131:
                monster.getMap().spawnMist(new MapleMist(calculateBoundingBox(monster.getPosition(), true), monster, this, monster.getPosition()), x * 10, false, false, false, false, false);
                break;
            case 132:
                disease = DiseaseStats.REVERSE_DIRECTION;
                break;
            case 133:
                disease = DiseaseStats.ZOMBIFY;
                break;
            case 134:
                disease = DiseaseStats.POTION;
                break;
            case 135:
                disease = DiseaseStats.SHADOW;
                break;
            case 136: //a
                disease = DiseaseStats.BLIND;
                break;
            case 137:
                disease = DiseaseStats.FREEZE;
                break;
            case 140:
                if (makeChanceResult()) {
                    stats.put(MonsterStatus.WEAPON_IMMUNITY, Integer.valueOf(x));
                }
                break;
            case 141:
                if (makeChanceResult()) {
                    stats.put(MonsterStatus.MAGIC_IMMUNITY, Integer.valueOf(x));
                }
                break;
            case 143:
                stats.put(MonsterStatus.WEAPON_DAMAGE_REFLECT, Integer.valueOf(x));
                stats.put(MonsterStatus.WEAPON_IMMUNITY, Integer.valueOf(x));
                reflection.add(x);
                break;
            case 144:
                stats.put(MonsterStatus.MAGIC_DAMAGE_REFLECT, Integer.valueOf(x));
                stats.put(MonsterStatus.MAGIC_IMMUNITY, Integer.valueOf(x));
                reflection.add(x);
                break;
            case 145:
                stats.put(MonsterStatus.WEAPON_DAMAGE_REFLECT, Integer.valueOf(x));
                stats.put(MonsterStatus.WEAPON_IMMUNITY, Integer.valueOf(x));
                stats.put(MonsterStatus.MAGIC_DAMAGE_REFLECT, Integer.valueOf(x));
                stats.put(MonsterStatus.MAGIC_IMMUNITY, Integer.valueOf(x));
                reflection.add(x);
                break;
            case 184: // Half and half forced position shift
                disease = DiseaseStats.TELEPORT;
                this.setX(player.getPosition().x);
                this.setY(player.getPosition().y);
                break;
            case 191: // // Banban Timewarp
                Point posFrom = monster.getPosition();
                Point mylt = new Point(lt.x + posFrom.x, lt.y + posFrom.y);
                Point myrb = new Point(rb.x + posFrom.x, rb.y + posFrom.y);
                Rectangle box = new Rectangle(posFrom.x, posFrom.y, myrb.x - mylt.x, myrb.y - mylt.y);
                MapleMist clock = new MapleMist(box, monster, this, monster.getPosition());
                clock.setClockType(Randomizer.rand(1, 2));
                monster.getMap().spawnClockMist(clock);
                monster.getMap().broadcastMessage(UIPacket.showInfo("'Crack' occurred in the gap of time."));
                break;
            case 201:
                int Xmin = monster.getPosition().x + lt.x;
                int Xmax = monster.getPosition().x + rb.x;
                int Ymin = monster.getPosition().y + lt.y;
                int Ymax = monster.getPosition().y + lt.y;
                int max = monsterId.size();
                if (skillLevel == 197) {
                    for (MapleMapObject ob : player.getMap().getAllMonster()) {
                        if (monsterId.contains(((MapleMonster) ob).getId())) {
                            max--;
                        }
                    }
                }
                if (monsterId.contains(8880160) || monsterId.contains(8880161) || monsterId.contains(8880170) || monsterId.contains(8880171)) {
                    int cc = 0;
                    for (MapleMapObject ob : player.getMap().getAllMonster()) {
                        int mobid = ((MapleMonster) ob).getId();
                        if (mobid == 8880160 || mobid == 8880161 || mobid == 8880170 || mobid == 8880171) {
                            cc++;
                        }
                    }
                    if (cc >= 5) {
                        max = 0;
                    }
                }
                for (int i = 0; i < max; i++) {
                    Point pos = new Point(Randomizer.rand(Xmin, Xmax), Randomizer.rand(Ymin, Ymax));
                    monster.getMap().spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(monsterId.get(i)), pos);
                }
                if (monsterId.contains(8880160) || monsterId.contains(8880161) || monsterId.contains(8880170) || monsterId.contains(8880171)) {
                    for (MapleMapObject ob : player.getMap().getAllMonster()) {
                        if (((MapleMonster) ob).getId() == 8880160) {
                            monster.getMap().killMonster((MapleMonster) ob);
                        }
                    }
                }
                break;
            case 234:
                break;
            case 215:
                switch (skillLevel) {
                    case 2:
                        long end = System.currentTimeMillis() + 10000;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 4; i++) {
                                    List<ObtacleAtom> olist = new ArrayList<>();
                                    int rand = Randomizer.rand(300, 500);
                                    int xMin = monster.getPosition().x - rand;
                                    int xMax = monster.getPosition().x + rand;
                                    int x = monster.getPosition().x;

                                    olist.add(new ObtacleAtom(58, x, -498, xMax, 16, 50, 90, 0, 790, 0, 71, 2, 514, 323));
                                    olist.add(new ObtacleAtom(58, x, -498, x, 16, 50, 90, 0, 790, 0, 71, 2, 514, 0));
                                    olist.add(new ObtacleAtom(58, x, -498, x + 100, 16, 50, 90, 0, 790, 0, 71, 2, 514, 0));
                                    olist.add(new ObtacleAtom(58, x, -498, xMin, 16, 50, 90, 0, 790, 0, 71, 2, 514, 36));
                                    monster.getMap().broadcastMessage(MobPacket.spawnObtacleAtomBomb(monster.getId(), 4, olist, Randomizer.nextInt()));
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(MobSkill.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                try {
                                    Thread.sleep(4000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(MobSkill.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }).start();
                        break;
                }
                break;
            case 238:
                if (!GameConstants.isLucidMap(player.getMapId())) {
                    return;
                }
                MapleMap fieldLucid = player.getMap();
                switch (skillLevel) {
                    case 1:
                    case 2:
                    case 3:
                        fieldLucid.broadcastMessage(BossPacket.doFlowerTrapSkill(skillLevel, Randomizer.nextInt(3), 1000, 48, Randomizer.nextBoolean()));
                        break;
                    case 4:
                    case 10:
                        List<FairyDust> fairyDust = new ArrayList<FairyDust>() {
                            {
                                int x, s, s2, v, v2, w, w2, u = 2640;
                                if (skillLevel == 4) {
                                    s = 180;
                                    s2 = 240;
                                    v = 100;
                                    v2 = 5;
                                    w = 3;
                                    w2 = 1;
                                    x = 40;
                                } else {
                                    s = 30;
                                    s2 = 330;
                                    v = 250;
                                    v2 = 100;
                                    w = 6;
                                    w2 = 3;
                                    x = 5;
                                }
                                for (int i = 0, max = Randomizer.rand(w, w + w2); i < max; i++) {
                                    x += Randomizer.nextInt(x);
                                    add(new FairyDust(Randomizer.nextInt(3), u, v + Randomizer.nextInt(v2), x + Randomizer.rand(s, s2)));
                                }
                            }
                        };
                        fieldLucid.broadcastMessage(BossPacket.doFairyDustSkill(skillLevel, fairyDust));
                        break;
                    case 5:
                        List<Integer> laserIntervals = new ArrayList<Integer>() {
                            {
                                for (int i = 0; i < 15; i++) {
                                    add(500);
                                }
                            }
                        };
                        fieldLucid.broadcastMessage(BossPacket.doLaserRainSkill(4500, laserIntervals));
                        break;
                    case 6:
                        player.getClient().sendPacket(BossPacket.doForcedTeleportSkill(Randomizer.nextInt(8)));
                        break;
                    case 7:
                        boolean isLeft = Randomizer.isSuccess(70);
                        if (fieldLucid.getId() == 450004150) {
                            fieldLucid.broadcastMessage(BossPacket.createDragon(1, 0, 0, 0, 0, isLeft));
                        } else {
                            int createPosX = isLeft ? -138 : 1498;
                            int createPosY = Randomizer.nextBoolean() ? -1312 : 238;
                            int posX = createPosX;
                            int posY = monster.getPosition().y;
                            fieldLucid.broadcastMessage(BossPacket.createDragon(2, posX, posY, createPosX, createPosY, isLeft));
                        }
                        break;
                    case 8:
                        fieldLucid.broadcastMessage(BossPacket.doRushSkill());
                        break;
                    case 9:
                        fieldLucid.setLucidState(FieldLucid.LucidState.DO_SKILL);
                        fieldLucid.broadcastMessage(BossPacket.setButterflyAction(ButterFly.Mode.MOVE, 700, -600));
                        fieldLucid.removeAllMobsExcept(Collections.singletonList(monster));
                        fieldLucid.broadcastMessage(BossPacket.doWelcomeBarrageSkill(2));
                        fieldLucid.broadcastMessage(BossPacket.setStainedGlassOnOff(false, FieldLucid.STAINED_GLASS));
                        fieldLucid.broadcastMessage(BossPacket.setFlyingMode(true));
                        fieldLucid.broadcastMessage(BossPacket.doBidirectionShoot(50, 120, 500, 3));
                        fieldLucid.broadcastMessage(BossPacket.doSpiralShoot(180, 150, 30, 30, 700, 12, 5, 1));
                        fieldLucid.broadcastMessage(BossPacket.doBidirectionShoot(50, 100, 500, 4));
                        fieldLucid.broadcastMessage(BossPacket.doSpiralShoot(180, 150, 30, 70, 1000, 12, 10, 0));
                        fieldLucid.broadcastMessage(BossPacket.doBidirectionShoot(50, 90, 700, 8));
                        fieldLucid.broadcastMessage(BossPacket.doSpiralShoot(180, 100, 30, 100, 700, 12, 0, 0));
                        MapTimer.getInstance().schedule(() -> fieldLucid.setLucidState(FieldLucid.LucidState.END_SKILL), 15700);
                        break;
                }
                break;
            case 200:
                for (Integer mobId : getSummons()) {
                    final MapleMonster toSpawn = MapleLifeProvider.getMonster(mobId);
                    toSpawn.setPosition(monster.getPosition());
                    int ypos = (int) monster.getPosition().getY(), xpos = (int) monster.getPosition().getX();
                    switch (mobId) {
                        case 8500003:
                            toSpawn.setFh((int) Math.ceil(Math.random() * 19.0));
                            ypos = -590;
                            break;
                        case 8500004:
                            xpos = (int) (monster.getPosition().getX() + Math.ceil(Math.random() * 1000.0) - 500);
                            ypos = (int) monster.getPosition().getY();
                            break;
                        case 8510100:
                            if (Math.ceil(Math.random() * 5) == 1) {
                                ypos = 78;
                                xpos = (int) (0 + Math.ceil(Math.random() * 5)) + ((Math.ceil(Math.random() * 2) == 1) ? 180 : 0);
                            } else {
                                xpos = (int) (monster.getPosition().getX() + Math.ceil(Math.random() * 1000.0) - 500);
                            }
                            break;
                    }
                    switch (monster.getMap().getId()) {
                        case 220080001:
                            if (xpos < -890) {
                                xpos = (int) (-890 + Math.ceil(Math.random() * 150));
                            } else if (xpos > 230) {
                                xpos = (int) (230 - Math.ceil(Math.random() * 150));
                            }
                            break;
                        case 230040420:
                            if (xpos < -239) {
                                xpos = (int) (-239 + Math.ceil(Math.random() * 150));
                            } else if (xpos > 371) {
                                xpos = (int) (371 - Math.ceil(Math.random() * 150));
                            }
                            break;
                    }
                    monster.getMap().spawnMonsterWithEffect(toSpawn, getSpawnEffect(), monster.getMap().calcPointMaple(new Point(xpos, ypos - 1)));
                }
                break;
        }

        if (stats.size() > 0) {
            if (lt != null && rb != null && skill) {
                for (MapleMapObject mons : getObjectsInRange(monster, MapleMapObjectType.MONSTER)) {
                    ((MapleMonster) mons).applyMonsterBuff(stats, getX(), getSkillId(), getDuration(), this, reflection);
                }
            } else {
                monster.applyMonsterBuff(stats, getX(), getSkillId(), getDuration(), this, reflection);
            }
        }
        if (disease != null) {
            if (lt != null && rb != null && skill) {
                for (MapleCharacter chr : getPlayersInRange(monster, player)) {
                    chr.giveDebuff(disease, this);
                }
            } else {
                player.giveDebuff(disease, this);
            }
        }
        monster.setMp(monster.getMp() - getMpCon());
    }

    public int getSkillId() {
        return skillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getMpCon() {
        return mpCon;
    }

    public List<Integer> getSummons() {
        return Collections.unmodifiableList(toSummon);
    }

    public int getSpawnEffect() {
        return spawnEffect;
    }

    public int getHP() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getDuration() {
        return duration;
    }

    public long getCoolTime() {
        return cooltime;
    }

    public Point getLt() {
        return lt;
    }

    public Point getRb() {
        return rb;
    }

    public int getLimit() {
        return limit;
    }

    public boolean makeChanceResult() {
        return prop == 1.0 || Math.random() < prop;
    }

    public Rectangle calculateBoundingBox(Point posFrom, boolean facingLeft) {
        Point mylt, myrb;
        if (facingLeft) {
            mylt = new Point(lt.x + posFrom.x, lt.y + posFrom.y);
            myrb = new Point(rb.x + posFrom.x, rb.y + posFrom.y);
        } else {
            myrb = new Point(lt.x * -1 + posFrom.x, rb.y + posFrom.y);
            mylt = new Point(rb.x * -1 + posFrom.x, lt.y + posFrom.y);
        }
        final Rectangle bounds = new Rectangle(mylt.x, mylt.y, myrb.x - mylt.x, myrb.y - mylt.y);
        return bounds;
    }

    private List<MapleCharacter> getPlayersInRange(MapleMonster monster, MapleCharacter player) {
        final Rectangle bounds = calculateBoundingBox(monster.getPosition(), monster.isFacingLeft());
        List<MapleCharacter> players = new ArrayList<MapleCharacter>();
        players.add(player);
        return monster.getMap().getPlayersInRect(bounds, players);
    }

    private List<MapleMapObject> getObjectsInRange(MapleMonster monster, MapleMapObjectType objectType) {
        final Rectangle bounds = calculateBoundingBox(monster.getPosition(), monster.isFacingLeft());
        List<MapleMapObjectType> objectTypes = new ArrayList<MapleMapObjectType>();
        objectTypes.add(objectType);
        return monster.getMap().getMapObjectsInRect(bounds, objectTypes);
    }
}
