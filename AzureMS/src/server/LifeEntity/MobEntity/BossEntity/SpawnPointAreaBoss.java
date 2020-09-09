package server.LifeEntity.MobEntity.BossEntity;

import connections.Packets.MainPacketCreator;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterListener;
import server.LifeEntity.MobEntity.MonsterEntity.MonsterSpawnEntry;
import server.Maps.MapleMapHandling.MapleMap;
import tools.RandomStream.Randomizer;

public class SpawnPointAreaBoss extends MonsterSpawnEntry {

    private MapleMonster monster;
    private Point pos1;
    private Point pos2;
    private Point pos3;
    private long nextPossibleSpawn;
    private int mobTime;
    private AtomicBoolean spawned = new AtomicBoolean(false);
    private String msg;

    public SpawnPointAreaBoss(final MapleMonster monster, final Point pos1, final Point pos2, final Point pos3,
            final int mobTime, final String msg) {
        this.monster = monster;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
        this.mobTime = mobTime * 1000;
        this.msg = msg;
        this.nextPossibleSpawn = System.currentTimeMillis();
    }

    @Override
    public final MapleMonster getMonster() {
        return monster;
    }

    @Override
    public final boolean shouldSpawn() {
        if (mobTime < 0) {
            return false;
        }
        if (spawned.get()) {
            return false;
        }
        return nextPossibleSpawn <= System.currentTimeMillis();
    }

    @Override
    public final MapleMonster spawnMonster(final MapleMap map) {
        final MapleMonster mob = new MapleMonster(monster);
        final int rand = Randomizer.nextInt(3);
        mob.setPosition(rand == 0 ? pos1 : rand == 1 ? pos2 : pos3);
        spawned.set(true);
        mob.addListener(new MonsterListener() {

            @Override
            public void monsterKilled() {
                nextPossibleSpawn = System.currentTimeMillis();

                if (mobTime > 0) {
                    nextPossibleSpawn += mobTime;
                }
                spawned.set(false);
            }
        });
        map.spawnMonster(mob, -2);

        if (msg != null) {
            map.broadcastMessage(MainPacketCreator.serverNotice(6, msg));
            // map.broadcastMessage(MaplePacketCreator.musicChange("Bgm04/WhiteChristmas_"));
        }
        return mob;
    }
}
