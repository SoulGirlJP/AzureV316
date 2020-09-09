importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var status = -1;
var mapiamaps = [234567899, 234567890,234567891,234567892,234567893,234567894,234567895,234567896,234567897,234567898];
function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0 && status == 0) {
	cm.dispose();
	return;sd
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {	
	//cm.sendOk("마피아 손좀보게 잠시만 기달"); cm.dispose(); return;
	if(cm.getPlayer().getMapId() == 310000004) {
	var text = "#fs11##e< 컨텐츠 : 마피아 >#n\r\n안녕하십니까, 여러분. 저는 이 저택의 집사 #b가면신사#k 라고 합니다. 이 저택 내부엔 거대한 게임장이 존재합니다. 바로 #b마피아 게임장#k 이죠. 한번 #b마피아 게임장#k 을 이용해 보시지 않겠습니까?\r\n\r\n#e- 참여조건 : 10 레벨 이상 -\r\n- 참여 인원 : 5명 ~ 10명 (파티 X)#n";
	if(!cm.getClient().getChannelServer().getMapFactory().getMap(234567899).MapiaIng) {
		text += "\r\n#b#L0#마피아 게임이 무엇이죠?\r\n#b#L1#마피아 게임을 이용 해보겠습니다. (현재 이용 가능)";
	} else {
		text += "\r\n#b#L0#마피아 게임이 무엇이죠?\r\n#r#L1#마피아 게임을 이용 해보겠습니다. (현재 이용 불가능)#k";
	}
	cm.sendSimple(text);
	} else {
	cm.warp(234567888); cm.dispose(); return;
	}
    } else if(status == 1) {
	sel = selection;
	if(selection == 0) {
	var text = "#e마피아 게임이란 ?#n\r\n#fs11#마피아 게임은 1986년 모스크바 대학교의 심리학부 교수 디마 다비도프가 창안하였으며, 현재까지 오면서 규칙이 많이 바뀌었겠지만";
	text += "현재 저희 #b워프스텔라#k의 룰로 의하면 다음과 같습니다.\r\n";
	text += "#b10명#k을 기준으로 #b시민 4명, 의사 1명, 경찰 2명, 마피아 3명#k 입니다.\r\n";
	text += "#b시민#k의 역할은 낮이되면 대화를 통해 마피아를 찾아내 투표를 유도하여 처형 시켜 #b마피아를 모두 처형#k시키면 게임에서 승리합니다. 다만, 시민은 밤에 아무 능력도 없습니다.\r\n";
	text += "#b마피아#k의 역할은 낮에 자신이 마피아라는걸 들키면 안되며, 밤이되면 #b시민들을 모두 암살#k시키면 게임에서 마피아가 승리합니다.\r\n";
	text += "#b의사#k는 마피아가 암살하려는 #b사람을 밤마다 살릴 수 있으며#k, 마피아에게 자신이 의사라는 사실도 들키면 불리해집니다.\r\n";
	text += "#b경찰#k은 마피아가 맞는지 확실이 확인하기 위해 존재하며, 밤에는 마피아같은 사람을 지목하면 #b마피아 인지 아닌지#k를 알 수 있습니다.";
	cm.sendOk(text); cm.dispose(); return;
	} else if(selection == 1) {
	if(cm.getClient().getChannelServer().getMapFactory().getMap(234567899).MapiaIng) {
		cm.sendOk("지금은 마피아를 진행하실 수 없습니다. 잠시 후 다시 시도해 주세요."); cm.dispose(); return;
	}
	cm.sendYesNo("현재 마피아를 하기위해 기다리고 있는 사람은 "+cm.getPlayerCount(cm.getPlayer().getMapId())+" 명 입니다. 게임을 시작하겠습니까?");
	} 
   } else if(status == 2) {
	for(i=0;i<mapiamaps.length;i++) {
		if(cm.getPlayerCount(mapiamaps[i]) > 0) {
			cm.sendOk("현재 마피아를 이용하고 있는 사람이 있습니다. 다시 시도해 주세요."); cm.dispose(); return;
		}
	}
	if(cm.getClient().getChannelServer().getMapFactory().getMap(234567899).MapiaIng) {
		cm.sendOk("지금은 마피아를 진행하실 수 없습니다. 잠시 후 다시 시도해 주세요."); cm.dispose(); return;
	}
	if(cm.getPlayerCount(cm.getPlayer().getMapId()) > 4 && cm.getPlayerCount(cm.getPlayer().getMapId()) < 11) { 
		cm.MapiaStart(cm.getPlayer(), 5, 234567899, 234567890,234567891,234567892,234567893,234567894,234567895,234567896,234567897,234567898, 120,45,30);
	if(cm.getPlayer().getClient().getChannel() == 1) {
	WorldBroadcasting.broadcast(MainPacketCreator.getGMText(10, "[마피아 알림] "+20+"세이상 채널에서 게임에 입장하였습니다."));
	} else {
	WorldBroadcasting.broadcast(MainPacketCreator.getGMText(10, "[마피아 알림] "+-(-cm.getPlayer().getClient().getChannel()-1)+" 채널에서 게임에 입장하였습니다."));
	}
		cm.dispose(); return;
	} else {
		cm.sendOk("최소 5명에서 최대 10명까지 입장 가능합니다.");
		cm.dispose(); return;
	}
   }
}