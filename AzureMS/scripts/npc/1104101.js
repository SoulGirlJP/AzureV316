importPackage(Packages.constants);

var status = -1;

var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
var HOT = "#fUI/CashShop.img/CSEffect/hot/0#";
var NEW = "#fUI/CashShop.img/CSEffect/new/0#";


function start() {
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var choose = "             #fnSharing Gothic Extrabold##fs17#"+별+"Azure Boss System"+별+"\r\n#fs10##Cgray#                       You are connected to Azure's boss system.#k\r\n#fs11#";
        //choose += "#L1000##v2591163#   #d 보스 시스템 점검중 (패치) #k #d( 카오스 HP : 8 4 0 억 )\r\n";
        choose += "#L15##dTrial of the Abandoned Mine #rChaos Zakum#k #d( ★★ )\r\n";
        choose += "#L0##dDragon's Nest #rChaos Horntail#k #d( ★ )\r\n";
        //choose += "#L1# #v2591218#   #d심해의 금붕어 #r피아누스#k #d( ★★ )#k\r\n";
        //choose += "#L30#----------------------------------------------------------------------------\r\n";
        choose += "#L25##dWolf camp #rFlame Wolf#k #d( ★★★ )\r\n";
        choose += "#L2##dWhite haired queen #rHilla#k #d( ★★★ )\r\n";
        choose += "#L3##dLion King #rVon leon#k #d( ★★★★ )\r\n";
        choose += "#L4##dA final report #rArkarium#k #d( ★★★★ )\r\n"
        choose += "#L5##dCorrupted Empress #rCygnus#k #d( ★★★★ )\r\n";
        choose += "#L6##dLegion master #rMagnus#k #d( ★★★★★ )\r\n";
        choose += "#L100##dEarth Defense Force #rKaung#k #d( ★★★★★ )\r\n";
        choose += "#L7##dTwilight of the gods #rChaos Pink Bean#k #d( ★★★★★ )#k \r\n";
        //choose += "#L20#----------------------------------------------------------------------------\r\n";
        choose += "#L8##dScreaming #rChaos Bloody Queen#k #d( ★★★★★ )\r\n";
        choose += "#L16##dMocking #rChaos Pierre#k #d( ★★★★★ )\r\n";
        choose += "#L17##dOf time #rChaos VonVon(KFC)#k #d( ★★★★★ )\r\n";
        choose += "#L9##dRuinous #rChaos Vellum#k #d( ★★★★★ )#k\r\n";
        //choose += "#L13#----------------------------------------------------------------------------\r\n";
        choose += "#L10##dBlack Haven core #r스우#k \r\n";
        choose += "#L26##dLegion Master #rDamien#k \r\n";
        choose += "#L21##dLegion Master #rLucid - Nightmare#k \r\n";
        //choose += "#L102##dMaple X Attack on Titan #rParty play#k #d(Personal compensation) \r\n";
        //choose += "#L100#----------------------------------------------------------------------------\r\n"; 
        choose += "#L24##dQueen of the seed #rDorothy#d( ★★★★★★★★ )#k \r\n";
        choose += "#L101##dOverseas Boss #rCross#d( ★★★★★★★★ )#k \r\n"
        choose += "#L103##dClock tower #rPapulatus#d( ★★★★★★★★★★ )#k \r\n"
        //choose += "#L23##dPawang #rUrsus #d( ★★★★★★★★★★★★★★ )#k \r\n";
        //choose += "#L6##v3014005#  Official ranking";
        //choose += "#L7##v4031286#  Warehouse use";
        //choose += "#L8##v3994009#  Solution#l\r\n";
	//choose += "　#L9##v2500000#  Reset cache archive"
	//choose += "#L10##v2501000#  Equipment option reset#l"

        cm.sendSimple(choose);
        cm.dispose();
    } else if (selection == 0) {//Hontel
        cm.dispose();
  	cm.openNpc(9000325);
    } else if (selection == 1) {//Pianus
        cm.dispose();
	cm.openNpc(9073002);
    } else if (selection == 2) {//Hilla
        cm.dispose();
	cm.openNpc(9020011);
    } else if (selection == 3) {//Von leon
	cm.dispose();
	cm.openNpc(2159310);
    } else if (selection == 4) {//Arkarium
	cm.dispose();
	cm.openNpc(2159308);
    } else if (selection == 5) {//Cygnus
	cm.dispose();
	cm.openNpc(2520001);
    } else if (selection == 6) {//Magnus
	cm.dispose();
	cm.openNpc(3000131);
    } else if (selection == 7) {//Chaos Pink Bean
	cm.dispose();
	cm.openNpc(2141000);
    } else if (selection == 8) {//Chaos Bloody Queen
	cm.dispose();
	cm.openNpc(1530665);
    } else if (selection == 9) {//Chaos Vellum
	cm.dispose();
	cm.openNpc(1064017);
    } else if (selection == 10) {//Swoo
	cm.dispose();
        cm.openNpc(1404005);
    } else if (selection == 15) {//Zakum
	cm.dispose();
        cm.openNpc(2030013);
    } else if (selection == 16) {//Chaos Pierre
	cm.dispose();
        cm.openNpc(1530623); 
    } else if (selection == 17) {//Chaos Banban (KFC)
	cm.dispose();
        cm.openNpc(9000204);
    } else if (selection == 21) {//Lucid
	cm.dispose();
        cm.openNpc(3003250);
    } else if (selection == 23) {//Ursus
	cm.dispose();
        cm.openNpc(9070201);
    } else if (selection == 24) {//Dorothy
	cm.dispose();
        cm.openNpc(2540010);
    } else if (selection == 25) {//Flame Wolf
	cm.dispose();
        cm.openNpc(9001059);
    } else if (selection == 26) {//Damien
	cm.dispose();
        cm.openNpc(1530621);  
    } else if (selection == 100) {//Kaung
	cm.dispose();
        cm.openNpc(2050005);
    } else if (selection == 101) {//Cross
	cm.dispose();
        cm.openNpc(9073003); 
    } else if (selection == 102) {//cross
	cm.dispose();
        cm.openNpc(9137200); 
    } else if (selection == 103) {//Papulatus
	cm.dispose();
        cm.openNpc(2043000); 
    } else if (selection == 11) {//Jaguar Habitat Move
         if (cm.getJob() >= 3300 && cm.getJob() <= 3312) {
        cm.warp(931000500,0);
	cm.sendOk("#fnSharing Gothic Extrabold##bTo do a special jaguar and a kayak\r\nFind the blackjack on the map.#k"); 
        cm.dispose();
   } else {
	cm.sendOk("#fnSharing Gothic Extrabold##rOnly Wild Hunter occupations are available.#k"); 
        cm.dispose();

   }

}
}