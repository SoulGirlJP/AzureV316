/* Dawnveil
    Maple Castle
    Made by Daenerys
*/
function enter(pi) {
    if (pi.getQuestStatus(13109)==1){
        pi.warp(130000000,0);
        return;
   } else {
	    pi.warp(100000000, 1);
	}
}