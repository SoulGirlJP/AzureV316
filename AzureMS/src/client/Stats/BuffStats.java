package client.Stats;

import java.io.Serializable;
import java.math.BigInteger;

public enum BuffStats implements Serializable, GlobalBuffStat {
    ComboDrain(473), // Yet
    
    IndiePAD(0),
    IndieMAD(1),
    IndiePDD(2),
    IndieMHP(3),
    IndieMHPR(4),
    IndieMMP(5),
    IndieMMPR(6),
    IndieACC(7),
    IndieEVA(8),
    IndieJump(9),
    IndieSpeed(10),
    IndieAllStat(11),
    IndieDodgeCriticalTime(13),
    IndieEXP(14),
    IndieBooster(15),
    IndieFixedDamageR(16),
    PyramidStunBuff(17),
    PyramidFrozenBuff(18),
    PyramidFireBuff(19),
    PyramidBonusDamageBuff(20),
    IndieRelaxEXP(21),
    IndieSTR(22),
    IndieDEX(23),
    IndieINT(24),
    IndieLUK(25),
    IndieDamR(26),
    IndieMDF(27),
    IndieMaxDamageOver(28),
    IndieAsrR(29),
    IndieTerR(30),
    IndieCr(31),
    IndiePDDR(32),
    IndieCrMax(33),
    IndieBDR(34),
    IndieStatR(35),
    IndieStance(36),
    IndieIgnoreMobpdpR(37),
    IndieEmpty(38),
    IndiePADR(39),
    IndieMADR(40),
    IndieCrMaxR(41),
    IndieEVAR(42),
    IndiePMdR(43),
    IndieDrainHP(44),
    IndieDamR_(45),
    IndieIliumStack(47),
    IndieReduceCooltime(48),
    IndieMaxDamageOverR(53),
    IndieForceJump(49),
    IndieForceSpeed(50),
    IndieQrPointTerm(51),
    IndieFastCharge(52),
    IndieGrandCross(55),
    IndieObsidiunbarrier(57),
    SUMMON(74),
    PAD(66),
    PDD(67),
    MAD(68),
    ACC(69),
    EVA(70),
    Craft(71),
    Speed(72),
    Jump(73),
    
    MagicGuard(74),
    DarkSight(75),
    Booster(76),
    PowerGuard(77),
    MaxHP(78),
    MaxMP(79),
    Invincible(80),
    SoulArrow(81),
    Stun(82),
    Poison(83),
    Seal(84),
    Darkness(85),
    ComboCounter(86),
    WeaponCharge(87),
    BlessedHammer(88),
    BlessedHammerBig(89),
    
    HolySymbol(91),
    MesoUp(92),
    ShadowPartner(93),
    PickPocket(94),
    MesoGuard(95),
    Thaw(96),
    Weakness(97),
    Curse(98),
    Slow(99),
    Morph(100),
    Regen(101),
    BasicStatUp(102),
    Stance(103),
    SharpEyes(104),

    NoBulletConsume(107),
    Infinity(108),
    AdvancedBless(109),
    IllusionStep(110),
    Blind(111),
    Concentration(112),
    BanMap(113),
    MaxLevelBuff(114),
    MesoUpByItem(115),
    
    Ghost(118),
    Barrier(119),
    ReverseInput(120),
    ItemUpByItem(121),
    RespectPImmune(122),
    RespectMImmune(123),
    DefenseAtt(124),
    DefenseState(125),
    DojangBerserk(126),
    DojangInvincible(127),
    DojangShield(128),
    SoulMasterFinal(129),
    WindBreakerFinal(130),
    ElementalReset(131),
    HideAttack(132),
    EventRate(133),
    ComboAbilityBuff(134),
    ComboDrainHp(135),
    ComboBarrier(136),
    BodyPressure(137),
    RepeatEffect(138),

    
    ExpBuffRate(139),
    StopPortion(140),
    StopMotion(141),
    Fear(142),
    HiddenPieceOn(143),
    
    SoulStone(146),
    Flying(147),
    Frozen(148),
    AssistCharge(149),
    Enrage(150),
    DrawBack(151),
    NotDamaged(152),
    FinalCut(153),
    HowlingAttackDamage(154),
    BeastFormDamageUp(155),
    Dance(156),
    EMHP(157),
    EMMP(158),
    EPAD(159),
    EMAD(160),
    EPDD(161),
    Guard(162),
    Slpidia(163),
    CraftOf(164),
    HowlingCritical(165),
    HowlingMaxMP(166),
    HowlingDefence(167),
    HowlingEvasion(168),
    PinkbeanMinibeenMove(169),
    Sneak(170),
    Mechanic(171),
    BeastFormMaxHP(172),
    Dice(173),
    BlessingArmor(174),
    DamR(175),
    TeleportMasteryOn(176),
    CombatOrders(177),
    Beholder(178),
    Inflation(180),
    OnixDivineProtection(181),
    Web(182),
    Bless(183),
    TimeBomb(184),
    DisOrder(185),
    Thread(186),
    Team(187),
    Explosion(188),
    BuffLimit(189),
    STR(190),
    INT(191),
    DEX(192),
    LUK(193),
    DispelItemOption(194),
    DarkTornado(195),
    PVPDamage(196),
    PvPScoreBonus(197),
    PvPInvincible(198),
    PvPRaceEffect(199),
    WeaknessMdamage(200),
    Frozen2(201),
    PVPDamageSkill(202),
    AmplifyDamage(203),
    Shock(204),
    InfinityForce(205),
    IncMaxHP(206),
    IncMaxMP(207),
    
    HolyMagicShell(208),
    KeyDownTimeIgnore(209),
    ArcaneAim(210),
    MasterMagicOn(211),
    AsrR(212),
    TerR(213),
    DamAbsorbShield(214),
    DevilishPower(215),
    SerpentScrewOnOff(215),
    Roulette(216),
    SpiritLink(217),
    ItemEvade(218),
    
    CriticalBuff(220),
    
    ItemCritical(225),
    
    Event2(227),
    VampiricTouch(228),
    DDR(229),
    IncTerR(230),
    IncAsrR(231),
    DeathMark(232),
    
    VenomSnake(235),
    
    CarnivalDefence(237),
    CarnivalExp(238),
    PyramidEffect(240),
    KillingPoint(241),
    HollowPointBullet(242),
    KeyDownMoving(243),
    IgnoreTargetDEF(244),
    ReviveOnce(245),
    Invisible(246),
    EnrageCr(247),
    EnrageCrDamMin(248),
    Judgement(249),
    
    GuidedArrow(254),
    
    BlessMark(256),
    SlowAttack(257),
    
    GrandCrossSize(260),
    
    BlessingArmorIncPAD(263),
    KeyDownAreaMoving(264),
    Larkness(265),
    StackBuff(266),
    
    AntiMagicShell(268),
    LifeTidal(269),
    
    SmashStack(271),
    PartyBarrier(272),
    ReshuffleSwitch(273),
    SpecialAction(274),
    VampDeathSummon(275),
    StopForceAtomInfo(276),
    SoulGazeCriDamR(277),
    SoulRageCount(278),
    PowerTransferGauge(279),
    AffinitySlug(280),
    Trinity(281),
    IncMaxDamage(282),
    BossShield(283),
    MobZoneState(284),
    GiveMeHeal(285),
    TouchMe(286),
    Contagion(287),
    ComboUnlimited(288),
    SoulExalt(289),
    IgnorePCounter(290),
    IgnoreAllCounter(291),
    IgnorePImmune(292),
    IgnoreAllImmune(293),
    FinalJudgement(294),
    
    IceAura(296),
    FireAura(297),
    VengeanceOfAngel(298),
    HeavensDoor(299),
    Preparation(300),
    BullsEye(301),
    IncEffectHPPotion(302),
    IncEffectMPPotion(303),
    BleedingToxin(304),
    IgnoreMobDamR(305),
    DamageReduce(999),

    Asura(306),
    MegaSmasher(307),
    FlipTheCoin(308),
    UnityOfPower(309),
    Stimulate(310),
    ReturnTeleport(311),
    DropRIncrease(312),
    IgnoreMobpdpR(313),
    BdR(314),
    CapDebuff(315),
    Exceed(316),
    DiabolikRecovery(317),
    FinalAttackProp(318),
    ExceedOverload(319),
    OverloadCount(320),
    BuckShot(321),
    FireBomb(322),
    SurplusSupply(324),
    SetBaseDamage(325),
    NewFlying(327),
    AmaranthGenerator(328),
    OnCapsule(329),
    CygnusElementSkill(330),
    StrikerHyperElectric(331),
    EventPointAbsorb(332),
    EventAssemble(333),
    StormBringer(334),
    ACCR(335),
    DEXR(336),
    Albatross(337),
    PoseType(339),
    ElementSoul(341),
    GlimmeringTime(342),
    
    SoulExplosion(344),
    SoulMP(345),
    FullSoulMP(346),
    
    ElementalCharge(348),
    Restoration(349),
    CrossOverChain(350),
    ChargeBuff(351),
    Reincarnation(352),
    KnightsAura(353),
    ChillingStep(354),
    DotBasedBuff(355),
    BlessEnsenble(356),
    ComboCostInc(357),
    ExtremeArchery(358),
    
    QuiverCatridge(360),
    AdvancedQuiver(361),
    UserControlMob(362),
    ImmuneBarrier(363),
    
    ZeroAuraStr(365),
    ZeroAuraSpd(366),
    CriticalGrowing(367),
    PASS_PINDER_SHAPE(368),
    QuickDraw(369),
    BowMasterConcentration(370),
    TimeFastBBuff(371),
    TimeFastABuff(372),
    GatherDropR(373),
    AimBox2D(374),
    TrueSniping(375),
    IncMonsterBattleCaptureRate(376),
    CursorSniping(377),
    DebuffTolerance(378),
    DotHealHPPerSecond(379),
    SpiritGuard(380),
    PreReviveOnce(381),
    SetBaseDamageByBuff(382),
    LimitMP(383),
    ReflectDamR(384),
    ComboTempest(385),
    MMPCutR(386),
    MHPCutR(387),
    SelfWeakness(388),
    ElementDarkness(389),
    FlareTrick(390),
    FlameDischarge(391),
    Dominion(392),
    SiphonVitality(393),
    
    ShadowServant(397),
    ShadowIllusion(398),
    KnockBack(399),
    
    ComplusionSlant(401),
    JaguarSummoned(402),
    JaguarCount(403),
    SSFShootingAttack(404),
    DevilCry(405),
    ShieldAttack(406),
    BMageAura(407),
    DarkLighting(408),
    MASTER_OF_DEATH(409),
    BMageDeath(410),
    BombTime(411),
    NoDebuff(412),
    
    XenonAegisSystem(415),
    AngelicBursterSoulSeeker(416),
    HiddenPossession(417),
    NightWalkerBat(418),
    NightLordMark(419),
    WizardIgnite(420),
    FireBarrier(421),
    
    HolyUnity(423),
    
    ShadowSpear(425),
    
    Ellision(427),
    LightOrDark(429),
    QuiverFullCBuster(431),
    GrandCross(432),
    
    PinkbeanYoYoStack(441),
    RandAreaAttack(442),
    
    NautilusFinalAttack(446),
    ViperTimeLeap(447),
    RoyalGuardState(448),
    RoyalGuardPrepare(449),
    MichaelSoulLink(450),
    MichaelStanceLink(451),
    TriflingWhimOnOff(452),
    AddRangeOnOff(453),
    KinesisPsychicPoint(454),
    KinesisPsychicOver(455),
    KinesisPsychicShield(456),
    KinesisIncMastery(457),
    KinesisPsychicEnergeShield(458),
    BladeStance(459),
    
    AngelicBursterSoulResonance(463),
    Fever(464),
    RpSiksin(465),
    TeleportMasteryRange(466),
    FixCoolTime(467),
    IncMobRateDummy(468),
    AdrenalinBoost(469),
    AranSmashSwing(470),
    AranDrain(471),
    AranBoostEndHunt(472),
    HiddenHyperLinkMaximization(473),
    RWCylinder(474),
    RWCombination(475),
    
    RWMagnumBlow(477),
    RWBarrier(478),
    RWBarrierHeal(479),
    RWMaximizeCannon(480),
    RWOverHeat(481),
    UsingScouter(482),
    RWMovingEvar(483),
    Stigma(484),
    InstallMaha(485),
    
    PinPointRocket(489),
    Transform(490),
    EnergyBurst(491),
    LightningUnion(492),
    BulletParty(493),
    UnionAuraBlow(495),
    Pray(496),
    
    AuraWeapon(500), // 316
    
    PsychicTornado(503), // 316
    
    SpreadThrow(504), // 316
    HowlingGale(505), // 316
    BigHugeGiganticCanonBall(506), // 316
    
    ShadowAssault(507), // 316
    
    BlitzShield(512),
    SplitArrow(511), // 316
    
    OverloadMode(513), // 316
    
    Spotlight(514), // 316
    
    IliumeFly(517), // 316
    KillMonster(518), //316
    
    KadenaStack(519),
    
    OverDrive(999),
    
    ReadyToDie(521),
    CrystalChargeMax(522),

    
    HarmonyLink(529), // 316
    FastCharge(530), // 316
    
    ArkGage(539),
    ArkTransform(540),
    ArkChargeMable(541),
    ArkChargeMableRed(542),
    ArkChargeMableBlue(543),
    ArkChargeMableGRAY(544),
    ArkComeDie(545),
    ArkBattle(546),
    ChargeSpellAmplification(547),
    
    ComboInstings(558),
    WindWall(559),
    
    SwordOfSoulLIght(562),
    
    RelicGage(581),
    
    stack(557),
    
    ChangeFoxMan(999),
    BattlePvP_Mike_Shield(999),
    BattlePvP_Mike_Bugle(999),
    BattlePvP_Helena_Mark(999),
    BattlePvP_Helena_WindSpirit(999),
    BattlePvP_LangE_Protection(999),
    BattlePvP_LeeMalNyun_ScaleUp(999),
    BattlePvP_Revive(999),
    PinkbeanAttackBuff(999),
    PinkbeanRelax(999),
    PinkbeanRollingGrade(999),
    NextAttackEnhance(999),
    AranBeyonderDamAbsorb(999),
    AranCombotempastOption(999),
    DebuffActiveSkillHPCon(999),
    DebuffIncHP(999),
    BowMasterMortalBlow(999),
    AnimalChange(999),
    TeamRoar(999),
    HayatoStance(999),
    HayatoStanceBonus(999),
    HayatoPAD(999),
    HayatoHPR(999),
    HayatoMPR(999),
    HayatoCr(999),
    KannaBDR(999),
    Battoujutsu(999),
    EyeForEye(999),
    FamiliarShadow(999),
    Unknown355(999),
    LightOfSpirit(999),
    HalfstatByDebuff(999),
    Unknown287(999),
    Unknown275(999),
    HitCriDamR(999),
    BlessOfDarkness(999),
    DojangLuckyBonus(999),
    PainMark(999),
    Magnet(999),
    MagnetArea(999),
    VampDeath(999),
    CarnivalAttack(999),
    UsefulAdvancedBless(999),
    Lapidification(999),
    DropRate(999),
    PlusExpRate(999),
    ItemInvincible(999),
    Awake(999),
    AsrRByItem(999),
    Event(999),
    DispelItemOptionByField(999),
    MagicShield(999),
    MagicResistance(999),
    Attract(999),
    ManaReflection(999),
    Translucence(999),
    TrueSight(999),
    SoulSkillDamageUp(999),
    NaviFlying(999),
    ArmorPiercing(999),
    DarknessAscension(999),
    BossWaitingLinesBuff(999),
    AddAttackCount(999),
    
    EnergyCharged(595),
    Dash_Speed(596),
    Dash_Jump(597),
    MonsterRiding(598),
    PartyBooster(599),
    GuidedBullet(600),
    Undead(601),
    RideVehicleExpire(602),
    RelicTitleGage(603);

    
    private static final long serialVersionUID = 0L;
    private final BigInteger value;
    private final boolean isIndie;
    private int num = 999;
    private int num_ = -1;
    public static int BIT_COUNT = 992;

    private BuffStats(String hexVal) {
        this.value = new BigInteger(hexVal, 16);
        this.isIndie = name().contains("Indie");
    }

    private BuffStats(int nValue, int nPos) {
        this.value = BigInteger.valueOf(nValue).shiftLeft(nPos * 32);
        this.isIndie = false;
        this.num = nPos * 32 + nValue;
    }

    private BuffStats(int flag) {
        this.value = BigInteger.ONE.shiftLeft((flag / 32) * 32 + 0x1F - (flag & 0x1F));
        this.num = flag;
        this.num_ = flag;
        this.isIndie = name().contains("Indie");
        
    }

    private BuffStats(int flag, boolean check) {
        this.value = BigInteger.ONE.shiftLeft(flag);
        this.num = (flag / 32 * 32) + flag & 0x1F;
        this.num_ = flag;
        this.isIndie = name().contains("Indie");
    }

    public int getNum_() {
        return num_;
    }

    public int getNum() {
        return num;
    }

    
    public BigInteger getBigValue() {
        return value;
    }

    public boolean isIndie() {
        return isIndie;
    }

    public boolean isEnDecode4Byte() {
        switch (this) {
            case CarnivalDefence:
            case SpiritLink:
            case DojangLuckyBonus:
            case SoulGazeCriDamR:
            case PowerTransferGauge:
            case ReturnTeleport:
            case ShadowPartner:
            case SetBaseDamage:
            case QuiverCatridge:
            case ImmuneBarrier:
            case NaviFlying:
            case Dance:
            case SetBaseDamageByBuff:
            case DotHealHPPerSecond:
            case MagnetArea:
                return true;
            default:
                return false;
        }
    }

    public boolean isMovementAffectingStat() {
        switch (this) {
            case Jump:
            case Stun:
            case Weakness:
            case Slow:
            case Morph:
            case Ghost:
            case BasicStatUp:
            case Attract:
            case Dash_Speed:
            case Dash_Jump:
            case Flying:
            case Frozen:
            case Frozen2:
            case Lapidification:
            case IndieSpeed:
            case IndieJump:
            case KeyDownMoving:
            case EnergyCharged:
            case Mechanic:
            case Magnet:
            case MagnetArea:
            case VampDeath:
            case VampDeathSummon:
            case GiveMeHeal:
            case DarkTornado:
            case NewFlying:
            case NaviFlying:
            case UserControlMob:
            case Dance:
            case SelfWeakness:
            case BattlePvP_Helena_WindSpirit:
            case BattlePvP_LeeMalNyun_ScaleUp:
            case TouchMe:
                //        case IndieForceSpeed:
                //        case IndieForceJump:
                return true;
            default:
                return false;
        }
    }

    public static BuffStats getCTSFromTSIndex(int index) {
        switch (index) {
            case 0:
                return EnergyCharged;
            case 1:
                return Dash_Speed;
            case 2:
                return Dash_Jump;
            case 3:
                return MonsterRiding;
            case 4:
                return PartyBooster;
            case 5:
                return GuidedBullet;
            case 6:
                return Undead;
            case 7:
                return RideVehicleExpire;
        }
        return null;
    }
}
