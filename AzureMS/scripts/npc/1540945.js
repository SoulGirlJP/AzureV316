importPackage(Packages.connections.Packets);
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
        cm.sendSimple("#fs11#Champion, what do you want to do??\r\n\r\n#L0##bI want to strengthen or produce V core.#l\r\n#L1#It's nothing. The weather is nice.");
    } else if (status == 1) {
        if (selection == 0) {
            cm.getClient().getSession().writeAndFlush(UIPacket.openUI(1131));
            cm.dispose();
        } else if (selection == 1) {
            cm.sendOk("#fs11#In this vicinity, the climate changes drastically as Erda flows. Take care.");
            cm.dispose();
        }
    }
}