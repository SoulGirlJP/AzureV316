var status = -1;

function start(mode, type, selection) {
    qm.forceStartQuest();
    qm.dispose();
}
function end(mode, type, selection) {
    if (mode == 1) status++; 
    else 
    {
		if (status == 0) {
			qm.sendNext("Wence has more to tell you. Go lend an ear`");
			qm.dispose();
			return;
		}
		status--;
    }
    if (status == 0) {
		qm.askAcceptDecline("You're received the Superior Hunter title!");
    } else if (status == 1) {
		qm.forceCompleteQuest();
        qm.gainItem(2043602,5);
        qm.gainItem(2043402,5);
        var rnum = Math.floor(Math.random() * 10);
        if (rnum<=2)qm.gainItem(2046374,1);
        qm.dispose();
    }
}
