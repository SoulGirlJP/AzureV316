importPackage(Packages.client);
importPackage(Packages.constants);

var status = -1;
var sel = 0;
var name = "";

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 1) {
        status++;
    } else {
	cm.dispose();
	return;
    }

    if (status == 0) {
	var chat= "Put your heart in the ring. A ring more beautiful than the stars in the sky.";
	chat += "\r\n\r\n";
	chat += "¡¡¡ß #b#h # Donation points from : #e#r"+cm.getRC()+"#n#k#b\r\n";
	chat += "#L1112001# #i1112001# #b#z1112001##k #e: #r30000 points#n#b#l\r\n";
	chat += "#L1112002# #i1112002# #b#z1112002##k #e: #r30000 points#n#b#l\r\n";
	chat += "#L1112003# #i1112003# #b#z1112003##k #e: #r30000 points#n#b#l\r\n";
	//chat += "#L1112801# #i1112801# #b#z1112801##k #e: #r30000 points#n#b#l\r\n";
	//chat += "#L1112007# #i1112007# #b#z1112007##k #e: #r10000 points#n#b#l\r\n";
	//chat += "#L1112012# #i1112012# #b#z1112012##k #e: #r10000 points#n#b#l\r\n";
	//chat += "#L1112013# #i1112013# #b#z1112013##k #e: #r10000 points#n#b#l\r\n";
	//chat += "#L1112006# #i1112006# #b#z1112006##k #e: #r10000 points#n#b#l\r\n";
	//chat += "#L1112005# #i1112005# #b#z1112005##k #e: #r10000 point#n#b#l\r\n";
	cm.sendSimple(chat);
    } else if (status == 1) {
	if (cm.getRC() < 30000) {
		cm.sendOk(cm.getPlayer().getName() + "I don't think you can make a ring with your donation points(You Poor)");
		cm.dispose();
		return;
	}
	sel = selection;
	cm.sendGetText("Please enter the name of the person who will hand you the ring");
    } else if (status == 2) {
	name = cm.getText();
	cm.sendYesNo("Your chosen ring [#b#i" + sel + "# #z" + sel + "##k] Do you really want to produce?");
    } else if (status == 3) {
	var id = MapleCharacterUtil.getIdByName(cm.getText());
	if (id <= 0) {
		cm.sendOk("Entered " + name + "Is a nonexistent person. Please check your nickname again.");
		cm.dispose();
		return;
	}
	var chr = cm.getChar(id);
	if (chr == null) {
		cm.sendOk("The person who receives the ring isn't connected " + name + "Please come back when you log in.");
		cm.dispose();
		return;
	}
	if (!(chr.getInventory(GameConstants.getInventoryType(sel)).getNextFreeSlot() > -1)) {
		cm.sendOk("Not enough inventory space for ring.");
		cm.dispose();
		return;
	}
	if (!cm.canHold(sel)) {
		cm.sendOk("You need at least one space in your inventory to get a ring.");
		cm.dispose();
		return;
	}
	cm.makeRing(sel,chr);
	cm.loseRC(30000);
	cm.dispose();
    }
}
