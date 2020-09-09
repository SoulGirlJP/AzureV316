/*
	NPC Name: 		Dida
	Description: 		Quest - 2102 Shibuya
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.sendNext("...What is it? Ah, I see that he's coming really close!");
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.askAcceptDecline("Watch out, because he seems... much more powerful than before. Do not underestimate him!");
    } else if (status == 1) {
	qm.forceStartQuest();
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}