var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendYesNo("We cannot let those aliens take out precious ore! We need to. I don't know, sabotage them? That sounds right. doesn't it! Go sabotage the heck out of them!");
    } else if (status == 1) {
	qm.sendNext("Alright, a good sabotage job needs a good sabotage plan! I've got four really great sabo-ideas.");
    } else if (status == 2) {
	qm.sendNext("Your first sabo-mission is in Jungle Valey. They've got Gunpowder Mounds meant for blasting. Light one of those up. While you're at it, I think you could take out one of those Galacto-Drills. They look really flimsy. Heck, I bet they'd blow up if you chucked some rocks into those holes they're digging'.");
	} else if (status == 3) {
	qm.sendNext("Oh, I just thought of another thing that'll really cheese 'em off! Go steal some of their stampies and mess up their computers! If I were an alien, I'd put that stuff right near the entrance to my intergalactic mining base. Let's just hope those aliens use the same operating system as us...");
	} else if (status == 4) {
	qm.forceStartQuest();
	qm.dispose();
    }
}


function end(mode, type, selection) {
	if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
if (status == 0) {
	qm.sendNext("How are the sabo-missions going? Are you sabotaging up a storm?");
    } else if (status == 1) {
	if(qm.itemQuantity(4033192) < 1){
	qm.sendPrev("You haven't sabotaged enough! There's still so much to do!\r\n\r\n#bSteal 1 #v4033192# from aliens#k: (" + qm.getPlayer().getItemQuantity(4033192, false) + "/1)");
    qm.dispose();
	} else {
	qm.sendNextPrev("I knew you'd mess up their operations!");
	}
	} else if (status == 2) {
	qm.sendPlayerToNpc("The aliens are holding a bunch of people hostage! I'm not sure why yet, but I have a very strong theory that people-eating is involved! They must be interstellar space chefs!");
	} else if (status == 3) {
	qm.sendNextPrev("Hmm, your theory is both ludicrous and implausible, but it's the best we've got! At least we know the people are still alive.");
	} else if (status == 4) {
	qm.gainExp(2000000);
	qm.forceCompleteQuest(28748);
	qm.dispose();
    }
}

