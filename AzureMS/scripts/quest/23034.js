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
            if (qm.getQuestStatus(23034) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23034) == 1) {
                qm.sendNext("에너지 전송 장치를 없애고 돌아왔구나! 후후, 역시! 내 눈은 틀림없어. 너라면 해낼 거라고 믿었지. 이걸로 우리 마을의 에너지 부족 현상도 한동안은 괜찮을 거야. 넌 마을을 위해 정말 큰 공을 세웠어.");
	    }
        } else if (status == 1) {
            qm.sendYesNo("네 능력이 이쯤 되었으니 걱정하지 않고 수업을 다음 단계로 넘기겠어. 다른 사람들은 아직 위험하다고 말렸지만... 너라면 스킬에 눌리지 않고 충분히 더 강한 와일드 헌터로 거듭날 수 있을 거야!");
        } else if (status == 2) {
	    qm.changeJob(3311);
	    qm.gainItem(1142244,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 상급생> 칭호를 획득 하셨습니다.");
	    qm.sendNext("널 전직시켰어. 이제 더 이상 예전의 네가 아니야. 더 강력하고 빠르고 화려한 스킬의 세계가 펼쳐질 거야. 라이딩까지 타고 다니니 사용하기 쉽지야 않겠지만... 겁날 게 뭐가 있겠어? 그 아슬아슬한 임무도 해낸 너인데 말이야!");
        } else if (status == 2) {
	    qm.sendPrev("그럼 다음 수업에 보자. 그때까지 레지스탕스로서 멋지게 활약하길 기대하지.");
            qm.dispose();
        }
    }
}