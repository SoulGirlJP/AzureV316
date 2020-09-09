/*
	NPC Name: 		Asia
	Description: 		Quest - Asia
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
	    qm.sendNext("I don't think you can escape from here now. If you do plan on battling here, you'll need more teammates to make it an even fight. What do you want to do? Do you still want to battle?");
	} else if (status == 1) {
	    qm.warp(802000109, 0);
	    //qm.forceStartQuest();
	    qm.dispose();
	}
    }
}

function end(mode, type, selection) {
}