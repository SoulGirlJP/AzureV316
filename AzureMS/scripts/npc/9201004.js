importPackage(Packages.constants);
var itemid = 4214002;
var status = -1;
var chr;
var sel = 0;
var info = "";
var sel2 = 0;

function start(infot, chrs) {
    status = -1;
	
    if (infot != null) 
	info = infot;

    if (info != "")
	action1 (1, 0, 0, chrs);
    else
    	action (1, 0, 0);
}

function action1(mode, type, selection, chrs) {
	if (chrs != null)
		chr = chrs;

	if (mode == 1) {
		status ++;
	} else {
		cm.dispose();
		return;
	}
	if (status == 0) {
		cm.sendYesNo(info + "\r\n님으로 부터 청혼 신청이 왔습니다. 승낙 할시 바로 결혼식이 시작 됩니다.");
	} else {
		if (cm.getChar(chr.getName()) == null) {
			cm.sendOk(chr.getName() + "님이 현재 이 맵에 존재하지 않습니다.");
			cm.dispose();
			return;
		}
		chr.gainItem(itemid,-1,false,-1,"");
		cm.startMarri(chr.getName());
		cm.dispose();
	}
}

function action(mode, type, selection) {
    if (cm.getPlayer().getMapId() != 680000210) {
    if (mode == 1) {
        status++;
    } else {
	cm.dispose();
	return;
    }
    if (info != "") {
	action1 (1, type, selection, null);
	return;
    }

    if (status == 0) {
	cm.sendSimple("진심으로 사랑하는 사람과 결혼을 해보시겠습니까?\r\n#L0##b결혼식을 진행하고 싶습니다.#l");
    } else if (status == 1) {
	if (ServerConstants.isMarri) {
		cm.sendOk("이미 다른 커플이 결혼식을 진행하고 있습니다. 결혼식이 끝난후 신청해 주시길 바랍니다.");
		cm.dispose();
		return;
	}
	cm.sendGetText("결혼을 하고싶은 상대방의 닉네임을 입력해 주세요.");
    } else if (status == 2) {
	if (cm.getPlayer().getName() == cm.getText()) {
		cm.sendOk("본인과의 결혼이 될거라고 생각 하십니까?");
		cm.dispose();
		return;
	}
	var error = cm.isMarriCheck(cm.getText());
	if (error != "") {
		cm.sendOk(error);
		cm.dispose();
		return;
	}

	if (!cm.haveItem(itemid,1)) {
		cm.sendOk("결혼식을 진행하기 위해선 #i" + itemid + "##b#z" + itemid + "##k이 필요 합니다.");
		cm.dispose();
		return;
	}
	chr = cm.getChar(cm.getText());
	if (chr != null) {
		cm.sendYesNo("#Cgray##e닉네임 : " + chr.getName() + "#n#k\r\n님에게 정말로 청혼 신청을 하시겠습니까?");
	} else {
		cm.sendOk("입력하신 분은 현재 이맵에 계시지 않습니다.");
		cm.dispose();
	}
    } else if (status == 3) {
		cm.sendOk("#Cgray##e닉네임 : " + chr.getName() + "#n#k\r\n님에게 청혼 신청을 하였습니다. 상대방이 청혼을 승낙 할시 곧 바로 결혼식이 진행 됩니다.");
		cm.sendPVP("#Cgray##e닉네임 : " + cm.getPlayer().getName() + "#n#k", cm.getPlayer(), chr, 9201004);
		cm.dispose();
    }
    } else {
    	if (mode == -1) {
        	cm.dispose();
        	return;
    	}
    	if (mode == 0) {
        	status --;
    	}
    	if (mode == 1) {
        	status++;
    	}

   	if (status == 0) {
        	cm.sendOk("진실한 사랑은 모든 것을 가능하게 한답니다.");
       		cm.dispose();
        	return;
    	}
	}
}
