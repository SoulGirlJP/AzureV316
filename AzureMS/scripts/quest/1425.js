var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    qm.sendNext("인파이터(Infighter)의 길을 택한 건가? 맨주먹이나 너클로 적을 맞서 싸우는 용감한 해적이지. 화려한만큼 쉽지 않은 직업일텐데... 과연 네가 해낼 수 있을까?");
        } else if (status == 1) {
	    qm.sendNextPrev("가장 특징적인 스킬은 #b토네이도 어퍼#k일까? 회오리 바람이 생길만큼 빠르게 어퍼컷을 올려쳐 진짜로 회오리 바람을 일으켜 버리지. #b스크류 펀치#k도 멋진 스킬이야. 전방으로 돌진하며 적을 공격하니까.");
        } else if (status == 2) {
	    qm.sendNextPrev("물론 기본기도 중요해. #b너클 마스터리#k와 #b너클 부스터#k를 익히면 너클을 더욱 빠르고 강력하게 사용할 수 있지. #bHP 증가#k를 통해 HP를 늘리고 참을성을 키우는 #b인듀런스#k를 통해 체력과 MP를 지속적으로 회복할 수도 있어.");
        } else if (status == 3) {
	    qm.sendNextPrev("사설이 너무 길었지? 그럼 인파이터가 되기 위한 시험을 시작할까? 시험 자체는 간단하다. 준비된 시험장에 들어가 준비된 몬스터를 모두 물리치고 #r그들이 떨어뜨리는 아이템#k을 가져오면 되니까. 다만 그 몬스터들은 방어력이 높으니 사냥하기 쉽지 않지. 꼭 기억해 둬라.");
        } else if (status == 4) {
	    qm.askAcceptDecline("시험을 보다가 물약이 떨어지면 #b퀘스트를 포기하고 다시 시작#k하니 그런 일이 없도록 미리 충분한 물약을 준비해서들어가도록. 그럼 바로 시험을 시작하지. 수락하면 널 시험의 선실로 보내겠다.");
        } else if (status == 5) {
	    qm.warp(912040000,0);
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendYesNo("검은 구슬을 모두 가져왔군. 나쁘지 않은 성적이야. 아니, 오히려 훌륭한 편이군. 너라면 분명 좋은 인파이터가 될 거야. 그럼 바로 인파이터로 만들어 주지.");
        } else if (status == 1) {
	    qm.changeJob(510);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋아. 너는 이제부터 #b인파이터#k야. 인파이터는 맨 몸과 맨 주먹만으로 적을 제압하는 자. 그렇기 때문에 남들보다 수련에 더 힘써야하지. 수련에 어려움이 있다면 나도 도와주겠어.");
        } else if (status == 2) {
            qm.sendNext("인파이터는 강해져야 해. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니야. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것... 그것은 강해지는 것 이상으로 어려운 일이야.");
            qm.dispose();
        }
    }
}