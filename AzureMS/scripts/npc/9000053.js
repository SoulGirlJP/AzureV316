importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);
importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(Packages.client);
importPackage(Packages.client.stats);
importPackage(java.lang);
var status = -1;
var etc;
var selFirstJob;
var selFinal;

var r = "#fs11.5##Cgray#(";
var s = ")#fs12#\r\n";

var talk;
var seld = -1;
var level = -1, ap = -1, 누적 = -1, 환포 = -1;

var 승급 = false; // advancement

var 환생상점 = 2040030;

var 환생보상 = 3003325;



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
            
            cm.sendSimple("#bAzureMS #kHello sir, #b#h # #kSincerely welcome. How can I  help you?\r\n#b" + "#L1##rGame Terms of Use #eAll agree #nAnd start the game.#l");
            if (cm.getPlayer().getJob() == 2004) {
                cm.teachSkill(27000106, 5, 5);
                cm.teachSkill(27000207, 5, 5);
                cm.teachSkill(27001201, 20, 20);
                cm.teachSkill(27001100, 20, 20);
            }
            
        if(cm.getPlayer().getJob() == 3002) { // Xenon
            cm.teachSkill(30021236, 1, 1) // Modal shift
            
        }
	if (cm.getPlayer().getJob() == 15500) { //Ark
	    cm.teachSkill(150011074, 1, 1);
	    cm.teachSkill(150010079, 1, 1);
	    cm.teachSkill(155101006, 1, 1);  //Erosion control
	    	    }
	if (cm.getPlayer().getJob() ==  2003) { //Phantom
	    cm.teachSkill(20031203, 1, 1);  // Return of Phantom
	    cm.teachSkill(20031205, 1, 1);  // Phantom Shroud
	    cm.teachSkill(20030206, 1, 1);  // High dexterity
	    cm.teachSkill(20031207, 1, 1);  // Return of Phantom
	    cm.teachSkill(20031208, 1, 1);  // Phantom Shroud
	    cm.teachSkill(20031210, 1, 1);  // Judgment
	    cm.teachSkill(20031260, 1, 1);  // Justice AUTO/MANUAL
	    	    }
            if (cm.getPlayer().getJob() == 2002) {
                cm.teachSkill(20020109, 1, 1);  //Elf's Recovery
                cm.teachSkill(20020111, 1, 1);  //Stylish Move
                cm.teachSkill(20020112, 1, 1);  //Kingship
            }
            if (cm.getPlayer().getJob() == 2007) {
                cm.teachSkill(25001002, 25, 25);
                cm.teachSkill(25000003, 25, 25);
            }
            if (cm.getPlayer().getJob() == 3001) { //Daemon
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
                        cm.teachSkill(10000252, 1, 1);
		cm.teachSkill(10001251, 1, 1);
		cm.teachSkill(10001252, 1, 1);
		cm.teachSkill(10001253, 1, 1);
		cm.teachSkill(10001254, 1, 1);
		cm.teachSkill(10001255, 1, 1);
	    }
        } else if (status == 1) {
     
            FirstJob(cm.getPlayer().getJob());
                }
           
        
            
     else if (status == 2) {
            selFirstJob = selection;
            if (selection < 1000) {
                SecondJob(selection);
            } else {
                cm.sendYesNo("Do you want to start the game with that job? Once you select a job, you can no longer change it. \r\n\r\n#r⑴ Regardless of whether or not you have subscribed to the terms and conditions, clicking the Yes button in that window will signify your acceptance of the game terms and conditions..\r\n\r\n#b⑵ If you do not agree to this, you can exit the game by pressing ESC after the conversation.");
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
			if (cm.getPlayer().getJob() != 10112) {
                    cm.getPlayer().changeJob(selFirstJob);
	  	  
                   
                   
                   
			}
		    cm.warp(100000000);
	    	    if (cm.getPlayer().getJob() == 3500) {
			cm.teachSkill(30001068, 1, 1);
	    	    }
                    cm.dispose();
		    cm.openNpc(9010031);
                    break;
                default:
                    cm.sendYesNo("Do you want to start the game with that job? This class can be changed to another class in another class by using Free Change through Meso Consumption.\r\n\r\n#r⑴ Regardless of whether or not you have subscribed to the terms and conditions, clicking the Yes button in that window will signify your acceptance of the game terms and conditions.\r\n\r\n#b⑵ If you do not agree to this, you can exit the game by pressing ESC after the conversation.");
            }
        }

         else if (status == 4) {
            var stats = cm.getPlayer().getStat();
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
	  
           cm.warp(100000000);     
            AdvAutoJob();
            
            if (cm.getPlayer().getJob() >= 1000 && cm.getPlayer().getJob() <= 1500) {
                cm.teachSkill(10000252, 1, 1);
                cm.teachSkill(10001253, 1, 1);
                cm.teachSkill(10001254, 1, 1);
            }
	    
	    if (cm.getPlayer().getJob() == 3500) {
		cm.teachSkill(30001068, 1, 1);
	    }
            
            cm.sendOk("Rebirth & Change job Complete.")

            
        }
	
            
            else if(status ==5) {
		//cm.getPlayer().fakeRelog(); // Reload
		cm.dispose();
	
                
            }
            

    }
}
function getLevel(lvl) {
	def = 200;
	ret = 5 * (lvl + 1);
	return lvl == 0 ? 210 : def + ret;
}

function getNeed(lvl) {
	return lvl == 0 ? 50 : lvl == 1 ? 200 : lvl == 2 ? 400 : lvl == 3 ? 600 : lvl == 4 ? 800 : lvl == 5 ? 1000 : lvl == 6 ? 2000 : -1;
}

function canInc() {
	return (누적 + 1) == getNeed(level) ? true : false;
}

function getAq(lvl) {
	return lvl == 0 ? 5 : lvl == 1 ? 5 : lvl == 2 ? 7 : lvl == 3 ? 9 : lvl == 4 ? 10 : lvl == 5 ? 12 : lvl == 6 ? 15 : 20;
}

function getPqmin(lvl) {
	return lvl == 0 ? 15 : lvl == 1 ? 30 : lvl == 2 ? 50 : lvl == 3 ? 70 : lvl == 4 ? 100 : lvl == 5 ? 200 : lvl == 6 ? 200 : 200;
}

function getPqmax(lvl) {
	return lvl == 0 ? 100 : lvl == 1 ? 100 : lvl == 2 ? 100 : lvl == 3 ? 100 : lvl == 4 ? 200 : lvl == 5 ? 300 : lvl == 6 ? 400 : 400;
}

function MasterHyper() {
	cm.teachSkill(80000400, 10);
	cm.teachSkill(80000401, 10);
	cm.teachSkill(80000402, 10);
	cm.teachSkill(80000403, 10);
	cm.teachSkill(80000404, 10);
	cm.teachSkill(80000405, 10);
	cm.teachSkill(80000406, 5);
	cm.teachSkill(80000407, 5);
	cm.teachSkill(80000408, 5);
	cm.teachSkill(80000409, 10);
	cm.teachSkill(80000410, 10);
	cm.teachSkill(80000411, 10);
	cm.teachSkill(80000412, 10);
	cm.teachSkill(80000413, 10);
	cm.teachSkill(80000414, 10);
	cm.teachSkill(80000415, 5);
	cm.teachSkill(80000416, 10);
	cm.teachSkill(80000417, 5);
}
function FirstJob(i) {

    talk = "#fs12##h #'S #kBelow is a list of jobs available on the server.\r\n";
    switch (i) {
        case 0:
            talk += "#L100##bWarrior　  " + r + "→ Hero, Dark Knight, Paladin" + s + "";
            talk += "#L200##bWizard  " + r + "→ Archmage (Fire, Poison), Archmage (Sun, Cole), Bishop" + s + "";
            talk += "#L300##bArcher　  " + r + "→ Bowmaster, Shrine" + s + "";
            talk += "#L400##bThief　  " + r + "→ Night Lord, Shadow, Dual Blader" + s + "";
            talk += "#L500##bPirate　  " + r + "→ Viper, Captain, Cannon Shooter" + s + "";
            break;
        case 301:
            talk += "#L301##bPathfinder　  " + r + "→ Pathfinder" + s + "";
            break;
        case 1000:
            talk += "#L1100##bMana Warrior　  " + r + "→ Warrior" + s + "";
            talk += "#L1200##bFlame Wizard  " + r + "→ Wizard" + s + "";
            talk += "#L1300##bWindbreaker  " + r + "→ Archer" + s + "";
            talk += "#L1400##bKnight Walker　  " + r + "→ Thief" + s + "";
            talk += "#L1500##bThunder Breaker　  " + r + "→ Pirate" + s + "";
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
            talk += "#L2500##bShade";
            break;
        case 2004:
            talk += "#L2700##bLuminous";
            break;

        case 3000:
            talk += "#L3200##bBattle Mage  " + r + "→ Wizard" + s + ""
            talk += "#L3300##bWild hunter  " + r + "→ Archer" + s + "";
            talk += "#L3500##bMechanic　　  " + r + "→ Pirate" + s + "";
            talk += "#L3700##bBlaster　  " + r + "→ Warrior" + s + "";
            break;

        case 3001:
            talk += "#L3100##bDaemon Slayer  " + r + "→ Attack Line" + s + "";
            talk += "#L3101##bDaemon Avenger　  " + r + "→ MaxHP" + s + "";
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
    var v1 = i == 100 ? "Warrior" : i == 200 ? "Wizard" : i == 300 ? "Archer" : i == 301 ? "Archer" : i == 400 ?  "Thief" : "Pirate" 
    var v2  = "#e" + v1 + " Job#nYou have chosen now #h #'S #bPlease choose the last change.#k If you achieve the previous level of 30, 60, 100 #rAutomatically change#k This becomes.\r\n";
    switch (i) {
        case 100:
            v2 += "#L112##bHero　　  " + r + "Fighter → Crusader → Hero" + s + "";
            v2 += "#L122##bPaladin　　  " + r + "Page → Night → Paladin" + s + "";
            v2 += "#L132##bDark Knight  " + r + "Spearman → Berserker → Dark Knight" + s + "";
            break;

        case 200:
            v2 += "#L212##bArchmage (Fire, Poison)  " + r + "Wizard → Mage → Archmage" + s + "";
            v2 += "#L222##bArc Mage (Sun, Cole)  " + r + "Wizard → Mage → Archmage" + s + "";
            v2 += "#L232##bBishop#e　　#n　　　　　" + r + "Cleric → Priest → Bishop" + s + "";
            break;

        case 300:
            v2 += "#L312##bBow Master  " + r + "Hunter → Ranger → Bow Master" + s + "";
            v2 += "#L322##bWind Archer　　　  " + r + "Shooter → Sniper → Wind Archer" + s + "";
            break;

        case 301:
            v2 += "#L332##bPathfinder  " + r + "Ancient Archer → Chaser → Pathfinder" + s + "";
       
        case 400:
            v2 += "#L412##bNight Lord　  " + r + "Assassin → Hermit → Night Lord" + s + "";
            v2 += "#L422##bShadower　　　  " + r + "Sheep → Sheepmaster → Shadow" + s + "";
            v2 += "#L434##bDual Blade  " + r + "Dewarer → Slasher → Dual Blader" + s + "";
            break;

        case 500:
            v2 += "#L512##bBuccaneer　　  " + r + "Infinity Fighter → Buccaneer → Viper" + s + "";
            v2 += "#L522##bCaptain　　　  " + r + "Gunslinger → Valkyrie → Captain" + s + "";
            v2 += "#L532##bCanon Master  " + r + "Cannon Shooter → Cannon Blaster → Cannon Master" + s + "";
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