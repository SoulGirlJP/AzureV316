/*
Á¦ÀÛÀÚ : ÆþÆþ(pongpong___@naver.com / unfix_@naver.com)
*/
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	if (cm.getPlayer().getMapId() == 931000312) {
	cm.sendOk("°¨¿Á¸Ê¿¡¼± »ç¿ëÇÒ¼ö ¾ø½À´Ï´Ù");
	cm.dispose()
	} else {
            var chat = "Please select a map to move.";
            chat += "\r\n#L99#Henesys";
            chat += "\r\n#L1#Lease Port";
            chat += "\r\n#L2#Ellinia";
            chat += "\r\n#L3#Kerning City";
            chat += "\r\n#L4#Nautilus";
            chat += "\r\n#L5#Perion";
            chat += "\r\n#L6#Sleepy Wood";
            chat += "\r\n#L7#Six Ways";
            chat += "\r\n#L8#Orbis";
            chat += "\r\n#L9#El Nath";
            chat += "\r\n#L10#Ludibrium";
            chat += "\r\n#L11#Leafre";
            chat += "\r\n#L12#Rien";
            chat += "\r\n#L13#Ariant";
            chat += "\r\n#L14#Magatia";
            chat += "\r\n#L15#Aquarium";
            chat += "\r\n#L16#Mulung";
            chat += "\r\n#L17#Baekcho Village";
            chat += "\r\n#L18#Edelstein";
            chat += "\r\n#L19#Ereve";
            chat += "\r\n#L20#Door to the future";
            chat += "\r\n#L21#Cave of Life";
            chat += "\r\n#L22#Source of the clock tower";
            chat += "\r\n#L23#Pianus Cave";
            chat += "\r\n#L24#Entrance to Altar of Zakum";
            chat += "\r\n#L25#Altar entrance to Chaos Zakum";
            cm.sendSimple(chat);
}
            } else if (selection == 0) {
		cm.gainItem(2430238, -1);
                cm.warp(100000000);
                cm.dispose();
            } else if (selection == 99) {
		cm.gainItem(2430238, -1);
                cm.warp(100000000);
                cm.dispose();
            } else if (selection == 1) {
		cm.gainItem(2430238, -1);
                cm.warp(104000000);
                cm.dispose();
            } else if (selection == 2) {
		cm.gainItem(2430238, -1);
                cm.warp(101000000);
                cm.dispose();
            } else if (selection == 3) {
		cm.gainItem(2430238, -1);
                cm.warp(103000000);
                cm.dispose();
            } else if (selection == 4) {
		cm.gainItem(2430238, -1);
                cm.warp(120000000);
                cm.dispose();
            } else if (selection == 5) {
		cm.gainItem(2430238, -1);
                cm.warp(102000000);
                cm.dispose();
            } else if (selection == 6) {
		cm.gainItem(2430238, -1);
                cm.warp(105000000);
                cm.dispose();
            } else if (selection == 7) {
		cm.gainItem(2430238, -1);
                cm.warp(104020000);
                cm.dispose();
            } else if (selection == 8) {
		cm.gainItem(2430238, -1);
                cm.warp(200000000);
                cm.dispose();
            } else if (selection == 9) {
		cm.gainItem(2430238, -1);
                cm.warp(211000000);
                cm.dispose();
            } else if (selection == 10) {
		cm.gainItem(2430238, -1);
                cm.warp(220000000);
                cm.dispose();
            } else if (selection == 11) {
		cm.gainItem(2430238, -1);
                cm.warp(240000000);
                cm.dispose();
            } else if (selection == 12) {
		cm.gainItem(2430238, -1);
                cm.warp(140000000);
                cm.dispose();
            } else if (selection == 13) {
		cm.gainItem(2430238, -1);
                cm.warp(260000000);
                cm.dispose();
            } else if (selection == 14) {
		cm.gainItem(2430238, -1);
                cm.warp(261000000);
                cm.dispose();
            } else if (selection == 15) {
		cm.gainItem(2430238, -1);
                cm.warp(230000000);
                cm.dispose();
            } else if (selection == 16) {
		cm.gainItem(2430238, -1);
                cm.warp(250000000);
                cm.dispose();
            } else if (selection == 17) {
		cm.gainItem(2430238, -1);
                cm.warp(251000000);
                cm.dispose();
            } else if (selection == 18) {
		cm.gainItem(2430238, -1);
                cm.warp(310000000);
                cm.dispose();
            } else if (selection == 19) {
		cm.gainItem(2430238, -1);
                cm.warp(130000000);
                cm.dispose();
            } else if (selection == 20) {
                if (cm.getPlayer().getLevel() >= 160) {
		cm.gainItem(2430238, -1);
                cm.warp(271000000);
                cm.dispose();
		 }else {
		cm.sendOk("I'm sorry but the future door can only be moved above level 160");
		cm.dispose();
}
	    } else if (selection == 21) {
			if (cm.haveItem(4000267,1) && cm.haveItem(4001078,1) && cm.haveItem(4001076,1) && cm.haveItem(2430457,1) && cm.haveItem(4001075,1) && cm.haveItem(4001077,10) && cm.getLevel() <120) {
            	cm.gainItem(2430457, -1);
	    	cm.gainItem(4001075, -1);
	    	cm.gainItem(4001077, -10);
	    	cm.gainItem(4001076, -1);
	    	cm.gainItem(4001078, -1);
	    	cm.gainItem(4000267, -1);
	    	cm.gainItem(2430238, -1);
		cm.warp(240060200);
		cm.dispose();
		    } else {
		        cm.sendOk("You must have 6 Horntails and Level 120 to move.");
		        cm.dispose();
}
	    } else if (selection == 22) {
		cm.gainItem(2430238, -1);
		cm.warp(220080001);
		cm.dispose();
	    } else if (selection == 23) {
		cm.gainItem(2430238, -1);
		cm.warp(230040420);
		cm.dispose();
	    } else if (selection == 24) {
		if (cm.getPlayer().getLevel() >120) {
		cm.gainItem(2430238, -1);
		cm.warp(211042400);
		cm.dispose();
		} else {
		cm.sendOkS("I can't go 120 levels yet",2);
		cm.dispose();
}
	    } else if (selection == 25) {
		if (cm.getPlayer().getLevel() >120) {
		cm.gainItem(2430238, -1);
		cm.warp(211042401);
		cm.dispose();
		} else {
		cm.sendOkS("I can't go 120 levels yet",2);
		cm.dispose();
}
		}
}
	}
