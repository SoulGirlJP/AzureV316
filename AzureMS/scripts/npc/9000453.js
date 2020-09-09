/*


소스: Dbg.client의 DbgPlayer.java에 다음을 추가해주세요.

    public void tddFame(int famechange) {
        this.fame -= famechange;
    }


*/

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
            	
	       var leaf = cm.itemQuantity(4310057);
	       var chat = "#fn나눔고딕 Extrabold# 안녕하세요 #h0#님! 핫타임코인으로 아이템을 받을수있다쿰.\r\n";
	       chat += "#fn나눔고딕 Extrabold# 현재 #h0#님의 핫타임코인는 #r#e"+ leaf +" #n#k개 입니다.#b"
	       chat += "\r\n\r\n           #e#r핫타임 아이템 목록 = 알려고하지마!!><#n#k"
	       chat += "\r\n#b        #fn나눔고딕 Extrabold##fs13#     #L400#핫타임 아이템 받기#k";
	       cm.sendSimple(chat);

	    }  if (selection == 400) {
		if (cm.haveItem(4310057, 1)) {
		    if (cm.canHold(4310057)) {
		        cm.sendOk("#fn나눔고딕 Extrabold# 핫타임 아이템이 지급되었습니다 !");
                        cm.gainItem(4310057, -1);
	                cm.gainSponserItem(1142322 ,'[추석]',1000,500,500);
			cm.dispose();
		    } else {
		        cm.sendOk("장비칸에 빈 공간이 없습니다.");
		        cm.dispose();
		    }
		} else {
		    cm.sendOk("#fn나눔고딕 Extrabold# 핫타임 코인이 부족합니다.");
		    cm.dispose();

}
		}
	}
}



