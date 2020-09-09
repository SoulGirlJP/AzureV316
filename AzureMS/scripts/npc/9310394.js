/*

NPSIAID: 9070007

Nfish Name: Rebecca

Map with nfish:: (310000001)

NPC Description: PC Room

*/

importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(Packages.provider);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server.items);
importPackage(Packages.packet.creators);


var status = -1;
var seld = -1;
var pt = -1;

var oldsel = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
	if (mode == -1) {
	cm.dispose();
	} else {
	if (mode == 0) {
        cm.sendOk("#fs11#Premium benefits are always ready.");
	cm.dispose();            
	return;        
	}        
	if (mode == 4) {
	cm.dispose();            
	return;        
	}        
	if (mode == 1)            
	status++;        
	else          
	status--;    

	if (status == 0) {

		var currentTimeMillis = new Date().getTime();
		pt = cm.getPlayer().getPremiumTime() - currentTimeMillis;
	var text = "#fs11#Good Morning? #bAzure's Premium Benefits#kCurious about?\r\n"
            text+= "#fs11#There are many benefits, so please check it out.\r\n#b"
            text+= "#L100##fs11##i1122017# #rI will use Azure Premium.\r\n"
            text+= "#L202##fs11##i3602003#  I want to use a part-time map\r\n"
            cm.sendSimple(text);

	} else if (status == 1) {
        	if (selection == 200) {
			if ((pt / 1000) < 1) {
                		cm.sendOk("#fs11#Only users who are using the premium service..");
                		cm.dispose();
                		return;
			}
			cm.dispose();
			cm.openNpc(2007);
		} else {
 			var text3 = "#fs11##rAzure Premium Benefits#k\r\n#d- Hire Honey Pendant (20% Bonus Experience) during Premium Hours\r\n- 20% Bonus experience\r\n- Special hunting grounds available (limited time)\r\n#b"
 
            		text3 += "#fs11##L2#I will purchase a premium period.\r\n#l"
            		cm.sendSimple(text3);
		}
	} else if (status == 2) {
                   a = selection;
          if (a == 2) {
              cm.sendGetText("#fs11##r#e< Premium time selection >#n#k\\r\n#i1122017# #b[Honey Premium]\r\n x 3000 honey points per hour#k\r\nAre you sure? Please enter your preferred time.\r\nIf you don't want to buy #rESC#kPlease press.\r\n#b(Immediately after purchase.)\r\n");
          } else {
              cm.sendGetText("#fs11##r#e< Select event duration >#n#k\\r\n#i1122017# #b[Honey Premium]\r\n x per hour #i4000047# 100°³#k\r\nAre you sure? Please enter your preferred date range.\r\nIf you don't want to buy #rESC#kPlease press.\r\n#b(Immediately after purchase.)\r\n");
          }
	
    } else if (status == 3) {
            tnumber = cm.getText();
            if (!(tnumber >= 0 && tnumber <= 100)) {
                cm.sendOk("#fs11#Please enter a number.");
                cm.dispose();
                return;
            }
            if (tnumber <= 0 || tnumber > 100) {
                cm.sendOk("#fs11#You can only buy from 1 to 100 hours at a time.");
                cm.dispose();
                return;
            }
          if (a == 2) {
              cm.sendYesNo("#fs11#\r\n#b#i1122017#[Honey Premium]#k\r\n#e<"+tnumber+">#nIs it time? If so "+(3000*tnumber)+" I'll spend points.");
          } else {
              cm.sendYesNo("#fs11#\r\n#b#i1122017#[Honey Premium]#k\r\n#e<"+tnumber+">#nIs it time? If so #i4000047# "+(100*tnumber)+" I'm going to consume a amount.");
          }
    } else if (status == 4) {
		if ((pt / 1000) >= 1) {
                	cm.sendOk("#fs11#You are already using premium service.");
                	cm.dispose();
                	return;
		}
            if (a == 2) {
            if (cm.getPlayer().getCSPoints(1) < (3000*tnumber)) {
                cm.sendOk("#fs11#Oh, looks like you're running out of points.");
                cm.dispose();
                return;
            }
            cm.getPlayer().modifyCSPoints(1, -(3000*tnumber), true);
            } else {
            if (!cm.haveItem(4000047, (100*tnumber))) {
                cm.sendOk("#fs11#Oh, you think you lack mouse.");
                cm.dispose();
                return;
            }
                cm.gainItem(4000047, -(100*tnumber));
            }
		cm.gainPCTime(tnumber * 3600);
		cm.getPlayer().getPT();
            cm.sendOk("#fs11#So have a good time!");
		cm.getClient().sendPacket(UIPacket.OpenUI(218));
		cm.getPlayer().dropMessage(5, "Azure Premium System "+(tnumber * 3600)+"Seconds left.");
            cm.dispose();
	}
}
}


