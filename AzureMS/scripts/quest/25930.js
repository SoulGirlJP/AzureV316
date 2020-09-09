/* Dawnveil
	Visiting Your Farm
	Clara
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNext("Hello there, #b#h ##k. What do you think of monsters? You probably think they're scary and they're going to eat your bones, huh?");
	} else if (status == 1) {
      qm.sendNextPrev("What if I told you I knew a place where you could raise your own monsters that would eat OTHER people's bones... maybe? Don't you want to live the Monster Life?");
    } else if (status == 2) {	    
	  qm.sendAcceptDecline("Do you want to listen to me give you instructions now? \r\n#r(Click Accept to move to the tutorial.)");
    } else if (status == 3) {
		qm.warp(100000000);
		qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}