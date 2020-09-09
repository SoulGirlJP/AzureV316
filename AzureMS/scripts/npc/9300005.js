importPackage(Packages.tools.RandomStream);
var status = -1;
var state, amount;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

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
	//cm.sendSimple("#fn나눔고딕 Extrabold##fs13# 안녕하세요, 저는 일반 강화 엔피시 입니다.\r\n#r황금단풍잎 <6개 소모>#k#l\r\n#b#L0#올스텟을 강화하겠습니다.\r\n#L1#공 마를 강화하겠습니다.\r\n");
        var chat = "#fn나눔고딕 Extrabold##fs13# #r#e" + cm.getPlayer().getName() + "#n#k님 안녕하세요, 저는 메소 강화 엔피시입니다.#l\r\n";
        chat += "#b#L0#올스텟을 강화하겠습니다.#k#l\r\n";
        chat += "#b#L1#공격력/마력을 강화 하겠습니다.#k#l\r\n";
        
        cm.sendSimple(chat);
    } else if (status == 1) {
        slot = selection;
        sel = selection == 0 ? "올스텟" : "공격력/마력";
        var itemid = 0;
        text = "#b#e"+sel+"#fn나눔고딕 Extrabold##fs13# 강화#n#k를 선택 하셧습니다.\r\n강화할 아이템을 선택 해 주세요.\r\n";
        for (i = 0; i < 101; i++) {
            if (cm.getEquip(i)) {
                itemid = cm.getEquip(i).getItemId();
		text += "\r\n#L"+i+"##b#i"+itemid+"##t"+itemid+"##k";
            }
        }
        cm.sendSimple(text);
    } else if (status == 2) {
        eq = cm.getEquip(selection);
        state = owner(eq);
        if (cm.getPlayer().getMeso() < meso(eq)) {
            cm.sendOk("#fn나눔고딕 Extrabold##fs13# 강화에 필요한 메소가 부족합니다.");
            cm.dispose();
            return;
        }
        
        if (check(eq)) {
            cm.sendYesNo("#fn나눔고딕 Extrabold##fs13# 현재 강화 상태 : #b"+state+"#k\r\n선택한 스탯 : "+sel+"\r\n다음 강화로 올라갈때 실패확률은 40%며 수치는 강화횟수에 따라 주어집니다.\r\n강화를 진행 하시겠습니까?");
        }
    } else if (status == 3) {
        if (Randomizer.isSuccess(40)) {
            cm.sendOk("#fn나눔고딕 Extrabold##fs13# 아이템 강화에 실패 하였습니다.");
            cm.gainMeso(-meso(eq));
            cm.dispose();
            return;
        }
        amount = java.lang.Integer.parseInt(stat(eq,sel));
        level = level(eq);
        cm.gainMeso(-meso(eq));
        eq.setOwner(level);
        if (sel.equals("올스텟")) {
            eq.setStr(eq.getStr() + amount);
            eq.setDex(eq.getDex() + amount);
            eq.setInt(eq.getInt() + amount);
            eq.setLuk(eq.getLuk() + amount);
            cm.fakeRelog();
            cm.updateChar();
            cm.sendOk("강화에 성공하여 올스텟 "+amount+"이 추가되고 "+level+" 이 되었습니다.");
            cm.dispose();
            return;
        } else {
            eq.setMatk(eq.getMatk() + amount);
            eq.setWatk(eq.getWatk() + amount);
	    cm.fakeRelog();
	    cm.updateChar();
	    cm.sendOk("강화에 성공하여 공마 "+amount+" 이 추가되고 "+level+" 이 되었습니다."); 
            cm.dispose();
            return;
        }
    }
}

function level(eq) {
    var lev;
    if (eq.getOwner().equals("1강")) {
        lev = "2강";
    } else if (eq.getOwner().equals("2강")) {
        lev = "3강";
    } else if (eq.getOwner().equals("3강")) {
        lev = "4강";
    } else if (eq.getOwner().equals("4강")) {
        lev = "5강";
    } else if (eq.getOwner().equals("5강")) {
        lev = "6강";
    } else if (eq.getOwner().equals("6강")) {
        lev = "7강";
    } else if (eq.getOwner().equals("7강")) {
        lev = "8강";
    } else if (eq.getOwner().equals("8강")) {
        lev = "9강";
    } else if (eq.getOwner().equals("9강")) {
        lev = "10강";
    } else {
        lev = "1강";
    }
    return lev;
}

function stat(eq, sel) {
    var stat;
	if(eq.getOwner().equals("1강")) { // 1강 - 2강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(15, 15);
	} else if(eq.getOwner().equals("2강")) { // 2강 - 3강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(20, 20);	
	} else if(eq.getOwner().equals("3강")) { // 3강 - 4강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(25, 25);	
	} else if(eq.getOwner().equals("4강")) { // 4강 - 5강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(30, 30);	
	} else if(eq.getOwner().equals("5강")) { // 5강 - 6강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(35, 35);	
	} else if(eq.getOwner().equals("6강")) { // 6강 - 7강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(40, 40);	
        } else if(eq.getOwner().equals("7강")) { // 6강 - 7강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(45, 45);	
        } else if(eq.getOwner().equals("8강")) { // 6강 - 7강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(50, 50);	
        } else if(eq.getOwner().equals("9강")) { // 6강 - 7강 넘어갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(60, 60);	         
	} else { // 1강으로 갈때 스텟
		stat = sel == "올스텟" ? Randomizer.rand(8, 50) : Randomizer.rand(10, 10);	
	}
	return stat;		
}

function check(eq) {
    if (eq.getOwner().equals("1★")) {
        cm.sendOk("#fn나눔고딕 Extrabold##fs13# 해당아이템은 황금 단풍잎 강화를 진행하여 메소 강화를 할 수 없습니다.");
        cm.dispose();
        return;
    }
    if (eq.getOwner().equals("10강")) {
        cm.sendOk("#fn나눔고딕 Extrabold##fs13# 해당 아이템은 강화를 더이상 할 수 없습니다.");
        cm.dispose();
        return;
    }
    return true;
}

function meso (eq) {
    var meso;
    if (eq.getOwner().equals("1강")) {
        meso = 5000000;
    } else if (eq.getOwner().equals("2강")) {
        meso = 10000000;
    } else if (eq.getOwner().equals("3강")) {
        meso = 30000000;
    } else if (eq.getOwner().equals("4강")) {
        meso = 50000000;
    } else if (eq.getOwner().equals("5강")) {
        meso = 100000000;
    } else if (eq.getOwner().equals("6강")) {
        meso = 200000000;
    } else if (eq.getOwner().equals("7강")) {
        meso = 300000000;
    } else if (eq.getOwner().equals("8강")) {
        meso = 500000000;
    } else if (eq.getOwner().equals("9강")) {
        meso = 1000000000;
    } else {
        meso = 10000000;
    }
    return meso;
}

function owner(eq) {
    for(i = 1; i < 7; i++) {
        if(eq.getOwner().equals(i+"강")) {
           return eq.getOwner();
	}
    } 
    
    if(state == null) {
        return "#r강화안함#k";
    }
}