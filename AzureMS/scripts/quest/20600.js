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
            qm.askAcceptDecline("#h #. 혹시 레벨이 90을 넘었다고 수련에 소홀해진 거 아닙니까? 확실히 당신은 강하지만 아직 수련은 끝나지 않았습니다. 기사단장들을 본받으세요. 검은 마법사에 대비하여 끊임없이 수련을 하는 그들을 말입니다.");
        } else if (status == 1) {
            qm.forceStartQuest();
	    qm.dispose();
        }
    }
}