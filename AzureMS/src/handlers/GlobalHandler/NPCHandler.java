package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import client.MapleClient;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import constants.GameConstants;
import constants.QuestConstants;
import constants.ServerConstants;
import scripting.MapScriptMethods;
import scripting.NPC.NPCConversationManager;
import scripting.NPC.NPCScriptManager;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.LifeEntity.NpcEntity.MapleNPC;
import server.Quests.MapleQuest;
import server.Shops.MapleShop;
import server.Systems.MapleStorage;
import tools.FileoutputUtil;

public class NPCHandler {

    public static void NPCAnimation(ReadingMaple slea, MapleClient c) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.NPC_ACTION.getValue());
        int length = (int) slea.available();
        if (length == 10) {
            mplew.writeInt(slea.readInt());
            mplew.writeShort(slea.readShort());
            mplew.writeInt(slea.readInt());
        } else if (length > 10) {
            mplew.write(slea.read(length - 9));
        } else {
            return;
        }
        c.getSession().writeAndFlush(mplew.getPacket());
    }

    public static final void NPCTalk(final ReadingMaple slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || chr.getMap() == null) {
            return;
        }
        final MapleNPC npc = chr.getMap().getNPCByOid(slea.readInt());
        if (npc == null) {
            return;
        }
        if (chr.getConversation() != 0) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
        }
        if (chr.hasBlockedInventory()) {
            return;
        }
        if (NPCScriptManager.getInstance().hasScript(c, npc.getId(), null)) {
            NPCScriptManager.getInstance().start(c, npc.getId(), null);
        } else if (npc.hasShop()) {
            chr.setConversation(1);
            npc.sendShop(c);
        } else {
            NPCScriptManager.getInstance().start(c, npc.getId(), null);
        }
    }

    @SuppressWarnings("empty-statement")
    public static final void Storage(ReadingMaple slea, final MapleClient c, MapleCharacter chr) {
        byte mode = slea.readByte();
        if (chr == null) {
            return;
        }
        MapleStorage storage = chr.getStorage();

        switch (mode) {
            case 3: {
                if (c.CheckSecondPassword(slea.readMapleAsciiString())) {
                    c.getPlayer().getStorage().sendStorage(c);
                } else {
                    c.sendPacket(MainPacketCreator.getSorageSpFaild());
                }
                break;
            }
            case 4: {
                byte type = slea.readByte();
                byte slot = storage.getSlot(MapleInventoryType.getByType(type), slea.readByte());
                IItem item = storage.takeOut(slot);
                ItemInformation ii = ItemInformation.getInstance();
                if (item != null) {
                    if (!InventoryManipulator.checkSpace(c, item.getItemId(), item.getQuantity(), item.getOwner())) {
                        storage.store(item);
                        chr.dropMessage(1, "Your inventory is full");
                    } else {
                        if (ii.isDropRestricted(item.getItemId())) {
                            short flag = item.getFlag();
                            if (ItemFlag.KARMA_EQ.check(flag)) {
                                item.setFlag((short) (flag - ItemFlag.KARMA_EQ.getValue()));
                            } else if (ItemFlag.KARMA_USE.check(flag)) {
                                item.setFlag((short) (flag - ItemFlag.KARMA_USE.getValue()));
                            } else if (ItemFlag.KARMA_ACC.check(flag)) {
                                item.setFlag((short) (flag - ItemFlag.KARMA_ACC.getValue()));
                            } else if (ItemFlag.KARMA_ACC_USE.check(flag)) {
                                item.setFlag((short) (flag - ItemFlag.KARMA_ACC_USE.getValue()));
                            } else {
                                c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                                return;
                            }
                        }
                        InventoryManipulator.addFromDrop(c, item, false);
                        storage.sendTakenOut(c, GameConstants.getInventoryType(item.getItemId()));
                    }
                } else {
                    c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                }
                break;
            }
            case 5: {
                final byte slot = (byte) slea.readShort();
                int itemId = slea.readInt();
                final MapleInventoryType type = GameConstants.getInventoryType(itemId);
                short quantity = slea.readShort();
                ItemInformation ii = ItemInformation.getInstance();
                if (quantity < 1) {
                    return;
                }
                if (storage.isFull()) {
                    c.getSession().writeAndFlush(MainPacketCreator.getStorageFull());
                    return;
                }
                if (chr.getInventory(type).getItem((short) slot) == null) {
                    c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                    return;
                }
                if (chr.getMeso() < 100L) {
                    chr.dropMessage(1, "You don't have enough mesos to store the item");
                    c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                    return;
                } else {
                    IItem item = chr.getInventory(type).getItem((short) slot).copy();

                    if (GameConstants.isPet(item.getItemId())) {
                        c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                        return;
                    }
                    if ((ii.isPickupRestricted(item.getItemId())) && (storage.findById(item.getItemId()) != null)) {
                        c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
                        return;
                    }
                    if ((item.getItemId() == itemId) && ((item.getQuantity() >= quantity) || (GameConstants.isThrowingStar(itemId)) || (GameConstants.isBullet(itemId)))) {
                        if ((GameConstants.isThrowingStar(itemId)) || (GameConstants.isBullet(itemId))) {
                            quantity = item.getQuantity();
                        }
                        InventoryManipulator.removeFromSlot(c, type, (short) slot, quantity, false);
                        chr.gainMeso(-100L, false, false);
                        item.setQuantity(quantity);
                        storage.store(item);
                    } else {
                        return;
                    }
                }
                storage.sendStored(c, GameConstants.getInventoryType(itemId));
                break;
            }
            case 6:
                storage.arrange();
                storage.update(c);
                break;
            case 7: {
                long meso = slea.readInt();
                long storageMesos = storage.getMeso();
                long playerMesos = chr.getMeso();

                if (((meso > 0L) && (storageMesos >= meso)) || ((meso < 0L) && (playerMesos >= -meso))) {
                    if ((meso < 0L) && (storageMesos - meso < 0L)) {
                        meso = -(9999999999L - storageMesos);
                        if (-meso <= playerMesos) ;
                    } else if ((meso > 0L) && (playerMesos + meso < 0L)) {
                        meso = 9999999999L - playerMesos;
                        if (meso > storageMesos) {
                            return;
                        }
                    }
                    storage.setMeso(storageMesos - meso);
                    chr.gainMeso(meso, false, false);
                } else {
                    return;
                }
                storage.sendMeso(c);
                break;
            }
            case 8:
                storage.close();
                chr.setConversation(0);
                break;
            default:
                System.out.println("Unhandled Storage mode : " + mode);
        }
    }

    public static void NPCMoreTalk(final ReadingMaple slea, final MapleClient c) {
        final byte lastMsg = slea.readByte();
        if (lastMsg == 10 && slea.available() >= 4) {
            slea.readShort();
        }
        final byte action = slea.readByte();

        if (((lastMsg == 0x12 && c.getPlayer().getConversation() >= 0) || (lastMsg == 0x12 && c.getPlayer().getConversation() == -1)) && action == 1) {
            byte lastbyte = slea.readByte();
            if (lastbyte == 0) {
                c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
            } else {
                MapScriptMethods.startDirectionInfo(c.getPlayer(), lastMsg == 0x13);
                c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
            }
            return;
        }
        final NPCConversationManager cm = NPCScriptManager.getInstance().getCM(c);
        if (cm == null || c.getPlayer().getConversation() == 0 || cm.getLastMsg() != lastMsg) {
            return;
        }
        cm.setLastMsg((byte) -1);
        if (lastMsg == 1) {
            NPCScriptManager.getInstance().action(c, action, lastMsg, -1);
        } else if (lastMsg == 4) {
            if (action != 0) {
                if (slea.available() >= 2) {
                    cm.setGetText(slea.readMapleAsciiString());
                }
                if (cm.getType() == 0) {
                    NPCScriptManager.getInstance().startQuest(c, action, lastMsg, -1);
                } else if (cm.getType() == 1) {
                    NPCScriptManager.getInstance().endQuest(c, action, lastMsg, -1);
                } else {
                    NPCScriptManager.getInstance().action(c, action, lastMsg, -1);
                }
            } else {
                cm.dispose();
            }
        } else if (lastMsg == 0x17) {
            NPCScriptManager.getInstance().action(c, (byte) 1, lastMsg, action);
        } else {
            int selection = -1;
            if (lastMsg == 0x2C && action == 1) {
                slea.skip(6);
                int nMixBaseHairColor = slea.readInt() & 0xFF;
                int nMixAddHairColor = slea.readInt() & 0xFF;
                int nMixHairBaseProb = slea.readInt() & 0xFF;
                c.getPlayer().setMixColor(nMixBaseHairColor, nMixAddHairColor, nMixHairBaseProb);
                c.getPlayer().getMap().broadcastMessage(MainPacketCreator.updateCharLook(c.getPlayer(), false));
                NPCScriptManager.getInstance().dispose(c);
                c.getPlayer().ea();
                return;
            }
            if (slea.available() >= 4) {
                selection = slea.readInt();
            } else if (slea.available() > 0) {
                selection = slea.readByte();
            }
            if (selection < -1) {
                selection = selection & 0xFF;
            }
            if (lastMsg == 4 && selection == -1) {
                cm.dispose();
                return;
            }
            if (selection >= 1000000 && selection <= 5999999) {
                if (!c.getPlayer().lastNpcTalk.contains("#L" + selection + "#")) {
                    FileoutputUtil.logToFile("ScriptError.txt", c.getPlayer().getId() + ":" + c.getPlayer().getName() + ":" + selection);
                    cm.dispose();
                    return;
                }
            }
            if (selection >= -1 && action != -1) {
                if (cm.getType() == 0) {
                    NPCScriptManager.getInstance().startQuest(c, action, lastMsg, selection);
                } else if (cm.getType() == 1) {
                    NPCScriptManager.getInstance().endQuest(c, action, lastMsg, selection);
                } else {
                    NPCScriptManager.getInstance().action(c, action, lastMsg, selection);
                } 
            } else {
                cm.dispose();
            }
        }
    }

    public static final void repairAll(final MapleClient c) {
        IEquip eq;
        double rPercentage;
        int price = 0;
        Map<String, Integer> eqStats;
        final ItemInformation ii = ItemInformation.getInstance();
        final Map<IEquip, Integer> eqs = new HashMap<>();
        final MapleInventoryType[] types = {MapleInventoryType.EQUIP, MapleInventoryType.EQUIPPED};
        for (MapleInventoryType type : types) {
            for (IItem item : c.getPlayer().getInventory(type).newList()) {
                if (item instanceof IEquip) {
                    eq = (IEquip) item;
                    if (eq.getDurability() >= 0) {
                        eqStats = ii.getEquipStats(eq.getItemId());
                        if (eqStats.containsKey("durability") && eqStats.get("durability") > 0 && eq.getDurability() < eqStats.get("durability")) {
                            rPercentage = (100.0 - Math.ceil((eq.getDurability() * 1000.0) / (eqStats.get("durability") * 10.0)));
                            eqs.put(eq, eqStats.get("durability"));
                            price += (int) Math.ceil(rPercentage * ii.getPrice(eq.getItemId()) / (ii.getReqLevel(eq.getItemId()) < 70 ? 100.0 : 1.0));
                        }
                    }
                }
            }
        }
        if (eqs.size() <= 0 || c.getPlayer().getMeso() < price) {
            return;
        }
        c.getPlayer().gainMeso(-price, true);
        Equip ez;
        for (Entry<IEquip, Integer> eqqz : eqs.entrySet()) {
            ez = (Equip) eqqz.getKey();
            ez.setDurability(eqqz.getValue());
            c.getPlayer().forceReAddItem(ez.copy(),
                    ez.getPosition() < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP);
        }
    }

    public static final void repair(final ReadingMaple slea, final MapleClient c) {
        if (slea.available() < 4) {
            return;
        }
        final int position = slea.readInt();
        final MapleInventoryType type = position < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP;
        final IItem item = c.getPlayer().getInventory(type).getItem((byte) position);
        if (item == null) {
            return;
        }
        final Equip eq = (Equip) item;
        final ItemInformation ii = ItemInformation.getInstance();
        final Map<String, Integer> eqStats = ii.getEquipStats(item.getItemId());
        if (eq.getDurability() < 0 || !eqStats.containsKey("durability") || eqStats.get("durability") <= 0 || eq.getDurability() >= eqStats.get("durability")) {
            return;
        }
        final double rPercentage = (100.0 - Math.ceil((eq.getDurability() * 1000.0) / (eqStats.get("durability") * 10.0)));
        final int price = (int) Math.ceil(rPercentage * ii.getPrice(eq.getItemId()) / (ii.getReqLevel(eq.getItemId()) < 70 ? 100.0 : 1.0)); // /
        if (c.getPlayer().getMeso() < price) {
            return;
        }
        c.getPlayer().gainMeso(-price, false);
        eq.setDurability(eqStats.get("durability"));
        c.getPlayer().forceReAddItem(eq.copy(), type);
    }

    public static void QuestAction(MapleClient c, ReadingMaple lea) {
        byte action = lea.readByte();
        int quest = lea.readInt();

        if (quest == 20734 || quest == 43726) {
            return;
        }

        if (QuestConstants.blockQuest.contains(quest)) {
            return;
        }

        if (c.getPlayer() == null) {
            return;
        }
        MapleQuest q = MapleQuest.getInstance(quest);
        switch (action) {
            case 0: {
                lea.readInt(); // update tick
                final int itemid = lea.readInt();
                q.RestoreLostItem(c.getPlayer(), itemid);
                if (ServerConstants.showPackets) {
                }
                break;
            }
            case 1: {
                int npc = lea.readInt();
                FileoutputUtil.log("quest.txt", quest + "");
                if (npc == 0 && quest > 0) {
                    if (ServerConstants.showPackets) {
                    }
                    q.forceStart(c.getPlayer(), npc, null);
                    if (quest == 25100) {
                        c.getPlayer().changeMap(200020001, 0);
                    }
                } else if (!q.hasStartScript()) {
                    if (ServerConstants.showPackets) {
                    }
                    q.start(c.getPlayer(), npc);
                }
                break;
            }
            case 2: {
                int npc = lea.readInt();
                lea.readInt();

                if (q.hasEndScript()) {
                    return;
                }

                if (lea.available() >= 4) {
                    int selection = lea.readInt();
                    q.complete(c.getPlayer(), npc, selection);
                    if (ServerConstants.showPackets) {
                    }
                } else {
                    if (ServerConstants.showPackets) {
                    }
                    q.complete(c.getPlayer(), npc);
                }
                break;
            }
            case 3: {
                if (GameConstants.canForfeit(q.getId())) {
                    if (ServerConstants.showPackets) {
                    }
                    q.forfeit(c.getPlayer());
                } else {
                    c.getPlayer().dropMessage(1, "You may not forfeit this quest.");
                }
                break;
            }
            case 4: {
                int npc = lea.readInt();

                if (c.getPlayer().hasBlockedInventory()) {
                    return;
                }

                if (ServerConstants.showPackets) {
                }
                if (!NPCScriptManager.getInstance().UseScript(c, quest)) {
                    if (!QuestConstants.blockQuest.contains(quest)) {
                        QuestConstants.blockQuest.add(quest);
                    }
                }
                NPCScriptManager.getInstance().startQuest(c, npc, quest);
                break;
            }
            case 5: {
                int npc = lea.readInt();

                if (c.getPlayer().hasBlockedInventory()) {
                    return;
                }

                if (ServerConstants.showPackets) {
                }
                NPCScriptManager.getInstance().endQuest(c, npc, quest, GameConstants.questReader.contains(quest));
                c.getSession().writeAndFlush(MainPacketCreator.showSpecialEffect(15));
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(),
                        MainPacketCreator.showSpecialEffect(c.getPlayer().getId(), 15), false);
                break;
            }
        }
    }

    public static final void NPCShop(final ReadingMaple rh, final MapleClient c, final MapleCharacter chr) {
        final byte bmode = rh.readByte();

        switch (bmode) {
            case 0: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                final short position = rh.readShort();
                final int itemId = rh.readInt();
                final short quantity = rh.readShort();
                shop.buy(c, itemId, quantity, position);
                break;
            }
            case 1: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                final byte slot = (byte) rh.readShort();
                final int itemId = rh.readInt();
                final short quantity = rh.readShort();
                try {
                    shop.sell(c, GameConstants.getInventoryType(itemId), slot, quantity);
                } catch (Exception e) {
                    if (!ServerConstants.realese) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 2: {
                final MapleShop shop = chr.getShop();
                if (shop == null) {
                    return;
                }
                final byte slot = (byte) rh.readShort();
                shop.recharge(c, slot);
                break;
            }
            default:
                chr.setConversation(0);
                break;
        }
    }

    public static final void UpdateQuest(final ReadingMaple slea, final MapleClient c) {
        final MapleQuest quest = MapleQuest.getInstance(slea.readShort());
        if (quest != null) {
            c.getPlayer().updateQuest(c.getPlayer().getQuest(quest));
        }
    }

    public static final void UseItemQuest(final ReadingMaple slea, final MapleClient c) {
        final int npcid = slea.readInt();
        final short slot = slea.readShort();
        final int itemId = slea.readInt();
        if (GameConstants.isTownScroll(itemId)) {
            InventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (byte) 1, false);
            c.getPlayer().changeMap(c.getPlayer().getMap().getReturnMapId(), 0);
            return;
        }
        NPCScriptManager.getInstance().start(c, 2007, "consume_" + itemId + ".js");
        c.getPlayer().ea();
    }

    public static final void NpcQuestAction(ReadingMaple rm, MapleClient c) {
        int quest = rm.readInt();
        int npc = rm.readInt();
        byte t = rm.readByte();
        int oid = rm.readInt();
        NPCScriptManager.getInstance().endQuest(c, npc, quest, true);
    }

    public static Invocable getInvocable(String path, MapleClient c, boolean npc) {
        ScriptEngineManager sem = new ScriptEngineManager();
        FileReader fr = null;
        try {
            path = "scripts/" + path;
            ScriptEngine engine = null;

            if (c != null) {
                engine = c.getScriptEngine(path);
            }
            if (engine == null) {
                File scriptFile = new File(path);
                if (!scriptFile.exists()) {
                    return null;
                }
                engine = sem.getEngineByName("nashorn");
                if (c != null) {
                    c.setScriptEngine(path, engine);
                }
                fr = new FileReader(scriptFile);
                engine.eval("load('nashorn:mozilla_compat.js');");
                engine.eval(fr);
            } else if (c != null && npc) {
                c.removeClickedNPC();
                NPCScriptManager.getInstance().dispose(c);
                c.getSession().writeAndFlush(MainPacketCreator.enableActions(c.getPlayer()));
            }
            return (Invocable) engine;
        } catch (FileNotFoundException | ScriptException e) {
            System.err.println("Error executing script. Path: " + path + "\nException " + e);
            return null;
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
