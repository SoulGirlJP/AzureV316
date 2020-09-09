// Viper Transformation quest

function enter(pi) {
    var pt = pi.getEventManager("KyrinTrainingGroundV");
    if (pt == null) {
	pi.warp(120000101, 0);
    } else {
	if (pt.getInstance("KyrinTrainingGroundV").getTimeLeft() < 120000) { // 2 minutes left
	    pi.warp(912010200, 0);
	} else {
	    pi.playerMessage("Please endure Kyrin's attack for a little while more!");
	    return false;
	}
    }
    return true;
}