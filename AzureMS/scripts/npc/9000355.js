importPackage(Packages.packet.creators);
importPackage(Packages.handling.world);
importPackage(Packages.client.items);
importPackage(Packages.server.items);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
importPackage(Packages.tools);
importPackage(java.lang);

달력 = new Date();

년   = 달력.getFullYear();
월   = 달력.getMonth() + 1;
일   = 달력.getDate();
일요 = 달력.getDay();
전후 = 달력.getHours() < 12 ? "오전" : "오후"			
시   = 달력.getHours() > 12 ? 달력.getHours() - 12 : 달력.getHours();
분   = 달력.getMinutes();
초   = 달력.getSeconds();
요일 = 일요 == 0 ? "일요일" : 일요 == 1 ? "월요일" : 일요 == 2 ? "화요일" : 일요 == 3 ? "수요일" : 일요 == 4 ? "목요일" : 일요 == 5 ? "금요일" : "토요일";
시간 = ""+년+"년 "+월+"월 "+일+"일 "+요일+" "+전후+" "+시+"시 "+분+"분 "+초+"초 ";

핫목록 = new Array(
new Array(4310080, 299),
new Array(4310069, 29),
new Array(4310156, 12),
new Array(4310088, 14)
)

핫당첨 = new Array(
new Array(1012532, ""),
new Array(1012427, "(#r올스텟 +50 / 공격력 +30#b)"),
new Array(1012531, ""),
new Array(1012529, ""),
new Array(1012437, ""),
new Array(1012436, ""),
new Array(1012435, ""),
new Array(1012434, ""),
new Array(1012433, ""),
new Array(1012432, ""),
new Array(1012431, ""),
new Array(1012430, ""),
new Array(1012429, ""),
new Array(1012428, ""),
new Array(1012427, ""),
new Array(3994641, ""),
new Array(3994002, ""),
new Array(3991013, ""),
new Array(3991003, "")


)

이벤트 = [
["이벤트 제목 1", "최초로 스우 격파도전을해라!!!"],
["이벤트 제목 2", "메이플 기념 아이템 드랍이벤트~!"]
]

var check = 0;
var status = -1;
var sel = -1;


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
	}

	if(status == 0){
	cm.sendSimple("현재 시간 │ "+시간+"\r\n"
		+ "서버 배율 │ 경험치 #e#r"+cm.getClient().getChannelServer().getExpRate()+"#k#n배 · "
		+ "메소 #e#r"+cm.getClient().getChannelServer().getDropRate()+"#k#n배 · "
		+ "드롭 #e#r"+cm.getClient().getChannelServer().getMesoRate()+"#k#n배\r\n\r\n#b"
		+ "#L9##e핫타임 보상을 받고싶어요.#b(#r#z2433424##b)\r\n#fs12##n"
		+ "#L1##e현재 진행중인 이벤트를 알고 싶어요. #b(#r총 "+이벤트.length+"개#b)\r\n\r\n#fs12##n"
		//+ "#L6##b출석체크 보상을 받고 싶어요. (#r경험치 2배 쿠폰#b)#b\r\n#fs12##n"
		//+ "#L7##b사용할 수 없는 아이템을 사용이 가능하게 바꿔주세요."
		);


	}else if(status == 1){
	선택 = selection;
	switch(선택) {
		case 1:
		var str = "현재 진행중인 이벤트 입니다. 보다 자세한 사항은 공지사항을 확인 해 주세요.\r\n\r\n"
		for(var i = 0; i < 이벤트.length; i++){
			str += "　#e#r◈#k#fs13# "+이벤트[i][0]+"#fs12##n#b\r\n";
			str += "　#fn나눔고딕#"+이벤트[i][1]+"#fn돋움#\r\n\r\n";

		}
		cm.sendNext(str);
		cm.dispose();
		break;

		case 6:
			if(cm.getParty() != null) {
				if(cm.BossCheck("출석체크", 3)) {
				cm.BossAdd("출석체크");
				cm.gainItem(2450042, 1);
				cm.sendOk("오늘도 즐거운 하루 보내세요~. 이건 운영자님이 드리는 작은 선물입니다.");
				WorldBroadcasting.broadcast(MainPacketCreator.getGMText(20, ""+cm.getPlayer().getName()+" 님이 출석체크를 하셨습니다. ("+시간+")"));	
				} else {
				cm.sendOk("이미 오늘은 출석체크를 하였습니다. 내일 또 이용해주세요~!");
				}
			} else {
			cm.sendOk("출석체크는 파티를 만드신 후에 가능합니다. #b/파티만들기#k를 채팅창에 치거나 #b단축키 P#k를 눌러서 파티를 만드실 수 있습니다.");

			}
		cm.dispose();			
		break;

		case 7:
		목록 = [2640000, 2640004, 2640001, 2640005, 2028208]
		가격 = [200,     200,     200,     200,     100]

		교환 = "#fs11##Cgray#멍청한#k#fs12# 관리자의 실수로 사용이 불가능한 아이템이 지급되셨나요? 사용이 가능한 아이템으로 교환해드리도록 하겠습니다.\r\n#b"
		for (var i = 0; i < 목록.length; i++) {
		교환 += "#L"+i+"# #i"+목록[i]+"# #z"+목록[i]+"# #r(보유 : "+cm.itemQuantity(목록[i])+")#b\r\n";
		}
		cm.sendSimple(교환);
		break;


		case 9:
			if(cm.haveItem(2433424)) {
			hT  = "핫타임으로 받은 #r#i2433424# #z2433424##k 아이템을 교환하시겠어요?\r\n\r\n#r";
			hT += "인벤토리의 각 칸에 여유공간이 4칸 이상이 있어야 정상적으로 수령이 가능합니다. ";
			hT += "#e인벤토리 부족으로 인해 받지 못한 아이템은 재수령이 불가능합니다.#k#n\r\n\r\n\r\n";
	
			hT += "#fs20##fna스마일M#　　　　100% 지급 아이템#fs12##fn돋움#\r\n";
			hT += "───────────────────────────\r\n";
				for(i = 0; i < 핫목록.length; i++) {
				hT += "　#b#i"+핫목록[i][0]+"# #z"+핫목록[i][0]+"# (#r"+Integer(핫목록[i][1]+1)+"개#b)\r\n"
				}
			hT += "\r\n\r\n#fs20##fna스마일M#　　 #r당첨 지급 아이템확률50%#fs12##fn돋움#\r\n";
			hT += "───────────────────────────\r\n";
				for(i = 0; i < 핫당첨.length; i++) {
				hT += "　#b#i"+핫당첨[i][0]+"# #z"+핫당첨[i][0]+"# "+핫당첨[i][1]+"\r\n"
				}
			
			cm.sendYesNo(hT);
			} else {
			cm.sendOk("핫타임 보상 수령 기간이 아니거나 이미 참여하셨습니다. 다음에 다시 시도해주세요.")
			cm.dispose();
			}
		break;
		
	}

	}else if(status == 2){
	switch(선택) {

		case 7:
		if(cm.haveItem(목록[selection])) {
			if(cm.canHold(4310119)) {
				cm.gainItem(목록[selection], -1);
				cm.gainItem(4310119, 가격[selection]);
				cm.sendOk("게임 이용에 불편을 드려 죄송합니다. 정상적으로 교환이 완료되었습니다.");
			} else {
			cm.sendOk("인벤토리의 여유 공간이 없거나 지급받으려는 아이템이 고유 아이템일 수 있습니다. 인벤토리를 확인해보시고, 다시 시도해주세요.");
			}
		} else {
		cm.sendOk("정말로 #i"+목록[selection]+"# #b#z"+목록[selection]+"##k 아이템을 보유하신게 맞나요? #h #님의 인벤토리를 아무리 찾아봐도 보이지가 않네요… ");
		}
		cm.dispose();
		break;


		case 9:
		cm.gainItem(2433424, -1);
		isLucky  = Math.floor(Math.random() * 60);
		getRate0 = Math.floor(Math.random() * 핫목록[0][1]+1);
		getRate1 = Math.floor(Math.random() * 핫목록[1][1]+1);
		getRate2 = Math.floor(Math.random() * 핫목록[2][1]+1);
		getRate3 = Math.floor(Math.random() * 핫목록[3][1]+1);
		//getRate4 = Math.floor(Math.random() * 핫목록[4][1]+1);
		//getRate5 = Math.floor(Math.random() * 핫목록[5][1]+1);
		//getRate6 = Math.floor(Math.random() * 핫목록[6][1]+1);

		gV = "#i2433424# #r#z2433424##k이 한 순간 빛나더니 아래의 아이템이 나왔습니다.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n"
		gV += "#i"+핫목록[0][0]+"# #z"+핫목록[0][0]+"# "+getRate0+"개\r\n"
		gV += "#i"+핫목록[1][0]+"# #z"+핫목록[1][0]+"# "+getRate1+"개\r\n"
		gV += "#i"+핫목록[2][0]+"# #z"+핫목록[2][0]+"# "+getRate2+"개\r\n"
		gV += "#i"+핫목록[3][0]+"# #z"+핫목록[3][0]+"# "+getRate3+"개\r\n"
		//gV += "#i"+핫목록[4][0]+"# #z"+핫목록[4][0]+"# "+getRate4+"개\r\n"
		//gV += "#i"+핫목록[5][0]+"# #z"+핫목록[5][0]+"# "+getRate5+"개\r\n"
		//gV += "#i"+핫목록[6][0]+"# #z"+핫목록[6][0]+"# "+getRate6+"개\r\n"

		cm.sendNext(gV);

		cm.gainItem(4310080, 300);
		cm.gainItem(4310069, 30);
		cm.gainItem(4310156, 13);
		cm.gainItem(4310088, 15);
		//cm.gainItem(핫목록[4][0], getRate4);
		//cm.gainItem(핫목록[5][0], getRate5);
		//cm.gainItem(핫목록[6][0], getRate6);

			switch(isLucky){
			case 1:  case 2: gainOther = 핫당첨[0][0]; break;
			case 7:  case 8: gainOther = 핫당첨[1][0]; break;
			case 13: case 14: gainOther = 핫당첨[2][0]; break;
			case 15: case 16: gainOther = 핫당첨[3][0]; break;
			case 17: case 18: gainOther = 핫당첨[4][0]; break;
			case 19: case 20: gainOther = 핫당첨[5][0]; break;

			case 3:   case 4:  gainOther = 핫당첨[6][0]; break;
			case 9:  case 10:  gainOther = 핫당첨[7][0]; break;
			case 21: case 22: gainOther = 핫당첨[8][0]; break;
			case 23: case 24: gainOther = 핫당첨[9][0]; break;
			case 25: case 26: gainOther = 핫당첨[10][0]; break;
			case 27: case 28: gainOther = 핫당첨[11][0]; break;

			case 5:   case 6:   gainOther = 핫당첨[12][0]; break;
			case 11:   case 12:  gainOther = 핫당첨[13][0]; break;
			case 29: case 30: gainOther = 핫당첨[14][0]; break;
			case 31:   case 34:   gainOther = 핫당첨[15][0]; break;
			case 32:   case 35:  gainOther = 핫당첨[16][0]; break;
			case 33: case 36: gainOther = 핫당첨[17][0]; break;
			default: gainOther = 0; break;
			}
		break;

	}

	} else if (status == 3) {
		switch(gainOther) {
		case 0: cm.dispose(); break;
		default:
		cm.sendNext("#e#r"+isLucky+"#k#n\r\n\r\n어라? #h #님의 #i2433424# #r#z2433424##k에 뭔가 더 있는 거 같은데요? 잠시만요!");
		}
	} else if (status == 4) {
	WorldBroadcasting.broadcast(UIPacket.detailShowInfo("[핫타임] "+cm.getPlayer().getName()+" 님이 ["+Packages.server.items.ItemInformation.getInstance().getName(gainOther)+"] 아이템을 획득하셨습니다. 모두 축하해주세요~",false));
	cm.sendOk("#i2433424# #r#z2433424##k에서 아래의 아이템이 추가로 나왔습니다. 축하드려요~\r\n\r\n"
		+ "#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i"+gainOther+"# #z"+gainOther+"#");
			if(gainOther != 1012427) { cm.gainItem(gainOther, 1); } else {
			var ii = Packages.server.items.ItemInformation.getInstance();
			var item = ii.getEquipById(gainOther);
			item.setStr(50);
			item.setDex(50);
			item.setInt(50);
			item.setLuk(50);
			item.setWatk(30);
			item.setMatk(30);
			item.setOwner(""+ cm.getPlayer().getName()+"");
			Packages.server.items.InventoryManipulator.addFromDrop(cm.getC(),item,false);
			}
	cm.dispose(); //MainPacketCreator.getGMText
	}
}