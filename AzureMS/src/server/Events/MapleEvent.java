package server.Events;

import client.Character.MapleCharacter;
import launcher.ServerPortInitialize.ChannelServer;
import server.Items.RandomRewards;
import server.Maps.MapleMapHandling.MapleMap;
import tools.RandomStream.Randomizer;

public abstract class MapleEvent {

    protected MapleEventType type;
    protected int channel, playerCount = 0;
    protected boolean isRunning = false;

    public MapleEvent(final int channel, final MapleEventType type) {
        this.channel = channel;
        this.type = type;
    }

    public void incrementPlayerCount() {
        playerCount++;
        if (playerCount == 250) {
            setEvent(ChannelServer.getInstance(channel), true);
        }
    }

    public MapleEventType getType() {
        return type;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public MapleMap getMap(final int i) {
        return getChannelServer().getMapFactory().getMap(type.mapids[i]);
    }

    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(channel);
    }

    public void broadcast(final byte[] packet) {
        for (int i = 0; i < type.mapids.length; i++) {
            getMap(i).broadcastMessage(packet);
        }
    }

    public static void givePrize(final MapleCharacter chr) {
        final int reward = RandomRewards.getEventReward();
        if (reward == 0) {
            final int mes = Randomizer.nextInt(9000000) + 1000000;
            chr.gainMeso(mes, true, false);
            chr.dropMessage(5, "You gained " + mes + " Mesos.");
        } else if (reward == 1) {
            final int cs = Randomizer.nextInt(4000) + 1000;
            chr.modifyCSPoints(1, cs, true);
            chr.dropMessage(5, "You gained " + (cs / 2) + " cash.");
        } else if (reward == 2) {
            chr.dropMessage(5, "You gained 1 Vote Point.");
        } else if (reward == 3) {
            chr.addFame(10);
            chr.dropMessage(5, "You gained 10 Fame.");
        } else if (reward == 4) {
            chr.dropMessage(5, "There was no reward.");
        } else {
            int max_quantity = 1;
            switch (reward) {
                case 5062000:
                    max_quantity = 3;
                    break;
                case 5220000:
                    max_quantity = 25;
                    break;
                case 4031307:
                case 5050000:
                    max_quantity = 5;
                    break;
                case 2022121:
                    max_quantity = 10;
                    break;
            }
            final int quantity = (max_quantity > 1 ? Randomizer.nextInt(max_quantity) : 0) + 1;
        }
    }

    public abstract void finished(MapleCharacter chr);

    public abstract void startEvent();

    public void onMapLoad(MapleCharacter chr) {
    }

    public void warpBack(MapleCharacter chr) {
    }

    public void reset() {
        isRunning = true;
        playerCount = 0;
    }

    public void unreset() {
        isRunning = false;
        playerCount = 0;
    }

    public static void setEvent(final ChannelServer cserv, final boolean auto) {
    }

    public static void mapLoad(final MapleCharacter chr, final int channel) {
    }

    public static void onStartEvent(final MapleCharacter chr) {
    }

    public static String scheduleEvent(final MapleEventType event, final ChannelServer cserv) {
        return null;
    }
}
