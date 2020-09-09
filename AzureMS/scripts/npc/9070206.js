

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
       if(cm.getPlayer().getMeso() >= 2147483000) {; numberset = 2147483; } else {; numberset = cm.getPlayer().getMeso() / 150; }
       cm.sendGetNumber("캐시충전을 할 수 있는 컴퓨터다.\r\n모니터 창에 #r1캐시당 150메소#k라고 써져 있는 것 같다.",1,1,numberset);
    } else if (status == 1) {
       cm.gainNX(selection);
       numberset2 = selection * 100
       cm.gainMeso(-numberset2)
       cm.dispose();
    }
}
