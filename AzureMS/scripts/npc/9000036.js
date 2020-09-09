var status = -1;

function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1 && type != 1) {
        status++;
    } else {
        if (type == 1 && mode == 1) {
            status++;
            selection = 1;
        } else if (type == 1 && mode == 0) {
            status++;
            selection = 0;
        } else {
            cm.dispose();
            return;
        }
    }
    if (status == 0) {
        var chat = "#fnSharing Ghotic Extrabold##fs13# Would you like to let AzureMS know your strength?";
	chat += "\r\n#b#L0#I wonder what the damage meter is.#l";
	chat += "\r\n#L1#We update damage amount meter.#l";
	chat += "\r\n#L2#I want to check the damage meter ranking.#l";
	cm.sendSimple(chat);
    } else if (status == 1) {
	if (selection == 0) {
		cm.sendOk("#fnSharing Ghotic Extrabold##fs13# Damage amount meter is a system that saves cumulative damage by pouring all fire power for 3 minutes for unlimited orange mushroom. The cumulative damage of the damage meter is divided into rankings.");
		cm.dispose();
	} else if (selection == 1) {
		if (cm.getPlayerCount(120000102) > 0) {
			cm.sendOk("Another user is already updating the damage meter.");
			cm.dispose();
			return;
		}
		cm.startDamageMeter();
		cm.sendOk("#fnSharing Ghotic Extrabold##fs13# The deal meter does not update when you leave the map or exit the game within the time limit.");
		cm.dispose();
	} else if (selection == 2) {
		cm.sendOk(cm.DamageMeterRank());
		cm.dispose();
	}
    }
}