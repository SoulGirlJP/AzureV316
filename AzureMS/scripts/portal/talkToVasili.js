/* RED 1st impact
    Explorer tut
    Made by Daenerys
*/
function enter(pi) {
    if (pi.getQuestStatus(32214)==1){
        pi.openNpc(10305, "ExplorerTut04");
     }
    if (pi.getQuestStatus(32214)==2){
        pi.openNpc(10305, "ExplorerTut05");
    } else {
        pi.openNpc(10305);
  }
}
