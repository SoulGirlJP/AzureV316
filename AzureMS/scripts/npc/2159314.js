var status = -1;var jagure = Array(9304000,9304001,9304002,9304003,9304004,9304005,9304006);
var i = 0;
var k1 = "#fNpc/9072201/stand/0#";
function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
	cm.dispose();
	return;
    }
    if (selection > 2) {
		cm.setJagure(selection);
		cm.sendOk("선택한 재규어로 변경이 완료 되었으니 한번 확인해봐!");
		cm.dispose();
    } else {
	if (selection == 1) {
		if (i == 0)
			i = 6;
		 else
			i --;
		
	} else if (selection == 2) {
		if (i == 6) 
			i = 0;
		 else 
			i ++;
	}
	var chat = "원하는 재규어를 선택해봐!\r\n\r\n";
	chat += "#L1##e<#n #l             #fMob/" + jagure[i] + "/stand/0#         #L2##e>#n#l";
	chat += "\r\n#k#e				#o" + jagure[i] + "##n#b";
	chat += "\r\n#L" + jagure[i] + "# #b이 재규어로 변경 하겠습니다.#l";
	cm.sendSimple(chat);
	//cm.dispose();
    }
}
