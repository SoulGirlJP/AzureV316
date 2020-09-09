var status = -1;
var sel = 0;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (!cm.getPlayer().isGM()) {
	cm.sendOk("GM만 나에게 말을 걸어 게임을 시작할 수 있다구YO!\r\n");
	cm.dispose();
	return;
    }
    if (status == 0) {
        var str = "";
        str += "#e#r두 배 더#k#n 짜릿해진 #e#b100인 빙고 <보드 에디션>!#k#n#l\r\n";
        str += "게임에 참여하겠나YO?\r\n";
        str += "차원이 다른 보상이 기다리고 있다구YO!\r\n";
        str += "이거 실화냐구YO!\r\n";
        str += "대기실에 최소 5명이 있어야 진행된다YO!\r\n\r\n";
        str += "#b('예'를 누를시 게임진행맵으로 이동 된다구YO!)#k#l";
        cm.sendYesNo(str);
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() == 922290000) {
            cm.StartBingoGame();
            cm.dispose();
        }
    }
}
