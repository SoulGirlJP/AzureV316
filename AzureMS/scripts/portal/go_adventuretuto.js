/* RED 1st impact
    Explorer tut
    Made by Daenerys
*/
function enter(pi) {
	if (pi.getQuestStatus(32204)==2 && pi.getQuestStatus(32207)==0){
        pi.warp(4000013, 0);
        return;
   } else {
	    pi.openNpc(10310, "ExplorerTut01");
	}
	if (pi.getQuestStatus(32207)==2 && pi.getQuestStatus(32210)==0){
        pi.warp(4000014, 0);	
        return;
   } else {
	    pi.openNpc(10310, "ExplorerTut02");
	}	
    if (pi.getQuestStatus(32210)==1){
        pi.warp(4000020, 0);	
        return;
   } else {
	    pi.openNpc(10310, "ExplorerTut03");
	}	
  }
 