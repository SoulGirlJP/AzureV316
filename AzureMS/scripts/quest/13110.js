/* Dawnveil
    [Maple Castle] The Full Moon Dream
	Lania
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.forceStartQuest();
	  qm.dispose();
	}
}


function end(mode, type, selection) { 
    if (mode == 0 && type == 0) { 
        status--; 
    } else if (mode == -1) { 
        qm.dispose(); 
        return; 
    } else { 
        status++; 
    } 
    if (status == 0) { 
	    qm.sendAcceptDecline("Open #i2431140# #bGhastly Purple Letter#k?");
	} else if (status == 1) {	
        qm.sendNextS("#e#bHappy Halloween!#n#k Tonight was the best cure for 1000 years of boredom I could have imagined!",1);
    } else if (status == 2) {	  
        qm.sendNextPrevS("We're the students of #bMaple Castle#k. It's just as you heard #b#h ##k. We went to school here before we sealed it off from the Black Mage.",1);
	} else if (status == 3) {	
        qm.sendNextPrevS("Remember the Sealed Emblem from the sugar bowl? That was supposed to be our promised to come back every Halloween. I'm really glad we made that before we bit the dust!",1);
    } else if (status == 4) {	  
	    qm.sendNextPrevS("It's been 1000 years since we sealed up the school. You spend that long bored, you have to make a big come-back! That's why we wanted to trick all of you, in case you haven't figured out, we were the people dressed up in the main castle hall!",1);   
    } else if (status == 5) {	  
	    qm.sendNextPrevS("So, thanks for entertaining us! This has been the most exciting thing to happen in 1000 years. You don't have to report anything back to Neinheart, we already sent him a letter. Ah, I wish I could see his expression...",1);   
    } else if (status == 6) {	  
	    qm.sendPrevS("We've got 1000 years to think of more pranks. When you're dead, you can help us come up with some! Hahaha!",1);   
    } else if (status == 7) {  
	    qm.forceCompleteQuest();
		qm.removeAll(3994663);
		qm.removeAll(3994661);
		qm.warp(100000000,1);
		qm.dispose(); 
  } 
}  
