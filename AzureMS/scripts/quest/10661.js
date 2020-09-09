/* RED 1st impact
    Everybody In Event
	Matilda
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendNextS("Hey there, #e#b#h0##k#n.\r\nYou have officially been declared a #e#rMaple BigWig#k#n! That means you're eligible for a  #b#t2430288#, a #t2430267#, a #t2430079#,a #t2450083#, and a #t2023304##k because you're so special!\r\n\r\n\r\n#i2430288# #t2430288#\r\n\r\n#i2430267# #t2430267#\r\n\r\n#i2430079# #t2430079#\r\n\r\n#i2450083# #t2450083#\r\n\r\n#i2023304# #t2023304#\r\n\r\n Are you ready to accept your #e#bawesome BigWig items#k#n now?\r\n#e#b1 Hour 2x EXP Coupon/1 Hour 2x Drop Coupon are only available once a day#k#n",5);
        qm.dispose();
		}
  }