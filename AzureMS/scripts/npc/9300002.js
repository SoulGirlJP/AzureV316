


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	리플러스 에 의해 만들어 졌습니다.

	엔피시아이디 : 9300002

	엔피시 이름 : 오장법사

	엔피시가 있는 맵 :  :  (0)

	엔피시 설명 : MISSINGNO
*/

status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

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
            if (cm.getClient().getChannelServer().getMapFactory().getMap(0).getCharactersSize() > 0) {
                cm.sendOk("#b(이미 다른 헌터가 헌팅중인거 같다.)");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getKeyValue("루돌프입장") != null) {
                cm.sendOk("하루 한번입장가능. 어제하셨으면 출석체크후 다시입장해주세요.");
                cm.dispose();
                return;
            }
cm.getPlayer().setKeyValue("루돌프입장", "끗")
cm.sendOk("야동");
cm.TimeMoveMap(0,100000000,300);
cm.getPlayer().getMap().spawnMonsterOnGroundBelow(Packages.server.life.MapleLifeProvider.getMonster(9300808), new java.awt.Point(50, 97));
cm.sendOk("5분동안 때리시고 @저장후 제접한번 하시면 랭킹시스템에 저장됩니다. 그리고 도중에 나가시면 다음입장불가능합니다.꼭 끝까지 깨세요");
        cm.dispose();
        return;
    }
cm.sendOk("5분동안 때리시고 @저장후 제접한번 하시면 랭킹시스템에 저장됩니다.");
cm.dispose();
}

