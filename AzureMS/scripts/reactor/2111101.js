function act()
{
	rm.changeMusic("Bgm06/FinalFight");
	for (i = 8800103; i < 8800111; i++)
	{
		rm.spawnMonster(i);
	}
	rm.spawnMonster(8800102);
	rm.mapMessage("원석의 힘으로 카오스 자쿰이 소환됩니다.");
	var em = rm.getPlayer().getEventInstance();
}