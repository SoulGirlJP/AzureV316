/*
제작자 : 백란(vmfhvlfqhwlak@nate.com);
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
  cm.sendYesNo("#b#z2432031##k Do you really want to use?");
 } else if (status == 1) {
cm.gainItem(2432031,-1);// MAke the item dissapear
cm.teachSkill(80001348,1,1); // Skill Cycle
cm.sendOk("Riding skill has been successfully applied.");
cm.dispose();
}
}