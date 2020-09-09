var status = -1;

importPackage(Packages.tools.RandomStream);
importPackage(Packages.client.inventory);
importPackage(java.lang);
importPackage(java.sql);
importPackage(java.util);
importPackage(java.io);
importPackage(Packages.packet.creators);
importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(Packages.client);
importPackage(Packages.client.stats);
importPackage(Packages.launch.world);
importPackage(MainPacketCreator);

function start() {
    status = -1;
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
        cm.sendYesNo("Would you like to open a random box? #bAll inventory space is at least 5 spaces#kRequired. Also, it should be less than 2 billion.");
    } else if (status == 1) {
        var item = "The following items are available from Cody's exclusive system.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n\r\n";
	var leftslot1 = cm.getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot();
        if (leftslot1 < 5) {
            cm.sendOk("You need at least 5 spaces for inventory. Free up at least 5 spaces on the installation tab and reopen it.");
            cm.dispose();
            return;
        }
	var leftslot3 = cm.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot();
        if (leftslot3 < 5) {
            cm.sendOk("You need at least 5 spaces for inventory. Free up at least 5 spaces on the Equipment tab and reopen it.");
            cm.dispose();
            return;
        }
        if (cm.getMeso() >= 9000000000) {
            cm.sendOk("You have too much mesos on you.\r\nPlease put in mesos in the storage.\r\n#rYou need to have lower than 9 bil in your inventory.");
            cm.dispose();
            return;
        }
        
        
        var cube = new Array(new Array(5062006, 5),new Array(5062006, 10),new Array(5062009, 30),new Array(5062009, 75),new Array(5062010, 30),new Array(5062010, 75),new Array(5062500, 30),new Array(5062500, 75));
         
        var orbs = new Array(new Array(4310184, 600), new Array(4310184, 1000), new Array(4310185, 600), new Array(4310185, 1000));
        
        cm.gainItem(2430495, -1);
        for (var i = 0; i < 2 + Randomizer.nextInt(3); i++) {
            var rand = Math.floor(Math.random() * 30);
            
             if (rand < 15) {
                var meso = 0;
                if (Randomizer.isSuccess(95)) {
                    meso = 5000000;
                }
                else if (Randomizer.isSuccess(75)){
                    meso = 10000000;
                }
                else if (Randomizer.isSuccess(50)){
                    meso = 25000000;
                }
                else if (Randomizer.isSuccess(25)){
                    meso = 50000000;
                }
                else if (Randomizer.isSuccess(15)){
                    meso = 250000000;
                }
                else if (Randomizer.isSuccess(5)) {
                    meso = 1500000000;
                    WorldBroadcasting.broadcastMessage(MainPacketCreator.getGMText(7, c.getPlayer().getName() + "Has won the 1.5 bil in donation box Congratz~!."));
                }
                else {
                    meso = 1500000;
                    
                }
                item += "#e#b"+meso+"#k#n Meso\r\n";
                cm.gainMeso(meso);
                }
            
            /*else if (rand < 10) {
                var calculate = Math.floor(Math.random() * normalequip.length);
                item += "#i"+normalequip[calculate]+"# #z"+normalequip[calculate]+"# 1 QTY\r\n";
                cm.gainItem(normalequip[calculate], 1);
            } else if (rand < 15) {
                var calculate = Math.floor(Math.random() * rareequip.length);
                item += "#i"+rareequip[calculate]+"# #z"+rareequip[calculate]+"# 1 QTY\r\n";
                cm.gainItem(rareequip[calculate], 1);
            } else if (rand < 20 && Randomizer.isSuccess(50)) {
                var calculate = Math.floor(Math.random() * rareacc.length);
                item += "#i"+rareacc[calculate]+"# #z"+rareacc[calculate]+"# 1 QTY\r\n";
                cm.gainItem(rareacc[calculate], 1);
            }*/ else if (rand < 25) {
                var calculate = Math.floor(Math.random() * cube.length);
                item += "#i"+cube[calculate][0]+"# #t"+cube[calculate][0]+"# "+cube[calculate][1]+" QTY\r\n";
                cm.gainItem(cube[calculate][0], cube[calculate][1]);
            } 
             else {
              var calculate = Math.floor(Math.random() * orbs.length);
                item += "#i"+orbs[calculate][0]+"# #t"+orbs[calculate][0]+"# "+orbs[calculate][1]+" QTY\r\n";
                cm.gainItem(orbs[calculate][0], orbs[calculate][1]);
            }
        }
        cm.sendOk("Here is your random Rewards!");
        cm.dispose();
    } 

}


