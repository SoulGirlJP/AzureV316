var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendYesNo("Now that i've thought about it, I bet those aliens were trying to catch anybody that saw them before they could tell the rest of us! We have to get them out and spread the word!");
    } else if (status == 1) {
	qm.sendNextPrev("Its your tiem to shine! Go rescue all those good folks. I'm not sure how you're going to get them out. but you have to try!");
    } else if (status == 2) {
	qm.sendNextPrev("You said you saw them near the base. I heard some folks talking about rectangular containers in the fields. I bet those are actually jail cells! Go click on them and see if you can break them out!");
	} else if (status == 3) {
	qm.sendNextPrev("I'm going to give you some Return to New Leaf City scrolls, but remember they're for the people you Rescue ONLY!");
	} else if (status == 4) {
	qm.forceStartQuest(28749);
	qm.dispose();
    }
}

