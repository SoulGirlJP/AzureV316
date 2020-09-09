


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	엔피시아이디 : 1052006
	
	엔피시 이름 : 웅이

	엔피시가 있는 맵 : 지하철 매표소

	엔피시 설명 : ㅇㅅㅇ


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
        var typed = false;
        var text = "안녕하세요, 저는 커닝시티지하철의 공익요원, #b#p1052006##k라고 합니다. 도와드릴 용건이 있으신가요?\r\n\r\n";
        if (cm.getQuestStatus(2055) >= 1) {
            text+= "#b#L0#공사장B1 입장권 구매 (1000메소)#l\r\n";
            typed = true;
        }
        if (cm.getQuestStatus(2056) >= 1) {
            text+= "#b#L1#공사장B2 입장권 구매 (2000메소)#l\r\n";
            typed = true;
        }
        if (cm.getQuestStatus(2057) >= 1) {
            text+= "#b#L2#공사장B3 입장권 구매 (3000메소)#l\r\n";
            typed = true;
        }
        if (typed) {
            cm.sendSimple(text);
        } else {
            cm.sendOk(text);
            cm.dispose();
        }
    } else if (status == 1) {
        select = selection;
        if (select == 0) {
            if (cm.getMeso() >= 1000 && cm.canHold(4031036)) {
                cm.gainMeso(-1000);
                cm.gainItem(4031036,1);
                cm.dispose();
            } else {
                cm.sendOk("#b1000 메소#k는 분명 제대로 갖고 계신건가요? 아니면 인벤토리 공간이 부족한건 아닌지 확인해 주세요.")
                cm.dispose();
            }
        } else if (select == 1) {
            if (cm.getMeso() >= 2000 && cm.canHold(4031037)) {
                cm.gainMeso(-2000);
                cm.gainItem(4031037,1);
                cm.dispose();
            } else {
                cm.sendOk("#b2000 메소#k는 분명 제대로 갖고 계신건가요? 아니면 인벤토리 공간이 부족한건 아닌지 확인해 주세요.")
                cm.dispose();
            }
        } else if (select == 2) {
            if (cm.getMeso() >= 3000 && cm.canHold(4031038)) {
                cm.gainMeso(-3000);
                cm.gainItem(4031038,1);
                cm.dispose();
            } else {
                cm.sendOk("#b3000 메소#k는 분명 제대로 갖고 계신건가요? 아니면 인벤토리 공간이 부족한건 아닌지 확인해 주세요.")
                cm.dispose();
            }
        }
    }
}


