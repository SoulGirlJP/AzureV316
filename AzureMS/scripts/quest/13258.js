/* RED 1st impact
	[Christmas] The Grand Christmas Tree
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendNext("What's Christmas without a Christmas Tree? It's like a Ribbon Pig with no ribbon! You MUST reconsider!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
		qm.sendAcceptDecline("Hey there, #b#h0##k. Did you see the giant tree in town?\r\nIt's supposed to be our Christmas Tree, but there aren't enough decorations. It's... quite lacking.\r\n#b#i4001779##t4001779##k would really liven things up.\r\nYou want to help out?");
	} else if (status == 1) {
        qm.sendNext("You'll find #i4001779# #b#t4001779##k while hunting monsters until 31/12/2013. These items can be used to decorate the town tree.\r\nMonsters that aren't #bbetween 11 levels below and 21 levels above you#k won't carry them, though.\r\n\r\nI think #e#r400#k should be sufficient#n.");
    } else if (status == 2) {	  	 
        qm.sendOk("#b#h0##k, I'll give you a magnificent Christmas gift if you decorate the Christmas Tree.\r\nI have faith in you!");    
    } else if (status == 3) {	 	   	
		qm.forceStartQuest();
		qm.dispose();
	}
}