/*
	NPC Name: 		Ponicher
	Description: 		Quest - A Battle Against Vergamot
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1) {
	    status++;
	}
	if (status == 0) {
	    qm.sendNext("Are you sure you want to leave right now? The boss is not easy to battle against, so if you can gather some friends, then do it! Let me know when you're ready, so I can take you to the spot where it awaits. Okay?");
	} else if (status == 1) {
	    qm.warp(802000209, 0);
	    //qm.forceStartQuest();
	    qm.dispose();
	}
    }
}

function end(mode, type, selection) {
}