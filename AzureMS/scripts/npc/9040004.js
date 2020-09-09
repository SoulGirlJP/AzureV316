/*
    
    엔피시 이름 : 명예의 바위

    엔피시 설명 : 길드 랭킹

*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.displayGuildRanks();
        cm.dispose();
        return;
    }
}
