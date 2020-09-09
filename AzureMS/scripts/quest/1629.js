var status = -1;

function start(mode, type, selection) {
    qm.forceStartQuest();
    qm.warp(931050418);
    qm.spawnMonster(9300478,1,755,171);
    qm.dispose();
}
function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}
