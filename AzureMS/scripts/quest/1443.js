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
            if (qm.getQuestStatus(1443) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(1443) == 1) {
                qm.sendYesNo("진정한 도적인 다크로드와의 전투는 자네를 진정한 도적으로 만들었다... 본인은 잘 못 느끼겠지만 말이야. 이제 남은 것은 전직 뿐. 더 강력한 도적, 시프 마스터가 될 준비가 되었나?");
	    }
        } else if (status == 1) {
	    qm.changeJob(421);
	    qm.gainItem(4031059,-1);
            qm.forceCompleteQuest();
	    qm.gainItem(1142109,1);
	    qm.showinfoMessage("<베테랑 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("이제부터 자네는 #b시프 마스터#k다. #b단도#k의 마스터, 진정한 시프 마스터로서 자네가 가진 힘을 마음껏 사용하게.");
            qm.dispose();
        }
    }
}