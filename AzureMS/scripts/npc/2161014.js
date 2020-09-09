


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	에반 에 의해 만들어 졌습니다.

	엔피시아이디 : 2161014

	엔피시 이름 : 반 레온

	엔피시가 있는 맵 : 사자왕의 성 : 알현실 (211070200)

	엔피시 설명 : 사자왕


*/

var status = -1;
var cost = 88200000; // 소환가격
var mob = 8840000; //소환될 몹 코드
var check = "반 레온";//체크될 몹 이름
map = 211070102;//맵 코드
var x = -1;//x좌표
var y = -181;//y좌표`
var hp = "1 4 7 억";

importPackage(Packages.constants);

function start() {
 action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

 if (status == 0) {
    if (cm.getPlayer().getMapId() == 211070102) {
  cm.sendSimple("     #fn나눔고딕 Extrabold##d"+check+"#k 은 하루마다#Cgray##fs10#(12시 기준) #fs15##r6번#k #fs12#만 소환이 가능합니다.\r\n\r\n                                        #fs13##d체력#k : #r"+hp+"#k\r\n\r\n              #L1##r" + cost + " 메소#k - #d"+check+" 소환#k#l\r\n                   #L2##fs11##b"+ServerConstants.serverName+" 전용 광장으로 가기#k#l\r\n");
    } else {
   cm.dispose();
}
  } else if(selection == 1) {
            if (cm.getPlayer().getParty() == null) {
            cm.sendOk("#fn나눔고딕 Extrabold##fs13##r                   소환하려면 파티가 필요합니다.");
            cm.dispose();
            }
            if (!cm.isLeader()) {
	  cm.sendOk("#fn나눔고딕 Extrabold##fs13##r                    파티장만 소환이 가능합니다.");
                cm.dispose();
            }
            if (!cm.allMembersHere()) {
	cm.sendOk("#fn나눔고딕 Extrabold##fs13##r                파티원 전원이 이곳에 있어야 합니다.");
                cm.dispose();
           }
           if (cm.getMonsterCount(map) > 0) {
	cm.dispose();
	cm.openNpc(9010017);
            } else {
 if(cm.BossCheck(""+check+"", 6)) {
 if(cm.getMeso() >= cost) {
 cm.gainMeso(-cost); // 메소 차감시키기
 cm.spawnMob(mob, x, y);
 cm.BossAdd(""+check+"");
 cm.playerMessage(-1,"[알림] 보스 소환이 완료 되었습니다.");
 cm.dispose();
} else {
	cm.sendOk("#fn나눔고딕 Extrabold##fs13#                           #r메소가 부족합니다.#k");
	cm.dispose();
}
} else {
	cm.sendOk("     #fn나눔고딕 Extrabold##d"+check+"#k 은 하루마다#Cgray##fs10#(12시 기준) #fs15##r6번#k #fs12#만 소환이 가능합니다.");
	cm.dispose();
	}
	}
  } else if (selection == 2) {
   cm.warp(680000000, 0);
   cm.dispose();
  }
}
