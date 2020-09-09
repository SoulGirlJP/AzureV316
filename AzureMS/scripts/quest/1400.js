var status = -1;
var selctioned = 0;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
	    qm.sendNext("흠. 너도 레벨이 슬슬 오르고 있는데, 어떤 직업으로 모험을 할지는 결정 했어? 어떤 직업이 있는지는 알고 있지? 강력한 힘과 체력의 전사, 다양한 마법의 마법사, 원거리에서 화살로 공격하는 궁수, 어둠 속에서 빛나는 칼날 도적, 다양한 연계기로 화려한 기술을 뽑내는 해적까지... 다양한 직업들이 있잖아.");
        } else if (status == 1) {
	    var chat = "빅토리아 아일랜드로 가면 전직관을 통해 원하는 직업으로 전직할 수 있어. 그러기 전에 미리 네가 원하는 직업을 알려주면 #b그들#k에게 널 추천하는 편지를 보내 줄게. 미리 소개 받고 가면 쉽게 전직할 수 있을 거야. 어떤 직업을 택할거야?\r\n#b";
	    chat += "\r\n#L0#강력한 힘과 체력의 전사";
	    chat += "\r\n#L1#다양한 마법으로 적과 싸우는 마법사";
	    chat += "\r\n#L2#빠르고 정확한 화살로 멀리서 공격하는 궁수";
	    chat += "\r\n#L3#어둠 속에서 능력을 발휘하는 도적";
	    chat += "\r\n#L4#다양한 연계기를 자랑하는 해적";
            qm.sendSimple(chat);
        } else if (status == 2) {
	    if (selection == 0) {
		qm.sendNext("오? 전사란 말이지. 전사는 멋지지! 강한 힘으로 몬스터를 공격하는 건 물론이고, 체력이 좋아서 어지간히 맞아도 위험하지 않으니까 말이야. 좋아, 그럼 널 추천하는 편지를 전사 전직관 #b주먹펴고 일어서#k에게 보내 놓을게.");
	    } else if (selection == 1) {
		qm.sendNext("마법사를 선택한 거야? 마법사는 신비롭지~ 다양한 마법으로 적을 공격하는 걸 보면 굉장히 화려하잖아? 체력은 약하지만 그것도 마법으로 보완이 되니까. 그럼 널 추천하는 편지를 마법사 전직관 #b하인즈#k님에게 보내 놓을게.");
	    } else if (selection == 2) {
		qm.sendNext("궁수에 대한 설명이 없습니다.");
	    } else if (selection == 3) {
		qm.sendNext("도적을 원하는 거야? 어둠 속에서 은밀하게 빛나는 칼날, 그게 바로 도적이지! 그늘 속에 가려 있는 듯 하면서도 엄청나게 강력한 그들!  그럼 널 추천하는 편지를 도적 전직관 #b다크로드#k에게 보내 놓을게...");
	    } else if (selection == 4) {
		qm.sendNext("해적! 한 손에 든 총으로 적을 무찌르는 것도, 온 몸을 무기로 체술 공격을 하는 것도 화려해! 컨트롤이 어려울 수도 있지만 그것만 극복하면 멋지지! 그럼 널 추천하는 편지를 해적 전직관 #b카이린#k에게 보내 놓을게.");
	    }
	    selctioned = selection;
        } else if (status == 3) {
	    if (selctioned == 0) {
		qm.sendNextPrev("네가 #b레벨 10#k이 될 때쯤에 그에게 연락이 올 거야. 꼭 멋진 전사로 전직하길 바랄게.");
	    } else if (selctioned == 1) {
		qm.sendNextPrev("마법사는 다른 직업보다 일찍 전직하는 거 알고 있지? 네 #b레벨이 8#k이 되면 하인즈님께 연락이 올 거야. 마법사로 전직하는 네 모습을 기대하지.");
	    } else if (selctioned == 2) {
		qm.sendNextPrev("궁수에 대한 설명이 없습니다.");
	    } else if (selctioned == 3) {
		qm.sendNextPrev("조금만 더 레벨을 올려서 #b레벨 10#k이 되면 그에게 연락이 올 거야. 꼭 멋진 도적이 되라고!..");
	    } else if (selctioned == 4) {
		qm.sendNextPrev("아직은 아니고, 한 #b레벨 10#k이 되면 그녀에게 연락이 올 거야. 위풍당당한 해적이 되어 보라고!");
	    }
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
    }
}