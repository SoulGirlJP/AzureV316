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
	    qm.sendNext("이런 우연이...여기서 또 뵙게 됬네요?");
        } else if (status == 1) {
            qm.sendNextPrevS("(...이런 데서 또 만나다니. 그 때 커닝시티 지하철에서 도와줬던 이상한 여자자나...) 네. 또 만났네요...",3);
        } else if (status == 2) {
	    qm.sendNext("저도 유적 발굴 현장을 도우러 왔다가, 제 이상한 게이트가 발견되었다고 들었어요. 혹시 직접 보셨나요?");
        } else if (status == 3) {
            qm.sendNextPrevS("네. 미접근 지역에서 스켈레톤 지휘관을 처치하다가 봤어요. 묘한 기운이 뿜어져 나오는 것 같았어요.",3);
        } else if (status == 4) {
            qm.sendYesNo("너무 궁금하군요! 저를 그곳으로 안내해 주실 수 있을까요? 제가 길 눈이 어두워서...");
        } else if (status == 5) {
            qm.sendYesNo("그럼 미접근 지역으로 바로 출발해볼까요?");
        } else if (status == 6) {
            qm.sendNext("그럼 지금 바로 가요.");
        } else if (status == 7) {
	    qm.warp(102040600,0);
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}