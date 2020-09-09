package server.Maps;

import client.Character.MapleCharacter;
import client.MapleClient;
import connections.Packets.MainPacketCreator;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapObject.AnimatedHinaMapObjectExtend;

public class MapleDragon extends AnimatedHinaMapObjectExtend {

    private int owner;
    private int jobid;

    public MapleDragon(MapleCharacter owner) {
        super();
        this.owner = owner.getId();
        this.jobid = owner.getJob();
        if (jobid < 2200 || jobid > 2218) {
            throw new RuntimeException("Trying to create a dragon for a non-Evan");
        }
        setPosition(owner.getPosition());
        setStance(4);
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.spawnDragon(this));
    }

    @Override
    public void sendDestroyData(MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.removeDragon(this.owner));
    }

    public int getOwner() {
        return this.owner;
    }

    public int getJobId() {
        return this.jobid;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.DRAGON;
    }
}
