/* Dawnveil
    [Halloween] A Fine, Fine Costume
	Maple Administrator
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Boo! Hey I got a #blittle something#k for all our Halloween Maplers. To be honest, I bought a ton of costumes and now I need to get rid of them. my cat is super allergic. Which one should I hand out today?");
	} else if (status == 1) {		
	    qm.sendNextPrev("Come see me during the Halloween Event, and I'll give you #bone of 6 Halloween outfits!#k\r\nYou can #bonly wear it today#k, though. You don't want to be behind the times.");	
    } else if (status == 2) {	
	    qm.sendNextPrev("Also, you MUST be wearing a #rHalloween outfit#k to enter #e#bMaple Castle#k, which is only accessible during the Halloween event!");	
	} else if (status == 3) {
	    qm.sendNextPrev("Here's your Halloween costume! The #rcostume festival#k will last throughout the #bHalloween Event#k, so keep it spooky!");
    } else if (status == 4) {	
	    qm.sendSimple("#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i1002877##bCow Mask x1#k\r\n#i1052179##bCow Costume x1#k\r\n\r\nEnjoy your gifts!");
	} else if (status == 5) {
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainItem(1002877, 1);
		qm.gainItem(1052179, 1);
		qm.gainItem(4310101, 2);
		qm.dispose();
	}
}

function end(mode, type, selection) {
	   qm.dispose();		
}