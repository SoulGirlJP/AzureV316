package server.Maps;

import java.awt.Point;

import client.Character.MapleCharacter;
import connections.Packets.MainPacketCreator;
import tools.Timer.EventTimer;
import launcher.Utility.WorldBroadcasting;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.Maps.MapleMapHandling.MapleMap;

public class AramiaFireWorks {

    private short kegs = 0;
    private static final AramiaFireWorks instance = new AramiaFireWorks();

    private static final int[] arrayMob = {9500168, 9500169, 9500170, 9500171, 9500173, 9500174, 9500175, 9500176,
        9500170, 9500171, 9500172, 9500173, 9500174, 9500175, 9400569};
    private static final int[] arrayX = {2100, 2605, 1800, 2600, 3120, 2700, 2320, 2062, 2800, 3100, 2300, 2840, 2700,
        2320, 1950};
    private static final int[] arrayY = {574, 364, 574, 316, 574, 574, 403, 364, 574, 574, 403, 574, 574, 403, 574};

    public static AramiaFireWorks getInstance() {
        return instance;
    }

    public final void giveKegs(final MapleCharacter c, final int kegs) {
        this.kegs += kegs;

        if (this.kegs >= 2000) {
            this.kegs = 0;
            broadcastEvent(c);
        }
    }

    public final short getKegsPercentage() {
        return (short) ((kegs / 2000) * 10000);
    }

    private void broadcastEvent(final MapleCharacter c) {
        WorldBroadcasting.broadcast(MainPacketCreator.serverNotice(5, "<Channel " + c.getClient().getChannel()
                + "> Aramia from Henesys park will shoot up the firecrackers soon!"));

        // Henesys Park
        EventTimer.getInstance().schedule(new Runnable() {

            @Override
            public final void run() {
                startEvent(c.getClient().getChannelServer().getMapFactory().getMap(100000200));
            }
        }, 10000);
    }

    private void startEvent(final MapleMap map) {
        map.startMapEffect("Who's going crazy with the fireworks?", 5121010);

        EventTimer.getInstance().schedule(new Runnable() {

            @Override
            public final void run() {
                spawnMonster(map);
            }
        }, 5000);
    }

    private void spawnMonster(final MapleMap map) {
        Point pos;

        for (int i = 0; i < arrayMob.length; i++) {
            pos = new Point(arrayX[i], arrayY[i]);
            map.spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(arrayMob[i]), pos);
        }
    }
}
