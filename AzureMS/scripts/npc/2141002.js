


/*
 
 * 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.
 
 * (Aeos Development Source Script
 
 5로로 에 의해 만들어 졌습니다.
 
 엔피시아이디 : 2141002
 
 엔피시 이름 : 잊혀진 신전관리인
 
 엔피시가 있는 맵 : 신들의 황혼
 
 엔피시 설명 : MISSINGNO
 
 
 */

var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
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
        cm.sendYesNo("원정대를 마치고 밖으로 나가시겠습니까?");
    } else if (status == 1) {
        if (cm.getPlayer().getParty().getLeader().getId() != cm.getPlayer().getId()) {
            if (cm.getPlayer().getMapId() == 270050100) {
                cm.warp(270050300);
            } else {
                cm.warp(270051300);
            }
        } else {
            if (cm.getPlayer().getMapId() == 270050100) {
                cm.removeNpc(cm.getMapId(), 2141000);
                cm.mapMessage(5, "원정대장이 도전을 포기하거나 완료하고 퇴장을 선언하여. 모든 파티원이 제단에서 퇴장 하였습니다.");
                cm.allPartyWarp(270050300, true);
                cm.resetMap(270050200);
                cm.resetMap(270050100);
                cm.resetMap(270050000);
            } else {
                cm.removeNpc(cm.getMapId(), 2141000);
                cm.mapMessage(5, "원정대장이 도전을 포기하거나 완료하고 퇴장을 선언하여. 모든 파티원이 제단에서 퇴장 하였습니다.");
                cm.allPartyWarp(270051300, true);
                cm.resetMap(270051200);
                cm.resetMap(270051100);
                cm.resetMap(270050000);
            }
        }
        cm.dispose();
    }

}
