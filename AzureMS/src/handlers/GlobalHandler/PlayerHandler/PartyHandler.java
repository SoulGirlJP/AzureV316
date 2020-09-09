package handlers.GlobalHandler.PlayerHandler;

import client.Character.MapleCharacter;
import client.Community.MapleExpedition.MapleExpedition;
import client.Community.MapleExpedition.MapleExpeditionType;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Community.MapleParty.MaplePartyOperation;
import client.MapleClient;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.MapleHolders.WideObjectHolder;
import launcher.Utility.WorldCommunity;

public class PartyHandler {

    public static void DenyPartyRequest(final ReadingMaple rh, final MapleClient c) {
        final int action = rh.readByte();
        final int partyid = rh.readInt();
        if (c.getPlayer().getParty() == null) {
            MapleParty party = WideObjectHolder.getInstance().getParty(partyid);
            if (party != null) {
                if (action == 0x28) { // Accept 1.2.220+ (+1)
                    if (party.getMembers().size() < 6) {
                        WorldCommunity.updateParty(partyid, MaplePartyOperation.JOIN,
                                new MaplePartyCharacter(c.getPlayer()));
                        c.getPlayer().receivePartyMemberHP();
                        c.getPlayer().updatePartyMemberHP();
                    } else {
                        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "The party is already full."));
                    }
                } else if (action == 0x27) { // 1.2.220+ (+1)
                    final MapleCharacter cfrom = c.getChannelServer().getPlayerStorage()
                            .getCharacterById(party.getLeader().getId());
                    if (cfrom != null) {
                        cfrom.getClient().getSession().writeAndFlush(
                                MainPacketCreator.serverNotice(5, c.getPlayer().getName() + " Has declined your party invitation."));
                    }
                }
            } else {
                c.getPlayer().dropMessage(5, "The party does not already exist.");
            }
        } else {
            c.getPlayer().dropMessage(5, "You can't join the party because you are already joined.");
        }
    }

    public static final void PartyOperatopn(final ReadingMaple rh, final MapleClient c) {
        final int operation = rh.readByte();
        MapleParty party = c.getPlayer().getParty();
        MaplePartyCharacter partyplayer = new MaplePartyCharacter(c.getPlayer());
        if (operation != 1 && operation != 4 && operation != 6 && operation != 7) {
            party.setVisible(rh.readByte());
        }
        switch (operation) {
            case 1: // create
                if (c.getPlayer().getParty() == null) {
                    party = WorldCommunity.createParty(partyplayer);
                    c.getPlayer().setParty(party);
                    party.setVisible(rh.readByte());
                    party.setPartyTitle(rh.readMapleAsciiString());
                    c.getSession().writeAndFlush(MainPacketCreator.partyCreated(party));
                } else {
                    c.getPlayer().dropMessage(5, "You can't create a party because you are already signed up.");
                    return;
                }
                break;
            case 2: // leave
                if (party != null) { // are we in a party? o.O"
                    if (partyplayer.equals(party.getLeader())) {
                        if (party.getExpedition() == null) { // disband
                            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.DISBAND, partyplayer);
                        } else {
                            if (party.getExpedition().getLeader() == partyplayer.getId()) {
                                WideObjectHolder.getInstance().disbandExpedition(party.getExpedition(), partyplayer);
                            } else {
                                WideObjectHolder.getInstance().leftExpedition(party.getExpedition(), party, partyplayer);
                                if (c.getPlayer().getEventInstance() != null) {
                                    c.getPlayer().getEventInstance().leftParty(c.getPlayer());
                                }
                                c.getPlayer().setParty(null);
                                return;
                            }
                        }
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().disbandParty();
                        }
                    } else {
                        if (party.getExpedition() == null) {
                            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.LEAVE, partyplayer);
                        } else {
                            WideObjectHolder.getInstance().leftExpedition(party.getExpedition(), party, partyplayer);
                        }
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().leftParty(c.getPlayer());
                        }
                        c.getPlayer().setParty(null);
                    }
                }
                break;
            case 3: // accept invitation
                final int partyid = rh.readInt();
                if (c.getPlayer().getParty() == null) {
                    party = WorldCommunity.getParty(partyid);
                    if (party != null) {
                        if (party.getMembers().size() < 6) {
                            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.JOIN, partyplayer);
                            c.getPlayer().receivePartyMemberHP();
                            c.getPlayer().updatePartyMemberHP();
                        } else {
                            c.getPlayer().dropMessage(5, "The party is already full.");
                        }
                    } else {
                        c.getPlayer().dropMessage(5, "The party you are trying to join does not already exist.");
                    }
                } else {
                    c.getPlayer().dropMessage(5, "You can't join the party because you are already joined.");
                }
                break;
            case 4: // invite
                final MapleCharacter invited = c.getChannelServer().getPlayerStorage().getCharacterByName(rh.readMapleAsciiString());
                if (invited != null) {
                    if (invited.getParty() == null) {
                        if (party.getMembers().size() < 6) {
                            if (c.getPlayer().getMapId() == invited.getMapId()) {
                                c.getPlayer().dropMessage(1, invited.getName() + " Has invited you to a party.");
                                invited.getClient().getSession()
                                        .writeAndFlush(MainPacketCreator.partyInvite(c.getPlayer()));
                            } else {
                                c.getPlayer().dropMessage(1, "If you're not in the same place, you can't invite them.");
                            }
                        } else {
                            c.getPlayer().dropMessage(5, "The party is already full.");
                        }
                    } else {
                        c.getPlayer().dropMessage(5, "You are already a party.");
                    }
                } else {
                    c.getPlayer().dropMessage(5, "No target found.");
                }
                break;
            case 6: // expel
                final MaplePartyCharacter expelled = party.getMemberById(rh.readInt());
                if (expelled != null) {
                    WorldCommunity.updateParty(party.getId(), MaplePartyOperation.EXPEL, expelled);
                    if (c.getPlayer().getEventInstance() != null) {
                        if (expelled.isOnline()) {
                            c.getPlayer().getEventInstance().disbandParty();
                        }
                    }
                }
                break;
            case 7: // change leader
                if (party.getExpedition() != null) {
                    c.getPlayer().message(1, "You can't give a party leader while on the expedition.");
                    return;
                }
                final MaplePartyCharacter newleader = party.getMemberById(rh.readInt());
                WorldCommunity.updateParty(party.getId(), MaplePartyOperation.CHANGE_LEADER, newleader);
                break;
            case 13: // change party title
                final String newTitle = rh.readMapleAsciiString();
                if (newTitle.length() > 0) {
                    party.setPartyTitle(newTitle);
                    c.send(MainPacketCreator.updateParty(c.getChannel(), party, MaplePartyOperation.CHANGE_PARTY_TITLE,
                            partyplayer));
                } else {
                    c.getPlayer().message(1, "Please enter the party name you wish to change.");
                    return;
                }
                break;
            default:
                System.out.println("Unhandled Party function." + operation + "");
                break;
        }
    }

    public static void processExpeditionRequest(ReadingMaple rh, MapleClient c) {
        byte action = rh.readByte();
        switch (action) {
            case 78: // Expedition Registration
                if (c.getPlayer().getParty() == null) {
                    final String partytitle = rh.readMapleAsciiString();
                    MaplePartyCharacter partyplayer = new MaplePartyCharacter(c.getPlayer());
                    MapleParty party = WorldCommunity.createParty(partyplayer);
                    c.getPlayer().setParty(party);
                    party.setPartyTitle(partytitle);
                    c.getSession().writeAndFlush(MainPacketCreator.partyCreated(party));
                    WideObjectHolder.getInstance().createExpedition(MapleExpeditionType.getById(rh.readInt()), party);
                    c.send(MainPacketCreator.updateExpedition(false, c.getPlayer().getParty().getExpedition()));
                } else {
                    c.getPlayer().dropMessage(5, "You can't create a party because you are already signed up.");
                }
                break;
            case 79: { // Expedition Invitation
                MapleCharacter hp = null;
                String target = rh.readMapleAsciiString();
                hp = c.getChannelServer().getPlayerStorage().getCharacterByName(target);
                if (hp == null) {
                    c.getPlayer().message(1, "No targets found on the current channel.");
                } else {
                    MapleExpeditionType type = c.getPlayer().getParty().getExpedition().getType();
                    if (hp.getLevel() < type.minlv || hp.getLevel() > type.maxlv) {
                        c.getPlayer().message(1, "Target is not a level to join this expedition.");
                    } else if (c.getPlayer().getParty().getExpedition().getAllMemberSize() >= type.maxplayer) {
                        c.getPlayer().message(1, "The maximum number of expeditions is full.");
                    } else if (hp.getParty() != null) {
                        c.getPlayer().message(1, "Target is already subscribed to the party.");
                    } else {
                        hp.send(MainPacketCreator.inviteExpedition(c.getPlayer().getParty().getExpedition().getType(),
                                c.getPlayer()));
                    }
                }
            }
            break;
            case 80: { // Invitation confirmation
                MapleCharacter hp = null;
                String origin = rh.readMapleAsciiString();
                rh.skip(4);
                int subAction = rh.readInt();
                if (subAction == 0x07) { // Per invitation
                    hp = c.getChannelServer().getPlayerStorage().getCharacterByName(origin);
                    if (hp == null) {
                        c.getPlayer().message(1, "You are not currently invited to this channel.");
                    } else {
                        hp.send(MainPacketCreator.serverNotice(1, c.getPlayer().getName() + " Invited you to the expedition."));
                    }
                } else if (subAction == 0x09) { // Decline invitation
                    hp = c.getChannelServer().getPlayerStorage().getCharacterByName(origin);
                    if (hp == null) {
                        c.getPlayer().message(1, "You are not currently invited to this channel.");
                    } else {
                        hp.send(MainPacketCreator.serverNotice(5, c.getPlayer().getName() + " Has rescinded the Fellowship invitation."));
                    }
                } else if (subAction == 0x08) { // Accept invitation
                    hp = c.getChannelServer().getPlayerStorage().getCharacterByName(origin);
                    if (hp == null) {
                        c.getPlayer().message(1, "You are not currently invited to this channel.");
                    } else {
                        MapleExpedition exp = hp.getParty().getExpedition();
                        if (exp == null) {
                            c.getPlayer().message(1, "Invitee is not currently on expedition.");
                            return;
                        }
                        if (exp.getAllMemberSize() < exp.getType().maxplayer) {
                            WideObjectHolder.getInstance().joinExpedition(exp, c.getPlayer());
                        } else {
                            c.getPlayer().message(1, "The expedition is already full.");
                        }
                    }
                }
                break;
            }
            case 81: {// Leave Expedition
                if (c.getPlayer().getParty() == null) {
                    c.getPlayer().dropMessage(5, "No party joined.");
                } else if (c.getPlayer().getParty().getExpedition() == null) {
                    c.getPlayer().dropMessage(5, "No expeditions joined.");
                } else {
                    MapleParty party = c.getPlayer().getParty();
                    MaplePartyCharacter partyplayer = new MaplePartyCharacter(c.getPlayer());
                    if (party.getExpedition().getLeader() == party.getLeader().getId()) { // disband
                        WideObjectHolder.getInstance().disbandExpedition(party.getExpedition(), partyplayer);
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().disbandParty();
                        }
                    } else {
                        WideObjectHolder.getInstance().leftExpedition(party.getExpedition(), party, partyplayer);
                        if (c.getPlayer().getEventInstance() != null) {
                            c.getPlayer().getEventInstance().leftParty(c.getPlayer());
                        }
                    }
                    c.getPlayer().setParty(null);
                }
                break;
            }
            case 82: { // Expedition Expedition
                int target = rh.readInt();
                MapleParty party = c.getPlayer().getParty();
                if (party == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                } else if (party.getExpedition() == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                }
                MapleExpedition exp = party.getExpedition();
                if (exp.getLeader() == c.getPlayer().getId()) {
                    MaplePartyCharacter found = null;
                    MapleParty hpcParty = null;
                    for (MapleParty partyzz : party.getExpedition().getPartys()) {
                        for (MaplePartyCharacter hpc : partyzz.getMembers()) {
                            if (hpc.getId() == target) {
                                found = hpc;
                                hpcParty = partyzz;
                                break;
                            }
                        }
                    }
                    if (found == null) {
                        c.getPlayer().message(1, "Target is not on expedition.");
                        return;
                    }
                    WideObjectHolder.getInstance().expelExpedition(exp, hpcParty, found);
                    if (c.getPlayer().getEventInstance() != null) {
                        c.getPlayer().getEventInstance().disbandParty();
                    }
                }
                break;
            }
            case 83: {// Expedition
                int target = rh.readInt();
                MapleParty party = c.getPlayer().getParty();
                if (party == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                } else if (party.getExpedition() == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                }
                MapleExpedition exp = party.getExpedition();
                if (exp.getLeader() == c.getPlayer().getId()) {
                    MaplePartyCharacter found = null;
                    MapleParty hpcParty = null;
                    for (MapleParty partyzz : party.getExpedition().getPartys()) {
                        for (MaplePartyCharacter hpc : partyzz.getMembers()) {
                            if (hpc.getId() == target) {
                                found = hpc;
                                hpcParty = partyzz;
                                break;
                            }
                        }
                    }
                    if (found == null) {
                        c.getPlayer().message(1, "Target is not on expedition.");
                        return;
                    }
                    WideObjectHolder.getInstance().changeLeaderExpedition(exp, hpcParty, found);
                }
                break;
            }
            case 84: {// Expedition Party Leader Giving
                int target = rh.readInt();
                MapleParty party = c.getPlayer().getParty();
                if (party == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                } else if (party.getExpedition() == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                }
                MapleExpedition exp = party.getExpedition();
                if (exp.getLeader() == c.getPlayer().getId()) {
                    MaplePartyCharacter found = null;
                    MapleParty hpcParty = null;
                    for (MapleParty partyzz : party.getExpedition().getPartys()) {
                        for (MaplePartyCharacter hpc : partyzz.getMembers()) {
                            if (hpc.getId() == target) {
                                found = hpc;
                                hpcParty = partyzz;
                                break;
                            }
                        }
                    }
                    if (found == null) {
                        c.getPlayer().message(1, "Target is not on expedition.");
                        return;
                    }
                    WideObjectHolder.getInstance().changeLeaderPartyInExpedition(exp, hpcParty, found);
                }
                break;
            }
            case 85: {// Expedition Party Moves
                int index = rh.readInt();
                int target = rh.readInt();
                MapleParty party = c.getPlayer().getParty();
                if (party == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                } else if (party.getExpedition() == null) {
                    c.getPlayer().message(1, "Not on expedition.");
                    return;
                }
                MapleExpedition exp = party.getExpedition();
                if (exp.getLeader() == c.getPlayer().getId()) {
                    MapleCharacter targetchr = null;
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        if (cserv.getPlayerStorage().getCharacterById(target) != null) {
                            targetchr = cserv.getPlayerStorage().getCharacterById(target);
                            break;
                        }
                    }
                    if (targetchr == null) {
                        c.getPlayer().message(1, "The target to be moved is not connected to the game.");
                        return;
                    }
                    WideObjectHolder.getInstance().movePlayerExpedition(exp, targetchr.getParty(), index, targetchr);
                }
                break;
            }
        }
    }
}
