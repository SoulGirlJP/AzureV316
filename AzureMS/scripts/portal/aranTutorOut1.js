function enter(pi) {
    if (pi.getQuestStatus(21000) == 0) {
	pi.playerMessage(5, "You can only exit after you accept the quest from Athena Pierce, who is to your right.");
    } else {
	pi.teachSkill(20000017,0,-1);
	pi.teachSkill(20000018,0,-1);
	pi.teachSkill(20000017,1,0);
	pi.teachSkill(20000018,1,0);
	pi.playPortalSE();
	pi.warp(914000200,1);
	pi.send(MainPacketCreator.showEffect("aran/tutorialGuide1/0"));
    }
}