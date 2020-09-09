package handlers.GlobalHandler.JobHandler;

import client.Character.MapleCharacter;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.MapleInventoryType;
import client.MapleClient;
import client.Stats.ClothesStats;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.SkillPackets.ZeroSkill;
import constants.GameConstants;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;

public class ZeroHandler {

    private static int type;
    private static int position;
    private static boolean ConfirmTag;

    public static void ZeroWeaponInfo(final ReadingMaple rh, final MapleClient c) {
        // NPCScriptManager.getInstance().start(c, 9900001, null);
    }
    
    public static void ZeroWeaponLevelUp(final ReadingMaple rh, final MapleClient c) {
        rh.skip(7);
        IEquip beta;
        beta = (IEquip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -10);
        IEquip alpha;
        int betatype = 11;
        int alphatype = 12;
        alpha = (IEquip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
        Equip nalphatype = (Equip) alpha;
        Equip nbetatype = (Equip) beta;
        nbetatype.setItemId(nbetatype.getItemId() + 1);
        nalphatype.setItemId(nalphatype.getItemId() + 1);
        if (nbetatype.getItemId() == 1562001) {
            nalphatype.setWatk((short) 100);

            nbetatype.setWatk((short) 102);
            nbetatype.setWdef((short) 80);
            nbetatype.setMdef((short) 35);
            nbetatype.addUpgradeSlots((byte) 7);
        } else if (nbetatype.getItemId() == 1562002) {
            nalphatype.addWatk((short) 3); // 103

            nbetatype.addWatk((short) 3); // 105
            nbetatype.addWdef((short) 10); // 90
            nbetatype.addMdef((short) 5); // 40
        } else if (nbetatype.getItemId() == 1562003) {
            nalphatype.addWatk((short) 2); // 105

            nbetatype.addWatk((short) 2); // 107
            nbetatype.addWdef((short) 10); // 100
            nbetatype.addMdef((short) 5); // 45
        } else if (nbetatype.getItemId() == 1562004) {
            nalphatype.addWatk((short) 7); // 112

            nbetatype.addWatk((short) 7); // 114
            nbetatype.addWdef((short) 10); // 110
            nbetatype.addMdef((short) 5); // 50
        } else if (nbetatype.getItemId() == 1562005) {
            nalphatype.addStr((short) 8);
            nalphatype.addDex((short) 4);
            nalphatype.addWatk((short) 5); // 117
            nalphatype.addAcc((short) 50); // 50
            nalphatype.addUpgradeSlots((byte) 1);

            nbetatype.addStr((short) 8);
            nbetatype.addDex((short) 4);
            nbetatype.addWatk((short) 7); // 121
            nbetatype.addWdef((short) 10); // 120
            nbetatype.addMdef((short) 5); // 55
            nbetatype.addAcc((short) 50); // 50
            nbetatype.addUpgradeSlots((byte) 1);
        } else if (nbetatype.getItemId() == 1562006) {
            nalphatype.addStr((short) 27); // 35
            nalphatype.addDex((short) 16); // 20
            nalphatype.addWatk((short) 18); // 135
            nalphatype.addAcc((short) 50); // 100

            nbetatype.addStr((short) 27); // 35
            nbetatype.addDex((short) 16); // 20
            nbetatype.addWatk((short) 18); // 139
            nbetatype.addWdef((short) 10); // 130
            nbetatype.addMdef((short) 5); // 60
            nbetatype.addAcc((short) 50); // 100
        } else if (nbetatype.getItemId() == 1562007) {
            nalphatype.addStr((short) 5); // 40
            nalphatype.addDex((short) 20); // 40
            nalphatype.addWatk((short) 34); // 169
            nalphatype.addAcc((short) 20); // 120
            nalphatype.addBossDamage((byte) 30); // 30
            nalphatype.addIgnoreWdef((short) 10); // 10

            nbetatype.addStr((short) 5); // 40
            nbetatype.addDex((short) 20); // 40
            nbetatype.addWatk((short) 34); // 174
            nbetatype.addWdef((short) 20); // 150
            nbetatype.addMdef((short) 10); // 70
            nbetatype.addAcc((short) 20); // 120
            nbetatype.addBossDamage((byte) 30); // 30
            nbetatype.addIgnoreWdef((short) 10); // 10
        }
    }

    public static void ZeroScroll(final ReadingMaple rh, final MapleClient c) {
        int s_type = rh.readInt();
        int pos = rh.readInt();
        
        c.getPlayer().zeroScrollPosition = pos;
        
        rh.skip(8);
        
        int s_pos = rh.readInt();
        c.send(ZeroSkill.Scroll(s_pos));
    }

    public static void ZeroScrollStart(ReadingMaple rh, MapleCharacter chr, MapleClient c) {
        c.send(ZeroSkill.ScrollStart());
        
        if (chr.zeroScrollPosition != -1) {
            InventoryHandler.UseUpgradeScroll((byte) chr.zeroScrollPosition, (byte) -10, c, chr);
            InventoryHandler.UseUpgradeScroll((byte) chr.zeroScrollPosition, (byte) -11, c, chr);
            chr.zeroScrollPosition = -1;
        }
    }

    public static void ZeroChat(final ReadingMaple rh, final MapleClient c, final String txt) {
        c.send(ZeroSkill.NPCTalk(txt));
    }

    public static void ZeroOpen(final ReadingMaple rh, final MapleClient c) {
        int type = rh.readInt();
        byte s_type = rh.readByte();
        if (s_type == 0) {
            c.send(ZeroSkill.Open(type));
        }
    }

    public static void ZeroTag(final ReadingMaple rh, final MapleClient c) {
        MapleCharacter player = c.getPlayer();
        final int RealTimeTF = rh.readInt();
        final int ChangeTF = rh.readInt();

        if (player.getGender() == 0) {
            player.setGender((byte) 1);
            player.setSecondGender((byte) 0);
        } else {
            player.setGender((byte) 0);
            player.setSecondGender((byte) 1);
        }
        player.send(ZeroSkill.ZeroTag(player, (byte) player.getGender()));
        player.getMap().broadcastMessage(player, ZeroSkill.MultiTag(player), player.getPosition());
    }

    public static void ZeroAssistRemove(final MapleClient c) {
        c.getPlayer().getMap().broadcastMessage(c.getPlayer(), ZeroSkill.OnZeroLastAssistState(c.getPlayer()),
                c.getPlayer().getPosition());
    }

    public static void ZeroClothes(int kind, int check, MapleClient c) {
        int value = ClothesStats.getValueByOrder(kind), bc = c.getPlayer().getBetaClothes();
        if (check == 1 && (bc & value) == 0) {
            c.getPlayer().pBetaClothes(value);
        } else if (check == 0 && (bc & value) != 0) {
            c.getPlayer().mBetaClothes(value);
        } else {
            // System.out.println("제로 공통 옷 처리 오류" + check + " " + bc + " " + value);
        }
        c.send(ZeroSkill.Clothes(c.getPlayer().getBetaClothes()));
        c.send(ZeroSkill.Reaction());
    }

    public static void ZeroShockWave(ReadingMaple rh, MapleClient c) {
        /*
		 * int skillid = rh.readInt(); if (skillid == 101000101) { int v1 =
		 * rh.readInt(); rh.skip(4); byte v2 = rh.readByte(); int v3 = rh.readInt();
		 * Point v4 = rh.readPos(); rh.skip(1);
		 * c.getSession().writeAndFlush(ZeroSkill.shockWave(skillid, 101000102, v1, v2,
		 * v3, v4)); }
         */
        rh.skip(5);
        byte v1 = rh.readByte();
        if (GameConstants.isZero(c.getPlayer().getJob())) {
            c.getSession().writeAndFlush(ZeroSkill.shockWave(1, 101000101, 101000102, 56));
        } else if (GameConstants.isKadena(c.getPlayer().getJob())) {
            // c.getSession().writeAndFlush(MainPacketCreator.KadenaMove());
        }
    }
}
