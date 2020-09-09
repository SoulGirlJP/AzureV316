/*

    퓨어 소스 팩의 스크립트 입니다. (제작 : 엑시즈)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 큐브조각 교환


*/
var status;
var select;
var sel = 0;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var text = "#b#t2431894# #r#c2431894# QTY#k Have Edition Potential Order #r20 QTY#k Master Edition Miracle Cube #r30 QTY#k Is required.\r\n";
                text += "#b#L2048307##i2048307# #z2048307##l\r\n";
		text += "#b#L5062500##i5062500# #z5062500##l\r\n";
           	cm.sendSimple(text);
        } else if(status == 1) {
		sel = selection;
		cm.sendYesNo("Really #b#z" + sel + "##k Do you want to exchange it with?");
	} else if (status == 2) {
		if (!cm.haveItem(2431894, (sel == 2048307 ? 20 : 30))) {
			cm.sendOk("To exchange for the selected item #z" + 2431894 + "# This is lacking.");
			cm.dispose();
			return;
		}
		if (cm.canHold(sel)) {
			cm.gainItem(2431894, -(sel == 2048307 ? 20 : 30));
			cm.gainItem(sel,1);
			cm.sendOk("Exchange is complete with the selected item.");
			cm.dispose();
		} else {
			cm.sendOk("There is not enough space in the inventory.");
			cm.dispose();
			return;
		}
        } else { 
            cm.dispose();
	    return;
        }
    }
}
