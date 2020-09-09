function start()
{
	St = -1;
	action(1, 0, 0);
}

function action(M, T, S)
{	
	if(M != 1)
	{
		cm.dispose();
		return;
	}

	if(M == 1)
	St++;
	else
	St--;

	if(St == 0)
	{
		cm.sendSimple("#e<보스: 힐라>#n\r\n 힐라를 처치하고, 아스완의 진정한 해방을 이뤄낼 준비는 되셨습니까? 다른 지역에 있는 파티원이 있다면, 모두 모여주세요.\r\n\r\n#b"
			+ "#L0#<보스: 힐라> 입장을 신청한다.#l");
	}

	else if(St == 1)
	{
		cm.sendSimple("#e<보스: 힐라>#n\r\n원하시는 모드를 선택해주세요.\r\n\r\n"
			+ "#L0#노말 모드 (레벨 120 이상)#l\r\n"
			+ "#L1#하드 모드 (레벨 170 이상)#l\r\n");
	}

	else if(St == 2)
	{
		levelType = S;
		partyPass = true;
		partyMany = 0;
		switch(levelType)
		{
			case 1:
			setMap = 262031100;
			setLevel = 170;
			break;

			default:
			setMap = 262030100;
			setLevel = 120;
			break;
		}

		if(cm.getParty() == null)
		{
			cm.sendOk("파티를 생성한 후 도전해 주세요.");
			cm.dispose();
			return;
		}

		if(cm.getPlayerCount(setMap) != 0 || cm.getPlayerCount(Number(setMap + 100)) != 0 || cm.getPlayerCount(Number(setMap + 200)) != 0)
		{
			cm.sendOk("이미 누군가가 힐라에 도전하고 있습니다.\r\n다른 채널을 이용해 주세요.");
			cm.dispose();
			return;
		}

		if(!cm.isLeader())
		{
			cm.sendOk("파티장만이 입장을 신청할 수 있습니다.");
			cm.dispose();
			return;
		}

		if(partyPass)
		{
			selStr = "파티원 중 #b#e";
			for(i = 0; i < cm.getPartyMembers().size(); i++)
			{
				if(cm.getPlayer().getParty().getMembers().get(i).getLevel() < setLevel)
				{
					partyPass = false;
					partyMany++;
					selStr += cm.getPlayer().getParty().getMembers().get(i).getName();
					if(partyMany != cm.getPartyMembers().size())
					{
						selStr += ", ";
					}
				}
			}
			selStr += "#n#k(은)는 레벨이 만족하지 않아서 입장을 할 수 없습니다.";
		}

		if(!partyPass)
		{
			cm.sendOk(selStr);
			cm.dispose();
			return;
		}

		em  = cm.getEventManager("BossHillah");
		eim = em.readyInstance();
		eim.setProperty("Global_StartMap", setMap+ "");
		eim.setProperty("Global_ExitMap", 262030000 + "");
		eim.setProperty("Global_RewardMap", 262030000 + "");
		eim.setProperty("Global_Status", 1 + "");
		eim.setProperty("Global_MinPerson", 1 + "");
		eim.setProperty("Global_checkStr", 0);
		eim.setProperty("Global_checkEnd", 0);
		eim.setProperty("Global_Bosstime", 0);
		eim.registerParty(cm.getParty(), cm.getMap());
		cm.dispose();
	}
}