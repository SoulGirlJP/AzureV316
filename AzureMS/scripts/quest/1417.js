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
	    qm.sendNext("클레릭(Cleric)의 길을 원하는 건가? 클레릭은 다른 두 계열의 마법사에 비해 조금 특이하지. #b회복#k과 #b보조#k 계열의 스킬이 많거든.");
        } else if (status == 1) {
	    qm.sendNextPrev("물론 기본은 마법사라네. 적의 MP를 흡수하는 #bMP이터#k, 순간 이동, #b텔레포트#k, 더 강력한 마법을 사용하기 위한 기본기 #b스펠 마스터리#k, #b하이 위즈덤#k등의 스킬은 모두 사용할 수 있지. #b인빈서블#k이라는 스킬을 통해 자신이 받는 데미지를 감소 시킬 수도 있고 말이야.");
        } else if (status == 2) {
	    qm.sendNextPrev("그러나 클레릭의 가장 큰 특징은 회복마법, #b힐#k이겠지. 자신은 물론 주변 파티원의 HP를 전원 회복시키는 놀라운 마법이라네. 뿐만 아니라 #b블레스#k를 통해 파티원의 능력치를 올려주기도 하니, 파티 사냥에서 무척 사랑받겠지?");
        } else if (status == 3) {
	    qm.sendNextPrev("공격 마법이 없는 것도 아니라네. #b홀리 에로우#k로 다수의 적을 공격할 수도 있지. 이 마법의 가장 큰 특징은 악마 계열의 몬스터나 언데드 계열의 몬스터에게 강력한 데미지를 줄 수 있다는 것이지.");
        } else if (status == 4) {
	    qm.sendNextPrev("설명은 이쯤 해둘까...? 무엇보다 마법은 직접 사용해 보는 것이 가장 재밌으니 말일세. 그럼 클레릭의 길을 택하겠는가? 택하겠다면 먼저 자네가 클레릭이 될 자격이 있는지 시험해 보도록 하겠네.");
        } else if (status == 5) {
	    qm.askAcceptDecline("시험은 간단하네... 준비된 시험장에 몬스터를 물리치고 #r어둠의 힘이 담긴 구슬 30개#k를 가져오면 된다네... 그럼 바로 시험을 시작하겠나? 수락하면 시험의 숲으로 보내주지.");
        } else if (status == 6) {
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
            qm.sendYesNo("검은 구슬은 모두 모아왔군... 자네라면 어렵지 않게 해낼 거라 믿었네. 그럼, 드디어... 자네를 클레릭으로 만들어 주지. 준비는 되었나...?");
        } else if (status == 1) {
	    qm.changeJob(230);
	    qm.forceCompleteQuest();
	    qm.removeAll(4031013);
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋네... 자네는 이제부터 #b클레릭#k일세... 클레릭은 신을 섬기는 마음으로 만물에게 생명의 힘을 주는 자... 더욱 깊은 신앙심을 가지도록 바라네...");
        } else if (status == 2) {
            qm.sendNext("클레릭은 신앙심이 가장 중요하다네. 신을 믿고 따르면서 모든 이들에게 관대함을 보이도록 하게... 꾸준히 정진한다면 더욱 높은 신적 능력을 얻을 수 있을지도 모르지...");
            qm.dispose();
        }
    }
}