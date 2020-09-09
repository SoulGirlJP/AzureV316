importPackage(Packages.server.quest);

function enter(pi) {
    if (pi.isQuestActive(25100) || pi.isQuestActive(25101)) {
        if (pi.isQuestActive(25101)) {
            MapleQuest.getInstance(25101).forfeit(pi.getPlayer());
        }
        if (pi.isQuestActive(25100)) {
            pi.forceCompleteQuest(25100);
        }
        pi.warp(915010000, "out00");
    } else {
        pi.playerMessage("I have nothing to look there.");
    }
    return true;
}