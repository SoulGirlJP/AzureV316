package connections.Packets;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.ItemInventory.PetsMounts.MaplePet;
import client.MapleQuestStatus;
import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.Skills.ISkill;
import client.Skills.InnerSkillValueHolder;
import client.Skills.LinkSkill;
import client.Skills.SkillEntry;
import client.Skills.SkillFactory;
import client.Stats.BuffStats;
import client.Stats.EquipStats;
import client.Stats.GlobalBuffStat;
import constants.GameConstants;
import constants.ServerConstants;
import launcher.Utility.MapleHolders.MapleCoolDownValueHolder;
import connections.Packets.PacketUtility.WritingPacket;
import server.Items.ItemInformation;
import server.Items.MapleRing;
import server.Movement.LifeMovementFragment;
import server.Quests.MapleQuest;
import server.Shops.AbstractPlayerStore;
import server.Shops.IMapleCharacterShop;
import server.Shops.MapleShopItem;
import tools.BitTools;
import tools.FileoutputUtil;
import tools.HexTool;
import tools.KoreanDateUtil;
import tools.Triple;
import tools.RandomStream.Randomizer;

public class PacketProvider {

    public final static long FT_UT_OFFSET = 116445060000000000L;
    public final static long MAX_TIME = 150842304000000000L;
    public final static long ZERO_TIME = 94354848000000000L;
    public final static long ZERO_TIME_REVERSE = -153052018564450501L;
    public final static long PERMANENT = 150841440000000000L;

    public static final long getKoreanTimestamp(final long realTimestamp) {
        return getTime(realTimestamp);
    }

    public static final long getTime(long realTimestamp) {
        if (realTimestamp == -1) {
            return MAX_TIME;
        } else if (realTimestamp == -2) {
            return ZERO_TIME;
        } else if (realTimestamp == -3) {
            return PERMANENT;
        } else if (realTimestamp == -4) {
            return ZERO_TIME_REVERSE;
        }
        return ((realTimestamp * 10000) + FT_UT_OFFSET);
    }

    public static void addZeroQuestInfo(final WritingPacket w) {
        w.write(HexTool.getByteArrayFromHexString(
                "01 18 01 5C 7C 00 00 09 00 31 30 30 30 30 30 30 30 30 00 00 00 00 09 00 31 30 31 30 30 30 30 30 30 A5 81 00 00 00 00 A6 81 00 00 00 00 40 83 00 00 01 00 34 41 83 00 00 01 00 34 42 83 00 00 01 00 32 F1 81 00 00 00 00 F3 81 00 00 00 00 40 84 00 00 01 00 30 41 84 00 00 01 00 30 42 84 00 00 01 00 30 43 84 00 00 03 00 32 36 36 08 84 00 00 02 00 39 39 DB 81 00 00 00 00 09 84 00 00 01 00 30 0A 84 00 00 01 00 30 0B 84 00 00 01 00 30 0C 84 00 00 01 00 30 0D 84 00 00 01 00 30 54 83 00 00 01 00 32 0E 84 00 00 01 00 30 55 83 00 00 01 00 30 0F 84 00 00 01 00 30 56 83 00 00 01 00 32 10 84 00 00 01 00 30 57 83 00 00 01 00 30 11 84 00 00 01 00 30 58 83 00 00 01 00 30 12 84 00 00 01 00 30 59 83 00 00 01 00 30 13 84 00 00 03 00 32 36 36 5A 83 00 00 01 00 30 14 84 00 00 01 00 30 5B 83 00 00 01 00 30 15 84 00 00 01 00 30 5C 83 00 00 01 00 30 3A 84 00 00 01 00 30 3B 84 00 00 01 00 30 3C 84 00 00 01 00 30 3D 84 00 00 01 00 30 3E 84 00 00 01 00 30 3F 84 00 00 03 00 31 30 30 21 0B 00 00 01 00 31 E6 0A 00 00 01 00 31 94 0A 00 00 01 00 31 0E 51 00 00 06 00 C5 BD BB F6 C1 DF 9B 0F 00 00 01 00 30 18 51 00 00 01 00 30 A0 99 00 00 00 00 A1 99 00 00 00 00 A2 99 00 00 00 00 C1 99 00 00 00 00 84 99 00 00 00 00 A3 99 00 00 00 00 C2 99 00 00 00 00 85 99 00 00 00 00 A4 99 00 00 00 00 A5 99 00 00 00 00 86 99 00 00 00 00 87 99 00 00 00 00 A6 99 00 00 00 00 88 99 00 00 00 00 A7 99 00 00 00 00 89 99 00 00 00 00 A8 99 00 00 00 00 8A 99 00 00 00 00 A9 99 00 00 00 00 8B 99 00 00 00 00 AA 99 00 00 00 00 8C 99 00 00 00 00 AB 99 00 00 00 00 8D 99 00 00 00 00 AC 99 00 00 00 00 8E 99 00 00 00 00 AD 99 00 00 00 00 8F 99 00 00 00 00 AE 99 00 00 00 00 90 99 00 00 00 00 AF 99 00 00 00 00 91 99 00 00 00 00 B0 99 00 00 00 00 92 99 00 00 00 00 B1 99 00 00 00 00 93 99 00 00 00 00 B2 99 00 00 00 00 94 99 00 00 00 00 B3 99 00 00 00 00 95 99 00 00 00 00 B4 99 00 00 00 00 96 99 00 00 00 00 B5 99 00 00 00 00 97 99 00 00 00 00 B6 99 00 00 00 00 98 99 00 00 00 00 B7 99 00 00 06 00 30 31 39 30 30 30 6B 1B 00 00 01 00 30 8A 1B 00 00 09 00 33 32 32 30 30 30 30 30 30 99 99 00 00 00 00 B8 99 00 00 00 00 82 9C 00 00 01 00 32 9A 99 00 00 00 00 9B 99 00 00 00 00 BA 99 00 00 00 00 45 9C 00 00 01 00 31 9C 99 00 00 00 00 84 9C 00 00 01 00 31 9D 99 00 00 00 00 47 9C 00 00 01 00 31 9E 99 00 00 00 00 4A 1C 00 00 01 00 30 9F 99 00 00 03 00 30 31 36 4B 1C 00 00 08 00 31 34 2F 30 35 2F 30 34 2D 1C 00 00 01 00 30 4B 9C 00 00 01 00 31 E0 9D 00 00 01 00 31 30 1C 00 00 01 00 30 58 1B 00 00 01 00 73 31 1C 00 00 08 00 31 34 2F 30 35 2F 30 34 C5 1D 00 00 00 00 40 9E 00 00 01 00 31 41 9E 00 00 01 00 31 5B 1B 00 00 08 00 31 35 2F 30 37 2F 30 34 72 1C 00 00 01 00 30 5C 1B 00 00 01 00 32 42 9E 00 00 01 00 35 82 1E 00 00 06 00 30 30 30 30 30 30 A0 9E 00 00 01 00 31 44 9E 00 00 01 00 30 A1 9E 00 00 01 00 31 A2 9E 00 00 01 00 31 B3 9C 00 00 01 00 31 A3 9E 00 00 01 00 31 B4 9C 00 00 01 00 31 A4 9E 00 00 01 00 31 B5 9C 00 00 01 00 31 7A 1C 00 00 07 00 33 37 30 30 31 33 35 B6 9C 00 00 01 00 31 13 9D 00 00 01 00 31 A6 9E 00 00 01 00 31 03 9F 00 00 01 00 31 B7 9C 00 00 01 00 31 14 9D 00 00 01 00 31 A7 9E 00 00 01 00 31 04 9F 00 00 01 00 31 7C 1C 00 00 07 00 33 31 31 30 30 30 30 15 9D 00 00 01 00 31 A8 9E 00 00 01 00 31 05 9F 00 00 01 00 31 62 9F 00 00 01 00 31 16 9D 00 00 01 00 31 A9 9E 00 00 01 00 31 06 9F 00 00 01 00 31 63 9F 00 00 01 00 31 17 9D 00 00 01 00 31 07 9F 00 00 01 00 31 64 9F 00 00 01 00 32 18 9D 00 00 01 00 31 08 9F 00 00 01 00 31 65 9F 00 00 01 00 30 19 9D 00 00 01 00 31 09 9F 00 00 01 00 31 77 9D 00 00 01 00 31 0A 9F 00 00 01 00 31 78 9D 00 00 01 00 31 0B 9F 00 00 01 00 31 79 9D 00 00 01 00 31 0C 9F 00 00 01 00 31 7A 9D 00 00 01 00 31 7B 9D 00 00 01 00 31 7C 9D 00 00 01 00 30 7D 9D 00 00 01 00 31 DB 9D 00 00 01 00 31 DD 9D 00 00 01 00 31 DE 9D 00 00 01 00 31 3B 9E 00 00 01 00 31 DF 9D 00 00 01 00 31 3C 9E 00 00 01 00 31 3D 9E 00 00 01 00 31 18 1F 00 00 01 00 30 3E 9E 00 00 01 00 31 19 1F 00 00 08 00 31 34 2F 30 35 2F 30 34 3F 9E 00 00 01 00 31 DC 1E 00 00 00 00 DD 1E 00 00 01 00 32 1B 1F 00 00 01 00 30 66 A1 00 00 01 00 34 1C 1F 00 00 08 00 31 34 2F 30 35 2F 30 34 98 A0 00 00 06 00 30 30 39 30 30 39 63 25 00 00 01 00 30 B1 A3 00 00 00 00 B3 A3 00 00 01 00 30 B8 A3 00 00 01 00 30 6D 65 00 00 01 00 30 76 A4 00 00 01 00 30 40 67 00 00 00 00 41 67 00 00 00 00 23 67 00 00 00 00 42 67 00 00 00 00 A0 67 00 00 00 00 43 67 00 00 00 00 A1 67 00 00 00 00 44 67 00 00 00 00 A2 67 00 00 00 00 45 67 00 00 00 00 A3 67 00 00 00 00 46 67 00 00 00 00 E2 67 00 00 00 00 A4 67 00 00 00 00 47 67 00 00 00 00 48 67 00 00 00 00 49 67 00 00 00 00 4A 67 00 00 00 00 9A 65 00 00 09 00 32 34 30 30 30 30 30 30 30 8D 67 00 00 00 00 8F 67 00 00 00 00 90 67 00 00 00 00 82 69 00 00 01 00 34 00 2A 00 00 00 00 83 69 00 00 01 00 35 93 67 00 00 00 00 20 2A 00 00 00 00 94 67 00 00 00 00 02 2A 00 00 00 00 95 67 00 00 00 00 38 67 00 00 00 00 96 67 00 00 00 00 88 69 00 00 01 00 31 99 67 00 00 00 00 A9 29 00 00 03 00 31 38 30 AA 29 00 00 00 00 3E 67 00 00 00 00 3F 67 00 00 00 00 9D 67 00 00 00 00 0A 2A 00 00 00 00 9E 67 00 00 00 00 EC 29 00 00 00 00 9F 67 00 00 00 00 0C 2A 00 00 00 00 EE 29 00 00 00 00 0E 2A 00 00 00 00 F0 29 00 00 00 00 10 2A 00 00 00 00 F2 29 00 00 00 00 12 2A 00 00 00 00 F4 29 00 00 00 00 14 2A 00 00 00 00 F6 29 00 00 00 00 16 2A 00 00 00 00 F8 29 00 00 00 00 18 2A 00 00 00 00 FA 29 00 00 00 00 1A 2A 00 00 00 00 FC 29 00 00 00 00 1C 2A 00 00 00 00 B5 AA 00 00 00 00 FE 29 00 00 00 00 1E 2A 00 00 00 00 64 2D 00 00 01 00 30 4D 71 00 00 00 00 4E 71 00 00 00 00 53 71 00 00 00 00 57 71 00 00 00 00 4C 75 00 00 09 00 31 30 30 30 30 30 30 30 30 4D 75 00 00 05 00 73 74 61 72 74 38 75 00 00 00 00 CD 36 00 00 09 00 33 32 37 30 30 30 30 30 30 17 37 00 00 09 00 31 30 30 30 30 30 30 30 30 21 7A 00 00 09 00 31 30 31 30 30 30 30 30 30 0E 7A 00 00 06 00 30 36 37 30 30 30 5F 38 00 00 00 00 2C 3B 00 00 00 00 D6 79 00 00 01 00 30 D7 79 00 00 01 00 30 65 7C 00 00 08 00 31 34 2F 30 37 2F 31 30 7D 79 00 00 00 00 66 7C 00 00 01 00 31 DB 79 00 00 01 00 30 DE 79 00 00 00 00 6B 7C 00 00 00 00 01 25 01 26 7F 00 00 00 5A 82 9F E8 8E CE 01 1D 83 00 00 00 D0 3C 03 47 3F D1 01 9A 0F 00 00 00 6A 7F 37 F2 85 CE 01 70 17 00 00 00 3A 2C 8F 32 E9 CE 01 59 98 00 00 00 60 BE 18 8B C5 D0 01 40 9C 00 00 00 AA 59 6C C3 83 CE 01 41 9C 00 00 00 36 E0 B3 C3 83 CE 01 89 1B 00 00 00 D2 29 28 E2 83 CE 01 42 9C 00 00 00 C2 66 FB C3 83 CE 01 43 9C 00 00 00 4E ED 42 C4 83 CE 01 44 9C 00 00 00 66 FA D1 C4 83 CE 01 A4 9C 00 00 00 5C 4C E6 E0 83 CE 01 A5 9C 00 00 00 46 A3 E0 E1 83 CE 01 53 1B 00 00 00 EE 96 A8 65 8D CE 01 A6 9C 00 00 00 4A 6B F3 E4 83 CE 01 A7 9C 00 00 00 7A 85 11 E6 83 CE 01 A8 9C 00 00 00 06 0C 59 E6 83 CE 01 A9 9C 00 00 00 2A 71 20 F0 83 CE 01 AA 9C 00 00 00 70 34 44 F0 83 CE 01 AB 9C 00 00 00 7E F0 05 FB 83 CE 01 08 9D 00 00 00 28 6B 67 A1 84 CE 01 AC 9C 00 00 00 7E F0 05 FB 83 CE 01 09 9D 00 00 00 6E 2E 8B A1 84 CE 01 AD 9C 00 00 00 24 E8 65 FD 83 CE 01 0A 9D 00 00 00 D4 8E 63 A8 84 CE 01 AE 9C 00 00 00 3C F5 F4 FD 83 CE 01 0B 9D 00 00 00 1A 52 87 A8 84 CE 01 AF 9C 00 00 00 56 66 0D 00 84 CE 01 0C 9D 00 00 00 F4 2B 18 AF 84 CE 01 72 9C 00 00 00 80 6B EA C6 83 CE 01 B0 9C 00 00 00 56 66 0D 00 84 CE 01 0D 9D 00 00 00 F6 8F A1 B0 84 CE 01 73 9C 00 00 00 82 CF 73 C8 83 CE 01 0E 9D 00 00 00 F8 F3 2A B2 84 CE 01 74 9C 00 00 00 C8 92 97 C8 83 CE 01 0F 9D 00 00 00 60 B8 8C BA 84 CE 01 6C 9D 00 00 00 9C 42 F0 53 85 CE 01 00 9F 00 00 00 E0 F0 8F 60 8C CE 01 75 9C 00 00 00 CA F6 20 CA 83 CE 01 10 9D 00 00 00 68 48 B2 C0 84 CE 01 6D 9D 00 00 00 28 C9 37 54 85 CE 01 67 1E 00 00 00 E4 58 FD C3 1F D1 01 76 9C 00 00 00 9C 40 8C CA 83 CE 01 11 9D 00 00 00 3A 92 1D C1 84 CE 01 6E 9D 00 00 00 B4 4F 7F 54 85 CE 01 01 9F 00 00 00 D8 A6 2D 7E 8C CE 01 77 9C 00 00 00 28 C7 D3 CA 83 CE 01 12 9D 00 00 00 80 55 41 C1 84 CE 01 6F 9D 00 00 00 FA 12 A3 54 85 CE 01 78 9C 00 00 00 E6 CB C2 CD 83 CE 01 70 9D 00 00 00 18 4C CE 59 85 CE 01 B0 1D 00 00 00 80 6F 12 0E C3 D0 01 60 9F 00 00 00 98 0F A6 82 8C CE 01 79 9C 00 00 00 72 52 0A CE 83 CE 01 71 9D 00 00 00 EE 5D 4C 5D 85 CE 01 61 9F 00 00 00 DE D2 C9 82 8C CE 01 7A 9C 00 00 00 B8 15 2E CE 83 CE 01 72 9D 00 00 00 34 21 70 5D 85 CE 01 7B 9C 00 00 00 DC 7A F5 D7 83 CE 01 73 9D 00 00 00 0A 33 EE 60 85 CE 01 D0 9D 00 00 00 DA 30 DD EE 85 CE 01 7C 9C 00 00 00 24 A2 A2 D9 83 CE 01 74 9D 00 00 00 A4 BB BA 8F 85 CE 01 D1 9D 00 00 00 F2 3D 6C EF 85 CE 01 7D 9C 00 00 00 6A 65 C6 D9 83 CE 01 75 9D 00 00 00 22 29 22 97 85 CE 01 D2 9D 00 00 00 42 3B 02 1B 86 CE 01 00 A0 00 00 00 74 5F A1 7D 88 CE 01 7E 9C 00 00 00 FA B3 20 DD 83 CE 01 76 9D 00 00 00 22 29 22 97 85 CE 01 D3 9D 00 00 00 CE C1 49 1B 86 CE 01 E2 9F 00 00 00 FA 12 A3 54 85 CE 01 01 A0 00 00 00 B2 F0 6F 2A 89 CE 01 7F 9C 00 00 00 86 3A 68 DD 83 CE 01 D4 9D 00 00 00 A8 9B DA 21 86 CE 01 C4 9F 00 00 00 66 FA D1 C4 83 CE 01 E3 9F 00 00 00 34 21 70 5D 85 CE 01 02 A0 00 00 00 DA 1D 4A 37 89 CE 01 D5 9D 00 00 00 EE 5E FE 21 86 CE 01 CF 1E 00 00 00 F4 68 6B 32 E9 CE 01 C5 9F 00 00 00 C8 92 97 C8 83 CE 01 E4 9F 00 00 00 0A 33 EE 60 85 CE 01 03 A0 00 00 00 E4 9E 31 4B 8A CE 01 D6 9D 00 00 00 0C 98 29 27 86 CE 01 C6 9F 00 00 00 28 C7 D3 CA 83 CE 01 E5 9F 00 00 00 A4 BB BA 8F 85 CE 01 04 A0 00 00 00 C6 09 9A 1C 8B CE 01 D7 9D 00 00 00 52 5B 4D 27 86 CE 01 34 9E 00 00 00 EE 01 E0 33 86 CE 01 C7 9F 00 00 00 B8 15 2E CE 83 CE 01 E6 9F 00 00 00 22 29 22 97 85 CE 01 D8 9D 00 00 00 A2 12 20 2F 86 CE 01 35 9E 00 00 00 06 0F 6F 34 86 CE 01 C8 9F 00 00 00 6A 65 C6 D9 83 CE 01 D9 9D 00 00 00 E8 D5 43 2F 86 CE 01 36 9E 00 00 00 92 95 B6 34 86 CE 01 B4 1E 00 00 00 4E 1F 95 19 7B D0 01 C9 9F 00 00 00 86 3A 68 DD 83 CE 01 37 9E 00 00 00 48 0B 32 F6 86 CE 01 D4 1E 00 00 00 CE 2F 0C 63 FE CE 01 1B 1E 00 00 00 C8 06 89 C2 4E D1 01 38 9E 00 00 00 88 8B 5E 27 87 CE 01 39 9E 00 00 00 EA 3B 31 BA 87 CE 01 0A A0 00 00 00 F6 98 E3 9A 8B CE 01 3A 9E 00 00 00 0C 3D 6F C2 87 CE 01 EC 9F 00 00 00 F2 3D 6C EF 85 CE 01 0B A0 00 00 00 60 C1 CE A4 8B CE 01 22 A1 00 00 00 74 5E EF B8 87 CE 01 60 A1 00 00 00 E4 9E 31 4B 8A CE 01 98 9E 00 00 00 74 5F A1 7D 88 CE 01 CE 9F 00 00 00 46 A3 E0 E1 83 CE 01 ED 9F 00 00 00 CE C1 49 1B 86 CE 01 0C A0 00 00 00 A8 D1 20 DC 8B CE 01 23 A1 00 00 00 16 76 2F 29 87 CE 01 99 9E 00 00 00 B2 F0 6F 2A 89 CE 01 CF 9F 00 00 00 06 0C 59 E6 83 CE 01 EE 9F 00 00 00 EE 5E FE 21 86 CE 01 0D A0 00 00 00 E0 F0 8F 60 8C CE 01 24 A1 00 00 00 8C 6B 7E B9 87 CE 01 9A 9E 00 00 00 DA 1D 4A 37 89 CE 01 D0 9F 00 00 00 7E F0 05 FB 83 CE 01 EF 9F 00 00 00 52 5B 4D 27 86 CE 01 0E A0 00 00 00 D8 A6 2D 7E 8C CE 01 25 A1 00 00 00 8C 6B 7E B9 87 CE 01 63 A1 00 00 00 6A 9D 70 1D 8B CE 01 C0 A1 00 00 00 54 B0 0B 84 8C CE 01 9B 9E 00 00 00 CC 91 A2 4A 8A CE 01 D1 9F 00 00 00 3C F5 F4 FD 83 CE 01 F0 9F 00 00 00 E8 D5 43 2F 86 CE 01 26 A1 00 00 00 5E B5 E9 B9 87 CE 01 64 A1 00 00 00 E0 7A B2 1E 8B CE 01 9C 9E 00 00 00 12 55 C6 4A 8A CE 01 D2 9F 00 00 00 56 66 0D 00 84 CE 01 27 A1 00 00 00 CE 4E 82 27 87 CE 01 65 A1 00 00 00 0C CD BD 1C 8B CE 01 9D 9E 00 00 00 E4 9E 31 4B 8A CE 01 28 A1 00 00 00 BA 09 06 2A 87 CE 01 9E 9E 00 00 00 3A 83 52 1C 8B CE 01 29 A1 00 00 00 4A 70 6D BC 87 CE 01 86 A1 00 00 00 54 69 96 9B 8B CE 01 9F 9E 00 00 00 C6 09 9A 1C 8B CE 01 FC 9E 00 00 00 52 05 0D 9A 8B CE 01 8F A0 00 00 00 DE 25 F4 C1 84 CE 01 2A A1 00 00 00 D0 B2 0B 29 87 CE 01 87 A1 00 00 00 E2 53 67 9D 8B CE 01 FD 9E 00 00 00 F6 98 E3 9A 8B CE 01 14 A0 00 00 00 DE D2 C9 82 8C CE 01 90 A0 00 00 00 C8 7C EE C2 84 CE 01 88 A1 00 00 00 54 69 96 9B 8B CE 01 FE 9E 00 00 00 60 C1 CE A4 8B CE 01 F6 9F 00 00 00 92 95 B6 34 86 CE 01 91 A0 00 00 00 82 B9 CA C2 84 CE 01 89 A1 00 00 00 FA 60 F6 9D 8B CE 01 FF 9E 00 00 00 A8 D1 20 DC 8B CE 01 D8 9F 00 00 00 6E 2E 8B A1 84 CE 01 F7 9F 00 00 00 48 0B 32 F6 86 CE 01 92 A0 00 00 00 24 E9 17 C2 84 CE 01 2D A1 00 00 00 A8 40 20 BD 87 CE 01 8A A1 00 00 00 CA 46 D8 9C 8B CE 01 D9 9F 00 00 00 1A 52 87 A8 84 CE 01 F8 9F 00 00 00 88 8B 5E 27 87 CE 01 DA 9F 00 00 00 F8 F3 2A B2 84 CE 01 F9 9F 00 00 00 EA 3B 31 BA 87 CE 01 2F A1 00 00 00 8E CF 07 BB 87 CE 01 DB 9F 00 00 00 60 B8 8C BA 84 CE 01 FA 9F 00 00 00 0C 3D 6F C2 87 CE 01 30 A1 00 00 00 D4 92 2B BB 87 CE 01 8D A1 00 00 00 E2 3C 0C D3 8B CE 01 DC 9F 00 00 00 80 55 41 C1 84 CE 01 8E A1 00 00 00 9E DD 71 D4 8B CE 01 97 A0 00 00 00 E0 89 7D C3 84 CE 01 8F A1 00 00 00 E2 3C 0C D3 8B CE 01 5A A0 00 00 00 3C F5 F4 FD 83 CE 01 90 A1 00 00 00 9E DD 71 D4 8B CE 01 9A A0 00 00 00 DE 25 F4 C1 84 CE 01 35 A1 00 00 00 8C 6B 7E B9 87 CE 01 54 A1 00 00 00 AE 58 77 45 8A CE 01 9B A0 00 00 00 98 62 D0 C1 84 CE 01 36 A1 00 00 00 8C 6B 7E B9 87 CE 01 55 A1 00 00 00 3A DF BE 45 8A CE 01 93 A1 00 00 00 9C 34 D7 73 8C CE 01 56 A1 00 00 00 AE 58 77 45 8A CE 01 94 A1 00 00 00 86 8B D1 74 8C CE 01 57 A1 00 00 00 98 AF 71 46 8A CE 01 95 A1 00 00 00 9C 34 D7 73 8C CE 01 5F A0 00 00 00 6A AB 89 FD 83 CE 01 58 A1 00 00 00 AE 58 77 45 8A CE 01 96 A1 00 00 00 70 E2 CB 75 8C CE 01 59 A1 00 00 00 3C 43 48 47 8A CE 01 C5 A3 00 00 00 6A C2 E4 C7 83 CE 01 5A A1 00 00 00 E0 D6 1E 48 8A CE 01 C6 A3 00 00 00 B0 85 08 C8 83 CE 01 5B A1 00 00 00 F8 E3 AD 48 8A CE 01 B8 A1 00 00 00 5A EF 3A 40 89 CE 01 9A A1 00 00 00 C4 61 B1 80 8C CE 01 B9 A1 00 00 00 5A EF 3A 40 89 CE 01 5D A1 00 00 00 E4 9E 31 4B 8A CE 01 BA A1 00 00 00 A0 B2 5E 40 89 CE 01 C9 A3 00 00 00 F6 48 2C C8 83 CE 01 5E A1 00 00 00 40 0B 5B 4A 8A CE 01 BC A1 00 00 00 5A EF 3A 40 89 CE 01 AC A3 00 00 00 4E 98 B5 AD 4E CF 01 BD A1 00 00 00 5A EF 3A 40 89 CE 01 BE A1 00 00 00 C6 09 9A 1C 8B CE 01 BF A1 00 00 00 D8 A6 2D 7E 8C CE 01 AF A3 00 00 00 5A EF 3A 40 89 CE 01 B0 A3 00 00 00 F6 DF 58 83 8C CE 01 B2 A3 00 00 00 A0 B2 5E 40 89 CE 01 67 65 00 00 00 02 94 89 3C D3 CE 01 B4 A3 00 00 00 86 3A 68 DD 83 CE 01 B5 A3 00 00 00 CC FD 8B DD 83 CE 01 6E 65 00 00 00 5A B6 53 63 FE CE 01 40 67 00 00 00 AE 25 A7 0D C3 D0 01 43 67 00 00 00 BC E8 A3 97 B6 D0 01 54 65 00 00 00 94 D2 24 55 B6 D0 01 2B 67 00 00 00 0C F2 31 C7 83 CE 01 2E 67 00 00 00 98 78 79 C7 83 CE 01 90 67 00 00 00 EE A8 E9 8C C5 D0 01 54 67 00 00 00 DA 73 8A C4 83 CE 01 35 67 00 00 00 98 78 79 C7 83 CE 01 A5 29 00 00 00 B0 48 A0 4D DE CF 01 79 67 00 00 00 BC D0 65 3C D3 CE 01 3B 67 00 00 00 12 C8 8D 3E 89 CE 01 A8 29 00 00 00 9A C9 E4 3C 04 CF 01 3E 67 00 00 00 62 E0 03 9A B6 D0 01 3F 67 00 00 00 AE 25 A7 0D C3 D0 01 CE AA 00 00 00 C8 06 89 C2 4E D1 01 1E 69 00 00 00 DA 73 8A C4 83 CE 01 BD 2B 00 00 00 94 B0 66 C4 83 CE 01 BE 2B 00 00 00 94 B0 66 C4 83 CE 01 79 2C 00 00 00 94 B0 66 C4 83 CE 01 BF 2B 00 00 00 0E 19 53 4E DE CF 01 6A 30 00 00 00 BA 79 B7 CF 83 CE 01 6B 30 00 00 00 BA 79 B7 CF 83 CE 01 6C 30 00 00 00 00 3D DB CF 83 CE 01 C0 36 00 00 00 F0 B3 BC 7E 8C CE 01 C1 36 00 00 00 94 B0 66 C4 83 CE 01 A3 36 00 00 00 DA 73 8A C4 83 CE 01 20 37 00 00 00 E2 05 14 54 85 CE 01 A4 36 00 00 00 20 F4 00 EF 85 CE 01 30 75 00 00 00 7C 81 79 67 8D CE 01 21 37 00 00 00 20 F4 00 EF 85 CE 01 22 37 00 00 00 BA 1F AF 2F 86 CE 01 A6 36 00 00 00 20 F4 00 EF 85 CE 01 32 75 00 00 00 C2 44 9D 67 8D CE 01 23 37 00 00 00 B0 2E 16 76 88 CE 01 A7 36 00 00 00 BA 79 B7 CF 83 CE 01 33 75 00 00 00 C2 44 9D 67 8D CE 01 A0 37 00 00 00 02 94 89 3C D3 CE 01 24 37 00 00 00 3E C0 90 9C 8B CE 01 A8 36 00 00 00 72 E2 58 28 87 CE 01 34 75 00 00 00 08 08 C1 67 8D CE 01 25 37 00 00 00 24 96 ED 82 8C CE 01 C9 36 00 00 00 42 E2 AB 3F 89 CE 01 AA 36 00 00 00 6A FF 08 E3 8E CE 01 35 75 00 00 00 08 08 C1 67 8D CE 01 36 75 00 00 00 94 8E 08 68 8D CE 01 AC 36 00 00 00 B6 93 0E 83 90 CE 01 37 75 00 00 00 DA 51 2C 68 8D CE 01 28 37 00 00 00 6A FF 08 E3 8E CE 01 0F 37 00 00 00 B0 C2 2C E3 8E CE 01 14 37 00 00 00 88 36 D0 5E 8D CE 01 80 79 00 00 00 A0 79 77 63 FE CE 01 16 37 00 00 00 20 37 AE C4 83 CE 01 B9 36 00 00 00 22 29 22 97 85 CE 01 81 79 00 00 00 A0 79 77 63 FE CE 01 06 39 00 00 00 76 07 18 04 58 D0 01 07 39 00 00 00 76 07 18 04 58 D0 01 F8 36 00 00 00 00 3D DB CF 83 CE 01 82 79 00 00 00 E6 3C 9B 63 FE CE 01 F9 36 00 00 00 1E B1 C6 66 8D CE 01 09 39 00 00 00 76 07 18 04 58 D0 01 0D 39 00 00 00 76 07 18 04 58 D0 01 0E 39 00 00 00 76 07 18 04 58 D0 01 1E 37 00 00 00 F8 50 49 A0 84 CE 01 62 7A 00 00 00 A2 14 6F C1 46 D0 01 0F 39 00 00 00 76 07 18 04 58 D0 01 1F 37 00 00 00 F8 50 49 A0 84 CE 01 08 7A 00 00 00 20 15 50 68 8D CE 01 30 39 00 00 00 D0 CA 03 C1 46 D0 01 09 7A 00 00 00 F6 85 50 E3 8E CE 01 68 3A 00 00 00 AE 25 A7 0D C3 D0 01 0A 7A 00 00 00 3C 49 74 E3 8E CE 01 69 3A 00 00 00 AE 25 A7 0D C3 D0 01 0B 7A 00 00 00 82 0C 98 E3 8E CE 01 8F 79 00 00 00 FE 49 2A 64 FE CE 01 0C 7A 00 00 00 82 0C 98 E3 8E CE 01 0D 7A 00 00 00 82 0C 98 E3 8E CE 01 C7 7B 00 00 00 94 B0 66 C4 83 CE 01 7C 79 00 00 00 14 64 C1 83 90 CE 01 7F 79 00 00 00 A0 79 77 63 FE CE 01 12 7D 00 00 00 6A FF 08 E3 8E CE 01 13 7D 00 00 00 BC D0 65 3C D3 CE 01"));
    }

    public static void addStartedQuestInfo(final WritingPacket w, final MapleCharacter chr) {
        final List<MapleQuestStatus> started = chr.getStartedQuests();
        final String[][] zeroQuestInfo = {{"31836", "100000000"}, {"0", "101000000"}, {"33189", ""},
        {"33190", ""}, {"33600", "4"}, {"33601", "4"}, {"33602", "2"}, {"33265", ""}, {"33267", ""},
        {"33856", "0"}, {"33857", "0"}, {"33858", "0"}, {"33859", "266"}, {"33800", "99"},
        {"33243", ""}, {"33801", "0"}, {"33802", "0"}, {"33803", "0"}, {"33804", "0"},
        {"33805", "0"}, {"33620", "2"}, {"33806", "0"}, {"33621", "0"}, {"33807", "0"},
        {"33622", "2"}, {"33808", "0"}, {"33623", "0"}, {"33809", "0"}, {"33624", "0"},
        {"33810", "0"}, {"33625", "0"}, {"33811", "266"}, {"33626", "0"}, {"33812", "0"},
        {"33627", "0"}, {"33813", "0"}, {"33628", "0"}, {"33850", "0"}, {"33851", "0"},
        {"33852", "0"}, {"33853", "0"}, {"33854", "0"}, {"33855", "100"}, {"2849", "1"},
        {"2790", "1"}, {"2708", "1"}, {"20750", "탐색중"}, {"3995", "0"}, {"20760", "0"},
        {"39328", ""}, {"39329", ""}, {"39330", ""}, {"39361", ""}, {"39300", ""}, {"39331", ""},
        {"39362", ""}, {"39301", ""}, {"39332", ""}, {"39333", ""}, {"39302", ""}, {"39303", ""},
        {"39334", ""}, {"39304", ""}, {"39335", ""}, {"39305", ""}, {"39336", ""}, {"39306", ""},
        {"39337", ""}, {"39307", ""}, {"39338", ""}, {"39308", ""}, {"39339", ""}, {"39309", ""},
        {"39340", ""}, {"39310", ""}, {"39341", ""}, {"39311", ""}, {"39342", ""}, {"39312", ""},
        {"39343", ""}, {"39313", ""}, {"39344", ""}, {"39314", ""}, {"39345", ""}, {"39315", ""},
        {"39346", ""}, {"39316", ""}, {"39347", ""}, {"39317", ""}, {"39348", ""}, {"39318", ""},
        {"39349", ""}, {"39319", ""}, {"39350", ""}, {"39320", ""}, {"39351", "019000"},
        {"7019", "0"}, {"7050", "322000000"}, {"39321", ""}, {"39352", ""}, {"40066", "2"},
        {"39322", ""}, {"39323", ""}, {"39354", ""}, {"40005", "1"}, {"39324", ""}, {"40068", "1"},
        {"39325", ""}, {"40007", "1"}, {"39326", ""}, {"7242", "0"}, {"39327", "016"},
        {"7243", "14/05/04"}, {"7213", "0"}, {"40011", "1"}, {"40416", "1"}, {"7216", "0"},
        {"7000", "s"}, {"7217", "14/05/04"}, {"7621", ""}, {"40512", "1"}, {"40513", "1"},
        {"7003", "15/07/04"}, {"7282", "0"}, {"7004", "2"}, {"40514", "5"}, {"7810", "000000"},
        {"40608", "1"}, {"40516", "0"}, {"40609", "1"}, {"40610", "1"}, {"40115", "1"},
        {"40611", "1"}, {"40116", "1"}, {"40612", "1"}, {"40117", "1"}, {"7290", "3700135"},
        {"40118", "1"}, {"40211", "1"}, {"40614", "1"}, {"40707", "1"}, {"40119", "1"},
        {"40212", "1"}, {"40615", "1"}, {"40708", "1"}, {"7292", "3110000"}, {"40213", "1"},
        {"40616", "1"}, {"40709", "1"}, {"40802", "1"}, {"40214", "1"}, {"40617", "1"},
        {"40710", "1"}, {"40803", "1"}, {"40215", "1"}, {"40711", "1"}, {"40804", "2"},
        {"40216", "1"}, {"40712", "1"}, {"40805", "0"}, {"40217", "1"}, {"40713", "1"},
        {"40311", "1"}, {"40714", "1"}, {"40312", "1"}, {"40715", "1"}, {"40313", "1"},
        {"40716", "1"}, {"40314", "1"}, {"40315", "1"}, {"40316", "0"}, {"40317", "1"},
        {"40411", "1"}, {"40413", "1"}, {"40414", "1"}, {"40507", "1"}, {"40415", "1"},
        {"40508", "1"}, {"40509", "1"}, {"7960", "0"}, {"40510", "1"}, {"7961", "14/05/04"},
        {"40511", "1"}, {"7900", ""}, {"7901", "2"}, {"7963", "0"}, {"41318", "4"},
        {"7964", "14/05/04"}, {"41112", "009009"}, {"9571", "0"}, {"41905", ""}, {"41907", "0"},
        {"41912", "0"}, {"25965", "0"}, {"42102", "0"}, {"26432", ""}, {"26433", ""}, {"26403", ""},
        {"26434", ""}, {"26528", ""}, {"26435", ""}, {"26529", ""}, {"26436", ""}, {"26530", ""},
        {"26437", ""}, {"26531", ""}, {"26438", ""}, {"26594", ""}, {"26532", ""}, {"26439", ""},
        {"26440", ""}, {"26441", ""}, {"26442", ""}, {"26010", "240000000"}, {"26509", ""},
        {"26511", ""}, {"26512", ""}, {"27010", "4"}, {"10752", ""}, {"27011", "5"}, {"26515", ""},
        {"10784", ""}, {"26516", ""}, {"10754", ""}, {"26517", ""}, {"26424", ""}, {"26518", ""},
        {"27016", "1"}, {"26521", ""}, {"10665", "180"}, {"10666", ""}, {"26430", ""},
        {"26431", ""}, {"26525", ""}, {"10762", ""}, {"26526", ""}, {"10732", ""}, {"26527", ""},
        {"10764", ""}, {"10734", ""}, {"10766", ""}, {"10736", ""}, {"10768", ""}, {"10738", ""},
        {"10770", ""}, {"10740", ""}, {"10772", ""}, {"10742", ""}, {"10774", ""}, {"10744", ""},
        {"10776", ""}, {"10746", ""}, {"10778", ""}, {"10748", ""}, {"10780", ""}, {"43701", ""},
        {"10750", ""}, {"10782", ""}, {"11620", "0"}, {"29005", ""}, {"29006", ""}, {"29011", ""},
        {"29015", ""}, {"30028", "100000000"}, {"30029", "start"}, {"30008", ""},
        {"14029", "327000000"}, {"14103", "100000000"}, {"31265", "101000000"}, {"31246", "067000"},
        {"14431", ""}, {"15148", ""}, {"31190", "0"}, {"31191", "0"}, {"31845", "14/07/10"},
        {"31101", ""}, {"31846", "1"}, {"31195", "0"}, {"31198", ""}, {"31851", ""}};
        final boolean isZero = GameConstants.isZero(chr.getJob());
        w.write(1);
        w.writeShort((isZero ? zeroQuestInfo.length : 0) + started.size());
        for (final MapleQuestStatus q : started) {
            w.writeInt(q.getQuest().getId()); // 1.2.251+
            w.writeMapleAsciiString(q.getCustomData() != null ? q.getCustomData() : "");
        }
        if (isZero) {
            for (String[] zq : zeroQuestInfo) {
                w.writeInt(Integer.parseInt(zq[0]));
                w.writeMapleAsciiString(zq[1]);
            }
        }
    }

    public static void addCompletedQuestInfo(final WritingPacket w, final MapleCharacter chr) {
        final List<MapleQuestStatus> completed = chr.getCompletedQuests();
        final int[][] zeroCompleteQuest = {{32550, -1618847232}, {30314216, 33565}, {54317056, 30490439},
        {3994, 931097088}, {30311922, 6000}, {-1892926976, 30337330}, {39001, 415129600},
        {30459275, 40000}, {1817815552, 30311363}, {40001, -1277151744}, {30311363, 7049},
        {673829376, 30311394}, {40002, -77151744}, {30311363, 40003}, {1122848256, 30311364},
        {40004, -772119040}, {30311364, 40100}, {-431203328, 30311392}, {40101, -526170624},
        {30311393, 6995}, {-1466503680, 30313829}, {40102, -211072512}, {30311396, 40103},
        {293960192, 30311398}, {40104, 1493960192}, {30311398, 40105}, {544287232, 30311408},
        {40106, 1144287232}, {30311408, 40107}, {99646976, 30311419}, {40200, 1735075840},
        {30311585, 40108}, {99646976, 30311419}, {40201, -1959891456}, {30311585, 40109},
        {1709712384, 30311421}, {40202, 1670304768}, {30311592, 40110}, {-185254912, 30311421},
        {40203, -2024662528}, {30311592, 40111}, {224810496, 30311424}, {40204, 405533696},
        {30311599, 40050}, {-362053632, 30311366}, {40112, 224810496}, {30311424, 40205},
        {-1584400896, 30311600}, {40051, 1942979072}, {30311368, 40206}, {720631808, 30311602},
        {40052, -1751988224}, {30311368, 40207}, {-1934073856, 30311610}, {40300, -264070144},
        {30311763, 40704}, {-1880039424, 30313568}, {40053, 553044480}, {30311370, 40208},
        {-1303877632, 30311616}, {40301, 935929856}, {30311764, 7783}, {-44506112, 30482371},
        {40054, -1941922816}, {30311370, 40209}, {496122368, 30311617}, {40302, 2135929856},
        {30311764, 40705}, {765908992, 30313598}, {40055, -741922816}, {30311370, 40210},
        {1096122368, 30311617}, {40303, -1559037440}, {30311764, 40056}, {-1026824704, 30311373},
        {40304, -833873920}, {30311769, 7600}, {309297152, 30458638}, {40800, -1508927488},
        {30313602, 40057}, {173175296, 30311374}, {40305, 1281224192}, {30311773, 40801},
        {-908927488, 30313602}, {40058, 773175296}, {30311374, 40306}, {1881224192, 30311773},
        {40059, -176497664}, {30311383, 40307}, {-298644992, 30311776}, {40400, -584001024},
        {30311918, 40060}, {-1566432256, 30311385}, {40308, -1162107904}, {30311823, 40401},
        {1815998976, 30311919}, {40061, -966432256}, {30311385, 40309}, {573121024, 30311831},
        {40402, 37437952}, {30311963, 40960}, {-1587579904, 30312573}, {40062, 548665856},
        {30311389, 40310}, {573121024, 30311831}, {40403, 1237437952}, {30311963, 40930},
        {-1559037440, 30311764}, {40961, 1878045184}, {30312746, 40063}, {1748665856, 30311389},
        {40404, -627333120}, {30311969, 40900}, {-772119040, 30311364}, {40931, 1881224192},
        {30311773, 40962}, {1243470336, 30312759}, {40405, -27333120}, {30311969, 7887},
        {1802040320, 30337330}, {40901, -1751988224}, {30311368, 40932}, {-298644992, 30311776},
        {40963, 832496640}, {30313035, 40406}, {697830400, 30311975}, {40902, -741922816},
        {30311370, 40933}, {-1162107904, 30311823}, {40964, -1710635520}, {30313244, 40407},
        {1297830400, 30311975}, {40500, -536744448}, {30311987, 40903}, {773175296, 30311374},
        {40934, 573121024}, {30311831, 40408}, {538092032, 30311983}, {40501, 1863255552},
        {30311988, 40904}, {-966432256, 30311385}, {40409, 1138092032}, {30311983, 40502},
        {-1231711744, 30311988}, {7860, -1793110528}, {30440217, 40905}, {1748665856, 30311389},
        {40503, 839600128}, {30312182, 7892}, {204459520, 30342755}, {7707, -1996044288},
        {30494402, 40504}, {1586202624, 30312231}, {40505, 826010112}, {30312378, 40970},
        {-476514816, 30313370}, {40506, 1866271744}, {30312386, 40940}, {1815998976, 30311919},
        {40971, -826187776}, {30313380, 41250}, {-279022592, 30312376}, {41312, 832496640},
        {30313035, 40600}, {-1587579904, 30312573}, {40910, -526170624}, {30311393, 40941},
        {1237437952, 30311963}, {40972, 550610944}, {30313436, 41251}, {796268032, 30312233},
        {40601, 1878045184}, {30312746, 40911}, {1493960192, 30311398}, {40942, -27333120},
        {30311969, 40973}, {-1880039424, 30313568}, {41252, 2120977408}, {30312377, 40602},
        {1243470336, 30312759}, {40912, 99646976}, {30311419, 40943}, {1297830400, 30311975},
        {40974, 765908992}, {30313598, 41253}, {2120977408, 30312377}, {41315, 1889364480},
        {30313245, 41408}, {196105216, 30313604}, {40603, -1567503360}, {30313034, 40913},
        {-185254912, 30311421}, {40944, 1138092032}, {30311983, 41254}, {-373989888, 30312377},
        {41316, -1300570112}, {30313246, 40604}, {-967503360, 30313034}, {40914, 224810496},
        {30311424, 41255}, {-2108764672, 30312231}, {41317, -1110635520}, {30313244, 40605},
        {832496640, 30313035}, {41256, 101300736}, {30312234, 40606}, {1384331776, 30313244},
        {41257, 1836075520}, {30312380, 41350}, {-1771482112, 30313371}, {40607, -1710635520},
        {30313244, 40700}, {218452480, 30313370}, {41103, -198844928}, {30311617, 41258},
        {196268032, 30312233}, {41351, 1733550592}, {30313373, 40701}, {-476514816, 30313370},
        {40980, -908927488}, {30313602, 41104}, {-293812224, 30311618}, {41352, -1771482112},
        {30313371, 40702}, {-826187776, 30313380}, {40950, -1231711744}, {30311988, 41105},
        {-893812224, 30311618}, {41353, -161416704}, {30313373, 40703}, {550610944, 30313436},
        {40920, -1959891456}, {30311585, 40951}, {839600128, 30312182}, {41106, 401155072},
        {30311618, 41261}, {541108224, 30312381}, {41354, -666449408}, {30313372, 40921},
        {-2024662528, 30311592}, {40952, 1586202624}, {30312231, 40922}, {720631808, 30311602},
        {40953, 826010112}, {30312378, 41263}, {131042816, 30312379}, {40923, -1934073856},
        {30311610, 40954}, {1866271744, 30312386}, {41264, 731042816}, {30312379, 41357},
        {205316608, 30313427}, {40924, 1096122368}, {30311617, 41358}, {1910349312, 30313428},
        {41111, 2106187776}, {30311619, 41359}, {205316608, 30313427}, {41050, -185254912},
        {30311421, 41360}, {1910349312, 30313428}, {41114, -198844928}, {30311617, 41269},
        {2120977408, 30312377}, {41300, 2002300416}, {30313029, 41115}, {-798844928, 30311617},
        {41270, 2120977408}, {30312377, 41301}, {-1092666880, 30313029}, {41363, -684418048},
        {30313587, 41302}, {2002300416, 30313029}, {41364, -779385344}, {30313588, 41303},
        {1907333120, 30313030}, {41365, -684418048}, {30313587, 41055}, {-1985254912, 30311421},
        {41304, 2002300416}, {30313029, 41366}, {-874352640, 30313589}, {41305, 1212365824},
        {30313031, 41925}, {-457020928, 30311367}, {41306, 517398528}, {30313032, 41926},
        {142979072, 30311368}, {41307, -1377568768}, {30313032, 41400}, {988764672, 30312768},
        {41370, -1318992896}, {30313600, 41401}};
        final boolean isZero = GameConstants.isZero(chr.getJob());
        int time;
        w.write(1);
        short size = (short) completed.size();
        w.writeShort((isZero ? zeroCompleteQuest.length : 0) + size);
        for (final MapleQuestStatus q : completed) {
            w.writeInt(q.getQuest().getId()); // 1.2.251+
            time = KoreanDateUtil.getQuestTimestamp(q.getCompletionTime());
            w.writeInt(time); // maybe start time
            w.writeInt(time); // completion time
        }
        if (isZero) {
            for (int[] zc : zeroCompleteQuest) {
                w.writeInt(zc[0]);
                w.writeInt(zc[1]);
                w.writeInt(zc[1]);
            }
        }
    }

    public static void addPlayerInfo(final WritingPacket packet, final MapleCharacter chr) {
        packet.writeLong(-1); // Flag.

        packet.write(0);
        for (int i = 0; i < 3; i++) {
            packet.writeInt(-3); // Pet Active Skill Cooldown
        }

        packet.write(0);

        packet.writeInt(0);

        packet.write(0);
        PacketProvider.addPlayerStats(packet, chr, true);
        packet.write(chr.getBuddylist().getCapacity());

        /* 정령의 축복 */
        if (chr.getBlessOfFairyOrigin() != null) {
            packet.write(1);
            packet.writeMapleAsciiString(chr.getBlessOfFairyOrigin());
        } else {
            packet.write(0);
        }

        /* 여제의 축복 */
        if (chr.getBlessOfEmpressOrigin() != null) {
            packet.write(1);
            packet.writeMapleAsciiString(chr.getBlessOfEmpressOrigin());
        } else {
            packet.write(0);
        }

        packet.write(0);
        addMoneyInfo(packet, chr);

        packet.writeInt(0);

        addInventoryInfo(packet, chr);

        addSkillInfo(packet, chr);
        addCoolDownInfo(packet, chr);

        if (GameConstants.isZero(chr.getJob())) {
            addZeroQuestInfo(packet);
        } else {
            addStartedQuestInfo(packet, chr);
            addCompletedQuestInfo(packet, chr);
        }

        packet.writeShort(0);

        addRingInfo(packet, chr);
        addRocksInfo(packet, chr);
        chr.QuestInfoPacket(packet);

        packet.writeShort(0);

        packet.writeShort(0);

        packet.write(1);

        // QuestRecordEx?
        packet.writeInt(0);
        for (int i = 0; i < 0; i++) {
            packet.writeInt(0);
            packet.writeMapleAsciiString("");
        }

        // Map Transfer
        packet.writeInt(0);
        for (int i = 0; i < 0; i++) {
            packet.writeInt(0);
            packet.writeInt(0);
        }

        if (GameConstants.isWildHunter(chr.getJob())) {
            addWildHunterInfo(packet, chr);
        }

        if (GameConstants.isZero(chr.getJob())) {
            chr.getStat().ZeroData(packet, chr);
        }
        packet.writeInt(0);
        chr.getStealSkills().connectData(packet, chr);
        addInnerStats(packet, chr);
        packet.writeShort(0);
        packet.writeInt(0);
        packet.write(0);
        addHonorInfo(packet, chr);
        packet.write(1);
        packet.writeShort(0);
        packet.write(0);
        packet.writeInt(21173);
        packet.writeInt(37141);
        packet.writeInt(1051291);
        packet.write(0);
        packet.writeInt(-1);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeShort(0);
        packet.writeShort(0);
        packet.write(0);
        addMonsterLife(packet, chr);
        packet.writeInt(16);
        packet.writeInt(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);

        packet.writeInt(0);
        packet.writeLong(getTime(-2));
        packet.writeInt(0);
        packet.writeInt(chr.getId());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeLong(getTime(-2));
        packet.writeInt(10);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeLong(0);
        
        packet.writeShort(0);
        packet.writeShort(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeShort(0);
        MatrixPacket.addMatrixInfo(packet, chr.cores, chr.getClient());
        packet.writeInt(chr.getClient().getAccID());
        packet.writeInt(chr.getId());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeLong(0);
        packet.writeInt(0);

        
        packet.writeInt(1);
        packet.writeInt(1);
        packet.writeInt(1);
        packet.write(2);
        packet.writeLong(0);
        packet.writeShort(0);
        packet.write(0);
        packet.writeInt(0);
        packet.writeInt(0);
    }

    public static final void addWildHunterInfo(final WritingPacket packet, final MapleCharacter chr) { //재규어
        packet.write(GameConstants.getJaguarType(chr));
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0);
    }

    public static final void addMoneyInfo(final WritingPacket packet, final MapleCharacter chr) {
        packet.writeLong(chr.getMeso()); // Mesos
    }

    public static final void addHonorInfo(final WritingPacket packet, final MapleCharacter chr) {
        packet.writeInt(chr.getInnerExp());
        packet.writeInt(chr.getInnerLevel());
    }

    public static void addRingInfo(final WritingPacket packet, final MapleCharacter chr) {
        Triple<List<MapleRing>, List<MapleRing>, List<MapleRing>> aRing = chr.getRings(true);
        List<MapleRing> cRing = aRing.getFirst();
        List<MapleRing> fRing = aRing.getSecond();
        List<MapleRing> mRing = aRing.getThird();
        packet.writeShort(cRing.size());
        for (MapleRing ring : cRing) {
            packet.writeInt(ring.getPartnerChrId());
            packet.writeAsciiString(ring.getPartnerName(), 13);
            packet.writeLong(ring.getRingId());
            packet.writeLong(ring.getPartnerRingId());
        }
        packet.writeShort(fRing.size());
        for (MapleRing ring : fRing) {
            packet.writeInt(ring.getPartnerChrId());
            packet.writeAsciiString(ring.getPartnerName(), 13);
            packet.writeLong(ring.getRingId());
            packet.writeLong(ring.getPartnerRingId());
            packet.writeInt(ring.getItemId());
        }
        packet.writeShort(mRing.size());
        int marriageId = 30000;
        for (MapleRing ring : mRing) {
            packet.writeInt(marriageId);
            packet.writeInt(chr.getId());
            packet.writeInt(ring.getPartnerChrId());
            packet.writeShort(3);
            packet.writeInt(ring.getItemId());
            packet.writeInt(ring.getItemId());
            packet.writeAsciiString(chr.getName(), 13);
            packet.writeAsciiString(ring.getPartnerName(), 13);
        }
    }

    public static final void addSkillInfo(final WritingPacket mplew, final MapleCharacter chr) {
        mplew.write(1); // true, Sometimes sent in different ways
        Map<ISkill, SkillEntry> skills = new HashMap<>();
        for (Entry<ISkill, SkillEntry> skill : chr.getSkills().entrySet()) {
            if (skill.getKey().getId() >= 400000000 && skill.getKey().getId() <= 499999999) {
                chr.matrixSkills.put(skill.getKey(), skill.getValue());
            } else {
                skills.put(skill.getKey(), skill.getValue());
            }
        }
        List<LinkSkill> link_skill1 = chr.getLinkSkill(true, false, false);
        List<LinkSkill> link_skill2 = chr.getLinkSkill(false, true, false);
        List<LinkSkill> link_skill3 = chr.getLinkSkill(false, false, false);
        List<LinkSkill> link_skill4 = chr.getLinkSkill(false, false, true);
        mplew.writeShort(skills.size() + link_skill2.size() + link_skill3.size());
        for (final Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            mplew.writeInt(skill.getKey().getId());
            if (GameConstants.isProfessionSkill(skill.getKey().getId())) {
                if (skill.getKey().getId() == chr.getProfession().getFirstProfessionSkill()) {
                    mplew.writeShort(chr.getProfession().getFirstProfessionExp());
                    mplew.write(0);
                    mplew.write(chr.getProfession().getFirstProfessionLevel());
                } else if (skill.getKey().getId() == chr.getProfession().getSecondProfessionSkill()) {
                    mplew.writeShort(chr.getProfession().getSecondProfessionExp());
                    mplew.write(0);
                    mplew.write(chr.getProfession().getSecondProfessionLevel());
                } else {
                    mplew.writeInt(2147483647);
                }
                mplew.writeLong(getTime(-1));
            } else {
                mplew.writeInt(skill.getValue().skillevel);
                mplew.writeLong(getTime(skill.getValue().expiration));
                if (SkillFactory.is_skill_need_master_level(skill.getKey().getId())) {
                    mplew.writeInt(skill.getValue().masterlevel);
                }
            }
        }
        for (final LinkSkill link_skills : link_skill2) {
            mplew.writeInt(link_skills.getRealSkillId());
            mplew.writeInt(link_skills.getLinkedCid());
            mplew.writeLong(getTime(-2));
        }
        for (final LinkSkill link_skills : link_skill3) {
            mplew.writeInt(link_skills.getSkillId());
            mplew.writeInt(link_skills.getLinkingCid());
            mplew.writeLong(getTime(-2));
        }

        mplew.writeShort(link_skill4.size()); // linkedSkillLevelUp
        for (final LinkSkill link_skills : link_skill4) {
            mplew.writeInt(link_skills.getSkillId());
            mplew.writeShort(1);
        }

        mplew.writeInt(link_skill1.size()); // Probably 5th chance (lol) was a link skill)
        for (final LinkSkill link_skills : link_skill1) {
            mplew.writeInt(link_skills.getLinkingCid());
            mplew.writeInt(link_skills.getLinkedCid());
            mplew.writeInt(link_skills.getSkillId());
            mplew.writeShort(link_skills.getSkillLevel());
            mplew.writeLong(getKoreanTimestamp(link_skills.getTime()));
        }
    }

    private static boolean isFortyJob(int job) {
        if (job / 10 == 43) {
            return job == 434;
        } else if (job / 100 == 22) {
            return job == 2219;
        } else {
            return job / 100 % 10 > 0 && job % 10 == 2;
        }
    }

    public static int checkHyper(int skillid) {
        final int job = skillid / 10000;
        if (!isFortyJob(job)) {
            return -2; // is not FortyJob
        }
        final int compare = skillid % 1000;
        if (30 <= compare && compare <= 52) {
            return 0; // passive
        } else if (53 <= compare && compare <= 60 || 30 <= compare && compare <= 32) {
            return 1; // active
        }
        return -1; // is not Hyper
    }

    public static final void addCoolDownInfo(final WritingPacket w, final MapleCharacter chr) {
        w.writeShort(chr.getAllCooldowns().size());
        for (final MapleCoolDownValueHolder cooling : chr.getAllCooldowns()) {
            w.writeInt(cooling.skillId);
            w.writeInt((int) (cooling.length + cooling.startTime - System.currentTimeMillis()) / 1000);
        }
    }

    public static final void addRocksInfo(final WritingPacket w, final MapleCharacter chr) {
        chr.sendPacketTrock(w);
    }

    public static final void addInnerStats(final WritingPacket w, final MapleCharacter player) {
        final List<InnerSkillValueHolder> skills = player.getInnerSkills();
        w.writeShort(skills.size());
        for (int i = 0; i < skills.size(); ++i) {
            w.write(i + 1);
            w.writeInt(skills.get(i).getSkillId()); // Skill id
            w.write(skills.get(i).getSkillLevel()); // Options (x, max = maxLevel)
            w.write(skills.get(i).getRank());
        }
    }

    public static void addInventoryInfo(WritingPacket w, MapleCharacter chr) {
        w.write(chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit()); // equip slots
        w.write(chr.getInventory(MapleInventoryType.USE).getSlotLimit()); // use slots
        w.write(chr.getInventory(MapleInventoryType.SETUP).getSlotLimit()); // set-up slots
        w.write(chr.getInventory(MapleInventoryType.ETC).getSlotLimit()); // etc slots
        w.write(chr.getInventory(MapleInventoryType.CASH).getSlotLimit()); // cash slots

        chr.getSymbols().clear();

        // 펜던트 만료일
        //w.write(new byte[]{0, (byte) 0xC0, (byte) 0xEC, (byte) 0x3A, (byte) 0xD2, (byte) 0xB3, (byte) 0xFF, 1});
        //w.write(new byte[]{0, (byte) 0xF4, (byte) 0xEC, (byte) 0x3A, (byte) 0xD2, (byte) 0xB3, (byte) 0xFF, 1});
        
        final MapleQuestStatus stat = chr.getQuestNoAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT)); // 0x200000 : int + int actually
        if (stat != null && stat.getCustomData() != null && Long.parseLong(stat.getCustomData()) > System.currentTimeMillis()) {
            w.writeLong(getTime(Long.parseLong(stat.getCustomData())));
        } else {
            w.writeLong(getTime(-1)); // All characters have 2 pendant slots //getTime(-2)
        }
        w.write(0); // Whether to send cache items
        MapleInventory iv = chr.getInventory(MapleInventoryType.EQUIPPED);
        Collection<IItem> equippedC = iv.list();
        List<IItem> equipped = new ArrayList<IItem>(equippedC.size());

        for (IItem item : equippedC) {
            equipped.add((IItem) item);
        }
        /* Start wearing item */
        Collections.sort(equipped);
        for (IItem item : equipped) {
            if (item.getPosition() < 0 && item.getPosition() > -100) {
                addItemInfo(w, item, false, false, null);
            }
        }
        /* Exit the item being equipped */
        w.writeShort(0);
        /* Starting cache item */
        for (IItem item : equipped) {
            if (item.getPosition() <= -100 && item.getPosition() > -1000) {
                addItemInfo(w, item, false, false, null);
            }
        }
        /* Exiting cache item */
        w.writeShort(0);

        /* Start Equipment Inventory */
        iv = chr.getInventory(MapleInventoryType.EQUIP);
        for (IItem item : iv.list()) {
            addItemInfo(w, item, false, false, null);
        }
        /* Terminate Equipment Inventory */
        w.writeShort(0);

        // NonBPEquip Start
        // Launch Evan Equipment Inventory
        for (IItem item : equipped) {
            if (item.getPosition() <= -1000 && item.getPosition() > -1100) {
                addItemInfo(w, item, false, false, null);
            }
        }
        // Evan Equipment Inventory Shutdown
        w.writeShort(0);
        // Start Mechanic Equipment Inventory
        for (IItem item : equipped) {
            if (item.getPosition() <= -1100 && item.getPosition() > -1200) {
                addItemInfo(w, item, false, false, null);
            }
        }
        // Exit Mechanic Equipment Inventory
        w.writeShort(0);
        // Launch Android Device Inventory
        for (IItem item : equipped) {
            if (item.getPosition() <= -1200 && item.getPosition() > -1300) {
                addItemInfo(w, item, false, false, null);
            }
        }
        // Android Device Inventory Exit
        w.writeShort(0);
        for (IItem item : equipped) {
            if (item.getPosition() <= -1300 && item.getPosition() > -1400) {
                addItemInfo(w, item, false, false, null);
            }
        }
        // Dress up cache ends
        w.writeShort(0); // 1.2.169+
        w.writeShort(0); // 1.2.182+
        for (IItem item : equipped) {
            if (item.getPosition() <= -1500 && item.getPosition() > -2000) {
                addItemInfo(w, item, false, false, null);
            }
        }
        // Zero beta clothes ends
        w.writeShort(0); // 1.2.183+

        w.writeShort(0); // 1.2.193+
        for (IItem item : equipped) {
            if (item.getPosition() <= -1600 && item.getPosition() >= -1700) {
                chr.getSymbols().add(item);
                addItemInfo(w, item, false, false, null);
            }
        }
        w.writeShort(0);
        // NonBPEquip END
        // for( int i = 0; i <8; i++)w.writeShort(0);

        // VirtualEquipInventory
        w.writeShort(0); // 1.2.201+

        w.writeInt(0);

        /* Start consumption inventory */
        iv = chr.getInventory(MapleInventoryType.USE);
        for (IItem item : iv.list()) {
            addItemInfo(w, item, false, false, null);
        }
        /* Exit consumption inventory */
        w.write(0);
        /* Start installation inventory */
        iv = chr.getInventory(MapleInventoryType.SETUP);
        for (IItem item : iv.list()) {
            addItemInfo(w, item, false, false, null);
        }
        /* Exit installation inventory */
        w.write(0);
        /* Start other inventory */
        iv = chr.getInventory(MapleInventoryType.ETC);
        for (IItem item : iv.list()) {
            addItemInfo(w, item, false, false, chr);
        }
        /* Exit other inventory */
        w.write(0);
        /* Start Cache Inventory */
        iv = chr.getInventory(MapleInventoryType.CASH);
        for (IItem item : iv.list()) {
            addItemInfo(w, item, false, false, chr);
        }
        /* Cache Inventory Termination */
        w.write(0);

        w.writeInt(0); // Consumption bags
        w.writeInt(chr.getExtendedSlots().size()); // Guitar bag
        for (int i = 0; i < chr.getExtendedSlots().size(); i++) {
            w.writeInt(i);
            w.writeInt(chr.getInventory(MapleInventoryType.ETC).findByUniqueId(chr.getExtendedSlot(i)).getItemId());
            for (IItem item : chr.getInventory(MapleInventoryType.ETC).list()) {
                if (item.getPosition() > (i * 100 + 100) && item.getPosition() < (i * 100 + 200)) {
                    addItemInfo(w, item, false, false, false, true, chr);
                }
            }
            w.writeInt(-1);
        }
        w.writeInt(0); // 1.2.173+ // Install bag

        w.writeInt(0);
        for (int i = 0; i < 0; i++) {
            // consumeItemLimit
            w.writeInt(0);
            w.writeLong(0);
        }

        w.writeInt(0);
        for (int i = 0; i < 0; i++) {
            // cashItemCoolTime
            w.writeLong(0);
            w.writeLong(0);
        }

        w.write(0); // POT?
    }

    public static final void addPlayerStats(final WritingPacket w, final MapleCharacter chr, final boolean ingame) {
        for (int i = 0; i < 2; i++) {
            w.writeInt(chr.getId()); // 1.2.239+
        }
        w.writeInt(0); // WorldIDForLog
        w.writeAsciiString(chr.getName(), 13);
        w.write(chr.getGender());
        w.write(chr.getSkinColor());
        w.writeInt(chr.getFace());
        if (chr.getBaseColor() != -1) {
            w.writeInt(chr.getHair() / 10 * 10 + chr.getBaseColor());
        } else {
            w.writeInt(chr.getHair());
        }
        w.write(chr.getBaseColor()); // MixBaseHairColor
        w.write(chr.getAddHairColor()); // MixAddHairColor
        w.write(chr.getBaseProb()); // MixHairBaseProb
        w.writeInt(chr.getLevel());
        w.writeShort(chr.getJob());
        chr.getStat().connectData(w);
        w.writeShort(chr.getRemainingAp());
        if (!GameConstants.isPinkBean(chr.getJob()) && chr.getJob() != 4100 && chr.getJob() != 900) { // Except pink bean.
            w.write(chr.getRemainingSpSize());
            for (int i = 0; i < chr.getRemainingSps().length; i++) {
                if (chr.getRemainingSp(i) > 0) {
                    w.write(i + 1);
                    w.writeInt(chr.getRemainingSp(i));
                }
            }
        } else {
            w.writeShort(chr.getRemainingSp());
        }
        w.writeLong(chr.getExp());
        w.writeInt(chr.getFame());
        w.writeInt(!GameConstants.isZero(chr.getJob()) ? 99999 : chr.getWP());
        w.writeInt(chr.getMapId());
        w.write(chr.getInitialSpawnpoint());
        w.writeShort(chr.getSubcategory());
        if (GameConstants.isDemonSlayer(chr.getJob()) || GameConstants.isDemonAvenger(chr.getJob())
                || GameConstants.isXenon(chr.getJob()) || GameConstants.isArk(chr.getJob())) {
            w.writeInt(chr.getSecondFace());
        }
        w.writeShort(chr.getProfession().getFatigue());
        w.writeInt(chr.getClient().getLastConnection()); // LastFatigueUpdateTime
        addAdditionalStats(w, chr);
        w.write(0);
        w.writeLong(getTime(-2));

        w.writeInt(0);
        w.write(10);

        w.writeInt(0);

        w.write(5);
        w.write(5);

        w.writeInt(0);

        for (int i = 0; i < 9; i++) {
            w.writeInt(0);
            w.writeInt(0);
            w.writeInt(0);
        }
        w.write(HexTool.getByteArrayFromHexString("C7 31 D3 01"));
        w.write(HexTool.getByteArrayFromHexString("90 C7 48 BE"));

        w.writeLong(0);
        w.writeLong(0);
        w.writeInt(0);
        w.writeInt(0);
        w.writeInt(0);
        w.write(chr.getBurningCharacter());
        w.write(1);
    }

    public static void addAdditionalStats(WritingPacket w, MapleCharacter chr) {
        w.writeInt(chr.getStat().getAmbition()); // charisma
        w.writeInt(chr.getStat().getInsight()); // insight
        w.writeInt(chr.getStat().getWillPower());// will
        w.writeInt(chr.getStat().getDiligence());// handicraft
        w.writeInt(chr.getStat().getEmpathy()); // Emotion
        w.writeInt(chr.getStat().getCharm()); // Charm
        w.writeShort(chr.getTodayCharisma()); // Charisma of the day
        w.writeShort(chr.getTodayInsight()); // Insight of the day
        w.writeShort(chr.getTodayWillPower()); // Will of the day
        w.writeShort(chr.getTodayDiligence()); // Today's dexterity
        w.writeShort(chr.getTodayEmpathy()); // Today's emotion
        w.writeShort(chr.getTodayCharm()); // Today's Attraction
    }

    public static void addMonsterLife(WritingPacket packet, MapleCharacter chr) {
        packet.writeMapleAsciiString(""); // sFarmName
        packet.writeInt(0); // FarmPoint
        packet.writeInt(0); // FarmLevel
        packet.writeInt(0); // FarmEXP
        packet.writeInt(0); // DecoPoint
        packet.writeInt(0); // FarmCash
        packet.writeInt(0); // FarmGender
        packet.writeInt(0); // FarmTheme
        packet.writeInt(0); // SlotExtend
        packet.writeInt(0); // LockerSlotCount
    }

    public static void addPlayerLooks(final WritingPacket w, final MapleCharacter chr, final boolean mega, boolean sec) {
        boolean isZero = GameConstants.isZero(chr.getJob());
        if (isZero) {
            w.write(isZero && sec ? chr.getSecondGender() : chr.getGender());
        } else {
            w.write(chr.getGender());
        }
        w.write(isZero && sec ? chr.getSecondSkinColor() : chr.getSkinColor());
        w.writeInt(isZero && sec ? chr.getSecondFace() : chr.getFace());
        w.writeInt(chr.getJob());
        w.write(mega ? 0 : 1);
        int hair = chr.getHair();
        if (chr.getBaseColor() != -1) {
            hair = chr.getHair() / 10 * 10 + chr.getBaseColor();
        }
        w.writeInt(isZero && sec ? chr.getSecondHair() : hair);

        final Map<Short, Integer> myEquip = new LinkedHashMap<Short, Integer>();
        final Map<Short, Integer> maskedEquip = new LinkedHashMap<Short, Integer>();
        MapleInventory equip = chr.getInventory(MapleInventoryType.EQUIPPED);

        for (final IItem item : equip.list()) {
            short pos_ = 0;
            if (isZero && sec) {
                if (item.getPosition() <= -1499 && item.getPosition() >= -1508) {
                    pos_ = BetaSlot(item.getPosition());
                }
            }
            short posTemp = item.getPosition();
            if (isZero) {
                if (sec) {
                    if (posTemp == -11) {
                        continue;
                    } else if (posTemp == -10) {
                        posTemp = -11;
                    }
                } else {
                    if (item.getPosition() == -10) {
                        continue;
                    }
                }
            }
            IEquip item_ = (IEquip) item;
            short pos = pos_ == 0 ? (short) (posTemp * -1) : pos_;
            if (pos < 100 && myEquip.get(pos) == null) {
                String lol = ((Integer) item.getItemId()).toString();
                String ss = lol.substring(0, 3);
                int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());

            } else if (pos > 100 && pos != 111) {
                pos -= 100;
                if (myEquip.get(pos) != null) {
                    maskedEquip.put(pos, myEquip.get(pos));
                }
                String lol = ((Integer) item.getItemId()).toString();
                String ss = lol.substring(0, 3);
                int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());

            } else if (myEquip.get(pos) != null) {
                maskedEquip.put(pos, item.getItemId());
            }
        }

        for (final Entry<Short, Integer> entry : myEquip.entrySet()) {
            w.write(entry.getKey());
            w.writeInt(entry.getValue());
        }
        w.write(0xFF);

        /* masked items */
        for (final Entry<Short, Integer> entry : maskedEquip.entrySet()) {
            w.write(entry.getKey());
            w.writeInt(entry.getValue());
        }
        w.write(0xFF);

        IItem cWeapon = equip.getItem((byte) -111);
        IItem weapon = equip.getItem((byte) -11);
        IItem sWeapon = equip.getItem((byte) -10);
        w.writeInt(cWeapon != null ? cWeapon.getItemId() : 0);
        w.writeInt(weapon != null ? weapon.getItemId() : 0);
        w.writeInt(sWeapon != null ? sWeapon.getItemId() : 0);
        w.write(0); // 엘프귀
        for (int i = 0; i < 3; i++) {
//            if (chr.getPet(i) != null) {
//                w.writeInt(chr.getPet(i).getPetItemId());
//            } else {
                w.writeInt(0);
//            }
        }
        w.writeInt(0); 
        w.writeInt(0);
        if (GameConstants.isDemonSlayer(chr.getJob()) || GameConstants.isDemonAvenger(chr.getJob())
                || GameConstants.isXenon(chr.getJob()) || GameConstants.isArk(chr.getJob())) {
            w.writeInt(chr.getSecondFace());
        }
        if (isZero) {
            w.write(chr.getSecondGender());
        }
        w.write(chr.getAddHairColor());
        w.write(chr.getBaseProb());
    }

    public static void set2barr(int value, byte[] dest, int index) {
        dest[index + 0] = (byte) (value >> 0);
        dest[index + 1] = (byte) (value >> 8);
        dest[index + 2] = (byte) (value >> 16);
        dest[index + 3] = (byte) (value >> 24);
    }

    public static int byteArrayToInt(byte bytes[], int index) {
        return ((((int) bytes[index] & 0xff) << 0) | (((int) bytes[index + 1] & 0xff) << 8)
                | (((int) bytes[index + 2] & 0xff) << 16) | (((int) bytes[index + 3] & 0xff << 24)));
    }

    public static int b2ui32(byte[] bytes, int index) {
        return byteArrayToInt(bytes, index);
    }

    public static long toUnsigned(int value) {
        return ((long) value & 0xFFFFFFFFL);
    }

    public static int get_hundred(int val) {
        return val == 0 ? -1 : (val % 1000);
    }

    public static int[] g_anWeaponType = new int[]{0, 30, 31, 32, 33, 37, 38, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
        39, 34, 52, 53, 35, 36, 21, 22, 23, 24, 56, 57, 26, 58};

    public static void encodePackedCharacterLook(WritingPacket oPacket, MapleCharacter c) {
        final Map<Short, Integer> myEquip = new LinkedHashMap<Short, Integer>();
        final Map<Short, Integer> maskedEquip = new LinkedHashMap<Short, Integer>();
        MapleInventory equip = c.getInventory(MapleInventoryType.EQUIPPED);

        for (final IItem item : equip.list()) {
            IEquip item_ = (IEquip) item;
            short pos = (short) (item.getPosition() * -1);
            if (pos < 100 && myEquip.get(pos) == null) {
                String lol = ((Integer) item.getItemId()).toString();
                String ss = lol.substring(0, 3);
                int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());
            } else if (pos > 100 && pos != 111) {
                pos -= 100;
                if (myEquip.get(pos) != null) {
                    maskedEquip.put(pos, myEquip.get(pos));
                }
                String lol = ((Integer) item.getItemId()).toString();
                String ss = lol.substring(0, 3);
                int moru = Integer.parseInt(ss + ((Integer) item_.getPotential7()).toString());
                myEquip.put(pos, item_.getPotential7() != 0 ? moru : item.getItemId());
            } else if (myEquip.get(pos) != null) {
                maskedEquip.put(pos, item.getItemId());
            }
        }

        int[] anHairEquip = new int[100];

        for (final Entry<Short, Integer> entry : myEquip.entrySet()) {
            if (entry.getKey() < 100) {
                anHairEquip[entry.getKey()] = entry.getValue();
            }
        }

        byte[] data = new byte[124];

        int datai = b2ui32(data, 0);
        datai |= c.getGender() & 1;
        datai |= 2 * (c.getSkinColor() & 0xF);
        datai |= 32 * (get_hundred(c.getFace()) & 0x3FF);
        set2barr(datai, data, 0);

        datai = b2ui32(data, 1);
        datai |= (c.getFace() / 1000 % 10 & 7) << 7;
        set2barr(datai, data, 1);

        datai = b2ui32(data, 2);
        datai |= 4 * (c.getHair() / 10000 == 4 ? 1 : 0);
        datai |= 8 * (get_hundred(c.getHair()) & 0x3FF);
        set2barr(datai, data, 2);

        datai = b2ui32(data, 3);
        datai |= 32 * (c.getHair() / 1000 % 10 & 0xF);
        set2barr(datai, data, 3);

        datai = b2ui32(data, 4);
        datai |= 2 * (get_hundred(anHairEquip[1]) & 0x3FF);
        set2barr(datai, data, 4);

        datai = b2ui32(data, 5);
        datai |= 8 * (anHairEquip[1] / 1000 % 10 & 7);
        datai |= (get_hundred(anHairEquip[2]) & 0x3FF) << 6;
        set2barr(datai, data, 5);

        datai = b2ui32(data, 7);
        datai |= anHairEquip[2] / 1000 % 10 & 3;
        datai |= 4 * (get_hundred(anHairEquip[3]) & 0x3FF);
        set2barr(datai, data, 7);

        datai = b2ui32(data, 8);
        datai |= 16 * (anHairEquip[3] / 1000 % 10 & 3);
        datai |= (get_hundred(anHairEquip[4]) & 0x3FF) << 6;
        set2barr(datai, data, 8);

        datai = b2ui32(data, 10);
        datai |= anHairEquip[4] / 1000 % 10 & 3;
        datai |= 4 * (anHairEquip[5] / 10000 == 105 ? 1 : 0);
        datai |= 8 * (get_hundred(anHairEquip[5]) & 0x3FF);
        set2barr(datai, data, 10);

        datai = b2ui32(data, 11);
        datai |= 32 * (anHairEquip[5] / 1000 % 10 & 15);
        set2barr(datai, data, 11);

        datai = b2ui32(data, 12);
        datai |= 2 * (get_hundred(anHairEquip[6]) & 0x3FF);
        set2barr(datai, data, 12);

        datai = b2ui32(data, 13);
        datai |= 8 * (anHairEquip[6] / 1000 % 10 & 3);
        datai |= 32 * (get_hundred(anHairEquip[7]) & 0x3FF);
        set2barr(datai, data, 13);

        datai = b2ui32(data, 14);
        datai |= (anHairEquip[7] / 1000 % 10 & 3) << 7;
        set2barr(datai, data, 14);

        datai = b2ui32(data, 15);
        datai |= 2 * (get_hundred(anHairEquip[8]) & 0x3FF);
        set2barr(datai, data, 15);

        datai = b2ui32(data, 16);
        datai |= 8 * (anHairEquip[8] / 1000 % 10 & 3);
        datai |= 32 * (get_hundred(anHairEquip[9]) & 0x3FF);
        set2barr(datai, data, 16);

        datai = b2ui32(data, 17);
        datai |= (anHairEquip[9] / 1000 % 10 & 3) << 7;
        set2barr(datai, data, 17);

        datai = b2ui32(data, 18);
        int v39 = 0;
        if (anHairEquip[10] != 0) {
            if (anHairEquip[10] / 10000 == 109) {
                v39 = 1;
            } else {
                v39 = (anHairEquip[10] / 10000 != 134 ? 1 : 0) + 2;
            }
        }

        datai |= 2 * (v39 & 3);
        datai |= 8 * (get_hundred(anHairEquip[10]) & 0x3FF);
        set2barr(datai, data, 18);

        datai = b2ui32(data, 19);
        datai |= 32 * (anHairEquip[10] / 1000 % 10 & 15);
        set2barr(datai, data, 19);

        IItem cWeapon = equip.getItem((byte) -111);
        IItem weapon = equip.getItem((byte) -11);

        int nWeapon = cWeapon != null ? cWeapon.getItemId() : (weapon != null ? weapon.getItemId() : 0);

        datai = b2ui32(data, 20);
        if (cWeapon != null) {
            datai |= 2;
        }
        datai |= 4 * (get_hundred(nWeapon) & 0x3FF);
        set2barr(datai, data, 20);

        int v7 = 1;
        int v6 = weapon == null ? 0 : (weapon.getItemId() / 10000 % 100);
        int v45 = 0;
        boolean error = false;
        while (g_anWeaponType[v7] != v6) {
            ++v7;
            if (v7 > 30) {
                v45 = 0;
                error = true;
                break;
            }
        }
        if (!error) {
            v45 = v7;
        }

        datai = b2ui32(data, 21);
        datai |= 16 * (nWeapon / 1000 % 10 & 3);
        datai |= (v45 & 0x1F) << 6;
        set2barr(datai, data, 21);

        datai = b2ui32(data, 22);
        datai |= 8 * 0;
        set2barr(datai, data, 22);

        data[119] = 12;
        byte[] toWrite = new byte[120];
        System.arraycopy(data, 0, toWrite, 0, 120);
        oPacket.write(toWrite);
    }

    public static short BetaSlot(short slot) {
        switch (slot) {
            case -1500:
                return -100;
            case -1501:
                return -101;
            case -1499:
                return -102;
            case -1502:
                return -103;
            case -1504:
                return -104;
            case -1507:
                return -105;
            case -1508:
                return -106;
            case -1505:
                return -107;
            case -1503:
                return -108;
            case -1506:
                return -110;
        }
        return 0;
    }

    public static final void addStarForceItemInfo(final WritingPacket w, final IItem item) {
        addItemInfo(w, item, true, true, false, false, null);
    }

    public static final void addItemInfo(final WritingPacket w, final IItem item, final boolean zeroPosition,
            final boolean leaveOut, final MapleCharacter chr) {
        addItemInfo(w, item, zeroPosition, leaveOut, false, false, chr);
    }

    public static final void addItemInfo(final WritingPacket w, final IItem item, final boolean zeroPosition,
            final boolean leaveOut, final boolean trade, final MapleCharacter chr) {
        addItemInfo(w, item, zeroPosition, leaveOut, trade, false, chr);
    }

    public static void encodeItemStats(Equip equip, WritingPacket w) {

        int equipStats = 0;
        try {
            for (EquipStats equipstat : EquipStats.values()) {
                switch (equipstat.name()) {
                    case "UPGRADE": {
                        equipStats |= equip.getUpgradeSlots() > 0 ? EquipStats.UPGRADE.getValue() : 0;
                        break;
                    }
                    case "LEVEL":
                        equipStats |= equip.getLevel() > 0 ? EquipStats.LEVEL.getValue() : 0;
                        break;
                    case "STR":
                        equipStats |= equip.getStr() > 0 ? EquipStats.STR.getValue() : 0;
                        break;
                    case "DEX":
                        equipStats |= equip.getDex() > 0 ? EquipStats.DEX.getValue() : 0;
                        break;
                    case "INT":
                        equipStats |= equip.getInt() > 0 ? EquipStats.INT.getValue() : 0;
                        break;
                    case "LUK":
                        equipStats |= equip.getLuk() > 0 ? EquipStats.LUK.getValue() : 0;
                        break;
                    case "HP":
                        equipStats |= equip.getHp() > 0 ? EquipStats.HP.getValue() : 0;
                        break;
                    case "MP":
                        equipStats |= equip.getMp() > 0 ? EquipStats.MP.getValue() : 0;
                        break;
                    case "WATK":
                        equipStats |= equip.getWatk() > 0 ? EquipStats.WATK.getValue() : 0;
                        break;
                    case "MATK":
                        equipStats |= equip.getMatk() > 0 ? EquipStats.MATK.getValue() : 0;
                        break;
                    case "WDEF":
                        equipStats |= equip.getWdef() > 0 ? EquipStats.WDEF.getValue() : 0;
                        break;
                    /*
				 * case "MDEF": equipStats |= equip.getMdef() > 0 ? EquipStats.MDEF.getValue() :
				 * 0; break; case "ACC": equipStats |= equip.getAcc() > 0 ?
				 * EquipStats.ACC.getValue() : 0; break; case "AVOID": equipStats |=
				 * equip.getAvoid() > 0 ? EquipStats.AVOID.getValue() : 0; break; case "HANDS":
				 * equipStats |= equip.getHands() > 0 ? EquipStats.HANDS.getValue() : 0; break;
				 * case "SPEED": equipStats |= equip.getSpeed() > 0 ?
				 * EquipStats.SPEED.getValue() : 0; break; case "JUMP": equipStats |=
				 * equip.getJump() > 0 ? EquipStats.JUMP.getValue() : 0; break;
                     */
                    case "FLAG":
                        equipStats |= equip.getFlag() > 0 ? EquipStats.FLAG.getValue() : 0;
                        break;
                    case "ITEMLEVEL":
                        equipStats |= equip.getItemLevel() != 0 ? EquipStats.ITEMLEVEL.getValue() : 0;
                        break;
                    // case "ITEMEXP":
                    // equipStats |= equip.getItemEXP() > 0 ? EquipStats.ITEMEXP.getValue() : 0;
                    // break;
                    case "DURABILITY":
                        equipStats |= equip.getDurability() != -1 ? EquipStats.DURABILITY.getValue() : 0;
                        break;
                    case "HAMMER":
                        equipStats |= equip.getViciousHammer() > 0 ? EquipStats.HAMMER.getValue() : 0;
                        break;
                    case "DOWNLEVEL":
                        equipStats |= equip.getDownLevel() > 0 ? EquipStats.DOWNLEVEL.getValue() : 0;
                        break;
                    case "ITEMTRACE":
                        equipStats |= (equip.getItemTrace() > 0 ? EquipStats.ITEMTRACE.getValue() : 0);
                        break;
                    case "BOSSDAMAGE":
                        equipStats |= equip.getBossDamage() > 0 ? EquipStats.BOSSDAMAGE.getValue() : 0;
                        break;
                    case "IGNOREWDEF":
                        equipStats |= equip.getIgnoreWdef() > 0 ? EquipStats.IGNOREWDEF.getValue() : 0;
                        break;
                }
            }
            w.writeInt(equipStats);
            for (EquipStats equipstat : EquipStats.values()) {
                switch (equipstat.name()) {
                    case "UPGRADE": {
                        w.write(equip.getUpgradeSlots() > 0 ? equip.getUpgradeSlots() : -88888);
                        break;
                    }
                    case "LEVEL":
                        w.write(equip.getLevel() > 0 ? equip.getLevel() : -88888);
                        break;
                    case "STR":
                        w.writeShort(equip.getStr() > 0 ? equip.getStr() : -88888);
                        break;
                    case "DEX":
                        w.writeShort(equip.getDex() > 0 ? equip.getDex() : -88888);
                        break;
                    case "INT":
                        w.writeShort(equip.getInt() > 0 ? equip.getInt() : -88888);
                        break;
                    case "LUK":
                        w.writeShort(equip.getLuk() > 0 ? equip.getLuk() : -88888);
                        break;
                    case "HP":
                        w.writeShort(equip.getHp() > 0 ? equip.getHp() : -88888);
                        break;
                    case "MP":
                        w.writeShort(equip.getMp() > 0 ? equip.getMp() : -88888);
                        break;
                    case "WATK":
                        w.writeShort(equip.getWatk() > 0 ? equip.getWatk() : -88888);
                        break;
                    case "MATK":
                        w.writeShort(equip.getMatk() > 0 ? equip.getMatk() : -88888);
                        break;
                    case "WDEF":
                        w.writeShort(equip.getWdef() > 0 ? equip.getWdef() : -88888);
                        break;
                    /*
				 * case "MDEF": w.writeShort(equip.getMdef() > 0 ? equip.getMdef() : -88888);
				 * break; case "ACC": w.writeShort(equip.getAcc() > 0 ? equip.getAcc() :
				 * -88888); break; case "AVOID": w.writeShort(equip.getAvoid() > 0 ?
				 * equip.getAvoid() : -88888); break; case "HANDS":
				 * w.writeShort(equip.getHands() > 0 ? equip.getHands() : -88888); break; case
				 * "SPEED": w.writeShort(equip.getSpeed() > 0 ? equip.getSpeed() : -88888);
				 * break; case "JUMP": w.writeShort(equip.getJump() > 0 ? equip.getJump() :
				 * -88888); break;
                     */
                    case "FLAG":
                        w.writeShort(equip.getFlag() > 0 ? equip.getFlag() : -88888);
                        break;
                    case "ITEMLEVEL":
                        w.write(equip.getItemLevel() != 0 ? equip.getItemLevel() : -88888);
                        break;
                    // case "ITEMEXP":
                    // w.writeLong(GameConstants.getEquipExpPercentage(equip) > 0 ?
                    // GameConstants.getEquipExpPercentage(equip) : -88888);
                    // break;
                    case "DURABILITY":
                        w.writeInt(equip.getDurability() != -1 ? equip.getDurability() : -88888);
                        break;
                    case "HAMMER":
                        w.writeInt(equip.getViciousHammer() > 0 ? equip.getViciousHammer() : -88888);
                        break;
                    case "DOWNLEVEL":
                        w.write(equip.getDownLevel() > 0 ? equip.getDownLevel() : -88888);
                        break;
                    case "ITEMTRACE":
                        w.writeShort(equip.getItemTrace() > 0 ? 0x88 : -88888);
                        break;
                    case "BOSSDAMAGE":
                        w.write(equip.getBossDamage() > 0 ? equip.getBossDamage() : -88888);
                        break;
                    case "IGNOREWDEF":
                        w.write(equip.getIgnoreWdef() > 0 ? equip.getIgnoreWdef() : -88888);
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static final void addItemInfo(final WritingPacket w, final IItem item, final boolean zeroPosition,
            final boolean leaveOut, final boolean trade, final boolean bagSlot, final MapleCharacter chr) {
        short pos = item.getPosition();
        if (zeroPosition) {
            if (!leaveOut) {
                w.write(0);
            }
        } else {
            if (pos <= -1) {
                pos *= -1;
                if (pos > 100 && pos < 1000) {
                    pos -= 100;
                }
            }
            if (bagSlot) {
                w.writeInt((pos % 100) - 1);
            } else if (!trade && item.getType() == 1) {
                w.writeShort(pos);
            } else {
                w.write(pos);
            }
        }
        w.write(item.getPet() != null ? 3 : item.getType());
        w.writeInt(item.getItemId());

        if (item.getPet() != null) { // Pet
            final MaplePet pet = item.getPet();
            addPetItemInfo(chr, w, pet, true, false);
            return;
        } else if (item.isCash()) { // Cash
            w.write(1);
            w.writeLong(item.getUniqueId());
        } else {
            w.write(0);
        }

        w.writeLong(getTime(item.getExpiration() <= System.currentTimeMillis() ? -1 : item.getExpiration()));
        w.writeInt(chr == null ? -1 : chr.getExtendedSlots().indexOf(item.getUniqueId()));
        w.write(0); // 316
        if (item.getType() == 1) {
            final Equip equip = (Equip) item;

            encodeItemStats(equip, w);

            int value = 4;
            if (equip.getAllDamageP() != 0) {
                value += 1;
            }
            if (equip.getAllStatP() != 0) {
                value += 2;
            }
            if (equip.getFire() >= 0) {
                value += 8;
            }
            // if (equip.getEnhance() > 25) {
            value += 16;
            // }
            w.writeInt(value);
            if (equip.getAllDamageP() != 0) {
                w.write(equip.getAllDamageP());
            }
            if (equip.getAllStatP() != 0) {
                w.write(equip.getAllStatP());
            }
            w.write(equip.getFire() == 0 ? -1 : equip.getFire());
            if (equip.getFire() >= 0) {
                w.writeLong(Randomizer.nextInt());
            }
            // if (equip.getEnhance() > 25) {
            w.writeInt(0x100); // +0x1000 : Handing down of equipment
            // }

            w.writeMapleAsciiString(equip.getOwner());
            w.write(equip.getState());
            w.write(equip.getEnhance() > 25 ? equip.getEnhance() - 25 : equip.getEnhance());
            w.writeShort(equip.getPotential1());
            w.writeShort(equip.getPotential2());
            w.writeShort(equip.getPotential3());
            w.writeShort(equip.getPotential4());
            w.writeShort(equip.getPotential5());
            w.writeShort(equip.getPotential6());
            w.writeShort(equip.getPotential7());
            if (!equip.isCash()) {
                w.writeLong(-1);
            }
            w.writeLong(getTime(-2));
            w.writeInt(-1);
            w.writeLong(0);
            w.writeLong(getTime(-2));

            w.writeInt(0);

            w.writeInt(0);
            w.writeInt(0);
            w.writeInt(0);

            w.writeShort(equip.getSoulName());
            w.writeShort(equip.getSoulEnchanter());
            w.writeShort(equip.getSoulPotential());
            if (GameConstants.isArcaneSymbol(equip.getItemId())) {
                w.writeShort(equip.getArc()); // Arcane force
                w.writeInt(equip.getArcEXP()); // Growth
                w.writeShort(equip.getArcLevel()); // level
            }
            w.write(HexTool.getByteArrayFromHexString("FF FF 00 80 05 BB 46 E6 17 02 00 40 E0 FD 3B 37 4F 01 00 80 05 BB 46 E6 17 02"));

        } else {
            w.writeShort(item.getQuantity());
            w.writeMapleAsciiString(item.getOwner());
            w.writeShort(item.getFlag());
            w.writeInt(0);
            if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
                w.writeInt(2);
                w.writeShort(0x54);
                w.write(0);
                w.write(0x34);
            }
        }
    }

    public static final void addCoreInfo(WritingPacket w, MapleCharacter player) {
        w.write(0);
        w.write(0);
    }

    public static final void addPetItemInfo(final MapleCharacter player, final WritingPacket w, MaplePet pet, boolean unequip, boolean petLoot) {
        w.write(1);
        w.writeLong(pet.getUniqueId());
        w.writeLong(getTime(-1));
        w.writeInt(-1);
        w.write(1);
        w.writeAsciiString(pet.getName(), 13);
        w.write(pet.getLevel());
        w.writeShort(pet.getCloseness());
        w.write(pet.getFullness());
        w.writeLong(getTime(pet.getExpireDate()));
        w.writeShort(0);
        w.writeShort(pet.getSkillValue());
        w.writeShort(0);
        w.writeInt(0);
        w.write(unequip ? 0 : (player.getPetIndex(pet) + 1));
        w.writeInt(pet.getBuffSkillId());
        w.writeInt(-1); // +168
        w.writeShort(100);
        w.writeShort(0);
    }
    
     /*   public static final void addPetItemInfo(final MapleCharacter player, final WritingPacket w, MaplePet pet, boolean unequip, boolean petLoot) {
        w.write(1);
        w.writeLong(pet.getUniqueId());
        w.writeLong(getTime(-1));
        w.writeInt(-1);
        w.writeAsciiString(pet.getName(), 13);
        w.write(pet.getLevel());
        w.writeShort(pet.getCloseness());
        w.write(pet.getFullness());
        w.writeLong(getTime(pet.getExpireDate()));
        w.writeShort(0);
        w.writeShort(101);
        w.writeShort(13326);
        w.writeInt(0);
        w.write(unequip ? 0 : (player.getPetIndex(pet) + 1));
        w.writeInt(pet.getBuffSkillId());
        w.writeInt(-1); //+168
        w.writeShort(100);
    }*/

    public static final void serializeMovementList(final WritingPacket packet, final List<LifeMovementFragment> moves) {
        packet.write(moves.size());
        for (LifeMovementFragment move : moves) {
            move.serialize(packet);
        }
    }

    public static final void addInteraction(final WritingPacket packet, IMapleCharacterShop shop) {
        packet.write(shop.getShopType());
        packet.writeInt(((AbstractPlayerStore) shop).getObjectId());
        packet.writeMapleAsciiString(shop.getDescription());
        if (shop.getShopType() != IMapleCharacterShop.HIRED_MERCHANT) {
            packet.write(shop.getPassword().length() > 0 ? 1 : 0); // password = false
        }
        packet.write(shop.getItemId() % 10);
        packet.write(shop.getSize());
        packet.write(shop.getMaxSize()); // full slots... 4 = 4-1=3 = has slots, 1-1=0 = no slots
        if (shop.getShopType() != IMapleCharacterShop.HIRED_MERCHANT) {
            packet.write(shop.isOpen() ? 0 : 1);
        }
    }

    public static byte[] convertFromBigInteger(BigInteger bi, int bits) {
        byte[] retVal = new byte[bits / 8];
        byte[] originalData = bi.toByteArray();

        for (int i = 0; i < originalData.length; i++) {
            retVal[i] = originalData[originalData.length - 1 - i];
        }

        return retVal;
    }

    public static <E extends GlobalBuffStat> void writeSingleMask(WritingPacket mplew, E statup) {
        mplew.write(convertFromBigInteger(statup.getBigValue(), BuffStats.BIT_COUNT));
    }

    public static <E extends GlobalBuffStat> void writeMask(WritingPacket mplew, Collection<E> statups) {
        BigInteger bi = BigInteger.valueOf(0);

        for (GlobalBuffStat statup : statups) {
            bi = bi.or(statup.getBigValue());
        }

        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));
    }

    public static <E extends GlobalBuffStat> BigInteger writeBuffMask(WritingPacket mplew, Collection<Triple<E, Integer, Boolean>> statups) {
        BigInteger bi = BigInteger.valueOf(0);
        for (Triple statup : statups) {
            bi = bi.or(((GlobalBuffStat) statup.first).getBigValue());
        }
        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));
        return bi;
    }

    public static <E extends GlobalBuffStat> BigInteger writeBuffMask(WritingPacket mplew, BigInteger bi) {
        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));
        return bi;
    }

    public static BigInteger writeBuffStats(WritingPacket mplew,
            Collection<Triple<BuffStats, Integer, Boolean>> statups) {
        BigInteger bi = BigInteger.valueOf(0);

        for (Triple statup : statups) {
            BuffStats buff = (BuffStats) statup.first;

            if (buff.getNum() > BuffStats.BIT_COUNT) {
                System.out.println("Invalid buffstat: " + buff.name());
                FileoutputUtil.logToFile("InvalidBuffStat.txt", "Invalid buffstat: " + buff.name());

                continue;
            }

            if (buff.getNum_() == 999) {
                System.out.println("Invalid buffstat: " + buff.name());
                FileoutputUtil.logToFile("InvalidBuffStat.txt", "Invalid buffstat: " + buff.name());
                continue;
            }

            bi = bi.or(buff.getBigValue());
        }

        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));

        return bi;
    }

    public static BigInteger writeBuffStatsSub(WritingPacket mplew,
            Collection<Triple<BuffStats, Integer, Boolean>> statups) {
        BigInteger bi = BigInteger.valueOf(0);

        for (Triple statup : statups) {
            BuffStats buff = (BuffStats) statup.first;

            if (buff.getNum() > BuffStats.BIT_COUNT) {
                System.out.println("Invalid buffstat: " + buff.name());
                FileoutputUtil.logToFile("InvalidBuffStat.txt", "Invalid buffstat: " + buff.name());

                continue;
            }

            if (buff.getNum_() == 999) {
                System.out.println("Invalid buffstat: " + buff.name());
                FileoutputUtil.logToFile("InvalidBuffStat.txt", "Invalid buffstat: " + buff.name());
                continue;
            }

            if (buff.equals(BuffStats.AuraWeapon)) {
                continue;
            }

            bi = bi.or(buff.getBigValue());
        }

        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));

        return bi;
    }

    public static <E extends GlobalBuffStat> BigInteger writeBuffMask(WritingPacket mplew,
            Collection<Triple<BuffStats, Integer, Boolean>> statups, List<BuffStats> blockStat) {
        BigInteger bi = BigInteger.valueOf(0);

        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (blockStat.contains(statup.first)) {
                continue;
            }
            bi = bi.or(((GlobalBuffStat) statup.first).getBigValue());
        }

        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));

        return bi;
    }

    public static <E extends GlobalBuffStat> void writeBuffMask(WritingPacket mplew, Map<E, Integer> statups) {
        BigInteger bi = BigInteger.valueOf(0);

        for (GlobalBuffStat statup : statups.keySet()) {
            bi = bi.or(statup.getBigValue());
        }

        mplew.write(convertFromBigInteger(bi, BuffStats.BIT_COUNT));
    }

    public static final void addShopItemInfo(final WritingPacket packet, MapleShopItem item, MapleClient c,
            final IItem i, int sid) {
        packet.writeInt(0); // 316
        packet.writeInt(item.getItemId());
        packet.writeInt(item.getTab());
        packet.writeInt(0);
        packet.writeInt(0);
        packet.writeInt(0); // 316
        packet.writeInt(item.getPriceQuantity() > 0 ? 0 : item.getPrice());
        packet.writeInt(0); // 316
        packet.writeInt(0); // 316
        packet.writeInt(item.getPriceQuantity() > 0 ? item.getPrice() : 0);
        packet.writeInt(item.getPriceQuantity());
        packet.writeInt(0);
        packet.write(ServerConstants.shopSale > 0);
        if (ServerConstants.shopSale > 0) {
            ///////////
            packet.writeInt(ServerConstants.shopSale);
            packet.write(1);
            packet.write(0);
            packet.writeMapleAsciiString("");
            packet.writeInt(0);
            packet.writeMapleAsciiString("");
            packet.writeLong(ZERO_TIME);
            packet.writeLong(ZERO_TIME);
            packet.writeMapleAsciiString("");
            packet.writeInt(2);
            packet.writeLong(PacketProvider.getTime(System.currentTimeMillis()));
            packet.writeLong(ZERO_TIME);
            /////////////////
        }
        packet.writeInt(0); // quantity left
        packet.writeInt(0);
        packet.writeInt(0); // 316
        packet.writeShort(0);
        packet.writeShort(0);
        packet.write(0);
        packet.write(0);
        packet.writeLong(ZERO_TIME);
        packet.writeLong(MAX_TIME);
        packet.writeInt(0);
        packet.writeShort(1);
        packet.write(0);
        packet.writeInt(item.getPriceQuantity() > 0 ? item.getPrice() : 0);
        packet.writeMapleAsciiString("");
        packet.writeInt(0);
        packet.writeInt(0);
        packet.write(0); // 316
        if (!GameConstants.isThrowingStar(item.getItemId()) && !GameConstants.isBullet(item.getItemId())) {
            packet.writeShort(item.getQuantity() > 1 ? item.getQuantity() : 1);
            packet.writeShort(item.getBuyable());
        } else {
            packet.writeAsciiString("333333");
            packet.writeShort(BitTools.doubleToShortBits(ItemInformation.getInstance().getPrice(item.getItemId())));
            packet.writeShort(ItemInformation.getInstance().getSlotMax(c, item.getItemId()));
        }
        packet.writeLong(MAX_TIME);
        packet.write(i == null ? 0 : 1);
        if (i != null) {
            addItemInfo(packet, i, true, true, c.getPlayer());
        }
    }

    public static boolean isContainsBuffStat(final BuffStats buff, List<Triple<BuffStats, Integer, Boolean>> statups) {
        for (Triple<BuffStats, Integer, Boolean> stat : statups) {
            if (stat.getFirst().equals(buff)) {
                return true;
            }
        }
        return false;
    }

    public static final void LIVE_EVENT_DECODE(final WritingPacket p) {
        p.writeMapleAsciiString("테스트"); // sName
        p.writeInt(1); // nCategory
        p.writeInt(1); // nEventType
        p.writeInt(1); // nEventValue
        p.writeInt(20170901); // nDateStart
        p.writeInt(20180901); // nDateEnd
        p.writeInt(0); // nTimeStart
        p.writeInt(0); // nTimeEnd
        p.writeInt(10); // nMinLevel
        p.writeMapleAsciiString("테스트");
    }

    public static void ArcaneSymbol(final WritingPacket mplew, IItem item) {
        Equip equip = (Equip) item;
        mplew.writeInt(0);
        mplew.writeInt(equip.getArcLevel()); // level
        mplew.writeInt((equip.getArcLevel() * equip.getArcLevel()) + 11); // Growth
        mplew.writeLong(12440000 + (6600000 * equip.getArcLevel())); // Required
        mplew.writeLong(0);
        
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
        mplew.writeShort(0);
    }
}
