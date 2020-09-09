// GM Commands Helper
// Script 9900002


importPackage(java.sql);
importPackage(Packages.packet.creators);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
importPackage(Packages.database);
importPackage(Packages.constants);
importPackage(Packages.client.stats)
importPackage(Packages.client)
importPackage(Packages.tools.RandomStream);


var enter = "\r\n";
var seld = -1;
//var transcendence = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM characters WHERE gm = 0 ORDER BY transcendence DESC LIMIT 10").executeQuery();

function start() {
	status = -1;
	action (1, 0, 0);
}

function action(mode, type , selection) {
    
    if (mode == 1) {
        status++;
        
    }else{
        status--;
        cm.dispose();
         }
         
    if (status == 0) {
        if (cm.getPlayer().getGMLevel() >= 5) {
        var msg ="#fUI/FarmUI.img/objectStatus/star/whole# ";
        msg += "#fs11##rGM OPTIONS #r#k#fUI/FarmUI.img/objectStatus/star/whole# "+enter;
        msg += "#L0##fUI/FarmUI.img/objectStatus/star/whole# GM Commands List\r\n";
        msg += "#L1##fUI/FarmUI.img/objectStatus/star/whole# GM Changes List\r\n";
        cm.sendSimple(msg);
        }
        
    }
    
    else if (status == 1) {
        
        seld = selection;
        switch (selection){
            case 0:
        var msg ="#fUI/FarmUI.img/objectStatus/star/whole# ";
        msg += "#fs11##rGM Commands List#k "+enter;
        msg += "#L2##fUI/FarmUI.img/objectStatus/star/whole# List 1\r\n";
        msg += "#L3##fUI/FarmUI.img/objectStatus/star/whole# List 2\r\n";
        cm.sendSimple(msg);
                break;
                
            case 1:
                cm.gainMeso(-999999999);
                cm.sendOk("Hello Fweesh");
                cm.dispose();
                break;
            
        }
        
        
    }
    
    else if(selection == 2) {
        var msg = "Here is List 1" +enter;
        msg += "!#rsethpmp#k (Sets an amount of hp and mp)\r\n";
        msg += "!#rmaxhpmp#k (maxes hp and mp)\r\n";
        msg += "!#rskill#k (Levels a particular skill)\r\n";
        msg += "!#rmaxstats#k (Maxes Stats)\r\n";
        msg += "!#rstatreset#k (Resets Stats)\r\n";
        msg += "!#rsp#k (Set the amount of sp you want)\r\n";
        msg += "!#rjob#k (Changes job id)\r\n";
        msg += "!#rcm#k (Outputs the Map ID)\r\n";
        msg += "!#rsaveall#k (Self Explanatory)\r\n";
        msg += "!#rmeso#k (Maxes you out of mesos)\r\n";
        msg += "!#ritem#k (Gives you an item with the ID)\r\n";
        msg += "!#rdrop#k (Drops the Item with the ID)\r\n";
        msg += "!#rlevel#k (Changes your level)\r\n";
        msg += "!#ronline#k (Outputs people online in 1 Channel)\r\n";
        msg += "!#ronlineall#k (Outputs people online in all Channels\r\n";
        msg += "!#rclear#k <all/equipped/equip/use/etc/setup/cash>#k\r\n";
        msg += "!#rbag#k (I have no clue on what it does)\r\n";
        msg += "!#rwords#k <1~9> (Color of chat)\r\n";
        msg += "!#rhide#k (No clue on what it does)\r\n";
        msg += "!#rmaxskill#k (Maxes all skils with Job ID)\r\n";
        msg += "!#rreputation#k (Gives you Reputation)\r\n";
        msg += "!#rdamage#k (Gives you Additional Damage i believe)\r\n";
        msg += "!#rkill#k (Kill a player)\r\n";
        msg += "!#rcash#k (Gives you the amount of NX)\r\n";
        msg += "End of List 1";
        cm.sendOk(msg);
 
    }
    
    else if(selection == 3) {
        var msg = "Here is List 2" +enter;
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        msg += "";
        
        msg += "End of List 2";
        cm.sendOk(msg);
    }
    
    else if (status == 2) {
        cm.dispose();
        
        
    }
}

