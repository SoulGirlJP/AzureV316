importPackage(java.sql);

importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.client.items);

// 달성 보상 관련 (adddmg : 추가데미지, addatk : 추가퍼센트 (없으면 0))
var enter = "\r\n";
var seld = -1;
var reward = [
	{'adddmg' : 200000000, 'itemid' : 1142900, 'allstat' : 150, 'atk' : 50,'addatk' : 20}, // 1성 올스탯 공마
	{'adddmg' : 200000000, 'itemid' : 1142901, 'allstat' : 200, 'atk' : 50, 'addatk' : 30}, // 2성
	{'adddmg' : 200000000, 'itemid' : 1142902, 'allstat' : 250, 'atk' : 80, 'addatk' : 50}, // 3성
	{'adddmg' : 300000000, 'itemid' : 1142903, 'allstat' : 300, 'atk' : 80, 'addatk' : 50}, // 4성
	{'adddmg' : 300000000, 'itemid' : 1142904, 'allstat' : 400, 'atk' : 120, 'addatk' : 100}, // 5성
	{'adddmg' : 300000000, 'itemid' : 1142905, 'allstat' : 500, 'atk' : 120, 'addatk' : 150}, // 6성
	{'adddmg' : 500000000, 'itemid' : 1142906, 'allstat' : 700, 'atk' : 200, 'addatk' : 150}, // 7성
	{'adddmg' : 500000000, 'itemid' : 1142907, 'allstat' : 900, 'atk' : 200, 'addatk' : 250}, // 8성
	{'adddmg' : 500000000, 'itemid' : 1142908, 'allstat' : 1200, 'atk' : 300, 'addatk' : 300}, // 9성
	{'adddmg' : 800000000, 'itemid' : 1142909, 'allstat' : 1500, 'atk' : 300, 'addatk' : 300}, // 10성
	{'adddmg' : 800000000, 'itemid' : 1142910, 'allstat' : 2000, 'atk' : 450, 'addatk' : 400}, // 11성
	{'adddmg' : 800000000, 'itemid' : 1142911, 'allstat' : 2500, 'atk' : 450, 'addatk' : 400}, // 12성
	{'adddmg' : 1000000000, 'itemid' : 1142912, 'allstat' : 3000, 'atk' : 800, 'addatk' : 400}, // 13성
	{'adddmg' : 1000000000, 'itemid' : 1142913, 'allstat' : 4000, 'atk' : 1000, 'addatk' : 500} // 14성
]

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
		var msg = "#fs11# #d안녕하세요. 봄온라인 환생등급 보상 시스템 입니다.\r\n 자신의 등급과 맞는 보상을 선택하여 수령하시길바랍니다."+enter;
		for (i = 0; i < reward.length; i++) {
			msg += "#L"+i+"#"+getColor(i+1)+(i+1)+"#fs11#성 달성 보상 ("+ getEnable(i+1)  +")#fs12#"+enter;
			//msg += "보상 아이템 : #b#i"+reward[i]['itemid']+"##z"+reward[i]['itemid']+"# (올스텟 : "+reward[i]['allstat']+" 공마 : "+reward[i]['atk']+")#k"+enter+enter;
		}
		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel + 1;
		if (!isGet(seld)) {
			cm.sendOk("#fs11#이미 받은 보상입니다.");
			cm.dispose();
			return;
		}
		if (!isOkStar(seld)) {
			var msg = seld+"성 보상 목록은 다음과 같습니다.#fs11##b"+enter;
			msg += " 추가데미지 : "+reward[sel]['adddmg']+enter;
			msg += " #fs11##k#k#d#fs11##fUI/GuildMark.img/Mark/Pattern/00004001/9# 추가퍼센트 : "+reward[seld - 1]['addatk']+ enter ;
			msg += " 보상아이템 : #i"+reward[sel]['itemid']+"##z"+reward[sel]['itemid']+"# (올스텟 : "+reward[sel]['allstat']+" 공마 : "+reward[sel]['atk']+")"+enter;
			cm.sendOk(msg);
			cm.dispose();
			return;
		}
		var msg = seld+"성 보상 목록은 다음과 같습니다.#fs11##b"+enter;
		msg += " #fs11##k#k#d#fs11##fUI/GuildMark.img/Mark/Pattern/00004001/9# 추가데미지 : "+reward[sel]['adddmg']+enter;
		msg += " #fs11##k#k#d#fs11##fUI/GuildMark.img/Mark/Pattern/00004001/9# 추가퍼센트 : "+reward[seld - 1]['addatk']+enter;
		msg += " #fs11# 보상아이템 : #i"+reward[sel]['itemid']+"##z"+reward[sel]['itemid']+"# (올스텟 : "+reward[sel]['allstat']+" 공마 : "+reward[sel]['atk']+")"+enter;
		msg += " #fs11# 정말 받으시려면 '예'를 눌러주세요.";
		cm.sendYesNo(msg);
	} else if (status == 2) {
			if (!isGet(seld)) {
				cm.sendOk("#fs11#이미 받은 보상입니다.");
				cm.dispose();
				return;
			}
			if (!isOkStar(seld)) {
				cm.sendOk("#fs11#정말 "+(seld+1)+"성이 맞는건가요?"); 
				cm.dispose();
				return;
			}
			if (seld != 1) {
				if (cm.getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(reward[seld-2]['itemid']) != null) {
					cm.sendOk("#fs11#이전 단계의 아이템인 #z" + reward[seld-2]['itemid'] + "# 착용을 해제하여주세요.");
					cm.dispose();
					return;
				}
				cm.gainItem(reward[seld-2]['itemid'], -1);
			} else {
				if (cm.getPlayer().getInventory(MapleInventoryType.EQUIPPED).findById(1142282) != null) {
					cm.sendOk("#fs11#이전 단계의 아이템인 #z1142282# 착용을 해제하여주세요.");
					cm.dispose();
					return;
				}
				cm.gainItem(1142282, -1);
			}
			cm.getPlayer().setAddDamage(cm.getPlayer().getAddDamage() + reward[seld-1]['adddmg']);
                                   	 cm.getPlayer().setDamageHit2(cm.getPlayer().getDamageHit2() + reward[seld-1]['addatk']);
			cm.gainItemAllStat(reward[seld-1]['itemid'], 1, reward[seld-1]['allstat'], reward[seld-1]['atk']);
			cm.getQuestRecord(Integer.parseInt(95951+""+seld)).setCustomData(1);
			cm.sendOk("#fs11# 등급 달성 보상을 지급하였습니다");
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
	if (isGet(star) && Integer.parseInt(cm.getQuestRecord(201801).getCustomData()) >= star) ret = "#b수령 가능#k"; else ret = "#r수령 불가#k";
	return ret;
}
function isOkStar(star) {
	return Integer.parseInt(cm.getQuestRecord(201801).getCustomData()) >= star ? true : false;
}