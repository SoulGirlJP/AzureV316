/*
제작자 : 백란(vmfhvlfqhwlak@nate.com)
수정자 : 타임(time_amd@nate.com)
*/


var status = -1;

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
  cm.sendYesNo("#b#z2430871##k Do you really want to use?");
 } else if (status == 1) {
cm.gainItem(2430871,-1);//Make the item disappear
cm.teachSkill(80001006,1,1); // Skill cycle
cm.sendOk("Riding skill has been successfully applied."); //Pink scooter
cm.dispose();
}
}