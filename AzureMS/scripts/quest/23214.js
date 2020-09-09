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
	    qm.sendNext("자, 갑니다! 거기에 있는 저는 소환수 모습이 아닌 과거 제 모습입니다.");
        } else if (status == 1) {
	    qm.sendNextPrev("물론 제가 만들어낸 형상이기에 과거의 저만큼 강하진 않지만, 그 차원은 제 영역 안이므로 충분히 강할 겁니다. 참고로 다른 차원이기 때문에 오래 머무르실 수 없다는 것을 명심해주세요!");
        } else if (status == 1) {
            qm.forceStartQuest();
	    qm.warp(931050120,0);
	    qm.spawnMob(9001037,0,0);
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendYesNo("수련은 어떠셨습니까? 성과가 있으시면 지금 상태를 각인 하도록 하겠습니다.");
        } else if (status == 1) {
	    qm.getPlayer().changeJob(2111);
	    qm.forceCompleteQuest();
            qm.sendNext("도움이 되었다니 정말 기쁩니다. 오늘은 무리해서 힘을 사용하니 피곤하네요. 이만 돌아가시죠. 네? 수고요? 별 말씀을요.");
        } else if (status == 2) {
            qm.sendPrev("얼굴이 빨갛게 된 것은 힘을 너무 써서 그런 거라고요!");
            qm.dispose();
        }
    }
}