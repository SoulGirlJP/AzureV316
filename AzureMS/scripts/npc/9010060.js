
importPackage(Packages.constants);
importPackage(Packages.server.items);
importPackage(Packages.client.items);
importPackage(java.lang);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);

var status = 0;
var invs = Array(1, 5);
var invv;
var selected;
var slot_1 = Array();



var slot_2 = Array();
var statsSel;
var hoo = 0;

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        var ask = "#b式式式式式式式式式式式式式式式式式式式式式式式式式式#k\r\n                #fUI/CashShop.img/CSEffect/specialLabel/0##fnSharing Ghotic Extrabold##fs16##b#e Boost sponsorship#n  \\r\n#b式式式式式式式式式式式式式式式式式式式#k\r\n#k #r  #k OBJ:#rStar force 0 ~ 25Castle equip\r\n\r\n#k #r  #k Ascension:#rAll Stats+8#k #b ATT,M.ATT+15\r\n\r\n#k #r  #k Success probability:#e#d100%#n#k \r\n #r< at once #i4310070# 1 is consumed. >#k#n\r\n\r\n               ";
        cm.sendYesNo(ask);
    } else if (status == 1) {
        var ok = false;
        var option = "             Please select an item to enhance\r\n#e#r [When not Enhanced]\r\n [Move item to top of equipment inventory]#n#k\r\n#b";
        for (var x = 0; x < invs.length; x++) {
            var inv = cm.getInventory(invs[x]);
            for (var i = 0; i <= inv.getSlotLimit(); i++) {
                if (x == 0) {
                    slot_1.push(i);
                } else {
                    slot_2.push(i);
                }
                var it = inv.getItem(i);
                if (it == null) {
                    continue;
                }

                var itemid = it.getItemId();
                if (cm.isCash(it.getItemId())) {
                    var itemid = 0;
                } else {
                    var itemid = it.getItemId();
                }
                if (!GameConstants.isEquip(itemid)) {
                    continue;
                }
                ok = true;
                option += "#L" + (invs[x] * 1000 + i) + "##v" + itemid + "##t" + itemid +
                        "##l\r\n";
            }
        }
        if (!ok) {
            cm.sendOk("There are no items. Please check the equipment window.");
            cm.dispose();
            return;
        }
        cm.sendSimple(option + "#k");
    } else if (status == 2) {
        invv = selection / 1000;
        selected = selection % 1000;
        var inzz = cm.getInventory(invv);
        if (invv == invs[0]) {
            statsSel = inzz.getItem(slot_1[selected]);
        } else {
            statsSel = inzz.getItem(slot_2[selected]);
        }
        if (statsSel == null) {
            cm.sendOk("An error occurred. Please try again in a few minutes.");
            cm.dispose();
            return;
        }
        if (statsSel.getEnhance() >= 26 || statsSel.getEnhance() < 0) {
            cm.sendOk("Boosting support #bSurprise#k Only items with a minimum rating of 25 or more are allowed.");
            cm.dispose();
            return;
	}
        if(!cm.haveItem(4310070, 1)) {
            cm.sendOk("Boosting support #i4310070# 1 item required.");
            cm.dispose();
            return;
        }
       
        statsSel.setStr(statsSel.getStr() + 8);
        statsSel.setDex(statsSel.getDex() + 8);
        statsSel.setInt(statsSel.getInt() + 8);
        statsSel.setLuk(statsSel.getLuk() + 8);
        statsSel.setWatk(statsSel.getWatk() + 15.6);
        statsSel.setMatk(statsSel.getMatk() + 15.6);
        statsSel.setEnhance(statsSel.getEnhance() + 0);
        cm.gainItem(4310070, -1);
cm.getPlayer().send(MainPacketCreator.getGMText(19, "[MapleStory] Successfully strengthened."));
        cm.fakeRelog();
        cm.updateChar();
        cm.sendOk("#fs 50##fnArchery# #bEnhanced!#k");
WorldBroadcasting.broadcast(MainPacketCreator.getGMText(5, "AzureMS◇ "+ cm.getPlayer().getName()+" ◆ Has completed □Support Enhancement■. Congratulations."));
        cm.dispose();
}
}