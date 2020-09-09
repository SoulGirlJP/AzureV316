package launcher.LauncherHandlers;

import java.util.ArrayList;

public class MapleNewCharJobType {

    public enum JobType {
        UltimateAdventurer(-1, 0, 410000002, true, true, false, false, false, false),
        Resistance(0, 3000, 410000002, true, false, false, false, false, false),
        Adventurer(1, 0, 410000002, false, true, false, false, false, false),
        Cygnus(2, 1000, 410000002, false, true, false, false, false, true),
        Aran(3, 2000, 410000002, true, true, false, false, true, false),
        Evan(4, 2001, 410000002, true, true, false, false, true, false),
        Mercedes(5, 2002, 410000002, false, false, false, false, false, false),
        Demon(6, 3001, 410000002, false, false, true, false, false, false),
        Phantom(7, 2003, 410000002, false, true, false, false, false, true),
        DualBlade(8, 0, 410000002, false, true, false, false, false, false),
        Mihile(9, 5000, 410000002, true, true, false, false, true, false),
        Luminous(10, 2004, 410000002, false, false, false, false, false, true),
        Kaiser(11, 6000, 410000002, false, true, false, false, false, false),
        AngelicBuster(12, 6001, 410000002, false, true, false, false, false, false),
        Cannoneer(13, 0, 410000002, true, true, false, false, true, false),
        Xenon(14, 3002, 410000002, true, true, true, false, false, false),
        Zero(15, 10112, 410000002, false, true, false, false, false, true),
        Shade(16, 2005, 410000002, false, false, false, false, true, true),
        Kinesis(18, 14000, 410000002, false, false, false, false, false, false),
        Kadena(19, 6002, 410000002, true, false, false, false, false, false),
        Iliume(20, 15000, 410000002, true, false, false, false, false, false),
        Ark(21, 15500, 410000002, true, false, true, false, false, false),
        PathFinder(22, 301, 410000002, true, false, false, true, false, false);
        public int type, id, map;
        public boolean hairColor, skinColor, faceMark, hat, bottom, cape;

        private JobType(int type, int id, int map, boolean hairColor, boolean skinColor, boolean faceMark, boolean hat,
                boolean bottom, boolean cape) {
            this.type = type;
            this.id = id;
            this.map = map;
            this.hairColor = hairColor;
            this.skinColor = skinColor;
            this.faceMark = faceMark;
            this.hat = hat;
            this.bottom = bottom;
            this.cape = cape;
        }

        public static JobType getByType(int g) {
            if (g == JobType.Cannoneer.type) {
                return JobType.Adventurer;
            }
            for (JobType e : JobType.values()) {
                if (e.type == g) {
                    return e;
                }
            }
            return null;
        }

        public static JobType getById(int g) {
            if (g == JobType.Adventurer.id) {
                return JobType.Adventurer;
            }
            for (JobType e : JobType.values()) {
                if (e.id == g) {
                    return e;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultFaceMark(JobType a) // demon, xenon
        {
            ArrayList list = new ArrayList();
            switch (a) {
                case Demon: {
                    list.add(1012276);
                    list.add(1012277);
                    list.add(1012278);
                    list.add(1012279);
                    list.add(1012280);
                    list.add(1012361);
                    break;
                }
                case Xenon: {
                    list.add(1012361);
                    list.add(1012363);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultBottom(JobType a) // Mihile, Evan, Aran
        {
            ArrayList list = new ArrayList();
            switch (a) {
                case Mihile: {
                    list.add(1061002);
                    list.add(1061008);
                    list.add(1060002);
                    list.add(1060006);
                    break;
                }
                case Aran: {
                    list.add(1062115);
                    break;
                }
                case Evan: {
                    list.add(1060138);
                    list.add(1061160);
                    break;
                }
                case Shade: {
                    list.add(1060183);
                    list.add(1061208);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultTop(JobType a) {
            ArrayList list = new ArrayList();
            switch (a) {
                case UltimateAdventurer:
                case Adventurer:
                case DualBlade:
                case Cannoneer: {
                    list.add(1051353);
                    list.add(1051354);
                    list.add(1051355);
                    list.add(1050286);
                    list.add(1050287);
                    list.add(1050288);
                    break;
                }
                case Cygnus: {
                    list.add(1051334);
                    list.add(1050272);
                    break;
                }
                case Zero: {
                    list.add(1052607);
                    list.add(1052606);
                    break;
                }
                case Aran: {
                    list.add(1042167);
                    break;
                }
                case Evan: {
                    list.add(1042180);
                    break;
                }
                case Mercedes: {
                    list.add(1051237);
                    list.add(1050192);
                    break;
                }
                case Phantom: {
                    list.add(1051272);
                    list.add(1050222);
                    break;
                }
                case Luminous: {
                    list.add(1052495);
                    break;
                }
                case Resistance: {
                    list.add(1051214);
                    list.add(1051215);
                    list.add(1051216);
                    list.add(1050173);
                    list.add(1050174);
                    list.add(1050175);
                    break;
                }
                case Demon: {
                    list.add(1051236);
                    list.add(1050191);
                    break;
                }
                case Xenon: {
                    list.add(1051307);
                    list.add(1050251);
                    break;
                }
                case Mihile: {
                    list.add(1041002);
                    list.add(1041006);
                    list.add(1041010);
                    list.add(1041011);
                    list.add(1040002);
                    list.add(1040006);
                    list.add(1040010);
                    break;
                }
                case Kaiser: {
                    list.add(1052529);
                    break;
                }
                case Kadena: {
                    list.add(1051532);
                    list.add(1050465);
                    break;
                }
                case AngelicBuster: {
                    list.add(1051293);
                    list.add(1052529);
                    break;
                }
                case Shade: {
                    list.add(1042288);
                    break;
                }
                case Iliume: {
                    list.add(1051534);
                    list.add(1050467);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultCape(JobType a) // Zero, Luminous, Phantom, Cygnus
        {
            ArrayList list = new ArrayList();
            switch (a) {
                case Cygnus: {
                    list.add(1102534);
                    break;
                }
                case Zero: {
                    list.add(1102552);
                    break;
                }
                case Luminous: {
                    list.add(1102442);
                    break;
                }
                case Phantom: {
                    list.add(1102347);
                    break;
                }
                case Kinesis: {
                    list.add(1051443);
                    list.add(1050374);
                    break;
                }
                case Shade: {
                    list.add(1102649);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultShoes(JobType a) {
            ArrayList list = new ArrayList();
            switch (a) {
                case UltimateAdventurer:
                case Adventurer:
                case DualBlade:
                case Cannoneer: {
                    list.add(1072833);
                    list.add(1072834);
                    break;
                }
                case Cygnus: {
                    list.add(1072785);
                    break;
                }
                case Zero: {
                    list.add(1072814);
                    break;
                }
                case Aran: {
                    list.add(1072383);
                    break;
                }
                case Evan: {
                    list.add(1072418);
                    break;
                }
                case Mercedes: {
                    list.add(1072519);
                    break;
                }
                case Phantom: {
                    list.add(1071035);
                    list.add(1070023);
                    break;
                }
                case Luminous: {
                    list.add(1072700);
                    break;
                }
                case Resistance: {
                    list.add(1072001);
                    list.add(1072005);
                    list.add(1072037);
                    list.add(1072038);
                    break;
                }
                case Demon: {
                    list.add(1072518);
                    break;
                }
                case Xenon: {
                    list.add(1072774);
                    break;
                }
                case Mihile: {
                    list.add(1072001);
                    list.add(1072005);
                    list.add(1072037);
                    list.add(1072038);
                    break;
                }
                case Kaiser: {
                    list.add(1072730);
                    break;
                }
                case Kadena: {
                    list.add(1073231);
                    break;
                }
                case AngelicBuster: {
                    list.add(1071041);
                    list.add(1072730);
                    break;
                }
                case Kinesis: {
                    list.add(1073081);
                    break;
                }
                case Shade: {
                    list.add(1072891);
                    break;
                }
                case Iliume: {
                    list.add(1073236);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultWeapon(JobType a) {
            ArrayList list = new ArrayList();
            switch (a) {
                case UltimateAdventurer:
                case Adventurer:
                case DualBlade:
                case Cannoneer:
                case Cygnus:
                case Resistance:
                case Mihile: {
                    list.add(1302000);
                    list.add(1312004);
                    list.add(1322005);
                    break;
                }
                case Aran: {
                    list.add(1442079);
                    break;
                }
                case Evan: {
                    list.add(1302132);
                    break;
                }
                case Mercedes: {
                    list.add(1522038);
                    break;
                }
                case Phantom: {
                    list.add(1362000);
                    break;
                }
                case Luminous: {
                    list.add(1212000);
                    break;
                }
                case Demon: {
                    list.add(1322123);
                    break;
                }
                case Xenon: {
                    list.add(1242000);
                    break;
                }
                case Kaiser: {
                    list.add(1402178);
                    break;
                }
                case Kadena: {
                    list.add(1272026);
                    break;
                }
                case Zero: {
                    list.add(1562000);
                    list.add(1572000);
                    break;
                }
                case AngelicBuster: {
                    list.add(1222000);
                    break;
                }
                case Kinesis: {
                    list.add(1262000);
                    break;
                }
                case Shade: {
                    list.add(1482191);
                    break;
                }
                case Iliume: {
                    list.add(1282021);
                    break;
                }
            }
            return list;
        }

        @SuppressWarnings("unchecked")
        public static ArrayList getDefaultShield(JobType a) {
            ArrayList list = new ArrayList();
            if (a == Demon) {
                list.add(1099001);
            }
            return list;
        }
    }
}
