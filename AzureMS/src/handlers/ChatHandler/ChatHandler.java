package handlers.ChatHandler;

import client.Character.MapleCharacter;
import client.Commands.CommandProcessor;
import java.util.Collection;

import client.MapleClient;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import constants.ServerConstants;
import launcher.AdminGUI.AdminTool;
import tools.LoggerChatting;
import launcher.AdminGUI.AdminToolPacket;
import launcher.AdminGUI.AdminToolStart;
import launcher.ServerPortInitialize.CashShopServer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import server.Quests.MapleQuest;

public class ChatHandler {
	
	private static void updateChatCount() {
		if (ServerConstants.chatlimit >= 500) {
			ServerConstants.chatlimit = 0;
	        AdminToolStart.ChatList.clear();
	        AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
        }
        ServerConstants.chatlimit++;
	}
	
    public static void GeneralChat(String text, byte unk, MapleClient c, MapleCharacter chr) {
    	if (!chr.isGM() && text.length() >= 80) {
            return;
        }
    	
    	boolean isCommand = text.startsWith(String.valueOf(ServerConstants.ADMIN_COMMAND_PREFIX)) ||
    						text.startsWith(String.valueOf(ServerConstants.PLAYER_COMMAND_PREFIX));
    	if (isCommand) {
    		CommandProcessor.getInstance().processCommand(c, text);
    	} else {
    		if (WorldCommunity.isFreeze) {
                chr.dropMessage(1, "The chat has been frozen, please try again later.");
                return;
            }
            if (chr.getChatban().equals("true")) {
                chr.dropMessage(1, "You're not allowed to use this chat, as you've been gagged.");
                return;
            }
            
            String outputMsg = "";
            if (text.charAt(0) == '~') {
                if (chr.getMeso() < 20000) {
                    chr.dropMessage(1, "This feature requires 20000 mesos to be able to use.");
                    return;
                } else {
                	updateChatCount();
                    chr.gainMeso(-20000, false);
                    
                    String Rank = c.getPlayer().getQuestNAdd(MapleQuest.getInstance(201801)).getCustomData();
                    outputMsg = "[All] <" + c.getPlayer().getHope() +  "> " + c.getPlayer().getName() + " [" + (Rank == null ? "0" : Rank) + " Castle]: " + text.replaceAll("~", "");
                    
                    WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(12, outputMsg));
                }
            } else {
            	updateChatCount();
            	
            	chr.getMap().broadcastMessage(MainPacketCreator.getChatText(chr.getId(), text, c.getPlayer().isGM(), unk), c.getPlayer().getPosition());
            	
            	outputMsg = "[Normal][Ch." + chr.getClient().getChannel() + "] " + chr.getName() + ": " + text;
            }
            
            AdminTool.broadcastMessage(AdminToolPacket.sendChatText(outputMsg));
            AdminToolStart.ChatList.addElement(outputMsg);
            AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
    	}
        LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("General chat: ", chr, text));
    }

    public static void Others(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        int type = rh.readByte();
        byte numRecipients = rh.readByte();
        int recipients[] = new int[numRecipients];

        for (byte i = 0; i < numRecipients; i++) {
            recipients[i] = rh.readInt();
        }
        
        if (WorldCommunity.isFreeze) {
            c.getPlayer().dropMessage(1, "The chat has been frozen, please try again later.");
            return;
        }
        if (c.getPlayer().getChatban().equals("true")) {
            c.getPlayer().dropMessage(1, "You're not allowed to use this chat, as you've been gagged.");
            return;
        }
        
        String chattext = rh.readMapleAsciiString();
        boolean isCommand = chattext.startsWith(String.valueOf(ServerConstants.ADMIN_COMMAND_PREFIX)) ||
        					chattext.startsWith(String.valueOf(ServerConstants.PLAYER_COMMAND_PREFIX));
        
        if (isCommand) {
        	CommandProcessor.getInstance().processCommand(c, chattext);
        } else {
        	switch (type) {
	            case 0:
	                ServerConstants.chatlimit++;
	                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Friend][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext));
	                WorldCommunity.buddyChat(recipients, chr.getId(), chr.getName(), chattext);
	                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("Friend : ", chr, chattext));
	                break;
	            case 1:
	                if (ServerConstants.chatlimit >= 500) {
	                    ServerConstants.chatlimit = 0;
	                    AdminToolStart.ChatList.clear();
	                    AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
	                }
	                ServerConstants.chatlimit++;
	                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Party][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext));
	                WorldCommunity.partyChat(chr.getParty(), chattext, chr.getName());
	                AdminToolStart.ChatList.addElement("[Party][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext);
	                AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
	                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("Party : ", chr, chattext));
	                break;
	            case 2:
	                ServerConstants.chatlimit++;
	                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Guild][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext));
	                ChannelServer.guildChat(chr.getGuildId(), chr.getName(), chr.getId(), chattext);
	                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("Guild : ", chr, chattext));
	                break;
	            case 3:
	                ServerConstants.chatlimit++;
	                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Union][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext));
	                WorldCommunity.allianceChat(chr.getGuildId(), chr.getName(), chr.getId(), chattext);
	                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("Union : ", chr, chattext));
	                break;
	            case 4:
	                ServerConstants.chatlimit++;
	                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Expedition][Ch." + chr.getClient().getChannel() + "]" + chr.getName() + " : " + chattext));
	                chr.getParty().getExpedition().broadcastMessage(chr, MainPacketCreator.multiChat(chr.getName(), chattext, 4));
	                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("Expedition : ", chr, chattext));
	                break;
	        }
        }
    }

    public static void Messenger(ReadingMaple slea, MapleClient c) {
        String name;
        String input;
        MapleMultiChat messenger = c.getPlayer().getMessenger();
        
        if (WorldCommunity.isFreeze) {
            c.getPlayer().dropMessage(1, "The chat has been frozen, please try again later.");
            return;
        }
        if (c.getPlayer().getChatban().equals("true")) {
            c.getPlayer().dropMessage(1, "You're not allowed to use this chat, as you've been gagged.");
            return;
        }

        switch (slea.readByte()) {
            case 0x00:
                if (messenger == null) {
                    slea.skip(1);
                    byte player = slea.readByte();
                    int messengerid = slea.readInt();
                    if (messengerid == 0) {
                        MapleMultiChatCharacter messengerplayer = new MapleMultiChatCharacter(c.getPlayer());
                        messenger = WorldCommunity.createMessenger(messengerplayer);
                        c.getPlayer().setMessenger(messenger);
                        c.getPlayer().setMessengerPosition(0);
                    } else {
                        messenger = WorldCommunity.getMessenger(messengerid);
                        int position = messenger.getLowestPosition();
                        MapleMultiChatCharacter messengerplayer = new MapleMultiChatCharacter(c.getPlayer(), position);
                        messenger.addMember(messengerplayer);
                        if (messenger != null) {
                            if (messenger.getMembers().size() < player) {
                                c.getPlayer().setMessenger(messenger);
                                c.getPlayer().setMessengerPosition(position);
                                WorldCommunity.joinMessenger(messenger.getId(), messengerplayer, c.getPlayer().getName(), messengerplayer.getChannel());
                            } else {
                                c.getPlayer().dropMessage(5, "Already the maximum number of messengers.");
                            }
                        }
                    }
                } else {
                    c.getPlayer().dropMessage(1, "The room is already closed.");
                }
                break;
            case 0x02:
                if (messenger != null) {
                    MapleMultiChatCharacter messengerplayer = new MapleMultiChatCharacter(c.getPlayer());
                    WorldCommunity.leaveMessenger(messenger.getId(), messengerplayer);
                    c.getPlayer().setMessenger(null);
                    c.getPlayer().setMessengerPosition(4);
                }
                break;
            case 0x03: {
                if (messenger.getMembers().size() < 6) {
                    input = slea.readMapleAsciiString();
                    MapleCharacter target = null;
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        target = cserv.getPlayerStorage().getCharacterByName(input);
                        if (target != null) {
                            break;
                        }
                    }
                    if (target != null) {
                        if (target.getMessenger() == null) {
                            if (!c.getPlayer().isGM() && target.isGM()) {
                                c.getSession().writeAndFlush(MainPacketCreator.messengerNote(input, 4, 0));
                                return;
                            }
                            target.getClient().getSession().writeAndFlush(MainPacketCreator.messengerInvite(c.getPlayer().getName(), messenger.getId()));
                            c.getSession().writeAndFlush(MainPacketCreator.messengerNote(input, 4, 1));
                        } else {
                            c.getSession().writeAndFlush(MainPacketCreator.messengerChat(c.getPlayer().getName(), c.getPlayer().getName() + " : " + input + " 님은 이미 메신저를 사용하는 중입니다."));
                        }
                    } else {
                        c.getSession().writeAndFlush(MainPacketCreator.messengerNote(input, 4, 0));
                    }
                    break;
                }
            }
            case 0x05:
                String targeted = slea.readMapleAsciiString();
                MapleCharacter target = null;
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    target = cserv.getPlayerStorage().getCharacterByName(targeted);
                    if (target != null) {
                        break;
                    }
                }
                if (target != null) {
                    if (target.getMessenger() != null) {
                        target.getClient().getSession().writeAndFlush(MainPacketCreator.messengerNote(c.getPlayer().getName(), 5, 0));
                    }
                }
                break;
            case 0x06:
                if (messenger != null) {
                    name = slea.readMapleAsciiString();
                    input = slea.readMapleAsciiString();
                    WorldCommunity.messengerChat(messenger.getId(), input, name);
                }
                break;
        }
    }

    public static void Whisper_Find(ReadingMaple rh, MapleClient c) {
        byte mode = rh.readByte();
        rh.skip(4);
        boolean friend = false;
        
        if (WorldCommunity.isFreeze) {
            c.getPlayer().dropMessage(1, "The chat has been frozen, please try again later.");
            return;
        }
        if (c.getPlayer().getChatban().equals("true")) {
            c.getPlayer().dropMessage(1, "You're not allowed to use this chat, as you've been gagged.");
            return;
        }
        
        switch (mode) {
            case 0x44:
                friend = true;
            case 5: {
                String recipient = rh.readMapleAsciiString();
                MapleCharacter player = c.getChannelServer().getPlayerStorage().getCharacterByName(recipient);
                if (player != null) {
                    if (!player.isGM() || (c.getPlayer().isGM() && player.isGM())) {
                        if (CashShopServer.getInstance().isCharacterInCS(recipient)) {
                            c.getSession().writeAndFlush(MainPacketCreator.getFindReplyWithCS(recipient, friend));
                        } else {
                            c.getSession().writeAndFlush(MainPacketCreator.getFindReplyWithMap(recipient, friend, player.getMap().getId()));
                        }
                    } else {
                        c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 0));
                    }
                } else {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        player = cserv.getPlayerStorage().getCharacterByName(recipient);
                        if (player != null) {
                            break;
                        }
                    }
                    if (CashShopServer.getInstance().getPlayerStorage().isCharacterConnected(recipient)) {
                        c.getSession().writeAndFlush(MainPacketCreator.getFindReplyWithCS(recipient, friend));
                        return;
                    } else if (player != null) {
                        c.send(MainPacketCreator.getFindReply(recipient, friend, player.getClient().getChannel()));
                    } else {
                        c.send(MainPacketCreator.getWhisperReply(recipient, (byte) 0));
                    }
                }
                break;
            }
            case 6: {
                String recipient = rh.readMapleAsciiString();
                String text = rh.readMapleAsciiString();
                ServerConstants.chatlimit++;
                AdminTool.broadcastMessage(AdminToolPacket.sendChatText("[Whisper][Ch." + c.getChannel() + "]" + c.getPlayer().getName() + " : " + text));
                
                boolean isCommand = text.startsWith(String.valueOf(ServerConstants.ADMIN_COMMAND_PREFIX)) ||
                					text.startsWith(String.valueOf(ServerConstants.PLAYER_COMMAND_PREFIX));
                
                if (isCommand) {
                	CommandProcessor.getInstance().processCommand(c, text);
                } else {
                	MapleCharacter player = c.getChannelServer().getPlayerStorage().getCharacterByName(recipient);
                    if (player != null) {
                        if (player.isGM() && !c.getPlayer().isGM()) {
                            c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 0));
                        } else {
                            player.getClient().getSession().writeAndFlush(MainPacketCreator.getWhisper(c.getPlayer().getName(), c.getChannel(), text));
                            c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 1));
                            LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("GW", c.getPlayer(), "[Object : " + player.getName() + "] : " + text));
                        }
                    } else {
                        Collection<ChannelServer> cservs = ChannelServer.getAllInstances();
                        for (ChannelServer cserv : cservs) {
                            player = cserv.getPlayerStorage().getCharacterByName(recipient);
                            if (player != null) {
                                break;
                            }
                        }
                        if (player != null) {
                            if (!c.getPlayer().isGM() && player.isGM()) {
                                c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 0));
                            } else {
                                player.getClient().getSession().writeAndFlush(MainPacketCreator.getWhisper(c.getPlayer().getName(), c.getChannel(), text));
                                LoggerChatting.writeLog(LoggerChatting.chatLog, LoggerChatting.getChatLogType("GW", c.getPlayer(), "[Object : " + player.getName() + "] : " + text));
                                c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 1));
                            }
                        } else {
                            c.getSession().writeAndFlush(MainPacketCreator.getWhisperReply(recipient, (byte) 0));
                        }
                    }
                }
                break;
            }
        }
    }
}
