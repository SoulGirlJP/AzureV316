/* @Author Lerk
 * 
 * Guild Quest Waiting Room - Entry Portal (map 990000000)
 */

function enter(pi) {
    if (pi.getEventInstance() == null) {
	pi.warp(101030104);
    } else {
	if (pi.getEventInstance().getProperty("canEnter") != null && pi.getEventInstance().getProperty("canEnter").equals("true")) {
	    pi.warp(990000100);
	} else { //cannot proceed while allies can still enter
	    pi.playerMessage("The portal is not open yet.");
		return false;
	}
    }
	return true;
}