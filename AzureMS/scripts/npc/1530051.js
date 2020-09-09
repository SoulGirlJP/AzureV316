// Boss
var status;
function start() {
    status = -1;
	action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0)
        cm.dispose();
    else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendYesNo("#e<구관을 되찾아주세요!>#n\r\n\r\n#e#dNo.1 #fUI/UIWindow2.img/MobGage/Mob/2700100# 의문의 숙녀#n#k   획득아이템 #i4310125# (1개)\r\n#e#dNo.2 #fUI/UIWindow2.img/MobGage/Mob/2700104# 웅기#n#k              획득아이템 #i4310125# (1~2개)\r\n#e#bNo.3 #fUI/UIWindow2.img/MobGage/Mob/2700103# C.드래곤#n#k       획득아이템 #i4310125# (1~2개)\r\n#e#bNo.4 #fUI/UIWindow2.img/MobGage/Mob/2700101# 반고흥#n#k           획득아이템 #i4310125# (1~3개)\r\n#e#bNo.5 #fUI/UIWindow2.img/MobGage/Mob/2700102# 최박사#n#k           획득아이템 #i4310125# (1~4개)\r\n#e#rNo.F #fUI/UIWindow2.img/MobGage/Mob/2700200# 박록#n#k              획득아이템 #i4310124# (1~5개)\r\n\r\n#e#r이동하시겠습니까?\r\n[파티를구성하지 않으면 입장이 불가능합니다.]\r\n#e#b#n#k#n#k\r\n");
	} else if (status == 1) {
            if (cm.getPlayer().getParty() != null) {
                if (cm.getPlayerCount(330003100) > 0 || cm.getPlayerCount(330003500) > 1 || cm.getPlayerCount(330003400) > 2 || cm.getPlayerCount(330003200) > 3 || cm.getPlayerCount(330003300) > 4 || cm.getPlayerCount(330003600) > 5)  {
                    cm.sendOk("현재 구관에 도전 중인 플레이어가 있습니다.");
                    cm.dispose();
                } else {
		    cm.resetMap(330003100);
                    cm.warpParty(330003100);
                    cm.spawnMob(2700100, cm.getPlayer().getPosition().x + 1000, cm.getPlayer().getPosition().y);
                    cm.dispose();
                }
            } else {
		cm.sendOk("파티를 만들어 주시길 바랍니다.");
		cm.dispose();
	    }
        }
    }
}