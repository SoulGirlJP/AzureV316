var status = -1;
var etc;
var selFirstJob;
var selFinal;

var r = "#fs11.5##Cgray#(";
var s = ")#fs12#\r\n";

var talk;


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            cm.sendSimple("#kWelcome to AzureMS #b#h #.\r\nHow can I help you?\r\n#b" + "#L1##rGame Terms of Use #eAll agree #nAnd start the game.#l");
            if (cm.getPlayer().getJob() == 2004) {
                cm.teachSkill(27000106, 5, 5);
                cm.teachSkill(27000207, 5, 5);
                cm.teachSkill(27001201, 20, 20);
                cm.teachSkill(27001100, 20, 20);
            }
	if (cm.getPlayer().getJob() == 15500) { //Ark
	    cm.teachSkill(150011074, 1, 1);
	    cm.teachSkill(150010079, 1, 1);
	    cm.teachSkill(155101006, 1, 1);  //Erosion control
	    	    }
            if (cm.getPlayer().getJob() == 2007) {
                cm.teachSkill(25001002, 25, 25);
                cm.teachSkill(25000003, 25, 25);
            }
            if (cm.getPlayer().getJob() == 3001) {
                cm.teachSkill(30010111, 1, 1);
                cm.teachSkill(30010185, 1, 1);
                cm.teachSkill(30010112, 2, 2);
            }
	    if (cm.getPlayer().getJob() == 2005) {
		cm.teachSkill(20051284, 1, 1);
		cm.teachSkill(20050285, 1, 1);
		cm.teachSkill(25001000, 30,30);
	 	cm.teachSkill(25001002, 30, 30);
	    }
	    if (cm.getPlayer().getJob() == 1000) {
		cm.teachSkill(10001251, 1, 1);
		cm.teachSkill(10001252, 1, 1);
		cm.teachSkill(10001253, 1, 1);
		cm.teachSkill(10001254, 1, 1);
		cm.teachSkill(10001255, 1, 1);
	    }
        } else if (status == 1) {
            FirstJob(cm.getPlayer().getJob());
        } else if (status == 2) {
            selFirstJob = selection;
            if (selection < 1000) {
                SecondJob(selection);
            } else {
                cm.sendYesNo("Do you want to start the game with that job? Once you select a job, you can no longer change it. \r\n\r\n#r©ç Regardless of whether or not you have subscribed to the terms and conditions, clicking the Yes button in that window will signify your acceptance of the game terms and conditions.\r\n\r\n#b©è If you do not agree to this, you can exit the game by pressing ESC after the conversation.");
                etc = 1;
            }

        } else if (status == 3) {
            selFinal = selection;
            switch (etc) {
                case 1:
                    for (var i = cm.getPlayer().getLevel(); i < 10; i++) {
                        cm.getPlayer().levelUp();
                    }
                    if (cm.getPlayer().getExp() < 0)
                        cm.getPlayer().gainExp(-cm.getPlayer().getExp(), false, false, true);
                    
                    AutoJob();
		    
            cm.gainMeso(500000);
			cm.gainItemAllStat(1142282, 1, 100, 50);
			if (cm.getPlayer().getJob() != 10112) {
                    cm.getPlayer().changeJob(selFirstJob);
	  	    cm.resetStats(4, 4, 4, 4);
			}
		    cm.warp(101050000);
	    	    if (cm.getPlayer().getJob() == 3500) {
			cm.teachSkill(30001068, 1, 1);
	    	    }
                    cm.dispose();
		    cm.openNpc(9010031);
                    break;
                default:
                    cm.sendYesNo("Do you want to start the game with that job? This class   can be changed to another class in another class by      using Free Change through Meso Consumption.\r\n\r\n#r©ç Regardless of whether or not you have subscribed to   the terms and conditions, clicking the Yes button in that   window will signify your acceptance of the game terms   and conditions.\r\n\r\n#b©è If you do not agree to this, you can exit the game by pressing ESC after the conversation.");
            }

        } else if (status == 4) {
            cm.gainMeso(500000);
			cm.gainItemAllStat(1142282, 1, 50, 20);
            for (var i = cm.getPlayer().getLevel(); i < 10; i++) {
                cm.getPlayer().levelUp();
            }
            if (cm.getPlayer().getExp() < 0)
                cm.getPlayer().gainExp(-cm.getPlayer().getExp(), false, false, true);
	    if (selFinal == 532) {
                cm.getPlayer().changeJob(501);
            } else if (selFinal == 434) {
                cm.getPlayer().changeJob(430);
                for (var i = cm.getPlayer().getLevel(); i < 20; i++) {
                    cm.getPlayer().levelUp();
                }
                if (cm.getPlayer().getExp() < 0)
                    cm.getPlayer().gainExp(-cm.getPlayer().getExp(), false, false, true);
            } else {
                cm.getPlayer().changeJob(selFirstJob);
            }
	    cm.resetStats(4, 4, 4, 4);
            AdvAutoJob();
	    
            if (cm.getPlayer().getJob() >= 1000 && cm.getPlayer().getJob() <= 1500) {
                cm.teachSkill(10000252, 1, 1);
                cm.teachSkill(10001253, 1, 1);
                cm.teachSkill(10001254, 1, 1);
            }
	    cm.warp(101050000);
	    if (cm.getPlayer().getJob() == 3500) {
		cm.teachSkill(30001068, 1, 1);
	    }
	    cm.dispose();
	    cm.openNpc(9010031);
        }
    }
}

function FirstJob(i) {

    talk = "#fs12##h #'S, #khere is a list of jobs available on the server:\r\n";
    switch (i) {
        case 0:
            talk += "#L100##bWarrior¡¡  " + r + "¡æ Hero, Dark Knight, Paladin" + s + "";
            talk += "#L200##bMage  " + r + "¡æ Mage(Fire, Poison), Mage(Sun, Cole), Bishop" + s + "";
            talk += "#L300##bBowman¡¡  " + r + "¡æ Bowmaster, Shrine" + s + "";
            talk += "#L400##bThief¡¡  " + r + "¡æ Night Road, Shadow, Dual Blader" + s + "";
            talk += "#L500##bPirate¡¡  " + r + "¡æ Viper, Captain, Cannon Shooter" + s + "";
            break;
        case 301:
            talk += "#L301##bPathfinder¡¡  " + r + "¡æ Pathfinder" + s + "";
            break;
        case 1000:
            talk += "#L1100##bPath Pass Soul Master¡¡  " + r + "¡æ Warrior" + s + "";
            talk += "#L1200##bFlame Wizard  " + r + "¡æ Mage" + s + "";
            talk += "#L1300##bWindbreaker  " + r + "¡æ Sorcerer" + s + "";
            talk += "#L1400##bKnight Walker¡¡  " + r + "¡æ Thief" + s + "";
            talk += "#L1500##bStriker¡¡  " + r + "¡æ Pirate" + s + "";
            break;
        case 2000:
            talk += "#L2100##bAran";
            break;
        case 2001:
            talk += "#L2200##bEvan";
            break;
        case 2002:
            talk += "#L2300##bMercedes";
            break;
        case 2003:
            talk += "#L2400##bPhantom";
            break;
        case 2005:
            talk += "#L2500##bEunwol";
            break;
        case 2004:
            talk += "#L2700##bLuminous";
            break;

        case 3000:
            talk += "#L3200##bBattle Mage  " + r + "¡æ Mage" + s + ""
            talk += "#L3300##bWild hunter  " + r + "¡æ Bowman" + s + "";
            talk += "#L3500##bMechanic¡¡¡¡  " + r + "¡æ Pirate" + s + "";
            talk += "#L3700##bBlaster¡¡  " + r + "¡æ Warrior" + s + "";
            break;

        case 3001:
            talk += "#L3100##bDemon Slayer  " + r + "¡æ Attack Range" + s + "";
            talk += "#L3101##bDemon Avenger¡¡  " + r + "¡æ MaxHP Series" + s + "";
            break;

        case 3002:
            talk += "#L3600##bXenon";
            break;

		case 10112:
			talk += "#L10112##bZero";
			break;
        case 5000:
            talk += "#L5100##bMikhail";
            break;
        case 6000:
            talk += "#L6100##bKaiser";
            break;
        case 6001:
            talk += "#L6500##bAngelic Buster";
            break;
        case 6002:
            talk += "#L6400##bCadena";
            break;
        case 14000:
            talk += "#L14200##bKinesis";
            break;
        case 15000:
            talk += "#L15200##bIllium";
            break;
        case 15500:
            talk += "#L15500##bArk";
            break;
    }
    cm.sendSimple(talk);
}


function SecondJob(i) {
    etc = 0;
    var v1 = i == 100 ? "Warrior" : i == 200 ? "Mage" : i == 300 ? "Bowman" : i == 301 ? "Bowman" : i == 400 ?  "Thief" : "Pirate" 
    var v2  = "You have chosen #e" + v1 + " Job #n. #r#h #'S \r\n#bPlease choose the last change.\r\n#kFormer level 30, 60, 100, 200. \r\n#rAutomatically #k Job Advance .\r\n";
    switch (i) {
        case 100:
            v2 += "#L112##bHero¡¡¡¡  " + r + "Fighter ¡æ Crusader ¡æ Hero" + s + "";
            v2 += "#L122##bPaladin¡¡¡¡  " + r + "Page ¡æ Night ¡æ Paladin" + s + "";
            v2 += "#L132##bDark Knight  " + r + "Spearman ¡æ Berserker ¡æ Dark Knight" + s + "";
            break;

        case 200:
            v2 += "#L212##bArchmage (Fire, Poison)  " + r + "Wizard ¡æ Mage ¡æ Archmage" + s + "";
            v2 += "#L222##bArc Mage (Sun, Cole)  " + r + "Wizard ¡æ Mage ¡æ Archmage" + s + "";
            v2 += "#L232##bBishop#e¡¡¡¡#n¡¡¡¡¡¡¡¡¡¡" + r + "Cleric ¡æ Priest ¡æ Bishop" + s + "";
            break;

        case 300:
            v2 += "#L312##bBowmaster  " + r + "Hunter ¡æ Ranger ¡æ Bowmaster" + s + "";
            v2 += "#L322##bShrine¡¡¡¡¡¡  " + r + "Shooter ¡æ Sniper ¡æ Shrine" + s + "";
            break;

        case 301:
            v2 += "#L332##bPathfinder  " + r + "Ancient Archer ¡æ Chaser ¡æ Pathfinder" + s + "";
			break;
       
        case 400:
            v2 += "#L412##bNight Lord¡¡  " + r + "Assassin ¡æ Hermit ¡æ Night Lord" + s + "";
            v2 += "#L422##bShadow¡¡¡¡¡¡  " + r + "Sheep ¡æ Sheepmaster ¡æ Shadow" + s + "";
            v2 += "#L434##bDual Blader  " + r + "Dewarer ¡æ Slasher ¡æ Dual Blader" + s + "";
            break;

        case 500:
            v2 += "#L512##bViper¡¡¡¡  " + r + "Infinity Fighter ¡æ Buccaneer ¡æ Viper" + s + "";
            v2 += "#L522##bCaptain¡¡¡¡¡¡  " + r + "Gunslinger ¡æ Valkyrie ¡æ Captain" + s + "";
            v2 += "#L532##bCanonmaster  " + r + "Cannon Shooter ¡æ Cannon Blaster ¡æ Cannon Master" + s + "";
            break;
    }

    cm.sendSimple(v2);

}


function AutoJob() {
    switch (selFirstJob) {
        case 570:
            cm.getPlayer().setKeyValue("AutoJob", "571");
            break;
        case 1100:
            cm.getPlayer().setKeyValue("AutoJob", "1110");
            break;
        case 1200:
            cm.getPlayer().setKeyValue("AutoJob", "1210");
            break;
        case 1300:
            cm.getPlayer().setKeyValue("AutoJob", "1310");
            break;
        case 1400:
            cm.getPlayer().setKeyValue("AutoJob", "1410");
            break;
        case 1500:
            cm.getPlayer().setKeyValue("AutoJob", "1510");
            break;
        case 2100:
            cm.getPlayer().setKeyValue("AutoJob", "2110");
            break;
        case 2200:
            cm.getPlayer().setKeyValue("AutoJob", "2210");
            break;
        case 2300:
            cm.getPlayer().setKeyValue("AutoJob", "2310");
            break;
        case 2400:
            cm.getPlayer().setKeyValue("AutoJob", "2410");
            break;
        case 2500:
            cm.getPlayer().setKeyValue("AutoJob", "2510");
            break;
        case 2700:
            cm.getPlayer().setKeyValue("AutoJob", "2710");
            break;
        case 3100:
            cm.getPlayer().setKeyValue("AutoJob", "3110");
            break;
        case 3101:
            cm.getPlayer().setKeyValue("AutoJob", "3120");
            break;
        case 3200:
            cm.getPlayer().setKeyValue("AutoJob", "3210");
            break;
        case 3300:
            cm.getPlayer().setKeyValue("AutoJob", "3310");
            break;
        case 3500:
            cm.getPlayer().setKeyValue("AutoJob", "3510");
            break;
        case 3600:
            cm.getPlayer().setKeyValue("AutoJob", "3610");
            break;
        case 3700:
            cm.getPlayer().setKeyValue("AutoJob", "3710");
            break;
        case 4100:
            cm.getPlayer().setKeyValue("AutoJob", "4110");
            break;
        case 4200:
            cm.getPlayer().setKeyValue("AutoJob", "4210");
            break;


        case 5100:
            cm.getPlayer().setKeyValue("AutoJob", "5110");
            break;

        case 6100:
            cm.getPlayer().setKeyValue("AutoJob", "6110");
            break;
        case 6400:
            cm.getPlayer().setKeyValue("AutoJob", "6400");
            break;
        case 6500:
            cm.getPlayer().setKeyValue("AutoJob", "6510");
            break;
        case 14200:
            cm.getPlayer().setKeyValue("AutoJob", "14200");
            break;
        case 15200:
            cm.getPlayer().setKeyValue("AutoJob", "15210");
            break;
        case 15500:
            cm.getPlayer().setKeyValue("AutoJob", "15510");
            break;
    }
}


function AdvAutoJob() {
    switch (selFinal) {
        case 112:
            cm.getPlayer().setKeyValue("AutoJob", "110");
            break;
        case 122:
            cm.getPlayer().setKeyValue("AutoJob", "120");
            break;
        case 132:
            cm.getPlayer().setKeyValue("AutoJob", "130");
            break;
        case 212:
            cm.getPlayer().setKeyValue("AutoJob", "210");
            break;
        case 222:
            cm.getPlayer().setKeyValue("AutoJob", "220");
            break;
        case 232:
            cm.getPlayer().setKeyValue("AutoJob", "230");
            break;
        case 332:
            cm.getPlayer().setKeyValue("AutoJob", "330");
            break;
        case 312:
            cm.getPlayer().setKeyValue("AutoJob", "310");
            break;
        case 322:
            cm.getPlayer().setKeyValue("AutoJob", "320");
            break;
        case 412:
            cm.getPlayer().setKeyValue("AutoJob", "410");
            break;
        case 422:
            cm.getPlayer().setKeyValue("AutoJob", "420");
            break;
        case 434:
            cm.getPlayer().setKeyValue("AutoJob", "430");
            break;
        case 512:
            cm.getPlayer().setKeyValue("AutoJob", "510");
            break;
        case 522:
            cm.getPlayer().setKeyValue("AutoJob", "520");
            break;
        case 532:
            cm.getPlayer().setKeyValue("AutoJob", "530");
            break;
    }
}