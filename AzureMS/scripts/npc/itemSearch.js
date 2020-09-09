
importPackage(Packages.server.items);
 importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);

importPackage(Packages.provider);
importPackage(Packages.tools);
importPackage(Packages.client);
importPackage(Packages.server);

var status = -1;
var sel = -1;

var banitem = [1003775,];

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendGetText("\r\n#fs11#구매를 원하는 아이템의 이름을 검색해주세요.\r\n\r\n아이템의 정확한 명칭을 모르시나요?\r\n#b아이템의 이름 일부분만 입력해도 검색이 가능합니다.#k\r\n#fs13#\r\n#r예) 카오스 자쿰의 투구 → '자쿰' 으로 검색이 가능합니다.#k\r\n\r\n");
    } else if (status == 1) {
        if (cm.getText().equals("") || cm.getText().equals(" ")) {
            cm.sendOk("잘못 입력 하셨습니다.");
            cm.dispose();
            return;
        }
        var t = cm.searchCashItem(cm.getText());
        if (t.equals("")) {
            cm.sendOk("검색된 결과가 없습니다.");
            cm.dispose();
            return;
        }
        cm.sendSimple("입력하신 [#b" + cm.getText() + "#k]의 검색 결과입니다.\r\n\r\n" + t);
    } else if (status == 2) {
        sel = selection;
		if (!ItemInformation.getInstance().isCash(sel)) {
           		a = new Date();
			temp = Randomizer.rand(0,9999999);
			cn = cm.getPlayer().getName();
           		fFile1 = new File("Log/fuck/copybug/"+temp+"_"+cn+".log");
           		if (!fFile1.exists()) fFile1.createNewFile();
               		out1 = new FileOutputStream("Log/fuck/copybug/"+temp+"_"+cn+".log",false);
			var msg =  "'"+cm.getPlayer().getName()+"'이(가) 의심됨.\r\n";
           		msg += "'"+a.getFullYear()+"년 " + Number(a.getMonth() + 1) + "월 " + a.getDate() + "일'\r\n";
			msg += "복사 시도 아이템코드 : "+selection+"\r\n";
			msg += "사용자 캐릭터 아이디 : "+cm.getPlayer().getId()+"\r\n";
			msg += "사용자 계정 : "+cm.getPlayer().getAccountID()+"\r\n";
            		out1.write(msg.getBytes());
            		out1.close();
			cm.sendOk("오류가 발생 하였습니다.");
			cm.dispose();
			return;
		}
	isban = false;
	itemid = selection;
	for (i = 0; i < banitem.length; i++) {
		if(banitem[i] == itemid) isban = true;
	}
	if (isban) {
		cm.sendOk("해당 아이템은 구매할 수 없습니다.");
		cm.dispose();
		return;
	}
        cm.sendYesNo("정말 선택하신 #i" + sel + "##b#t" + sel + "##k (을)를 지급 받으시겠습니까?");
    } else if (status == 3) {
		cm.gainItem(sel, 1);
            	cm.sendYesNo("선택하신 아이템을 지급 해 드렸습니다.\r\n추가로 더 검색을 원하시면 '예'를 눌러주세요.");
   } else if (status == 4) {
        cm.dispose();
        cm.getClient().setClickedNPC(1000);
        cm.openNpc(1540010, "itemSearch");
	}
}