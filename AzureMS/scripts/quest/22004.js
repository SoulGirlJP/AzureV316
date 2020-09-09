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
            qm.sendNext("어이쿠, 이런 뭔가 허전하다 했더니 오늘은 사랑이 담긴 도시락을 빼먹고 왔군. 어제는 손수건을, 그제는 모자를, 그 전에는 신발을 잊었는데. 점점 건망증이 심해진단 말이야.");
	} else if (status == 1) {
            qm.sendNextPrev("아무튼 마침 잘 왔구나. 여기까지 온 김에 아빠 부탁 하나만 들어주지 않으련?");
        } else if (status == 2) {
            qm.sendNextPrev("사실은 요새 농장의 돼지들이 좀 이상하단다. 괜히 화를 내거나 짜증을 부리는 일이 많아. 그게 걱정돼서 오늘도 일찍 나와봤는데, 아니나 다를까 돼지들 중 몇마리가 밤새 울타리를 뚫고 밖으로 뛰쳐나간 모양이구나.");
        } else if (status == 3) {
            qm.askAcceptDecline("돼지들을 찾아오기 전에 일단 망가진 울타리부터 고쳐놔야 하지 않겠니? 다행이 그렇게 많이 망가진 건 아니라 나무토막만 몇 개 있으면 고칠 수 있을 것 같구나. 에반이 #b나무토막#k을 #b3개#k만 구해다주면 편할 텐데...");
        } else if (status == 4) {
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
	    qm.gainExp(210);
	    qm.gainItem(3010097,1);
	    qm.gainItem(2022621,15);
	    qm.gainItem(2022622,15);
            qm.forceCompleteQuest();
            qm.sendNext("오, 나무토막은 다 구해온 거니? 장하구나. 그럼 상으로 뭘 주면 될까... 옳지, 그게 있었지.\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i3010097# 튼튼한 나무 의자 1개\r\n#i2022621# 맛있는 우유 15개\r\n#i2022622# 맛있는 주스 15개\r\n#fUI/UIWindow.img/QuestIcon/8/0# 210 exp");
        } else if (status == 1) {
            qm.sendNextPrev("자, 울타리를 만들고 남은 판자로 만든 새 의자란다. 모양은 그래도 튼튼해서 쓸만할 거야. 잘 쓰렴.");
        } else if (status == 2) {
            qm.askAcceptDecline("이런, 문제가 하나 더 있구나. 울타리가 망가진 틈을 타 태어난 지 얼마 되지 않은 #b아기 돼지#k 한 마리가 도망친 모양이야. 어려서 길도 모를테니 아무래도 직접 찾으러 가야 할 것 같구나. 에반이 도와주지 않겠니?");
        } else if (status == 3) {
            qm.forceStartQuest(22005);
            qm.dispose();
        }
    }
}