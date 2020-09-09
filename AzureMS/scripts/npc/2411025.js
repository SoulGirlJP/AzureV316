importPackage(Packages.constants);

var status = -1;

º°º¸ = "#fUI/GuildMark.img/Mark/Pattern/00004001/13#"

function start() {
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        var chat = "#fnSharing Ghotic ExtraBold#         #b"+"AzureMS "+"#kConsumption Shop :)\r\n\r\n\r\n";
        chat += "#L1#"+"#d Consumption, Order Form, Small Store\r\n";
        //chat += "#L2#"+"#d Pael & Signage & Elixir & Ark & Anvil & Carter & Ear Sensor\r\n";
        chat += "#L4#"+"#d And, naming your pet\r\n";
        //chat += "#L3#"+"#d Damage skin\r\n";
        //chat += "#L5#"+"#d Damage skin 2\r\n";
        //chat += "#L6#"+"#d Damage Skin (Unit)\r\n";
        cm.sendSimple(chat);

    } else if (status == 1) {
    if (selection == 1) {
       cm.dispose();
       cm.openShop(2232100);

    } else if (selection == 2) {
        cm.dispose();
        cm.openShop(444449);

    } else if (selection == 3) {
        cm.dispose();
        cm.openShop(1064004);
    } else if (selection == 5) {
        cm.dispose();
        cm.openShop(1064005);
    } else if (selection == 6) {
        cm.dispose();
        cm.openShop(1064006);


    } else if (selection == 4) {
        cm.dispose();
        cm.openShop(1103003);

		}
	}
}