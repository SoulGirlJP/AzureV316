/*

     [ PlatinumMS ]

     이 스크립트는 PlatinumMS 에서 제작한 스크립트 입니다.

     스크립트 용도 : 70레벨 장비상자

*/

importPackage(Packages.client.items);
var status = -1;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		    cm.gainItem(1003529, 1);
		    cm.gainItem(1052457, 1);
		    cm.gainItem(1102394, 1);
	            cm.gainItem(1082430, 1);
		    cm.gainItem(1072660, 1);
		    cm.gainItem(1112742, 1);
		    cm.gainItem(1132151, 1);
	            cm.gainItem(1152088, 1);
	            cm.gainItem(1122196, 1);
	            cm.gainItem(1152088, 1);
	            cm.gainItem(1672010, 1);
	            cm.gainItem(1662014, 1);
	            cm.gainItem(1012318, 1);

	    cm.gainItem(2431770, -1);
                    cm.sendOk("#e#rFax style#k#n I got a set of violet. Have a pleasant fax style!");
                    cm.dispose();
                    return;

                    }
        
    }
}