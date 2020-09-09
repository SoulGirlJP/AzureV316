var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("다시 생각해 보라고. 정말 이 길을 가도 괜찮을지.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("정말 선택한 건가? ...후회해도 되돌릴 방법은 없어. 신중하게 결정하라고. 나이트워커의 길을 가겠어?");
    } else if (status == 1) {
	qm.sendNext("이제부터 넌 나이트워커다. 동료가 된 기념으로 네게 능력을 좀 나눠주지.");
        qm.gainItem(1472061,1);
        qm.gainItem(1142066,1);
	qm.changeJob(1400);
	qm.forceCompleteQuest();
	qm.showinfoMessage("<수련기사의 훈장> 칭호를 획득 하셨습니다.");
    } else if (status == 2) {
	qm.sendNextPrev("이제 넌 나이트워커다. 강해지고 싶다면 스탯창(S키)에서 적절한 스탯을 올리도록. 어려우면 #b자동배분#k으로 해도 되고... 뭐, 자동배분을 못 믿겠으면 직접 올리고.");
    } else if (status == 3) {
	qm.sendNextPrev("필요할 것 같아서 장비, 기타 아이템의 보관함 갯수를 좀 늘렸어. 잘 써먹어 봐.");
    } else if (status == 4) {
	qm.sendNextPrev("그리고, 네게 약간의 #bSP#k를 지급했으니 #bSkill 메뉴#k를 열어 스킬을 배우도록 해. 한 개밖에 못 올리겠지만... 뭐. 그거라도 올려 놔야 사냥하기 쉬울 테니. 아, 다른 스킬을 익혀야만 올릴 수 있는 스킬도 있으니 그건 건들지 마.");
    } else if (status == 5) {
	qm.sendNextPrev("노블레스 때와 달리 나이트워커가 된 이상 죽게 되면 그동안 쌓았던 경험치의 일부를 잃을 수도 있어. 자기 몸은 자기가 알아서 챙기라고.");
    } else if (status == 6) {
        qm.sendNextPrev("그럼... 시그너스 기사단의 기사로서 누구보다 깊은 어둠까지 확인해 보자고.");
	qm.safeDispose();
    }
}