


/*

	퓨어 온라인 소스 팩의 스크립트 입니다.

        제작 : 주크블랙

	엔피시아이디 : 
	
	엔피시 이름 :

	엔피시가 있는 맵 : 

	엔피시 설명 : 


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
        if (cm.getProfession(2) != MapleProfessionType.EQUIP.getValue()) {
            cm.getPlayer().message(5, "전문기술이 장비 제작이 아니므로 열 수 없습니다.");
            cm.dispose();
            return;
        }
        cm.openUI(42);
        cm.dispose();
    }
}


