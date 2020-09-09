// 아스완 전장 : 보급전 공략!
// made by 렌트리아(tnqhd1124) 또는 컴퓨터(tnqhd1139)
// Pure DEV

importPackage(Packages.server.quest);

var status = -1;
var select= -1;
function start(mode, type, selection) {
	if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
	}
    if (status == 0) {
	    qm.sendNext("보급전은 별도의 공략은 없고, 참여 방법만 설명해 주겠네. #r오후 2시부터 8시 사이#k에 보급전에 참여하면 #b화면 좌측 #i3800265#퀘스트 알리미#k를 통해 해방전 보급 모드 퀘스트를 시작할 수 있네."); 
	} else if (status == 1) {
		qm.sendNextPrev("퀘스트를 받고 몬스터를 사냥하면 아스완 보급 상자#i4033160#를 구할 수 있지.#r보급 상자 100개#k를 모두 모았다면 맵 내의 포탈을 통해 밖으로 이동하여 나에게로 오면 완료할 수 있다네.");
    } else if (status == 2) {
		qm.sendNextPrev("보급전을 완료하면 명예 레벨에 따라 명성치를 보상으로 지급받을 수 있다네. #b보급전에 참여한 사람이 많을수록 해방전의 결과에 지대한 영향#k을 미칠 수 있으니 보급전도 소홀히 해서는 안될 것이야.");
    } else if (status == 3) {
		qm.sendNextPrev("여기까지가 보급전의 참여 방법일세. 혹시 공략이 기억나지 않거나 다시 듣고 싶다면 오른쪽에 있는 #b재무대신 우드완#k에게 다시 물어보게나. 내가 해 준 그대로 다시 일러줄 걸세. 이제 보급전에 도전해 보겠는가?");
		qm.completeQuest(3979);
		qm.dispose();
	}
}