package handlers;

import client.Character.MapleCharacter;
import client.MapleClient;
import client.Skills.SkillFactory;
import client.Stats.BuffStats;
import constants.ServerConstants;
import constants.Data.ServerType;
import handlers.CashShopHandler.CashShopOperation;
import client.AntiCheat.AntiHackHandler;
import handlers.ChatHandler.ChatHandler;
import launcher.ServerPortInitialize.ChannelServer;
import connections.Packets.MainPacketCreator;
import connections.Opcodes.RecvPacketOpcode;
import connections.Opcodes.SendPacketOpcode;
import connections.Packets.SkillPackets.KinesisSkill;
import connections.Packets.PacketUtility.ReadingMaple;
import connections.Packets.SoulWeaponPacket;
import handlers.AuctionHandler.AuctionHandler;
import handlers.DueyHandler.DueyHandler;
import handlers.GlobalHandler.BBSHandler;
import handlers.GlobalHandler.BossEventHandler.AswanHandler;
import handlers.GlobalHandler.BossEventHandler.Damien.DemianHandler;
import handlers.GlobalHandler.GuildHandler.AllianceHandler;
import handlers.GlobalHandler.GuildHandler.GuildHandler;
import handlers.GlobalHandler.HiredMerchantHandler;
import handlers.GlobalHandler.InterServerHandler;
import handlers.GlobalHandler.ItemInventoryHandler.InventoryHandler;
import handlers.GlobalHandler.ItemInventoryHandler.ItempotHandler;
import handlers.GlobalHandler.JobHandler.AngelicBusterHandler;
import handlers.GlobalHandler.JobHandler.PhantomHandler;
import handlers.GlobalHandler.JobHandler.ZeroHandler;
import handlers.GlobalHandler.MatrixHandler;
import handlers.GlobalHandler.MobHandler;
import handlers.GlobalHandler.MonsterCollectionHandler;
import handlers.GlobalHandler.NPCHandler;
import handlers.GlobalHandler.PetHandler;
import handlers.GlobalHandler.PlayerHandler.BuddyListHandler;
import handlers.GlobalHandler.PlayerHandler.PartyHandler;
import handlers.GlobalHandler.PlayerHandler.PlayerHandler;
import handlers.GlobalHandler.PlayerHandler.PlayerInteractionHandler;
import handlers.GlobalHandler.PlayerHandler.PlayersHandler;
import handlers.GlobalHandler.ProfessionHandler;
import handlers.GlobalHandler.SecurityHandler;
import handlers.GlobalHandler.StatsHandling;
import handlers.GlobalHandler.SummonHandler;
import handlers.LoginHandler.CharLoginHandler;
import server.Systems.StarForceSystem;
import tools.FileoutputUtil;
import tools.StringUtil;

public class MapleServerHandler {

    public static final void handlePacket(final RecvPacketOpcode header, final ReadingMaple rh, final MapleClient c,
            final ServerType serverType) throws InterruptedException {
        if (header == null) {
            return;
        }
        switch (header) {
            case PERMISSION_REQUEST:
                byte pLocale = rh.readByte();
                short pVersion = rh.readShort();
                short pString = rh.readShort();
                if (pLocale != ServerConstants.check && pVersion != ServerConstants.MAPLE_VERSION
                        && pString != ServerConstants.subVersion) {
                    System.err.println("Invalid Version: " + c.getSessionIPAddress());
                    c.getSession().close();
                }
                break;
            case CLIENT_EXCEPTION_INFO:
                SocketHandler.handleClientExceptionInfo(rh, c);
                break;
            case CHECK_HOTFIX:
                CharLoginHandler.checkHotfix(rh, c);
                break;
            case WORLDINFO_REQUEST:
                CharLoginHandler.worldSelect(rh, c);
                break;
            case WVS_SETUP_STEP:
                break;
            case SET_BURNING_CHAR_R:
                CharLoginHandler.setBurningCharacter(rh, c);
                break;
            case CLIENT_QUIT:
                InterServerHandler.getGameQuitRequest(rh, c);
                break;
            case LOGIN_REQUEST: {
                try {
                    CharLoginHandler.getLoginRequest(rh, c);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case CHECK_SECOND_PASSWORD:
                CharLoginHandler.checkSecondPassword(rh, c);
                break;
            case REDISPLAY_CHANNEL:
                CharLoginHandler.getDisplayChannel(false, c);
                break;
            case RELOGIN_2:
                CharLoginHandler.getDisplayChannel(false, c);
                break;
            case ENTER_CREATE_CHAR:
                CharLoginHandler.getIPRequest(rh, c);
                break;
            case SECONDPW_RESULT_R:
                CharLoginHandler.getSPCheck(rh, c);
                break;
            case SESSION_CHECK_R:
                CharLoginHandler.getSessionCheck(rh, c);
                break;
            case NEW_CONNECTION:
                CharLoginHandler.newConnection(c);
                break;
            case PONG:
                c.pongReceived(rh);
                break;
            case Obsidiunbarrier:
                PlayerHandler.Obsidiunbarrier(rh, c);
                break;
            case LOGIN_PASSWORD: {
                try {
                    CharLoginHandler.checkLoginAuthInfo(rh, c);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case CHARLIST_REQUEST:
                CharLoginHandler.CharlistRequest(rh, c);
                break;
            case CHARACTER_CARD:
                CharLoginHandler.updateCharCard(rh, c);
                break;
            case CHECK_CHAR_NAME:
                CharLoginHandler.CheckCharName(rh.readMapleAsciiString(), c);
                break;
            case CREATE_CHAR:
                CharLoginHandler.CreateChar(rh, c);
                break;
            case CLIENT_ERROR: // might not be correct
                if (rh.available() >= 6L) {
                    rh.skip(6);
                    short badPacketSize = rh.readShort();
                    rh.skip(4);
                    int pHeader = rh.readShort();
                    String pHeaderStr = Integer.toHexString(pHeader).toUpperCase();
                    pHeaderStr = StringUtil.getLeftPaddedStr(pHeaderStr, '0', 4);
                    String op = SendPacketOpcode.getOpcodeName(pHeader);
                    String from = "";
                    if (c.getPlayer() != null) {
                        from = new StringBuilder().append("Chr: ").append(c.getPlayer().getName()).append(" LVL(")
                                .append(c.getPlayer().getLevel()).append(") job: ").append(c.getPlayer().getJob())
                                .append(" MapID: ").append(c.getPlayer().getMapId()).toString();
                    }

                    String Recv = new StringBuilder().append(from).append("\r\n").append("SendOP(-38): ").append(op)
                            .append(" [").append(pHeaderStr).append("] (").append(badPacketSize - 4).append(")")
                            .append(rh.toString(false)).append("\r\n\r\n").toString();
                    System.out.println(Recv);
                    FileoutputUtil.log("ClientErrorPacket.txt", Recv);
                }
                break;
            case DELETE_CHAR:
                CharLoginHandler.DeleteChar(rh, c);
                break;
            case CHAR_SELECT:
                CharLoginHandler.Character_WithSecondPassword(rh, c);
                break;
            case AUTH_LOGIN_WITH_SPW:
                CharLoginHandler.checkSecondPassword(rh, c);
                break;
            case secondpassword:
                CharLoginHandler.secondpasword(rh, c);
                break;
            case REG_SECOND_PASSWORD:
                CharLoginHandler.registerSecondPassword(rh, c);
                break;
            case ONLY_REG_SECOND_PASSWORD:
                CharLoginHandler.onlyRegisterSecondPassword(rh, c);
                break;
            case CHANGE_CHANNEL:
                InterServerHandler.ChangeChannel(rh, c, c.getPlayer());
                break;
            case PLAYER_LOGGEDIN:
                rh.skip(4);
                final int playerid = rh.readInt();
                if (serverType.equals(ServerType.CASHSHOP)) {
                    CashShopOperation.EnterCS(playerid, c);
                } else if (serverType.equals(ServerType.AUCTION)) {
                    AuctionHandler.EnterAuction(playerid, c);
                } else {
                    InterServerHandler.Loggedin(playerid, c);
                }
                break;
            case ENTER_CASH_SHOP:
                InterServerHandler.EnterCS(c, c.getPlayer(), true);
                break;
            /*
		 * case ENTER_ASWAN_FIELD: AswanHandler.EnterAswanField(rh, c); break; case
		 * ENTER_ASWAN: AswanHandler.EnterAswan(c); break;
             */
            case ENTER_MTS:
                InterServerHandler.EnterMTS(c);
                break;
            case MOVE_PLAYER:
                PlayerHandler.MovePlayer(rh, c, c.getPlayer());
                break;
            case MOVE_ANDROID:
                PlayerHandler.MoveAndroid(rh, c, c.getPlayer());
                break;
            case CHAR_INFO_REQUEST:
                rh.skip(4);
                PlayerHandler.CharInfoRequest(rh.readInt(), c, c.getPlayer());
                break;
            case ROOM_CHANGE:
                PlayerHandler.RoomChange(rh, c, c.getPlayer());
                break;
            case DF_COMBO:
                PlayerHandler.absorbingDF(rh, c);
                break;
            case WILL_OF_SOWRD_COMBO:
                PlayerHandler.absorbingSword(rh, c.getPlayer());
                break;
            case PSYCHIC_GREP_R:
                KinesisSkill.PsychicGrep(rh, c);
                break;
            case ReleasePsychicLock:
                PlayerHandler.ReleasePsychicLock(rh, c);
                break;
            case PSYCHIC_UNKNOWN_R:
                KinesisSkill.PsychicUnknown(rh, c);
                break;
            case CANCEL_PSYCHIC_GREP_R:
                KinesisSkill.CancelPsychicGrep(rh, c);
                break;
            case PASSIVE_ENERGY:
            case MAGNETIC_DAMAGE:
            case SPOTLIGHT_ATTACK:
            case CLOSE_RANGE_ATTACK:
            		PlayerHandler.closeRangeAttack(rh, c, c.getPlayer(), header);
                break;
            case RANGED_ATTACK:
            		PlayerHandler.rangedAttack(rh, c, c.getPlayer());
                break;
            case MAGIC_ATTACK:
            		 PlayerHandler.MagicDamage(rh, c, c.getPlayer(), header);
                break;
            case SPECIAL_SKILL:
            		PlayerHandler.SpecialSkill(rh, c, c.getPlayer());
                break;
            case FACE_EXPRESSION:
                PlayerHandler.ChangeEmotion(rh.readInt(), c.getPlayer());
                break;
            case ANDROID_FACE_EXPRESSION:
                PlayerHandler.ChangeEmotionAndroid(rh.readInt(), c.getPlayer());
                break;
            case TAKE_DAMAGE:
                PlayerHandler.TakeDamage(rh, c, c.getPlayer());
                break;
            case HEAL_OVER_TIME:
            case HEAL_OVER_TIME_FROM_POT:
                PlayerHandler.Heal(rh, c.getPlayer());
                break;
            case CANCEL_EFFECT:
            case CANCEL_BUFF:
                PlayerHandler.CancelBuffHandler(rh.readInt(), c.getPlayer());
                break;
            case CANCEL_ITEM_EFFECT:
                PlayerHandler.CancelItemEffect(rh.readInt(), c.getPlayer());
                break;
            case USE_CHAIR:
                rh.skip(4);
                PlayerHandler.UseChair(rh.readInt(), c, c.getPlayer(), rh);
                break;
            case CANCEL_CHAIR:
                PlayerHandler.CancelChair(rh.readShort(), c, c.getPlayer());
                break;
            case USE_ITEMEFFECT:
                PlayerHandler.UseItemEffect(rh.readInt(), c, c.getPlayer());
                break;
            case MAKER_SKILL:
                PlayerHandler.makerSkill(rh, c);
                break;
            case SKILL_EFFECT:
                PlayerHandler.SkillEffect(rh, c.getPlayer());
                break;
            case MESO_DROP:
                rh.skip(4);
                PlayerHandler.DropMeso(rh.readInt(), c.getPlayer());
                break;
            case WHEEL_OF_FORTUNE:
                PlayerHandler.WheelOfFortuneEffect(rh.readInt(), c.getPlayer());
                break;
            case CHANGE_KEYMAP:
                PlayerHandler.ChangeKeymap(rh, c.getPlayer());
                break;
            case QUICK_SLOT:
                PlayerHandler.ChangeQuickSlot(rh, c.getPlayer());
                break;
            case CHANGE_MAP:
                if (serverType.equals(ServerType.CHANNEL)) {
                    PlayerHandler.ChangeMap(rh, c, c.getPlayer());
                } else {
                    CashShopOperation.LeaveCS(rh, c, c.getPlayer());
                }
                break;
            case CHANGE_MAP_SPECIAL:
                rh.skip(1);
                PlayerHandler.ChangeMapSpecial(rh.readMapleAsciiString(), c, c.getPlayer());
                break;
            case USE_INNER_PORTAL:
                rh.skip(1);
                PlayerHandler.InnerPortal(rh, c, c.getPlayer());
                break;
            case TROCK_ADD_MAP:
                PlayerHandler.TrockAddMap(rh, c, c.getPlayer());
                break;
            case ARAN_GAIN_COMBO:
                PlayerHandler.AranGainCombo(c, c.getPlayer());
                break;
            case ARAN_LOSE_COMBO:
                PlayerHandler.AranLoseCombo(c, c.getPlayer());
                break;
            case BLESS_OF_DARKNES:
                PlayerHandler.BlessOfDarkness(c.getPlayer());
                break;
            case SKILL_MACRO:
                PlayerHandler.ChangeSkillMacro(rh, c.getPlayer());
                break;
            case SUB_SUMMON_ACTION:
                PlayerHandler.subSummonAction(rh, c);
                break;
            case DI_AHF_RLAH:
                PlayerHandler.znfxkdladmfwnwkdyd(rh, c);
                break;
            case GIVE_FAME:
                PlayersHandler.GiveFame(rh, c, c.getPlayer());
                break;
            case TRANSFORM_PLAYER:
                PlayersHandler.TransformPlayer(rh, c, c.getPlayer());
                break;
            case NOTE_ACTION:
                PlayersHandler.Note(rh, c.getPlayer());
                break;
            case USE_DOOR:
                PlayersHandler.UseDoor(rh, c.getPlayer());
                break;
            case USE_MECH_DOOR:
                PlayersHandler.UseMechDoor(rh, c.getPlayer());
                break;
            case DAMAGE_REACTOR:
                PlayersHandler.HitReactor(rh, c);
                break;
            case CLOSE_CHALKBOARD:
                c.getPlayer().setChalkboard(null);
                break;
            case ITEM_SORT:
                InventoryHandler.ItemSort(rh, c);
                break;
            case DRESS_UP:
                AngelicBusterHandler.DressUpTime(rh, c);
                break;
            case ITEM_MOVE:
                InventoryHandler.ItemMove(rh, c);
                break;
            case ITEM_PICKUP:
                InventoryHandler.Pickup_Player(rh, c, c.getPlayer());
                break;
            case ITEM_GATHER:
                InventoryHandler.ItemGather(rh, c);
                break;
            case USE_CASH_ITEM:
                InventoryHandler.UseCashItem(rh, c);
                break;
            case RUNE_TOUCH:
                PlayersHandler.TouchRune(rh, c.getPlayer());
                break;
            case RUNE_USE:
                PlayersHandler.UseRune(rh, c.getPlayer());
                break;
            case USE_EDITIONAL_SCROLL:
                InventoryHandler.EditionalScroll(rh, c);
                break;
            case USE_PET_LOOT:
                InventoryHandler.UsePetLoot(rh, c);
                break;
            case USE_ITEM:
                InventoryHandler.UseItem(rh, c, c.getPlayer());
                break;
            case USE_SCRIPTED_NPC_ITEM:
                InventoryHandler.UseScriptedNPCItem(rh, c, c.getPlayer());
                break;
            case USE_RETURN_SCROLL:
                InventoryHandler.UseReturnScroll(rh, c, c.getPlayer());
                break;
            case USE_STAMP:
                InventoryHandler.UseStamp(rh, c);
                break;
            case USE_SOUL_ENCHANTER:
                InventoryHandler.UseSoulEnchanter(rh, c, c.getPlayer());
                break;
            case USE_SOUL_SCROLL:
                InventoryHandler.UseSoulScroll(rh, c, c.getPlayer());
                break;
            case SHOW_SOULEFFECT_R:
                c.getPlayer().soulEffect = rh.readByte();
                c.getPlayer().getMap()
                        .broadcastMessage(SoulWeaponPacket.showSoulEffect(c.getPlayer(), c.getPlayer().soulEffect));
                break;
            case USE_EDITIONAL_STAMP:
                InventoryHandler.UseEditionalStamp(rh, c);
                break;
            case USE_MAGNIFY_GLASS:
                rh.skip(4);
                InventoryHandler.MagnifyingGlass(c, (byte) rh.readShort(), (byte) rh.readShort());
                break;
            case USE_UPGRADE_SCROLL:
                rh.skip(4);
                InventoryHandler.UseUpgradeScroll((byte) rh.readShort(), (byte) rh.readShort(), c, c.getPlayer());
                break;
            case USE_SPECIAL_SCROLL:
                rh.skip(4);
                InventoryHandler.UseSpecialScroll(rh, c.getPlayer());
                break;
            case USE_POTENTIAL_SCROLL:
                rh.skip(4);
                InventoryHandler.UseUpgradeScroll((byte) rh.readShort(), (byte) rh.readShort(), c, c.getPlayer());
                break;
            case USE_EQUIP_SCROLL:
                rh.skip(4);
                InventoryHandler.UseUpgradeScroll((byte) rh.readShort(), (byte) rh.readShort(), c, c.getPlayer());
                break;
            case USE_REBIRTH_SCROLL:
                rh.skip(4);
                InventoryHandler.UseUpgradeScroll((byte) rh.readShort(), (byte) rh.readShort(), c, c.getPlayer());
                break;
            case USE_MEMORIAL_CUBE:
                rh.skip(4);
                InventoryHandler.UseMemorialCube(rh, c.getPlayer());
                break;
            case USE_SILVER_KARMA:
                InventoryHandler.UseKarma(rh, c);
                break;
            case USE_SUMMON_BAG:
                InventoryHandler.UseSummonBag(rh, c, c.getPlayer());
                break;
            case USE_SKILL_BOOK:
                InventoryHandler.UseSkillBook(rh, c, c.getPlayer());
                break;
            case USE_CATCH_ITEM:
                InventoryHandler.UseCatchItem(rh, c, c.getPlayer());
                break;
            case REWARD_ITEM:
                InventoryHandler.UseRewardItem(rh, c, c.getPlayer());
                break;
            case HYPNOTIZE_DMG:
                MobHandler.HypnotizeDmg(rh, c.getPlayer());
                break;
            case MOVE_LIFE:
                MobHandler.MoveMonster(rh, c, c.getPlayer());
                break;
            case AUTO_AGGRO:
                MobHandler.AutoAggro(rh.readInt(), c.getPlayer());
                break;
            case FRIENDLY_DAMAGE:
                MobHandler.FriendlyDamage(rh, c.getPlayer());
                break;
            case EQUIP_UPGRADE_SYSTEM:
                StarForceSystem.AddItemRecv(rh, c);
                break;
            case MONSTER_BOMB:
                MobHandler.MonsterBomb(rh.readInt(), c.getPlayer());
                break;
            case NPC_SHOP:
                NPCHandler.NPCShop(rh, c, c.getPlayer());
                break;
            case NPC_TALK:
                NPCHandler.NPCTalk(rh, c, c.getPlayer());
                break;
            case NPC_TALK_MORE:
                NPCHandler.NPCMoreTalk(rh, c);
                break;
            case NPC_ACTION:
                NPCHandler.NPCAnimation(rh, c);
                break;
            case QUEST_ACTION:
                NPCHandler.QuestAction(c, rh);
                break;
            case UPDATE_QUEST:
                NPCHandler.UpdateQuest(rh, c);
                break;
            case STORAGE:
                NPCHandler.Storage(rh, c, c.getPlayer());
                break;
            case KADENA_SKILL:
                PlayerHandler.KadenaSkill(rh, c);
                break;
            case GENERAL_CHAT:
                rh.skip(4);
                ChatHandler.GeneralChat(rh.readMapleAsciiString(), rh.readByte(), c, c.getPlayer());
                break;
            case PARTYCHAT:
                ChatHandler.Others(rh, c, c.getPlayer());
                break;
            case WHISPER:
                ChatHandler.Whisper_Find(rh, c);
                break;
            case MESSENGER:
                ChatHandler.Messenger(rh, c);
                break;
            case AUTO_ASSIGN_AP:
                StatsHandling.AutoAssignAP(rh, c, c.getPlayer());
                break;
            case DISTRIBUTE_AP:
                StatsHandling.DistributeAP(rh, c, c.getPlayer());
                break;
            case DISTRIBUTE_SP:
                rh.skip(4);
                StatsHandling.DistributeSP(rh, rh.readInt(), c, c.getPlayer());
                break;
            case PLAYER_INTERACTION:
                PlayerInteractionHandler.PlayerInteraction(rh, c, c.getPlayer());
                break;

            case GUILD_OPERATION:
                GuildHandler.GuildOpertion(rh, c);
                break;
            case DENY_GUILD_REQUEST:
                GuildHandler.DenyGuildRequest(rh, c);
                break;
            case GUILD_REQUEST_SEND:
                GuildHandler.Sendrequest(rh, c);
                break;
            case GUILD_ACCEPT_JOIN_REQUEST:
                GuildHandler.acceptJoinRequest(rh, c.getPlayer());
                break;
            case GUILD_DECLINE_JOIN_REQUEST:
                GuildHandler.declineJoinRequest(rh, c.getPlayer());
                break;
            case GUILD_JOIN_REQUEST:
                GuildHandler.joinRequest(rh, c.getPlayer());
                break;
            case GUILD_REMOVE_JOIN_REQUEST:
                GuildHandler.removeJoinRequest(rh, c.getPlayer());
                break;
            case ALLIANCE_OPERATION:
                AllianceHandler.AllianceOperatopn(rh, c, false);
                break;
            case DENY_ALLIANCE_REQUEST:
                AllianceHandler.AllianceOperatopn(rh, c, true);
                break;
            case BBS_OPERATION:
                BBSHandler.BBSOperatopn(rh, c);
                break;
            case PARTY_OPERATION:
                PartyHandler.PartyOperatopn(rh, c);
                break;
            case DENY_PARTY_REQUEST:
                PartyHandler.DenyPartyRequest(rh, c);
                break;
            case BUDDYLIST_MODIFY:
                BuddyListHandler.BuddyOperation(rh, c);
                break;
            case BUY_CS_ITEM:
                CashShopOperation.BuyCashItem(rh, c, c.getPlayer());
                break;
            case COUPON_CODE:
                rh.skip(2); // 선물받을 대상. (MapleAsciiString)
                CashShopOperation.CouponCode(rh.readMapleAsciiString(), c);
                break;
            case CS_UPDATE:
                CashShopOperation.CSUpdate(rh, c, c.getPlayer());
                break;
            case DAMAGE_SUMMON:
                rh.skip(4);
                SummonHandler.DamageSummon(rh, c.getPlayer());
                break;
            case MOVE_SUMMON:
                SummonHandler.MoveSummon(rh, c.getPlayer());
                break;
            case SUMMON_ATTACK:
                SummonHandler.SummonAttack(rh, c, c.getPlayer());
                break;
            case SUMMON_SPECIAL_ATTACK:
                SummonHandler.SummonSpecialAttack(rh, c, c.getPlayer());
                break;
            case SPAWN_PET:
                PetHandler.SpawnPet(rh, c, c.getPlayer());
                break;
            case REGISTER_PET_BUFF:
                PetHandler.RegisterPetBuff(rh, c.getPlayer());
                break;
            case MOVE_PET:
                PetHandler.MovePet(rh, c.getPlayer());
                break;
            case PET_CHAT:
                PetHandler.PetChat(rh, c.getPlayer());
                break;
            case PET_COMMAND:
                //PetHandler.PetCommand(rh, c, c.getPlayer());
                break;
            case PET_FOOD:
                PetHandler.PetFood(rh, c, c.getPlayer());
                break;
            case PET_LOOT:
                InventoryHandler.Pickup_Pet(rh, c, c.getPlayer());
                break;
            case PET_AUTO_POT:
                PetHandler.Pet_AutoPotion(rh, c, c.getPlayer());
                break;
            case USE_HIRED_MERCHANT:
                HiredMerchantHandler.UseHiredMerchant(rh, c);
                break;
            case MERCH_ITEM_STORE:
                HiredMerchantHandler.MerchantItemStore(rh, c);
                break;
            case MOVE_DRAGON:
                SummonHandler.MoveDragon(rh, c.getPlayer());
                break;
            case USE_MAGNIFYING_GLASS:
                InventoryHandler.MagnifyingGlass(c, (byte) rh.readShort(), (byte) rh.readShort());
                break;
            case GOLDEN_HAMMER:
                InventoryHandler.UseGoldenHammer(rh, c);
                break;
            case HAMMER_EFFECT:
                InventoryHandler.HammerEffect(rh, c);
                break;
            case EQUIPPED_SKILL:
                PhantomHandler.equippedSkill(rh, c);
                break;
            case STEEL_SKILL_CHECK:
                PhantomHandler.steelSkillCheck(rh, c);
                break;
            case STEEL_SKILL:
                PhantomHandler.steelSkill(rh, c);
                break;
            case SUMMON_SKILL:
                SummonHandler.summonSkill(rh, c, c.getPlayer());
                break;
            case REMOVE_SUMMON:
                SummonHandler.removeSummon(rh, c);
                break;
            case HEAD_TITLE:
                InventoryHandler.headTitle(rh, c);
                break;
            case START_GATHER:
                ProfessionHandler.startGathering(rh, c);
                break;
            case END_GATHER:
                InventoryHandler.ItemGather(rh, c);
                break;
            case ITEMPOT_PUT:
                ItempotHandler.putItempot(rh, c);
                break;
            case ITEMPOT_REMOVE:
                ItempotHandler.removeItempot(rh, c);
                break;
            case ITEMPOT_FEED:
                ItempotHandler.feedItempot(rh, c);
                break;
            case ITEMPOT_CURE:
                ItempotHandler.cureItempot(rh, c);
                break;
            case PROFESSIONINFO_REQUEST:
                ProfessionHandler.getProfessionInfo(rh, c);
                break;
            case PROFESSION_MAKE_EFFECT:
                ProfessionHandler.professionMakeEffect(rh, c);
                break;
            case PROFESSION_MAKE_SOMETHING:
                ProfessionHandler.professionMakeTime(rh, c);
                break;
            case PROFESSION_MAKE:
                ProfessionHandler.professionMake(rh, c);
                break;
            case SPAWN_EXTRACTOR:
                ProfessionHandler.spawnExtractor(rh, c);
                break;
            case USE_RECIPE:
                ProfessionHandler.useRecipe(rh, c);
                break;
            case EXPEDITION_OPERATION:
                PartyHandler.processExpeditionRequest(rh, c);
                break;
            case VOYD_PRESSURE:
                PlayerHandler.VoydPressure(rh, c.getPlayer());
                break;
            case AGI_BUFF:
                PlayerHandler.Agi_Buff(rh, c);
                break;
            case USE_BAG:
                InventoryHandler.useItem(rh, c);
                break;
            case MOVE_BAG:
                ProfessionHandler.MoveBag(rh, c);
                break;
            case SWITCH_BAG:
                ProfessionHandler.SwitchBag(rh, c);
                break;
            case HYPER_RECV:
                PlayerHandler.getHyperSkill(rh, c);
                break;
            case REQUEST_ASWAN_DEAD:
                AswanHandler.RequestAswanDead(c);
                break;
            case FOLLOW_REQUEST:
                PlayersHandler.FollowRequest(rh, c);
                break;
            case FOLLOW_REPLY:
                PlayersHandler.FollowReply(rh, c);
                break;
            case USE_BLACK_CUBE:
                InventoryHandler.UseBlackCube(c, rh);
                break;
            case WARP_TO_STARPLANET:
                PlayerHandler.warpToStarplanet(rh.readByte(), rh, c.getPlayer());
                break;
            case MAPLE_GUIDE:
            case MAPLE_CONTENT_MAP:
                PlayerHandler.MapleGuide(rh, rh.readShort(), c.getPlayer());
                break;
            case RETRACE_MECH:
                PlayerHandler.CancelBuffHandler(rh.readInt(), c.getPlayer());
                break;
            case SET_FREE_JOB:
                PlayerHandler.SetFreeJob(rh, c.getPlayer());
                break;
            case MAPLE_CHAT:
                PlayerHandler.MapleChat(rh, c.getPlayer());
                break;
            case ORBITAL_FLAME:
                PlayerHandler.OrbitalFlame(rh, c);
                break;
            case DMG_FLAME:
                rh.skip(12);
                PlayerHandler.closeRangeAttack(rh, c, c.getPlayer(), header);
                break;
            case DUEY_HANDLER:
                DueyHandler.DueyHandler(rh, c.getPlayer());
                break;
            case ARROW_FLATTER_ACTION:
                PlayerHandler.ArrowFlatterAction(rh, c.getPlayer());
                break;
            case INNER_CHANGE:
                PlayerHandler.ChangeInner(rh, c);
                break;
            case STAR_PLANET_RANK:
                PlayerHandler.getStarPlanetRank(rh, c.getPlayer());
                break;
            case ZERO_WEAPONINFO:
                ZeroHandler.ZeroWeaponInfo(rh, c);
                break;
            case ZERO_UPGRADE:
                ZeroHandler.ZeroWeaponLevelUp(rh, c);
                break;
            case ZERO_SHOCKWAVE:
                ZeroHandler.ZeroShockWave(rh, c);
                break;
            case ZERO_CHAT:
                ZeroHandler.ZeroChat(rh, c, "Hey, I actually like you quite a bit. How do you know? Originally we were one.");
                break;
            case ZERO_TAG:
                ZeroHandler.ZeroTag(rh, c);
                break;
            case ZERO_ASSIST_REMOVE:
                ZeroHandler.ZeroAssistRemove(c);
                break;
            case ZERO_OPEN:
                ZeroHandler.ZeroOpen(rh, c);
                break;
            case ZERO_CLOTHES:
                ZeroHandler.ZeroClothes(rh.readInt(), rh.readByte(), c);
                break;
            case ZERO_SCROLL:
                ZeroHandler.ZeroScroll(rh, c);
                break;
            case ZERO_SCROLL_START:
                ZeroHandler.ZeroScrollStart(rh, c.getPlayer(), c);
                break;
            case GAME_END:
                //c.getPlayer().send(MainPacketCreator.GameEnd());
                ChannelServer.isCharacterListConnected(c.loadCharacterNames(), true);
                break;
            case MOVE_GRENADE:
                PlayerHandler.OnThrowGrenade(rh, c);
                break;
            case HOLLY:
                rh.skip(5);
                PlayerHandler.Holly(rh, c.getPlayer());
                break;
            case AUCTION:
            case LEAVE_AUCTION:
                AuctionHandler.Handle(rh, c, rh.readInt());
                break;
            case MIST_SKILL:
                PlayerHandler.mistSkill(rh, c.getPlayer());
                break;
            case COMBAT_ANALYZE:
                c.getSession().writeAndFlush(MainPacketCreator.getCombatAnalyze(rh.readByte()));
                break;
            case PROCESS_CHECK:
                AntiHackHandler.ProcessCheck(rh, c);
                break;
            case OnOpenGateClose:
                PlayerHandler.OnOpenGateClose(rh, c);
                break;
            case CreateKinesisPsychicArea:
                PlayerHandler.CreateKinesisPsychicArea(rh, c);
                break;
            case DoActivePsychicArea:
                PlayerHandler.DoActivePsychicArea(rh, c);
                break;
            case DebuffPsychicArea:
                PlayerHandler.DebuffPsychicArea(rh, c);
                break;
            case CheckReincarnation:

                break;
            case UserDamageSkinSaveRequest:
                PlayerHandler.UserDamageSkinSaveRequest(rh, c);
                break;
            case LINK_SKILL:
                PlayerHandler.LinkSkillAction(c, rh);
                break;
            case UNLINK_SKILL:
                PlayerHandler.UnLinkSkillAction(c, rh);
                break;
            case MemoInGameRequest:
                PlayerHandler.OnMemoInGameRequest(rh, c);
                break;
            case ObtacleAtomCollision:
                MobHandler.ObtacleAtomCollision(rh, c.getPlayer());
                break;
            case NAME_CHANGE_CUPON:
                PlayerHandler.NameChange(rh, c);
                break;
            case NAME_CHANGE:
                PlayerHandler.NameChange_From_Login(rh, c);
                break;
            case MannequinResult:
                PlayerHandler.MannequinResult(rh, c);
                break;
            case HyperStatUp:
                PlayerHandler.HyperStatUp(rh, c);
                break;
            case HyperStatRemove:
                PlayerHandler.HyperStatRemove(rh, c);
                break;
            case HyperSkillUp:
                PlayerHandler.HyperSkillUp(rh, c);
                break;
            case HyperSkillRemove:
                PlayerHandler.HyperSkillRemove(rh, c);
                break;
            case USE_QUEST_ITEM:
                NPCHandler.UseItemQuest(rh, c);
                break;
            case EnterDungen:
                PlayerHandler.EnterDungen(rh, c);
                break;
            case CHNAGE_SECCOND_PW:
                CharLoginHandler.changeSeccondPassword(rh, c);
                break;
            case REGISTER_SP:
                CharLoginHandler.registerSP(rh, c);
                break;
            case MonsterCollectionRequest:
                MonsterCollectionHandler.handleMonsterCollection(rh, c);
                break;
            case EVENT_LIST:
                PlayerHandler.EventList(rh, c);
                break;
            case CASH_REMOVER:
                PlayerHandler.CashRemover(rh, c);
                break;
            case UserTowerChairSetting:
                rh.skip(4);
                PlayerHandler.TowerChair(rh, c);
                break;
            case EquipCore:
                MatrixHandler.EquipCore(rh, c);
                break;
            case MatrixQuestion:
                MatrixHandler.MatrixQuestion(rh, c);
                break;
            case DarkSpear:
                PlayerHandler.DarkSpear(rh, c);
                break;
            case DaetCount:
                PlayerHandler.DaethCount(rh, c);
                break;
            case NPC_QUEST_ACTION:
                NPCHandler.NpcQuestAction(rh, c);
                break;
            case DemianSword:
                DemianHandler.CDemianFlyingSword_EncodeMakeEnterInfo(rh, c);
                break;
            case BlockGameRes:
                PlayerHandler.HandleBlockGameRes(rh, c);
                break;
            case ExitBlockGame:
                PlayerHandler.ExitBlockGame(rh, c);
                break;
            case MOB_SKILL_DELAY:
                MobHandler.MobSkillDelayEnd(rh, c.getPlayer());
                break;
            case LoadedDice:
                PlayerHandler.LoadedDice(rh, c);
                break;
            case IncSkillTime:
                PlayerHandler.IncSkillTime(rh, c);
                break;
            case SymbolExp:
                PlayerHandler.SymbolExp(rh, c);
                break;
            case InhumanSpeed:
                PlayerHandler.InhumanSpeed(rh, c);
                break;
            case BOSS_RATE:
                PlayerHandler.BossRate(rh, c);
                break;
            case ICBMData:
                PlayerHandler.ICBMData(rh, c);
                break;
            case EnterAuction:
                InterServerHandler.EnterAuction(c);
                break;
            case HowlingGaleEnd:
                PlayerHandler.HowlingGaleEnd(rh, c);
                break;
            case Joker:
                PlayerHandler.Joker(rh, c);
                break;
            case SUMMON_ATOM_ATTACK:
                SummonHandler.AtomAttack(rh, c);
                break;
            case SPOTLIGHT_BUFF:
                PlayerHandler.SpotlightBuff(rh, c);
                break;
            case XENON_MEGA_SMASHER:
                PlayerHandler.XenonMegaSmahser(rh, c);
                break;
            case DAILY_GIFT:
                PlayerHandler.DailyGift(rh, c);
		System.out.println("Daily gift call");
                break;
            case SECURITY_PACKET:
                SecurityHandler.HandleSecurity(rh, c);
                break;
            case ANDROID_EAR:
                PlayerHandler.AndroidEar(c, rh);
                break;
            case SLFCG_SECURITY:
            case SLFCG_SECURITY2: {
                if (c.getPlayer().isGM()) {
                    System.out.println(c.getPlayer().getName() + " Operator nuclear use detection");
                    break;
                }
                ChannelServer cserv = c.getChannelServer();
                final MapleCharacter target = cserv.getPlayerStorage().getCharacterByName(c.getPlayer().getName());

                if (target != null) {
                    if (target.ban(header == RecvPacketOpcode.SLFCG_SECURITY ? "Debugger Detected" : "Cheat Detected", true,
                            true)) {
                        System.out.println(c.getPlayer().getName() + " Nuclear use detection, van completed");
                    } else {
                        System.out.println(c.getPlayer().getName() + " Nuclear use detection, Ban failure");
                    }
                    c.disconnect(true, false);
                    c.getSession().close();
                }
                break;
            }
            case SLFCG_SECURITY_HEARTBEAT:
                PlayerHandler.MovePlayer(rh, c, c.getPlayer());
                break;
            case ClickBingoCell:
                rh.skip(8);
                PlayerHandler.HandleCellClick(rh.readInt(), c);
                break;
            case ClickBingo:
                PlayerHandler.HandleBingoClick(c);
                break;
            case ARAN_COMMAND_LOCK:
                PlayerHandler.AranCommandLock(rh, c);
                break;
            case CRYSTAL_SKILL:
                PlayerHandler.CrystalSkill(rh, c);
                break;
            case BLACK_ALTER:
                SummonHandler.removeSummon(rh, c);
                break;
            case HAMONY_LINK:
                c.getPlayer().setHamonyCount(c.getPlayer().getHamonyCount() + 1);
                int passiveid = 152000007;
                SkillFactory.getSkill(passiveid).getEffect(c.getPlayer().getSkillLevel(passiveid)).applyTo(c.getPlayer());
                if (c.getPlayer().getHamonyCount() == 15) {
                    c.getPlayer().setHamonyCount(0);
                    c.getPlayer().cancelEffectFromBuffStat(BuffStats.HarmonyLink);
                }
                break;
            case ARK_AUTO_SKILL:
                PlayerHandler.ArkAutoSkill(rh, c);
                break;
            case GRAND_CROSS:
                PlayerHandler.GiveGrandCross(rh, c);
                break;
            case PEACE_MAKER:
                break;
            default:
                System.out.println("[UNHANDLED] Recv [" + header.toString() + "] found");
                break;
        }
    }
}
