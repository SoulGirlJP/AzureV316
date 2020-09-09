var status = -1;

function end(mode, type, selection) {
    cm.dispose(); // TODO: REMOVE => WOLLY ADDED THIS
    return; // TODO: REMOVE => WOLLY ADDED THIS
    
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
            if (qm.getQuestStatus(2570) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(2570) == 1) {
		qm.sendNext("반갑군, 와나왔구나. 스토너를 도와 이곳까지 왔다는 소리는 들었다. 꽤 다친 것 같았는데 벌써 다 나은 건가? 정말 강골이군... 스토너가 탐을 내는 것도 이해가 가. 내 이름은 카이린. 해적선, 노틸러스의 함장이며 해적들의 전직교관이기도 하지.");
	} else if (status == 1) {
	    qm.sendNextPrev("스토너가 널 #b캐논슈터#k로 만들고 싶어한다는 말은 들었겠지? 나 역시 너 같은 좋은 인재를 해적으로 받아들이는 것은 환영이야. 하지만 네가 과연 해적을 원할지 조금 걱정되는군. 그러니 해적에 관한 이야기를 먼저 해야겠어.");
	} else if (status == 2) {
	    qm.sendNextPrev("그러니 너도 해적이 된다면 검은 마법사를 조사하는 일을 좀 해줘야 할 거야. 물론 이건 의무는 아니다. 어디까지나 권유이지. 나는 해적들의 전직교관이지만 해적들의 주인은 아냐. 명령이 아닌 권유일 뿐이다.");
	} else if (status == 3) {
	    qm.sendNextPrev("그러나 너 역시 메이플 월드를 모험하는 자라면 메이플 월드를 위해 이 정도의 일은 해줄 수 있을 거라고 믿는다. 보상을 바래서가 아니라, 어디까지나 선의로... 후후, 서론이 너무 길었군.");
	} else if (status == 4) {
	    qm.askAcceptDecline("너무 오래 떠들었군... 그럼 이제 결정해라. 해적이 될 것인가? 캐논슈터가 되어준다면 기쁠 것 같군.");
	} else if (status == 5) {
	    qm.getPlayer().changeJob(501);
	    qm.forceCompleteQuest();
	    qm.sendNext("자, 이제 너도 우리 해적의 일원이야. 해적 스킬들이 많이 생겼으니 스킬창을 열어보게. SP도 약간 줬으니 스킬을 올릴 수 있을 거야. 더 레벨이 높아지면 더 많은 스킬을 쓸 수 있으니 수련에 힘쓰도록.");
	}
    }
}   
}