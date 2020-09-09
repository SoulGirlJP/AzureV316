package server.Quests;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Character.MapleCharacter;
import client.MapleQuestStatus;
import constants.GameConstants;
import connections.Packets.MainPacketCreator;
import provider.Lib.Bin.ReadBin;
import scripting.NPC.NPCScriptManager;
import tools.Pair;

public class MapleQuest {

    private static final long serialVersionUID = 9179541993413738569L;

    private int id;

    private int viewMedalItem;
    private int selectedSkillID;

    private boolean autoStart;
    private boolean autoPreComplete;
    private boolean repeatable;
    private boolean customend;
    private boolean blocked;
    private boolean autoAccept;
    private boolean autoComplete;
    private boolean scriptedStart;

    private String name;

    private static final Map<Integer, MapleQuest> quests = new LinkedHashMap<>();
    private final Map<Integer, Integer> relevantMobs = new LinkedHashMap<>();

    private List<MapleQuestRequirement> startReqs = new LinkedList<>();
    private List<MapleQuestRequirement> completeReqs = new LinkedList<>();

    private List<MapleQuestAction> startActs = new LinkedList<>();
    private List<MapleQuestAction> completeActs = new LinkedList<>();

    private Map<String, List<Pair<String, Pair<String, Integer>>>> partyQuestInfo = new LinkedHashMap<>(); // [rank,
    // [more/less/equal,
    // [property,
    // value]]]

    protected MapleQuest(final int id) {
        this.id = id;
    }

    public static void a() {
        System.out.println("a");
    }

    public static void initQuests() throws IOException {

        ReadBin data = new ReadBin("Quest.wz/Check.dat");

        int size = data.readShort();
        for (int s = 0; s < size; s++) {
            int questid = data.readInt();

            MapleQuest ret = getQuest(questid);
            MapleQuestRequirement req = new MapleQuestRequirement(ret, data);

            if (req.getInterval() > 0) {
                ret.repeatable = true;
            }

            if (req.getNormalAutoStart()) {
                ret.repeatable = true;
                ret.autoStart = true;
            }

            if (!req.getStartScript().isEmpty()) {
                ret.scriptedStart = true;
            }

            if (!req.getEndScript().isEmpty()) {
                ret.customend = true;
            }

            if (!req.getMobs().isEmpty()) {
                for (Entry<Integer, Integer> mob : req.getMobs().entrySet()) {
                    ret.relevantMobs.put(mob.getKey(), mob.getValue());
                }
            }

            ret.startReqs.add(req);

            quests.put(questid, ret);
        }

        for (int s = 0; s < size; s++) {
            int questid = data.readInt();

            MapleQuest ret = getQuest(questid);
            MapleQuestRequirement req = new MapleQuestRequirement(ret, data);

            if (req.getInterval() > 0) {
                ret.repeatable = true;
            }

            if (req.getNormalAutoStart()) {
                ret.repeatable = true;
                ret.autoStart = true;
            }

            if (!req.getStartScript().isEmpty()) {
                ret.scriptedStart = true;
            }

            if (!req.getEndScript().isEmpty()) {
                ret.customend = true;
            }

            if (!req.getMobs().isEmpty()) {
                for (Entry<Integer, Integer> mob : req.getMobs().entrySet()) {
                    ret.relevantMobs.put(mob.getKey(), mob.getValue());
                }
            }

            ret.completeReqs.add(req);

            quests.put(questid, ret);
        }
        data.close();

        ReadBin act = new ReadBin("Quest.wz/Act.dat");

        size = act.readShort();
        for (int s = 0; s < size; s++) {
            int questid = act.readInt();

            MapleQuest ret = getQuest(questid);
            MapleQuestAction mqa = new MapleQuestAction(ret, act);

            ret.startActs.add(mqa);

            quests.put(questid, ret);
        }

        for (int s = 0; s < size; s++) {
            int questid = act.readInt();

            MapleQuest ret = getQuest(questid);
            MapleQuestAction mqa = new MapleQuestAction(ret, act);

            ret.completeActs.add(mqa);

            quests.put(questid, ret);
        }
        act.close();

        ReadBin qi = new ReadBin("Quest.wz/QuestInfo.dat");

        size = qi.readShort();
        for (short s = 0; s < size; s++) {
            int questid = qi.readInt();

            MapleQuest ret = getQuest(questid);

            ret.name = qi.readString();
            ret.autoStart = qi.readBool();
            ret.autoPreComplete = qi.readBool();
            ret.viewMedalItem = qi.readInt();
            ret.selectedSkillID = qi.readInt();
            ret.blocked = qi.readBool();
            ret.autoAccept = qi.readBool();
            ret.autoComplete = qi.readBool();

            quests.put(questid, ret);
        }
        qi.close();
        System.out.println("[Notice] " + quests.size() + "Quest loaded. \r\n");
    }

    private static MapleQuest getQuest(int questid) {
        MapleQuest ret = quests.get(questid);
        if (ret == null) {
            ret = new MapleQuest(questid);
        }

        return ret;
    }

    @Deprecated
    public List<Pair<String, Pair<String, Integer>>> getInfoByRank(final String rank) {
        return partyQuestInfo.get(rank);
    }

    @Deprecated
    public boolean isPartyQuest() {
        return partyQuestInfo.size() > 0;
    }

    public final int getSkillID() {
        return selectedSkillID;
    }

    public final String getName() {
        return name;
    }

    public final List<MapleQuestAction> getCompleteActs() {
        return completeActs;
    }

    /**
     * Create/Get an instance of MapleQuest.
     *
     * @param id
     * @return
     */
    public static MapleQuest getInstance(int id) {
        MapleQuest ret = quests.get(id);
        if (ret == null) {
            ret = new MapleQuest(id);
            quests.put(id, ret);
        }
        return ret;
    }

    public static Collection<MapleQuest> getAllInstances() {
        return quests.values();
    }

    public boolean canStart(MapleCharacter c, int npcid) {
        if (c.getQuest(this).getStatus() != 0 && !(c.getQuest(this).getStatus() == 2 && repeatable)) {
            return false;
        }
        if (blocked && !c.isGM()) {
            return false;
        }
        // if (autoAccept) {
        // return true; //need script
        // }
        for (MapleQuestRequirement r : startReqs) {

            if (npcid == 0) { // everyday. we don't want ok
                forceComplete(c, npcid);
                return false;
            }

            if (!r.check(c, npcid)) {
                return false;
            }
        }
        return true;
    }

    public boolean canComplete(MapleCharacter c, int npcid) {
        if (c.getQuest(this).getStatus() != 1) {
            return false;
        }
        if (blocked && !c.isGM()) {
            return false;
        }
        if (autoComplete && npcid > 0 && viewMedalItem <= 0) {
            forceComplete(c, npcid);
            return false; // skip script
        }
        for (MapleQuestRequirement r : completeReqs) {
            if (!r.check(c, npcid)) {
                return false;
            }
        }
        return true;
    }

    public final void RestoreLostItem(final MapleCharacter c, final int itemid) {
        if (blocked && !c.isGM()) {
            return;
        }
        for (final MapleQuestAction a : startActs) {
            if (a.RestoreLostItem(c, itemid)) {
                break;
            }
        }
    }

    public void start(MapleCharacter c, int npc) {
        if ((autoStart || checkNPCOnMap(c, npc)) && canStart(c, npc)) {

            for (MapleQuestAction a : startActs) {
                if (!a.checkEnd(c, null)) {
                    return;
                }
            }

            for (MapleQuestAction a : startActs) {
                a.runStart(c, null);
            }
            if (!customend) {
                if (NPCScriptManager.getInstance().UseScript(c.getClient(), getId())) {
                    NPCScriptManager.getInstance().startQuest(c.getClient(), npc, getId());
                } else {
                    forceStart(c, npc, null);
                }
            } else {
                if (NPCScriptManager.getInstance().UseScript(c.getClient(), getId())) {
                    NPCScriptManager.getInstance().startQuest(c.getClient(), npc, getId());
                } else {
                    forceStart(c, npc, null);
                }
            }
        }
    }

    public void complete(MapleCharacter c, int npc) {
        complete(c, npc, null);
    }

    public void complete(MapleCharacter c, int npc, Integer selection) {
        if (c.getMap() != null && (autoPreComplete || checkNPCOnMap(c, npc)) && canComplete(c, npc)) {

            for (MapleQuestAction a : completeActs) {
                if (!a.checkEnd(c, selection)) {
                    return;
                }
            }
            if (NPCScriptManager.getInstance().UseScript(c.getClient(), getId()) && npc != 0) {
                NPCScriptManager.getInstance().endQuest(c.getClient(), npc, getId(),
                        GameConstants.questReader.contains(this.id));
            }
            forceComplete(c, npc);
            for (MapleQuestAction a : completeActs) {
                a.runEnd(c, selection);
            }

            c.getClient().getSession().writeAndFlush(MainPacketCreator.showSpecialEffect(15)); // Quest completion
            c.getMap().broadcastMessage(c, MainPacketCreator.showSpecialEffect(c.getId(), 15), false);
        }
    }

    public void forfeit(MapleCharacter c) {
        if (c.getQuest(this).getStatus() != (byte) 1) {
            return;
        }
        final MapleQuestStatus oldStatus = c.getQuest(this);
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte) 0);
        newStatus.setForfeited(oldStatus.getForfeited() + 1);
        newStatus.setCompletionTime(oldStatus.getCompletionTime());
        c.updateQuest(newStatus);
    }

    public void forceStart(MapleCharacter c, int npc, String customData) {
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte) 1, npc);
        newStatus.setForfeited(c.getQuest(this).getForfeited());
        newStatus.setCompletionTime(c.getQuest(this).getCompletionTime());
        newStatus.setCustomData(customData);
        c.updateQuest(newStatus);
    }

    public void forceComplete(MapleCharacter c, int npc) {
        final MapleQuestStatus newStatus = new MapleQuestStatus(this, (byte) 2, npc);
        newStatus.setForfeited(c.getQuest(this).getForfeited());
        c.updateQuest(newStatus);
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getRelevantMobs() {
        return relevantMobs;
    }

    private boolean checkNPCOnMap(MapleCharacter player, int npcid) {
        // mir = 1013000
        return (GameConstants.isEvan(player.getJob()) && npcid == 1013000) || npcid == 9000040 || npcid == 9000066
                || (player.getMap() != null && player.getMap().containsNPC(npcid));
    }

    public int getMedalItem() {
        return viewMedalItem;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public static enum MedalQuest {

        Beginner(29005, 29015, 15, new int[]{100000000, 100020400, 100040000, 101000000, 101020300, 101040300,
            102000000, 102020500, 102030400, 102040200, 103000000, 103020200, 103030400, 103040000, 104000000,
            104020000, 106020100, 120000000, 120020400, 120030000}), ElNath(
                29006, 29012, 50,
                new int[]{200000000, 200010100, 200010300, 200080000, 200080100, 211000000, 211030000,
                    211040300, 211041200, 211041800}), LudusLake(
                29007, 29012, 40,
                new int[]{222000000, 222010400, 222020000, 220000000, 220020300, 220040200,
                    221020701, 221000000, 221030600, 221040400}), Underwater(29008, 29012,
                40,
                new int[]{230000000, 230010400, 230010200, 230010201,
                    230020000, 230020201, 230030100, 230040000, 230040200,
                    230040400}), MuLung(
                29009, 29012, 50,
                new int[]{251000000, 251010200, 251010402,
                    251010500, 250010500, 250010504,
                    250000000, 250010300, 250010304,
                    250020300}), NihalDesert(29010, 29012,
                70,
                new int[]{261030000,
                    261020401, 261020000,
                    261010100, 261000000,
                    260020700, 260020300,
                    260000000, 260010600,
                    260010300}), MinarForest(
                29011, 29012,
                70,
                new int[]{
                    240000000,
                    240010200,
                    240010800,
                    240020401,
                    240020101,
                    240030000,
                    240040400,
                    240040511,
                    240040521,
                    240050000}), Sleepywood(
                29014,
                29015,
                50,
                new int[]{
                    105000000,
                    105000000,
                    105010100,
                    105020100,
                    105020300,
                    105030000,
                    105030100,
                    105030300,
                    105030500,
                    105030500}); // repeated
        // map
        public int questid, level, lquestid;
        public int[] maps;

        private MedalQuest(int questid, int lquestid, int level, int[] maps) {
            this.questid = questid; // infoquest = questid -2005, customdata = questid -1995
            this.level = level;
            this.lquestid = lquestid;
            this.maps = maps; // note # of maps
        }
    }

    public boolean hasStartScript() {
        return scriptedStart;
    }

    public boolean hasEndScript() {
        return customend;
    }
}
