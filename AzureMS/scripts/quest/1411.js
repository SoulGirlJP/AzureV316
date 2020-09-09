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
	    qm.sendNext("파이터(Fiter)에 대한 설명이 없습니다.");
        } else if (status == 1) {
	    qm.sendNextPrev("전사답게 파이터의 스킬에는 스스로를 강화하는 게 많다네. 무기를 능숙히 다루는 #b웨폰 마스터리#k, 강력한 데미지의 #b파이널 어택#k, 공격속도를 올려주는 #b웨폰 부스터#k, 튼튼한 신체를 만드는 #b피지컬 트레이닝#k까지.");
        } else if (status == 2) {
	    qm.sendNextPrev("파이터(Fiter)의 스킬에 대한 설명이 없습니다.");
        } else if (status == 3) {
	    qm.sendNextPrev("파이터(Fiter)의 스킬에 대한 설명이 없습니다.");
        } else if (status == 4) {
	    qm.sendNextPrev("물론 더 강력한 공격 스킬도 가지고 있네. #b그라운드 스매시#k라는 스킬을 아는가? 전방으로 충격파를 날려 다수의 적을 공격하는 스킬인데, 이것만 있다면 사냥이 예전보다 한결 쉬워질 걸세.");
        } else if (status == 5) {
	    qm.sendNextPrev("자, 설명은 이쯤이면 되었을 것 같군. 이제 선택의 시간이네. 파이터의 길을 원하는가? 원하다면 자네를 내가 준비한 시험장으로 보내겠네. 그 안에서 #r어둠의 힘이 담긴 구슬 30개#k를 구해오면 진정한 페이지가 될 수 있지.");
        } else if (status == 6) {
	    qm.askAcceptDecline("자네가 퀘스트를 수락하면 바로 시험이 시작된다네. 만약 원하지 않는다면 지금 거절하게. 거절하고 다른 길에 대한 설명을 듣는 것도 나쁘지는 않아. 어때... 파이터의 시험을 시작하겠는가?");
        } else if (status == 7) {
	    qm.warp(910230000,0);
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
            qm.sendYesNo("검은 구슬 30개를 모두 가져왔군... 역시 내 눈은 틀리지 않았어. 자네라면 훌륭히 해낼 거라고 믿었지. 침착하면서도 용맹해 보이는 자네라면 말이야... 좋아, 그럼 자네를 파이터의 길로 인도하지. 준비는 되었나?");
        } else if (status == 1) {
	    qm.changeJob(110);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("파이터(Fiter)에 대한 설명이 없습니다.");
        } else if (status == 2) {
            qm.sendNext("앞으로 자네는 지금까지보다 훨씬 더 강한 힘을 갖게 될 걸세. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니라네. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것. 그것이 자네에게 주어진 책임이라네.");
            qm.dispose();
        }
    }
}