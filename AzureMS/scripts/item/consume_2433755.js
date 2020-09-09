var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("Please select your desired item.\r\n\r\n#L0#getKeyValue_User (Get the user's key value.)\r\n#L1#setKeyValue_User (Set the user's key value.)\r\n#L2#Recovery license\r\n#L3#Recovery of the map\r\n#L4#Currency Setting")
    } else if (status == 1) {
        선택 = selection;
        cm.sendGetText("Please enter your username or map code.\r\n\r\n(When recovering a map, the map code, otherwise the username)");
    } else if (status == 2) {
        if (선택 == 3) {
            cm.resetMap(cm.getText());
            cm.sendOk("Complete.");
            cm.dispose();
        } else {
            if (cm.getText() != cm.getPlayer().getName()) {
                target = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(cm.getText());
            } else {
                target = cm.getPlayer()
            }
            if (target != null) {
                if (선택 == 2) {
                    target.setKeyValue("permission_bok9", 1);
                    cm.sendOk("Successful application for restoration.");
                    cm.dispose();
                } else if (선택 == 0) {
                    cm.sendGetText("Please write the name of the key value you want.\r\n\r\n(Type rcDamage to see additional damage.)");
                } else if (선택 == 1) {
                    cm.sendGetText("Please enter the name of the key value you wish to set.\r\n\r\n(Please type rcDamage for additional damage.)");
                } else if (선택 == 4) {
                    cm.sendGetText("Please write the numbers.")
                }
            } else {
                cm.sendOk("The character you entered is not connected to the same channel.");
                cm.dispose();
            }
        }
    } else if (status == 3) {
        if (선택 == 0) {
            cm.sendOk("target : " + target + "\r\nKey Value Name : " + cm.getText() + "\r\nKey value" + target.getKeyValue("" + cm.getText() + "") + "");
            cm.dispose();
        } else if (선택 == 4) {
             target.gainGP(cm.getText());
             cm.sendOk("Complete");
             cm.dispose();
        } else {
            skv = cm.getText();
            cm.sendGetText("Please enter a value to set.");
        }
    } else if (status == 4) {
        target.setKeyValue("" + skv + "", cm.getText());
        cm.sendOk("Complete.");
        cm.dispose();

    }
}
