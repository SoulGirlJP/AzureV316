function enter(pi) {
    pi.dispose();
    if (pi.getPlayer().getMapId() == 401060399) {
        pi.openNpc(3001032);
    } else {
        pi.openNpc(3001020);
    }
}