
var status = -1;

function start() {
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
	var text = "#b#e<Lost Crayons>#k#n\r\n\r\n";
	text += "I have to draw a rainbow picture but I can't draw because I don't have crayons\r\n";
	text += "#e#dCan you get me some crayons? Sniff..#k#n\r\n\r\n";
	text += "#g * Crayons drop in all fields.#k\r\n\r\n";
	text += "#b#e[Quest item]#k#n\r\n";
	text += "#i4001326# #b#z4001326##k #r(#c4001326# / 1)#k\r\n";
	text += "#i4001327# #b#z4001327##k #r(#c4001327# / 1)#k\r\n";
	text += "#i4001328# #b#z4001328##k #r(#c4001328# / 1)#k\r\n";
	text += "#i4001329# #b#z4001329##k #r(#c4001329# / 1)#k\r\n";
	text += "#i4001330# #b#z4001330##k #r(#c4001330# / 1)#k\r\n";
	text += "#i4001331# #b#z4001331##k #r(#c4001331# / 1)#k\r\n";
	text += "#i4001332# #b#z4001332##k #r(#c4001332# / 1)#k\r\n\r\n";
	text += "#r#e[Quest reward]#k#n\r\n";
	text += "#i2433019# #b#z2433019##k\r\n";
	if (cm.haveItem(4001326) && cm.haveItem(4001327) && cm.haveItem(4001328) && cm.haveItem(4001329) && cm.haveItem(4001330) && cm.haveItem(4001331) && cm.haveItem(4001332)) {
	text += "\r\n#L0##bI've collected all seven colored crayons#k\r\n";
	} else {
	text += "\r\n\r\n#e#rThere are some items in the crayons you are missing.#k#n";
	}
	cm.sendSimple(text);
    } else if (status == 1) {
	if (selection == 0) {
	  if (cm.haveItem(4001326) && cm.haveItem(4001327) && cm.haveItem(4001328) && cm.haveItem(4001329) && cm.haveItem(4001330) && cm.haveItem(4001331) && cm.haveItem(4001332)) {
	    cm.gainItem(4001326,-1);
	    cm.gainItem(4001327,-1);
	    cm.gainItem(4001328,-1);
	    cm.gainItem(4001329,-1);
	    cm.gainItem(4001330,-1);
	    cm.gainItem(4001331,-1);
	    cm.gainItem(4001332,-1);
	    cm.gainItem(2433019,1);
	    cm.sendOk("Thank you! Thanks to you, I can draw a rainbow picture!\r\nIn return #i2433019# #b#z2433019##k I'll give you! I got you a picture of my rainbow~!\r\n\r\nOh and do I have to draw a rainbow picture anytime #bBring me crayons every time!#k");
	    cm.dispose();
	  } else {
	    cm.sendOk("I don't have crayons?");
	    cm.dispose();
	  }
	}
    }
}