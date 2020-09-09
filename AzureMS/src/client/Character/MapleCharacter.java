package client.Character;

import client.AntiCheat.CalcDamage;
import client.BingoGame;
import client.Stats.PlayerStatList;
import client.DailyGift;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import client.ItemInventory.Equip;
import client.ItemInventory.IEquip;
import client.ItemInventory.IItem;
import client.ItemInventory.InventoryContainer;
import client.ItemInventory.Item;
import client.ItemInventory.ItemFactory;
import client.ItemInventory.ItemFlag;
import client.ItemInventory.MapleInventory;
import client.ItemInventory.MapleInventoryType;
import client.Skills.CoolDownValueHolder;
import client.Skills.ISkill;
import client.Skills.InnerAbillity;
import client.Skills.InnerSkillValueHolder;
import client.Skills.LinkSkill;
import client.Skills.PhantomStealSkill;
import client.Skills.SkillEntry;
import client.Skills.SkillFactory;
import client.Skills.SkillMacro;
import client.Skills.SkillStatEffect;
import client.Skills.StackedSkillEntry;
import client.Skills.VCore;
import client.Stats.BuffStats;
import client.Stats.BuffStatsValueHolder;
import client.Stats.DiseaseStats;
import client.Stats.DiseaseValueHolder;
import client.Stats.EnchantEquipStats;
import client.Stats.PlayerStats;
import client.Community.MapleBuddy.BuddyList;
import client.Community.MapleBuddy.BuddylistEntry;
import client.Community.MapleGuild.MapleAlliance;
import client.Community.MapleGuild.MapleGuild;
import client.Community.MapleGuild.MapleGuildCharacter;
import handlers.ChatHandler.MapleMultiChat;
import handlers.ChatHandler.MapleMultiChatCharacter;
import client.Community.MapleParty.MapleParty;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Community.MapleParty.MaplePartyOperation;
import client.Community.MapleUserTrade;
import client.ForceAtom;
import client.ItemInventory.MapleItempotMain;
import client.ItemInventory.PetsMounts.MapleMount;
import client.ItemInventory.PetsMounts.MaplePet;
import client.MapleAndroid;
import client.MapleCarnivalParty;
import client.MapleClient;
import client.MapleKeyBinding;
import client.MapleKeyLayout;
import client.MapleProfession;
import client.MapleProfessionType;
import client.MapleQuestStatus;
import client.MapleReward;
import client.Skills.SkillEffectEntry;
import client.Skills.StealSkillEntry;
import constants.GameConstants;
import constants.ServerConstants;
import constants.Data.QuickMoveEntry;
import connections.Database.MYSQL;
import connections.Database.MYSQLException;
import handlers.GlobalHandler.MapleMechDoor;
import java.util.TimerTask;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.LauncherHandlers.ChracterTransfer;
import launcher.LauncherHandlers.MapleNewCharJobType.JobType;
import launcher.Utility.MapleHolders.MapleBuffValueHolder;
import launcher.Utility.MapleHolders.MapleCoolDownValueHolder;
import launcher.Utility.MapleHolders.MapleDiseaseValueHolder;
import launcher.Utility.MapleHolders.MaplePlayerHolder;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import connections.Packets.AndroidPacket;
import connections.Packets.CashPacket;
import connections.Packets.MainPacketCreator;
import connections.Packets.MobPacket;
import connections.Packets.PetPacket;
import connections.Packets.SLFCGPacket;
import connections.Packets.SoulWeaponPacket;
import connections.Packets.UIPacket;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.Items.InventoryManipulator;
import server.Items.ItemInformation;
import server.Items.MapleCashInventory;
import server.Items.MapleRing;
import server.Systems.MapleStorage;
import server.LifeEntity.MobEntity.MapleLifeProvider;
import server.LifeEntity.MobEntity.MonsterEntity.MapleMonster;
import server.LifeEntity.MobEntity.MobSkill;
import server.Maps.MapObject.AnimatedHinaMapObjectExtend;
import server.Maps.MapField.FieldLimitType;
import server.Maps.MapleCarnivalChallenge;
import server.Maps.MapleDoor;
import server.Maps.MapleDragon;
import server.Maps.MapleExtractor;
import server.Maps.MapleFootHold.MapleFoothold;
import server.Maps.MapleMapHandling.MapleMap;
import server.Maps.MapObject.MapleMapObject;
import server.Maps.MapObject.MapleMapObjectType;
import server.Maps.MapleMapHandling.MaplePortal;
import server.Maps.MapSummon.MapleSummon;
import server.Maps.MapleWorldMap.MapleWorldMapProvider;
import server.Maps.SavedLocationType;
import server.Movement.LifeMovementFragment;
import server.Quests.MapleQuest;
import server.Shops.IMapleCharacterShop;
import server.Shops.MapleShop;
import server.Shops.MapleShopFactory;
import server.Shops.MapleShopItem;
import tools.ArrayMap;
import tools.CurrentTime;
import tools.FileoutputUtil;
import tools.Pair;
import tools.StringUtil;
import tools.Timer.BuffTimer;
import tools.Timer.CloneTimer;
import tools.Timer.EtcTimer;
import tools.Triple;
import tools.RandomStream.PlayerRandomStream;
import tools.RandomStream.Randomizer;
import launcher.LauncherHandlers.MapleLoginHelper;
import connections.Packets.PacketUtility.WritingPacket;
import connections.Packets.SkillPackets.PhantomSkill;
import handlers.GlobalHandler.ItemInventoryHandler.MapleQuickSlot;
import scripting.EventManager.EventInstanceManager;
import server.Maps.MapSummon.SummonMovementType;
import tools.AttackPair;

public class MapleCharacter extends AnimatedHinaMapObjectExtend implements InventoryContainer, Serializable {

    public boolean GuildWarp = false;
    public int GuildMap = 0;

    private DailyGift dailyGift;

    private String name, chalktext, BlessOfFairy_Origin, BlessOfEmpress_Origin, TowerChairSetting = "#0";
    private boolean quickmoved = false;
    private transient Map<Integer, Integer> linkMobIds = new HashMap<Integer, Integer>();
    private transient Map<Integer, Integer> damageMeter = new ArrayMap<Integer, Integer>();
    private long lastCombo, lastfametime, lastBulletUsedTime, keydown_skill, lastSummonTime, lastChannelChange = System.currentTimeMillis(),
            exp, meso, pqStartTime;
    private byte gender, secondGender, skinColor, secondSkinColor, gmLevel, burning;
    public short level;
    public short job;
    public short combo;
    private List<MapleSummon> mines = new ArrayList<MapleSummon>();
    public IItem cashPacketTemp = null;
    public int reborns, tierReborns, RecoveryShoot = 0;
    private int accountid, id, headtitle = 0, rank, rankMove, worldRank, worldRankdMove, mpApUsed, hpApUsed, hair,
            hair2, face, face2, remainingAp, fame, mapid, initialSpawnPoint, guildid, guildrank, allianceRank,
            fallcounter, maplepoints, nxcash, realcash, messengerposition, followid, chair, itemEffect, subcategory,
            innerExp, innerLevel, artifactPoints, morphGage, cardStack, cardStackRunningId, betaclothes,
            FrozenMobCount = 0, MesoChairCount = 0, tempmeso = 0, maxmeso = 0, BlockCount = 0, BlockCoin = 0;
    private int[] remainingSp = new int[10];
    private transient MapleDragon dragon;
    public ScheduledFuture<?> rapidtimer1 = null;
    public ScheduledFuture<?> rapidtimer2 = null;
    public ScheduledFuture<?> willtimer1 = null, willtimer2 = null;
    public int RapidTimeCount = 0;
    public boolean 탐지기 = false;
    public Point summonxy = null;
    private boolean canDoor, smega, hidden, petLoot, followinitiator, followon, hasSummon;
    private int[] wishlist, savedLocations;
    private Map<Integer, List<Integer>> rocks;
    private boolean fishing2 = false;
    private long damage = 0;
    private int damagehit;
    private int damagehit2;
    private String hope = "";

    private transient AtomicInteger inst;
    private transient List<LifeMovementFragment> lastres;
    private transient ReentrantReadWriteLock visibleMapObjectsLock;
    private transient Set<MapleMonster> controlled;
    private transient Set<MapleMapObject> visibleMapObjects;

    private transient ScheduledFuture<?> hpDecreaseTask;
    private transient ScheduledFuture<?> mapTimeLimitTask, fishing, savetimeTask;
    private transient ScheduledFuture<?> infinityReGenTask = null;
    private transient ScheduledFuture<?> diabolicRecoveryTask = null;
    private transient ScheduledFuture<?> InduerenseTask = null;
    private transient ScheduledFuture<?> selfRecoveryTask = null;
    private transient ScheduledFuture<?> mercedesRecoveryTask = null;
    private transient ScheduledFuture<?> SurPlusTask = null;
    private transient ScheduledFuture<?> ej = null;

    private List<Integer> lastmonthfameids, extendedSlots;
    private int rankpoint;
    private List<MapleDoor> doors;
    private List<MapleMechDoor> mechdoors;
    private MaplePet[] pets = new MaplePet[3];
    private PhantomStealSkill steelskills;
    public int glass_plusCTS_Morph = 1;
    public int glass_minusCTS_Morph = 9999;
    private List<IItem> rebuyList;
    private MapleShop aswanShopList;
    private transient MapleExtractor extractor;
    public int vpoints;
    public int tt = 0;
    private Map<MapleQuest, MapleQuestStatus> quests;
    private Map<Integer, String> questinfo;
    private Map<ISkill, SkillEntry> skills = new LinkedHashMap<ISkill, SkillEntry>();
    public Map<ISkill, SkillEntry> matrixSkills = new LinkedHashMap<ISkill, SkillEntry>();
    private List<InnerSkillValueHolder> innerSkills;
    private transient Map<BuffStats, List<BuffStatsValueHolder>> effects = new LinkedHashMap<BuffStats, List<BuffStatsValueHolder>>();
    private transient Map<BuffStats, List<StackedSkillEntry>> stackedEffects = new LinkedHashMap<BuffStats, List<StackedSkillEntry>>();
    private MapleCashInventory cashInv;
    private Map<String, String> CustomValues = new HashMap<String, String>();
    private Map<String, Integer> CustomValues2 = new HashMap<String, Integer>();
    private Map<String, Integer> CustomValues3 = new HashMap<String, Integer>();
    private transient Map<Integer, Pair<Integer, MapleSummon>> summons;
    private transient Map<Integer, CoolDownValueHolder> coolDowns = new LinkedHashMap<Integer, CoolDownValueHolder>(50);
    private transient Map<DiseaseStats, DiseaseValueHolder> diseases;
    private MapleAlliance alliance;
    private BuddyList buddylist;
    private MapleClient client;
    private PlayerStats stats;
    private MapleAndroid android;
    private MapleCarnivalParty carnivalParty;
    private PlayerRandomStream CRand;
    public List<VCore> cores;
    private transient MapleMap map;
    private byte team;
    private transient Deque<MapleCarnivalChallenge> pendingCarnivalRequests;
    private transient MapleShop shop;
    private MapleStorage storage;
    private transient MapleUserTrade trade;
    private MapleMount mount;
    private MapleMultiChat messenger;
    private IMapleCharacterShop playerShop;
    private MapleParty party;
    private MapleGuildCharacter mgc;
    private transient EventInstanceManager eventInstance;
    private MapleInventory[] inventory;
    private SkillMacro[] skillMacros = new SkillMacro[5];
    private MapleKeyLayout keylayout;
    private MapleProfession profession;
    private SkillEffectEntry skilleffects;
    public transient long lastUsedSkill = System.currentTimeMillis();
    public int usedSkillFast = 0;
    private short ForcingItem = 0;
    private int askguildid = 0;
    private transient Map<Integer, Integer> wpForce = new HashMap<Integer, Integer>();
    private int wp = 0;
    private MapleQuickSlot quickslot;
    public boolean skillmacros_changed = false, inventoryslot_changed = false, skillcooldown_changed = false,
            savedlocation_changed = false, keyvalue_changed = false, isCatching = false, isCatched = false,
            isExitBuff = true, EnergyCharge = false, 에너지차지 = false;
    public int petAutoHP, petAutoMP, SurPlus = 0, PPoint = 0, Bullet = 0, Cylinder = 3, Cycount = 0, monsterCombo = 0,
            ELEMENTAL_CHARGE = 0, ELEMENTAL_CHARGE_ID = 0, InhumanSpeedCount = 0, Recharge = 0, RechargeFill = 0, FlameDischarge = 0, FlameBall = 0,
            CristalCharge;
    private long lastViewTime, monsterComboTime;
    private byte blessOfDarkness;
    public boolean isFinalFiguration = false, isTrade = false;
    public int overLoad, flipTheCoin = 0, combination = 0, beath = 0, attackcount = 0, acaneAim = 0, elementalAdep = -1,
            exeedCount = 0, exeedSkill = 0, exeedAttackCount = 0, dualBrid = 0, unitiyOfPower = 0, lightning = 0, 에너지차지구분 = 0, blackJack = 0;
    public int KillingPoint;
    private int hitcountbat, batcount;
    public final Integer effectssy = 0;
    public Equip MemorialE = null;
    public Equip MemorialEalpha = null;

    private ScheduledFuture<?> pendantOfSpirit = null, MesoChairTimer = null;
    public boolean quiver = false;
    public int quivermode = 0;
    public int[] quivercount = {10, 10, 10};

    private ScheduledFuture<?> LastTouchedRune = null;
    private int TouchedRune, LastTouchedRuneTime = 0;
    private int willfirst = 0, willfirst2 = 0;

    private int StarPer[] = {0, 0, 0};
    private List<Pair<EnchantEquipStats, Integer>> stata = new ArrayList<Pair<EnchantEquipStats, Integer>>();

    private int gp;
    private int Soul;
    private byte pendantExp = 0;
    private boolean prmiumpc = false;
    public java.util.Timer fishings = null;
    public int fishingfirst = 0;
    public long time = 0;
    public short arancombo = 2;
    private String chatban;
    private String chairtext;
    private long reincarnationTime = -1;
    private int reincarnationCount = 30;
    private int reincarnationMobCount = 30;
    private long lastrcdmg = -1;
    private transient CalcDamage calcDamage;
    private int slimeVirusCount = 10;
    public int SpiritGuard = 3;
    public int luminusskill[] = new int[2];
    public int luminusskillLen[] = new int[2];
    public int deathCount = 1, DeathCount_ = 0, savetime = 0;
    private int BULLET_SKILL_ID;
    private Map<Integer, Pair<Integer, Integer>> link_skill = new HashMap<Integer, Pair<Integer, Integer>>();
    public byte soulEffect = 1;
    private int WarpRand = -1;
    private long DamageMeter_ = -1;
    private int loginpoint = 0;
    private long logintimer = -1;
    private long eaTime = 0;
    private int totalCP;
    private int availableCP;
    public int selMannequineSlot = 0;
    private int starterquest, starterquestid;
    public byte modifiyInvCount = 8;
    public int zeroScrollPosition = -1;
    public int royalGuard = 0;
    public byte pih = 0;
    private int coreq = 0;
    public int bossDeathCount = -1;
    public long BattleUserRespawnUI = -1;
    private int bodyOfSteel = 0;
    public long lastArrowRain;
    private List<IItem> symbols = new ArrayList<>();
    public Map<Integer, Integer> shieldChaching = new HashMap<>();
    public String lastNpcTalk = "";
    public int KADENA_STACK = 0, KADENA_WEAPON_STACK = 0;
    public Point FlameHayzePositoin = null;

    public long premiumtime = 0;
    public boolean alreadynotice = false;
    public int Infinitycount = 0;
    private int infinityStack = 0;
    private long Infinitytime = System.currentTimeMillis();
    public int saveCrystalskill = 1, saveCrystalskill1 = 1, saveCrystalskill2 = 1, saveCrystalskill3 = 1;

    public byte mCount = 0;
    private long dojoStartTime = 0, dojoStopTime = 0, dojoCoolTime = 0;

    private MapleCharacter(final boolean ChannelServer) {
        setStance(0);
        setPosition(new Point(0, 0));
        inventory = new MapleInventory[MapleInventoryType.values().length];
        for (MapleInventoryType type : MapleInventoryType.values()) {
            inventory[type.ordinal()] = new MapleInventory(type);
        }
        quests = new LinkedHashMap<MapleQuest, MapleQuestStatus>();
        stats = new PlayerStats();
        profession = new MapleProfession(this);
        innerSkills = new LinkedList<>();
        cores = new ArrayList<>();
        aswanShopList = null;
        for (int i = 0; i < remainingSp.length; i++) {
            remainingSp[i] = 0;
        }
        if (ChannelServer) {
            lastCombo = 0;
            combo = 0;
            keydown_skill = 0;
            messengerposition = 4;
            canDoor = true;
            smega = true;
            hasSummon = false;
            pqStartTime = 0;
            isExitBuff = true;
            wishlist = new int[12];
            rocks = new HashMap<Integer, List<Integer>>();
            inst = new AtomicInteger();
            inst.set(0);
            keylayout = new MapleKeyLayout();
            doors = new ArrayList<MapleDoor>();
            mechdoors = new ArrayList<MapleMechDoor>();
            diseases = new LinkedHashMap<DiseaseStats, DiseaseValueHolder>(5);
            controlled = new LinkedHashSet<MapleMonster>();
            summons = new LinkedHashMap<Integer, Pair<Integer, MapleSummon>>();
            visibleMapObjects = new LinkedHashSet<MapleMapObject>();
            visibleMapObjectsLock = new ReentrantReadWriteLock();
            skilleffects = null;
            savedLocations = new int[SavedLocationType.values().length];
            for (int i = 0; i < SavedLocationType.values().length; i++) {
                savedLocations[i] = -1;
            }
            questinfo = new LinkedHashMap<Integer, String>();
            cardStack = 0;
            cardStackRunningId = 0;
            android = null;
            extendedSlots = new ArrayList<Integer>();
            followid = 0;
            followinitiator = false;
            followon = false;
        }
    }

    public static MapleCharacter getDefault(final MapleClient client) {
        MapleCharacter ret = new MapleCharacter(false);
        ret.client = client;
        ret.map = null;
        ret.exp = 0;
        ret.gmLevel = 0;
        ret.job = 0;
        ret.meso = 0;
        ret.level = 1;
        ret.remainingAp = 0;
        ret.fame = 0;
        ret.accountid = client.getAccID();
        ret.buddylist = new BuddyList(50);
        ret.dailyGift = new DailyGift();
        ret.stats.str = 12;
        ret.stats.dex = 5;
        ret.stats.int_ = 4;
        ret.stats.luk = 4;
        ret.stats.maxhp = 50;
        ret.stats.hp = 50;
        ret.stats.maxmp = 5;
        ret.stats.mp = 5;
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT name, nxCash, mPoints, vpoints, realcash FROM accounts WHERE id = ?");
            ps.setInt(1, ret.accountid);
            rs = ps.executeQuery();
            if (rs.next()) {
                ret.client.setAccountName(rs.getString("name"));
                ret.nxcash = rs.getInt("nxCash");
                ret.maplepoints = rs.getInt("mPoints");
                ret.vpoints = rs.getInt("vpoints");
                ret.realcash = rs.getInt("realcash");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error getting character default" + e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
        return ret;
    }

    public final static MapleCharacter ReconstructChr(final ChracterTransfer ct, final MapleClient client,
            final boolean isChannel) {
        MapleCharacter ret = new MapleCharacter(true);
        ret.client = client;
        if (!isChannel) {
            ret.client.setChannel(ct.channel);
        }
        ret.id = ct.characterid;
        ret.name = ct.name;
        ret.level = ct.level;
        ret.fame = ct.fame;

        ret.CRand = new PlayerRandomStream();

        ret.stats.setStr(ct.str);
        ret.stats.setDex(ct.dex);
        ret.stats.setInt(ct.int_);
        ret.stats.setLuk(ct.luk);
        ret.stats.setMaxHp(ct.maxhp);
        ret.stats.setMaxMp(ct.maxmp);
        ret.stats.hp = (ct.hp);
        ret.stats.mp = (ct.mp);

        ret.exp = ct.exp;
        ret.hpApUsed = ct.hpApUsed;
        ret.mpApUsed = ct.mpApUsed;
        ret.remainingSp = (int[]) ct.remainingSp;
        ret.remainingAp = ct.remainingAp;
        ret.meso = ct.meso;
        ret.gmLevel = ct.gmLevel;
        ret.skinColor = ct.skinColor;
        ret.secondSkinColor = ct.secondSkinColor;
        ret.gender = ct.gender;
        ret.secondGender = ct.secondGender;
        ret.job = ct.job;
        ret.hair = ct.hair;
        ret.hair2 = ct.hair2;
        ret.face = ct.face;
        ret.face2 = ct.face2;
        ret.wp = ct.wp;
        ret.askguildid = ct.askguildid;
        ret.accountid = ct.accountid;
        ret.mapid = ct.mapid;
        ret.damage = ct.damage;
        ret.damagehit = ct.damagehit;
        ret.damagehit2 = ct.damagehit2;
        ret.hope = ct.hope;
        ret.initialSpawnPoint = ct.initialSpawnPoint;
        ret.rank = ct.rank;
        ret.rankMove = ct.rankMove;
        ret.worldRank = ct.worldRank;
        ret.worldRankdMove = ct.worldRankMove;
        ret.tierReborns = ct.tierReborns;
        ret.reborns = ct.reborns;
        ret.guildid = ct.guildid;
        ret.guildrank = ct.guildrank;
        ret.allianceRank = ct.alliancerank;
        ret.subcategory = ct.subcategory;
        ret.exeedCount = ct.exeedCount;
        ret.rankpoint = ct.rankpoint;
        ret.gp = ct.gp;
        ret.Soul = ct.Soul;
        ret.chatban = ct.chatban;
        ret.betaclothes = ct.betaclothes;
        ret.extendedSlots = (List<Integer>) ct.extendedSlots;
        ret.loginpoint = ct.loginpoint;
        ret.logintimer = ct.logintimer;
        if (ret.guildid > 0) {
            ret.mgc = new MapleGuildCharacter(ret);
        }
        ret.buddylist = new BuddyList(ct.buddysize);
        ret.dailyGift = new DailyGift();
        ret.cashInv = (MapleCashInventory) ct.cashinventory;
        ret.quickslot = (MapleQuickSlot) ct.quickslot;
        ret.CustomValues = ct.CustomValues;
        ret.CustomValues2 = ct.CustomValues2;
        ret.CustomValues3 = ct.CustomValues3;
        ret.steelskills = (PhantomStealSkill) ct.steelskills;
        ret.rebuyList = (ArrayList<IItem>) ct.rebuyList;
        ret.profession = (MapleProfession) ct.profession;
        ret.stats = (PlayerStats) ct.stats;
        ret.hidden = ct.hidden;
        ret.innerExp = ct.innerexp;
        ret.innerLevel = ct.innerlevel;
        ret.isFinalFiguration = ct.isFinalFiguration;
        ret.innerSkills = (LinkedList<InnerSkillValueHolder>) ct.innerSkills;
        ret.cores = (ArrayList<VCore>) ct.cores;
        ret.coreq = ct.coreq;
        ret.aswanShopList = (MapleShop) ct.aswanShopList;
        ret.lastChannelChange = System.currentTimeMillis();
        if (isChannel) {
            final MapleWorldMapProvider mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
            ret.map = mapFactory.getMap(ret.mapid);
            if (ret.map == null) {
                ret.map = mapFactory.getMap(101050000);
            } else if (ret.map.getForcedReturnId() != 999999999) {
                ret.map = ret.map.getForcedReturnMap();
            }
            MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
            if (portal == null) {
                portal = ret.map.getPortal(0);
                ret.initialSpawnPoint = 0;
            }
            ret.setPosition(portal.getPosition());

            int partyid = ct.partyid;
            if (partyid >= 0) {
                MapleParty party = WorldCommunity.getParty(partyid);
                if (party != null && party.getMemberById(ret.id) != null) {
                    ret.party = party;
                }
            }

            final int messengerid = ct.messengerid;
            final int position = ct.messengerposition;
            if (messengerid > 0 && position < 4 && position > -1) {
                MapleMultiChat messenger = WorldCommunity.getMessenger(messengerid);
                if (messenger != null) {
                    ret.messenger = messenger;
                    ret.messengerposition = position;
                }
            }
        } else {
            int partyid = ct.partyid;
            if (partyid >= 0) {
                MapleParty party = WorldCommunity.getParty(partyid);
                if (party != null && party.getMemberById(ret.id) != null) {
                    ret.party = party;
                }
            }

            ret.messenger = null;
            ret.messengerposition = ct.messengerposition;
        }

        MapleQuestStatus queststatus;
        MapleQuestStatus queststatus_from;
        MapleQuest quest;
        for (final Map.Entry<Integer, Object> qs : ct.Quest.entrySet()) {
            quest = MapleQuest.getInstance(qs.getKey());
            queststatus_from = (MapleQuestStatus) qs.getValue();

            queststatus = new MapleQuestStatus(quest, queststatus_from.getStatus());
            queststatus.setForfeited(queststatus_from.getForfeited());
            queststatus.setCustomData(queststatus_from.getCustomData());
            queststatus.setCompletionTime(queststatus_from.getCompletionTime());

            if (queststatus_from.getMobKills() != null) {
                for (final Map.Entry<Integer, Integer> mobkills : queststatus_from.getMobKills().entrySet()) {
                    queststatus.setMobKills(mobkills.getKey(), mobkills.getValue());
                }
            }
            ret.quests.put(quest, queststatus);
        }
        for (final Map.Entry<Integer, Object> qs : ct.Skills.entrySet()) {
            ret.skills.put(SkillFactory.getSkill(qs.getKey()), (SkillEntry) qs.getValue());
        }

        ret.inventory = (MapleInventory[]) ct.inventorys;
        ret.BlessOfFairy_Origin = ct.BlessOfFairy;
        ret.BlessOfEmpress_Origin = ct.BlessOfEmpress;
        ret.skillMacros = (SkillMacro[]) ct.skillmacro;
        ret.keylayout = (MapleKeyLayout) ct.keymap;
        ret.questinfo = (Map<Integer, String>) ct.InfoQuest;
        ret.savedLocations = (int[]) ct.savedlocation;
        ret.wishlist = (int[]) ct.wishlist;
        ret.rocks = (Map<Integer, List<Integer>>) ct.rocks;
        ret.burning = ct.burning;

        ret.buddylist.loadFromTransfer(ct.buddies);
        ret.dailyGift = new DailyGift();
        ret.keydown_skill = 0;
        ret.lastfametime = ct.lastfametime;
        ret.lastmonthfameids = (List<Integer>) ct.famedcharacters;
        ret.storage = (MapleStorage) ct.storage;
        client.setAccountName(ct.accountname);
        ret.nxcash = ct.nxcash;
        ret.vpoints = ct.vpoints;
        ret.realcash = ct.realcash;
        ret.maplepoints = ct.MaplePoints;
        ret.headtitle = ct.headtitle;
        ret.cardStack = ct.cardStack;
        ret.android = (MapleAndroid) ct.android;
        ret.pets = ct.pets;
        ret.petAutoHP = ct.petAutoHP;
        ret.petAutoMP = ct.petAutoMP;
        ret.petLoot = ct.petLoot;
        ret.calcDamage = ct.cd;
        ret.mount = new MapleMount(ret, ct.mount_itemid, 80001000, ct.mount_Fatigue, ct.mount_level, ct.mount_exp);
        ret.silentEnforceMaxHpMp();
        return ret;
    }

    public void SaveSLFCG() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement(
                    "UPDATE characters SET frozenmobcount = ?, mesochair = ?, TowerChairSetting = ? WHERE id = ?",
                    MYSQL.RETURN_GENERATED_KEYS);
            ps.setInt(1, FrozenMobCount);
            ps.setInt(2, MesoChairCount);
            ps.setString(3, TowerChairSetting);
            ps.setInt(4, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static MapleCharacter loadCharFromDB(int charid, MapleClient client, boolean channelserver) {
        MapleCharacter ret = new MapleCharacter(channelserver);
        ret.client = client;
        ret.id = charid;
        ret.calcDamage = new CalcDamage();
        ret.calcDamage.SetSeed(Randomizer.nextInt(), Randomizer.nextInt(), Randomizer.nextInt());
        long t = System.currentTimeMillis();

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, charid);
            rs = ps.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("Loading the Char Failed (char not found)");
            }
            ret.name = rs.getString("name");
            ret.level = rs.getShort("level");
            ret.fame = rs.getInt("fame");
            ret.FrozenMobCount = rs.getInt("frozenmobcount");
            ret.MesoChairCount = rs.getInt("mesochair");
            ret.TowerChairSetting = rs.getString("TowerChairSetting");
            ret.stats = new PlayerStats();
            ret.profession = new MapleProfession(ret);

            if (channelserver) {
                ret.stats.setStr(rs.getInt("str"));
                ret.stats.setDex(rs.getInt("dex"));
                ret.stats.setInt(rs.getInt("int"));
                ret.stats.setLuk(rs.getInt("luk"));
                ret.stats.setMaxHp(rs.getInt("maxhp"));
                ret.stats.setMaxMp(rs.getInt("maxmp"));
                ret.stats.hp = rs.getInt("hp");
                ret.stats.mp = rs.getInt("mp");
            } else {
                ret.stats.str = rs.getInt("str");
                ret.stats.dex = rs.getInt("dex");
                ret.stats.int_ = rs.getInt("int");
                ret.stats.luk = rs.getInt("luk");
                ret.stats.maxhp = rs.getInt("maxhp");
                ret.stats.maxmp = rs.getInt("maxmp");
                ret.stats.hp = rs.getInt("hp");
                ret.stats.mp = rs.getInt("mp");
            }

            ret.exp = rs.getLong("exp");
            ret.hpApUsed = rs.getInt("hpApUsed");
            ret.mpApUsed = rs.getInt("mpApUsed");

            final String[] sp = rs.getString("sp").split(",");
            for (int i = 0; i < ret.remainingSp.length; i++) {
                ret.remainingSp[i] = Integer.parseInt(sp[i]);
            }

            ret.remainingAp = rs.getInt("ap");
            ret.subcategory = rs.getInt("subcategory");
            ret.meso = rs.getLong("meso");
            ret.gmLevel = rs.getByte("gm");
            ret.skinColor = rs.getByte("skincolor");
            ret.secondSkinColor = rs.getByte("skincolor2");
            ret.gender = rs.getByte("gender");
            ret.secondGender = rs.getByte("gender2");
            ret.job = rs.getShort("job");
            ret.hair = rs.getInt("hair");
            ret.hair2 = rs.getInt("hair2");
            ret.face = rs.getInt("face");
            ret.face2 = rs.getInt("face2");
            ret.wp = rs.getInt("wp");
            ret.askguildid = rs.getInt("askguildid");
            ret.accountid = rs.getInt("accountid");
            ret.mapid = rs.getInt("map");
            ret.initialSpawnPoint = rs.getInt("spawnpoint");
            ret.rank = rs.getInt("rank");
            ret.rankMove = rs.getInt("rankMove");
            ret.worldRank = rs.getInt("worldRank");
            ret.worldRankdMove = rs.getInt("worldRankMove");
            ret.guildid = rs.getInt("guildid");
            ret.guildrank = rs.getInt("guildrank");
            ret.allianceRank = rs.getInt("allianceRank");
            ret.gp = rs.getInt("gp");
            ret.Soul = rs.getInt("Soul");
            ret.chatban = rs.getString("chatban");
            ret.betaclothes = rs.getInt("betaclothes");
            ret.burning = rs.getByte("burning");
            ret.tierReborns = rs.getByte("tierReborns");
            ret.reborns = rs.getInt("reborns");
            ret.damagehit = rs.getInt("damagehit");
            ret.damagehit2 = rs.getInt("damagehit2");
            ret.damage = rs.getLong("damage");
            ret.hope = rs.getString("hope");
            ret.profession.setFirstProfession(MapleProfessionType.getProfessionById(rs.getInt("firstProfession")));
            ret.profession.setSecondProfession(MapleProfessionType.getProfessionById(rs.getInt("secondProfession")));
            ret.profession.setFirstProfessionLevel(rs.getInt("firstProfessionLevel"));
            ret.profession.setSecondProfessionLevel(rs.getInt("secondProfessionLevel"));
            ret.profession.setFirstProfessionExp(rs.getInt("firstProfessionExp"));
            ret.profession.setSecondProfessionExp(rs.getInt("secondProfessionExp"));
            ret.profession.setFatigue(rs.getInt("fatigue"));
            ret.lastViewTime = rs.getLong("last_command_time");
            ret.getStat().setAmbition(rs.getInt("ambition"));
            ret.getStat().setCharm(rs.getInt("charm"));
            ret.getStat().setDiligence(rs.getInt("diligence"));
            ret.getStat().setEmpathy(rs.getInt("empathy"));
            ret.getStat().setInsight(rs.getInt("insight"));
            ret.getStat().setWillPower(rs.getInt("willpower"));
            if (ret.guildid > 0 && client != null) {
                ret.mgc = new MapleGuildCharacter(ret);
            }
            ret.buddylist = new BuddyList(rs.getInt("buddyCapacity"));
            ret.dailyGift = new DailyGift();
            ret.innerExp = rs.getInt("innerExp");
            ret.innerLevel = rs.getInt("innerLevel");
            ret.artifactPoints = rs.getInt("artifactPoints");
            ret.morphGage = rs.getInt("morphGage");
            ret.petLoot = rs.getInt("pet_loot") == 1;
            ret.loginpoint = rs.getInt("loginpoint");
            ret.coreq = rs.getInt("coreq");
            if (ServerConstants.isLocal) {
                System.out.println("base info : " + (System.currentTimeMillis() - t) + "ms");
                t = System.currentTimeMillis();
            }
            if (channelserver) {
                MapleWorldMapProvider mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
                if (ret.mapid != 410000002) {
                    if (ret.isGM()) {
                        ret.mapid = 100000000;
                        ret.map = mapFactory.getMap(100000000);
                    } else {
                        ret.mapid = 100000000;
                        ret.map = mapFactory.getMap(100000000);
                    }
                } else {
                    ret.mapid = 410000002;
                    ret.map = mapFactory.getMap(410000002);
                }
                if (ret.map == null) {
                    ret.map = mapFactory.getMap(101050000);
                }
                MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
                if (portal == null) {
                    portal = ret.map.getPortal(0);
                    ret.initialSpawnPoint = 0;
                }
                if (ServerConstants.isLocal) {
                    System.out.println("map info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ret.setPosition(portal.getPosition());

                int partyid = rs.getInt("party");
                if (partyid >= 0) {
                    MapleParty party = WorldCommunity.getParty(partyid);
                    if (party != null && party.getMemberById(ret.id) != null) {
                        ret.party = party;
                    }
                }
                final int messengerid = rs.getInt("messengerid");
                final int position = rs.getInt("messengerposition");
                if (messengerid > 0 && position < 4 && position > -1) {
                    MapleMultiChat messenger = WorldCommunity.getMessenger(messengerid);
                    if (messenger != null) {
                        ret.messenger = messenger;
                        ret.messengerposition = position;
                    }
                }
                if (ServerConstants.isLocal) {
                    System.out.println("etc info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                String[] pets = rs.getString("pet_id").split(",");
                ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE uniqueid = ?");
                for (int next = 0; next < 3; ++next) {
                    if (!pets[next].equals("-1")) {
                        int petid = Integer.parseInt(pets[next]);
                        ps.setInt(1, petid);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            MaplePet pet = MaplePet.loadFromDb(rs.getInt("itemid"), petid, rs.getShort("position"));
                            ret.addPetBySlotId(pet, (byte) next);
                        }
                        rs.close();
                    }
                }
                ps.close();
            }
            rs.close();
            ps.close();

            if (ServerConstants.isLocal) {
                System.out.println("pet info : " + (System.currentTimeMillis() - t) + "ms");
                t = System.currentTimeMillis();
            }
            ret.loadKeyValues();
            if (ServerConstants.isLocal) {
                System.out.println("keyvalues info : " + (System.currentTimeMillis() - t) + "ms");
                t = System.currentTimeMillis();
            }
            if (channelserver) {
                ps = con.prepareStatement("SELECT * FROM queststatus WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                pse = con.prepareStatement("SELECT * FROM queststatusmobs WHERE queststatusid = ?");
                if (ServerConstants.isLocal) {
                    System.out.println("quest query time : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                while (rs.next()) {
                    final int id = rs.getInt("quest");
                    final MapleQuest q = MapleQuest.getInstance(id);
                    final MapleQuestStatus status = new MapleQuestStatus(q, rs.getByte("status"));
                    final long cTime = rs.getLong("time");
                    if (cTime > -1) {
                        status.setCompletionTime(cTime * 1000);
                    }
                    status.setForfeited(rs.getInt("forfeited"));
                    status.setCustomData(rs.getString("customData"));
                    ret.quests.put(q, status);
                    pse.setInt(1, rs.getInt("queststatusid"));
                    final ResultSet rsMobs = pse.executeQuery();

                    while (rsMobs.next()) {
                        status.setMobKills(rsMobs.getInt("mob"), rsMobs.getInt("count"));
                    }
                    rsMobs.close();
                }
                rs.close();
                ps.close();
                pse.close();

                if (ServerConstants.isLocal) {
                    System.out.println("quest info parsing : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                if (ret.getKeyValue("HeadTitle") == null) {
                    ret.setKeyValue("HeadTitle", "0");
                }
                ret.headtitle = Integer.parseInt(ret.getKeyValue("HeadTitle"));
                ret.CRand = new PlayerRandomStream();

                ps = con.prepareStatement("SELECT * FROM inventoryslot where characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    throw new RuntimeException("No Inventory slot column found in SQL. [inventoryslot]");
                } else {
                    ret.getInventory(MapleInventoryType.EQUIP).setSlotLimit(rs.getByte("equip"));
                    ret.getInventory(MapleInventoryType.USE).setSlotLimit(rs.getByte("use"));
                    ret.getInventory(MapleInventoryType.SETUP).setSlotLimit(rs.getByte("setup"));
                    ret.getInventory(MapleInventoryType.ETC).setSlotLimit(rs.getByte("etc"));
                    ret.getInventory(MapleInventoryType.CASH).setSlotLimit(rs.getByte("cash"));
                }
                ps.close();
                rs.close();

                if (ServerConstants.isLocal) {
                    System.out.println("etc2 info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }

                while (true) {
                    //    System.out.println("[Notice] 캐릭터 인벤토리리 불러오는중");
                    try {
                        for (MapleInventory inv : ret.getInventorys()) {
                            inv.list().clear();
                        }
                        Map<Integer, Pair<IItem, MapleInventoryType>> list2 = ItemFactory.INVENTORY.loadItems(con, false, charid);
                        int dSize = 0;
                        for (Pair<IItem, MapleInventoryType> mit : list2.values()) {
                            ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                            dSize++;
                        }
                        if (ret.rankpoint <= dSize) {
                            //    System.out.println("[Notice] Completed loading character inventory");
                            break;
                        } else {
                            System.out.println("[Notice] Failed to load character inventory " + ret.rankpoint + " : " + dSize);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }

                if (ServerConstants.isLocal) {
                    System.out.println("inventory info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ret.cashInv = new MapleCashInventory(ret.accountid);
                ret.cashInv.loadFromDB();

                if (ServerConstants.isLocal) {
                    System.out.println("csinv info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                rs.close();
                ps.close();

                ret.quickslot = new MapleQuickSlot(ret.id);
                ret.quickslot.loadFromDB();

                ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                ps.setInt(1, ret.accountid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ret.getClient().setAccountName(rs.getString("name"));
                    ret.nxcash = rs.getInt("nxCash");
                    ret.vpoints = rs.getInt("vpoints");
                    ret.maplepoints = rs.getInt("mPoints");
                    ret.realcash = rs.getInt("realcash");
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("etc3 info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement("SELECT * FROM questinfo WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();

                while (rs.next()) {
                    ret.questinfo.put(rs.getInt("quest"), rs.getString("data"));
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("questinfo info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement(
                        "SELECT skillid, skilllevel, masterlevel, expiration FROM skills WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int sid = rs.getInt("skillid");
                    if (sid == 80001044 || sid == 80001027 || sid == 80001028 || sid == 80001137 || sid == 80001144) {
                        continue;
                    }
                    ret.skills.put(SkillFactory.getSkill(rs.getInt("skillid")), new SkillEntry(rs.getByte("skilllevel"),
                            rs.getByte("masterlevel"), rs.getLong("expiration")));
                }
                rs.close();
                ps.close();
                ps = con.prepareStatement("SELECT SkillID,StartTime,length FROM skills_cooldowns WHERE charid = ?");
                ps.setInt(1, ret.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getLong("length") + rs.getLong("StartTime") - System.currentTimeMillis() <= 0) {
                        continue;
                    }
                    ret.addCooldown(rs.getInt("SkillID"), rs.getLong("StartTime"), rs.getLong("length"));
                }
                ps.close();
                rs.close();
                if (ServerConstants.isLocal) {
                    System.out.println("skill info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }

                ps = con.prepareStatement(
                        "SELECT crcid, coreid, level, exp, state, skill1, skill2, skill3 FROM core WHERE charid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.cores.add(new VCore(rs.getLong("crcid"), rs.getInt("coreid"), charid, rs.getInt("level"),
                            rs.getInt("exp"), rs.getInt("state"), rs.getInt("skill1"), rs.getInt("skill2"),
                            rs.getInt("skill3")));
                }
                rs.close();
                ps.close();

                ps = con.prepareStatement(
                        "SELECT skill_id, skill_level, max_level, rank FROM inner_ability_skills WHERE player_id = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.innerSkills.add(new InnerSkillValueHolder(rs.getInt("skill_id"),
                            (byte) rs.getInt("skill_level"), (byte) rs.getInt("max_level"), (byte) rs.getInt("rank")));
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("inner info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ret.retrieveLinkBless();

                if (ServerConstants.isLocal) {
                    System.out.println("linkbless info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement("SELECT * FROM skillmacros WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                int position;
                while (rs.next()) {
                    position = rs.getInt("position");
                    SkillMacro macro = new SkillMacro(rs.getInt("skill1"), rs.getInt("skill2"), rs.getInt("skill3"),
                            rs.getString("name"), rs.getInt("shout"), position);
                    ret.skillMacros[position] = macro;
                }

                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("skillmacro info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement("SELECT `key`,`type`,`action` FROM keymap WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();

                final Map<Integer, MapleKeyBinding> keyb = ret.keylayout.Layout();
                while (rs.next()) {
                    keyb.put(Integer.valueOf(rs.getInt("key")),
                            new MapleKeyBinding(rs.getInt("type"), rs.getInt("action")));
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("keymap info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement("SELECT `locationtype`,`map` FROM savedlocations WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.savedLocations[rs.getInt("locationtype")] = rs.getInt("map");
                }
                rs.close();
                ps.close();

                ps = con.prepareStatement(
                        "SELECT `characterid_to`,`when` FROM famelog WHERE characterid = ? AND DATEDIFF(NOW(),`when`) < 30");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                ret.lastfametime = 0;
                ret.lastmonthfameids = new ArrayList<Integer>(31);
                while (rs.next()) {
                    ret.lastfametime = Math.max(ret.lastfametime, rs.getTimestamp("when").getTime());
                    ret.lastmonthfameids.add(Integer.valueOf(rs.getInt("characterid_to")));
                }
                rs.close();
                ps.close();

                ret.buddylist.loadFromDb(charid);
                ret.dailyGift.loadDailyGift(client.getAccID(client.getAccountName()));
                ret.storage = MapleStorage.loadStorage(ret.accountid);
                ret.loadSteelSkills();

                ps = con.prepareStatement("SELECT sn FROM wishlist WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    ret.wishlist[i] = rs.getInt("sn");
                    i++;
                }
                while (i < 12) {
                    ret.wishlist[i] = 0;
                    i++;
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("etc4 info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement(
                        "SELECT `uniqueid` FROM extendedslots WHERE characterid = ? ORDER by `index` ASC");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.extendedSlots.add(Integer.valueOf(rs.getInt("uniqueid")));
                }
                rs.close();
                ps.close();

                if (ServerConstants.isLocal) {
                    System.out.println("bags info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }
                ps = con.prepareStatement("SELECT mapid, type FROM trocklocations WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ret.addTrockMap(rs.getInt("type"), rs.getInt("mapid"));
                }
                rs.close();
                ps.close();

                ps = con.prepareStatement("SELECT * FROM mountdata WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException("No mount data found on SQL column");
                }
                final IItem mount = ret.getInventory(MapleInventoryType.EQUIPPED).getItem((short) -22);
                ret.mount = new MapleMount(ret, mount != null ? mount.getItemId() : 0, 1004, rs.getInt("Fatigue"),
                        rs.getInt("Level"), rs.getInt("Exp"));
                ps.close();
                rs.close();
                loadItemPot(charid);

                if (ServerConstants.isLocal) {
                    System.out.println("etc5 info : " + (System.currentTimeMillis() - t) + "ms");
                    t = System.currentTimeMillis();
                }

            } else {
                Map<Integer, Pair<IItem, MapleInventoryType>> list2 = ItemFactory.INVENTORY.loadItems(con, true,
                        charid);
                for (Pair<IItem, MapleInventoryType> mit : list2.values()) {
                    ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                }
            }
        } catch (SQLException ess) {
            if (!ServerConstants.realese) {
                ess.printStackTrace();
            }
            System.err.println("Character loading failed.");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignore) {

            }
        }
        if (ServerConstants.isLocal) {
            System.out.println("QUERY Searched Time : " + (System.currentTimeMillis() - t) + "ms");
        }
        t = System.currentTimeMillis();
        if (channelserver) {
            if (ret.stats.getHp() > ret.stats.getCurrentMaxHp()) {
                ret.stats.setHp(ret.stats.getCurrentMaxHp(), ret);
            }
            if (ret.stats.getMp() > ret.stats.getCurrentMaxMp()) {
                ret.stats.setMp(ret.stats.getCurrentMaxMp());
            }
            ret.stats.recalcLocalStats(ret);
            ret.silentEnforceMaxHpMp();
        }

        if (ret.getClient() != null) {
        }
        if (ServerConstants.isLocal) {
            System.out.println("all char loaded time : " + (System.currentTimeMillis() - t) + "ms");
        }
        return ret;
    }

    public static void saveNewCharToDB(final MapleCharacter chr) {
        checkFirstItem(chr);
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            con.setAutoCommit(false);
            ps = con.prepareStatement(
                    "INSERT INTO characters (level, fame, str, dex, luk, `int`, exp, hp, mp, maxhp, maxmp, sp, ap, gm, skincolor, skincolor2, gender, gender2, job, hair, hair2, face, face2, wp, askguildid, map, meso, hpApUsed, mpApUsed, spawnpoint, party, buddyCapacity, messengerid, messengerposition, monsterbookcover, accountid, name, reborns, subcategory, rankpoint, gp, Soul, chatban, betaclothes, tierReborns) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    MYSQL.RETURN_GENERATED_KEYS);
            final PlayerStats stat = chr.stats;
            int level = 1;
            if (GameConstants.isZero(chr.getJob())) {
                level = 100;
                stat.setMaxHp(7457);
                stat.setMaxMp(100);
                stat.setStr(518);
                stat.setCurrentMaxHp(7457);
                stat.setCurrentMaxMp(100);
            }
            ps.setInt(1, level); // Level
            ps.setInt(2, chr.fame); // Fame
            ps.setInt(3, stat.getStr()); // Str
            ps.setInt(4, stat.getDex()); // Dex
            ps.setInt(5, stat.getInt()); // Int
            ps.setInt(6, stat.getLuk()); // Luk
            ps.setInt(7, 0); // EXP
            ps.setLong(8, stat.getHp()); // HP
            ps.setInt(9, stat.getMp()); // MP
            ps.setInt(10, stat.getMaxHp());
            ps.setInt(11, stat.getMaxMp());
            ps.setString(12, "0,0,0,0,0,0,0,0,0,0"); // Remaining SP
            ps.setInt(13, 0); // Remaining AP
            ps.setInt(14, 0); // GM Level
            ps.setByte(15, chr.skinColor);
            ps.setByte(16, chr.secondSkinColor);
            ps.setByte(17, chr.gender);
            ps.setByte(18, chr.secondGender);
            ps.setInt(19, chr.job);
            ps.setInt(20, chr.hair);
            ps.setInt(21, chr.hair2);
            ps.setInt(22, chr.face);
            ps.setInt(23, chr.face2);
            ps.setInt(24, chr.wp);
            ps.setInt(25, chr.askguildid);
            ps.setInt(26, JobType.getById(chr.job).map);
            ps.setLong(27, chr.meso); // Meso
            ps.setInt(28, 0); // HP ap used
            ps.setInt(29, 0); // MP ap used
            ps.setInt(30, 0); // Spawnpoint
            ps.setInt(31, -1); // Party
            ps.setInt(32, chr.buddylist.getCapacity()); // Buddylist
            ps.setInt(33, 0); // MessengerId
            ps.setInt(34, 4); // Messenger Position
            ps.setInt(35, 0); // Monster book cover
            ps.setInt(36, chr.getAccountID());
            ps.setString(37, chr.name);
            ps.setInt(38, chr.reborns);
            ps.setInt(39, chr.subcategory); // for now
            ps.setInt(40, chr.rankpoint);
            ps.setInt(41, chr.gp);
            ps.setInt(42, chr.Soul);
            ps.setString(43, "false");
            ps.setInt(44, 0); // BetaClothes
            ps.setInt(45, chr.tierReborns);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                chr.id = rs.getInt(1);
            } else {
                throw new MYSQLException("Inserting char failed.");
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement(
                    "INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)",
                    MYSQL.RETURN_GENERATED_KEYS);
            pse = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (final MapleQuestStatus q : chr.quests.values()) {
                ps.setInt(2, q.getQuest().getId());
                ps.setInt(3, q.getStatus());
                ps.setInt(4, (int) (q.getCompletionTime() / 1000));
                ps.setInt(5, q.getForfeited());
                ps.setString(6, q.getCustomData());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();

                if (q.hasMobKills()) {
                    for (int mob : q.getMobKills().keySet()) {
                        pse.setInt(1, rs.getInt(1));
                        pse.setInt(2, mob);
                        pse.setInt(3, q.getMobKills(mob));
                        pse.executeUpdate();
                    }
                }
                rs.close();
            }
            ps.close();
            pse.close();

            ps = con.prepareStatement(
                    "INSERT INTO inventoryslot (characterid, `equip`, `use`, `setup`, `etc`, `cash`) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setInt(2, 96); // Eq
            ps.setInt(3, 96); // Use
            ps.setInt(4, 96); // Setup
            ps.setInt(5, 96); // ETC
            ps.setInt(6, 60); // Cash
            ps.execute();
            ps.close();

            ps = con.prepareStatement(
                    "INSERT INTO mountdata (characterid, `Level`, `Exp`, `Fatigue`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setInt(2, 1);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.execute();
            ps.close();

            List<Pair<IItem, MapleInventoryType>> listing = new ArrayList<>();
            for (final MapleInventory iv : chr.inventory) {
                for (final IItem item : iv.list()) {
                    listing.add(new Pair<>(item, iv.getType()));
                }
            }
            ItemFactory.INVENTORY.saveItems(listing, con, chr.id);

            ps = con.prepareStatement(
                    "INSERT INTO mountdata (characterid, `Level`, `Exp`, `Fatigue`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setInt(2, 1);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.execute();
            ps.close();

            final int[] array1 = {2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 31, 33, 34, 35,
                37, 38, 39, 40, 41, 43, 44, 45, 46, 47, 48, 50, 51, 56, 57, 59, 60, 61, 62, 63, 64, 65, 83};
            final int[] array2 = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                4, 5, 5, 4, 4, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 0};
            final int[] array3 = {10, 12, 13, 18, 23, 28, 8, 5, 0, 4, 27, 30, 32, 1, 24, 19, 14, 15, 52, 2, 25, 17, 11,
                3, 20, 26, 16, 22, 9, 50, 51, 6, 31, 29, 7, 33, 53, 54, 100, 101, 102, 103, 104, 105, 106, 52};

            ps = con.prepareStatement("INSERT INTO keymap (characterid, `key`, `type`, `action`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (int i = 0; i < array1.length; i++) {
                ps.setInt(2, array1[i]);
                ps.setInt(3, array2[i]);
                ps.setInt(4, array3[i]);
                ps.execute();
            }
            ps.setInt(2, 1);
            ps.setInt(3, 4);
            ps.setInt(4, 46);
            ps.execute();
            ps.close();

            chr.deleteWhereCharacterId(con, "DELETE FROM skills WHERE characterid = ?");
            ps = con.prepareStatement(
                    "INSERT INTO skills (characterid, skillid, skilllevel, masterlevel, expiration) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (final Entry<ISkill, SkillEntry> skill : chr.skills.entrySet()) {
                ps.setInt(2, skill.getKey().getId());
                ps.setInt(3, skill.getValue().skillevel);
                ps.setInt(4, skill.getValue().masterlevel);
                ps.setLong(5, skill.getValue().expiration);
                ps.executeUpdate();
            }
            ps.close();

            chr.saveKeyValues();

            con.commit();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            System.err.println("[charsave] Error saving character data");
            try {
                con.rollback();
            } catch (SQLException ex) {
                if (!ServerConstants.realese) {
                    ex.printStackTrace();
                }
                System.err.println("[charsave] Error Rolling Back");
            }
        } finally {
            try {
                if (pse != null) {
                    pse.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
                System.err.println("[charsave] Error going back to autocommit mode");
            }
        }
    }

    public void CharacterSaveToDB(boolean fromcs) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
                    ps = con.prepareStatement(
                    "UPDATE characters SET level = ?, fame = ?, str = ?, dex = ?, luk = ?, `int` = ?, exp = ?, hp = ?, mp = ?, maxhp = ?, maxmp = ?, sp = ?, ap = ?, gm = ?, skincolor = ?, skincolor2 = ?, gender = ?, gender2 = ?, job = ?, hair = ?, hair2 = ?, face = ?, face2 = ?, wp = ?, askguildid = ?, map = ?, meso = ?, hpApUsed = ?, mpApUsed = ?, spawnpoint = ?, party = ?, buddyCapacity = ?, messengerid = ?, messengerposition = ?, reborns = ?, subcategory = ?, rankpoint = ?, gp = ?, Soul = ?, damage = ?, damagehit = ?, damagehit2 = ?, hope = ?, ambition = ?, insight = ?, willpower = ?, diligence = ?, empathy = ?, charm = ?, innerExp = ?, innerLevel = ?, artifactPoints = ?, morphGage = ?, firstProfession = ?, secondProfession = ?, firstProfessionLevel = ?, secondProfessionLevel = ?, firstProfessionExp = ?, secondProfessionExp = ?, fatigue = ?, last_command_time = ?, pet_id = ?, pet_loot = ?, pet_hp = ?, pet_mp = ?, chatban = ?, betaclothes = ?, burning = ?, loginpoint = ?, coreq = ?, tierReborns = ? WHERE id = ?",
                    MYSQL.RETURN_GENERATED_KEYS);
                

            int index = 0;
            ps.setInt(++index, level);
            ps.setInt(++index, fame);
            ps.setInt(++index, stats.getStr());
            ps.setInt(++index, stats.getDex());
            ps.setInt(++index, stats.getLuk());
            ps.setInt(++index, stats.getInt());
            ps.setLong(++index, exp);
            ps.setLong(++index, stats.getHp() < 1 ? 50 : stats.getHp());
            ps.setInt(++index, stats.getMp());
            ps.setInt(++index, stats.getMaxHp());
            ps.setInt(++index, stats.getMaxMp());

            final StringBuilder sps = new StringBuilder();
            for (int i = 0; i < remainingSp.length; i++) {
                sps.append(remainingSp[i]);
                sps.append(",");
            }

            String sp = sps.toString();
            ps.setString(++index, sp.substring(0, sp.length() - 1));
            ps.setInt(++index, remainingAp);
            ps.setByte(++index, gmLevel);
            ps.setByte(++index, skinColor);
            ps.setByte(++index, secondSkinColor);
            ps.setByte(++index, gender);
            ps.setInt(++index, secondGender);
            ps.setShort(++index, job);
            ps.setInt(++index, hair);
            ps.setInt(++index, hair2);
            ps.setInt(++index, face);
            ps.setInt(++index, face2);
            ps.setInt(++index, wp);
            ps.setInt(++index, askguildid);
            if (map != null) {
                if (map.getForcedReturnId() != 999999999) {
                    ps.setInt(++index, map.getForcedReturnId());
                } else {
                    ps.setInt(++index, stats.getHp() < 1 ? map.getReturnMapId() : map.getId());
                }
            } else {
                ps.setInt(++index, mapid);
            }
            ps.setLong(++index, meso);
            ps.setInt(++index, hpApUsed);
            ps.setInt(++index, mpApUsed);
            if (map == null) {
                ps.setInt(++index, 0);
            } else {
                final MaplePortal closest = map.findClosestSpawnpoint(getPosition());
                ps.setInt(++index, closest != null ? closest.getId() : 0);
            }
            ps.setInt(++index, party != null ? party.getId() : -1);
            ps.setInt(++index, buddylist.getCapacity());
            if (messenger != null) {
                ps.setInt(++index, messenger.getId());
                ps.setInt(++index, messengerposition);
            } else {
                ps.setInt(++index, 0);
                ps.setInt(++index, 4);
            }
            ps.setInt(++index, getReborns());
            ps.setInt(++index, subcategory);
            int itemC = 0;
            for (MapleInventory inv : this.inventory) {
                itemC += inv.list().size();
            }
            ps.setInt(++index, itemC);
            ps.setInt(++index, gp);
            ps.setInt(++index, Soul);
            ps.setLong(++index, damage);
            ps.setLong(++index, damagehit);
            ps.setInt(++index, damagehit2);
            ps.setString(++index, hope);
            ps.setInt(++index, getStat().getAmbition());
            ps.setInt(++index, getStat().getInsight());
            ps.setInt(++index, getStat().getWillPower());
            ps.setInt(++index, getStat().getDiligence());
            ps.setInt(++index, getStat().getEmpathy());
            ps.setInt(++index, getStat().getCharm());
            ps.setInt(++index, getInnerExp());
            ps.setInt(++index, getInnerLevel());
            ps.setInt(++index, getArtifactPoints());
            ps.setInt(++index, getMorphGage());
            ps.setInt(++index, profession.getFirstProfessionSkill());
            ps.setInt(++index, profession.getSecondProfessionSkill());
            ps.setInt(++index, profession.getFirstProfessionLevel());
            ps.setInt(++index, profession.getSecondProfessionLevel());
            ps.setInt(++index, profession.getFirstProfessionExp());
            ps.setInt(++index, profession.getSecondProfessionExp());
            ps.setInt(++index, profession.getFatigue());
            ps.setLong(++index, lastViewTime);
            sps.delete(0, sps.toString().length());
            for (int i = 0; i < 3; i++) {
                if (pets[i] != null) {
                    sps.append(pets[i].getUniqueId());
                } else {
                    sps.append("-1");
                }
                sps.append(",");
            }
            sp = sps.toString();

            ps.setString(++index, sp.substring(0, sp.length() - 1));
            ps.setBoolean(++index, petLoot);
            ps.setInt(++index, petAutoHP);
            ps.setInt(++index, petAutoMP);
            ps.setString(++index, chatban);
            ps.setInt(++index, GameConstants.isZero(getJob()) ? betaclothes : 0);
            ps.setInt(++index, burning);
            ps.setInt(++index, loginpoint);
            ps.setInt(++index, coreq);
            ps.setInt(++index, getTierReborns());
            ps.setInt(++index, id);

            if (ps.executeUpdate() < 1) {
                throw new MYSQLException("Character not in database (" + id + ")");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            /*if (!ServerConstants.realese) {
                
            }*/
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public long getPremiumTime() {
        return premiumtime;
    }

    public List<VCore> getCores() {
        return cores;
    }

    public long getPT() {
        long ret = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM ggpremium WHERE aid = ?");
            ps.setInt(1, this.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                premiumtime = rs.getLong("limit");
                ret = rs.getLong("limit");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
        return ret;
    }

    public boolean ExistPT() {
        boolean ret = false;
         Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM ggpremium WHERE aid = ?");
            ps.setInt(1, this.getAccountID());
            rs = ps.executeQuery();
            if (rs.next()) {
                ret = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
        return ret;
    }

    public void gainPT(long time) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("DELETE FROM ggpremium WHERE aid = ?");
            ps.setInt(1, this.getId());
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("INSERT INTO ggpremium(`aid`, `limit`) VALUES (?, ?)");
            ps.setInt(1, this.getId());
            ps.setLong(2, time);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void PetSaveToDB() {
        for (int i = 0; i < pets.length; ++i) {
            if (pets[i] != null) {
                pets[i].saveToDb();
            }
        }
    }

    public void MacroSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        if (skillmacros_changed) {
            try {
                con = MYSQL.getConnection();
                deleteWhereCharacterId(con, "DELETE FROM skillmacros WHERE characterid = ?");
                for (int i = 0; i < 5; i++) {
                    final SkillMacro macro = skillMacros[i];
                    if (macro != null) {
                        ps = con.prepareStatement(
                                "INSERT INTO skillmacros (characterid, skill1, skill2, skill3, name, shout, position) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setInt(2, macro.getSkill1());
                        ps.setInt(3, macro.getSkill2());
                        ps.setInt(4, macro.getSkill3());
                        ps.setString(5, macro.getName());
                        ps.setInt(6, macro.getShout());
                        ps.setInt(7, i);
                        ps.execute();
                    }
                }
                ps.close();
                con.close();
            } catch (SQLException e) {
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
            finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
        }
    }

    public void SlotSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            if (inventoryslot_changed) {
                deleteWhereCharacterId(con, "DELETE FROM inventoryslot WHERE characterid = ?");
                ps = con.prepareStatement(
                        "INSERT INTO inventoryslot (characterid, `equip`, `use`, `setup`, `etc`, `cash`) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setInt(1, id);
                ps.setInt(2, getInventory(MapleInventoryType.EQUIP).getSlotLimit());
                ps.setInt(3, getInventory(MapleInventoryType.USE).getSlotLimit());
                ps.setInt(4, getInventory(MapleInventoryType.SETUP).getSlotLimit());
                ps.setInt(5, getInventory(MapleInventoryType.ETC).getSlotLimit());
                ps.setInt(6, getInventory(MapleInventoryType.CASH).getSlotLimit());
                ps.execute();
                ps.close();
            }
            deleteWhereCharacterId(con, "DELETE FROM extendedslots WHERE `characterid` = ?");
            for (int i = 0; i < extendedSlots.size(); i++) {
                if (getInventory(MapleInventoryType.ETC).findByUniqueId(extendedSlots.get(i)) != null) { // just in case
                    ps = con.prepareStatement(
                            "INSERT INTO `extendedslots` (`index`, `characterid`, `uniqueid`) VALUES (?, ?, ?) ");
                    ps.setInt(1, i);
                    ps.setInt(2, getId());
                    ps.setInt(3, extendedSlots.get(i));
                    ps.executeUpdate();
                    ps.close();
                }
            }
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void QuestInfoSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            deleteWhereCharacterId(con, "DELETE FROM questinfo WHERE characterid = ?");
            ps = con.prepareStatement("INSERT INTO questinfo (`characterid`, `quest`, `data`) VALUES (?, ?, ?)");
            ps.setInt(1, id);
            for (final Entry<Integer, String> q : questinfo.entrySet()) {
                ps.setInt(2, q.getKey());
                ps.setString(3, q.getValue());
                ps.execute();
            }
            ps.close();

            deleteWhereCharacterId(con, "DELETE FROM queststatus WHERE characterid = ?");
            ps = con.prepareStatement(
                    "INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)",
                    MYSQL.RETURN_GENERATED_KEYS);
            pse = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
            ps.setInt(1, id);
            for (final MapleQuestStatus q : quests.values()) {
                ps.setInt(2, q.getQuest().getId());
                ps.setInt(3, q.getStatus());
                ps.setInt(4, (int) (q.getCompletionTime() / 1000));
                ps.setInt(5, q.getForfeited());
                ps.setString(6, q.getCustomData());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();

                if (q.hasMobKills()) {
                    for (int mob : q.getMobKills().keySet()) {
                        pse.setInt(1, rs.getInt(1));
                        pse.setInt(2, mob);
                        pse.setInt(3, q.getMobKills(mob));
                        pse.executeUpdate();
                    }
                }
                rs.close();
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (pse != null) {
                    pse.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void SkillSaveToDB(boolean dc) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            deleteWhereCharacterId(con, "DELETE FROM skills WHERE characterid = ?");
            ps = con.prepareStatement(
                    "INSERT INTO skills (characterid, skillid, skilllevel, masterlevel) VALUES (?, ?, ?, ?)");
            ps.setInt(1, id);
            for (final Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
                ps.setInt(2, skill.getKey().getId());
                ps.setInt(3, skill.getValue().skillevel);
                ps.setInt(4, skill.getValue().masterlevel);
                ps.execute();
            }
            ps.close();
            if (innerSkills != null) {
                deleteWhereCharacterId(con, "DELETE FROM inner_ability_skills WHERE player_id = ?");
                ps = con.prepareStatement(
                        "INSERT INTO inner_ability_skills (player_id, skill_id, skill_level, max_level, rank) VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, id);
                for (InnerSkillValueHolder inner : innerSkills) {
                    ps.setInt(2, inner.getSkillId());
                    ps.setInt(3, inner.getSkillLevel());
                    ps.setInt(4, inner.getMaxLevel());
                    ps.setInt(5, inner.getRank());
                    ps.executeUpdate();
                }
                ps.close();
            }
            deleteWhereCharacterId(con, "DELETE FROM skills_cooldowns WHERE charid = ?");
            if (getAllCooldowns().size() > 0) {
                for (final MapleCoolDownValueHolder cooling : getAllCooldowns()) {
                    ps = con.prepareStatement(
                            "INSERT INTO skills_cooldowns (charid, SkillID, length, StartTime) VALUES (?, ?, ?, ?)");
                    ps.setInt(1, getId());
                    ps.setInt(2, cooling.skillId);
                    ps.setLong(3, cooling.length);
                    ps.setLong(4, cooling.startTime);
                    ps.executeUpdate();
                    ps.close();
                }
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void BuddiesSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            deleteWhereCharacterId(con, "DELETE FROM buddies WHERE characterid = ? AND pending = 0");
            ps = con.prepareStatement(
                    "INSERT INTO buddies (characterid, `buddyid`, `pending`, `groupname`) VALUES (?, ?, 0, ?)");
            ps.setInt(1, id);
            for (BuddylistEntry entry : buddylist.getBuddies()) {
                if (entry.isVisible()) {
                    ps.setInt(2, entry.getCharacterId());
                    ps.setString(3, entry.getGroup());
                    ps.execute();
                }
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void CashSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement(
                    "UPDATE accounts SET `nxCash` = ?, `mPoints` = ?, `vpoints` = ?, `realcash` = ? WHERE id = ?");
            ps.setInt(1, nxcash);
            ps.setInt(2, maplepoints);
            ps.setInt(3, getVPoints());
            ps.setInt(4, getRC());
            ps.setInt(5, client.getAccID());
            ps.execute();
            ps.close();
            if (cashInv != null) {
                ps = con.prepareStatement("DELETE FROM inventoryitems WHERE accountid = ? AND type = ?");
                ps.setInt(1, accountid);
                ps.setInt(2, ItemFactory.CASHSHOP.getValue());
                ps.executeUpdate();
                ps.close();
                cashInv.saveToDB();
            } else {
                System.err.println("Cash Shop inventory failed to save due to null pointer.");
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void WishSaveToDB() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            deleteWhereCharacterId(con, "DELETE FROM wishlist WHERE characterid = ?");
            for (int i = 0; i < getWishlistSize(); i++) {
                ps = con.prepareStatement("INSERT INTO wishlist(characterid, sn) VALUES(?, ?) ");
                ps.setInt(1, getId());
                ps.setInt(2, wishlist[i]);
                ps.execute();
                ps.close();
            }
            deleteWhereCharacterId(con, "DELETE FROM trocklocations WHERE characterid = ?");
            for (Entry<Integer, List<Integer>> e : rocks.entrySet()) {
                for (Integer i : e.getValue()) {
                    if (i != 999999999) {
                        ps = con.prepareStatement(
                                "INSERT INTO trocklocations(characterid, mapid, type) VALUES(?, ?, ?) ");
                        ps.setInt(1, getId());
                        ps.setInt(2, i);
                        ps.setInt(3, e.getKey());
                        ps.execute();
                        ps.close();
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
            }
        }
    }

    public void saveToDB(boolean dc, boolean fromcs) {
        Connection con = null;
        ReentrantLock LockObj = new ReentrantLock();
        LockObj.lock();
        try {
            con = MYSQL.getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            con.setAutoCommit(false);

            /* Key layout, ride saving */
            try {
                deleteWhereCharacterId(con, "DELETE FROM inventoryitems WHERE characterid = ? AND issale = 0");
                keylayout.saveKeys(id);
                mount.saveMount(id);
            } catch (SQLException e) {
                if (!ServerConstants.realese) {
                    e.printStackTrace();
                }
            }

            /* Save character */
            CharacterSaveToDB(fromcs);

            /* Save Pet */
            PetSaveToDB();

            /* Save macro slot */
            MacroSaveToDB();
            
            /* Save items */
            saveInventory(con);
            
            /* Slot storage */
            SlotSaveToDB();

            /* Save quest info */
            QuestInfoSaveToDB();

            /* Save skill data */
            SkillSaveToDB(dc);

            /* Friend data storage */
            BuddiesSaveToDB();

            /* Cash inventory storage */
            CashSaveToDB();

            /* Save wish list */
            WishSaveToDB();


            

            /* Warehouse storage */
            if (storage != null) {
                storage.saveToDB();
            } else {
                System.err.println("Warehouse inventory failed to save because it encountered a null pointer.");
            }

            /* Steel skill storage */
            if (steelskills != null) {
                saveSteelSkills();
            } else {
                System.err.println("Save failed because steel skill information has a null pointer.");
            }

            /* VCORE Save */
            saveCores(con);

            /* Quick Slot Save */
            quickslot.saveToDB();

            /* Key value storage */
            if (keyvalue_changed) {
                setKeyValue("HeadTitle", headtitle + "");
                setKeyValue("lastLogout", Long.toString(System.currentTimeMillis()));
                saveKeyValues();
            }
            con.commit();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
            System.err.println(MapleClient.getLogMessage(this, "[charsave] Error saving character data."));
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println(MapleClient.getLogMessage(this, "[charsave] Error Rolling Back"));
                ex.printStackTrace();
            }
        } finally {
            try {
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                if (ServerConstants.realese) {
                    e.printStackTrace();
                }
            }
            LockObj.unlock();
        }
    }

    public boolean getbuff(int buffid) {
        String info = getQuestNAdd(MapleQuest.getInstance(buffid)).getCustomData();
        if (info == null || info.equals("")) {
            return false;
        }
        return true;
    }

    public void buybuff(int buffid) {
        getQuestNAdd(MapleQuest.getInstance(buffid)).setCustomData("buy");
        givebuff(buffid);
    }

    public void givebuff() {
        int[] id = {5321054, 2311003};
        for (int i = 0; i < id.length; i++) {
            givebuff(id[i]);
        }
    }

    public void givebuff(int buffid) {
        String info = getQuestNAdd(MapleQuest.getInstance(buffid)).getCustomData();
        if (info != null && info.contains("buy")) {
            ISkill skill = SkillFactory.getSkill(buffid);
            SkillStatEffect eff = skill.getEffect(skill.getMaxLevel());
            eff.applyBuffEffect(this);
        }
    }

    /*
    public void giveRcBuff(int skill) {
        ISkill S = SkillFactory.getSkill(skill);
        S.getEffect(S.getMaxLevel()).applyBuffEffect(this);
    }
     */
    public String getDateKey(String key, boolean a) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        return getKeyValue1(year + "" + month + "" + day + "_" + key);
    }

    public void setDateKey(String key, String value, boolean a) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        setKeyValue(year + "" + month + "" + day + "_" + key, value, true);
    }

    public int getHbPoint() {
        if (this.getKeyValue2("HongBoP") == -1 || this.getKeyValue2("HongBoP") == 0) {
            setKeyValue2("HongBoP", 0);
        }
        return this.getKeyValue2("HongBoP");
    }

    public void gainHbPoint(int a) {
        setKeyValue2("HongBoP", getHbPoint() + a);
    }

    public void doneWillMob() {
        willtimer1 = null;
        willtimer2 = null;
        willfirst = 0;
        willfirst2 = 0;
    }

    public void spawnWillMob1(long t, boolean istwo) {
        this.willtimer1 = tools.Timer.MapTimer.getInstance().register(new Runnable() {
            public void run() {
                if (willfirst != 0) {
                    if (!istwo) {
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(-465, 215));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880316), new Point(535, 215));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880316), new Point(215, 215));
                    } else {
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(-136, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(179, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(285, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(390, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880315), new Point(529, 281));
                    }
                } else {
                    willfirst++;
                }
            }
        }, t * 1000L);
    }

    public void spawnWillMob2(long t, boolean istwo) {
        this.willtimer2 = tools.Timer.MapTimer.getInstance().register(new Runnable() {
            public void run() {
                if (willfirst2 != 0) {
                    if (!istwo) {
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(-185, 215));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(6, 215));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(249, 215));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(474, 215));
                    } else {
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(246, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(157, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(-204, 281));
                        client.getChannelServer().getMapFactory().getMap(450008250)
                                .spawnMonsterOnGroundBelow(MapleLifeProvider.getMonster(8880306), new Point(-330, 281));
                    }
                } else {
                    willfirst2++;
                }
            }
        }, t * 1000L);
    }

    public void saveInventory(final Connection con) throws SQLException {
        List<Pair<IItem, MapleInventoryType>> listing = new ArrayList<>();
        for (final MapleInventory iv : inventory) {
            for (final IItem item : iv.list()) {
                listing.add(new Pair<>(item, iv.getType()));
            }
        }
        if (con != null) {
            ItemFactory.INVENTORY.saveItems(listing, con, id);
        } else {
            ItemFactory.INVENTORY.saveItems(listing, id);
        }
    }

    private void saveCores(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM core WHERE charid = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        for (VCore core : cores) {
            core.SaveCore(con);
        }
    }

    public void deleteWhereCharacterId(Connection con, String sql) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public final PlayerStats getStat() {
        return stats;
    }

    public int getMaxHp() {
        return getStat().getMaxHp();
    }

    public int getMaxMp() {
        return getStat().getMaxMp();
    }

    public void setHp(int amount) {
        getStat().setHp(amount, this);
    }

    public void setMp(int amount) {
        getStat().setMp(amount);
    }

    public void healHP(int delta) {
        addHP(delta);
    }

    public void healMP(int delta) {
        addMP(delta);
    }

    public final PlayerRandomStream CRand() {
        return CRand;
    }

    public final void QuestInfoPacket(final WritingPacket packet) {
        packet.writeShort(questinfo.size());
        for (final Entry<Integer, String> q : questinfo.entrySet()) {
            if (q.getKey() == 26544) {
                System.out.println();
            }
            packet.writeInt(q.getKey()); // 1.2.251+
            packet.writeMapleAsciiString(q.getValue() == null ? "" : q.getValue());
        }
    }

    public final void updateInfoQuest(final int questid, final String data) {
        questinfo.put(questid, data);
        client.getSession().writeAndFlush(MainPacketCreator.updateInfoQuest(questid, data));
    }

    public final String getInfoQuest(final int questid) {
        if (questinfo.containsKey(questid)) {
            return questinfo.get(questid);
        }
        return "";
    }

    public final int getNumQuest() {
        int i = 0;
        for (final MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 2 && !q.isCustomQuest()) {
                i++;
            }
        }
        return i;
    }

    public final byte getQuestStatus(final int quest) {
        for (final MapleQuestStatus q : quests.values()) {
            if (q.getQuest().getId() == quest) {
                return q.getStatus();
            }
        }
        return 0;
    }

    public final MapleQuestStatus getQuest(final MapleQuest quest) {
        if (!quests.containsKey(quest)) {
            return new MapleQuestStatus(quest, (byte) 0);
        }
        return quests.get(quest);
    }

    public final MapleQuestStatus getQuestNAdd(final MapleQuest quest) {
        if (!quests.containsKey(quest)) {
            MapleQuestStatus status = new MapleQuestStatus(quest, (byte) 0);
            quests.put(quest, status);
            return status;
        }
        return quests.get(quest);
    }

    public final void updateQuest(final MapleQuestStatus quest) {
        quests.put(quest.getQuest(), quest);
        if (!quest.isCustomQuest()) {
            if (quest.getStatus() == 1) {
                client.getSession().writeAndFlush(
                        MainPacketCreator.startQuest(this, (short) quest.getQuest().getId(), quest.getCustomData()));
            } else if (quest.getStatus() == 2) {
                client.getSession().writeAndFlush(MainPacketCreator.completeQuest((short) quest.getQuest().getId()));
            } else if (quest.getStatus() == 0) {
                client.getSession()
                        .writeAndFlush(MainPacketCreator.forfeitQuest(this, (short) quest.getQuest().getId()));
            }
        }
    }

    public final Map<Integer, String> getInfoQuest_Map() {
        return questinfo;
    }

    public final Map<MapleQuest, MapleQuestStatus> getQuest_Map() {
        return quests;
    }

    public long ARROW_RAIN = -1;

    public boolean isActiveBuffedValue(int skillid) {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        Map<BuffStats, List<BuffStatsValueHolder>> effectCopy = Collections.synchronizedMap(effects);
        for (List<BuffStatsValueHolder> holders : effectCopy.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }
        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skillid) {
                return true;
            }
        }
        return false;
    }

    public final Integer getBuffedSkill_X(final BuffStats effect) {
        return getBuffedSkill_X(effect, -1);
    }

    public final Integer getBuffedSkill_X(final BuffStats effect, int skillid) {
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        if (skillid == -1) {
            if (effects.get(effect) != null) {
                return effects.get(effect).get(0).effect.getX();
            }
        } else {
            for (BuffStatsValueHolder bsvh : mbsvh) {
                if (bsvh.effect.getSourceId() == skillid) {
                    return bsvh.effect.getX();
                }
            }
        }
        return null;
    }

    public final Integer getBuffedSkill_Y(final BuffStats effect) {
        return getBuffedSkill_Y(effect, -1);
    }

    public final Integer getBuffedSkill_Y(final BuffStats effect, int skillid) {
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        if (skillid == -1) {
            if (effects.get(effect) != null) {
                return effects.get(effect).get(0).effect.getY();
            }
        } else {
            for (BuffStatsValueHolder bsvh : mbsvh) {
                if (bsvh.effect.getSourceId() == skillid) {
                    return bsvh.effect.getY();
                }
            }
        }
        return null;
    }

    public Integer getBuffedValue(BuffStats effect) {
        return getBuffedValue(effect, -1);
    }

    public Integer getBuffedValue(BuffStats effect, int skillid) {
        if (!effects.containsKey(effect)) {
            return null;
        }
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        for (BuffStatsValueHolder bsvh : mbsvh) {
            if (bsvh.effect.getSourceId() == skillid || skillid == -1) {
                return Integer.valueOf(bsvh.value);
            }
        }
        return null;
    }

    public int getTrueBuffSource(final BuffStats effect) {
        if (!effects.containsKey(effect)) {
            return -1;
        }
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        for (BuffStatsValueHolder bsvh : mbsvh) {
            return bsvh.effect == null ? -1
                    : (bsvh.effect.isSkill() ? bsvh.effect.getSourceId() : -bsvh.effect.getSourceId());
        }
        return -1;
    }

    public final SkillStatEffect getStatForBuff(final BuffStats effect) {
        return getBuffedSkillEffect(effect);
    }

    public final SkillStatEffect getBuffedSkillEffect(final BuffStats effect) {
        return getBuffedSkillEffect(effect, -1);
    }

    public final SkillStatEffect getBuffedSkillEffect(final BuffStats effect, int skillid) {
        if (!effects.containsKey(effect)) {
            return null;
        }
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        for (BuffStatsValueHolder bsvh : mbsvh) {
            if (bsvh.effect.getSourceId() == skillid || skillid == -1) {
                return bsvh.effect;
            }
        }
        return null;
    }

    public int getItemQuantity(int itemid, boolean checkEquipped) {
        int possesed = inventory[GameConstants.getInventoryType(itemid).ordinal()].countById(itemid);
        if (checkEquipped) {
            possesed += inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        return possesed;
    }

    public int getTierReborns(){
        return tierReborns;
        
    }
    public int getReborns() {
        return reborns;
    }

    public int getVPoints() {
        return vpoints;
    }

    public int getMaxStats() {
        return Short.MAX_VALUE;
    }

    public int getNX() {
        return nxcash;
    }

    public void gainVPoints(int gainedpoints) {
        this.vpoints += gainedpoints;
    }

    public void gainItemAllStat(int itemid, short quantity, short allstat, short wmtk) {
        Equip equip = new Equip(itemid, quantity, (byte) 0);
        equip.setStr(allstat);
        equip.setDex(allstat);
        equip.setInt(allstat);
        equip.setLuk(allstat);
        if (wmtk != -1) {
            equip.setWatk(wmtk);
            equip.setMatk(wmtk);
        }
        InventoryManipulator.addFromDrop(client, equip, true);
    }

    public final void gainItem(final int id, final int quantity) {
        gainItem(id, (short) quantity, false, -1, null);
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period,
            String gm_log) {
        if (quantity >= 0) {
            final ItemInformation ii = ItemInformation.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!InventoryManipulator.checkSpace(client, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id)
                    && !GameConstants.isBullet(id)) {
                IItem item = randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id), true) : ii.getEquipById(id);
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + period);
                }
                item.setGMLog(CurrentTime.getAllCurrentTime() + "에 " + gm_log);
                InventoryManipulator.addbyItem(client, item);
            } else {
                InventoryManipulator.addById(client, id, quantity, "", null, period,
                        CurrentTime.getAllCurrentTime() + "에 " + getName() + "Items obtained by the gainItem script called on.");
            }
        } else {
            InventoryManipulator.removeById(client, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        client.getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, quantity, true));
    }

    public void setBuffedValue(BuffStats effect, int skillid, int value) {
        if (!effects.containsKey(effect)) {
            return;
        }
        if (skillid == -1) {
            if (effects.get(effect) != null) {
                try {
                    effects.get(effect).get(0).value = value;
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException ef) {
                    ef.printStackTrace();
                }
            }
        } else {
            for (BuffStatsValueHolder bsvhs : effects.get(effect)) {
                if (bsvhs.effect.getSourceId() == skillid) {
                    bsvhs.value = value;
                }
            }
        }
    }

    public Long getBuffedStarttime(BuffStats effect, int skillid) {
        if (!effects.containsKey(effect)) {
            return null;
        }
        final List<BuffStatsValueHolder> mbsvh = effects.get(effect);
        if (mbsvh.size() == 1 && skillid == -1) {
            return Long.valueOf(mbsvh.get(0).startTime);
        }
        for (BuffStatsValueHolder bsvh : mbsvh) {
            if (bsvh.effect.getSourceId() == skillid) {
                return bsvh.startTime;
            }
        }
        return null;
    }

    public void startMapTimeLimitTask(int time, final MapleMap to) {
        client.getSession().writeAndFlush(MainPacketCreator.getClock(time));

        time *= 1000;
        mapTimeLimitTask = EtcTimer.getInstance().register(new Runnable() {

            @Override
            public void run() {
                changeMap(to, to.getPortal(0));
            }
        }, time, time);
    }

    public void cancelMapTimeLimitTask() {
        if (mapTimeLimitTask != null) {
            mapTimeLimitTask.cancel(false);
        }
    }

    public void cancelFishingTask() {
        if (fishing != null) {
            fishing.cancel(false);
        }
    }

    public void registerEffect(SkillStatEffect effect, long starttime, ScheduledFuture<?> schedule) {
        if (effect.isHide()) {
            this.hidden = true;
            map.broadcastMessage(this, MainPacketCreator.removePlayerFromMap(getId()), false);
        }
        for (Triple<BuffStats, Integer, Boolean> statup : effect.getStatups()) {
            if (!effects.containsKey(statup.getFirst())) {
                effects.put(statup.getFirst(), new ArrayList<BuffStatsValueHolder>());
            }
            effects.get(statup.getFirst())
                    .add(new BuffStatsValueHolder(effect, starttime, schedule, statup.getSecond()));
        }
        stats.recalcLocalStats(this);
    }

    public void forceRegisterEffect(SkillStatEffect effect, List<Triple<BuffStats, Integer, Boolean>> statups,
            long starttime, ScheduledFuture<?> schedule) {
        for (Triple<BuffStats, Integer, Boolean> statup : statups) {
            if (!effects.containsKey(statup.getFirst())) {
                effects.put(statup.getFirst(), new ArrayList<BuffStatsValueHolder>());
            }
            effects.get(statup.getFirst())
                    .add(new BuffStatsValueHolder(effect, starttime, schedule, statup.getSecond()));
        }
        stats.recalcLocalStats(this);
    }

    public void checkInduerense() {
        if (getJob() == 510 || getJob() == 511 || getJob() == 512) {
            if (getSkillLevel(5100013) > 0) {
                if (InduerenseTask == null) {
                    final SkillStatEffect eff = SkillFactory.getSkill(5100013).getEffect(getSkillLevel(5100013));
                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            int recoverhp = (int) ((eff.getX() / 100.0D) * getStat().getCurrentMaxHp());
                            int recovermp = (int) ((eff.getX() / 100.0D) * getStat().getCurrentMaxMp());
                            if (isAlive()) {
                                addHP(recoverhp);
                                if (getStat().getHp() + recoverhp < getStat().getCurrentMaxHp()) {
                                    send(MainPacketCreator.showOwnRecoverHP(
                                            Math.min(getStat().getCurrentMaxHp() - getStat().getHp(), recoverhp)));
                                }
                                addMP(recovermp);
                            }
                        }
                    };
                    BuffTimer tMan = BuffTimer.getInstance();
                    InduerenseTask = tMan.register(r, eff.getY() * 1000);
                }
            }
        }
    }

    public void setNullSelfRecovery() {
        selfRecoveryTask = null;
    }

    public void checkSelfRecovery() {
        if (getSkillLevel(GameConstants.getRecoverySkill(getJob() / 10)) > 0) {
            if (selfRecoveryTask == null) {
                final SkillStatEffect eff = SkillFactory.getSkill(GameConstants.getRecoverySkill(getJob() / 10))
                        .getEffect(getSkillLevel(GameConstants.getRecoverySkill(getJob() / 10)));
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        int recoverhp = eff.getSkillStats().getStats("hp");
                        int recovermp = eff.getSkillStats().getStats("mp");
                        if (GameConstants.getRecoverySkill(getJob() / 10) == 61110006) {
                            recoverhp = getStat().getCurrentMaxHp() * (eff.getSkillStats().getStats("x") / 100);
                            recovermp = getStat().getCurrentMaxMp() * (eff.getSkillStats().getStats("x") / 100);
                        }
                        if (isAlive()) {
                            addHP(recoverhp);
                            addMP(recovermp);
                        }
                    }
                };
                BuffTimer tMan = BuffTimer.getInstance();
                selfRecoveryTask = tMan.register(r, 4000);
            }
        }
    }

    public void checkMercedesRecovery() {
        if (GameConstants.isMercedes(getJob())) {
            if (getSkillLevel(20020109) > 0) {
                if (mercedesRecoveryTask == null) {
                    final SkillStatEffect eff = SkillFactory.getSkill(20020109).getEffect(getSkillLevel(20020109));
                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            int recoverhp = (int) ((eff.getX() / 100.0D) * getStat().getCurrentMaxHp());
                            int recovermp = (int) ((eff.getX() / 100.0D) * getStat().getCurrentMaxMp());
                            if (isAlive()) {
                                addHP(recoverhp);
                                if (getStat().getHp() + recoverhp < getStat().getCurrentMaxHp()) {
                                    send(MainPacketCreator.showOwnRecoverHP(
                                            Math.min(getStat().getCurrentMaxHp() - getStat().getHp(), recoverhp)));
                                }
                                addMP(recovermp);
                            }
                        }
                    };
                    BuffTimer tMan = BuffTimer.getInstance();
                    mercedesRecoveryTask = tMan.register(r, 4000);
                }
            }
        }
    }

    public final void startInfinityRegen(final SkillStatEffect eff) {
        BuffTimer tMan = BuffTimer.getInstance();
        infinityReGenTask = tMan.register(new Runnable() {

            @Override
            public void run() {
                final int regenHP = (int) (getStat().getCurrentMaxHp() * (eff.getY() / 100.0D));
                final int regenMP = (int) (getStat().getCurrentMaxMp() * (eff.getY() / 100.0D));
                addMPHP(regenHP, regenMP);
                if (getStat().getCurrentMaxHp() - regenHP > 0) {
                    send(MainPacketCreator.showOwnRecoverHP(Math.min(getStat().getCurrentMaxHp() - regenHP, regenHP)));
                }

                // Final damage increased by # damage%
                if (getInfinityStack() < 20) {
                    setInfinityStack(getInfinityStack() + 1);
                    send(MainPacketCreator.givePMDR(getInfinityStack() * eff.getDamage(), 5000));
                }
            }
        }, 4000);
    }

    public final void startSurPlus() {
        if (SurPlusTask == null) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (isAlive()) {
                        giveSurPlus(1);
                    }
                }
            };
            BuffTimer tMan = BuffTimer.getInstance();
            SurPlusTask = tMan.register(r, 4000);
        }
    }

    public final void startDiabolicRecovery(final SkillStatEffect eff) {
        BuffTimer tMan = BuffTimer.getInstance();
        final int regenHP = (int) (getStat().getCurrentMaxHp() * (eff.getX() / 100.0D));
        if (diabolicRecoveryTask != null) {
            diabolicRecoveryTask.cancel(true);
            diabolicRecoveryTask = null;
        }
        Runnable r = new Runnable() {

            @Override
            public void run() {
                if (!isActiveBuffedValue(31211004)) {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
                MapleCharacter.this.addHP(regenHP);
                if (getStat().getCurrentMaxHp() - regenHP > 0) {
                    send(MainPacketCreator.showOwnRecoverHP(Math.min(getStat().getCurrentMaxHp() - regenHP, regenHP)));
                }
            }
        };
        diabolicRecoveryTask = tMan.register(r, eff.getW() * 1000);
        tMan.schedule(new Runnable() {

            @Override
            public void run() {
                if (diabolicRecoveryTask != null) {
                    diabolicRecoveryTask.cancel(true);
                    diabolicRecoveryTask = null;
                }
            }
        }, eff.getDuration());
    }

    public List<BuffStats> getBuffStats(final SkillStatEffect effect, final long startTime) {
        final List<BuffStats> bstats = new ArrayList<BuffStats>();
        for (final Entry<BuffStats, List<BuffStatsValueHolder>> stateffect : effects.entrySet()) {
            final List<BuffStatsValueHolder> mbsvh = stateffect.getValue();
            for (final BuffStatsValueHolder bsvh : mbsvh) {
                if (bsvh.effect.sameSource(effect) && (startTime == -1 || startTime == bsvh.startTime)) {
                    bstats.add(stateffect.getKey());
                }
            }
        }
        return bstats;
    }

    public void cancelEffect(final SkillStatEffect effect, final boolean overwrite, final long startTime) {
        try {
            List<BuffStats> buffstats;
            final List<BuffStatsValueHolder> effectsToCancel;
            final MaplePlayerHolder hph = getClient().getChannelServer().getPlayerStorage();
            if (!overwrite) {
                if (effect.getSourceId() == 1320019) {
                    SkillStatEffect eff = getBuffedSkillEffect(BuffStats.Reincarnation, 1320019);
                    if (eff == null) {
                        return;
                    }
                    if (eff.getZ() > 0) {
                        checkReincarnationBuff(false);
                        return;
                    } else if (!skillisCooling(1320019)) {
                        giveCoolDowns(1320019, startTime, eff.getCooldown());
                        if (this.reincarnationMobCount > 0) {
                            addHP(-getStat().getCurrentMaxHp());
                        }
                    }
                }
            }
            isExitBuff = false;
            if (!overwrite) {
                buffstats = getBuffStats(effect, startTime);
            } else {
                final List<Triple<BuffStats, Integer, Boolean>> statups = effect.getStatups();
                buffstats = new ArrayList<BuffStats>(statups.size());
                for (final Triple<BuffStats, Integer, Boolean> statup : statups) {
                    buffstats.add(statup.getFirst());
                }
            }
            if (effect.getSourceId() == 400021032 || effect.getSourceId() == 400021033) {
                cancelEffect(effect, false, -1);
            } else if ((effect.getSourceId() == 15001022 || effect.getSourceId() == 27121005) && !overwrite) {
                buffstats.add(BuffStats.IgnoreTargetDEF);
                acaneAim = 0;
            } else if (effect.getSourceId() == 51001005 && !overwrite) {
                royalGuard = 0;
            } else if (effect.getSourceId() == 400011066) {
                bodyOfSteel = 0;
            } else if (effect.getSourceId() == 400041032 && !overwrite) {
                readyToDie = 0;
            } else if (effect.getSourceId() == 400051002 && !overwrite) {
                transformEnergyOrb = 0;
            } else if (effect.getSourceId() == 4221013 && !overwrite) {
                KillingPoint = 0;
                send(MainPacketCreator.cancelKillingPoint());
            } else if (effect.getSourceId() == 1200014 && !overwrite) {
                if (getSkillLevel(400011052) > 0 && getBuffedValue(BuffStats.BlessedHammer, 400011052) != null) {
                    elementalChargeHandler(-5);
                    cancelEffectFromBuffStat(BuffStats.BlessedHammer, 400011052);
                    cancelEffectFromBuffStat(BuffStats.BlessedHammerBig, 400011053);
                }
                ELEMENTAL_CHARGE = 0;
                ELEMENTAL_CHARGE_ID = 0;
            } else if (effect.isInfinity()) {
                setInfinityStack(0);
                send(MainPacketCreator.givePMDR(0, 0));
                if (infinityReGenTask != null) {
                    infinityReGenTask.cancel(true);
                    infinityReGenTask = null;
                }
            } else if (effect.getSourceId() == 400041007) {
                buffstats.add(BuffStats.MegaSmasher);
            } else if (effect.getSourceId() == 400051015) {
                buffstats.add(BuffStats.DevilishPower);
            } else if ((effect.getSourceId() == 152000007 || effect.getSourceId() == 152110009 || effect.getSourceId() == 152120012) && !overwrite) {
                BLESS_MARK = 0;
            } else if ((effect.getSourceId() == 64100004 || effect.getSourceId() == 64120006
                    || effect.getSourceId() == 64110005) && !overwrite) {
                KADENA_STACK = 0;
            } else if (effect.getSourceId() == 3111005 || effect.getSourceId() == 3211005 || effect.getSourceId() == 3311009) { //패파 3311009 레이븐
                if (getBuffedValue(BuffStats.ReflectDamR) != null) {
                    cancelEffectFromBuffStat(BuffStats.ReflectDamR, 400001012);
                }
            } else if (effect.getSourceId() == 400001012 && !overwrite) {
                for (Entry<Integer, Pair<Integer, MapleSummon>> summon : getSummons().entrySet()) {
                    if (summon.getValue().right.getSkill() == 400001012) {
                        summon.getValue().right.updateSummon(getMap(), true);
                        summon.getValue().right.setSkill(summon.getValue().right.BEFORE_SKILL,
                                summon.getValue().right.BEFORE_LEVEL);
                        summon.getValue().right.updateSummon(getMap(), false);
                        break;
                    }
                }
            } else if (effect.getSourceId() == 21110016 || effect.getSourceId() == 21121058) {
                if (getCombo() > 500) {
                    setCombo((short) (getCombo() - 500));
                }
                updateCombo(getCombo(), System.currentTimeMillis());
            } else if (effect.getSourceId() == 4211003 && !overwrite) {
                this.mesoCount = 0;
            } else if ((effect.getSourceId() == 152110008 || effect.getSourceId() == 152120014) && !overwrite) {
                this.setCristalCharge(0);
                this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, this.getCrystalOid(), 2), true);
                this.resetEnableCristalSkill(this.getCrystalOid());
            }

            if (!overwrite && buffstats == null) {
                isExitBuff = true;
                return;
            }
            effectsToCancel = new ArrayList<BuffStatsValueHolder>(buffstats.size());
            for (final BuffStats stat : buffstats) {
                if (stackedEffects.containsKey(stat)) {
                    final List<StackedSkillEntry> sses = stackedEffects.get(stat);
                    int i = 0;
                    boolean delete = false;
                    for (final StackedSkillEntry sse : sses) {
                        if (sse.getSkillId() == (effect.isSkill() ? effect.getSourceId() : -effect.getSourceId())) {
                            delete = true;
                            break;
                        }
                        ++i;
                    }
                    if (delete) {
                        sses.remove(i);
                        if (sses.isEmpty()) {
                            stackedEffects.remove(stat);
                        }
                    }
                }
                if (effects.containsKey(stat)) {
                    final List<BuffStatsValueHolder> mbsvh = effects.get(stat);
                    if (mbsvh.size() > 0) {
                        int i = 0;
                        boolean delete = false;
                        for (final BuffStatsValueHolder bsvh : mbsvh) {
                            if (bsvh.effect.getSourceId() == effect.getSourceId()) {
                                boolean addMbsvh = true;
                                for (final BuffStatsValueHolder contained : effectsToCancel) {
                                    if (bsvh.startTime == contained.startTime && contained.effect == bsvh.effect) {
                                        addMbsvh = false;
                                    }
                                }
                                if (addMbsvh) {
                                    effectsToCancel.add(bsvh);
                                }

                                if (effect.getSourceId() == 152121005 && !overwrite) {
                                    if (summons.size() > 0) {
                                        List<MapleSummon> summons = new ArrayList<>();
                                        getSummons().values().stream().filter(sum -> sum.left == 152121006).forEach(s -> summons.add(s.right));
                                        summons.forEach(sum -> {
                                            removeSummon(sum.getObjectId());
                                            map.broadcastMessage(MainPacketCreator.removeSummon(sum, true));
                                            map.removeMapObject(sum);
                                            removeVisibleMapObject(sum);
                                        });
                                    }
                                    SkillStatEffect a = SkillFactory.getSkill(152001003).getEffect(getSkillLevel(152001003));
                                    SkillStatEffect a1 = SkillFactory.getSkill(152101008).getEffect(getSkillLevel(152101008));
                                    a.applyTo(this);
                                    a1.applyTo(this);
                                }

                                final int summonId = bsvh.effect.getSourceId();
                                if (!overwrite) {
                                    for (Pair<Integer, MapleSummon> summon : new ArrayList<>(summons.values())) {
                                        if (summon.getRight().getSkill() == summonId) {
                                            summon.getRight().removeSummon(map);
                                        }
                                    }
                                }
                                if (summonId == 14001027) { // Shadow Bat
                                    int skillids[] = {14000027, 14100027, 14110029, 14120008};
                                    for (int s : skillids) {
                                        for (Pair<Integer, MapleSummon> summon : new ArrayList<>(summons.values())) {
                                            if (summon.left == s) {
                                                summon.getRight().removeSummon(map);
                                            }
                                        }
                                    }
                                   
                                }
                                if (summonId == 400051038) {
                                    int skillids[] = {400051038, 400051052, 400051053};
                                    for (int s : skillids) {
                                        for (Pair<Integer, MapleSummon> summon : new ArrayList<>(summons.values())) {
                                            if (summon.left == s) {
                                                summon.getRight().removeSummon(map);
                                            }
                                        }
                                    }
                                }
                                delete = true;
                                break;
                            }
                            ++i;
                        }
                        if (delete) {
                            mbsvh.remove(i);
                            if (mbsvh.isEmpty()) {
                                effects.remove(stat);
                            }
                        }
                    }
                }
            }
            isExitBuff = true;
            for (final BuffStatsValueHolder cancelEffectCancelTasks : effectsToCancel) {
                if (cancelEffectCancelTasks.schedule != null) {
                    cancelEffectCancelTasks.schedule.cancel(false);
                }
            }
            getStat().recalcLocalStats(this);
            enforceMaxHpMp();
            if (effect.isMagicDoor()) {
                if (!getDoors().isEmpty()) {
                    final MapleDoor door = getDoors().iterator().next();
                    for (final MapleCharacter chr : door.getTarget().getCharacters()) {
                        door.sendDestroyData(chr.getClient());
                    }
                    for (final MapleCharacter chr : door.getTown().getCharacters()) {
                        door.sendDestroyData(chr.getClient());
                    }
                    for (final MapleDoor destroyDoor : getDoors()) {
                        door.getTarget().removeMapObject(destroyDoor);
                        door.getTown().removeMapObject(destroyDoor);
                    }
                    clearDoors();
                    silentPartyUpdate();
                }
            } else if (effect.isMonsterRiding()) {
                setKeyValue2("mountid", 0);
                setKeyValue2("mountsourceid", 0);
            }
            if (!overwrite) {
                cancelPlayerBuffs(buffstats, effect.getSourceId());
            }

            if (effect.getSourceId() == 152111003 && !overwrite) {
                SkillStatEffect a = SkillFactory.getSkill(152101000).getEffect(getSkillLevel(152101000));
                a.applyTo(this);
                SkillStatEffect a1 = SkillFactory.getSkill(152101008).getEffect(getSkillLevel(152101008)); // Machina
                a1.applyTo(this);
            }

            if (effect.getSourceId() == 400051033 && skillisCooling(400051033)
                    && effect.getStat("cooltime") * 1000 - getCooldownLimit(
                    400051033) > (effect.getStat("cooltime") * 1000 - effect.getStat("time") - 10000)) {
                IEquip eqpWeapon = (IEquip) getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);

                if (eqpWeapon != null) {
                    setBuffedValue(BuffStats.IndiePAD, 400051033,
                            (int) -(eqpWeapon.getWatk() * (double) effect.getY() / 100));
                    setBuffedValue(BuffStats.OverDrive, 400051033, -1);

                    List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                    statups.add(new Triple<>(BuffStats.IndiePAD,
                            (int) -(eqpWeapon.getWatk() * (double) effect.getY() / 100), true));
                    statups.add(new Triple<>(BuffStats.OverDrive, -1, false));

                    long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
                    int localDuration = effect.getStat("cooltime") * 1000 - effect.getStat("time");
                    Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
                    for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                        if (statup.getThird()) {
                            if (!stacked.containsKey(statup.getFirst())) {
                                stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                            }
                            stacked.get(statup.getFirst()).add(
                                    new StackedSkillEntry(400051033, statup.getSecond(), overlap_magic, localDuration));
                        }
                    }

                    send(MainPacketCreator.giveBuff(400051033, localDuration, statups, effect, stacked,
                            SkillFactory.getSkill(400051033).getAnimationTime(), this));
                    final long starttime = System.currentTimeMillis();
                    forceRegisterEffect(effect, statups, starttime,
                            tools.Timer.BuffTimer.getInstance().schedule(
                                    new SkillStatEffect.CancelEffectAction(this, effect, starttime),
                                    ((starttime + localDuration) - System.currentTimeMillis())));
                    getMap().broadcastMessage(this, MainPacketCreator.giveForeignBuff(this, statups), false);
                }
            }
        } catch (Exception ex) {
            if (!ServerConstants.realese) {
                ex.printStackTrace();
            }
        }
        if (effect != null && effect.getSourceId() != 2221011 && effect.getSourceId() != 2220010) {
        }
    }

    public void cancelBuffStats(int skill, BuffStats... statt) {
        final List<BuffStats> buffStatList = Arrays.asList(statt);
        for (BuffStats stats : buffStatList) {
            cancelEffectFromBuffStat(stats, skill);
        }
    }

    public void cancelEffectFromBuffStat(BuffStats stat, int skill) {
        final List<SkillStatEffect> toCancelEffects = new ArrayList<SkillStatEffect>();
        if (effects.containsKey(stat)) {
            for (BuffStatsValueHolder bsvh : effects.get(stat)) {
                if (bsvh.effect != null) {
                    if (bsvh.effect.getSourceId() == skill || skill == -1) {
                        toCancelEffects.add(bsvh.effect);
                    }
                }
            }
        }
        for (SkillStatEffect effect : toCancelEffects) {
            cancelEffect(effect, false, -1);
        }
    }

    private void cancelPlayerBuffs(List<BuffStats> buffstats, int skillid) {
        if (skillid == 61111008 || skillid == 61120008 || skillid == 61121053) {
            isFinalFiguration = false;
            changeKaiserTransformKey();
        }
        if (skillid == 1105) {
            return;
        }
        client.getSession().writeAndFlush(MainPacketCreator.cancelBuff(buffstats, buffstats.contains(BuffStats.MonsterRiding), false, Collections.unmodifiableMap(stackedEffects)));
        map.broadcastMessage(this, MainPacketCreator.cancelForeignBuff(getId(), skillid, buffstats), false);

        if (skillid == 4221054) {
            flipTheCoin = 0;
        }
        if (skillid == 37110009 || skillid == 37120012) {
            combination = 0;
        }
        if ((buffstats.contains(BuffStats.MonsterRiding) || buffstats.contains(BuffStats.RideVehicleExpire)) && GameConstants.isEvan(job) && job >= 2200) {
            makeDragon();
            map.spawnDragon(dragon);
        }
        stats.recalcLocalStats(this);
        enforceMaxHpMp();
    }

    public void dispel() {
        if (!isHidden()) {
            LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
            for (final List<BuffStatsValueHolder> holders : effects.values()) {
                for (final BuffStatsValueHolder bsvh : holders) {
                    allBuffs.add(bsvh);
                }
            }

            for (final BuffStatsValueHolder mbsvh : allBuffs) {
                if (mbsvh.effect.isSkill() && mbsvh.schedule != null && !mbsvh.effect.isMorph()) {
                    cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                }
            }
        }
    }

    public void dispelSkill(int skillid) {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }

        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (skillid == 0) {
                if (mbsvh.effect.isSkill() && (mbsvh.effect.getSourceId() == 1004
                        || mbsvh.effect.getSourceId() == 10001004 || mbsvh.effect.getSourceId() == 20001004
                        || mbsvh.effect.getSourceId() == 20011004 || mbsvh.effect.getSourceId() == 20011004
                        || mbsvh.effect.getSourceId() == 20021004 || mbsvh.effect.getSourceId() == 20031004
                        || mbsvh.effect.getSourceId() == 30001004 || mbsvh.effect.getSourceId() == 30011004
                        || mbsvh.effect.getSourceId() == 50001004 || mbsvh.effect.getSourceId() == 2121005
                        || mbsvh.effect.getSourceId() == 2221005 || mbsvh.effect.getSourceId() == 2321003
                        || mbsvh.effect.getSourceId() == 3111002 || mbsvh.effect.getSourceId() == 3111005
                        || mbsvh.effect.getSourceId() == 3211002 || mbsvh.effect.getSourceId() == 3211005
                        || mbsvh.effect.getSourceId() == 4111002 || mbsvh.effect.getSourceId() == 2211011)) {
                    cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                    break;
                }
            } else if (mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skillid) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }

    public void dispelItem(int skillid) {
        final LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();

        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.getSummonMovementType() != null) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
            }
        }
    }

    public void cancelAllBuffs_() {
        effects.clear();
    }

    public void cancelAllBuffs() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }

        for (BuffStatsValueHolder mbsvh : allBuffs) {
            cancelEffect(mbsvh.effect, false, mbsvh.startTime);
        }
    }

    public void cancelMorphs() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }

        for (BuffStatsValueHolder mbsvh : allBuffs) {
            switch (mbsvh.effect.getSourceId()) {
                case 5111005:
                case 5121003:
                case 15111002:
                case 13111005:
                case 61111008: // Final Figure (3rd)
                case 61120008: // Final Figure (4th)
                case 61121053: //Final trance
                    return; // Since we can't have more than 1, save up on loops
                default:
                    if (mbsvh.effect.isMorph()) {
                        cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                        continue;
                    }
            }
        }
    }

    public int getCTS_MorphState() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }

        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMorph()) {
                return mbsvh.effect.getSourceId();
            }
        }
        return -1;
    }

    public void silentGiveBuffs(List<MapleBuffValueHolder> buffs) {
        for (MapleBuffValueHolder mbsvh : buffs) {
            mbsvh.effect.silentApplyBuff(this, mbsvh.startTime);
        }

    }

    public List<BuffStatsValueHolder> getAllBuffs_() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }
        return allBuffs;
    }

    public List<MapleBuffValueHolder> getAllBuffs() { // 후원 버프
        List<MapleBuffValueHolder> ret = new ArrayList<MapleBuffValueHolder>();
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                if (bsvh.effect.getSourceId() == 5321054) {
                }
                allBuffs.add(bsvh);
            }
        }
        for (Iterator<BuffStatsValueHolder> it = allBuffs.iterator(); it.hasNext();) {
            BuffStatsValueHolder mbsvh = it.next();
            ret.add(new MapleBuffValueHolder(mbsvh.startTime, mbsvh.effect));
        }
        return ret;
    }

    public Map<BuffStats, List<BuffStatsValueHolder>> getEffects() {
        return effects;
    }

    public void cancelMagicDoor() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }
        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMagicDoor()) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }

    public int getMonsterCombo() {
        return monsterCombo;
    }

    public void setMonsterCombo(int count) {
        monsterCombo = count;
    }

    public void addMonsterCombo(int amount) {
        monsterCombo += amount;
    }

    public long getMonsterComboTime() {
        return monsterComboTime;
    }

    public void setMonsterComboTime(long count) {
        monsterComboTime = count;
    }

    public void silentEnforceMaxHpMp() {
        stats.setMp(stats.getCurrentMaxMp());
        stats.setHp(stats.getCurrentMaxHp(), true, this);
    }

    public void enforceMaxHpMp() {
        try {
            List<Pair<PlayerStatList, Long>> statups = new ArrayList<Pair<PlayerStatList, Long>>(2);
            stats.setMp(stats.getMp());
            statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.MP, Long.valueOf(stats.getMp())));
            stats.setHp(stats.getHp(), this);
            statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.HP, Long.valueOf(stats.getHp())));
            if (statups.size() > 0) {
                client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statups, getJob(), this));
            }
        } catch (NullPointerException ex) {
        }
    }

    public void refreshMaxHpMp() {
        getStat().recalcLocalStats(this);
        List<Pair<PlayerStatList, Long>> statups = new ArrayList<Pair<PlayerStatList, Long>>(2);
        stats.setMp(stats.getMp());
        statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.MP, Long.valueOf(stats.getMp())));
        stats.setHp(stats.getHp(), this);
        statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.HP, Long.valueOf(stats.getHp())));
        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statups, getJob(), this));
    }

    public void refreshMaxHp() {
        getStat().recalcLocalStats(this);
        List<Pair<PlayerStatList, Long>> statups = new ArrayList<Pair<PlayerStatList, Long>>(2);
        stats.setHp(stats.getHp(), this);
        statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.HP, Long.valueOf(stats.getHp())));
        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statups, getJob(), this));
    }

    public MapleMap getMap() {
        return map;
    }

    public void setMap(MapleMap newmap) {
        this.map = newmap;
    }

    public void setMap(int PmapId) {
        this.mapid = PmapId;
    }

    public int getMapId() {
        if (map != null) {
            return map.getId();
        }
        return mapid;
    }

    public int getInitialSpawnpoint() {
        return initialSpawnPoint;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final String getBlessOfFairyOrigin() {
        return BlessOfFairy_Origin;
    }

    public final String getBlessOfEmpressOrigin() {
        return BlessOfEmpress_Origin;
    }

    public final short getLevel() {
        return level;
    }

    public int getRank() {
        return rank;
    }

    public int getRankMove() {
        return rankMove;
    }

    public int getWorldRank() {
        return worldRank;
    }

    public int getWorldRankMove() {
        return worldRankdMove;
    }

    public int getFame() {
        return fame;
    }

    public final int getFallCounter() {
        return fallcounter;
    }

    public final MapleClient getClient() {
        return client;
    }

    public final void setClient(final MapleClient client) {
        this.client = client;
    }

    public long getExp() {
        return exp;
    }

    public void setInnerExp(int exp) {
        this.innerExp = exp;
    }

    public void gainInnerExp(int exp) {
        this.innerExp += exp;
    }

    public int getInnerExp() {
        return innerExp;
    }

    public void setInnerLevel(int level) {
        this.innerLevel = level;
    }

    public int getInnerLevel() {
        if (innerLevel == 0) {
            innerLevel++;
        }
        return innerLevel;
    }

    public int getArtifactPoints() {
        return artifactPoints;
    }

    public void setArtifactPoints(int points) {
        artifactPoints = points;
    }

    public void addArtifactPoints(int points) {
        artifactPoints = artifactPoints + points;
    }

    public int getRemainingAp() {
        return remainingAp;
    }

    public int[] getRemainingSps() {
        return remainingSp;
    }

    public void setRemainingSps(int[] s) {
        remainingSp = s;
    }

    public int getRemainingSp() {
        return remainingSp[GameConstants.getSkillBook(job)]; // default
    }

    public int getRemainingSp(final int skillbook) {
        return remainingSp[skillbook];
    }

    public int getRemainingSpSize() {
        int ret = 0;
        for (int i = 0; i < remainingSp.length; i++) {
            if (remainingSp[i] > 0) {
                ret++;
            }
        }
        return ret;
    }

    public int getMpApUsed() {
        return mpApUsed;
    }

    public void setMpApUsed(int mpApUsed) {
        this.mpApUsed = mpApUsed;
    }

    public int getHpApUsed() {
        return hpApUsed;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHpApUsed(int hpApUsed) {
        this.hpApUsed = hpApUsed;
    }

    public byte getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(byte skinColor) {
        this.skinColor = skinColor;
    }

    public byte getSecondSkinColor() {
        return secondSkinColor;
    }

    public void setSecondSkinColor(byte skinColor) {
        this.secondSkinColor = skinColor;
    }

    public void setBarrier(boolean v) {
        this.barrier = v;
    }

    public boolean barrier = false;

    public boolean Barrier() {
        return barrier;
    }

    public short getJob() {
        return job;
    }

    public void setJob(short newcharjob) {
        job = newcharjob;
    }

    public int getGender() {
        return gender;
    }

    public byte getSecondGender() {
        return secondGender;
    }

    public int getHair() {
        if (!ServerConstants.real_face_hair.contains(String.valueOf(hair))) {
            setKeyValue("MixColor", null);
            return 30000;
        }
        return hair;
    }

    public int getSecondHair() {
        return hair2;
    }

    public int getFace() {
        if (!ServerConstants.real_face_hair.contains(String.valueOf(face))) {
            return 20000;
        }
        return face;
    }

    public int getSecondFace() {
        return face2;
    }

    public int getAskGuildid() {
        return askguildid;
    }

    public void setAskguildid(int askguildid) {
        this.askguildid = askguildid;
    }

    public int getWP() {
        return wp;
    }

    public void setWP(int wp) {
        this.wp = wp;
    }

    public void gainWP(int wp) {
        this.wp += wp;
    }

    public int addWP(int wp) {
        int wf = wpForce.size() + 1;
        wpForce.put(wf, wp);
        return wf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setSecondHair(int hair) {
        this.hair2 = hair;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setSecondFace(int face) {
        this.face2 = face;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    public void setFallCounter(int fallcounter) {
        this.fallcounter = fallcounter;
    }

    public void setRemainingAp(int remainingAp) {
        this.remainingAp = remainingAp;
    }

    public void setRemainingSp(int remainingSp) {
        this.remainingSp[GameConstants.getSkillBook(job)] = remainingSp; // default
    }

    public void setRemainingSp(int remainingSp, final int skillbook) {
        this.remainingSp[skillbook] = remainingSp;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public void setSecondGender(byte gender) {
        this.secondGender = gender;
    }

    public final DailyGift getDailyGift() {
        return dailyGift;
    }

    public BuddyList getBuddylist() {
        return buddylist;
    }

    public void addFame(int famechange) {
        this.fame += famechange;
    }

    public void setLastViewTime(long time) {
        this.lastViewTime = time;
    }

    public long getLastViewTime() {
        return lastViewTime;
    }

    public void setPetLoot(boolean status) {
        this.petLoot = status;
    }

    public boolean getPetLoot() {
        return petLoot;
    }

    public void setPetAutoHP(int itemId) {
        this.petAutoHP = itemId;
    }

    public int getPetAutoHP() {
        return petAutoHP;
    }

    public void setPetAutoMP(int itemId) {
        this.petAutoMP = itemId;
    }

    public int getPetAutoMP() {
        return petAutoMP;
    }

    public void changeMap(int map, int portal) {
        MapleMap warpMap = client.getChannelServer().getMapFactory().getMap(map);
        changeMap(warpMap, warpMap.getPortal(portal));
    }

    public void changeMapBanish(final int mapid, final String portal, final String msg) {
        dropMessage(5, msg);
        final MapleMap map = client.getChannelServer().getMapFactory().getMap(mapid);
        changeMap(map, map.getPortal(portal));
    }

    public void changeMap(final MapleMap to, final Point pos) {
        changeMapInternal(to, pos, MainPacketCreator.getWarpToMap(to, 0x81, this));
    }

    public void changeMap(final MapleMap to, final MaplePortal pto) {
        changeMapInternal(to, pto.getPosition(), MainPacketCreator.getWarpToMap(to, pto.getId(), this));
    }

    private void changeMapInternal(MapleMap to, Point pos, byte[] warpPacket) {
        if (to.getId() == 951000000 || to.getId() == 910001000) {
            MapleMap map = client.getChannelServer().getMapFactory().getMap(to.getId());
            MaplePortal pt = map.getPortal(2);
            to = map;
            pos = pt.getPosition();
            warpPacket = MainPacketCreator.getWarpToMap(to, pt.getId(), this);
        }
        WarpRand = -1;
        this.bossDeathCount = -1;
        if (eventInstance != null) {
            eventInstance.changedMap(this, to.getId());
        }

        client.getSession().writeAndFlush(warpPacket);

        if (getQuickMoved()) {
            client.send(MainPacketCreator.getQuickMove(new ArrayList<QuickMoveEntry>()));
            setQuickMoved(false);
        }
        if (GameConstants.isPhantom(getJob())) { //
            client.send(MainPacketCreator.cardAmount(getCardStack()));
        }

        if (getBuffedValue(BuffStats.SUMMON) != null) {
            cancelEffectFromBuffStat(BuffStats.SUMMON, -1);
        }
        if (getBuffedValue(BuffStats.Beholder) != null) {
            cancelEffectFromBuffStat(BuffStats.Beholder, -1);
        }
        if (getBuffedValue(BuffStats.PickPocket) != null) {
            this.mesoCount = 0;
            SkillFactory.getSkill(4211003).getEffect(this.getSkillLevel(4211003)).applyTo(this);
        }

        List<MapleSummon> maelstrom = new ArrayList<>();
        getSummons().values().stream().filter(sum -> sum.left == 12111022).forEach(s -> maelstrom.add(s.right));
        maelstrom.forEach(sum -> {
            removeSummon(sum.getObjectId());
            map.broadcastMessage(MainPacketCreator.removeSummon(sum, true));
            map.removeMapObject(sum);
            removeVisibleMapObject(sum);
        });

        map.removePlayer(this);
        if (client.getChannelServer().getPlayerStorage().getCharacterById(getId()) != null) {
            map = to;
            setPosition(pos);
            if (android != null) {
                android.setPosition(pos);
            }
            to.addPlayer(this);
        }
        this.setAtomsa(1);
    }

    public void leaveMap() {
        controlled.clear();
        visibleMapObjects.clear();
        if (chair != 0) {
            cancelFishingTask();
            chair = 0;
        }
        if (hpDecreaseTask != null) {
            hpDecreaseTask.cancel(false);
        }
        cancelMapTimeLimitTask();
    }

    public void resetStats(final int str, final int dex, final int int_, final int luk) {
        List<Pair<PlayerStatList, Long>> stats = new ArrayList<Pair<PlayerStatList, Long>>(2);
        final MapleCharacter chr = this;
        int total = chr.getStat().getStr() + chr.getStat().getDex() + chr.getStat().getLuk() + chr.getStat().getInt()
                + chr.getRemainingAp();

        total -= str;
        chr.getStat().setStr(str);

        total -= dex;
        chr.getStat().setDex(dex);

        total -= int_;
        chr.getStat().setInt(int_);

        total -= luk;
        chr.getStat().setLuk(luk);

        chr.setRemainingAp(total);

        stats.add(new Pair<PlayerStatList, Long>(PlayerStatList.STR, Long.valueOf(str)));
        stats.add(new Pair<PlayerStatList, Long>(PlayerStatList.DEX, Long.valueOf(dex)));
        stats.add(new Pair<PlayerStatList, Long>(PlayerStatList.INT, Long.valueOf(int_)));
        stats.add(new Pair<PlayerStatList, Long>(PlayerStatList.LUK, Long.valueOf(luk)));
        stats.add(new Pair<PlayerStatList, Long>(PlayerStatList.AVAILABLEAP, Long.valueOf(total)));
        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(stats, false, chr.getJob(), this));
    }

    public void startHurtHp() {
        hpDecreaseTask = EtcTimer.getInstance().register(new Runnable() {

            @Override
            public void run() {
                if (map.getHPDec() < 1 || !isAlive()) {
                    return;
                } else if (getInventory(MapleInventoryType.EQUIPPED).findById(map.getHPDecProtect()) == null) {
                    addHP(-map.getHPDec());
                }
            }
        }, 10000);
    }

    public void changeJob(int newJob) {
        int maxhp = stats.getMaxHp(), maxmp = stats.getMaxMp();
        List<Pair<PlayerStatList, Long>> statup = new ArrayList<Pair<PlayerStatList, Long>>(2);
        this.job = (short) newJob;
        updateSingleStat(PlayerStatList.JOB, job);
        switch (job) {
            case 100:
            case 1100:
            case 2100:
            case 3100:
            case 3101:
                maxhp += Randomizer.rand(500, 700);
                break;
            case 200:
            case 2200:
            case 2210:
            case 3200:
                maxmp += Randomizer.rand(200, 450);
                break;
            case 300:
            case 400:
            case 500:
            case 501:
            case 3500:
                maxhp += Randomizer.rand(200, 380);
                maxmp += Randomizer.rand(50, 150);
                break;
            case 110:
            case 3110:
                maxhp += Randomizer.rand(700, 1000);
                break;
            case 120:
            case 130:
            case 1110:
            case 2110:
                maxhp += Randomizer.rand(600, 800);
                break;
            case 210:
            case 220:
            case 230:
            case 3210:
                maxmp += Randomizer.rand(800, 1000);
                break;
            case 310:
            case 320:
            case 410:
            case 420:
            case 430:
            case 1310:
            case 1410:
            case 2310:
            case 3510:
            case 3310:
            case 530:
                maxhp += Randomizer.rand(600, 800);
                maxmp += Randomizer.rand(300, 500);
                break;
            case 900:
            case 800:
                maxhp += 500000;
                maxmp += 500000;
                break;
        }
        if (!GameConstants.isBeginnerJob(job)) {
            if (GameConstants.isEvan(job)) {
                makeDragon();
                map.spawnDragon(dragon);
            } else if (GameConstants.isPhantom(job)) {
                client.send(MainPacketCreator.cardAmount(getCardStack()));
            }
        }
        if (GameConstants.isKOC(getJob()) && getLevel() >= 100) {
            if (getSkillLevel(Integer.parseInt(String.valueOf(getJob() + "1000"))) <= 0) {
                teachSkill(Integer.parseInt(String.valueOf(getJob() + "1000")), (byte) 0,
                        SkillFactory.getSkill(Integer.parseInt(String.valueOf(getJob() + "1000"))).getMaxLevel());
            }
        }
        if (!isGM()) {
            switch (job % 1000) {
                case 100:
                    resetStats(25, 4, 4, 4);
                    break;
                case 200:
                    resetStats(4, 4, 20, 4);
                    break;
                case 300:
                case 400:
                    resetStats(4, 25, 4, 4);
                    break;
                case 500:
                    resetStats(4, 20, 4, 4);
                    break;
            }
        }
        if (maxhp >= 500000) {
            maxhp = 500000;
        }
        if (maxmp >= 500000) {
            maxmp = 500000;
        }
        if (newJob == 3112) {
            maxmp += 5;
        }
        stats.setMaxHp(maxhp);
        stats.setMaxMp(maxmp);
        stats.setHp(stats.getCurrentMaxHp(), this);
        stats.setMp(stats.getCurrentMaxMp());
        stats.recalcLocalStats(this);
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXHP, Long.valueOf(maxhp)));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXMP, Long.valueOf(maxmp)));
        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statup, getJob(), this));
        map.broadcastMessage(this, MainPacketCreator.showSpecialEffect(getId(), 14), false); // 1.2.251+, (+1)
        silentPartyUpdate();
        guildUpdate();
        checkForceShield();
        for (int i = 0; i < (getJob() % 10) + 1; i++) {
            maxskill(((i + 1) == ((getJob() % 10) + 1)) ? getJob() - (getJob() % 100) : getJob() - (i + 1));
        }
        maxskill(getJob());
        if (GameConstants.isDemonAvenger(getJob())) {
            maxskill(3101);
        }
    }

    public long getLastRcDmg() {
        return lastrcdmg;
    }

    public void setLastRcDmg(Long t) {
        lastrcdmg = t;
    }

    public int getMYRebornsRank() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ret = 0;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and reborns > 0 and `reborns` > ?");
            // ps.setInt(1, this.getId());
            ps.setInt(1, getReborns());
            rs = ps.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("count(*)") + 1;
            }

            ps.close();
            rs.close();
            con.close();

            return !isGM() ? ret : 99;
        } catch (SQLException ex) {

        }
        return -1;
    }

    public void makeDragon() {
        dragon = new MapleDragon(this);
    }

    public MapleDragon getDragon() {
        return dragon;
    }

    public void gainAp(int ap) {
        this.remainingAp += ap;
        updateSingleStat(PlayerStatList.AVAILABLEAP, this.remainingAp);
    }

    public void gainHellAp(final int amount) {
        gainAp(amount);
    }

    public void setAp(int ap) {
        this.remainingAp = ap;
        updateSingleStat(PlayerStatList.AVAILABLEAP, this.remainingAp);
    }

    public int getAvargeSP() {
        int p = 0;
        for (ISkill s : SkillFactory.getAllSkills(job)) {
            p += s.getMaxLevel();
        }
        return Math.max(p / 30, 3);
    }

    public void gainSP(int sp) {
        this.remainingSp[GameConstants.getSkillBook(job)] += sp;
        client.getSession().writeAndFlush(MainPacketCreator.updateSp(this, false));
        if (sp > 0) {
            client.getSession().writeAndFlush(UIPacket.getSPMsg((byte) sp, (short) job));
        }
    }

    public void gainSP(int sp, int job) {
        this.remainingSp[job] += sp;
        client.getSession().writeAndFlush(MainPacketCreator.updateSp(this, false));
        if (sp > 0) {
            client.getSession().writeAndFlush(UIPacket.getSPMsg((byte) sp, (short) job));
        }
    }

    public void spawnPet(byte slot) {
        spawnPet(slot, false, true);
    }

    public void spawnPet(byte slot, boolean lead) {
        spawnPet(slot, lead, true);
    }

    
    public void spawnPet(byte slot, boolean lead, boolean broadcast) {
        final IItem item = getInventory(MapleInventoryType.CASH).getItem(slot);
        if (item == null || item.getItemId() >= 5010000 || item.getItemId() < 5000000) {
            return;
        }
        Map<String, Integer> multipet = ItemInformation.getInstance().getEquipStats(item.getItemId());
        int petBuffindex = 0;
        final MaplePet pet = item.getPet();
        if (pet != null) {
            int petbuffid;
            List<Integer> petbufflist = new ArrayList<>();
            List<Integer> petbufflists = new ArrayList<>();
            if (getPetIndex(pet) != -1) {
                for (MaplePet pet_ : getPets()) {
                    if (pet_ != null) {
                        petbuffid = GameConstants.getPetBuff(pet_.getPetItemId());
                        if (petbuffid == 0) {
                            continue;
                        }
                        petbufflists.add(petbuffid);
                    }
                }
                petbuffid = GameConstants.getPetBuff(pet.getPetItemId());
                if (petbufflists.size() == 3) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                } else if (petbufflists.size() == 2) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                } else if (petbufflists.size() == 1) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                }
                if (petbuffid != 0) {
                    petbufflist.add(petbuffid + petBuffindex);
                }
                for (Integer skill : petbufflist) {
                    changeSkillLevel_NoSkip(SkillFactory.getSkill(80000000 + skill), (byte) -1, (byte) -1);
                }
                unequipPet(pet, false, false);
            } else {
                //if (!(multipet.containsKey("multiPet") && multipet.get("multiPet") == 1) && getPet(0) != null) {
                //    unequipPet(getPet(0), false, false);
                //}
                //shiftPetsRight();
                final Point pos = getPosition();
                pet.setPos(pos);
                try {
                    pet.setFh(getMap().getFootholds().findMaple(pos).getId());
                } catch (NullPointerException e) {
                    pet.setFh(0); //lol, it can be fixed by movement
                }
                pet.setStance(0);
                pet.setSummoned(1);
                addPet(pet);
                pet.setSummoned(getPetIndex(pet) + 1); //then get the index
                if (broadcast && getMap() != null) {
                    //////System.out.println(getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()));
                    client.send(PetPacket.updatePet(this, pet, false, petLoot));
                    //client.send(PetPacket.showPetUpdate(this, pet.getUniqueId(), (byte) (pet.getSummonedValue() - 1)));
                    getMap().broadcastMessage(this, PetPacket.showPet(this, pet, false, false, false), true);
                }
                for (MaplePet pet_ : getPets()) {
                    if (pet_ != null) {
                        petbuffid = GameConstants.getPetBuff(pet_.getPetItemId());
                        if (petbuffid == 0) {
                            continue;
                        }
                        petbufflists.add(petbuffid);
                    }
                }
                petbuffid = GameConstants.getPetBuff(pet.getPetItemId());
                if (getPetIndex(pet) == -1) {
                    client.send(MainPacketCreator.resetActions());
                    return;
                }
                if (petbufflists.size() == 3) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                } else if (petbufflists.size() == 2) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                } else if (petbufflists.size() == 1) {
                    for (MaplePet pet_ : getPets()) {
                        if (pet_ != null && pet_.getUniqueId() != pet.getUniqueId()) {
                            if (GameConstants.getPetBuff(pet_.getPetItemId()) - petbuffid == 0) {
                                ++petBuffindex;
                            }
                        }
                    }
                }
                if (petbuffid != 0) {
                    petbufflist.add(petbuffid + petBuffindex);
                }
                for (Integer skill : petbufflist) {
                    changeSkillLevel_NoSkip(SkillFactory.getSkill(80000000 + skill), (byte) 1, (byte) 1);
                }

            }
            client.send(MainPacketCreator.resetActions());
        }
    }

    public void changeSkillLevel_Skip(ISkill skil, int skilLevel, byte masterLevel) {
        final Map<ISkill, SkillEntry> enry = new HashMap<ISkill, SkillEntry>(1);
        enry.put(skil, new SkillEntry((byte) skilLevel, masterLevel, -1L));
        changeSkillLevel_Skip(enry, true);
    }

    public void changeSkillLevel_NoSkip(ISkill skil, int skilLevel, byte masterLevel) {
        final Map<ISkill, SkillEntry> enry = new HashMap<ISkill, SkillEntry>(1);
        enry.put(skil, new SkillEntry((byte) skilLevel, masterLevel, -1L));
        changeSkillLevel_NoSkip(enry, true);
    }

    public void changeSkillLevel_Skip(final Map<ISkill, SkillEntry> skill, final boolean write) { // only used for temporary skills (not saved into db)
        if (skill.isEmpty()) {
            return;
        }
        final Map<ISkill, SkillEntry> newL = new HashMap<>();
        for (final Entry<ISkill, SkillEntry> z : skill.entrySet()) {
            if (z.getKey() == null) {
                continue;
            }
            newL.put(z.getKey(), z.getValue());
            if (z.getValue().skillevel == 0 && z.getValue().masterlevel == 0) {
                if (skills.containsKey(z.getKey())) {
                    skills.remove(z.getKey());
                } else {
                    continue;
                }
            } else {
                skills.put(z.getKey(), z.getValue());
            }
        }
        if (write && !newL.isEmpty()) {
            client.send(MainPacketCreator.updateSkill(newL));
        }
    }

    public void changeSkillLevel_NoSkip(final Map<ISkill, SkillEntry> skill, final boolean write) { // only used for temporary skills (not saved into db)
        if (skill.isEmpty()) {
            return;
        }
        final Map<ISkill, SkillEntry> newL = new HashMap<>();
        for (final Entry<ISkill, SkillEntry> z : skill.entrySet()) {
            if (z.getKey() == null) {
                continue;
            }
            newL.put(z.getKey(), z.getValue());
            if ((z.getValue().skillevel == 0 && z.getValue().masterlevel == 0) || (z.getValue().skillevel == -1 && z.getValue().masterlevel == -1)) {
                if (skills.containsKey(z.getKey())) {
                    skills.remove(z.getKey());
                } else {
                    continue;
                }
            } else {
                skills.put(z.getKey(), z.getValue());
            }
        }
        if (write && !newL.isEmpty()) {
            client.send(MainPacketCreator.updateSkill(newL));
        }
    }

    public void changeSkillLevel(final int skill, byte newLevel, byte newMasterLevel) {
        changeSkillLevel(SkillFactory.getSkill(skill), newLevel, newMasterLevel, -1);
    }

    public void changeSkillLevel(final ISkill skill, byte newLevel, byte newMasterlevel) {
        changeSkillLevel(skill, newLevel, newMasterlevel, -1);
    }

    public void changeSkillLevel(final ISkill skill, byte newLevel, byte newMasterlevel, long expiration) {
        if (skill == null) {
            return;
        }
        if (newLevel == 0 && newMasterlevel == 0) {
            if (skills.containsKey(skill)) {
                skills.remove(skill);
            }
        } else {
            if (newLevel < 0) {
                newLevel = 0;
            }
            if (newMasterlevel < 0) {
                newMasterlevel = 0;
            }
            skills.put(skill, new SkillEntry(newLevel, newMasterlevel, expiration));
        }
        if (client.getPlayer() != null) {
            if (!GameConstants.isProfessionSkill(skill.getId())) {
                final Map<ISkill, SkillEntry> updates = new HashMap<>();
                updates.put(skill, new SkillEntry(newLevel, newMasterlevel, expiration));
                client.getSession().writeAndFlush(MainPacketCreator.updateSkill(updates));
            } else if (profession.getFirstProfessionSkill() == skill.getId()) {
                client.send(MainPacketCreator.updateProfessionSkill(profession.getFirstProfessionExp(), skill.getId(),
                        profession.getFirstProfessionLevel(), 10));
            } else if (profession.getSecondProfessionSkill() == skill.getId()) {
                client.send(MainPacketCreator.updateProfessionSkill(profession.getSecondProfessionExp(), skill.getId(),
                        profession.getSecondProfessionLevel(), 10));
            } else if (newLevel == 0 && newMasterlevel == 0) {
                client.send(MainPacketCreator.updateProfessionSkill(0, skill.getId(), 0, 0));
            } else {
                client.send(MainPacketCreator.updateProfessionSkill(-1, skill.getId(), -1, 1));
            }
            getStat().recalcLocalStats(this);
            if (GameConstants.isDemonAvenger(job)) {
                getStat().giveDemonWatk(this);
            }
        }
    }

    public void playerDead() {
        if (getEventInstance() != null) {
            getEventInstance().playerKilled(this);
        }
		
        dispelSkill(0);
        cancelAllBuffs();
        checkFollow();
        int charms = getItemQuantity(5130000, false);
        if (charms > 0) {
            InventoryManipulator.removeById(client, MapleInventoryType.CASH, 5130000, 1, true, false);
            charms--;
            if (charms > 0xFF) {
                charms = 0xFF;
            }
            client.getSession().writeAndFlush(CashPacket.useCharm((byte) charms, (byte) 0));
        }
        ea();
        updateSingleStat(PlayerStatList.HP, stats.getHp());
        //client.getSession().writeAndFlush(UIPacket.OpenUIOnDead());
        if (this.bossDeathCount >= 0) {
            this.BattleUserRespawnUI = System.currentTimeMillis() + 4000;
            client.getSession().writeAndFlush(UIPacket.BattleUserRespawnUI());
        }
        dropMessage(5, "You died Test 3");
    }

    public void updatePartyMemberHP() {

        if (party != null) {
            final int channel = client.getChannel();
            for (MaplePartyCharacter partychar : party.getMembers()) {
                if (partychar.getMapid() == getMapId() && partychar.getChannel() == channel) {
                    final MapleCharacter other = ChannelServer.getInstance(channel).getPlayerStorage()
                            .getCharacterByName(partychar.getName());
                    if (other != null) {
                        other.getClient().getSession().writeAndFlush(
                                MainPacketCreator.updatePartyMemberHP(getId(), stats.getHp(), stats.getCurrentMaxHp()));
                    }
                }
            }
        }
    }

    public void receivePartyMemberHP() {
        int channel = client.getChannel();
        for (MaplePartyCharacter partychar : party.getMembers()) {
            if (partychar.getMapid() == getMapId() && partychar.getChannel() == channel) {
                MapleCharacter other = ChannelServer.getInstance(channel).getPlayerStorage()
                        .getCharacterByName(partychar.getName());
                if (other != null) {
                    client.getSession().writeAndFlush(MainPacketCreator.updatePartyMemberHP(other.getId(),
                            other.getStat().getHp(), other.getStat().getCurrentMaxHp()));
                }
            }
        }
    }

    //Ad hoc test function
    public void statupdate_bytaiga(int inputmp) 
    {
        updateSingleStat(PlayerStatList.HP, stats.getHp());
		
		int beta = stats.getMp() - inputmp;
        beta = Math.min(getStat().getCurrentMaxMp(), beta);
        if (stats.setMp(beta)) {
            updateSingleStat(PlayerStatList.MP, stats.getMp());
        }
    }

    /**
     * Convenience function which adds the supplied parameter to the current hp
     * then directly does a updateSingleStat.
     *
     * @see MapleCharacter#setHp(int)
     * @param delta
     */
    public void addHP(int delta) {
        int alpha = stats.getHp() + delta;
        alpha = Math.min(getStat().getCurrentMaxHp(), alpha);
        if (stats.setHp(alpha, this)) {
            updateSingleStat(PlayerStatList.HP, stats.getHp());
        }
    }

    /**
     * Convenience function which adds the supplied parameter to the current mp
     * then directly does a updateSingleStat.
     *
     * @see MapleCharacter#setMp(int)
     * @param delta
     */

    public void addMP(int delta) {
        int beta = stats.getMp() + delta;
        beta = Math.min(getStat().getCurrentMaxMp(), beta);
        if (stats.setMp(beta)) {
            updateSingleStat(PlayerStatList.MP, stats.getMp());
        }
    }

    public void addMPHP(int hpDiff, int mpDiff) {
        List<Pair<PlayerStatList, Long>> statups = new ArrayList<Pair<PlayerStatList, Long>>();
        int alpha = Math.min(getStat().getCurrentMaxHp(), stats.getHp() + hpDiff);
        int beta = Math.min(getStat().getCurrentMaxMp(), stats.getMp() + mpDiff);
        if (stats.setHp(alpha, this)) {
            statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.HP, Long.valueOf(stats.getHp())));
        }
        if (stats.setMp(beta)) {
            statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.MP, Long.valueOf(stats.getMp())));
        }
        if (statups.size() > 0) {
            client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statups, getJob(), this));
        }
    }

    public void updateSingleStat(PlayerStatList stat, long newval) {
        updateSingleStat(stat, newval, false);
    }

    /**
     * Updates a single stat of this MapleCharacter for the client. This method
     * only creates and sends an update packet, it does not update the stat
     * stored in this MapleCharacter instance.
     *
     * @param stat
     * @param newval
     * @param itemReaction
     */
    public void updateSingleStat(PlayerStatList stat, long newval, boolean itemReaction) {
        List<Pair<PlayerStatList, Long>> statups = new ArrayList<Pair<PlayerStatList, Long>>();
        statups.add(new Pair<PlayerStatList, Long>(PlayerStatList.AVAILABLESP, Long.valueOf(newval)));
        if (stat == PlayerStatList.AVAILABLESP) {
            if (statups.size() >= newval) {
                client.getSession().writeAndFlush(MainPacketCreator.updateSp(this, false));
                return;
            } else {
                client.getSession().writeAndFlush(MainPacketCreator.updateSp(this, false));
                return;
            }
        }
        Pair<PlayerStatList, Long> statpair = new Pair<PlayerStatList, Long>(stat, Long.valueOf(newval));
        client.getSession().writeAndFlush(
                MainPacketCreator.updatePlayerStats(Collections.singletonList(statpair), itemReaction, getJob(), this));
    }

    public void gainExp(final long total, final boolean show, final boolean inChat, final boolean white) {
        boolean levelUp = false;
        long nowNeedExp = GameConstants.getExpNeededForLevel(level);
        if (level == 900) {
            final long needed = GameConstants.getExpNeededForLevel(level);
            if (exp + total > needed) {
                setExp(needed);
            } else {
                exp += total;
            }
        } else if (exp + total >= GameConstants.getExpNeededForLevel(level)) {
            exp += total;
            levelUp();
            if ((this.getBurningCharacter() == 1) && (this.getLevel() > 10) && (this.getLevel() <= 99)) {
                for (int i = 0; i < 3; i++) {
                    levelUp();
                }
                setExp(0);
            }
            final long needed = GameConstants.getExpNeededForLevel(level);
            if (exp > needed) {
                setExp(needed);
            }
            levelUp = true;
        } else {
            exp += total;
        }
        if (total != 0) {
            if (exp < 0) {
                if (total > 0) {
                    setExp(GameConstants.getExpNeededForLevel(level));
                } else if (total < 0) {
                    setExp(0);
                }
            }
            updateSingleStat(PlayerStatList.EXP, getExp());
            if (show) {
                client.getSession().writeAndFlush(MainPacketCreator.GainEXP_Others(total, inChat, white));
            }
        }
        if (levelUp && (total - nowNeedExp > 0)) {
            gainExp(total - nowNeedExp, show, inChat, white);
        }
    }

    public void gainExpMonster(final int gain, final boolean show, final boolean white, final byte pty, final int Class_Bonus_EXP) {

        int rate = client.getChannelServer().getExpRate();

        int SelectedMobBonusExp = 0;
        int PartyBonusPercentage = 0;
        int WeddingBonusExp = 0;
        int PartyBonusExp = 0;
        int ItemBonusExp = 0;
        int PremiumIPBonusExp = 0;
        int RainbowWeekEventBonusExp = 0;
        int BoomUpEventBonusExp = 0;
        int PlusExpBuffBonusExp = 0;
        int PsdBonusExpRate = 0;
        int IndieBonusExp = 0;
        int RelaxBonusExp = 0;
        int InstallItemBonusExp = 0;
        int AswanWinnerBonusExp = 0;
        int ExpByIncExpR = 0;
        int ValuePackBonusExp = 0;
        int ExpByIncPQExpR = 0;
        int BaseAddExp = 0;
        int BloodAllianceBonusExp = 0;
        int FreezeHotEventBonusExp = 0;
        int UserHPRateBonusExp = 0;
        int FieldExp = 0;
        int total = gain * rate + Class_Bonus_EXP;
        int limit = 50;
        
       
        if (getMapId() != 993014200) {
            if (getBuffedSkillEffect(BuffStats.ExpBuffRate) != null) {
                for (BuffStatsValueHolder bsvh : effects.get(BuffStats.ExpBuffRate)) {
                    PlusExpBuffBonusExp += (int) (total * (bsvh.value / 100.0D)) - total;
                    total += PlusExpBuffBonusExp;
                }
            }
        }

        if (WorldCommunity.사피) {
            ItemBonusExp += total;
            total += ItemBonusExp;
        }
        if (pendantExp > 0) {
            ItemBonusExp = (int) ((gain * rate) * ((this.pendantExp * 10) / 100.0D));
            total += ItemBonusExp;
        }

        Integer UserHPRate = getBuffedValue(BuffStats.MaxHP, -2023128);
        if (UserHPRate != null) {
            UserHPRateBonusExp = (int) ((gain * rate) * (UserHPRate / 100.0D));
            total += UserHPRateBonusExp;
        }

        if ((getPremiumTime() - System.currentTimeMillis()) >= 1) {
            PremiumIPBonusExp = (int) ((gain * rate) * ((rate * 20) / 100.0D));
            total += PremiumIPBonusExp;
        }

        if (getSkillLevel(20021110) > 0) { // Elf Blessing
            PsdBonusExpRate += (int) ((gain * rate)
                    * (SkillFactory.getSkill(20021110).getEffect(getSkillLevel(20021110)).getStat("expR") / 100.0D));
            total += PsdBonusExpRate;
        }
        else if (getSkillLevel(20021110) > 1) { // Elf Blessing 2
                PsdBonusExpRate += (int) ((gain * rate)
                    * (SkillFactory.getSkill(20021110).getEffect(getSkillLevel(20021110)).getStat("expR") / 50.0D));
            total += PsdBonusExpRate;
                
         }
        
        else { // Elf Blessing 3
            PsdBonusExpRate += (int) ((gain * rate)
                    * (SkillFactory.getSkill(20021110).getEffect(getSkillLevel(20021110)).getStat("expR") / 5.0D));
            total += PsdBonusExpRate;
            
        }

        if (getSkillLevel(80001040) > 0) { // link
            PsdBonusExpRate += (int) ((gain * rate)
                    * (SkillFactory.getSkill(80001040).getEffect(getSkillLevel(80001040)).getStat("expR") / 100.0D));
            total += PsdBonusExpRate;
        }

        if (this.isActiveBuffedValue(31121003)) { // link
            PsdBonusExpRate += (int) ((gain * rate) * (20 / 100.0D));
            total += PsdBonusExpRate;
        }

        if (getSkillLevel(80001040) > 0) {
            PsdBonusExpRate += (int) ((gain * rate)
                    * (SkillFactory.getSkill(80001040).getEffect(getSkillLevel(80001040)).getStat("expR") / 100.0D));
            total += PsdBonusExpRate;
        }

        if (getBuffedSkillEffect(BuffStats.IndieEXP) != null) {
            for (BuffStatsValueHolder bsvh : effects.get(BuffStats.IndieEXP)) {
                IndieBonusExp += (int) ((gain * rate) * (bsvh.value / 100.0D));
                total += IndieBonusExp;
            }
        }

        if (pty > 1) {
            PartyBonusExp = (int) (((float) ((gain * rate) / 20)) * (pty + 1));
            total += PartyBonusExp;
        }

        Integer hsb = getBuffedValue(BuffStats.HolySymbol);
        if (hsb != null) {
            BaseAddExp = (int) ((gain * rate) * (hsb / 100.0D));
            total += BaseAddExp;
        }

        if (getBuffedValue(BuffStats.Dice) != null) {
            if (getBuffedValue(BuffStats.Dice) == 6) {
                PsdBonusExpRate += (int) ((gain * rate) * (30 / 100.0D));
                total += PsdBonusExpRate;
            } else {
                int rand1 = 0;
                int rand2 = 0;
                rand1 = getBuffedValue(BuffStats.Dice) & 0xF;
                rand2 = getBuffedValue(BuffStats.Dice) & 0xF0;
                if (rand1 == 6 && rand2 == 6) {
                    PsdBonusExpRate += (int) ((gain * rate) * (60 / 100.0D));
                    total += PsdBonusExpRate;
                } else if (rand1 == 6 || (rand1 == 1 && rand2 == 6)) {
                    PsdBonusExpRate += (int) ((gain * rate) * (30 / 100.0D));
                    total += PsdBonusExpRate;
                }
            }
        }
        if (GameConstants.isFrozenMap(getMapId())) {
            FieldExp = (int) (total * 20);
            total += FieldExp;
        }
        if (String.valueOf(total).contains("-")) {
            total = Integer.parseInt(String.valueOf(total).replaceAll("-", ""));
        }
        gainItemExp(total);
        if (level == 900) { // Was 275
            final long needed = GameConstants.getExpNeededForLevel(level);
            if (exp + total > needed) {
                setExp(needed);
            } else {
                exp += total;
            }
            
     if (getReborns() >= 0) {
            limit = 800 + getReborns();
            }
     
     if (level > limit){
            total = 0;
        }
     
        } else if (exp + total >= GameConstants.getExpNeededForLevel(level)) {
            exp += total;
            levelUp();
            if ((this.getBurningCharacter() == 1) && (this.getLevel() > 10) && (this.getLevel() <= 99)) { // 1.2.251+, Burning Season 2 correspondence.
                for (int i = 0; i < 3; i++) {
                    levelUp();
                }
                setExp(0);
            }
            final long needed = GameConstants.getExpNeededForLevel(level);
            if (exp > needed) {
                setExp(needed);
            }
        } else {
            exp += total;
        }
        
       
        if (gain != 0) {
            if (exp < 0) { // After adding, and negative
                if (gain > 0) {
                    setExp(GameConstants.getExpNeededForLevel(level));
                } else if (gain < 0) {
                    setExp(0);
                }
            }
            updateSingleStat(PlayerStatList.EXP, getExp());
            if (show) { // still show the expgain even if it's not there
                client.getSession()
                        .writeAndFlush(MainPacketCreator.GainEXP_Monster(gain * rate, false, white, SelectedMobBonusExp,
                                PartyBonusPercentage, WeddingBonusExp, PartyBonusExp, ItemBonusExp, PremiumIPBonusExp,
                                RainbowWeekEventBonusExp, BoomUpEventBonusExp, PlusExpBuffBonusExp, PsdBonusExpRate,
                                IndieBonusExp, RelaxBonusExp, InstallItemBonusExp, AswanWinnerBonusExp, ExpByIncExpR,
                                ValuePackBonusExp, ExpByIncPQExpR, BaseAddExp, BloodAllianceBonusExp,
                                FreezeHotEventBonusExp, UserHPRateBonusExp, FieldExp));
            }
        }
    }

    public void gainItemExp(int exp) {
        boolean levelup = false;
        for (IItem item : getInventory(MapleInventoryType.EQUIPPED).list()) {
            Equip equip = (Equip) item;
            if (ItemInformation.getInstance().getMaxLevelEquip(equip.getItemId()) > 0
                    && equip.getItemLevel() < ItemInformation.getInstance().getMaxLevelEquip(equip.getItemId())) {
                equip.setItemEXP(equip.getItemEXP() + exp);
                client.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, equip));
                if (equip.getItemEXP() >= GameConstants.getTimelessRequiredEXP(equip.getItemLevel())) {
                    ItemInformation.getInstance().levelUpItem(equip);
                    levelup = true;
                }
            }
        }
        if (levelup) {
            equipChanged();
        }
    }

    public void silentPartyUpdate() {
        if (party != null) {
            WorldCommunity.updateParty(party.getId(), MaplePartyOperation.SILENT_UPDATE, new MaplePartyCharacter(this));
        }
    }

    public boolean isGM() {
        return gmLevel > 0;
    }

    public int getGMLevel() {
        return gmLevel;
    }

    public boolean hasGmLevel(byte level) {
        return gmLevel >= level;
    }

    public void setGMLevel(byte level) {
        this.gmLevel = level;
    }

    public Map<Byte, Integer> getEquips(boolean paramBoolean) {
        Map<Byte, Integer> localHashMap = new HashMap<Byte, Integer>();
        for (IItem item : this.getInventory(MapleInventoryType.EQUIPPED).list()) {
            int i2 = item.getItemId();
            if ((paramBoolean) && (((Equip) item).getPotential7() != 0)) {
                String str = Integer.toString(item.getItemId()).substring(0, 3);
                i2 = Integer.parseInt(str + Integer.toString(((Equip) item).getPotential7()));
            }
            localHashMap.put((byte) item.getPosition(), i2);
        }
        return localHashMap;
    }

    public Map<Byte, Integer> getSecondEquips(boolean paramBoolean) {
        Map<Byte, Integer> localHashMap = new HashMap<Byte, Integer>();
        for (IItem item : this.getInventory(MapleInventoryType.EQUIPPED).list()) {
            int i1 = item.getItemId();
            if ((paramBoolean) && (((Equip) item).getPotential7() != 0)) {
                i1 = ((Equip) item).getPotential7();
            }
            if ((GameConstants.isAngelicBuster(getJob())) && (GameConstants.isOverall(i1))) {
                i1 = 1051291;
            }
            if ((GameConstants.isAngelicBuster(getJob())) && (!GameConstants.isOverall(i1))
                    && (!GameConstants.isSecondaryWeapon(i1)) && (!GameConstants.isWeapon(i1))
                    && (!GameConstants.isMedal(i1))) {
                continue;
            }
            localHashMap.put((byte) item.getPosition(), i1);
        }
        return localHashMap;
    }

    public final MapleInventory getInventory(MapleInventoryType type) {
        return inventory[type.ordinal()];
    }

    public final MapleInventory[] getInventorys() {
        return inventory;
    }

    public final void expirationTask() {
        long expiration;
        final long currenttime = System.currentTimeMillis();
        for (final MapleInventory inv : inventory) {
            final List<IItem> toberemove = new ArrayList<IItem>();
            for (final IItem item : inv.list()) {
                expiration = item.getExpiration();
                if (expiration != -1 && !GameConstants.isPet(item.getItemId())) {
                    short flag = item.getFlag();
                    if (ItemFlag.LOCK.check(flag)) {
                        if (currenttime > expiration) {
                            item.setExpiration(-1);
                            item.setFlag((short) (flag - ItemFlag.LOCK.getValue()));
                            client.getSession()
                                    .writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, item));
                        }
                    } else if (currenttime > expiration) {
                        if (item.isCash()) {
                            client.getSession().writeAndFlush(CashPacket.itemExpired(item.getItemId()));
                        } else {
                            message(5, "[" + ItemInformation.getInstance().getName(item.getItemId())
                                    + "] Expired and disappeared.");
                        }
                        toberemove.add(item);
                    }
                } else if (ItemInformation.getInstance().isExpireOnLogout(item.getItemId())) {
                    client.getSession().writeAndFlush(CashPacket.itemExpired(item.getItemId()));
                    toberemove.add(item);
                } else if (expiration == -1 && item.getItemId() == 1012270) {
                    item.setExpiration(System.currentTimeMillis() + (5L * 86400L * 1000L));
                    client.getSession()
                            .writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, item));
                }
            }
            for (final IItem item : toberemove) {
                InventoryManipulator.removeFromSlot(client, inv.getType(), item.getPosition(), item.getQuantity(),
                        false);
            }
        }
        final List<Integer> toRemoveSkills = new ArrayList<Integer>();
        for (Entry<ISkill, SkillEntry> se : skills.entrySet()) {
            if (se.getValue().expiration < currenttime && se.getValue().expiration != -1) {
                toRemoveSkills.add(se.getKey().getId());
            }
        }
        for (Integer i : toRemoveSkills) {
            changeSkillLevel(SkillFactory.getSkill(i), (byte) 0, (byte) 0);
            dropMessage(5, "[" + SkillFactory.getSkillName(i) + "] Skill expired and expired.");
        }
    }

    public MapleShop getShop() {
        return shop;
    }

    public void setShop(MapleShop shop) {
        this.shop = shop;
    }

    public long getMeso() {
        return meso;
    }

    public final int[] getSavedLocations() {
        return savedLocations;
    }

    public int getSavedLocation(SavedLocationType type) {
        return savedLocations[type.ordinal()];
    }

    public void saveLocation(SavedLocationType type) {
        savedLocations[type.ordinal()] = getMapId();
    }

    public void saveLocation(SavedLocationType type, int mapz) {
        savedLocations[type.ordinal()] = mapz;
    }

    public void clearSavedLocation(SavedLocationType type) {
        savedLocations[type.ordinal()] = -1;
    }

    public void gainMeso(long gain, boolean show) {
        gainMeso(gain, show, false, false);
    }

    public void gainMeso(long gain, boolean show, boolean enableActions) {
        gainMeso(gain, show, enableActions, false);
    }

    public void gainMeso(long gain, boolean show, boolean enableActions, boolean inChat) {
        if (meso + gain < 0) {
            client.getSession().writeAndFlush(MainPacketCreator.resetActions(this));
            return;
        }
        meso += gain;
        updateSingleStat(PlayerStatList.MESO, meso, enableActions);
        if (show) {
            client.getSession().writeAndFlush(MainPacketCreator.showMesoGain(gain, inChat, 0, 0));
        }
    }
    
    public void setMeso(long gain) {
    	if (gain < 0) {
    		client.getSession().writeAndFlush(MainPacketCreator.resetActions(this));
            return;
    	}
    	updateSingleStat(PlayerStatList.MESO, gain, true);
    }

    public void controlMonster(MapleMonster monster, boolean aggro) {
        monster.setController(this);
        controlled.add(monster);
        client.getSession().writeAndFlush(MobPacket.controlMonster(monster, false,
                monster.getId() != 8220028 ? aggro : false, GameConstants.isAswanMap(mapid)));
    }

    public void stopControllingMonster(MapleMonster monster) {
        controlled.remove(monster);
    }

    public void checkMonsterAggro(MapleMonster monster) {
        if (monster.getController() == this) {
            monster.setControllerHasAggro(true);
        } else {
            monster.switchController(this, true);
        }
    }

    public Collection<MapleMonster> getControlledMonsters() {
        return Collections.unmodifiableCollection(controlled);
    }

    public int getAccountID() {
        return accountid;
    }

    public void mobKilled(final int id) {
        try {
            for (MapleQuestStatus q : quests.values()) {
                if (q.getStatus() != 1 || !q.hasMobKills()) {
                    continue;
                }
                if (q.mobKilled(id)) {
                    client.getSession().writeAndFlush(MainPacketCreator.updateQuestMobKills(q));
                    if (q.getQuest().canComplete(this, 0)) {
                        client.getSession()
                                .writeAndFlush(MainPacketCreator.getShowQuestCompletion(q.getQuest().getId()));
                    }
                }
            }
        } catch (NullPointerException e) {
        }
    }

    public final List<MapleQuestStatus> getStartedQuests() {
        List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 1 && !q.isCustomQuest()) {
                ret.add(q);
            }
        }
        return Collections.unmodifiableList(ret);
    }

    public final List<MapleQuestStatus> getCompletedQuests() {
        List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 2 && !q.isCustomQuest()) {
                ret.add(q);
            }
        }
        return Collections.unmodifiableList(ret);
    }

    public Map<ISkill, SkillEntry> getSkills() {
        return Collections.unmodifiableMap(skills);
    }

    public List<InnerSkillValueHolder> getInnerSkills() {
        return innerSkills;
    }

    public byte getSummonLinkSkillLevel(final ISkill skill) {
        switch (skill.getId()) {
            case 23111009:
            case 23111010:
                return (byte) getSkillLevel(23111008);
            case 5211015:
            case 5211016:
                return (byte) getSkillLevel(5211011);
            case 5320011:
                return (byte) getSkillLevel(5321004);
            case 33101008:
                return (byte) getSkillLevel(33101004);
            case 33001011:
                return (byte) getSkillLevel(33001010);
            case 35111009:
            case 35111010:
                return (byte) getSkillLevel(35111001);
            case 35121013:
                return (byte) getSkillLevel(35121005);
            case 35121011:
                return (byte) getSkillLevel(35121009);
            case 36121013:
            case 36121014:
                return (byte) getSkillLevel(36121002);
            case 152121006:
                return (byte) getSkillLevel(152121005);
            case 400051052:
            case 400051053:
                return (byte) getSkillLevel(400051038);
        }
        if (GameConstants.isAngelicBlessSkill(skill.getId())) {
            return (byte) 1;
        }
        if (GameConstants.isSaintSaverSkill(skill.getId())) {
            return (byte) 1;
        }
        return getSkillLevel(skill);
    }

    public byte getSummonLinkSkillLevel(final int skill) {
        switch (skill) {
            case 23111009:
            case 23111010:
                return (byte) getSkillLevel(23111008);
            case 5211015:
            case 5211016:
                return (byte) getSkillLevel(5211011);
            case 5320011:
                return (byte) getSkillLevel(5321004);
            case 33101008:
                return (byte) getSkillLevel(33101004);
            case 35111009:
            case 35111010:
                return (byte) getSkillLevel(35111001);
        }
        if (GameConstants.isAngelicBlessSkill(skill)) {
            return (byte) 1;
        }
        if (GameConstants.isSaintSaverSkill(skill)) {
            return (byte) 1;
        }
        return (byte) getSkillLevel(skill);
    }

    public byte getSkillLevel(final ISkill skill) {
        final SkillEntry ret = skills.get(skill);
        if (skill == null) {
            return 0;
        }
        if (ret == null) {
            return 0;
        }
        if (ret.skillevel == 0) {
            return 0;
        }
        if (SkillFactory.getSkill(skill.getId()).getMaxLevel() == 1) {
            return 1;
        }
        if (link_skill.containsKey(skill.getId())) {
            return link_skill.get(skill.getId()).getLeft().byteValue();
        }
        byte skillLevel = (byte) Math.min(ret.skillevel + getStat().getIncAllSkill(), skill.getMaxLevel());
        if (skill.canCombatOrdered() && getBuffedValue(BuffStats.CombatOrders) != null) {
            skillLevel += getBuffedValue(BuffStats.CombatOrders).byteValue();
        }
        return skillLevel;
    }

    public int getSkillLevel(int skill) {
        SkillEntry ret = skills.get(SkillFactory.getSkill(skill));
        if (ret == null) {
            return 0;
        }
        if (ret.skillevel == 0) {
            return 0;
        }
        if (SkillFactory.getSkill(skill).getMaxLevel() == 1) {
            return 1;
        }
        if (link_skill.containsKey(skill)) {
            return link_skill.get(skill).getLeft();
        }
        byte skillLevel = (byte) Math.min(ret.skillevel + getStat().getIncAllSkill(),
                SkillFactory.getSkill(skill).getMaxLevel());
        if (SkillFactory.getSkill(skill).canCombatOrdered() && getBuffedValue(BuffStats.CombatOrders) != null) {
            skillLevel += getBuffedValue(BuffStats.CombatOrders).byteValue();
        }
        return skillLevel;
    }

    public byte getOriginSkillLevel(final ISkill skill) {
        final SkillEntry ret = skills.get(skill);
        if (skill == null) {
            return 0;
        }
        if (ret == null) {
            return 0;
        }
        if (ret.skillevel == 0) {
            return 0;
        }
        if (SkillFactory.getSkill(skill.getId()).getMaxLevel() == 1) {
            return 1;
        }
        return ret.skillevel;
    }

    public int getOriginSkillLevel(int skill) {
        SkillEntry ret = skills.get(SkillFactory.getSkill(skill));
        if (ret == null) {
            return 0;
        }
        if (ret.skillevel == 0) {
            return 0;
        }
        if (SkillFactory.getSkill(skill).getMaxLevel() == 1) {
            return 1;
        }
        return ret.skillevel;
    }

    public byte getMasterLevel(final ISkill skill) {
        final SkillEntry ret = skills.get(skill);
        if (ret == null) {
            return 0;
        }
        return ret.masterlevel;
    }

    public byte getMasterLevel(final int skill) {
        final SkillEntry ret = skills.get(SkillFactory.getSkill(skill));
        if (ret == null) {
            return 0;
        }
        return ret.masterlevel;
    }

    public void levelUp() {
        
        if (level != getReborns() + 100) {
        exp -= GameConstants.getExpNeededForLevel(level);
        level += 1;
        
        
        /*
        remainingAp += 5;
        remainingSp[0] += 1;
        remainingSp[1] += 1;
        remainingSp[2] += 1;
        remainingSp[3] += 1;
        remainingSp[4] += 1;
        */

        if (level >= 10) {
            gainLinkSkill();
        }
        AutoJob();

        if (level >= 100 && job == 6411) {
            changeJob(6412);
        }

        if (GameConstants.isZero(getJob())) { //Zero skill master
            zeroSkillLevel();
        }
        if (getStat().getStr() == 32000) { // Only Str
            if (getStat().getDex() == 32000) {
                if (getStat().getInt() == 32000) {
                    if (getStat().getLuk() == 32000) {
                        remainingAp = 0;
                    }
                }
            }
        }
        if (getStat().getDex() == 32000) { // Only Dex
            if (getStat().getStr() == 32000) {
                if (getStat().getInt() == 32000) {
                    if (getStat().getLuk() == 32000) {
                        remainingAp = 0;
                    }
                }
            }
        }
        if (getStat().getInt() == 32000) { // Only Int
            if (getStat().getDex() == 32000) {
                if (getStat().getStr() == 32000) {
                    if (getStat().getLuk() == 32000) {
                        remainingAp = 0;
                    }
                }
            }
        }
        if (getStat().getLuk() == 32000) { // Only Luk
            if (getStat().getStr() == 32000) {
                if (getStat().getInt() == 32000) {
                    if (getStat().getDex() == 32000) {
                        remainingAp = 0;
                    }
                }
            }
        }
        
        if (getStat().getStr() == 32000 && // when everything equals to 32000 (For GM's when max stat i think)
                getStat().getDex() == 32000 &&
                getStat().getInt() == 32000 &&
                getStat().getLuk() == 32000) {
            
            remainingAp = 0;
        }
        if (getStat().getStr() < 32000 // When Rb < get full points
                && getStat().getDex() < 32000
                && getStat().getInt() < 32000
                && getStat().getLuk() < 32000 
                && getReborns() < 3) {
            remainingAp += 3;
        }
       if (getStat().getStr() < 32000 || // If smaller than
               getStat().getDex() < 32000 ||
               getStat().getInt() < 32000 ||
               getStat().getLuk() < 32000){ 
            if (getReborns() >= 3){            
            remainingAp += Randomizer.rand(1, 2);
        }
       } else {
           
           remainingAp = 0;
           
       }
        

        /*if (level == 30 || level == 60 || level == 100) {
            setInnerAbility(level);
        }*/
        if (getInnerLevel() < 4) {
            if (level == 100 || level == 120 || level == 150) { //Ability
                innerLevelUp();
            }
        }

        if (level == 200) { //Symbol slot number 3
            MapleQuest.getInstance(1465).forceComplete(this, 0);
        } else if (level == 205) { //Symbol slot number 6
            int questid[] = {34450, 34451, 34452, 34453, 34454, 34455, 34456, 34457, 34458, 34459, 34460, 34461, 34462, 34463, 34464, 34465, 34466, 34467, 34468, 34469, 34470, 34471, 34472, 34473, 34474, 34475, 34476, 34477, 34478};
            for (int i = 0; i < questid.length; i++) {
                MapleQuest.getInstance(questid[i]).forceComplete(this, 0);
                getClient().getSession().writeAndFlush(MainPacketCreator.completeQuest(questid[i]));
            }
        }
        int maxhp = stats.getMaxHp();
        int maxmp = stats.getMaxMp();

        if ((GameConstants.isBeginnerJob(job)) && (job != 3001) && (job != 10000)) { // Beginner
            maxhp += Randomizer.rand(24, 32);
            maxmp += Randomizer.rand(20, 24);
        } else if (job == 3001 || job == 10000) { // Beginner
            maxhp += Randomizer.rand(52, 56);
        } else if (job >= 100 && job <= 132) { // Warrior
            maxhp += Randomizer.rand(70, 105);
            maxmp += Randomizer.rand(10, 20);
        } else if (job >= 200 && job <= 232) { // Wizard
            maxhp += Randomizer.rand(20, 36);
            maxmp += Randomizer.rand(44, 63);
        } else if ((job >= 300 && job <= 332) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3300 && job <= 3312)) {// Wild Hunter, defeat
            maxhp += Randomizer.rand(34, 55);
            maxmp += Randomizer.rand(28, 40);
        } else if (job >= 500 && job <= 532) { // Pirate
            maxhp += Randomizer.rand(50, 60);
            maxmp += Randomizer.rand(37, 50);
        } else if (job >= 2300 && job <= 2312) { // Mercedes
            maxhp += Randomizer.rand(45, 66);
            maxmp += Randomizer.rand(35, 43);
        } else if (job >= 3100 && job <= 3122) { // Daemon Slayer, Daemon Avenger | Demon Slayer, Demon Avenger
            maxhp += Randomizer.rand(70, 105);
        } else if (job >= 1100 && job <= 1112) { // Soul Master | Dawn Warrior
            maxhp += Randomizer.rand(70, 100);
            maxmp += Randomizer.rand(10, 20);
        } else if (job >= 1200 && job <= 1212) { // Flame Wizard
            maxhp += Randomizer.rand(20, 38);
            maxmp += Randomizer.rand(50, 75);
        } else if (job >= 2200 && job <= 2218) { // Evan
            maxhp += Randomizer.rand(25, 40);
            maxmp += Randomizer.rand(50, 80);
        } else if (job >= 2700 && job <= 2712) { // Luminous
            maxhp += Randomizer.rand(25, 40);
            maxmp += Randomizer.rand(60, 100);
        } else if (job >= 1500 && job <= 1512) { // Striker
            maxhp += Randomizer.rand(56, 67);
            maxmp += Randomizer.rand(34, 47);
        } else if (job >= 2100 && job <= 2112) { // Aran
            maxhp += Randomizer.rand(100, 130);
            maxmp += Randomizer.rand(10, 15);
        } else if (job >= 2400 && job <= 2412) { // Phantom
            maxhp += Randomizer.rand(56, 67);
            maxmp += Randomizer.rand(74, 100);
        } else if (job >= 3700 && job <= 3712) { // Blasher | Blaster
            maxhp += Randomizer.rand(56, 67);
            maxmp += Randomizer.rand(74, 100);
        } else if (job >= 3500 && job <= 3512) { // Mechanic
            maxhp += Randomizer.rand(56, 67);
            maxmp += Randomizer.rand(34, 47);
        } else if (job >= 3600 && job <= 3612) { // Xenon
            maxhp += Randomizer.rand(100, 130);
            maxmp += Randomizer.rand(10, 15);
        } else if (job >= 2500 && job <= 2512) { // Silverwall | Pathfinder
            maxhp += Randomizer.rand(66, 77);
            maxmp += Randomizer.rand(44, 57);
        } else if (job >= 3200 && job <= 3212) { // Battle Mage
            maxhp += Randomizer.rand(30, 36);
            maxmp += Randomizer.rand(44, 63);
        } else if (job >= 5100 && job <= 5112) { // Mikhail | Mihile
            maxhp += Randomizer.rand(70, 105);
            maxmp += Randomizer.rand(10, 20);
        } else if (job >= 6100 && job <= 6112) { // Kaiser
            maxhp += Randomizer.rand(70, 105);
            maxmp += Randomizer.rand(10, 20);
        } else if (job >= 6500 && job <= 6512) { // Angelic Buster
            maxhp += Randomizer.rand(56, 67);
        } else if (job >= 10100 && job <= 10112) { // Zero
            maxhp += Randomizer.rand(70, 105);
        } else if (job >= 13000 && job <= 13100) { // Pink Bean
            maxhp += Randomizer.rand(56, 67);
            maxmp += Randomizer.rand(44, 63);
        } else if (job >= 14000 && job <= 14212) { // Kinesis
            maxhp += Randomizer.rand(70, 105);
        } else if (job >= 6400 && job <= 6412) { // Cadena
            maxhp += Randomizer.rand(70, 105);
            maxmp += Randomizer.rand(74, 100);
        } else if (job >= 15210 && job <= 15212 || job == 15000) { // Illium
            maxhp += Randomizer.rand(60, 95);
            maxmp += Randomizer.rand(64, 90);
        } else if (job >= 15500 && job <= 15512) { // Arc | Ark
            maxhp += Randomizer.rand(60, 95);
            maxmp += Randomizer.rand(64, 90);
        }
        maxmp += stats.getInt() / 10;

        if (level >= 900 && !isGM()) {
            final StringBuilder sb = new StringBuilder("[Celebration] ");
            final IItem medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
            if (medal != null) {
                sb.append("<");
                sb.append(ItemInformation.getInstance().getName(medal.getItemId()));
                sb.append("> ");
            }

            sb.append(getName());
            sb.append(" Level " + level + "You have achieved! Please congratulate him everyone!");
            WorldBroadcasting.broadcastMessage(MainPacketCreator.serverNotice(6, sb.toString()));
        }

        maxhp = Math.min(500000, Math.abs(maxhp));
        maxmp = Math.min(500000, Math.abs(maxmp));

        stats.setInfo(maxhp, maxmp, getStat().getCurrentMaxHp(), getStat().getCurrentMaxMp());
        stats.recalcLocalStats(this);

        final List<Pair<PlayerStatList, Long>> statup = new ArrayList<Pair<PlayerStatList, Long>>(8);
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXHP, Long.valueOf(maxhp)));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.MAXMP, Long.valueOf(maxmp)));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.HP, Long.valueOf(getStat().getCurrentMaxHp())));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.MP, Long.valueOf(getStat().getCurrentMaxMp())));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.EXP, exp));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.LEVEL, (long) level));

        if (level >= 10) {
            client.getSession().writeAndFlush(MainPacketCreator.updateSp(this, false));
        }

        /*if (level <= 10) {
            stats.setStr(stats.getStr() + remainingAp);
            remainingAp = 0;
            statup.add(new Pair<PlayerStat, Long>(PlayerStat.STR, (long) stats.getStr()));
        }*/
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.AVAILABLEAP, (long) remainingAp));

        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statup, getJob(), this));
        map.broadcastMessage(this, MainPacketCreator.showSpecialEffect(getId(), 0), false);

        silentPartyUpdate();
        guildUpdate();

        if (GameConstants.isDemonAvenger(job)) {
            getStat().giveDemonWatk(this);
        }
        } 
        
        else {

            exp = GameConstants.getExpNeededForLevel(level);
            getClient().getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(1052230, 3500, "#face1#Please rebirth before gaining any other level. @rebirth", ""));

        }
        
    }

    public void updateLevel(int levelp){
        exp = 0;
        setLevel(levelp);
        final List<Pair<PlayerStatList, Long>> statup = new ArrayList<Pair<PlayerStatList, Long>>(2);
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.EXP, exp));
        statup.add(new Pair<PlayerStatList, Long>(PlayerStatList.LEVEL, (long) level));

        client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statup, getJob(), this));
        
    }
    public int getHitCountBat() {
        return hitcountbat;
    }

    public void setHitCountBat(int hitcount) {
        this.hitcountbat = hitcount;
    }

    public int getBatCount() {
        return batcount;
    }

    public void setBatCount(int count) {
        this.batcount = count;
    }

    public void changeKeybinding(int key, MapleKeyBinding keybinding) {
        if (keybinding.getType() != 0) {
            keylayout.Layout().put(Integer.valueOf(key), keybinding);
        } else {
            keylayout.Layout().remove(Integer.valueOf(key));
        }
    }

    public void sendMacros() {
        for (int i = 0; i < 5; i++) {
            if (skillMacros[i] != null) {
                send(MainPacketCreator.getMacros(skillMacros));
                break;
            }
        }
    }

    public void updateMacros(int position, SkillMacro updateMacro) {
        skillMacros[position] = updateMacro;
        skillmacros_changed = true;
    }

    public final SkillMacro[] getMacros() {
        return skillMacros;
    }

    public void tempban(String reason, Calendar duration, int greason, boolean IPMac) {
        if (IPMac) {
            client.banMacs();
        }

        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
            ps.setString(1, client.getIp());
            ps.execute();
            ps.close();

            client.getSession().close();

            ps = con.prepareStatement("UPDATE accounts SET tempban = ?, banreason = ?, greason = ? WHERE id = ?");
            Timestamp TS = new Timestamp(duration.getTimeInMillis());
            ps.setTimestamp(1, TS);
            ps.setString(2, reason);
            ps.setInt(3, greason);
            ps.setInt(4, accountid);
            ps.execute();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error while tempbanning" + ex);
        }

    }

    public final boolean ban(String reason, boolean IPMac, boolean autoban) {
        if (lastmonthfameids == null) {
            throw new RuntimeException("Trying to ban a non-loaded character (testhack)");
        }
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE accounts SET banned = ?, banreason = ? WHERE id = ?");
            ps.setInt(1, autoban ? 2 : 1);
            ps.setString(2, reason);
            ps.setInt(3, accountid);
            ps.execute();
            ps.close();

            if (IPMac) {
                ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                ps.setString(1, client.getIp());
                ps.execute();
                ps.close();
            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
            return false;
        }
        return true;
    }

    public int gainReward(int cid, int item, int quan) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO rewardsaves (cid, itemid, quantity) VALUES (?, ?, ?)");
            ps.setInt(1, cid);
            ps.setInt(2, item);
            ps.setInt(3, quan);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
        }
        return -1;
    }

    public static boolean ban(String id, String reason, boolean accountId) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps;
            if (id.matches("/[0-9]{1,3}\\..*")) {
                ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                ps.setString(1, id);
                ps.execute();
                ps.close();
                con.close();
                return true;
            }
            if (accountId) {
                ps = con.prepareStatement("SELECT id FROM accounts WHERE name = ?");
            } else {
                ps = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
            }
            boolean ret = false;
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement psb = con
                        .prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE id = ?");
                psb.setString(1, reason);
                psb.setInt(2, rs.getInt(1));
                psb.execute();
                psb.close();
                ret = true;
            }
            rs.close();
            ps.close();
            con.close();
            return ret;
        } catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
        }
        return false;
    }

    /**
     * Oid of players is always = the cid
     */
    @Override
    public int getObjectId() {
        return getId();
    }

    /**
     * Throws unsupported operation exception, oid of players is read only
     */
    @Override
    public void setObjectId(int id) {
        throw new UnsupportedOperationException();
    }

    public MapleStorage getStorage() {
        return storage;
    }

    public boolean isAlive() {
        return stats.getHp() > 0;
    }

    @Override
    public void sendDestroyData(final MapleClient client) {
        client.getSession().writeAndFlush(MainPacketCreator.removePlayerFromMap(this.getObjectId()));
    }

    @Override
    public void sendSpawnData(final MapleClient client) {
        if (!isHidden()) {
            client.getSession().writeAndFlush(MainPacketCreator.spawnPlayerMapobject(this));
            for (int i = 0; i < 3; ++i) {
                if (pets[i] != null) {
                    client.send(PetPacket.showPet(this, this.getPet(i), false, false, false));
                }
            }
            if (android != null) {
                client.getSession().writeAndFlush(AndroidPacket.spawnAndroid(this, android));
            }
            if (dragon != null) {
                client.getSession().writeAndFlush(MainPacketCreator.spawnDragon(dragon));
            }
            if (followid > 0 && followon) {
                client.getSession().writeAndFlush(MainPacketCreator.followEffect(followinitiator ? followid : id,
                        followinitiator ? id : followid, null));
            }
            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            for (Entry<BuffStats, List<BuffStatsValueHolder>> effect : effects.entrySet()) {
                for (BuffStatsValueHolder bsvh : effect.getValue()) {
                    statups.clear();
                    statups.add(new Triple<>(effect.getKey(), bsvh.value, false));
                }
            }
            if (statups.size() > 0) {
                getMap().broadcastMessage(this, MainPacketCreator.giveForeignBuff(this, statups), false);
            }
            if (getSkillEffect() != null) {
                getMap().broadcastMessage(MainPacketCreator.skillEffect(this, getSkillEffect(), getPosition()));
            }
        }
    }

    public void setDragon(MapleDragon d) {
        this.dragon = d;
    }

    // Secondary Weapon | Secondary Equip
    
    public void checkForceShield() { 
        final ItemInformation li = ItemInformation.getInstance();
        Equip equip;
        boolean potential = false;
        
        switch (job) {
            case 301: // PathFinder ------------
                equip = (Equip) li.getEquipById(1353703);
                potential = true;
                break;
            case 15200: // Illium ---------
                equip = (Equip) li.getEquipById(1353503);
                potential = true;
                break;
            case 410: // Night Lord ---------
                equip = (Equip) li.getEquipById(1352292);
                potential = true;
                break; 
            case 420: // Shadower ---------
                equip = (Equip) li.getEquipById(1352282);
                potential = true;
                break; 
            case 510: // Bucaneer ----------
                equip = (Equip) li.getEquipById(1352902);
                potential = true;
                break;
            case 520: // Corsair ----------
                equip = (Equip) li.getEquipById(1352912);
                potential = true;
                break;    
            case 210: // Fire Poison ---------
                equip = (Equip) li.getEquipById(1352232);
                potential = true;
                break;     
            case 220: // Ice Lightning --------- 
                equip = (Equip) li.getEquipById(1352242);
                potential = true;
                break;         
            case 230: // Bishop ---------
                equip = (Equip) li.getEquipById(1352252);
                potential = true;
                break;      
            case 310: // Bowmaster ---------
                equip = (Equip) li.getEquipById(1352262);
                potential = true;
                break;  
            case 320: //  Marksman---------
                equip = (Equip) li.getEquipById(1352272);
                potential = true;
                break;    
            case 15500: // Ark ---------
                equip = (Equip) li.getEquipById(1353603);
                potential = true;
                break;
            case 430: // Dual Blade --------
                equip = (Equip) li.getEquipById(1342000);
                potential = true;
                break;
            case 501: // Cannoneer --------
                equip = (Equip) li.getEquipById(1352923);
                potential = true;
                break;
            case 110: // Hero --------
                equip = (Equip) li.getEquipById(1352202);
                potential = true;
                break;
            case 120: // Paladin --------
                equip = (Equip) li.getEquipById(1352212);
                potential = true;
                break;
            case 130: // Dark Knight --------
                equip = (Equip) li.getEquipById(1352222);
                potential = true;
                break;
            case 1100: // Dawn Warrior ------- 
                equip = (Equip) li.getEquipById(1352972);
                potential = true;
                break;
            case 1200: // Flame Wizard -----
                equip = (Equip) li.getEquipById(1352972);
                potential = true;
                break;
            case 1300: // Wind Archer -----
                equip = (Equip) li.getEquipById(1352972);
                potential = true;
                break;
            case 1400: // Night Walker -------
                equip = (Equip) li.getEquipById(1352972);
                potential = true;
                break;
            case 1500: // Thunder Breaker ------
                equip = (Equip) li.getEquipById(1352972);
                potential = true;
                break;
            case 2100: // Aran --------
                equip = (Equip) li.getEquipById(1352932);
                potential = true;
                break;
            case 2200: // Evan ---------
                equip = (Equip) li.getEquipById(1352943);
                potential = true;
                break;
            case 2300: // Mercedes -------
                equip = (Equip) li.getEquipById(1352003);
                potential = true;
                break;
            case 2400: // Phantom ---------
                equip = (Equip) li.getEquipById(1352103);
                potential = true;
                break;
            case 2500: // Shade ------------
                equip = (Equip) li.getEquipById(1353103);
                potential = true;
                break;
            case 2700: // Luminous -------------
                equip = (Equip) li.getEquipById(1352403);
                potential = true;
                break;
            case 3001: // Demon Slayer Beginner --------
                equip = (Equip) li.getEquipById(1099004);
                potential = true;
                break;
            /*case 3112: // Demon Slayer
                potential = true;
            case 3100:
            case 3110:
            case 3111:
                equip = (Equip) li.getEquipById(1099001 + job % 10 + ((job % 100) / 10));
                break;*/
            case 3200: // Battle Mage --------
                equip = (Equip) li.getEquipById(1352953);
                potential = true;
                break;
            case 3300: // Wild Hunter ---------
                equip = (Equip) li.getEquipById(1352963);
                potential = true;
                break;
            case 3500: // Mechanic -----------
                equip = (Equip) li.getEquipById(1352703);
                potential = true;
                break;
            case 3600: // Xenon  ---------
                equip = (Equip) li.getEquipById(1353004);
                potential = true;
                break;
            case 3700: // Blaster --------
                equip = (Equip) li.getEquipById(1353403);
                potential = true;
                break;      
            case 5100: // Mihile --------
                equip = (Equip) li.getEquipById(1098003);
                potential = true;
                break;
          /*case 5112:
            case 5110:
            case 5111:
                equip = (Equip) li.getEquipById(1098000 + job % 10 + ((job % 100) / 10));
                break;*/
            case 6001: // Angelic Buster Beginner -------
                equip = (Equip) li.getEquipById(1352604);
                potential = true;
                break;
            case 6100: // Kaiser ----------
                equip = (Equip) li.getEquipById(1352503);
                        potential = true;
                break;
            case 6400: // Cadena ---------
                equip = (Equip) li.getEquipById(1353303);
                potential = true;
                break;
            case 6500: // Angelic Buster
                equip = (Equip) li.getEquipById(1352604);
                potential = true;
                break;
           /* case 2510: // Shade
                equip = (Equip) li.getEquipById(1353101);
                break;
            case 2511: // Shade
                equip = (Equip) li.getEquipById(1353102);
                break;
            case 2512: // Shade
                equip = (Equip) li.getEquipById(1353103);
                break;*/
            case 14200: // Kinesis --------
                equip = (Equip) li.getEquipById(1353203);
                potential = true;
                break;
            default:
                equip = null;
        }
        if (equip != null) {
            if (potential) {
                equip.renewPotential();
            }
            equip.setPosition((short) -10);
            equip.setQuantity((short) 1);
            equip.setGMLog("Secondary weapon"); // Was 보조무기 
            forceReAddItem_NoUpdate(equip, MapleInventoryType.EQUIPPED);
            equipChanged();
        }
    
    }

    public final void equipChanged() {
        map.broadcastMessage(this, MainPacketCreator.updateCharLook(this, false), false);
        stats.recalcLocalStats(this);
        enforceMaxHpMp();
        if (client.getPlayer().getMessenger() != null) {
            WorldCommunity.updateMessenger(client.getPlayer().getMessenger().getId(), client.getPlayer().getName(), client.getChannel());

        }
        if (GameConstants.isDemonAvenger(job)) {
            getStat().giveDemonWatk(this);
        }
    }

    public final MaplePet getPet(final long index) {
        return pets[(int) index];
    }

    public void updatePet() {
        for (int i = 0; i < 3; ++i) {
            if (pets[i] != null) {
                getClient().send(PetPacket.updatePet(this, pets[i], false, petLoot));
            }
        }
    }

    public void addPet(final MaplePet pet) {
        for (int i = 0; i < 3; ++i) {
            if (pets[i] == null) {
                pets[i] = pet;
                return;
            }
        }
    }

    public void addPetBySlotId(final MaplePet pet, int slotid) {
        if (pets[slotid] == null) {
            pets[slotid] = pet;
            pets[slotid].setPos(getPosition());
        }
    }

    public void removePet(MaplePet pet, boolean shiftLeft) {
        int slot = -1;
        for (int i = 0; i < 3; i++) {
            if (pets[i] != null) {
                if (pets[i].getUniqueId() == pet.getUniqueId()) {
                    pets[i] = null;
                    slot = i;
                    break;
                }
            }
        }
        if (shiftLeft) {
            if (slot > -1) {
                for (int i = slot; i < 3; i++) {
                    if (i != 2) {
                        pets[i] = pets[i + 1];
                    } else {
                        pets[i] = null;
                    }
                }
            }
        }
    }

    public final int getPetIndex(final MaplePet pet) {
        for (int i = 0; i < 3; ++i) {
            if (pets[i] != null) {
                if (pets[i].getUniqueId() == pet.getUniqueId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public final int getPetIndex(final int petId) {
        for (int i = 0; i < 3; ++i) {
            if (pets[i] != null) {
                if (pets[i].getUniqueId() == petId) {
                    return i;
                }
            }
        }
        return -1;
    }

    public final MaplePet[] getPets() {
        return pets;
    }

    public final MaplePet[] getEqPets() {
        final MaplePet[] pets = new MaplePet[getEqPet()];
        int ret = 0;
        for (int i = 0; i < 3; i++) {
            if (this.pets[i] != null) {
                pets[ret] = this.pets[i];
                ret++;
            }
        }
        return pets;
    }

    public final byte getPetById(final int petId) {
        byte count = 0;
        for (final MaplePet pet : pets) {
            if (pet.getPetItemId() == petId) {
                return count;
            }
            count++;

        }
        return -1;
    }

    public final int getEqPet() {
        int ret = 0;
        for (int i = 0; i < 3; i++) {
            if (pets[i] != null) {
                ret++;
            }
        }
        return ret == 0 ? -1 : ret;
    }

    public final void unequipAllPets() {
        for (final MaplePet pet : pets) {
            if (pet != null) {
                unequipPet(pet, true, false);
            }
        }
    }

    public void unequipPet(MaplePet pet, boolean shiftLeft, boolean hunger) {
        pet.saveToDb();
        client.send(PetPacket.updatePet(this, pet, true, petLoot));
        map.broadcastMessage(this, PetPacket.showPet(this, pet, true, hunger, false), true);
        if (pet != null) {
            removePet(pet, shiftLeft);
        }
    }

    public void shiftPetsRight() {
        if (pets[2] == null) {
            pets[2] = pets[1];
            pets[1] = pets[0];
            pets[0] = null;
        }
    }

    public final long getLastFameTime() {
        return lastfametime;
    }

    public final List<Integer> getFamedCharacters() {
        return lastmonthfameids;
    }

    public FameStatus canGiveFame(MapleCharacter from) {
        if (lastfametime >= System.currentTimeMillis() - 60 * 60 * 24 * 1000) {
            return FameStatus.NOT_TODAY;
        } else if (lastmonthfameids.contains(Integer.valueOf(from.getId()))) {
            return FameStatus.NOT_THIS_MONTH;
        }
        return FameStatus.OK;
    }

    public void hasGivenFame(MapleCharacter to) {
        lastfametime = System.currentTimeMillis();
        lastmonthfameids.add(Integer.valueOf(to.getId()));
        Connection con = null;
        try {
            con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO famelog (characterid, characterid_to) VALUES (?, ?)");
            ps.setInt(1, getId());
            ps.setInt(2, to.getId());
            ps.execute();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("ERROR writing famelog for char " + getName() + " to " + to.getName() + e);
        }
    }

    public final MapleKeyLayout getKeyLayout() {
        return this.keylayout;
    }

    public final void setExpeditionKilledBoss(boolean kill) {
        if (getParty() != null) {
            if (getParty().getExpedition() != null) {
                getParty().getExpedition().setBossKilled(kill);
            }
        }
    }

    public MapleParty getParty() {
        return party;
    }

    public int getPartyId() {
        return (party != null ? party.getId() : -1);
    }

    public void setParty(MapleParty party) {
        this.party = party;
    }

    public MapleUserTrade getTrade() {
        return trade;
    }

    public void setTrade(MapleUserTrade trade) {
        this.trade = trade;
    }

    public EventInstanceManager getEventInstance() {
        return eventInstance;
    }

    public void setEventInstance(EventInstanceManager eventInstance) {
        this.eventInstance = eventInstance;
    }

    public void addDoor(MapleDoor door) {
        doors.add(door);
    }

    public void clearDoors() {
        doors.clear();
    }

    public void cancelMechDoor() {
        for (final MapleMechDoor destroyDoor : getMechDoors()) {
            for (final MapleCharacter chr : getMap().getCharacters()) {
                destroyDoor.sendDestroyData(chr.getClient());
            }
            getMap().removeMapObject(destroyDoor);
        }
        clearMechDoors();
    }

    public void clearMechDoors() {
        mechdoors.clear();
    }

    public void addMechDoor(MapleMechDoor door) {
        mechdoors.add(door);
    }

    public List<MapleMechDoor> getMechDoors() {
        return new ArrayList<MapleMechDoor>(mechdoors);
    }

    public List<MapleDoor> getDoors() {
        return new ArrayList<MapleDoor>(doors);
    }

    public void setSmega() {
        if (smega) {
            smega = false;
            dropMessage(5, "You have set megaphone to disabled mode");
        } else {
            smega = true;
            dropMessage(5, "You have set megaphone to enabled mode");
        }
    }

    public boolean getSmega() {
        return smega;
    }

    public boolean canDoor() {
        return canDoor;
    }

    public void disableDoor() {
        canDoor = false;
        BuffTimer.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                canDoor = true;
            }
        }, 5000);
    }

    public Map<Integer, Pair<Integer, MapleSummon>> getSummons() {
        return summons;
    }

    public int getSummonCount(int skillId) {
        int count = 0;

        for (Pair<Integer, MapleSummon> summon : summons.values()) {
            if (summon.left == skillId) {
                count++;
            }
        }

        return count;
    }

    public void removeSummon(int oid) {
        for (final Pair<Integer, MapleSummon> summon : summons.values()) {
            if (summon.getRight().getObjectId() == oid) {
                summons.remove(oid);
                break;
            }
        }
    }

    public int getChair() {
        return chair;
    }

    public int getItemEffect() {
        return itemEffect;
    }

    public void setChair(int chair) {
        this.chair = chair;
    }

    public String getChairText() {
        return this.chairtext;
    }

    public void setChairText(String chairtext) {
        this.chairtext = chairtext;
    }

    public void setItemEffect(int itemEffect) {
        this.itemEffect = itemEffect;
    }

    @Override
    public Collection<MapleInventory> allInventories() {
        return Arrays.asList(inventory);
    }

    public void setInv(MapleInventory[] inv) {
        this.inventory = inv;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.PLAYER;
    }

    public int getGuildId() {
        return guildid;
    }

    public int getGuildRank() {
        return guildrank;
    }

    public void setGuildId(int _id) {
        guildid = _id;
        if (guildid > 0) {
            if (mgc == null) {
                mgc = new MapleGuildCharacter(this);
            } else {
                mgc.setGuildId(guildid);
            }
        } else {
            mgc = null;
        }
    }

    public void setGuildRank(int _rank) {
        guildrank = _rank;
        if (mgc != null) {
            mgc.setGuildRank(_rank);
        }
    }

    public MapleGuildCharacter getMGC() {
        return mgc;
    }

    public void setAllianceRank(int rank) {
        allianceRank = rank;
    }

    public int getAllianceRank() {
        return allianceRank;
    }

    public MapleGuild getGuild() {
        return ChannelServer.getGuild(getGuildId());
    }

    public void guildUpdate() {
        if (guildid <= 0) {
            return;
        }
        mgc.setLevel((short) level);
        mgc.setJobId(job);

        ChannelServer.memberLevelJobUpdate(mgc);
    }

    public boolean reborn() {
        if (level < 200) {
            return false;
        }

        this.resetStats(4, 4, 4, 4);
        this.reborns += 100 + ((level - 200) * 2);
        if (level == 900) {
            this.reborns += 8;
        }
        level = 10;
        remainingAp = reborns;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.broadcastPacket(
                    MainPacketCreator.getGMText(10, "[Celebration] " + name + "Reborn. (Current rebirth point : " + reborns + ")"));
        }
        client.send(MainPacketCreator.getPlayerInfo(this));
        map.removePlayer(this);
        map.addPlayer(this);
        return true;
    }

    public void setTierReborns(int tierReborns) {
        this.tierReborns = tierReborns;
        
    }
    public void setReborns(int reborns) {
        this.reborns = reborns;
    }

    public void saveGuildStatus() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("UPDATE characters SET guildid = ?, guildrank = ? WHERE id = ?");
            ps.setInt(1, guildid);
            ps.setInt(2, guildrank);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getLocalizedMessage() + se);
        }
    }

    public MapleAlliance getAlliance() {
        return alliance;
    }

    public void modifyCSPoints(int type, int quantity, boolean show) {
        if (getNX() < 0) {
            nxcash = 0;
        }
        switch (type) {
            case 1:
                nxcash += quantity;
                break;
            case 2:
                maplepoints += quantity;
                break;
            default:
                break;
        }
        send(MainPacketCreator.showMaplePoint(nxcash));
    }

    public void message(int type, String msg) {
        getClient().send(MainPacketCreator.serverNotice(type, msg));
    }

    public void message(String msg) {
        getClient().send(MainPacketCreator.serverNotice(5, msg));
    }

    public void Message(int type, String msg) {
        getClient().send(MainPacketCreator.getGMText(type, msg));
    }

    public void Message(String msg) {
        getClient().send(MainPacketCreator.getGMText(8, msg));
    }

    public void dropShowInfo(String msg) {
        getClient().send(UIPacket.showInfo(msg));
    }

    public int getCSPoints(int type) {
        switch (type) {
            case 1:
                return nxcash;
            case 2:
                return maplepoints;
            default:
                return 0;
        }
    }

    public final boolean haveItem(int itemid, int quantity, boolean checkEquipped, boolean greaterOrEquals) {
        int possesed = inventory[GameConstants.getInventoryType(itemid).ordinal()].countById(itemid);
        if (checkEquipped) {
            possesed += inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        if (greaterOrEquals) {
            return possesed >= quantity;
        } else {
            return possesed == quantity;
        }
    }

    public void setLevel(int level) {
        this.level = (short) level;
    }

    public final MapleQuestStatus getQuestNoAdd(final MapleQuest quest) {
        return quests.get(quest);
    }

    public boolean canSummon(int g) {
        if (lastSummonTime + g < System.currentTimeMillis()) {
            lastSummonTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public int getMorphGage() {
        return morphGage;
    }

    public void setMorphGage(int gage) {
        morphGage = gage;

    }

    public void BuyPET(int Petitem) {
        int uniqueid = Petitem;
        Item itemr = new Item(Petitem, (short) 1, (short) 1, (short) 0);
        itemr.setExpiration(2475606994921L);
        final MaplePet pet = MaplePet.createPet(Petitem, itemr.getExpiration());
        itemr.setPet(pet);
        itemr.setUniqueId(pet.getUniqueId());
        InventoryManipulator.addbyItem(client, itemr);
        InventoryManipulator.addFromDrop(getClient(), itemr, false);
    }

    public static boolean isIceAura = false;

    public static boolean isIceAura() {
        return isIceAura;
    }

    public void setIceAura(boolean aura) {
        this.isIceAura = aura;
    }

    public enum FameStatus {
        OK, NOT_TODAY, NOT_THIS_MONTH
    }

    public int getBuddyCapacity() {
        return buddylist.getCapacity();
    }

    public void setBuddyCapacity(int capacity) {
        buddylist.setCapacity(capacity);
        client.getSession().writeAndFlush(MainPacketCreator.updateBuddyCapacity(capacity));
    }

    public MapleMultiChat getMessenger() {
        return messenger;
    }

    public void setMessenger(MapleMultiChat messenger) {
        this.messenger = messenger;
    }

    public int getMessengerPosition() {
        return messengerposition;
    }

    public void setMessengerPosition(int position) {
        this.messengerposition = position;
    }

    public boolean getNXCodeValid(String code, boolean validcode) throws SQLException {
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT `valid` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            validcode = rs.getInt("valid") == 0 ? false : true;
        }
        rs.close();
        ps.close();
        con.close();
        return validcode;
    }

    public int getNXCodeType(String code) throws SQLException {
        int type = -1;
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT `type` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            type = rs.getInt("type");
        }
        rs.close();
        ps.close();
        con.close();
        return type;
    }

    public int getNXCodeItem(String code) throws SQLException {
        int item = -1;
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT `item` FROM nxcode WHERE code = ?");
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            item = rs.getInt("item");
        }
        rs.close();
        ps.close();
        con.close();
        return item;
    }

    public void setNXCodeUsed(String code) throws SQLException {
        Connection con = MYSQL.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE nxcode SET `valid` = 0 WHERE code = ?");
        ps.setString(1, code);
        ps.execute();

        ps = con.prepareStatement("UPDATE nxcode SET `user` = ? WHERE code = ?");
        ps.setString(1, getName());
        ps.setString(2, code);
        ps.execute();
        ps.close();
        con.close();
    }

    public void addCooldown(int skillId, long startTime, long length) {
        if (skillId == 31121003) {
            if (this.isActiveBuffedValue(31121007)) {
                length /= 2;
            }
        }
        if (skillId == 24121052) {
            length /= 2;
        }
        if (skillId == 400011031 && this.isActiveBuffedValue(400011016)) {
            length /= 2;
        }
        if (skillId == 400031040) {
            length = 2;
        }
        coolDowns.put(skillId, new CoolDownValueHolder(skillId, startTime, length));
        client.sendPacket(MainPacketCreator.skillCooldown(skillId, length > 1000 ? (int) (length / 1000) : (int) length));
    }

    public void removeCooldown(int skillId) {
        if (coolDowns.containsKey(Integer.valueOf(skillId))) {
            coolDowns.remove(Integer.valueOf(skillId));
        }
    }

    public Map<Integer, CoolDownValueHolder> getCooldowns() {
        return coolDowns;
    }

    public void changeCooldown(int skillid, long changetime) {
        if (skillisCooling(skillid)) {
            for (MapleCoolDownValueHolder mcdvh : getAllCooldowns()) {
                if (mcdvh.skillId == skillid) {
                    long startTime = mcdvh.startTime;
                    long length = mcdvh.length;
                    removeCooldown(skillid);
                    addCooldown(skillid, startTime, length + changetime);
                    break;
                }
            }
        }
    }

    public void cancelCooldown(int skillid) {
        removeCooldown(skillid);
        // getClient().getSession().writeAndFlush(MainPacketCreator.skillCooldown(skillid,
        // 0));
    }

    public boolean skillisCooling(int skillId) {
        long now = System.currentTimeMillis();
        if (coolDowns.containsKey(skillId)) {
            System.out.println(coolDowns.get(skillId).length);
            return (coolDowns.get(skillId).startTime + coolDowns.get(skillId).length) >= now;
        }
        return false;
    }

    public void giveCoolDowns(final int skillid, long starttime, long length) {
        addCooldown(skillid, System.currentTimeMillis(), length * 1000);
    }

    public void giveCoolDowns(final List<MapleCoolDownValueHolder> cooldowns) {
        int time;
        if (cooldowns != null) {
            for (MapleCoolDownValueHolder cooldown : cooldowns) {
                time = (int) ((cooldown.length + cooldown.startTime) - System.currentTimeMillis());
                if (time > 0) {
                    addCooldown(cooldown.skillId, System.currentTimeMillis(), time);
                }
            }
        }
    }

    public long getCooldownLimit(int skillid) {
        for (MapleCoolDownValueHolder mcdvh : getAllCooldowns()) {
            if (mcdvh.skillId == skillid) {
                return System.currentTimeMillis() - mcdvh.startTime;
            }
        }
        return 0L;
    }

    public List<MapleCoolDownValueHolder> getAllCooldowns() {
        List<MapleCoolDownValueHolder> ret = new ArrayList<MapleCoolDownValueHolder>();
        for (CoolDownValueHolder mcdvh : coolDowns.values()) {
            ret.add(new MapleCoolDownValueHolder(mcdvh.skillId, mcdvh.startTime, mcdvh.length));
        }
        return ret;
    }

    public final List<MapleDiseaseValueHolder> getAllDiseases() {
        final List<MapleDiseaseValueHolder> ret = new ArrayList<MapleDiseaseValueHolder>(5);

        DiseaseValueHolder vh;
        for (Entry<DiseaseStats, DiseaseValueHolder> disease : diseases.entrySet()) {
            vh = disease.getValue();
            ret.add(new MapleDiseaseValueHolder(disease.getKey(), vh.startTime, vh.length));
        }
        return ret;
    }

    public final boolean hasDisease(final DiseaseStats dis) {
        for (final DiseaseStats disease : diseases.keySet()) {
            if (disease == dis) {
                return true;
            }
        }
        return false;
    }

    public void elementalAdep_fp() {
        elementalAdep--;
        client.getSession().writeAndFlush(UIPacket.showWZEffect("Skill/211.img/skill/2111011/special", 1));
        getMap().broadcastMessage(this, UIPacket.broadcastWZEffect(id, "Skill/211.img/skill/2111011/special", 1),
                false);
        client.getSession().writeAndFlush(UIPacket.showWZEffect("Skill/211.img/skill/2111011/special0", 1));
        getMap().broadcastMessage(this, UIPacket.broadcastWZEffect(id, "Skill/211.img/skill/2111011/special0", 1),
                false);
        client.getSession()
                .writeAndFlush(UIPacket.showWZEffect("Skill/211.img/skill/2111011/count/" + elementalAdep, 1));
        getMap().broadcastMessage(this,
                UIPacket.broadcastWZEffect(id, "Skill/211.img/skill/2111011/count/" + elementalAdep, 1), false);
        getBuffedSkillEffect(BuffStats.StackBuff, 2111011).applyTo(this, getPosition());
        if (elementalAdep == 0) {
            elementalAdep = -1;
            cancelEffectFromBuffStat(BuffStats.StackBuff, 2111011);
        }
    }

    public void SpiritGuard() {
        SpiritGuard--;
        getBuffedSkillEffect(BuffStats.SpiritGuard, 25121209).applyTo(this);
        if (SpiritGuard == 0) {
            cancelEffectFromBuffStat(BuffStats.SpiritGuard, 25121209);
        }
    }

    public void giveDebuff(final DiseaseStats disease, MobSkill skill) {

        if (!hasDisease(disease) && diseases.size() < 2) {
            if (isActiveBuffedValue(2111011)) {
                elementalAdep_fp();
                return;
            }
            if (isActiveBuffedValue(25121209)) {
                SpiritGuard();
                return;
            }
            if (!(disease == DiseaseStats.SEDUCE || disease == DiseaseStats.STUN)) {
                if (isActiveBuffedValue(2321005)) {
                    return;
                }
            }
            BuffTimer.getInstance().schedule(new Runnable() {

                @Override
                public void run() {
                    dispelDebuff(disease);
                }
            }, skill.getDuration());
            diseases.put(disease, new DiseaseValueHolder(System.currentTimeMillis(), skill.getDuration()));
            client.getSession().writeAndFlush(MainPacketCreator.giveDebuff(disease, skill.getX(), skill));
        }
    }

    public final void giveSilentDebuff(final List<MapleDiseaseValueHolder> ld) {
        if (ld != null) {
            for (final MapleDiseaseValueHolder disease : ld) {

                BuffTimer.getInstance().schedule(new Runnable() {

                    @Override
                    public void run() {
                        dispelDebuff(disease.disease);
                    }
                }, (disease.length + disease.startTime) - System.currentTimeMillis());

                diseases.put(disease.disease, new DiseaseValueHolder(disease.startTime, disease.length));
            }
        }
    }

    public void dispelDebuff(DiseaseStats debuff) {
        if (hasDisease(debuff)) {
            client.getSession().writeAndFlush(MainPacketCreator.cancelDebuff(debuff));
            map.broadcastMessage(this, MainPacketCreator.cancelForeignDebuff(id, debuff), false);
            diseases.remove(debuff);
        }
    }

    public void dispelDebuffs() {
        dispelDebuff(DiseaseStats.CURSE);
        dispelDebuff(DiseaseStats.DARKNESS);
        dispelDebuff(DiseaseStats.POISON);
        dispelDebuff(DiseaseStats.SEAL);
        dispelDebuff(DiseaseStats.WEAKEN);
    }

    public void cancelAllDebuffs() {
        diseases.clear();
    }

    public void unlockMaxDamage() {
        SkillFactory.getSkill(1105).getEffect(1).applyTo(this);
        if (isBuckshot()) {
            BuckShot();
        }
        if (isStance()) {
            Stance();
        }
        if (isHolySymbol()) {
            HolySymbol();
        }
        if (isSharpEyes()) {
            SharpEyes();
        }
        if (isPartyBooster()) {
            PartyBooster();
        }
        if (isShadowPartner()) {
            if (GameConstants.isThief(getJob())) {
                ShadowPartner();
            }
        }
        if (isMagicArrow()) {
            MagicArrow();
            Message(7, "[Notice] Sponsored Buff: Purchase a magic arrow and have a 100% chance to fire a magic arrow.");
        }
        if (isTrifleWorm()) {
            TrifleWorm();
            changeSkillLevel(SkillFactory.getSkill(13120003), (byte) 20, (byte) 20);
        }
        if (isNoir()) {
            Noir();
        }
        if (isBling()) {
            Bling();
        }
        if (isKinesis()) {
            Kinesis();
        }
    }

    public boolean isBuckshot() {
        return getKeyValue("rc_shot") != null;
    }

    public void BuckShot() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.BuckShot, 20, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(5321054, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(5321054, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.BuckShot, 20, false)), SkillFactory.getSkill(5321054).getEffect(getSkillLevel(5321054)), null, SkillFactory.getSkill(5321054).getAnimationTime(), getClient().getPlayer()));
    }

    public boolean isStance() {
        return getKeyValue("stance") != null;
    }

    public void Stance() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.Stance, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(80001140, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(80001140, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.Stance, 1, false)), SkillFactory.getSkill(80001140).getEffect(getSkillLevel(80001140)), null, SkillFactory.getSkill(80001140).getAnimationTime(), getClient().getPlayer()));
    }

    public boolean isHolySymbol() {
        return getKeyValue("HolySymbol") != null;
    }

    public void HolySymbol() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.HolySymbol, 20, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(2311003, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(2311003, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.HolySymbol, 20, false)), SkillFactory.getSkill(2311003).getEffect(getSkillLevel(2311003)), null, SkillFactory.getSkill(2311003).getAnimationTime(), getClient().getPlayer()));
    }

    public boolean isSharpEyes() {
        return getKeyValue("SharpEyes") != null;
    }

    public void SharpEyes() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.SharpEyes, 15, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(3121002, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(3121002, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.SharpEyes, 15, false)), SkillFactory.getSkill(3121002).getEffect(getSkillLevel(3121002)), null, SkillFactory.getSkill(3121002).getAnimationTime(), getClient().getPlayer()));
    }

    // CTS_PartyBooster
    public boolean isPartyBooster() {
        return getKeyValue("PartyBooster") != null;
    }

    public void PartyBooster() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PartyBooster, 20, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(5121009, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(5121009, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PartyBooster, 20, false)), SkillFactory.getSkill(5121009).getEffect(getSkillLevel(5121009)), null, SkillFactory.getSkill(5121009).getAnimationTime(), getClient().getPlayer()));
    }

    // 4331002 CTS_ShadowPartner
    public boolean isShadowPartner() {
        return getKeyValue("ShadowPartner") != null;
    }

    public void ShadowPartner() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.ShadowPartner, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(4331002, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(4331002, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.ShadowPartner, 1, false)), SkillFactory.getSkill(4331002).getEffect(getSkillLevel(4331002)), null, SkillFactory.getSkill(4331002).getAnimationTime(), getClient().getPlayer()));
    }

    public boolean isMagicArrow() {
        return getKeyValue("MagicArrow") != null;
    }

    public void MagicArrow() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPRaceEffect, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(3100010, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(3100010, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPRaceEffect, 1, false)), SkillFactory.getSkill(3100010).getEffect(getSkillLevel(3100010)), null, SkillFactory.getSkill(3100010).getAnimationTime(), getClient().getPlayer()));
    }

    // CTS_WindBreakTrifleWorm
    public boolean isTrifleWorm() {
        return getKeyValue("TrifleWorm") != null;
    }

    public void TrifleWorm() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.TriflingWhimOnOff, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(13101022, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(13101022, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.TriflingWhimOnOff, 1, false)), SkillFactory.getSkill(13101022).getEffect(getSkillLevel(13101022)), null, SkillFactory.getSkill(13101022).getAnimationTime(), getClient().getPlayer()));
    }

    public boolean isNoir() {
        return getKeyValue("noir") != null;
    }

    public void Noir() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPInvincible, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(24120002, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(24120002, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPInvincible, 1, false)), SkillFactory.getSkill(24120002).getEffect(getSkillLevel(24120002)), null, SkillFactory.getSkill(24120002).getAnimationTime(), getClient().getPlayer()));
        changeSkillLevel(SkillFactory.getSkill(24120002), (byte) 20, (byte) 20);
    }

    public boolean isBling() {
        return getKeyValue("bling") != null;
    }

    public void Bling() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPRaceEffect, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(24100003, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(24100003, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPRaceEffect, 1, false)), SkillFactory.getSkill(24100003).getEffect(getSkillLevel(24100003)), null, SkillFactory.getSkill(24120002).getAnimationTime(), getClient().getPlayer()));
        changeSkillLevel(SkillFactory.getSkill(24100003), (byte) 20, (byte) 20);
    }

    public boolean isKinesis() {
        return getKeyValue("kinesis") != null;
    }

    public void Kinesis() {
        int overlap_magic = (int) (System.currentTimeMillis() % 1000000000);
        Map<BuffStats, List<StackedSkillEntry>> stacked = getStackSkills();
        for (Triple<BuffStats, Integer, Boolean> statup : Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPScoreBonus, 1, false))) {
            if (statup.getThird()) {
                if (!stacked.containsKey(statup.getFirst())) {
                    stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                }
                stacked.get(statup.getFirst()).add(new StackedSkillEntry(142110011, statup.getSecond(), overlap_magic, Integer.MAX_VALUE));
            }
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.giveBuff(142110011, Integer.MAX_VALUE, Collections.singletonList(new Triple<BuffStats, Integer, Boolean>(BuffStats.PvPScoreBonus, 1, false)), SkillFactory.getSkill(142110011).getEffect(getSkillLevel(142110011)), null, SkillFactory.getSkill(142110011).getAnimationTime(), getClient().getPlayer()));
        changeSkillLevel(SkillFactory.getSkill(142110011), (byte) 20, (byte) 20);
    }

    public void setLevel(final short level) {
        this.level = (short) (level - 1);
    }

    public void sendNote(String to, String msg) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO notes (`to`, `from`, `message`, `timestamp`) VALUES (?, ?, ?, ?)");
            ps.setString(1, to);
            ps.setString(2, getName());
            ps.setString(3, msg);
            ps.setLong(4, System.currentTimeMillis());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to send note" + e);
        }
    }

    public void showNote() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM notes WHERE `to`= ?");
            ps.setString(1, getName());
            ResultSet rs = ps.executeQuery();
            int count = getNoteSize();
            System.out.println(count);
            client.getSession().writeAndFlush(MainPacketCreator.showNotes(rs, count));
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to show note" + e);
        }
    }

    public int getNoteSize() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM notes WHERE `to`= ?");
            ps.setString(1, getName());
            ResultSet rs = ps.executeQuery();
            int ret = 0;
            while (rs.next()) {
                ret++;
            }
            rs.close();
            ps.close();
            con.close();
            return ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void deleteNote(int id) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM notes WHERE `id`=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to delete note" + e);
        }
    }

    public void deleteNote() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM notes WHERE `to`= ?");
            ps.setString(1, getName());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to delete note" + e);
        }
    }

    public void cancelRapidTime(byte type) {
        if (type == 1) {
            this.rapidtimer1 = tools.Timer.MapTimer.getInstance().schedule(new Runnable() {
                public void run() {
                    client.getPlayer().changeSkillLevel(100000277, (byte) 0, (byte) 0);
                }
            }, 20000L);
        } else if (type == 2) {
            this.rapidtimer2 = tools.Timer.MapTimer.getInstance().schedule(new Runnable() {
                public void run() {
                    client.getPlayer().changeSkillLevel(100000277, (byte) 0, (byte) 0);
                }
            }, 20000L);
        }
    }

    public final short getCombo() {
        return combo;
    }

    public void setCombo(final short combo) {
        this.combo = combo;
    }

    public void updateCombo(short combo, long curr) { //아란 콤보
        if (combo > 1000) {
            combo = 1000;
        }
        getClient().getSession().writeAndFlush(MainPacketCreator.AranCombo(combo));
        setLastCombo(curr);
        setCombo(combo);
        switch (combo) {
            case 50:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 50);
                break;
            case 100:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 100);
                break;
            case 150:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 150);
                break;
            case 200:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 200);
                break;
            case 250:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 250);
                break;
            case 300:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 300);
                break;
            case 350:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 350);
                break;
            case 400:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 400);
                break;
            case 450:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 450);
                break;
            case 500:
                SkillFactory.getSkill(21000000).getEffect(getSkillLevel(21000000)).applyTo(this);
                setCombo((short) 500);
                break;
            default:
                if (combo % 1000 == 0) {
                    SkillFactory.getSkill(21110016).getEffect(getSkillLevel(21110016)).applyTo(this);
                }
                break;
        }
    }

    public void useComboSkill(int skill) {
        if (getBuffedValue(BuffStats.ComboUnlimited) == null && getSkillLevel(skill) > 0) {
            SkillStatEffect effect = SkillFactory.getSkill(skill).getEffect(getSkillLevel(skill));
            if (effect != null && effect.getComboCon() > 0) {
                updateCombo((short) (getCombo() - effect.getComboCon()), System.currentTimeMillis());
            }
        }
    }

    public final long getLastCombo() {
        return lastCombo;
    }

    public void setLastCombo(final long combo) {
        this.lastCombo = combo;
    }

    public final long getKeyDownSkill_Time() {
        return keydown_skill;
    }

    public void setKeyDownSkill_Time(final long keydown_skill) {
        this.keydown_skill = keydown_skill;
    }

    public void setChalkboard(String text) {
        this.chalktext = text;
        map.broadcastMessage(CashPacket.useChalkboard(getId(), text));
    }

    public String getChalkboard() {
        return chalktext;
    }

    public MapleMount getMount() {
        return mount;
    }

    public void setWishList(int[] wishlist) {
        this.wishlist = wishlist;
    }

    public int[] getWishlist() {
        return wishlist;
    }

    public void clearWishlist() {
        for (int i = 0; i < 12; i++) {
            wishlist[i] = 0;
        }
    }

    public int getWishlistSize() {
        int ret = 0;
        for (int i = 0; i < 12; i++) {
            if (wishlist[i] > 0) {
                ret++;
            }
        }
        return ret;
    }

    public List<LifeMovementFragment> getLastRes() {
        return lastres;
    }

    public void setLastRes(List<LifeMovementFragment> lastres) {
        this.lastres = lastres;
    }

    public void dropMessage(int type, String message) {
        client.getSession().writeAndFlush(MainPacketCreator.serverNotice(type, message));
    }

    public IMapleCharacterShop getPlayerShop() {
        return playerShop;
    }

    public void setPlayerShop(IMapleCharacterShop playerShop) {
        this.playerShop = playerShop;
    }

    public int getConversation() {
        return inst.get();
    }

    public void setConversation(int inst) {
        this.inst.set(inst);
    }

    public int getSubcategory() {
        if (job >= 430 && job <= 434) {
            return 1; // dont set it
        } else if (job == 501 || (job >= 530 && job <= 532)) {
            return 2;
        }
        if (getKeyValue("dualBlade") == null) {
            return 0;
        }
        if (getKeyValue("dualBlade").equals("1")) {
            return 1;
        }
        return 0;
    }

    public void setSubcategory(int a) {
        subcategory = a;
    }

    public final void clearLinkMids() {
        linkMobIds.clear();
        if (getBuffedValue(BuffStats.ArcaneAim) != null) {
            this.cancelEffectFromBuffStat(BuffStats.ArcaneAim, -1);
        }
    }

    public int getLinkMid(int mob) {
        if (!linkMobIds.containsKey(mob)) {
            return -1;
        }
        return linkMobIds.get(mob);
    }

    public void setLinkMid(int mob, int lm) {
        this.linkMobIds.put(mob, lm);
    }

    public Map<Integer, Integer> getAllLinkMid() {
        return linkMobIds;
    }

    public final void clearDamageMeters() {
        damageMeter.clear();
    }

    public int getDamageMeter(int mob) {
        if (!damageMeter.containsKey(mob)) {
            return -1;
        }
        return damageMeter.get(mob);
    }

    public void setDamageMeter(int mob, int dmg) {
        this.damageMeter.put(mob, dmg);
    }

    public Map<Integer, Integer> getAllDamageMeter() {
        return damageMeter;
    }

    public MapleCashInventory getCashInventory() {
        return cashInv;
    }

    public MapleQuickSlot getQuickSlot() {
        return quickslot;
    }

    public Map<String, String> getCustomValues() {
        return CustomValues;
    }

    public Map<String, Integer> getCustomValues2() {
        return CustomValues2;
    }

    public Map<String, Integer> getCustomValues3() {
        return CustomValues3;
    }

    public String getKeyValue(String key) {
        if (CustomValues.containsKey(key)) {
            return CustomValues.get(key);
        }
        return null;
    }

    public int getDamageHit() {
        return damagehit;
    }

    public void setDamageHit(int hit) {
        this.damagehit = hit;
    }

    public void addDamageHit(int hit) {
        this.damagehit += hit;
    }

    public void loseDamageHit(int hit) {
        this.damagehit -= hit;
    }

    public int getDamageHit2() {
        return damagehit2;
    }

    public void setDamageHit2(int hit) {
        this.damagehit2 = hit;
    }

    public void addDamageHit2(int hit) {
        this.damagehit2 += hit;
    }

    public void loseDamageHit2(int hit) {
        this.damagehit2 -= hit;
    }

    public long getAddDamage() {
        return damage;
    }

    public void gainAddDamage(long dmg) {
        this.damage += dmg;
    }

    public void setAddDamage(long dmg) {
        this.damage = dmg;
    }

    public void loseAddDamage(long dmg) {
        this.damage -= dmg;
    }

    public String getHope() {
        return hope;
    }

    public void setHope(String hope) {
        this.hope = hope;
    }

    public void setKeyValue(String key, String values) {
        if (CustomValues.containsKey(key)) {
            CustomValues.remove(key);
        }
        CustomValues.put(key, values);
        keyvalue_changed = true;
    }

    public int getKeyValue2(String key) {
        if (CustomValues2.containsKey(key)) {
            return CustomValues2.get(key).intValue();
        }
        if (key.contains("hyper")) {
            return 0;
        }
        return -1;
    }

    public void setKeyValue2(String key, int values) {
        if (CustomValues2.containsKey(key)) {
            CustomValues2.remove(key);
        }
        CustomValues2.put(key, values);
        keyvalue_changed = true;
    }

    public int getKeyValue3(String key) {
        if (CustomValues3.containsKey(key)) {
            return CustomValues3.get(key).intValue();
        }
        return -1;
    }

    public void setKeyValue3(String key, int values) {
        if (CustomValues3.containsKey(key)) {
            CustomValues3.remove(key);
        }
        CustomValues3.put(key, values);
        keyvalue_changed = true;
    }

    public boolean isEquippedSoulWeapon() {
        IEquip weapon = (IEquip) getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);
        if (weapon == null) {
            return false;
        }
        return weapon.getSoulEnchanter() != 0;
    }

    public boolean isSoulWeapon(IEquip equip) {
        if (equip == null) {
            return false;
        }
        return equip.getSoulEnchanter() != 0;
    }

    public void equipSoulWeapon(Equip equip) {
        changeSkillLevel(getEquippedSoulSkill(), (byte) -1, (byte) 0);
        changeSkillLevel(equip.getSoulSkill(), (byte) 1, (byte) 1);
        setSoulCount(0);

        getClient().getSession().writeAndFlush(SoulWeaponPacket.giveSoulGauge(getSoulCount(), equip.getSoulSkill()));
    }

    public void unequipSoulWeapon(Equip equip) {
        changeSkillLevel(equip.getSoulSkill(), (byte) -1, (byte) 0);
        setSoulCount(0);
        getClient().getSession().writeAndFlush(SoulWeaponPacket.cancelSoulGauge());
        SkillStatEffect eff = getBuffedSkillEffect(BuffStats.FullSoulMP, -1);
        if (eff != null) {
            cancelEffect(eff, false, getBuffedStarttime(BuffStats.FullSoulMP, eff.getSourceId()));
        }
    }

    public void checkSoulState(boolean useskill, int skillid) {
        SkillStatEffect skill = SkillFactory.getSkill(skillid).getEffect(getSkillLevel(skillid));
        long cooldown = getCooldownLimit(skillid);
        if (useskill) {
            if (getSoulCount() >= skill.getSoulMPCon()) {
                setSoulCount((short) (getSoulCount() - skill.getSoulMPCon()));
                cancelEffect(skill, false, getBuffedStarttime(BuffStats.FullSoulMP, skillid));
                send(SoulWeaponPacket.giveSoulGauge(getSoulCount(), skillid));
                getClient().getSession().writeAndFlush(MainPacketCreator.getInventoryFull());
            }
        } else if ((getSoulCount() >= skill.getSoulMPCon())) {
            if (getBuffedSkillEffect(BuffStats.FullSoulMP) == null) {
                List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                statups.add(new Triple<>(BuffStats.FullSoulMP, 0, false));
                for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                    if (!effects.containsKey(statup.getFirst())) {
                        effects.put(statup.getFirst(), new ArrayList<>());
                    }
                    effects.get(statup.getFirst())
                            .add(new BuffStatsValueHolder(skill, System.currentTimeMillis(), null, statup.getSecond()));
                }
                send(MainPacketCreator.giveBuff(skillid, Integer.MAX_VALUE, statups, skill, stackedEffects, 0, this));
                getMap().broadcastMessage(this, SoulWeaponPacket.showSoulEffect(this, (byte) 1), false);
                getMap().broadcastMessage(this, MainPacketCreator.giveForeignBuff(this, statups), false);
            }
        }
    }

    public int getSoulCount() {
        return this.Soul;
    }

    public void setSoulCount(int soulcount) {
        this.Soul = (soulcount > 1000 ? 1000 : soulcount);
    }

    public void addSoulCount() {
        if (this.Soul < 1000) {
            this.Soul = (short) (this.Soul + 1);
        }
    }

    public int addgetSoulCount() {
        addSoulCount();
        return getSoulCount();
    }

    public int getEquippedSoulSkill() {
        IEquip weapon = (IEquip) getInventory(MapleInventoryType.EQUIPPED).getItem((short) -11);

        return weapon.getSoulSkill();
    }

    public int getSoulSkillMpCon() {
        int skillid = getEquippedSoulSkill();
        SkillStatEffect skill = SkillFactory.getSkill(skillid).getEffect(getSkillLevel(skillid));

        return skill.getSoulMPCon();
    }

    public void reloadChar() {
        client.getPlayer().getClient().getSession().write(MainPacketCreator.getPlayerInfo(client.getPlayer()));
        client.getPlayer().getMap().removePlayer(client.getPlayer());
        client.getPlayer().getMap().addPlayer(client.getPlayer());
    }

    public void saveKeyValues() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM keyvalue WHERE cid = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            for (Entry<String, String> keyset : CustomValues.entrySet()) {
                StringBuilder sb = new StringBuilder("INSERT INTO `keyvalue` VALUES ('");
                sb.append(id);
                sb.append("', '");
                sb.append(keyset.getKey());
                sb.append("', '");
                sb.append(keyset.getValue() == null ? "null" : keyset.getValue());
                sb.append("')");
                ps = con.prepareStatement(sb.toString());
                ps.executeUpdate();
            }
            ps.close();

            ps = con.prepareStatement("DELETE FROM keyvalue2 WHERE cid = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            for (Entry<String, Integer> keyset : CustomValues2.entrySet()) {
                StringBuilder sb = new StringBuilder("INSERT INTO `keyvalue2` VALUES ('");
                sb.append(id);
                sb.append("', '");
                sb.append(keyset.getKey());
                sb.append("', '");
                sb.append(keyset.getValue());
                sb.append("')");
                ps = con.prepareStatement(sb.toString());
                ps.executeUpdate();
            }
            ps.close();

            ps = con.prepareStatement("DELETE FROM akeyvalue WHERE aid = ?");
            ps.setInt(1, this.getAccountID());
            ps.executeUpdate();
            for (Entry<String, Integer> keyset : CustomValues3.entrySet()) {
                StringBuilder sb = new StringBuilder("INSERT INTO `akeyvalue` VALUES ('");
                sb.append(this.getAccountID());
                sb.append("', '");
                sb.append(keyset.getKey());
                sb.append("', '");
                sb.append(keyset.getValue());
                sb.append("')");
                ps = con.prepareStatement(sb.toString());
                ps.executeUpdate();
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[오류] 커스텀 값들을 저장하는데 실패했습니다.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public void loadKeyValues() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM keyvalue WHERE cid = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomValues.put(rs.getString("key"), rs.getString("value").equals("null") ? null : rs.getString("value"));
            }
            ps.close();

            ps = con.prepareStatement("SELECT * FROM keyvalue2 WHERE cid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomValues2.put(rs.getString("key"), rs.getInt("value"));
            }
            ps.close();

            ps = con.prepareStatement("SELECT * FROM akeyvalue WHERE aid = ?");
            ps.setInt(1, this.getAccountID());
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomValues3.put(rs.getString("key"), rs.getInt("value"));
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load custom values.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public void saveSteelSkills() {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM steelskills WHERE cid = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            for (int i = 1; i <= 5; ++i) {
                for (StealSkillEntry sse : steelskills.getSkillEntrys(i)) {
                    ps = con.prepareStatement("INSERT INTO steelskills VALUES (?, ?, ?, ?, ?, ?)");
                    ps.setInt(1, id);
                    ps.setInt(2, sse.getSkillId());
                    ps.setInt(3, sse.getSkillLevel());
                    ps.setInt(4, sse.getJobIndex(sse.getSkillId()));
                    ps.setInt(5, sse.getSlot());
                    ps.setInt(6, sse.isEquipped() ? 1 : 0);
                    ps.executeUpdate();
                }
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[Error] Failed to save steal skill information.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public void loadSteelSkills() {
        steelskills = new PhantomStealSkill();
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM steelskills WHERE cid = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StealSkillEntry sse = new StealSkillEntry(rs.getInt("skillid"), rs.getInt("skilllevel"));
                sse.setSlot(rs.getInt("slot"));
                if (rs.getInt("equipped") == 1) {
                    sse.setEquipped(true);
                }
                steelskills.addSkill(StealSkillEntry.getJobIndex(rs.getInt("skillid")), sse);
                int[] baseskills = {24001001, 24101001, 24111001, 24121001, 24121054};
                for (int i : baseskills) {
                    if (getSkillLevel(i) >= rs.getInt("skilllevel")
                            && getSkillLevel(rs.getInt("skillid")) < rs.getInt("skilllevel")) {
                        changeSkillLevel(SkillFactory.getSkill(rs.getInt("skillid")), (byte) rs.getInt("skilllevel"),
                                (byte) rs.getInt("skilllevel"));
                    }
                }
            }
            ps.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("[Error] Failed to load steal skill information.");
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public PhantomStealSkill getStealSkills() {
        return steelskills;
    }

    public int getEquippedSkillId(int index) {
        int count = 0;
        switch (index) {
            case 1:
            case 2:
                count = 4;
                break;
            case 3:
                count = 3;
                break;
            case 4:
            case 5:
                count = 2;
                break;
        }
        for (int i = 0; i < count; ++i) {
            StealSkillEntry sse = getStealSkills().getSkillEntrys(index).get(i);
            if (sse.isEquipped()) {
                return sse.getSkillId();
            }
        }
        return 1;
    }

    public void send(Object ob) {
        getClient().getSession().writeAndFlush((byte[]) ob);
    }

    public void ea() {
        send(MainPacketCreator.resetActions(this));
    }

    public void forceReAddItem_NoUpdate(IItem item, MapleInventoryType type) {
        getInventory(type).removeSlot(item.getPosition());
        getInventory(type).addFromDB(item);
        client.sendPacket(MainPacketCreator.addInventorySlot(type.equals(MapleInventoryType.EQUIPPED) ? MapleInventoryType.EQUIP : type, item));
    }

    public void forceReAddItem(IItem item, MapleInventoryType type) {
        forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            client.getSession().writeAndFlush(MainPacketCreator.addInventorySlot(MapleInventoryType.EQUIP, item));
        }
    }

    public List<IItem> getRebuyList() {
        if (rebuyList == null) {
            rebuyList = new ArrayList<IItem>();
        }
        return rebuyList;
    }

    public void setQuickMoved(boolean t) {
        quickmoved = t;
    }

    public final boolean getQuickMoved() {
        return quickmoved;
    }

    public final void setGM(int d) {
        setGMLevel((byte) d);
    }

    public void setHeadTitle(int title) {
        this.headtitle = title;
    }

    public int getHeadTitle() {
        return headtitle;
    }

    public Triple<List<MapleRing>, List<MapleRing>, List<MapleRing>> getRings(boolean equip) {
        MapleInventory iv = getInventory(MapleInventoryType.EQUIPPED);
        List<Item> equipped = iv.newList();
        Collections.sort(equipped);
        List<MapleRing> crings = new ArrayList<>(), frings = new ArrayList<>(), mrings = new ArrayList<>();
        MapleRing ring;
        for (IItem ite : equipped) {
            Equip item = (Equip) ite;
            if (item.getRing() != null) {
                ring = item.getRing();
                ring.setEquipped(true);
                if (GameConstants.isEffectRing(item.getItemId())) {
                    if (equip) {
                        if (GameConstants.isCrushRing(item.getItemId())) {
                            crings.add(ring);
                        } else if (GameConstants.isFriendshipRing(item.getItemId())) {
                            frings.add(ring);
                        } else if (GameConstants.isMarriageRing(item.getItemId())) {
                            mrings.add(ring);
                        }
                    } else if (crings.isEmpty() && GameConstants.isCrushRing(item.getItemId())) {
                        crings.add(ring);
                    } else if (frings.isEmpty() && GameConstants.isFriendshipRing(item.getItemId())) {
                        frings.add(ring);
                    } else if (mrings.isEmpty() && GameConstants.isMarriageRing(item.getItemId())) {
                        mrings.add(ring);
                    }
                }
            }
        }
        if (equip) {
            iv = getInventory(MapleInventoryType.EQUIP);
            for (IItem ite : iv.list()) {
                Equip item = (Equip) ite;
                if (item.getRing() != null && GameConstants.isCrushRing(item.getItemId())) {
                    ring = item.getRing();
                    ring.setEquipped(false);
                    if (GameConstants.isFriendshipRing(item.getItemId())) {
                        frings.add(ring);
                    } else if (GameConstants.isCrushRing(item.getItemId())) {
                        crings.add(ring);
                    } else if (GameConstants.isMarriageRing(item.getItemId())) {
                        mrings.add(ring);
                    }
                }
            }
        }
        Collections.sort(frings, new MapleRing.RingComparator());
        Collections.sort(crings, new MapleRing.RingComparator());
        Collections.sort(mrings, new MapleRing.RingComparator());
        return new Triple<>(crings, frings, mrings);
    }

    public void addTrockMap(int type, int map) {
        if (!rocks.containsKey(type)) {
            rocks.put(type, new ArrayList<Integer>());
        }
        rocks.get(type).add(map);
    }

    public List<Integer> getTrockMaps(int type) {
        if (!rocks.containsKey(type)) {
            rocks.put(type, new ArrayList<Integer>());
        }
        return rocks.get(type);
    }

    public Map<Integer, List<Integer>> getTrockMaps() {
        return rocks;
    }

    public void deleteFromTrockMaps(int type, int mapid) {
        List<Integer> maps = rocks.get(type);
        if (maps != null) {
            maps.remove(mapid);
        }
    }

    public void sendPacketTrock(WritingPacket packet) {
        for (Integer i : getTrockMaps(1)) {
            packet.writeInt(i);
        }
        for (int i = getTrockMaps(1).size(); i < 5; i++) {
            packet.writeInt(999999999);
        }
        for (Integer i : getTrockMaps(2)) {
            packet.writeInt(i);
        }
        for (int i = getTrockMaps(2).size(); i < 10; i++) {
            packet.writeInt(999999999);
        }
        for (Integer i : getTrockMaps(3)) {
            packet.writeInt(i);
        }
        for (int i = getTrockMaps(3).size(); i < 13; i++) {
            packet.writeInt(999999999);
        }
    }

    public void sendPacketTrock(WritingPacket packet, int type) {
        if (type == 1) {
            for (Integer i : getTrockMaps(1)) {
                packet.writeInt(i);
            }
            for (int i = getTrockMaps(1).size(); i < 5; i++) {
                packet.writeInt(999999999);
            }
        } else if (type == 2) {
            for (Integer i : getTrockMaps(2)) {
                packet.writeInt(i);
            }
            for (int i = getTrockMaps(2).size(); i < 10; i++) {
                packet.writeInt(999999999);
            }
        } else if (type == 3) {
            for (Integer i : getTrockMaps(3)) {
                packet.writeInt(i);
            }
            for (int i = getTrockMaps(3).size(); i < 13; i++) {
                packet.writeInt(999999999);
            }
        }
    }

    public void addRewardDB(int cid, int itemid, int quantity) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO rewardsaves VALUES (NULL, ?, ?, ?)");
            ps.setInt(1, cid);
            ps.setInt(2, itemid);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public List<Pair<Integer, Integer>> getRewardDB() {
        List<Pair<Integer, Integer>> rewards = new ArrayList<Pair<Integer, Integer>>();
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM `rewardsaves` WHERE `cid` = ?");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rewards.add(new Pair(rs.getInt("itemid"), rs.getInt("quantity")));
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        return rewards;

    }

    public void removeRewardsDB(int selection, int quantity, int cid) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("DELETE FROM rewardsaves WHERE itemid = ? AND quantity = ? AND cid = ? LIMIT 1");
            ps.setInt(1, selection);
            ps.setInt(2, quantity);
            ps.setInt(3, cid);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            if (!ServerConstants.realese) {
                ex.printStackTrace();
            }
        }
    }

    public String printRewardsSaves() {
        String str = "";
        int o = 0;
        for (Pair<Integer, Integer> d : getRewardDB()) {
            str += "#L" + (o++) + "#" + "#z" + d.getLeft() + "# " + d.getRight() + "개#l\r\n";
        }
        return str;
    }

    public int[] getRewardsSavedItem(int what) {
        int po = 0;
        int[] items = {0, 0};
        for (Pair<Integer, Integer> d : getRewardDB()) {
            if (what == po) {
                items[0] = d.getLeft();
                items[1] = d.getRight();
                break;
            }
            po++;
        }
        return items;
    }

    public void gainMedalReward(int item) {
        send(UIPacket.showInfo("<" + ItemInformation.getInstance().getName(item) + "> Earned a medal!"));
        message(5, ("<" + ItemInformation.getInstance().getName(item)) + "> Earned a medal.");
        gainItem(item, (short) 1, false, -1, "Auto Medal Quest Completion Medal.");
    }

    public List<MapleSummon> getMines() {
        return mines;
    }

    public void addCardStack(int amount) {
        if (this.getSkillLevel(24120002) > 0) {
            this.cardStack = Math.min(40, amount + getCardStack());
        } else {
            this.cardStack = Math.min(20, amount + getCardStack());
        }
        send(MainPacketCreator.cardAmount(getCardStack()));
    }

    public void setCardStack(int amount) {
        this.cardStack = amount;
    }

    public int getCardStack() {
        return cardStack;
    }

    public int addCardStackRunningId() {
        return ++cardStackRunningId;
    }

    public List<Integer> getProfessions() {
        List<Integer> prof = new ArrayList<>();
        for (int i = 9200; i <= 9204; i++) {
            if (getProfessionLevel(id * 10000) > 0) {
                prof.add(i);
            }
        }
        return prof;
    }

    public MapleProfession getProfession() {
        return profession;
    }

    public int getGatherToolPosition(MapleProfessionType type) {
        if (type == MapleProfessionType.HERBALISM) {
            // 1502000
            for (IItem i : getInventory(MapleInventoryType.EQUIP).list()) {
                if (i.getItemId() / 10000 == 150) {
                    return i.getPosition();
                }
            }
        } else if (type == MapleProfessionType.MINING) {
            // 1512000
            for (IItem i : getInventory(MapleInventoryType.EQUIP).list()) {
                if (i.getItemId() / 10000 == 151) {
                    return i.getPosition();
                }
            }
        }
        return 0;
    }

    public int getGatherTool(MapleProfessionType type) {
        if (type == MapleProfessionType.HERBALISM) {
            // 1502000
            for (IItem i : getInventory(MapleInventoryType.EQUIP).list()) {
                if (i.getItemId() / 10000 == 150) {
                    return i.getItemId();
                }
            }
        } else if (type == MapleProfessionType.MINING) {
            // 1512000
            for (IItem i : getInventory(MapleInventoryType.EQUIP).list()) {
                if (i.getItemId() / 10000 == 151) {
                    return i.getItemId();
                }
            }
        }
        return 0;
    }

    // ret.getStat().setAmbition(rs.getInt("ambition"));
    // ret.getStat().setCharm(rs.getInt("charm"));
    // ret.getStat().setDiligence(rs.getInt("diligence"));
    // ret.getStat().setEmpathy(rs.getInt("empathy"));
    // ret.getStat().setInsight(rs.getInt("insight"));
    // ret.getStat().setWillPower(rs.getInt("willpower"));
    /*
	 * 0 : 카리스마 1 : 통찰력 2 : 의지 3 : 손재주 4 : 감성 5 : 매력
     */
    public void addAmbition(int amount) {
        addCharisma(amount);
    }

    public void addCharisma(int amount) {
        boolean limited = false;
        if (getTodayCharisma() >= 500) {
            limited = true;
        } else {
            if (getTodayCharisma() + amount > 500) {
                amount = 500 - getTodayCharisma();
            }
            getStat().setAmbition(getStat().getAmbition() + amount);
            updateSingleStat(PlayerStatList.CHARISMA, getStat().getAmbition() + amount);
            addTodayCharisma(amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 0, limited));
    }

    public final short getTodayCharisma() {
        if (getKeyValue("Today_Charisma") == null) {
            setKeyValue("Today_Charisma", "0");
        }
        return Short.parseShort(getKeyValue("Today_Charisma"));
    }

    public final void addTodayCharisma(int amount) {
        setKeyValue("Today_Charisma", Math.min((getTodayCharisma() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public void addCharm(int amount) {
        boolean limited = false;
        if (getTodayCharm() >= 5000) {
            limited = true;
        } else {
            if (getTodayCharm() + amount > 5000) {
                amount = 5000 - getTodayCharm();
            }
            getStat().setCharm(getStat().getCharm() + amount);
            addTodayCharm(amount);
            updateSingleStat(PlayerStatList.CHARM, getStat().getCharm() + amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 5, limited));
    }

    public final short getTodayCharm() {
        if (getKeyValue("Today_Charm") == null) {
            setKeyValue("Today_Charm", "0");
        }
        return Short.parseShort(getKeyValue("Today_Charm"));
    }

    public final void addTodayCharm(int amount) {
        setKeyValue("Today_Charm", Math.min((getTodayCharm() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public void addDiligence(int amount) {
        boolean limited = false;
        if (getTodayDiligence() >= 500) {
            limited = true;
        } else {
            if (getTodayDiligence() + amount > 500) {
                amount = 500 - getTodayDiligence();
            }
            getStat().setDiligence(getStat().getDiligence() + amount);
            addTodayDiligence(amount);
            updateSingleStat(PlayerStatList.CRAFT, getStat().getDiligence() + amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 3, limited));
    }

    public final short getTodayDiligence() {
        if (getKeyValue("Today_Diligence") == null) {
            setKeyValue("Today_Diligence", "0");
        }
        return Short.parseShort(getKeyValue("Today_Diligence"));
    }

    public final void addTodayDiligence(int amount) {
        setKeyValue("Today_Diligence", Math.min((getTodayDiligence() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public void addEmpathy(int amount) {
        boolean limited = false;
        if (getTodayEmpathy() >= 500) {
            limited = true;
        } else {
            if (getTodayEmpathy() + amount > 500) {
                amount = 500 - getTodayEmpathy();
            }
            getStat().setEmpathy(getStat().getEmpathy() + amount);
            addTodayEmpathy(amount);
            updateSingleStat(PlayerStatList.SENSE, getStat().getEmpathy() + amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 4, limited));
    }

    public final short getTodayEmpathy() {
        if (getKeyValue("Today_Empathy") == null) {
            setKeyValue("Today_Empathy", "0");
        }
        return Short.parseShort(getKeyValue("Today_Empathy"));
    }

    public final void addTodayEmpathy(int amount) {
        setKeyValue("Today_Empathy", Math.min((getTodayEmpathy() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public void addInsight(int amount) {
        boolean limited = false;
        if (getTodayInsight() >= 500) {
            limited = true;
        } else {
            if (getTodayInsight() + amount > 500) {
                amount = 500 - getTodayInsight();
            }
            getStat().setInsight(getStat().getInsight() + amount);
            updateSingleStat(PlayerStatList.INSIGHT, getStat().getInsight() + amount);
            addTodayInsight(amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 1, limited));
    }

    public final short getTodayInsight() {
        if (getKeyValue("Today_Insight") == null) {
            setKeyValue("Today_Insight", "0");
        }
        return Short.parseShort(getKeyValue("Today_Insight"));
    }

    public final void addTodayInsight(int amount) {
        setKeyValue("Today_Insight", Math.min((getTodayInsight() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public void addWillPower(int amount) {
        boolean limited = false;
        if (getTodayWillPower() >= 500) {
            limited = true;
        } else {
            if (getTodayWillPower() + amount > 500) {
                amount = 500 - getTodayWillPower();
            }
            getStat().setWillPower(getStat().getWillPower() + amount);
            addTodayWillPower(amount);
            updateSingleStat(PlayerStatList.WILLPOWER, getStat().getWillPower() + amount);
        }
        send(MainPacketCreator.GainEXP_Trait(amount, 2, limited));
    }

    public final short getTodayWillPower() {
        if (getKeyValue("Today_WillPower") == null) {
            setKeyValue("Today_WillPower", "0");
        }
        return Short.parseShort(getKeyValue("Today_WillPower"));
    }

    public final void addTodayWillPower(int amount) {
        setKeyValue("Today_WillPower", Math.min((getTodayWillPower() + amount), 500) + "");
        send(MainPacketCreator.updateTodayTrait(this));
    }

    public final String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(Calendar.getInstance().getTime());
        return time;
    }

    public final void updateToday() {
        try {
            if (getKeyValue("Today_TraitLimit") == null) {
                setKeyValue("Today_TraitLimit", getToday());
            }
            if (!getToday().equals(getKeyValue("Today_TraitLimit"))) {
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date z = df.parse(getKeyValue("Today_TraitLimit"));
                if (Calendar.getInstance().getTime().after(z)) {
                    setKeyValue("Today_TraitLimit", getToday());
                    setKeyValue("Today_WillPower", "0");
                    setKeyValue("Today_Insight", "0");
                    setKeyValue("Today_Empathy", "0");
                    setKeyValue("Today_Diligence", "0");
                    setKeyValue("Today_Charm", "0");
                    setKeyValue("Today_Charisma", "0");
                    setKeyValue("Today_EnergyDrink", "0");
                    setKeyValue("Today_Check", null);
                    getProfession().setFatigue(0);
                    setArtifactPoints(0);
                    // updateSingleStat(PlayerStat.FATIGUE, 0);
                }

            }
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public MapleExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(MapleExtractor me) {
        removeExtractor();
        this.extractor = me;
    }

    public void removeExtractor() {
        if (extractor != null) {
            map.broadcastMessage(MainPacketCreator.removeExtractor(this.id));
            map.removeMapObject(extractor);
            extractor = null;
        }
    }

    public void setHide(boolean h) {
        if (h == true) {
            this.hidden = true;
            map.broadcastMessage(this, MainPacketCreator.removePlayerFromMap(getId()), false);
        } else {
            this.hidden = false;
            for (MapleMapObject objs : map.getAllPlayer()) {
                MapleCharacter chr = (MapleCharacter) objs;
                if (chr.getId() != getId()) {
                    sendSpawnData(chr.getClient());
                }
            }
        }
    }

    public void checkBossMapOut() {
        MapleWorldMapProvider fac = getClient().getChannelServer().getMapFactory();
        if (GameConstants.getBossReturnMap(getMapId()) == -1) {
            return;
        }
        changeMap(fac.getMap(GameConstants.getBossReturnMap(getMapId())),
                fac.getMap(GameConstants.getBossReturnMap(getMapId())).getPortal(0));
    }

    public void setSkillEffect(SkillEffectEntry eff) {
        this.skilleffects = eff;
    }

    public SkillEffectEntry getSkillEffect() {
        return skilleffects;
    }

    public void updateOneInfoQuest(int questid, String key, String value) {
        String allValues = getInfoQuest(questid);
        if (!allValues.equals("")) {
            Map<String, String> values = new HashMap<String, String>();
            String[] keyvalues = allValues.split(";");
            for (int i = 0; i < keyvalues.length; i++) {
                String[] keyandvalue = keyvalues[i].split("=");
                values.put(keyandvalue[0], keyandvalue[1]);
            }
            if (values.containsKey(key)) {
                values.remove(key);
            }
            values.put(key, value);
            allValues = "";
            int size = 1;
            for (Entry<String, String> e : values.entrySet()) {
                allValues += e.getKey() + "=" + e.getValue();
                if (size < values.size()) {
                    allValues += ";";
                }
                size++;
            }
        } else {
            allValues = key + "=" + value;
        }
        updateInfoQuest(questid, allValues);
    }

    public String getOneInfoQuest(int questid, String key) {

        String allValues = getInfoQuest(questid);
        if (!allValues.equals("")) {
            Map<String, String> values = new HashMap<String, String>();
            String[] keyvalues = allValues.split(";");
            for (int i = 0; i < keyvalues.length; i++) {
                String[] keyandvalue = keyvalues[i].split("=");
                values.put(keyandvalue[0], keyandvalue[1]);
            }
            if (values.containsKey(key)) {
                return values.get(key);
            }
        }
        return "";
    }

    public void addInnerExp(int amount) {
        if (getInnerExp() + amount >= Integer.MAX_VALUE) {
            setInnerExp(Integer.MAX_VALUE);
        } else {
            setInnerExp(getInnerExp() + amount);
        }
        send(MainPacketCreator.updateInnerExp(getInnerExp()));
    }

    public int getInnerNextExp() {
        if (getInnerLevel() == 0) {
            return 0;
        }
        return (getInnerLevel() + 1) * 500;
    }

    public void setInnerAbility(int level) {
        if (level >= 30) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(0, -1);
            getInnerSkills().add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getSkillLevel());
        } else if (level >= 60) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(0, 2), -1);
            getInnerSkills().add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getSkillLevel());
        } else if (level >= 100) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(1, 3), -1);
            getInnerSkills().add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getSkillLevel());
        }
    }

    public static int[][] getZeroSkillList() {
        final int skilllist[][] = {{101101100, 105}, {100001261, 110}, {101101200, 110}, {101100100, 110}, {101110205, 110}, {101100102, 110}, {101100200, 115}, {101111100, 115}, {100001274, 120}, {101111200, 120}, {101110101, 125}, {101110202, 130}, {101110103, 130}, {101100203, 130}, {101121100, 135}, {100001272, 140}, {101121200, 140}, {101120100, 140}, {101120201, 145}, {101120102, 145}, {101120202, 150}, {101100201, 150}, {101100101, 155}, {101110200, 160}, {101120207, 160}, {101120109, 165}, {101110102, 165}, {100001283, 170}, {101110203, 170}, {101120104, 170}, {101120204, 180}, {101120110, 185}, {100001005, 200}};
        return skilllist;
    }

    public void zeroSkillLevel() {
        int skill[][] = getZeroSkillList();
        for (int i = 0; i < skill.length; i++) {
            if (level >= skill[i][1]) {
                if (getSkillLevel(skill[i][0]) <= 0) {
                    changeSkillLevel(skill[i][0], (byte) SkillFactory.getSkill(skill[i][0]).getMaxLevel(), (byte) SkillFactory.getSkill(skill[i][0]).getMaxLevel());
                }
            }
        }
    }

    public void innerLevelUp() {
        if (getInnerLevel() == 1) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(0, -1);
            innerSkills.add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getMaxLevel());
            send(MainPacketCreator.updateInnerAbility(isvh, 1, false));
        } else if (getInnerLevel() == 2) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(0, 2), -1);
            innerSkills.add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getMaxLevel());
            send(MainPacketCreator.updateInnerAbility(isvh, 2, false));
        } else if (getInnerLevel() >= 3) {
            InnerSkillValueHolder isvh = InnerAbillity.getInstance().renewSkill(Randomizer.rand(1, 3), -1);
            innerSkills.add(isvh);
            changeSkillLevel(SkillFactory.getSkill(isvh.getSkillId()), isvh.getSkillLevel(), isvh.getMaxLevel());
            send(MainPacketCreator.updateInnerAbility(isvh, 3, true));
        }

        setInnerLevel(getInnerLevel() + 1);
    }

    public void makeNewAswanShop() {
        aswanShopList = new MapleShop(100000000 + getId(), 2182002);

        int itemid = GameConstants.getAswanRecipes()[(int) Math.floor(Math.random() * GameConstants.getAswanRecipes().length)];
        aswanShopList.addItem(new MapleShopItem((short) 1, itemid, 4310038, 70, (byte) 0, 1, 0, 0));
        itemid = GameConstants.getAswanScrolls()[(int) Math.floor(Math.random() * GameConstants.getAswanScrolls().length)];
        aswanShopList.addItem(new MapleShopItem((short) 1, itemid, 4310036, 15, (byte) 0, 1, 0, 1));
        itemid = GameConstants.getAswanScrolls()[(int) Math.floor(Math.random() * GameConstants.getAswanScrolls().length)];
        aswanShopList.addItem(new MapleShopItem((short) 1, itemid, 4310036, 15, (byte) 0, 1, 0, 2));
        itemid = (Integer) GameConstants.getUseItems()[(int) Math.floor(Math.random() * GameConstants.getUseItems().length)].getLeft();
        int price = (Integer) GameConstants.getUseItems()[(int) Math.floor(Math.random() * GameConstants.getUseItems().length)].getRight();
        aswanShopList.addItem(new MapleShopItem((short) 1, itemid, price, 0, (byte) 0, 1, 0, 3));
        itemid = GameConstants.getCirculators()[(int) Math.floor(Math.random() * GameConstants.getCirculators().length)];
        price = InnerAbillity.getInstance().getCirculatorRank(itemid) * 10;
        if (InnerAbillity.getInstance().getCirculatorRank(itemid) > 3) {
            price *= 2;
        }
        aswanShopList.addItem(new MapleShopItem((short) 1, itemid, InnerAbillity.getInstance().getCirculatorRank(itemid) > 5 ? 4310038 : 4310036, price, (byte) 0, 1, 0, 0));

        Message(1, "Whipiel's Whisper: You defeated the remnants of Hilla in Aswan? I've brought in a new item, so come see me. Maybe you need something.~");
    }

    public MapleShop getAswanShop() {
        return aswanShopList;
    }

    public void openAswanShop() {
        if (aswanShopList == null) {
            MapleShopFactory.getInstance().getShop(2182002).sendShop(client);
        } else {
            getAswanShop().sendShop(client);
        }
    }

    public Map<BuffStats, List<StackedSkillEntry>> getStackSkills() {
        return stackedEffects;
    }

    public MapleCarnivalParty getCarnivalParty() {
        return carnivalParty;
    }

    public void setCarnivalParty(MapleCarnivalParty party) {
        carnivalParty = party;
    }

    public void setAndroid(MapleAndroid and) {
        this.android = and;
        if (map != null && and != null) { // Set
            android.setStance(0);
            android.setPosition(getPosition());
            map.broadcastMessage(this, AndroidPacket.spawnAndroid(this, android), true);
            map.broadcastMessage(this, AndroidPacket.showAndroidEmotion(this.getId(), Randomizer.nextInt(17) + 1),
                    true);
        } else if (map != null && and == null) { // Remove
            map.broadcastMessage(this, AndroidPacket.deactivateAndroid(this.getId()), true);
        }
    }

    public void updateAndroid() {
        if (map != null && android != null) { // Set
            map.broadcastMessage(this, AndroidPacket.spawnAndroid(this, android), true);
        } else if (map != null && android == null) { // Remove
            map.broadcastMessage(this, AndroidPacket.deactivateAndroid(this.getId()), true);
        }
    }

    public MapleAndroid getAndroid() {
        return android;
    }

    public void removeAndroid() {
        setAndroid(null);
    }

    public List<Integer> getExtendedSlots() {
        return extendedSlots;
    }

    public int getExtendedSlot(int index) {
        if (extendedSlots.size() <= index || index < 0) {
            return -1;
        }
        return extendedSlots.get(index);
    }

    public boolean hasBlockedInventory() {
        return !isAlive() || getTrade() != null || getConversation() > 0 || getPlayerShop() != null || map == null;
    }

    public int addPlusOfGlassCTS_Morph(int amount) {
        glass_plusCTS_Morph += amount;
        if (glass_plusCTS_Morph >= 10000) {
            glass_plusCTS_Morph = 10000;
        }
        return glass_plusCTS_Morph;
    }

    public int addMinusOfGlassCTS_Morph(int amount) {
        glass_minusCTS_Morph -= amount;
        if (glass_minusCTS_Morph <= 1) {
            glass_minusCTS_Morph = 1;
        }

        return glass_minusCTS_Morph;
    }

    public int getPlusOfGlassCTS_Morph() {
        return glass_plusCTS_Morph;
    }

    public int getMinusOfGlassCTS_Morph() {
        return glass_minusCTS_Morph;
    }

    public boolean isEquilibrium() {
        if (getBuffedValue(BuffStats.Larkness) != null) {
            if (getBuffedValue(BuffStats.Larkness) == 20040219 || getBuffedValue(BuffStats.Larkness) == 20040220) {
                return true;
            }
        }
        return false;
    }

    public final void getSunfireBuffedValue(int skillid, int attackSkill, Integer Gauge) {
        final ISkill sunfireid = SkillFactory.getSkill(skillid);
        final byte skilllevel = getSkillLevel(sunfireid);
        if (skilllevel > 0) {
            final SkillStatEffect sunfireBuff = sunfireid.getEffect(skilllevel);
            if (getBuffedValue(BuffStats.Larkness) == null || getBuffedValue(BuffStats.Larkness) == 20040216) {
                sunfireBuff.applySunfireBuff(this, true, attackSkill);
            }
            if (getMinusOfGlassCTS_Morph() <= 1 && getBuffedValue(BuffStats.Larkness) == 20040216) {
                final SkillStatEffect Equili = SkillFactory.getSkill(20040219).getEffect(1);
                Equili.applyequilibriumBuff(this, true);
            }
        } else {
            this.changeSkillLevel(sunfireid, (byte) sunfireid.getMaxLevel(), (byte) sunfireid.getMasterLevel());
            this.getSunfireBuffedValue(skillid, attackSkill, Gauge);
        }
    }

    public void giveEqilibriumbuff() {
        final SkillStatEffect Equili = SkillFactory.getSkill(20040219).getEffect(1);
        Equili.applyequilibriumBuff(this, true);
    }

    public final void getEclipseBuffedValue(int skillid, int attackSkill, Integer Gauge) {
        final ISkill eclipseid = SkillFactory.getSkill(skillid);
        final byte skilllevel = getSkillLevel(eclipseid);
        if (skilllevel > 0) {
            final SkillStatEffect eclipseBuff = eclipseid.getEffect(skilllevel);
            if (getBuffedValue(BuffStats.Larkness) == null || getBuffedValue(BuffStats.Larkness) == 20040217) {
                eclipseBuff.applyEclipseBuff(this, true, attackSkill);
            }
            if (getPlusOfGlassCTS_Morph() >= 10000 && getBuffedValue(BuffStats.Larkness) == 20040217) {
                final SkillStatEffect Equili = SkillFactory.getSkill(20040220).getEffect(1);
                Equili.applyequilibriumBuff(this, false);
            }
        } else {
            this.changeSkillLevel(eclipseid, (byte) eclipseid.getMaxLevel(), (byte) eclipseid.getMasterLevel());
            this.getEclipseBuffedValue(skillid, attackSkill, Gauge);
        }
    }

    public void mastery4thJobSkills(MapleCharacter player, int jobId) { // The master level of the fourth job is paid as a base 10.
        MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz"))
                .getData(StringUtil.getLeftPaddedStr("" + jobId, '0', 3) + ".img");
        for (MapleData skill : data) {
            if (skill != null) {
                for (MapleData skillId : skill.getChildren()) {
                    if (!skillId.getName().equals("icon")) {
                        if (MapleDataTool.getIntConvert("invisible", skillId, 0) == 0) { // Invisible skills are not posted
                            player.changeSkillLevel(SkillFactory.getSkill(Integer.parseInt(skillId.getName())),
                                    (byte) 0, (byte) 10);
                        }
                    }
                }
            }
        }
    }

    public void startQuest(int id, int npcid) {
        try {
            MapleQuest.getInstance(id).forceStart(this, npcid, "");
        } catch (NullPointerException ex) {

        }
    }

    public void completeQuest(int id, int npcid) {
        try {
            MapleQuest.getInstance(id).forceComplete(this, npcid);
        } catch (NullPointerException ex) {

        }
    }

    private final MapleMap getWarpMap(final int map) {
        if (getEventInstance() != null) {
            return getEventInstance().getMapFactory().getMap(map);
        }
        return ChannelServer.getInstance(client.getChannel()).getMapFactory().getMap(map);
    }

    public final void warp(final int map) {
        final MapleMap mapz = getWarpMap(map);
        changeMap(mapz, mapz.getPortalSP().get(Randomizer.nextInt(mapz.getPortalSP().size())));
    }

    public static final void crossChannelWarp(final MapleClient c, final int map, final byte channel) {
        String IP = null;
        IP = ServerConstants.getServerHost(c);

        MapleCharacter chr = c.getPlayer();
        MapleMap target = c.getChannelServer().getMapFactory().getMap(map);
        {
            if (chr.getTrade() != null) {
                MapleUserTrade.cancelTrade(chr.getTrade());
            }
            if (chr.getPets() != null) {
                chr.unequipAllPets();
            }
            final IMapleCharacterShop shop = chr.getPlayerShop();
            if (shop != null) {
                shop.removeVisitor(chr);
                if (shop.isOwner(chr)) {
                    shop.setOpen(true);
                }
            }
        }
        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());
        if (chr.getMessenger() != null) {
            WorldCommunity.silentLeaveMessenger(chr.getMessenger().getId(), new MapleMultiChatCharacter(chr));
        }
        for (final Entry<BuffStats, List<BuffStatsValueHolder>> effects : chr.getEffects().entrySet()) {
            for (BuffStatsValueHolder cancelEffectCancelTasks : effects.getValue()) {

                if (cancelEffectCancelTasks.schedule != null) {
                    cancelEffectCancelTasks.schedule.cancel(false);
                }
            }
        }
        // ChannelServer.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
        ChannelServer.addCooldownsToStorage(chr.getId(), chr.getAllCooldowns());
        ChannelServer.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        ChannelServer.ChannelChange_Data(new ChracterTransfer(chr), chr.getId(), channel);
        chr.setMap(target);
        chr.setMap(target.getId());
        chr.saveToDB(false, false);
        ch.removePlayer(chr);
        chr.getMap().removePlayer(chr);
        c.setPlayer(null);
        c.updateLoginState(MapleClient.CHANGE_CHANNEL, c.getSessionIPAddress());
        c.getSession().writeAndFlush(MainPacketCreator.getChannelChange(ServerConstants.basePorts + (channel), IP));
    }

    public void setLastCC(long d) {
        lastChannelChange = d;
    }

    public long getLastCC() {
        return lastChannelChange;
    }

    public void skillReset() {
        List<ISkill> skillss = new ArrayList<ISkill>();
        for (ISkill skill : skills.keySet()) {
            for (InnerSkillValueHolder inner : innerSkills) {
                if (skill.getId() == inner.getSkillId()) {
                    continue;
                }
            }
            skillss.add(skill);
        }
        for (ISkill i : skillss) {
            changeSkillLevel(i, (byte) 0, (byte) 0);
        }
    }

    public void retrieveLinkBless() {
        int blessOfFairy = 12;
        blessOfFairy += GameConstants.getBeginnerJobCode(job) * 10000;
        int blessOfEmpress = 73;
        blessOfEmpress += GameConstants.getBeginnerJobCode(job) * 10000;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT `level`, `name` FROM `characters` WHERE `accountid` = ? AND (`job` DIV 1000 != 1) AND (`job` DIV 1000 != 5) AND `name` NOT LIKE ? ORDER BY `level` DESC");
            ps.setInt(1, accountid);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            this.BlessOfFairy_Origin = null;
            if (rs.next()) {
                int skillLevel = 0;
                skillLevel = rs.getInt("level") / 10;
                BlessOfFairy_Origin = rs.getString("name");
                skills.put(SkillFactory.getSkill(blessOfFairy), new SkillEntry((byte) skillLevel, (byte) 0, -1));
            }
            ps.close();
            rs.close();

            ps = con.prepareStatement(
                    "SELECT `level`, `name` FROM `characters` WHERE `accountid` = ? AND ((`job` DIV 1000 = 1) OR (`job` DIV 1000 = 5)) AND `name` NOT LIKE ? ORDER BY `level` DESC");
            ps.setInt(1, accountid);
            ps.setString(2, name);
            rs = ps.executeQuery();
            BlessOfEmpress_Origin = null;
            if (rs.next()) {
                int skillLevel = 0;
                skillLevel = rs.getInt("level") / 5;
                BlessOfEmpress_Origin = rs.getString("name");
                int saintSpirit = 24 + getSkillLevel(GameConstants.getBeginnerJobCode(job) * 10000 + 202);
                skillLevel = Math.min(skillLevel, saintSpirit);
                skills.put(SkillFactory.getSkill(blessOfEmpress), new SkillEntry((byte) skillLevel, (byte) 0, -1));
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    public static void loadItemPot(int charid) {
        MapleItempotMain.getInstance().saveToDB(charid);
    }

    public boolean haveItem(int itemid) {
        return getItemQuantity(itemid, false) > 0;
    }

    public byte getBlessOfDark() {
        return blessOfDarkness;
    }

    public void setBlessOfDark(byte count) {
        blessOfDarkness = count;
    }

    public void giveSurPlus(int surplus) {
        int MaxSurPlus = 0;
        SurPlus += surplus;
        switch (getJob()) {
            case 3600:
                MaxSurPlus = 5;
                break;
            case 3610:
                MaxSurPlus = 10;
                break;
            case 3611:
                MaxSurPlus = 15;
                break;
            case 3612:
                if (isActiveBuffedValue(400041029)) {
                    MaxSurPlus = 40;
                } else {
                    MaxSurPlus = 20;
                }

                break;
        }
        if (SurPlus < 0) {
            SurPlus = 0;
        }
        if (MaxSurPlus < SurPlus) {
            SurPlus = MaxSurPlus;
        }
        send(MainPacketCreator.giveSurPlus(SurPlus));
    }

    public void giveBulletGauge(int skillid, boolean CloseR) {
        SkillStatEffect effect = null;
        byte MaxBulletGauge = 0, MaxCylinderGauge = 0;
        boolean isRevolving = (this.Bullet <= 0) || (skillid == 37000010);
        switch (getJob()) {
            case 3700:
                MaxBulletGauge = 3;
                MaxCylinderGauge = 3;
                break;
            case 3710:
                MaxBulletGauge = 4;
                MaxCylinderGauge = 4;
                break;
            case 3711:
                MaxBulletGauge = 5;
                MaxCylinderGauge = 5;
                break;
            case 3712:
                MaxBulletGauge = 6;
                MaxCylinderGauge = 6;
                break;
        }
        if (CloseR) {
            BULLET_SKILL_ID = skillid;
            lastBulletUsedTime = System.currentTimeMillis();
        }
        /* Start of increase and decrease condition */
        switch (skillid) {
            case 37001001: //When using linked skills
            case 37000009:
            case 37100008:
                if ((BULLET_SKILL_ID != 0) && ((System.currentTimeMillis() - lastBulletUsedTime) < 650)) { //Combined skill and Revolving Cannon simultaneously.
                    BULLET_SKILL_ID = 0;
                    lastBulletUsedTime = 0;
                    Cylinder++;
                } else {
                    BULLET_SKILL_ID = 0;
                    lastBulletUsedTime = 0;
                    return;
                }
            case 37001004: //When using an explosion move
                Bullet -= 1;
                break;
            case 37001002: //When using the release file bunker,
            case 37000008:
            case 37000011:
            case 37000012:
            case 37000013:
                effect = SkillFactory.getSkill(skillid).getEffect(this.getSkillLevel(skillid));
                effect.applyTo(this); //Over buff applied !
                Cylinder = 0;
                break;
        }
        /* End increase and decrease condition */
        if (isRevolving || Bullet <= 0) {
            send(MainPacketCreator.giveBulletGauge(skillid, (byte) this.Bullet, (byte) this.Cylinder)); //Fixed a bug that caused reload animations to fai.
            Bullet = MaxBulletGauge;
            isRevolving = true;
        }
        if (MaxCylinderGauge < Cylinder) {
            Cylinder = MaxCylinderGauge;
        }
        send(MainPacketCreator.giveBulletGauge(isRevolving ? 37000010 : skillid, (byte) this.Bullet, (byte) this.Cylinder));
    }

    public void givePPoint(SkillStatEffect effects, boolean boss) {
        if (effects == null) {
            return;
        }
        int MaxPPoint = 30;
        if (effects != null) {
            if (effects.getPPCon() > 0) {
                if (isActiveBuffedValue(142121032)) {
                    PPoint -= effects.getPPCon() / 2;
                } else {
                    PPoint -= effects.getPPCon();
                }
            }
            if (effects.getPPRecovery() > 0) {
                if (getSkillLevel(142120033) > 0) {
                    if (boss) {
                        PPoint += effects.getPPRecovery() + 1;
                    } else {
                        PPoint += effects.getPPRecovery();
                    }
                } else {
                    PPoint += effects.getPPRecovery();
                }
            }
            if (effects.getSourceId() == 142121005) {
                PPoint -= effects.getW();
            }
        }
        if (PPoint < 0) {
            PPoint = 0;
        }
        if (MaxPPoint < PPoint) {
            PPoint = MaxPPoint;
        }
        if (effects != null) {
            if (effects.getSourceId() == 142121008 || effects.getSourceId() == 142121030) {
                PPoint = 30;
            } else if (effects.getSourceId() == 400021008) {
                effects.applyTo(this);

            }
        }
        send(MainPacketCreator.givePsychicPoint(getJob(), PPoint));
    }

    public void givePP(int c) {
        PPoint += c;
        if (30 < PPoint) {
            PPoint = 30;
        }
        send(MainPacketCreator.givePsychicPoint(getJob(), PPoint));
    }

    public void gainRC(int rc) {
        this.realcash += rc;
    }

    public void gainRC(long RC, boolean show) {
        this.realcash += RC;
    }

    public void gainRC(long RC, boolean show, boolean enableActions) {
        this.realcash += RC;
    }

    public int getRC() {
        return realcash;
    }

    public void loseRC(int rc) {
        realcash -= rc;
    }

    public int getCSPoints() {
        return nxcash;
    }

    public void modifyRC(int type, int quantity, boolean show) {
        if (getRC() < 0) {
            realcash = 0;
        }
        if (getRC() + quantity < 2099999999) {
            switch (type) {
                case 1:
                    realcash += quantity;
                    break;
                case 2:
                    maplepoints += quantity;
                    break;
                default:
                    break;
            }
        } else {
            dropMessage(5, "Exceeded the amount of sponsored points.");
        }
    }

    public int getRuneTimeStamp() {
        return LastTouchedRuneTime;
    }

    public void setRuneTimeStamp() {
        if (LastTouchedRune == null) {
            LastTouchedRuneTime = 360000;
            LastTouchedRune = EtcTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    LastTouchedRuneTime = LastTouchedRuneTime - 1000;
                    if (LastTouchedRuneTime <= 0) {
                        LastTouchedRune = null;
                        LastTouchedRuneTime = 0;
                    }
                }
            }, 1000);
        }
    }

    public int getTouchedRune() {
        return TouchedRune;
    }

    public void setTouchedRune(int type) {
        TouchedRune = type;
    }

    public int getFollowId() {
        return followid;
    }

    public void setFollowId(int fi) {
        this.followid = fi;
        if (fi == 0) {
            this.followinitiator = false;
            this.followon = false;
        }
    }

    public void setFollowInitiator(boolean fi) {
        this.followinitiator = fi;
    }

    public void setFollowOn(boolean fi) {
        this.followon = fi;
    }

    public boolean isFollowOn() {
        return followon;
    }

    public boolean isFollowInitiator() {
        return followinitiator;
    }

    public void checkFollow() {
        if (followid <= 0) {
            return;
        }
        if (followon) {
            map.broadcastMessage(MainPacketCreator.followEffect(id, 0, null));
            map.broadcastMessage(MainPacketCreator.followEffect(followid, 0, null));
        }
        MapleCharacter tt = map.getCharacterById_InMap(followid);
        client.getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Follow me canceled."));
        if (tt != null) {
            tt.setFollowId(0);
            tt.getClient().getSession().writeAndFlush(MainPacketCreator.serverNotice(1, "Follow me canceled."));
        }
        setFollowId(0);
    }

    public final void timeMoveMap(final int destination, final int movemap, final int time) {
        changeMap(movemap, 0);
        getClient().send(MainPacketCreator.getClock(time));
        CloneTimer tMan = CloneTimer.getInstance();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (client.getPlayer() != null) {
                    if (getMapId() == movemap) {
                        changeMap(destination, 0);
                    }
                }
            }
        };
        tMan.schedule(r, time * 1000);
    }

    public int getPendantExp() {
        return pendantExp;
    }

    public void equipPendantOfSpirit() {
        pendantExp = 3;
        dropMessage(5, "Bonus experience when hunting monsters due to wearing a spirit pendant " + pendantExp + "You get an additional 0%..");
        /*if (pendantOfSpirit == null) {
            pendantOfSpirit = EtcTimer.getInstance().register(new Runnable() {
                @Override
                public void run() {
                    if (pendantExp == 0) {
                        pendantExp++;
                        dropMessage(5, "Bonus experience when hunting monsters due to wearing Elemental Pendants " + pendantExp + "You get an additional 0%..");
                    } else if (pendantExp < 3) {
                        pendantExp++;
                        dropMessage(5,
                                "Have you worn an Elemental Pendant? " + pendantExp + " Time passed. " + pendantExp + "Gain 0% Bonus Experience.");
                    } else {
                        pendantOfSpirit.cancel(false);
                    }
                }
            }, 3600000); // 1 hour
        }*/
    }

    public void unequipPendantOfSpirit() {
        if (pendantOfSpirit != null) {
            pendantOfSpirit.cancel(false);
            pendantOfSpirit = null;
        }
        pendantExp = 0;
    }

    public void setPC(boolean a) {
        this.prmiumpc = a;
    }

    public void fakeRelog() {
        this.client.getSession().writeAndFlush(MainPacketCreator.getPlayerInfo(this));
        MapleMap mapp = getMap();
        mapp.removePlayer(this);
        mapp.addPlayer(this);
    }

    public void handleForceGain(int oid, int skillid) {
        handleForceGain(oid, skillid, 0);
    }

    public void handleForceGain(int oid, int skillid, int extraForce) {
        if (!GameConstants.isForceIncrease(skillid) && extraForce <= 0) {
            return;
        }
        int forceGain = 1;
        if (getLevel() >= 30 && getLevel() < 70) {
            forceGain = 2;
        } else if (getLevel() >= 70 && getLevel() < 120) {
            forceGain = 3;
        } else if (getLevel() >= 120) {
            forceGain = 4;
        }
        if (extraForce > 0) {
            stats.mp += extraForce;
        } else {
            stats.mp++; // counter
        }
        if (GameConstants.isDemonSlayer(getJob())) {
            getStat().addForce(extraForce > 0 ? extraForce : forceGain);
        }

        getMap().broadcastMessage(null, MainPacketCreator.absorbingDF(oid, stats.mp, forceGain, false, this, null),
                getPosition());
        if (GameConstants.isDemonSlayer(getJob())) {
            if (stats.mpRecoverProp > 0 && extraForce <= 0) {
                if (Randomizer.nextInt(100) <= stats.mpRecoverProp) {// i think its out of 100, anyway
                    stats.mp++; // counter
                    getStat().addForce(stats.mpRecover);
                    getMap().broadcastMessage(null,
                            MainPacketCreator.absorbingDF(oid, stats.mp, stats.mpRecover, false, this, null),
                            getPosition());
                }
            }
        }
        if (stats.mp <= stats.getCurrentMaxMp()) {
            final List<Pair<PlayerStatList, Long>> statupz = new ArrayList<Pair<PlayerStatList, Long>>(8);
            statupz.add(new Pair<PlayerStatList, Long>(PlayerStatList.MP, (long) stats.mp));
            client.getSession().writeAndFlush(MainPacketCreator.updatePlayerStats(statupz, getJob(), this));
        }
    }

    public void zerooskill(int i) {
        MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz"))
                .getData(StringUtil.getLeftPaddedStr("" + i, '0', 3) + ".img");
        final ISkill skills = null;
        byte maxLevel = 0;
        for (MapleData skill : data) {
            if (skill != null) {
                for (MapleData skillId : skill.getChildren()) {
                    if (!skillId.getName().equals("icon")) {
                        maxLevel = (byte) MapleDataTool.getIntConvert("maxLevel", skillId.getChildByPath("common"), 0);
                        if (getLevel() >= MapleDataTool.getIntConvert("reqLev", skillId, 0)) {
                            changeSkillLevel(SkillFactory.getSkill(Integer.parseInt(skillId.getName())), (byte) 0,
                                    (byte) 0);
                        }
                    }
                }
            }
        }
    }

    public void maxskill(int i) {
        MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz"))
                .getData(StringUtil.getLeftPaddedStr("" + i, '0', 3) + ".img");
        byte maxLevel = 0;
        for (MapleData skill : data) {
            if (skill != null) {
                for (MapleData skillId : skill.getChildren()) {
                    if (!skillId.getName().equals("icon")) {
                        maxLevel = (byte) MapleDataTool.getIntConvert("maxLevel", skillId.getChildByPath("common"), 0);
                        if (MapleDataTool.getIntConvert("invisible", skillId, 0) == 0) { // Invisible skills are not posted
                            if (getLevel() >= MapleDataTool.getIntConvert("reqLev", skillId, 0)) {
                                try {
									//Defeat Jump Skills!
                                    if(Integer.parseInt(skillId.getName()) == 3001007 && (GameConstants.isPathFinder(job)))
                                    {
                                        return;
                                    }
									else
									{
										changeSkillLevel(SkillFactory.getSkill(Integer.parseInt(skillId.getName())), maxLevel, maxLevel);
									}
                                } catch (NumberFormatException ex) {
									System.out.println(ex + "Skillmaster error occurred!");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (GameConstants.isZero(i)) {
            changeSkillLevel(SkillFactory.getSkill(100000267),
                    (byte) 1, (byte) 1);
        }
    }

    public void maxskill_(int i) {
        MapleData data = MapleDataProviderFactory.getDataProvider(MapleDataProviderFactory.fileInWZPath("Skill.wz"))
                .getData(StringUtil.getLeftPaddedStr("" + i, '0', 3) + ".img");
        byte maxLevel = 0;
        for (MapleData skill : data) {
            if (skill != null) {
                for (MapleData skillId : skill.getChildren()) {
                    if (!skillId.getName().equals("icon")) {
                        maxLevel = (byte) MapleDataTool.getIntConvert("maxLevel", skillId.getChildByPath("common"), 0);
                        if (MapleDataTool.getIntConvert("invisible", skillId, 0) == 0) { // Invisible skills are not posted
                            if (getLevel() >= MapleDataTool.getIntConvert("reqLev", skillId, 0)) {
                                try {
                                    changeSkillLevel(SkillFactory.getSkill(Integer.parseInt(skillId.getName())),
                                            (byte) 0, maxLevel);
                                } catch (NumberFormatException ex) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getFH() {
        MapleFoothold fh = getMap().getFootholds().findMaple(getTruePosition());
        if (fh != null) {
            return fh.getId();
        }
        return 0;
    }

    public final int getRankPoint() {
        return rankpoint;
    }

    public final void setRankPoint(int point) {
        this.rankpoint = point;
    }

    public final void addRankPoint(int point) {
        this.rankpoint += point;
    }

    public int getGP() {
        return this.gp;
    }

    public void gainGP(int i) {
        this.gp += i;
    }

    public final void teachSkill(final int id, final byte level, final byte masterlevel) {
        changeSkillLevel(SkillFactory.getSkill(id), level, masterlevel);
    }

    public String getDateKey(String key) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        return getKeyValue1(year + "" + month + "" + day + "_" + key);
    }

    public void setDateKey(String key, String value) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        setKeyValue(year + "" + month + "" + day + "_" + key, value, true);
    }

    public int getDateKey3(String key) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        return getKeyValue3(year + "" + month + "" + day + "_" + key);
    }

    public void setDateKey3(String key, int value) {
        Calendar ocal = Calendar.getInstance();
        int year = ocal.get(ocal.YEAR);
        int month = ocal.get(ocal.MONTH) + 1;
        int day = ocal.get(ocal.DAY_OF_MONTH);
        setKeyValue3(year + "" + month + "" + day + "_" + key, value);
    }

    public void setKeyValue(String key, String value, boolean a) {
        if (getKeyValue1(key) == null) {
            try {
                Connection con = MYSQL.getConnection();
                PreparedStatement ps = null;
                String query = "INSERT into `acheck` (`cid`, `keya`, `value`, `day`) VALUES ('";
                query = new StringBuilder().append(query).append(id).toString();
                query = new StringBuilder().append(query).append("', '").toString();
                query = new StringBuilder().append(query).append(key).toString();
                query = new StringBuilder().append(query).append("', '").toString();
                query = new StringBuilder().append(query).append(value).toString();
                if (a) {
                    query = new StringBuilder().append(query).append("', '").toString();
                    query = new StringBuilder().append(query).append("1").toString();
                    query = new StringBuilder().append(query).append("')").toString();
                } else {
                    query = new StringBuilder().append(query).append("', '").toString();
                    query = new StringBuilder().append(query).append("0").toString();
                    query = new StringBuilder().append(query).append("')").toString();
                }
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                ps.close();
                con.close();
            } catch (SQLException ex) {

            }
        } else {
            try {
                Connection con = MYSQL.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE acheck SET value = ? WHERE cid = ? AND keya = ?");
                ps.setString(1, value);
                ps.setInt(2, id);
                ps.setString(3, key);
                ps.executeUpdate();
                ps.close();
                con.close();
            } catch (SQLException ex) {

            }
        }
    }

    public String getKeyValue1(String key) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM acheck WHERE cid = ? and keya = ?");
            ps.setInt(1, id);
            ps.setString(2, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String ret = rs.getString("value");
                rs.close();
                ps.close();
                con.close();
                return ret;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    private static void handlePickPocket(final MapleCharacter player, final MapleMonster mob, AttackPair oned) {
        ISkill skill = SkillFactory.getSkill(4211003);
        SkillStatEffect s = skill.getEffect(player.getSkillLevel(skill));
        for (final Pair<Long, Boolean> eachde : oned.attack) {
            final Long eachd = eachde.left;
            if (s.makeChanceResult()) {
                EtcTimer.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        player.getMap().spawnMesoDrop(1, new Point((int) (mob.getPosition().getX()), (int) (mob.getPosition().getY())), mob, player, true, (byte) 0);
                        //player.addpocket();
                    }
                }, 100L);
            }
        }
    }

    public final void handleOrbgain() {
        int orbcount = getBuffedValue(BuffStats.ComboCounter).intValue();
        ISkill combo;
        ISkill advcombo;
        switch (getJob()) {
            case 1110:
            case 1111:
            case 1112:
                combo = SkillFactory.getSkill(11111001);
                advcombo = SkillFactory.getSkill(11110005);
                break;
            default:
                combo = SkillFactory.getSkill(1101013);
                advcombo = SkillFactory.getSkill(1120003);
                break;
        }

        if (GameConstants.isPhantom(getJob())) {
            int comboid = getBuffedSkillEffect(BuffStats.ComboCounter, -1).getSourceId();
            if (comboid == 11111001) {
                combo = SkillFactory.getSkill(11111001);
                advcombo = SkillFactory.getSkill(11110005);
            } else if (comboid == 1101013) {
                combo = SkillFactory.getSkill(1101013);
                advcombo = SkillFactory.getSkill(1120003);
            }
        }
        SkillStatEffect ceffect = null;
        int advComboSkillLevel = getSkillLevel(advcombo);
        if (advComboSkillLevel > 0) {
            ceffect = advcombo.getEffect(advComboSkillLevel);
        } else {
            ceffect = combo.getEffect(getSkillLevel(combo));
        }

        if (orbcount < ceffect.getX() + 1) {
            int neworbcount = orbcount + 1;
            if ((advComboSkillLevel > 0) && (ceffect.makeChanceResult()) && (neworbcount < ceffect.getX() + 1)) {
                neworbcount++;
            }
            /*
            if (neworbcount > 11) {
                neworbcount = 11;
            }
            if (count != 0) {
                neworbcount = orbcount - count;
            }
             */
            List<Triple<BuffStats, Integer, Boolean>> stat = new ArrayList<Triple<BuffStats, Integer, Boolean>>();
            stat.add(new Triple<BuffStats, Integer, Boolean>(BuffStats.ComboCounter, neworbcount, false));
            setBuffedValue(BuffStats.ComboCounter, combo.getId(), neworbcount);
            int duration = ceffect.getDuration();
            duration += (int) (getBuffedStarttime(BuffStats.ComboCounter, combo.getId()) - System.currentTimeMillis());
            synchronized (this.stackedEffects) {
                this.client.getSession().writeAndFlush(MainPacketCreator.giveBuff(combo.getId(), duration, stat, ceffect, Collections.unmodifiableMap(this.stackedEffects), combo.getAnimationTime(), this));
            }
            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<Triple<BuffStats, Integer, Boolean>>();
            for (Entry<BuffStats, List<BuffStatsValueHolder>> effect : effects.entrySet()) {
                for (BuffStatsValueHolder bsvh : effect.getValue()) {
                    statups.add(new Triple<BuffStats, Integer, Boolean>(effect.getKey(), bsvh.value, false));
                }
            }
            if (statups.size() > 0) {
                getMap().broadcastMessage(this, MainPacketCreator.giveForeignBuff(this, statups), false);
            }
        }
    }

    public void handleOrbconsume(int amount) {
        ISkill combo;
        switch (getJob()) {
            case 1110:
            case 1111:
            case 1112:
                combo = SkillFactory.getSkill(11111001);
                break;
            default:
                combo = SkillFactory.getSkill(1101013);
        }
        SkillStatEffect ceffect = combo.getEffect(getSkillLevel(combo));
        List stat = Collections.singletonList(new Triple(BuffStats.ComboCounter, (getBuffedValue(BuffStats.ComboCounter) - amount), false));
        setBuffedValue(BuffStats.ComboCounter, combo.getId(), getBuffedValue(BuffStats.ComboCounter, combo.getId()) - amount);
        int duration = ceffect.getDuration();
        duration += (int) (getBuffedStarttime(BuffStats.ComboCounter, combo.getId()) - System.currentTimeMillis());
        this.client.getSession().writeAndFlush(MainPacketCreator.giveBuff(combo.getId(), duration, stat, ceffect, Collections.unmodifiableMap(this.stackedEffects), combo.getAnimationTime(), this));
    }

    public String getChatban() {
        return chatban;
    }

    public void setChatban(String a) {
        this.chatban = a;
    }

    public int getTotalSkillLevel(int skillid) {
        return getTotalSkillLevel(SkillFactory.getSkill(skillid));
    }

    public int getTotalSkillLevel(ISkill skill) {
        if (skill == null) {
            return 0;
        }
        if (GameConstants.iskaiser_Transfiguration_Skill(skill.getId())) {
            return skill.getMaxLevel();
        }
        SkillEntry ret = (SkillEntry) this.skills.get(skill);
        if ((ret == null) || (ret.skillevel <= 0)) {
            return 0;
        }
        return ret.skillevel;
    }

    public List HeadTitle() {
        List<Integer> num_ = new ArrayList<Integer>();
        int num = getKeyValue2("HeadTitle");
        int aa = num / 10000;
        int bb = num / 1000 - aa * 10;
        int cc = num / 100 - (aa * 100 + bb * 10);
        int dd = num / 10 - (aa * 1000 + bb * 100 + cc * 10);
        int ee = num / 1 - (aa * 10000 + bb * 1000 + cc * 100 + dd * 10);
        num_.add(aa);
        num_.add(bb);
        num_.add(cc);
        num_.add(dd);
        num_.add(ee);
        return num_;
    }

    public boolean AutoJob() {
        if (getKeyValue("AutoJob") != null) {
            if (level == 20) {
                switch (getKeyValue("AutoJob")) {
                    case "430":
                        getClient().send(UIPacket.showInfo("[Rememberer of darkness] I changed to semi-dudor."));
                        changeJob(430);
                        setKeyValue("AutoJob", "430");
                        return true;
                }
            } else if (level == 30) {
                switch (getKeyValue("AutoJob")) {
                    case "110":
                        getClient().send(UIPacket.showInfo("[Two-handed swordsman] Changed to Fighter."));
                        changeJob(110);
                        return true;
                    case "120":
                        getClient().send(UIPacket.showInfo("[One-handed Sword Knight] Changed to Page."));
                        changeJob(120);
                        return true;
                    case "130":
                        getClient().send(UIPacket.showInfo("[Knight] Changed to Spearman."));
                        changeJob(130);
                        return true;
                    case "210":
                        getClient().send(UIPacket.showInfo("[Fire*Poison] Changed to Wizard."));
                        changeJob(210);
                        return true;
                    case "220":
                        getClient().send(UIPacket.showInfo("[Ice*lightning] Changed to Wizard."));
                        changeJob(220);
                        return true;
                    case "230":
                        getClient().send(UIPacket.showInfo("[Heal*Buff] Changed to Cleric."));
                        changeJob(230);
                        return true;
                    case "310":
                        getClient().send(UIPacket.showInfo("[Shooter] I changed to Hunter."));
                        changeJob(310);
                        return true;
                    case "320":
                        getClient().send(UIPacket.showInfo("[Next shooter] Changed to shooter."));
                        changeJob(320);
                        return true;
                    case "330":
                        getClient().send(UIPacket.showInfo("[Action] I changed to Ancient Archer."));
                        changeJob(330);
                        return true;
                    case "410":
                        getClient().send(UIPacket.showInfo("[Recognition assassination Beginner] Changed to Assassin."));
                        changeJob(410);
                        return true;
                    case "420":
                        getClient().send(UIPacket.showInfo("[Sword assassination initiation period] shifted to Seef."));
                        changeJob(420);
                        return true;
                    case "510":
                        getClient().send(UIPacket.showInfo("[Knuckle Beginner] Changed to Infinite."));
                        changeJob(510);
                        return true;
                    case "520":
                        getClient().send(UIPacket.showInfo("[Gun Beginner] Changed to Gunslinger."));
                        changeJob(520);
                        return true;
                    case "430":
                        getClient().send(UIPacket.showInfo("[The Dark Past] Changed to Durer."));
                        changeJob(431);
                        return true;
                    case "530":
                        getClient().send(UIPacket.showInfo("[Introductory Cannon] Changed to Cannon Shooter."));
                        changeJob(530);
                        return true;
                    case "1110":
                        getClient().send(UIPacket.showInfo("[Introductory Cygnus] Changed to Light Knight."));
                        changeJob(1110);
                        return true;
                    case "1210":
                        getClient().send(UIPacket.showInfo("[Introductory Cygnus] Changed to Fire Knight."));
                        changeJob(1210);
                        return true;
                    case "1310":
                        getClient().send(UIPacket.showInfo("[Introductory Cygnus] Changed to Knight of the Wind."));
                        changeJob(1310);
                        return true;
                    case "1410":
                        getClient().send(UIPacket.showInfo("[Initiator for Cygnus] Changed to Dark Knight."));
                        changeJob(1410);
                        return true;
                    case "1510":
                        getClient().send(UIPacket.showInfo("[Introductory Cygnus] Changed to Lightning Knight."));
                        changeJob(1510);
                        return true;
                    case "2110":
                        getClient().send(UIPacket.showInfo("[Heroic Instinct] Changed to Aran."));
                        changeJob(2110);
                        return true;
                    case "2210":
                        getClient().send(UIPacket.showInfo("[Second Step] Changed to Evan."));
                        changeJob(2211);
                        return true;
                    case "2310":
                        getClient().send(UIPacket.showInfo("[Heroic Instinct] Changed to Mercedes."));
                        changeJob(2310);
                        return true;
                    case "2410":
                        getClient().send(UIPacket.showInfo("[Heroic Instinct] Changed to Phantom."));
                        changeJob(2410);
                        return true;
                    case "2510":
                        getClient().send(UIPacket.showInfo("[Heroic Instinct] Changed to Eunwol."));
                        changeJob(2510);
                        return true;
                    case "2710":
                        getClient().send(UIPacket.showInfo("Hero's Instinct] Changed to Luminous."));
                        changeJob(2710);
                        return true;
                    case "3110":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Changed to Daemon Slayer."));
                        changeJob(3110);
                        return true;
                    case "3120":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Changed to Daemon Avenger."));
                        changeJob(3120);
                        return true;
                    case "3210":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Changed to Battle Mage."));
                        changeJob(3210);
                        return true;
                    case "3310":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Changed to Wild Hunter."));
                        changeJob(3310);
                        return true;
                    case "3510":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Changed to Mechanic."));
                        changeJob(3510);
                        return true;
                    case "3610":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] Moved to Xenon."));
                        changeJob(3610);
                        return true;
                    case "3710":
                        getClient().send(UIPacket.showInfo("[Introduction to Resistance] He changed to Blaster."));
                        changeJob(3710);
                        return true;
                    case "5110":
                        getClient().send(UIPacket.showInfo("[Director of Cygnus] Changed to Light Knight."));
                        changeJob(5110);
                        return true;
                    case "6110":
                        getClient().send(UIPacket.showInfo("[Nova trainee] Changed to Kaiser."));
                        changeJob(6110);
                        return true;
                    case "6510":
                        getClient().send(UIPacket.showInfo("[Nova trainee] Changed to Angelic Buster."));
                        changeJob(6510);
                        return true;
                    case "14200":
                        getClient().send(UIPacket.showInfo("[Enlightenment of Super Power] Changed to Kinesis."));
                        changeJob(14210);
                        return true;
                    case "6400":
                        getClient().send(UIPacket.showInfo("[Enlightenment of Weapon] Changed to Cadena."));
                        changeJob(6410);
                        return true;
                    case "15210":
                        getClient().send(UIPacket.showInfo("[Magic Enlightenment] Changed to Illium."));
                        changeJob(15210);
                        return true;
                    case "15510":
                        getClient().send(UIPacket.showInfo("[Copper of Specter] Changed to Ark."));
                        changeJob(15510);
                        return true;
                    default:
                        return true;
                }
            } else if (level == 45) {
                switch (getKeyValue("AutoJob")) {
                    case "430":
                        getClient().send(UIPacket.showInfo("Dark Identity Changed to Dualmaster."));
                        changeJob(432);
                        return true;
                    default:
                        return true;
                }
            } else if (level == 60) {
                switch (getKeyValue("AutoJob")) {
                    case "110":
                        getClient().send(UIPacket.showInfo("[Soul Fencing Knight] Changed to Crusader."));
                        changeJob(111);
                        return true;
                    case "120":
                        getClient().send(UIPacket.showInfo("[Professional Sword of Knight] Changed to Knight."));
                        changeJob(121);
                        return true;
                    case "130":
                        getClient().send(UIPacket.showInfo("[Dragon Sword Knight] was a dragon knight."));
                        changeJob(131);
                        return true;
                    case "210":
                        getClient().send(UIPacket.showInfo("[Fire*Poison] Changed to Meiji."));
                        changeJob(211);
                        return true;
                    case "220":
                        getClient().send(UIPacket.showInfo("[Ice*Lightning] Changed to Meiji."));
                        changeJob(221);
                        return true;
                    case "230":
                        getClient().send(UIPacket.showInfo("[Heal*Buff] I'm a former priest."));
                        changeJob(231);
                        return true;
                    case "310":
                        getClient().send(UIPacket.showInfo("[Series shooter] Changed to Ranger."));
                        changeJob(311);
                        return true;
                    case "320":
                        getClient().send(UIPacket.showInfo("[Brown-backed shooter] Changed to sniper."));
                        changeJob(321);
                        return true;
                    case "330":
                        getClient().send(UIPacket.showInfo("[Master of Ancients] Changed to Chaser."));
                        changeJob(331);
                        return true;
                    case "410":
                        getClient().send(UIPacket.showInfo("[Assassination Specialist] I changed to Hermit."));
                        changeJob(411);
                        return true;
                    case "420":
                        getClient().send(UIPacket.showInfo("[The Darkman] Changed to Sheep Master."));
                        changeJob(421);
                        return true;
                    case "510":
                        getClient().send(UIPacket.showInfo("[Dragon Knuckle Fighter] Changed to Buccaneer."));
                        changeJob(511);
                        return true;
                    case "520":
                        getClient().send(UIPacket.showInfo("[Gun Mastery] I changed to Valkyrie."));
                        changeJob(521);
                        return true;
                    case "430":
                        getClient().send(UIPacket.showInfo("[Knowing the Darkness] She changed to Slasher."));
                        changeJob(433);
                        return true;
                    case "530":
                        getClient().send(UIPacket.showInfo("[Canon Mastery] Changed to Cannon Shooter."));
                        changeJob(531);
                        return true;
                    case "2110":
                        getClient().send(UIPacket.showInfo("[Hero's Enlightenment] Changed to Aran."));
                        changeJob(2111);
                        return true;
                    case "2210":
                        getClient().send(UIPacket.showInfo("[Evolutionary Dragon] Changed to Evan."));
                        changeJob(2214);
                        return true;
                    case "2310":
                        getClient().send(UIPacket.showInfo("[Hero's Enlightenment] Changed to Mercedes."));
                        changeJob(2311);
                        return true;
                    case "2410":
                        getClient().send(UIPacket.showInfo("[Hero's Enlightenment] Changed to Phantom."));
                        changeJob(2411);
                        return true;
                    case "2510":
                        getClient().send(UIPacket.showInfo("[Hero's Enlightenment] Changed to Eunwol."));
                        changeJob(2511);
                        return true;
                    case "2710":
                        getClient().send(UIPacket.showInfo("[Hero's Enlightenment] Changed to Luminous."));
                        changeJob(2711);
                        return true;
                    case "3110":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Daemon Slayer."));
                        changeJob(3111);
                        return true;
                    case "3120":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Daemon Avenger."));
                        changeJob(3121);
                        return true;
                    case "3210":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Battle Mage."));
                        changeJob(3211);
                        return true;
                    case "3310":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Wild Hunter."));
                        changeJob(3311);
                        return true;
                    case "3510":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Mechanic."));
                        changeJob(3511);
                        return true;
                    case "3610":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Xenon."));
                        changeJob(3611);
                        return true;
                    case "3710":
                        getClient().send(UIPacket.showInfo("[Resistence Agent] Changed to Blaster."));
                        changeJob(3711);
                        return true;
                    case "5110":
                        getClient().send(UIPacket.showInfo("[Director of Cygnus] Changed to Light Knight."));
                        changeJob(5111);
                        return true;
                    case "6110":
                        getClient().send(UIPacket.showInfo("[Guardian of Nova] Changed to Kaiser."));
                        changeJob(6111);
                        return true;
                    case "6510":
                        getClient().send(UIPacket.showInfo("[Guardian of Nova] Changed to Angelic Buster."));
                        changeJob(6511);
                        return true;
                    case "1110":
                        getClient().send(UIPacket.showInfo("[Cygnus Official Article] Changed to Soul Master."));
                        changeJob(1111);
                        return true;
                    case "1210":
                        getClient().send(UIPacket.showInfo("[Cygnus Official Article] Changed to Flame Wizard."));
                        changeJob(1211);
                        return true;
                    case "1310":
                        getClient().send(UIPacket.showInfo("[Cygnus Official Article] Changed to Windbreaker."));
                        changeJob(1311);
                        return true;
                    case "1410":
                        getClient().send(UIPacket.showInfo("[Cygnus Official Article] Changed to Night Walker."));
                        changeJob(1411);
                        return true;
                    case "1510":
                        getClient().send(UIPacket.showInfo("[Cygnus Official Article] Striker Changed."));
                        changeJob(1511);
                        return true;
                    case "14200":
                        getClient().send(UIPacket.showInfo("[Enlightenment of Super Power] I changed to Kinesis."));
                        changeJob(14211);
                        return true;
                    case "6400":
                        getClient().send(UIPacket.showInfo("[Weapon master] Changed to Cadena."));
                        changeJob(6411);
                        return true;
                    case "15210":
                        getClient().send(UIPacket.showInfo("[Magic Master] Changed to Illium."));
                        changeJob(15211);
                        return true;
                    case "15510":
                        getClient().send(UIPacket.showInfo("[The Spectator] Ex-Arc."));
                        changeJob(15511);
                        return true;
                }
            } else if (level == 100) {
                switch (getKeyValue("AutoJob")) {
                    case "110":
                        getClient().send(UIPacket.showInfo("[Master of Chain Swordsman] Changed to Hero."));
                        changeJob(112);
                        return true;
                    case "120":
                        getClient().send(UIPacket.showInfo("[Master of Fantasy Swordsman] Changed to Paladin."));
                        changeJob(122);
                        return true;
                    case "130":
                        getClient().send(UIPacket.showInfo("[Master of Dark Dragon Spear] Changed to Dark Knight."));
                        changeJob(132);
                        return true;
                    case "210":
                        getClient().send(UIPacket.showInfo("[Fire*Poison Master] Changed to Archmage."));
                        changeJob(212);
                        return true;
                    case "220":
                        getClient().send(UIPacket.showInfo("[Ice*Lightning Master] Changed to Archmage."));
                        changeJob(222);
                        return true;
                    case "230":
                        getClient().send(UIPacket.showInfo("[Heal*Buff Master] Changed to Bishop."));
                        changeJob(232);
                        return true;
                    case "310":
                        getClient().send(UIPacket.showInfo("[Arrow Speaker Master] Changed to Bow Master."));
                        changeJob(312);
                        return true;
                    case "320":
                        getClient().send(UIPacket.showInfo("[Master of Arrow Power] Changed to Shrine."));
                        changeJob(322);
                        return true;
                    case "330":
                        getClient().send(UIPacket.showInfo("[Ancient Master] Changed to Pathfinder."));
                        changeJob(332);
                        return true;
                    case "410":
                        getClient().send(UIPacket.showInfo("[Master of Chain Assassination] Changed to Knight Road."));
                        changeJob(412);
                        return true;
                    case "420":
                        getClient().send(UIPacket.showInfo("Dark Assassination Master Changed to Shadow."));
                        changeJob(422);
                        return true;
                    case "510":
                        getClient().send(UIPacket.showInfo("[Knuckle Fighter of Spirits] Changed to Viper."));
                        changeJob(512);
                        return true;
                    case "520":
                        getClient().send(UIPacket.showInfo("[Battle Gun Mastery] Former Captain."));
                        changeJob(522);
                        return true;
                    case "430":
                        getClient().send(UIPacket.showInfo("[Adjuster of Darkness] Changed to Dual Blade."));
                        changeJob(434);
                        return true;
                    case "530":
                        getClient().send(UIPacket.showInfo("[Canon Mastery of Destruction] Changed to Cannon Shooter."));
                        changeJob(532);
                        return true;
                    case "2110":
                        getClient().send(UIPacket.showInfo("[Hero's Resurrection] Changed to Aran."));
                        changeJob(2112);
                        return true;
                    case "2210":
                        getClient().send(UIPacket.showInfo("[Legendary Dragon] Changed to Evan."));
                        changeJob(2217);
                        return true;
                    case "2310":
                        getClient().send(UIPacket.showInfo("[Hero's Resurrection]."));
                        changeJob(2312);
                        return true;
                    case "2410":
                        getClient().send(UIPacket.showInfo("[Hero's Resurrection] Changed to Phantom."));
                        changeJob(2412);
                        return true;
                    case "2510":
                        getClient().send(UIPacket.showInfo("[Hero's Resurrection] Changed to Eunwol."));
                        changeJob(2512);
                        return true;
                    case "2710":
                        getClient().send(UIPacket.showInfo("[Hero's Resurrection] Changed to Luminous."));
                        changeJob(2712);
                        return true;
                    case "3110":
                        getClient().send(UIPacket.showInfo("[Hero of Resistance] Changed to Daemon Slayer."));
                        changeJob(3112);
                        return true;
                    case "3120":
                        getClient().send(UIPacket.showInfo("[Resistence Hero] Changed to Daemon Avenger."));
                        changeJob(3122);
                        return true;
                    case "3210":
                        getClient().send(UIPacket.showInfo("[Hero of Resistance] Changed to Battle Mage."));
                        changeJob(3212);
                        return true;
                    case "3310":
                        getClient().send(UIPacket.showInfo("[Hero of Resistance] Changed to Wild Hunter."));
                        changeJob(3312);
                        return true;
                    case "3510":
                        getClient().send(UIPacket.showInfo("[Hero of Resistance] Moved to Mechanic."));
                        changeJob(3512);
                        return true;
                    case "3610":
                        getClient().send(UIPacket.showInfo("[Hero of Resistance] Moved to Xenon."));
                        changeJob(3612);
                        return true;
                    case "3710":
                        getClient().send(UIPacket.showInfo("[Heroes of Resistance] Changed to Blaster."));
                        changeJob(3712);
                        return true;
                    case "5110":
                        getClient().send(UIPacket.showInfo("[Director of Cygnus] Changed to Light Knight."));
                        changeJob(5112);
                        return true;
                    case "6110":
                        getClient().send(UIPacket.showInfo("[Dragon Knight] Changed to Kaiser."));
                        changeJob(6112);
                        return true;
                    case "6510":
                        getClient().send(UIPacket.showInfo("[Idol of Battlefield] Changed to Angelic Buster."));
                        changeJob(6512);
                        return true;
                    case "1110":
                        getClient().send(UIPacket.showInfo("[Cygnus Hero] Changed to Great Spirit of Light."));
                        changeJob(1112);
                        changeSkillLevel(11121000, (byte) 30, (byte) 30);
                        return true;
                    case "1210":
                        getClient().send(UIPacket.showInfo("[Cygnus Hero] Changed to Great Spirit of Fire."));
                        changeJob(1212);
                        changeSkillLevel(12121000, (byte) 30, (byte) 30);
                        return true;
                    case "1310":
                        getClient().send(UIPacket.showInfo("[Cygnus Hero] Changed to Great Spirit of the Wind."));
                        changeJob(1312);
                        changeSkillLevel(13121000, (byte) 30, (byte) 30);
                        return true;
                    case "1410":
                        getClient().send(UIPacket.showInfo("[Cygnus Hero] Changed to Dark Spirit."));
                        changeJob(1412);
                        changeSkillLevel(14121000, (byte) 30, (byte) 30);
                        return true;
                    case "1510":
                        getClient().send(UIPacket.showInfo("[Cygnus Heroes] Changed to Great Spirit of Lightning."));
                        changeJob(1512);
                        changeSkillLevel(15121000, (byte) 30, (byte) 30);
                        return true;
                    case "14200":
                        getClient().send(UIPacket.showInfo("[Superhero] Changed to Kinesis."));
                        changeJob(14212);
                        return true;
                    case "6400":
                        getClient().send(UIPacket.showInfo("[Hero of Nova] Changed to Cadena."));
                        changeJob(6412);
                        return true;
                    case "15210":
                        getClient().send(UIPacket.showInfo("[Magic Transcendence] Changed to Illium."));
                        changeJob(15212);
                        return true;
                    case "15510":
                        getClient().send(UIPacket.showInfo("[King of Specter] Advance to Ark."));
                        changeJob(15512);
                        return true;
                }
            }
        }
        return false;
    }

    public int getDamageSkin() {
        final List<MapleQuestStatus> started = getStartedQuests();
        String customdata = "0";
        for (final MapleQuestStatus q : started) {
            if (q.getQuest().getId() == 7291 && q.getCustomData() != null) {
                customdata = q.getCustomData();
            }
        }
        return Integer.parseInt(customdata);
    }

    public void setAuctionMeso(int meso) {
        this.setKeyValue2("AUCTION_Meso", meso);
    }

    public int getBetaClothes() {
        return betaclothes;
    }

    public void pBetaClothes(int value) {
        betaclothes += value;
    }

    public void mBetaClothes(int value) {
        betaclothes -= value;
    }

    /*
    public void giveBuff(int skill, int level) {
        SkillFactory.getSkill(skill).getEffect(level).applyTo(getClient().getPlayer());
    }
     */
    public int ForcingItem() {
        return ForcingItem;
    }

    public void setForcingItem(short a) {
        this.ForcingItem = a;
    }

    public void elementalChargeHandler(int Count) {
        ELEMENTAL_CHARGE += Count;
        ISkill skill = SkillFactory.getSkill(1200014);
        int skillLevel = getSkillLevel(skill);
        SkillStatEffect effect = getBuffedSkillEffect(BuffStats.ElementalCharge, 1200014);
        if (effect == null) {
            effect = skill.getEffect(skillLevel);
        }
        effect.applyTo(this);
        if (getSkillLevel(400011052) > 0) {
            SkillFactory.getSkill(400011052).getEffect(getSkillLevel(400011052)).applyTo(this);
        } else if (isActiveBuffedValue(400011053)) {
            SkillFactory.getSkill(400011053).getEffect(getSkillLevel(400011052)).applyTo(this);
        }
    }

    public void InhumanSpeed(int count) {
        ISkill skill = SkillFactory.getSkill(400031020);
        SkillStatEffect effect = getBuffedSkillEffect(BuffStats.SlowAttack, 400031020);
        if (effect == null) {
            effect = skill.getEffect(getSkillLevel(skill));
        }
        if (getCooldownLimit(400031020) > 0) {
            this.InhumanSpeedCount += count;
            if (this.InhumanSpeedCount == 30000) {
                this.InhumanSpeedCount = 0;
                effect.applyTo(this);
            }
        }
    }

    public int GetCount() {
        return ELEMENTAL_CHARGE;
    }

    public int getElementalChargeCount() {
        return ELEMENTAL_CHARGE;
    }

    public void SetSkillid(int id) {
        this.ELEMENTAL_CHARGE_ID = id;
    }

    public int GetSkillid() {
        return ELEMENTAL_CHARGE_ID;
    }

    public byte getBurningCharacter() {
        return burning;
    }

    public int[] StarPer() {
        return StarPer;
    }

    public List<Pair<EnchantEquipStats, Integer>> stata() {
        return stata;
    }

    public void setFishing(boolean a) {
        this.fishing2 = a;
    }

    public boolean Fishing() {
        return fishing2;
    }

    public void StarPers(int a, int b, int c) {
        this.StarPer[0] = a;
        this.StarPer[1] = b;
        this.StarPer[2] = c;
    }

    public void statas(List<Pair<EnchantEquipStats, Integer>> a) {
        this.stata = a;
    }

    public void checkReincarnation(final long time) {
        if (this.reincarnationTime == -1) {
            this.reincarnationTime = time + 10000;
        } else if (this.reincarnationTime <= time) {
            final ISkill BerserkX = SkillFactory.getSkill(1320016);
            final int skilllevel = getSkillLevel(BerserkX);
            if (skilllevel >= 1 && map != null) {
                client.getSession().writeAndFlush(
                        MainPacketCreator.showSkillEffect(-1, level, 1320016, skilllevel, (byte) 0, 1, null, null));
                map.broadcastMessage(this,
                        MainPacketCreator.showSkillEffect(getId(), level, 1320016, skilllevel, (byte) 0, 1, null, null),
                        false);
            }
            this.reincarnationTime = time + 10000;
        }
    }

    public int getReincarnationCount() {
        return this.reincarnationCount;
    }

    public void resetCheckReincarnationBuff() {
        this.reincarnationCount = 30;
        this.reincarnationMobCount = 30;
    }

    public void checkReincarnationBuff(boolean killMonster) {
        if (killMonster) {
            if (this.reincarnationMobCount > 0) {
                this.reincarnationMobCount--;
            }
            if (this.reincarnationCount > 0) {
                this.reincarnationCount--;
                SkillStatEffect eff = getBuffedSkillEffect(BuffStats.Reincarnation, 1320019);
                if (eff != null) {
                    eff.applyTo(this);
                }
            }
        } else {
            this.reincarnationCount--;
            SkillStatEffect eff = getBuffedSkillEffect(BuffStats.Reincarnation, 1320019);
            if (eff != null) {
                eff.applyTo(this);
            }
        }
    }

    public byte getProfessionLevel(int id) {
        int ret = getSkillLevel(id);
        if (ret <= 0) {
            return 0;
        }
        return (byte) ((ret >>> 24) & 0xFF);
    }

    public void cancelEffectFromBuffStat(BuffStats stat) {
        cancelEffectFromBuffStat(stat, -1);
    }

    public CalcDamage getCalcDamage() {
        return calcDamage;
    }

    public void addSlimeVirusCount(int i) {
        slimeVirusCount += i;
    }

    public void setSlimeVirusCount(int i) {
        slimeVirusCount = i;
    }

    public int getSlimeVirusCount() {
        return slimeVirusCount;
    }

    public void changeKaiserTransformKey() {
        MapleKeyLayout key = getKeyLayout();
        Integer k = null;
        if (this.isFinalFiguration) {
            for (Entry<Integer, MapleKeyBinding> lay : key.Layout().entrySet()) {
                if (lay.getValue().getAction() == 53) {
                    k = lay.getKey();
                    break;
                }
            }
            if (k != null) {
                if (getSkillLevel(61111114) == 0) {
                    changeSkillLevel(61111114, (byte) 1, (byte) 1);
                }
                key.Layout().get(k).setAction(61111114);
                key.Layout().get(k).setType(1);
            }
        } else {
            for (Entry<Integer, MapleKeyBinding> lay : key.Layout().entrySet()) {
                if (lay.getValue().getAction() == 61111114) {
                    k = lay.getKey();
                    break;
                }
            }
            if (k != null) {
                if (getSkillLevel(61111114) != 0) {
                    changeSkillLevel(61111114, (byte) 0, (byte) 0);
                }
                key.Layout().get(k).setAction(53);
                key.Layout().get(k).setType(5);
            }
        }
        send(MainPacketCreator.getKeymap(key));
    }

    public void removeLinkSkill(int paramInt1, int paramInt2) {
        PreparedStatement localPreparedStatement = null;
        ResultSet localResultSet = null;
        Connection localConnection = null;
        try {
            if ((localResultSet = (localPreparedStatement = (localConnection = MYSQL.getConnection()).prepareStatement("SELECT * FROM linkskill WHERE skillid = " + paramInt1 + " AND linking_cid = " + paramInt2)).executeQuery()).next()) {
                localConnection.prepareStatement("DELETE FROM linkskill WHERE skillid = " + paramInt1 + " AND linking_cid = " + paramInt2).execute();
            }
        } catch (SQLException localSQLException2) {
            localSQLException2.printStackTrace();
        } finally {
            try {
                localPreparedStatement.close();
                localResultSet.close();
                localConnection.close();
            } catch (SQLException localSQLException4) {
                localSQLException4.printStackTrace();
            }
        }
    }

    public void addLinkSkill(LinkSkill paramLinkSkill) {
        PreparedStatement localPreparedStatement = null;
        Connection localObject = null;
        try {
            removeLinkSkill(paramLinkSkill.getSkillId(), paramLinkSkill.getLinkingCid());
            (localPreparedStatement = (localObject = MYSQL.getConnection()).prepareStatement(
                    "INSERT INTO linkskill (accid, realskillid, skillid, linking_cid, linked_cid, skilllevel, time) VALUES (?, ?, ?, ?, ?, ?, ?)"))
                    .setInt(1, paramLinkSkill.getAccId());
            localPreparedStatement.setInt(2, paramLinkSkill.getRealSkillId());
            localPreparedStatement.setInt(3, paramLinkSkill.getSkillId());
            localPreparedStatement.setInt(4, paramLinkSkill.getLinkingCid());
            localPreparedStatement.setInt(5, paramLinkSkill.getLinkedCid());
            localPreparedStatement.setInt(6, paramLinkSkill.getSkillLevel());
            localPreparedStatement.setLong(7, paramLinkSkill.getTime());
            localPreparedStatement.execute();
        } catch (SQLException localSQLException2) {
            localSQLException2.printStackTrace();
        } finally {
            try {
                localPreparedStatement.close();
                localObject.close();
            } catch (SQLException localSQLException4) {
                localSQLException4.printStackTrace();
            }
        }
    }

    public void LinkSkill(LinkSkill paramLinkSkill) {
        LinkSkill localLinkSkill = new LinkSkill(getClient().getAccID(), paramLinkSkill.getRealSkillId(),
                paramLinkSkill.getSkillId(), paramLinkSkill.getLinkingCid(), getId(), paramLinkSkill.getSkillLevel(),
                paramLinkSkill.getTime());
        addLinkSkill(localLinkSkill);
        getClient().send(MainPacketCreator.ChangeLinkSkillState(paramLinkSkill.getSkillId(), true));
        getClient().send(MainPacketCreator.LinkedSkill(localLinkSkill));
    }

    public void UnLinkSkill(LinkSkill paramLinkSkill) {
        LinkSkill localLinkSkill = new LinkSkill(getClient().getAccID(), paramLinkSkill.getRealSkillId(),
                paramLinkSkill.getSkillId(), paramLinkSkill.getLinkingCid(), paramLinkSkill.getLinkingCid(),
                paramLinkSkill.getSkillLevel(), paramLinkSkill.getTime());
        addLinkSkill(localLinkSkill);
        getClient().send(MainPacketCreator.ChangeLinkSkillState(paramLinkSkill.getSkillId(), false));
        getClient().send(MainPacketCreator.UnLinkedSkill(localLinkSkill.getSkillId(), localLinkSkill.getLinkedCid()));
    }

    public List<LinkSkill> getLinkSkill(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
        List<LinkSkill> localArrayList = new ArrayList<>();
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = null;
            if (paramBoolean1) {
                ps = con.prepareStatement("SELECT * FROM linkskill WHERE accid = " + getClient().getAccID());
            } else if (paramBoolean2) {
                ps = con.prepareStatement("SELECT * FROM linkskill WHERE linking_cid = " + getId());
            } else {
                ps = con.prepareStatement("SELECT * FROM linkskill WHERE linked_cid = " + getId());
            }
            ResultSet localResultSet = ps.executeQuery();
            while (localResultSet.next()) {
                if ((!paramBoolean2) && (paramBoolean3)) {
                    if ((localResultSet.getInt("linked_cid") != getId()) || (localResultSet.getInt("skilllevel") == 0)) {
                        continue;
                    }
                    localArrayList.add(new LinkSkill(localResultSet.getInt("accid"),
                            localResultSet.getInt("realskillid"), localResultSet.getInt("skillid"),
                            localResultSet.getInt("linking_cid"), localResultSet.getInt("linked_cid"),
                            localResultSet.getInt("skilllevel"), localResultSet.getLong("time")));
                    continue;
                }
                if ((!paramBoolean1) && (!paramBoolean2) && (!paramBoolean3)) {
                    if (getId() == localResultSet.getInt("linking_cid")) {
                        continue;
                    }
                    localArrayList.add(new LinkSkill(localResultSet.getInt("accid"),
                            localResultSet.getInt("realskillid"), localResultSet.getInt("skillid"),
                            localResultSet.getInt("linking_cid"), localResultSet.getInt("linked_cid"),
                            localResultSet.getInt("skilllevel"), localResultSet.getLong("time")));
                    continue;
                }
                if (localResultSet.getInt("skilllevel") == 0) {
                    continue;
                }
                localArrayList.add(new LinkSkill(localResultSet.getInt("accid"), localResultSet.getInt("realskillid"),
                        localResultSet.getInt("skillid"), localResultSet.getInt("linking_cid"),
                        localResultSet.getInt("linked_cid"), localResultSet.getInt("skilllevel"),
                        localResultSet.getLong("time")));
            }
            localResultSet.close();
            ps.close();
            con.close();
        } catch (SQLException localSQLException) {
            localSQLException.printStackTrace();
        }
        return localArrayList;
    }

    public void gainLinkSkill() {
        LinkSkill link_skill = null;
        int skillid = GameConstants.getLinkSkill(getJob());
        int lvl = getLevel() >= 120 ? 2 : getLevel() >= 70 ? 1 : 0;
        if (skillid == 100000271) {
            lvl = getLevel() >= 200 ? 5 : getLevel() >= 190 ? 4 : getLevel() >= 180 ? 3 : getLevel() >= 170 ? 2 : getLevel() >= 160 ? 1 : 0;
        }
        if (skillid != -1) {
            int link_skillid = getLinkSkillId(skillid);
            List<LinkSkill> linkskill = getLinkSkill(false, true, false);
            for (LinkSkill skill : linkskill) {
                if (skill.checkInfo(link_skillid, getId())) {
                    if (lvl == skill.getSkillLevel()) {
                        return;
                    }
                    if (getJob() >= 501 && getJob() <= 532) {
                        link_skill = new LinkSkill(getClient().getAccID(), skillid, 80000000, getId(), getId(), lvl, System.currentTimeMillis() - 86400000L);
                    } else {
                        link_skill = new LinkSkill(getClient().getAccID(), skillid, link_skillid, getId(), getId(), lvl, System.currentTimeMillis() - 86400000L);
                    }
                    break;
                }
            }
            if (link_skill == null) {
                if (getJob() >= 501 && getJob() <= 532) {
                    link_skill = new LinkSkill(getClient().getAccID(), skillid, 80000000, getId(), getId(), lvl, System.currentTimeMillis() - 86400000L);
                } else {
                    link_skill = new LinkSkill(getClient().getAccID(), skillid, link_skillid, getId(), getId(), lvl, System.currentTimeMillis() - 86400000L);
                }
            }
            addLinkSkill(link_skill);
            getClient().getSession().writeAndFlush(MainPacketCreator.updateLinkSkill(link_skill.getRealSkillId(), link_skill.getLinkingCid(), -1, link_skill.getTime()));
        }
    }

    public int getLinkSkillId(int paramInt) {
        int sid = -1;
        final String sname = SkillFactory.getSkillName(paramInt);
        MapleData data = null;
        MapleDataProvider dataProvider = MapleDataProviderFactory.getDataProvider(new File("wz/String.wz"));
        data = dataProvider.getData("Skill.img");
        List<Pair<Integer, String>> skillPairList = new LinkedList<Pair<Integer, String>>();
        for (MapleData skillIdData : data.getChildren()) {
            skillPairList.add(new Pair<Integer, String>(Integer.parseInt(skillIdData.getName()),
                    MapleDataTool.getString(skillIdData.getChildByPath("name"), "NO-NAME")));
        }
        for (Pair<Integer, String> skillPair : skillPairList) {
            if (skillPair.getRight().toLowerCase().contains(sname) && paramInt != skillPair.left) {
                sid = skillPair.left;
                break;
            }
        }
        data = null;
        dataProvider = null;
        return sid;
    }

    public void setWarpRand(int i) {
        this.WarpRand = i;
    }

    public int getWarpRand() {
        return this.WarpRand;
    }

    public void setDamageMeter(long i) {
        this.DamageMeter_ = i;
    }

    public void addDamageMeter(long i) {
        this.DamageMeter_ += i;
    }

    public long getDamageMeter() {
        return this.DamageMeter_;
    }

    public void removeAllEquip(int id, boolean show) {
        MapleInventoryType type = GameConstants.getInventoryType(id);
        int possessed = getInventory(type).countById(id);

        if (possessed > 0) {
            InventoryManipulator.removeById(getClient(), type, id, possessed, true, false);
            if (show) {
                getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, (short) -possessed, true));
            }
        }
        if (type == MapleInventoryType.EQUIP) {
            type = MapleInventoryType.EQUIPPED;
            possessed = getInventory(type).countById(id);

            if (possessed > 0) {
                IItem equip = getInventory(type).findById(id);
                if (equip != null) {
                    getInventory(type).removeSlot(equip.getPosition());
                    equipChanged();
                    getClient().getSession().writeAndFlush(
                            MainPacketCreator.removeInventoryItem(MapleInventoryType.EQUIP, equip.getPosition()));
                }
            }
        }
    }

    public static void checkFirstItem(final MapleCharacter chr) {
        for (IItem items : chr.getInventory(MapleInventoryType.EQUIPPED).list()) {
            if (!MapleLoginHelper.getInstance().isCreateItem(items.getItemId())) {
                chr.ban("ETC 조작 감지", true, true);
                chr.getClient().getSession().close();
            }
        }
    }

    public void LoginPointa() {
        this.fishings = new java.util.Timer();
        this.fishings.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (fishings != null) {
                    if (map.getAllMonster().size() > 0) {
                        if (탐지기 == false) {
                            dropMessage(5, "After an hour, the detector was activated. Enter <~ detector> in the chat window.");
                            send(MainPacketCreator.sendHint("After an hour, the detector was activated. In chat #e<~detector>#nPlease enter.", 500, 5));
                            탐지기 = true;
                        } else {
                            warp(910001000);
                        }
                    }
                }
            }
        }, 0, 3600000);
    }

    public void setLT(long time) {
        setKeyValue("loginTimer", Long.toString(time + ((1000 * 60) * 60)));
    }

    public long getLT() {
        if (getKeyValue("loginTimer") == null) {
            setLT(System.currentTimeMillis());
        }
        String key = getKeyValue("loginTimer");
        long loginTimer = Long.parseLong(key);
        if (loginTimer == 0) {
            setLT(System.currentTimeMillis());
        }
        return loginTimer;
    }
    public boolean logoutLPCheck = true;

    public long getLastLogoutTime() {
        String v = getKeyValue("lastLogout");

        if (v == null) {
            return 0;
        }

        return Long.parseLong(v);
    }

    public void giveLP() {
        loginpoint += (!getMap().getAllMonster().isEmpty() ? 1 : 1);
    }

    public int getLoginPoint() {
        return loginpoint;
    }

    public void addLoginPoint(int i) {
        this.loginpoint += i;
    }

    public boolean isEA() {
        return System.currentTimeMillis() >= eaTime;
    }

    public void updateEA() {
        eaTime = System.currentTimeMillis() + 5000;
        send(MainPacketCreator.SkillUseResult((byte) 0));
    }

    public boolean canHold(final int itemid) {
        return getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }

    public void addCP(int ammount) {
        totalCP += ammount;
        availableCP += ammount;
    }

    public void useCP(int ammount) {
        availableCP -= ammount;
    }

    public byte getTeam() {
        return this.team;
    }

    public void setTeam(byte team) {
        this.team = team;
    }

    public void resetCP() {
        totalCP = 0;
        availableCP = 0;
    }

    public void clearCarnivalRequests() {
        pendingCarnivalRequests = new LinkedList<>();
    }

    public int itemQuantity(final int itemid) {
        return getInventory(GameConstants.getInventoryType(itemid)).countById(itemid);
    }

    public void updateOneInfo(final int questid, final String key, final String value) {
        if (!questinfo.containsKey(questid) || key == null || value == null || MapleQuest.getInstance(questid) == null
                || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        final String[] split = questinfo.get(questid).split(";");
        boolean changed = false;
        final StringBuilder newQuest = new StringBuilder();
        for (String x : split) {
            final String[] split2 = x.split("="); // should be only 2
            if (split2.length != 2) {
                continue;
            }
            if (split2[0].equals(key)) {
                newQuest.append(key).append("=").append(value);
            } else {
                newQuest.append(x);
            }
            newQuest.append(";");
            changed = true;
        }

        updateInfoQuest(questid,
                changed ? newQuest.toString().substring(0, newQuest.toString().length() - 1) : newQuest.toString());
    }

    public void recalcPartyQuestRank(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        if (!startPartyQuest(questid)) {
            final String oldRank = getOneInfo(questid, "rank");
            if (oldRank == null || oldRank.equals("S")) {
                return;
            }
            String newRank;
            switch (oldRank) {
                case "A":
                    newRank = "S";
                    break;
                case "B":
                    newRank = "A";
                    break;
                case "C":
                    newRank = "B";
                    break;
                case "D":
                    newRank = "C";
                    break;
                case "F":
                    newRank = "D";
                    break;
                default:
                    return;
            }
            final List<Pair<String, Pair<String, Integer>>> questInfo = MapleQuest.getInstance(questid)
                    .getInfoByRank(newRank);
            if (questInfo == null) {
                return;
            }
            for (Pair<String, Pair<String, Integer>> q : questInfo) {
                boolean found = false;
                final String val = getOneInfo(questid, q.right.left);
                if (val == null) {
                    return;
                }
                int vall;
                try {
                    vall = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    return;
                }
                switch (q.left) {
                    case "less":
                        found = vall < q.right.right;
                        break;
                    case "more":
                        found = vall > q.right.right;
                        break;
                    case "equal":
                        found = vall == q.right.right;
                        break;
                }
                if (!found) {
                    return;
                }
            }
            // perfectly safe
            updateOneInfo(questid, "rank", newRank);
        }
    }

    public boolean startPartyQuest(final int questid) {
        boolean ret = false;
        MapleQuest q = MapleQuest.getInstance(questid);
        if (q == null || !q.isPartyQuest()) {
            return false;
        }
        if (!quests.containsKey(q) || !questinfo.containsKey(questid)) {
            final MapleQuestStatus status = getQuestNAdd(q);
            status.setStatus((byte) 1);
            updateQuest(status);
            switch (questid) {
                case 1300:
                case 1301:
                case 1302: // carnival, ariants.
                    updateInfoQuest(questid,
                            "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0;gvup=0;vic=0;lose=0;draw=0");
                    break;
                case 1303: // ghost pq
                    updateInfoQuest(questid,
                            "min=0;sec=0;date=0000-00-00;have=0;have1=0;rank=F;try=0;cmp=0;CR=0;VR=0;vic=0;lose=0");
                    break;
                case 1204: // herb town pq
                    updateInfoQuest(questid,
                            "min=0;sec=0;date=0000-00-00;have0=0;have1=0;have2=0;have3=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                case 1206: // ellin pq
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have0=0;have1=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                default:
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
            }
            ret = true;
        } // started the quest.
        return ret;
    }

    public String getOneInfo(final int questid, final String key) {
        if (!questinfo.containsKey(questid) || key == null || MapleQuest.getInstance(questid) == null
                || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return null;
        }
        final String[] split = questinfo.get(questid).split(";");
        for (String x : split) {
            final String[] split2 = x.split("="); // should be only 2
            if (split2.length == 2 && split2[0].equals(key)) {
                return split2[1];
            }
        }
        return null;
    }

    public void tryPartyQuest(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        try {
            startPartyQuest(questid);
            pqStartTime = System.currentTimeMillis();
            updateOneInfo(questid, "try", String.valueOf(Integer.parseInt(getOneInfo(questid, "try")) + 1));
        } catch (NumberFormatException e) {
            System.out.println("tryPartyQuest error");
        }
    }

    public void endPartyQuest(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        try {
            startPartyQuest(questid);
            if (pqStartTime > 0) {
                final long changeTime = System.currentTimeMillis() - pqStartTime;
                final int mins = (int) (changeTime / 1000 / 60), secs = (int) (changeTime / 1000 % 60);
                final int mins2 = Integer.parseInt(getOneInfo(questid, "min"));
                if (mins2 <= 0 || mins < mins2) {
                    updateOneInfo(questid, "min", String.valueOf(mins));
                    updateOneInfo(questid, "sec", String.valueOf(secs));
                    updateOneInfo(questid, "date", FileoutputUtil.CurrentReadable_Date());
                }
                final int newCmp = Integer.parseInt(getOneInfo(questid, "cmp")) + 1;
                updateOneInfo(questid, "cmp", String.valueOf(newCmp));
                updateOneInfo(questid, "CR", String
                        .valueOf((int) Math.ceil((newCmp * 100.0) / Integer.parseInt(getOneInfo(questid, "try")))));
                recalcPartyQuestRank(questid);
                pqStartTime = 0;
            }
        } catch (Exception e) {
            System.out.println("endPartyQuest error");
        }

    }

    public void havePartyQuest(final int itemId) {
        int questid, index = -1;
        switch (itemId) {
            case 1002798:
                questid = 1200; // henesys
                break;
            case 1072369:
                questid = 1201; // kerning
                break;
            case 1022073:
                questid = 1202; // ludi
                break;
            case 1082232:
                questid = 1203; // orbis
                break;
            case 1002571:
            case 1002572:
            case 1002573:
            case 1002574:
                questid = 1204; // herbtown
                index = itemId - 1002571;
                break;
            case 1102226:
                questid = 1303; // ghost
                break;
            case 1102227:
                questid = 1303; // ghost
                index = 0;
                break;
            case 1122010:
                questid = 1205; // magatia
                break;
            case 1032061:
            case 1032060:
                questid = 1206; // ellin
                index = itemId - 1032060;
                break;
            case 3010018:
                questid = 1300; // ariant
                break;
            case 1122007:
                questid = 1301; // carnival
                break;
            case 1122058:
                questid = 1302; // carnival2
                break;
            default:
                return;
        }
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        startPartyQuest(questid);
        updateOneInfo(questid, "have" + (index == -1 ? "" : index), "1");
    }

    public void changeSkillsLevel(final Map<ISkill, SkillEntry> ss) {
        changeSkillsLevel(ss, false);
    }

    public boolean changeSkillData(final ISkill skill, int newLevel, byte newMasterlevel, long expiration) {
        if (skill == null || (!GameConstants.isApplicableSkill(skill.getId())
                && !GameConstants.isApplicableSkill_(skill.getId()))) {
            return false;
        }
        if (newLevel == 0 && newMasterlevel == 0) {
            if (skills.containsKey(skill)) {
                skills.remove(skill);
            } else {
                return false; // nothing happen
            }
        } else {
            skills.put(skill, new SkillEntry((byte) newLevel, newMasterlevel, expiration));
        }
        return true;
    }

    public void reUpdateStat(boolean hasRecovery, boolean recalculate) {
        if (recalculate) {
            stats.recalcLocalStats(this);
        }
    }

    public void changeSkillsLevel(final Map<ISkill, SkillEntry> ss, boolean hyper) {
        if (ss.isEmpty()) {
            return;
        }
        final Map<ISkill, SkillEntry> list = new HashMap<>();
        boolean hasRecovery = false, recalculate = false;
        for (final Entry<ISkill, SkillEntry> data : ss.entrySet()) {
            if (changeSkillData(data.getKey(), data.getValue().skillevel, data.getValue().masterlevel,
                    data.getValue().expiration)) {
                list.put(data.getKey(), data.getValue());
                if (GameConstants.isRecoveryIncSkill(data.getKey().getId())) {
                    hasRecovery = true;
                }
                if (data.getKey().getId() < 80000000) {
                    recalculate = true;
                }
            }
        }
        if (list.isEmpty()) { // nothing is changed
            return;
        }
        client.getSession().writeAndFlush(MainPacketCreator.updateSkill(list));
        reUpdateStat(hasRecovery, recalculate);
    }

    public int getMorphState() {
        LinkedList<BuffStatsValueHolder> allBuffs = new LinkedList<BuffStatsValueHolder>();
        for (List<BuffStatsValueHolder> holders : effects.values()) {
            for (BuffStatsValueHolder bsvh : holders) {
                allBuffs.add(bsvh);
            }
        }
        for (BuffStatsValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMorph()) {
                return mbsvh.effect.getSourceId();
            }
        }
        return -1;
    }

    public void removeAll(int id) {
        removeAll(id, true);
    }

    public void removeAll(int id, boolean show) {
        MapleInventoryType type = GameConstants.getInventoryType(id);
        int possessed = getInventory(type).countById(id);

        if (possessed > 0) {
            InventoryManipulator.removeById(getClient(), type, id, possessed, true, false);
            if (show) {
                getClient().getSession().writeAndFlush(MainPacketCreator.getShowItemGain(id, (short) -possessed, true));
            }
        }
    }

    public int getIntNoRecord(int questID) {
        final MapleQuestStatus stat = getQuestNoAdd(MapleQuest.getInstance(questID));
        if (stat == null || stat.getCustomData() == null) {
            return 0;
        }
        return Integer.parseInt(stat.getCustomData());
    }

    public int getIntRecord(int questID) {
        final MapleQuestStatus stat = getQuestNAdd(MapleQuest.getInstance(questID));
        if (stat.getCustomData() == null) {
            stat.setCustomData("0");
        }
        return Integer.parseInt(stat.getCustomData());
    }

    public boolean hasSummon() {
        return hasSummon;
    }

    public void setHasSummon(boolean summ) {
        this.hasSummon = summ;
    }

    public void updateReward() {
        List<MapleReward> rewards = new LinkedList();
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM rewards WHERE `cid`=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    // rewards.last();
                    // int size = rewards.getRow();
                    // rewards.first();
                    // client.getSession().writeAndFlush(Reward.updateReward(rewards.getInt("id"),
                    // (byte) 9, rewards, size, 9));
                    while (rs.next()) {
                        rewards.add(new MapleReward(rs.getInt("id"), rs.getLong("start"), rs.getLong("end"),
                                rs.getInt("type"), rs.getInt("itemId"), rs.getInt("mp"), rs.getInt("meso"),
                                rs.getInt("exp"), rs.getString("desc")));
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to update rewards: " + e);
        }
    }

    public MapleReward getReward(int id) {
        MapleReward reward = null;
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM rewards WHERE `id` = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        reward = new MapleReward(rs.getInt("id"), rs.getLong("start"), rs.getLong("end"),
                                rs.getInt("type"), rs.getInt("itemId"), rs.getInt("mp"), rs.getInt("meso"),
                                rs.getInt("exp"), rs.getString("desc"));
                    }
                }
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to obtain reward information: " + e);
        }
        return reward;
    }

    public void addReward(int type, int item, int mp, int meso, int exp, String desc) {
        addReward(0L, 0L, type, item, mp, meso, exp, desc);
    }

    public void addReward(long start, long end, int type, int item, int mp, int meso, int exp, String desc) {
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO rewards (`cid`, `start`, `end`, `type`, `itemId`, `mp`, `meso`, `exp`, `desc`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, id);
                ps.setLong(2, start);
                ps.setLong(3, end);
                ps.setInt(4, type);
                ps.setInt(5, item);
                ps.setInt(6, mp);
                ps.setInt(7, meso);
                ps.setInt(8, exp);
                ps.setString(9, desc);
                ps.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to obtain reward: " + e);
        }
    }

    public void deleteReward(int id) {
        try {
            Connection con = MYSQL.getConnection();
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM rewards WHERE `id` = ?")) {
                ps.setInt(1, id);
                ps.execute();
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Unable to delete reward: " + e);
        }
        updateReward();
    }

    public void checkCustomReward(int level) {
        List<Integer> rewards = new LinkedList<>();
        int mp = 0;
        switch (level) {
            case 10:
                rewards.add(2450000);
                rewards.add(2022918);
                mp = 1000;
                break;
            case 20:
                rewards.add(2022918);
                rewards.add(1032099);
                mp = 3000;
                break;
            case 30:
                addReward(4, 0, 0, 5000000, 0, "Here is a special reward for beginners to help you start your journey!");// was
                rewards.add(2450000);
                rewards.add(1112659);
                mp = 5000;
                break;
            case 50:
                rewards.add(2022918);
                rewards.add(1003361);
                rewards.add(1082399);
                rewards.add(1102337);
                mp = 7500;
                break;
            case 70:
                rewards.add(2450000);
                rewards.add(2022918);
                rewards.add(1003016);
                mp = 10000;
                break;
            case 100:
                addReward(4, 0, 0, 50000000, 0, "Here is a special reward for the experts! Beta only tho.");// just for beta
                rewards.add(2450000);
                rewards.add(2022918);
                rewards.add(1122195);
                rewards.add(1132043);
                rewards.add(1152084);
                mp = 12500;
                break;
            case 120:
                rewards.add(2450000);
                rewards.add(1182007);
                mp = 15000;
                break;
            case 150:
                rewards.add(1142349);
                mp = 17500;
                break;
            case 170:
                rewards.add(1142295);
                mp = 20000;
                break;
            case 200:
                rewards.add(1142456);
                mp = 25000;
                break;
        }
        for (int reward : rewards) {
            addReward(1, reward, 0, 0, 0, "Here is a special reward for reaching Lv. " + level + "!");
        }
        if (mp != 0) {
            addReward(3, 0, mp, 0, 0, "Here is a special reward for reaching Lv. " + level + "!");
        }
        updateReward();
    }

    public void newCharRewards() {
        List<Integer> rewards = new LinkedList<>();
        rewards.add(2022680);
        rewards.add(2450031);
        rewards.add(1142358);
        for (int reward : rewards) {
            addReward(1, reward, 0, 0, 0, "Here is a special reward for beginners to help you start your journey!");
        }
        updateReward();
    }

    public int getPQLog(String pqid) {
        try {
            Connection con1 = MYSQL.getConnection();
            int ret_count = 0;
            PreparedStatement ps;
            ps = con1.prepareStatement(
                    "SELECT COUNT(*) FROM pqlog WHERE charid = ? and pqid = ? and lastattempt >= subtime(current_timestamp, '1 0:0:0.0')");
            ps.setInt(1, id);
            ps.setString(2, pqid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            } else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            con1.close();
            return ret_count;
        } catch (Exception Ex) {
            return -1;
        }
    }

    public void setPQLog(String pqid) {
        try {
            Connection con1 = MYSQL.getConnection();
            PreparedStatement ps;
            ps = con1.prepareStatement("INSERT INTO pqlog (accid, charid, pqid) values (?,?,?)");
            ps.setInt(1, accountid);
            ps.setInt(2, id);
            ps.setString(3, pqid);
            ps.executeUpdate();
            ps.close();
            con1.close();
        } catch (Exception Ex) {
        }
    }

    public void expandInventory(byte type, int amount) {
        final MapleInventory inv = getInventory(MapleInventoryType.getByType(type));
        inv.addSlot((byte) amount);
        client.getSession().writeAndFlush(MainPacketCreator.getSlotUpdate(type, (byte) inv.getSlotLimit()));
    }

    public int getStarterQuestID() {
        return starterquestid;
    }

    public int getStarterQuest() {
        return starterquest;
    }

    public void setStarterQuest(int p) {
        this.starterquest = p;
    }

    public void setStarterQuestID(int p) {
        this.starterquestid = p;
    }

    public final boolean haveItem(int itemid, int quantity) {
        return haveItem(itemid, quantity, true, true);
    }

    public long getLongNoRecord(int questID) {
        final MapleQuestStatus stat = getQuestNoAdd(MapleQuest.getInstance(questID));
        if (stat == null || stat.getCustomData() == null) {
            return 0;
        }
        return Long.parseLong(stat.getCustomData());
    }

    public void forceChangeChannel(final int channel) {
        final ChannelServer toch = ChannelServer.getInstance(channel);

        if (FieldLimitType.ChannelSwitch.check(getMap().getFieldLimit()) || channel == client.getChannel()) {
            client.getSession().close();
            return;
        } else if (toch == null || toch.isShutdown()) {
            client.getSession().writeAndFlush(MainPacketCreator.serverNotice(5, "Not currently accessible."));
            return;
        }
        if (getTrade() != null) {
            MapleUserTrade.cancelTrade(getTrade());
        }

        final IMapleCharacterShop shop = getPlayerShop();
        if (shop != null) {
            shop.removeVisitor(this);
            if (shop.isOwner(this)) {
                shop.setOpen(true);
            }
        }

        final ChannelServer ch = ChannelServer.getInstance(client.getChannel());
        if (getMessenger() != null) {
            WorldCommunity.silentLeaveMessenger(getMessenger().getId(), new MapleMultiChatCharacter(this));
        }
        try {
            cancelAllBuffs();
        } catch (Exception ex) {

        }

        try {
            cancelAllBuffs();
        } catch (Exception ex) {

        }
        // ChannelServer.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
        ChannelServer.addCooldownsToStorage(getId(), getAllCooldowns());
        ChannelServer.addDiseaseToStorage(getId(), getAllDiseases());
        ChannelServer.ChannelChange_Data(new ChracterTransfer(this), getId(), channel);
        ch.removePlayer(this);
        client.updateLoginState(MapleClient.CHANGE_CHANNEL, client.getSessionIPAddress());
        client.getSession().writeAndFlush(MainPacketCreator.getChannelChange(ServerConstants.basePorts + (channel),
                ServerConstants.getServerHost(client)));
        saveToDB(false, false);
        getMap().removePlayer(this);
        client.setPlayer(null);
    }

    public final void setQuestAdd(final MapleQuest quest, final byte status, final String customData) {
        if (!quests.containsKey(quest)) {
            final MapleQuestStatus stat = new MapleQuestStatus(quest, status);
            stat.setCustomData(customData);
            quests.put(quest, stat);
        }
    }

    public void forceCompleteQuest(int id) {
        MapleQuest.getInstance(id).forceComplete(this, 9270035); // troll
    }

    public void setCoreq(int q) {
        this.coreq = q;
    }

    public void gainCoreq(int q) {
        this.coreq += q;
        updateInfoQuest(1477, "count=" + coreq);
    }

    public int getCoreq() {
        return this.coreq;
    }

    public void setBodyOfSteel(int bodyOfSteel) {
        this.bodyOfSteel = bodyOfSteel;
    }

    public void addBodyOfSteel() {
        this.bodyOfSteel++;
    }

    public int getBodyOfSteel() {
        return bodyOfSteel;
    }

    private ScheduledFuture _angelOfLibraTask = null;

    public void addVisibleMapObject(MapleMapObject mo) {
        visibleMapObjectsLock.writeLock().lock();
        try {
            visibleMapObjects.add(mo);
        } finally {
            visibleMapObjectsLock.writeLock().unlock();
        }
    }

    public void removeVisibleMapObject(MapleMapObject mo) {
        visibleMapObjectsLock.writeLock().lock();
        try {
            visibleMapObjects.remove(mo);
        } finally {
            visibleMapObjectsLock.writeLock().unlock();
        }
    }

    public boolean isMapObjectVisible(MapleMapObject mo) {
        visibleMapObjectsLock.readLock().lock();
        try {
            return visibleMapObjects.contains(mo);
        } finally {
            visibleMapObjectsLock.readLock().unlock();
        }
    }

    public Collection<MapleMapObject> getAndWriteLockVisibleMapObjects() {
        visibleMapObjectsLock.writeLock().lock();
        return visibleMapObjects;
    }

    public void unlockWriteVisibleMapObjects() {
        visibleMapObjectsLock.writeLock().unlock();
    }

    public void setTowerChairSetting(String a1) {
        TowerChairSetting = a1;
    }

    public String getTowerChairSetting() {
        if (TowerChairSetting == null || TowerChairSetting == "") {
            return "#0";
        }
        return TowerChairSetting;
    }

    public ScheduledFuture<?> getMesoChairTimer() {
        return MesoChairTimer;
    }

    public void setMesoChairTimer(ScheduledFuture<?> a1) {
        MesoChairTimer = a1;
        tempmeso = 0;
    }

    public int getMesoChairCount() {
        return MesoChairCount > 999999999 ? 999999999 : MesoChairCount;
    }

    public void UpdateMesoChairCount(int a1) {
        if (tempmeso >= a1) {
            MesoChairTimer.cancel(true);
            MesoChairTimer = null;
            setChair(0);
            setChairText(null);
            getClient().getSession().writeAndFlush(MainPacketCreator.cancelChair(this, -1));
            getMap().broadcastMessage(this, MainPacketCreator.showChair(this.id, 0, null), true);
            return;
        }
        MesoChairCount = MesoChairCount + 500;
        gainMeso(-500, false);
        tempmeso += 500;
        getMap().broadcastMessage(SLFCGPacket.MesoChairPacket(getId(), 500, getChair()));
    }

    public int getFrozenMobCount() {
        return FrozenMobCount;
    }

    public void setFrozenMobCount(int a1) {
        FrozenMobCount = a1;
        if (FrozenMobCount < 0) {
            FrozenMobCount = 0;
            getMap().killAllMonsters(true);
            getClient().getSession().writeAndFlush(MainPacketCreator.OnAddPopupSay(1052230, 3500, "#face1#No monster left in frozen link!", ""));
        }
        getClient().getSession().writeAndFlush(SLFCGPacket.FrozenLinkMobCount(FrozenMobCount));
    }

    public int getBlockCount() {
        return BlockCount;
    }

    public void setBlockCount(int a1) {
        BlockCount = a1;
    }

    public void setBlockCoin(int a1) {
        BlockCoin = a1;
    }

    public int getBlockCoin() {
        return BlockCoin;
    }

    public void addBlockCoin(int a1) {
        BlockCoin += a1;
    }

    public void performAngelOfLibra() {
        final MapleCharacter chr = this;
        final SkillStatEffect effect = SkillFactory.getSkill(400021032).getEffect(getSkillLevel(400021032));

        if (_angelOfLibraTask != null) {
            _angelOfLibraTask.cancel(false);
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (Pair<Integer, MapleSummon> summon : chr.getSummons().values()) {
                    if (summon.getLeft() == 400021032) {
                        List<MapleCharacter> toBuff = new LinkedList<MapleCharacter>();
                        toBuff.add(chr);

                        if (chr.getPartyId() != -1) {
                            final Rectangle bounds = effect.calculateBoundingBox(chr.getPosition(), chr.isFacingLeft());
                            final List<MapleMapObject> affecteds = chr.getMap().getMapObjectsInRect(bounds,
                                    Arrays.asList(MapleMapObjectType.PLAYER));

                            for (MapleMapObject chrObj : affecteds) {
                                MapleCharacter chr = (MapleCharacter) chrObj;

                                if (chr.getParty() != null) {
                                    for (MapleMapObject mo : affecteds) {
                                        MapleCharacter hp = (MapleCharacter) mo;
                                        if (hp.getPartyId() == chr.getPartyId()) {
                                            toBuff.add(hp);
                                        }
                                    }
                                }
                            }
                        }

                        List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
                        statups.add(new Triple<>(BuffStats.IndieDamR,
                                Integer.max(0,
                                        Integer.min(effect.getDamage(), effect.getStat("w")
                                                + (int) (chr.getStat().getInt() / effect.getStat("attackCount")))),
                                true));

                        for (MapleCharacter target : toBuff) {
                            if (!target.isAlive()) {
                                continue;
                            }

                            target.addHP(target.getMaxHp() * (effect.getY() / 100));
                            target.cancelEffect(effect, true, -1);

                            long overlap_magic = (long) (System.currentTimeMillis() % 1000000000);
                            int localDuration = effect.getStat("subTime") * 1000;
                            Map<BuffStats, List<StackedSkillEntry>> stacked = target.getStackSkills();
                            for (Triple<BuffStats, Integer, Boolean> statup : statups) {
                                if (statup.getThird()) {
                                    if (!stacked.containsKey(statup.getFirst())) {
                                        stacked.put(statup.getFirst(), new ArrayList<StackedSkillEntry>());
                                    }
                                    stacked.get(statup.getFirst()).add(new StackedSkillEntry(400021032,
                                            statup.getSecond(), overlap_magic, localDuration));
                                }
                            }

                            target.getClient().getSession()
                                    .writeAndFlush(MainPacketCreator.giveBuff(400021032, localDuration, statups, effect,
                                            stacked, SkillFactory.getSkill(400021032).getAnimationTime(), target));
                            final long starttime = System.currentTimeMillis();
                            target.registerEffect(effect, starttime,
                                    tools.Timer.BuffTimer.getInstance().schedule(
                                            new SkillStatEffect.CancelEffectAction(target, effect, starttime),
                                            ((starttime + localDuration) - System.currentTimeMillis())));
                            target.getMap().broadcastMessage(target, MainPacketCreator.giveForeignBuff(target, statups),
                                    false);
                        }
                        performAngelOfLibra();
                    }
                }
            }
        };

        _angelOfLibraTask = BuffTimer.getInstance().schedule(r, effect.getX() * 1000);
    }

    public int trueSniping = 0;
    public int readyToDie = 0;
    public int shadowAssault = 0;
    public int transformEnergyOrb = 0;
    public int loadedDice = -1;
    public long bulletPartyStartTime = 0;
    public long lastIncSkillTime = 0;
    public long lastBHGCGiveTime = 0;
    public int BHGCCount = 0;

    public void updateSymbol() {
        for (IItem item : getSymbols()) {
            getClient().getSession().write(MainPacketCreator.updateInventorySlot(
                    item.getPosition() < 0 ? MapleInventoryType.EQUIPPED : MapleInventoryType.EQUIP, item, false));
        }
    }

    public List<IItem> getSymbols() {
        return symbols;
    }

    public int flameDischarge = 0;
    public int mesoCount = 0;
    public long lastHowlingGaleTime = 0;
    public long lastDemonicFrenzyTime = 0;
    public int HowlingGaleCount = 0;

    public int getNextSCIdx() {
        return this.shieldChaching.size() + 1;
    }

    public void addSC(int id) {
        this.shieldChaching.put(id, 8);
    }

    public void addSC2(int id) {
        this.shieldChaching.put(id, 19);
    }

    public void minSC(int id) {
        this.shieldChaching.put(id, shieldChaching.get(id) - 1);
    }

    public int getSC(int id) {
        return this.shieldChaching.get(id);
    }

    public void removeSC(int id) {
        this.shieldChaching.remove(id);
    }

    public void gianHP(int i) {
        int p = getKeyValue("hpoint") == null ? 0 : Integer.parseInt(getKeyValue("hpoint"));
        p += i;
        setKeyValue("hpoint", String.valueOf(p));
    }

    public int getHP() {
        return getKeyValue("hpoint") == null ? 0 : Integer.parseInt(getKeyValue("hpoint"));
    }

    public long lastChainArtsFuryTime;

    public void setMixColor(int a, int b, int c) {
        setKeyValue("MixColor", "BaseColor=" + a + ";AddColor=" + b + ";BaseProb=" + c + ";");
    }

    public int getBaseColor() {
        if (getKeyValue("MixColor") == null) {
            return -1;
        }
        return Integer.parseInt(getKeyValue("MixColor").split("BaseColor=")[1].split(";")[0]);
    }

    public int getAddHairColor() {
        if (getKeyValue("MixColor") == null) {
            return 0;
        }
        return Integer.parseInt(getKeyValue("MixColor").split("AddColor=")[1].split(";")[0]);
    }

    public int getBaseProb() {
        if (getKeyValue("MixColor") == null) {
            return 0;
        }
        return Integer.parseInt(getKeyValue("MixColor").split("BaseProb=")[1].split(";")[0]);
    }

    public boolean isMegaSmasherCharging = false;
    public long megaSmasherChargeStartTime = 0;

    public int BLESS_MARK = 0;

    public Rectangle getBounds() {
        return new Rectangle(getTruePosition().x - 25, getTruePosition().y - 75, 50, 75);
    }

    public int DOMINION_MAP = -1;
    public long DOMINION_TIME = -1;
    private BingoGame BingoInstance = null;

    public BingoGame getBingoGame() {
        return BingoInstance;
    }

    public void setBingoGame(BingoGame a1) {
        BingoInstance = a1;
    }

    private int shadowBatMobCount = 3;
    private int shadowAttackCount = 0;
    private int nightWalkerAttackCount = 0;

    public int getShadowBatMobCount() {
        return shadowBatMobCount;
    }

    public void setShadowBatMobCount(int shadowBatMobCount) {
        this.shadowBatMobCount = shadowBatMobCount;
    }

    public int getShadowAttackCount() {
        return shadowAttackCount;
    }

    public void setShadowAttackCount(int attackCount) {
        this.shadowAttackCount = attackCount;
    }

    public int getNightWalkerAttackCount() {
        return nightWalkerAttackCount;
    }

    public void setNightWalkerAttackCount(int nightWalkerAttackCount) {
        this.nightWalkerAttackCount = nightWalkerAttackCount;
    }

    public int getDeathCount() {
        return DeathCount_;
    }

    public void setDeathCount(int count) {
        DeathCount_ = count;
    }

    public int getSaveTime() {
        return savetime;
    }

    public void setSaveTime(int time) {
        savetime = time;
    }

    public void cancelSaveTimeLimitTask() {
        if (savetimeTask != null) {
            savetimeTask.cancel(false);
            savetimeTask = null;
        }
    }

    public void startMapSaveTimeLimitTask(int time, final MapleMap to) {
        if (time <= 0) { //jail
            time = 1;
        }
        client.getSession().writeAndFlush(MainPacketCreator.getClock(time));
        final MapleMap ourMap = getMap();
        setSaveTime(time);
        time *= 1000;
        if (savetimeTask != null) {
            savetimeTask.cancel(true);
            savetimeTask = null;
        }
        savetimeTask = EtcTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                if (savetime > 0) {
                    savetime--;
                } else {
                    cancelSaveTimeLimitTask();
                }
            }
        }, 1000);

        mapTimeLimitTask = EtcTimer.getInstance().register(new Runnable() {

            @Override
            public void run() {
                changeMap(to, to.getPortal(0));
            }
        }, time, time);
    }

    public int getBlockGameLimit(String name) {
        int i = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                int getlimit = rs.getInt("blockgamelimit");
                i = getlimit;
            }
        } catch (SQLException e) {
            System.err.println("Error getting character default" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("" + e);
            }
        }
        return i;
    }

    public void setBlockGameLimit(int i, String name) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("UPDATE accounts SET blockgamelimit = ? WHERE name = ?");
            ps.setInt(1, i);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SET LIMIT ERROR" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Error getting character default" + e);
            }
        }
    }

    public int getBlockGameDays(String name) {
        int i = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                int getdays = rs.getInt("blockgamedays");
                i = getdays;
            }
        } catch (SQLException e) {
            System.err.println("" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.err.println("" + e);
            }
        }
        return i;
    }

    public void setBlockGameDays(int i, String name) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("UPDATE accounts SET blockgamedays = ? WHERE name = ?");
            ps.setInt(1, i);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SET DAYS ERROR" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("" + e);
            }
        }
    }

    public void 귀문진시작(SkillStatEffect eff) {
        MapleCharacter chr = this;

        BuffTimer tMan = BuffTimer.getInstance();
        Runnable r;
        r = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (chr.isActiveBuffedValue(400051022)) {
                    SkillStatEffect a = SkillFactory.getSkill(400051023).getEffect(chr.getSkillLevel(400051022));
                    MapleSummon summon = new MapleSummon(chr, 400051023, a.getDuration(), chr.summonxy, SummonMovementType.귀문진, System.currentTimeMillis());
                    chr.getMap().spawnSummon(summon, true, a.getDuration());
                } else {
                    ej.cancel(true);
                    ej = null;
                }
            }
        };

        ej = tMan.register(r, 1000, 1000);
    }

    public void PhantomSkillDelete() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int TestCount = 0;

        try {
            con = MYSQL.getConnection();
            ps = con.prepareStatement("SELECT * FROM steelskills WHERE cid = ?");
            ps.setInt(1, this.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                TestCount++;
                StealSkillEntry sse = getStealSkills().getSkillEntryById(rs.getInt("skillid"));
                send(PhantomSkill.getSteelSkillCheck(getId(), true, sse, true));
                steelskills.deleteSkill(rs.getInt("skillid"));
                ea();
                PreparedStatement delete = con.prepareStatement("DELETE FROM steelskills WHERE cid = ?");
                delete.setInt(1, this.getId());
                delete.execute();
                delete.close();
            }
            saveSteelSkills();
            System.out.println("[Phantom Skills] Steal Skill, " + TestCount + "Deleted");
        } catch (SQLException e) {
            System.err.println("[Phantom Skill] Steel skill deletion error : " + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.err.println("[Phantom Skill] Close Error : " + ex);
            }
        }
    }

    public int pocket = 0;

    public void addpocket() {
        SkillStatEffect effect = SkillFactory.getSkill(4211003).getEffect(getSkillLevel(4211003));
        List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
        statups.add(new Triple<BuffStats, Integer, Boolean>(BuffStats.PickPocket, 1, false));
        this.send(MainPacketCreator.giveBuff(4211003, Integer.MAX_VALUE, statups, effect, this.getStackSkills(), 0, this));
    }

    public int getInfinitycount() {
        return Infinitycount;
    }

    public void setInfinitycount(int Infinitycount) {
        this.Infinitycount = Infinitycount;
    }

    public long getInfinitytime() {
        return Infinitytime;
    }

    public void setInfinitytime(long Infinitytime) {
        this.Infinitytime = Infinitytime;
    }

    public int getInfinityStack() {
        return infinityStack;
    }

    public void setInfinityStack(int infinityStack) {
        this.infinityStack = infinityStack;
    }

    public int 마법잔해 = 0;
    public List<Point> Pointxy = new ArrayList<Point>();

    public int getBlessMarkSkillID() {
        int blessMark = 0;
        if (getSkillLevel(152000007) > 0) { //Bless Mark
            blessMark = 152000007;
            if (getSkillLevel(152110009) > 0) { //Blessmark Skilled
                blessMark = 152110009;
            }
            if (getSkillLevel(152120012) > 0) { //Blessmark Skilleds
                blessMark = 152120012;
            }
        }
        return blessMark;
    }

    public int shape = 0;

    public void GivePassPinderShape(int skillid) {
        List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
        switch (skillid) {
            case 3011004:
            case 3300002:
            case 3321003:

                shape = 1;
                statups.add(new Triple<>(BuffStats.PASS_PINDER_SHAPE, 1, false));
                break;
            case 3321004:
            case 3310001:
            case 3310004:
            case 3311013:
            case 3321005:
            case 3301003:
            case 3301004:
                shape = 2;
                statups.add(new Triple<>(BuffStats.PASS_PINDER_SHAPE, 2, false));
                break;
            case 3311002:
            case 3311003:
            case 3320001:
            case 3320008:
            case 3321006:
            case 3321007:
                shape = 3;
                statups.add(new Triple<>(BuffStats.PASS_PINDER_SHAPE, 3, false));
                break;
        }
        SkillStatEffect Effect = SkillFactory.getSkill(skillid).getEffect(getSkillLevel(skillid));
        send(MainPacketCreator.giveBuff(2, 0, statups, Effect, getStackSkills(),
                SkillFactory.getSkill(400051033).getAnimationTime(), this));
    }

    private int RelicValue = 0;

    public void setRelicCount(int value) {
        if (value >= 1000) {
            RelicValue = 1000;
        } else if (value < 0) {
            RelicValue = 0;
        } else {
            RelicValue = value;
        }
        if (!this.isActiveBuffedValue(3310006) && RelicValue == 1000) {
            SkillStatEffect effects = SkillFactory.getSkill(3310006).getEffect(this.getSkillLevel(3310006));
            effects.applyTo(this);
        }

        List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
        statups.add(new Triple<>(BuffStats.RelicTitleGage, 0, false));
        statups.add(new Triple<>(BuffStats.RelicGage, -1, false));
        send(MainPacketCreator.giveBuff(332, 0, statups, null, getStackSkills(),
                0, this));
    }

    public int getRelicCount() {
        return RelicValue;
    }

    public int[] ArkMable = {0, 0, 0, 0};

    public void AddArkMable(int skillid) {
        if (((ArkMable[0] / 2) + ArkMable[1] + ArkMable[2] + ArkMable[3]) < 5) {
            List<Triple<BuffStats, Integer, Boolean>> statups = new ArrayList<>();
            switch (skillid) {
                case 155001100:
                    ArkMable[0] = ArkMable[0] + 2;
                    statups.add(new Triple<>(BuffStats.ArkChargeMable, 1, false));
                    break;
                case 155101100:
                case 155101101:
                    ArkMable[1] = ArkMable[1] + 1;
                    statups.add(new Triple<>(BuffStats.ArkChargeMableRed, 1, false));
                    break;
                case 155111102:
                    ArkMable[2] = ArkMable[2] + 1;
                    statups.add(new Triple<>(BuffStats.ArkChargeMableBlue, 1, false));
                    break;
                case 155121102:
                    ArkMable[3] = ArkMable[3] + 1;
                    statups.add(new Triple<>(BuffStats.ArkChargeMableGRAY, 1, false));
                    break;
                case 155001103:
                    statups.add(new Triple<>(BuffStats.ArkChargeMable, 1, false));
                    statups.add(new Triple<>(BuffStats.ArkChargeMableRed, 1, false));
                    statups.add(new Triple<>(BuffStats.ArkChargeMableBlue, 1, false));
                    statups.add(new Triple<>(BuffStats.ArkChargeMableGRAY, 1, false));
                    break;
            }
            send(MainPacketCreator.giveBuff(0, 0, statups, null, getStackSkills(),
                    0, this));
        }
    }

    public void ResetArkMable() {
        for (int i = 0; i < 4; i++) {
            ArkMable[i] = 0;
        }
    }

    private int ArkGage = 0;
    public int arkatom = 0;

    public void setArkGage(int value) {
        ArkGage = value;
    }

    public int getArkGage() {
        return ArkGage;
    }

    public int getCristalCharge() {
        return this.CristalCharge;
    }

    public void setCristalCharge(final int CristalCharge) {
        this.CristalCharge = CristalCharge;
    }

    public void addCristalCharge(final int n) {
        int stack = 30;
        if (this.getTotalSkillLevel(152110008) > 0) {
            stack = 150;
        }
        this.CristalCharge = Math.min(stack, this.CristalCharge + n);
    }

    public int getCristalLevel() {
        int level = 0;
        if (this.CristalCharge >= 30 && this.CristalCharge < 60) {
            level = 1;
        } else if (this.CristalCharge >= 60 && this.CristalCharge < 90) {
            level = 2;
        } else if (this.CristalCharge >= 90 && this.CristalCharge < 150) {
            level = 3;
        } else if (this.CristalCharge >= 150) {
            level = 4;
        }
        return level;
    }

    public void resetEnableCristalSkill(final int n) {
        this.CristalCharge = 0;
        this.saveCrystalskill = 1;
        this.saveCrystalskill1 = 1;
        this.saveCrystalskill2 = 1;
        this.saveCrystalskill3 = 1;
        this.getMap().broadcastMessage(this, MainPacketCreator.EnableCrystal(this, n, 2, 0), true);
    }

    public void handleCristalCharge(int cristalLevel, final int n) {
        int n2 = 0;
        if (cristalLevel == 152001002 || cristalLevel == 152120003) {
            n2 = 2;
        } else if (cristalLevel == 152001001 || cristalLevel == 152120001 || cristalLevel == 152120002) {
            n2 = 10;
        } else if (cristalLevel == 152121004) {
            n2 = 100;
        }
        if (this.getBuffedValue(BuffStats.FastCharge) != null) {
            n2 *= 2;
        }
        cristalLevel = this.getCristalLevel();
        this.addCristalCharge(n2);
        if (this.getCristalCharge() >= 30 && this.getCristalCharge() < 60) {
            this.getMap().broadcastMessage(this, MainPacketCreator.EnableCrystal(this, n, 2, 1), true);
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, n, 3), true);
        } else if (this.getCristalCharge() >= 60 && this.getCristalCharge() < 90 && cristalLevel == 1) {
            this.getMap().broadcastMessage(this, MainPacketCreator.EnableCrystal(this, n, 2, 2), true);
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, n, 3), true);
        } else if (this.getCristalCharge() >= 90 && this.getCristalCharge() < 150 && cristalLevel == 2) {
            this.getMap().broadcastMessage(this, MainPacketCreator.EnableCrystal(this, n, 2, 3), true);
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, n, 3), true);
        } else if (this.getCristalCharge() >= 150 && cristalLevel == 3) {
            this.getMap().broadcastMessage(this, MainPacketCreator.EnableCrystal(this, n, 2, 4), true);
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, n, 3), true);
        }
        this.getMap().broadcastMessage(this, MainPacketCreator.CrystalSkill(this, n, 2), true);
        if (cristalLevel != this.getCristalLevel()) {
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalUpdate(this, n, 3), true);
        } else {
            this.getMap().broadcastMessage(this, MainPacketCreator.CrystalUpdate(this, n, 2), true);
        }
        if (this.getCristalCharge() >= 150) {
            cristalLevel = 152110008;
            if (this.getSkillLevel(152120014) > 0) {
                cristalLevel = 152120014;
            }
            if (this.getBuffedValue(BuffStats.CrystalChargeMax, cristalLevel) == null) {
                SkillStatEffect a = SkillFactory.getSkill(cristalLevel).getEffect(this.getSkillLevel(cristalLevel));
                a.applyTo(this);
            }
        }

    }

    public int getCrystalOid() {
        visibleMapObjectsLock.writeLock().lock();
        try {
            for (Pair<Integer, MapleSummon> summon : getSummons().values()) {
                if (summon.getLeft() == 152101000) {
                    return summon.getRight().getObjectId();
                }
            }
        } finally {
            visibleMapObjectsLock.writeLock().unlock();
        }
        return 0;
    }

    private int hamonycount = 0;

    public void setHamonyCount(int i) {
        this.hamonycount = i;
    }

    public int getHamonyCount() {
        return this.hamonycount;
    }

    public int lastSkill = 0;
    public long lastSkillTime = 0;
    public int ObjectId = 0;

    public void addBossClearDB(String bossname) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO bossclear VALUES (NULL, ?, ?, ?)");
            ps.setInt(1, getId());
            ps.setLong(2, System.currentTimeMillis());
            ps.setString(3, bossname);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
    }

    public Long getBossClearDB(String bossname) {
        Long time = null;
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bossclear WHERE cid = ? AND bossname = ?");
            ps.setInt(1, getId());
            ps.setString(2, bossname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                time = rs.getLong("cleartime");
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            if (!ServerConstants.realese) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public void removeBossClearDB(String bossname) {
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con
                    .prepareStatement("DELETE FROM bossclear WHERE cid = ? AND bossname = ?");
            ps.setInt(1, getId());
            ps.setString(2, bossname);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            if (!ServerConstants.realese) {
                ex.printStackTrace();
            }
        }
    }

    public String checkBossClearDB(int limit, String bossname) {
        Long clearTime = getBossClearDB(bossname);
        if (clearTime != null) {
            long time = System.currentTimeMillis() - clearTime;
            long remainingTime = (limit * 24 * 60 * 60 * 1000) - time;
            if (remainingTime <= 0) {
                return null;
            }
            remainingTime /= 1000;

            long day = remainingTime / (24 * 60 * 60);
            long temp = remainingTime - (day * 24 * 60 * 60);

            long hour = temp / (60 * 60);
            temp = temp - (hour * 60 * 60);

            long min = temp / 60;
            temp = temp - (min * 60);

            long sec = temp;

            return day + " day " + hour + " hour " + min + " min " + sec + " sec";
        }
        return null;
    }

    private transient Map<Equip, Integer> equipcustomValue = new LinkedHashMap<Equip, Integer>();

    public int getEquipCustomValues(final Equip equip) {
        if (equipcustomValue.containsKey(equip)) {
            return equipcustomValue.get(equip);
        }
        return -1;
    }

    public void changeEquipCustomValue(final Equip equip, final int value) {
        equipcustomValue.put(equip, value);
    }

    public void setEquipCustomValue(final Equip equip, final int value) {
        if (equipcustomValue.get(equip) != null) {
            equipcustomValue.remove(equip);
        }
        equipcustomValue.put(equip, value);
    }

    public void removeEquipCustomValue(final Equip equip) {
        if (equipcustomValue.get(equip) != null) {
            equipcustomValue.remove(equip);
        }
    }

    public int combokillexp;

    public boolean checkDate() {
        String date = "";
        try {
            Connection con = MYSQL.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, getAccountID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                date = rs.getString("createdat");
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (date.length() > 0) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            int time = Integer.parseInt(date.substring(11, 13));
            if (year == 2019 && month <= 8) {
                if (month == 8) {
                    if (day < 5) {
                        return true;
                    } else if (day == 5) {
                        if (time <= 14) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkBoSang() {
        if (getKeyValue3("bosang__") == -1) {
            return false;
        }
        return true;
    }

    public void recvBoSang() {
        setKeyValue3("bosang__", 1);
    }
    private List<ForceAtom> atoms = new ArrayList<>();

    public List<ForceAtom> getAtoms() {
        return atoms;
    }

    public void setAtoms(List<ForceAtom> atoms) {
        this.atoms = atoms;
    }

    private int Atom_Count = 0;

    public int getAtom_Count() {
        return Atom_Count;
    }

    public void setAtom_Count(int atom_Count) {
        Atom_Count = atom_Count;
    }
    public List<Point> AtomPos = new ArrayList<Point>();

    public List<Point> getAtomPos() {
        return AtomPos;
    }

    public List<Integer> AtomToMonster = new ArrayList<Integer>();

    public List<Integer> getAtomsToMonster() {
        return AtomToMonster;
    }

    public int atomsa = 1;

    public void setAtomsa(int i) {
        this.atomsa = i;
    }

    public int getAtomsa() {
        return atomsa;
    }
    
    public int aa = 0;
    public void setaa(int value) {
        if(aa > 8 && value != 0) {
            return;
        }
        aa = value;
    }
    
    public int getaa() {
        return aa;
    }

    public int saveSkill = 0;
    public void setsaveSkill(int value) {
        saveSkill = value;
    }
    
    public long getsaveSkill() {
        return saveSkill;
    }
    
    public long times = 0;
    public void settimes(long value) {
        times = value;
    }
    
    public long gettimes() {
        return times;
    }
    
    public void stackCool(int skillId, long startTime, long length) {
        if (skillId == 31121003) {
            if (this.isActiveBuffedValue(31121007)) {
                length /= 2;
            }
        }
        if (skillId == 24121052) {
            length /= 2;
        }
        if (skillId == 400011031 && this.isActiveBuffedValue(400011016)) {
            length /= 2;
        }
        if (skillId == 400031040) {
            length = 2;
        }
        coolDowns.put(skillId, new CoolDownValueHolder(skillId, startTime, length));
    }
    
    
    public int bb = 0;
    public void setbb(int value) {
        if(bb >= 12 && value != 0) {
            return;
        }
        bb = value;
    }
    public int getbb() {
        return bb;
    }


    public void removeKeyValue_new(int type, String key) {
        MapleQuest quest = MapleQuest.getInstance(type);
        final MapleQuestStatus stat = quests.get(quest);
        if (stat == null) { // key ?곗??곌?  null
            MapleQuestStatus qs = new MapleQuestStatus(quest, (byte)1);
            updateInfoQuest(type, qs.getCustomData());
            return;
        }
        if (stat.getCustomData() == null) {
            return;
        }
        String[] data = stat.getCustomData().split(";");
        for (String s : data) {
            if (s.startsWith(key + "=")) { // 湲곗〈??議댁???? ?곗??????
                String newkey = stat.getCustomData().replace(s + ";", "");
                stat.setCustomData(newkey);
                updateInfoQuest(type, stat.getCustomData());
                return;
            }
        }
        stat.setCustomData(stat.getCustomData()); // 湲곗〈 ?ㅻ꺼瑜?? ???곗???異??.
        updateInfoQuest(type, stat.getCustomData());
    }

    public void removeKeyValue_new(int type) {
        MapleQuest quest = MapleQuest.getInstance(type);
        final MapleQuestStatus stat = quests.get(quest);
        if (stat == null) { // key ?곗??곌?  null
            return;
        }
        if (stat.getCustomData() == null) {
            return;
        }
        stat.setCustomData("");
        updateInfoQuest(type, stat.getCustomData());
    }

    public void setKeyValue_new(int type, String key, String value) {
        MapleQuest quest = MapleQuest.getInstance(type);
        final MapleQuestStatus stat = quests.get(quest);
        if (stat == null) {
            MapleQuestStatus qs = new MapleQuestStatus(quest, (byte)1);
            qs.setCustomData(key + "=" + value + ";");
            quests.put(quest, qs);
            updateInfoQuest(type, qs.getCustomData());
            return;
        }
        if (stat.getCustomData() == null) {
            return;
        }
        String[] data = stat.getCustomData().split(";");
        for (String s : data) {
            if (s.startsWith(key + "=")) {
                String newkey = stat.getCustomData().replace(s, key + "=" + value);
                stat.setCustomData(newkey);
                updateInfoQuest(type, stat.getCustomData());
                return;
            }
        }
        stat.setCustomData(stat.getCustomData() + key + "=" + value + ";");
        updateInfoQuest(type, stat.getCustomData());
    }

    public long getKeyValue_new(int type, String key) {
        MapleQuest quest = MapleQuest.getInstance(type);
        MapleQuestStatus q = quests.get(quest);
        if (q == null) {
            return -1;
        }
        if (q.getCustomData() == null) {
            return -1;
        }
        String[] data = q.getCustomData().split(";");
        for (String s : data) {
            if (s.startsWith(key + "=")) {
                String newkey = s.replace(key + "=", "");
                String newkey2 = newkey.replace(";", "");
                long dd = Long.valueOf(newkey2);
                return dd;
            }
        }
        return -1; // 誘몄〈?ъ? -1
    }

    public long getDojoStartTime() {
        return dojoStartTime;
    }

    public void setDojoStartTime(long dojoStartTime) {
        this.dojoStartTime = dojoStartTime;
    }

    public long getDojoStopTime() {
        return dojoStopTime;
    }

    public void setDojoStopTime(long dojoStopTime) {
        this.dojoStopTime = dojoStopTime;
    }

    public long getDojoCoolTime() {
        return dojoCoolTime;
    }

    public void setDojoCoolTime(long dojoCoolTime) {
        this.dojoCoolTime = dojoCoolTime;
    }
}