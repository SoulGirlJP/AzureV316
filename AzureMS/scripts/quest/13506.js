/* Return to Masteria
    [Collection] Cassandra's Latest Craze
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("You should make more of an effort to better yourself. I mean, your hands are the most important part of your body!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDecline("Hi, #b#h0##k! Do you like your hands? I hope so, because people that use their hands a lot are smarter! I read that in a book, so it must be true! I've got a great plan to keep my hands busy this year. Are you curious?");
	} else if (status == 1) {
	    qm.sendNextS("My new hobby may not sound grand, but it might be my best yet! I collect pebbles from different parts of Maple World, and then carve them into little monsters! Doesn't that sound fab? I'm using my hands AND flexing my creative juices.",1);
	} else if (status == 2) {
	   	qm.sendNextPrevS("Since you're still listening, you must be very interested. Well, do you see that icon for Cassandra's Collection on the left? It'll be there until #b 03/06/14 #k, and it's your key to my magical world of monster statues.",1);
	} else if (status == 3) {
		qm.sendNextPrevS("All YOU have to do is fill those nice little spots with monster statues. If you want statues, I'll give you one whenever you #bcomplete a <Cassandra's Collection> quest#k through the Event Notifier on the left side of the screen. Sometimes, I might even give you #i3994717# #r#t3994717##k if I'm feeling extra fabulous. Those can go in any space! I just like them so much.",1);
	} else if (status == 4) {
		qm.sendNextPrevS("#i3800451#\r\n\r\nIf you can line up #rthe monster statues horizontally, vertically, or diagonally#k, you'll complete a line! Complete #rthree lines#k, and you'll complete the whole collection! Complete as many as you want... I want to fill my living room with these!",1);
	} else if (status == 5) {
		qm.sendNextPrevS("I'll give you a gift whenever you complete a line. You'll also get one when you complete three lines and finish the collection. Good luck!",1);
	} else if (status == 6) {
        qm.forceStartQuest();
		qm.forceCompleteQuest();
        qm.dispose();
    }
}