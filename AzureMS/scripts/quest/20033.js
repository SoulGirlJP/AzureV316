var status = -1;

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
            if (qm.getQuestStatus(20033) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(20033) == 1) {
		qm.sendSimple("계란은 가져온거야? 깨지지는 않았겠지? 잠깐만 뭐야 이 꼴은 무슨 일이 있었어?#b\r\n#L0#저기...울프가 갑자기 공격을 해서...그리고 저...울프가 도망쳐 버렸어요.");
	    }
	} else if (status == 1) {
	    qm.gainExp(135);
	    qm.gainExp(98);
	    qm.gainItem(4033196,-10);
	    qm.forceCompleteQuest();
	    qm.sendNext("뭐라고! 울프가 도망을 쳐? 이런 변변치 못한 녀석 같으니라구!! 오늘 밥은 없을 줄 알아!! 만약에 울프를 찾지 못하면 네 녀석도 나가버려!");
	} else if (status == 2) {
	    qm.warp(913070004,0);
	    qm.dispose();
	}
    }
}