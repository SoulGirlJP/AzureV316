package handlers.ChatHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class MapleMultiChat {

    private List<MapleMultiChatCharacter> members = new LinkedList<MapleMultiChatCharacter>();
    private int id;
    private boolean pos0 = false;
    private boolean pos1 = false;
    @SuppressWarnings("unused")
    private boolean pos2 = false;
    private boolean pos3 = false;
    private boolean pos4 = false;
    private boolean pos5 = false;

    public MapleMultiChat(int id, MapleMultiChatCharacter chrfor) {
        this.members.add(chrfor);
        int position = getLowestPosition();
        chrfor.setPosition(position);
        this.id = id;
    }

    public boolean containsMembers(MapleMultiChatCharacter member) {
        return members.contains(member);
    }

    public void addMember(MapleMultiChatCharacter member) {
        members.add(member);
    }

    public void removeMember(MapleMultiChatCharacter member) {
        int position = member.getPosition();
        if (position == 0) {
            pos0 = false;
        } else if (position == 1) {
            pos1 = false;
        } else if (position == 2) {
            pos2 = false;
        } else if (position == 3) {
            pos3 = false;
        } else if (position == 4) {
            pos4 = false;
        } else if (position == 5) {
            pos5 = false;
        }
        members.remove(member);
    }

    public void silentRemoveMember(MapleMultiChatCharacter member) {
        members.remove(member);
    }

    public void silentAddMember(MapleMultiChatCharacter member, int position) {
        members.add(member);
        member.setPosition(position);
    }

    public void updateMember(MapleMultiChatCharacter member) {
        for (int i = 0; i < members.size(); i++) {
            MapleMultiChatCharacter chr = members.get(i);
            if (chr.equals(member)) {
                members.set(i, member);
            }
        }
    }

    public Collection<MapleMultiChatCharacter> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int getLowestPosition() {
        int position;
        if (pos0) {
            if (pos1) {
                this.pos2 = true;
                position = 1;
            } else if (pos2) {
                this.pos3 = true;
                position = 2;
            } else if (pos3) {
                this.pos4 = true;
                position = 3;
            } else if (pos4) {
                this.pos5 = true;
                position = 4;
            } else {
                position = 5;
            }
        } else {
            this.pos0 = true;
            position = 0;
        }
        return position;
    }

    public int getPositionByName(String name) {
        for (MapleMultiChatCharacter messengerchar : members) {
            if (messengerchar.getName().equals(name)) {
                return messengerchar.getPosition();
            }
        }
        return 4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        final MapleMultiChat other = (MapleMultiChat) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
