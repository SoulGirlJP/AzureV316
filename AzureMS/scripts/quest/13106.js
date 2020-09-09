/* Dawnveil
    [Maple Castle] The Hero's Gauntlet
	Angelic Buster
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	    qm.sendNextS("Over here! Hey, isn't this AWESOME? Oh, right, I haven't told you what it is. Okay, welcome to the #bHero's Gauntlet#k!\r\n...Wait. Wait wait wait. You're...NOT here for the Hero's Gauntlet?You want the #bMaple Castle clue#k? Oh...yeah...that...ha\r\nha...",1);
	} else if (status == 1) {
	    qm.sendAcceptDecline("Are you suuuuuuuuuuure you reeeeeeeeeeeally need that?\r\n...Oh, Neinheart's getting impatient, huh...\r\nWell, I was kinda busy running this super cool, totally rad challenge for cool and rad people. Why don'tcha give the #bHero's Gauntlet#k a try while I...put the finishing touches on this clue...");
	} else if (status == 2) {
	    qm.forceStartQuest();
		qm.forceCompleteQuest();
		qm.gainItem(3994657,1);
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
	    qm.sendNextS("I knew you'd have fun! You can try it again anytime. You and me are buddies now, so I'll let you skip the line, hee hee! Oh, and you needed the \r#i3994657##b#t3994657##k right?\nWell, I'm done with it! Here, check this out...",1);
	} else if (status == 1) {
	    qm.sendNextPrevS("A-hem.\r\n&quot;#bThe Black Mage continues to grow stronger. The school is in chaos. Maple Castle may fall sooner than we thought. We cannot lose a thousand years worth of magic knowledge to these villains.&quot;", 1);
	} else if (status == 2) {
	    qm.sendNextPrevS("Pretty big downer, isn't it? The rest of the pages got shredded. #bIt's true that there was a secret magic power hidden away in Maple Castle#k. It must've been amazing, since the big, bad #bBlack Mage#k had his eye on it. ",1);	
	} else if (status == 3) {
	    qm.sendPrevS("I bet the Black Mage is why #bMaple Castle#k shut down. So, there you go! Time to tell #rNeinheart#k all about this, right? I did my part. Free at last!",1);
	    qm.dispose();		
	}
  }
}