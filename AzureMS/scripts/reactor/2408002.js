/*
 혼테일 파티퀘스트 열쇠 워프
 제작 : 주크블랙
*/
importPackage(Packages.client.items);
importPackage(Packages.packet.creators);
function act() {
    var eim = rm.getPlayer().getEventInstance();
    var mapid = rm.getPlayer().getMapId();
    var map = eim.getMapFactory().getMap(240050100);
    if (mapid == 240050101) { //첫번째 미로방
        var drop = new Item (4001088, 0, 1, 0);
        map.spawnItemDrop(map.getReactor(2402002), rm.getPlayer(), drop, map.getReactor(2402002).getPosition(), false, false);
        map.getReactor(2402002).setState(1);
        eim.addAchievementRatio(5);
        rm.mapMessage(6, "열쇠가 어디론가 사라졌습니다.");
        map.broadcastMessage(MainPacketCreator.serverNotice(6, "반짝이는 빛과 함께 어딘가에서 열쇠가 나타났습니다."));
    } else if (mapid == 240050102) { //두번째 미로방
        var drop = new Item (4001089, 0, 1, 0);
        map.spawnItemDrop(map.getReactor(2402002), rm.getPlayer(), drop, map.getReactor(2402002).getPosition(), false, false);
        map.getReactor(2402002).setState(0);
        eim.addAchievementRatio(5);
        rm.mapMessage(6, "열쇠가 어디론가 사라졌습니다.");
        map.broadcastMessage(MainPacketCreator.serverNotice(6, "반짝이는 빛과 함께 어딘가에서 열쇠가 나타났습니다."));
    } else if (mapid == 240050103) { //세번째 미로방
        var drop = new Item (4001090, 0, 1, 0);
        map.spawnItemDrop(map.getReactor(2402002), rm.getPlayer(), drop, map.getReactor(2402002).getPosition(), false, false);
        map.getReactor(2402002).setState(1);
        eim.addAchievementRatio(5);
        rm.mapMessage(6, "열쇠가 어디론가 사라졌습니다.");
        map.broadcastMessage(MainPacketCreator.serverNotice(6, "반짝이는 빛과 함께 어딘가에서 열쇠가 나타났습니다."));
    } else if (mapid == 240050104) { //네번째 미로방
        var drop = new Item (4001091, 0, 1, 0);
        map.spawnItemDrop(map.getReactor(2402002), rm.getPlayer(), drop, map.getReactor(2402002).getPosition(), false, false);
        map.getReactor(2402002).setState(0);
        eim.addAchievementRatio(5);
        rm.mapMessage(6, "열쇠가 어디론가 사라졌습니다.");
        map.broadcastMessage(MainPacketCreator.serverNotice(6, "반짝이는 빛과 함께 어딘가에서 열쇠가 나타났습니다."));
    }
    
}
	
	