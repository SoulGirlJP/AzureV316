var status;

function start() {
    status = -1;
    action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    return;
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var text = "#h #Hello, Having a fun trip to Maple World?\r\n#bMiracle Cube Pieces#k of #bLatent Order#k We will exchange. \r\nNow #r#c2430112##kThere are two pieces of Miracle cube.\r\nPlease select your desired exchange item.\r\n\r\n";
            if(cm.haveItem(2430112, 10)) {
                text += "#b#L0#1 into 10 pieces #z2049401#Exchange with.#l\r\n";
            } else {
                text += "#k#L0#Á¶1 into 10 pieces #z2049401#Exchange with.#l\r\n";
            }
            if(cm.haveItem(2430112, 15)) {
                text += "#b#L1#1 into 15 pieces #z2049400#Exchange with.#l\r\n";
            } else {
                text += "#k#L1#1 into 15 pieces #z2049400#Exchange with.#l\r\n";
            }
            text += "#L2##bI won't exchange it.#l";
            cm.sendSimple(text);
        } else if(status == 1) {
            if(selection == 0) {
                if(cm.haveItem(2430112, 10)) {
                    if(cm.canHold(2049401)) {
                        cm.gainItem(2430112, -10);
                        cm.gainItem(2049401, 1);
                    } else {
                        cm.sendOk("Sorry, but you don't have enough inventory space. #bConsumption#k Please free up inventory space on the tab.");
                    }
                } else {
                    cm.sendOk("I'm sorry #b#z2430112##kI don't think this is enough.");
                }
                cm.dispose();
            } else if(selection == 1) {
                if(cm.haveItem(2430112, 15)) {
                    if(cm.canHold(2049400)) {
                        cm.gainItem(2430112, -15);
                        cm.gainItem(2049400, 1);
                    } else {
                        cm.sendOk("Sorry, but you don't have enough inventory space.. #bConsumption #kPlease free up inventory space on the tab.");
                    }
                } else {
                    cm.sendOk("I'm sorry #b#z2430112##k I don't think this is enough.");
                }
                cm.dispose();
            } else {
                cm.dispose();
            }
        } else { 
            cm.dispose();
        }
    }
}