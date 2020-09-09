/*
제작 : (Dhae) dhae0107
목적 : 공부및 배포용
코드 : 11100 (@변생[변생하기] - 루시)
설명 : 변생 진행 엔피시
*/

importPackage(Packages.constants);
importPackage(Packages.server.items);
importPackage(Packages.client.items);
importPackage(java.lang);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);
importPackage(Packages.client);

별 = "#fUI/UIToolTip/Item/Equip/Star/Star#"
숫자1 = "#fUI/UIWindow4.img/monsterCollection/number/1#"
숫자2 = "#fUI/UIWindow4.img/monsterCollection/number/2#"

후포 = (50000); // Currency that is necessary for rebirth
변렙 = (250) // Transformation level
이동 = (109090200) // Where is former N Fish



function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
    	cm.dispose();	
        return;	
    }
    if (status == 3 && mode == 0) {
    	cm.sendOk("Please choose carefully.");	
        cm.dispose();	
        return;	
    }
    if (mode == -1) {
        cm.dispose();	
        return;
    
    }
    if (mode == 0) {
    	status --;	
    }
    if (mode == 1) {
    	status++;	
    }

if (status == 0) {
	cm.sendOk("#r#e※Cautions when changing※#k#n\n#fs20#\r\n\r\n"+숫자1+"#fs# Before rebirth #b#eUnmount all items being equipped#n#k Do.\r\n"+숫자2+"\r\n\r\n#fnSunny gothic##e#d< We are not responsible for any disadvantages caused by not knowing the above items.\r\n#r#eYou will not gain Rebirth Points if you change job.\r\nBecause this is basically a reward.");
        //cm.sendOk("#rSoon Re-enabled.");
	
}else if(status == 1){
	말 = "Hi #b#h ##ksir\r\n\r\n"
	말+= "#fnSunny gothic##k#n#e#b#h ##k'S level : #d"+cm.getPlayerStat("LVL") + " / "+변렙+  "#n#k#fn#\r\n\r\n"
	말+= "#L0#"+별+" #b#eWe proceed transformation#n I'll do it.\r\n"
	말+= "#L1#"+별+" #eWhat is rebirth #nIs it?"
	cm.sendSimple(말);


}else if(status == 2){
	if (selection == 0) {
	if (cm.getPlayer().getLevel() >= 변렙) {
	말 = "#b#e< Adventurer & Cygnus >#k#n\r\n"
	말+= "#L0# Adventurer #L301# Pathfinder #L1000# Cygnus #L5000# Mikhail \r\n\r\n\r\n#l"
	말+= "#b#e< Hero Class >#k#n\r\n"
	말+= "#L2000# Aran #L2001# Evan #L2002# Mercedes #L2003# Phantom #L2004# Luminous #L2005# Silverwall\r\n\r\n\r\n#l"
	말+= "#b#e< Resistance >#n#k\r\n"
	말+= "#L3000# Resistance #L3001# Daemon #L3002# Xenon\r\n\r\n\r\n#l"
	말+= "#b#e< Nova >#n#k\r\n"
	말+= "#L6000# Kaiser #L6001# Angelic Buster #L6002# Cadena\r\n\r\n\r\n#l "
	말+= "#b#e< Lev >#n#k\r\n"
	말+= "#L15500# Ark \r\n\r\n\r\n#l "
	cm.sendSimple(말);
}else{
	cm.sendOk("#e#b#h ##k'S Level : #r"+cm.getPlayerStat("LVL")+"#k / #dRequired level#k :#r "+변렙+"");
	cm.dispose();
    }

	}else if(selection == 1) {
	cm.sendOk("#e#b< With Rebirth & Changing Job? >#n#k\r\n\r\nGo back to level 10\r\n#r#eHaving a new class#n#k.\r\nWhen chose another job..\r\n#r#e[Keystored] skills are reset.\r\n#b#e*Hint* (Except Phantom skills)#n#k.");
	cm.dispose();
    }


}else if(status == 3){
	직업 = selection;
	if (직업 == 0) {
	이름 = "#b< Adventurer >#k가" 
	}else if(직업 == 1000) {
	이름 = "#b< Cygnus >#k가"
	}else if(직업 == 301) {
	이름 = "#b< Pathfinder >#k가"
	}else if(직업 == 2000) {
	이름 = "#b< Aran >#k이"
	}else if(직업 == 2001) {
	이름 = "#b< Evan >#k이"
	}else if(직업 == 2002) {
	이름 = "#b< Mercedes >#k가" 
	}else if(직업 == 2003) {
	이름 = "#b< Phantom >#k이"
	}else if(직업 == 2004) {
	이름 = "#b< Luminous >#k가"
	}else if(직업 == 2005) {
	이름 = "#b< Silverwall >#k이"
	}else if(직업 == 3000) {
	이름 = "#b< Resistance >#k가"
	}else if(직업 == 3001) {
	이름 = "#b< Daemon >#k가"
	}else if(직업 == 3002) {
	이름 = "#b< Xenon >#k이"
	}else if(직업 == 5000) {
	이름 = "#b< Mikhail >#k이"
	}else if(직업 == 6000) {
	이름 = "#b< Kaiser >#k가"
	}else if(직업 == 6001) {
	이름 = "#b< Angelic Buster >#k가"
	}else if(직업 == 6002) {
	이름 = "#b< Cadena >#k가"
	}else if(직업 == 15500) {
	이름 = "#b< Ark >#k가"
	}if (직업 != 1){
	cm.sendYesNo("The job you choose "+이름+" Is that correct?");}


}else if(status == 4){
    
        cm.getPlayer().setReborns(cm.getPlayer().getReborns() + 1);
        cm.warp(이동);
	cm.unequipEverything();
	//cm.getPlayer().skillReset();
	cm.changeJob(직업);
	cm.getPlayer().setLevel(1);
        
        //cm.getPlayer().PhantomSkillDelete();
	cm.fakeRelog();
	cm.updateChar();
        cm.sendOk("#fs 50##fnRebirth# #bSuccessful!#k");;
        cm.dispose();
}
}