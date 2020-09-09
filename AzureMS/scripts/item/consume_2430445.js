
/*

    히나 소스 팩의 스크립트 입니다. (제작 : 티썬)

    엔피시아이디 : ?
    
    엔피시 이름 : 메이플 운영자

    엔피시가 있는 맵 : ?

    엔피시 설명 : 20레벨 장비상자


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
                case 100:
                case 1100:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040163, 1);
                        cm.gainItem(1060153, 1);
                    } else {
                        cm.gainItem(1051239, 1);

                    }
                    cm.gainItem(1003307, 1);
                    cm.gainItem(1072569, 1);
                    cm.gainItem(1082351, 1); 
                    cm.gainItem(1092097, 1);
                    cm.gainItem(1302184, 1);
                    cm.gainItem(1312079, 1);
                    cm.gainItem(1422081, 1);
                    break;
                case 2100:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040163, 1);
                        cm.gainItem(1060153, 1);
                    } else {
                        cm.gainItem(1051239, 1);

                    }
                    cm.gainItem(1003307, 1);
                    cm.gainItem(1072569, 1);
                    cm.gainItem(1082351, 1); 
                    cm.gainItem(1442092, 1); 
                    break;
                case 3100:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040163, 1);
                        cm.gainItem(1060153, 1);
                    } else {
                        cm.gainItem(1051239, 1);

                    }
                    cm.gainItem(1003307, 1);
                    cm.gainItem(1072569, 1);
                    cm.gainItem(1082351, 1); 
                    cm.gainItem(1312079, 1);
                    break;
                case 5100:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040163, 1);
                        cm.gainItem(1060153, 1);
                    } else {
                        cm.gainItem(1051239, 1);

                    }
                    cm.gainItem(1003302, 1);
                    cm.gainItem(1072564, 1);
                    cm.gainItem(1082346, 1); 
                    cm.gainItem(1302184, 1);
                    break;
                case 200:
                case 1200:
                case 2210:
                case 3200:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050195, 1);
                        //cm.gainItem(1060156, 1);
                    } else {
                        cm.gainItem(1051240, 1);
                        //cm.gainItem(1061179, 1);
                    }
                    cm.gainItem(1003308, 1);
                    cm.gainItem(1072570, 1);
                    cm.gainItem(1082352, 1);
                    cm.gainItem(1372110, 1);
                    break;
                case 300:
                case 1300:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040168, 1);
                        cm.gainItem(1060157, 1);
                    } else {
                        cm.gainItem(1041170, 1);
                        cm.gainItem(1061180, 1);
                    }
                    cm.gainItem(1003309, 1);
                    cm.gainItem(1082353, 1);
                    cm.gainItem(1072571, 1);
                    cm.gainItem(1452038, 1);
                    break;
                case 2300:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040168, 1);
                        cm.gainItem(1060157, 1);
                    } else {
                        cm.gainItem(1041170, 1);
                        cm.gainItem(1061180, 1);
                    }
                    cm.gainItem(1003309, 1);
                    cm.gainItem(1082353, 1);
                    cm.gainItem(1072571, 1);
                    cm.gainItem(1522030, 1);
                    break;
                case 3300:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040168, 1);
                        cm.gainItem(1060157, 1);
                    } else {
                        cm.gainItem(1041170, 1);
                        cm.gainItem(1061180, 1);
                    }
                    cm.gainItem(1003309, 1);
                    cm.gainItem(1082353, 1);
                    cm.gainItem(1072571, 1);
                    break;
                case 400:
                case 1400: {
                    if (cm.getPlayer().getKeyValue("dualBlade") == null) {
                        if (cm.getPlayer().getGender() == 0) {
                            cm.gainItem(1040169, 1);
                            cm.gainItem(1060158, 1);
                        } else {
                            cm.gainItem(1041171, 1);
                            cm.gainItem(1061181, 1);
                        }
                        cm.gainItem(1003310, 1);
                        cm.gainItem(1072572, 1);
                        cm.gainItem(1082354, 1);

                        cm.gainItem(1472034, 1);
                        cm.gainItem(1332033, 1);
                        break;
                    } else {
                        cm.sendOk("You are not in a vocational level to receive equipment. If you are at a level where you can change your job, you can get your equipment after you change your job.");
                        cm.dispose();
                        return;
                    }
                } 
                case 430:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040169, 1);
                        cm.gainItem(1060158, 1);
                    } else {
                        cm.gainItem(1041171, 1);
                        cm.gainItem(1061181, 1);
                    }
                    cm.gainItem(1003310, 1);
                    cm.gainItem(1072572, 1);
                    cm.gainItem(1082354, 1);
                    
                    cm.gainItem(1332033, 1);
                    cm.gainItem(1342047, 1);
                    
                    break;
                case 2400:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040169, 1);
                        cm.gainItem(1060158, 1);
                    } else {
                        cm.gainItem(1041171, 1);
                        cm.gainItem(1061181, 1);
                    }
                    cm.gainItem(1003310, 1);
                    cm.gainItem(1072572, 1);
                    cm.gainItem(1082354, 1);
                    
                    cm.gainItem(1362032, 1);
                    break;
                case 500:
                case 1500:
		case 2500:
                    cm.gainItem(1003311, 1);
                    cm.gainItem(1052391, 1);
                    cm.gainItem(1072573, 1);
                    cm.gainItem(1082355, 1);
                    cm.gainItem(1482015, 1);
                    cm.gainItem(1492015, 1);
                    cm.gainItem(1353100, 1);
                    break;
                case 3500:
                    cm.gainItem(1003311, 1);
                    cm.gainItem(1052391, 1);
                    cm.gainItem(1072573, 1);
                    cm.gainItem(1082355, 1);
                    cm.gainItem(1492015, 1);
                    break;
                case 501:
                    cm.gainItem(1003311, 1);
                    cm.gainItem(1052391, 1);
                    cm.gainItem(1072573, 1);
                    cm.gainItem(1082355, 1);
                    cm.gainItem(1532024, 1);
                    break;
                case 2700:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1050195, 1);
                    } else {
                        cm.gainItem(1051240, 1);
                    }
                    cm.gainItem(1003308, 1);
                    cm.gainItem(1072570, 1);
                    cm.gainItem(1082352, 1);
                    break;
                case 6100:
                    if (cm.getPlayer().getGender() == 0) {
                        cm.gainItem(1040012, 1);
                        cm.gainItem(1060010, 1);
                    } else {
                        cm.gainItem(1041168, 1);
                        cm.gainItem(1061018, 1);
                    }
                    cm.gainItem(1003307, 1);
                    cm.gainItem(1072569, 1);
                    cm.gainItem(1082351, 1); 
                    cm.gainItem(1402121, 1);
                    break;
                case 6500:
                    cm.gainItem(1003311, 1);
                    cm.gainItem(1052391, 1);
                    cm.gainItem(1072573, 1);
                    cm.gainItem(1082355, 1);
                    break;
                default:
                    cm.sendOk("You are not in a vocational level to receive equipment. If you are at a level where you can change your job, you can get your equipment after you change your job.");
                    cm.dispose();
                    return;
                    
            }
	    cm.gainItem(2430445,-1);
	    cm.dispose();
        }
    }
}