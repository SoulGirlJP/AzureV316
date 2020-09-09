/*
¡¶¿€¿⁄ : ljw5992@naver.com / Harmony_yeane@nate.com
*/

importPackage(Packages.client.items);

var status = -1;
var rand = Math.floor(Math.random()*10);

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
  cm.sendYesNo("Would you open the Hot Time box? Leave 2 or more empty spaces. The chance of winning an item is 30% .11.");
 } else if (status == 1) {
if (rand < 3){
  if (cm.getPlayer().getInventory(HarmonyInventoryType.EQUIP).getNumFreeSlot() > 2){
   cm.sendOk("#i1112594##b(#z1112594##k) Acquired.");
   cm.gainItem(2431420, -1);
   cm.gainItem(itemid,1);
   cm.dispose();
  } else {
   cm.sendOk("Not enough space in the equipment window");
   cm.dispose();
  }
}else{
   cm.sendOk("Whack");
}
 }
}
