var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) 
	status++;
    else 
	status--;
    if (status == 0) {
	if (cm.getEventInstance().getProperty("MonsterParkExtreme").equals("1")) {
	    cm.sendYesNo("#fn나눔고딕 Extrabold#이 정도면 보스를 소환하기에 충분한 것 같네요.\r\n지금 보스를 소환하시면 몬스터가 모두 사라지고\r\n더 이상 몬스터가 #r리젠#k 이 되지 않는답니다.\r\n\r\n#b지금 바로 보스를 소환하시겠어요?#k");
	} else {
	    cm.sendOk("#fn나눔고딕 Extrabold##r아직은 보스 소환을 위한 시간이 부족해요..\r\n조금만 더 힘을 내주세요.#k");
	    cm.dispose();
	}
    } else if (status == 1) {
	if (cm.getQuestStatus(100001) == 1) {
	    var x = [754,1690,-316,-2957,53,1333,405,-1006];
	    var y = [66,220,-387,776,167,30,-297,-16];
	    var mobids = [9801002,9801005,9801009,9801012,9801015,9801018,9801023,9801026];
	    var mapids = [951000200,951000210,951000220,951000230,951000240,951000250,951000260,951000270];
	    cm.killAllMob();
            cm.getPlayer().getMap().respawn(false,0);
	    for (i = 0; i < mobids.length; i++) {
		if (cm.getMapId() == mapids[i]) {
		    cm.spawnMob(mobids[i],x[i],y[i]);
		    cm.forcePartyCompleteQuest(100001);
                    cm.dispose();
		    return;
		}
	    }
	} else {
	   cm.sendOk("#fn나눔고딕 Extrabold##r더 이상 소환될 보스가 없는 것 같네요.#k");
           cm.dispose();
	}
	cm.dispose();
    }
}