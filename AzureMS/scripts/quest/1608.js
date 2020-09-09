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
	    qm.sendYesNo("이 푸른 빛의 기둥이 유적 발굴지의 몬스터들을 난폭하게 만든 것 일까요? 무슨 일이 생길지 모르니 제가 가까이 가서 만져볼게요. 준비되셨나요?");
        } else if (status == 1) {
            qm.sendNext("혹시, 위험한 일이 발생하면 저를 꼭 지켜주세요. 그럼, 하나... 둘... 셋!");
        } else if (status == 2) {
	    qm.timeMoveMap(102040600,931050402,60 * 7);
            qm.spawnMob(9300471,0,0);
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}