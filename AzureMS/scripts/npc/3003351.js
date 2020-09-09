var status = -1;

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
          
	var chat = "#fnSharing Gothic ExtraBold##fs15#Free skill purchase#Cgray# #fs10##r#k#k#fs11#";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L4##d Purchase 200 Million Extra Damage #e#r(10000P)#l#n#k";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L5##d Purchase 600 million additional damage #e#r(30000P)#l#n#k";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L0##d Purchase 1.2 billion additional damage #e#r(50000P)#l#n#k";
	//chat += "\r\n#L1##d Purchase additional 2.4 billion damage #e#r(100000P)#l#n#k";
            //chat += "\r\n#L2##d Buy 5 billion additional damage #e#r(200000P)#l#n#k";
            //chat += "\r\n#L3##d Purchase additional 8 billion damage #e#r(300000P)#l#n#k\r\n\r\n\r\n";
	//chat += "#fnSharing Gothic ExtraBold##fs15#Donation Special List#Cgray# #fs10##r(Special)#k#fs11#";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L20##b#s5321054# Purchase additional damage per room#k#e#r(200000P)#l#n#k";
	
	chat += "#fnSharing Gothic ExtraBold#\r\n#L26##b#s5321054# #eBuck shot#n Purchase #e#r(300000P)#l#n#k";
	chat += "#fnSharing Gothic ExtraBold#\r\n#L21##b#s2311003# #eHoly symbol#n Purchase #e#r(200000P)#l#n#k";
	//if (cm.getPlayer().isGM()) {
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L22##b#s2121054# #eFire aura#n Purchase #e#r(300000P)#l#n#k";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L27##b#s65111007# #eSoul seeker#n Purchase #e#r(300000P)#l#n#k";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L23##b#s400051015# #eSerpent Screw#n Purchase #e#r(300000P)#l#n#k";
	chat += "#fnSharing Gothic ExtraBold#\r\n#L24##b#s2001002# #eMagic Guard#n Purchase #e#r(100000P)#l#n#k";
	//chat += "#fnSharing Gothic ExtraBold#\r\n#L25##b#s13120003# #eTripling 윔#n Purchase #e#r(300000P)#l#n#k";
	//}
    
	cm.sendSimple(chat);
    } else if (status == 1) {
        select = selection;
	var now = cm.parseLong(cm.getPlayer().getKeyValue("rcDamage"));
	if (now == -1) {
		now = 0;
	}
        if (select == 0) {
		if (cm.getPlayer().getRC() < 50000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-50000);
		now += 1200000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 1.2 billion damage using Sponsored Points.\r\n#r Current total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 1) {
		if (cm.getPlayer().getRC() < 100000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-100000);
                now += 2400000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 2.4 billion damage using Sponsored Points.\r\n#r Current total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 2) {
		if (cm.getPlayer().getRC() < 200000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-200000);
                now += 5000000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 5 billion damage using Sponsored Points.\r\n#r Current total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 3) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
                now += 8000000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 8 billion damage using Sponsored Points.\r\n#rCurrent total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 4) {
		if (cm.getPlayer().getRC() < 10000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-10000);
                now += 200000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 200 million damage using Sponsored Points.\r\n#rCurrent total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 5) {
		if (cm.getPlayer().getRC() < 30000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-30000);
                now += 600000000;
	        cm.getPlayer().setKeyValue("rcDamage", now);
	        cm.sendOk("Added 600 million damage using Sponsored Points.\r\n#rCurrent total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 10) {
		if (cm.getPlayer().getRC() < 50000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-50000);
                now += 1;
	        cm.getPlayer().setKeyValue("rcDamageX", now);
	        cm.sendOk("Added 1 additional damage stroke using Donation Point.\r\n#rCurrent total damage : " + cm.getPlayer().getKeyValue("rcDamage"));
		cm.dispose();
	}
        if (select == 20) {
		if (cm.getPlayer().getRC() < 200000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-200000);
		if (cm.getPlayer().getKeyValue("qkdekdcnepa") != "buy") {
			cm.getPlayer().gainRC(-0);
	        	cm.getPlayer().setKeyValue("qkdekdcnepa", "buy");
	        	cm.sendOk("Damage per room has been paid.");
			cm.dispose();
		} else {
	        	cm.sendOk("Duplicate purchases are not possible.");
			cm.dispose();
		}
	}
        if (select == 21) {
		if (cm.getPlayer().getRC() < 200000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-200000);
		cm.getPlayer().buybuff(2311003);
	        cm.sendOk("Purchased Holly Symbol using Sponsor Point.");
		cm.dispose();
	}
        if (select == 26) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
		cm.getPlayer().buybuff(5321054);
	        cm.sendOk("Purchased a buck shot using sponsored points.");
		cm.dispose();
	}
        if (select == 22) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getKeyValue("fireaura2") != null) {
			cm.sendOk("Double purchase is not possible.");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
		cm.teachSkill(2121054, 1, 1);
		cm.getPlayer().setKeyValue("fireaura2", "ok");
	        cm.sendOk("Purchased Fire Aura using Ponation Points. \r\nCan be used with @Fire Aura // Fire Aura Off commands.");
		writelog("Fire aura");
		cm.dispose();
	}
        if (select == 23) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getKeyValue("scrue2") != null) {
			cm.sendOk("Double purchase is not possible..");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
		cm.teachSkill(400051015, 1, 1);
		cm.getPlayer().setKeyValue("scrue2", "ok");
	        cm.sendOk("Purchased Serpent Screws with Sponsor Points.\r\nCan be used with @ SerpentScrewon / @ SerpentScrewOff commands.");
		writelog("Serpent screw");
		cm.dispose();
	}
        if (select == 27) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getKeyValue("scrue2") != null) {
			cm.sendOk("Double purchase is not possible..");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
		cm.teachSkill(65111007, 1, 1);
		cm.getPlayer().setKeyValue("scrue2", "ok");
	        cm.sendOk("Purchased Serpent Screws with Sponsor Points.\r\nCan be used with @ SerpentScrewon / @ SerpentScrewOff commands.");
		writelog("Serpent screw");
		cm.dispose();
	}
        if (select == 24) {
		if (cm.getPlayer().getRC() < 100000) {
			cm.sendOk("Not enough points to buy the selected item.\r\nCan be used as @magic guard on / @ magic guard off command.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getKeyValue("magicg2") != null) {
			cm.sendOk("Double purchase is not possible..");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-100000);
		cm.teachSkill(2001002, 1, 1);
		cm.getPlayer().setKeyValue("magicg2", "ok");
	        cm.sendOk("Purchased Magic Guard using Donation Points.\r\nCan be used as @magic guard on / @ magic guard off command.");
		writelog("Magic Guard");
		cm.dispose();
	}

        if (select == 25) {
		if (cm.getPlayer().getRC() < 300000) {
			cm.sendOk("Not enough points to buy the selected item.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getKeyValue("Donate_Trifle") != null) {
			cm.sendOk("Double purchase is not possible..");
			cm.dispose();
			return;
		}
		cm.getPlayer().gainRC(-300000);
		cm.teachSkill(13120003, 20, 20);
		cm.getPlayer().setKeyValue("Donate_Trifle", "ok");
	        cm.sendOk("Purchased Trifling Soap using Donation Points.");
		writelog("Tripling 윔");
		cm.dispose();
	}
    } else if (status == 2) {
        if (select == 1) {
            cm.dispose();
        }
    }
}

function writelog(t) {
           	a = new Date();
	temp = Randomizer.rand(0,9999999);
	cn = cm.getPlayer().getName();
           fFile1 = new File("property/Logs/후원스킬/"+t+"/"+temp+"_"+cn+".log");
           if (!fFile1.exists()) {
               	fFile1.createNewFile();
           }
           out1 = new FileOutputStream("property/Logs/후원스킬/"+t+"/"+temp+"_"+cn+".log",false);
	   	var msg =  "'"+cm.getPlayer().getName()+"'S Purchased.\r\n";
           	msg += "'"+a.getFullYear()+"년 " + Number(a.getMonth() + 1) + " Month " + a.getDate() + " Date'\r\n";
		msg += "Purchased skill : "+t+"\r\n";
            	out1.write(msg.getBytes());
            	out1.close();
}
