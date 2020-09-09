importPackage(java.awt);
importPackage(java.lang);
importPackage(Packages.tools.RandomStream);
importPackage(Packages.packet.creators);
importPackage(Packages.server.life);

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
        status --;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        var eim = cm.getPlayer().getEventInstance();
        if (eim == null) {
            cm.warp(100000000);
            cm.dispose();
            return;
        }
        if (eim.getProperty("2P_Progress").equals("2") && eim.getProperty("1P_Progress").equals("2")) {
            var progress = Integer.parseInt(eim.getProperty("allProgress"));
            if (progress == 30) {
                progress += 10;
                eim.setProperty("allProgress", progress+"");
                cm.getPlayer().getMap().broadcastMessage(cm.getPlayer(), UIPacket.AchievementRatio(progress), true);
                cm.getPlayer().getMap().startMapEffect("다른 쪽에 있는 석상을 찾았습니다! 석상이 시작지점을 가리킵니다.. 다시 처음 위치에 있던 석상을 찾아야 합니다!", 5120035);
                cm.dispose();
            } else if (progress == 40) {
                cm.sendOk("석상은 아무런 반응도 없습니다.");
                cm.dispose();
            } else if (progress == 50) {
                cm.sendNext("갑자기 석상이 붉은 빛으로 변하기 시작했습니다! 반대쪽 석상에 무슨 일이 벌어진 것 같습니다.");
            } else if (progress == 60) {
                cm.sendOk("반대쪽 석상에 무슨 일이 생긴 것 같습니다. 얼른 가봐야 합니다!");
                cm.dispose();
            } else if (progress == 70) {
                cm.sendOk("붉은 빛이 감돌고 있습니다.");
                cm.dispose();
            }
        } else {
            cm.sendOk("석상은 아무런 반응도 없습니다. 아직 동료가 도착하지 않아서 그런 것으로 보입니다.");
            cm.dispose();
            return;
        }
    } else if (status == 1) {
        var eim = cm.getPlayer().getEventInstance();
        var progress = Integer.parseInt(eim.getProperty("allProgress"));
        if (progress == 50) {
            progress += 20;
            eim.setProperty("allProgress", progress+"");
            cm.getPlayer().getMap().broadcastMessage(cm.getPlayer(), UIPacket.AchievementRatio(progress), true);
//            cm.getPlayer().getMap().startMapEffect("비밀통로 어딘가에서 이상한 기운이 감돕니다. 이상한 기운의 근원을 찾아 없애야합니다!", 5120035);
//            for (var i = 0; i < 10 ; i++) {
//                //69, 2714
//                var pos = new Point(-352, 100);
//                var mob = MapleLifeProvider.getMonster(9800014);
//                mob.disableDrops();
//                eim.registerMonster(mob);
//                eim.setProperty("9800014", "10");
//                cm.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, pos);
//            }
            var it = eim.getPlayers().iterator();
            var text = "몬스터를 모두 잡았습니다! 잠시 후 다음 스테이지로 이동됩니다.";
            while (it.hasNext()) {
                var chr = it.next();
                chr.send(UIPacket.showInfo(text));
                chr.send(MainPacketCreator.getGMText(7, text));
                chr.send(MainPacketCreator.showEffect("monsterPark/clear"));
                chr.send(MainPacketCreator.playSound("Party1/Clear"));
                chr.send(UIPacket.AchievementRatio(progress));
            }
            eim.schedule("moveAll", 5000);
            cm.dispose();
        }
    }
}


