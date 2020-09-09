package server;

import client.Character.MapleCharacter;
import constants.ServerConstants;

public class MapleSlideMenu {

    public static class SlideMenu0 {

        public static final int version = ServerConstants.MAPLE_VERSION;
        public static final int above = 0xFF;

        public static enum DimensionalMirror {


            PQ0(0, "Ariant Coliseum", 682020000, 3, 20, 30, 0, 0, false),
            PQ1(1, "Mu Lung Dojo", 682020000, 4, 90, above, 0, 0, true),
            PQ2(2, "Monster Carnival", 0, 0, 30, 50, 0, 0, false),
            PQ3(3, "Monster Carnival 2", 0, 0, 50, 70, 0, 0, false),
            PQ4(4, "Sea of Fog", 0, 0, 120, 159, 0, 0, false),
            PQ5(5, "Nett's Pyramid", 0, 0, 60, 109, 0, 0, false),
            PQ6(6, "Kerning Subway", 0, 0, 25, 30, 0, 0, false),
            PQ7(7, "Happy Ville", 209000000, 0, 13, above, 0, 0, true),
            PQ8(8, "Golden Temple", 0, 0, 110, above, 0, 0, false),
            PQ9(9, "Moon Bunny", 0, 0, 50, above, 0, 0, false),
            PQ10(10, "First Time Together", 0, 0, 50, above, 0, 0, false),
            PQ11(11, "Dimensional Schism", 0, 0, 50, above, 0, 0, false),
            PQ12(12, "Forest of Poison Haze", 0, 0, 70, above, 0, 0, false),
            PQ13(13, "Remnant of the Goddess", 0, 0, 70, above, 0, 0, false),
            PQ14(14, "Lord Pirate", 0, 0, 70, above, 0, 0, false),
            PQ15(15, "Romeo and Juliet", 0, 0, 70, above, 0, 0, false),
            PQ16(16, "Resurrection of the Hoblin King", 0, 0, 120, above, 0, 0, false),
            PQ17(17, "Dragon's Nest", 0, 0, 120, above, 0, 0, false),
            PQ18(18, "Event Map", 0, 0, 10, above, 0, 0, false),
            PQ19(19, "Halloween Tree", 0, 0, 120, above, 0, 0, false),
            PQ20(20, "Event Map2", 0, 0, 10, above, 0, 0, false),
            PQ21(21, "Kenta in Danger", 0, 0, 120, above, 0, 0, false),
            PQ22(22, "Escape", 0, 0, 120, above, 0, 0, false),
            PQ23(23, "The Ice Knight's Curse", 0, 0, 20, above, 0, 0, false),
            PQ24(24, "Event Map3", 0, 0, 10, above, 0, 0, false),
            PQ25(25, "Maple Alliance", 0, 0, 10, above, 0, 0, false),
            PQ26(26, "Halloween Tree 2", 0, 0, 10, above, 0, 0, false),
            PQ27(27, "Fight for Azwan", 0, 0, 40, above, 0, 0, true),
            PQ28(28, "Golden Temple", 0, 0, 105, above, 0, 0, false),
            PQ29(29, "Spiegelmann's Gonzo Gallery", 0, 0, 50, 120, 0, 0, false),
            PQ30(30, "Battle Mode", 0, 0, 70, above, 0, 0, false),
            PQ31(31, "Zakura Castle", 0, 0, 200, above, 0, 0, false),
            PQ32(32, "Evolution Lab", 957000000, 0, 100, above, 1802, 1, true),
            PQ33(33, "Dimenstion Invasion", 0, 0, 140, above, 0, 0, false),
            PQ34(34, "Party Quest Beginner", 0, 0, 50, above, 0, 0, false),
            PQ35(35, "Party Quest Advanced", 0, 0, 70, above, 0, 0, false),
            PQ36(36, "Party Quests", 910002000, 2, 10, above, 0, 0, true),
            PQ37(37, "Tangyoon", 0, 0, 70, above, 0, 0, false),
            PQ38(38, "Crimsonwood Keep", 301000000, 0, 130, above, 0, 0, true),
            PQ40(40, "Momijigaoka", 0, 0, 0, above, 0, 0, true),
            PQ87(87, "Old Maple", 0, 0, 13, above, 0, 0, true),
            PQ88(88, "New Leaf City", 0, 0, 10, above, 0, 0, true),
            PQ89(89, "Haunted Mansion Secret Corridor", 0, 0, 10, above, 0, 0, false),
            PQ98(98, "Astaroth", 0, 0, 25, 40, 0, 0, true),
            PQ99(99, "Dragon Nest", 900020220, 0, 10, above, 0, 0, false),
            PQ200(200, "GOGO MESORANGERZ", 0, 0, 10, above, 0, 0, false),
            PQ201(201, "Twisted Aqua", 860000000, 6, 170, above, 0, 0, true),
            DEFAULT(Integer.MAX_VALUE, "Default Map", 999999999, 0, 0, 0, 0, 0, false);
            private int id, map, portal, minLevel, maxLevel, requieredQuest, requieredQuestState;
            private String name, portal_string;
            private boolean show;

            DimensionalMirror(int id, String name, int map, int portal, int minLevel, int maxLevel, int requieredQuest, int requieredQuestState, boolean show) {
                this.id = id;
                this.name = name;
                this.map = map;
                this.portal = portal;
                this.minLevel = minLevel;
                this.maxLevel = maxLevel;
                this.requieredQuest = requieredQuest;
                this.requieredQuestState = requieredQuestState;
                this.show = show;
            }

            DimensionalMirror(int id, String name, int map, String portal_string, int minLevel, int maxLevel, int requieredQuest, int requieredQuestState, boolean show) {
                this.id = id;
                this.name = name;
                this.map = map;
                this.portal_string = portal_string;
                this.minLevel = minLevel;
                this.maxLevel = maxLevel;
                this.requieredQuest = requieredQuest;
                this.requieredQuestState = requieredQuestState;
                this.show = show;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getMap() {
                return map;
            }

            public int getPortal() {
                return portal;
            }

            public String getPortalString() {
                return portal_string;
            }

            public int getMinLevel() {
                return minLevel;
            }

            public int getMaxLevel() {
                return maxLevel;
            }

            public int getRequieredQuest() {
                return requieredQuest;
            }

            public int getRequieredQuestState() {
                return requieredQuestState;
            }

            public boolean show() {
                return show;
            }

            public static DimensionalMirror getById(int id) {
                for (DimensionalMirror mirror : DimensionalMirror.values()) {
                    if (mirror.getId() == id) {
                        return mirror;
                    }
                }
                return DimensionalMirror.DEFAULT;
            }
        }

        public static String getSelectionInfo(MapleCharacter chr, int npcid) {
            String mapselect = "";
            for (DimensionalMirror mirror : DimensionalMirror.values()) {
                if (chr.getLevel() >= mirror.getMinLevel() && chr.getLevel() <= mirror.getMaxLevel() && mirror.show()) {
                    if ((chr.getQuestStatus(mirror.getRequieredQuest()) >= mirror.getRequieredQuestState()) || mirror.getRequieredQuest() == 0) {
                        if (mirror != DimensionalMirror.DEFAULT) {
                            mapselect += "#" + mirror.getId() + "#" + mirror.getName();
                        }
                    }
                }
            }
            if (mapselect.isEmpty() || mapselect.equals("")) {
                mapselect = "#-1# There are no locations you can move to.";
            }
            if (npcid == 9201231) {
                mapselect = "#87# Crack in the Dimensional Mirror";
            }
            return mapselect;
        }

        public static int getDataInteger(int id) {
            System.out.println(DimensionalMirror.getById(id).getMap());
            return DimensionalMirror.getById(id).getMap();
        }
    }

    public static class SlideMenu1 {

        public static enum TimeGate {
            //TODO: finish this(add quest ids and map ids)

            YEAR_2021(1, "Year 2021, Average Town", 0, 0, 0, 0),
            YEAR_2099(2, "Year 2099, Midnight Harbor", 0, 0, 0, 0),
            YEAR_2215(3, "Year 2215, Bombed City Center", 0, 0, 0, 0),
            YEAR_2216(4, "Year 2216, Ruined City", 0, 0, 0, 0),
            YEAR_2230(5, "Year 2230, Dangerous Tower", 0, 0, 0, 0),
            YEAR_2503(6, "Year 2503, Air Battleship Hermes", 0, 0, 0, 0);
            private int id, map, portal, requieredQuest, requieredQuestState;
            private String name;

            TimeGate(int id, String name, int map, int portal, int requieredQuest, int requieredQuestState) {
                this.id = id;
                this.name = name;
                this.map = map;
                this.requieredQuest = requieredQuest;
                this.requieredQuestState = requieredQuestState;
                this.portal = portal;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getMap() {
                return map;
            }

            public int getPortal() {
                return portal;
            }

            public int getRequieredQuest() {
                return requieredQuest;
            }

            public int getRequieredQuestState() {
                return requieredQuestState;
            }

            public static TimeGate getById(int id) {
                for (TimeGate gate : TimeGate.values()) {
                    if (gate.getId() == id) {
                        return gate;
                    }
                }
                return null; //default
            }
        }

        public static String getSelectionInfo(MapleCharacter chr, int npc) {
            String mapselect = "";
            for (TimeGate gate : TimeGate.values()) {
                if ((chr.getQuestStatus(gate.getRequieredQuest()) == gate.getRequieredQuestState()) || gate.getRequieredQuest() == 0) {
                    mapselect += "#" + gate.getId() + "#" + gate.getName();
                }
            }
            if (mapselect == null || mapselect.isEmpty()) {
                mapselect = "#-1# There are no locations you can move to.";
            }
            return mapselect;
        }

        public static int getDataInteger(int id) {
            return TimeGate.getById(id) != null ? TimeGate.getById(id).getMap() : TimeGate.YEAR_2099.getMap(); //Year 2099 as default
        }
    }

    public static class SlideMenu2 {

        public static String getSelectionInfo(MapleCharacter chr, int npc) {
            return null;
        }

        public static int getDataInteger(int id) {
            return 0;
        }
    }

    public static class SlideMenu3 {

        public static String getSelectionInfo(MapleCharacter chr, int npc) {
            return null;
        }

        public static int getDataInteger(int id) {
            return 0;
        }
    }

    public static class SlideMenu4 {

        public static enum BuffMenu {
            /*
             #0# Recover 50% HP
             #1# Recover 100% HP
             #2# MaxHP + 10000 (Duration: 10 min)
             #3# Weapon/Magic ATT + 30 (Duration: 10 min)
             #4# Weapon/Magic ATT + 60 (Duration: 10 min)
             #5# Weapon/Magic DEF + 2500 (Duration: 10 min)
             #6# Weapon/Magic DEF + 4000 (Duration: 10 min)
             #7# Accuracy/Avoidability + 2000 (Duration: 10 min)
             #8# Speed/Jump MAX (Duration: 10 min)
             #9# Attack Speed + 1 (Duration: 10 min)
             */

            BUFF0(0, "Recover 50% HP", 2022855),
            BUFF1(1, "Recover 100% HP", 2022856),
            BUFF2(2, "MaxHP + 10000 (Duration: 10 min)", 2022857),
            BUFF3(3, "Weapon/Magic ATT + 30 (Duration: 10 min)", 2022858),
            BUFF4(4, "Weapon/Magic ATT + 60 (Duration: 10 min)", 2022859),
            BUFF5(5, "Weapon/Magic DEF + 2500 (Duration: 10 min)", 2022860),
            BUFF6(6, "Weapon/Magic DEF + 4000 (Duration: 10 min)", 2022861),
            BUFF7(7, "Accuracy/Avoidability + 2000 (Duration: 10 min)", 2022862),
            BUFF8(8, "Speed/Jump MAX (Duration: 10 min)", 2022863),
            BUFF9(9, "Attack Speed + 1 (Duration: 10 min)", 2022864);
            private final int id, buff;
            private final String name;

            BuffMenu(int id, String name, int buff) {
                this.id = id;
                this.name = name;
                this.buff = buff;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getBuff() {
                return buff;
            }

            public static BuffMenu getById(int id) {
                for (BuffMenu buff : BuffMenu.values()) {
                    if (buff.getId() == id) {
                        return buff;
                    }
                }
                return null;
            }
        }

        public static String getSelectionInfo(MapleCharacter chr, int npc) {
            String buffselect = "";
            for (BuffMenu buffdata : BuffMenu.values()) {
                buffselect += "#" + buffdata.getId() + "# " + buffdata.getName();
            }
            return buffselect;
        }

        public static int getDataInteger(int id) {
            BuffMenu dataInteger = BuffMenu.getById(id);
            return dataInteger != null ? dataInteger.getBuff() : BuffMenu.BUFF0.getBuff();
        }
    }

    public static class SlideMenu5 {

        public static enum TownTeleport {

            TOWN_0(0, "Six Path Crossway", 0, 0),
            TOWN_1(1, "Henesys", 0, 0),
            TOWN_2(2, "Ellinia", 0, 0),
            TOWN_3(3, "Perion", 0, 0),
            TOWN_4(4, "Kerning City", 0, 0),
            TOWN_5(5, "Lith Harbor", 0, 0),
            TOWN_6(6, "Sleepywood", 0, 0),
            TOWN_7(7, "Nautilus", 0, 0),
            TOWN_8(8, "Ereve", 0, 0),
            TOWN_9(9, "Rien", 0, 0),
            TOWN_10(10, "Orbis", 0, 0),
            TOWN_11(11, "El Nath", 0, 0),
            TOWN_12(12, "Ludibrium", 0, 0),
            TOWN_13(13, "Omega Sector", 0, 0),
            TOWN_14(14, "Korean Folk Town", 0, 0),
            TOWN_15(15, "Aquarium", 0, 0),
            TOWN_16(16, "Leafre", 0, 0),
            TOWN_17(17, "Mu Lung", 0, 0),
            TOWN_18(18, "Herb Town", 0, 0),
            TOWN_19(19, "Ariant", 0, 0),
            TOWN_20(20, "Magatia", 0, 0),
            TOWN_21(21, "Edelstein", 0, 0),
            TOWN_22(22, "Elluel", 0, 0);
            private final int id, map, portal;
            private final String name;

            TownTeleport(int id, String name, int map, int portal) {
                this.id = id;
                this.name = name;
                this.map = map;
                this.portal = portal;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getMap() {
                return map;
            }

            public int getPortal() {
                return portal;
            }

            public static TownTeleport getById(int id) {
                for (TownTeleport town : TownTeleport.values()) {
                    if (town.getId() == id) {
                        return town;
                    }
                }
                return null;
            }
        }

        public static String getSelectionInfo(MapleCharacter chr, int npc) {
            String mapselect = "";
            for (TownTeleport gate : TownTeleport.values()) {
                mapselect += "#" + gate.getId() + "#" + gate.getName();
            }
            if (mapselect == null || "".equals(mapselect)) {
                mapselect = "#-1# There are no locations you can move to.";
            }
            return mapselect;
        }

        public static int getDataInteger(int id) {
            if (TownTeleport.getById(id) == null) {
                return -1;
            }
            return TownTeleport.getById(id).getMap();
        }
    }

    public static Class<?> getSlideMenu(int id) {
        final Class<?>[] slideMenus = new Class<?>[]{SlideMenu0.class,
            SlideMenu1.class, SlideMenu2.class, SlideMenu3.class,
            SlideMenu4.class, SlideMenu5.class};
        return slideMenus[id];
    }
}
