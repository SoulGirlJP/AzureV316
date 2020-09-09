/*  
  This file is part of Mushy.
  Copyright (c) 2015 ~ 2016 Maxcloud <no-email@provided.com>
  
  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License as
  published by the Free Software Foundation; either version 2 of the
  License, or (at your option) any later version.  See the file
  COPYING included with this distribution for more information.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package server.Quests;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Character.MapleCharacter;
import client.MapleQuestStatus;
import client.ItemInventory.InventoryException;
import client.ItemInventory.MapleInventoryType;
import client.Skills.ISkill;
import client.Skills.SkillEntry;
import client.Skills.SkillFactory;
import client.Stats.PlayerStatList;
import constants.GameConstants;
import provider.Lib.Bin.ReadBin;
import connections.Packets.MainPacketCreator;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import tools.FileoutputUtil;
import tools.RandomStream.Randomizer;

class MapleQuestAction implements Serializable
{

	private static final long serialVersionUID = 9179541993413738569L;

	private MapleQuest quest;

	private int exp;
	private int pop;
	private int buffItemID;
	private int nextQuest;
	private int money;

	private int insightExp;
	private int senseExp;
	private int charismaExp;
	private int charmExp;
	private int craftExp;
	private int willExp;

	private List<QuestItem> items = new ArrayList<QuestItem>();
	private List<QuestSkill> skills = new ArrayList<QuestSkill>();

	private Map<Integer, Byte> quests = new HashMap<Integer, Byte>();
	private Map<Byte, List<Short>> sp = new HashMap<Byte, List<Short>>();

	private List<Short> jobs = new ArrayList<Short>();

	protected MapleQuestAction(MapleQuest quest, ReadBin data) throws IOException
	{
		this.quest = quest;

		this.exp = data.readInt();
		this.pop = data.readInt();
		this.buffItemID = data.readInt();
		this.nextQuest = data.readInt();
		this.money = data.readInt();

		this.insightExp = data.readInt();
		this.senseExp = data.readInt();
		this.charismaExp = data.readInt();
		this.charmExp = data.readInt();
		this.craftExp = data.readInt();
		this.willExp = data.readInt();

		// item required / giving item.
		int size = data.readShort();
		for (short b = 0; b < size; b++)
		{

			int id = data.readInt();
			byte count = data.readByte();
			int period = data.readInt();
			byte gender = data.readByte();
			int job = data.readInt();
			int jobex = data.readInt();
			int prop = data.readInt();

			this.items.add(new QuestItem(id, count, period, gender, job, jobex, prop));
		}

		// skill gain
		size = data.readShort();
		for (short b = 0; b < size; b++)
		{

			int skillid = data.readInt();
			byte skillLevel = data.readByte();
			byte masterLevel = data.readByte();

			List<Short> jobs = new ArrayList<>();
			int jSize = data.readShort();
			for (short s = 0; s < jSize; s++)
			{
				short j = data.readShort();

				jobs.add(j);
			}

			this.skills.add(new QuestSkill(skillid, skillLevel, masterLevel, jobs));
		}

		// quest requirement(s)
		size = data.readShort();
		for (short b = 0; b < size; b++)
		{
			int id = data.readInt();
			byte state = data.readByte();

			this.quests.put(id, state);
		}

		// sp
		size = data.readShort();
		for (short b = 0; b < size; b++)
		{
			byte sp = data.readByte();

			List<Short> jobs = new ArrayList<>();
			int jSize = data.readShort();
			for (short s = 0; s < jSize; s++)
			{
				short j = data.readShort();

				jobs.add(j);
			}

			this.sp.put(sp, jobs);
		}

		// job
		size = data.readShort();
		for (short b = 0; b < size; b++)
		{
			short j = data.readShort();

			this.jobs.add(j);
		}
	}

	private static boolean canGetItem(QuestItem item, MapleCharacter c)
	{
		if (item.getGender() != 2 && item.getGender() >= 0 && item.getGender() != c.getGender())
		{
			return false;
		}
		if (item.getJob() > 0)
		{
			final List<Integer> code = getJobBy5ByteEncoding(item.getJob());
			boolean found = code.stream().anyMatch(codec -> codec / 100 == c.getJob() / 100);

			if (!found && item.getJobEx() > 0)
			{
				final List<Integer> codeEx = getJobBySimpleEncoding(item.getJobEx());
				found = codeEx.stream().anyMatch(codec -> (codec / 100 % 10) == (c.getJob() / 100 % 10));
			}
			return found;
		}
		return true;
	}

	public final boolean RestoreLostItem(final MapleCharacter c, final int itemid)
	{
		for (QuestItem item : items)
		{
			if (item.getItemId() == itemid)
			{
				if (!c.haveItem(item.getItemId(), item.getCount(), true, false))
				{
					InventoryManipulator.addById(c.getClient(), item.getItemId(), (short) item.getCount(),
							"Obtained from quest (Restored) " + quest.getId() + " on "
									+ FileoutputUtil.CurrentReadable_Date());
				}
				return true;
			}
		}
		return false;
	}

	public void runStart(MapleCharacter c, Integer extSelection)
	{

		// exp
		if (exp > 0)
		{
			MapleQuestStatus status = c.getQuest(quest);

			if (status.getForfeited() > 0)
			{
				return;
			}

			int rate = GameConstants.getExpRate_Quest(c.getLevel());
			int bonus = 1;
			int trait = 1;

			c.gainExp(exp * rate * bonus * trait / 100, true, true, true);
		}

		// pop
		if (pop > 0)
		{
			MapleQuestStatus status = c.getQuest(quest);

			if (status.getForfeited() > 0)
			{
				return;
			}

			int gain = pop;
			c.addFame(gain);
			c.updateSingleStat(PlayerStatList.FAME, c.getFame());
			c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowFameGain(gain));
		}

		// buffitemid
		if (buffItemID > -1)
		{
			MapleQuestStatus status = c.getQuest(quest);

			if (status.getForfeited() > 0)
			{
				return;
			}

			int tobuff = buffItemID;
			if (tobuff <= 0)
			{
				return;
			}
			ItemInformation.getInstance().getItemEffect(tobuff).applyTo(c);
		}

		// nextquest
		if (nextQuest > -1)
		{
			c.getClient().getSession().writeAndFlush(
					MainPacketCreator.updateQuestFinish(quest.getId(), c.getQuest(quest).getNpc(), nextQuest));
		}

		// money
		if (money > -1)
		{
			MapleQuestStatus status = c.getQuest(quest);

			if (status.getForfeited() > 0)
			{
				return;
			}

			c.gainMeso(money, true, true);
		}

		// item required/given
		if (!items.isEmpty())
		{

			// first check for randomness in item selection
			Map<Integer, Integer> props = new HashMap<>();
			for (QuestItem item : items)
			{
				if (item.getProp() > 0 && canGetItem(item, c))
				{
					for (int i = 0; i < item.getProp(); i++)
					{
						props.put(props.size(), item.getItemId());
					}
				}
			}

			int selection = 0;
			int extNum = 0;
			if (props.size() > 0)
			{
				selection = props.get(Randomizer.nextInt(props.size()));
			}

			for (QuestItem item : items)
			{
				if (!canGetItem(item, c))
				{
					continue;
				}

				final int id = item.getItemId();

				if (item.getProp() != -2)
				{
					if (item.getProp() == -1)
					{
						if (extSelection != null && extSelection != extNum++)
						{
							continue;
						}
					}
					else if (id != selection)
					{
						continue;
					}
				}

				final short count = (short) item.getCount();
				if (count < 0)
				{ // remove items
					try
					{
						InventoryManipulator.removeById(c.getClient(), GameConstants.getInventoryType(id), id,
								(count * -1), true, false);
					}
					catch (InventoryException ie)
					{
						// it's better to catch this here so we'll atleast try to remove the other items
						System.err.println("[h4x] Completing a quest without meeting the requirements" + ie);
					}
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, count, true));
				}
				else
				{ // add items
					final int period = item.getPeriod() / 1440; // im guessing.
					final String name = ItemInformation.getInstance().getName(id);
					if (id / 10000 == 114 && name != null && name.length() > 0)
					{ // medal
						final String msg = "You have attained title <" + name + ">";
						c.dropMessage(-1, msg);
						c.dropMessage(5, msg);
					}
					InventoryManipulator.addById(c.getClient(), id, count, "", null, period, false,
							"Obtained from quest " + quest.getId() + " on " + FileoutputUtil.CurrentReadable_Date());
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, count, true));
				}

			}
		}

		// skill gain
		if (!skills.isEmpty())
		{
			final Map<ISkill, SkillEntry> sa = new HashMap<>();
			for (QuestSkill skills : skills)
			{

				int skillid = skills.getSkillId();
				int skillLevel = skills.getSkillLevel();
				int masterLevel = skills.getMasterLevel();

				ISkill skill = SkillFactory.getSkill(skillid);

				boolean found = false;
				for (int applicableJob : skills.getJobs())
				{
					if (c.getJob() == applicableJob)
					{
						found = true;
						break;
					}
				}

				if (skill.isBeginnerSkill() || found)
				{
					byte level = (byte) Math.max(skillLevel, c.getSkillLevel(skill));
					byte mLevel = (byte) Math.max(masterLevel, c.getMasterLevel(skill));
					long expire = SkillFactory.getDefaultSExpiry(skill);

					sa.put(skill, new SkillEntry(level, mLevel, expire));
				}
			}
			c.changeSkillsLevel(sa);
		}

		// quest requirement
		if (!quests.isEmpty())
		{
			for (Entry<Integer, Byte> q : quests.entrySet())
			{
				MapleQuest quest = MapleQuest.getInstance(q.getKey());
				MapleQuestStatus status = new MapleQuestStatus(quest, q.getValue());

				c.updateQuest(status);
			}
		}

		// sp
		if (!sp.isEmpty())
		{
			MapleQuestStatus status = c.getQuest(quest);
			if (status.getForfeited() > 0)
			{
				return;
			}

			for (Entry<Byte, List<Short>> entry : sp.entrySet())
			{
				byte sp = entry.getKey();
				List<Short> jobs = entry.getValue();

				if (jobs.size() > 0)
				{

					int job = 0;
					for (short j : jobs)
					{
						if (c.getJob() >= j && j > job)
						{
							job = j;
						}
					}

					if (job == 0)
					{
						c.gainSP(sp);
					}
					else
					{
						c.gainSP(sp, GameConstants.getSkillBook(job));
					}

				}
				else
				{
					c.gainSP(sp);
				}
			}

		}

		// job
		if (!jobs.isEmpty())
		{
			// TODO: ...
		}

	}

	public boolean checkEnd(MapleCharacter c, Integer extSelection)
	{

		if (money > -1)
		{
			final int meso = money;
			if (c.getMeso() + meso < 0)
			{
				c.dropMessage(1, "Meso exceed the max amount, 9999999999.");
				return false;
			}
			else if (meso < 0 && c.getMeso() < Math.abs(meso))
			{
				c.dropMessage(1, "Insufficient meso.");
				return false;
			}
		}

		if (!items.isEmpty())
		{
			// first check for randomness in item selection
			final Map<Integer, Integer> props = new HashMap<>();

			for (QuestItem item : items)
			{
				if (item.getProp() > 0 && canGetItem(item, c))
				{
					for (int i = 0; i < item.getProp(); i++)
					{
						props.put(props.size(), item.getItemId());
					}
				}
			}
			int selection = 0;
			int extNum = 0;
			if (props.size() > 0)
			{
				selection = props.get(Randomizer.nextInt(props.size()));
			}
			byte eq = 0, use = 0, setup = 0, etc = 0, cash = 0;

			for (QuestItem item : items)
			{
				if (!canGetItem(item, c))
				{
					continue;
				}
				final int id = item.getItemId();
				if (item.getProp() != -2)
				{
					if (item.getProp() == -1)
					{
						if (extSelection != null && extSelection != extNum++)
						{
							continue;
						}
					}
					else if (id != selection)
					{
						continue;
					}
				}
				final short count = (short) item.getCount();
				if (count < 0)
				{ // remove items
					if (!c.haveItem(id, count, false, true))
					{
						c.dropMessage(1, "You are short of some item to complete quest.");
						return false;
					}
				}
				else
				{ // add items
					if (ItemInformation.getInstance().isPickupRestricted(id) && c.haveItem(id, 1, true, false))
					{
						c.dropMessage(1, "You have this item already: " + ItemInformation.getInstance().getName(id));
						return false;
					}
					switch (GameConstants.getInventoryType(id))
					{
					case EQUIP:
						eq++;
						break;
					case USE:
						use++;
						break;
					case SETUP:
						setup++;
						break;
					case ETC:
						etc++;
						break;
					case CASH:
						cash++;
						break;
					}
				}
			}
			if (c.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < eq)
			{
				c.dropMessage(1, "Please make space for your Equip inventory.");
				return false;
			}
			else if (c.getInventory(MapleInventoryType.USE).getNumFreeSlot() < use)
			{
				c.dropMessage(1, "Please make space for your Use inventory.");
				return false;
			}
			else if (c.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < setup)
			{
				c.dropMessage(1, "Please make space for your Setup inventory.");
				return false;
			}
			else if (c.getInventory(MapleInventoryType.ETC).getNumFreeSlot() < etc)
			{
				c.dropMessage(1, "Please make space for your Etc inventory.");
				return false;
			}
			else if (c.getInventory(MapleInventoryType.CASH).getNumFreeSlot() < cash)
			{
				c.dropMessage(1, "Please make space for your Cash inventory.");
				return false;
			}
		}

		return true;
	}

	public void runEnd(MapleCharacter c, Integer extSelection)
	{

		// exp
		if (exp > 0)
		{
			int rate = GameConstants.getExpRate_Quest(c.getLevel());

			c.gainExp(exp * rate, true, true, true);
		}

		// pop
		if (pop > 0)
		{
			int gain = pop;
			c.addFame(gain);
			c.updateSingleStat(PlayerStatList.FAME, c.getFame());
			c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowFameGain(gain));
		}

		// buffitemid
		if (buffItemID > -1)
		{
			MapleQuestStatus status = c.getQuest(quest);

			int tobuff = buffItemID;
			if (tobuff <= 0)
			{
				return;
			}
			ItemInformation.getInstance().getItemEffect(tobuff).applyTo(c);
		}

		// nextquest
		if (nextQuest > -1)
		{
			c.getClient().getSession().writeAndFlush(
					MainPacketCreator.updateQuestFinish(quest.getId(), c.getQuest(quest).getNpc(), nextQuest));
		}

		// money
		if (money > -1)
		{
			c.gainMeso(money, true, true);
		}

		// item required/given
		if (!items.isEmpty())
		{
			// first check for randomness in item selection
			Map<Integer, Integer> props = new HashMap<>();
			for (QuestItem item : items)
			{
				if (item.getProp() > 0 && canGetItem(item, c))
				{
					for (int i = 0; i < item.getProp(); i++)
					{
						props.put(props.size(), item.getItemId());
					}
				}
			}

			int selection = 0;
			int extNum = 0;
			if (props.size() > 0)
			{
				selection = props.get(Randomizer.nextInt(props.size()));
			}

			for (QuestItem item : items)
			{
				if (!canGetItem(item, c))
				{
					continue;
				}
				final int id = item.getItemId();
				if (item.getProp() != -2)
				{
					if (item.getProp() == -1)
					{
						if (extSelection != null && extSelection != -1 && extSelection != extNum++)
						{
							continue;
						}
					}
					else if (id != selection)
					{
						continue;
					}
				}
				final short count = (short) item.getCount();
				if (count < 0)
				{ // remove items
					InventoryManipulator.removeById(c.getClient(), GameConstants.getInventoryType(id), id, (count * -1),
							true, false);
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, count, true));
				}
				else
				{ // add items
					final int period = item.getPeriod() / 1440; // im guessing.
					final String name = ItemInformation.getInstance().getName(id);
					if (id / 10000 == 114 && name != null && name.length() > 0)
					{ // medal
						final String msg = "You have attained title <" + name + ">";
						c.dropMessage(-1, msg);
						c.dropMessage(5, msg);
					}
					InventoryManipulator.addById(c.getClient(), id, count, "", null, period);
					c.getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, count, true));
				}
			}
		}

		// quest requirement
		if (!quests.isEmpty())
		{
			for (Entry<Integer, Byte> q : quests.entrySet())
			{
				MapleQuest quest = MapleQuest.getInstance(q.getKey());
				MapleQuestStatus status = new MapleQuestStatus(quest, q.getValue());

				c.updateQuest(status);
			}
		}

		// skill gain
		if (!skills.isEmpty())
		{
			final Map<ISkill, SkillEntry> sa = new HashMap<>();
			for (QuestSkill skills : skills)
			{

				int skillid = skills.getSkillId();
				int skillLevel = skills.getSkillLevel();
				int masterLevel = skills.getMasterLevel();

				ISkill skill = SkillFactory.getSkill(skillid);

				boolean found = false;
				for (int applicableJob : skills.getJobs())
				{
					if (c.getJob() == applicableJob)
					{
						found = true;
						break;
					}
				}

				if (skill.isBeginnerSkill() || found)
				{
					byte level = (byte) Math.max(skillLevel, c.getSkillLevel(skill));
					byte mLevel = (byte) Math.max(masterLevel, c.getMasterLevel(skill));
					long expire = SkillFactory.getDefaultSExpiry(skill);

					sa.put(skill, new SkillEntry(level, mLevel, expire));
				}
			}
			c.changeSkillsLevel(sa);
		}

		// sp
		if (!sp.isEmpty())
		{
			MapleQuestStatus status = c.getQuest(quest);
			if (status.getForfeited() > 0)
			{
				return;
			}

			for (Entry<Byte, List<Short>> entry : sp.entrySet())
			{
				byte sp = entry.getKey();
				List<Short> jobs = entry.getValue();

				if (jobs.size() > 0)
				{

					int job = 0;
					for (short j : jobs)
					{
						if (c.getJob() >= j && j > job)
						{
							job = j;
						}
					}

					if (job == 0)
					{
						c.gainSP(sp);
					}
					else
					{
						c.gainSP(sp, GameConstants.getSkillBook(job));
					}

				}
				else
				{
					c.gainSP(sp);
				}
			}

		}
	}

	private static List<Integer> getJobBy5ByteEncoding(int encoded)
	{
		List<Integer> ret = new ArrayList<>();
		if ((encoded & 0x1) != 0)
		{
			ret.add(0);
		}
		if ((encoded & 0x2) != 0)
		{
			ret.add(100);
		}
		if ((encoded & 0x4) != 0)
		{
			ret.add(200);
		}
		if ((encoded & 0x8) != 0)
		{
			ret.add(300);
		}
		if ((encoded & 0x10) != 0)
		{
			ret.add(400);
		}
		if ((encoded & 0x20) != 0)
		{
			ret.add(500);
		}
		if ((encoded & 0x400) != 0)
		{
			ret.add(1000);
		}
		if ((encoded & 0x800) != 0)
		{
			ret.add(1100);
		}
		if ((encoded & 0x1000) != 0)
		{
			ret.add(1200);
		}
		if ((encoded & 0x2000) != 0)
		{
			ret.add(1300);
		}
		if ((encoded & 0x4000) != 0)
		{
			ret.add(1400);
		}
		if ((encoded & 0x8000) != 0)
		{
			ret.add(1500);
		}
		if ((encoded & 0x20000) != 0)
		{
			ret.add(2001); // im not sure of this one
			ret.add(2200);
		}
		if ((encoded & 0x100000) != 0)
		{
			ret.add(2000);
			ret.add(2001); // ?
		}
		if ((encoded & 0x200000) != 0)
		{
			ret.add(2100);
		}
		if ((encoded & 0x400000) != 0)
		{
			ret.add(2001); // ?
			ret.add(2200);
		}

		if ((encoded & 0x40000000) != 0)
		{ // i haven't seen any higher than this o.o
			ret.add(3000);
			ret.add(3200);
			ret.add(3300);
			ret.add(3500);
		}
		return ret;
	}

	private static List<Integer> getJobBySimpleEncoding(int encoded)
	{
		List<Integer> ret = new ArrayList<>();
		if ((encoded & 0x1) != 0)
		{
			ret.add(200);
		}
		if ((encoded & 0x2) != 0)
		{
			ret.add(300);
		}
		if ((encoded & 0x4) != 0)
		{
			ret.add(400);
		}
		if ((encoded & 0x8) != 0)
		{
			ret.add(500);
		}
		return ret;
	}

	public List<QuestSkill> getSkills()
	{
		return skills;
	}

	public List<QuestItem> getItems()
	{
		return items;
	}
}
