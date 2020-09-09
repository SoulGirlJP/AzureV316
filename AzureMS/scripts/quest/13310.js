/* Return to Masteria
	Lucky Lucky Monstory
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 1) {
		    qm.sendOk("Visit me again when you change your mind. Because you WILL change your mind. Oh yes.");
            qm.dispose();
        status--;
    }
	if (status == 0) {
	  qm.sendAcceptDecline("Are you here to get into some Lucky Lucky Monstory?");
	} else if (status == 1) {
	  qm.sendNextS("It's really quite simple. If you hunt a monster, you will get #b#t2431318##k. You can use that to obtain a specific Monster Card. After that, the Monster Card will be listed in your Monster Card Collection automatically.",1);//noescape
	} else if  (status == 2)  {
	  qm.sendNextPrevS("You can check your collected cards by hitting the Event Notifier icon on the left side of the screen.",1);
	} else if  (status == 3)  {
	  qm.sendNextPrevS("Mash the #bAccept Reward button#k on the right as soon as you nab six Monster Cards to receive a gift. Then, you can enjoy the rest of the event. Or do whatever, really.",1);
	} else if  (status == 4)  {
	  qm.sendNextPrevS("Also, you can get up to three lines of Monster Cards per day. Keep the Monster Cards that you've collected and compare them with the Winning Monster Card on #bevery Monday at 6pm#k. If you have the Winning Monster Card, then you'll get a BIG prize. HUGE, even!",1);
	} else if  (status == 5)  {
	  qm.sendNextPrevS("You win first prize if all of the six cards in a line are the same as the winning cards! If 5 of them are the same, then you'll win the second prize. If if if you have 4 matching cards, then you'll win third prize! If you have less than 4 matching cards, well... Just don't have less than 4, and it won't be an issue.",1);
	} else if  (status == 6)  {
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.dispose();
	}
}