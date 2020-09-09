var status = -1;

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
	qm.sendNext("(This oughtta get us off of this rock...)");
    } else if (status == 1) {
	qm.sendPlayerToNpc("#b(I've got the Master Key. What am I waiting for?! I should get back to #eBurke.)");
    } else if (status == 2) {
	qm.warp(552000022);
	qm.forceCompleteQuest(53247);
	qm.dispose();
    }
}