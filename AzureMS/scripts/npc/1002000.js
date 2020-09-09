// Job Choosing
// Script : 1002000


importPackage(java.sql);

importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.database);
importPackage(Packages.constants);

var transcendence = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY transcendence DESC LIMIT 10").executeQuery();

function start() {
var i = 0;
        var print = new StringBuilder();
        while (transcendence.next()) {
            i++;
            switch(transcendence.getInt("job")){

                case 100:
                    job = "Warrior";
                    break;
                case 200:
                    job = "Wizard";
                    break;
                case 300:
                    job = "Archer";
                    break;
                case 400:
                    job = "Thief";
                    break;
                case 500:
                    job = "Pirate";
                    break;

                case 110:
                case 111:
                case 112:
                    job = "Fighter";
                    break;

                case 120:
                case 121:
                case 122:
                    job = "Page";
                    break;

                case 130:
                case 131:
                case 132:
                    job = "Spearman";
                    break;

                case 210:
                case 211:
                case 212:
                    job = "Wizard (Fire, Poison)";
                    break;

                case 220:
                case 221:
                case 222:
                    job = "Wizard (Sun, Call)";
                    break;

                case 230:
                case 231:
                case 232:
                    job = "Cleric";
                    break;

                case 310:
                case 311:
                case 312:
                    job = "Hunter";
                    break;

                case 320:
                case 321:
                case 322:
                    job = "shooter";
                    break;

                case 410:
                case 411:
                case 412:
                    job = "Assassin";
                    break;

                case 420:
                case 421:
                case 422:
                    job = "Sheep";
                    break;
 
                case 430:
                case 431:
                case 432:
                case 433:
                case 434:
                    job = "Dual Blade";
                    break;

                case 510:
                case 511:
                case 512:
                    job = "Infighter";
                    break;

                case 520:
                case 521:
                case 522:
                    job = "Gunslinger";
                    break;

                case 530:
                case 531:
                case 532:
                    job = "Cannon shooter";
                    break;

                case 1100:
                case 1110:
                case 1111:
                case 1112:
                    job = "Soul Master";
                    break;

                case 1200:
                case 1210:
                case 1211:
                case 1212:
                    job = "Flame Wizard";
                    break;

                case 1300:
                case 1310:
                case 1311:
                case 1312:
                    job = "Windbreaker";
                    break;

                case 1400:
                case 1410:
                case 1411:
                case 1412:
                    job = "Night Walker";
                    break;

                case 1500:
                case 1510:
                case 1511:
                case 1512:
                    job = "Striker";
                    break;

                case 2000:
                case 2100:
                case 2110:
                case 2111:
                case 2112:
                    job = "Aran";
                    break;

                case 2001:
                case 2200:
                case 2210:
                case 2211:
                case 2212:
                case 2213:
                case 2214:
                case 2215:
                case 2216:
                case 2217:
                case 2218:
                    job = "Evan";
                    break;

                case 2002:
                case 2300:
                case 2310:
                case 2311:
                case 2312:
                    job = "Mercedes";
                    break;

                case 2003:
                case 2400:
                case 2410:
                case 2411:
                case 2412:
                    job = "Phantom";
                    break;

                case 2005:
                case 2500:
                case 2510:
                case 2511:
                case 2512:
                    job = "Silverwall";
                    break;

                case 2004:
                case 2700:
                case 2710:
                case 2711:
                case 2712:
                    job = "Luminous";
                    break;
                case 3001:
                case 3100:
                case 3110:
                case 3111:
                case 3112:
                    job = "Daemonslayer";
                    break;

                case 3002:
                case 3101:
                case 3120:
                case 3121:
                case 3122:
                    job = "Daemon Avenger";
                    break;

                case 3200:
                case 3210:
                case 3211:
                case 3212:
                    job = "Battle Mage";
                    break;

                case 3300:
                case 3310:
                case 3311:
                case 3312:
                    job = "Wild hunter";
                    break;

                case 3500:
                case 3512:
                case 3511:
                case 3512:
                    job = "Mechanic";
                    break;

                case 3600:
                case 3610:
                case 3611:
                case 3612:
                    job = "Xenon";
                    break;

                case 3700:
                case 3710:
                case 3711:
                case 3712:
                    job = "Blaster";
                    break;

                case 5000:
                case 5100:
                case 5110:
                case 5111:
                case 5112:
                    job = "Mikhail";
                    break;

                case 6000:
                case 6100:
                case 6110:
                case 6111:
                case 6112:
                    job = "Kaiser";
                    break;

                case 6001:
                case 6500:
                case 6510:
                case 6511:
                case 6512:
                    job = "Angelic Buster";
                    break;


                case 10000:
                case 10100:
                case 10110:
                case 10111:
                case 10112:
                    job = "Zero";
                    break;

                case 13000:
                case 13100:
                    job = "Pink Bean";
                    break;

                case 14000:
                case 14200:
                case 14210:
                case 14211:
                case 14212:
                    job = "Kinesis";
                    break;

                default:
                    job = "Unidentified";
            }
            print.append("#b"+i).append("top#k ").append(transcendence.getString("name")).append(" #bTranscendence points :#k ").append(transcendence.getInt("transcendence")).append(" #djob :#k ").append(job).append("\r\n");
    }
    cm.sendOk("#fnSharing Ghotic Extrabold#"+ServerConstants.serverName+" #bReal time ranking#k is.\r\nCurrently ranking #fs15##bTranscendence points#k #fs12#Are aggregated into.\r\n\r\n"+print.toString());
    cm.dispose();
}

