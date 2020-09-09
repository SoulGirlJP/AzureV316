package client.Community.MapleParty;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import client.Community.MapleExpedition.MapleExpedition;

public class MapleParty {

    private MaplePartyCharacter leader;
    private List<MaplePartyCharacter> members = new LinkedList<MaplePartyCharacter>();
    private MapleExpedition exp = null;
    private byte visible;
    private int id;
    private String partytitle;

    public MapleParty(int id, MaplePartyCharacter chrfor) {
        this.leader = chrfor;
        this.members.add(this.leader);
        this.id = id;
    }

    public byte getVisible() {
        return visible;
    }

    public void setVisible(byte set) {
        this.visible = set;
    }

    public String getPatryTitle() {
        return partytitle;
    }

    public void setPartyTitle(String title) {
        this.partytitle = title;
    }

    public void setExpedition(MapleExpedition exp) {
        this.exp = exp;
    }

    public MapleExpedition getExpedition() {
        return exp;
    }

    public boolean containsMembers(MaplePartyCharacter member) {
        return members.contains(member);
    }

    public void addMember(MaplePartyCharacter member) {
        members.add(member);
    }

    public void removeMember(MaplePartyCharacter member) {
        members.remove(member);
    }

    public void updateMember(MaplePartyCharacter member) {
        for (int i = 0; i < members.size(); i++) {
            MaplePartyCharacter chr = members.get(i);
            if (chr.equals(member)) {
                members.set(i, member);
            }
        }
    }

    public MaplePartyCharacter getMemberById(int id) {
        for (MaplePartyCharacter chr : members) {
            if (chr.getId() == id) {
                return chr;
            }
        }
        return null;
    }

    public Collection<MaplePartyCharacter> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaplePartyCharacter getLeader() {
        return leader;
    }

    public void setLeader(MaplePartyCharacter nLeader) {
        leader = nLeader;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapleParty other = (MapleParty) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    public MaplePartyCharacter getMemberByIndex(int index) {
        return members.get(index);
    }
}
