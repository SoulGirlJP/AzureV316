// 아스완 전장 : 점령전 공략!
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
	    qm.sendNext("점령전의 기본 공략법은 끊임없이 밀려오는 적들이 #b좌측 끝으로 가지 못하게#k 막으면서 맵의 우측 끝에 있는 #r가디언 타워를 부수는 것#k일세. 적들이 좌측 끝가지 도달하게 되면 #r가디언 타워가 체력을 회복#k하기 때문에, 적들을 잘 처리하는 것이 우선 과제라고 할 수 있지."); 
	} else if (status == 1) {
		qm.sendNextPrev("가운데 있는 아울 타워는 강력한 예고 공격을 하네. 머리 위에 아래같은 돌덩이들이 생성되면 곧 떨어져 피해를 입게 되니 빠르게 피해 주게.\r\n						#i3800258#");
    } else if (status == 2) {
		qm.sendNextPrev("가디언 타워의 기본 공격인 레이져는 강력한 데미지를 주지만 #b아군 몬스터가 주위에 있을 땐 아군 몬스터만 공격#k한다네. 체력에 여유가 없다면 아군 몬스터와 함께 가디언 타워를 공격하게.");
    } else if (status == 3) {
		qm.sendNextPrev("가디언 타워도 아울 타워처럼 강력한 예고 공격을 한다네. #b바닥에 아래와 같은 석상들이 생성#k되면 그 자리에 곧 강력한 데미지를 주는 공격이 가해지니 빠르게 피해 주어야 하네.\r\n						#i3800259#");
    } else if (status == 4) {
		qm.sendNextPrev("여기까지가 이지, 노멀 난이도에서의 공략법일세. 하지만 하드와 헬 난이도에서는 점령전을 더욱 힘들게 하는 힐라의 수호 몬스터가 등장하여 괴롭히지. 힐라의 수호 몬스터 알타는 이렇게 생겼네.\r\n						#i3800260#");
    } else if (status == 5) {
		qm.sendNextPrev("알타는 전장 전체에 강력한 알타의 예고 공격이 이루어진다네.\r\n			#i3800262#\r\n먹구름이 생기고\r\n			#i3800261#\r\n바닥에 마법진이 생긴 곳으로부터 피하게. 가디언 타워를 쉽게 공략하려면 알타부터 파괴하는 것이 도움이 될 걸세.");
    } else if (status == 6) {
		qm.sendNextPrev("여기까지가 점령전의 기본적인 공략법일세. 혹시 공략이 기억나지 않거나 다시 듣고 싶다면 오른쪽에 있는 #b재무대신 우드완#k에게 다시 물어보게나. 내가 해 준 그대로 다시 일러줄 걸세 이제 실전에 도전해 보겠는가?");
		qm.completeQuest(3976);
		qm.dispose();
	}
}