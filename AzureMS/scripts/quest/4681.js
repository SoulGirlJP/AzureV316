/*
	NPC Name: 		Asia
	Description: 		Quest - The door to the future
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
	return;
    } else if (mode == 0) {
	if (status == 40) {
	    qm.sendOk("I understand that you feel trepidations about helping out a strange town like Zipangu. I am the one that is unaffected by #bThe Spell of Acacia#k, which means I will have to risk my life to save this town from total destruction.");
	    qm.dispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    switch (status) {
	case 0:
	    if (qm.getQuestCustomData() != null) { // if (qm.getQuestCustomData().equals("readHistory")) {
		qm.sendSimple("Good to see you back, Mapler. How did you fare against the goblin? #b \n\r #L0#I was able to defeat the monster of Ninja Castle!#l \n\r #L1#No, I wasn't able to.#l");
		status = 99;
	    } else {
		qm.sendNext("My name is #p9120025#, and I am the protector of Zipangu at \n#m802000101#.");
	    }
	    break;
	case 1:
	    qm.sendNextPrev("(#p9120025# flashed a smile.)");
	    break;
	case 2:
	    qm.sendNextPrev("Oh... I see that you have been making proper strides. \n\n&lt;Grit... you were right...&gt;");
	    break;
	case 3:
	    qm.sendNextPrev("I remember when you first stepped into Maple Island, looking very green to say the least... and here you are, standing right in front of me looking every bit like a true Mapler. As someone that closely followed your path, I feel... a rush of satisfaction seeing you all grown up.\n\n(#p9120025# begins to well up)");
	    break;
	case 4:
	    qm.sendNextPrev("...I apologize for speaking in a way that makes you feel that I know you more than I really do. I'm sorry.");
	    break;
	case 5:
	    qm.sendNextPrev("My task is to determine who actually has power, and who is in need of one.");
	    break;
	case 6:
	    qm.sendNextPrev("And in the end, my ultimate goal is to find the person that is capable of rescuing Zipangu from destruction.");
	    break;
	case 7:
	    qm.sendNextPrev("This land right here is the lowest, most distant land you'll find in #m802000101#.");
	    break;
	case 8:
	    qm.sendNextPrev("I will now give you a dose of truth on Zipangu.");
	    break;
	case 9:
	    qm.sendNextPrev("Zipangu...");
	    break;
	case 10:
	    qm.sendNextPrev("Zipangu... will be destroyed a 100 years from now.");
	    break;
	case 11:
	    qm.sendNextPrev("...Yes, Zipangu will one day completely disappear from this world without a trace. I saw it with my own eyes how this ball of #bdistortion#k appeared from the sky and completely swallowed up Zipangu as a whole.");
	    break;
	case 12:
	    qm.sendNextPrev("Zipangu in the ancient days was a hotbed for black magic, and I am a magician in Zipangu who grew up learning the intricacies of the black magic.");
	    break;
	case 13:
	    qm.sendNextPrev("At the time, Zipangu had strong ties with Magatia, sharing valuable informations with one another. You may be living in an age where science rules the world, but back in the day, Magatia was home to various studies regarding the black magic.");
	    break;
	case 14:
	    qm.sendNextPrev("Magatia, the forefront of magic powers.\nThat's where I met a wizard, who taught me everything and... in the end... I was able to acquire a special power that allowed me to achieve #bimmortality#k.");
	    break;
	case 15:
	    qm.sendNextPrev("It was the magic that no one could achieve until then. I used it wisely for the sake of Zipangu.\n As time went on, however, new generations came and went, and immortality became an outlawed practice. All of a sudden, I was considered an outcast, embraced by no one. I lost everything; fame, fortune, country, you name it, I lost it. In the end, I went into seclusion in the mountains of Zipangu, eager to use my power of immortality to view the apocalypse, the day the world ends for good.");
	    break;
	case 16:
	    qm.sendNextPrev("But the apocalypse... came much, much sooner than I anticipated.");
	    break;
	case 17:
	    qm.sendNextPrev("The moment Zipangu got swallowed up by a giant \n#bdistortion#k and vanished, I used a time-warp magic to enter the world after the vanishment. And that's where I found it.");
	    break;
	case 18:
	    qm.sendNextPrev("That's where I found the truth of Zipangu.");
	    break;
	case 19:
	    qm.sendNextPrev("It turned out that the history of Zipangu is chronicled in a single book.");
	    break;
	case 20:
	    qm.sendNextPrev("The history of Zipangu was just a reenactment of the contents listed in the book.");
	    break;
	case 21:
	    qm.sendNextPrev("The fact that a supernatural book that chronicles every move of every resident of Zipangu exists... the book that's called #bAcacia Chronicle#k...");
	    break;
	case 22:
	    qm.sendNextPrev("I don't know about you, but I was furious. Furious at the thought of my life being pre-determined by this miniscule copy of a book...");
	    break;
	case 23:
	    qm.sendNextPrev("In that world of distortion, I was able to get my hands on \n#bAcacia Chronicle#k, and started reading it.");
	    break;
	case 24:
	    qm.sendNextPrev("And then I realized...");
	    break;
	case 25:
	    qm.sendNextPrev("I realized that #bAcacia Chronicle#k did not include informations about me.");
	    break;
	case 26:
	    qm.sendNextPrev("I was someone that did not exist, according to the book.");
	    break;
	case 27:
	    qm.sendNextPrev("It might be that the reason why I was freed from the #bcurse of Acacia Chronicle#k was because I was able to form a close bond with someone outside Zipangu, which in the end led me to achieve #bimmortality#k.");
	    break;
	case 28:
	    qm.sendNextPrev("It also remained to be seen whether #bAcacia Chronicle#k can be subject to change in contents for revised editions.");
	    break;
	case 29:
	    qm.sendNextPrev("So my plan was to return to the past and alter the history myself, so the part where the #bdistortion#k appeared in the book would be destroyed at the last minute, and prevent Zipangu from a certain destruction.");
	    break;
	case 30:
	    qm.sendNextPrev("But in the end, I realized that tampering with the past, even if it meant saving Zipangu, would be a foolish act.\nAltering the past through the book would most certainly wipe out the very existence of all those people that would be born in the future.");
	    break;
	case 31:
	    qm.sendNextPrev("In the end, I came to conclusion that the best course of action would be to alter the history of Zipangu RIGHT BEFORE the destruction.");
	    break;
	case 32:
	    qm.sendNextPrev("The problem is that altering the history is not a one-time deal. History has its ability to revise itself, so even if I alter a portion of it, the history would try to remedy that by returning to its pre-alteration stage.");
	    break;
	case 33:
	    qm.sendNextPrev("Today, I spend my days observing Zipangu which is right on the verge of destruction, with the starting point being #m802000101#.");
	    break;
	case 34:
	    qm.sendNextPrev("And... one more thing...");
	    break;
	case 35:
	    qm.sendNextPrev("I see a huge wave of history revision coming soon, and it's such that even I can't do anything about it.");
	    break;
	case 36:
	    qm.sendNextPrev("I am fully aware that since this is all about Zipangu, it's only right that a resident of Zipangu should take care of this in-house.");
	    break;
	case 37:
	    qm.sendNextPrev("Unfortunately, #bThe Spell of Acacia#k has prevented Zipanguans from directly altering the history themselves.");
	    break;
	case 38:
	    qm.sendNextPrev("For the future of Zipangu...");
	    break;
	case 39:
	    qm.sendNextPrev("(#p9120025# suddenly gets down on her knees)");
	    break;
	case 40:
	    qm.sendYesNo("I would like for you to help us save Zipangu, the one that is unaffected by #bThe Spell of Acacia#k. Will you?");
	    break;
	case 41:
	    //	    qm.forceStartQuest();
	    qm.setQuestCustomData("readHistory");
	    qm.sendNextPrev("Thank you, Mapler... thank you...#p9120025# breathes a heavy sigh of relief)");
	    break;
	case 42:
	    qm.sendNextPrev("With the kind of power you possess, I have no doubt in my mind that you are capable of holding your own against the powerful foes from era.");
	    break;
	case 43:
	    qm.sendNextPrev("That being said... I want to see the extent of your power with my very own eyes.");
	    break;
	case 44:
	    qm.sendNextPrev("I had been in search of someone that possessed immense talent and power for the past few thousand years.\nThe era where Zipangu is on the verge of destruction is a scientifically-advanced era where the monsters you'll face against are beyond your wildest imagination.");
	    break;
	case 45:
	    qm.sendNextPrev("To find someone that's capable of battling those monsters, I formulated a test that'll weed out the bad candidates.");
	    break;
	case 46:
	    qm.sendNextPrev("Zipangu used to be divided by a number of forces, each hoping to conquer and unify the land.");
	    break;
	case 47:
	    qm.sendNextPrev("The force that was the most prominent was the one that based itself on Ninja Castle. \nWith the outstanding soldiers they call Ninjas protecting the castle, Zipangu under the Ninja Castle was peaceful to say the least.");
	    break;
	case 48:
	    qm.sendNextPrev("This should have been the beginning of an era where peace and tranquility rule the land, but that wasn't the case. A goblin that noticed the immense power of ninjas decided to enter the body of the castle lord and wound up taking over the Ninja Castle.");
	    break;
	case 49:
	    qm.sendNextPrev("For the next 10 years, the lord of the castle that was controlled by the goblin made life here miserable, but one day, a brave Samurai appeared in Zipangu and proceeded to defeat the goblin, bringing peace back to this place.");
	    break;
	case 50:
	    qm.sendNextPrev("In the history of Zipangu, monsters and goblins that possess the special powers of Ninjas have been notorious for their immense power.");
	    break;
	case 51:
	    qm.sendNextPrev("The goblin in the Ninja Castle, I believe, is a good indicator of gauging the powers of prospective candidates that will later battle the monsters of the future.");
	    break;
	case 52:
	    qm.sendNextPrev("I also realized that the goblin, which had reigned supreme for 10 years, does not affect the course of the future of Zipangu if defeated by an individual that is unaffected by \n#bThe Spell of Acacia#k.");
	    break;
	case 53:
	    qm.sendNextPrev("I prepared the Worm Hole at Mushroom Shrine, which will allow one to travel to the past, and brought residents of the Maple World to the Ninja Castle to test out their strength and see whether they were strong enough to battle the monsters from the future.");
	    break;
	case 54:
	    qm.sendNextPrev("If you cannot defeat the goblin in the castle, then you will not be able to even compete against those monsters from the future.");
	    break;
	case 55:
	    qm.sendNextPrev("I am sure you are plenty strong enough, but I want concrete proof that you can do it.");
	    break;
	case 56:
	    qm.sendNext("Please defeat the Ninja Goblin at the Ninja Castle and bring back the following items as proof: #b300 #t04000340#s, 1 #t04000342#, and 1 #t04000343#.");
	    qm.dispose();
	    break;
	case 100:
	    if (selection == 0) {
		if (qm.haveItem(4000343, 1) && qm.haveItem(4000340, 300) && qm.haveItem(4000342, 1)) {
		    status = 119;
		    qm.sendNextPrev("Brilliant.");
		} else {
		    qm.sendNext("This isn't enough for me to gauge the extent of your power. I want you to defeat more monsters inside the castle and bring sufficient amount of evidence, okay? #b300 #t04000340#s, 1 #t04000342#, and 1 #t04000343#.");
		    qm.dispose();
		}
	    } else {
		if (qm.haveItem(5252002, 1)) {
		    status = 129;
		    qm.sendNextPrev("Brilliant.");
		} else {
		    status = 109;
		    qm.sendNext("If you do not have evidence that you have defeated the goblin at the castle, then I can't send you to the future. The future is full of monsters that far exceeds that of the goblin.");
		}
	    }
	    break;
	case 110:
	    qm.sendNextPrev("Please defeat the goblin at Ninja Castle and bring me an evidence.");
	    break;
	case 111:
	    qm.sendPrev("I hear stories that you may buy your evidence at a place called Cash Shop... but if you are good at combat, then you really don't need to spend money on stuff like that. I already feel the wave of restoration hitting this place. I'll now leave it up to you.");
	    qm.dispose();
	    break;
	case 120:
	    qm.sendNextPrev("Now I know for sure that you are more than capable of battling the monsters of the future from Zipangu.");
	    break;
	case 121:
	    qm.sendNextPrev("I will now hand you a #bLv 1 Gate Pass#k. It's an evidence that you're from #m802000101#, and the pass is required for you to enter the future of Zipangu. Please do not lose this pass.");
	    break;
	case 122:
	    qm.sendNextPrev("This will not enable you to open the door to Zipangu's future, and take part in altering the history of this great town.");
	    break;
	case 123:
	    qm.sendNextPrev("I have a mission for you right now that I'd like for you to accept immediately. Are you ready?");
	    break;
	case 124:
	    qm.gainItem(4000343, -1);
	    qm.gainItem(4000340, -300);
	    qm.gainItem(4000342, -1);
	    qm.forceStartQuest();
	    qm.dispose();
	    break;
	case 130:
	    qm.sendNextPrev("Now I know for sure that you are more than capable of battling the monsters of the future from Zipangu.");
	    break;
	case 131:
	    qm.sendNextPrev("I will now hand you a #bLv 1 Gate Pass#k. It's an evidence that you're from #m802000101#, and the pass is required for you to enter the future of Zipangu. Please do not lose this pass.");
	    break;
	case 132:
	    qm.sendNextPrev("This will not enable you to open the door to Zipangu's future, and take part in altering the history of this great town.");
	    break;
	case 133:
	    qm.sendNextPrev("I have a mission for you right now that I'd like for you to accept immediately. Are you ready?");
	    break;
	case 134:
	    qm.gainItem(5252002, -1);
	    qm.forceStartQuest();
	    qm.dispose();
	    break;
	default:
	    qm.dispose();
	    break;
    }
}

function end(mode, type, selection) {
}