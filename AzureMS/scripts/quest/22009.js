/*
	Description: 	Quest -  Verifying the Farm Situation
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendOk("뭐얏? 잘 생각해 봐! 농장 일이 잘 안 되면 우리는 뭘 먹고 살겠어? 응? 자, 다시 말을 걸어서 이번에는 꼭 수락해!");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.askAcceptDecline("집 주변이야 그렇다고 쳐도, 농장 쪽까지 여우가 들었다면 클인인데. 농장 일에 엄청나게 방해가 될 게 뻔하잖아. 미리미리 여우의 숫자를 조사해야겠어. 그렇지?");
    } else if (status == 1) {
	qm.forceStartQuest();
	qm.sendOk("그럼그럼, 역시 내 아우. 그럼 #b농장 중심지#k로 가서 #b아빠#k한테 농장 쪽 사정을 물어봐 줘. 그쪽도 음흉한 여우가 늘었다고 하면 한 번 크게 음흉한 여우사냥을 해야 할 것 같으니 말이야.");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendOk("응? 또 무슨 일이냐,에반? 또 사랑이 담긴 도시락을 배달 온 건 아닐테고 아빠는 바빠서 놀아주지도 못할 텐데.. 뭐? 농장에 여우들이 늘었냐고?");
    } else if (status == 1) {
	qm.sendNext("글쎄다? 여우가 늘었는지 줄었는지 바빠서 원 살펴볼 틈이 없구나. #b돼지#k들이 이상하게 날뒤고 있거든.오히려 여우들이 돼지를 슬슬 피할 정도니..");
	qm.gainExp(260);
	qm.forceCompleteQuest();
    } else if (status == 2) {
	qm.sendPrev("아, 그래서 집 쪽에 음흉한 여우가 늘었을지도 모르겠구나. 돼지들을 피해 집 쪽으로 간 거지. 흠...");
	qm.dispose();
    }
}