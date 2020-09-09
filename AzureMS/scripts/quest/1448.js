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
            if (qm.getQuestStatus(1448) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(1448) == 1) {
		qm.sendNext("시험을 무사히 통과했군... 제법인데? 하지만 그곳에서 #b카이린#k님을 뵙고 놀라긴 했지? 분신에 불과한 #b다른 차원의 카이린#k님이지만 그렇다고 해도 제법 강력할 텐데... 카이린님이 자네를 부탁한다는 말을 괜히 하신 게 아니로군.");
	    }
        } else if (status == 1) {
            qm.askAcceptDecline("진정한 해적인 카이린님과의 전투는 자네를 진정한 해적으로 만들었다. 느껴지는가? 이제 남은 것은 전직 뿐. 더 강력한 해적, 캐논 블래스터가 될 준비가 되었나?");
        } else if (status == 2) {
	    qm.changeJob(531);
	    qm.forceCompleteQuest();
	    qm.gainItem(4031059,-1); 
	    qm.gainItem(1142109,1);
	    qm.showinfoMessage("<베테랑 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제부터 자네는 #b캐논 블래스터#k이다. #b캐논#k의 마스터, 진정한 캐논 블래스터로서  자네가 가진 힘을 마음껏 사용하게.");
	    qm.dispose();
	}
    }
}