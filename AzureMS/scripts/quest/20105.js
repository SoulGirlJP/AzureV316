var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("앗, 안 할 거야? 안 할 거야? 아... 아쉽다.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("정말 선택한 거야? 혹시나 해서 말하는데, 되돌릴 수는 없어? 신중하게 결정해. 정말 스트라이커의 길을 가겠어?");
    } else if (status == 1) {
	qm.sendNext("이제부터 넌 스트라이커야! 이야호! 번개의 기사단 한 명 추가! 아참, 이럴 게 아니라 너한테도 능력을 좀 나눠줄게.");
        qm.gainItem(1482014,1);
        qm.gainItem(1142066,1);
	qm.changeJob(1500);
	qm.forceCompleteQuest();
	qm.showinfoMessage("<수련기사의 훈장> 칭호를 획득 하셨습니다.");
    } else if (status == 2) {
	qm.sendNextPrev("이제 넌 스트라이커야. 강해지고 싶으면 스탯창(S키)에서 적절한 스탯을 올리도록 해. 어려우면 친절한 #b자동배분#k도 있단 말씀☆");
    } else if (status == 3) {
	qm.sendNextPrev("그리고! 스트라이커로서 필요한 게 많을 테니 네 장비, 기타 아이템의 보관함 갯수를 늘려줬어! 잘 했지?");
    } else if (status == 4) {
	qm.sendNextPrev("아참, 또... 너한테 약간의 #bSP#k를 지급했으니 #bSkill 메뉴#k를 열어 스킬도 배워봐. 당장은 한 개밖에 못 올리지만 곧 모든 스킬을 다 배울 수 있을 거야. 아참참, 처음부터 전부 올릴 수 있는 건 아니고 다른 스킬을 익히지 않고는 배울 수 없는 스킬도 있어.");
    } else if (status == 5) {
	qm.sendNextPrev("노블레스 때와 스트라이커가 된 이상 죽게 되면 그동안 쌓았던 경험치의 일부를 잃을 수도 있어. 사냥이 재밌다고 정신 빼놓지는 말라고. 알았지?");
    } else if (status == 6) {
        qm.sendNextPrev("그럼... 시그너스 기사단의 기사로서 신나는 모험을 함께 하자고!");
	qm.safeDispose();
    }
}