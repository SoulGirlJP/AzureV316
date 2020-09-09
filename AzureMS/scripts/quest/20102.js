var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("다, 다시 생각하시는 건가요오....");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("서, 선택하신 건가요? 정말요? 되돌릴 수는 없어요. 신중하게 생각하세요... 정말 플레임 위자드의 길을 가시겠어요?");
    } else if (status == 1) {
	qm.sendNext("이제부터 당신은 플레임 위자드세요. 아, 정말 기뻐요! 동료가 생기다니... 아참, 이럴 게 아니라 당신께 능력을 좀 나눠드릴게요.");
        qm.gainItem(1372043,1);
        qm.gainItem(1142066,1);
	qm.changeJob(1200);
	qm.forceCompleteQuest();
	qm.showinfoMessage("<수련기사의 훈장> 칭호를 획득 하셨습니다.");
    } else if (status == 2) {
	qm.sendNextPrev("이제 당신은 플레임 위자드세요. 이제 더 강해지고 싶다면 스탯창(S키)에서 적절한 스탯을 올리도록 하세요. 어려우시면 #b자동분배#k로 하셔도 좋아요. 마법사에 익숙치 않은 분들께 무척 유용하답니다.");
    } else if (status == 3) {
	qm.sendNextPrev("아참, 또... 당신께 약간의 #bSP#k를 지급했으니 #bSkill 메뉴#k를 열어 스킬을 배우도록 하세요. 한 개밖에 못올리겠지만... 열심히 노력하시면 금방 모든 스킬을 익힐 수 있으실 거예요. 아, 처음부터 전부 올릴 수 있는 건 아니고 다른 스킬을 익히지 않고는 배울 수 없는 스킬도 있어요.");
    } else if (status == 4) {
	qm.sendNextPrev("노블레스 때와 달리 플레임 위자드가 된 이상 죽게 되면 그동안 쌓았던 경험치의 일부를 잃을 수도 있어요. 부디 몸 조심하시고...");
    } else if (status == 5) {
	qm.sendNextPrev("그럼... 시그너스 기사단의 기사로서 함께 열심히 노력해 나가요!");
	qm.safeDispose();
    }
}