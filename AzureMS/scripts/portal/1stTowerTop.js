function enter(pi) {
    if (pi.getQuestStatus(3164) == 1) {
	pi.forceCompleteQuest(3164);
	pi.playerMessage("퀘스트완료");
    }
    pi.warp(211060201,0);
}