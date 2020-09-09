


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	아룽 에 의해 만들어 졌습니다.

	엔피시아이디 : 1512001

	엔피시 이름 : 아기 펭귄

	엔피시가 있는 맵 : 리에나 해협 : 마녀 바바라의 집 (141040000)

	엔피시 설명 : MISSINGNO


*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    cm.dispose(); // TODO: REMOVE => WOLLY ADDED THIS
    return; // TODO: REMOVE => WOLLY ADDED THIS

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.sendOk("");
    }
}
        
var status = 0;
/*
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && (status == 0 || status == 1)) {
	    cm.sendOk("#fn바탕##e#r이런 미친새끼야 안할거면 꺼져ㅡ");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
	    cm.sendYesNo("#fn바탕##e#r잉여#k#n#b 보다 못한 놈아 친구 목록 늘리고 싶으면  \r\n\r\n예 버튼 클릭 해라.#k");
	} else if (status == 1) {
	    cm.sendYesNo("#fn바탕##k#n#b옘병 미친놈아 #e#r3000000#k#n#b 만원 있으면 \r\n\r\n예 버튼 클릭 해라.#k");
	} else if (status == 2) {
	    if (cm.getMeso() >= 2500000 && cm.getBuddyCapacity() < 100) {
		cm.gainMeso(-2500000);
           cm.teachSkill(20011004,1,1);
		cm.updateBuddyCapacity(cm.getBuddyCapacity() + 5);
		cm.sendNext("#fn바탕##r옛다,#k#n#b 새끼 ?만한게 친구는 많나보네?");
	    } else {
		cm.sendNext("#fn바탕##e#r돈없음ㅗㅗ");
	    }
	    cm.dispose();
	}
    }
}
        cm.dispose();
        return;
    }
}*/
