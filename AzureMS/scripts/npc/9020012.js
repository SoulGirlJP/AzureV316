importPackage(Packages.packet.creators);
importPackage(Packages.client);
importPackage(Packages.constants);
importPackage(Packages.launch.world);

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
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
	if(cm.getPlayer().getMapId() == 401060200) {
	if(status == 0) {
	cm.sendSimple("#L0#노말매그너스 소환하기#l#k\r\n#L1#마을가기#l#k\r\n\r\n#L2##r#e(보스소환이 안될시 클릭)#l#k\r\n");
	} else if (status == 1) {
	if(selection == 0) {
 if (cm.getMonsterCount(401060200) > 0) {
  cm.sendOk("모든 몬스터를 전멸시켜야 합니다.");
  cm.dispose();
} else { 
 cm.spawnMob(8880000,2341,-1345);
 cm.dispose();
}
	} else if (selection == 1) {
	cm.allPartyWarp(100050001,true);
	cm.dispose();
	} else if (selection == 2) {
	if (cm.getMonsterCount(401060200) > 0){
    	cm.killAllMob();
	}else {
	cm.sendOk("몹이 한마리도 소환되어 있지 않습니다.");
	}
			}
		}


	} else if (cm.getPlayer().getMapId() == 401060300) {
	if(status == 0) {
	cm.sendSimple("#L0#하드매그너스 소환하기#l#k\r\n#L1#마을가기#l#k\r\n\r\n#L2##r#e(보스소환이 안될시 클릭)#l#k\r\n");
		} else if (status == 1) {
	if(selection == 0) {
 if (cm.getMonsterCount(401060300) > 0) {
  cm.sendOk("모든 몬스터를 전멸시켜야 합니다.");
  cm.dispose();
} else { 
 cm.spawnMob(9300854,2341,-1345);
 cm.dispose();
}
	} else if (selection == 1) {
	cm.allPartyWarp(100050001,true);
	cm.dispose();
	} else if (selection == 2) {
	if (cm.getMonsterCount(401060300) > 0){
    	cm.killAllMob();
	}else {
	cm.sendOk("몹이 한마리도 소환되어 있지 않습니다.");
}
			}
		}


	} else if (cm.getPlayer().getMapId() == 280030000) {
	if(status == 0) {
cm.sendSimple("#L0#카오스자쿰 소환하기#l#k\r\n#L1#마을가기#l#k\r\n\r\n#L2##r#e(보스소환이 안될시 클릭)#l#k\r\n");
		} else if (status == 1) {
	if(selection == 0) {
 if (cm.getMonsterCount(280030000) > 0) {
  cm.sendOk("모든 몬스터를 전멸시켜야 합니다.");
  cm.dispose();
} else { 
 cm.spawnMob(8800103,0,-387);
 cm.spawnMob(8800104,0,-387);
 cm.spawnMob(8800105,0,-387);
 cm.spawnMob(8800106,0,-387);
 cm.spawnMob(8800107,0,-387); 
 cm.spawnMob(8800108,0,-387); 
 cm.spawnMob(8800109,0,-387); 
 cm.spawnMob(8800110,0,-387);
 cm.spawnMob(8800100,0,-387);
 cm.dispose();
}
	} else if (selection == 1) {
	cm.allPartyWarp(100050001,true);
	cm.dispose();
	} else if (selection == 2) {
	if (cm.getMonsterCount(280030000) > 0){
    	cm.killAllMob();
	}else {
	cm.sendOk("몹이 한마리도 소환되어 있지 않습니다.");
}			
}
		}
}
}