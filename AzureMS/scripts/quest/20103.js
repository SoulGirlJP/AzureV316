var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("깊이 생각하세요.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("선택하시는 건가요...? ...되돌릴 수는 없습니다. 신중한 결정이 당신을 현명하게 만듭니다.");
    } else if (status == 1) {
	qm.sendNext("이제부터 당신은 윈드 브레이커입니다. 이제 당신께 능력을 나눠 드리겠습니다...");
        qm.gainItem(1452051,1);
        qm.gainItem(1142066,1);
	qm.changeJob(1300);
	qm.forceCompleteQuest();
	qm.showinfoMessage("<수련기사의 훈장> 칭호를 획득 하셨습니다.");
    } else if (status == 2) {
	qm.sendNextPrev("이제 당신은 윈드 브레이커입니다. 강해지고 싶다면 스탯창(S키)에서 적절한 스탯을 올리도록 하세요. #b자동배분#k의 도움을 받는다면 보다 간단히 올릴 수 있을 겁니다.");
    } else if (status == 3) {
	qm.sendNextPrev("윈드 브레이커로서 필요한 물건이 많을 테니 당신의 장비, 기타 아이템의 보관함 갯수를 늘려 드렸습니다.");
    } else if (status == 4) {
	qm.sendNextPrev("당신께 약간의 #bSP#k를 지급했습니다. #bSkill 메뉴#k를 열어 스킬을 배우십시오. 간혹 다른 스킬을 익히지 않고는 배울 수 없는 스킬도 있으니 잘 생각해 보시고 스킬을 올리십시오.");
    } else if (status == 5) {
	qm.sendNextPrev("노블레스 때와 달리 윈드 브레이커가 된 이상 죽게 되면 그동안 쌓았던 경험치의 일부를 잃을 수 있습니다. 부디 유념하시길.");
    } else if (status == 6) {
        qm.sendNextPrev("그럼... 시그너스 기사단의 기사로서 누구보다 충실한 모습을 보여주시길...");
	qm.safeDispose();
    }
}