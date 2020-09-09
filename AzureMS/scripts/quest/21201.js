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
            if (qm.getQuestStatus(21201) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(21202) == 2) {
		qm.sendNext("검은 마법사를 물리치고 유명한 무기로 만들어 주겠다고 하더니 결국 저주에 걸려서 수백 년이나 날 내팽겨쳐 놓고... 뭐? 이제 와서 기억이 안 난다고? 그런 말로 넘어갈 수 있을 줄 알았니? 날 달라고 사정할 때는 언제고...");
	    }
        } else if (status == 1) {
            qm.sendNextPrevS("대장옹에게 능력을 증명할 테니 만들어만 달라고 했었지.",3);
        } else if (status == 2) {
            qm.sendNextPrev("맞아! 그렇게 애걸복걸해서 날 얻었으면 고이 모셔야지. 이만큼 훌륭한 무기가 세상에 넘쳐나는 줄 알아? 검은 마법사와도 싸울 수 있는 최강의 거대한 폴암이 바로 나라고. 그런데 얼음에 갇혀서 몇백년이나 쉬게 만들다니...");
        } else if (status == 3) {
            qm.sendNextPrevS("애걸복걸하지는 않았는데.",3);
        } else if (status == 4) {
            qm.sendNextPrev("뭐? 애걸복걸하지 않았다니? 너 완전 울고불고 난리를 치며 제발 건네 달라고 무릎까지 꿇고 사정사정했다고 대장옹이 그랬는데 아니긴 뭐가 아니야... 응?! 아란! 너 설마... 기억이 돌아온 거야!?");
        } else if (status == 5) {
            qm.sendNextPrevS("아주 약간이지만...",3);
        } else if (status == 6) {
            qm.sendNextPrev("...정말 아란이구나... 흐, 흠! 흠흠! 아니, 난 감동 같은 거 안했어!... 검은 마법사한테 당해서 능력도 잃고 이제는 날 들고 다닐 힘도 없긴 하지만... 그래도 날 기억해내다니 근성 만큼은 내 주인답다고 생각한 것 뿐이야!");
        } else if (status == 7) {
            qm.askAcceptDecline("기억이 없다 해도 내 주인. 극한까지 단련했던 그 몸은 아직 스킬을 기억하고 있을 거야. 얼음 속에서 다 사라진 것 같지만 난 알 수 있지. 좋아, 네 능력을 깨워 주도록 하지!");
        } else if (status == 8) {
	    qm.forceCompleteQuest();
	    qm.getPlayer().changeJob(2110);
	    qm.gainItem(1142130,1);
	    qm.showinfoMessage("<기억속의 아란> 칭호를 획득 하셨습니다.");
	    qm.sendNext("아직 네 레벨이 예전 같지 않아서 모든 능력을 다 깨워줄 수는 없지만, 약간이라도 깨운다면 그걸 기반으로 더 빨리 레벨을 올릴 수 있을 거야! 어서 빨리 예전의 능력을 되찾아줘.");
	    qm.dispose();
	}
    }
}