package server.Systems;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.Equip;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventoryType;
import client.ItemInventory.MapleWeaponType;
import client.Stats.EnchantEquipStats;
import client.Stats.EquipStats;
import constants.GameConstants;
import constants.ServerConstants;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketProvider;
import server.Items.InventoryManipulator;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import provider.MapleData;
import provider.MapleDataTool;
import tools.HexTool;
import tools.Pair;
import tools.RandomStream.Randomizer;
import server.Items.ItemInformation;

public class StarForceSystem {
    
    public static int[] usejuhun = new int[4];
    public static String[] scrollstring = new String[]{};
        
    public static void AddItemRecv(ReadingMaple rh, MapleClient c) {
        short position;
        Equip item;
        byte a = rh.readByte();
        switch (a) {
            case 0:
                rh.skip(4);
                position = rh.readShort();
                short sposition = rh.readShort();
                boolean success = false;
                if (position > 0) {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(position);
                } else {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(position);
                }
                int itemtype = item.getItemId() / 10000;
                int per;
                final ItemInformation ii = ItemInformation.getInstance();
                final MapleData IData = ii.getItemData(item.getItemId());
                final MapleData info = IData.getChildByPath("info");
                int Job = MapleDataTool.getInt("reqJob", info, 0);
                int level = MapleDataTool.getInt("reqLevel", info, 0);
                if (item.getUpgradeSlots() > 0)
                    scrollstaticvoid(Job, itemtype, level);
                else {
                    scrollstring = new String[2];
                    scrollstring[0] = "이노센트 주문서 30%";
                    scrollstring[1] = "순백의 주문서 5%";
                }
                String scroll = scrollstring[sposition];
                if (scroll.startsWith("순백")) {
                    per = 5;
                } else if (scroll.startsWith("이노센트")) {
                    per = 30;
                } else {
                    per = Integer.valueOf(scroll.split("%")[0]); // % 이전 맨 앞에서 값 출력
                }
                if (c.getPlayer().isGM()) {
                    per = 100;
                }
                if (Randomizer.isSuccess(per)) {
                    success = true;
                }
                c.send(UpdateItemResult(item, success, sposition, c.getPlayer()));
                Equip zeros = null;
                if (GameConstants.isZero(c.getPlayer().getJob())) {
                    if (item.getPosition() == -11) {
                        zeros = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
                    } else if (item.getPosition() == -10) {
                        zeros = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
                    }
                }
                c.send(MainPacketCreator.updateEquipSlot(item));
                if (zeros != null) {
                    c.send(MainPacketCreator.updateEquipSlot(zeros));
                }
                break;
            case 0x32:
                position = rh.readShort();
                rh.skip(2);
                if (position > 0) {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(position);
                } else {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(position);
                }
                c.send(AddItemOnSystem(c, item, a, ServerConstants.feverTime ? 1 : 0));
                break;
            case 0x35:
                // StarForceChance
                if (ServerConstants.feverTime || c.getPlayer().getGMLevel() > 0) {
                    if (c.getPlayer().ForcingItem() > 0) {
                        item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) (c.getPlayer().ForcingItem()));
                    } else {
                        item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) (c.getPlayer().ForcingItem()));
                    }
                    StarSystem(c.getPlayer(), item, false);
                } else {
                    c.getPlayer().send(StarForceChance());
                }
                break;
            case 0x1:
                // StarForceUpgrade!
                boolean chance = false;
                rh.skip(4);
                if (c.getPlayer().ForcingItem() > 0) {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP)
                            .getItem((short) c.getPlayer().ForcingItem());
                } else {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)
                            .getItem((short) c.getPlayer().ForcingItem());
                }
                if (rh.readByte() == 0) {
                    chance = true;
                }
                StarSystem(c.getPlayer(), item, chance);
                break;
            case 0x34:
                short slot = rh.readShort();
                c.getPlayer().setForcingItem(slot);
                if (slot > 0) {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(slot);
                } else {
                    item = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slot);
                }
                StarSystemSet(item, c.getPlayer());
                c.getPlayer().send(StarForceItemSet(c, item, StarForceMeso(item), c.getPlayer().StarPer()[0],
                        c.getPlayer().StarPer()[1], c.getPlayer().StarPer()[2]));
                break;
            case 2:
                rh.skip(4);
                short targetslot = rh.readShort();
                short traceslot = rh.readShort();
                Equip targetitem = null;
                if (targetslot > 0) {
                    targetitem = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(targetslot);
                } else {
                    targetitem = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(targetslot);
                }
                Equip originalitem = targetitem;
                Equip traceitem = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(traceslot);
                targetitem.set(traceitem);
                for (int i = 0; i < targetitem.getEnhance(); i++) {
                    StarSystemUpgrade(targetitem, 0, c.getPlayer());
                }
                originalitem.setOwner(traceitem.getOwner());
                InventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, traceslot, (short) 1, false);
                c.getPlayer().send(EquipTrace(originalitem, targetitem));
                c.getPlayer().send(MainPacketCreator.updateEquipSlot(targetitem));
                break;
            default:
                break;
        }
    }

    public static int AddAllSmalls(int a) {
        int b = 0;
        if (a <= 0) {
            return 0;
        } else {
            for (int i = 1; i < 2147483647; i++) {
                if ((a - i) > 0) {
                    b += (a - i);
                } else {
                    break;
                }
            }
        }
        return b + a;
    }

    public static Pair<String, Boolean> isSuperial(int itemid) {
        if ((itemid >= 1102471 && itemid <= 1102475) || (itemid >= 1072732 && itemid <= 1072736)
                || (itemid >= 1132164 && itemid <= 1132168)) {
            return new Pair<String, Boolean>("Helisium", true);
        } else if ((itemid >= 1102476 && itemid <= 1102480) || (itemid >= 1072737 && itemid <= 1072741)
                || (itemid >= 1132169 && itemid <= 1132173)) {
            return new Pair<String, Boolean>("Nova", true);
        } else if ((itemid >= 1102481 && itemid <= 1102485) || (itemid >= 1072743 && itemid <= 1072747)
                || (itemid >= 1132174 && itemid <= 1132178 || (itemid >= 1082543 && itemid <= 1082547))) {
            return new Pair<String, Boolean>("Tilent", true);
        } else if ((itemid >= 1122241 && itemid <= 1122245)) {
            return new Pair<String, Boolean>("MindPendent", true);
        }
        return new Pair<String, Boolean>(null, false);
    }

    public static byte[] StarForceItemSet(MapleClient c, Equip item, long meso, int sucper, int downper, int desper) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
        packet.write(0x34);
        packet.write(downper > 0 ? desper > 0 ? 2 : 1 : 0);
        packet.writeLong(meso);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.write(0);
        packet.write(0);

        packet.writeInt(sucper * 10);
        packet.writeInt(desper * 10);
        packet.writeLong(0);
        packet.write(c.getPlayer().getEquipCustomValues(item) == 2 ? 1 : 0);
        StarForceStat(c, item, packet, item.getEnhance() == 0 ? 0 : (item.getEnhance() - 25));
        return packet.getPacket();
    }

    public static EnchantEquipStats Stat1(Equip item, int ReqJob) {
        if (GameConstants.isWeapon(item.getItemId())) {
            if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.AXE1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BLUNT1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.AXE2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BLUNT2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SPEAR
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.POLE_ARM
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.KNUCKLE
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.HANDCANNON
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.TEDO
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DAGGER
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.ENERGYSWORD
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.GUNTLIT
                    || ReqJob == 24) {
                return EnchantEquipStats.STR;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SOULSHOOTER
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CROSSBOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.GUN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DUALBOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.에인션트보우) {
                return EnchantEquipStats.DEX;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.WAND
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.STAFF
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.PLANE
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.LIMITER) {
                return EnchantEquipStats.INT;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CHAIN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CLAW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CAIN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.KATARA) {
                return EnchantEquipStats.LUK;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DESPERADO) {
                return EnchantEquipStats.HP;
            }
        } else {
            switch (ReqJob) {
                case 0:
                    return EnchantEquipStats.STR;
                case 1:
                case 16:
                    return EnchantEquipStats.STR;
                case 2:
                    return EnchantEquipStats.INT;
                case 4:
                    return EnchantEquipStats.DEX;
                case 8:
                    return EnchantEquipStats.LUK;
            }
        }
        return null;
    }

    public static EnchantEquipStats Stat2(Equip item, int ReqJob) {
        if (GameConstants.isWeapon(item.getItemId())) {
            if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.AXE1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BLUNT1H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.AXE2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BLUNT2H
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SPEAR
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.POLE_ARM
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.KNUCKLE
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.HANDCANNON
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SWORD
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.TEDO
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DAGGER
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.ENERGYSWORD
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.GUNTLIT
                    || ReqJob == 24) {
                return EnchantEquipStats.DEX;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.SOULSHOOTER
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.BOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CROSSBOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.GUN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DUALBOW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.에인션트보우) {
                return EnchantEquipStats.STR;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.WAND
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.STAFF
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.PLANE
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.LIMITER) {
                return EnchantEquipStats.LUK;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CHAIN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CLAW
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.CAIN
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.KATARA
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DAGGER) {
                return EnchantEquipStats.DEX;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DESPERADO) {
                return EnchantEquipStats.STR;
            }
        } else {
            switch (ReqJob) {
                case 0:
                    return EnchantEquipStats.DEX;
                case 1:
                case 16:
                    return EnchantEquipStats.DEX;
                case 2:
                    return EnchantEquipStats.LUK;
                case 4:
                    return EnchantEquipStats.STR;
                case 8:
                    return EnchantEquipStats.DEX;
            }
        }
        return null;
    }

    public static EnchantEquipStats Stat3(Equip item, int ReqJob) {
        if (GameConstants.isWeapon(item.getItemId())) {
            if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.ENERGYSWORD || ReqJob == 24) {
                return EnchantEquipStats.LUK;
            } else if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.DESPERADO) {
                return EnchantEquipStats.DEX;
            }
        } else {
            switch (ReqJob) {
                case 0:
                    return EnchantEquipStats.INT;
                case 8:
                    return EnchantEquipStats.STR;
                case 16:
                    return EnchantEquipStats.LUK;
            }
        }
        return null;
    }

    public static EnchantEquipStats Stat4(Equip item, int ReqJob) {
        if (!GameConstants.isWeapon(item.getItemId())) {
            switch (ReqJob) {
                case 0:
                case 8:
                    return EnchantEquipStats.LUK;
            }
        }
        return null;
    }

    public static EnchantEquipStats Atk(Equip item) {
        if (GameConstants.isWeapon(item.getItemId())) {
            if (GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.WAND
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.STAFF
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.PLANE
                    || GameConstants.getWeaponType(item.getItemId()) == MapleWeaponType.LIMITER) {
                return EnchantEquipStats.MATK;
            } else {
                return EnchantEquipStats.WATK;
            }
        }
        return null;
    }

    public static void StarForceStat(MapleClient c, Equip item, WritingPacket packet, int enhance) {
        c.getPlayer().stata().removeAll(c.getPlayer().stata());
        int stats = 0;
        MapleWeaponType a;
        final ItemInformation ii = ItemInformation.getInstance();
        final MapleData IData = ii.getItemData(item.getItemId());
        final MapleData info = IData.getChildByPath("info");
        int Job = MapleDataTool.getInt("reqJob", info, 0);
        Pair<String, Boolean> superial = isSuperial(item.getItemId());
        if (superial.getRight()) {
            if (enhance >= 5 && enhance < 15) {
                if (superial.getLeft().equals("Helisium")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, 3 + AddAllSmalls(enhance - 5)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, 3 + AddAllSmalls(enhance - 5)));
                } else if (superial.getLeft().equals("Nova")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, 6 + AddAllSmalls(enhance - 5)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, 6 + AddAllSmalls(enhance - 5)));
                } else if (superial.getLeft().equals("Tilent")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, 9 + AddAllSmalls(enhance - 5)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, 9 + AddAllSmalls(enhance - 5)));
                } else if (superial.getLeft().equals("MindPendent")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, 9 + AddAllSmalls(enhance - 5)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, 9 + AddAllSmalls(enhance - 5)));
                }
            } else {
                if (superial.getLeft().equals("Helisium")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.STR, 5 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.DEX, 5 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.INT, 5 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.LUK, 5 + AddAllSmalls(enhance)));
                } else if (superial.getLeft().equals("Nova")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.STR, 10 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.DEX, 10 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.INT, 10 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.LUK, 10 + AddAllSmalls(enhance)));
                } else if (superial.getLeft().equals("Tilent")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.STR, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.DEX, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.INT, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.LUK, 20 + AddAllSmalls(enhance)));
                } else if (superial.getLeft().equals("MindPendent")) {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.STR, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.DEX, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.INT, 20 + AddAllSmalls(enhance)));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.LUK, 20 + AddAllSmalls(enhance)));
                }
            }
        } else {
            if (GameConstants.isWeapon(item.getItemId())) {
                if (ItemInformation.getInstance().getEquipStats(item.getItemId()).containsKey("reqLevel") && ItemInformation.getInstance().getEquipStats(item.getItemId()).get("reqLevel") > 100) {
                    if (enhance < 15) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), Atk(item) == EnchantEquipStats.WATK ? ((item.getWatk() / 50) + 1) : ((item.getMatk() / 50) + 1)));
                    } else if (enhance < 22) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), enhance - 7));
                    } else {
                       
                        switch (enhance) {
                            case 22:
                                c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), 31));
                                break;
                            case 23:
                                c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), 62));
                                break;
                            case 24:
                                c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), 124));
                                break;
                        }
                    }
                    if (enhance >= 4 && enhance < 10) {
                        if (Stat1(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 5));
                        }
                        if (Stat2(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 5));
                        }
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 5));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 5));
                        }
                    } else if (enhance >= 10 && enhance < 15) {
                        if (Stat1(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 7));
                        }
                        if (Stat2(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 7));
                        }
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 7));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 7));
                        }
                    } else if (enhance >= 15 && enhance < 20) {
                        if (Stat1(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 15));
                        }
                        if (Stat2(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 15));
                        }
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 15));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 15));
                        }
                    } else if (enhance >= 20 && enhance < 25) {
                        if (Stat1(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 30));
                        }
                        if (Stat2(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 30));
                        }
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 30));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 30));
                        }
                    } else {
                        if (Stat1(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 3));
                        }
                        if (Stat2(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 3));
                        }
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 3));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 3));
                        }
                    }
                    if (Stat1(item, Job) != EnchantEquipStats.HP && Stat2(item, Job) != EnchantEquipStats.HP && Stat3(item, Job) != EnchantEquipStats.HP && Stat4(item, Job) != EnchantEquipStats.HP) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.HP, 10));
                    }
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MP, 10));
                } else {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Atk(item), Atk(item) == EnchantEquipStats.WATK ? ((item.getWatk() / 50) + 1) : ((item.getMatk() / 50) + 1)));
                    if (Stat1(item, Job) != EnchantEquipStats.HP && Stat2(item, Job) != EnchantEquipStats.HP && Stat3(item, Job) != EnchantEquipStats.HP && Stat4(item, Job) != EnchantEquipStats.HP) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.HP, 5));
                    }
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MP, 5));
                    if (Stat1(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 2));
                    }
                    if (Stat2(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 2));
                    }
                    if (Stat3(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 2));
                    }
                    if (Stat4(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 2));
                    }
                }
            } else {
                if (ItemInformation.getInstance().getEquipStats(item.getItemId()).get("reqLevel") > 100) {
                    if (enhance >= 4 && enhance < 10) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 5));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 5));
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 5));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 5));
                        }
                    } else if (enhance >= 10 && enhance < 15) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 7));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 7));
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 7));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 7));
                        }
                    } else if (enhance >= 15 && enhance < 20) {
                        if (enhance >= 17) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, enhance - 4));
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, enhance - 4));
                        }
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 15));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 15));
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 15));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 15));
                        }
                    } else if (enhance >= 20 && enhance < 25) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WATK, enhance + 2));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MATK, enhance + 2));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 30));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 30));
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 30));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 30));
                        }
                    } else {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 3));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 3));
                        if (Stat3(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 3));
                        }
                        if (Stat4(item, Job) != null) {
                            c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 3));
                        }
                    }
                    if (Stat1(item, Job) != EnchantEquipStats.HP && Stat2(item, Job) != EnchantEquipStats.HP && Stat3(item, Job) != EnchantEquipStats.HP && Stat4(item, Job) != EnchantEquipStats.HP) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.HP, 50));
                    }
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MP, 50));
                    if (enhance >= 4 && enhance < 10) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 15));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 15));
                    } else if (enhance >= 10 && enhance < 15) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 25));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 25));
                    } else if (enhance >= 15 && enhance < 20) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 55));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 55));
                    } else if (enhance >= 20 && enhance < 25) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 95));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 95));
                    } else {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 9));
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 9));
                    }
                } else {
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat1(item, Job), 2));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat2(item, Job), 2));
                    if (Stat3(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat3(item, Job), 2));
                    }
                    if (Stat4(item, Job) != null) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(Stat4(item, Job), 2));
                    }
                    if (Stat1(item, Job) != EnchantEquipStats.HP && Stat2(item, Job) != EnchantEquipStats.HP && Stat3(item, Job) != EnchantEquipStats.HP && Stat4(item, Job) != EnchantEquipStats.HP) {
                        c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.HP, 5));
                    }
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MP, 5));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.WDEF, 5));
                    c.getPlayer().stata().add(new Pair<EnchantEquipStats, Integer>(EnchantEquipStats.MDEF, 5));
                }
            }
        }
        for (int i = 0; i < c.getPlayer().stata().size(); i++) {
            stats |= c.getPlayer().stata().get(i).left.getValue();
        }
        if (packet != null) {
            packet.writeInt(stats);
            for (int i = 0; i < c.getPlayer().stata().size(); i++) {
                packet.writeInt(c.getPlayer().stata().get(i).getRight());
            }
        }

    }

    public static byte[] StarForceChance() {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
        packet.write(HexTool.getByteArrayFromHexString("35 00 00 5C 13 A5"));

        return packet.getPacket();
    }

    public static void StarSystemUpgrade(Equip equip, int success, MapleCharacter chr) {
        if (success == 1) {
            for (int i = 0; i < chr.stata().size(); i++) {
                EquipStats use = null;
                switch (chr.stata().get(i).getLeft().getValue()) {
                    case 0x1:
                        equip.setWatk((short) (equip.getWatk() + chr.stata().get(i).getRight()));
                        break;
                    case 0x2:
                        equip.setMatk((short) (equip.getMatk() + chr.stata().get(i).getRight()));
                        break;
                    case 0x4:
                        equip.setStr((short) (equip.getStr() + chr.stata().get(i).getRight()));
                        break;
                    case 0x8:
                        equip.setDex((short) (equip.getDex() + chr.stata().get(i).getRight()));
                        break;
                    case 0x10:
                        equip.setInt((short) (equip.getInt() + chr.stata().get(i).getRight()));
                        break;
                    case 0x20:
                        equip.setLuk((short) (equip.getLuk() + chr.stata().get(i).getRight()));
                        break;
                    case 0x40:
                        equip.setWdef((short) (equip.getWdef() + chr.stata().get(i).getRight()));
                        break;
                    case 0x80:
                        equip.setMdef((short) (equip.getMdef() + chr.stata().get(i).getRight()));
                        break;
                    case 0x100:
                        equip.setHp((short) (equip.getHp() + chr.stata().get(i).getRight()));
                        break;
                    case 0x200:
                        equip.setMp((short) (equip.getMp() + chr.stata().get(i).getRight()));
                        break;
                    case 0x400:
                        equip.setAcc((short) (equip.getAcc() + chr.stata().get(i).getRight()));
                        break;
                    case 0x800:
                        equip.setAvoid((short) (equip.getAvoid() + chr.stata().get(i).getRight()));
                        break;
                }
            }
            if (equip.getEnhance() > 25) {
                equip.setEnhance((byte) (equip.getEnhance() + 1));
            } else {
                equip.setEnhance((byte) 26);
            }
        } else if (success == 0) {
            Equip equipc = (Equip) equip.copy();
            StarForceStat(chr.getClient(), equipc, null,
                    equip.getEnhance() >= 26 ? equip.getEnhance() - 26 : equip.getEnhance() - 1);

            for (int i = 0; i < chr.stata().size(); i++) {
                EquipStats use = null;
                switch (chr.stata().get(i).getLeft().getValue()) {
                    case 0x1:
                        equip.setWatk((short) (equip.getWatk() - chr.stata().get(i).getRight()));
                        break;
                    case 0x2:
                        equip.setMatk((short) (equip.getMatk() - chr.stata().get(i).getRight()));
                        break;
                    case 0x4:
                        equip.setStr((short) (equip.getStr() - chr.stata().get(i).getRight()));
                        break;
                    case 0x8:
                        equip.setDex((short) (equip.getDex() - chr.stata().get(i).getRight()));
                        break;
                    case 0x10:
                        equip.setInt((short) (equip.getInt() - chr.stata().get(i).getRight()));
                        break;
                    case 0x20:
                        equip.setLuk((short) (equip.getLuk() - chr.stata().get(i).getRight()));
                        break;
                    case 0x40:
                        equip.setWdef((short) (equip.getWdef() - chr.stata().get(i).getRight()));
                        break;
                    case 0x80:
                        equip.setMdef((short) (equip.getMdef() - chr.stata().get(i).getRight()));
                        break;
                    case 0x100:
                        equip.setHp((short) (equip.getHp() - chr.stata().get(i).getRight()));
                        break;
                    case 0x200:
                        equip.setMp((short) (equip.getMp() - chr.stata().get(i).getRight()));
                        break;
                    case 0x400:
                        equip.setAcc((short) (equip.getAcc() - chr.stata().get(i).getRight()));
                        break;
                    case 0x800:
                        equip.setAvoid((short) (equip.getAvoid() - chr.stata().get(i).getRight()));
                        break;
                }
            }
            equip.setEnhance((byte) (equip.getEnhance() > 26 ? equip.getEnhance() - 1 : equip.getEnhance() - 26));
        } else if (success == 2) {// 실패&펑
            if (GameConstants.isZero(chr.getJob()) && (equip.getPosition() == -11 || equip.getPosition() == -10)) {
                Equip wa = (Equip) (chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11));
                Equip wb = (Equip) (chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10));
                Equip waa = (Equip) wa.copy();
                Equip wab = (Equip) wb.copy();
                waa.setItemTrace((short) 1);
                wab.setItemTrace((short) 1);
                InventoryManipulator.addFromDrop(chr.getClient(), waa, false);
                InventoryManipulator.addFromDrop(chr.getClient(), wab, false);
                InventoryHandler.resetZeroWeapon(chr);
            } else if (equip.getPosition() > 0) {
                ((Equip) (chr.getInventory(MapleInventoryType.EQUIP).getItem(equip.getPosition())))
                        .setItemTrace((short) 1);
            } else {
                ((Equip) (chr.getInventory(MapleInventoryType.EQUIPPED).getItem(equip.getPosition())))
                        .setItemTrace((short) 1);
            }
        }
    }

    public static void StarSystemUpdate(Equip equip, int success, MapleCharacter chr, boolean isZeroWeapon) {
        if (!isZeroWeapon) {
            chr.gainMeso(-StarForceMeso(equip), false);
        }
        if (success == 0) { // Grade down
            if (chr.getEquipCustomValues(equip) == -1) {
                chr.changeEquipCustomValue(equip, 1);
            } else {
                chr.changeEquipCustomValue(equip, 2);
            }
        } else {
            chr.removeEquipCustomValue(equip);
        }
        chr.send(UpdateItemStar(equip, success, chr));
        chr.send(MainPacketCreator.updateEquipSlot(equip));
    }

    public static void StarSystemSet(Equip equip, MapleCharacter chr) {
        int enhance = (equip.getEnhance() > 25 ? equip.getEnhance() - 25 : 0);
        switch (enhance) {
            case 0:
                chr.StarPers(95, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(50, 0, 0);
                }
                break;
            case 1:
                chr.StarPers(90, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(50, 0, 0);
                }
                break;
            case 2:
                chr.StarPers(85, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(45, 55, 0);
                }
                break;
            case 3:
                chr.StarPers(80, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(40, 60, 0);
                }
                break;
            case 4:
                chr.StarPers(75, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                	chr.StarPers(40, 60, 0);
                }
                break;
            case 5:
                chr.StarPers(70, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                	chr.StarPers(40, 60, 2);
                }
                break;
            case 6:
                chr.StarPers(65, 35, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(30, 70, 0);
                }
                break;
            case 7:
                chr.StarPers(60, 40, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(30, 70, 0);
                }
                break;
            case 8:
                chr.StarPers(55, 45, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(25, 74, 1);
                }
                break;
            case 9:
                chr.StarPers(50, 50, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(25, 72, 3);
                }
                break;
            case 10:
                chr.StarPers(45, 0, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(20, 75, 5);
                }
                break;
            case 11:
                chr.StarPers(30, 70, 0);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(15, 75, 10);
                }
                break;
            case 12:
            	chr.StarPers(30, 69, 1);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(10, 75, 15);
                }
                break;
            case 13:
                chr.StarPers(30, 69, 1);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(3, 30, 67);
                }
                break;
            case 14:
            	chr.StarPers(30, 69, 1);
                if (isSuperial(equip.getItemId()).right) {
                    chr.StarPers(1, 20, 79);
                }
                break;
            case 15:
            	chr.StarPers(30, 0, 2);
                break;
            case 16:
            	chr.StarPers(30, 68, 2);
                break;
            case 17:
            	chr.StarPers(30, 68, 2);
                break;
            case 18:
            	chr.StarPers(30, 67, 3);
                break;
            case 19:
            	chr.StarPers(30, 67, 3);
                break;
            case 20:
            	chr.StarPers(30, 0, 7);
                break;
            case 21:
            	chr.StarPers(30, 63, 7);
                break;
            case 22:
            	chr.StarPers(3, 78, 19);
                break;
            case 23:
            	chr.StarPers(2, 69, 29);
                break;
            case 24:
            	chr.StarPers(1, 59, 40);
                break;
        }
        if (chr.getEquipCustomValues(equip) == 2 || chr.getGMLevel() > 0) {
            chr.StarPers(100, 0, 0);
        }
    }

    public static void StarSystem(MapleCharacter chr, Equip equip, boolean isChance) {
        int success = 0;
        int FailAndDown = 0;
        int FailAndDestroy = 0;
        int meso = 0;
        int enhance = (equip.getEnhance() > 25 ? equip.getEnhance() - 25 : 0);
        switch (enhance) {
            case 0:
                success = 95;
                if (isSuperial(equip.getItemId()).right) {
                    success = 50;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 1:
                success = 90;
                if (isSuperial(equip.getItemId()).right) {
                    success = 45;
                    FailAndDown = 55;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 2:
                success = 85;
                if (isSuperial(equip.getItemId()).right) {
                    success = 40;
                    FailAndDown = 60;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 3:
                success = 85;
                if (isSuperial(equip.getItemId()).right) {
                    success = 35;
                    FailAndDown = 65;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 4:
                success = 80;
                if (isSuperial(equip.getItemId()).right) {
                    success = 35;
                    FailAndDown = 65;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 5:
                success = 75;
                if (isSuperial(equip.getItemId()).right) {
                    success = 30;
                    FailAndDown = 70;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 6:
                success = 70;
                FailAndDown = 30;
                if (isSuperial(equip.getItemId()).right) {
                    success = 30;
                    FailAndDown = 70;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 7:
                success = 65;
                FailAndDown = 35;
                if (isSuperial(equip.getItemId()).right) {
                    success = 30;
                    FailAndDown = 70;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 8:
                success = 60;
                FailAndDown = 40;
                if (isSuperial(equip.getItemId()).right) {
                    success = 25;
                    FailAndDown = 74;
                    FailAndDestroy = 1;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 9:
                success = 55;
                FailAndDown = 45;
                if (isSuperial(equip.getItemId()).right) {
                    success = 25;
                    FailAndDown = 72;
                    FailAndDestroy = 3;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 10:
                success = 45;
                if (isSuperial(equip.getItemId()).right) {
                    success = 20;
                    FailAndDown = 75;
                    FailAndDestroy = 5;
                }
                if (isChance) {
                    success += 5;
                }
                break;
            case 11:
                success = 35;
                FailAndDown = 65;
                if (isSuperial(equip.getItemId()).right) {
                    success = 15;
                    FailAndDown = 75;
                    FailAndDestroy = 10;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 12:
                success = 30;
                FailAndDown = 69;
                FailAndDestroy = 1;
                if (isSuperial(equip.getItemId()).right) {
                    success = 10;
                    FailAndDown = 75;
                    FailAndDestroy = 15;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 13:
                success = 30;
                FailAndDown = 69;
                FailAndDestroy = 1;
                if (isSuperial(equip.getItemId()).right) {
                    success = 3;
                    FailAndDown = 30;
                    FailAndDestroy = 67;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 14:
                success = 30;
                FailAndDown = 69;
                FailAndDestroy = 1;
                if (isSuperial(equip.getItemId()).right) {
                    success = 1;
                    FailAndDown = 20;
                    FailAndDestroy = 69;
                }
                if (isChance) {
                    success += 5;
                    FailAndDown -= 5;
                }
                break;
            case 15:
                success = 30;
                FailAndDestroy = 1;
                if (isChance) {
                    success += 5;
                }
                break;
            case 16:
                success = 30;
                FailAndDown = 68;
                FailAndDestroy = 2;
                if (isChance) {
                    success += 5;
                }
                break;
            case 17:
                success = 30;
                FailAndDown = 68;
                FailAndDestroy = 2;
                if (isChance) {
                    success += 5;
                }
                break;
            case 18:
                success = 30;
                FailAndDown = 67;
                FailAndDestroy = 3;
                if (isChance) {
                    success += 5;
                }
                break;
            case 19:
                success = 30;
                FailAndDown = 67;
                FailAndDestroy = 3;
                if (isChance) {
                    success += 5;
                }
                break;
            case 20:
                success = 30;
                FailAndDestroy = 7;
                if (isChance) {
                    success += 3;
                }
                break;
            case 21:
                success = 30;
                FailAndDown = 63;
                FailAndDestroy = 7;
                if (isChance) {
                    success += 5;
                }
                break;
            case 22:
                success = 3;
                FailAndDown = 78;
                FailAndDestroy = 19;
                if (isChance) {
                    success += 1;
                }
                break;
            case 23:
                success = 2;
                FailAndDown = 69;
                FailAndDestroy = 29;
                if (isChance) {
                    success += 1;
                }
                break;
            case 24:
                success = 1;
                FailAndDown = 59;
                FailAndDestroy = 40;
                if (isChance) {
                    success += 1;
                }
                break;
        }
        if (chr.getEquipCustomValues(equip) == 2 || chr.getGMLevel() > 0) {
            success = 100;
            FailAndDown = 0;
            FailAndDestroy = 0;
        } else if (ServerConstants.feverTime) {
            success *= 2;
        }
        Equip zeros = null;
        if (GameConstants.isZero(chr.getJob())) {
            if (equip.getPosition() == -11) {
                zeros = (Equip) (chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10));
            } else if (equip.getPosition() == -10) {
                zeros = (Equip) (chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11));
            }
        }
        if (Randomizer.isSuccess(success)) {
            if (zeros != null) {
                StarSystemUpdate(zeros, 1, chr, true);
            }
            StarSystemUpdate(equip, 1, chr, false);
        } else {//1: success 3: maintenance 2: pow 0: presumed to fall
            int Total = 100 - success;// First, let's subtract the probability from 100 and make it ex 3 per: 97 per
            if (FailAndDown > 0 && FailAndDestroy > 0) { // If there's a chance to go down // 97
                if (Randomizer.isSuccess(FailAndDown, Total)) {// If you have a chance to go down
                    if (zeros != null) {
                        StarSystemUpdate(zeros, 0, chr, true);
                    }
                    StarSystemUpdate(equip, 0, chr, false);
                } else {// If you have a chance to burst
                    if (zeros != null) {
                        StarSystemUpdate(equip, 2, chr, true);
                    }
                    StarSystemUpdate(equip, 2, chr, false);
                }
            } else if (FailAndDown > 0 && FailAndDestroy == 0) {// If there's a chance to go down
                if (zeros != null) {
                    StarSystemUpdate(equip, 0, chr, true);
                }
                StarSystemUpdate(equip, 0, chr, false);
            } else if (FailAndDown == 0 && FailAndDestroy == 0) {// If grade is maintained
                if (zeros != null) {
                    StarSystemUpdate(equip, 3, chr, true);
                }
                StarSystemUpdate(equip, 3, chr, false);
            } else if (FailAndDown == 0 && FailAndDestroy > 0) {// If there is a probability of popping
                if (Randomizer.isSuccess(FailAndDestroy, Total)) {// pop
                    if (zeros != null) {
                        StarSystemUpdate(equip, 2, chr, true);
                    }
                    StarSystemUpdate(equip, 2, chr, false);
                } else {// 유지
                    if (zeros != null) {
                        StarSystemUpdate(equip, 3, chr, true);
                    }
                    StarSystemUpdate(equip, 3, chr, false);
                }
            }
        }
    }

    public static void Itemupgrade(MapleClient c, Equip item, int scrollnumber, boolean success, MapleCharacter chr, boolean zeroWeapon) {//숫자별로
        int 주흔코드 = 4001832;
        int percent = -1;
        String value = scrollstring[scrollnumber].split(" ")[1];
        if (success) {
        	if (scrollstring[scrollnumber].startsWith("이노센트")) {
                chr.gainItem(주흔코드, (short) -5000, false, 0, null);
                Equip origin = (Equip) ItemInformation.getInstance().getEquipById(item.getItemId());
                        item.setAcc(origin.getAcc());
                        item.setAvoid(origin.getAvoid());
                        item.setDex(origin.getDex());
                        item.setHands(origin.getHands());
                        item.setHp(origin.getHp());
                        item.setHpR(origin.getHpR());
                        item.setInt(origin.getInt());
                        item.setJump(origin.getJump());
                        item.setLevel(origin.getLevel());
                        item.setLuk(origin.getLuk());
                        item.setMatk(origin.getMatk());
                        item.setMdef(origin.getMdef());
                        item.setMp(origin.getMp());
                        item.setMpR(origin.getMpR());
                        item.setSpeed(origin.getSpeed());
                        item.setStr(origin.getStr());
                        item.setUpgradeSlots(origin.getUpgradeSlots());
                        item.setWatk(origin.getWatk());
                        item.setWdef(origin.getWdef());
                        item.setEnhance((byte) 0);
                        item.setViciousHammer((byte) 0);
                        item.setAllDamageP(origin.getAllDamageP());
                        item.setIgnoreWdef(origin.getIgnoreWdef());
                        item.setBossDamage(origin.getBossDamage());

                
    			return;

        	} else if (scrollstring[scrollnumber].startsWith("순백")) {
                item.setUpgradeSlots((byte)((byte)item.getUpgradeSlots() + 1));
                chr.gainItem(주흔코드, (short) -3000, false, 0, null);
                return;
        	} else {
            percent = Integer.valueOf(scrollstring[scrollnumber].split("%")[0]);
                switch (value) {
                	case "힘":
                		if (percent == 100) {
                			item.addStr((short) 3);
                			item.addHp((short) 20);
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addStr((short) 4);
                			item.addHp((short) 70);
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addStr((short) 7);
                			item.addHp((short) 120);
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "민첩":
                		if (percent == 100) {
                			item.addDex((short) 3);
                			item.addHp((short) 20);
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addDex((short) 4);
                			item.addHp((short) 70);
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addDex((short) 7);
                			item.addHp((short) 120);
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "지력":
                		if (percent == 100) {
                			item.addInt((short) 3);
                			item.addHp((short) 20);
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addInt((short)  4);
                			item.addHp((short) 70);
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addInt((short) 7);
                			item.addHp((short) 120);
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "행운":
                		if (percent == 100) {
                			item.addLuk((short) 3);
                			item.addHp((short) 20);
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addLuk((short) 4);
                			item.addHp((short) 70);
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addLuk((short) 7);
                			item.addHp((short) 120);
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "체력":
                		if (percent == 100) {
                			item.addHp((short) 180);
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addHp((short) 270);
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addHp((short) 470);
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "공격력":
                		if (percent == 70 && (item.getItemId() / 10000 == 108)) {
                			item.addWatk((short) 2);
                		} else if (percent == 30 && (item.getItemId() / 10000 == 108)) {
                			item.addWatk((short) 3);
                		} else if (percent == 100) {
                			item.addWatk((short) 3);
                		} else if (percent == 70) {
                			item.addWatk((short) 5);
                		}
                		break;
                	case "마력":
                		if (percent == 70 && (item.getItemId() / 10000 == 108)) {
                			item.addMatk((short) 2);
                		} else if (percent == 30 && (item.getItemId() / 10000 == 108)) {
                			item.addMatk((short) 3);
                		} else if (percent == 100) {
                			item.addMatk((short) 3);
                		} else if (percent == 70) {
                			item.addMatk((short) 5);
                		}
                		break;
                	case "방어력":
                		if (percent == 100) {
                			item.addWdef((short) 3);
                			item.addMdef((short) 3);
                		} else if (percent == 70) {
                			item.addWdef((short) 5);
                			item.addMdef((short) 5);
                		} else if (percent == 30) {
                			item.addWdef((short) 10);
                			item.addMdef((short) 10);
                		}
                		break;
                	case "공격력(힘)": 
                		if (percent == 30) {
                			item.addWatk((short) 7);
                			item.addStr((short) 3);
                		} else if (percent == 15) {
                			item.addWatk((short) 9);
                			item.addStr((short) 4);
                		}
                		break;
                	case "마력(지력)":
                		if (percent == 30) {
                			item.addMatk((short) 7);
                			item.addInt((short) 3);
                		} else if (percent == 15) {
                			item.addMatk((short) 9);
                			item.addInt((short) 4);
                		}
                		break;
                	case "공격력(민첩)":
                		if (percent == 30) {
                			item.addWatk((short) 7);
                			item.addDex((short) 3);
                		} else if (percent == 15) {
                			item.addWatk((short) 9);
                			item.addDex((short) 4);
                		}
                		break;
                	case "공격력(행운)":
                		if (percent == 30) {
                			item.addWatk((short) 7);
                			item.addLuk((short) 3);
                		} else if (percent == 15) {
                			item.addWatk((short) 9);
                			item.addLuk((short) 4);
                		}
                		break;
                	
                		
                }
                item.setLevel((byte) (item.getLevel() + 1));
                item.setUpgradeSlots((byte)(item.getUpgradeSlots() - 1));
            }
        } else {
            if (!ItemFlag.SAFETY.check(item.getFlag())) {
            	if (!(scrollstring[scrollnumber].startsWith("이노센트") || scrollstring[scrollnumber].startsWith("순백"))) {
            		item.setUpgradeSlots((byte)((byte)item.getUpgradeSlots() - 1));
            	}
            }

            if (scrollstring[scrollnumber].startsWith("이노센트")) {
                chr.gainItem(주흔코드, (short) -5000, false, 0, null);
        	}
            if (scrollstring[scrollnumber].startsWith("순백")) {
            	chr.gainItem(주흔코드, (short) -3000, false, 0, null);
            }
        }
        if (ItemFlag.SAFETY.check(item.getFlag())) {
            item.setFlag((short) (item.getFlag() - ItemFlag.SAFETY.getValue()));
        }
        if (!zeroWeapon) {
            chr.gainItem(주흔코드, (short) -usejuhun[percent == 100 ? 0 : percent == 70 ? 1 : percent == 30 ? 2 : 3], false, 0, null);
        }
    }

    public static byte[] EquipTrace(Equip equip, Equip trace) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
        packet.write(0x65);
        packet.writeInt(1);
        packet.write(0);
        PacketProvider.addStarForceItemInfo(packet, equip);
        PacketProvider.addStarForceItemInfo(packet, trace);

        return packet.getPacket();
    }

    public static byte[] UpdateItemStar(Equip equip, int success, MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
        packet.write(0x65);
        packet.write(success);
        packet.writeInt(0);
        PacketProvider.addStarForceItemInfo(packet, equip);
        StarSystemUpgrade(equip, success, chr);
        if (success != 4) {
            PacketProvider.addStarForceItemInfo(packet, equip);
        }
        return packet.getPacket();
    }    

    public static byte[] UpdateItemResult(Equip item, boolean success, int scrollnumber, MapleCharacter chr) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
        packet.write(0x64);
        packet.write(0);
        packet.writeInt(success ? 1 : 0);
        packet.writeMapleAsciiString(scrollstring[scrollnumber]); // 1.2.251+
        PacketProvider.addStarForceItemInfo(packet, item);
        Equip zeros = null;
        if (GameConstants.isZero(chr.getJob())) {
            if (item.getPosition() == -11) {
                zeros = (Equip) chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
            } else if (item.getPosition() == -10) {
                zeros = (Equip) chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
            }
        }
        if (zeros != null) {
            Itemupgrade(chr.getClient(), zeros, scrollnumber, success, chr, true);
        }
        Itemupgrade(chr.getClient(), item, scrollnumber, success, chr, false);
        PacketProvider.addStarForceItemInfo(packet, item);

        return packet.getPacket();
    }

    public static void scrollstaticvoid(int job, int itemtype, int level) {
        boolean a = false;
        boolean a2 = false;
        boolean b = false;
        boolean c = false;
        if (itemtype == 121 || itemtype == 126 ||itemtype == 137 || itemtype == 138) {
        	a2 = true;
        } else if (itemtype > 120 && itemtype < 172) {//a = weapon
            a = true;
        } else if (itemtype == 108) {//b = Gloves
            c = true;
        } else {//c = Others
            b = true;
        }
        setJuhun(level, a);
        if (itemtype == 167) {
        	scrollstring = new String[16];
        	scrollstring[0] = "100% 공격력 주문서";
        	scrollstring[1] = "70% 공격력 주문서";
        	scrollstring[2] = "30% 공격력(힘) 주문서";
        	scrollstring[3] = "15% 공격력(힘) 주문서";
        	scrollstring[4] = "30% 공격력(민첩) 주문서";
        	scrollstring[5] = "15% 공격력(민첩) 주문서";
        	scrollstring[6] = "30% 공격력(행운) 주문서";
        	scrollstring[7] = "15% 공격력(행운) 주문서";
        	scrollstring[8] = "100% 마력 주문서";
        	scrollstring[9] = "70% 마력 주문서";
        	scrollstring[10] = "30% 마력(지력) 주문서";
        	scrollstring[11] = "15% 마력(지력) 주문서";
        	scrollstring[12] = "이노센트 주문서 30%";
        	scrollstring[13] = "순백의 주문서 5%";
        	scrollstring[14] = "이노센트 주문서 30%";
        	scrollstring[15] = "순백의 주문서 5%";
        } else if (a) {
        	scrollstring = new String[10];
        	scrollstring[0] = "100% 공격력 주문서";
        	scrollstring[1] = "70% 공격력 주문서";
        	scrollstring[2] = "30% 공격력(힘) 주문서";
        	scrollstring[3] = "15% 공격력(힘) 주문서";
        	scrollstring[4] = "30% 공격력(민첩) 주문서";
        	scrollstring[5] = "15% 공격력(민첩) 주문서";
        	scrollstring[6] = "30% 공격력(행운) 주문서";
        	scrollstring[7] = "15% 공격력(행운) 주문서";
        	scrollstring[8] = "이노센트 주문서 30%";
        	scrollstring[9] = "순백의 주문서 5%";
        } else if (a2) {
        	scrollstring = new String[6];
        	scrollstring[0] = "100% 마력 주문서";
        	scrollstring[1] = "70% 마력 주문서";
        	scrollstring[2] = "30% 마력(지력) 주문서";
        	scrollstring[3] = "15% 마력(지력) 주문서";
        	scrollstring[4] = "이노센트 주문서 30%";
        	scrollstring[5] = "순백의 주문서 5%";
        } else if (b) {
        	scrollstring = new String[17];
        	scrollstring[0] = "100% 힘 주문서";
        	scrollstring[1] = "70% 힘 주문서";
        	scrollstring[2] = "30% 힘 주문서";
        	scrollstring[3] = "100% 지력 주문서";
        	scrollstring[4] = "70% 지력 주문서";
        	scrollstring[5] = "30% 지력 주문서";
        	scrollstring[6] = "100% 민첩 주문서";
        	scrollstring[7] = "70% 민첩 주문서";
        	scrollstring[8] = "30% 민첩 주문서";
        	scrollstring[9] = "100% 행운 주문서";
        	scrollstring[10] = "70% 행운 주문서";
        	scrollstring[11] = "30% 행운 주문서";
        	scrollstring[12] = "100% 체력 주문서";
        	scrollstring[13] = "70% 체력 주문서";
        	scrollstring[14] = "30% 체력 주문서";
        	scrollstring[15] = "이노센트 주문서 30%";
        	scrollstring[16] = "순백의 주문서 5%";
        } else if (c) {
        	scrollstring = new String[7];
        	scrollstring[0] = "70% 공격력 주문서";
        	scrollstring[1] = "30% 공격력 주문서";
        	scrollstring[2] = "70% 마력 주문서";
        	scrollstring[3] = "30% 마력 주문서";
        	scrollstring[4] = "100% 방어력 주문서";
        	scrollstring[5] = "이노센트 주문서 30%";
        	scrollstring[6] = "순백의 주문서 5%";
        }
    }
    private static void setJuhun(int level, boolean weapon) {
		switch (level / 10) {
		case 1:
			usejuhun[0] = 2;
			usejuhun[1] = 3;
			usejuhun[2] = 4;
			usejuhun[3] = 5;
			break;
		case 2:
		usejuhun[0] = 3;
		usejuhun[1] = 4;
		usejuhun[2] = 5;
		usejuhun[3] = 6;
		break;
		case 3:
		usejuhun[0] = 5;
		usejuhun[1] = 7;
		usejuhun[2] = 8;
		usejuhun[3] = 10;
		break;
		case 4:
		usejuhun[0] = 6;
		usejuhun[1] = 8;
		usejuhun[2] = 10;
		usejuhun[3] = 12;
		break;
		case 5:
		usejuhun[0] = 8;
		usejuhun[1] = 10;
		usejuhun[2] = 12;
		usejuhun[3] = 14;
		break;
		case 6:
		usejuhun[0] = 9;
		usejuhun[1] = 12;
		usejuhun[2] = 14;
		usejuhun[3] = 17;
		break;
		case 7:
		usejuhun[0] = 11;
		usejuhun[1] = 14;
		usejuhun[2] = 17;
		usejuhun[3] = 20;
		break;
		case 8:
		usejuhun[0] = 23;
		usejuhun[1] = 30;
		usejuhun[2] = 36;
		usejuhun[3] = 43;
		break;
		case 9:
		usejuhun[0] = 29;
		usejuhun[1] = 38;
		usejuhun[2] = 46;
		usejuhun[3] = 55;
		break;
		case 10:
		usejuhun[0] = 36;
		usejuhun[1] = 47;
		usejuhun[2] = 56;
		usejuhun[3] = 67;
		break;
		case 11:
		usejuhun[0] = 43;
		usejuhun[1] = 56;
		usejuhun[2] = 67;
		usejuhun[3] = 80;
		break;
		case 12:
		if (!weapon) {
		usejuhun[0] = 95;
		usejuhun[1] = 120;
		usejuhun[2] = 145;
		} else {
		usejuhun[0] = 155;
		usejuhun[1] = 200;
		usejuhun[2] = 240;
		usejuhun[3] = 290;
		}
		break;
		case 13:
		if (!weapon) {
		usejuhun[0] = 120;
		usejuhun[1] = 155;
		usejuhun[2] = 190;
		} else {
		usejuhun[0] = 200;
		usejuhun[1] = 260;
		usejuhun[2] = 310;
		usejuhun[3] = 370;
		}
		break;
		case 14:
		if (!weapon) {
		usejuhun[0] = 150;
		usejuhun[1] = 195;
		usejuhun[2] = 230;
		} else {
		usejuhun[0] = 240;
		usejuhun[1] = 320;
		usejuhun[2] = 380;
		usejuhun[3] = 460;
		}
		case 15:
		case 16:
		case 20:
		if (!weapon) {
		usejuhun[0] = 185;
		usejuhun[1] = 240;
		usejuhun[2] = 290;
		} else {
		usejuhun[0] = 280;
		usejuhun[1] = 380;
		usejuhun[2] = 450;
		usejuhun[3] = 570;
		}
		}
	}
    
    public static int scrolljuhun(String scroll) {
    	if (scroll.startsWith("100%")) {
    		return usejuhun[0];
    	} else if (scroll.startsWith("70%")) {
    		return usejuhun[1];
    	} else if (scroll.startsWith("30%")) {
    		return usejuhun[2];
    	} else if (scroll.startsWith("15%")) {
    		return usejuhun[3];
    	} else if (scroll.contains("이노센트")) {
    		return 5000;
    	} else if (scroll.contains("순백")) {
    		return 3000;
    	} else {
    		return 0;
    	}
    }
    
    public static int scrollstatic(String scroll) {
    	if (scroll.contains("공격력") || scroll.contains("마력")) {
    		if (((scroll.startsWith("100%") || scroll.startsWith("70%")) && scroll.contains("공격력")) || scroll.equals("30% 공격력 주문서")) {
    			return 1;
    		} else if (((scroll.startsWith("100%") || scroll.startsWith("70%")) && scroll.contains("마력")) || scroll.equals("30% 마력 주문서")) {
    			return 2;
    		} else if (scroll.contains("힘")) {
    			return 5;
    		} else if (scroll.contains("민첩")) {
    			return 9;
    		} else if (scroll.contains("행운")) {
    			return 33;
    		}
    	} else {
    		if (scroll.contains("힘")) {
    			return 324;
    		} else if (scroll.contains("민첩")) {
    			return 328;
    		} else if (scroll.contains("지력")) {
    			return 336;
    		} else if (scroll.contains("행운")) {
    			return 352;
    		} else if (scroll.contains("체력")) {
    			return 320;
    		} else if (scroll.contains("방어력")) {
    			return 64;
    		}
    	}
		return 0;
    }
    
    private static int scrolltype(String string) {
		if (string.startsWith("100")) {
			return 0;
		} else if (string.startsWith("70")) {
			return 1;
		} else if (string.startsWith("30")) {
			return 2;
		} else if (string.startsWith("15")) {
			return 3;
		} else if (string.startsWith("이노센트")) {
			return 4;
		} else if (string.startsWith("순백")) {
			return 5;
		}
			return 0;
	}

    public static byte[] AddItemOnSystem(MapleClient c, Equip item, int a, int FeverTime) {
        WritingPacket packet = new WritingPacket();
        try {
            packet.writeShort(SendPacketOpcode.EQUIP_UPGRADE_SYSTEM_SEND.getValue());
            packet.write(0x32);
            packet.write(FeverTime);
            int itemtype = item.getItemId() / 10000;
            final ItemInformation ii = ItemInformation.getInstance();
            final MapleData IData = ii.getItemData(item.getItemId());
            final MapleData info = IData.getChildByPath("info");
            int Job = MapleDataTool.getInt("reqJob", info, 0);
            int level = MapleDataTool.getInt("reqLevel", info, 0);
            if (item.getUpgradeSlots() > 0)
                    scrollstaticvoid(Job, itemtype, level);
            else {
                    scrollstring = new String[2];
                    scrollstring[0] = "이노센트 주문서 30%";
                    scrollstring[1] = "순백의 주문서 5%";
            }
            packet.write(scrollstring.length);
            for (int i = 0; i < scrollstring.length; i++) {
                    packet.writeInt(scrolltype(scrollstring[i]));
                    packet.writeMapleAsciiString(scrollstring[i]);
                    packet.writeInt(scrollstring[i].contains("순백") ? 2 : scrollstring[i].contains("이노센트") ? 1 : 0); // 특수 주문서
                    packet.writeInt((scrollstring[i].contains("이노센트") || scrollstring[i].contains("순백")) ? 1 : 0); // 특수 주문서
                    packet.writeInt(scrollstatic(scrollstring[i]));
                    switch (scrollstatic(scrollstring[i])) {
                            case 0: // Whiteness, Ino
                                    break;
                            case 1: // ATT
                            case 2: // M.ATT
                            case 64: // Defense
                                    packet.writeInt(scrollstring[i].startsWith("100%") || (itemtype == 108 && scrollstring[i].startsWith("70%")) ? 2 : 3);
                                    break;
                            case 5: // Weapon (STR)
                            case 9: // Weapon (Dex)
                            case 33: // Weapons (Luck)
                                    packet.writeInt(scrollstring[i].startsWith("30%") ? 7 : 9); // ATT
                                    packet.writeInt(scrollstring[i].startsWith("30%") ? (scrollstring[i].contains("HP") ? 150 : 3) : (scrollstring[i].contains("HP") ? 200 : 4)); // Juice stat
                                    break;
                            default:
                                    packet.writeInt(scrollstring[i].startsWith("100%") ? 3 : scrollstring[i].startsWith("70%") ? 4 : 7);
                                    packet.writeInt(scrollstring[i].startsWith("100%") ? 30 : scrollstring[i].startsWith("70%") ? 70 : 120);
                                    if (scrollstatic(scrollstring[i]) != 320)
                                            packet.writeInt(scrollstring[i].startsWith("100%") ? 3 : scrollstring[i].startsWith("70%") ? 5 : 10);
                                    break;
                    }
                    packet.writeInt(scrolljuhun(scrollstring[i]));
                    packet.writeInt(scrolljuhun(scrollstring[i]));
                    packet.write(scrollstring[i].startsWith("100%") ? 1 : 0);
            }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return packet.getPacket();
    }

    public static long StarForceMeso(Equip item) {
        long base = 0;
        int ReqLevel = ItemInformation.getInstance().getEquipStats(item.getItemId()).get("reqLevel");
        int enhance = item.getEnhance() == 0 ? 0 : item.getEnhance() - 25;
        if (enhance < 0) {
            enhance = 0;
        }
        if (isSuperial(item.getItemId()).right) {
            switch (isSuperial(item.getItemId()).left) {
                case "Helisium":
                    return 2978300;
                case "Nova":
                    return 9253950;
                case "Tilent":
                base = 27916100;
            return (base + (enhance * 7925600));
            }
        } else if (ReqLevel < 110) {
            base = 41000;
            return (base + (enhance * 20100));
        } else if (ReqLevel >= 110 && ReqLevel < 120) {
            base = 54200;
            return (base + (enhance * 35533));
        } else if (ReqLevel >= 120 && ReqLevel < 130) {
            if (enhance <= 9) {
                base = 70100;
                return (base + (enhance * 46800));
            } else {
                base = 1401050;
                return (base + (enhance * 253166));
            }
        } else if (ReqLevel >= 130 && ReqLevel < 140) {
            if (enhance <= 9) {
                base = 88900;
                return (base + (enhance * 65866));
            } else {
                base = 1561150;
                return (base + (enhance * 218833));
            }
        } else if (ReqLevel >= 140 && ReqLevel < 150) {
            if (enhance <= 9) {
                base = 300000;
                return (base + (enhance * 270000));
            } else {
                base = 7447700;
                return (base + (enhance * 3902233));
            }
        } else if (ReqLevel >= 150 && ReqLevel < 160) {
            if (enhance <= 9) {
                base = 400000;
                return (base + (enhance * 300000));
            } else {
                base = 7470350;
                return (base + (enhance * 4643500));
            }
        } else if (ReqLevel >= 160) {
            if (enhance <= 9) {
                base = 500000;
                return (base + (enhance * 400000));
            } else {
                base = 9670350;
                return (base + (enhance * 5343700));
            }
        }
        return 0;
    }
}
