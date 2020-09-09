package connections.Packets;

import java.util.List;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.IItem;
import handlers.GlobalHandler.PlayerHandler.PlayerInteractionHandler;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import server.Items.MerchItemPackage;
import server.Shops.AbstractPlayerStore;
import server.Shops.AbstractPlayerStore.BoughtItem;
import server.Shops.HiredMerchant;
import server.Shops.IMapleCharacterShop;
import server.Shops.MapleCharacterShop;
import server.Shops.MapleCharacterShopItem;
import server.Shops.MapleMiniGame;
import tools.Pair;

public class PlayerShopPacket {

    public static final byte[] sendPlayerShopBox(final MapleCharacter c) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.UPDATE_CHAR_BOX.getValue());
        packet.writeInt(c.getId());
        addAnnounceBox(packet, c);

        return packet.getPacket();
    }

    public static final void addAnnounceBox(final WritingPacket packet, final MapleCharacter chr) {
        if (chr.getPlayerShop() != null && chr.getPlayerShop().isOwner(chr)) {
            addInteraction(packet, chr.getPlayerShop());
        } else {
            packet.write(0);
        }
    }

    public static final void addInteraction(final WritingPacket packet, IMapleCharacterShop shop) {
        packet.write(shop.getGameType());
        packet.writeInt(((AbstractPlayerStore) shop).getObjectId());
        packet.writeMapleAsciiString(shop.getDescription());
        packet.write(shop.getPassword().length() > 0 ? 1 : 0); // password = false
        packet.write(shop.getItemId() % 10);
        packet.write(shop.getSize()); // current size
        packet.write(shop.getMaxSize()); // full slots... 4 = 4-1=3 = has slots, 1-1=0 = no slots
        packet.write(shop.isOpen() ? 0 : 1);
        packet.writeMapleAsciiString(shop.getOwnerName());
        packet.writeMapleAsciiString("[Mini Room]" + shop.getDescription());
        packet.writeLong(0);
        packet.write(0);
        packet.writeInt(0);
    }

    public static final byte[] sendTitleBox() {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SEND_TITLE_BOX.getValue());
        packet.write(7);

        return packet.getPacket();
    }

    public static final byte[] alreadyOpenedShop(int channel, int room) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.SEND_TITLE_BOX.getValue());
        packet.write(8);
        packet.writeInt(910000000 + room);
        packet.write(channel);

        return packet.getPacket();
    }

    public static final byte[] getHiredMerch(final MapleCharacter chr, final HiredMerchant merch,
            final boolean firstTime) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x14);
        packet.write(6); // Like mode.
        packet.write(7); // Like mode.
        packet.writeShort(merch.getVisitorSlot(chr));
        packet.writeInt(merch.getItemId());
        packet.writeMapleAsciiString("고용상인");
        for (final Pair<Byte, MapleCharacter> storechr : merch.getVisitors()) {
            packet.write(storechr.left);
            PacketProvider.addPlayerLooks(packet, storechr.right, false, chr.getGender() == 1);
            packet.writeMapleAsciiString(storechr.right.getName());
            packet.writeShort(storechr.right.getJob());
        }
        packet.write(0xFF);
        if (merch.isOwner(chr)) {
            packet.writeShort(merch.getMessages().size());
            for (int i = 0; i < merch.getMessages().size(); i++) {
                packet.writeMapleAsciiString(merch.getMessages().get(i).getLeft());
                packet.write(merch.getMessages().get(i).getRight());
            }
        } else {
            packet.writeShort(0); // Messages
        }
        packet.writeMapleAsciiString(merch.getOwnerName());
        if (merch.isOwner(chr)) {
            packet.writeInt(merch.getTimeLeft());
            packet.write(firstTime ? 1 : 0);
            packet.write(merch.getBoughtItems().size());
            for (BoughtItem SoldItem : merch.getBoughtItems()) {
                packet.writeInt(SoldItem.id);
                packet.writeShort(SoldItem.quantity); // number of purchased
                packet.writeLong(SoldItem.totalPrice); // total price
                packet.writeMapleAsciiString(SoldItem.buyer); // name of the buyer
            }
            packet.writeLong(merch.getMeso());
        }
        packet.writeInt(19167); // TODO : value
        packet.writeMapleAsciiString(merch.getDescription());
        packet.write(16); // official is 16 (slot) not 10
        packet.writeLong(merch.getMeso()); // meso
        packet.write(merch.getItems().size());
        for (final MapleCharacterShopItem item : merch.getItems()) {
            packet.writeShort(item.bundles);
            packet.writeShort(item.item.getQuantity());
            packet.writeLong(item.price); // v192 4byte.
            PacketProvider.addItemInfo(packet, item.item, true, true, null);
        }
        packet.writeShort(0);

        return packet.getPacket();
    }

    public static final byte[] getPlayerStore(final MapleCharacter chr, final boolean firstTime) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        IMapleCharacterShop ips = chr.getPlayerShop();
        switch (ips.getShopType()) {
            case IMapleCharacterShop.PLAYER_SHOP:
                packet.write(0x14);
                packet.write(5);
                packet.write(7);
                break;
        }
        packet.writeShort(ips.getVisitorSlot(chr));
        PacketProvider.addPlayerLooks(packet, ((MapleCharacterShop) ips).getMCOwner(), false, chr.getGender() == 1);
        packet.writeMapleAsciiString(ips.getOwnerName());
        packet.writeShort(((MapleCharacterShop) ips).getMCOwner().getJob());
        for (final Pair<Byte, MapleCharacter> storechr : ips.getVisitors()) {
            packet.write(storechr.left);
            PacketProvider.addPlayerLooks(packet, storechr.right, false, chr.getGender() == 1);
            packet.writeMapleAsciiString(storechr.right.getName());
            packet.writeShort(storechr.right.getJob());
        }
        packet.write(0xFF);
        packet.writeInt(2665);
        packet.writeMapleAsciiString(ips.getDescription());
        packet.write(0x10);
        packet.write(ips.getItems().size());
        packet.writeShort(0);
        for (final MapleCharacterShopItem item : ips.getItems()) {
            packet.writeShort(item.bundles);
            packet.writeShort(item.item.getQuantity());
            packet.writeInt(item.price);
            PacketProvider.addItemInfo(packet, item.item, true, true, null);
        }
        return packet.getPacket();
    }

    public static final byte[] shopChat(final String message, final int slot) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x18);
        packet.write(0x19);
        packet.write(slot);
        packet.writeMapleAsciiString(message);
        packet.writeMapleAsciiString("");
        packet.writeMapleAsciiString(message);
        packet.writeLong(0);
        packet.write(-1);
        packet.writeInt(0);
        return packet.getPacket();
    }

    public static final byte[] shopErrorMessage(final int error, final int type, final int msg) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(error);
        packet.write(type);
        packet.write(msg);

        return packet.getPacket();
    }

    public static final byte[] spawnHiredMerchant(final HiredMerchant hm) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.SPAWN_HIRED_MERCHANT.getValue());
        packet.writeInt(hm.getOwnerId());
        packet.writeInt(hm.getItemId());
        packet.writePos(hm.getPosition());
        packet.writeShort(0);
        packet.writeMapleAsciiString(hm.getOwnerName());
        PacketProvider.addInteraction(packet, hm);
        return packet.getPacket();
    }

    public static final byte[] destroyHiredMerchant(final int id) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.DESTROY_HIRED_MERCHANT.getValue());
        packet.writeInt(id);

        return packet.getPacket();
    }

    public static final byte[] shopItemUpdate(final IMapleCharacterShop shop) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x4B);
        if (shop.getShopType() == IMapleCharacterShop.HIRED_MERCHANT) {
            packet.writeInt(0);
            packet.writeInt(0); // 202v
        }
        packet.write(shop.getItems().size());
        for (final MapleCharacterShopItem item : shop.getItems()) {
            packet.writeShort(item.bundles);
            packet.writeShort(item.item.getQuantity());
            packet.writeInt(item.price);
            packet.writeInt(0); // v192
            PacketProvider.addItemInfo(packet, item.item, true, true, null);
        }
        packet.writeShort(0);
        return packet.getPacket();
    }

    public static final byte[] shopVisitorAdd(final MapleCharacter chr, final int slot) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(11);
        packet.write(slot);
        PacketProvider.addPlayerLooks(packet, chr, false, chr.getGender() == 1);
        packet.writeMapleAsciiString(chr.getName());
        packet.writeShort(chr.getJob());

        return packet.getPacket();
    }

    public static final byte[] shopVisitorLeave(final byte slot) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(51); // 1.2.220+ Packet modification request.
        packet.write(slot);

        return packet.getPacket();
    }

    public static final byte[] updateHiredMerchant(final HiredMerchant shop) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.UPDATE_HIRED_MERCHANT.getValue());
        packet.writeInt(shop.getOwnerId());
        PacketProvider.addInteraction(packet, shop);

        return packet.getPacket();
    }

    public static final byte[] merchItem_Message(final byte op) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.MERCH_ITEM_MSG.getValue());
        packet.write(op);

        return packet.getPacket();
    }

    public static final byte[] merchItem_checkSPW(final boolean success) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.MERCH_ITEM_STORE.getValue());
        packet.write(0x27);
        packet.write(success ? 0 : 1);

        return packet.getPacket();
    }

    public static final byte[] merchItemStore(final byte op) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MERCH_ITEM_STORE.getValue());
        packet.write(op);
        switch (op) {
            case 0x29:
                packet.writeLong(0);
                packet.writeInt(0);
                break;
            default:
                packet.write(0);
                break;
        }
        return packet.getPacket();
    }

    public static final byte[] merchItemStore_ItemData(final MerchItemPackage pack) {
        final WritingPacket packet = new WritingPacket();

        packet.writeShort(SendPacketOpcode.MERCH_ITEM_STORE.getValue());
        packet.write(0x28);
        packet.writeInt(9030000); // Fredrick
        packet.writeInt(32272); // pack.getPackageid()
        packet.write0(5);
        packet.writeLong(pack.getMesos());
        packet.write0(3);
        packet.write(pack.getItems().size());
        for (final IItem item : pack.getItems()) {
            PacketProvider.addItemInfo(packet, item, true, true, null);
        }
        packet.writeShort(0);
        return packet.getPacket();
    }

    public static final byte[] cannotBackItem() {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.MERCH_ITEM_STORE.getValue());
        packet.write(0x28);
        packet.writeInt(9030000); // Fredrick
        packet.writeInt(999999999); // pack.getPackageid()
        packet.write(0);
        return packet.getPacket();
    }

    public static final byte[] merchantBlackListView(final List<String> blackList) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x38); // v192
        packet.writeShort(blackList.size());
        for (int i = 0; i < blackList.size(); i++) {
            if (blackList.get(i) != null) {
                packet.writeMapleAsciiString(blackList.get(i));
            }
        }
        return packet.getPacket();
    }

    public static final byte[] merchantVisitorView(List<String> visitor) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x37); // v192
        packet.writeShort(visitor.size());
        for (String visit : visitor) {
            packet.writeMapleAsciiString(visit);
            packet.writeInt(1);
        }
        return packet.getPacket();
    }

    public static final byte[] MaintanceCheck(boolean correct, int oid) { // show when closed the shop
        final WritingPacket packet = new WritingPacket();
        // 0x28 = All of your belongings are moved successfully.
        packet.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        packet.write(0x1E); // v192
        packet.write(19);
        packet.write(6);
        packet.write(correct ? 1 : 0);
        packet.writeInt(oid);
        packet.write(0);
        return packet.getPacket();
    }

    public static byte[] getMiniGame(MapleClient c, MapleMiniGame minigame) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(20);
        mplew.write(minigame.getGameType());
        mplew.write(minigame.getMaxSize());
        mplew.writeShort(minigame.getVisitorSlot(c.getPlayer()));
        PacketProvider.addPlayerLooks(mplew, minigame.getMCOwner(), false, false);
        mplew.writeMapleAsciiString(minigame.getOwnerName());
        mplew.writeShort(minigame.getMCOwner().getJob());
        for (Pair<Byte, MapleCharacter> visitorz : minigame.getVisitors()) {
            mplew.write(visitorz.getLeft());
            PacketProvider.addPlayerLooks(mplew, visitorz.getRight(), false, false);
            mplew.writeMapleAsciiString(visitorz.getRight().getName());
            mplew.writeShort(visitorz.getRight().getJob());
        }
        mplew.write(-1);
        mplew.write(0);
        addGameInfo(mplew, minigame.getMCOwner(), minigame);
        for (Pair<Byte, MapleCharacter> visitorz : minigame.getVisitors()) {
            mplew.write(visitorz.getLeft());
            addGameInfo(mplew, visitorz.getRight(), minigame);
        }
        mplew.write(-1);
        mplew.writeMapleAsciiString(minigame.getDescription());
        mplew.writeShort(minigame.getPieceType());
        return mplew.getPacket();
    }

    public static byte[] getMiniGameReady(boolean ready) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(ready ? PlayerInteractionHandler.READY : PlayerInteractionHandler.UN_READY);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameInfoMsg(byte type, String name) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.CHAT);
        mplew.write(23);
        mplew.write(type);
        mplew.writeMapleAsciiString(name);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameStart(int loser) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.START);
        mplew.write(loser == 1 ? 0 : 1);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameSkip(int slot) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(0x62);
        // owner = 1 visitor = 0?
        mplew.write(slot);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameRequestTie() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.REQUEST_TIE);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameDenyTie() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.ANSWER_TIE);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameRequestRedo() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.REQUEST_REDO);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameDenyRedo() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.ANSWER_REDO);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameFull() {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.writeShort(5);
        mplew.write(2);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameMoveOmok(int move1, int move2, int move3) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.MOVE_OMOK);
        mplew.writeInt(move1);
        mplew.writeInt(move2);
        mplew.write(move3);
        return mplew.getPacket();
    }

    public static byte[] getMiniGameNewVisitor(MapleCharacter c, int slot, MapleMiniGame game) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.VISIT);
        mplew.write(slot);
        PacketProvider.addPlayerLooks(mplew, c, false, false);
        mplew.writeMapleAsciiString(c.getName());
        mplew.writeShort(c.getJob());
        addGameInfo(mplew, c, game);
        mplew.write0(100);
        return mplew.getPacket();
    }

    public static void addGameInfo(WritingPacket mplew, MapleCharacter chr, MapleMiniGame game) {
        mplew.writeInt(game.getGameType()); // start of visitor; unknown
        mplew.writeInt(game.getWins(chr));
        mplew.writeInt(game.getTies(chr));
        mplew.writeInt(game.getLosses(chr));
        mplew.writeInt(game.getScore(chr)); // points
    }

    public static byte[] getMatchCardStart(MapleMiniGame game, int loser) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(PlayerInteractionHandler.START);
        mplew.write(loser == 1 ? 0 : 1);
        int times = game.getPieceType() == 1 ? 20 : (game.getPieceType() == 2 ? 30 : 12);
        mplew.write(times);
        for (int i = 1; i <= times; i++) {
            mplew.writeInt(game.getCardId(i));
        }
        return mplew.getPacket();
    }

    public static byte[] getMatchCardSelect(int turn, int slot, int firstslot, int type) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(103);
        mplew.write(turn);
        mplew.write(slot);
        if (turn == 0) {
            mplew.write(firstslot);
            mplew.write(type);
        }
        return mplew.getPacket();
    }

    public static byte[] getMiniGameResult(MapleMiniGame game, int type, int x) {
        WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PLAYER_INTERACTION.getValue());
        mplew.write(97);
        mplew.write(type); // lose = 0, tie = 1, win = 2
        game.setPoints(x, type);
        if (type != 0) {
            game.setPoints(x == 1 ? 0 : 1, type == 2 ? 0 : 1);
        }
        if (type != 1) {
            if (type == 0) {
                mplew.write(x == 1 ? 0 : 1); // who did it?
            } else {
                mplew.write(x);
            }
        }
        addGameInfo(mplew, game.getMCOwner(), game);
        for (Pair<Byte, MapleCharacter> visitorz : game.getVisitors()) {
            addGameInfo(mplew, visitorz.right, game);
        }

        return mplew.getPacket();
    }

}
