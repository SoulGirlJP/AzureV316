function enter(pi) {
        pi.playerMessage("물에 빠져 입구로 돌아갑니다.");
        pi.warp(pi.getPlayer().getMapId(), "st00");
        //pi.dispose();
        return false;
}