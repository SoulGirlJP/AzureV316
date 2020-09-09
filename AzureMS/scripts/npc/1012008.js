var status = 0;
var Star = "#fUI/FarmUI.img/eventBox/button:Ok/mouseOver/0#";
importPackage(Packages.constants);

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
	datecheck = true;
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        È½¼ö = cm.getPlayer().getDateKey("jump");
        if (È½¼ö == null) {
	cm.getPlayer().setDateKey("jump", 5);
        }
        if (status == 0) {
           var chat = "#fnClear Gothic Extrabold##fs22##e#d        " + Star + " Running Man " + Star + "#d#n#fs22# \r\n";
		chat += "          #fnClear Gothic Extrabold##fs16# Remaining entries : " + È½¼ö + " \r\n#fnClear Gothic Extrabold##fs22#"
		chat += "#L2##fUI/GuildMark.img/Mark/Pattern/00004001/2##bRunning Man Perseverance#l\r\n";
	        chat += "#L3##fUI/GuildMark.img/Mark/Pattern/00004001/3##kRunning Man's Endure Forest#l\r\n";
		chat += "#L4##fUI/GuildMark.img/Mark/Pattern/00004001/4##rRunning Man's Highland Forest#l\r\n";
		//chat += "	#L5##e#fUI/GuildMark.img/Mark/Pattern/00004001/3##b·±´×¸Ç ÁöÇÏÃ¶ ÀÌ¿ëÇÏ±â(º¸»óx)#l\r\n";
		//chat += "	#L1##e#fUI/GuildMark.img/Mark/Pattern/00004001/1##r·±´×¸Ç Åõ¸íÀÇ½£ ÀÌ¿ëÇÏ±â(º¸»óx)#l\r\n";
		//chat += "	#L6##e#fUI/GuildMark.img/Mark/Pattern/00004001/2##k·±´×¸Ç Æê»êÃ¥·Î ÀÌ¿ëÇÏ±â(º¸»óx)#l";
		cm.sendSimpleS(chat, 2);
		} else if (status == 1) {
		if (selection == 1) {
if (È½¼ö <= 0) {
cm.sendOk("Jump maps are only available 5 times a day.");
cm.dispose();
} else {
cm.warp(992048000, 0);
cm.dispose();
}
		} else if (selection == 2) {
if (È½¼ö <= 0) {
cm.sendOk("The jump map is only available 5 times a day.");
cm.dispose();
} else {
cm.getPlayer().setDateKey("jump", È½¼ö - 1);
cm.warp(910530000, 0);
cm.dispose();
}
		} else if (selection == 3) {
if (È½¼ö <= 0) {
cm.sendOk("The jump map is only available 5 times a day.");
cm.dispose();
} else {
cm.getPlayer().setDateKey("jump", È½¼ö - 1);
cm.warp(910130000, 0);
cm.dispose();
}
		} else if (selection == 4) {
if (È½¼ö <= 0) {
cm.sendOk("The jump map is only available 5 times a day.");
cm.dispose();
} else {
cm.getPlayer().setDateKey("jump", È½¼ö - 1);
cm.warp(109040000, 0);
cm.dispose();
}
		} else if (selection == 5) {
if (È½¼ö <= 0) {
cm.sendOk("The jump map is only available 5 times a day.");
cm.dispose();
} else {
cm.warp(910360200, 0);
cm.dispose();
}
		} else if (selection == 6) {
if (È½¼ö <= 0) {
cm.sendOk("The jump map is only available 5 times a day.");
cm.dispose();
} else {
cm.warp(100000202, 0);
cm.dispose();
}
		}
		}
	}
}