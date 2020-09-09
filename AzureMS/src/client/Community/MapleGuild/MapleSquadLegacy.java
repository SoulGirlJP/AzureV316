package client.Community.MapleGuild;

import java.util.LinkedList;
import java.util.List;

import client.Character.MapleCharacter;
import launcher.ServerPortInitialize.ChannelServer;
import tools.Timer.EtcTimer;

public class MapleSquadLegacy {

    private MapleCharacter leader;
    private List<MapleCharacter> members = new LinkedList<MapleCharacter>();
    private List<MapleCharacter> bannedMembers = new LinkedList<MapleCharacter>();
    private int ch;
    private String type;
    private byte status = 0;

    public MapleSquadLegacy(final int ch, final String type, final MapleCharacter leader, final int expiration) {
        this.leader = leader;
        this.members.add(leader);
        this.ch = ch;
        this.type = type;
        this.status = 1;

        scheduleRemoval(expiration);
    }

    private void scheduleRemoval(final int time) {
        EtcTimer.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                members.clear();
                bannedMembers.clear();
                leader = null;
                ChannelServer.getInstance(ch).removeMapleSquad(type);
            }
        }, time);
    }

    public void removeMember(String name) {
        MapleCharacter del = null;
        for (MapleCharacter chr : members) {
            if (chr.getName().equals(name)) {
                del = chr;
                break;
            }
        }
        removeMember(del);
    }

    public void removeMember(MapleCharacter member) {
        if (members.contains(member)) {
            members.remove(member);
        }
    }

    public void reAddMember(MapleCharacter chr) {
        removeMember(chr);
        members.add(chr);
    }

    public MapleCharacter getLeader() {
        return leader;
    }

    public boolean containsMember(MapleCharacter member) {
        final int id = member.getId();
        for (MapleCharacter mmbr : members) {
            if (id == mmbr.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<MapleCharacter> getMembers() {
        return members;
    }

    public int getSquadSize() {
        return members.size();
    }

    public boolean isBanned(MapleCharacter member) {
        return bannedMembers.contains(member);
    }

    public int addMember(MapleCharacter member, boolean join) {
        if (join) {
            if (!members.contains(member)) {
                if (members.size() <= 30) {
                    members.add(member);
                    getLeader().dropMessage(5, member.getName() + " has joined the fight!");
                    return 1;
                }
                return 2;
            }
            return -1;
        } else {
            if (members.contains(member)) {
                members.remove(member);
                getLeader().dropMessage(5, member.getName() + " have withdrawed from the fight.");
                return 1;
            }
            return -1;
        }
    }

    public void acceptMember(int pos) {
        final MapleCharacter toadd = bannedMembers.get(pos);
        if (toadd != null) {
            members.add(toadd);
            bannedMembers.remove(toadd);

            toadd.dropMessage(5, leader.getName() + " has decided to add you back to the squad.");
        }
    }

    public void banMember(int pos) {
        final MapleCharacter toban = members.get(pos);
        if (toban == leader) {
            return;
        }
        if (toban != null) {
            members.remove(toban);
            bannedMembers.add(toban);

            toban.dropMessage(5, leader.getName() + " has removed you from the squad.");
        }
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getBannedMemberSize() {
        return bannedMembers.size();
    }

    public String getSquadMemberString(byte type) {
        switch (type) {
            case 0: {
                StringBuilder sb = new StringBuilder("Squad members : ");
                sb.append("#b").append(members.size()).append(" #k ").append("List of participants : \n\r ");
                int i = 0;
                for (MapleCharacter chr : members) {
                    i++;
                    sb.append(i).append(" : ").append(chr.getName()).append(" ");
                    if (chr == leader) {
                        sb.append("(Leader of the squad)");
                    }
                    sb.append(" \n\r ");
                }
                while (i < 30) {
                    i++;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
            case 1: {
                StringBuilder sb = new StringBuilder("Squad members : ");
                sb.append("#b").append(members.size()).append(" #n ").append("List of participants : \n\r ");
                int i = 0, selection = 0;
                for (MapleCharacter chr : members) {
                    i++;
                    sb.append("#b#L").append(selection).append("#");
                    selection++;
                    sb.append(i).append(" : ").append(chr.getName()).append(" ");
                    if (chr == leader) {
                        sb.append("(Leader of the squad)");
                    }
                    sb.append("#l").append(" \n\r ");
                }
                while (i < 30) {
                    i++;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
            case 2: {
                StringBuilder sb = new StringBuilder("Squad members : ");
                sb.append("#b").append(members.size()).append(" #n ").append("List of participants : \n\r ");
                int i = 0, selection = 0;
                for (MapleCharacter chr : bannedMembers) {
                    i++;
                    sb.append("#b#L").append(selection).append("#");
                    selection++;
                    sb.append(i).append(" : ").append(chr.getName()).append(" ");
                    if (chr == leader) {
                        sb.append("(Leader of the squad)");
                    }
                    sb.append("#l").append(" \n\r ");
                }
                while (i < 30) {
                    i++;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
        }
        return null;
    }
}
