/* Dawnveil
    Ellinel Fairy Academy
    Made by Daenerys
*/
function enter(pi) {
  if (pi.getQuestStatus(32102)==1){
    pi.gainItem(4033824,30);
	pi.warp(101070001,0);
	pi.forceCompleteQuest(32102);
    return true;
  } else {
    pi.warp(101071000,0);
  }
} 