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
	    qm.sendYesNo("갑작스러운 제안에 놀라셨겠군요. 죄송하지만, 당신이 우리와 함께 할 자격이 있는지를 먼저 확인해봐도 될까요? 자격도 없는 이에게 우리의 이야기를 함부로 누설할 수는 없으니까요. 괜찮으시겠습니까?");
	} else if (status == 1) {
	    qm.sendNext("네 좋습니다. 그럼 간단한 테스트에 협조 부탁드립니다. 여기 있는 제 강아지 해피를 시간 내에 물리치시면 됩니다. 그럼 시작해보죠.");
        } else if (status == 2) {
	    qm.warp(931050510,0);
	    qm.spawnMob(9300474,0,0);
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}