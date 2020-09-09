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
            if (qm.getQuestStatus(23035) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23035) == 1) {
                qm.sendNext("에너지 전송 장치를 파괴하는데 성공하셨군요! 당신에 대한 신뢰를 이렇게 보답해 주시다니... 정말 감사합니다. 이걸로 우리 마을의 에너지 부족 현상도 어느 정도 완화될 것입니다. 당신이야 말로 이 마을에 가장 큰 공을 세운 겁니다.");
	    }
        } else if (status == 1) {
	    qm.sendYesNo("당신의 능력을 이 눈으로 확인했으니, 이제 제 능력도 보여드려야 할 차례군요. 당신계 새로운 스킬을 전수하도록 하겠습니다. 스킬의 숙련도가 높지 않으면 사용할 수 없어 나중에나 알려드리려 했지만... 지금의 당신으로도 충분하군요.");
        } else if (status == 2) {
	    qm.changeJob(3511);
	    qm.gainItem(1142244,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 상급생> 칭호를 획득 하셨습니다.");
	    qm.sendNext("당신을 전직시켰습니다. 이제 더 이상 예전의 당신이 아닙니다. 더 다양하고 복잡한, 그만큼 강력한 스킬이 당신을 구성할 겁니다. 어려울 것 같다고요? 걱정 마십시오. 당신이라면 자유자재로 다룰 수 있을 겁니다. 그 어려운 임무도 해냈는 걸요.");
            qm.dispose();
        }
    }
}