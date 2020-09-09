/* RED Zero
    [Attendance] Pink Heater Fans
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else 
        if (status == 0) {
		    qm.sendOk("Really? Aww... Well, talk to me again if you change your mind!");
            qm.dispose();
        status--;
    }
    if (status == 0) {
	    qm.sendAcceptDecline("Hello, #b#h0##k!\r\nYou know that the #e#bWinter Attendance Check Season 2#k#n is in progress, right? Wanna try now?");
	} else if (status == 1) {
	    qm.sendOk("All right, the whole fan thing sort of backfired and now all my plants are dead. But here's the thing. I was using BLUE fans. That's the color of cold! I figured it alllll out this time... PINK fans. So hot, so warm! And maybe electric ones because my arms are all burny now. Go get me #b30 #e#t3994855#s#k#n from #rmonsters around your level#k, and my apartment will finally be too hot to sleep in!");
	    qm.forceStartQuest();
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
}