var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
var status = 0;
var tsd = 1000;

importPackage(Packages.constants);

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
if (cm.getPlayer().getLevel() >= 200) {
		var jessica = "#fn나눔고딕 Extrabold#캐릭터의 #rMaxHP#k 와 #bMaxMP#k 를 강화하실 수 있습니다.\r\n\r\n";
		jessica += "                                      #b초월 포인트#k : #r"+cm.getTSD()+" P#k\r\n\r\n";
		jessica += "#L0##r[+ HP 5000]#k #d강화#k - #b초월 포인트#k #r"+tsd+" P#k\r\n";
		jessica += "#L1##b[+ MP 5000]#k #d강화#k - #b초월 포인트#k #r"+tsd+" P#k\r\n";
		cm.sendSimple(jessica);
} else {
cm.sendOk("#fn나눔고딕 Extrabold##rMaxHP 와 MaxMP 강화는 레벨 200 이상만 이용 가능합니다.#k",9062004);
cm.dispose();
}
	} else if (status == 1) {
		if (selection == 0) {
		if (cm.getPlayer().getStat().getMaxHp() <= 495000) {
			if (cm.getTSD() >= tsd) {
				cm.gainTSD(-tsd);
                                cm.setStat_s(5000,"HP");
				cm.showEffect(false,"phantom/back");
				cm.showEffect(false,"phantom/suu");
                                cm.playSound(false,"Field.img/rootabyss/undo");
				cm.sendOk("#fn나눔고딕 Extrabold#당신의 #rHP 5000#k 이 증가하였습니다.\r\n\r\n#d현재 총 MaxHP : "+cm.getPlayer().getStat().getMaxHp()+"#k");
                                cm.dispose();
	                } else {
				cm.sendOk("#fn나눔고딕 Extrabold##r강화를 위한 초월 포인트가 부족합니다.#k");
				cm.dispose();
		        }
		} else {
				cm.sendOk("#fn나눔고딕 Extrabold##r당신의 HP 는 이미 한계치에 근접 도달해 강화할 수 없습니다.#k");
				cm.dispose();
		}
		} else if (selection == 1) {
		if (cm.getPlayer().getStat().getMaxMp() <= 495000) {
			if (cm.getTSD() >= tsd) {
				cm.gainTSD(-tsd);
                                cm.setStat_s(5000,"MP");
				cm.showEffect(false,"phantom/back");
				cm.showEffect(false,"phantom/suu");
                                cm.playSound(false,"Field.img/rootabyss/undo");
				cm.sendOk("#fn나눔고딕 Extrabold#당신의 #bMP 5000#k 이 증가하였습니다.\r\n\r\n#d현재 총 MaxMP : "+cm.getPlayer().getStat().getMaxMp()+"#k");
                                cm.dispose();
	                } else {
				cm.sendOk("#fn나눔고딕 Extrabold##r강화를 위한 초월 포인트가 부족합니다.#k");
				cm.dispose();
		        }
		} else {
				cm.sendOk("#fn나눔고딕 Extrabold##r당신의 MP 는 이미 한계치에 근접 도달해 강화할 수 없습니다.#k");
				cm.dispose();
		}

		}
}
}
}