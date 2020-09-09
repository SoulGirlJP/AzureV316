function enter(pi) {
    if (!pi.getInfoQuest(23007).contains("vel00=1") && !pi.getInfoQuest(23007).contains("vel01=1")) {
    	pi.openNpc(2159006);
    }
}