var status = -1;
var sel = 0;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 1) {
            cm.dispose();
            return;
        }
        if (status == 5) {
            cm.dispose();
            return;
        }
        status--;
    }
    switch (status) {
        case 0:
            var t = "#fs11##b#e<Frozen-Link>#n#k Welcome to FZL !\r\n\r\n";
            t += "#fs11##L0##b<Frozen Link>I want to enter.\r\n";
            //t += "#fs11##L1##b<프로즌 링크>를 이용한 횟수를 알고 싶어요.#k";
            cm.sendSimple(t);
            break;
        case 1:
            sel = selection;
            if (selection == 0) {
                var qwer = cm.findFrozenMap(); 
                if (qwer != 0 && cm.haveItem(4310237, 500)) {
                    cm.warp(qwer);
                } else {
                    cm.sendOk("#fs11#The frozen link is full or there is no frozen coin. (500 required)");
                }
                cm.dispose();

            } else if (selection == 1) {
                cm.sendOk("Remaining frequency check function is being prepared!");
                cm.dispose();
            }
            break;
    }
}