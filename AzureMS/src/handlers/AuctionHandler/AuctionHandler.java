package handlers.AuctionHandler;

import constants.ServerConstants;


import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.Item;
import client.ItemInventory.ItemFactory;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;


import connections.Database.MYSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;


import connections.Packets.MainPacketCreator;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import launcher.LauncherHandlers.ChracterTransfer;
import launcher.ServerPortInitialize.AuctionServer;
import launcher.ServerPortInitialize.ChannelServer;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;


public class AuctionHandler {

    public static void EnterAuction(int playerid, MapleClient c) {
        final AuctionServer cs = AuctionServer.getInstance();
        final ChracterTransfer transfer = cs.getPlayerStorage().getPendingCharacter(playerid);

        MapleCharacter chr = MapleCharacter.ReconstructChr(transfer, c, false);
        c.setPlayer(chr);
        c.setAccID(chr.getAccountID());
        c.isAuction = true;

        final int state = c.getLoginState();
        boolean allowLogin = true;
        if (state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL) {
            if (!ChannelServer.isCharacterListConnected(c.loadCharacterNames(), false)) {
                allowLogin = true;
            }
        }
        if (!allowLogin) {
            c.setPlayer(null);
            c.getSession().close();
            return;
        }
        c.updateLoginState(MapleClient.LOGIN_LOGGEDIN, c.getSessionIPAddress());

        cs.getPlayerStorage().registerPlayer(chr);

        c.send(MainPacketCreator.getAuctionInfo(chr));
        c.sendPing();
    }

    public static final void Handle(final ReadingMaple rh, final MapleClient c, int op) {
        switch (op) {
            case 0: { // 아이템 로드
                c.getSession().writeAndFlush(AuctionPacket.AuctionOn());
                c.getSession().writeAndFlush(AuctionPacket.showItemPriceList(WorldAuction.getItems(c.getPlayer())));
                c.getSession().writeAndFlush(AuctionPacket.showItemList(WorldAuction.getItems(c.getPlayer()), false));
                break;
            }
            case 1: { // 경매장 퇴장
                final AuctionServer cs = AuctionServer.getInstance();
                cs.getPlayerStorage().deregisterPlayer(c.getPlayer());
                c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
                ChannelServer.getInstance(c.getChannel()).ChannelChange_Data(new ChracterTransfer(c.getPlayer()), c.getPlayer().getId(), c.getChannel());
                c.getSession().writeAndFlush(MainPacketCreator.getChannelChange(ServerConstants.basePorts + (c.getChannel()), ServerConstants.getServerHost(c)));
                c.getPlayer().saveToDB(false, true);
                c.setPlayer(null);
                c.isAuction = false;
                break;
            }
            case 10: { // 아이템 등록
                final boolean isbargain = rh.readInt() > 0;
                final int itemid = rh.readInt();
                final int quantity = rh.readInt();
                final long bid = 0;
                final long meso = rh.readLong();
                final int time = rh.readInt();
                final byte inv = rh.readByte();
                final short slot = rh.readShort();
                Item item = (Item) c.getPlayer().getInventory(MapleInventoryType.getByType(inv)).getItem(slot), copyItem;
                if (c.getPlayer().getMeso() < 10000) { // deposit
                    c.getSession().writeAndFlush(AuctionPacket.AuctionMessage((byte) 1, (byte) 3));
                    return;
                }
                if (item == null || item.getItemId() != itemid || item.getQuantity() < quantity) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }
                boolean isPremiumUser = AuctionHandler.WorldAuction.isPremiumUser(c.getPlayer());
                int limitCount = isPremiumUser ? 20 : 10;
                if (AuctionHandler.WorldAuction.getSellAllItemsCount(c.getPlayer().getId()) >= limitCount) {
                    c.getSession().writeAndFlush(AuctionHandler.AuctionPacket.AuctionMessage((byte) 1, (byte) 4));
                    return;
                }
                copyItem = (Item) item.copy();
                copyItem.setQuantity((short) quantity);
                AuctionItemPackage aitem = new AuctionItemPackage(c.getPlayer().getId(), c.getPlayer().getName(), copyItem, bid, meso, System.currentTimeMillis() + (time * 60 * 60 * 1000), isbargain, 0, 0, System.currentTimeMillis(), 0);
                WorldAuction.addItem(aitem);
                c.getPlayer().gainMeso(-10000, true); // deposit
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(op, 0));
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                InventoryManipulator.removeFromSlot(c, MapleInventoryType.getByType(inv), slot, (short) quantity, false);
                WorldAuction.addAuction(c.getPlayer().getId(), bid, item.getInventoryId(), (byte) 0);
                break;
            }
            case 11: {
                final int id = rh.readInt();
                AuctionItemPackage item = WorldAuction.findByIid(id);

                long gap = item.getExpiredTime() - item.getStartTime();

                item.setStartTime(System.currentTimeMillis());
                item.setExpiredTime(System.currentTimeMillis() + gap);
                item.setType(0);

                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(op, 0));
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.showItemList(WorldAuction.getItems(c.getPlayer()), false));
                break;
            }
            case 12: { // Unregister Item
                AuctionItemPackage item = WorldAuction.findByIid(rh.readInt());
                final int status = 4;
                if (item == null) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }
                if (status == 1) {
                    c.getPlayer().gainMeso(WorldAuction.getBidById(c.getPlayer().getId(), item.getItem().getInventoryId()), true);
                } else if (status == 3) {
                    c.getPlayer().gainMeso(item.getBid(), true);
                } else if (status == 2 || status == 4) {
                }

                item.setBuyTime(System.currentTimeMillis());

                boolean isOnwer = c.getPlayer().getId() == item.getOwnerId();
                if (status != 1) {
                    item.setType(status == 2 ? (item.getType(isOnwer, true) == 18 ? 27 : 17) : status == 3 ? (item.getType(isOnwer, true) == 17 ? 28 : 18) : status == 4 ? 4 : 0);
                    item.setBid(0);
                }
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(op, 0));
                break;
            }
            case 20: {
                final int id = rh.readInt();
                final long meso = rh.readLong();
                AuctionItemPackage item = WorldAuction.findByIid(id);

                if (item == null || c.getPlayer().getMeso() < meso || item.getMesos() > meso) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }

                if (item.getOwnerId() == c.getPlayer().getId() || item.getBuyer() != 0) {
                    c.getPlayer().dropMessage(1, "ERROR " + (item.getOwnerId() == c.getPlayer().getId()) + "|" + (item.getBuyer() != 0));
                    return;
                }

                c.getPlayer().gainMeso(-meso, false);

                item.setBid(meso);
                item.setBuyer(c.getPlayer().getId());
                item.setBuyTime(System.currentTimeMillis());
                item.setType(2);

                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(20, (byte) 0));
                break;
            }
            case 21: { // Purchase (number)
                final int id = rh.readInt();
                final long meso = rh.readLong();
                final int count = rh.readInt();
                AuctionItemPackage item = WorldAuction.findByIid(id);

                if (item == null || c.getPlayer().getMeso() < meso || (item.getMesos() * item.getItem().getQuantity()) < meso || item.getItem().getQuantity() < count) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }

                if (item.getOwnerId() == c.getPlayer().getId()) {
                    c.getPlayer().dropMessage(1, "ERROR2 " + (item.getOwnerId() == c.getPlayer().getId()) + "|" + (item.getBuyer() != 0));
                    return;
                }

                c.getPlayer().gainMeso(-meso, false);

                item.getItem().setQuantity((short) (item.getItem().getQuantity() - count));
                item.setBuyer(0);
                item.setBuyTime(System.currentTimeMillis());
                item.setBid(item.getBid() + meso);

                // All sold
                if (item.getItem().getQuantity() <= 0) {
                    item.setType(-1);
                }

                Item copyitem = (Item) item.getItem().copy();
                copyitem.setQuantity((short) count);

                AuctionItemPackage aitem = new AuctionItemPackage(item.getOwnerId(), item.getOwnerName(), copyitem, meso, meso * 2, item.getExpiredTime(), item.isBargain(), c.getPlayer().getId(), System.currentTimeMillis(), item.getStartTime(), 2);
                WorldAuction.addItem(aitem);

                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(20, (byte) 0));
                break;
            }
            case 30: { // Receipt of payment
                final int id = rh.readInt();

                AuctionItemPackage item = WorldAuction.findByIid(id);

                if (item == null || item.getType(true, false) != 3) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }

                long mesos = item.getBid() / 100 * 95 + 2000;

                c.getPlayer().gainMeso(mesos, true);

                boolean isOnwer = c.getPlayer().getId() == item.getOwnerId();
                item.setType(item.getType(isOnwer, true) == 17 ? 28 : 18);
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(30, (byte) 0));

                break;
            }
            case 31: { // Receive Goods
                final int id = rh.readInt();

                AuctionItemPackage item = WorldAuction.findByIid(id);
                if (item == null) {
                    c.getPlayer().dropMessage(1, "An error occurred.");
                    return;
                }

                boolean isNotSelled = System.currentTimeMillis() > item.getExpiredTime() && item.getOwnerId() == c.getPlayer().getId();
                InventoryManipulator.addbyItem(c, item.getItem());
                boolean isOnwer = c.getPlayer().getId() == item.getOwnerId();
                item.setType(isNotSelled ? 9 : (item.getType(isOnwer, true) == 18 ? 27 : 17));
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                c.getSession().writeAndFlush(AuctionPacket.AuctionMessage(31, (byte) 0));
                break;
            }
            case 40: { //Search
                List<AuctionItemPackage> allItem = WorldAuction.getItems(c.getPlayer());
                List<AuctionItemPackage> filter = new LinkedList<>();
		ItemInformation ii = ItemInformation.getInstance();
                
                rh.skip(1);
                rh.skip(4);
                rh.skip(4);
                
                String str1 = rh.readMapleAsciiString();
                String str2 = rh.readMapleAsciiString();
                
                
                for (AuctionItemPackage aip : allItem) {
                    if (ii.getName(aip.getItem().getItemId()).contains(str1) || ii.getName(aip.getItem().getItemId()).contains(str2))
                        filter.add(aip);
                    if (filter.size() == 100)
                        break;
                }
                
                c.getSession().writeAndFlush(AuctionPacket.showItemList(filter, true));
                break;
            }
            case 50: { // List of items for sale
                c.getSession().writeAndFlush(AuctionPacket.AuctionSell(WorldAuction.getSellItems(c.getPlayer().getId())));
                break;
            }
            case 51: { // Completed item list
                c.getSession().writeAndFlush(AuctionPacket.updateAuctionHistory(WorldAuction.getCompleteItems(c.getPlayer().getId()), c.getPlayer().getName(), c.getPlayer().getId()));
                break;
            }
        }
    }

    public static class SearchManager {

        public static void searchEquipDetailOption(int option, int value, List<AuctionItemPackage> allItem_, List<AuctionItemPackage> filter) {
            final ItemInformation ii = ItemInformation.getInstance();
            List<AuctionItemPackage> allItem = new LinkedList<>(allItem_);
            filter.clear();
            switch (option) {
                case 0: // STR
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getStr() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 1: // DEX
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getDex() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 2: // INT
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getInt() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 3: // LUK
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getLuk() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 4: // ATT
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getWatk() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 5: // M.ATT
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getMatk() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 6: // Void Damage
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getBossDamage() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 7: // Ignore Monster ERA
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getIgnoreWdef() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 8: // % Damage Increased
                {
                    int[] potentials = new int[]{601, 602, 603, 604, 30601, 30602, 32601, 40601, 40602, 40603, 42601, 42602, 60003, 60011, 60024, 60039}; // Increases damage from boss attacks
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 9: // Ignore Monster ERA
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).ignoreTargetDEF;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 10: // % Total damage increased
                {
                    int[] potentials = new int[]{10070, 12070, 20070, 22070, 30070, 32070, 32071, 40070, 42070, 42071, 60001, 60009, 60037}; // 데미지 %
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDAMr);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 11: // Attack Damage Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 12: // % M.ATT Damage ~ I Was thinking about Mage?
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 13: // All Stats % Increase
                {
                    int[] potentials = new int[]{391, 20086, 22086, 22087, 30086, 32086, 32087, 40086, 42086, 42087, 60002, 60004, 60005, 60038}; // All Stats : + #incSTRr%
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 14: // STR % Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 15: // DEX % Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 16: // INT % Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 17: // LUK % Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKr;
                                    }
                                    if (val >= value) {
                                        filter.add(item); // 
                                    }
                                }
                            });
                    break;
                case 18: // Critical Chance Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 19: // Abnormal duration reduced
                {
                    int[] potentials = new int[]{20396}; // Duration over all states:-# time seconds
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).time);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 20: // Increased state abnormal immunity
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAsrR;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 21: // Increased all property immunity
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incTerR;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 22: // Critical Damage Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMax;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 23: // Critical Damage Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incCriticaldamageMin;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 24: // Reduced all skill cooldowns
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).reduceCooltime;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 25: // Default Option: Increase Total Damage
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getAllDamageP() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 26: // Default option: Increase All Stats
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getAllStatP() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 27: // Increased item acquisition chance
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incRewardProp;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 28: // MaxHP % increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHPr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 29: // MaxMP % Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMPr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 30: // Basic option : MaxHP
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getHpR() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 31: // Basic option : MaxMP
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getMpR() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 32: // Attack Damage Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPAD;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 33: // M.ATT Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMAD;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                case 34: // Default option: Reduced wear restriction level
                    allItem.stream().filter(item -> item.getItem().getType() == 1)
                            .filter(item -> ((Equip) item.getItem()).getDownLevel() >= value).forEach(a -> {
                        if (!filter.contains(a)) {
                            filter.add(a);
                        }
                    });
                    break;
                case 35: // Usable Haste Skill
                {
                    int[] potentials = new int[]{31001};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 36: // Useful Mystic Door Skill
                {
                    int[] potentials = new int[]{31002};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 37: // <Useful Sharp Eyes> skill
                {
                    int[] potentials = new int[]{31003};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 38: // Usable Hyper Body Skills
                {
                    int[] potentials = new int[]{31004};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 39: // Useful Combat Orders
                {
                    int[] potentials = new int[]{41005};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 40: // <Useful Advanced Bless> Skill
                {
                    int[] potentials = new int[]{41006};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 41: // <Useful Wind Booster> Skill
                {
                    int[] potentials = new int[]{41007};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).level);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 42: // Maximum damage limit increased
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMaxDamage;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 43: // STR Increase
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 44: // DEX Increase
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEX;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 45: // INT Increase
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINT;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 46: { // LUK Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUK;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 47: // MaxHP Increase
                {
                    allItem.stream()
                            .forEach(item -> {
                                Equip e = (Equip) item.getItem();
                                int val = 0;
                                if (e.getPotential1() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (e.getPotential2() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (e.getPotential3() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (e.getPotential4() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (e.getPotential5() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (e.getPotential6() > 0) {
                                    val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMHP;
                                }
                                if (val >= value) {
                                    filter.add(item);
                                }
                            });
                    break;
                }
                case 48: { // MaxMP Increase
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMMP;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 49: // All Stats Increase
                {
                    int[] potentials = new int[]{201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 10081, 12081, 12082, 40081};
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    for (int next = 0; next < potentials.length; ++next) {
                                        if (e.getPotential1() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                        if (e.getPotential2() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                        if (e.getPotential3() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                        if (e.getPotential4() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                        if (e.getPotential5() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                        if (e.getPotential6() == potentials[next]) {
                                            val += (ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTR);
                                        }
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 50: // All skill levels increased
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incAllskill;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 51: // HP Recovery on Attack
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).HP;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 52: { // Mp Recovery on Attack
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).MP;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 53: // STR increase per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incSTRlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 54: // DEX increase per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incDEXlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 55: // INT increase per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incINTlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 56: // Increase LUK per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incLUKlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 57: // HP Recovery Items and Skill Efficiency Increase
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).RecoveryUP;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 58: // Experience gain increased
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incEXPr;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 59: // Meso gain increased
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMesoProp;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 60: // Attack damage per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incPADlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
                case 61: // Increases horsepower per character level 10
                {
                    allItem.stream()
                            .forEach(item -> {
                                if (item.getItem().getType() == 1) {
                                    Equip e = (Equip) item.getItem();
                                    int val = 0;
                                    if (e.getPotential1() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential1()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (e.getPotential2() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential2()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (e.getPotential3() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential3()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (e.getPotential4() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential4()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (e.getPotential5() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential5()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (e.getPotential6() > 0) {
                                        val += ii.getPotentialInfo(e.getPotential6()).get(Math.max(0, (ii.getReqLevel(item.getItem().getItemId()) / 10) - 1)).incMADlv;
                                    }
                                    if (val >= value) {
                                        filter.add(item);
                                    }
                                }
                            });
                    break;
                }
            }
        }
    }

    public static class WorldAuction {

        static List<AuctionItemPackage> items = new ArrayList<>();
        static Map<Integer, List<AuctionInfo>> auctions = new HashMap<>();

        public static void addAuction(final int cid, final long bid, final int iid, final byte status) {
            if (auctions.get(iid) == null) {
                auctions.put(iid, new ArrayList<AuctionInfo>());
            }
            boolean isBest = true, isExist = false;
            for (AuctionInfo ai : auctions.get(iid)) {
                if (ai.getCharacterId() == cid) {
                    isExist = true;
                    auctions.get(iid).set(auctions.get(iid).indexOf(ai), new AuctionInfo(bid, cid, status));
                }
                if (bid < ai.getBid()) {
                    isBest = false;
                }
            }
            if (!isExist) {
                auctions.get(iid).add(new AuctionInfo(bid, cid, status));
            }
            if (isBest) {
            }
        }

        public static long getBidById(final int cid, final int iid) {
            long bid = 0;
            if (auctions.size() > 0) {
                if (auctions.get(iid) != null) {
                    for (AuctionInfo ai : auctions.get(iid)) {
                        if (ai.getCharacterId() == cid && ai.getBid() >= bid) {
                            bid = ai.getBid();
                        }
                    }
                }
            }
            return bid;
        }

        public static List<AuctionItemPackage> getItems(MapleCharacter player) {
            List<AuctionItemPackage> items_ = new ArrayList<>();
            for (AuctionItemPackage aitem : items) {
                if ((aitem.expiredTime > System.currentTimeMillis() && aitem.type == 0 && aitem.getOwnerId() != player.getId() && (aitem.getBuyer() == 999999 || aitem.getBuyer() == 0)) && aitem.type != 27  && aitem.type != 28) {
                    items_.add(aitem);
                }
            }
            return items_;
        }

        public static List<AuctionItemPackage> getAllItems() {
            List<AuctionItemPackage> items_ = new ArrayList<>();
            for (AuctionItemPackage aitem : items) {
                if ((aitem.expiredTime > System.currentTimeMillis() && (aitem.getBuyer() == 999999 || aitem.getBuyer() == 0)) && aitem.type != 27  && aitem.type != 28) {
                    items_.add(aitem);
                }
            }
            return items_;
        }

        public static List<AuctionItemPackage> getCompleteItems(final int charid) {
            List<AuctionItemPackage> items_ = new ArrayList<>();
            for (AuctionItemPackage aitem : items) {
                if (aitem.type != 0 && (aitem.getOwnerId() == charid || aitem.getBuyer() == charid || getBidById(charid, (int) aitem.getItem().getInventoryId()) > 0) && aitem.type != 27  && aitem.type != 28 && aitem.type != 17  && aitem.type != 18) {
                    items_.add(aitem);
                }
            }
            System.out.println("getCompleteItems " + items_.size());
            return items_;
        }

        public static List<AuctionItemPackage> getSellAllItems(final int charid) {
            List<AuctionItemPackage> items_ = new ArrayList<>();
            for (AuctionItemPackage aitem : items) {
                if (aitem.getBuyer() == 0 && aitem.getType(false, true) == 0 && aitem.type != 27  && aitem.type != 28) {
                    items_.add(aitem);
                }
            }
            return items_;
        }

        public static int getSellAllItemsCount(final int charid) {
            int count = 0;
            for (AuctionItemPackage aitem : items) {
                if (aitem.getBuyer() == 0 && (aitem.type == 4 || aitem.type == 0) && aitem.ownerid == charid && aitem.type != 27  && aitem.type != 28) {
                    count++;
                }
            }
            return count;
        }

        public static int getBuyAllItemsCount(final int charid) {
            int count = 0;
            for (AuctionItemPackage aitem : items) {
                if (aitem.getBuyer() == 0 && (aitem.type == 2) && aitem.ownerid == charid && aitem.type != 27  && aitem.type != 28) {
                    count++;
                }
            }
            return count;
        }

        public static List<AuctionItemPackage> getSellItems(final int charid) {
            List<AuctionItemPackage> items_ = new ArrayList<>();
            for (AuctionItemPackage aitem : items) {
                if (aitem.getBuyer() == 0 && aitem.getType(false, true) == 0 && aitem.getOwnerId() == charid && aitem.type != 27  && aitem.type != 28) {
                    items_.add(aitem);
                }
            }
            return items_;
        }

        public static final boolean isPremiumUser(MapleCharacter player) {
            String grade = player.getOneInfoQuest(18202, "grade");
            if (grade != null && !grade.isEmpty() && Integer.parseInt(grade) > 5) {
                String date = player.getOneInfoQuest(18202, "end");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                try {
                    Date d = sdf.parse(date);
                    if (d.getTime() > System.currentTimeMillis()) {
                        return true;
                    }
                } catch (ParseException e) {
                    System.err.println(e);
                }
            }
            return false;
        }

        public static final void addItem(final AuctionItemPackage aitem) {
            aitem.getItem().setInventoryId(items.size() + 1);
            items.add(aitem);
        }

        public static final AuctionItemPackage findByIid(final int id) {
            for (AuctionItemPackage item : items) {
                if (item.getItem().getInventoryId() == id) {
                    return item;
                }
            }
            return null;
        }

        public static final void load() { 
            try {
                System.out.println("[Notification] Load Maple Auction data.");
                ItemFactory.AUCTION.loadAuctionItem();
                Connection con = MYSQL.getConnection();
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM `auctions`")) {
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            addAuction(rs.getInt("characterid"), rs.getLong("bid"), rs.getInt("inventoryid"), rs.getByte("status"));
                        }
                    }
                }
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        public static final void save() {
            try {
                System.out.println("Saving auction house..");
                final Connection con = MYSQL.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM auctions");
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("DELETE FROM auctionitems");
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("DELETE FROM auctionequipment");
                ps.executeUpdate();
                ps.close();
                ItemFactory.AUCTION.saveAuctionItems(items);
                ps = con.prepareStatement("INSERT INTO auctions (`inventoryid`, `characterid`, `bid`, `status`) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                for (Map.Entry<Integer, List<AuctionInfo>> au : auctions.entrySet()) {
                    ps.setInt(1, au.getKey());
                    for (AuctionInfo ai : au.getValue()) {
                        ps.setInt(2, ai.getCharacterId());
                        ps.setLong(3, ai.getBid());
                        ps.setByte(4, ai.getStatus());
                        ps.executeUpdate();
                    }
                }
                ps.close();
                con.close();
            } catch (SQLException ex) {
            }
        }
    }

    public static class AuctionInfo {

        private int cid;
        private long bid;
        private byte status;

        public AuctionInfo(final long bid, final int cid, final byte status) {
            this.bid = bid;
            this.cid = cid;
            this.status = status;
        }

        public void setBid(final long bid) {
            this.bid = bid;
        }

        public long getBid() {
            return bid;
        }

        public int getCharacterId() {
            return cid;
        }

        public void setStatus(final byte status) {
            this.status = status;
        }

        public byte getStatus() {
            return status;
        }
    }

    public static class AuctionItemPackage {

        private long expiredTime, buyTime, startTime;
        private long bid = 0, mesos = 0;
        private Item item;
        private boolean bargain;
        private int ownerid, buyer, type;
        private String ownername;

        public AuctionItemPackage(final int ownerid, final String ownername, final Item item, final long bid, final long mesos, final long expiredTime, final boolean bargain, final int buyer, final long buyTime, final long startTime, final int type) {
            this.ownerid = ownerid;
            this.ownername = ownername;
            this.item = item;
            this.bid = bid;
            this.mesos = mesos;
            this.expiredTime = expiredTime;
            this.bargain = bargain;
            this.buyer = buyer;
            this.buyTime = buyTime;
            this.startTime = startTime;
            this.type = type;
        }

        public int getOwnerId() {
            return ownerid;
        }

        public String getOwnerName() {
            return ownername;
        }

        public void setExpiredTime(long expiredTime) {
            this.expiredTime = expiredTime;
        }

        public long getExpiredTime() {
            return expiredTime;
        }

        public void setBuyTime(long buyTime) {
            this.buyTime = buyTime;
        }

        public long getBuyTime() {
            return buyTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getBid() {
            return bid;
        }

        public void setBid(long set) {
            bid = set;
        }

        public long getMesos() {
            return mesos;
        }

        public void setMesos(long set) {
            mesos = set;
        }

        public Item getItem() {
            return item;
        }

        public boolean isBargain() {
            return bargain;
        }

        public int getBuyer() {
            return buyer;
        }

        public void setBuyer(int buyer) {
            this.buyer = buyer;
        }

        public int getType(boolean isOwner, boolean isReal) {
            if (isReal) {
                return type;
            }

            if (type == 17) {
                return isOwner ? 3 : 7;
            }

            if (type == 27) {
                return isOwner ? 8 : 7;
            }

            if (type == 18) {
                return isOwner ? 8 : 2;
            }

            if (type == 28) {
                return isOwner ? 8 : 7;
            }

            if (type == 2) {
                return isOwner ? 3 : 2;
            }

            if (type == 0) {
                return isOwner ? 4 : 0;
            }

            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class AuctionPacket {

        public static byte[] AuctionMessage(int message, int sub) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(message);
            mplew.writeInt(0);
            mplew.writeInt(sub);
            return mplew.getPacket();
        }
        
        public static byte[] AuctionOn() {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(0);
            mplew.writeInt(0);
            mplew.writeInt(0);
            return mplew.getPacket();
        }

        public static byte[] showItemList(List<AuctionItemPackage> items, final boolean isSearch) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(40);
            mplew.writeInt(1000); // 316
            mplew.writeInt(0);
            mplew.write(1);
            mplew.write(0);
            mplew.writeInt(items.size());
            for (AuctionItemPackage aitem : items) {
                addAuctionItemInfo(mplew, aitem, 0, "");
            }
            return mplew.getPacket();
        }
        
        public static byte[] showItemPriceList(List<AuctionItemPackage> items) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(41);
            mplew.writeInt(1000); // 316
            mplew.writeInt(0);
            mplew.write(1);
            mplew.write(0);
            mplew.writeInt(items.size());
            for (AuctionItemPackage aitem : items) {
                addAuctionItemInfo(mplew, aitem, 0, "");
            }
            return mplew.getPacket();
        }

        public static byte[] updateAuctionHistory(List<AuctionItemPackage> items, final String buyername, final int ownerId) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(51);
            mplew.writeInt(0); // 316
            mplew.writeInt(0);
            
            mplew.writeInt(items.size());
            for (AuctionItemPackage aitem : items) {
                boolean Refund = false;
                boolean boughtItem = aitem.getBuyer() == ownerId;
                Item item = aitem.getItem();
                int status = aitem.getType(ownerId == aitem.getOwnerId(), false);

                mplew.writeLong((int) item.getInventoryId());
                mplew.writeInt(item.getInventoryId());
                mplew.writeInt(boughtItem ? ownerId : aitem.getOwnerId());
                mplew.writeInt(boughtItem ? ownerId : aitem.getOwnerId());
                mplew.writeInt(item.getItemId());
                mplew.writeInt(status);
                mplew.writeLong(aitem.getBid());
                mplew.writeLong(PacketProvider.getTime(aitem.getBuyTime() + (12 * 60 * 60 * 1000)));
                mplew.writeLong(boughtItem ? 0 : 2000);
                mplew.writeInt(aitem.getItem().getQuantity());
                mplew.writeInt(0);

                mplew.write(Refund ? 0 : 1);
                if (!Refund) {
                    //mplew.writeInt(aitem.getItem().getInventoryId());
                    addAuctionItemInfo(mplew, aitem, ownerId, buyername);
                }
            }
            return mplew.getPacket();
        }

        public static byte[] AuctionSell(List<AuctionItemPackage> aitems) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION.getValue());
            mplew.writeInt(50);
            mplew.writeInt(0); // 316
            mplew.writeInt(0);
            
            mplew.writeInt(aitems.size());
            for (AuctionItemPackage aitem : aitems) {
                addAuctionItemInfo(mplew, aitem, 0, "");
            }

            return mplew.getPacket();
        }

        public static byte[] AuctionBuy(AuctionItemPackage aitem, final long price, final int status) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION_BUY.getValue());
            mplew.writeInt(51);
            mplew.writeInt((int) aitem.getItem().getInventoryId());
            mplew.writeInt(0);
            mplew.writeInt(aitem.getBuyer());
            mplew.writeInt(aitem.getItem().getItemId());
            mplew.writeInt(status);
            mplew.writeLong(price);
            mplew.writeLong(PacketProvider.getTime(aitem.getBuyTime() + (12 * 60 * 60 * 1000)));
            mplew.writeLong(0);
            mplew.writeInt(1);
            mplew.writeInt(7);

            return mplew.getPacket();
        }

        public static byte[] AuctionBargaining(AuctionItemPackage aitem, long bargainmeso, final String bargainstring) {
            WritingPacket mplew = new WritingPacket();
            mplew.writeShort(SendPacketOpcode.AUCTION_BUY.getValue());
            mplew.write(0);
            mplew.writeInt(aitem.isBargain() ? 1 : 0);
            mplew.writeInt(aitem.getItem().getInventoryId());
            mplew.writeInt(0);
            mplew.writeInt(0);
            mplew.writeLong(0);
            mplew.writeMapleAsciiString(aitem.getOwnerName());
            mplew.writeLong(bargainmeso);
            mplew.writeMapleAsciiString(bargainstring);
            mplew.writeInt(0);
            mplew.writeInt(0);
            mplew.writeInt(0);
            mplew.writeInt(0);

            mplew.write(1);
            PacketProvider.addItemInfo(mplew, aitem.getItem(), true, true, null);

            return mplew.getPacket();
        }

        public static void addAuctionItemInfo(WritingPacket mplew, AuctionItemPackage aitem, final int ownerId, final String buyername) {
            Item item = aitem.getItem();
            int status = aitem.getType(ownerId == aitem.getOwnerId(), false);
            mplew.writeInt(item.getInventoryId());
            mplew.writeInt(aitem.isBargain() ? 1 : 0);
            mplew.writeInt(aitem.getOwnerId());
            mplew.writeInt(aitem.getOwnerId());
            mplew.writeInt(status);
            mplew.writeInt(0);
            mplew.writeAsciiString(aitem.getOwnerName(), 13);
            mplew.writeLong(0);
            mplew.writeLong(-1);
            mplew.writeLong(aitem.getMesos() * aitem.getItem().getQuantity());
            mplew.writeLong(aitem.getMesos()); // 316
            mplew.writeLong(PacketProvider.getTime(aitem.getExpiredTime()));
            mplew.writeInt(aitem.getBuyer());
            mplew.writeAsciiString(buyername, 13);
            mplew.writeInt(-1);
            mplew.writeLong(0);
            mplew.writeLong(PacketProvider.getTime(aitem.getStartTime()));
            mplew.writeLong(2000);
            mplew.writeInt(aitem.getItem().getItemId() / 1000000);
            mplew.writeInt(0); // 316
            mplew.writeLong(PacketProvider.getTime(-2)); // 316
            PacketProvider.addItemInfo(mplew, item, true, true, null);
        }
    }
}
