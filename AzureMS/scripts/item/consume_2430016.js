/*
Á¦ÀÛÀÚ : time_amd@nate.com
*/

importPackage(Packages.client.items);

var status = -1;
//var arr = "4001780,2048717,4001715";

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
  cm.sendYesNo("Would you open the Hot Time box? Leave at least 2 spaces");

 } else if (status == 1) {
  if (cm.getPlayer().getInventory(HarmonyInventoryType.EQUIP).getNumFreeSlot() > 2){
   var itemid = arr.split(",")[Math.floor(Math.random()*54+1)/1];
   cm.sendOk("#i" + itemid + "##b(#z"+itemid+"##k) Acquired.");
   cm.gainItem(2431536, -1);
   cm.gainItem(itemid,1);
   cm.dispose();
  } else {
   cm.sendOk("Not enough space in the equipment window");
   cm.dispose();
  }
 }
}
