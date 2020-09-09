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
            if (qm.getQuestStatus(23023) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23023) == 1) {
                qm.sendNext("후후... 이 임무는 일부러 너에게 맡긴 거야. 그 블랙윙 녀석, 예전에 널 괴롭혔던 녀석이잖아? 예전에는 옷자락도 건드리기 어려웠던 녀석을 이렇게 간단히 해치우니 기분 좋지 않아? 임무에 겸사 겸사 복수까지 하니 속이 시원하지?");
	    }
        } else if (status == 1) {
            qm.sendYesNo("좋아! 성장에 대한 확인은 이 정도면 충분해! 좀 이를 거라고 예상했지만 전혀 이르지 않은 것 같군. 그럼 다음 단계로 넘어가 보자! 이전까지는 상상도 한 적 없을, 더 강력한 힘을 가진 배틀 메이지로 말이야...");
        } else if (status == 2) {
	    qm.changeJob(3210);
	    qm.gainItem(4032737,-1);
	    qm.gainItem(1142243,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 중급생> 칭호를 획득 하셨습니다.");
	    qm.sendNext("널 전직시켰어. 동시에 지금까지 사용해 왔던 스킬, 그 이상의 강력한 스킬들을 전수했지. 이제 더 이상 예전의 네가 아니야. 더 강력하고 광폭한 배틀 메이지가 된 거라고. 후후, 제자가 이렇게 잘 성장하는 걸 보면 내 수업도 나쁘진 않은걸?");
        } else if (status == 3) {
	    qm.sendPrev("그럼 다음 수업에 보자. 그때까지 레지스탕스로서 멋지게 활약하길 기대하지.");
            qm.dispose();
        }
    }
}