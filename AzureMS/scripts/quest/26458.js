/* RED 1st impact
    Everybody in event
	Matilda
    Made by Daenerys
*/
var status = -1;
var sel = 0;

function start(mode, type, selection) {
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
        qm.sendSimple("Hey, you! Thanks for logging in!\r\n\r\nHere's some #e#bstuff you might like#k#n!\r\n#b\r\n#L0# Benefit 1. Everybody in Event exclusive weapon#l\r\n#L1# Benefit 2. Everybody in Event exclusive mount#l\r\n#L2# Benefit 3. On the spot resurrection / Everybody in Event Space Rock#l\r\n#L3# Skip the explanation.#l");
    } else if (status == 1) {
        sel = selection;
	if (selection == 0) {		
	    qm.sendNext("#eTime to log in!#n\r\n\r\nWhen you log in during the Everybody In Event, you can get a #b#e#t2430288##k#n and a temporary Everybody In weapon.\r\n#i2430288:# #t2430288:#\r\n\r\n#rDouble-click#k on the box to receive a weapon for your current job and level. You can only have 1 Everybody In weapon at a time. It'll #rvanish when you log out#k.\r\n\r\nIf you lose the #t2430288#, you can get a replacement from #b#e#p9000018##n#k in any town.");
        } else if (selection == 1) {
		qm.sendNext("#eTry logging in from the Everybody in Event!#n\r\n\r\nIf you login from the Everybody in Event, you can receive the #b#e#t2430079##n#k for free.\r\n#i2430079:# #t2430079#\r\n\r\n#rDouble-click#k #t2430079# to learn the #bFloaty Balloon Mount#k skill. The coupon will be #rauto-removed when you log out#k.\r\n\r\nIf you lose the #t2430079# or if it disappears after the mount skill has expired, you can receive it again from #b#eNPC#p9000018##n#k located in each town.");
        } else if (selection == 2) {
		qm.sendNext("#eTry logging in from the Everybody in Event!#n\r\n\r\nIf you die while playing MapleStory from the Everybody In Event, you don't need to backtrack all the way from the nearest town. You can actually #e#brevive on the spot#n#k and keep enjoying the game!\r\n\r\n#rDouble-click#k the #b#e#t2430267##k#n that's given for free from the Everybody In Event to warp to the desired town, no matter the continent. #t2430267# can be used #ronce every 30 minutes#k.\r\n#i2430267# #t2430267#\r\n\r\nEven after use, the Space Rock will not disappear so you can use it repeatedly. It will be #rauto-removed at log out#k. If you lose #t2430267#, you can receive it again from #b#e NPC #p9000018##n#k in each town.");
        } else if (selection == 3) {
		qm.sendOk("Enjoy amazing Everybody In Event benefits! I hope you have a great time!");
		qm.dispose();
	   }
	    qm.dispose();
    }
}