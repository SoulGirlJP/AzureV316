
importPackage(Packages.packet.creators);
importPackage(Packages.server.life);
importPackage(java.lang);
importPackage(java.awt);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.launch.world);



/*

	퓨어 온라인 소스 스크립트입니다.

	파티퀘스트


        설정되는 Property : 
        Global_StartMap : 시작 맵
        Global_ExitMap  : 나가기맵
        Global_MinPerson: 최소인원
        Global_RewardMap: clearPQ 메소드로 갈 보상 맵.

        입장 엔피시로 해줘야 할것 : 
        var eim = em.readyInstance();
        eim.setProperty("Global_StartMap", [시작맵]+"");
        eim.setProperty("Global_ExitMap", [나가기맵]+"");
        eim.setProperty("Global_MinPerson", [최소인원]+"");
        eim.setProperty("Global_RewardMap", [보상맵]+"");
        eim.startEventTimer([제한시간]);
        eim.registerParty([PureParty 타입], [PureMap 타입]);
*/

function init() {
    
}

function setup(eim) {
    var a = Randomizer.nextInt();
    while (em.getInstance("MonsterPark_ID"+a) != null) {
        a = Randomizer.nextInt();
    }
    var eim = em.newInstance("MonsterPark_ID"+a);
    return eim;
}

function playerEntry(eim, player) {
    var map = Integer.parseInt(eim.getProperty("Global_StartMap"));
    player.changeMap(eim.getMapFactory().getMap(map), eim.getMapFactory().getMap(map).getPortal("sp"));
    player.message(6, "[Notification] You have entered a Monster Park instance. good luck! :)");
}



function changedMap(eim, player, mapid) {
    
}

function scheduledTimeout(eim) {
    
    var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        if (chr == null) {
            System.out.println("Character NULL!");
        }
        if (exit == null) {
            System.out.println("EXIT Map NULL!");
        }
        if (exit.getPortal("sp") == null) {
            System.out.println("EXIT Map Portal NULL!");
        }
        chr.changeMap(exit, exit.getPortal("sp"));
        chr.Message(8, "Timed out Please start again from the beginning.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function allMonstersDead(eim) {
    var startmap = Integer.parseInt(eim.getProperty("Global_StartMap"));
    var curstage = Integer.parseInt(eim.getProperty("CurrentStage"));
    var curmap = (startmap + ((curstage - 1) * 100));
    var map = eim.getMapFactory().getMap(curmap);
    if (curstage < 6) {
        map.broadcastMessage(MainPacketCreator.showEffect("monsterPark/clear"));
    } else {
        map.broadcastMessage(MainPacketCreator.showEffect("monsterPark/clearF"));
    }
    map.broadcastMessage(MainPacketCreator.playSound("Party1/Clear"));
    eim.setProperty("CurrentStage", (curstage + 1)+"");
}

function playerDead(eim, player) {
    return 0;
}

function playerRevive(eim, player) {
    
}

function playerDisconnected(eim, player) {
    /* 0 : 모두 나갈때 까지는 인스턴스 유지
     * 1 ~ : 일정 수준 이상의 사람만 남으면 누가 나가던지 인스턴스 유지
     * -1 ~ 이하 : 일정 수준 이상의 사람만 남으면 유지이나, 파티장이 나가면 인스턴스 삭제
     */
    if (eim.getProperty("Global_MinPerson") == null) {
        return 0;
    }
    return -Integer.parseInt(eim.getProperty("Global_MinPerson"));
}

function monsterValue(eim, mobid) {
    return 1;
}

function leftParty(eim, player) {
    if (eim.getPlayerCount() < Integer.parseInt(eim.getProperty("Global_MinPerson"))) {
        var exit = em.getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
        var it = eim.getPlayers().iterator();
        while (it.hasNext()) {
            var chr = it.next();
            chr.changeMap(exit, exit.getPortal(0));
            chr.Message("The party member has left the party and can no longer proceed with the quest.");
        }
        eim.unregisterAll();
        if (eim != null) {
            eim.dispose();
        }
    }
    
}

function disbandParty(eim) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("The party leader has left the party and can no longer proceed with the quest.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function clearPQ(eim) {
    
}

function playerExit(eim, player) {
    var exit = eim.getPlayers().get(0).getClient().getChannelServer().getMapFactory().getMap(Integer.parseInt(eim.getProperty("Global_ExitMap")));
    var it = eim.getPlayers().iterator();
    while (it.hasNext()) {
        var chr = it.next();
        chr.changeMap(exit, exit.getPortal(0));
        chr.Message("You can't proceed anymore because you've given up a party quest.");
    }
    eim.unregisterAll();
    if (eim != null) {
	eim.dispose();
    }
}

function onMapLoad(eim, player) {
    
}

function cancelSchedule(a) {
    
}