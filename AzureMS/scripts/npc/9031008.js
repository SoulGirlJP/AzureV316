


/*

	히나 온라인 소스 팩의 스크립트 입니다.

        제작 : 티썬

	엔피시아이디 : 
	
	엔피시 이름 : 힘멜

	엔피시가 있는 맵 : 마이스터 빌


	엔피시 설명 : 비밀광산/약초밭 입장


*/


var status = -1;
importPackage(Packages.client);
function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        if (cm.getProfession(1) == MapleProfessionType.HERBALISM.getValue() && cm.getQuestStatus(3196) < 2) {
            cm.sendYesNo("스타첼의 약초 밭으로 가겠나? 스타첼의 약초채집 강의를 다 들어야만 비밀 농장을 이용할 수 있다네.");
        } else if (cm.getProfession(1) == MapleProfessionType.MINING.getValue() && cm.getQuestStatus(3198) < 2) {
            cm.sendYesNo("노붐의 광산으로 가겠나? 노붐의 채광 강의를 다 들어야만 비밀 농장을 이용할 수 있다네.");
        } else if (cm.getProfession(1) == MapleProfessionType.NONE.getValue()) {
            cm.sendOk("자네.. 아직 전문기술을 배우지 못한 것 같은데? 이곳은 전문기술을 배워야만 들어갈 수 있다네.");
            cm.dispose();
            return;
        } else if (cm.getProfession(1) == MapleProfessionType.HERBALISM.getValue() && cm.getQuestStatus(3196) == 2) {
            cm.sendSimple("어디로 가고 싶나? (제한시간 10분, 하루 무제한 입장 가능)#b\r\n#b#L0##m910001003##l\r\n#L1##m910001004##l\r\n#L2##m910001007##l\r\n#L3##m910001009##l\r\n");
        } else if (cm.getProfession(1) == MapleProfessionType.MINING.getValue() && cm.getQuestStatus(3198) == 2) {
            cm.sendSimple("어디로 가고 싶나? (제한시간 10분, 하루 무제한 입장 가능)#b\r\n#b#L0##m910001005##l\r\n#L1##m910001006##l\r\n#L2##m910001008##l\r\n#L3##m910001010##l\r\n");
        }
    } else if (status == 1) {
        if (cm.getProfession(1) == MapleProfessionType.HERBALISM.getValue() && cm.getQuestStatus(3195) >= 0 && cm.getQuestStatus(3196) < 2) {
            if (cm.getQuestStatus(3195) >= 1) {
                cm.warp(910001001);
                cm.dispose();
            } else {
                cm.sendOk("아직 스타첼의 약초채집 강의를 듣지 못한 것 같군. 강의를 받은 후 다시 찾아오게나.");
                cm.dispose();
            }
        } else if (cm.getProfession(1) == MapleProfessionType.MINING.getValue() && cm.getQuestStatus(3197) >= 0 && cm.getQuestStatus(3198) < 2) {
            if (cm.getQuestStatus(3197) >= 1) {
                cm.warp(910001002);
                cm.dispose();
            } else {
                cm.sendOk("아직 노붐의 채광 강의를 듣지 못한 것 같군. 강의를 받은 후 다시 찾아오게나.");
                cm.dispose();
            }
        } else if (cm.getProfession(1) == MapleProfessionType.HERBALISM.getValue() && cm.getQuestStatus(3196) == 2) {
            if (selection == 0) {
                if (cm.haveItem(4001482, 1)) {
                    cm.gainItem(4001482, -1);
                    cm.timeMoveMap(910001000, 910001003, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001482##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 1) {
                if (cm.haveItem(4001483, 1)) {
                    cm.gainItem(4001483, -1);
                    cm.timeMoveMap(910001000, 910001004, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001483##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 2) {
                if (cm.haveItem(4001570, 1)) {
                    cm.gainItem(4001570, -1);
                    cm.timeMoveMap(910001000, 910001007, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001570##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 3) {
                if (cm.haveItem(4001572, 1)) {
                    cm.gainItem(4001572, -1);
                    cm.timeMoveMap(910001000, 910001009, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001572##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            }
            
        } else if (cm.getProfession(1) == MapleProfessionType.MINING.getValue() && cm.getQuestStatus(3198) == 2) {
            
            if (selection == 0) {
                if (cm.haveItem(4001480, 1)) {
                    cm.gainItem(4001480, -1);
                    cm.timeMoveMap(910001000, 910001005, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001480##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 1) {
                if (cm.haveItem(4001481, 1)) {
                    cm.gainItem(4001481, -1);
                    cm.timeMoveMap(910001000, 910001006, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001481##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 2) {
                if (cm.haveItem(4001569, 1)) {
                    cm.gainItem(4001569, -1);
                    cm.timeMoveMap(910001000, 910001008, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001569##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            } else if (selection == 3) {
                if (cm.haveItem(4001571, 1)) {
                    cm.gainItem(4001571, -1);
                    cm.timeMoveMap(910001000, 910001010, 600);
                } else {
                    cm.sendOk("자네.. #b#t4001571##k은 잘 가지고 있는건가? 다시 한번 확인해보게.");
                    cm.dispose();
                    return;
                }
            }
        }
    }
}


