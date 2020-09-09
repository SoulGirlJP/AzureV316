/* Return to Masteria
	BeastTamer Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("Yoohoo! Stranger walking in the dangerous woods who I trust unconditionally? Can you help me?");
	} else if (status == 1) {
      qm.sendNextPrevS("A talking cat! Just the type of thing true heroes stumble upon in the woods!",14);
	} else if  (status == 2)  {
	  qm.sendNextPrev("My name is Arby, and I'm in a bit of a pickle. Mmmm, pickles... with mayonnaise!");
	} else if  (status == 3)  {
	  qm.sendNextPrev("I've been stuck in this trap for days, and I've been passing the time daydreaming about my favorite foods. Mm, shrimp pizza... cucumber skin salad...");
	} else if  (status == 4)  {
	  qm.sendAcceptDecline("But now I'm really, really, super starving. Mind whacking the trap by pressing #e#b[Ctrl]#k#n a few times and setting me free so I can get some good eats?");
	} else if  (status == 5)  {
	  qm.sendNextS("Sure. #e#b[Ctrl]#k#n, by the trap. Easy sauce for an aspiring hero!",14);
	} else if  (status == 6)  {
	  qm.sendNextPrev("Mmmm, sauce... mango chutney..");
	} else if  (status == 7)  {
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.warp(866104000,0);
	  qm.dispose();
	}
}