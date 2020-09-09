/* Dawnveil
    Gachapon Reborn
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
		qm.sendAcceptDecline("Starting today, you'll bee seeing a whole new Gachapon around Maple World. I know, because I have a whole storage closet full of OLD Gachapon and it's drive me insane... Do you want to hear about it?");
    } else if (status == 1) {	   
        qm.sendOk("There are a lot of changes./r/n/r/nFirst up, we've moved all of the Gachapons into one place. No more running around town to find them./r/nSecond, I personally did a massive house-cleaning on the... less than useful items that were popping up. For example, for equips,you'll only find top quality, mostly level 70 and up items./r/nFinally, all the other Gachapons were combined. Now you can get chairs, mounts and Nebulite with a single convenient ticket!/r/n/r/nYou'll probably want to exchange all those old Nebulite and mount tickets. Just go talk to the Gachapon NPC and trade them in. You can use your existing chair tickets as normal though. Just double-click to get your reward!");	
	} else if (status == 2) {	   
		qm.forceStartQuest();
		qm.dispose();
	}
}

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	  qm.sendNext("Good luck on your rewards! Wish me luck on the inventory I have to do now.");
	  qm.forceCompleteQuest();
	  qm.dispose();		
	}
  }
}