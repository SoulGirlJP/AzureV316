function enter(pi) {
	if (pi.getQeustStatus(22008) == 1){
		pi.warp(100030103);
	} else {
		pi.playerMessage(0, "You cannot go to the Back Yard without a reason");
    	} 
	return true;
}  