importPackage(Packages.connections.Packets);
importPackage(Packages.handlers.GlobalHandler);
importPackage(Packages.handlers.CashShopHandler);

// Cash Button UI

function start() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(false));
	cm.getClient().getSession().writeAndFlush(UIPacket.OpenUI(152));
	cm.dispose();
}

function mirrorD_322_0_() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
	cm.dispose();
	cm.openNpc(9000369);
}

function mirrorD_322_1_() {
/*
	if (cm.getPlayer().getMapId() == 123456788) {
		cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
        cm.dispose();
        cm.getClient().setClickedNPC(1000);
        cm.openNpc(2007, "byeSquar");
		return;
	}
*/
        cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
        cm.dispose();
        //cm.getClient().setClickedNPC(1000);
        //cm.openNpc(2007, "warp");
	cm.openNpc(9010095);
}

function mirrorD_322_2_() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
	cm.dispose();
	cm.openNpc(1531010);
}


function mirrorD_322_3_() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
	cm.dispose();
	cm.openNpc(1404015);
}


function mirrorD_323_0_() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
	cm.dispose();
	cm.openNpc(1052210);
}


function mirrorD_323_1_() {
	cm.getClient().getSession().writeAndFlush(UIPacket.NewOnSetMirrorDungeonInfo(true));
	cm.dispose();
	cm.openNpc(9000424);
}

function action(a, b, c) {
	cm.dispose();
}