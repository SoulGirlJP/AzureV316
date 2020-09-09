var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
var 작은별 = "#fUI/UIToolTip/Item/Equip/Star/Star#";

importPackage(Packages.constants);

var status = 0;

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
	    if (cm.getPlayer().getLevel() >= 200) {
		var jessica = "            #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 악세사리 상점 "+별+"\r\n#fs10##Cgray#                                  원하시는 장비를 선택해주세요.#k#fs12#\r\n\r\n";
		jessica += "--------------------------------------------------------------------------------\r\n";
		jessica += 작은별+"#L0##i1122017# #b#z1122017##k #d1 일권#k#l\r\n\r\n       * #i4310129# #r- 100 개 차감#k\r\n\r\n--------------------------------------------------------------------------------\r\n";
		cm.sendSimple(jessica);
	} else {
	cm.sendOk("#fn나눔고딕 Extrabold##r악세사리 상점은 레벨 200 이상만 이용 가능합니다.",9062004);
	cm.dispose();
        }
	} else if (status == 1) {
	if (selection == 0) {
           if(cm.haveItem(4310129, 100)) {
	   if (cm.canHold(1122017)) {
              cm.gainItem(4310129, -100);
              cm.gainItemPeriod(1122017, 1, 1);
	      cm.sendOk("#fn나눔고딕 Extrabold##i1122017# #b#z1122017##k #d1 일권#k 구입이 완료 되었습니다.");
	      cm.dispose();
		    } else {
		        cm.sendOk("#fn나눔고딕 Extrabold##r장비 창을 한 칸 이상 비워주세요.#k");
		        cm.dispose();
		    }
	   } else {
		cm.sendOk("#fn나눔고딕 Extrabold##r구매를 위한 썸머리밋 코인이 부족합니다.#k");
		cm.dispose();
	   }
}
}
}
}