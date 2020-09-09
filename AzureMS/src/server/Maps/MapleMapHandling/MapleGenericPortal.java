package server.Maps.MapleMapHandling;

import java.awt.Point;

import client.MapleClient;
import constants.ServerConstants;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import scripting.Portal.PortalScriptManager;


public class MapleGenericPortal implements MaplePortal {

    private String name, target, scriptName;
    private Point position;
    private int targetmap, type, id;

    public MapleGenericPortal(final int type) {
        this.type = type;
    }

    
    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    
    public final String getName() {
        return name;
    }

    
    public final Point getPosition() {
        return position;
    }

    
    public final String getTarget() {
        return target;
    }

    
    public final int getTargetMapId() {
        return targetmap;
    }

   
    public final int getType() {
        return type;
    }

    
    public final String getScriptName() {
        return scriptName;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setPosition(final Point position) {
        this.position = position;
    }

    public final void setTarget(final String target) {
        this.target = target;
    }

    public final void setTargetMapId(final int targetmapid) {
        this.targetmap = targetmapid;
    }

    
    public final void setScriptName(final String scriptName) {
        this.scriptName = scriptName;
    }

    
    public final void enterPortal(final MapleClient c) {
        if (getScriptName() != null) {
            final MapleMap currentmap = c.getPlayer().getMap();
            c.getPlayer().checkFollow();
            try {
                try {
                    PortalScriptManager.getInstance().executePortalScript(this, c);
                } catch (Exception ex) {

                }
                if (c.getPlayer().getMap() == currentmap) { // Character is still on the same map.
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                }
            } catch (final Exception e) {
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
        } else if (getTargetMapId() != 999999999) {
            MapleMap to = null;
            if (c.getPlayer().getEventInstance() == null) {
                to = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(getTargetMapId());
            } else {
                to = c.getPlayer().getEventInstance().getMapFactory().getMap(getTargetMapId());
            }
            c.getPlayer().changeMap(to,
                    to.getPortal(getTarget()) == null ? to.getPortal(0) : to.getPortal(getTarget())); // late resolving
            // makes this
            // harder but
            // prevents us
            // from loading
            // the whole
            // world at once
        }
    }
}
