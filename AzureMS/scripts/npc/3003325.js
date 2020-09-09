importPackage(java.sql);

importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

//Reward Achievement (adddmg: Additional Damage, addatk: Additional Strike (0 if not))
var enter = "\r\n";
var seld = -1;
var reward = [
	{'adddmg' : 1000000, 'itemid' : 1112662, 'allstat' : 100, 'atk' : 100}, // 1 Castle All Stat ATT
	{'adddmg' : 2000000, 'itemid' : 1032110, 'allstat' : 200, 'atk' : 200}, // 2 castle
	{'adddmg' : 5000000, 'itemid' : 1122149, 'allstat' : 300, 'atk' : 300}, // 3 castle
	{'adddmg' : 7000000, 'itemid' : 1132104, 'allstat' : 400, 'atk' : 400}, // 4 castle
	{'adddmg' : 10000000, 'itemid' : 1112908, 'allstat' : 500, 'atk' : 500}, // 5 castle
	{'adddmg' : 20000000, 'itemid' : 1332063, 'allstat' : 600, 'atk' : 600}, // 6 castle
	{'adddmg' : 70000000, 'itemid' : 1332063, 'allstat' : 700, 'atk' : 700} // 7castle
]

var fourth = [
	{'item' : 4310218, 'qty' : 10},
	{'item' : 4310218, 'qty' : 20}
] // 4 star guitar

var sixth = [
	{'itemid' : 1005003, 'allstat' : 1000, 'atk' : 1000},
	{'itemid' : 1053219, 'allstat' : 1000, 'atk' : 1000},
	{'itemid' : 1053220, 'allstat' : 1000, 'atk' : 1000}
]

var seldsix = -1;
var issix = false;
var isfour = false;
function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		if (cm.getQuestRecord(201801).getCustomData() == null) {
			cm.sendOk("#fs11#Does not match current rating");
			cm.dispose();
			return;
		}
		//+Integer.parseInt(cm.getQuestRecord(201801).getCustomData())
		var msg = "#fs11# #dGreetings. I am Azure's Rebirth Tier Advancer!\r\n Please select to upgrade your tier."+enter;
		for (i = 0; i < reward.length; i++) {
			msg += "#L"+i+"#"+getColor(i+1)+(i+1)+"#fs11# Tier Level rewards ("+ getEnable(i+1)  +")#fs12#"+enter;
			//msg += "보상 아이템 : #b#i"+reward[i]['itemid']+"##z"+reward[i]['itemid']+"# (올스텟 : "+reward[i]['allstat']+" 공마 : "+reward[i]['atk']+")#k"+enter+enter;
		}
		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel + 1;
		if (!isGet(seld)) {
			cm.sendOk("#fs11#You have already obtained the rewards.");
			cm.dispose();
			return;
		}
		if (!isOkStar(seld)) {
			var msg = seld+" Tier level are as follows.#fs11##b"+enter;
			msg += " Additional DPS : "+reward[sel]['adddmg']+enter;
			if (seld != 6) {
				if (seld == 4) {
					for (i = 0; i < fourth.length; i++) {
						msg += " Rewards : #i"+fourth[i]['item']+"##z"+fourth[i]['item']+"# "+fourth[i]['qty']+"QTY"+enter;
					}
				} else { 
					msg += " Rewards : #i"+reward[sel]['itemid']+"##z"+reward[sel]['itemid']+"# (All Stats : "+reward[sel]['allstat']+" WA/MA : "+reward[sel]['atk']+")"+enter;
				}
			} else {
				msg += " Rewards"+enter;
				for (i = 0; i < sixth.length; i++) {
					msg += "  #i"+sixth[i]['itemid']+"##z"+sixth[i]['itemid']+"# (All Stats : "+sixth[i]['allstat']+" WA/MA : "+sixth[i]['atk']+")"+enter;
				}
			}
			cm.sendOk(msg);
			cm.dispose();
			return;
		}
		var msg = seld+" Tier Reward requirements are the same.#fs11##b"+enter;
		msg += " #fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# Additional DPS : "+reward[sel]['adddmg']+enter;
		if (seld != 6) {
			if (seld == 4) {
				isfour = true;
				for (i = 0; i < fourth.length; i++) {
					msg += " Reward Item : #i"+fourth[i]['item']+"##z"+fourth[i]['item']+"# "+fourth[i]['qty']+"개"+enter;
				}
			} else {
				msg += " #fs11# Reward Item : #i"+reward[sel]['itemid']+"##z"+reward[sel]['itemid']+"# (All Stats : "+reward[sel]['allstat']+" ATT : "+reward[sel]['atk']+")"+enter;
			}
			msg += " #fs11# Please press 'Yes' to receive the ball.";
			cm.sendYesNo(msg);
		} else {
			issix = true;
			msg += " #fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# Reward Item (Take 1, select item you want.)"+enter;
			for (i = 0; i < sixth.length; i++) {
				msg += "#L"+i+"# ▣ #i"+sixth[i]['itemid']+"##z"+sixth[i]['itemid']+"# (All Stats : "+sixth[i]['allstat']+" ATT : "+sixth[i]['atk']+")"+enter;
			}
			cm.sendSimple(msg);
		}
	} else if (status == 2) {
		if (issix) {
			seldsix = sel;
			var msg = seld+"The list of gender rewards is as follows.#fs11##b"+enter;
			msg += " #fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# Additional Damage : "+reward[seld - 1]['adddmg']+enter;
			msg += " #fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# Reward Item : #i"+sixth[sel]['itemid']+"##z"+sixth[sel]['itemid']+"# (All Stats : "+sixth[sel]['allstat']+" ATT : "+sixth[sel]['atk']+")"+enter;
			msg += "Please press Yes to receive it.";
			cm.sendYesNo(msg);
		} else {
			if (!isGet(seld)) {
				cm.sendOk("#fs11#I have already received this reward.");
				cm.dispose();
				return;
			}
			if (!isOkStar(seld)) {
				cm.sendOk("#fs11#Really "+(seld+1)+"Is it the last name?");
				cm.dispose();
				return;
			}
                	//cm.getPlayer().gainAddDamagein(reward[seld-1]['adddmg'],true);
			if (cm.getPlayer().getKeyValue("rcDamage") == null) cm.getPlayer().setKeyValue("rcDamage", "0");
			cm.getPlayer().setKeyValue("rcDamage", ""+(Long.parseLong(cm.getPlayer().getKeyValue("rcDamage")) + reward[seld-1]['adddmg']));
			if (!isfour) {
			cm.gainItemAllStat(reward[seld-1]['itemid'], 1, reward[seld-1]['allstat'], reward[seld-1]['atk']);
			} else {
				for (i = 0; i < fourth.length; i++) cm.gainItem(fourth[i]['item'], fourth[i]['qty']); 
			}
			cm.getQuestRecord(Integer.parseInt(95951+""+seld)).setCustomData(1);
			cm.sendOk("#fs11# You have earned a Tier Reward");
			cm.dispose();
		}
	} else if (status == 3) {
			if (!isGet(seld)) {
				cm.sendOk("#fs11#I have already received this reward.");
				cm.dispose();
				return;
			}
			if (!isOkStar(seld)) {
				cm.sendOk("#fs11#Really "+(seld+1)+"Is it the last name?");
				cm.dispose();
				return;
			}
                	//cm.getPlayer().gainAddDamagein(reward[seld-1]['adddmg'],true);
			if (cm.getPlayer().getKeyValue("rcDamage") == null) cm.getPlayer().setKeyValue("rcDamage", "0");
			cm.getPlayer().setKeyValue("rcDamage", ""+(Long.parseLong(cm.getPlayer().getKeyValue("rcDamage")) + reward[seld-1]['adddmg']));
			cm.gainItemAllStat(sixth[seldsix]['itemid'], 1, sixth[seldsix]['allstat'], sixth[seldsix]['atk']);
			cm.getQuestRecord(Integer.parseInt(95951+""+seld)).setCustomData(1);
			cm.sendOk("#fs11# You have earned a Tier Reward");
			cm.dispose();
	}
}
function isGet(name) {
	return cm.getQuestRecord(Integer.parseInt(95951+""+name)).getCustomData() == null ? true : false;
//cm.CountCheck(name, 1) ? true : false;
}

function getColor(name) {
	return isGet(name) ? "#b" : "#r";
}

function getEnable(star) {
	var ret = "";
	if (isGet(star) && Integer.parseInt(cm.getQuestRecord(201801).getCustomData()) >= star) ret = "#bComplete#k"; else ret = "#rIncomplete#k";
	return ret;
}
function isOkStar(star) {
	return Integer.parseInt(cm.getQuestRecord(201801).getCustomData()) >= star ? true : false;
}