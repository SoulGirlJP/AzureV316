var º° = "#fUI/FarmUI.img/objectStatus/star/whole3#";

importPackage(Packages.constants);

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
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
	if (status == 0) {
		var jessica = "       #fnSharing Ghotic Extrabold##fs17#"+º°+"#e#r AzureMS Rebirth System "+º°+"\r\n#fs10##Cgray#       You are connected to the Rebirth System of AzureMS.#k\r\n#fs12#";
                jessica += "#L0##fs12##d Rebirth UI#k#r#fs11# ( Rebirthing Section )\r\n\r\n#k";
                jessica += "#L99# More Coming Soon.."
                
		//jessica += "#L0##fs12##d  Exchange Meso#k#r#fs11# ( My scar is soon meso )\r\n#k"
		//jessica += "#L1##fs12##d  Prospective choice#k#r#fs11# ( My future hope? )\r\n#k"
		//jessica += "#L2##fs12##d  Measure combat#k#r#fs11# ( My Combat Measure )\r\n#k"
		//jessica += "#L15##fs12##d  Gambling house#k#r#fs11# ( If you are addicted, you are responsible )\r\n#k"
		//jessica += "#L12##fs12##d  Mini Games#k#r#fs11# ( If you want to play mini games? )\r\n#k"
		//jessica += "#L5##fs12##d  Couple Rings#k#r#fs11# ( To a dear woman.. )\r\n#k"
		//jessica += "#L10##fs12##d  Wedding hall#k#r#fs11# ( Will you marry me?.. )\r\n#k"
		//jessica += "#L20##fs12##d  Jump NPC#k#r#fs11# ( Jump Map Contents )\r\n#k"
		//jessica += "#L6##fs12##d  Guild Hall#k#r#fs11# ( Go to Guild Hall Map )#l#d#n\r\n#k";
		//jessica += "#L7##fs12##d  Maple PVP Match#k#r#fs11# ( Battle with Players )#l#d#n\r\n#k";
		//jessica += "#L8##fs12##d  Wolf and Sheep Drinker#k#r#fs11# ( Tag with Players )#l#d#n\r\n#k";
		//jessica += "#L9##fs12##d  #eMulung Dojo#k#r#fs11# ( Paint Breaker )#l#d#n\r\n#k";
		cm.sendSimple(jessica);

	} else if (status == 1) {
	if (selection == 0) {
		cm.dispose();
		cm.openNpc(2510022); // 1012112 meso
	} else if (selection == 1) {
		cm.dispose();
		cm.openNpc(2450023);
	} else if (selection == 2) {
		cm.dispose();
		cm.openNpc(9000036);
	} else if (selection == 3) {
		cm.dispose();
		cm.openNpc(9000225);
	} else if (selection == 4) {
		cm.dispose();
		cm.openNpc(9000018);
	} else if (selection == 5) {
		cm.dispose();
		cm.openNpc(9201000);
	} else if (selection == 6) {
		cm.dispose();
		cm.openNpc(2010011);
	} else if (selection == 7) {
		cm.dispose();
		cm.openNpc(9000254);
	} else if (selection == 8) {
		cm.dispose();
		cm.warp(109090000);
	} else if (selection == 9) {
		cm.dispose();
		cm.warp(925020000);
	} else if (selection == 10) {
		cm.dispose();
	        cm.warp(680000300, 0);
	} else if (selection == 12) {
		cm.dispose();
	        cm.warp(910002020, 0);
	} else if (selection == 13) {
		cm.dispose();
		cm.openNpc(3003150);
	} else if (selection == 14) {
		cm.dispose();
		cm.openNpc();
	} else if (selection == 20) {
		cm.dispose();
		cm.openNpc(1012008);
	} else if (selection == 15) {
		cm.dispose();
	        cm.warp(323000101, 0);

}
}
}
}