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
            if (qm.getQuestStatus(1437) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(1437) == 1) {
                qm.sendNext("시험을 무사히 통과했군... 후후, #b다른 차원의 하인즈#k님은 어땠나... 훗, 이것이 바로 #b성스러운 돌#k이 가진 힘이지. 다른 차원에 분신을 소환해 싸울 수 있게 하는 힘. 하인즈님께 감사하라고. 자네를 위해 이런 준비를 해주신 거니...");
	    }
        } else if (status == 1) {
            qm.sendYesNo("진정한 마법사인  하인즈님과의 전투로 자네 또한 진정한 마법사의 자격을 얻었군. 이제 남은 것은 전직 뿐. 더 강력한 마법사, 프리스트가 되겠는가?");
        } else if (status == 2) {
	    qm.changeJob(231);
	    qm.gainItem(4031059,-1);
            qm.forceCompleteQuest();
	    qm.gainItem(1142109,1);
	    qm.showinfoMessage("<베테랑 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제 자네는 #b프리스트#k라네. #b치유와 신성#k의 마스터, 진정한 메이지로서 자네가 가진 힘을 마음껏 펼쳐보게.");
            qm.dispose();
        }
    }
}