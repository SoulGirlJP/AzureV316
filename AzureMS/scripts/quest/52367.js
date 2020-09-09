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
	qm.sendNext("Why aren't you in school yet? You should be studying!");
    } else if (status == 1) {
	qm.sendNextPrev("I'll be in every town. Come find me if you wanna go to school!");
    } else if (status == 2) {
	qm.sendNextPrev("Come out to the school today and i'll give you a tour!");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.dispose();
    }
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
	qm.sendNext("Here's the deal kid. This school's like a maze. The classrooms are a big jumble. You never know where you're gonna end up when you go through a door.");
    } else if (status == 1) {
	qm.sendNextPrev("If you want to make the most of your time here, keep moving through classrooms to get more EXP and raise your Friendship with Four Pilars of Heaven... when you meet them.");
    } else if (status == 2) {
	qm.sendNextPrev("Don't get too gready, though. Each consecutive classroom will become more challenging! But don't worry about losing EXP from getting knocked out. The school nurse is great!");
    } else if (status == 3) {
	qm.sendNextPrev("Work on your Friendship with the Four Pilars of Heaven and I hear you can buy stuff from their lockers.");
    } else if (status == 4) {
	qm.sendNextPrev("You can always get back here if you get to the roof and take the door on the left.");
    } else if (status == 5) {
	qm.sendNextPrev("And take this keym I'll get you past the lock on the Eastern door.");
    } else if (status == 6) {
	if(qm.getPlayer().getLevel() <= 30) {
	qm.gainExp(15000 * 2);
	qm.gainItem(5252017, 1);
	qm.forceCompleteQuest();
	qm.dispose();
	}
	if(qm.getPlayer().getLevel() <= 70 && qm.getPlayer().getLevel() > 30) {
	qm.gainExp(30000 * 2);
	qm.gainItem(5252017, 1);
	qm.forceCompleteQuest();
	qm.dispose();
	}
	if(qm.getPlayer().getLevel() <= 120 && qm.getPlayer().getLevel() > 70) {
	qm.gainExp(60000 * 2);
	qm.gainItem(5252017, 1);
	qm.forceCompleteQuest();
	qm.dispose();
	}
	if(qm.getPlayer().getLevel() <= 200 && qm.getPlayer().getLevel() > 120) {
	qm.gainExp(120000 * 2);
	qm.gainItem(5252017, 1);
	qm.forceCompleteQuest();
	qm.dispose();
	}
	qm.dispose();
    }
}