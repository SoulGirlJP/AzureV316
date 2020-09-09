/* Cygnus revamp
	Noblesse tutorial
	The Path of a Thunder Breaker
    Made by Daenerys
*/

var status = -1;

function start(mode, type, selection) {
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("This is an important decision to make.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("Have ye made yer decision? The decision will be final, so think carefully before deciding what to do. Are ye sure ye want to become a Thunder Breaker?");
    } else if (status == 1) {
	qm.sendNext("I have just molded yer body to make it perfect for a Thunder Breaker. If ye wish to become more powerful, use Stat Window (S) to raise the appropriate stats. If ye arn't sure what to raise, just click on #bAuto#k.");
	if (qm.getJob() != 1500) {
	    qm.gainItem(1482014, 1);
	    qm.gainItem(1142066, 1);
	    qm.expandInventory(1, 4);
	    qm.expandInventory(4, 4);
	    qm.changeJob(1500);
		qm.getPlayer().gainSP(5, 0);
		qm.gainExp(1242);
	}
	qm.forceCompleteQuest();
    } else if (status == 2) {
	qm.sendNextPrev("I have also expanded yer inventory slot counts for yer equipment and etc. inventory. Use those slots wisely and fill them up with items required for Knights to carry.");
    } else if (status == 3) {
	qm.sendNextPrev("I have also given ye a hint of #bSP#k, so open the #bSkill Menu#k to acquire new skills. Of course, ye can't raise them at all once, and there are some skills out there where ye won't be able to acquire them unless ye master the basic skills first.");
    } else if (status == 4) {
	qm.sendNextPrev("Unlike yer time as a Noblesse, once ye become the Thunder Breaker, ye will lost a portion of yer EXP when ye run out of HP, okay?");
    } else if (status == 5) {
	qm.sendNextPrev("Now... I want ye to go out there and show the world how the Knights of Cygnus operate.");
	qm.safeDispose();
    }
}