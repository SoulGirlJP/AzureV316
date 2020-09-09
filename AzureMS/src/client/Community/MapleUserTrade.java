package client.Community;

import java.util.LinkedList;
import java.util.List;

import client.Character.MapleCharacter;
import client.ItemInventory.IItem;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import constants.GameConstants;
import connections.Packets.MainPacketCreator;
import server.Items.InventoryManipulator;
import tools.LoggerChatting;
import constants.ServerConstants;
import launcher.AdminGUI.AdminToolStart;

public class MapleUserTrade {

    private MapleUserTrade partner = null;
    private final List<IItem> items = new LinkedList<>();
    public List<IItem> exchangeItems;
    public long meso = 0, exchangeMeso = 0;
    private boolean locked = false;
    private final MapleCharacter chr;
    private final byte tradingslot;
    private byte rps = 0;
    public byte type = 0;

    public MapleUserTrade(final byte tradingslot, final MapleCharacter chr) {
        this.tradingslot = tradingslot;
        this.chr = chr;
    }

    public final void CompleteTrade() {
        for (final IItem item : exchangeItems) {
            short flag = item.getFlag();

            if (ItemFlag.KARMA_EQ.check(flag)) {
                item.setFlag((short) (flag - ItemFlag.KARMA_EQ.getValue()));
            } else if (ItemFlag.KARMA_USE.check(flag) && !GameConstants.isEquip(item.getItemId())) {
                item.setFlag((short) (flag - ItemFlag.KARMA_USE.getValue()));
            }
            InventoryManipulator.addFromDrop(chr.getClient(), item, false);
        }
        if (exchangeMeso > 0) {
            chr.gainMeso(exchangeMeso - GameConstants.getTaxAmount(exchangeMeso), false, true, false);
        }
        exchangeMeso = 0;
        if (exchangeItems != null) { // just to be on the safe side...
            exchangeItems.clear();
        }
        chr.isTrade = false;
        chr.getClient().getSession().writeAndFlush(MainPacketCreator.TradeMessage(tradingslot, (byte) 0x07));
    }

    public final void cancel() {
        for (final IItem item : items) {
            InventoryManipulator.addFromDrop(chr.getClient(), item, false);
        }
        if (meso > 0) {
            chr.gainMeso(meso, false, true, false);
        }
        meso = 0;
        if (items != null) { // just to be on the safe side...
            items.clear();
        }
        if (chr.isTrade) {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.getTradeCancel(tradingslot));
        } else {
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.ExitRPS());
        }

        chr.isTrade = false;
    }

    public final boolean isLocked() {
        return locked;
    }

    public final void setMeso(final int meso) {
        if (locked || partner == null || meso <= 0 || this.meso + meso <= 0) {
            return;
        }
        if (chr.getMeso() >= meso) {
            chr.gainMeso(-meso, false, true, false);
            this.meso += meso;
            chr.getClient().getSession().writeAndFlush(MainPacketCreator.getTradeMesoSet((byte) 0, this.meso));
            if (partner != null) {
                partner.getChr().getClient().getSession()
                        .writeAndFlush(MainPacketCreator.getTradeMesoSet((byte) 1, this.meso));
            }
        }
    }

    public final void addItem(final IItem item) {
        if (locked || partner == null) {
            return;
        }
        items.add(item);
        chr.getClient().getSession().writeAndFlush(MainPacketCreator.getTradeItemAdd((byte) 0, item));
        if (partner != null) {
            partner.getChr().getClient().getSession().writeAndFlush(MainPacketCreator.getTradeItemAdd((byte) 1, item));
        }
    }

    public final void chat(final String message) {
        if (ServerConstants.chatlimit >= 500) {
            ServerConstants.chatlimit = 0;
            AdminToolStart.ChatList.clear();
            AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
        }
        ServerConstants.chatlimit++;
        AdminToolStart.ChatList.addElement("[Exchange] : " + chr.getName() + " : " + message + "");
        AdminToolStart.Chat.setModel(AdminToolStart.ChatList);
        chr.getClient().send(MainPacketCreator.getPlayerShopChat(chr, message, true));
        if (partner != null) {
            partner.getChr().getClient().send(MainPacketCreator.getPlayerShopChat(chr, message, false));
        }
    }

    public final MapleUserTrade getPartner() {
        return partner;
    }

    public final void setPartner(final MapleUserTrade partner) {
        if (locked) {
            return;
        }
        this.partner = partner;
    }

    public final MapleCharacter getChr() {
        return chr;
    }

    private final boolean check() {
        if (chr.getMeso() + exchangeMeso < 0) {
            return false;
        }
        byte eq = 0, use = 0, setup = 0, etc = 0;
        for (final IItem item : exchangeItems) {
            switch (GameConstants.getInventoryType(item.getItemId())) {
                case EQUIP:
                    eq++;
                    break;
                case USE:
                    use++;
                    break;
                case SETUP:
                    setup++;
                    break;
                case ETC:
                    etc++;
                    break;
                case CASH: // Not allowed, probably hacking
                    return false;
            }
        }
        if (chr.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < eq
                || chr.getInventory(MapleInventoryType.USE).getNumFreeSlot() < use
                || chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < setup
                || chr.getInventory(MapleInventoryType.ETC).getNumFreeSlot() < etc) {
            return false;
        }
        return true;
    }

    public final static void completeTrade(final MapleCharacter c) {
        final MapleUserTrade local = c.getTrade();
        final MapleUserTrade partner = local.getPartner();

        if (partner == null || local.locked) {
            return;
        }
        local.locked = true; // Locking the trade
        partner.getChr().getClient().getSession().writeAndFlush(MainPacketCreator.getTradeConfirmation());

        partner.exchangeItems = local.items; // Copy this to partner's trade since it's alreadt accepted
        partner.exchangeMeso = local.meso; // Copy this to partner's trade since it's alreadt accepted

        if (partner.isLocked()) { // Both locked
            if (!local.check() || !partner.check()) { // Check for full inventories
                // NOTE : IF accepted = other party but inventory is full, the item is lost.
                partner.cancel();
                local.cancel();

                c.getClient().getSession()
                        .writeAndFlush(MainPacketCreator.serverNotice(5, "Not enough inventory space to complete trade."));
                partner.getChr().getClient().getSession()
                        .writeAndFlush(MainPacketCreator.serverNotice(5, "Not enough inventory space to complete trade."));
            } else {
                LoggerChatting.writeTradeLog(LoggerChatting.tradeLog, LoggerChatting.getTradeLogType(local, partner),
                        local.getChr().getName() + "&" + partner.getChr().getName());
                local.CompleteTrade();
                partner.CompleteTrade();
            }
            partner.getChr().setTrade(null);
            c.setTrade(null);
        }

    }

    public static final void cancelTrade(final MapleUserTrade Localtrade) {
        Localtrade.cancel();

        final MapleUserTrade partner = Localtrade.getPartner();
        if (partner != null) {
            partner.cancel();
            partner.chr.pih = (byte) 0;
            partner.getChr().setTrade(null);
        }
        Localtrade.chr.pih = (byte) 0;
        Localtrade.chr.setTrade(null);
    }

    public static final void startTrade(final MapleCharacter c, boolean isTrade, byte type) {
        if (c.getLevel() < 60) {
            c.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Trades available from level 60 and above."));
            return;
        }
        if (c.getTrade() == null) {
            c.setTrade(new MapleUserTrade((byte) 0, c));
            c.getTrade().type = type;
            c.getClient().getSession()
                    .writeAndFlush(MainPacketCreator.getTradeStart(c.getClient(), c.getTrade(), (byte) 0, type));
            c.isTrade = isTrade;
        } else {
            c.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Already trading."));
        }
    }

    public static final void inviteTrade(final MapleCharacter c1, final MapleCharacter c2, boolean isTrade, byte type) {
        if (c2.getLevel() < 60 || c1.getLevel() < 60) {
            c1.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Trades available from level 60 and above."));
            cancelTrade(c1.getTrade());
            return;
        }
        if (c2.getTrade() == null) {
            c2.setTrade(new MapleUserTrade((byte) 1, c2));
            c2.getTrade().setPartner(c1.getTrade());
            c1.getTrade().setPartner(c2.getTrade());
            c2.getClient().getSession().writeAndFlush(MainPacketCreator.getTradeInvite(c1, isTrade, type));
            c1.isTrade = isTrade;
            c2.isTrade = isTrade;
        } else {
            c1.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "I'm already trading with someone."));
            cancelTrade(c1.getTrade());
        }
    }

    public static final void visitTrade(final MapleCharacter c1, final MapleCharacter c2, boolean isTrade, byte type) {
        if (c1.getTrade() != null && c1.getTrade().getPartner() == c2.getTrade() && c2.getTrade() != null
                && c2.getTrade().getPartner() == c1.getTrade()) {
            // We don't need to check for map here as the user is found via
            // MapleMap.getCharacterById_InMap()
            c2.getClient().getSession().writeAndFlush(MainPacketCreator.getTradePartnerAdd(c1));
            c1.getClient().getSession()
                    .writeAndFlush(MainPacketCreator.getTradeStart(c1.getClient(), c1.getTrade(), (byte) 1, type));
        } else {
            c1.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "The opponent has already closed the trade."));
        }
    }

    public static final void declineTrade(final MapleCharacter c) {
        final MapleUserTrade trade = c.getTrade();
        if (trade != null) {
            if (trade.getPartner() != null) {
                MapleCharacter other = trade.getPartner().getChr();
                other.getTrade().cancel();
                other.setTrade(null);
                other.getClient().getSession()
                        .writeAndFlush(MainPacketCreator.serverNotice(5, c.getName() + " Has declined your exchange request."));
            }
            trade.cancel();
            c.setTrade(null);
        }
    }

    public void setRPS(byte rps) {
        this.rps = rps;
    }

    public byte getPRS() {
        return rps;
    }
}
