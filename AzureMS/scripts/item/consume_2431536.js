/*
제작자 : 백 란 (vmfhvlfqhwlak@nate.com)
*/

importPackage(Packages.client.items);

var status = -1;
var arr = "2430053,2430091,2430093,2430149,2430264,2430610,2430728,2430938,2430968,2431490,2431502,2431799,2431915,2431949,2432008,2432015,2432031,2432216,2432219,2432291,2432295,2432309,2432328,2432359,242361";

function start() {
 action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
 if (status == 0) {
  cm.sendYesNo("#b#z2431536##kOpen it?\r\n#rI hope you get a good ride.#k");
} else if (status == 1) {
  if (cm.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() > 1){
   var itemid = arr.split(",")[Math.floor(Math.random()*54+1)/1];
   cm.sendOk("#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i" + itemid + "##b#z"+itemid+"##k");
   cm.gainItem(2431536, -1);
   cm.gainItem(itemid,1);
   cm.dispose();
  } else {
   cm.sendOk("Looks like there's no room in the sovican?");
   cm.dispose();
  }
 }
}