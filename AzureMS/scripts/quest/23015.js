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
	    qm.sendNext("와일드헌터라면 라이딩이 필수지. 와일드헌터가 되면서 너에겐 포획이라는 특별한 스킬이 생겼을꺼야. 그 스킬로 재규어를 길들여서 타고 다닐수 있어.");
        } else if (status == 1) {
            qm.sendNextPrev("스킬창을 잘 보면 #s30001061# 스킬을 찾을 수 있을꺼야. 먼저 공격을 통해 재규어의 HP를 50%이하로 떨어뜨린 후, 포획 스킬을 사용해서 재규어를 사로잡는거지. 사로 잡은 후, #s33001001#을 사용하면 재규어를 탈 수 있어. 어때, 간단하지?");
        } else if (status == 2) {
            qm.sendNextPrev("재규어들은 어디에서 찾을 수 있냐고? 내 앞의 블랙잭이 너를 그들이 있는 곳으로 인도해 줄 꺼야.");
        } else if (status == 3) {
            qm.sendNextPrevS("저기요, 블랙잭님? 제가 어디로 가면 되는지 알려주세요.",3);
        } else if (status == 4) {
	    qm.sendNextPrevNPC("음 새로운 와일드헌터인가. 아직 애송이로군.",4,2151008);
        } else if (status == 5) {
            qm.sendNextPrevS("아직은 약하지만 당당한 레지스탕스의 일원이 되기 위해 노력할 꺼예요. 어디로 가면 재규어들을 만날 수 있는거죠?",3);
        } else if (status == 6) {
	    qm.sendNextPrevNPC("정신상태는 쓸만하군. 나의 형제들을 만나게 해주지. 앞으로도 그들을 만나길 원할 때는 나를 찾아와.",4,2151008);
	} else if (status == 7) {
	    qm.forceStartQuest();
	    qm.warp(931000500,0);
	    qm.dispose();
	}
    }
}