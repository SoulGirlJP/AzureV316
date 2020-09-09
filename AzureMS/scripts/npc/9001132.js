importPackage(Packages.constants);

var status = -1;

º°º¸ = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"

function start() {
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var chat = "#fnSharing Ghotic ExtraBold# Welcome to overseas cash drawer><:)\r\n\r\n\r\n";
        chat += "#L1#"+"#r#e[NEW]#k#dOverseas Cape#k I will use.\r\n";
        chat += "#L2#"+"#r#e[NEW]#k#dOverseas Suit#k I will use.\r\n";
        chat += "#L3#"+"#r#e[NEW]#k#dOverseas draw#k I will use.\r\n";
        chat += "#L4#"+"#r#e[NEW]#k#dOverseas Bottom#k I will use.\r\n";
        chat += "#L5#"+"#r#e[NEW]#k#dOverseas Weapons#k I will use.\r\n";
        chat += "#L6#"+"#r#e[NEW]#k#dOverseas Access#k I will use.\r\n";
        chat += "#L7#"+"#r#e[NEW]#k#dOverseas Cloak#k I will use.\r\n";
        chat += "#L8#"+"#r#e[NEW]#k#dOversea Gloves#k I will use.\r\n";
        chat += "#L9#"+"#r#e[NEW]#k#dOverseas Shoes#k I will use.\r\n";
        cm.sendSimple(chat);

    } else if (status == 1) {
    if (selection == 1) {
       cm.dispose();
       cm.openNpc(9010067);

    } else if (selection == 2) {
        cm.dispose();
        cm.openNpc(1052236);

    } else if (selection == 3) {
        cm.dispose();
        cm.openNpc(1052233);

    } else if (selection == 4) {
        cm.dispose();
        cm.openNpc(1052235);

    } else if (selection == 5) {
        cm.dispose();
        cm.openNpc(1052232);

    } else if (selection == 6) {
        cm.dispose();
        cm.openNpc(2192032);

    } else if (selection == 7) {
        cm.dispose();
        cm.openNpc(2131003);

    } else if (selection == 8) {
        cm.dispose();
        cm.openNpc(1052237);

    } else if (selection == 9) {
        cm.dispose();
        cm.openNpc(1052240);

		}
	}
}