

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
         
	       var Lcoin = cm.itemQuantity(4031456);
               //var Lcoin2 = cm.itemQuantity(4033825);
                
	       var chat = "\r\n#L998#";
	       chat += "#b#h0##k님의#r#i4031456# 낚시 구슬 갯수 #r#e"+ Lcoin +" #n#k#k#n\r\n"

//chat += "\r\n\r\n#L998##r#i4031456#  #e#r1#k#n(개)#L4# #i2450135# [#b#z2450135#]#e#r 3개#k#n";

chat += "\r\n\r\n#L998##r#i4031456#  #e#r3#k#n(개)#L1# #i1190900##r[#e#d올스텟3000,공마 300#n#k]#e#r1개#k#n";

chat += "\r\n\r\n#L998##r#i4031456#  #e#r3#k#n(개)#L2# #i1182006##r[#e#d올스텟3000,공마 300#n#k]#e#r1개#k#n";

chat += "\r\n\r\n#L998##r#i4031456#  #e#r150#k#n(개)#L3# #i1142948##r[#e#d올스텟30000,공마 3000#n#k]#e#r1개#k#n";

	       cm.sendSimple(chat);

	    }  if (selection >= 9999999) {
		    cm.dispose();
} else if (selection == 1) {
if (cm.haveItem(4031456, 3)) {
		    if (cm.canHold(4031456)) {
		        cm.sendOk("#r낚시 구슬#k로 #r#i1190900# 를 구입 하셨습니다.");
			cm.gainItem(4031456, -3);
                        cm.gainSponserItem(1190900,'[봄온라인]',3000,300,0);
			cm.dispose();
		    } else {
		        cm.sendOk("장비칸에 빈 공간이 없습니다.");
		        cm.dispose();
		    }
		} else {
		    cm.sendOk("#r낚시 구슬#k가 부족합니다.");
		    cm.dispose();
}
} else if (selection == 2) {
if (cm.haveItem(4031456, 3)) {
		    if (cm.canHold(4031456)) {
		        cm.sendOk("#r낚시 구슬#k로 #r#i1182006# 를 구입 하셨습니다.");
			cm.gainItem(4031456, -3);
                        cm.gainSponserItem(1182006,'[봄온라인]',3000,300,0);
			cm.dispose();
		    } else {
		        cm.sendOk("장비칸에 빈 공간이 없습니다.");
		        cm.dispose();
		    }
		} else {
		    cm.sendOk("#r낚시 구슬#k가 부족합니다.");
		    cm.dispose();
}
} else if (selection == 3) {
if (cm.haveItem(4031456, 150)) {
		    if (cm.canHold(4031456)) {
		        cm.sendOk("#r낚시 구슬#k로 #r#i1142948# 를 구입 하셨습니다.");
			cm.gainItem(4031456, -150);
                        cm.gainSponserItem(1142948,'[봄온라인]',30000,3000,0);
			cm.dispose();
		    } else {
		        cm.sendOk("장비칸에 빈 공간이 없습니다.");
		        cm.dispose();
		    }
		} else {
		    cm.sendOk("#r낚시 구슬#k가 부족합니다.");
		    cm.dispose();
}
} else if (selection == 4) {
if (cm.haveItem(4031456, 1)) {
		    if (cm.canHold(4031456)) {
		        cm.sendOk("#r낚시 구슬#k로 #r#i2450135# 를 구입 하셨습니다.");
			cm.gainItem(4031456, -1);
                        cm.gainItem(2450135, 3);
			cm.dispose();
		    } else {
		        cm.sendOk("소비창에 빈 공간이 없습니다.");
		        cm.dispose();
		    }
		} else {
		    cm.sendOk("#r낚시 구슬#k가 부족합니다.");
		    cm.dispose();
}
             } else if (selection == 30001) {
		cm.dispose();
		cm.openNpc(1033221);
		return;

             } else if (selection == 30002) {
		cm.dispose();
		cm.openNpc(1033211);
		return;
             } else if (selection == 30003) {
		cm.dispose();
		cm.openNpc(1033206);
		return;
             } else if (selection == 30004) {
		cm.dispose();
		cm.openNpc(1033205);
		return;
             } else if (selection == 30009) {
		cm.dispose();
		cm.openNpc(1033111);
		return;
 } else if (selection == 6000) {
		cm.dispose();
		cm.openNpc(9072100);
		return;
             } else if (selection == 30006) {
		cm.dispose();
		cm.openNpc(1033202);
		return;
             } else if (selection == 30007) {
		cm.dispose();
		cm.openNpc(1033201);
		return;
            } else if (selection == 30008) {
		cm.dispose();
		cm.openNpc(1033200);
		return;
            } else if (selection == 10011) {
		cm.dispose();
		cm.openNpc(1033110);
		return;
  } else if (selection == 6001) {
		cm.dispose();
		cm.openNpc(1033105);
		return;
} else if (selection == 998) {
		cm.dispose();
		cm.openNpc(1540895);
		return;




		}
	}
}