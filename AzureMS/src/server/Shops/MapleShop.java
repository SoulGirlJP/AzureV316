package server.Shops;

import client.Character.MapleCharacter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import client.MapleClient;
import client.ItemInventory.IItem;
import client.ItemInventory.Item;
import client.ItemInventory.MapleInventoryType;
import client.Skills.SkillFactory;
import constants.GameConstants;
import connections.Database.MYSQL;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.Utility.WorldBroadcasting;
import connections.Packets.MainPacketCreator;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import tools.CurrentTime;
import tools.FileoutputUtil;

public class MapleShop {

    private static final Set<Integer> rechargeableItems = new LinkedHashSet<Integer>();
    private int id;
    private int npcId;
    private List<MapleShopItem> items;

    static {
        rechargeableItems.add(2070000);
        rechargeableItems.add(2070001);
        rechargeableItems.add(2070002);
        rechargeableItems.add(2070003);
        rechargeableItems.add(2070004);
        rechargeableItems.add(2070005);
        rechargeableItems.add(2070006);
        rechargeableItems.add(2070007);
        rechargeableItems.add(2070008);
        rechargeableItems.add(2070009);
        rechargeableItems.add(2070010);
        rechargeableItems.add(2070011);
        rechargeableItems.add(2070012);
        rechargeableItems.add(2070013);
        rechargeableItems.add(2070023);
        rechargeableItems.add(2070024);
        rechargeableItems.add(2070026);
        rechargeableItems.add(2330000);
        rechargeableItems.add(2330001);
        rechargeableItems.add(2330002);
        rechargeableItems.add(2330003);
        rechargeableItems.add(2330004);
        rechargeableItems.add(2330005);
        rechargeableItems.add(2330008);
        rechargeableItems.add(2330016);
        rechargeableItems.add(2331000);
        rechargeableItems.add(2332000);
    }

    public MapleShop(int id, int npcId) {
        this.id = id;
        this.npcId = npcId;
        items = new LinkedList<MapleShopItem>();
    }

    public void addItem(MapleShopItem item) {
        items.add(item);
    }

    public void sendShop(MapleClient c) {
        if (items == null) {
            System.out.println("[Notification] No information in store.");
            return;
        }
        c.getPlayer().setShop(this);
        c.getSession().writeAndFlush(MainPacketCreator.getNPCShop(c, getNpcId(), items));
    }

    public void buy(MapleClient c, int itemId, short quantity, short position) {
   if (quantity < 0) {
          WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(7, c.getPlayer().getName() + " Was automatically banned by copying."));
            c.getSession().close();
            c.getPlayer().ban("Negative Radiation Nucleus (Sales)", false, false);
            return;
        }
        int x = 0, index = -1;
        for (IItem i : c.getPlayer().getRebuyList()) {
            if (i.getItemId() == itemId) {
                index = x;
                break;
            }
            x++;
        }
        if (index >= 0) {
            ItemInformation ii = ItemInformation.getInstance();
            final IItem i = c.getPlayer().getRebuyList().get(index);
            final int price = (int) Math.max(Math.ceil(ii.getPrice(itemId) * (GameConstants.isRechargable(itemId) ? 1 : i.getQuantity())), 0);
            if (price >= 0 && c.getPlayer().getMeso() >= price) {
                if (InventoryManipulator.checkSpace(c, itemId, i.getQuantity(), i.getOwner())) {
                    c.getPlayer().gainMeso(-price, false);
                    InventoryManipulator.addbyItem(c, i);
                    c.getPlayer().getRebuyList().remove(index);
                    c.getSession().write(MainPacketCreator.confirmShopTransaction((byte) 0, index));
                } else {
                    c.getPlayer().dropMessage(1, "Inventory is full.");
                    c.getSession().write(MainPacketCreator.confirmShopTransaction((byte) 0, -1));
                }
            } else {
                c.getSession().write(MainPacketCreator.confirmShopTransaction((byte) 0, -1));
            }
            return;
        }
        MapleShopItem item = findById(itemId, position);
        if (item != null && item.getPrice() > 0 && item.getPriceQuantity() == 0) {
            if (c.getPlayer().getMeso() >= item.getPrice() * quantity) {
                if (InventoryManipulator.checkSpace(c, itemId, (short) (quantity * item.getQuantity()), "")) {
                    if (GameConstants.isPet(itemId)) {
                        c.getPlayer().BuyPET(itemId);
                        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "" + ItemInformation.getInstance().getName(itemId) + "I purchased a pet."));
                        c.getPlayer().gainMeso(-(item.getPrice()), false);
                    } else {
                        ItemInformation ii = ItemInformation.getInstance();
                        if (GameConstants.isRechargable(itemId)) {
                            quantity = ii.getSlotMax(c, item.getItemId());
                            c.getPlayer().gainMeso(-(item.getPrice()), false);
                            InventoryManipulator.addById(c, itemId, (short) (quantity * item.getQuantity()), null, null, item.getPeriod() <= 0 ? 0 : ((item.getPeriod() * 60L * 1000L) + System.currentTimeMillis()), CurrentTime.getAllCurrentTime() + " on " + getId() + " Item purchased at store.");
                        } else {
                            c.getPlayer().gainMeso(-(item.getPrice() * quantity), false);
                            c.getPlayer().gainItem(itemId, (short) (item.getQuantity() * quantity), true, item.getPeriod() <= 0 ? 0 : ((item.getPeriod() * 60L * 1000L) + System.currentTimeMillis()), "Item purchased at store");
                        }
                    }
                } else {
                    c.getPlayer().dropMessage(1, "Not enough inventory.");
                }
                c.getSession().write(MainPacketCreator.confirmShopTransaction((byte) 0, -1));
            }
        } else if (item != null && item.getPrice() > 0 && item.getPriceQuantity() > 0) {
            if (c.getPlayer().haveItem(item.getPrice(), item.getPriceQuantity() * quantity, false, true)) {
                if (InventoryManipulator.checkSpace(c, itemId, (short) (quantity * item.getQuantity()), "")) {
                    if (GameConstants.isPet(itemId)) {
                        c.getPlayer().BuyPET(itemId);
                        c.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "" + ItemInformation.getInstance().getName(itemId) + "I purchased a pet."));
                        InventoryManipulator.removeById(c, GameConstants.getInventoryType(item.getPrice()), item.getPrice(), item.getPriceQuantity(), false, false);
                    } else {
                        ItemInformation ii = ItemInformation.getInstance();
                        if (GameConstants.isRechargable(itemId)) {
                            quantity = ii.getSlotMax(c, item.getItemId());
                            InventoryManipulator.removeById(c, GameConstants.getInventoryType(item.getPrice()), item.getPrice(), item.getPriceQuantity(), false, false);
                            InventoryManipulator.addById(c, itemId, (short) (quantity * item.getQuantity()), null, null, item.getPeriod() <= 0 ? 0 : ((item.getPeriod() * 60L * 1000L) + System.currentTimeMillis()), CurrentTime.getAllCurrentTime() + "에 " + getId() + " 상점에서 구매한 아이템.");
                        } else {
                            InventoryManipulator.removeById(c, GameConstants.getInventoryType(item.getPrice()), item.getPrice(), item.getPriceQuantity() * quantity, false, false);
                            if (GameConstants.isMonsterLifeBox(itemId)) {
                                IItem item1 = InventoryManipulator.BuyMonsterLifeWeapon(itemId);
                                if (InventoryManipulator.checkSpace(c, item1.getItemId(), (short) 1, "")) {
                                    InventoryManipulator.addbyItem(c, item1);
                                } else {
                                    c.getPlayer().dropMessage(1, "There is not enough inventory.");
                                }
                            } else {
                                InventoryManipulator.addById(c, itemId, (short) (quantity * item.getQuantity()), null, null, item.getPeriod() <= 0 ? 0 : ((item.getPeriod() * 60L * 1000L) + System.currentTimeMillis()), CurrentTime.getAllCurrentTime() + "에 " + getId() + " 상점에서 구매한 아이템.");
                            }
                        }
                    }
                } else {
                    c.getPlayer().dropMessage(1, "There is not enough inventory.");
                }
                c.getSession().write(MainPacketCreator.confirmShopTransaction((byte) 0, -1));
            }
        }
    }

    public void sell(MapleClient c, MapleInventoryType type, byte slot, short quantity) {
   if (quantity < 0) {
            WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(21, c.getPlayer().getName()+" Was automatically banned by copying."));
            c.getSession().close();
            c.getPlayer().ban("Negative Radiation Nuclear (Purchase)", false, false);
            return;
        }
        IItem item = c.getPlayer().getInventory(type).getItem(slot);

        if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
            quantity = item.getQuantity();
        }
        short iQuant = item.getQuantity();
        if (iQuant == 0xFFFF) {
            iQuant = 1;
        }
        final ItemInformation ii = ItemInformation.getInstance();
        if (quantity <= iQuant && iQuant > 0) {
            IItem itemm = item.copy();
            itemm.setQuantity((short) quantity);
            c.getPlayer().getRebuyList().add(itemm);
            if (quantity < 0) {
                ban(c, c.getPlayer());
                WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(21, "[Notice] " + c.getPlayer().getName() + " Was automatically banned by copying."));
                FileoutputUtil.logToFile_("복사핵로그.txt", c.getPlayer().getName() + "\r\n");
                c.getSession().close();
                return;
            }
            InventoryManipulator.removeFromSlot(c, type, slot, quantity, false);
            double price;
            if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
                price = ii.getWholePrice(item.getItemId()) / (double) ii.getSlotMax(c, item.getItemId());
            } else {
                price = ii.getPrice(item.getItemId());
            }
            int recvMesos = (int) Math.max(Math.ceil(price * quantity), 0);
            if (recvMesos >= Integer.MAX_VALUE) {
                c.getPlayer().dropMessage(1, "An error has occurred. Please try again in a few minutes.");
                return;
            }
            if (price != -1 && recvMesos > 0) {
                c.getPlayer().gainMeso(recvMesos, false);
            }
            c.send(MainPacketCreator.confirmShopTransactionAdditional(getNpcId(), items, this, c));
        }
    }

    public void recharge(final MapleClient c, final byte slot) {
        final IItem item = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);

        if (item == null || (!GameConstants.isThrowingStar(item.getItemId()) && !GameConstants.isBullet(item.getItemId()))) {
            return;
        }
        final ItemInformation ii = ItemInformation.getInstance();
        short slotMax = ii.getSlotMax(c, item.getItemId());
        final int skill = GameConstants.getMasterySkill(c.getPlayer().getJob());

        if (skill != 0) {
            slotMax += c.getPlayer().getSkillLevel(SkillFactory.getSkill(skill)) * 10;
        }
        if (item.getQuantity() < slotMax) {
            final int price = (int) Math.round(ii.getPrice(item.getItemId()) * (slotMax - item.getQuantity()));
            if (c.getPlayer().getMeso() >= price) {
                item.setQuantity(slotMax);
                c.getSession().writeAndFlush(MainPacketCreator.updateInventorySlot(MapleInventoryType.USE, (Item) item, false));
                c.getPlayer().gainMeso(-price, false, true, false);
                c.getSession().writeAndFlush(MainPacketCreator.confirmShopTransaction((byte) 0, -1));
            }
        }
    }

    protected MapleShopItem findById(int itemId, int position) {
        for (MapleShopItem item : items) {
            if (item.getItemId() == itemId && item.getPosition() == position) {
                return item;
            }
        }
        for (MapleShopItem item : items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public static MapleShop createFromDB(int id, boolean isShopId) {
        MapleShop ret = null;
        int shopId;

        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement(isShopId ? "SELECT * FROM shops WHERE shopid = ?" : "SELECT * FROM shops WHERE npcid = ?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shopId = rs.getInt("shopid");
                ret = new MapleShop(shopId, rs.getInt("npcid"));
                rs.close();
                ps.close();
            } else {
                rs.close();
                ps.close();
                con.close();
                return null;
            }
            ps = con.prepareStatement("SELECT * FROM shopitems WHERE shopid = ? ORDER BY position ASC");
            ps.setInt(1, shopId);
            rs = ps.executeQuery();
            List<Integer> recharges = new ArrayList<Integer>(rechargeableItems);
            int i = 0;
            while (rs.next()) {
                if (GameConstants.isThrowingStar(rs.getInt("itemid")) || GameConstants.isBullet(rs.getInt("itemid"))) {
                    MapleShopItem starItem = new MapleShopItem((short) 1, rs.getInt("itemid"), rs.getInt("price"), rs.getInt("pricequantity"), (byte) rs.getInt("Tab"), rs.getInt("quantity"), rs.getInt("period"), i);
                    ret.addItem(starItem);
                    if (rechargeableItems.contains(starItem.getItemId())) {
                        recharges.remove(Integer.valueOf(starItem.getItemId()));
                    }
                } else {
                    ret.addItem(new MapleShopItem((short) 1000, rs.getInt("itemid"), rs.getInt("price"), rs.getInt("pricequantity"), (byte) rs.getInt("Tab"), rs.getInt("quantity"), rs.getInt("period"), i));
                }
                i++;
            }
            i = 0;
            for (Integer recharge : recharges) {
                ret.addItem(new MapleShopItem((short) 1000, recharge.intValue(), 0, 0, (byte) 0, 0, 0, i));
                i++;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Could not load shop" + e);
        }
        return ret;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getId() {
        return id;
    }

    public void ban(MapleClient c, MapleCharacter chr) {
        ChannelServer cserv = c.getChannelServer();
        final StringBuilder sb = new StringBuilder(c.getPlayer().getName());
        sb.append(" banned ").append(chr.getName()).append(": ").append("복사핵");

        final MapleCharacter target = cserv.getPlayerStorage().getCharacterByName(chr.getName());

        if (target != null) {
            sb.append(" (IP: ").append(target.getClient().getIp().split(":")[0]).append(")");
            if (target.ban(sb.toString(), true, false)) {
                if (c.getPlayer().getKeyValue("Banned_Today") == null) {
                    c.getPlayer().setKeyValue("Banned_Today", "0");
                }
                c.getPlayer().setKeyValue("Banned_Today", (Integer.parseInt(c.getPlayer().getKeyValue("Banned_Today")) + 1) + "");
            } else {
                c.getPlayer().dropMessage(6, "Ban failed.");
            }
        } else {
            if (MapleCharacter.ban(chr.getName(), sb.toString(), false)) {
                c.getPlayer().dropMessage(6, chr.getName() + " Offline Ban Success.");
            } else {
                c.getPlayer().dropMessage(6, chr.getName() + " Failed to ban.");
            }
        }
    }
}
