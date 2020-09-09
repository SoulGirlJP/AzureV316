
var status = 0;
var points;
var sel;
var sel2;
var itemList = Array (1002357,1003112,1002971,1052202,1003621,1052526,1003622,1052527,1122000,1122076,1402179,1402180); // 아이템 목록 
var number = Array (50,80,60,60,1000,1000,1500,1500,1000,2000,3000,5000);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    } else {
        if (status <= 2 && mode == 0) {
            cm.dispose();
            return;
        }  
        if (mode == 0) {
            cm.dispose();
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
             cm.sendSimple("어떻습니까?\r\n\r\n저와 함게 아니를 퇴치하러 가시지 않겠습니까?\r\n\r\n#r다만 되돌아 올때는 지금 있는 곳과 가장 가까운 마을로 이동하게되니 주의 하십시오.#k\r\n#L0##b아니를 퇴치하러 가겠습니다.")
            if (selection == 0) {
		if (cm.getClient().getChannelServer().getMapFactory().getMap(921133000).getCharactersSize() > 0) {
             cm.sendOk("이미, 10명의 모험가들이 보스몬스터를 퇴치하러 입장했습니다.\r\n다른채널을 이용해주시거나 잠시후에 도전해주십시오.");
             cm.dispose();
 	            return;
  		  }
  		 try {
                var em = cm.getEventManager("BossAni");
                em.startInstance(cm.getPlayer());
                cm.resetMap(921133000);
                cm.allPartyWarp(921133000, true);
                cm.dispose();
            } catch (err) {
                cm.sendOk(err);
            }
}
        }
    }
}