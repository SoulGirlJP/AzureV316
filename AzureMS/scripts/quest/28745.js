var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendAcceptDecline("This place is going bananas! #b#m600000000##k got hit with some CRAZY earthquakes! I'm talking buildings falling over, fields disappearing into the ground, I don't even recognize the city I made! I'd hire a seismologist, but the budget's all tied up in expansion right now. You're pretty seimologically inclined. Think you could help me?");
    } else if (status == 1) {
	qm.sendAcceptDecline("That's what I like to hear! Meet me at #e#b#m600000000##k#n. If you need a ride, I can send my special Invisible Mayoral Hover Limo, no charge. What do you say?");
    } else if (status == 2) {
	qm.sendNext("All right! I knew you liked to ride in style. You won't actually see the limo. Just stand still.");
	qm.forceStartQuest(28745);
	qm.dispose();
    }
}

