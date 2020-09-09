var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendYesNoS("V 코어 조각을 사용하여 코어젬스톤을 제작하시겠소?\r\n\r\n#b코어젬스톤 제작에 필요한 V 코어 조각 개수 : 45\r\n현재 보유한 V 코어 조각 개수 : " + cm.getPlayer().getCoreq(), 4);
    } else {
        if (cm.getPlayer().getCoreq() < 45) {
            cm.sendOkS("코어젬스톤을 제작하기 위해서는 45개의 V 코어 조각이 필요하오. 필요 없는 코어를 분해해서 V 코어 조각을 모은 뒤 시도하시오.", 4);
            cm.dispose();
            return;
        }
        if (!cm.canHold(2435719)) {
            cm.sendOkS("코어젬스톤을 제작하기 위해서는 1칸 이상의 소비칸이 필요하오.", 4);
            cm.dispose();
            return;
        }
        cm.getPlayer().gainCoreq(-45);
        cm.gainItem(2435719, 1);
        cm.sendOkS("V 코어 조각을 #b45개#k 사용하여 코어젬스톤을 만들었소. 인벤토리를 확인해 보시오.", 4);
    }
}