package handlers.GlobalHandler;

import client.Character.MapleCharacter;
import java.math.BigInteger;

import client.MapleClient;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.UIPacket;
import server.Items.InventoryManipulator;
import server.MonsterCollection;
import server.MonsterCollectionRegion;
import server.MonsterCollectionRegion.ClearQuest.ClearQuestSub;
import server.MonsterCollectionRegion.CollectionField;
import server.MonsterCollectionRegion.CollectionField.CollectionGroup;
import server.MonsterCollectionRegion.CollectionField.CollectionGroup.CollectionMob;

public class MonsterCollectionHandler {

    public static int getCollectionId(int regionId, int sessionId) {
        return sessionId + 100 * (regionId + 1000);
    }

    public static void handleMonsterCollection(ReadingMaple slea, MapleClient c) {
        final MapleCharacter chr = c.getPlayer();

        int type = slea.readInt();

        if (type == 99) {
            // special
            return;
        }

        int regionId = slea.readInt();
        int sessionId = slea.readInt();
        int groupId = slea.readInt();
        slea.readInt();
        slea.readInt();

        if (type == 0) {// Give reward
            String key = "mc" + getCollectionId(regionId, sessionId);

            if (chr.getKeyValue(key) == null) {
                c.send(UIPacket.OnMonsterCollectionResult(2));

                return;
            }

            BigInteger flag = new BigInteger(new StringBuilder(chr.getKeyValue(key)).reverse().toString(), 16);
            CollectionGroup group = MonsterCollection.getInstance().getCollectionGroup(regionId, sessionId, groupId);

            if (group == null) {
                c.send(UIPacket.OnMonsterCollectionResult(1));

                return;
            }

            for (CollectionMob mob : group.mobs) {

                if (mob.isSkip()) {
                    continue;
                }

                if (flag.and(BigInteger.ONE.shiftLeft(mob.convertMobFlag())).compareTo(BigInteger.ZERO) == 0) {
                    c.send(UIPacket.OnMonsterCollectionResult(2));

                    return;
                }
            }

            String keyForRecords = "mr" + group.recordID;
            String data = String.format("G:%d:%d:%d:%d=1", regionId, sessionId, groupId, 0);

            if (chr.getKeyValue(keyForRecords) != null && chr.getKeyValue(keyForRecords).contains(data)) {
                c.send(UIPacket.OnMonsterCollectionResult(4));

                return;
            }

            if (!InventoryManipulator.checkSpace(c, group.rewardID, group.rewardCount, "")) {
                c.send(UIPacket.OnMonsterCollectionResult(3, group.rewardID / 1000000, group.rewardCount));

                return;
            }

            chr.gainItem(group.rewardID, group.rewardCount);

            if (chr.getKeyValue(keyForRecords) != null) {
                data = chr.getKeyValue(keyForRecords).concat(";").concat(data);
            }

            chr.setKeyValue(keyForRecords, data);

            c.send(UIPacket.OnMonsterCollectionResult(0));
            c.send(UIPacket.OnCollectionRecordMessage(group.recordID, data));
        } else if (type == 1) {
            CollectionField session = MonsterCollection.getInstance().getCollectionSession(regionId, sessionId);
            String key = "mc" + getCollectionId(regionId, sessionId);

            if (chr.getKeyValue(key) == null) {
                c.send(UIPacket.OnMonsterCollectionResult(2));

                return;
            }

            BigInteger flag = new BigInteger(new StringBuilder(chr.getKeyValue(key)).reverse().toString(), 16);

            for (CollectionGroup group : session.groups) {
                if (group == null) {
                    c.send(UIPacket.OnMonsterCollectionResult(1));

                    return;
                }

                for (CollectionMob mob : group.mobs) {

                    if (mob.isSkip()) {
                        continue;
                    }

                    if (flag.and(BigInteger.ONE.shiftLeft(mob.convertMobFlag())).compareTo(BigInteger.ZERO) == 0) {
                        c.send(UIPacket.OnMonsterCollectionResult(2));

                        return;
                    }
                }
            }

            String keyForRecords = "mr" + session.recordID;
            String data = String.format("S:%d:%d:%d:%d=1", regionId, sessionId, 0, 0);

            if (chr.getKeyValue(keyForRecords) != null && chr.getKeyValue(keyForRecords).contains(data)) {
                c.send(UIPacket.OnMonsterCollectionResult(4));

                return;
            }

            if (!InventoryManipulator.checkSpace(c, session.rewardID, 1, "")) {
                c.send(UIPacket.OnMonsterCollectionResult(3, session.rewardID / 1000000, 1));

                return;
            }

            chr.gainItem(session.rewardID, 1);

            if (chr.getKeyValue(keyForRecords) != null) {
                data = chr.getKeyValue(keyForRecords).concat(";").concat(data);
            }

            chr.setKeyValue(keyForRecords, data);

            c.send(UIPacket.OnMonsterCollectionResult(0));
            c.send(UIPacket.OnCollectionRecordMessage(session.recordID, data));
        } else if (type == 3) {
            MonsterCollectionRegion region = MonsterCollection.getInstance().getCollectionRegion(regionId);
            int collectedCount = 0;

            for (CollectionField session : region.fields) {
                String key = "mc" + getCollectionId(regionId, session.sessionID);

                if (chr.getKeyValue(key) == null) {
                    continue;
                }

                BigInteger flag = new BigInteger(new StringBuilder(chr.getKeyValue(key)).reverse().toString(), 16);

                for (CollectionGroup group : session.groups) {
                    for (CollectionMob mob : group.mobs) {
                        if (mob.isSkip()) {
                            continue;
                        }

                        if (flag.and(BigInteger.ONE.shiftLeft(mob.convertMobFlag())).compareTo(BigInteger.ZERO) != 0) {
                            collectedCount++;
                        }
                    }
                }
            }

            boolean itemGiven = false;

            for (ClearQuestSub clearQuestData : region.clearQuest.clearQuestSub) {
                String keyForRecords = "mr" + clearQuestData.recordID;
                String data = String.format("cc%d=1", clearQuestData.clearCount);

                if (chr.getKeyValue(keyForRecords) != null && chr.getKeyValue(keyForRecords).contains(data)) {
                    continue;
                }

                if (collectedCount < clearQuestData.clearCount) {
                    c.send(UIPacket.OnMonsterCollectionResult(2));

                    return;
                }

                if (!InventoryManipulator.checkSpace(c, region.rewardID, 1, "")) {
                    c.send(UIPacket.OnMonsterCollectionResult(3, region.rewardID / 1000000, 1));

                    return;
                }

                chr.gainItem(clearQuestData.rewardID, 1);

                if (chr.getKeyValue(keyForRecords) != null) {
                    data = chr.getKeyValue(keyForRecords).concat(";").concat(data);
                }

                chr.setKeyValue(keyForRecords, data);

                c.send(UIPacket.OnMonsterCollectionResult(0));
                c.send(UIPacket.OnCollectionRecordMessage(clearQuestData.recordID, data));

                itemGiven = true;
                break;
            }

            if (!itemGiven) {
                c.send(UIPacket.OnMonsterCollectionResult(4));
            }
        }
    }
}
