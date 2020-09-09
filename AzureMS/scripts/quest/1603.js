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
	    qm.sendNext("- 공고 - 최근 유적 발굴 현장에 출몰한 스켈레톤들의 공격으로 사상자가 속출되고 있습니다. 용기있는 분들의 도전을 기다리고 있습니다. 자세한 상황은 #r유적 발굴단 캠프#k에 있는 #b발굴단장 샨#k에게 문의해주십시오.");
        } else if (status == 1) {
            qm.sendYesNo("지금 바로 #r페리온, 유적발굴단캠프#k로 이동하시겠습니까?");
        } else if (status == 2) {
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}