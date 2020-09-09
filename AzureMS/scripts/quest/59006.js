/* Return to Masteria
	BeastTamer Tutorial
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
	  qm.sendNext("Hey! Looks like you #bleveled#k up from fighting those wolves");
	} else if (status == 1) {
	  qm.sendNextPrevS("I did, what now?",14);
	} else if  (status == 2)  {
	  qm.sendNextPrev("Hee hee, you leveled up! You gain #bEXP#k for hunting monsters, see? When you get enough EXP, you #blevel up#k!");
	} else if  (status == 3)  {
	  qm.sendNextPrevS("Sounds awfully heroic. Tell me more!",14);
	} else if  (status == 4)  {
	  qm.sendNextPrev("Each time you level up, you get more HP. You also get #bAbility Points#k, which you can use to increase your stats. But I'd just use the #bAuto-Assign#k button in that Stat window. It's a lot faster.");
	} else if  (status == 5)  {
	  qm.sendAcceptDecline("Do it now, do it now! Open your #bstat window#k and distribute your Ability Points!");
	} else if  (status == 6)  {
	  qm.sendNext("Press #b[S]#k to open your stats window.\r\n#i03800628#");
	} else if  (status == 7)  {
	  qm.forceStartQuest();
	  qm.forceCompleteQuest();
	  qm.gainExp(50);
	  qm.gainAp(10);
	  qm.OpenUI(2);
	  qm.dispose();
	}
}