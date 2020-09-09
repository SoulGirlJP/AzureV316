importPackage(Packages.packet.creators);

var status = -1;

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
	cm.sendSimple_("지금 부터 점령전에 대한 설명을 해 드릴테니 잘 들어주시기 바랍니다.\r\n#L0##b#e설명을 듣는다#l");
    } else if (status == 1) {
	cm.sendSimple_("점령전은 필드의 몬스터를 모두 처치하고나면 거대한 목마가 나타납니다. 목마를 처치하고나면 다음 스테이지로 이동이가능 하며 파이널 스테이지까지 클리어 하게되면 점령전이 종료됩니다.\r\n#L0##b#e점령전을 시작한다.#l");
    } else if (status == 2) {
	cm.getPlayer().getMap().respawn(true);
	cm.getPlayer().getMap().broadcastMessage(UIPacket.clearMidMsg());
	cm.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect("monsterPark/stageEff/stage"));
	cm.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect("monsterPark/stageEff/number/" + 1));
	cm.dispose();
    }
}
