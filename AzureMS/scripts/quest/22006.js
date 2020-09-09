var status = -1;

function end(mode, type, selection) {
	status++;
	if (mode != 1) {
	    if(type == 1 && mode == 0)
		    status -= 2;
		else{
		    qm.dispose();
			return;
		}
	}
	if (status == 0) {
		qm.sendNext("에반, 돌아왔니? 아, 다 먹은 빈 도시락통도 가져왔구나 착하기도 하지. 응? 알을 어떻게 하면 잘 키우냐고?.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i2022621# 20 #t2022621#s \r\n#i2022622# 20 #t2022622# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 270 exp");
	} else if (status == 1){
		qm.forceCompleteQuest();
		qm.gainItem(4032450, -1);
		qm.gainItem(2022621, 20);
		qm.gainItem(2022622, 20);
		qm.gainExp(270);
		qm.sendOk("알을 부화시키는 건 여러 방법이 있지만 부화기를 쓰는 게 제일 간단하단다. 그러고 보니 #b유타#k가 부화기를 하나 가지고 있었던 것 같은데, 유타에게 달라고 하면 되지 않을까?");
		qm.dispose();
	}
}