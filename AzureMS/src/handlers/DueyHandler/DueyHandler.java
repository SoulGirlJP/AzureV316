package handlers.DueyHandler;

import static connections.Packets.PacketProvider.getTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.Stats.EquipStats;
import constants.GameConstants;
import connections.Database.MYSQL;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketProvider;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PacketUtility.WritingPacket;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import tools.RandomStream.Randomizer;

public class DueyHandler {

    public static void DueyHandler(ReadingMaple rm, MapleCharacter chr) {
        int value = rm.readByte();
        switch (value) {
            case 6: {
                int invid = rm.readInt();
                delDueyItem(invid);
                chr.send(removeItem(invid, true));
                chr.send(MainPacketCreator.resetActions(chr));
                break;
            }
            case 5: {
                int invid = rm.readInt();
                gainDueyItem(chr, invid);
                chr.send(removeItem(invid, false));
                delDueyItem(invid);
                chr.send(MainPacketCreator.resetActions(chr));
                break;
            }
            default: {
                int type = rm.readByte();
                int pos = rm.readShort();
                int quantity = rm.readShort();
                int mesos = rm.readInt();
                String name = rm.readMapleAsciiString();
                if (chr.getName().equals(name)) {
                    chr.send(DueyMessage(15));
                    chr.send(MainPacketCreator.resetActions(chr));
                    return;
                }
                chr.send(DueyMessage(19));
                saveDueyItems(type, pos, quantity, mesos, name, chr);
                InventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.getByType((byte) type), (byte) pos,
                        (short) quantity, false);
                chr.gainMeso(-mesos, false);
                chr.saveToDB(true, true);
                MapleCharacter recv = null;
                recv = chr.getClient().getChannelServer().getPlayerStorage().getCharacterByName(name);
                if (recv != null) {
                    recv.send(DueyHandler.DueyMessage(28));
                }
                chr.send(MainPacketCreator.resetActions(chr));
                break;
            }
        }
    }

    public static byte[] removeItem(int invid, boolean del) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DUEY_HANDLER.getValue());
        packet.write(24);
        packet.writeInt(invid);
        packet.write(del ? 3 : 4);
        return packet.getPacket();
    }

    public static byte[] DueyMessage(int value) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DUEY_HANDLER.getValue());
        packet.write(value);
        if (value == 28) {
            packet.write(0);
        }
        return packet.getPacket();
    }

    public static byte[] sendDuey(int type, String name) {
        WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.DUEY_HANDLER.getValue());
        packet.write(type);
        if (DueyItemSize(name) > 0) {
            packet.write(0);
            packet.write(DueyItemSize(name));
            DueyItemInfo(packet, name);
        } else {
            packet.write0(3);
        }
        return packet.getPacket();
    }

    public static void delDueyItem(int invid) {
        try {
            PreparedStatement ps;
            Connection con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM dueyitems WHERE inventoryitemid = ?");
            ps.setInt(1, invid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ps = con.prepareStatement("DELETE FROM dueyitems WHERE inventoryitemid = ?");
                ps.setInt(1, invid);
                ps.execute();
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {

        }
    }

    public static void gainDueyItem(MapleCharacter chr, int invid) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement dueydb = con.prepareStatement("SELECT * FROM dueyitems WHERE inventoryitemid = ?");
            dueydb.setInt(1, invid);
            ResultSet rs = dueydb.executeQuery();
            while (rs.next()) {
                if (rs.getInt("inventorytype") == 1) {
                    Equip equip = (Equip) ItemInformation.getInstance().getEquipById(rs.getInt("itemid"));
                    equip.setUpgradeSlots((byte) rs.getInt("upgradeslots"));
                    equip.setLevel((byte) rs.getInt("level"));
                    equip.setStr((short) rs.getInt("str"));
                    equip.setDex((short) rs.getInt("dex"));
                    equip.setInt((short) rs.getInt("int"));
                    equip.setLuk((short) rs.getInt("luk"));
                    equip.setHp((short) rs.getInt("hp"));
                    equip.setMp((short) rs.getInt("mp"));
                    equip.setWatk((short) rs.getInt("watk"));
                    equip.setMatk((short) rs.getInt("matk"));
                    equip.setWdef((short) rs.getInt("wdef"));
                    equip.setMdef((short) rs.getInt("mdef"));
                    equip.setAcc((short) rs.getInt("acc"));
                    equip.setAvoid((short) rs.getInt("avoid"));
                    equip.setHands((short) rs.getInt("hands"));
                    equip.setSpeed((short) rs.getInt("speed"));
                    equip.setJump((short) rs.getInt("jump"));
                    equip.setViciousHammer((byte) rs.getInt("ViciousHammer"));
                    equip.setItemLevel((byte) rs.getInt("itemLevel"));
                    equip.setItemEXP(rs.getInt("itemEXP"));
                    equip.setDurability(rs.getInt("durability"));
                    equip.setEnhance((byte) rs.getInt("enhance"));
                    equip.setState((byte) rs.getInt("state"));
                    equip.setLines((byte) rs.getInt("lines"));
                    equip.setPotential1(rs.getInt("potential1"));
                    equip.setPotential2(rs.getInt("potential2"));
                    equip.setPotential3(rs.getInt("potential3"));
                    equip.setPotential4(rs.getInt("potential4"));
                    equip.setPotential5(rs.getInt("potential5"));
                    equip.setPotential6(rs.getInt("potential6"));
                    equip.setPotential7(rs.getInt("potential7"));
                    equip.setFire((byte) rs.getInt("fire"));
                    equip.setDownLevel((byte) rs.getInt("downlevel"));
                    equip.setBossDamage((byte) rs.getInt("bossdmg"));
                    equip.setAllDamageP((byte) rs.getInt("alldmgp"));
                    equip.setAllStatP((byte) rs.getInt("allstatp"));
                    equip.setIgnoreWdef((short) rs.getInt("IgnoreWdef"));
                    equip.setSoulName((short) rs.getInt("soulname"));
                    equip.setSoulEnchanter((short) rs.getInt("soulenchanter"));
                    equip.setSoulPotential(rs.getShort("soulpotential"));
                    equip.setSoulSkill(rs.getInt("soulskill"));
                    InventoryManipulator.addFromDrop(chr.getClient(), equip, false);
                } else if (rs.getInt("inventorytype") != 0) {
                    chr.gainItem(rs.getInt("itemid"), rs.getInt("quantity"));
                }
                if (rs.getInt("mesos") > 0) {
                    chr.gainMeso(rs.getInt("mesos"), true);
                }
            }
            dueydb.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void DueyItemInfo(WritingPacket packet, String name) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        time.setSeconds(0);
        time.setNanos(0);
        time.setDate((time.getDate() + 29));
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement dueydb = con.prepareStatement("SELECT * FROM dueyitems WHERE name = ?");
            dueydb.setString(1, name);
            ResultSet rs = dueydb.executeQuery();
            while (rs.next()) {
                packet.writeInt(rs.getInt("inventoryitemid"));
                packet.writeAsciiString(rs.getString("recvname"), 13);
                packet.writeInt(rs.getInt("mesos"));
                packet.writeLong(PacketProvider.getTime(time.getTime()));
                packet.write(1);
                packet.writeAsciiString(rs.getString("recvname") + " Courier sent by has arrived.", 201);
                if (rs.getInt("itemid") > 0) {
                    packet.write(1);
                    packet.write(rs.getInt("inventorytype") == 1 ? 1 : 2);
                    packet.writeInt(rs.getInt("itemid"));
                    packet.write(0);
                    packet.writeLong(getTime(-1));
                    packet.writeInt(-1);
                    if (rs.getInt("inventorytype") == 1) {
                        int equipStats = 0;
                        try {
                            for (EquipStats equipstat : EquipStats.values()) {
                                switch (equipstat.name()) {
                                    case "UPGRADE": {
                                        equipStats |= rs.getInt("upgradeslots") > 0 ? EquipStats.UPGRADE.getValue() : 0;
                                        break;
                                    }
                                    case "LEVEL":
                                        equipStats |= rs.getInt("level") > 0 ? EquipStats.LEVEL.getValue() : 0;
                                        break;
                                    case "STR":
                                        equipStats |= rs.getInt("str") > 0 ? EquipStats.STR.getValue() : 0;
                                        break;
                                    case "DEX":
                                        equipStats |= rs.getInt("dex") > 0 ? EquipStats.DEX.getValue() : 0;
                                        break;
                                    case "INT":
                                        equipStats |= rs.getInt("int") > 0 ? EquipStats.INT.getValue() : 0;
                                        break;
                                    case "LUK":
                                        equipStats |= rs.getInt("luk") > 0 ? EquipStats.LUK.getValue() : 0;
                                        break;
                                    case "HP":
                                        equipStats |= rs.getInt("hp") > 0 ? EquipStats.HP.getValue() : 0;
                                        break;
                                    case "MP":
                                        equipStats |= rs.getInt("mp") > 0 ? EquipStats.MP.getValue() : 0;
                                        break;
                                    case "WATK":
                                        equipStats |= rs.getInt("watk") > 0 ? EquipStats.WATK.getValue() : 0;
                                        break;
                                    case "MATK":
                                        equipStats |= rs.getInt("matk") > 0 ? EquipStats.MATK.getValue() : 0;
                                        break;
                                    case "WDEF":
                                        equipStats |= rs.getInt("wdef") > 0 ? EquipStats.WDEF.getValue() : 0;
                                        break;
                                    case "MDEF":
                                        equipStats |= rs.getInt("mdef") > 0 ? EquipStats.MDEF.getValue() : 0;
                                        break;
                                    case "ACC":
                                        equipStats |= rs.getInt("acc") > 0 ? EquipStats.ACC.getValue() : 0;
                                        break;
                                    case "AVOID":
                                        equipStats |= rs.getInt("avoid") > 0 ? EquipStats.AVOID.getValue() : 0;
                                        break;
                                    case "HANDS":
                                        equipStats |= rs.getInt("hands") > 0 ? EquipStats.HANDS.getValue() : 0;
                                        break;
                                    case "SPEED":
                                        equipStats |= rs.getInt("speed") > 0 ? EquipStats.SPEED.getValue() : 0;
                                        break;
                                    case "JUMP":
                                        equipStats |= rs.getInt("jump") > 0 ? EquipStats.JUMP.getValue() : 0;
                                        break;
                                    case "ITEMLEVEL":
                                        equipStats |= rs.getInt("itemLevel") != 0 ? EquipStats.ITEMLEVEL.getValue() : 0;
                                        break;
                                    case "ITEMEXP":
                                        equipStats |= rs.getInt("itemEXP") > 0 ? EquipStats.ITEMEXP.getValue() : 0;
                                        break;
                                    case "DURABILITY":
                                        equipStats |= rs.getInt("durability") != -1 ? EquipStats.DURABILITY.getValue() : 0;
                                        break;
                                    case "HAMMER":
                                        equipStats |= rs.getInt("ViciousHammer") > 0 ? EquipStats.HAMMER.getValue() : 0;
                                        break;
                                    case "DOWNLEVEL":
                                        equipStats |= rs.getInt("downlevel") > 0 ? EquipStats.DOWNLEVEL.getValue() : 0;
                                        break;
                                    case "BOSSDAMAGE":
                                        equipStats |= rs.getInt("bossdmg") > 0 ? EquipStats.BOSSDAMAGE.getValue() : 0;
                                        break;
                                    case "IGNOREWDEF":
                                        equipStats |= rs.getInt("IgnoreWdef") > 0 ? EquipStats.IGNOREWDEF.getValue() : 0;
                                        break;
                                }
                            }

                            packet.writeInt(equipStats);
                            for (EquipStats equipstat : EquipStats.values()) {
                                switch (equipstat.name()) {
                                    case "UPGRADE": {
                                        packet.write(rs.getInt("upgradeslots") > 0 ? rs.getInt("upgradeslots") : -88888);
                                        break;
                                    }
                                    case "LEVEL":
                                        packet.write(rs.getInt("level") > 0 ? rs.getInt("level") : -88888);
                                        break;
                                    case "STR":
                                        packet.writeShort(rs.getInt("str") > 0 ? rs.getInt("str") : -88888);
                                        break;
                                    case "DEX":
                                        packet.writeShort(rs.getInt("dex") > 0 ? rs.getInt("dex") : -88888);
                                        break;
                                    case "INT":
                                        packet.writeShort(rs.getInt("int") > 0 ? rs.getInt("int") : -88888);
                                        break;
                                    case "LUK":
                                        packet.writeShort(rs.getInt("luk") > 0 ? rs.getInt("luk") : -88888);
                                        break;
                                    case "HP":
                                        packet.writeShort(rs.getInt("hp") > 0 ? rs.getInt("hp") : -88888);
                                        break;
                                    case "MP":
                                        packet.writeShort(rs.getInt("mp") > 0 ? rs.getInt("mp") : -88888);
                                        break;
                                    case "WATK":
                                        packet.writeShort(rs.getInt("watk") > 0 ? rs.getInt("watk") : -88888);
                                        break;
                                    case "MATK":
                                        packet.writeShort(rs.getInt("matk") > 0 ? rs.getInt("matk") : -88888);
                                        break;
                                    case "WDEF":
                                        packet.writeShort(rs.getInt("wdef") > 0 ? rs.getInt("wdef") : -88888);
                                        break;
                                    case "MDEF":
                                        packet.writeShort(rs.getInt("mdef") > 0 ? rs.getInt("mdef") : -88888);
                                        break;
                                    case "ACC":
                                        packet.writeShort(rs.getInt("acc") > 0 ? rs.getInt("acc") : -88888);
                                        break;
                                    case "AVOID":
                                        packet.writeShort(rs.getInt("avoid") > 0 ? rs.getInt("avoid") : -88888);
                                        break;
                                    case "HANDS":
                                        packet.writeShort(rs.getInt("hands") > 0 ? rs.getInt("hands") : -88888);
                                        break;
                                    case "SPEED":
                                        packet.writeShort(rs.getInt("speed") > 0 ? rs.getInt("speed") : -88888);
                                        break;
                                    case "JUMP":
                                        packet.writeShort(rs.getInt("jump") > 0 ? rs.getInt("jump") : -88888);
                                        break;
                                    case "ITEMLEVEL":
                                        packet.write(rs.getInt("itemLevel") != 0 ? rs.getInt("itemLevel") : -88888);
                                        break;
                                    case "ITEMEXP":
                                        packet.writeLong(rs.getInt("itemEXP") > 0 ? rs.getInt("itemEXP") : -88888);
                                        break;
                                    case "DURABILITY":
                                        packet.writeInt(rs.getInt("durability") != -1 ? rs.getInt("durability") : -88888);
                                        break;
                                    case "HAMMER":
                                        packet.writeInt(
                                                rs.getInt("ViciousHammer") > 0 ? rs.getInt("ViciousHammer") : -88888);
                                        break;
                                    case "DOWNLEVEL":
                                        packet.write(rs.getInt("downlevel") > 0 ? rs.getInt("downlevel") : -88888);
                                        break;
                                    case "BOSSDAMAGE":
                                        packet.write(rs.getInt("bossdmg") > 0 ? rs.getInt("bossdmg") : -88888);
                                        break;
                                    case "IGNOREWDEF":
                                        packet.write(rs.getInt("IgnoreWdef") > 0 ? rs.getInt("IgnoreWdef") : -88888);
                                        break;
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        int value = 4;
                        if (rs.getInt("alldmgp") != 0) {
                            value += 1;
                        }
                        if (rs.getInt("allstatp") != 0) {
                            value += 2;
                        }
                        if (rs.getInt("fire") >= 0) {
                            value += 8;
                        }
                        packet.writeInt(value);
                        if (rs.getInt("alldmgp") != 0) {
                            packet.write(rs.getInt("alldmgp"));
                        }
                        if (rs.getInt("allstatp") != 0) {
                            packet.write(rs.getInt("allstatp"));
                        }
                        packet.write(rs.getInt("fire")); // Scissors
                        if (rs.getInt("fire") >= 0) {
                            packet.writeInt(Randomizer.nextInt());
                            packet.writeInt(0);
                        }
                        if (rs.getInt("starforce") > 0) {
                            packet.write(0);
                            packet.writeShort(rs.getInt("starforce"));
                            packet.write(0);
                        }
                        packet.writeShort(0);
                        packet.write(rs.getInt("state")); // State
                        packet.write(rs.getInt("enhance")); // Enchance
                        packet.writeShort(rs.getInt("potential1")); // head
                        packet.writeShort(rs.getInt("potential2")); // cove
                        packet.writeShort(rs.getInt("potential3")); // cove
                        packet.writeShort(rs.getInt("potential4")); // Edition 1
                        packet.writeShort(rs.getInt("potential5")); // Edition 2
                        packet.writeShort(rs.getInt("potential6")); // Edition 3
                        packet.writeShort(rs.getInt("potential7")); // Mother
                        packet.writeInt(-1);
                        packet.writeInt(-1);
                        packet.writeLong(PacketProvider.getTime(-2));
                        packet.writeInt(-1);
                        packet.writeLong(0);
                        packet.writeLong(PacketProvider.getTime(-2));
                        packet.write0(16);
                        packet.writeShort(rs.getInt("soulname"));
                        packet.writeShort(rs.getInt("soulenchanter"));
                        packet.writeShort(rs.getInt("soulpotential"));
                    } else {
                        packet.writeShort(rs.getInt("quantity"));
                        packet.writeShort(0);
                        packet.writeShort(0);
                        if (GameConstants.isThrowingStar(rs.getInt("itemid"))
                                || GameConstants.isBullet(rs.getInt("itemid"))) {
                            packet.writeInt(2);
                            packet.writeShort(0x54);
                            packet.write(0);
                            packet.write(0x34);
                        }
                    }
                } else {
                    packet.write(0);
                }
            }
            packet.write(0);
            rs.close();
            dueydb.close();
            con.close();
        } catch (SQLException ex) {
        }
    }

    public static int DueyItemSize(String name) {
        int size = 0;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement dueydb = con.prepareStatement("SELECT * FROM dueyitems WHERE name = ?");
            dueydb.setString(1, name);
            ResultSet rs = dueydb.executeQuery();
            while (rs.next()) {
                size++;
            }
            rs.close();
            dueydb.close();
            con.close();
        } catch (SQLException ex) {
        }
        return size;
    }

    public static void saveDueyItems(int type, int pos, int quantity, int mesos, String name, MapleCharacter chr) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement pse = con.prepareStatement(
                    "INSERT INTO dueyitems VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            MapleInventory inventory = chr.getInventory(MapleInventoryType.getByType((byte) type));
            if (type == 1) {
                Equip equip = (Equip) inventory.getItem((byte) pos);
                pse.setInt(1, mesos);
                pse.setInt(2, equip.getItemId());
                pse.setInt(3, 1);
                pse.setInt(4, equip.getUpgradeSlots());
                pse.setInt(5, equip.getLevel());
                pse.setInt(6, equip.getStr());
                pse.setInt(7, equip.getDex());
                pse.setInt(8, equip.getInt());
                pse.setInt(9, equip.getLuk());
                pse.setInt(10, equip.getHp());
                pse.setInt(11, equip.getMp());
                pse.setInt(12, equip.getWatk());
                pse.setInt(13, equip.getMatk());
                pse.setInt(14, equip.getWdef());
                pse.setInt(15, equip.getMdef());
                pse.setInt(16, equip.getAcc());
                pse.setInt(17, equip.getAvoid());
                pse.setInt(18, equip.getHands());
                pse.setInt(19, equip.getSpeed());
                pse.setInt(20, equip.getJump());
                pse.setInt(21, equip.getViciousHammer());
                pse.setInt(22, equip.getItemLevel());
                pse.setInt(23, equip.getItemEXP());
                pse.setInt(24, equip.getDurability());
                pse.setByte(25, equip.getEnhance());
                pse.setByte(26, equip.getState());
                pse.setByte(27, equip.getLines());
                pse.setInt(28, equip.getPotential1());
                pse.setInt(29, equip.getPotential2());
                pse.setInt(30, equip.getPotential3());
                pse.setInt(31, equip.getPotential4());
                pse.setInt(32, equip.getPotential5());
                pse.setInt(33, equip.getPotential6());
                pse.setInt(34, equip.getanvil());
                pse.setInt(35, equip.getHpR());
                pse.setInt(36, equip.getMpR());
                pse.setInt(37, equip.getPotential7());
                pse.setInt(38, equip.getFire());
                pse.setShort(39, equip.getDownLevel());
                pse.setByte(40, equip.getBossDamage());
                pse.setByte(41, equip.getAllDamageP());
                pse.setByte(42, equip.getAllStatP());
                pse.setShort(43, equip.getIgnoreWdef());
                pse.setShort(44, equip.getSoulName());
                pse.setShort(45, equip.getSoulEnchanter());
                pse.setInt(46, equip.getSoulPotential());
                pse.setInt(47, equip.getSoulSkill());
                pse.setString(48, name);
                pse.setInt(49, type);
                pse.setInt(50, equip.getInventoryId());
                pse.setString(51, chr.getName());
                pse.executeUpdate();
            } else {
                pse.setInt(1, mesos);
                pse.setInt(2, 0);
                pse.setInt(3, 0);
                pse.setInt(4, 0);
                pse.setInt(5, 0);
                pse.setInt(6, 0);
                pse.setInt(7, 0);
                pse.setInt(8, 0);
                pse.setInt(9, 0);
                pse.setInt(10, 0);
                pse.setInt(11, 0);
                pse.setInt(12, 0);
                pse.setInt(13, 0);
                pse.setInt(14, 0);
                pse.setInt(15, 0);
                pse.setInt(16, 0);
                pse.setInt(17, 0);
                pse.setInt(18, 0);
                pse.setInt(19, 0);
                pse.setInt(20, 0);
                pse.setInt(21, 0);
                pse.setInt(22, 0);
                pse.setInt(23, 0);
                pse.setInt(24, 0);
                pse.setByte(25, (byte) 0);
                pse.setByte(26, (byte) 0);
                pse.setByte(27, (byte) 0);
                pse.setInt(28, 0);
                pse.setInt(29, 0);
                pse.setInt(30, 0);
                pse.setInt(31, 0);
                pse.setInt(32, 0);
                pse.setInt(33, 0);
                pse.setInt(34, 0);
                pse.setInt(35, 0);
                pse.setInt(36, 0);
                pse.setInt(37, 0);
                pse.setInt(38, 0);
                pse.setByte(39, (byte) 0);
                pse.setByte(40, (byte) 0);
                pse.setByte(41, (byte) 0);
                pse.setByte(42, (byte) 0);
                pse.setShort(43, (short) 0);
                pse.setShort(44, (short) 0);
                pse.setShort(45, (short) 0);
                pse.setInt(46, 0);
                pse.setInt(47, 0);
                pse.setInt(48, 0);
                pse.setString(49, name);
                pse.setInt(50, type);
                pse.setInt(51, Randomizer.nextInt());
                pse.setString(52, chr.getName());
                pse.executeUpdate();
            }
            pse.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Duey Saving error : " + ex);
        }
    }
}
