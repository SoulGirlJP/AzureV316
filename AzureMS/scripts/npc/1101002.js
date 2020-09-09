


/*

	퓨어 온라인 소스 팩의 스크립트 입니다.

        제작 : 주크블랙

	엔피시아이디 : 
	
	엔피시 이름 :

	엔피시가 있는 맵 : 

	엔피시 설명 : 


*/


var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
        if (cm.getQuestStatus(3507) == 1) {
            cm.completeQuest(3507);
            cm.sendOk("잃어버린 추억을 찾아 퀘스트가 완료되었습니다. #b후회의 길을 걷는자 1#k 퀘스트로 진행해 주세요.");
            cm.dispose();
        } else if (cm.getQuestStatus(3513) >= 1 && cm.getQuestStatus(3514) != 2) {
            if (cm.getMeso() >= 1000000) {
                cm.gainMeso(-1000000);
                cm.completeQuest(3514);
                cm.getPlayer().message(1, "'감정을 파는 마제사' 퀘스트가 완료되었습니다. [망각의 길을 걷는자 1] 퀘스트로 진행해 주세요.");
                cm.getPlayer().message(6, "'감정을 파는 마제사' 퀘스트가 완료되었습니다. [망각의 길을 걷는자 1] 퀘스트로 진행해 주세요.");
                cm.dispose();
            } else {
                cm.sendOk("'감정을 파는 마제사' 퀘스트를 완료하기 위해서는 100만 메소가 필요합니다.");
                cm.dispose();
            }
        } else {
            cm.sendOk("잃어버린 추억을 찾아 퀘스트를 받은 후 이용해 주세요.");
            cm.dispose();
        }
    }
}


