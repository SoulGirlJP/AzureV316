function act()
{
	rm.changeMusic("Bgm06/FinalFight");
	for (i = 8800023; i < 8800031; i++)
	{
		rm.spawnMonster(i);
	}
	rm.spawnMonster(8800022);
	rm.mapMessage("원석의 힘으로 자쿰이 소환됩니다.");
}