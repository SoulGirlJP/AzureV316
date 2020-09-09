package client.ItemInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import client.MapleAndroid;
import client.ItemInventory.PetsMounts.MaplePet;
import constants.GameConstants;
import constants.ServerConstants;
import connections.Database.MYSQL;
import connections.Database.MYSQLException;
import handlers.AuctionHandler.AuctionHandler.AuctionItemPackage;
import handlers.AuctionHandler.AuctionHandler.WorldAuction;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import server.Items.ItemInformation;
import tools.Pair;

public enum ItemFactory {

    INVENTORY("inventoryitems", "inventoryequipment", 1, "characterid"), STORAGE("inventoryitems", "inventoryequipment", 2, "accountid"), CASHSHOP("inventoryitems", "inventoryequipment", 4, "accountid"), HIRED_MERCHANT("inventoryitems", "inventoryequipment", 3, "packageid"), AUCTION("auctionitems", "auctionequipment", 7, "packageid");
    private final int value;
    private final String table, table_equip, arg;

    private ItemFactory(String table, String table_equip, int value, String arg) {
        this.table = table;
        this.table_equip = table_equip;
        this.value = value;
        this.arg = arg;
    }

    public int getValue() {
        return value;
    }

    public void saveAuctionItems(List<AuctionItemPackage> aitems) {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO auctionitems (characterid, type, accountid, packageId, itemid, inventorytype, position, quantity, owner, GM_Log, uniqueid, expiredate, flag, giftFrom, isCash, isPet, isAndroid, bid, meso, expired, bargain, ownername, buyer, buytime, starttime, `status`, inventoryid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    MYSQL.RETURN_GENERATED_KEYS);
            pse = con.prepareStatement(
                    "INSERT INTO auctionequipment VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            List<Pair<IItem, Byte>> items = new ArrayList<Pair<IItem, Byte>>();

            for (AuctionItemPackage aitem : aitems) {
                items.add(new Pair(aitem.getItem(), GameConstants.getInventoryType(aitem.getItem().getItemId()).getType()));
            }

            int ai = 0;
            for (Pair<IItem, Byte> item : items) {
                ps.setInt(1, aitems.get(ai).getOwnerId());
                ps.setInt(2, 7);
                ps.setInt(3, -1);
                ps.setInt(4, -1);
                ps.setInt(5, item.getLeft().getItemId());
                ps.setByte(6, item.getRight());
                ps.setInt(7, item.getLeft().getPosition());
                ps.setInt(8, item.getLeft().getQuantity());
                ps.setString(9, item.getLeft().getOwner());
                ps.setString(10, item.getLeft().getGMLog());
                ps.setInt(11, item.getLeft().getUniqueId());
                ps.setLong(12, item.getLeft().getExpiration());
                ps.setShort(13, item.getLeft().getFlag());
                ps.setString(14, item.getLeft().getGiftFrom());
                ps.setInt(15, item.getLeft().isCash() ? 1 : 0);
                ps.setInt(16, item.getLeft().getPet() != null ? 1 : 0);
                ps.setInt(17, item.getLeft().getAndroid() != null ? 1 : 0);
                AuctionItemPackage aitem = aitems.get(ai);
                ps.setLong(18, aitem.getBid());
                ps.setLong(19, aitem.getMesos());
                ps.setLong(20, aitem.getExpiredTime());
                ps.setBoolean(21, aitem.isBargain());
                ps.setString(22, aitem.getOwnerName());
                ps.setInt(23, aitem.getBuyer());
                ps.setLong(24, aitem.getBuyTime());
                ps.setLong(25, aitem.getStartTime());
                ps.setInt(26, aitem.getType(false, true));
                ps.setLong(27, item.getLeft().getInventoryId());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (!rs.next()) {
                    throw new MYSQLException("아이템 정보 삽입 실패.");
                } else {
                    int type = rs.getInt(1);
                    if (item.getRight().equals(MapleInventoryType.EQUIP.getType())
                            || item.getRight().equals(MapleInventoryType.EQUIPPED.getType())) {
                        IEquip equip = (IEquip) item.getLeft();
                        pse.setInt(1, type);
                        pse.setInt(2, equip.getUpgradeSlots());
                        pse.setInt(3, equip.getLevel());
                        pse.setInt(4, equip.getStr());
                        pse.setInt(5, equip.getDex());
                        pse.setInt(6, equip.getInt());
                        pse.setInt(7, equip.getLuk());
                        pse.setInt(8, equip.getHp());
                        pse.setInt(9, equip.getMp());
                        pse.setInt(10, equip.getWatk());
                        pse.setInt(11, equip.getMatk());
                        pse.setInt(12, equip.getWdef());
                        pse.setInt(13, equip.getMdef());
                        pse.setInt(14, equip.getAcc());
                        pse.setInt(15, equip.getAvoid());
                        pse.setInt(16, equip.getHands());
                        pse.setInt(17, equip.getSpeed());
                        pse.setInt(18, equip.getJump());
                        pse.setInt(19, equip.getViciousHammer());
                        pse.setInt(20, equip.getItemLevel());
                        pse.setInt(21, equip.getItemEXP());
                        pse.setInt(22, equip.getDurability());
                        pse.setByte(23, equip.getEnhance());
                        pse.setByte(24, equip.getState());
                        pse.setByte(25, equip.getLines());
                        pse.setInt(26, equip.getPotential1());
                        pse.setInt(27, equip.getPotential2());
                        pse.setInt(28, equip.getPotential3());
                        pse.setInt(29, equip.getPotential4());
                        pse.setInt(30, equip.getPotential5());
                        pse.setInt(31, equip.getPotential6());
                        pse.setInt(32, equip.getanvil());
                        pse.setInt(33, equip.getHpR());
                        pse.setInt(34, equip.getMpR());
                        pse.setInt(35, equip.getPotential7());
                        pse.setInt(36, equip.getFire());
                        pse.setShort(37, equip.getDownLevel());
                        pse.setByte(38, equip.getBossDamage());
                        pse.setByte(39, equip.getAllDamageP());
                        pse.setByte(40, equip.getAllStatP());
                        pse.setShort(41, equip.getIgnoreWdef());
                        pse.setShort(42, equip.getSoulName());
                        pse.setShort(43, equip.getSoulEnchanter());
                        pse.setInt(44, equip.getSoulPotential());
                        pse.setInt(45, equip.getSoulSkill());
                        pse.setInt(46, 0);
                        pse.setInt(47, equip.getItemTrace());
                        pse.setString(48, equip.getFireStatToString());
                        pse.setShort(49, equip.getArc());
                        pse.setInt(50, equip.getArcEXP());
                        pse.setInt(51, equip.getArcLevel());
                        pse.executeUpdate();
                        if (equip.getAndroid() != null) {
                            equip.getAndroid().saveToDb();
                        }
                    }
                }
                ai++;
                rs.close();
            }
            ps.close();
            pse.close();
            con.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (pse != null) {
                    pse.close();
                }
                if (con != null) {
                    con.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("[커넥션ERROR] 커넥션을 닫는데 문제가 발생 하였습니다.  " + ex);
            }
        }
    }

    public void loadAuctionItem() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            byte invstype = 7;
            con = MYSQL.getConnection();
            ps = con.prepareStatement(
                    "SELECT * FROM auctionitems LEFT JOIN auctionequipment USING (inventoryitemid) WHERE type = ?");

            ps.setInt(1, invstype);
            rs = ps.executeQuery();

            long expiration;
            boolean isCash;
            MapleInventoryType type;

            Item item;
            List<IItem> items = new ArrayList<IItem>();
            MaplePet pet;
            MapleAndroid android;
            while (rs.next()) {
                type = MapleInventoryType.getByType(rs.getByte("inventorytype"));
                expiration = rs.getLong("expiredate");
                isCash = rs.getInt("isCash") == 1;
                if (type.equals(MapleInventoryType.EQUIP) || type.equals(MapleInventoryType.EQUIPPED)) {
                    final Equip equip = new Equip(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("flag"));
                    equip.setOwner(rs.getString("owner"));
                    equip.setQuantity(rs.getShort("quantity"));
                    equip.setAcc(rs.getShort("acc"));
                    equip.setAvoid(rs.getShort("avoid"));
                    equip.setDex(rs.getShort("dex"));
                    equip.setHands(rs.getShort("hands"));
                    equip.setHp(rs.getShort("hp"));
                    equip.setInt(rs.getShort("int"));
                    equip.setJump(rs.getShort("jump"));
                    equip.setLuk(rs.getShort("luk"));
                    equip.setMatk(rs.getShort("matk"));
                    equip.setMdef(rs.getShort("mdef"));
                    equip.setMp(rs.getShort("mp"));
                    equip.setSpeed(rs.getShort("speed"));
                    equip.setStr(rs.getShort("str"));
                    equip.setWatk(rs.getShort("watk"));
                    equip.setWdef(rs.getShort("wdef"));
                    equip.setItemLevel(rs.getByte("itemLevel"));
                    if (ItemInformation.getInstance().getMaxLevelEquip(equip.getItemId()) > 0
                            && equip.getItemLevel() == 0) {
                        equip.setItemLevel((byte) 1);
                    }
                    equip.setItemEXP(rs.getInt("itemEXP"));
                    equip.setViciousHammer(rs.getByte("ViciousHammer"));
                    equip.setDurability(rs.getInt("durability"));
                    equip.setEnhance(rs.getByte("enhance"));
                    equip.setPotential1(rs.getInt("potential1"));
                    equip.setPotential2(rs.getInt("potential2"));
                    equip.setPotential3(rs.getInt("potential3"));
                    equip.setPotential4(rs.getInt("potential4"));
                    equip.setPotential5(rs.getInt("potential5"));
                    equip.setPotential6(rs.getInt("potential6"));
                    equip.setPotential7(rs.getInt("potential7"));
                    equip.setanvil(rs.getInt("anvil"));
                    equip.setHpR(rs.getShort("hpR"));
                    equip.setMpR(rs.getShort("mpR"));
                    equip.setState(rs.getByte("state"));
                    equip.setLines(rs.getByte("lines"));
                    equip.setFire((rs.getByte("fire")));
                    equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                    equip.setLevel(rs.getByte("level"));
                    equip.setBossDamage(rs.getByte("bossdmg"));
                    equip.setAllDamageP(rs.getByte("alldmgp"));
                    equip.setAllStatP(rs.getByte("allstatp"));
                    equip.setDownLevel(rs.getShort("downlevel"));
                    equip.setIgnoreWdef(rs.getShort("IgnoreWdef"));
                    equip.setSoulName(rs.getShort("soulname"));
                    equip.setSoulEnchanter(rs.getShort("soulenchanter"));
                    equip.setSoulPotential(rs.getShort("soulpotential"));
                    equip.setSoulSkill(rs.getInt("soulskill"));
                    equip.setItemTrace(rs.getShort("itemtrace"));
                    equip.setExpiration(expiration);
                    equip.setCash(isCash);
                    try {
                        equip.setFireStat(rs.getString("firestat"));
                    } catch (SQLException ex) {

                    }
                    equip.setArc(rs.getShort("arc"));
                    equip.setArcEXP(rs.getInt("arcexp"));
                    equip.setArcLevel(rs.getInt("arclevel"));
                    if (rs.getInt("isAndroid") > 0) {
                        android = MapleAndroid.loadFromDb(equip.getItemId(), rs.getInt("uniqueid"));
                        equip.setAndroid(android);
                    }
                    equip.setUniqueId(rs.getInt("uniqueid"));
                    equip.setGMLog(rs.getString("GM_Log"));

                    int e = 1;
                    if (equip.getState() > 1) {
                        int[] potentials = {equip.getPotential1(), equip.getPotential2(), equip.getPotential3(),
                            equip.getPotential4(), equip.getPotential5(), equip.getPotential6()};
                        for (int i : potentials) {
                            if (i > 0) {
                                int id = equip.getItemId();
                                if (i == 60002 || i == 31001 || i == 31002 || i == 31003 || i == 31004
                                        || (GameConstants.getOptionType(id) != 10 && (i % 1000 == 70 || i % 1000 == 71
                                        || i % 1000 == 291 || i % 1000 == 292 || i % 1000 == 601
                                        || i % 1000 == 602 || i % 1000 == 603 || i >= 60000))) {
                                    int level = equip.getState() - 16;
                                    int temp = level;
                                    int a = 0;
                                    while (temp > 1) {
                                        if (temp > 1) {
                                            --temp;
                                            ++a;
                                        }
                                    }
                                    int option = InventoryHandler.potential(level, e == 1 ? true : false, id);
                                    if (e == 1) {
                                        equip.setPotential1(option);
                                    } else if (e == 2) {
                                        equip.setPotential2(option);
                                    } else if (e == 3) {
                                        equip.setPotential3(option);
                                    } else if (e == 4) {
                                        equip.setPotential4(option);
                                    } else if (e == 5) {
                                        equip.setPotential5(option);
                                    } else if (e == 6) {
                                        equip.setPotential6(option);
                                    }
                                }
                            }
                            e++;
                        }
                    }
                    WorldAuction.addItem(new AuctionItemPackage(rs.getInt("characterid"), rs.getString("ownername"),
                            equip, rs.getLong("bid"), rs.getLong("meso"), rs.getLong("expired"),
                            rs.getInt("bargain") == 1, rs.getInt("buyer"), rs.getLong("buytime"),
                            rs.getLong("starttime"), rs.getInt("status")));

                } else {
                    item = new Item(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("quantity"),
                            rs.getByte("flag"));
                    item.setOwner(rs.getString("owner"));
                    item.setExpiration(expiration);
                    item.setGMLog(rs.getString("GM_Log"));
                    item.setGiftFrom(rs.getString("giftFrom"));
                    item.setCash(isCash);
                    if (rs.getInt("isPet") > 0) {
                        pet = MaplePet.loadFromDb(item.getItemId(), rs.getInt("uniqueid"), item.getPosition());
                        item.setPet(pet);
                    }
                    item.setUniqueId(rs.getInt("uniqueid"));
                    WorldAuction.addItem(new AuctionItemPackage(rs.getInt("characterid"), rs.getString("ownername"),
                            item, rs.getLong("bid"), rs.getLong("meso"), rs.getLong("expired"),
                            rs.getInt("bargain") == 1, rs.getInt("buyer"), rs.getLong("buytime"),
                            rs.getLong("starttime"), rs.getInt("status")));

                }
            }
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[커넥션ERROR] 커넥션을 닫는데 문제가 발생 하였습니다.  " + ex);
            }
        }
    }

    public int getItemSize(Connection con, boolean login, int id) throws SQLException {
        int ret = 0;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `");
        query.append(table);
        query.append("` LEFT JOIN `");
        query.append(table_equip);
        query.append("` USING (`inventoryitemid`) WHERE `type` = ?");
        query.append(" AND `");
        query.append(arg);
        query.append("` = ?");
        if (login) {
            query.append(" AND `inventorytype` = ");
            query.append(MapleInventoryType.EQUIPPED.getType());
        }
        try (PreparedStatement ps = con.prepareStatement(query.toString())) {
            ps.setInt(1, value);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ret++;
                }
            }
        }
        return ret;
    }

    public Map<Integer, Pair<IItem, MapleInventoryType>> loadItems(boolean login, int id) throws SQLException {
        return loadItems(MYSQL.getConnection(), login, id);
    }

    public Map<Integer, Pair<IItem, MapleInventoryType>> loadItems(Connection con, boolean login, int id)
            throws SQLException {

        Map<Integer, Pair<IItem, MapleInventoryType>> items = new LinkedHashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `");
        query.append(table);
        query.append("` LEFT JOIN `");
        query.append(table_equip);
        query.append("` USING (`inventoryitemid`) WHERE `type` = ?");
        query.append(" AND `");
        query.append(arg);
        query.append("` = ?");

        if (login) {
            query.append(" AND `inventorytype` = ");
            query.append(MapleInventoryType.EQUIPPED.getType());
        }
        try (PreparedStatement ps = con.prepareStatement(query.toString())) {
            ps.setInt(1, value);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MapleInventoryType mit = MapleInventoryType.getByType(rs.getByte("inventorytype"));

                    if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                        Equip equip = new Equip(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("flag"));
                        equip.setPotential7(rs.getInt("potential7"));
                        if (!login && equip.getPosition() != -55) {
                            equip.setOwner(rs.getString("owner"));
                            equip.setQuantity(rs.getShort("quantity"));
                            equip.setAcc(rs.getShort("acc"));
                            equip.setAvoid(rs.getShort("avoid"));
                            equip.setDex(rs.getShort("dex"));
                            equip.setHands(rs.getShort("hands"));
                            equip.setHp(rs.getShort("hp"));
                            equip.setInt(rs.getShort("int"));
                            equip.setJump(rs.getShort("jump"));
                            equip.setLuk(rs.getShort("luk"));
                            equip.setMatk(rs.getShort("matk"));
                            equip.setMdef(rs.getShort("mdef"));
                            equip.setMp(rs.getShort("mp"));
                            equip.setSpeed(rs.getShort("speed"));
                            equip.setStr(rs.getShort("str"));
                            equip.setWatk(rs.getShort("watk"));
                            equip.setWdef(rs.getShort("wdef"));
                            equip.setItemLevel(rs.getByte("itemLevel"));
                            if (ItemInformation.getInstance().getMaxLevelEquip(equip.getItemId()) > 0
                                    && equip.getItemLevel() == 0) {
                                equip.setItemLevel((byte) 1);
                            }
                            equip.setItemEXP(rs.getInt("itemEXP"));
                            equip.setViciousHammer(rs.getByte("ViciousHammer"));
                            equip.setDurability(rs.getInt("durability"));
                            equip.setEnhance(rs.getByte("enhance"));
                            equip.setPotential1(rs.getInt("potential1"));
                            equip.setPotential2(rs.getInt("potential2"));
                            equip.setPotential3(rs.getInt("potential3"));
                            equip.setPotential4(rs.getInt("potential4"));
                            equip.setPotential5(rs.getInt("potential5"));
                            equip.setPotential6(rs.getInt("potential6"));
                            equip.setanvil(rs.getInt("anvil"));
                            equip.setHpR(rs.getShort("hpR"));
                            equip.setMpR(rs.getShort("mpR"));
                            equip.setState(rs.getByte("state"));
                            equip.setLines(rs.getByte("lines"));
                            equip.setFire((rs.getByte("fire")));
                            equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                            equip.setLevel(rs.getByte("level"));
                            equip.setBossDamage(rs.getByte("bossdmg"));
                            equip.setAllDamageP(rs.getByte("alldmgp"));
                            equip.setAllStatP(rs.getByte("allstatp"));
                            equip.setDownLevel(rs.getShort("downlevel"));
                            equip.setIgnoreWdef(rs.getShort("IgnoreWdef"));
                            equip.setSoulName(rs.getShort("soulname"));
                            equip.setSoulEnchanter(rs.getShort("soulenchanter"));
                            equip.setSoulPotential(rs.getShort("soulpotential"));
                            equip.setSoulSkill(rs.getInt("soulskill"));
                            equip.setItemTrace(rs.getShort("itemtrace"));
                            equip.setExpiration(rs.getLong("expiredate"));
                            equip.setCash(rs.getInt("isCash") == 1);
                            equip.setFireStat(rs.getString("firestat"));
                            equip.setArc(rs.getShort("arc"));
                            equip.setArcEXP(rs.getInt("arcexp"));
                            equip.setArcLevel(rs.getInt("arclevel"));
                            if (rs.getInt("isAndroid") > 0) {
                                equip.setAndroid(MapleAndroid.loadFromDb(equip.getItemId(), rs.getInt("uniqueid")));
                            }
                            equip.setUniqueId(rs.getInt("uniqueid"));
                            equip.setGMLog(rs.getString("GM_Log")); 
                        }
                        items.put(rs.getInt("inventoryitemid"), new Pair<>(equip, mit));
                    } else {
                        Item item = new Item(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("quantity"),
                                rs.getShort("flag"));
                        item.setOwner(rs.getString("owner"));
                        item.setInventoryId(rs.getInt("inventoryitemid"));
                        item.setExpiration(rs.getLong("expiredate"));
                        item.setGMLog(rs.getString("GM_Log"));
                        item.setGiftFrom(rs.getString("giftFrom"));
                        item.setCash(rs.getInt("isCash") == 1);
                        item.setUniqueId(rs.getInt("uniqueid"));

                        if (GameConstants.isPet(item.getItemId())) {
                            if (item.getUniqueId() > -1) {
                                MaplePet pet = MaplePet.loadFromDb(item.getItemId(), item.getUniqueId(),
                                        item.getPosition());
                                if (pet != null) {
                                    item.setPet(pet);
                                }
                            } else {
                                item.setPet(MaplePet.createPet(item.getItemId(), item.getExpiration()));
                            }
                        }

                        items.put(rs.getInt("inventoryitemid"), new Pair<>(item, mit));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void saveItems(List<Pair<IItem, MapleInventoryType>> items, int id) throws SQLException {
        saveItems(items, MYSQL.getConnection(), id);
    }

    public void saveItems(List<Pair<IItem, MapleInventoryType>> items, final Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        
        try
        {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM `");
            query.append(table);
            query.append("` WHERE `type` = ? AND `");
            query.append(arg);
            query.append("` = ?");

            ps = con.prepareStatement(query.toString());
            ps.setInt(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            if (items == null) {
                return;
            }
            StringBuilder query_2 = new StringBuilder("INSERT INTO `");
            query_2.append(table);
            query_2.append("` (");
            query_2.append(arg);
            query_2.append(
                    ", type, itemid, inventorytype, position, quantity, owner, GM_Log, uniqueid, expiredate, flag, giftFrom, isCash, isPet, isAndroid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps = con.prepareStatement(query_2.toString(), Statement.RETURN_GENERATED_KEYS);
            String valueStr = "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            pse = con
                    .prepareStatement("INSERT INTO " + table_equip + " VALUES (DEFAULT, " + valueStr + ")");
            final Iterator<Pair<IItem, MapleInventoryType>> iter = items.iterator();
            Pair<IItem, MapleInventoryType> pair;
            while (iter.hasNext()) {
                pair = iter.next();
                IItem item = pair.getLeft();
                MapleInventoryType mit = pair.getRight();
                if (item.getPosition() == -55) {
                    continue;
                }
                ps.setInt(1, id);
                ps.setInt(2, value);
                ps.setInt(3, item.getItemId());
                ps.setInt(4, mit.getType());
                ps.setInt(5, item.getPosition());
                ps.setInt(6, item.getQuantity());
                ps.setString(7, item.getOwner());
                ps.setString(8, item.getGMLog());
                if (item.getPet() != null) {
                    ps.setInt(9, Math.max(item.getUniqueId(), item.getPet().getUniqueId()));
                } else {
                    ps.setInt(9, item.getUniqueId());
                }
                ps.setLong(10, item.getExpiration());
                ps.setShort(11, item.getFlag());
                ps.setString(12, item.getGiftFrom());
                ps.setByte(13, (byte) (item.isCash() ? 1 : 0));
                ps.setByte(14, (byte) (item.getPet() != null ? 1 : 0));
                ps.setByte(15, (byte) (item.getAndroid() != null ? 1 : 0));

                ps.executeUpdate();
                final int iid;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        rs.close();
                        continue;
                    }
                    iid = rs.getInt(1);
                }

                if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                    Equip equip = (Equip) item;
                    int i = 0;
                    pse.setInt(1, iid);
                    pse.setInt(2, equip.getUpgradeSlots());
                    pse.setInt(3, equip.getLevel());
                    pse.setInt(4, equip.getStr());
                    pse.setInt(5, equip.getDex());
                    pse.setInt(6, equip.getInt());
                    pse.setInt(7, equip.getLuk());
                    pse.setInt(8, equip.getHp());
                    pse.setInt(9, equip.getMp());
                    pse.setInt(10, equip.getWatk());
                    pse.setInt(11, equip.getMatk());
                    pse.setInt(12, equip.getWdef());
                    pse.setInt(13, equip.getMdef());
                    pse.setInt(14, equip.getAcc());
                    pse.setInt(15, equip.getAvoid());
                    pse.setInt(16, equip.getHands());
                    pse.setInt(17, equip.getSpeed());
                    pse.setInt(18, equip.getJump());
                    pse.setInt(19, equip.getViciousHammer());
                    pse.setInt(20, equip.getItemLevel());
                    pse.setInt(21, equip.getItemEXP());
                    pse.setInt(22, equip.getDurability());
                    pse.setByte(23, equip.getEnhance());
                    pse.setInt(24, equip.getState());
                    pse.setByte(25, equip.getLines());
                    pse.setInt(26, equip.getPotential1());
                    pse.setInt(27, equip.getPotential2());
                    pse.setInt(28, equip.getPotential3());
                    pse.setInt(29, equip.getPotential4());
                    pse.setInt(30, equip.getPotential5());
                    pse.setInt(31, equip.getPotential6());
                    pse.setInt(32, equip.getanvil());
                    pse.setInt(33, equip.getHpR());
                    pse.setInt(34, equip.getMpR());
                    pse.setInt(35, equip.getPotential7());
                    pse.setLong(36, equip.getFire());
                    pse.setShort(37, equip.getDownLevel());
                    pse.setByte(38, equip.getBossDamage());
                    pse.setByte(39, equip.getAllDamageP());
                    pse.setByte(40, equip.getAllStatP());
                    pse.setShort(41, equip.getIgnoreWdef());
                    pse.setShort(42, equip.getSoulName());
                    pse.setShort(43, equip.getSoulEnchanter());
                    pse.setInt(44, equip.getSoulPotential());
                    pse.setInt(45, equip.getSoulSkill());
                    pse.setInt(46, 0);
                    pse.setInt(47, equip.getItemTrace());
                    pse.setString(48, equip.getFireStatToString());
                    pse.setShort(49, equip.getArc());
                    pse.setInt(50, equip.getArcEXP());
                    pse.setInt(51, equip.getArcLevel());
                    pse.executeUpdate();
                    if (equip.getAndroid() != null) {
                        equip.getAndroid().saveToDb();
                    }
                }
            }
            pse.close();
            ps.close();
        }
        finally {
            try {
                if (pse != null) {
                    pse.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("[커넥션ERROR] 커넥션을 닫는데 문제가 발생 하였습니다.  " + ex);
            }
        }
    }
}
