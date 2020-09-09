function enter(pi) {
    if (pi.getQuestStatus(20752)==1) 
        {
            pi.forceCompleteQuest(20752);
        }
    else if (pi.getQuestStatus(20755)==1 || pi.getQuestStatus(20756)==1 || pi.getQuestStatus(20757)==1 || pi.getQuestStatus(20757)==2) 
        {
            pi.warp(927010000,"out00");
        }
}