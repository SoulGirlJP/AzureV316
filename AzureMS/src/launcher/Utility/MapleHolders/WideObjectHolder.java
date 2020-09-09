/*
 * 테스피아 Project
 * ==================================
 * 팬더 spirit_m@nate.com
 * 백호 softwarewithcreative@nate.com
 * ==================================
 *
 */
package launcher.Utility.MapleHolders;

import client.Character.MapleCharacter;
import client.Community.MapleExpedition.MapleExpedition;
import client.Community.MapleExpedition.MapleExpeditionType;
import client.Community.MapleGuild.MapleAlliance;
import client.Community.MapleGuild.MapleGuild;
import client.Community.MapleGuild.MapleGuildCharacter;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Community.MapleParty.MaplePartyOperation;
import static client.Community.MapleParty.MaplePartyOperation.LEAVE;
import client.MapleClient;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import handlers.ChatHandler.MapleMultiChat;
import handlers.ChatHandler.MapleMultiChatCharacter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldCommunity;
//import packet.transfer.write.byte[];

/**
 * @author T-Sun
 * <p>
 * This file was written by T-Sun (doomgate17@naver.com)
 */
public class WideObjectHolder {

    private final Map<Integer, MapleParty> parties = new HashMap<Integer, MapleParty>();
    private final AtomicInteger runningPartyId = new AtomicInteger();
    private final Map<Integer, MapleExpedition> expeditions = new HashMap<Integer, MapleExpedition>();
    private final AtomicInteger runningExpeditionId = new AtomicInteger();
    private final Map<Integer, MapleMultiChat> messengers = new HashMap<Integer, MapleMultiChat>();
    private final AtomicInteger runningMessengerId = new AtomicInteger();
    private final Map<Integer, MapleGuild> guilds = new LinkedHashMap<Integer, MapleGuild>();
    private final Map<Integer, MapleAlliance> alliances = new LinkedHashMap<Integer, MapleAlliance>();
    private final MapleBuffStorage buffStorage = new MapleBuffStorage();
    private final Lock Guild_Mutex = new ReentrantLock();
    private final Lock Alliance_Mutex = new ReentrantLock();

    private static WideObjectHolder instance = new WideObjectHolder();

    public static WideObjectHolder getInstance() {
        return instance;
    }

    public void LoadAllGuild() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT guildid FROM guilds");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int guildid = rs.getInt("guildid");
                guilds.put(guildid, getGuild(guildid));
            }

            rs.close();
            ps.close();

            System.out.println("Full guild gun" + guilds.size() + "Cached QTY.");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public MapleParty getParty(int partyId) {
        return parties.get(partyId);
    }

    public MapleMultiChat getMultiChat(int chatid) {
        return messengers.get(chatid);
    }

    public MapleParty createParty(final MaplePartyCharacter chrfor) {
        final int partyid = runningPartyId.incrementAndGet();
        final MapleParty party = new MapleParty(partyid, chrfor);
        parties.put(party.getId(), party);
        return party;
    }

    public MapleParty disbandParty(final int partyid) {
        return parties.remove(partyid);
    }

    public void updateParty(MapleParty party, MaplePartyOperation operation, MaplePartyCharacter target) {
        for (ChannelServer server : ChannelServer.getAllInstances()) {
            for (MaplePartyCharacter partychar : party.getMembers()) {
                if (partychar.getChannel() == server.getChannel()) {
                    final MapleCharacter chr = server.getPlayerStorage().getCharacterByName(partychar.getName());
                    if (chr != null) {
                        if (operation == MaplePartyOperation.DISBAND) {
                            chr.setParty(null);
                        } else {
                            chr.setParty(party);
                        }
                        chr.getClient().getSession().writeAndFlush(MainPacketCreator.updateParty(chr.getClient().getChannel(), party, operation, target));
                    }
                }
            }
            switch (operation) {
                case LEAVE:
                case EXPEL: {
                    if (target.getChannel() == server.getChannel()) {
                        final MapleCharacter chr = server.getPlayerStorage().getCharacterByName(target.getName());
                        if (chr != null) {
                            chr.getClient().getSession().writeAndFlush(MainPacketCreator.updateParty(chr.getClient().getChannel(), party, operation, target));
                            chr.setParty(null);
                        }
                    }
                }
            }
        }
    }

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static HashMap<Integer, Integer> idToChannel = new HashMap<Integer, Integer>();
    private static HashMap<String, Integer> nameToChannel = new HashMap<String, Integer>();

    public static void register(int id, String name, int channel) {
        lock.writeLock().lock();
        try {
            idToChannel.put(id, channel);
            nameToChannel.put(name.toLowerCase(), channel);
        } finally {
            lock.writeLock().unlock();
        }
        //System.out.println("Char added: " + id + " " + name + " to channel " + channel);
    }

    public static void forceDeregister(int id) {
        lock.writeLock().lock();
        try {
            idToChannel.remove(id);
        } finally {
            lock.writeLock().unlock();
        }
        //System.out.println("Char removed: " + id);
    }

    public static void forceDeregister(String id) {
        lock.writeLock().lock();
        try {
            nameToChannel.remove(id.toLowerCase());
        } finally {
            lock.writeLock().unlock();
        }
        //System.out.println("Char removed: " + id);
    }

    public static void forceDeregister(int id, String name) {
        lock.writeLock().lock();
        try {
            idToChannel.remove(id);
            nameToChannel.remove(name.toLowerCase());
        } finally {
            lock.writeLock().unlock();
        }
        //System.out.println("Char removed: " + id + " " + name);
    }

    public static int findChannel(int id) {
        Integer ret;
        lock.readLock().lock();
        try {
            ret = idToChannel.get(id);
        } finally {
            lock.readLock().unlock();
        }
        if (ret != null) {
            if (ret != -10 && ChannelServer.getInstance(ret) == null) { //wha
                forceDeregister(id);
                return -1;
            }
            return ret;
        }
        return -1;
    }

    public static int findChannel(String st) {
        Integer ret;
        lock.readLock().lock();
        try {
            ret = nameToChannel.get(st.toLowerCase());
        } finally {
            lock.readLock().unlock();
        }
        if (ret != null) {
            if (ret != -10 && ChannelServer.getInstance(ret) == null) { //wha
                forceDeregister(st);
                return -1;
            }
            return ret;
        }
        return -1;
    }

    public final int createGuild(final int leaderId, final String name) {
        return MapleGuild.createGuild(leaderId, name);
    }

    public final MapleGuild getGuild(final int id) {
        Guild_Mutex.lock();
        try {
            if (guilds.get(id) != null) {
                return guilds.get(id);
            }
            final MapleGuild g = new MapleGuild(id);
            if (g.getId() == -1) { //failed to load
                return null;
            }
            guilds.put(id, g);
            return g;
        } finally {
            Guild_Mutex.unlock();
        }
    }

    public MapleGuild getGuildByName(String guildName) {
        Guild_Mutex.lock();
        try {
            for (MapleGuild g : guilds.values()) {
                if (g.getName().equalsIgnoreCase(guildName)) {
                    return g;
                }
            }
            return null;
        } finally {
            Guild_Mutex.unlock();
        }
    }

    public void setGuildMemberOnline(final MapleGuildCharacter mgc, final boolean bOnline, final int channel) {
        getGuild(mgc.getGuildId()).setOnline(mgc.getId(), bOnline, channel);
    }

    public final int addGuildMember(final MapleGuildCharacter mgc, final MapleClient c) {
        final MapleGuild g = guilds.get(mgc.getGuildId());
        if (g != null) {
            return g.addGuildMember(mgc, c);
        }
        return 0;
    }

    public void leaveGuild(final MapleGuildCharacter mgc) {
        final MapleGuild g = guilds.get(mgc.getGuildId());
        if (g != null) {
            g.leaveGuild(mgc);
        }
    }

    public void allianceChat(final int gid, final String name, final int cid, final String msg) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            final MapleAlliance ga = getAlliance(g.getAllianceId());
            if (ga != null) {
                for (int i = 0; i < ga.getNoGuilds(); i++) {
                    final MapleGuild g_ = getGuild(ga.getGuildId(i));
                    if (g_ != null) {
                        g_.allianceChat(name, cid, msg);
                    }
                }
            }
        }
    }

    public void guildChat(final int gid, final String name, final int cid, final String msg) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.guildChat(name, cid, msg);
        }
    }

    public void guildPacket(final int gid, byte[] packet) {

        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.guildMessage(packet);
        }
    }

    public void changeRank(final int gid, final int cid, final int newRank) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.changeRank(cid, newRank);
        }
    }

    public void expelMember(final MapleGuildCharacter initiator, final String name, final int cid) {
        final MapleGuild g = guilds.get(initiator.getGuildId());
        if (g != null) {
            g.expelMember(initiator, name, cid);
        }
    }

    public void setGuildNotice(final int gid, final String notice) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.setGuildNotice(notice);
        }
    }

    public void setGuildLeader(int gid, int cid) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.changeGuildLeader(cid);
        }
    }

    public int getSkillLevel(int gid, int sid) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            return g.getSkillLevel(sid);
        }
        return 0;
    }

    public boolean purchaseSkill(int gid, int sid, String name, int cid, MapleCharacter chr, boolean purchase) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            return g.purchaseSkill(sid, name, cid, chr, purchase);
        }
        return false;
    }

    public boolean activateSkill(int gid, int sid, String name) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            return g.activateSkill(sid, name);
        }
        return false;
    }

    public void memberLevelJobUpdate(final MapleGuildCharacter mgc) {
        final MapleGuild g = guilds.get(mgc.getGuildId());
        if (g != null) {
            g.memberLevelJobUpdate(mgc);
        }
    }

    public void changeRankTitle(final int gid, final String[] ranks) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.changeRankTitle(ranks);
        }
    }

    public void setGuildEmblem(final int gid, final short bg, final byte bgcolor, final short logo, final byte logocolor) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.setGuildEmblem(bg, bgcolor, logo, logocolor);
        }
    }

    public void disbandGuild(final int gid) {
        Guild_Mutex.lock();
        try {
            guilds.get(gid).disbandGuild();
            guilds.remove(gid);
        } finally {
            Guild_Mutex.unlock();
        }
    }

    public final boolean increaseGuildCapacity(final int gid) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            return g.increaseCapacity();
        }
        return false;
    }

    public void gainGP(final int gid, final int amount) {
        final MapleGuild g = guilds.get(gid);
        if (g != null) {
            g.gainGP(amount);
        }
    }

    public int getInvitedId(final int gid) {
        final MapleGuild g = getGuild(gid);
        if (g != null) {
            return g.getInvitedId();
        }
        return 0;
    }

    public void setInvitedId(final int gid, final int inviteid) {
        final MapleGuild g = getGuild(gid);
        if (g != null) {
            g.setInvitedId(inviteid);
        }
    }

    public int getGuildLeader(final int guildName) {
        final MapleGuild mga = getGuild(guildName);
        if (mga != null) {
            return mga.getLeaderId();
        }
        return 0;
    }

    public int getGuildLeader(final String guildName) {
        final MapleGuild mga = getGuildByName(guildName);
        if (mga != null) {
            return mga.getLeaderId();
        }
        return 0;
    }

    public MapleAlliance getAlliance(final int allianceid) {
        MapleAlliance ret = null;
        Alliance_Mutex.lock();
        try {
            ret = alliances.get(allianceid);
            if (ret == null) {
                ret = new MapleAlliance(allianceid);
                if (ret == null || ret.getId() <= 0) { //failed to load
                    return null;
                }
                alliances.put(allianceid, ret);
            }
        } finally {
            Alliance_Mutex.unlock();
        }
        return ret;
    }

    public int getAllianceLeader(final int allianceid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.getLeaderId();
        }
        return 0;
    }

    public void updateAllianceRanks(final int allianceid, final String[] ranks) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            mga.setRank(ranks);
        }
    }

    public void updateAllianceNotice(final int allianceid, final String notice) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            mga.setNotice(notice);
        }
    }

    public boolean canInvite(final int allianceid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.getCapacity() > mga.getNoGuilds();
        }
        return false;
    }

    public boolean changeAllianceLeader(final int allianceid, final int cid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.setLeaderId(cid);
        }
        return false;
    }

    public boolean changeAllianceLeader(final int allianceid, final int cid, final boolean sameGuild) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.setLeaderId(cid, sameGuild);
        }
        return false;
    }

    public boolean changeAllianceRank(final int allianceid, final int cid, final int change) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.changeAllianceRank(cid, change);
        }
        return false;
    }

    public boolean changeAllianceCapacity(final int allianceid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.setCapacity();
        }
        return false;
    }

    public boolean disbandAlliance(final int allianceid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.disband();
        }
        return false;
    }

    public boolean addGuildToAlliance(final int allianceid, final int gid) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.addGuild(gid);
        }
        return false;
    }

    public boolean removeGuildFromAlliance(final int allianceid, final int gid, final boolean expelled) {
        final MapleAlliance mga = getAlliance(allianceid);
        if (mga != null) {
            return mga.removeGuild(gid, expelled);
        }
        return false;
    }

    public void sendGuild(final int allianceid) {
        final MapleAlliance alliance = getAlliance(allianceid);
        if (alliance != null) {
            sendGuild(MainPacketCreator.getAllianceUpdate(alliance), -1, allianceid);
            sendGuild(MainPacketCreator.getGuildAlliance(alliance), -1, allianceid);
        }
    }

    public void sendGuild(final byte[] packet, final int exceptionId, final int allianceid) {
        final MapleAlliance alliance = getAlliance(allianceid);
        if (alliance != null) {
            for (int i = 0; i < alliance.getNoGuilds(); i++) {
                int gid = alliance.getGuildId(i);
                if (gid > 0 && gid != exceptionId) {
                    getGuild(gid).broadcast(packet);
                }
            }
        }
    }

    public boolean createAlliance(final MapleClient client, final String alliancename, final int cid, final int cid2, final int gid, final int gid2) {
        final int allianceid = getGuild(gid).createAlliance(client, alliancename, gid2);
        if (allianceid <= 0) {
            return false;
        }
        final MapleGuild g = getGuild(gid), g_ = getGuild(gid2);
        g.setAllianceId(allianceid);
        g_.setAllianceId(allianceid);
        g.changeARank(true);
        g_.changeARank(false);

        final MapleAlliance alliance = getAlliance(allianceid);
        sendGuild(MainPacketCreator.createGuildAlliance(alliance), -1, allianceid);
        sendGuild(MainPacketCreator.getAllianceInfo(alliance), -1, allianceid);
        sendGuild(MainPacketCreator.getGuildAlliance(alliance), -1, allianceid);
        sendGuild(MainPacketCreator.changeAlliance(alliance, true), -1, allianceid);
        return true;
    }

    public void setNewAlliance(final int gid, final int allianceid) {
        final MapleAlliance alliance = getAlliance(allianceid);
        final MapleGuild guild = getGuild(gid);
        if (alliance != null && guild != null) {
            for (int i = 0; i < alliance.getNoGuilds(); i++) {
                if (gid == alliance.getGuildId(i)) {
                    guild.setAllianceId(allianceid);
                    guild.broadcast(MainPacketCreator.getAllianceInfo(alliance));
                    guild.broadcast(MainPacketCreator.getGuildAlliance(alliance));
                    guild.changeARank();
                    guild.writeToDB(false);
                } else {
                    final MapleGuild g_ = getGuild(alliance.getGuildId(i));
                    if (g_ != null) {
                        g_.broadcast(MainPacketCreator.changeGuildInAlliance(alliance, guild, true));
                    }
                }
            }
        }
    }

    public void setOldAlliance(final int gid, final boolean expelled, final int allianceid) {
        final MapleAlliance alliance = getAlliance(allianceid);
        final MapleGuild g_ = getGuild(gid);
        if (alliance != null) {

            for (int i = 0; i < alliance.getNoGuilds(); i++) {
                MapleGuild guild = getGuild(alliance.getGuildId(i));
                if (guild != null) {
                    if (g_ != null) {
                        if (expelled) {
                            guild.broadcast(MainPacketCreator.serverNotice(5, "[" + g_.getName() + "] The guild was expelled from coalition."));
                        } else {
                            guild.broadcast(MainPacketCreator.serverNotice(5, "[" + g_.getName() + "] Guild has left the Union."));
                        }
                        guild.broadcast(MainPacketCreator.removeGuildFromAlliance(alliance, g_, expelled));
                    } else {
                        guild.changeARank(5);
                        guild.setAllianceId(0);
                    }
                }
            }
            if (g_ != null) {
                g_.changeARank(5);
                g_.setAllianceId(0);
                g_.broadcast(MainPacketCreator.disbandAlliance(allianceid));
                g_.broadcast(MainPacketCreator.removeGuildFromAlliance(alliance, g_, expelled));
                if (expelled) {
                    g_.broadcast(MainPacketCreator.serverNotice(5, "Exiled from the Union."));
                } else {
                    g_.broadcast(MainPacketCreator.serverNotice(5, "You left the Union."));
                }
            } else {
                alliance.broadcast(MainPacketCreator.serverNotice(5, "Union is disbanded."));
                alliance.broadcast(MainPacketCreator.disbandAlliance(allianceid));
            }
        }

        if (gid == -1) {
            Alliance_Mutex.lock();
            try {
                alliances.remove(allianceid);
            } finally {
                Alliance_Mutex.unlock();
            }
        }
    }

    public List<byte[]> getAllianceInfo(final int allianceid, final boolean start) {
        List<byte[]> ret = new ArrayList<byte[]>();
        final MapleAlliance alliance = getAlliance(allianceid);
        if (alliance != null) {
            if (start) {
                ret.add(MainPacketCreator.getAllianceInfo(alliance));
                ret.add(MainPacketCreator.getGuildAlliance(alliance));
            }
            ret.add(MainPacketCreator.getAllianceUpdate(alliance));
        }
        return ret;
    }

    public final MapleMultiChat createMessenger(final MapleMultiChatCharacter chrfor) {
        final int messengerid = runningMessengerId.incrementAndGet();
        final MapleMultiChat messenger = new MapleMultiChat(messengerid, chrfor);
        messengers.put(messenger.getId(), messenger);
        return messenger;
    }

    public final MapleMultiChat getMessenger(final int messengerid) {
        return messengers.get(messengerid);
    }

    public final MapleBuffStorage getPlayerBuffStorage() {
        return buffStorage;
    }

    public final void createExpedition(MapleExpeditionType type, MapleParty defaultparty) {
        MapleExpedition exp = new MapleExpedition(defaultparty.getLeader().getId(), type);
        final int expid = runningExpeditionId.incrementAndGet();
        exp.setId(expid);
        defaultparty.setExpedition(exp);
        exp.addParty(exp.getNextFreeSlot(), defaultparty);
        expeditions.put(expid, exp);
    }

    public final MapleExpedition getExpedition(int id) {
        return expeditions.get(id);
    }

    public final void removeExpedition(int id) {
        expeditions.remove(id);
    }

    public final void disbandExpedition(MapleExpedition exp, MaplePartyCharacter partyplayer) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chr != null) {
                        chr.send(MainPacketCreator.disbandExpedition());
                        chr.checkBossMapOut();
                    }
                }
            }
        }
        for (MapleParty party : exp.getPartys()) {
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.DISBAND, partyplayer);
        }
        removeExpedition(exp.getId());
    }

    public final void leftExpedition(MapleExpedition exp, MapleParty party, MaplePartyCharacter partyplayer) {
        if (party.getMembers().size() > 1) {
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.LEAVE, partyplayer);
        } else {
            exp.removeParty(party.getExpedition().getPositionByPartyId(party.getId()));
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.DISBAND, partyplayer);
        }
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chr != null) {
                        if (chr.getId() == partyplayer.getId()) {
                            chr.send(MainPacketCreator.leavedExpedition());
                        } else {
                            chr.send(MainPacketCreator.leaveExpedition(partyplayer.getName()));
                            chr.send(MainPacketCreator.updateExpedition(true, exp));
                        }
                        chr.checkBossMapOut();
                    }
                }
            }
        }
    }

    public final void expelExpedition(MapleExpedition exp, MapleParty party, MaplePartyCharacter partyplayer) {
        if (party.getMembers().size() > 1) {
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.EXPEL, partyplayer);
        } else {
            exp.removeParty(party.getExpedition().getPositionByPartyId(party.getId()));
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.DISBAND, partyplayer);
        }
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chr != null) {
                        if (chr.getId() == partyplayer.getId()) {
                            chr.send(MainPacketCreator.expeledExpedition());
                        } else {
                            chr.send(MainPacketCreator.expelExpedition(partyplayer.getName()));
                            chr.send(MainPacketCreator.updateExpedition(true, exp));
                        }
                        chr.checkBossMapOut();
                    }
                }
            }
        }
    }

    public final void joinExpedition(MapleExpedition exp, MapleCharacter ch) {
        boolean joined = false;
        MaplePartyCharacter hpc = new MaplePartyCharacter(ch);
        for (MapleParty party : exp.getPartys()) {
            if (party.getMembers().size() < 6) {
                WorldCommunity.updateParty(party.getId(), MaplePartyOperation.JOIN, hpc);
                ch.receivePartyMemberHP();
                ch.updatePartyMemberHP();
                joined = true;
                break;
            }
        }
        if (!joined) {
            MapleParty party = WorldCommunity.createParty(hpc);
            party.setExpedition(exp);
            exp.addParty(exp.getNextFreeSlot(), party);
            ch.setParty(party);
            ch.send(MainPacketCreator.partyCreated(party));
        }

        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpcz : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpcz.getName());
                    if (chr != null) {
                        if (chr.getId() != ch.getId()) {
                            chr.send(MainPacketCreator.serverNotice(5, "'" + ch.getName() + "' Has joined the Fellowship."));
                            chr.send(MainPacketCreator.updateExpedition(true, exp));
                        } else {
                            chr.send(MainPacketCreator.updateExpedition(false, exp));
                        }
                    }
                }
            }
        }
    }

    public final void changeLeaderExpedition(MapleExpedition exp, MapleParty party, MaplePartyCharacter partyplayer) {
        exp.setLeader(partyplayer.getId());
        WorldCommunity.updateParty(party.getId(), MaplePartyOperation.CHANGE_LEADER, partyplayer);
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chr != null) {
                        chr.send(MainPacketCreator.changeLeaderExpedition(exp.getPositionByPartyId(party.getId())));
                    }
                }
            }
        }
    }

    public final void changeLeaderPartyInExpedition(MapleExpedition exp, MapleParty party, MaplePartyCharacter partyplayer) {
        party.setLeader(partyplayer);
        exp.setLeader(partyplayer.getId());
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chr = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chr != null) {
                        chr.send(MainPacketCreator.updateExpedition(true, exp));
                    }
                }
            }
        }
        WorldCommunity.updateParty(party.getId(), MaplePartyOperation.CHANGE_LEADER, partyplayer);
    }

    public final void movePlayerExpedition(MapleExpedition exp, MapleParty from, int index, MapleCharacter chr) {
        MaplePartyCharacter partyplayer = new MaplePartyCharacter(chr);

        if (from.getMembers().size() > 1) {
            WorldCommunity.updateParty(from.getId(), MaplePartyOperation.LEAVE, partyplayer);
        } else {
            exp.removeParty(from.getExpedition().getPositionByPartyId(from.getId()));
            WorldCommunity.updateParty(from.getId(), MaplePartyOperation.DISBAND, partyplayer);
        }

        if (exp.isContained(index)) {
            MapleParty to = exp.getParty(index);
            if (to.getMembers().size() < 6) {
                WorldCommunity.updateParty(to.getId(), MaplePartyOperation.JOIN, partyplayer);
                chr.receivePartyMemberHP();
                chr.updatePartyMemberHP();
            }
        } else {
            MapleParty party = WorldCommunity.createParty(partyplayer);
            party.setExpedition(exp);
            exp.addParty(index, party);
            chr.setParty(party);
            chr.send(MainPacketCreator.partyCreated(party));
        }

        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleParty partys : exp.getPartys()) {
                for (MaplePartyCharacter hpc : partys.getMembers()) {
                    MapleCharacter chrs = cserv.getPlayerStorage().getCharacterByName(hpc.getName());
                    if (chrs != null) {
                        chrs.send(MainPacketCreator.updateExpedition(true, exp));
                    }
                }
            }
        }
    }

    public List<MapleGuild> getGuildForSearch(int world, int minGuildLevel, int maxGuildLevel, int minMemberSize, int maxMemberSize, int minAvgLevel, int maxAvgLevel) {
        List<MapleGuild> ret = new LinkedList<>();
        guilds.values()
                .stream()
                .filter(g -> g.getLevel() >= minGuildLevel && g.getLevel() <= maxGuildLevel)
                .filter(g -> g.getMembers().size() >= minMemberSize && g.getMembers().size() <= maxMemberSize)
                .filter(g -> g.getAvgLevel() >= minAvgLevel && g.getAvgLevel() <= maxAvgLevel)
                .forEach(ret::add);
        return ret;
    }

    public List<MapleGuild> getGuildForSearch(int worldid, byte mode, String text, boolean likeSearch) {
        List<MapleGuild> ret = new LinkedList<>();

        List<Integer> temp = new LinkedList<>();
        if (mode == 1 || mode == 2) {
            List<MapleGuild> guild = new LinkedList<>();
            if (likeSearch) {
                guild = guilds.values().stream().filter(g -> g.getName().contains(text)).collect(Collectors.toList());
            } else {
                guild = guilds.values().stream().filter(g -> g.getName().equals(text)).collect(Collectors.toList());
            }
            if (guild.size() > 0) {
                guild.forEach(g -> {
                    ret.add(g);
                });
            }
        }
        // Guild Master Name Search
        if (mode == 1 || mode == 3) {
            List<MapleGuild> guild = new LinkedList<>();
            if (likeSearch) {
                guild = guilds.values().stream().filter(g -> g.getLeaderName().contains(text)).collect(Collectors.toList());
            } else {
                guild = guilds.values().stream().filter(g -> g.getLeaderName().equals(text)).collect(Collectors.toList());
            }
            if (guild.size() > 0) {
                guild.forEach(g -> {
                    if (ret.stream().filter(gg -> gg.getId() == g.getId()).count() == 0) {
                        ret.add(g);
                    }
                });
            }
        }
        return ret;
    }
}
