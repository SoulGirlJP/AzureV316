importPackage(java.util);
importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client.items);

var status = -1;
var spirit = 0;
var collab = 0;
var ticket = 0;
var questid = 0;
function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
	if (mode == -1) {
	cm.dispose();
	} else {
	if (mode == 0) {
	cm.dispose();            
	return;        
	}        
	if (mode == 1)            
	status++;        
	else           
	status--;    

	if (status == 0) {
	var etc = cm.getPlayer().getInventory(MapleInventoryType.ETC);
	var chat = "안녕~ 날씨 참 좋지? 몬스터파크를 이용하려면 당연히 나를 찾아와야지. 내가 뭘 해주면 좋겠어?\r\n#b";
	chat += "#L0# #i4001513#  #z4001513# 교환하기#l\r\n";
	chat += "#L1# #i4001515#  #z4001515# 교환하기#l\r\n";
	chat += "#L2# #i4001521#  #z4001521# 교환하기#l\r\n";
	chat += "#L3# #i4031138# #r입장 티켓 구입하기#l\r\n";
	cm.sendSimple(chat);

	} else if (status == 1) {
	selected = selection;
	spirit = selection == 0 ? 1 : selection == 1 ? 2 : selection == 2 ? 3 : 4
	var t_1 = selection == 0 ? 4001513 : selection == 1 ? 4001515 : selection == 2 ? 4001521 : 0
	var t_2 = selection == 0 ? 4001514 : selection == 1 ? 4001516 : selection == 2 ? 4001522 : 0

		if(selected != 3) {
		cm.sendGetNumber("#b#z"+t_1+"##k 10장당 #b#z"+t_2+"##k 1장으로 교환할 수 있어. 몇 장 교환할래?", 1, 0, cm.itemQuantity(t_1)/10);
		}
		
		else if(selected == 3){
		var chat = "음~원래 이러면 안되지만, 요새 내 기분이 좋아서 특별히 판매하는거야. #r티켓 종류와 상관없이 1인당 하루에 딱 20장#k만 팔꺼야. 참, 슈피겔만에겐 비밀이야! \r\n#b";
		chat += "#L0##z4001514# 5만 메소#l\r\n";
		chat += "#L1##z4001516# 10만 메소#l\r\n";
		chat += "#L2##t4001522# 20만 메소#l#k";
		spirit = 4;
		cm.sendSimple(chat);
		}

	} else if (status == 2) {
		buyitem = selection;
		if(spirit != 4) {
		var t_3 = spirit == 1 ? 4001513 : spirit == 2 ? 4001515 : 4001521
		var t_4 = spirit == 1 ? 4001514 : spirit == 2 ? 4001516 : 4001522
		cm.sendYesNo("정말 #b#z"+t_3+"# "+selection*10+"장#k를 #b#z"+t_4+"# "+selection+"장#k으로 교환할꺼야? 한 번 교환하면 다시 조각으로 바꾸긴 불가능하니까 신중히 결정해도록 해.");
		spirit = t_3 == 4001513 ? 4001514 : t_3 == 4001515 ? 4001516 : 4001522
		collab = 1;
		}

		else {
		selected = selection
		var t_5 = selected == 0 ? 4001514 : selected == 1 ? 4001516 : 4001522
		var t_6 = selected == 0 ? 50000 : selected  == 1 ? 100000 : 200000
		var questid = selected == 0 ? 7902 : selected == 1 ? 7903 : 7904
		cm.sendGetNumber("#b#z"+t_5+"##k를 구입하고 싶은거야? 한 장당 #b"+t_6/10000+"만 메소#k에 판매하고 있어. 몇 장 구입할래?",1,1,cm.getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot()*100);
		ticket = t_5 == 4001514 ? 4001514 : t_5 == 4001516 ? 4001516 : 4001522
		collab = 2;
		}


	} else if (status == 3) {
		if(collab == 1) {
			var rob = spirit == 4001514 ? 4001513 : spirit == 4001516 ? 4001515 : 4001521
			if(!cm.canHold(spirit)) {
			cm.sendOk("네 인벤토리 기타탭이 꽉 찬것 같아. 비우고 다시 말을 걸어줘~");
			cm.dispsoe();
			} else {
			cm.gainItem(spirit, buyitem);
			cm.gainItem(rob, -buyitem*10);
			cm.sendOk("#b#z"+spirit+"# "+buyitem+"개#k는 잘 받았지? 그럼 몬스터파크에서 즐거운 시간 보내도록 해~ 후후후.");
			cm.dispose();
			}
		}

		else if(collab == 2) {
		var questid = t_6 == 50000 ? 7902 : t_6 == 100000 ? 7903 : 7904
		var meso = t_6 == 50000 ? 50000 : t_6 == 100000 ? 100000 : 200000
		var date = Calendar.getInstance().get(Calendar.YEAR)%100+"/"+StringUtil.getLeftPaddedStr(Calendar.getInstance().get(Calendar.MONTH)+"", "0", 2)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			if (cm.getPlayer().getOneInfoQuest(questid, "eNum").equals("20") && cm.getPlayer().getOneInfoQuest(questid, "lastDate").equals(date)) {
			cm.sendOk("넌 이미 오늘 20장을 구입했어. 더 이상은 곤란해~ 이러다 슈피겔만이 눈치챈다구. 내일 다시 와~");
			cm.dispose();
//			return;
			} else {
				if (cm.getPlayer().getMeso() < meso * selection){
					cm.sendOk("메소가 부족한걸? 나도 슈피겔만 몰래 장사하는거지만 이러는건 아니라고 생각해.");
					cm.dispose();
				} else {
					cm.sendOk("#b#z"+ticket+"# "+selection+"개#k는 잘 받았지? 그럼 몬스터파크에서 즐거운 시간 보내도록 해~ 후후후.");
					cm.gainMeso(-meso*selection)
					cm.gainItem(ticket, selection)
					cm.dispose();
						if (!cm.getPlayer().getOneInfoQuest(questid, "lastDate").equals(date)) {
						cm.getPlayer().updateOneInfoQuest(questid, "eNum", "1");
						cm.getPlayer().updateOneInfoQuest(questid, "lastDate", date);
						} else {
						var eNum = Integer.parseInt(cm.getPlayer().getOneInfoQuest(questid, "eNum"));
						cm.getPlayer().updateOneInfoQuest(questid, "eNum", (eNum+1)+"");
            					}
					}
				}
			}
		}
	}
}