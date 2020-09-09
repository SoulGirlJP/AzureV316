/*
    	! 이 스크립트는 Hina Developmest of KMS의 일부입니다.
    	! 이 스크립트는 무단 수정과 무단 배포가 불가능합니다.
    	! 이 주석은 제작자의 동의가 있을시 수정이 가능합니다.
    	Copyright ⓒ 2012 Scripts Maker	루돌프 <rhkddbs_4399@naver.com>
					티썬 <doomgate17@naver.com>
					백호 <baekhoms@naver.com>
					엘도라도 <junir0542@naver.com>
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) { 
	    qm.sendNext("그것봐요. 나인하트씨. 이 분은 빛의 기사의 피를 이어받았고, 그만한 자격이 있음을 이렇게 훌륭하게 증명하셨잖아요.");
        } else if (status == 1) {
            qm.sendNextPrev("네. 항상 그렇듯이 여제님의 말씀이 맞는 것 같습니다. 아직 많이 부족하긴 하지만 이 소년에게는 빛의 기사의 피를 이어나갈 자격이 있는 것 같군요.");
        } else if (status == 2) {
            qm.sendNextPrevS("우리 아버지가 빛의 기사였다고요? 내가 빛의 기사가 될 거라는 건가요? 나는 그냥 평범한 소년일 뿐이에요. 그 흔한 이름조차 없는...",3);
        } else if (status == 3) {
            qm.askAcceptDecline("선택은 당신의 몫입니다. 하지만 빛을 가지고 태어난 자신의 운명을 거스르지 않으셨으면 좋겠어요. 당신을 위해서 그리고 이 메이플 월드를 위해서요.");
        } else if (status == 4) {
	    qm.warp(130000000,0);
	    qm.getPlayer().setLevel(10);
            qm.forceStartQuest();
	    qm.getPlayer().changeJob(5100);
	    qm.getPlayer().gainSP(1);
            qm.sendNextPrev("당신에게 이름이 필요할 것 같군요. '빛으로 태어난 사람'이라는 뜻의 '#b미하일#k'이 어떤가요? 당신에게 정말 잘 어울리는 것 같아요. 이제 저와 함께 에레브로 가요. 새로운 빛이 시작되기에 그 곳만큼 좋은 곳은 없으니까요.");
            qm.dispose();
	}
    }
}
