package server.LifeEntity.MobEntity;

import connections.Packets.MainPacketCreator;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;

import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterListener;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterSpawnEntry;
import server.Maps.MapleMapHandling.MapleMap;

public class SpawnPoint extends MonsterSpawnEntry {

    private MapleMonster monster;
    private Point pos;
    private long nextPossibleSpawn;
    private int mobTime;
    private AtomicInteger spawnedMonsters = new AtomicInteger(0);
    private boolean immobile;
    private String msg;
    private int level = -1;

    public SpawnPoint(final MapleMonster monster, final Point pos, final int mobTime, final String msg) {
        this.monster = monster;
        this.pos = pos;
        this.mobTime = mobTime * 1000;
        this.msg = msg;
        this.immobile = !monster.getStats().getMobile();
        this.nextPossibleSpawn = System.currentTimeMillis();
    }

    @Override
    public final MapleMonster getMonster() {
        return monster;
    }

    public int getMobTime() {
        return mobTime;
    }

    @Override
    public final boolean shouldSpawn() {
        if (mobTime < 0) {
            return false;
        }
        // regular spawnpoints should spawn a maximum of 3 monsters; immobile
        // spawnpoints or spawnpoints with mobtime a
        // maximum of 1
        if (((mobTime != 0 || immobile) && spawnedMonsters.get() > 0) || spawnedMonsters.get() > 1) {
            return false;
        }
        return nextPossibleSpawn <= System.currentTimeMillis();
    }

    public final void setLevel(int c) {
        this.level = c;
    }

    @Override
    public final MapleMonster spawnMonster(final MapleMap map) {
        final MapleMonster mob = new MapleMonster(monster);
        mob.setPosition(pos);
        mob.setCy(pos.y);
        mob.setRx0(pos.x - 50);
        mob.setRx1(pos.x + 50);
        if (level > -1) {
            mob.changeLevel(level);
        }
        if (map.getChangeableMobOrigin() != null) {
            mob.changeableMob(map.getChangeableMobOrigin());
        }
        spawnedMonsters.incrementAndGet();
        mob.addListener(new MonsterListener() {

            @Override
            public void monsterKilled() {
                nextPossibleSpawn = System.currentTimeMillis();

                if (mobTime > 0) {
                    nextPossibleSpawn += mobTime;
                }
                spawnedMonsters.decrementAndGet();
            }
        });
        map.spawnMonster(mob, -2);

        if (msg != null) {
            map.broadcastMessage(MainPacketCreator.serverNotice(6, msg));
        }
        return mob;
    }
}
