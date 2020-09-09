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
	    qm.sendNext("페이지(Page)의 길을 택한 건가? 페이지는 자신을 절제하고, 적을 경계하며, 강한 힘으로 타인을 지키기를 원하는 자들을 말한다네. 주로 사용하는 무기는 #b검#k과 #b둔기#k이지.");
        } else if (status == 1) {
	    qm.sendNextPrev("전사답게 페이지의 스킬에는 스스로를 강화하는 게 많다네. 무기를 능숙히 다루는 #b웨폰 마스터리#k, 강력한 데미지의 #b파이널 어택#k, 공격속도를 올려주는 #b웨폰 부스터#k, 튼튼한 신체를 만드는 #b피지컬 트레이닝#k까지.");
        } else if (status == 2) {
	    qm.sendNextPrev("물론 그뿐만이 아니지. 페이지에게는 #b위협#k이라는 스킬이 있어 다수의 적을 일정시간 동안 위축되게 하는 기술이 있다네. 적에는 냉정한 페이지의 특징을 그대로 보여준다고 할까?");
        } else if (status == 3) {
	    qm.sendNextPrev("거기에 #b파워 가드#k를 쓰면 일정 시간동안 적으로부터 받는 데미지의 일부를 흡수하고, 피해를 되돌려 줄 수도 있지. 냉정하면서도 효율적인 기술이라네.");
        } else if (status == 4) {
	    qm.sendNextPrev("물론 더 강력한 공격 스킬도 가지고 있네. #b그라운드 스매시#k라는 스킬을 아는가? 전방으로 충격파를 날려 다수의 적을 공격하는 스킬인데, 이것만 있다면 사냥이 예전보다 한결 쉬워질 걸세.");
        } else if (status == 5) {
	    qm.sendNextPrev("자, 설명은 이쯤이면 되었을 것 같군. 이제 선택의 시간이네. 페이지의 길을 원하는가? 원하다면 자네를 내가 준비한 시험장으로 보내겠네. 그 안에서 #r어둠의 힘이 담긴 구슬 30개#k를 구해오면 진정한 페이지가 될 수 있지.");
        } else if (status == 6) {
	    qm.askAcceptDecline("자네가 퀘스트를 수락하면 바로 시험이 시작된다네. 만약 원하지 않는다면 지금 거절하게. 거절하고 다른 길에 대한 설명을 듣는 것도 나쁘지는 않아. 어때... 페이지의 시험을 시작하겠는가?");
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
            qm.sendYesNo("검은 구슬 30개를 모두 가져왔군... 역시 내 눈은 틀리지 않았어. 자네라면 훌륭히 해낼 거라고 믿었지. 침착하면서도 용맹해 보이는 자네라면 말이야... 좋아, 그럼 자네를 페이지의 길로 인도하지. 준비는 되었나?");
        } else if (status == 1) {
	    qm.changeJob(120);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋아! 자네는 이제부터 #b페이지#k일세! 페이지는 적에게는 냉정하지만 아군에게는 누구보다 헌신적인, 기사도를 가진 전사라네. 밝은 눈으로 올바른 길로 나아가길 바라네.");
        } else if (status == 2) {
            qm.sendNext("앞으로 자네는 지금까지보다 훨씬 더 강한 힘을 갖게 될 걸세. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니라네. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것. 그것이 자네에게 주어진 책임이라네.");
            qm.dispose();
        }
    }
}