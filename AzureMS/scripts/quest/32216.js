/* RED 1st impact
    Job selection
	Sugar
    Made by Daenerys
*/

var status = -1;
var sel = 0;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 7) {
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
        qm.sendNextS("You d-defeated the monsters, and you really helped me out, too, #h #. You seem r-ready to pick a Job. Did you decide which one you want?",1);
	} else if (status == 1) {
	    qm.sendNextPrevS("#bHuh? Job?#k",17);
	} else if (status == 2) {
	    qm.sendNextPrevS("There are five different Explorer Jobs. You can advance to them on Victoria Island. Hm, I think they were... Warrior, Magician, Bowman, Thief, and Pirate.",1);
	} else if (status == 3) {
		qm.sendNextPrevS("#bWhat are they like?#k",17);
	} else if (status == 4) {
	    qm.sendNextPrevS("Let's see. Warriors have great strength and defense, so they excel at close-range combat. Magicians use magic, so they f-favor intelligence over power, and they're good at long-range combat against multiple enemies.",1);
	} else if (status == 5) {
	    qm.sendNextPrevS("B-bowman are also good at long-range combat. They shoot arrows from afar and can keep enemies at a distance. And, let's see... Thieves are close-range, like warriors, but they focus on speed instead of strength.",1);
	} else if (status == 6) {
	    qm.sendNextPrevS("Finally, Pirates... are Pirates. Some use their fists in close-range combat, others shoot guns or cannons from afar. Their attacks are pretty fancy, either way.",1);
	} else if (status == 7) {
        qm.sendSimple("If you pick your Job right now, the captain offered to contact your new job instructor as soon as we pull into harbor. So, #h #, which Job do you want?\r\n\r\n#b#L0# Warrior, powerful and defensive#l\r\n#L1# Magician, intelligent and magical#l\r\n#L2# Bowman, long-ranged and controlled#l\r\n#L3# Thief, speedy and sneaky#l\r\n#L4# Pirate, fancy and unique#l#k");
    } else if (status == 8) {
        sel = selection;
	if (selection == 0) {		
	   qm.sendNextS("Oh, t-totally! #h #, you'll make a great Warrior!",1);
        } else if (selection == 1) {
		qm.sendNextS("Oh, t-totally! #h #, you'll make a great Magician!",1);
        } else if (selection == 2) {
		qm.sendNextS("Oh, t-totally! #h #, you'll make a great Bowman!",1);
        } else if (selection == 3) {
		qm.sendNextS("Oh, t-totally! #h #, you'll make a great Thief!",1);
        } else if (selection == 4) {
		qm.sendNextS("Oh, t-totally! #h #, you'll make a great Pirate!",1);
		}
	} else if (status == 9) {
	  if (sel == 0) {
        qm.sendNextS("#h #! Should I be a Magician if you're going to be a Warrior? I'm weak, but maybe with magic, I can help others.",1);
        } else if (sel == 1) {
		qm.sendNextS("Maybe I should become a Warrior. I want to learn to stand on my own two feet and use my strength to help others.",1);
        } else if (sel == 2) {
		qm.sendNextS("Maybe I should become a Thief. I want to learn to stand on my own two feet and use my strength to help others.",1);
        } else if (sel == 3) {
		qm.sendNextS("Maybe I should become a Pirate. I want to learn to stand on my own two feet and use my strength to help others.",1);
        } else if (sel == 4) {
		qm.sendNextS("Maybe I should become a Bowman. I want to learn to stand on my own two feet and use my strength to help others.",1);
		}
    } else if (status == 10) {
	  if (sel == 0) {
	    qm.forceStartQuest(1401);
        qm.showAdvanturerBoatScene();
		qm.dispose();
        } else if (sel == 1) {
		qm.forceStartQuest(1402);
		qm.showAdvanturerBoatScene();
		qm.dispose();
        } else if (sel == 2) {
		qm.forceStartQuest(1403);
		qm.showAdvanturerBoatScene();
		qm.dispose();
        } else if (sel == 3) {
		qm.forceStartQuest(1404);
		qm.showAdvanturerBoatScene();
		qm.dispose();
        } else if (sel == 4) {
		qm.forceStartQuest(1405);
		qm.showAdvanturerBoatScene();
		qm.dispose();
	   }
	    qm.dispose();
    }
}