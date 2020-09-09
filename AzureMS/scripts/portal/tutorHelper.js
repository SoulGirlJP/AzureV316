function enter(pi) {
    if (pi.getQuestStatus(20021) == 0) {
	pi.playerSummonHint(true);
	pi.summonMsg("Welcome to the world of Maple! My name is Koo, and I will be your guide! I will be here to answer your questions and guide you until you reach Level 10 and become a Knight-in-Training. If you have any questions, double-click me!");
//	pi.forceCompleteQuest(20100);
	pi.forceCompleteQuest(20021);
    }
}