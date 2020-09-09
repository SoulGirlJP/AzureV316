
/*

    히나 소스 팩의 스크립트 입니다. (제작 : 티썬)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 60레벨 장비상자


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
            var leftslot = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot();
            if (leftslot < 9) {
                cm.sendOk("You need at least 9 spaces for inventory. Free up at least 9 spaces on the Equipment tab and reopen it.");
                cm.dispose();
                return;
            }
            
            switch (cm.getPlayer().getJob()) {
                case 111:
                case 121:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 131:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1432114, 1);
                    break;
                case 1111:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 2111:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1442152, 1);
                    break;
                case 3111:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1322120, 1);
                    break;
                case 5111:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1092103, 1);
                    
                    cm.gainItem(1302190, 1);
                    break;
                case 211:
                case 221:
                case 231:
                case 1211:
                case 2215:
                case 3211:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050203, 1);
//                        cm.gainItem(1060156, 1);
                    } else {
                        cm.gainItem(1051247, 1);
//                        cm.gainItem(1061184, 1);
                    }
                    cm.gainItem(1003337, 1);
                    cm.gainItem(1072600, 1);
                    cm.gainItem(1082382, 1);
                    cm.gainItem(1372116, 1);
                    break;
                case 311:
                case 1311:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1452145, 1);
                    break;
                case 321:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1462134, 1);
                    break;
                case 2311:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1522036, 1);
                    break;
                case 3311:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050204, 1);
                        //cm.gainItem(1060166, 1);
                    } else {
                        cm.gainItem(1051248, 1);
                        //cm.gainItem(1061190, 1);
                    }
                    cm.gainItem(1003338, 1);
                    cm.gainItem(1072601, 1);
                    cm.gainItem(1082383, 1);
                    cm.gainItem(1462134, 1);
                    break;
                case 411:
                case 421:
                case 1411:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1472119, 1);
                    cm.gainItem(1332166, 1);
                    break;
                    
                case 433:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1332166, 1);
                    cm.gainItem(1342051, 1);
                    
                    break;
                case 2411:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040183, 1);
                        cm.gainItem(1060171, 1);
                    } else {
                        cm.gainItem(1041186, 1);
                        cm.gainItem(1061195, 1);
                    }
                    cm.gainItem(1003339, 1);
                    cm.gainItem(1072602, 1);
                    cm.gainItem(1082384, 1);
                    
                    cm.gainItem(1362054, 1);
                    break;
                case 511:
                case 521:
                case 1511:
		case 2511:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1482118, 1);
                    cm.gainItem(1492117, 1);
                    break;
                case 3511:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1492117, 1);
                    break;
                case 531:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1532053, 1);
                    break;
                case 2711:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050203, 1);
//                        cm.gainItem(1060156, 1);
                    } else {
                        cm.gainItem(1051247, 1);
//                        cm.gainItem(1061184, 1);
                    }
                    cm.gainItem(1003337, 1);
                    cm.gainItem(1072600, 1);
                    cm.gainItem(1082382, 1);
                    cm.gainItem(1212038, 1);
                    break;
                case 6111:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040182, 1);
                        cm.gainItem(1060170, 1);
                    } else {
                        cm.gainItem(1041185, 1);
                        cm.gainItem(1061194, 1);

                    }
                    cm.gainItem(1003336, 1);
                    cm.gainItem(1072599, 1);
                    cm.gainItem(1082381, 1); 
                    cm.gainItem(1402127, 1);
                    break;
                case 6511:
                    cm.gainItem(1003340, 1);
                    cm.gainItem(1052397, 1);
                    cm.gainItem(1072603, 1);
                    cm.gainItem(1082385, 1);
                    cm.gainItem(1222038, 1);
                    break;
                default:
                    cm.sendOk("You are not in a vocational level to receive equipment. If you are at a level where you can change your job, you can get your equipment after you change your job.");
                    cm.dispose();
                    return;
            }
	    cm.gainItem(2430451,-1);
	    cm.dispose();
        }
    }
}