package connections.Packets;

import java.util.List;

import client.Character.MapleCharacter;
import client.ItemInventory.PetsMounts.MaplePet;
import client.Stats.PlayerStatList;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.PacketUtility.WritingPacket;
import server.Movement.LifeMovementFragment;
import tools.HexTool;

public class PetPacket {

    public static final byte[] updatePet(final MapleCharacter player, final MaplePet pet, final boolean unequip,
            final boolean petLoot) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.MODIFY_INVENTORY_ITEM.getValue());
        mplew.write(0);
        mplew.write(2);
        mplew.write(0);
        
        mplew.write(3);
        mplew.write(5);
        mplew.writeShort(pet.getInventoryPosition());
        mplew.write(0);
        
        mplew.write(0);
        mplew.write(5);
        mplew.writeShort(pet.getInventoryPosition());
        mplew.write(3);
        mplew.writeInt(pet.getPetItemId());
        PacketProvider.addPetItemInfo(player, mplew, pet, unequip, petLoot);
        mplew.write(0);
        mplew.write(HexTool.getByteArrayFromHexString("02 01 8E FF 84 FF 00 13")); // 1.2.316
        return mplew.getPacket();
    }

    public static final byte[] showPet(final MapleCharacter chr, final MaplePet pet, final boolean remove,
            final boolean hunger, final boolean cloye) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.SPAWN_PET.getValue());
        mplew.writeInt(chr.getId());
        mplew.writeInt(chr.getPetIndex(pet));
        if (remove) {
            mplew.writeShort(hunger ? 0x100 : 0);
        } else {
            mplew.write(1);
            mplew.write(1); // Set Chloe to not float
            mplew.writeInt(pet.getPetItemId());
            mplew.writeMapleAsciiString(pet.getName());
            mplew.writeLong(pet.getUniqueId());
            mplew.writeShort(pet.getPos().x);
            mplew.writeShort(pet.getPos().y - 20);
            mplew.write(pet.getStance());
            mplew.writeShort(pet.getFh());
            mplew.writeInt(0xFFFFFFFF); // Pet Color, RGB.
            mplew.writeShort(0xFFFF);
            mplew.writeInt(100);
        }
        return mplew.getPacket();
    }

    public static final byte[] movePet(final int cid, final int pid, final byte slot,
            final List<LifeMovementFragment> moves) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.MOVE_PET.getValue());
        mplew.writeInt(cid);
        mplew.writeLong(slot);
        mplew.writeLong(pid);
        PacketProvider.serializeMovementList(mplew, moves);

        return mplew.getPacket();
    }

    public static final byte[] petChat(final int cid, final int un, final String text, final byte slot) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PET_CHAT.getValue());
        mplew.writeInt(cid);
        mplew.writeInt(slot);
        mplew.writeMapleAsciiString(text);

        return mplew.getPacket();
    }

    public static final byte[] commandResponse(final int cid, final byte command, final byte slot,
            final boolean success) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.PET_COMMAND.getValue());
        mplew.writeInt(cid);
        mplew.writeInt(slot);
        mplew.write(command + 1);
        mplew.write(command);
        mplew.writeInt(0); // 1.2.251+

        return mplew.getPacket();
    }

    public static final byte[] showOwnPetLevelUp(final byte index) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.SHOW_ITEM_GAIN_INCHAT.getValue());
        mplew.write(10);
        mplew.write(0);
        mplew.writeInt(index);

        return mplew.getPacket();
    }

    public static final byte[] showPetLevelUp(final MapleCharacter chr, final byte index) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.SHOW_FOREIGN_EFFECT.getValue());
        mplew.writeInt(chr.getId());
        mplew.write(10);
        mplew.write(0);
        mplew.writeInt(index);

        return mplew.getPacket();
    }

    public static final byte[] emptyStatUpdate() {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        mplew.write(1);
        mplew.write(0);
        mplew.writeLong(0);
        mplew.writeLong(0);

        return mplew.getPacket();
    }

    public static final byte[] petStatUpdate(final MapleCharacter chr) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        mplew.write(0);
        mplew.write(0);
        mplew.writeLong(PlayerStatList.PET.getValue());

        byte count = 0;
        for (final MaplePet pet : chr.getPets()) {
            mplew.writeLong(pet.getUniqueId());
            count++;
        }
        while (count < 3) {
            mplew.write0(8);
            count++;
        }
        mplew.write(0);
        mplew.writeShort(0);

        return mplew.getPacket();
    }

    public static final byte[] weirdStatUpdate(final MaplePet pet) {
        final WritingPacket mplew = new WritingPacket();
        mplew.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        mplew.write(0);
        mplew.write(0);
        mplew.write(8);
        mplew.write(0);
        mplew.write(0x18);
        mplew.write(0);
        mplew.writeInt(pet.getUniqueId());
        mplew.writeLong(0);
        mplew.writeLong(0);
        mplew.writeInt(0);
        mplew.write(1);

        return mplew.getPacket();
    }

    public static final byte[] updatePetLootStatus(int status) {
        final WritingPacket packet = new WritingPacket();
        packet.writeShort(SendPacketOpcode.PET_LOOT_STATUS.getValue());
        packet.write(status);
        packet.write(1);

        return packet.getPacket();
    }
}
