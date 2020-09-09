var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendYesNo("First off, we've got to find out what caused that earthquake! According to my egghead homies in #b#m600000000##k. this wasn't a natural phenomenon. I think you should take a look into it.");
    } else if (status == 1) {
	qm.sendNext("Take this #v2430680# #b#z2430680##k thing. The guys who made it said you could 'activate it to find the earthquake's epicenter.' whatever that means. So, do that.");
    } else if (status == 2) {
	qm.forceStartQuest(28746);
	qm.dispose();
    }
}

