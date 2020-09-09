package server.LifeEntity.MobEntity.MonsterEntity;

import server.Maps.MapleMapHandling.MapleMap;

public abstract class MonsterSpawnEntry {

    public abstract MapleMonster getMonster();

    public abstract boolean shouldSpawn();

    public abstract MapleMonster spawnMonster(MapleMap map);
}
