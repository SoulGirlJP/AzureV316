var status = -1;

function start(mode, type, selection) {
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
	qm.sendPlayerToNpc("I've only just begun to restore my former powers from all those years ago. However, while I am still not a Great Thief, I must prepare to make my entrance.");
    } else if (status == 1) {
	qm.sendNextS("Every hero has to take their first slep, #h #, Your first slep will be into Ereve, All preparations are complete, you only need to exit the ship and descent down.", 1);
    } else if (status == 2) {
	qm.sendPlayerToNpc("This is no problem for the world's greatest thief!");
    } else if (status == 3) {
	qm.sendNextPrevS("You may leave now, Your journey begins here.", 1);
    } else if (status == 4) {
	qm.forceStartQuest();
	qm.dispose();
    }
}