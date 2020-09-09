var status = -1;


function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.dispose();
	}
	status--;
    }
    if (status == 0) {
	var chat = "Login points to exchange items to help you in your adventure. There's plenty of time so take a look.\r\n\r\n" + cm.getPlayer().getName() + "Holds login points : #r" + cm.getPlayer().getLoginPoint() + "#k#n\r\n\r\n#L0##bWe exchange item at login point.#l";
	cm.sendSimple(chat);
	cm.dispose();
    }
}
