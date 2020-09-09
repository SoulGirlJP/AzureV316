package server.Maps;

import java.awt.Point;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import client.Character.MapleCharacter;
import client.Community.MapleParty.MaplePartyCharacter;
import client.MapleClient;
import connections.Packets.MainPacketCreator;
import server.Maps.MapObject.AbstractHinaMapObject;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapleMapHandling.MaplePortal;

public class MapleDoor extends AbstractHinaMapObject {

    private WeakReference<MapleCharacter> owner;
    private MapleMap town;
    private MaplePortal townPortal;
    private MapleMap target;
    private Point targetPosition;
    private boolean isTown;

    public MapleDoor(final MapleCharacter owner, final Point targetPosition) {
        super();
        this.owner = new WeakReference<MapleCharacter>(owner);
        this.target = owner.getMap();
        this.targetPosition = targetPosition;
        setPosition(this.targetPosition);
        this.town = this.target.getReturnMap();
        this.townPortal = getFreePortal();
        this.isTown = false;
    }

    public MapleDoor(final MapleDoor origDoor) {
        super();
        this.owner = origDoor.owner;
        this.town = origDoor.town;
        this.townPortal = origDoor.townPortal;
        this.target = origDoor.target;
        this.targetPosition = origDoor.targetPosition;
        this.townPortal = origDoor.townPortal;
        setPosition(townPortal.getPosition());
        isTown = true;
    }

    private final MaplePortal getFreePortal() {
        MapleCharacter realOwner = owner.get();
        if (realOwner != null) {
            final List<MaplePortal> freePortals = new ArrayList<MaplePortal>();

            for (final MaplePortal port : town.getPortals()) {
                if (port.getType() == 6) {
                    freePortals.add(port);
                }
            }
            Collections.sort(freePortals, new Comparator<MaplePortal>() {

                @Override
                public final int compare(final MaplePortal o1, final MaplePortal o2) {
                    if (o1.getId() < o2.getId()) {
                        return -1;
                    } else if (o1.getId() == o2.getId()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
            for (final MapleMapObject obj : town.getAllDoor()) {
                final MapleDoor door = (MapleDoor) obj;
                if (door.getOwner().getParty() != null
                        && realOwner.getParty().containsMembers(new MaplePartyCharacter(door.getOwner()))) {
                    freePortals.remove(door.getTownPortal());
                }
            }

            return freePortals.iterator().next();
        }
        return null;
    }

    @Override
    public final void sendSpawnData(final MapleClient client) {
        sendSpawnData(client, false);
    }

    public final void sendSpawnData(final MapleClient client, boolean animated) {
        if (getOwner() == null || target == null || client.getPlayer() == null) {
            return;
        }
        if (target.getId() == client.getPlayer().getMapId() || getOwner().getId() == client.getPlayer().getId()
                || (getOwner() != null && getOwner().getParty() != null && client.getPlayer().getParty() != null
                && getOwner().getParty().getId() == client.getPlayer().getParty().getId())) {
            if (!isTown) {
                client.getPlayer().getMap().broadcastMessage(client.getPlayer(), MainPacketCreator.spawnDoor(
                        getOwner().getId(),
                        target.getId() == client.getPlayer().getMapId() ? targetPosition : townPortal.getPosition(),
                        animated), false); // spawnDoor always has same position.
            }
            if (getOwner() != null && getOwner().getParty() != null && client.getPlayer().getParty() != null
                    && (getOwner().getId() == client.getPlayer().getId()
                    || getOwner().getParty().getId() == client.getPlayer().getParty().getId())) {
                // client.getPlayer().getMap().broadcastMessage(client.getPlayer(),
                // MainPacketCreator.partyPortal(town.getId(), target.getId(), 2311002,
                // target.getId() == client.getPlayer().getMapId() ? targetPosition :
                // townPortal.getPosition(), animated), false);
            } else {
                client.getPlayer().getMap().broadcastMessage(client.getPlayer(), MainPacketCreator.spawnPortal(
                        town.getId(), target.getId(), 2311002,
                        target.getId() == client.getPlayer().getMapId() ? targetPosition : townPortal.getPosition()),
                        false);
            }
        }
    }

    @Override
    public final void sendDestroyData(final MapleClient client) {
        sendDestroyData(client, false);
    }

    public final void sendDestroyData(final MapleClient client, boolean animated) {
        if (client.getPlayer() == null || getOwner() == null || target == null) {
            return;
        }
        if (target.getId() == client.getPlayer().getMapId() || getOwner().getId() == client.getPlayer().getId()
                || (getOwner() != null && getOwner().getParty() != null && client.getPlayer().getParty() != null
                && getOwner().getParty().getId() == client.getPlayer().getParty().getId())) {
            client.getSession().writeAndFlush(MainPacketCreator.removeDoor(getOwner().getId(), animated));
            if (getOwner() != null && getOwner().getParty() != null && client.getPlayer().getParty() != null
                    && (getOwner().getId() == client.getPlayer().getId()
                    || getOwner().getParty().getId() == client.getPlayer().getParty().getId())) {
                // client.getSession().writeAndFlush(MainPacketCreator.partyPortal(999999999,
                // 999999999, 0, new Point(-1, -1), false));
            } else {
                client.getSession().writeAndFlush(MainPacketCreator.spawnPortal(999999999, 999999999, 0, null));
            }
        }
    }

    public final void warp(final MapleCharacter chr, final boolean toTown) {
        if (getOwner() == null) {
            return;
        }
        if (chr == getOwner()
                || getOwner().getParty() != null && getOwner().getParty().containsMembers(new MaplePartyCharacter(chr))) {
            if (!toTown) {
                // chr.changeMap(target, targetPosition);
                chr.changeMap(target, target.getPortal("sp"));
            } else {
                chr.changeMap(town, townPortal);
            }
        } else {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.resetActions(chr));
        }
    }

    public final MapleCharacter getOwner() {
        return owner.get();
    }

    public final MapleMap getTown() {
        return town;
    }

    public final MaplePortal getTownPortal() {
        return townPortal;
    }

    public final MapleMap getTarget() {
        return target;
    }

    public final Point getTargetPosition() {
        return targetPosition;
    }

    @Override
    public final MapleMapObjectType getType() {
        return MapleMapObjectType.DOOR;
    }
}
