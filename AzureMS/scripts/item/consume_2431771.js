var select = -1;
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
	    var cps ="#eTake your pick\r\n"
	    cps += "#b#L1##v1302212# #t1302212#\r\n";
	    cps += "#b#L2##v1312114# #t1312114#\r\n";
	    cps += "#b#L3##v1322154# #t1322154#\r\n";
	    cps += "#b#L4##v1332186# #t1332186#\r\n";
	    cps += "#b#L5##v1362060# #t1362060#\r\n";
	    cps += "#b#L6##v1372131# #t1372131#\r\n";
	    cps += "#b#L7##v1382160# #t1382160#\r\n";
	    cps += "#b#L8##v1402145# #t1402145#\r\n";
	    cps += "#b#L9##v1412102# #t1412102#\r\n";
	    cps += "#b#L10##v1422105# #t1422105#\r\n";
	    cps += "#b#L11##v1432135# #t1432135#\r\n";
	    cps += "#b#L12##v1442173# #t1442173#\r\n";
	    cps += "#b#L13##v1452165# #t1452165#\r\n";
	    cps += "#b#L14##v1462156# #t1462156#\r\n";
	    cps += "#b#L15##v1472177# #t1472177#\r\n";
	    cps += "#b#L16##v1482138# #t1482138#\r\n";
	    cps += "#b#L17##v1522068# #t1522068#\r\n";
	    cps += "#b#L18##v1532073# #t1532073#\r\n";
	    cps += "#b#L19##v1232060# #t1232060#< Daemon Avenger >\r\n";
	    cps += "#b#L20##v1242065# #t1242065#< Xenon >\r\n";
	    cps += "#b#L21##v1222061# #t1222061#< Angelic Buster >\r\n";
	    cm.sendSimple(cps);
	    
	 } else if (selection == 1) {
		cm.gainItem(1302212, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 2) {
		cm.gainItem(1312144, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 3) {
		cm.gainItem(1322154, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 4) {
		cm.gainItem(1332186, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 5) {
		cm.gainItem(1362060, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 6) {
		cm.gainItem(1372131, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 7) {
		cm.gainItem(1382160, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 8) {
		cm.gainItem(1402145, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 9) {
		cm.gainItem(1412102, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 10) {
		cm.gainItem(1422105, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 11) {
		cm.gainItem(1432135, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 12) {
		cm.gainItem(1442173, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 13) {
		cm.gainItem(1452165, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 14) {
		cm.gainItem(1462156, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 15) {
		cm.gainItem(1472177, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 16) {
		cm.gainItem(1482138, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 17) {
		cm.gainItem(1522068, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 18) {
		cm.gainItem(1532073, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 19) {
		cm.gainItem(1232060, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 20) {
		cm.gainItem(1242065, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
	 } else if (selection == 21) {
		cm.gainItem(1222061, 1);
		cm.gainItem(2431771, -1);
		cm.dispose();
}
}
}