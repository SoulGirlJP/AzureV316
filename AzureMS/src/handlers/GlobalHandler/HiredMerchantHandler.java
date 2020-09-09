package handlers.GlobalHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.Character.MapleCharacter;
import client.ItemInventory.IItem;
import client.ItemInventory.ItemFactory;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PlayerShopPacket;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldConnected;
import server.Items.InventoryManipulator;
import server.Items.MerchItemPackage;
import tools.Pair;

public class HiredMerchantHandler {

    public static final void UseHiredMerchant(final ReadingMaple rh, final MapleClient c) {
        if (c.getPlayer().getMap().allowPersonalShop()) {
            final byte state = checkExistance(c.getAccID());

            switch (state) {
                case 1:
                    c.getPlayer().dropMessage(1, "Go find Fredrick first.");
                    break;
                case 0:
                    boolean merch = true;
                    merch = WorldConnected.hasMerchant(c.getAccID());
                    if (!merch) {
                        c.getSession().writeAndFlush(PlayerShopPacket.sendTitleBox());
                    } else {
                        c.getPlayer().dropMessage(1, "Employment shop is already open.");
                    }
                    break;
                default:
                    c.getPlayer().dropMessage(1, "An unknown error occured.");
                    break;
            }
        } else {
            c.getSession().close();
        }
    }

    public static void displayMerch(MapleClient c) {
        int conv = c.getPlayer().getConversation();
        boolean merch = WorldConnected.hasMerchant(c.getPlayer().getAccountID());
        MerchItemPackage pack;
        if (merch) {
            c.getPlayer().dropMessage(1, "Please close the merchant and try again");
            c.getPlayer().setConversation(0);
        } else if (c.getChannelServer().isShutdown()) {
            c.getPlayer().dropMessage(1, "Shutdown in progress, not available.");
            c.getPlayer().setConversation(0);
        } else if (conv == 3) {
            pack = loadItemFrom_Database(c.getPlayer().getId());
            if (pack == null) {
                c.getPlayer().dropMessage(1, "No items in storage.");
                c.getPlayer().setConversation(0);
            } else {
                c.getSession().writeAndFlush(PlayerShopPacket.merchItemStore_ItemData(pack));
            }
        }
        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
    }

    private static final byte checkExistance(final int accid) {
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where accountid = ?");
            ps.setInt(1, accid);

            if (ps.executeQuery().next()) {
                ps.close();
                con.close();
                return 1;
            }
            ps.close();
            con.close();
            return 0;
        } catch (SQLException se) {
            return -1;
        }
    }

    public static final void MerchantItemStore(final ReadingMaple rh, final MapleClient c) {
        final byte operation = rh.readByte();

        switch (operation) {
            case 23: {
                final String SPW = rh.readMapleAsciiString();
                if (!c.getSecondPassword().equals(SPW)) {
                    c.send(PlayerShopPacket.merchItem_checkSPW(false));
                    c.getPlayer().ea();
                    return;
                }
                final int conv = c.getPlayer().getConversation();
                if (conv == 3) { // Hired Merch
                    final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId());
                    boolean opened = false;
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        if (cserv.constainsMerchant(c.getAccID())) {
                            opened = true;
                            break;
                        }
                    }
                    c.getSession().writeAndFlush(PlayerShopPacket.merchItemStore_ItemData(pack));
                }
                break;
            }
            case 29: {
                c.getSession().writeAndFlush(PlayerShopPacket.merchItemStore((byte) 0x29));
                break;
            }
            case 30: {
                if (c.getPlayer().getConversation() != 3) {
                    return;
                }
                final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId());
                if (deletePackage(c.getPlayer().getId(), pack.getPackageid())) {
                    c.getPlayer().gainMeso(pack.getMesos(), false);
                    for (IItem item : pack.getItems()) {
                        InventoryManipulator.addFromDrop(c, item, false);
                    }
                    c.getPlayer().dropMessage(1, "Found all items.");
                    c.getSession().writeAndFlush(PlayerShopPacket.merchItem_Message((byte) 0x22));
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                } else {
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                }
                break;
            }
            case 31: { // Exit
                c.getPlayer().setConversation(0);
                break;
            }
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
        }
    }

    private static final boolean check(final MapleCharacter chr, final MerchItemPackage pack) {
        if (chr.getMeso() + pack.getMesos() < 0) {
            return false;
        }
        byte eq = 0, use = 0, setup = 0, etc = 0, cash = 0;
        for (IItem item : pack.getItems()) {
            final MapleInventoryType invtype = GameConstants.getInventoryType(item.getItemId());
            if (invtype == MapleInventoryType.EQUIP) {
                eq++;
            } else if (invtype == MapleInventoryType.USE) {
                use++;
            } else if (invtype == MapleInventoryType.SETUP) {
                setup++;
            } else if (invtype == MapleInventoryType.ETC) {
                etc++;
            } else if (invtype == MapleInventoryType.CASH) {
                cash++;
            }
        }
        if (chr.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() <= eq
                || chr.getInventory(MapleInventoryType.USE).getNumFreeSlot() <= use
                || chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() <= setup
                || chr.getInventory(MapleInventoryType.ETC).getNumFreeSlot() <= etc
                || chr.getInventory(MapleInventoryType.CASH).getNumFreeSlot() <= cash) {
            return false;
        }
        return true;
    }

    public static final boolean deletePackage(final int charid, int packageId) {
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE from hiredmerch where characterid = ?");
            ps.setInt(1, charid);
            ps.execute();
            ps.close();
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static final MerchItemPackage loadItemFrom_Database(final int charid) {
        Connection con = null;

        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where characterid = ?");
            ps.setInt(1, charid);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                ps.close();
                rs.close();
                con.close();
                return null;
            }
            final int packageid = rs.getInt("PackageId");

            final MerchItemPackage pack = new MerchItemPackage();
            pack.setPackageid(packageid);
            pack.setMesos(rs.getInt("Mesos"));
            pack.setSentTime(rs.getLong("time"));
            ps.close();
            rs.close();
            con.close();
            List<Pair<IItem, MapleInventoryType>> list = new ArrayList<>();
            for (IItem item : pack.getItems()) {
                list.add(new Pair<>(item, GameConstants.getInventoryType(item.getItemId())));
            }
            ItemFactory.HIRED_MERCHANT.saveItems(list, charid);

            return pack;
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
