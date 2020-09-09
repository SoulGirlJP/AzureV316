/* Dawnveil
    Maple Castle
    Made by Daenerys
*/
function enter(pi) {
    if (pi.getQuestStatus(13103)==1 && pi.itemQuantity(3994656) < 1){
        pi.warp(910028310,1);
		pi.gainItem(3994656,5);
        return;
   } else {
	    pi.topMsg("You can't enter the Party Hall now.");
	}
    if (pi.getQuestStatus(13107)==1 && pi.itemQuantity(3994660) < 1){
        pi.warp(910028330,1);
		pi.gainItem(3994660,10);
        return;
   }
    if (pi.getQuestStatus(13110)==1 && pi.itemQuantity(3994663) < 1){
        pi.warp(910028350,1);
		pi.gainItem(3994663,1);
        return;
   }
 } 
 