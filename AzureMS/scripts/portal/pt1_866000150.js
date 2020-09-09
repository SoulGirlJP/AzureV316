/* Return to Masteria
    BeastTamer Quest line
    Made by Daenerys
*/
function enter(pi) {
    if (pi.getQuestStatus(59052)==1){
        pi.warp(866033000,0);
        return;
   } else {
	    pi.topMsg("The path to the Kobold King's Room is blocked. It's also... really, really stinky here. Talk to Woodrock again.");
		pi.forceCompleteQuest(59049);
		pi.warp(866000000,0);
   }
}