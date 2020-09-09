/* RED Zero
    [New Years] Glowing Ghost&apos;s Gift
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendOk("#b#eEnjoy your #e#t1012367##n. Aren't I just the most generous person EVER? Ha!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/4/0#\r\n#i1012367# #t1012367# x1(10 days)");
		qm.gainItem(1012367,1);
    } else if (status == 1) {	   
	    qm.sendPrev("If you come back tomorrow with the Glowing Nose, I'll #e#bupgrade it#k#n. So come say hi tomorrow!");
		qm.forceStartQuest();
		qm.dispose();
	}
}