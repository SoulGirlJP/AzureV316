/* Grand Athenaeum
    The Explorer Book And A Maple Leaf
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 3) {
		    qm.sendNextS("Eh? No? Why? What about your adventures? Your memories? My entertainment?",4,9010010);
            qm.dispose();
        status--;
    }
    if (status == 0) {
	  qm.sendNextS("Hello #b#h0##k!\r\nI'm #bCassandra#k.",4,9010010);
	} else if (status == 1) {
      qm.sendNextPrevS("How do I know your name? That's a silly question. I'm Cassandra. I totally know everything!",4,9010010);
	} else if  (status == 2)  {
	  qm.sendNextPrevS("I came to give you a gift. It's an #bExplorer Book#k, kinda like a diary. In this, you can record every exciting adventure you'll ever have! And then I can read about it later!",4,9010010);
	} else if  (status == 3)  {
	  qm.sendYesNoS("Do you want the #bExplorer Book#k? You do, right?",16);
	} else if  (status == 4)  {
	  qm.sendNextS("Let's see... I know there's a book that's just perfect for a Adventurer like you...",4,9010010);
    } else if  (status == 5)  {
	  qm.sendNextPrevS("Found it!  Here. Take a good look at it after I take off.",4,9010010);
    } else if  (status == 6)  {
	  qm.sendPrevS("Well, have a blast in your adventures!",4,9010010);
    } else if  (status == 7)  {	
      qm.forceStartQuest();
	  qm.gainItem(4460000,1);
	  qm.dispose();
	}
 }

function end(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNextS("An #bExplorer Book#k? So, I can record all my adventures here?",16);
	} else if (status == 1) {
	    qm.sendNextPrevS("I've already had a few adventures, but I still haven't learned much about Maple World. Time for a fresh start! Except...",16);
	} else if (status == 2) {
	    qm.forceStartQuest();
	    qm.forceCompleteQuest();
        qm.showMapleLeafScene();
		qm.gainItem(2040804,1);
	    qm.dispose();
	}
}