var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("불독에게 밥을 주고 온 거야? 그럼 에반 너도 아침밥 먹어. 오늘 아침밥은 수제 샌드위치야. 내가 가지고 나왔어. 히히. 사실은 불독에게 먹이주기를 안 도와주면 아침밥도 안 주려고 했는데.");
	} else if (status == 1) {
            qm.askAcceptDecline("자, #b샌드위치#k를 줄 테니 #b다 먹으면 엄마에게 가봐#k. 뭔가 너한테 할 말이 있으시다는데?");
        } else if (status == 2) {
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    qm.gainItem(1003028,1);
	    qm.gainItem(2022621,5);
	    qm.gainItem(2022622,5);
	    qm.gainExp(60);
	    qm.forceCompleteQuest();
            qm.sendNext("아침밥은 잘 먹었니, 에반? 그럼 엄마 심부름 하만 해주지 않으련?\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i1003028# 평범한 밀짚모자 1개\r\n#i2022621# 맛있는 우유 5개\r\n#i2022622# 맛있는 주스 5개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 60 exp");
            qm.dispose();
        }
    }
}