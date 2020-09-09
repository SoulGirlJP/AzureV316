var status = -1;
var a = 1000;
var b = 800;
var c = 600;


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
    if (cm.getPlayer().getMapId() == 100030301) {
        cm.sendOk("#fn나눔고딕 Extrabold#핀볼 게임에 참여하지 않을래?\r\n네가 핀볼이 되는거야! 정말 재밌겠지!?\r\n\r\n#fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#b#L1#핀볼 게임 시작\r\n#L2#게임 플레이 방법\r\n#L4#핀볼 게임 등급표\r\n#L3#오늘의 남은 입장 횟수");
    } else {
         if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= a) {
	cm.sendNext("#fn나눔고딕 Extrabold##r[보상 : 지급 받기]#k\r\n\r\n                              ★ #r1 등급#k 을 기록한 보상 ★\r\n                                        #i4310129# X #r10 개#k\r\n\r\n#d핀볼 게임 클리어 성공!#k\r\n너는 #r"+cm.getPinBallPoint()+" 점#k 을 기록해서 #b썸머리밋 코인#k #r10 개#k 를 획득했어!\r\n\r\n#r(오늘의 남은 횟수 :  "+cm.GetCount("핀볼", 6)+" 회)#k\r\n");
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= b && cm.getPinBallPoint() <= a - 1) {
	cm.sendNext("#fn나눔고딕 Extrabold##r[보상 : 지급 받기]#k\r\n\r\n                              ★ #r2 등급#k 을 기록한 보상 ★\r\n                                          #i4310129# X #r8 개#k\r\n\r\n#d핀볼 게임 클리어 성공!#k\r\n너는 #r"+cm.getPinBallPoint()+" 점#k 을 기록해서 #b썸머리밋 코인#k #r8 개#k 를 획득했어!\r\n\r\n#r(오늘의 남은 횟수 :  "+cm.GetCount("핀볼", 6)+" 회)#k\r\n");
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= c && cm.getPinBallPoint() <= b - 1) {
	cm.sendNext("#fn나눔고딕 Extrabold##r[보상 : 지급 받기]#k\r\n\r\n                              ★ #r3 등급#k 을 기록한 보상 ★\r\n                                          #i4310129# X #r4 개#k\r\n\r\n#d핀볼 게임 클리어 성공!#k\r\n너는 #r"+cm.getPinBallPoint()+" 점#k 을 기록해서 #b썸머리밋 코인#k #r4 개#k 를 획득했어!\r\n\r\n#r(오늘의 남은 횟수 :  "+cm.GetCount("핀볼", 6)+" 회)#k\r\n");
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= 0 && cm.getPinBallPoint() <= c - 1) {
	cm.sendNext("#fn나눔고딕 Extrabold##r[보상 : 지급 받기]#k\r\n\r\n#d핀볼 게임 클리어 실패…#k\r\n\r\n#h #, 너는 #b"+cm.getPinBallPoint()+" 점#k 을 기록해서 아무것도 얻지 못했어.. 실망이야..\r\n\r\n#r(오늘의 남은 횟수 :  "+cm.GetCount("핀볼", 6)+" 회)#k\r\n");
        } else {
           if (cm.getPlayer().getMapId() == 910800200) {
	cm.sendYesNo("#fn나눔고딕 Extrabold##r정말 도전을 포기하고 퇴장 하겠어?#k");
        } else {
	cm.sendOk("#fn나눔고딕 Extrabold##r* 플레이 조건#k\r\n\r\n#d- 퀘스트의 전당 에서 플레이 가능#k",9062004);
	cm.dispose();
    }
    }
    }
    } else if (status == 1) {
        if (selection == 2) {
	cm.sendOk("#fn나눔고딕 Extrabold#핀볼 게임은 컨트롤을 통해 핀볼 공이 아닌 너가 직접 통통!\r\n튕기며 점수를 획득하는 재미있는 미니 게임이야!\r\n점수도 얻고 보상도 얻고 이게바로 일석이조 아니겠어!?");
	cm.dispose();
        } else if (selection == 3) {
	cm.sendOk("                   #fn나눔고딕 Extrabold##r(오늘의 남은 입장 횟수 : "+cm.GetCount("핀볼", 6)+" 회)#k");
	cm.dispose();
        } else if (selection == 4) {
	cm.sendOk("#fn나눔고딕 Extrabold##r[핀볼 게임 등급표]#k\r\n\r\n자, 봐. 핀볼 게임 등급 표, 보상, 리워드는 아래와 같아.\r\n\r\n#b[1 등급]#k #i4310129# 10 개 : #d" + (a) + " 점 이상#k\r\n#b[2 등급]#k #i4310129# 8 개 : #d" + (a - 1) + " 점 이하 " + b + " 점 이상#k\r\n#b[3 등급]#k #i4310129# 4 개 : #d" + (b - 1) + " 점 이하 " + c + " 점 이상#k\r\n#b[4 등급]#k #i4310129# 0 개 : #d" + (c - 1) + " 점 이하#k\r\n\r\n#Cgray#(4 등급은 클리어 실패 로 간주됩니다.)");
	cm.dispose();
        } else {
        if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= a) {
	cm.gainItem(4310129, 10);
	cm.warp(100030301,0);
	cm.resetPinBallPoint();
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
	cm.dispose();
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= b && cm.getPinBallPoint() <= a - 1) {
	cm.gainItem(4310129, 8);
	cm.warp(100030301,0);
	cm.resetPinBallPoint();
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
	cm.dispose();
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= c && cm.getPinBallPoint() <= b - 1) {
	cm.gainItem(4310129, 4);
	cm.warp(100030301,0);
	cm.resetPinBallPoint();
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
	cm.dispose();
        } else if (cm.getPlayer().getMapId() == 910800250 && cm.getPinBallPoint() >= 0 && cm.getPinBallPoint() <= c - 1) {
	cm.warp(100030301,0);
	cm.resetPinBallPoint();
	cm.showEffect(false,"quest/carnival/lose");
        cm.playSound(false,"Field.img/Party1/Failed");
	cm.dispose();
        } else {
        if (cm.getPlayer().getMapId() == 100030301) {
	cm.sendYesNo("#fn나눔고딕 Extrabold##r[핀볼 게임 시작]#k\r\n\r\n통통! 튕기며 점수를 얻는 핀볼 게임! 지금 바로 도전하겠어?\r\n\r\n#b(수락 시 핀볼 게임 맵으로 이동 됩니다.)");
        } else {  
	cm.warp(100030301,0);
	cm.resetPinBallPoint();
        cm.sendOk("#fn나눔고딕 Extrabold##r다음에는 꼭 용기 있게 도전하길 바래..#k");
	cm.showEffect(false,"quest/carnival/lose");
        cm.playSound(false,"Field.img/Party1/Failed");
	cm.dispose();
        }
        }
        }
    } else if (status == 2) {
	if (cm.getPlayerCount(910800200) > 0) {
	cm.sendOk("#fn나눔고딕 Extrabold##r앗!, 현재 이미 도전중인 플레이어가 있는것 같은데?!..\r\n조금만 기다리거나, 다른 채널에서 다시 보자꾸나!#k");
	cm.dispose();
	} else
        if (cm.CountCheck("핀볼", 6)) {
	cm.resetPinBallPoint();
	cm.warp(910800200, 0);
	cm.CountAdd("핀볼");
	cm.dispose();
           } else {
	cm.sendOk("#fn나눔고딕 Extrabold##b#h ##k, 넌 오늘의 입장 횟수를 초과 한 것 같은데?\r\n\r\n#r하루에 입장 가능한 횟수는 6 회야..\r\n내일 기약하며 다시 오길 바래..#k");
	cm.dispose();
           }
}
}
