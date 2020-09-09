// Copyright SoulGirlJP rights reserved.
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.tools);
importPackage(java.util);
var status = -1;

/* Below is the settings part */
mapid = 993014200;
mobid = [9010152,9010153,9010154,9010155,9010156,9010157,9010158,9010159,9010160,9010161,9010162,9010163,9010164,9010165,9010166,9010167,
             9010168,9010169,9010170,9010171,9010172,9010173,9010174,9010175,9010176,9070177,9010178,8644005,8644006,8644424];
mobx = [2694, 2395, 2139, 1898, 1715, 2824];
moby = [401, 166, -79];
addmobcount = [500, 500, 500, 500, 500, 500];

ccoin = 4310237;

howsec = 1; // How much time to respawn mobs [Unit: second]


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
	mapid = cm.getPlayer().getMapId();
    if (cm.getPlayer().getDateKey("howmuch") == null) {
        cm.getPlayer().setDateKey("howmuch",6);
    }
    cD = cm.getQuestRecord(12345);
    if (cD.getCustomData() == null) {
        cD.setCustomData("0");
    }
    
    for (i = 0; i < addmobcount.length; i++) {
        if (cm.getPlayer().getLevel() <= addmobcount[i]) {
            mcount = Number(i + 1)
            break;
        }
    }
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        if (status == 0 && selection == -1) {
             selection = 1
        }
        status++;
    }

    if (status == 0) {
        if (cm.getFrozenMobCount() <= 0) {
            if (cm.getPlayer().getDateKey("howmuch") <= 0) {
                cm.sendOk("#fs11#I've already run out of charges today!")
                cm.dispose();
                return;
            } else {
                cm.sendYesNo("#fs11#Come on! \r\n\r\n"
                    + "#i" + ccoin + "# #b#z" + ccoin + "# 100 #kParty #bmonster#k #bI'll charge 1!\r\n\r\nI want to use it right now~?");
            }
        } else {
            富 = "Coolest hunting ground in AzureMS!\r\n";
            富 += "#b<Frozen-Link>#kSincerely welcome to FZL~!\r\n\r\n";
            富 += "#b#L0#I want to changed monster type.\r\n";
            富 += "#b#L1# I'll charge #k to hunt monsters.\r\n";
            cm.sendSimple(富);
        }

    } else if (status == 1) {
        S = selection;
        if (selection == 0) {
            富 = "Please select the monster to change or summon.\r\n\r\n";
            for (i = 0; i < mobid.length; i++) {
                getLevel = Math.floor(cm.getPlayer().getLevel() / 5) - 10;
                if (i >= getLevel - 3 && i <= Number(getLevel + 1)) {
                    gS = Number(i + 1) * 10;
                    富 += "#L" + i + "# #b(Lv." + gS + ") #o" + mobid[i] + "#\r\n"
                }
            }
            cm.sendSimple(富);
        } else if (selection == 1) {
            if (Math.floor(cm.itemQuantity(ccoin) / 100) < cm.getPlayer().getDateKey("howmuch")) {
                limit = Math.floor(cm.itemQuantity(ccoin) / 100);
            } else {
                limit = cm.getPlayer().getDateKey("howmuch");
            }
            cm.sendGetNumber("#fs11#How many times will you charge?\r\n\r\n#rConsumes 100 Frozen Link coins and charges 500 monsters per time..\r\n\r\n#b( Holding #z" + ccoin + "# : " + cm.itemQuantity(ccoin) + "俺)", 1, 1, limit);
        } else {
             cm.sendOk(selection)
        }
    } else if (status == 2) {
        if (cD.getCustomData() == "0") {
            cD.setCustomData("1");
        } else {
            cD.setCustomData("0");
        }
        if (S == 1) {
            if (selection <= limit) {
                cm.addFrozenMobCount(500 * selection);
                cm.gainItem(ccoin , -selection*100);
                cm.getPlayer().dropMessage(100, selection * 500 + "Marie has been charged.");
                cm.getPlayer().setDateKey("howmuch",cm.getPlayer().getDateKey("howmuch") - selection);
                selection = Math.floor(cm.getPlayer().getLevel() / 10) - 1;
            } else {
                cm.sendOk("#fs11#Error detected.");
                cm.dispose();
                return;
            }
        }
        gCD = cD.getCustomData();
        cm.killAllMob();
        cm.warp(mapid);
        var tick = 0;
        var schedule = Packages.tools.Timer.MapTimer.getInstance().register(function () {
            if (Number(cm.getFrozenMobCount()) <= 0 || cm.getPlayer().getMapId() != mapid || gCD != cm.getQuestRecord(12345).getCustomData()) {
                cm.killAllMob();
                schedule.cancel(true);
            }
            if (tick == 0) {
                for (i = 0; i < mobx.length; i++) {
                    for (g = 0; g < moby.length; g++) {
                        cm.spawnMob(mobid[selection], mobx[i], moby[g]);
                    }
                }
            }
            if (tick > 0 && (tick % howsec == 0)) {
                realmcount = cm.getMonsterCount(mapid);
                howmany = mobx.length * moby.length;
                for (i = 0; i < howmany - realmcount; i++) {
                    rd1 = Math.floor(Math.random() * mobx.length);
                    rd2 = Math.floor(Math.random() * moby.length);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    cm.spawnMob(mobid[selection], mobx[rd1], moby[rd2]);
                    
                    //cm.getPlayer().dropMessage(6, rd1)
                }
            }
            tick++;

        }, 10);
        cm.dispose();
    }
}
