var status;
function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendYesNo("#e<스우를 해방시켜주세요!>#n\r\n\r\n#bStage.1 #fUI/UIWindow2.img/MobGage/Mob/8240097# 이볼빙 스우    \r\nStage.2 #fUI/UIWindow2.img/MobGage/Mob/8240098# 깨어난 스우     \r\nStage.3 #fUI/UIWindow2.img/MobGage/Mob/8240099# 각성한 스우\r\n\r\n#k#e<획득아이템>#n#k\r\n\r\n#b#z4033076# (6~22개)");
    } else if (status == 1) {
        if (cm.getPlayer().getParty() != null) {
            if (cm.getPlayerCount(350060160) > 1 || cm.getPlayerCount(350060180) > 1 || cm.getPlayerCount(350060200) > 1) {
                cm.sendOk("지금 스우에 도전 중인 파티가 있습니다.");
                cm.dispose();
            } else {
                cm.resetMap(350060160);
                cm.resetMap(350060180);
                cm.resetMap(350060200);
                em = cm.getEventManager("Swoo");
                eim = em.readyInstance();
                eim.setProperty("Stage", "0");
                eim.setProperty("nextWarp", "false");
                eim.setProperty("Global_MinPerson", cm.getParty().getMembers().size());
                eim.registerParty(cm.getParty(), cm.getMap());
                cm.dispose();
            }
        } else {
            cm.sendOk("파티를 만들어 주시길 바랍니다.");
            cm.dispose();
        }
    }
}