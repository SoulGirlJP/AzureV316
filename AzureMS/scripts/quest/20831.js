/* Cygnus revamp
	Noblesse tutorial
	Kizan
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	status++;
	if (status == 0) {
		qm.sendYesNo("Did you enjoy the drink? You better have! It is the special concoction of my people, the Piyo Tribe!\r\nNow... Pop quiz! Do you remember how to fight? Defeat 3 #o9300730# monsters and bring me 3 Tutorial Tino's Feather items!");
	} else if (status == 1) {
	    qm.forceStartQuest();
		qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/cygnusTutorial/4");
		qm.spawnMonster(9300730,3);
		qm.gainItem(4000489,3);
	    qm.dispose();
	}
  }

function end(mode, type, selection) {
	 qm.dispose();
	}