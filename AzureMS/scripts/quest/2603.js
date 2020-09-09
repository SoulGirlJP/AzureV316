/*

	퓨어 소스 팩의 스크립트 입니다. (제작 : 주크블랙)

	엔피시아이디 : 
	
	엔피시 이름 : 시바

	엔피시가 있는 맵 : 초보 수련장 입구

	엔피시 설명 : 퀘스트


*/
importPackage(Packages.server.quest);

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
            if (qm.getQuestStatus(2603) == 0) {
		qm.forceStartQuest();
                qm.dispose();
	    } else if (qm.getQuestStatus(2603) == 1) {
		if (qm.getPlayer().getQuest(MapleQuest.getInstance(2603)).getMobKills(100002) == 20) {
		    qm.sendNext("하아... 느려, 느리다고. 미안하지만, 너 정말 재능이 없는 것 같다. 이대로 수련을 해봐야 과연 이도류를 제대로 배울 수 있을지 모르겠어.");
		} else {
		    qm.sendOk("아직 다 잡지 못했는데? 그리고.. 핵을 쓰다 걸리면 영정이다 ^^ 넌 의심스러운데?");
                    qm.dispose();
                }
	    }
        } else if (status == 1) {
	    qm.sendPrev("....라고 말하기까지 했는데 여전히 무표정? 너 정말 재밌는 녀석인걸? 이봐, 홍아! 나와봐. 이 녀석 꽤나 흥미롭다고?");
        } else if (status == 2) {
            qm.gainExp(50);
            qm.spawnNPCTemp(1057001, -932, 152);
	    qm.forceCompleteQuest();
            qm.dispose();
        }
    }
}