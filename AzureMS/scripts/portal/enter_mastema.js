function enter(pi) {
    if (pi.getQuestStatus(23210)==1) pi.warp(931050100,1);
    else if (pi.getQuestStatus(23213)==1) pi.warp(931050110,1);
    else if (pi.getQuestStatus(23214)==2 && pi.getQuestStatus(23215)!=1 && pi.getQuestStatus(23215)!=2 && pi.getPlayer().getLevel()>99) {
        pi.forceStartQuest(23215);
        pi.warp(220050300);
    }
}