package handlers.GlobalHandler.BossEventHandler;

import client.MapleClient;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import scripting.EventManager.EventInstanceManager;
import scripting.EventManager.EventManager;
import server.Maps.MapleMapHandling.MapleMap;

public class AswanHandler {

    private static EventInstanceManager eim;
    private static EventManager em;

    public static void EnterAswan(final MapleClient c) {
        AswanWarp(c, 262000300);
    }

    public static void EnterAswanField(final ReadingMaple rm, final MapleClient c) {
        em = c.getChannelServer().getEventSM().getEventManager("AswanEvent");
        byte type; // Occupy = 0, Attack = 1, Defense = 2, Supply = 3
        byte mode; // EASY=0, NORMAL=1, HARD=2, HELL=3
        byte count; // SOLO=0, PARTY=1
        type = rm.readByte();
        mode = rm.readByte();
        count = rm.readByte();
        // final List<Integer> maps = new ArrayList<Integer>();

        switch (type) {
            case 0: // Occupation
                if (mode == 0) { // EASY
                    if (count == 0) { // SOLO
                        // eim = em.readyInstance();
                        eim.setProperty("Global_StartMap", 262020000 + "");
                        eim.setProperty("Global_ExitMap", 262000000 + "");
                        eim.setProperty("Global_MinPerson", 1 + "");
                        eim.setProperty("Global_RewardMap", 262000000 + "");
                        eim.setProperty("CurrentStage", "1");
                        eim.startEventTimer(1200000);
                        eim.registerPlayer(c.getPlayer());
                        // prepareAswanMob(c, 955000100, eim);
                    } else { // PARTY
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    }
                } else if (mode == 1) { // NORMAL
                    if (count == 0) { // SOLO
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    } else { // PARTY
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    }
                } else if (mode == 2) { // HARD
                    if (count == 0) { // SOLO
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    } else { // PARTY
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    }
                } else if (mode == 3) { // HELL
                    if (count == 0) { // SOLO
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    } else { // PARTY
                        c.send(MainPacketCreator.getGMText(7, "Sorry. Currently implementing."));
                        c.send(MainPacketCreator.resetActions(c.getPlayer()));
                    }
                }
                break;
            case 1: // Attack
                break;
            case 2: // Defense
                break;
            case 3: // Supply
                break;
            default: // Otherwise
                break;
        }
    }

    public static void RequestAswanDead(MapleClient c) {
        long now = System.currentTimeMillis();
        if (now >= c.getPlayer().BattleUserRespawnUI) {
            c.getPlayer().addHP(c.getPlayer().getStat().getCurrentMaxHp());
            AswanWarp(c, c.getPlayer().getMapId());
            if (c.getPlayer().bossDeathCount > 0) {
                c.getPlayer().bossDeathCount--;
                c.sendPacket(MainPacketCreator.getDeathCount(c.getPlayer().bossDeathCount));
            }
        }
        /*
		 * em = c.getChannelServer().getEventSM().getEventManager("AswanEvent");
		 * c.getPlayer().updateSingleStat(PlayerStat.HP, 50); AswanWarp(c,
		 * c.getPlayer().getMapId());
		 * c.getSession().writeAndFlush(MainPacketCreator.resetActions());
		 * eim.registerPlayer(c.getPlayer());
         */
    }

    public static void AswanWarp(MapleClient c, int mapid) {
        c.getPlayer().changeMap(c.getChannelServer().getMapFactory().getMap(mapid),
                c.getChannelServer().getMapFactory().getMap(mapid).getPortal(0));
    }

    public static void prepareAswanMob(MapleClient c, int mapid, EventInstanceManager eim) {
        MapleMap map = eim.getMapFactory().getMap(mapid);
        if (c.getPlayer().getParty() != null) {
            map.setChangeableMobOrigin(ChannelServer.getInstance(c.getChannel()).getPlayerStorage()
                    .getCharacterById(c.getPlayer().getParty().getLeader().getId()));
        } else {
            map.setChangeableMobOrigin(c.getPlayer());
        }
        map.killAllMonsters(false);
        map.respawn(true);
    }
}
