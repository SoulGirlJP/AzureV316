
/*

    히나 소스 팩의 스크립트 입니다. (제작 : 티썬)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 30레벨 장비상자


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
                case 110:
                case 120:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1092099, 1);
                    cm.gainItem(1302186, 1);
                    break;
                case 130:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1092099, 1);
                    cm.gainItem(1432110, 1);
                    break;
                case 1110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1092099, 1);
                    cm.gainItem(1302186, 1);
                    break;
                case 2110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1442148, 1);
                    break;
                case 3110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1092099, 1);
                    cm.gainItem(1322124, 1);
                    break;
                case 5110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1302186, 1);
                    break;
                case 210:
                case 220:
                case 230:
                case 1210:
                case 2211:
                case 3210:
//                    if (cm.getPlayer().getGender() == 0) {
//                        cm.gainItem(1050197, 1);
//                        cm.gainItem(1060156, 1);
//                    } else {
//                        cm.gainItem(1041175, 1);
//                        cm.gainItem(1061184, 1);
//                    }
                    cm.gainItem(1003317, 1);
                    cm.gainItem(1072580, 1);
                    cm.gainItem(1082362, 1);
                    cm.gainItem(1382136, 1);
                    break;
                case 310:
                case 1310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040173, 1);
                        cm.gainItem(1060161, 1);
                    } else {
                        //cm.gainItem(1041176, 1);
                        cm.gainItem(1051204, 1);
                    }
                    cm.gainItem(1003318, 1);
                    cm.gainItem(1082358, 1);
                    cm.gainItem(1072581, 1);
                    cm.gainItem(1452141, 1);
                    break;
                case 320:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040173, 1);
                        cm.gainItem(1060161, 1);
                    } else {
                        cm.gainItem(1041176, 1);
                        cm.gainItem(1051204, 1);
                    }
                    cm.gainItem(1003318, 1);
                    cm.gainItem(1082358, 1);
                    cm.gainItem(1072581, 1);
                    cm.gainItem(1462093, 1);
                    break;
                case 2310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040173, 1);
                        cm.gainItem(1060161, 1);
                    } else {
                        cm.gainItem(1041176, 1);
                        cm.gainItem(1051204, 1);
                    }
                    cm.gainItem(1003318, 1);
                    cm.gainItem(1082358, 1);
                    cm.gainItem(1072581, 1);
                    cm.gainItem(1352006, 1);
                    cm.gainItem(1522032, 1);
                    break;
                case 3310:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040173, 1);
                        cm.gainItem(1060161, 1);
                    } else {
                        cm.gainItem(1041176, 1);
                        cm.gainItem(1051204, 1);
                    }
                    cm.gainItem(1003318, 1);
                    cm.gainItem(1082358, 1);
                    cm.gainItem(1072581, 1);
                    cm.gainItem(1462093, 1);
                    break;
                case 410:
                case 420:
                case 1410:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040174, 1);
                        cm.gainItem(1060162, 1);
                    } else {
                        cm.gainItem(1041177, 1);
                        cm.gainItem(1061186, 1);
                    }
                    cm.gainItem(1003319, 1);
                    cm.gainItem(1072582, 1);
                    cm.gainItem(1082364, 1);
                    
                    cm.gainItem(1472153, 1);
                    cm.gainItem(1332162, 1);
                    break;
                    
                case 431:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040174, 1);
                        cm.gainItem(1060162, 1);
                    } else {
                        cm.gainItem(1041177, 1);
                        cm.gainItem(1061186, 1);
                    }
                    cm.gainItem(1003319, 1);
                    cm.gainItem(1072582, 1);
                    cm.gainItem(1082364, 1);
                    
                    cm.gainItem(1332162, 1);
                    cm.gainItem(1342048, 1);
                    
                    break;
                case 2410:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040174, 1);
                        cm.gainItem(1060162, 1);
                    } else {
                        cm.gainItem(1041177, 1);
                        cm.gainItem(1061186, 1);
                    }
                    cm.gainItem(1003319, 1);
                    cm.gainItem(1072582, 1);
                    cm.gainItem(1082364, 1);
                    
                    cm.gainItem(1362050, 1);
                    cm.gainItem(1352106, 1);
                    break;
                case 510:
                case 520:
                case 1510:
		case 2510:
                    cm.gainItem(1003320, 1);
                    cm.gainItem(1052393, 1);
                    cm.gainItem(1072583, 1);
                    cm.gainItem(1082365, 1);
                    cm.gainItem(1482114, 1);
                    cm.gainItem(1492113, 1);
                    break;
                case 3510:
                    cm.gainItem(1003320, 1);
                    cm.gainItem(1052393, 1);
                    cm.gainItem(1072583, 1);
                    cm.gainItem(1082365, 1);
                    cm.gainItem(1492113, 1);
                    break;
                case 530:
                    cm.gainItem(1003320, 1);
                    cm.gainItem(1052393, 1);
                    cm.gainItem(1072583, 1);
                    cm.gainItem(1082365, 1);
                    cm.gainItem(1532058, 1);
                    break;
                case 2710:
                    cm.gainItem(1003317, 1);
                    cm.gainItem(1072580, 1);
                    cm.gainItem(1082362, 1);
                    cm.gainItem(1212036, 1);
                    break;
                case 6110:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050196, 1);
                        //cm.gainItem(1060159, 1);
                    } else {
                        cm.gainItem(1051241, 1);
                        //cm.gainItem(1061182, 1);

                    }
                    cm.gainItem(1003316, 1);
                    cm.gainItem(1072579, 1);
                    cm.gainItem(1082361, 1); 
                    cm.gainItem(1402123, 1);
                    break;
                case 6510:
                    cm.gainItem(1003320, 1);
                    cm.gainItem(1052393, 1);
                    cm.gainItem(1072583, 1);
                    cm.gainItem(1082365, 1);
                    cm.gainItem(1222036, 1);
                    break;
                default:
                    cm.sendOk("You are not in a vocational level to receive equipment. If you are at a level where you can change your job, you can get your equipment after you change your job.");
                    cm.dispose();
                    return;
                    
            }
	    cm.gainItem(2430447,-1);
	    cm.dispose();
        }
    }
}