/* RED 1st impact
    [Smart Mount] Success with Irvin
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 2) {
		    qm.sendNext("I thought I smelled a hoodlum. I didn't want to waste my time on you anyway.");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendNext("You there. I'm sure you've heard of my amazing Auto-pilot system that I INVENTED. Did I mention that I invented it?");
	} else if (status == 1) {
		qm.sendNextPrev("Auto-pilot is now available on #bVictoria Island AND El Nath#k! Did I mention that I invented the system? It just came to me one day. No big deal.");
	} else if (status == 2) {
	    qm.sendYesNo("I'm sure you want to hear me explain how to use Auto-pilot in El Nath.");
	} else if (status == 3) {
	    qm.sendNext("My Auto-pilot system is currently available in El Nath, Rien, and Ereve. You have no idea how much work I had to put in to make that happen. You ready for the details? Of course, it'll cost you...");
	} else if (status == 4) {
	    qm.sendNextPrev("Hey, I'm saving for retirement! Anyway, I'll let you use Auto-pilot for 900 mesos in Victoria Island, Rien, and Ereve.");
	} else if (status == 5) {
	    qm.sendNextPrev("To use Auto-pilot in El Nath, you'll need to buy an #bEl Nath Flight Permit#k. Hey, what luck! I just so happen to be holding a special deal right now! Buy a permit, and you'll also get special service for free!");
	} else if (status == 6) {
	    qm.sendOk("Why are you hesitating? Don't you want special service? Anyway, I'll be waiting for you at the Station in each continent, as usual, so buy an #bEl Nath Flight Permit#k from me and then talk to me again.");
	} else if (status == 7) {
        qm.forceStartQuest();
        qm.dispose();
    }
}