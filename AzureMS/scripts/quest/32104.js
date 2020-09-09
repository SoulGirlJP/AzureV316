/* Dawnveil
    [Ellinel Fairy Academy] Fanzy's Magic 2
	Fanzy
    Made by Daenerys
*/
print("test")

var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.forceStartQuest();
		qm.gainItem(4033825,5);
		qm.dispose();
	}
}

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNext("Did you bring the #b Starlight Crystal#k?");
	} else if (status == 1) {
	    qm.sendNextPrev("Not bad, not bad. I'll start the incantation.");
	} else if (status == 2) {
		qm.removeAll(4033824);
		qm.removeAll(4033825);
		qm.warp(101070010,0);
		qm.gainExp(4748);
		qm.forceCompleteQuest();
	    qm.dispose();		
	}
  }
}