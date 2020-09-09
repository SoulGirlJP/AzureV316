function enter(pi) {
try {
    var em = pi.getEventManager("CWKPQ");
    if (em != null) {
	if (em.getProperty("glpq6") == null || !em.getProperty("glpq6").equals("3") || pi.getPlayer().getEventInstance() == null || !pi.getPlayer().getEventInstance().getName().startsWith("CWKPQ")){
	    pi.playerMessage("The portal is not opened yet.");
	} else {
	    pi.warp(610030700, 0);
	}
    }
} catch(e) {
    pi.playerMessage("Error: " + e);
}
}