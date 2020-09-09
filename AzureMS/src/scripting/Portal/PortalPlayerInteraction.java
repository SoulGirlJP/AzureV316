package scripting.Portal;

import client.MapleClient;
import scripting.AbstractPlayerInteraction;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MonsterEntity.OverrideMonsterStats;
import server.Maps.MapleMapHandling.MaplePortal;

public class PortalPlayerInteraction extends AbstractPlayerInteraction
{

	private final MaplePortal portal;

	public PortalPlayerInteraction(final MapleClient c, final MaplePortal portal)
	{
		super(c, portal.getId(), c.getPlayer().getMapId(), "");
		this.portal = portal;
	}

	public final MaplePortal getPortal()
	{
		return portal;
	}

	public final void inFreeMarket()
	{
		if (getMapId() != 910000000)
		{
			if (getPlayer().getLevel() >= 15)
			{
				saveLocation("FREE_MARKET");
				playPortalSE();
				warp(910000000, "st00");
			}
			else
			{
				playerMessage(5, "You must be level 15 in order to be able to enter the FreeMarket.");
			}
		}
	}

	public final void inArdentmill()
	{
		if (getMapId() != 910001000)
		{
			if (getPlayer().getLevel() >= 10)
			{
				saveLocation("ARDENTMILL");
				playPortalSE();
				warp(910001000, "st00");
			}
			else
			{
				playerMessage(5, "You must be level 15 in order to be able to enter the Crafting Town.");
			}
		}
	}

	// summon one monster on reactor location
	@Override
	public void spawnMonster(int id)
	{
		spawnMonster(id, 1, portal.getPosition());
	}

	// summon monsters on reactor location
	@Override
	public void spawnMonster(int id, int qty)
	{
		spawnMonster(id, qty, portal.getPosition());
	}

	// summon special monsters on reactor location
	public void spawnMonster(int id, long hp, int mp, int qty, int exp)
	{
		MapleMonster monster = MapleLifeProvider.getMonster(id);
		OverrideMonsterStats stats = new OverrideMonsterStats();
		stats.setOHp(hp);
		stats.setOMp(mp);
		stats.setOExp(exp);
		monster.setOverrideStats(stats);
		for (int i = 0; i < qty; i++)
		{
			getMap().spawnMonsterOnGroundBelow(monster, portal.getPosition());
		}
	}
}
