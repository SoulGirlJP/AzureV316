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
	    qm.sendNext("불, 독 계열의 위자드(Wizard)는 이름 그대로 위자드, 더 높은 등급의 마법사라네. 사용할 수 있는 마법의 속성이 불과 독 속성이라 그리 불리는 것이지.");
        } else if (status == 1) {
	    qm.sendNextPrev("위자드답게 기본적으로는 더 높은 등급의 마법들을 사용할 줄 안다네. 적의 MP를 흡수하는 #bMP이터#k나 파티원의 마력을 증가시키는 #b메디테이션#k, 순간 이동, #b텔레포트#k, 적들의 이동속도를 낮추는 #b슬로우#k나 더 강력한 마법을 사용하기 위한 기본기 #b스펠 마스터리#k, #b하이 위즈덤#k.");
        } else if (status == 2) {
	    qm.sendNextPrev("하지만 불, 독 계열의 위자드의 특징은 무엇보다 강력한 화염의 화살 #b파이어 에로우#k와 맹독성 물방울을 적에게 날려 주변의 적들까지 중독시키는 #b포이즌 브레스#k겠지. 둘 모두 강력한 공격 스킬이라네.");
        } else if (status == 3) {
	    qm.sendNextPrev("설명은 이쯤 해둘까...? 무엇보다 마법은 직접 사용해 보는 것이 가장 재밌으니 말일세. 그럼 불, 독 위자드의 길을 택하겠는가? 택하겠다면 먼저 자네가 위자드가 될 자격이 있는지 시험해 보도록 하겠네.");
        } else if (status == 4) {
	    qm.askAcceptDecline("시험은 간단하네... 준비된 시험장에 몬스터를 물리치고 #r어둠의 힘이 담긴 구슬 30개#k를 가져오면 된다네... 그럼 바로 시험을 시작하겠나? 수락하면 시험의 숲으로 보내주지.");
        } else if (status == 5) {
	    qm.warp(910140000,0);
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
            qm.sendYesNo("검은 구슬은 모두 모아왔군... 자네라면 어렵지 않게 해낼 거라 믿었네. 그럼, 드디어... 자네를 불, 독 계열의 위자드로 만들어 주지. 준비는 되었나...?");
        } else if (status == 1) {
	    qm.changeJob(210);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋네... 자네는 이제부터 #b불, 독 계열의 위자드#k일세... 위자드는 높은 지능을 바탕으로 초자연의 힘으로 적을 제압하는 자... 더욱 학업에 정진하도록 바라네...");
        } else if (status == 2) {
            qm.sendNext("위자드는 강해져야 하네... 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니라네. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것... 그것은 강해지는 것 이상으로 어려운 일이지...");
            qm.dispose();
        }
    }
}