
importPackage(java.text);

function enter(pi) {
if (pi.getPlayer().getMapId() == 270010100) { /* 타임로드 : 추억의 길1 */
	if(pi.getQuestStatus(3501) == 2) {
		pi.warp(270010110);
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
		pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270010200) { /* 타임로드 : 추억의 길2 */
	if(pi.getQuestStatus(3502) == 2) {
		pi.warp(270010300);
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
                pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270010300) { /* 타임로드 : 추억의 길3 */
	if(pi.getQuestStatus(3503) == 2) {
		pi.warp(270010400);
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
                pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270010400) { /* 타임로드 : 추억의 길4 */
	if(pi.getQuestStatus(3504) == 2) {
		pi.warp(270010500);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270010500) { /* 타임로드 : 추억의 길5 */
	if(pi.getQuestStatus(3507) == 2) {
		pi.warp(270020000);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270020100) { /* 타임로드 : 후회의 길1 */
	if(pi.getQuestStatus(3508) == 2) {
		pi.warp(270020200);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);
pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270020200) { /* 타임로드 : 후회의 길2 */
	if(pi.getQuestStatus(3509) == 2) {
		pi.warp(270020300);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270020300) { /* 타임로드 : 후회의 길3 */
	if(pi.getQuestStatus(3510) == 2) {
		pi.warp(270020400);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270020400) { /* 타임로드 : 후회의 길4 */
	if(pi.getQuestStatus(3511) == 2) {
		pi.warp(270020500);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270020500) { /* 타임로드 : 후회의 길5 */
	if(pi.getQuestStatus(3514) == 2) {
		pi.warp(270030000);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270030100) { /* 타임로드 : 망각의 길1 */
	if(pi.getQuestStatus(3514) == 2) {
		pi.warp(270030200);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270030200) { /* 타임로드 : 망각의 길2 */
	if(pi.getQuestStatus(3515) == 2) {
		pi.warp(270030300);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270030300) { /* 타임로드 : 망각의 길3 */
	if(pi.getQuestStatus(3516) == 2) {
		pi.warp(270030400);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270030400) { /* 타임로드 : 망각의 길4 */
	if(pi.getQuestStatus(3517) == 2) {
		pi.warp(270030410);
		
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270030500) { /* 타임로드 : 망각의 길5 */
	if(pi.haveItem(4032002, 1)) {
            pi.gainItem(4032002, -1);
		pi.warp(270040000);
		return true;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}
else if (pi.getPlayer().getMapId() == 270040000) { /* 신전 깊은 곳 : 부서진 회랑 */
	if(pi.getQuestStatus(3521) == 2) {
		pi.warp(270040100);
		//pi.getPlayer().getClient().getSession().write(MaplePacketCreator.serverNotice(5, "신들의 황혼 이후 퀘스트 공사중이므로 입장이 불가능 합니다."));
		return false;
	} else {
		pi.warp(pi.getPlayer().getMapId(),0);pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
		return false;
	}
}

	
pi.warp(pi.getPlayer().getMapId(),0);
pi.playerMessage(5, "알 수 없는 힘에 의해 더 이상 진행할 수 없다.");
return false;

}