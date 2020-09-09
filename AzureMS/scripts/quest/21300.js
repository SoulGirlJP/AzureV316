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
	    qm.sendNext("수련은 잘 되고 있나요? 흐음... 레벨 70이라... 아직 부족하지만, 처음 얼음 속에서 만났을 때는 막 꺼낸 동태같던 당신에 비하면 장족의 발전이네요. 이대로 계속 강해지다 보면 분명 예전의 힘을 되찾을 수 있을 거예요.");
        } else if (status == 1) {
            qm.askAcceptDecline("하지만 그 전에 먼저 리엔으로 잠시 돌아와 주셔야겠어요. #b당신의 폴암이 또다시 이상한 반응을 보이고 있거든요. 뭔가 할 말이 있는 모양이에요.#k 어쩌면 당신의 능력을 깨워줄 수 있을지도 모르니 빨리 와 주세요.");
        } else if (status == 2) {
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}