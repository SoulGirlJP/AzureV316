// 아스완 전장 : 방어전 공략!
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
	    qm.sendNext("방어전의 기본 공략법은 #b시그너스 가드스톤이 파괴되지 않도록#k 지키는 것일세. 끊임없이 밀려오는 적들이 가드스톤을 파괴하지 못하게 물리치면서, 일정 주기로 반복되는 #r디스트로이어의 공격을 저지하는 것#k이 방어전의 과제라고 할 수 있지."); 
	} else if (status == 1) {
		qm.sendNextPrev("밀려오는 몬스터들을 상대하다 보면 어느샌가 화면 가운데에 아래와 같은 메세지가 표시될 걸세. #i3800264#메세지가 표시되면 #b맵 어딘가에 디스트로이어가 시그너스 가드스톤을 포격하기 위한 준비#k를 하고 있다는 뜻이야.");
    } else if (status == 2) {
		qm.sendNextPrev("디스트로이어의 모습은 아래와 같네.<이지/노멀 난이도><하드/헬 난이도>#i3800266##i3800263##r난이도가 높을수록 준비 시간이 빠르고, 포격의 위력은 강해진다네#k. 하지만 포격을 준비하는 동안에는 아무런 공격도 할 수 없으니 가드스톤 주위의 적들이 정리되었다면 빠르게 처리하는 것이 중요하네.");
    } else if (status == 3) {
		qm.sendNextPrev("여기까지가 방어전의 기본적인 공략법일세. 혹시 공략이 기억나지 않거나 다시 듣고 싶다면 오른쪽에 있는 #b재무대신 우드완#k에게 다시 물어보게나. 내가 해 준 그대로 다시 일러줄 걸세. 이제 실전에 도전해 보겠는가?");
		qm.completeQuest(3978);
		qm.dispose();
	}
}