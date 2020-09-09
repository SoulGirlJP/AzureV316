/* Dawnveil
    [Maple Castle] The Grand Finale
	Neinheart
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
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNextS("Did you collect the #bMaple Castle clues#k from Phantom, Lania, and Angelic Buster?",1);
	} else if (status == 1) {
	    qm.sendNextPrevS("Ah, so that's how #bMaple Castle#k faded away into history... I doubt we'll find any remaining magic if the students already destroyed it all.\r\nStill, I have nothing but respect for the Maple Castle students who made such sacrifices to prevent the Black Mage from becoming stronger.", 1);
	} else if (status == 2) {
	    qm.sendNextPrevS("Thanks for all your help, #b#h ##k. I don't think we should pry into Maple Castle history any further. Seems it was another victim of the Black Mage's nefarious schemes. What a shame.",1);	
	} else if (status == 3) {
	    qm.sendNextPrevS("......",1);
        } else if (status == 4) {    
            qm.sendNextPrevS("I can't help but notce your skeptical gaze. Forget what I said earlier about the empress's test sheet. I suggest you be about your business.",1);
	} else if (status == 5) {
	    qm.sendPrevS("#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i2431132##bHalloween Mask Fragment x4#k\r\n#i3994650##bBoo Buddy Candy x1#k\r\n\r\nAnyway, thank you for your help with finding the castle secrets.",1);
	    qm.forceCompleteQuest();
	    qm.removeAll(3994657);
        qm.removeAll(3994658);
        qm.removeAll(3994659);
	    qm.gainItem(2431132, 4);
	    qm.gainItem(3994650, 1);
	    qm.dispose();		
	}
    }
}