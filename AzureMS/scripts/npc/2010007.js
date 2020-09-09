importPackage(Packages.community);

var status = 0;
var sel;

function start() {
    cm.sendSimple("How can I help you?\r\n#b#L0#I want to make a guild.#l\r\n#L1#I want to dismantle the guild.#l\r\n#L2#I want to increase the number of players in guild.#l#k");
}

function action(mode, type, selection) {
    if (mode == -1 || (mode == 0 && status < 2)) {
        cm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 1) {
            sel = selection;
            if (selection == 0) {
                if (cm.getPlayer().getGuildId() > 0) {
                    cm.sendOk("You already have guild. Destroy the guild or leave the guild.");
                    cm.dispose();
                } else
                    cm.sendYesNo("Oh! You're here to register a guild ... To register a guild #b  10 million mesos#k needed. I believe you will be ready. Will you make a guild?");
            } else if (selection == 1) {
                if (cm.getPlayer().getGuildId() < 1 || cm.getPlayer().getGuildRank() != 1) {
                    cm.sendOk("Only the guild leader can dismantle the guild.");
                    cm.dispose();
                } else
                    cm.sendYesNo("Do you really want to dismantle the guild? Oops ... these ... Once you dismantle, your guild will be deleted forever. Various guild privileges also disappear with course. Would you still do it?");
            } else if (selection == 2) {
                if (cm.getPlayer().getGuildId() < 1 || cm.getPlayer().getGuildRank() != 1) {
                    cm.sendOk("Only a guild leader can extend a guild.");
                    cm.dispose();
                } else
                    cm.sendYesNo("The number of guild members #b5 #kTo expand #k It's going to cost you #b10 million mesos.");
            }
        } else if (status == 2) {
            if (sel == 0 && cm.getPlayer().getGuildId() <= 0) {
                cm.genericGuildMessage(3);
            } else if (cm.getPlayer().getGuildId() > 0 && cm.getPlayer().getGuildRank() == 1) {
                if (sel == 1) {
                    cm.disbandGuild();
                } else if (sel == 2) {
                    cm.increaseGuildCapacity();
                }
            }
            cm.dispose();
        }
    }
}
