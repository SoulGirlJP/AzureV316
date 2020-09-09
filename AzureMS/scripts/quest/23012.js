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
            if (qm.getQuestStatus(23012) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23012) == 1) {
                qm.sendYesNo("기세 좋게 수락한 건 고마운데... 정말 깊이 생각한 거 맞나? 강하지만 그만큼 어려운 게 와일드 헌터라고. 라이딩을 조종하며 동시에 공격 하는 건 꽤나 높은 난이도의 컨트롤을 요하는데 네가 할 수 있겠어? 진지하게 고민한 후 대답해.");
	    }
        } else if (status == 1) {
	    qm.changeJob(3300);
	    qm.gainItem(1462093,1);
	    qm.gainItem(1142242,1);
	    qm.teachSkill(30001061,1,1);
            qm.forceCompleteQuest();
	    qm.showinfoMessage("<특별수업 신입생> 칭호를 획득 하셨습니다.");
            qm.sendNext("하하하! 좋아! 정식 레지스탕스가 된 걸 환영해. 지금부터 너는 와일드 헌터다. 라이딩을 타고 질풍보다 빨리 움직이며 적을 해치우자고.");
        } else if (status == 2) {
	    qm.sendNextPrev("와일드헌터라면 라이딩이 필수지. 와일드헌터가 되면서 너에겐 포획이라는 특별한 스킬이 생겼을꺼야. 그 스킬로 재규어를 길들여서 타고 다닐수 있어.");
        } else if (status == 3) {
	    qm.sendNextPrev("스킬창을 잘 보면 스킬을 찾을 수 있을꺼야. 먼저 공격을 통해 재규어의 HP를 50%이하로 떨어뜨린 후, 포획 스킬을 사용해서 재규어를 사로잡는거지. 어때, 간단하지?");
        } else if (status == 4) {
	    qm.sendPrev("재규어들은 어디에서 찾을 수 있냐고? 내 앞의 블랙잭이 너를 그들이 있는 곳으로 인도해 줄 꺼야.");
            qm.dispose();
        }
    }
}