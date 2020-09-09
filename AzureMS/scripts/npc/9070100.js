importPackage(Packages.packet.creators);

function start() {
    status = -1;
    action (1, 0, 0);
}

var START_MATCH = 1;
var START_PARTY = 2;
var TODAY_LIMIT = 3;
function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status--;
    }
    if (mode == 1) {
        status++;
    }
    if (status == -1) {
       cm.dispose();
   }
    if (status == 0) {
		cm.sendSpirit("#b자네는 무엇을 원하나?#k"
				+"\r\n#L1#빠른시작 (매칭을 통해 18인 도전)#l"
				+"\r\n#L2#파티플레이 (1인 ~ 18인으로 도전)#l"
				+"\r\n#L3#오늘의 남은 횟수 확인)#l",true,0);
		}

	else if(status == 1)
		{
		S1 = selection;
		switch(S1)
			{
			case START_MATCH:
				if(cm.getPlayer().getParty())
					{
					cm.sendOk("자네, 파티를 맺은 상태군. 파티로 우르스에게 도전하려면 파티플레이를 선택해주게.");
					cm.dispose();
					return;
					} else {
                                                            cm.getClient().getSession().write(UIPacket.OpenUrus());
                                                            }
			break;

			case START_PARTY:
				if(!cm.getPlayer().getParty())
					{
					cm.sendOk("자네, 파티에 들지 않았군. 개인으로 다른 용사들과 같이 우르스에게 도전하려거든 빠른시작을 선택하게나.");
					cm.dispose();
					return;
					} else
				if(!cm.isLeader())
					{
					cm.sendOk("이부분 대사안땀... 못땀... ㅠㅠ");
					cm.dispose();
					return;
					} else {
                                                            cm.getClient().getSession().write(UIPacket.OpenUrus());
                                                            }
			break;

			case TODAY_LIMIT:
			cm.sendNext("오늘은 아직 ★번 더 도전할 수 있다네. 단, 계정당으로 입장이 제한된다는 것을 잊지말게.");
			cm.dispose();
			break;

			default:
			cm.getPlayer().dropMessage(5, "예상되지 못한 작업입니다. 관리자에게 문의주세요.");
			cm.dispose();
			break;
			}
		}
}		
