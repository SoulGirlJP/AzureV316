/* RED 1st impact
    [Gingerbread] Save Me, Talking Cookie!
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 4) {
		    qm.sendOk("Okay, sure. Just come back with the character who's ready for some serious baking!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendNext("Hello, I'm the Gingerbread Man! But wait, don't eat me! I'm not here for that. I'm here for a favor.");
	} else if (status == 1) {
	    qm.sendNextPrev("I have lots of friends who haven't been baked to perfection yet. I want to help them get out of their doughy phase, but I might burn if I get near another oven. Can you #bhelp me out once a day#k and bake some of my gingerbread friends?");
	} else if (status == 2) {
	    qm.sendNextPrev("I'll reward you, of course. You still won't get to eat me, but I'll give you sme neat stuff depending on how much you help.");
	} else if (status == 3) {
	    qm.sendNextPrev("#i03800483#");
	} else if (status == 4) {
	    qm.sendAcceptDecline("Now, do you want to use this character to #bmake Gingerbread Men?#k Think this through, because #ronly one character per nexonID may complete the Gingerbread Man event.)#k");
	} else if (status == 5) {
	   qm.sendNext("Let me tell you a little about the Gingerbread Man Bake! It's not difficult. Just hold onto the #i3994801##b#t3994801##k I give you for #r30 minutes#k! That's it! Easy, right?");
	} else if (status == 6) {
	    qm.sendNextPrev("After 30 mintues the #i3994801##b#t3994801##k in the dough form will change into #i3994802##b#t3994802##k. Just bring that #b#t3994802##k to me and the mission will be complete!");
	} else if (status == 7) {
	   qm.sendNextPrev("I've got a gift ready for each time you participate, so make sure to get your baking in every day. And remember that this can only be #rdone by one character on each nexonID#k. It's a rare event so remember that if you end up deleting this character, then you will no longer be able to participate in the #bGingerbread Man#k event. Scary, right?");
	} else if (status == 8) {
	    qm.sendPrev("Me and my doughy friends will all be waiting for your help.\r\nDon't leave without lending a hand! I will be waiting!");	
        qm.forceStartQuest();
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
}