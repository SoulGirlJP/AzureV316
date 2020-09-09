/* Dawnveil
    [Halloween] Cassandra, Lord of Candy
	Cassandra
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Hey there #b#h ##k. Are you ready to be a great pal during the Halloween event? I prepared a little something.\r\nHeh, can you tell what this is?\r\n\r\n#i3994650##bBoo Buddy Candy#k\r\n\r\nIt's a Halloween candy for those that participate in the Halloween event!");
	} else if (status == 1) {		
	    qm.sendNextPrev("#bRight-click#k on any Maple friend and select a piece of #bGive Halloween Candy#k to share.\r\nYou'll get a #bbuff and, for every 5 candies, a gift box#k just for sharing!");	
    } else if (status == 2) {	
	    qm.sendNextPrev("However, you can only get #b10#k candies per day! Choose your recipients wisely.");	
	} else if (status == 3) {
	    qm.sendNextPrev("I will post the #rtop 5#k candy gifters at #b7:15 PM#k every day. You can give candies away between #b12 AM and 7PM#k, so be diligent!");
    } else if (status == 4) {	
	    qm.sendPrev("#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i3994650##bBoo Buddy Candy x1#k\r\n\r\nThanks for listening! Here's some candy #bfor your friends#k.\r\nHappy Halloween!");
	} else if (status == 5) {
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainItem(3994650, 1);
		qm.dispose();
	}
}

function end(mode, type, selection) {
	   qm.dispose();		
}