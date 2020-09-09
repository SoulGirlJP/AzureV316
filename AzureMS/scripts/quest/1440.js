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
            if (qm.getQuestStatus(1440) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(1440) == 1) {
                qm.sendNext("시험을 무사히 통과했군요. #b다른 차원의 헬레나#k님을 만나본 소감은 어떤가요. 놀랐나요? 이것이 바로 #b성스러운 돌#k이 가진 놀라움이지요. 다른 차원에 분신을 소환해 싸울 수 있게 하는 힘. 헬레나님이 당신을 위해 준비한 것이랍니다.");
	    }
        } else if (status == 1) {
            qm.sendYesNo("진정한 궁수이신 헬레나님과의 전투는 당신을 진정한 궁수로의 길로 이끌죠... 당신의 성장이 느껴지시나요? 이제 남은 것은 전직 뿐이랍니다. 더 강력한 궁수, 저격수가 될 준비가 되었나요?");
        } else if (status == 2) {
	    qm.changeJob(321);
	    qm.gainItem(4031059,-1);
            qm.forceCompleteQuest();
	    qm.gainItem(1142109,1);
	    qm.showinfoMessage("<베테랑 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제부터 당신은 #b저격수#k입니다. #b석궁#k의 마스터, 진정한 저격수로서 당신이 가진 힘을 마음껏 펼쳐보세요.");
            qm.dispose();
        }
    }
}