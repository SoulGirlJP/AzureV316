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
	    qm.sendNext("오랜만입니다. 그 동안 정말 강해졌군요. 이제 시그너스 기사단에 당신만큼 강한 기사는 거의 없을 겁니다. 기사단장들도 당신을 당해내긴 힘들 것 같으니 말입니다. ...빈말은 여기까지 하고, 본론을 이야기 할까요?");
        } else if (status == 1) {
	    qm.sendNextPrev("새로운 임무입니다. 얼마 전에 들어온 정보에 의하면 #r블랙윙#k의 멤버 중 누군가가 여제를 노리고 있다고 합니다. 그것을 저지하기 위해 기사단의 상급기사 #b듀나미스#k가 움직이고 있습니다만, 그녀 혼자로는 어려울 것 같군요.");
        } else if (status == 2) {
            qm.askAcceptDecline("빅토리아 아일랜드 지역이면 모를까, 오시리아는 기사단의 정보원들도 다 파악하지 못한 곳이라 지원이 필요할 것 같습니다. 당신께서 듀나미스를 서포트 해주십시오. 그녀가 마지막으로 연락한 곳은 #b엘나스#k이니 그곳에서 듀나미스를 찾으면 될 겁니다.");
        } else if (status == 3) {
            qm.forceStartQuest();
	    qm.dispose();
        }
    }
}