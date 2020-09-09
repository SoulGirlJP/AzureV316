


/*

	히나 온라인 소스 팩의 스크립트 입니다.

        제작 : 티썬

	엔피시아이디 : 
	
	엔피시 이름 :

	엔피시가 있는 맵 : 

	엔피시 설명 : 아스완 해방전 입장


*/

importPackage (java.lang);
importPackage (java.util);
importPackage (Packages.tools);
importPackage (Packages.server.quest);
var status = -1;

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
        var date = Calendar.getInstance().get(Calendar.YEAR)%100+"/"+StringUtil.getLeftPaddedStr(Calendar.getInstance().get(Calendar.MONTH)+"", "0", 2)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (cm.getPlayer().getKeyValue("AswanOffSeason_LastDate") == null) {
            cm.getPlayer().setKeyValue("AswanOffSeason_LastDate", date);
        }
        if (cm.getQuestStatus(7963) == 0 || !cm.getPlayer().getKeyValue("AswanOffSeason_LastDate").equals(date)) {
            cm.forceStartQuest(7963, "0");
            cm.getPlayer().setKeyValue("AswanOffSeason_LastDate", date);
        }
        cm.sendSimple("#e<아스완 해방전>#n\r\n여전히 아스완 지역을 배회하고 있는 힐라의 잔당들을 소탕하시겠습니까?#b\r\n\r\n\r\n#L0# 힐라의 잔당을 소탕한다. (레벨 40이상. 남은 입장 횟수: "+(5-Integer.parseInt(cm.getQuestCustomData(7963)))+"번)#l\r\n#L1# 힐라의 탑 입구로 보내드립니다. (레벨 120이상)#l");
    } else if (status == 1) {
        if (selection == 0) {
            if (cm.getPlayer().getLevel() < 40) {
                cm.sendOk("아직 힐라의 잔당을 소탕하실 레벨이 부족하신것 같네요. 잔당 소탕은 레벨 40부터 입장 가능합니다.");
                cm.dispose();
                return;
            }
            if (cm.getQuestCustomData(7963).equals("5")) {
                cm.sendOk("오늘 입장가능한 횟수를 모두 사용하셨습니다. 내일 다시 도전해 주세요.");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getParty() != null) {
                var em = cm.getEventManager("AswanOffSeason");
                if (!cm.isLeader()) {
                    cm.sendOk("파티장이 제게 말을 걸어야 합니다.");
                    cm.dispose();
                    return;
                }
                if (!cm.allMembersHere()) {
                    cm.sendOk("파티원이 전원 이곳에 모여있어야 합니다.");
                    cm.dispose();
                    return;
                }
                var it = cm.getClient().getChannelServer().getPartyMembers().iterator();
                var levelPass = true;
                var limitPass = true;
                while (it.hasNext()) {
                    var chr = it.next();
                    if (!checkLevel(chr.getLevel(), 40, 200)) {
                        levelPass = false;
                        break;
                    }
                    if (chr.getQuestNAdd(MapleQuest.getInstance(7963)).getCustomData().equals("5")) {
                        limitPass = false;
                        break;
                    }
                }
                if (!levelPass) {
                    cm.sendOk("파티원 중 레벨이 맞지 않는 파티원이 있습니다.\r\n#r40레벨 이상#k의 파티원만 입장할 수 있습니다.");
                    cm.dispose();
                    return;
                }
                if (!limitPass) {
                    cm.sendOk("파티원 중 오늘 도전 횟수를 모두 소진한 파티원이 있습니다.\r\n하루에 5번만 입장 가능합니다.");
                    cm.dispose();
                    return;
                }
                //
                var eim = em.readyInstance();
                eim.setProperty("Global_StartMap", 955000100+"");
                eim.setProperty("Global_ExitMap", 262000000+"");
                eim.setProperty("Global_MinPerson", 1+"");
                eim.setProperty("Global_RewardMap", 262000000+"");
                eim.setProperty("CurrentStage", "1");
                eim.startEventTimer(1200000);
                cm.prepareAswanMob(955000100);
                var it2 = cm.getClient().getChannelServer().getPartyMembers(cm.getParty()).iterator();
                var quest = MapleQuest.getInstance(7963);
                while (it2.hasNext()) {
                    var chr = it2.next();
                    var count = Integer.parseInt(chr.getQuestNAdd(quest).getCustomData());
                    quest.forceStart(chr, 2100, (count+1)+"");
                }
                eim.registerParty(cm.getPlayer().getParty(), cm.getPlayer().getMap());
            } else {
                
                if (!checkLevel(cm.getPlayer().getLevel(), 40, 200)) {
                    cm.sendOk("파티원 중 레벨이 맞지 않는 파티원이 있습니다.\r\n#r40레벨 이상#k의 파티원만 입장할 수 있습니다.");
                    cm.dispose();
                    return;
                }
            
                var em = cm.getEventManager("AswanOffSeason");
                var eim = em.readyInstance();
                eim.setProperty("Global_StartMap", 955000100+"");
                eim.setProperty("Global_ExitMap", 262000000+"");
                eim.setProperty("Global_MinPerson", 1+"");
                eim.setProperty("Global_RewardMap", 262000000+"");
                eim.setProperty("CurrentStage", "1");
                eim.startEventTimer(1200000);
                cm.prepareAswanMob(955000100, eim);
                eim.registerPlayer(cm.getPlayer());
                cm.forceStartQuest(7963, (Integer.parseInt(cm.getQuestCustomData(7963))+1)+"");
                cm.dispose();
            }
        } else {
            cm.warp(262030000,0);
            cm.dispose();
        }
    }
}


function checkLevel(cur, min, max) {
    return (cur >= min && cur <= max);
}
