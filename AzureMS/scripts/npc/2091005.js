var status = -1;

function start() {
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
	cm.sendYesNo("벌써 포기하는거야? 그러니까 자만하지 말라고 했잖아.");
    } else if (status == 1) {
	cm.dispose();
	cm.warp(925020002);
	cm.openNpcCustom(cm.getClient(), 0, "dojo_exit");
    }
}