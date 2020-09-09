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

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
            if (qm.getQuestStatus(23212) == 0) {
                qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(23212) == 1) {
		qm.askAcceptDecline("자 이제 계약의 의식을 시작하겠습니다. 정신을 최대한 저에게 집중해주세요.");
	    }
        } else if (status == 1) {
            qm.sendNextS("#b(이상한 기운이 몸으로 스며드는 것 같다.)#k",3);
        } else if (status == 2) {
	    qm.getPlayer().changeJob(3110);
	    qm.forceCompleteQuest();
            qm.sendPrev("계.약.완.료. 잘 부탁 드립니다. 이젠 소리 내어 말하지 마시고 제 마음속으로 바로 말을 거세요.");
            qm.dispose();
	}
    }
}