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
	    qm.sendYesNo("아! 참, #b오르비스 구름공원4#k에 사는 스피루나가 여행가들을 자신의 집으로 초대하던데? 뭔가 좋은 것을 주려나봐요. 가보시는게 어때요? 스피루나는 #r오르비스 구름공원4#k에 있는 집에서 살고 있어요. 오르비스로 가시려면 여섯갈래길, 오르비스 정거장으로 가셔서 비행선을 타시면 되요. 꼭 가보세요. 아셨죠?");
        } else if (status == 1) {
            qm.forceStartQuest();
            qm.dispose();
	}
    }
}