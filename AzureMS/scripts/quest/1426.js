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
	    qm.sendNext("건슬링거(Gunslinger)의 길을 택한 건가? 건슬링거는 #b총#k을 이용해 먼 곳에 있는 적을 공격하는 원거리 직업이지. 총을 이용한 다양한 스킬은 활보다 화려하지만, 그만큼 난이도가 높아. 물론 재미는 있지만.");
        } else if (status == 1) {
	    qm.sendNextPrev("역시 가장 중요한 건 기본기야. #b건 마스터리#k, #b건 부스터#k는 기본 중에 기본들이지. 총을 강하고 빠르게 정확하게 쏘는 것들이니까.");
        } else if (status == 2) {
	    qm.sendNextPrev("하지만 진짜 재미는 공격 스킬에서 오지. 눈으로 확인할 수 없을만큼 빠른 속도로 다수의 대상에게 3번 연속 총을 발사하는 #b매그넘 샷#k이나, 하나의 적에게 세 번 연속 탄환을 발사하는 #b트리플 파이어#k, 후방으로 긴급 회피하면서도 총을 쏘는 #b백스텝 샷#k들은 강하면서도 재미있어.");
        } else if (status == 3) {
	    qm.sendNextPrev("한 순간 높이 점프하는 #b윙즈#k를 통해 점프한 후, 스킬 키를 계속 누르고 있으면 천천히 낙하하지. 거기다 낙하하면서도 총을 쏠 수 있어. 중요한 건 상황에 맞는 스킬을 정확히 사용하는 거야.");
        } else if (status == 4) {
	    qm.sendNextPrev("사설이 너무 길었지? 그럼 건슬링거가 되기 위한 시험을 시작할까? 시험 자체는 간단하다. 준비된 시험장에 들어가 준비된 몬스터를 모두 물리치고 #r그들이 떨어뜨리는 아이템#k을 가져오면 되니까. 다만 그 몬스터들은 방어력이 높으니 사냥하기 쉽지 않지. 꼭 기억해 둬라.");
        } else if (status == 5) {
	    qm.askAcceptDecline("시험을 보다가 물약이 떨어지면 #b퀘스트를 포기하고 다시 시작#k하니 그런 일이 없도록 미리 충분한 물약을 준비해서들어가도록. 그럼 바로 시험을 시작하지. 수락하면 널 시험의 선실로 보내겠다.");
        } else if (status == 6) {
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
            qm.sendYesNo("검은 구슬을 모두 가져왔군. 나쁘지 않은 성적이야. 아니, 오히려 훌륭한 편이군. 너라면 분명 좋은 건슬링거가 될 거야. 그럼 바로 건슬링거로 만들어 주지.");
        } else if (status == 1) {
	    qm.changeJob(520);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋아. 너는 이제부터 #b건슬링거#k야. 건슬링거는 건으로 멀리있는 적에게까지 예리한 공격으로 위협할 수 있는 자... 지금보다 강해지기 위해서는 수련에 힘써야하지. 수련에 어려움이 있다면 나도 도와주겠어.");
        } else if (status == 2) {
            qm.sendPrev("건슬링거는 강해져야 해. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니야. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것... 그것은 강해지는 것 이상으로 어려운 일이야.");
            qm.dispose();
        }
    }
}