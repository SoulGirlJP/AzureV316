var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 2) {
            qm.sendNext("영웅이었다면서 뭘 그렇게 밍기적거리세요? 쇠뿔도 단김에 빼라잖아요? 강해지려면 일단 팍! 시작하고 보는 거예요.");
            qm.dispose();
	    return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("자, 그럼 이제 설명은 여기까지로 해 두고 다음 단계로 넘어가죠, 다음 단계가 뭐냐고요? 방금 말씀 드렸잖아요. 검은 마법사를 한 방에 해치울 수 있을 정도로 강해질 때까지 수련하는 거예요.");
        } else if (status == 1) {
            qm.sendNextPrev("당신이 과거에 영웅이었던 건 확실하지만 그거야 수백 년 전 얘기, 검은 마법사의 저주가 아니래도 얼음 속에 그렇게 오래있었으니 몸이 굳었을 게 당연하잖아요? 일단은 그 굳은 몸부터 풀어봐야겠어요. 어떻게 하느냐고요?");
        } else if (status == 2) {
            qm.askAcceptDecline("체력이 국력이다! 영웅도 기초 체력부터! ...라는 말도 모르세요? 당연히 #b기초 체력 단련#k을 해야죠. ...아, 기억을 잃었으니 모르시겠군요. 뭐 모르셔도 괜찮아요. 직접 해보시면 알 테니까. 그럼 바로 기초 체력 단련에 들어갈까요?");
        } else if (status == 3) {
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode > 0)
            status++;
        else
            status--;
        if (status > 0) {
            qm.sendNext("무루파는 퇴치하신 건가요? 하긴, 이 정도는 기본중에 기본. 눈 감고 휘둘러도 할 수 있는 간단한 일인데 못하면 창피하죠. 펭귄만도 못한 순발력의 영웅? 생각만 해도 부끄럽네요.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i2000022# 15 #t2000022# \r\n#i2000023# 15 #t2000023# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 320 exp");
        } else if (status == 1) {
            qm.gainItem(2000022, 15);
            qm.gainItem(2000023, 15);
            qm.forceCompleteQuest();
            qm.gainExp(320);
            qm.sendNext("펭귄보다 좀 낫긴 하지만 아직 본격적인 단련을 하기엔 못 미치는 체력이에요. 어서 다음 단계로 넘어가죠. 다음 단계 단\r\n련을 할 준비가 되면 말을 걸어 주세요.");
            qm.dispose();
        }
    }
}