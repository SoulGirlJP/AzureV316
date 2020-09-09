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
            if (qm.getQuestStatus(23024) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23024) == 1) {
                qm.sendNext("이게 바로 블랙윙의 보고서로군. 하하하! 역시 내 눈은 틀리지 않았어! 너라면 해낼 거라고 생각했지!");
	    }
        } else if (status == 1) {
            qm.sendNextPrev("이 임무는 다른 녀석에게 돌아갈 걸 일부러 빼와서 너에게 맡긴 거야. 그 블랙윙 녀석, 예전에 널 괴롭혔던 녀석이잖냐? 예전에 당했던 걸 복수하라고 일부러 너에게 맡겼지. 임무에 복수까지 하다니, 이런게 일석이조 아니겠어?");
        } else if (status == 2) {
            qm.sendNextPrev("하지만 아무리 나라도 솔직히 네가 이 정도로 빠르고 깔끔하게 해낼 거라고는 생각지 못했는데... 정말 대단한걸? 짧은 시간 동안 엄청나게 성장했어.");
        } else if (status == 3) {
            qm.sendYesNo("그럼 이제 고민할 필요 없겠군! 좀 이를 거라고 예상했지만 전혀 이르지 않아. 지금 널 다음 단계로 성장시키겠어. 이전까지와는 전혀 다른, 더 강력한 힘을 가진 와일드 헌터로 말이야.");
        } else if (status == 4) {
	    qm.changeJob(3310);
	    qm.gainItem(4032738,-1);
	    qm.gainItem(1142243,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 중급생> 칭호를 획득 하셨습니다.");
	    qm.sendNext("널 전직시켰어. 동시에 강력한 스킬들을 전수했지. 이제 더 이상 예전의 네가 아니야. 더 빠르고 거칠고 강한 와일드 헌터가 된 거야. 새로운 힘을 마음껏 즐기도록!");
        } else if (status == 5) {
	    qm.sendPrev("그럼 다음 수업에 보자. 그때까지 레지스탕스로서 멋지게 활약하길 기대하지.");
            qm.dispose();
        }
    }
}