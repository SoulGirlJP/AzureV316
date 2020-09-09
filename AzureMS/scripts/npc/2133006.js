// Boss

importPackage (Packages.server.quest);

var status = -1;

function start() {
status = -1;
action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
	cm.dispose();
	} else {
	if (mode == 0) {
	cm.sendOk("위험한 곳이긴 하지만 입장은 언제든지 가능합니다.");
	cm.dispose();            
	return;        
	}        
	if (mode == 1)            
	status++;        
	else           
	status--;    

	if (status == 0) {
		if(cm.getQuestStatus(31229) != 0) {
	cm.sendYesNo("이 곳은 에피네아의 은신처입니다.\r\n허락받지 못한 자에게는 매우 위험한 곳입니다.\r\n그래도 입장하시겠습니까?");
		} else {
	cm.getPlayer().dropMessage(5, "아직은 들어갈 수 없다.");
		}
	} else if (status == 1) {
	cm.dispose();
	cm.warp(300030310, 0);
	cm.killAllMob();
	cm.spawnMob(5250007, 58, 150);
	}

}
}