function enter(pi) {
    if (pi.getQuestStatus(25111)==1) {
        pi.warp(915010100 ,1);
    } 
    else if (pi.getQuestStatus(25111)==2) {
        pi.warp(915010100,1);
        pi.spawnMonster(9001046,7,171,182);
    }
}