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
			//	cm.getPlayer().gainRC(140000);
cm.sendSimple("#fs11#Hello~! Welcome to #dAzureMS#k. I am #dHalocat#k and this is the #bWarp System#k. It will take you to the place you want for free.#b\r\n"
+ "#k#d#fs11##L0##fUI/GuildMark.img/Mark/Pattern/00004001/10# #e#fc0xFFff8888#1~99 training grounds#k#n#d.#l\r\n"
+ "#k#d#fs11##L1111##fUI/GuildMark.img/Mark/Pattern/00004001/12# #e#fc0xFFff6666#100~199 training grounds#k#n#d.#l\r\n"
+ "#k#d#fs11##L500##fUI/GuildMark.img/Mark/Pattern/00004001/13# #e#fc0xFFff3333#200~275 training grounds#k#n#d.#l\r\n"
+ "#k#d#fs11##L7##fUI/GuildMark.img/Mark/Pattern/00004001/14# #e#rTowns & maps#k#n#d.#l\r\n"
+ "#k#d#fs11##L6##fUI/GuildMark.img/Mark/Pattern/00004001/15# #dEvent Maps #e#r(O.X quiz,Bingo,etc)#k#n#d.#l\r\n"
//+ "#k#d#fs11##L15##fUI/GuildMark.img/Mark/Pattern/00004001/4# I want to move to #e#rfishing#e#r#k#n#d.#l\r\n"
+ "#k#d#fs11##L5##fUI/GuildMark.img/Mark/Pattern/00004001/14# #e#rBoss System#k#n#d.#l\r\n"
+ "#k#d#fs11##L13##fUI/GuildMark.img/Mark/Pattern/00004001/14# #e#rDisposal & Gift System#k#n#d.#l\r\n"
+ (cm.getPlayer().getJob() >= 3300 && cm.getPlayer().getJob() <= 3312 ? "#b#L4##dI want to leave this place #eJaguar Habitat#n want to move.#l\r\n" : ""));
       
        
	}

	else if(St == 1)
	{
		mapType = S;
		switch(mapType)
		{
			default:
			cm.sendSimple ("Please select a destination. #e#rA challenging mode #k#nlisted outside of normal mode is recommended.\r\n#b"
                             + "#b#L211042300#Zakum ¦¢ Gate to Zakum ¦¢ Easy, Chaos#l\r\n"
                             + "#b#L240050400#Horntail ¦¢ Entrance of the Horntail Cave ¦¢ Easy, Chaos#l\r\n"
                             + "#b#L270050000#Pink Bean ¦¢ Forgotten Twilight ¦¢ Chaos#l\r\n"
                             + "#b#L262030000#Hilla ¦¢ Entrance to Hilla's Tower ¦¢ Hard#l\r\n"
                             + "#b#L211070000#Von Leon ¦¢ Hallway in front of Reality Room ¦¢ Easy#l\r\n"
                             + "#b#L272020110#Arkarium ¦¢ Altar of Arkarium ¦¢ Easy#l\r\n"
                             + "#b#L271040000#Cygnus ¦¢ Garden of Cygnus ¦¢ Easy#l\r\n"
                             + "#b#L105200000#Root Abyss ¦¢ Giant Roots ¦¢ Chaos#l\r\n"
                             + "#b#L401060000#Magnus ¦¢ Entrance to Hellsium Top Level ¦¢ Easy, Hard#l\r\n"
                             + "#b#L350060300#Lotus¦¢ Core Inlet ¦¢ Hard#l\r\n"
                             + "#b#L105300303#Damian ¦¢ Road to the top of the world tree ¦¢ Hard#l\r\n"
                             + "#b#L450004000#Lucid | Nightmare Clock Tower | Easy, Hard#l\r\n");
			break;

			case 0:
cm.sendSimple("#fnSharing Ghotic Extrabold##r[Notice]#k Choose a training ground you wish to go.#b\r\n"
+ "#b#L100040300#(Lv.10~) Golem's Temple - Golem's Temple 3\r\n"
+ "#L101030100#(Lv.20~) North Forest - Green Tree Trunk\r\n"
+ "#L10830000#(Lv.20~) Elodin - Deepening Forest 1\r\n"
+ "#L120041800#(Lv.30~) Gold Beach - Rough Waves\r\n"
+ "#L101070100#(Lv.30~) Night's Forest - Radiant Lake Path 1\r\n"
+ "#L101071200#(Lv.30~) Ellinel Lake - Above the Lake 3\r\n"
+ "#L141030100#(Lv.30~) Riena Strait - Underwater Ice Cave 1\r\n"
+ "#L102030000#(Lv.50~) Burnt Land - Wild Boar Land\r\n"
+ "#L102040301#(Lv.50~) Excavation Site - Military Camp 1\r\n"
+ "#L105010000#(Lv.60~) Swamp - Silent Swamp\r\n"
+ "#L105010100#(Lv.60~) Swamp - Humid Swamp\r\n"
+ "#L200010200#(Lv.60~) Orbis - Stairway to the Sky I\r\n"
+ "#L200010300#(Lv.60~) Orbis - Stairway to the Sky II\r\n"
+ "#L230020200#(Lv.60~) Aqua Road - Sand Castle Playground\r\n"
+ "#L211040200#(Lv.70~) El Nath - Ice Valley II\r\n"
+ "#L260020600#(Lv.70~) Sunset Road - Sahel 2\r\n"
+ "#L260020610#(Lv.70~) Sunset Road - The Desert of Serenity\r\n"
+ "#L261010003#(Lv.80~) Zenumist Research Institute - Lab - Unit 103\r\n"
+ "#L261020401#(Lv.80~) Hidden Street - Authorized Personnel Only\r\n"
+ "#L261020400#(Lv.80~) Alcadno Research Institute - Lab - Area C-2\r\n"
+ "#L261020500#(Lv.80~) Alcadno Research Institute - Lab - Area C-3\r\n"
+ "#L240010100#(Lv.90~) Leafre - West Border\r\n"
+ "#L240010200#(Lv.90~) Leafre - Cranky Forest\r\n"
+ "#L240010600#(Lv.90~) Leafre - Sky Nest II(¡ÚX5)\r\n"
+ "#L240020200#(Lv.90~) Leafre - The Area of Black Kentaurus(¡ÚX15)#l\r\n"
+ "#L300010300#(Lv.90~) Ellin Forest - Mossy Tree Forest Trail\r\n"
+ "#L300030200#(Lv.90~) Ellin Forest - Fairy Forest 1\r\n");
			break;
			
			case 1111:
cm.sendSimple("#fnSharing Ghotic Extrabold##r[Notice]#k Choose a training ground you wish to go.#b\r\n"
                                               + "#L220011000#(Lv.100~) Ludibrium - Cloud Terrace<5>  \r\n"
											   + "#L220020100#(Lv.100~) Toy Factory <Process 1> Zone 2 \r\n"
											   + "#L220030100#(Lv.100~) Toy Factory <Process 2> Zone 2  \r\n"
											   + "#L211040600#(Lv.110~) El Nath - Sharp Cliff 4  #l\r\n"
											   + "#L220060201#(Lv.110~) Hidden Street - Unbalanced Time(¡ÚX26)  #l\r\n"
											   + "#L220060301#(Lv.110~) Hidden Street - Lost Time(¡ÚX27)  #l\r\n"
											   + "#L220070400#(Lv.110~) Clocktower - Forgotten Passage(¡ÚX26)  #l\r\n"
											   + "#L224000142#(Lv.120~) Korean Folk Town - Goblin House  #l\r\n"
											   + "#L211042000#(Lv.120~) Elnath - The Cave of Trial 1(¡ÚX55)  #l\r\n"
											   + "#L250020300#(Lv.120~) Mulung Garden - Master Training Center  #l\r\n"
											   + "#L240030102#(Lv.130~) Minar Forest - The Forest Disappeared #l\r\n"
											   + "#L240040320#(Lv.130~) Minar Forest - Entrance to Dragon Nest  #l\r\n"
											   + "#L240040300#(Lv.130~) Minar Forest - West Road(¡ÚX65)  #l\r\n"
											   + "#L240040521#(Lv.130~) Minar Forest - Dangerous Dragons Nest #l\r\n"
											   + "#L240040511#(Lv.140~) Minar Forest - The Dragon Nest 1(¡ÚX70)#l\r\n"
											   + "#L103041119#(Lv.140~) Kerning Tower - Cafe(¡ÚX80)#l\r\n"
											   + "#L103041149#(Lv.140~) Kerning Tower - Cosmetic Shop<4>(¡ÚX80)#l\r\n"
											   + "#L270030630#(Lv.140~) Time Lane  - Road to Oblivion 4(¡ÚX90)#l\r\n"
											   + "#L221030640#(Lv.160~) UFO Interior - Hallway H01(¡ÚX140)#l\r\n"
											   + "#L221030650#(Lv.160~) UFO Interior - Hallway H02(¡ÚX140)#l\r\n"
											   + "#L221030660#(Lv.160~) UFO Interior - Hallway H03(¡ÚX140)#l\r\n"
											   + "#L271000200#(Lv.160~) Dark Ereve - Mutant Tiru Forest#l\r\n"
											   + "#L271010100#(Lv.160~) Henesys Ruins - Henesys Ruins Market#l\r\n"
											   + "#L271010300#(Lv.160~) Henesys Ruins - Hill North l\r\n"
											   + "#L271030101#(Lv.170~) Knight's Stronghold -  District 1(¡ÚX120)#l\r\n"
											   + "#L271030102#(Lv.170~) Knight's Stronghold -  District 2(¡ÚX120)#l\r\n"
											   + "#L271030310#(Lv.170~) Knight's Stronghold - Armory 1(¡ÚX120)#l\r\n"
											   + "#L271030320#(Lv.170~) Knight's Stronghold - Armory 1(¡ÚX120)#l\r\n"
											   + "#L273010000#(Lv.180~) Twilight Perion - Deserted Southern Ridge#l\r\n"
											   + "#L273030100#(Lv.180~) Twilight Perion - Fiery Rocky Hills#l\r\n"
											   + "#L273020100#(Lv.180~) Twilight Perion - Desoloate Rocky Road#l\r\n"
											   + "#L273040100#(Lv.180~) Twilight Perion - Excavation Site 2#l\r\n"
											   + "#L273040200#(Lv.180~) Twilight Perion - Excavation Site 2#l\r\n"
											   + "#L273040200#(Lv.180~) Twilight Perion - Excavation Site 3#l\r\n"
											   + "#L273040300#(Lv.180~) Twilight Perion - Excavation Site 4#l\r\n"
											   + "#L273060100#(Lv.180~) Twilight Perion - Gale Pleateau#l\r\n"
											   + "#L273060300#(Lv.180~) Twilight Perion - Warrior Grounds#l\r\n"
											   + "#L410000103#(Lv.190~) Fox Point Village- Fox Tree Midway 3#l\r\n"
											   + "#L310070130#(Lv.190~) Scrapyard - Scrapyard Hill 3#l\r\n"
											   + "#L310070140#(Lv.190~) Scrapyard  - Scrapyard Hill 4#l\r\n"
											   + "#L310070160#(Lv.190~) Scrapyard - Scrapyard Hill 5#l\r\n"
											   + "#L310070200#(Lv.190~) Skyline - Skyline 1#l\r\n"
											   + "#L105300301#(Lv.200~) Dark World Tree- Upper Stem Crossroad#l\r\n");
			break;

			case 500:
			cm.sendSimple("#fnSharing Ghotic Extrabold##r[Notice]#k Choose a training ground you wish to go.#b\r\n"
                                               + "\r\n#e#r¡ØÁÖÀÇ ¡é¡é Training grounds have powerful monsters.#k#b#n\r\n"
                                               + "\r\n#e#r¡Ø#b¼Ò#g¸ê#rÀÇ#b¿©#g·Î#b#n\r\n"
											   + "#L450001012#(Lv.200~) Lake of Oblivion - Land of Rage(¡ßX30)\r\n"
											   + "#L450001014#(Lv.200~) Lake of Oblivion - Land of Sorrow(¡ßX30)\r\n"
											   + "#L450001112#(Lv.200~) Lake of Oblivion - Fire Zone(¡ßX40)\r\n"
											   + "#L450001214#(Lv.200~) Cave of Repose - Cave Path 2(¡ßX60)\r\n"
											   + "#L450001216#(Lv.200~) Cave of Repose - Below the Cave(¡ßX60)\r\n"
											   + "#L450001230#(Lv.200~) Cave of Repose - Arma's Hideout(¡ßX60)\r\n"
											   + "#L450001262#(Lv.200~) Cave of Repose - Hidden cave(¡ßX80)\r\n"
                                                                                     + "\r\n#e#r¡Ø#bÃò#gÃò#r¾Æ#bÀÏ#g·£#rµå#b#n\r\n"
											   + "#L450002010#(Lv.210~) Chu Chu - Slurpy Forest Depths(¡ßX100)\r\n"
											   + "#L450002006#(Lv.210~) Chu Chu - Dealie-Bobber Forest 1(¡ßX100)\r\n"
											   + "#L450002008#(Lv.210~) Chu Chu - Bitty-Bobble Forest(¡ßX1-0)\r\n"
											   + "#L450002004#(Lv.210~) Chu Chu - Mottled Forest 3(¡ßX100)\r\n"
											   + "#L450002011#(Lv.210~) Ereve Valley - Village Entrance(¡ßX130)\r\n"
											   + "#L450002014#(Lv.210~) Ereve Valley - Torrent Zone 3(¡ßX130)\r\n"
											   + "#L450002015#(Lv.210~) Ereve Valley - Below the Falls(¡ßX130)\r\n"
											   + "#L450002016#(Lv.210~) Skywhale - Mountain's Mouth(¡ßX160)\r\n"
											   + "#L450002017#(Lv.210~) Skywhale - Mountain side 1(¡ßX160)\r\n"
											   + "\r\n#e#r¡Ø#b·¹#gÇï#r¸¥#b#n\r\n"
											   + "#L450003220#(Lv.220~) Lachelein - Outlaw's Street 3(¡ßX190)\r\n"
											   + "#L450003310#(Lv.220~) Lachelein - Chicken Festival 2(¡ßX210)\r\n"
											   + "#L450003410#(Lv.220~) Lachelein - Revelation Place 2(¡ßX210)\r\n"
											   + "#L450003420#(Lv.220~) Lachelein - Revelation Place 3(¡ßX210)\r\n"
											   + "#L450003500#(Lv.220~) Lachelein - Clocktower 1F(¡ßX240)\r\n"
											   + "#L450003540#(Lv.220~) Lachelein - Clocktower 5F(¡ßX240)\r\n"
											   + "\r\n#e#r¡Ø#b¾Æ#g¸£#rÄ«#b³ª#b#n\r\n"
											   + "#L450005130#(Lv.225~) Arcana - The Forest of Earth(¡ßX280)\r\n"
											   + "#L450005222#(Lv.225~) Arcana - Frost and Thunder 2(¡ßX320)\r\n"
                                                                                           + "#L450005410#(Lv.225~) Arcana - Cavern Upper Path(¡ßX360)\r\n"
											   + "#L450005430#(Lv.225~) Arcana - Cavern Lower Path(¡ßX360)\r\n"
											   + "#L450005431#(Lv.225~) Arcana - Cavern - Lower Path 1(¡ßX360)\r\n"
											   + "#L450005432#(Lv.225~) Arcana - Cavern - Lower Path 2(¡ßX360)\r\n"
										           + "#L450005440#(Lv.225~) Arcana - Cavern - Lower path(¡ßX360)\r\n"
											   + "#L450005500#(Lv.225~) Arcana - Labyrinth Cavern (¡ßX360)\r\n"
											   + "#L450005510#(Lv.225~) Arcana - Labyrinth Upper Path(¡ßX360)\r\n"
                                                                                           + "\r\n#e#r¡Ø#b¸ð#g¶ó#r½º#k#b#n\r\n"
											   + "#L450006210#(Lv.230~) Morass - Shadowdance Hall 2¡ßX480)\r\n"
											   + "#L450006220#(Lv.230~) Morass - Shadowdance Hall 3¡ßX480)\r\n"
											   + "#L450006230#(Lv.230~) Morass - Shadowdance Hall 4¡ßX480)\r\n"
											   + "#L450006300#(Lv.230~) Morass - Abandoned Area(¡ßX480)\r\n"
											   + "#L450006410#(Lv.230~) Morass - That Day in Trueffet 2(¡ßX520)\r\n"
											   + "#L450006420#(Lv.230~) Morass - That Day in Trueffet 3(¡ßX520)\r\n"
											   + "#L450006430#(Lv.230~) Morass - That Day in Trueffet 4(¡ßX520)\r\n"
                                                                                           + "\r\n#e#r¡Ø#b¿¡#g½º#rÆä#b¶ó#k#b#n\r\n"
											   + "#L450007110#(Lv.235~) Esfera - Mirror-touched Sea 2(¡ßX600)\r\n"
											   + "#L450007120#(Lv.235~) Esfera - Mirror-touched Sea 3(¡ßX600)\r\n"
											   + "#L450007210#(Lv.235~) Esfera - Radiant Temple 2(¡ßX640)\r\n"
											   + "#L450007220#(Lv.235~) Esfera - Radiant Temple 3(¡ßX640)\r\n"
											   + "#L450007230#(Lv.235~) Esfera - Radiant Temple 4(¡ßX640)\r\n"
                                                                                           + "\r\n#e#r¡Ø#b¹®#gºê#r¸´#dÁö#k#b#n\r\n"
											   + "#L450009120#(Lv.245~) Moonbridge - Last Horizon 2(¡ßX670)\r\n"
											   + "#L450009150#(Lv.245~) Moonbridge - Last Horizon 5(¡ßX670)\r\n"
											   + "#L450009160#(Lv.245~) Moonbridge - Last Horizon 6(¡ßX670)\r\n"
											   + "#L450009320#(Lv.245~) Moonbridge - Void Current 2(¡ßX730)\r\n"
											   + "#L450009330#(Lv.245~) Moonbridge - Void Current 3(¡ßX730)\r\n"
											   + "#L450009340#(Lv.245~) Moonbridge - Void Current 4(¡ßX730)\r\n"
											   + "#L450009360#(Lv.245~) Moonbridge - Void Current 6(¡ßX730)\r\n"
											   + "\r\n#e#r¡Ø#b°í#gÅë#rÀÇ#b¹Ì#d±Ã#k#b#n\r\n"
											   + "#L450011410#(Lv.250~) Labyrinth - Suffering Interior 2(¡ßX760)\r\n"
											   + "#L450011440#(Lv.250~) Labyrinth - Suffering Interior 5(¡ßX760)\r\n"
											   + "#L450011540#(Lv.250~) Labyrinth - Suffering Core 4(¡ßX790)\r\n"
											   + "#L450011550#(Lv.250~) Labyrinth - Suffering Core 5(¡ßX790)\r\n"
											   + "#L450011570#(Lv.250~) Labyrinth - Suffering Core 7(¡ßX790)\r\n"
											   + "#L450011600#(Lv.250~) Labyrinth - Suffering Deep Core 1(¡ßX820)\r\n"
                                                                                           + "\r\n#e#r¡Ø#b¸®#g¸à#k#b#n\r\n"
                                                                                           + "#L450012020#(Lv.255~) Limen - World's Sorrow Depths 1(¡ßX850)\r\n"
                                                                                           + "#L450012030#(Lv.255~) Limen - World's Sorrow Depths 2(¡ßX850)\r\n"
											   + "#L450012040#(Lv.255~) Limen - World's Sorrow Depths 3(¡ßX850)\r\n"
											   + "#L450012100#(Lv.255~) Limen - World's Sorrow Midpoint 1(¡ßX850)\r\n"
											   + "#L450012110#(Lv.255~) Limen - World's Sorrow Midpoint 2(¡ßX850)\r\n"
											   + "#L450012120#(Lv.255~) Limen - World's Sorrow Midpoint 3(¡ßX850)\r\n"
											   + "#L450012350#(Lv.255~) Limen - End of the World 1-6(¡ßX880)\r\n");
			break;


			case 3:
				cm.dispose();
				cm.openNpc(9071003);
                        break;
                        case 4:
				cm.dispose();
				cm.openNpc(2159314);
			break;
			case 5:
				cm.dispose();
				cm.warp(970060000,0);
			break;
                        case 6:
		cm.sendSimple("#fnSharing Ghotic Extrabold##r[Notice]#k Please select the event map you want.#b\r\n"
                 + "#L109020001# #e#rOX Quiz#k#n#d I will move.\r\n"
                 + "#L180010002# #e#rWhite shelter#k#n#d I will move.\r\n"
                 + "#L922290000# #e#rBingo#k#n#d I will move.\r\n"
                 + "#L993014000# #e#rFrozen-Link#k#n#d I will move.\r\n"
                 + "#L993017000# #e#rBuzz buzz house#k#n#d I will move.\r\n"
				 + "#L951000000# #e#rMonster Park#k#n#d I will move.\r\n");
			break;
                        case 7:
		cm.sendSimple("#fnSharing Ghotic Extrabold##r[Notice]#k Please select your village.#b\r\n"
                  + "#L230040401#A Small Wrecked Ship.\r\n"
                  + "#L200090001#Orbis Cabin.\r\n"
                  + "#L200110020#Flight Date.\r\n"
                  + "#L100000000#Henesys.\r\n"
                  + "#L103000000#Kerning City.\r\n"
                  + "#L101000000#Ellinia.\r\n"
                  + "#L102000000#Perion.\r\n"
                  + "#L310000000#Edelstein.\r\n"
                  + "#L105000000#Sleepwood.\r\n" 
                  + "#L130000000#Ereve.\r\n"
                  + "#L101050000#Elluel.\r\n"
                  + "#L140000000#Rien.\r\n"
                  + "#L200000000#Orbis.\r\n"
                  + "#L211000000#El Nath.\r\n"
                  + "#L220000000#Ludibrium.\r\n"
                  + "#L240000000#Leafre.\r\n"
                  + "#L260000000#Ariant.\r\n"
                  + "#L261000000#Magatia.\r\n"
                  + "#L252000000#Golden Temple.\r\n"
                  + "#L100040000#Rock Giant.\r\n"
                  + "#L273000000#Twilight Perion.\r\n"
                  + "#L219000000#Cork Town.\r\n"
                  + "#L105200000#Root Abyss.\r\n"
                  + "#L931050810#Pantheon.\r\n"
                  + "#L105300000#The Fallen World Tree\r\n"
                  + "#L450003330#Lachelein.\r\n"
                  + "#L103041000#Kerning Tower.\r\n"
                  + "#L450005000#Arcana.\r\n"
                  + "#L450002000#ChuChu Island\r\n"
                  + "#L450001000#An Unnamed Village.\r\n"
                  + "#L450006130#Morass Truffle Square.\r\n"
                  + "#L450007040#Esfera Base Camp.\r\n");
			break;
                        case 8:
				cm.dispose();
				cm.warp(993014000,0);
			break;
                        case 9:
				cm.dispose();
				cm.warp(993017000,0);
			break;
                        case 10:
				cm.dispose();
				cm.warp(100000202,0);
			break;
                        case 11:
				cm.dispose();
				cm.warp(910530000,0);
			break;
                        case 12:
				cm.dispose();
				cm.warp(910130000,0);
			break;
                        case 13:
				cm.dispose();
				cm.warp(100000001,0);
			break;
                        case 15:
				cm.dispose();
				cm.warp(100000055,0);
			break;
}
}
	else if(St == 2)
	{
		cm.dispose();
		cm.warp(S, "sp");
	}
}
function z(i, z)
{
	switch(z)
	{
		case 1:
		return "#fnµ¸¿òÃ¼#"+i+"  #fnµ¸¿ò#";

		case 2:
		return "#fnµ¸¿òÃ¼#"+i+" #fnµ¸¿ò#";

		default:
		return "#fnµ¸¿òÃ¼#"+i+"#fnµ¸¿ò#";
	}
}
