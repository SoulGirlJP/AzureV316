package server.Items;

import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IItem;
import client.ItemInventory.InventoryException;
import client.ItemInventory.Item;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.MapleAndroid;
import client.MapleClient;
import client.ItemInventory.PetsMounts.MaplePet;
import client.ItemInventory.StructPotentialItem;
import client.Skills.SkillFactory;
import client.Skills.SkillStatEffect;
import client.Stats.BuffStats;
import client.Stats.PlayerStats;
import connections.Packets.MainPacketCreator;
import constants.GameConstants;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import tools.RandomStream.Randomizer;

public class InventoryManipulator {

    public static void addRing(MapleCharacter chr, int itemId, int ringId, int sn, String partner) {
        CashItemInfo csi = CashItemFactory.getInstance().getItem(sn);
        if (csi == null) {
            return;
        }
    }

    public static boolean addbyItem(final MapleClient c, IItem item) {
        if (item.getItemId() == 5000312) {
            return false;
        }
        if (GameConstants.환생의불꽃아이템(item.getItemId())) {
            if (item.getOwner() == "" || item.getOwner() == null) {
                item = (IItem) InventoryHandler.환생의불꽃((Equip) item);
            }
        }
        final MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        final short newSlot = c.getPlayer().getInventory(type).addItem(item);
        if (newSlot == -1) {
            c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
            c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
            return false;
        }

        if (GameConstants.isArcaneSymbol(item.getItemId())) { //Arcane Symbol
            final Equip equip = (Equip) item;
            equip.setArc((short) 30);
            equip.setArcLevel((byte) 1);
            equip.setArcEXP(0);
            if (GameConstants.isStrJob(c.getPlayer().getJob())) {
                equip.setStr((short) 300);
            } else if (GameConstants.isIntJob(c.getPlayer().getJob())) {
                equip.setInt((short) 300);
            } else if (GameConstants.isDexJob(c.getPlayer().getJob())) {
                equip.setDex((short) 300);
            } else if (GameConstants.isLukJob(c.getPlayer().getJob())) {
                equip.setLuk((short) 300);
            } else if (GameConstants.isDemonAvenger(c.getPlayer().getJob())) {
                equip.setHp((short) 4200);
            } else if (GameConstants.isXenon(c.getPlayer().getJob())) {
                equip.setStr((short) 117);
                equip.setDex((short) 117);
                equip.setLuk((short) 117);
            }
        }
        c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(type, item));
        return true;
    }

    public static int addById(MapleClient c, int itemId, short quantity) {
        return addById(c, itemId, quantity, null, null, 0, null);
    }

    public static int addById(MapleClient c, int itemId, short quantity, String owner) {
        return addById(c, itemId, quantity, owner, null, 0, null);
    }

    public static int addById(MapleClient c, int itemId, short quantity, String owner, MaplePet pet) {
        return addById(c, itemId, quantity, owner, pet, 0, null);
    }

    public static boolean addById(MapleClient c, int itemId, short quantity, String owner, int petid) {
        return addById(c, itemId, quantity, owner, petid);
    }

    public static int addById(MapleClient c, int itemId, short quantity, String owner, MaplePet pet, long period) {
        return addById(c, itemId, quantity, owner, pet, period, false, null);
    }

    public static int addById(MapleClient c, int itemId, short quantity, String owner, MaplePet pet, long period, String log) {
        return addById(c, itemId, quantity, owner, pet, period, false, null);
    }

    public static int addById(MapleClient c, int itemId, short quantity, String owner, MaplePet pet, long period, boolean justice, String log) {
        if (itemId == 5000312) {
            return 0;
        }
        final ItemInformation ii = ItemInformation.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(itemId);
        if (!type.equals(MapleInventoryType.EQUIP)) {
            final short slotMax = ii.getSlotMax(c, itemId);
            final List<IItem> existing = c.getPlayer().getInventory(type).listById(itemId);
            if (!GameConstants.isThrowingStar(itemId) && !GameConstants.isBullet(itemId)) {
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0) {
                        if (i.hasNext()) {
                            Item eItem = (Item) i.next();

                            short oldQ = eItem.getQuantity();
                            if (oldQ < slotMax && (eItem.getOwner().equals(owner) || owner == null) && eItem.getExpiration() == -1) {
                                short newQ = (short) Math.min(oldQ + quantity, slotMax);
                                quantity -= (newQ - oldQ);
                                eItem.setQuantity(newQ);
                                c.getSession().writeAndFlush(MainPacketCreator.updateInventorySlot(type, eItem, false));
                            }
                        } else {
                            break;
                        }
                    }
                }
                short inventorypos;
                Item nItem;

                while (quantity > 0) {
                    short newQ = (short) Math.min(quantity, slotMax);
                    if (newQ != 0) {
                        quantity -= newQ;
                        nItem = new Item(itemId, (byte) 0, newQ, (byte) 0);
                        inventorypos = c.getPlayer().getInventory(type).addItem(nItem);
                        if (inventorypos == -1) {
                            c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                            c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                            return newQ;
                        }
                        if (owner != null) {
                            nItem.setOwner(owner);
                        }
                        if (period > 0 && period < 100000000) {
                            nItem.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                        } else if (period > 100000000) {
                            nItem.setExpiration(period);
                        } else {
                            nItem.setExpiration(-1);
                        }

                        if (log != null) {
                            nItem.setGMLog(log);
                        }
                        if (pet != null) {
                            nItem.setPet(pet);
                            nItem.setCash(true);
                            pet.setInventoryPosition(inventorypos);
                        }
                        c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(type, nItem));
                        if ((GameConstants.isThrowingStar(itemId) || GameConstants.isBullet(itemId)) && quantity == 0) {
                            break;
                        }
                    } else {
                        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                        return 0;
                    }
                }
            } else {
                final Item nItem = new Item(itemId, (byte) 0, quantity, (byte) 0);

                if (log != null) {
                    nItem.setGMLog(log);
                }
                final short newSlot = c.getPlayer().getInventory(type).addItem(nItem);

                if (newSlot == -1) {
                    c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                    c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                    return newSlot;
                }

                if (period > 0 && period < 100000000) {
                    nItem.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                } else if (period > 100000000) {
                    nItem.setExpiration(period);
                } else {
                    nItem.setExpiration(-1);
                }

                c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(type, nItem));
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            }
        } else {
            if (quantity == 1) {
                IItem nEquip = ii.getEquipById(itemId);
                if (GameConstants.환생의불꽃아이템(nEquip.getItemId())) {
                    if (nEquip.getOwner() == "" || nEquip.getOwner() == null) {
                        nEquip = (Item) InventoryHandler.환생의불꽃((Equip) nEquip);
                    }
                }
                if (GameConstants.isArcaneSymbol(nEquip.getItemId())) { //Arcane Symbol
                    final Equip equip = (Equip) nEquip;
                    equip.setArc((short) 30);
                    equip.setArcLevel((byte) 1);
                    equip.setArcEXP(0);
                    if (GameConstants.isStrJob(c.getPlayer().getJob())) {
                        equip.setStr((short) 300);
                    } else if (GameConstants.isIntJob(c.getPlayer().getJob())) {
                        equip.setInt((short) 300);
                    } else if (GameConstants.isDexJob(c.getPlayer().getJob())) {
                        equip.setDex((short) 300);
                    } else if (GameConstants.isLukJob(c.getPlayer().getJob())) {
                        equip.setLuk((short) 300);
                    } else if (GameConstants.isDemonAvenger(c.getPlayer().getJob())) {
                        equip.setHp((short) 4200);
                    } else if (GameConstants.isXenon(c.getPlayer().getJob())) {
                        equip.setStr((short) 117);
                        equip.setDex((short) 117);
                        equip.setLuk((short) 117);
                    }
                }
                if (owner != null) {
                    nEquip.setOwner(owner);
                }
                if (period > 0 && period < 100000000) {
                    nEquip.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                } else if (period > 100000000) {
                    nEquip.setExpiration(period);
                } else {
                    nEquip.setExpiration(-1);
                }
                if (justice) {
                    ((Equip) nEquip).setState((byte) 2);
                    ((Equip) nEquip).setLines((byte) Randomizer.rand(2, 3));
                }
                if (log != null) {
                    nEquip.setGMLog(log);
                }
                if (ii.getMaxLevelEquip(itemId) > 0) {
                    ((Equip) nEquip).setItemLevel((byte) 1);
                }
                short newSlot = c.getPlayer().getInventory(type).addItem(nEquip);
                if (newSlot == -1) {
                    c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                    c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                    return 0;
                }
                c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(type, nEquip));
            } else {
                throw new InventoryException("Trying to create equip with non-one quantity");
            }
        }
        return 0;
    }

    public static short addFromDrop(final MapleClient c, final IItem item, final boolean show) {
        return addFromDrop(c, item, show, false);
    }

    public static short addFromDrop(final MapleClient c, IItem item, final boolean show, final boolean enhance) {
        return addFromDrop(c, item, show, false, (long) 0);
    }

    public static short addFromDrop(final MapleClient c, IItem item, final boolean show, final boolean enhance,
            long period) {
        return addFromDrop(c, item, show, false, (long) 0, false);
    }

    public static short addFromDrop(final MapleClient c, IItem item, final boolean show, final boolean enhance,
            long period, boolean petloot) {
        final ItemInformation ii = ItemInformation.getInstance();
        short nSlot = -1;
        if (ii.isPickupRestricted(item.getItemId()) && c.getPlayer().haveItem(item.getItemId(), 1, true, false)) {
            if (!petloot) {
                c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
            }
            c.getSession().writeAndFlush(MainPacketCreator.showItemUnavailable());
            return -1;
        }
        short quantity = item.getQuantity();
        final MapleInventoryType type = GameConstants.getInventoryType(item.getItemId());
        if (!type.equals(MapleInventoryType.EQUIP)) {
            final short slotMax = ii.getSlotMax(c, item.getItemId());
            final List<IItem> existing = c.getPlayer().getInventory(type).listById(item.getItemId());
            if (!GameConstants.isThrowingStar(item.getItemId()) && !GameConstants.isBullet(item.getItemId())) {
                if (existing.size() > 0) {
                    Iterator<IItem> i = existing.iterator();
                    while (quantity > 0) {
                        if (i.hasNext()) {
                            Item eItem = (Item) i.next();
                            if (GameConstants.환생의불꽃아이템(eItem.getItemId())) {
                                if (eItem.getOwner() == "" || eItem.getOwner() == null) {
                                    eItem = InventoryHandler.환생의불꽃((Equip) eItem);
                                }
                            }
                            final short oldQ = eItem.getQuantity();
                            if (oldQ < slotMax && item.getOwner().equals(eItem.getOwner())
                                    && item.getExpiration() == eItem.getExpiration()) {
                                final short newQ = (short) Math.min(oldQ + quantity, slotMax);
                                quantity -= (newQ - oldQ);
                                eItem.setQuantity(newQ);
                                if (!c.isCS && !c.isAuction) {
                                    c.getSession().writeAndFlush(
                                            MainPacketCreator.updateInventorySlot(type, eItem, petloot ? false : true));
                                }
                                if (eItem.isCash()) {
                                    c.getPlayer().cashPacketTemp = eItem;
                                }
                                nSlot = eItem.getPosition();
                            }
                        } else {
                            break;
                        }
                    }
                }

                while (quantity > 0) {
                    final short newQ = (short) Math.min(quantity, slotMax);
                    quantity -= newQ;
                    final Item nItem = new Item(item.getItemId(), (byte) 0, newQ, (byte) 0);
                    nItem.setOwner(item.getOwner());
                    nItem.setCash(item.isCash());
                    nItem.setFlag(item.getFlag());
                    if (item.getGMLog() != null) {
                        nItem.setGMLog(item.getGMLog());
                    }
                    if (item.getPet() != null) {
                        nItem.setPet(item.getPet());
                    }
                    nItem.setUniqueId(item.getUniqueId());
                    if (period != 0) {
                        if (period > 0 && period < 100000000) {
                            nItem.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                        } else if (period > 100000000) {
                            nItem.setExpiration(period);
                        } else if (period == -1) {
                            nItem.setExpiration(-1);
                        }
                    }
                    nItem.setExpiration(item.getExpiration());

                    nSlot = c.getPlayer().getInventory(type).addItem(nItem);
                    if (nSlot == -1) {
                        if (!petloot) {
                            c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        }
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        item.setQuantity((short) (quantity + newQ));
                        return /* item.getQuantity() */ nSlot;
                    }
                    if (nItem.isCash()) {
                        c.getPlayer().cashPacketTemp = nItem;
                    }
                    if (!c.isCS && !c.isAuction) {
                        c.getSession()
                                .writeAndFlush(MainPacketCreator.addInventorySlot(type, nItem, petloot ? false : true));
                    }
                }
            } else {

                final Item nItem = new Item(item.getItemId(), (byte) 0, quantity, (byte) 0);
                nItem.setExpiration(item.getExpiration());
                nItem.setOwner(item.getOwner());
                nItem.setUniqueId(item.getUniqueId());
                nItem.setCash(item.isCash());
                nItem.setFlag(item.getFlag());
                if (item.getGMLog() != null) {
                    nItem.setGMLog(item.getGMLog());
                }

                nSlot = c.getPlayer().getInventory(type).addItem(nItem);
                if (nSlot == -1) {
                    if (!petloot) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                    }
                    c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                    return -1;
                }
                if (nItem.isCash()) {
                    c.getPlayer().cashPacketTemp = nItem;
                }
                if (!c.isCS && !c.isAuction) {
                    c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(type, nItem));
                }
                if (!petloot) {
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                }
            }
        } else {
            if (quantity == 1) {
                if (enhance) {
                    item = checkEnhanced(item, c.getPlayer());
                }
                nSlot = c.getPlayer().getInventory(type).addItem(item);

                if (nSlot == -1) {
                    if (!petloot) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                    }
                    c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                    return -1;
                }
                if (!c.isCS && !c.isAuction) {
                    c.getSession()
                            .writeAndFlush(MainPacketCreator.addInventorySlot(type, item, petloot ? false : true));
                }
            } else {
                throw new RuntimeException("Trying to create equip with non-one quantity");
            }
        }
        if (show) {
            c.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(item.getItemId(), item.getQuantity()));
        }
        return nSlot;
    }

    public static boolean checkSpace(final MapleClient c, final int itemid, int quantity, final String owner) {
        final ItemInformation ii = ItemInformation.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(itemid);

        if (!type.equals(MapleInventoryType.EQUIP)) {
            final short slotMax = ii.getSlotMax(c, itemid);
            final List<IItem> existing = c.getPlayer().getInventory(type).listById(itemid);
            if (!GameConstants.isThrowingStar(itemid) && !GameConstants.isBullet(itemid)) {
                if (existing.size() > 0) {
                    for (IItem eItem : existing) {
                        final short oldQ = eItem.getQuantity();
                        if (oldQ < slotMax && owner.equals(eItem.getOwner())) {
                            final short newQ = (short) Math.min(oldQ + quantity, slotMax);
                            quantity -= (newQ - oldQ);
                        }
                        if (quantity <= 0) {
                            break;
                        }
                    }
                }
            }

            final int numSlotsNeeded;
            if (slotMax > 0) {
                numSlotsNeeded = (int) (Math.ceil(((double) quantity) / slotMax));
            } else {
                numSlotsNeeded = 1;
            }
            return !c.getPlayer().getInventory(type).isFull(numSlotsNeeded - 1);
        } else {
            return !c.getPlayer().getInventory(type).isFull();
        }
    }

    public static void removeFromSlot(final MapleClient c, final MapleInventoryType type, final short slot,
            final short quantity, final boolean fromDrop) {
        removeFromSlot(c, type, slot, quantity, fromDrop, false);
    }

    public static void removeFromSlot(final MapleClient c, final MapleInventoryType type, final short slot,
            short quantity, final boolean fromDrop, final boolean consume) {
        final IItem item = c.getPlayer().getInventory(type).getItem(slot);
        final boolean allowZero = consume
                && (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId()));
        c.getPlayer().getInventory(type).removeItem(slot, quantity, allowZero);
        if (item.getQuantity() == 0 && !allowZero) {
            c.getSession().writeAndFlush(MainPacketCreator.clearInventoryItem(type, item.getPosition(), fromDrop));
        } else {
            c.getSession().writeAndFlush(MainPacketCreator.updateInventorySlot(type, (Item) item, fromDrop));
        }
    }

    public static boolean removeFromSlot_Lock(final MapleClient c, final MapleInventoryType type, final short slot,
            short quantity, final boolean fromDrop, final boolean consume) {
        if (c.getPlayer() == null || c.getPlayer().getInventory(type) == null) {
            return false;
        }
        final Item item = (Item) c.getPlayer().getInventory(type).getItem((byte) slot);
        if (item != null) {
            if (ItemFlag.LOCK.check(item.getFlag())) {
                return false;
            }
            removeFromSlot(c, type, slot, quantity, fromDrop, consume);
            return true;
        }
        return false;
    }

    public static boolean removeById_Lock(final MapleClient c, final MapleInventoryType type, final int itemId) {
        for (IItem item : c.getPlayer().getInventory(type).listById(itemId)) {
            if (removeFromSlot_Lock(c, type, item.getPosition(), (short) 1, false, false)) {
                return true;
            }
        }
        return false;
    }

    public static void removeById(final MapleClient c, final MapleInventoryType type, final int itemId,
            final int quantity, final boolean fromDrop, final boolean consume) {
        int remremove = quantity;
        for (IItem item : c.getPlayer().getInventory(type).listById(itemId)) {
            if (remremove < item.getQuantity()) {
                removeFromSlot(c, type, item.getPosition(), (short) remremove, fromDrop, consume);
                remremove = 0;
                break;
            } else {
                remremove -= item.getQuantity();
                removeFromSlot(c, type, item.getPosition(), item.getQuantity(), fromDrop, consume);
            }
        }
        if (remremove > 0) {
            throw new InventoryException("Not enough cheese available ( ItemID:" + itemId + ", Remove Amount:"
                    + (quantity - remremove) + "| Current Amount:" + quantity + ")");
        }
    }

    public static void move(final MapleClient c, final MapleInventoryType type, final short src, final short dst) {
        if (src < 0 || dst < 0) {
            return;
        }
        final ItemInformation ii = ItemInformation.getInstance();
        final IItem source = c.getPlayer().getInventory(type).getItem(src);
        final IItem initialTarget = c.getPlayer().getInventory(type).getItem(dst);
        if (source == null) {
            return;
        }
        boolean bag = false, switchSrcDst = false, bothBag = false;
        short eqIndicator = -1;
        if (dst > c.getPlayer().getInventory(type).getSlotLimit()) {
            if (type == MapleInventoryType.ETC && dst > 100 && dst % 100 != 0) {
                final int uniqueid = c.getPlayer().getExtendedSlot((dst / 100) - 1);
                final int eSlot = c.getPlayer().getInventory(MapleInventoryType.ETC).findByUniqueId(uniqueid)
                        .getItemId();
                if (eSlot > 0) {
                    final SkillStatEffect ee = ii.getItemEffect(eSlot);
                    if (dst % 100 > ee.getSkillStats().getStats("slotCount")
                            || ee.getSkillStats().getStats("type") != ii.getBagType(source.getItemId())
                            || ee.getSkillStats().getStats("type") <= 0) {
                        c.getPlayer().dropMessage(1, "It is an item that can not be put in the bag. " + ee.getSkillStats().getStats("type")
                                + " != " + ii.getBagType(source.getItemId()));
                        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                        return;
                    } else {
                        eqIndicator = 0;
                        bag = true;
                    }
                } else {
                    c.getPlayer().dropMessage(1, "I cannot put it in the bag.");
                    c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                    return;
                }
            } else {
                c.getPlayer().dropMessage(1, "I cannot put it in the bag.");
                c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
                return;
            }
        }
        if (src > c.getPlayer().getInventory(type).getSlotLimit() && type == MapleInventoryType.ETC && src > 100
                && src % 100 != 0) {

            if (!bag) {
                switchSrcDst = true;
                eqIndicator = 0;
                bag = true;
            } else {
                bothBag = true;
            }
        }
        short olddstQ = -1;
        if (initialTarget != null) {
            olddstQ = initialTarget.getQuantity();
        }
        final short oldsrcQ = source.getQuantity();
        final short slotMax = ii.getSlotMax(c, source.getItemId());
        c.getPlayer().getInventory(type).move(src, dst, slotMax);

        if (!type.equals(MapleInventoryType.EQUIP) && initialTarget != null
                && initialTarget.getItemId() == source.getItemId()
                && initialTarget.getExpiration() == source.getExpiration()
                && !GameConstants.isThrowingStar(source.getItemId()) && !GameConstants.isBullet(source.getItemId())
                && initialTarget.getOwner().equals(source.getOwner())) { // Fixed item evaporation bug
            if ((olddstQ + oldsrcQ) > slotMax) {
                c.getSession().writeAndFlush(MainPacketCreator.moveAndMergeWithRestInventoryItem(type, src, dst,
                        (short) ((olddstQ + oldsrcQ) - slotMax), slotMax, bag, switchSrcDst, bothBag));
            } else {
                c.getSession()
                        .writeAndFlush(MainPacketCreator.moveAndMergeInventoryItem(type, src, dst,
                                ((Item) c.getPlayer().getInventory(type).getItem(dst)).getQuantity(), bag, switchSrcDst,
                                bothBag));
            }
        } else {
            c.getSession().writeAndFlush(MainPacketCreator.moveInventoryItem(type, switchSrcDst ? dst : src,
                    switchSrcDst ? src : dst, eqIndicator, bag, bothBag, c.getPlayer().modifiyInvCount++));
        }
    }

    public static void equip(final MapleClient c, final short src, short dst) {
        final ItemInformation ii = ItemInformation.getInstance();
        final MapleCharacter chr = c.getPlayer();
        Equip source = (Equip) chr.getInventory(src < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP)
                .getItem(src);
        Equip target = (Equip) chr.getInventory(MapleInventoryType.EQUIPPED).getItem(dst);

        if (source == null) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }

        final Map<String, Integer> stats = ii.getEquipStats(source.getItemId());

        if (GameConstants.isArcaneSymbol(source.getItemId())) {
            Equip symbol1 = (Equip) chr.getInventory(MapleInventoryType.EQUIP).getItem((short) -1600);
            Equip symbol2 = (Equip) chr.getInventory(MapleInventoryType.EQUIP).getItem((short) -1601);
            Equip symbol3 = (Equip) chr.getInventory(MapleInventoryType.EQUIP).getItem((short) -1602);
            if (symbol1 != null) {
                if (symbol1.getItemId() == source.getItemId()) {
                    chr.ea();
                    return;
                }
            } else if (symbol2 != null) {
                if (symbol2.getItemId() == source.getItemId()) {
                    chr.ea();
                    return;
                }
            } else if (symbol3 != null) {
                if (symbol3.getItemId() == source.getItemId()) {
                    chr.ea();
                    return;
                }
            }
        }

        if (GameConstants.isWeapon(source.getItemId()) && (dst != -10 && dst != -11)
                && !GameConstants.isInvisibleKatara(source.getItemId())) {
            return;
        }
        if (GameConstants.isInvisibleKatara(source.getItemId())) {
            dst = (byte) -110; // shield slot
        } else if (GameConstants.isKatara(source.getItemId())) {
            dst = (byte) -10; // shield slot
        }
        if (GameConstants.isEvanDragonItem(source.getItemId()) && (chr.getJob() < 2200 || chr.getJob() > 2218)) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        } else if ((dst <= -1200 || (dst >= -999 && dst < -99)) && !stats.containsKey("cash")) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        } else if ((dst <= -1300 && dst > -1400) && c.getPlayer().getAndroid() == null) {
            chr.ea();
            return;
        }

        int topPos = -5;
        int bottomPos = -6;
        int weaponPos = -11;
        int shieldPos = -10;
        switch (dst) {
            case -105: // Cash item;;
                topPos = -105;
                bottomPos = -106;
            case -5:
            case -1203: { // Top
                if (dst == -1203) {
                    topPos = -1203;
                    bottomPos = -1204;
                }
                final IItem equippedTop = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) topPos);
                final IItem bottom = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) bottomPos);
                if (bottom != null && GameConstants.isOverall(source.getItemId())) { // When I wear tops with pants on
                    if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (byte) bottomPos, chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                }
                break;
            }
            case -106: // Cash item;;
                topPos = -105;
                bottomPos = -106;
            case -6:
            case -1204: { // Bottom
                if (dst == -1204) {
                    topPos = -1203;
                    bottomPos = -1204;
                }
                final IItem top = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) topPos);
                final IItem bottom = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) bottomPos);
                if (top != null && GameConstants.isOverall(top.getItemId())) { // When I wear bottoms in a suit
                    if (chr.getInventory(MapleInventoryType.EQUIP)
                            .isFull(bottom != null && GameConstants.isOverall(top.getItemId()) ? 1 : 0)) { // If the top is worn, the free space 두칸은 필요.
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (byte) topPos, chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                }
                break;
            }
            case -10: { // Shield
                IItem shield = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) shieldPos);
                if (shield != null && GameConstants.isTwoHanded(source.getItemId())) {
                    if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (byte) -10, chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                }
                break;
            }
            case -11: { // Weapon
                IItem weapon = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) weaponPos);
                if (GameConstants.isKatara(source.getItemId())) {
                    if ((chr.getJob() != 900 && (chr.getJob() < 430 || chr.getJob() > 434)) || weapon == null
                            || !GameConstants.isDagger(weapon.getItemId())) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        return;
                    }
                } else if (weapon != null && GameConstants.isTwoHanded(weapon.getItemId())) {
                    if (chr.getInventory(MapleInventoryType.EQUIP).isFull()) {
                        c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
                        c.getSession().writeAndFlush(MainPacketCreator.getShowInventoryFull());
                        return;
                    }
                    unequip(c, (byte) -11, chr.getInventory(MapleInventoryType.EQUIP).getNextFreeSlot());
                }
                break;
            }
        }
        source = (Equip) chr.getInventory(src < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP)
                .getItem(src); // Equip
        target = (Equip) chr.getInventory(MapleInventoryType.EQUIPPED).getItem(dst); // Currently equipping

        chr.getInventory(src < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP).removeSlot(src);

        if (target != null) {
            chr.getInventory(MapleInventoryType.EQUIPPED).removeSlot(dst);
        }
        source.setPosition(dst);
        chr.getInventory(MapleInventoryType.EQUIPPED).addFromDB(source);
        if (target != null) {
            target.setPosition(src);
            chr.getInventory(MapleInventoryType.EQUIP).addFromDB(target);
        }
        if (GameConstants.isWeapon(source.getItemId())) {
            c.getPlayer().cancelEffectFromBuffStat(BuffStats.Booster, -1);
            c.getPlayer().cancelEffectFromBuffStat(BuffStats.NoBulletConsume, -1);
            c.getPlayer().cancelEffectFromBuffStat(BuffStats.SoulArrow, -1);
            c.getPlayer().cancelEffectFromBuffStat(BuffStats.WeaponCharge, -1);
        }
        if (source.getItemId() >= 1113098 && source.getItemId() <= 1113122) {
            c.getPlayer().changeSkillLevel(source.getItemId() + 78888357, (byte) 4, (byte) 4);
        }
        c.getSession().writeAndFlush(MainPacketCreator.moveInventoryItem(MapleInventoryType.EQUIP, src, dst, (byte) 2, false, false, c.getPlayer().modifiyInvCount++));
        if (source.getState() > 0) {
            int[] potentials = {source.getPotential1(), source.getPotential2(), source.getPotential3(),
                source.getPotential4(), source.getPotential5()};

            for (int i : potentials) {
                if (i == 0) {
                    continue;
                }
                if (ii.getReqLevel(source.getItemId()) < 10) {
                    continue;
                }
                StructPotentialItem pot = ii.getPotentialInfo(i).get((ii.getReqLevel(source.getItemId()) / 10) - 1);
                if (pot.skillID > 0
                        && chr.getSkillLevel(PlayerStats.getSkillByJob(pot.skillID, c.getPlayer().getJob())) <= 0) {
                    chr.changeSkillLevel(SkillFactory.getSkill(PlayerStats.getSkillByJob(pot.skillID, chr.getJob())),
                            (byte) 1, (byte) 1);
                }
            }
        }
        chr.equipChanged();
        if (source.getItemId() == 1122017 || source.getItemId() == 1122155) {
            c.getPlayer().equipPendantOfSpirit();
        }
        if (stats.get("equipTradeBlock") == 1 || source.getItemId() / 10000 == 167 || source.getFire() >= 0) { // Block trade when equipped.
            short flag = source.getFlag();
            if (!ItemFlag.UNTRADEABLE.check(flag)) {
                flag |= ItemFlag.UNTRADEABLE.getValue();
                source.setFlag(flag);
                c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, source));
            }
        }
        if (source.getItemId() / 10000 == 167) { // Heart
            final Item android = (Item) chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -27);
            chr.removeAndroid();
            if (android != null) {
                short flag = android.getFlag();
                if (ItemFlag.ANDROID_ACTIVATED.check(flag)) {
                    if (android.getAndroid() != null) {
                        chr.setAndroid(android.getAndroid());
                    } else {
                        chr.dropMessage(1, "An error occurred!");
                        System.err.println("[Error] " + chr.getName() + " of " + android.getUniqueId()
                                + " NULL pointer in Android with unique id.");
                        return;
                    }
                } else {
                    MapleAndroid newAndroid = MapleAndroid.createAndroid(android.getItemId());
                    android.setUniqueId(newAndroid.getUniqueId());
                    android.setAndroid(newAndroid);
                    flag |= ItemFlag.ANDROID_ACTIVATED.getValue();
                    flag |= ItemFlag.UNTRADEABLE.getValue();
                    android.setFlag(flag);
                    chr.setAndroid(newAndroid);
                    c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, source));
                }
            }
        }
        if (source.getItemId() == 1142145) {
            c.getPlayer().setPC(true);
            c.getPlayer().dropMessage(5, "Earn 50% bonus experience when hunting monsters for wearing Premium PC Room Medal.");
        }

        if (source.getItemId() / 10000 == 166) { // Android
            final IItem heart = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -28);
            chr.removeAndroid();
            if (heart != null) {
                short flag = source.getFlag();
                if (ItemFlag.ANDROID_ACTIVATED.check(flag)) {
                    if (source.getAndroid() != null) {
                        chr.setAndroid(source.getAndroid());
                    } else {
                        chr.dropMessage(1, "An error occurred!");
                        System.err.println("[Error] " + chr.getName() + " of " + source.getUniqueId()
                                + " NULL pointer in Android with unique id.");
                        return;
                    }
                } else {
                    MapleAndroid newAndroid = MapleAndroid.createAndroid(source.getItemId());
                    source.setUniqueId(newAndroid.getUniqueId());
                    source.setAndroid(newAndroid);
                    flag |= ItemFlag.ANDROID_ACTIVATED.getValue();
                    flag |= ItemFlag.UNTRADEABLE.getValue();
                    source.setFlag(flag);
                    chr.setAndroid(newAndroid);
                    c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, source));
                }
            }
        } else if (dst <= -1200 && chr.getAndroid() != null) {
            chr.updateAndroid();
        }

        if (stats.get("charmEXP") > 0 || stats.get("willEXP") > 0 || stats.get("charismaEXP") > 0
                || ItemInformation.getInstance().getCashCharmExp((Equip) source) > 0) {
            short flag = source.getFlag();
            if (!ItemFlag.CHARM_EQUIPPED.check(flag)) {
                flag |= ItemFlag.CHARM_EQUIPPED.getValue();
                source.setFlag(flag);
                if (stats.get("charmEXP") > 0) {
                    c.getPlayer().addCharm(stats.get("charmEXP"));
                }
                if (stats.get("willEXP") > 0) {
                    c.getPlayer().addWillPower(stats.get("willEXP"));
                }
                if (stats.get("charismaEXP") > 0) {
                    c.getPlayer().addCharisma(stats.get("charismaEXP"));
                }
                if (ItemInformation.getInstance().getCashCharmExp((Equip) source) > 0) {
                    c.getPlayer().addCharm(ItemInformation.getInstance().getCashCharmExp((Equip) source));
                }
                c.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, source));
            }
        }
        if (target != null && chr.isSoulWeapon(target)) {
            chr.unequipSoulWeapon(target);
        }
        if (source != null && chr.isSoulWeapon(source)) {
            chr.equipSoulWeapon(source);
        }
    }

    public static void unequip(final MapleClient c, final short src, final short dst) {
        Equip source = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(src);
        Equip target = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(dst);

        if (dst < 0 || source == null) {
            return;
        }
        if (target != null && src <= 0) { // do not allow switching with equip
            c.getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
            return;
        }
        c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).removeSlot(src);
        if (target != null) {
            c.getPlayer().getInventory(MapleInventoryType.EQUIP).removeSlot(dst);
        }
        source.setPosition(dst);
        c.getPlayer().getInventory(MapleInventoryType.EQUIP).addFromDB(source);
        if (target != null) {
            target.setPosition(src);
            c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).addFromDB(target);
        }
        if (source.getState() > 0) {
            ItemInformation ii = ItemInformation.getInstance();
            int[] potentials = {source.getPotential1(), source.getPotential2(), source.getPotential3(),
                source.getPotential4(), source.getPotential5()};
            for (int i : potentials) {
                if (i == 0) {
                    continue;
                }
                if (ii.getReqLevel(source.getItemId()) < 10) {
                    continue;
                }
                StructPotentialItem pot = ii.getPotentialInfo(i).get((ii.getReqLevel(source.getItemId()) / 10) - 1);
                if (pot.skillID > 0 && c.getPlayer()
                        .getSkillLevel(PlayerStats.getSkillByJob(pot.skillID, c.getPlayer().getJob())) > 0) {
                    c.getPlayer().changeSkillLevel(
                            SkillFactory.getSkill(PlayerStats.getSkillByJob(pot.skillID, c.getPlayer().getJob())),
                            (byte) 0, (byte) 0);
                }
            }
        }
        if (c.getPlayer().getBuffedValue(BuffStats.Booster) != null && GameConstants.isWeapon(source.getItemId())) {
            c.getPlayer().cancelBuffStats(-1, BuffStats.Booster);
        }
        if (source.getItemId() == 1112585) {
            c.getPlayer().dispelItem(2022746);
        } else if (source.getItemId() == 1112586) {
            c.getPlayer().dispelItem(2022747);
        } else if (source.getItemId() == 1112594) {
            c.getPlayer().dispelItem(2022764);
        } else if (source.getItemId() == 1112663) {
            c.getPlayer().dispelItem(2022823);
        } else if (source.getItemId() == 1112735) {
            c.getPlayer().dispelItem(2022823);
        } else if (source.getItemId() / 10000 == 166) {
            c.getPlayer().removeAndroid();
        } else if (source.getItemId() / 10000 == 167) {
            c.getPlayer().removeAndroid();
        } else if (src <= -1200 && c.getPlayer().getAndroid() != null) {
            c.getPlayer().updateAndroid();
        } else if (source.getItemId() == 1122017 || source.getItemId() == 1122155) {
            c.getPlayer().unequipPendantOfSpirit();
        } else if (source.getItemId() == 1142145) {
            c.getPlayer().setPC(false);
        }
        if (source.getItemId() >= 1113098 && source.getItemId() <= 1113122) {
            c.getPlayer().changeSkillLevel(source.getItemId() + 78888357, (byte) -1, (byte) 4);
        }
        c.getSession().writeAndFlush(MainPacketCreator.moveInventoryItem(MapleInventoryType.EQUIP, src, dst, (byte) 1,
                false, false, c.getPlayer().modifiyInvCount++));
        c.getPlayer().equipChanged();
        if (source != null && c.getPlayer().isSoulWeapon(source)) {
            c.getPlayer().unequipSoulWeapon(source);
        }
    }

    public static void drop(final MapleClient c, MapleInventoryType type, final short src, final short quantity) {
        final ItemInformation ii = ItemInformation.getInstance();
        if (src < 0) {
            type = MapleInventoryType.EQUIPPED;
        }
        final IItem source = c.getPlayer().getInventory(type).getItem(src);
        if (quantity < 0 || source == null || GameConstants.isPet(source.getItemId()) || quantity == 0
                && !GameConstants.isThrowingStar(source.getItemId()) && !GameConstants.isBullet(source.getItemId())) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final short flag = source.getFlag();
        if (ItemFlag.LOCK.check(flag)) { // hack
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        final Point dropPos = new Point(c.getPlayer().getPosition());

        if (quantity < source.getQuantity() && !GameConstants.isThrowingStar(source.getItemId())
                && !GameConstants.isBullet(source.getItemId())) {
            final IItem target = source.copy();
            target.setQuantity(quantity);
            source.setQuantity((short) (source.getQuantity() - quantity));
            c.getSession().writeAndFlush(MainPacketCreator.dropInventoryItemUpdate(type, source));

            if (ii.isDropRestricted(target.getItemId())) {
                if (ItemFlag.KARMA_EQ.check(flag)) {
                    target.setFlag((short) (flag - ItemFlag.KARMA_EQ.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
                } else if (ItemFlag.KARMA_USE.check(flag) && !GameConstants.isEquip(source.getItemId())) {
                    target.setFlag((short) (flag - ItemFlag.KARMA_USE.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
                } else {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos);
                }
            } else {
                if (ItemFlag.UNTRADEABLE.check(flag)) {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos);
                } else {
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), target, dropPos, true, true);
                }
            }
        } else {
            c.getPlayer().getInventory(type).removeSlot(src);
            c.getSession().writeAndFlush(
                    MainPacketCreator.dropInventoryItem((src < 0 ? MapleInventoryType.EQUIP : type), src));
            if (src < 0) {
                c.getPlayer().equipChanged();
            }
            if (ii.isDropRestricted(source.getItemId())) {
                if (ItemFlag.KARMA_EQ.check(flag)) {
                    source.setFlag((short) (flag - ItemFlag.KARMA_EQ.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
                } else if (ItemFlag.KARMA_USE.check(flag) && !GameConstants.isEquip(source.getItemId())) {
                    source.setFlag((short) (flag - ItemFlag.KARMA_USE.getValue()));
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
                } else {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos);
                    if (source.getItemId() / 10000 == 433) {
                        c.getPlayer().getExtendedSlots().remove(Integer.valueOf(source.getUniqueId()));
                    }
                }
            } else {
                if (ItemFlag.UNTRADEABLE.check(flag)) {
                    c.getPlayer().getMap().disappearingItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos);
                    if (source.getItemId() / 10000 == 433) {
                        c.getPlayer().getExtendedSlots().remove(Integer.valueOf(source.getUniqueId()));
                    }
                } else {
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), source, dropPos, true, true);
                }
            }
        }
        c.getPlayer().ea();
    }

    private final static IItem checkEnhanced(final IItem before, final MapleCharacter chr) {
        if (before instanceof Equip) {
            final Equip eq = (Equip) before;
            if (eq.getState() == 0 && (eq.getUpgradeSlots() >= 1 || eq.getLevel() >= 1) && Randomizer.nextInt(100) > 92) { // 20% chance of pot?
                eq.setState((byte) 1);
                // chr.dropMessage(5, "You have obtained an item with hidden Potential.");
            }
        }
        return before;
    }

    public static IItem BuyMonsterLifeWeapon(int boxid) {
        return InventoryHandler.환생의불꽃((Equip) ItemInformation.getInstance().getEquipById(boxid));
    }
}
