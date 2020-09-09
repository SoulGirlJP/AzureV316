var status = -1;
var qt = 10;
var st1 = 0;
var st2 = 0;
var st3 = 0;

litem = [1113089, 1032227, 1122274];
qty = [0,0,0];


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
	if (status >= 3 && status <= 5) {
	    qt -= selection;
	    qty[status - 3] = selection;
	    if (qt <= 0) {
		status = 5;
	    }
        }
        status++;
    }

    if (status == 0) {
        var text = "Flowers, butterflies and people are all gone. All that's left is burning flames ... Why did this beautiful castle change like this?.\r\n\r\n";
        text += "#fUI/UIWindow.img/UtilDlgEx/list1#\r\n";
        text += "#L0##dIpia's Hope";
        cm.sendOk(text);
    } else if (status == 1) {
        qt2 = cm.itemQuantity(litem[0]) + cm.itemQuantity(litem[1]) + cm.itemQuantity(litem[2]);
        if (qt2 < 10) {
            var text = "Please bring the jewelry I made in my life.\r\n\r\n";
            text += "#d * Must have at least 10 total jewelry\r\n";
            text += "#i" + litem[0] + "# #b#z" + litem[0] + "# #r#c" + litem[0] + "# QTY#k Possesion\r\n";
            text += "#i" + litem[1] + "# #b#z" + litem[1] + "# #r#c" + litem[1] + "# QTY#k Possesion\r\n";
            text += "#i" + litem[2] + "# #b#z" + litem[2] + "# #r#c" + litem[2] + "# QTY#k Possesion\r\n";
            cm.sendOk(text);
            cm.dispose();
            return;
        } else {
            cm.sendNext("You have enough jewelry that I made in my life.");
        }
    } else if (status == 2) {
        if (qt2 >= 10) {
            cm.sendNext("Please tell us how many rings / earrings / pendants you will use to create your new ring.");
        } else {
            cm.sendOk("Unexpected value detected and exit script");
            cm.dispose();
            return;
        }
    } else if (status == 3) {
            var text = "Several #bRing#k Would you like to use?\r\n";
            text += "Ipia's Ring : #c" + litem[0] + "# QTY\r\n";
            text += "Additional Accessories Needed : " + qt + " QTY";
            if (cm.itemQuantity(litem[0]) > qt) {
                cm.sendGetNumber(text,cm.itemQuantity(litem[0]),0,qt);
            } else {
                cm.sendGetNumber(text,cm.itemQuantity(litem[0]),0,cm.itemQuantity(litem[0]));
            }
    } else if (status == 4) {
            var text = "Several #bEarrings#k Would you like to use?\r\n";
            text += "Ipia Earrings I currently own : #c" + litem[1] + "# QTY\r\n";
            text += "Additional Accessories Needed : " + qt + " QTY";
            if (cm.itemQuantity(litem[1]) > qt) {
                cm.sendGetNumber(text,cm.itemQuantity(litem[1]),0,qt);
            } else {
                cm.sendGetNumber(text,cm.itemQuantity(litem[1]),0,cm.itemQuantity(litem[1]));
            }
    } else if (status == 5) {
            var text = "Several #bPendant#k Would you like to use?\r\n";
            text += "Ipia Necklace : #c" + litem[2] + "# QTY\r\n";
            text += "Additional Accessories Needed : " + qt + " QTY";
            if (cm.itemQuantity(litem[1]) > qt) {
                cm.sendGetNumber(text,cm.itemQuantity(litem[2]),0,qt);
            } else {
                cm.sendGetNumber(text,cm.itemQuantity(litem[2]),0,cm.itemQuantity(litem[2]));
            }
    } else if (status == 6) {
            if (qt != 0) {
               cm.sendOk("The number of ornaments you entered is not enough. Please enter a total of 10.");
               cm.dispose();
               return;
            }
            var text = "Do you want to proceed with the next quantity?\r\n";
            text += "#r#e(Caution! Items will disappear in order from the first column of inventory.)#k#n\r\n";
            text += "#z" + litem[0] + "# : #b" + qty[0] + " QTY#k\r\n";
            text += "#z" + litem[1] + "# : #b" + qty[1] + " QTY#k\r\n";
            text += "#z" + litem[2] + "# : #b" + qty[2] + " QTY#k\r\n";
            cm.sendYesNo(text);
    } else if (status == 7) {
            cm.sendOk("I handed you a ring of my wishes. Please let Leon feel my presence...");
            cm.gainItem(litem[0],-qty[0]);
            cm.gainItem(litem[1],-qty[1]);
            cm.gainItem(litem[2],-qty[2]);
            cm.gainItem(1113282,1);
            cm.dispose();
    }
}