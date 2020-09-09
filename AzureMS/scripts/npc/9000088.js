
/* 마이스터 빌 */


var status = -1;

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
        if (cm.getPlayer().getLevel() < 30) {
            cm.getPlayer().message(5, "#b<마이스터 빌>#k은 레벨 30이상만 입장 가능합니다.");
            return false;
        }
        cm.sendYesNo("전문기술의 마을 #b<마이스터 빌>#k로 이동하시고 싶으세요?");
    } else if (status == 1) {
        cm.saveLocation("PROFESSION");
        cm.warp(910001000, 0);
        cm.dispose();
    }
}


