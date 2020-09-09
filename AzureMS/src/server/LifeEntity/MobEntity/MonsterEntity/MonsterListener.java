package server.LifeEntity.MobEntity.MonsterEntity;

public interface MonsterListener {

    /**
     *
     * @param monster The monster that was killed
     * @param highestDamageChar The char that did the highest damage to the
     * monster. Can be null if that char is offline.
     */
    public void monsterKilled();
}
