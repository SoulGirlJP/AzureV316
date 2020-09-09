function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, sel) {
    if (mode == 1) {
        status++;
    } else {
        cm.sendOk("잘 생각했어YO! 더 놀다오도록 해YO!");
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendYesNo("행성 #b#e비타#k#n에 착륙하시겠나YO?");
    } else if (status == 1) {
        cm.warp(993016000);
        cm.dispose();
    }
}