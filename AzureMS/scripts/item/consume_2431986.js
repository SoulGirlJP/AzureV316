/*
Á¦ÀÛÀÚ : ljw5992@naver.com / dbg_yeane@nate.com
*/

importPackage(Packages.client.items);

var status = -1;
var arr = "2431965,2431966,2431967,2432131,2432153,2432154,2432207,2432354,2432355,2432465,2432479,2432526,2432532,2432592";

function start() {
 action(1, 0, 0);
}

function action(mode, type, selection) {
 if (mode == 1) {
  status++;
 } else {
  status--;
  cm.dispose();
 }
 if (status == 0) {
  cm.sendYesNo("Would you open the damage skin box? Leave at least 1 space");

 } else if (status == 1) {
  if (cm.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() > 1){
   var itemid = arr.split(",")[Math.floor(Math.random()*54+1)/1];
   cm.sendOk("#i" + itemid + "##b(#z"+itemid+"##k) Acquired.");
   cm.gainItem(2431986, -1);
   cm.gainItem(itemid,1);
   cm.dispose();
  } else {
   cm.sendOk("Not enough space in the equipment window");
   cm.dispose();
  }
 }
}
