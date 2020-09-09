function enter(pi) {
    if (Math.floor(pi.getPlayer().getMapId()/100)%2 == 0) {
        pi.openNpc(2083002, "HtailPass1");
    } else {
        pi.openNpc(2083002, "HtailPass2");
    }
}