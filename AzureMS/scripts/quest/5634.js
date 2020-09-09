/* RED 1st impact
    [All 4 One] Invite your friends and your own All 4 One!
	All 4 One
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNextS("#e#bOne for all, all for one! Gather up a Crew Boss and 3 Members to form your Crew!#k#n\r\n\r\nAny players with a character over level 100 before 12/4 can participate in the #e#bAll 4 One Event#k#n as a Crew Boss.\r\nDon't be too sad if you didn't qualify to be a Crew Boss. Any characters created after 12/4 can join the festivities as a Crew Member instead!",5);
    } else if (status == 1) {	   
        qm.sendNextPrevS("The Crew Boss can invite any characters created\r\nbetween 12/4 and 12/31 to their Crew, then help them reach Level 100 (Lv. 180 for Zero) to earn #i4310088:# #e#b#t4310088#(50)#k#n.\r\nIf the Crew Member reaches their target on time, they'll earn\r\n1. #i4310088:# #e#b#t4310088#(25)#k#n\r\n2. #i5150052:# #e#b#t5150052##k#n\r\n3. #i5150056:# #e#b#t5150056##k#n\r\n4. #i5151035:# #e#b#t5151035##k#n",5);	
	} else if (status == 2) {	
	    qm.sendNextPrevS("The Crew Boss can invite up to 3 members to join their Crew, and if everyone reaches their target level by 01/31/2014, the Crew Boss will get an immediate boost of #e#bMaple Point : 10000#k#n Amazing, right?",5);	
	} else if (status == 3) {	
	    qm.sendNextPrevS("On top of that, all 4 members of the Crew will get these awesome rewards!\r\n1. #i1114000:# #e#b#t1114000##k#n\r\n2. #i1142668:# #e#b#t1142668##k#n\r\n3. #i5010115:# #e#b#t5010115##k#n",5);
	} else if (status == 4) {	
        qm.sendNextPrevS("Oh, and if the Crew Boss succeed in inviting a Mapler that has never participated in the #e#bAll 4 One Event#k#n,\r\n the Crew Member will get #i2450067:# #e#b#t2450067#(3)#k#n. It pays to help a newbie!",5);	
	} else if (status == 5) {	
        qm.sendNextPrevS("Cool, huh? You should get your own Crew together and do your best to help each other win! All for one!",5);		
	} else if (status == 6) {	
        qm.sendNextPrevS("You are eligible to be a Crew Member!\r\nFind yourself a Crew Boss and team up with your fellow Maplers to earn excellent rewards!",5);		
	} else if (status == 7) {	
        qm.sendPrevS("Only characters created AFTER 12/4/2013 get to be Crew Members.\r\nThis is a great time to make a new character, and join in the All 4 One Event!",5);			
	} else if (status == 8) {	
	    qm.dispose();
	}
}

function end(mode, type, selection) {
	   qm.dispose();		
}