var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 5)  {
            qm.sendNextS("#b(두려운 마음에 거절을 눌러 버렸다. 하지만 이대로 도망칠 수도 없는데... 마음을 진정시키고 다시 말을 걸자.)#k",3);
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("이제 얼추 몸이 풀리신 것 같네요. 이럴 때야말로 더욱 혹독하게 단련을 해줘야 완벽한 기초 체력을 갖게 되는 법. 자, 그럼 계속해서 기초 체력 단련을 해보죠.");
        } else if (status == 1) {
            qm.sendNextPrev("그럼 이번에는 #b눈 덮인 벌판3#k에 가서 #r무루무루#k를 퇴치해 보죠. 한... #r20마리#k 정도 퇴치하면 체력 단련에 도움이 될 거예요. 자, 어서 가서... 응? 뭔가 하시고 싶은 말씀이라도 있으신가요?");
        } else if (status == 2) {
            qm.sendNextPrevS("...왠지 점점 숫자가 늘어나고 있지 않아?",3);
        } else if (status == 3) {
            qm.sendNextPrev("늘어나고 있어요. 어머, 혹시 20마리로는 부족하신 건가요? 그럼 한 100마리쯤 퇴치해 볼까요? 아니, 아니지. 이왕 수련하는 건데 슬리피우드의 누구처럼 999마리 잡기 정도는...");
        } else if (status == 4) {
            qm.sendNextPrevS("아, 아냐. 이대로도 충분하다.",3);
        } else if (status == 5) {
            qm.askAcceptDecline("어머어머, 그렇게 사양하실 거 없어요. 빨리 강해지고 싶으신 영웅님의 마음이라면 충★분★히★ 알고 있는 걸요. 역시 영웅님은 대단하시다니...");
        } else if (status == 6) {
            qm.sendNextS("#b(더 이상 듣고 있다가는 정말 999마리 퇴치를 하게 될 것 같아 수락해 버렸다.)#k",3);
        } else if (status == 7) {
            qm.sendNextPrev("그럼 무루무루 20마리 퇴치를 부탁 드릴게요.");
        } else if (status == 8) {
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
        if (status == 0) {
            qm.sendNext("무루무를 모두 퇴치하고 돌아오셨군요. 좀 모자라지 않았나요? 역시 한 300마리만이라도 더 퇴치...\r\n\r\n#b(등 뒤에서 식은땀이 흐른다.)");
        } else if (status == 0) {
            qm.sendNext("...했으면 좋겠지만 이미 기초 체력은 어느 정도 만들어진 것 같으니 그럴 필요는 없을 것 같네요.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i2000022# 25 #t2000022# \r\n#i2000023# 25 #t2000023# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 550 exp");
        } else if (status == 1) {
            qm.gainItem(2000022,25);
            qm.gainItem(2000023,25);
            qm.forceCompleteQuest();
            qm.gainExp(550);
            qm.sendNext("마지막으로 기초 체력을 점검하기로 해요. 그럼 준비가 되면 말을 걸어 주세요.");
            qm.dispose();
        }
    }
}