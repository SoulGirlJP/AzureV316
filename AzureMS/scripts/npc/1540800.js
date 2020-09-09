var status = 0;
var sel = 0;

var 음치 = Array (2431289,2450067,1662000,1662001,1672000,1662002,1662003,1672022);
var 음치갯수 = Array (35,70,30,30,30,50,50,50);
var 몸치 = Array (1662004,1662005,1662006,1662009,1662010,1662011,1662012,1662013,1662014,1662015,1662016,1662019,1662020,1672027,1672040);
var 몸치갯수 = Array (30,30,30,30,30,30,30,30,30,30,30,30,30,30,30);
var 박치 = Array (2022918,2023072,2023132);
var 박치갯수 = Array(50,100,200);

var 음치가격 = 15000; //메소로 교환할때의 가격
var 몸치가격 = 16000; //메소로 교환할때의 가격
var 박치가격 = 17000; //메소로 교환할때의 가격

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
		var jessica = "낚시를 하는 동안은 세월과 나와 오로지 바다만 있을뿐이지..\r\n\r\n#b";
		jessica += "#L0#낚시가 뭔가요?\r\n";
		jessica += "#L1#낚시 용품 구입\r\n";
		jessica += "#L2#물고기 교환";
		cm.sendSimple(jessica);

        } else if (status == 1) {
	if (selection == 0) {
		cm.sendPrev("#b낚시#k가 뭐냐고?\r\n\r\n#b미끼#k를 이용해서 #b물고기#k를 잡는것이지.");
		cm.dispose();

	} else if (selection == 1) {
		sel = 1;
		var jessica2 = "한번 골라보게\r\n\r\n#b";
		jessica2 += "#L0##i3010432# 낚시 의자 (1.500.000)\r\n";
		jessica2 += "#L1##i2430996# 미끼 구입 (1.000.000)";
		cm.sendSimple(jessica2);

	} else if (selection == 2) {
			var chat = "한번 골라보게\r\n\r\n";
       			chat += "#L18181818##i4001189# #b물고기를 메소로 교환#l\r\n#e";
			chat += "\r\n\r\n#k#e<음치>#n#b\r\n";
			{
				for (var i = 0; i < 음치.length; i ++) {
					chat += "#L" + i + "# #i" + 음치[i] + "# #z" + 음치[i] + "# : 음치(" + 음치갯수[i] + ")#l\r\n";
				}
			}
			chat += "\r\n\r\n#k#e<몸치>#n#b\r\n";
			{
				for (var i = 0; i < 몸치.length; i ++) {
					chat += "#L" + ((i + 1) * 1000) + "# #i" + 몸치[i] + "# #z" + 몸치[i] + "# : 몸치(" + 몸치갯수[i] + ")#l\r\n";
				}
			}
			chat += "\r\n\r\n#k#e<박치>#n#b\r\n";
			{
				for (var i = 0; i < 박치.length; i ++) {
					chat += "#L" + ((i + 1) * 50000) + "# #i" + 박치[i] + "# #z" + 박치[i] + "# : 박치(" + 박치갯수[i] + ")#l\r\n";
				}
			}
			cm.sendSimple(chat);
		}
        } else if (status == 2) {
		if (sel == 1) {
			if (selection == 0) {
				if (cm.getMeso() >= 1000000) {
					cm.gainItem(3010432, 1);
					cm.gainMeso(-1500000);
					cm.sendOk("다음에도 꼭! 이용해주길 바라네.");
					cm.dispose();
				} else {
					cm.sendOk("150만 메소가 있는게 확실한가? 음..다음에 다시 찾아와주게나" );
					cm.dispose();
				}
     			} else if (selection == 1) {
              	 		 if (cm.getMeso() >= 1000000) {
               				 cm.gainItem(2430996, 100);
               				 cm.gainMeso(-1000000);
               				 cm.sendOk("다음에도 꼭! 이용해주길 바라네.");
               				 cm.dispose();
        			} else {
             	 	     	   cm.sendOk("100만 메소가 있는게 확실한가? 음..다음에 다시 찾아와주게나" );
				}
			}
		} else {
			if (selection == 18181818) {
				var chat1 = "메소로 교환할 물고기를 선택해 주게나.\r\n\r\n#b";
				chat1 += "#L4001187##i4001187##t4001187# 마리당 " + 음치가격 + " 메소\r\n";
				chat1 += "#L4001188##i4001188##t4001188# 마리당 " + 몸치가격 + " 메소\r\n";
				chat1 += "#L4001189##i4001189##t4001189# 마리당 " + 박치가격 + " 메소\r\n";
				cm.sendSimple(chat1);
			} else if (selection >= 50000) {
				sel = parseInt(((selection - 1) / 50000));
				if (cm.haveItem(4001189,박치갯수[sel])) {
					if (cm.canHold(박치[sel])) {
						cm.gainItem(4001189,-박치갯수[sel]);
						cm.gainItem(박치[sel],1);
						cm.sendOk("다음에도 또 찾아와 주게나");
						cm.dispose();
					} else {
						cm.sendOk("아이템을 받을 공간이 부족하구려");
						cm.dispose();
					}
				} else {
					cm.sendOk("박치를 확실히 가지고 있는겐가?");
					cm.dispose();
				}
			} else if (selection >= 1000) {
				sel = parseInt(((selection - 1) / 1000));
				if (cm.haveItem(4001188,몸치갯수[sel])) {
					if (cm.canHold(몸치[sel])) {
						cm.gainItem(4001188,-몸치갯수[sel]);
						cm.gainItem(몸치[sel],1);
						cm.sendOk("다음에도 또 찾아와 주게나");
						cm.dispose();
					} else {
						cm.sendOk("아이템을 받을 공간이 부족하구려");
						cm.dispose();
					}
				} else {
					cm.sendOk("몸치를 확실히 가지고 있는겐가?");
					cm.dispose();
				}
			} else {
				sel = selection;
				if (cm.haveItem(4001187,음치갯수[sel])) {
					if (cm.canHold(음치[sel])) {
						cm.gainItem(4001187,-음치갯수[sel]);
						cm.gainItem(음치[sel],1);
						cm.sendOk("다음에도 또 찾아와 주게나");
						cm.dispose();
					} else {
						cm.sendOk("아이템을 받을 공간이 부족하구려");
						cm.dispose();
					}
				} else {
					cm.sendOk("음치를 확실히 가지고 있는겐가?");
					cm.dispose();
				}
			}
		}
	} else if (status == 3) {
		sel = selection;
		cm.sendGetNumber("교환할 갯수를 입력해 주게나.", 0, 1, 100);
	} else if (status == 4) {
		if (cm.haveItem(sel,selection)) {
			cm.gainItem(sel,-selection);
			cm.gainMeso((sel == 4001187 ? 음치가격 : sel == 4001188 ? 몸치가격 : 박치가격) * selection);
			cm.sendOk("다음에도 또 찾아와 주게나");
			cm.dispose();
		} else {
			cm.sendOk("#t" + sel + "#를 확실히 가지고 있는겐가?");
			cm.dispose();
		}
	}
    }
}