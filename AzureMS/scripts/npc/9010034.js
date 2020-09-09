


/*


	* (Pure Development Source Script)

	엔피시아이디 : 9010034

	엔피시 이름 : 시그너스

	엔피시가 있는 맵 : 고지를향하여 (109040004)

	엔피시 설명 : 2차전직 수련 완료


*/
importPackage(java.lang);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var status = -1;
var time = 300000;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
       if (checkTime(cm) && checkPos(cm)) {

       // WorldBroadcasting.broadcast(MainPacketCreator.getGMText(10, cm.getPlayer().getName()+" 님이 로즈 이벤트를 완주하셨습니다. 보너스아이템을 받으실수 있긔~")); //
	//cm.warp(100000000);
        //var it = cm.getPlayer().getMap().getAllPlayerThreadSafe().iterator();
       // var map = cm.getClient().getChannelServer().getMapFactory().getMap(100000000);
       // while (it.hasNext()) {
        //    var chr = it.next();
        //    chr.changeMap(map, map.getPortal(0));
       // }
        //cm.dispose();
       // return;

	//}
            if (isHack(cm) && !cm.getPlayer().hasGmLevel(5)) {
                
                WorldBroadcasting.broadcast(MainPacketCreator.serverNotice(6, cm.getPlayer().getName()+"이(가) 자동 핵 감지 기능에 의해 차단되었습니다."));
                cm.getPlayer().ban("핵 사용 (2차전직)", true, false);
                cm.getClient().getSession().close();
                return;
            }
            cm.sendYesNo("와! 대단하시네요~! 이렇게 빠른 시간내에 도달하실줄은 몰랐어요! 지금 다음 수련을 시작하시겠어요?");
        } else if (!checkPos(cm)) {
            cm.getPlayer().message(5, "거리가 너무 멀어 대화를 걸 수 없다.");
            cm.dispose();
        } else if (!checkTime(cm)) {
            cm.sendOk("음.. 이미 제한 시간이 지난 것 같아요. 죄송하지만 재입장 하셔서 다시 도전해 주세요!");
            cm.dispose();
        }
    } else if (status == 1) {
        cm.getPlayer().Message(7, "이번 수련은 검은 구슬 20개를 모아 제게 가져다 주시면 돼요! 채널을 옮기거나 게임을 종료시 첫 수련부터 다시 하셔야 해요!!");
        cm.warp(103050370, 0);
        cm.dispose();
    }
}

function checkPos(cm) {
    var ltx = 588;
    var lty = -1950;
    var rbx = 900;
    var rby = -1780;
    var curx = cm.getPlayer().getPosition().getX();
    var cury = cm.getPlayer().getPosition().getY();
    return curx >= ltx && cury >= lty && curx <= rbx && cury <= rby;
}

function checkTime(cm) {
    if (cm.getPlayer().getKeyValue2("2ndTrialStartTime") == -1) {
        return false;
    }
    var startTime = cm.getPlayer().getKeyValue2("2ndTrialStartTime");
    return time > (System.currentTimeMillis() / 1000) - startTime;
}

function isHack(cm) {
    var startTime = cm.getPlayer().getKeyValue("2ndTrialStartTime");
    return (System.currentTimeMillis() / 1000) - startTime < 60000;
}