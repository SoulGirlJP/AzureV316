

function enter(pi) {
    if (pi.getQuestRecord(70001).getCustomData() == "3") {
        pi.openNpc(9000396,"jobchoice");
	return true;
    } else {
	pi.getPlayer().message("'다미' 에게 먼저 말을 걸어주세요.");
        return false;
    }
}