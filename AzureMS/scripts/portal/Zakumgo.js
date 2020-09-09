function enter(pi) {
    if (pi.getClient().getChannel() % 2 == 1) { //카오스 입장
        pi.warp(211042301,1);
    } else { //노멀 입장
        pi.warp(211042300,1);
    }
    return true;
}