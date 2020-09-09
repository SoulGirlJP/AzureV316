


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	엔피시아이디 : 1052007
	
	엔피시 이름 : 개찰구

	엔피시가 있는 맵 : 103020000

	엔피시 설명 : 개찰구


*/


var status = -1;
var select = 0;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        var text = "커닝시티 지하철의 개찰구다.\r\n\r\n";
        if (cm.getQuestStatus(2055) >= 1) {
            text += "#e#L0##b3호선공사장 - B1#l\r\n";
        } else if (cm.getQuestStatus(2056) >= 1) {
            text += "#e#L1##b3호선공사장 - B2#l\r\n";
        } else if (cm.getQuestStatus(2057) >= 1) {
            text += "#e#L2##b3호선공사장 - B3#l\r\n\r\n";
        }
        //text += "#n#L3##b커닝스퀘어행 지하철 타기#k#l\r\n"; //스퀘어삭제
        text += "#n#L4##b커닝시티 지하철 선로#k#l\r\n";
        cm.sendSimple(text);
    } else if (status == 1) {
        select = selection;
        if (select == 0) {
            if (cm.haveItem(4031036, 1)) {
                cm.gainItem(4031036, -1);
                cm.warp(910360000, 0); //103000900
                cm.dispose();
            } else {
                cm.sendOk("#b#v4031036##k 아이템이 필요합니다.");
                cm.dispose();
            }
        } else if (select == 1) {
            if (cm.haveItem(4031037, 1)) {
                cm.gainItem(4031037, -1);
                cm.warp(910360100, 0);//103000903
                cm.dispose();
            } else {
                cm.sendOk("#b#v4031037##k 아이템이 필요합니다.");
                cm.dispose();
            }
        } else if (select == 2) {
            if (cm.haveItem(4031038, 1)) {
                cm.gainItem(4031038, -1);
                cm.warp(910360200, 0);//103000906
                cm.dispose();
            } else {
                cm.sendOk("#b#v4031038##k 아이템이 필요합니다.");
                cm.dispose();
            }
        } else if (select == 3) {
            cm.sendYesNo("#b#e커닝스퀘어 역#k#n으로 가는 열차에 탑승하시겠습니까?");
        } else if (select == 4) {
            cm.sendYesNo("해당 지역은 몬스터가 출몰할 수 있어 위험합니다. 정말 가시겠습니까?");
        }
    } else if (status == 2) {
        if (select == 3) {
            cm.timeMoveMap(103020020, 103020010, 14);
            cm.getPlayer().message(6, "다음 정차할 역은 커닝스퀘어, 커닝스퀘어 역입니다. 내리실 문은 오른쪽입니다.");
            cm.dispose();
        } else if (select == 4) {
            cm.warp(103020100, 0);
            cm.dispose();
        }
    }
}


