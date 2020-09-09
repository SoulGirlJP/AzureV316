var status = -1;

var coin = 20;
var mob = 8881000; //소환될 몹 코드
var check = "우르스";//체크될 몹 이름
map = 970072300;//맵 코드
var x = -40;//x좌표
var y = 86;//y좌표
var hp = "14 조 21 억";

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
 if (cm.getPlayer().getMapId() == 970072300) {
    if (cm.getPlayer().getStat().getHp() == 0) {
    cm.sendSimple("#fn나눔고딕 Extrabold#이런.. 자네.. 상태가 좋지 않아 보이는군..\r\n#fs13##L3##b[부활]#k #d캐릭터 체력 회복#k #r(500 만 메소)#k#l");
    } else {
    cm.sendSimple("     #fn나눔고딕 Extrabold##d"+check+"#k 는 하루마다#Cgray##fs10#(12시 기준) #fs15##r1번#k #fs12#만 소환이 가능합니다.\r\n\r\n                 #fs13##d등급 :#k #r프리미엄#k          #d체력#k : #r"+hp+"#k\r\n\r\n           #L1##b창조된 빛의 정수#k #r" + coin + " 개#k - #d"+check+" 소환#k#l\r\n                   #L2##fs11##b"+ServerConstants.serverName+" 전용 광장으로 가기#k#l\r\n");
    }
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
 if(cm.BossCheck(""+check+"", 1)) {
 if(cm.haveItem(4033364,coin)) {
 cm.gainItem(4033364,-coin);
 cm.spawnMob(mob, x, y);
 cm.BossAdd(""+check+"");
 cm.playerMessage(-1,"[알림] 보스 소환이 완료 되었습니다.");
 cm.dispose();
} else {
	cm.sendOk("#fn나눔고딕 Extrabold##fs13#                   #r창조된 빛의 정수가 부족합니다.#k");
	cm.dispose();
}
} else {
	cm.sendOk("     #fn나눔고딕 Extrabold##d"+check+"#k 는 하루마다#Cgray##fs10#(12시 기준) #fs15##r1번#k #fs12#만 소환이 가능합니다.");
	cm.dispose();
	}
	}
  } else if (selection == 2) {
   cm.warp(680000000, 0);
   cm.dispose();
  } else if (selection == 3) {
   if(cm.getMeso() >= 5000000) {
   cm.gainMeso(-5000000);
   cm.getPlayer().getStat().heal(cm.getPlayer());
   cm.getPlayer().dispelDebuffs();
   cm.sendOk("#fn나눔고딕 Extrabold##fs13#              #b캐릭터의 체력이 모두 회복 되었습니다.#k");
   cm.dispose();
   } else {
   cm.sendOk("#fn나눔고딕 Extrabold##fs13#                #r체력 회복을 위한 메소가 부족합니다.#k");
   cm.dispose();
   }
  }
}
