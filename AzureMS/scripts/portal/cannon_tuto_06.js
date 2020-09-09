function enter(pi) {
    pi.dispose();
    pi.EnableUI(0);
    pi.sendDirectionStatus(4,1096003);
    pi.sendDirectionStatus(3,4);
    pi.openNpc(1096003);
}