function enter(pi) {
    if (pi.getQuestStatus(20754)==1) {
        pi.warp(913060000,"out00");
        return;
    } 
    if (pi.getQuestStatus(20320)==1){
        pi.warp(913070200,1);
        return;
    }
    if (pi.getQuestStatus(20411)==1){
        pi.warp(913070100,1);
        return;
    }
}