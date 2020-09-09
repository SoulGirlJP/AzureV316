// We don't use shammosRevive afaik.
function enter(pi) {
    if (pi.getPlayer().getEventInstance() != null) {
    	pi.gainExp_PQ(200, 1.5);
		pi.addTrait("will", 15);
		pi.addTrait("insight", 3);
		if (pi.canHold(4001530, 1) && pi.isGMS()) { //TODO JUMP
			pi.gainItem(4001530, 1);
		}
    }
    pi.warp(211000002,0);
}