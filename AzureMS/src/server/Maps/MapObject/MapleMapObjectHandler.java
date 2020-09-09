package server.Maps.MapObject;

import client.Stats.MonsterStatus;
import client.Stats.MonsterStatusEffect;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.ArrowFlatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import connections.Packets.MainPacketCreator;
import launcher.ServerPortInitialize.ChannelServer;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;

public class MapleMapObjectHandler implements Runnable {
    
    public MapleMapObjectHandler() {
        System.out.println("[Notification] The MapleMapObjectHandler thread is up.");
    }
    
    @Override
    public void run() {
        long time = System.currentTimeMillis();

        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            for (Iterator<MapleMap> iterator = cs.getMapFactory().getAllLoadedMaps().iterator(); iterator.hasNext(); ) {
                MapleMap map = iterator.next();
                if (map != null) {
                    for (final ArrowFlatter arrow : map.getArrowFlatter()) {
                        if (time >= arrow.getTime()) {
                            map.broadcastMessage(MainPacketCreator.cancelArrowFlatter(arrow.getObjectId(), arrow.getArrow()));
                            map.removeMapObject(arrow);
                        }
                    }
                    for (final MapleMapObject obj : map.getAllMonster()) {
                        if (((MapleMonster) obj).isAlive()) {
                            List<MonsterStatusEffect> cancelStatus = new ArrayList<MonsterStatusEffect>();
                            for (final Entry<MonsterStatus, MonsterStatusEffect> stat : ((MapleMonster) obj).getStati().entrySet()) {
                                if (stat.getValue().getPoison() != null) {
                                    if (time >= stat.getValue().getPoison().getCheckTime()) {
                                        stat.getValue().getPoison().pdamage(time);
                                    }
                                }
                                if (((MapleMonster) obj).isAlive()) {
                                    if (time >= stat.getValue().getEndTime()) {
                                        cancelStatus.add(stat.getValue());
                                    }
                                }
                            }
                            for (final MonsterStatusEffect cancelStat : cancelStatus) {
                                ((MapleMonster) obj).cancelSingleStatus(cancelStat);
                            }
                            if (cancelStatus.size() > 0) {
                                cancelStatus.clear();
                            }
                        }
                    }
                }
            }
        }
    }
}
