var status;
var select;
var tt;
var price;
importPackage(Packages.client);
function start() {
    status = -1;
	action(1, 1, 0);
}

function action(mode, type, selection) {
    if (mode != 1)
        cm.dispose();
    else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
		
		cm.sendSimple("이 비행기는 나와 수십년을 함께한 비행기지.. 이녀석만큼은 절대 팔 수 없어.\r\n\r\n#b#L0##s80001027# 나무 비행기 대여 (1일 - 15만 메소)#l\r\n#L1##s80001027# 나무 비행기 대여 (3일 - 40만 메소)#l\r\n#L2##s80001027# 나무 비행기 대여 (7일 - 90만 메소)#l\r\n\r\n#L3##s80001028# 빨간 비행기 대여 (1일 - 30만 메소)#l\r\n#L4##s80001028# 빨간 비행기 대여 (3일 - 80만 메소)#l\r\n#L5##s80001028# 빨간 비행기 대여 (7일 - 180만 메소)#l\r\n");
		

	} else if (status == 1) {
		select = selection;
		
			var msg="정말로 #e";
			if (select <= 2) {
				msg+="#s80001027# 나무 비행기";
			} else {
				msg+="#s80001028# 빨간 비행기";
			}
			msg += "#k#n 를 ";
			if (select >= 3) tt = select - 3;
			else tt = select;
			if (tt == 0) tt = 1;
			else if (tt == 1) tt = 3;
			else if (tt == 2) tt = 7;
			msg += tt;
			msg += "일 동안 대여해볼텐가? \r\n\r\n요금은 #b";
			if (tt == 1) price = 15;
			else if (tt == 3) price = 40;
			else if (tt == 7) price = 90;
			if (select >= 3) price *= 2;
			msg += price;
			msg +="만#k 메소 라네. 어떤가? 괜찮은 가격이지?";
			cm.sendYesNo(msg);
		
	} else if (status == 2) {
			if (cm.getMeso() >= price * 10000) {
				var skill = 80001027;
				if (select >= 3) skill++;
				//cm.getPlayer().changeSkillLevel(SkillFactory.getSkill(skill),1,1,java.lang.System.currentTimeMillis() + 1000 * 86400 * tt);
				cm.teachSkillPeriod(skill,1,1,tt);
				cm.getPlayer().Message("비행기를 성공적으로 대여했습니다.");
				cm.gainMeso(-(price * 10000));
				cm.dispose();
			} else {
				cm.sendOk("자네.. 메소는 제대로 갖고 있는건가?");
				cm.dispose();
				return;
			}
	}
	
    }
}