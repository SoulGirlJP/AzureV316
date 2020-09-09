/* RED 1st impact
    [Attendance] Winter Attendance - Season 1
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendNext("Really? Aww... Well, talk to me again if you change your mind!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendYesNo("Hello, #b#h0##k!\r\nYou know that #e#bWinter Attendance Check Season 1#k#n is in progress, right? Wanna try now?");
	} else if (status == 1) {
        qm.forceStartQuest();
        qm.dispose();
    }
}