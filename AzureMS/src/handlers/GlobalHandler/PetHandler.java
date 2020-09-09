package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventoryType;
import java.util.List;

import client.MapleClient;
import client.ItemInventory.PetsMounts.MaplePet;
import client.ItemInventory.PetsMounts.PetCommand;
import client.ItemInventory.PetsMounts.PetDataFactory;
import connections.Packets.MainPacketCreator;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.PetPacket;
import constants.GameConstants;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.Movement.LifeMovementFragment;
import tools.RandomStream.Randomizer;

public class PetHandler {

    public static void SpawnPet(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        rh.skip(4);
        byte slot = rh.readByte();
        chr.spawnPet(slot, slot > 0);

    }

    public static void Pet_AutoPotion(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        rh.skip(5);
        byte slot = rh.readByte();
        IItem toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);

        if (!chr.isAlive() || toUse == null || toUse.getQuantity() < 1) {
            c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
            return;
        }
        InventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short) 1, false);
        ItemInformation.getInstance().getItemEffect(toUse.getItemId()).applyTo(chr);
    }

    public static void PetChat(ReadingMaple slea, MapleCharacter chr) {
        int petid = slea.readInt();
        slea.skip(4);
        int command = slea.readShort();
        String text = slea.readMapleAsciiString();
        chr.getMap().broadcastMessage(chr, PetPacket.petChat(chr.getId(), command, text, (byte) petid), true);
    }

    public static void PetCommand(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        int petIndex = rh.readInt();
        if (petIndex == -1) {
            return;
        }
        MaplePet pet = chr.getPet(petIndex);
        rh.skip(1);
        byte command = rh.readByte();
        PetCommand petCommand = PetDataFactory.getPetCommand(pet.getPetItemId(), (int) command);

        boolean success = false;
        if (Randomizer.nextInt(99) <= petCommand.getProbability()) {
            success = true;
            if (pet.getCloseness() < 30000) {
                int newCloseness = pet.getCloseness() + petCommand.getIncrease();
                if (newCloseness > 30000) {
                    newCloseness = 30000;
                }
                pet.setCloseness(newCloseness);
                if (newCloseness >= GameConstants.getClosenessNeededForLevel(pet.getLevel() + 1)) {
                    pet.setLevel(pet.getLevel() + 1);
                    c.getSession().writeAndFlush(PetPacket.showOwnPetLevelUp((byte) petIndex));
                    // chr.getMap().broadcastMessage(PetPacket.showPetLevelUp(chr, (byte)
                    // petIndex));
                }
                c.getSession()
                        .writeAndFlush(PetPacket.updatePet(c.getPlayer(), pet, false, c.getPlayer().getPetLoot()));
            }
        }
        chr.getMap().broadcastMessage(chr, PetPacket.commandResponse(chr.getId(), command, (byte) petIndex, success),
                true);
    }

    public static void PetFood(ReadingMaple rh, MapleClient c, MapleCharacter chr) {
        int previousFullness = 100;

        for (MaplePet pet : chr.getPets()) {
            if (pet == null) {
                continue;
            }
            if (pet.getFullness() < previousFullness) {
                previousFullness = pet.getFullness();
                rh.skip(6);
                int itemId = rh.readInt();
                boolean gainCloseness = false;

                if (Randomizer.nextInt(99) <= 50) {
                    gainCloseness = true;
                }
                if (pet.getFullness() < 100) {
                    int newFullness = pet.getFullness() + 30;
                    if (newFullness > 100) {
                        newFullness = 100;
                    }
                    pet.setFullness(newFullness);
                    int index = chr.getPetIndex(pet);

                    if (gainCloseness && pet.getCloseness() < 30000) {
                        int newCloseness = pet.getCloseness() + 1;
                        if (newCloseness > 30000) {
                            newCloseness = 30000;
                        }
                        pet.setCloseness(newCloseness);
                        if (newCloseness >= GameConstants.getClosenessNeededForLevel(pet.getLevel() + 1)) {
                            pet.setLevel(pet.getLevel() + 1);

                            c.getSession().writeAndFlush(PetPacket.showOwnPetLevelUp((byte) index));
                            // chr.getMap().broadcastMessage(PetPacket.showPetLevelUp(chr, (byte) index));
                        }
                    }
                    c.getSession().writeAndFlush(PetPacket.updatePet(chr, pet, false, chr.getPetLoot()));
                    chr.getMap().broadcastMessage(c.getPlayer(),
                            PetPacket.commandResponse(chr.getId(), (byte) 1, (byte) index, true), true);
                } else {
                    if (gainCloseness) {
                        int newCloseness = pet.getCloseness() - 1;
                        if (newCloseness < 0) {
                            newCloseness = 0;
                        }
                        pet.setCloseness(newCloseness);
                        if (newCloseness < GameConstants.getClosenessNeededForLevel(pet.getLevel())) {
                            pet.setLevel(pet.getLevel() - 1);
                        }
                    }
                    c.getSession().writeAndFlush(PetPacket.updatePet(chr, pet, false, chr.getPetLoot()));
                    chr.getMap().broadcastMessage(chr,
                            PetPacket.commandResponse(chr.getId(), (byte) 1, (byte) (chr.getPetIndex(pet) + 1), false),
                            true);
                }
                InventoryManipulator.removeById(c, MapleInventoryType.USE, itemId, 1, true, false);
                return;
            }
        }
        c.getSession().writeAndFlush(MainPacketCreator.resetActions(c.getPlayer()));
    }

    public static void MovePet(ReadingMaple rh, MapleCharacter chr) {
        long slot = rh.readInt();
        rh.skip(13); // Start POS
        String originalrh = rh.toString();
        List<LifeMovementFragment> res = MovementParse.parseMovement(rh);
        if (chr.getPet(slot) != null) {
            if (res != null && res.size() != 0) { // map crash hack
                int petId = chr.getPet(slot).getUniqueId();
                if (slot == -1) {
                    return;
                }
                chr.getPet(slot).updatePosition(res);
                chr.getMap().broadcastMessage(chr, PetPacket.movePet(chr.getId(), petId, (byte) slot, res), false);
            } else {
                String t = "Pet Move packet : " + originalrh;
            }
        }
    }

    public static void RegisterPetBuff(ReadingMaple slea, MapleCharacter chr) {
        int petId = slea.readInt();
        int skillId = slea.readInt();
        int mode = slea.readByte();
        MaplePet pet = chr.getPet(petId);

        if (pet == null) {
            chr.dropMessage(1, "Failed to find pet! Please report to the management.");
            chr.ea();
            return;
        }

        if (mode == 0) { // register
            pet.setBuffSkillId(skillId);
        } else { // remove
            pet.setBuffSkillId(0);
        }
        chr.getClient().send(PetPacket.updatePet(chr, pet, false, chr.getPetLoot()));
    }
}
