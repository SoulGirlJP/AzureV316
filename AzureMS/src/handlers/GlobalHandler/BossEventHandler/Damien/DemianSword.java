package handlers.GlobalHandler.BossEventHandler.Damien;

import client.MapleClient;
import connections.Packets.DemianPacket;
import server.Maps.MapObject.AbstractHinaMapObject;
import server.Maps.MapObject.MapleMapObjectType;

public class DemianSword extends AbstractHinaMapObject {

    private int attackIdx;
    private int mobId;
    private int cid;
    private int sworldOid;

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.DemianSword;
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        client.sendPacket(DemianPacket.Demian_OnFlyingSwordCreat(true, this));
    }

    @Override
    public void sendDestroyData(MapleClient client) {
        client.sendPacket(DemianPacket.Demian_OnFlyingSwordCreat(false, this));
    }

    public void setSwordOid(int i) {
        this.sworldOid = i;
    }

    public int getSwordOid() {
        return sworldOid;
    }

    public int getAttackIdx() {
        return this.attackIdx;
    }

    public void setAttackIdx(int i) {
        this.attackIdx = i;
    }

    public int getMobId() {
        return this.mobId;
    }

    public void setMobId(int i) {
        this.mobId = i;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int i) {
        this.cid = i;
    }

}
