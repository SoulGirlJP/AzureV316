importPackage(Packages.constants);
importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);

var enter = "\r\n";
var seld = -1, seld2 = -1, androidseld = -1;
var android = false;
var andhair = false;

/* Do not modify */
var 피부 = [0, 1, 2, 3, 4, 9, 10, 11, 12, 13];
var 렌즈 = [];
var 성형 = [];
var 헤어 = [];
var 염색 = [];

/* Editable */
var 남자헤어1 = [30000, 30020, 30030, 30040, 30050, 30060, 30100, 30110, 30120, 30130, 30140, 30150, 30160, 30170, 30180, 30190, 30200, 30210, 30220, 30230, 30240, 30250, 30260, 30270, 30280, 30290, 30300, 30310, 30320, 30330, 30340, 30350, 30360, 30370, 30400, 30410, 30420, 30440, 30450, 30460, 30470, 30480, 30490, 30510, 30520, 30530, 30540, 30560, 30570, 30590, 30610, 30620, 30630, 30640, 30650, 30660, 40770, 43290, 43300, 43310, 43320, 43350];
var 남자헤어2 = [30840, 30850, 30860, 30870, 30880, 30910, 30930, 30940, 30950, 33030, 33060, 33070, 33080, 33090, 33110, 33120, 33130, 33150, 33170, 33180, 33190, 33210, 33220, 33250, 33260, 33270, 30670, 30680, 30700, 30710, 30730, 30760, 30770, 30790, 30800, 30810, 30820, 30830, 33280, 33310, 33330, 33350, 33360, 33370, 33380, 33390, 33400, 33410, 33430, 33440, 33450, 33460, 33480, 33500, 33510, 33520, 33530, 33550, 40770, 43290, 43300, 43310];
var 남자헤어3 = [33580, 33590, 33600, 33610, 33620, 33630, 33640, 33660, 33670, 33680, 33690, 33700, 33710, 33720, 33730, 33740, 33750, 33760, 33770, 33780, 33790, 33800, 33810, 33820, 33830, 33930, 33940, 33950, 33960, 33990, 35000, 35010, 35020, 35030, 35040, 35050, 35060, 35070, 35080, 35090, 35100, 35150, 35180, 35190, 35200, 35210, 35250, 35260, 35280, 35290, 35300, 35310, 35330, 35350, 35360, 43320, 43350, 40650, 40710, 43580, 43570, 43330];
var 남자헤어4 = [36010, 36020, 36030, 36040, 36050, 36070, 36080, 36090, 36100, 36130, 36140, 36150, 36160, 36170, 36180, 36190, 36200, 36210, 36220, 36230, 36240, 36250, 36300, 36310, 36330, 36340, 36350, 36380, 36390, 36400, 36410, 36420, 36430, 36440, 36450, 36460, 36470, 36480, 36510, 36520, 36530, 36560, 36570, 36580, 36590, 36620, 36630, 36640, 36650, 36670, 36680, 36690, 36700, 36710, 36720, 36730, 36740, 36750, 36760, 36770, 36780, 36790];
var 남자헤어5 = [36800, 36810, 36820, 36830, 36840, 36850, 36860, 36900, 36910, 36920, 36940, 36950, 36980, 36990, 40000, 40010, 40020, 40050, 40060, 40090, 40100, 40120, 40250, 40260, 40270, 40280, 40290, 40300, 40310, 40320, 40330, 40390, 40400, 40410, 40420, 40440, 40450, 40470, 40480, 40490, 40500, 40510, 40570, 40580, 40600, 40610, 40640, 40660, 40670, 40690, 40720, 40740, 40810, 40820, 41060, 41070, 40930, 43020, 43140, 43150, 43180, 40780];
var 남자헤어6 = [43667, 43610, 43620, 43700, 43750, 43750, 43760, 43770, 43780, 45000, 45010];

var 여자헤어1 = [37000, 37010, 37020, 37030, 37040, 37060, 37070, 37080, 37090, 37100, 37110, 37120, 37130, 37140, 37190, 37210, 37220, 37230, 37240, 37250, 37260, 37280, 37300, 37310, 37320, 37340, 37370, 37380, 37400, 37450, 37460, 37490, 37500, 37510, 37520, 37530, 37560, 37570, 37580, 37610, 37620, 37630, 37640, 37650, 37660, 37670, 37680, 37690, 37700, 37710, 37720, 37730, 37740, 37750, 37760, 37770, 37780, 37790, 37800, 37810, 37820, 37830, 34360, 34370, 34380, 34400, 34410, 34420, 34430, 34440, 34450, 34470, 34480, 34490, 34510, 34540];
var 여자헤어2 = [31000, 31010, 31020, 31030, 31040, 31050, 31060, 31070, 31080, 31090, 31100, 31110, 31120, 31130, 31140, 31150, 31160, 31170, 31180, 31190, 31200, 31210, 31220, 31230, 31240, 31250, 31260, 31270, 31280, 31290, 31300, 31310, 31320, 31330, 31340, 31410, 31420, 31440, 31450, 31460, 31470, 31480, 31490, 31510, 31520, 31530, 31540, 31550, 31560, 31590, 31610, 31620, 31630, 31640, 31650, 31670, 31680, 31690, 31700, 31710, 31720, 31740, 34560, 34590, 34600, 34610, 34620, 34630, 34640, 34660, 34670, 34680, 34690, 34700, 34710, 34720];
var 여자헤어3 = [37840, 37850, 37860, 37880, 37910, 37920, 37940, 37950, 37960, 37970, 37980, 37990, 38000, 38010, 38020, 38030, 38040, 38050, 38060, 38070, 38090, 38100, 38110, 38120, 38130, 38270, 38280, 38290, 38300, 38310, 38380, 38390, 38400, 38410, 38420, 38430, 38440, 38460, 38470, 38520, 38540, 38550, 38560, 38570, 38580, 38590, 38600, 38610, 38620, 38630, 38640, 38650, 38660, 38670, 38680, 38690, 38700, 38740, 38750, 38760, 34730, 34740, 34750, 34760, 34770, 34780, 34790, 34800, 34810, 34820, 34830, 34840, 34850, 34860];
var 여자헤어4 = [38800, 38810, 38820, 38840, 38880, 38910, 38940, 39090, 41080, 41090, 41100, 41110, 41120, 41150, 41160, 41200, 41220, 41350, 41360, 41370, 41380, 41390, 41400, 41440, 41470, 41480, 41490, 41510, 41520, 41560, 41570, 41590, 41600, 41700, 41720, 41730, 41740, 41750, 41850, 41860, 41880, 41920, 41930, 41950, 44010, 44120, 44130, 44320, 44200, 44330, 44460, 41900, 44360, 44290, 44470, 44480, 44490, 44500, 44530, 41870, 34870, 34880, 34900, 34910, 34940, 34950, 34960];
var 여자헤어5 = [44650, 44770, 44850, 44780, 44790, 44802, 44900, 41940, 44840, 44940, 44950, 47000, 47040, 47020, 47010, 47030, 47070, 47270, 47280, 31750, 31780, 31790, 31800, 31810, 31820, 31840, 31850, 31860, 31880, 31890, 31910, 31920, 31930, 31940, 31950, 31990, 34040, 34070, 34080, 34090, 34100, 34110, 34120, 34130, 34140, 34150, 34160, 34170, 34180, 34190, 34210, 34220, 34230, 34240, 34250, 34260, 34270, 34310, 34320, 34330, 34340];
var 여자헤어6 = [41890, 48020, 38730, 38490, 44830, 41340, 48070, 48360, 47090, 34970];

var 최신남자성형 = [20000, 20001, 20002, 20003, 20004, 20005, 20006, 20007, 20008, 20009, 20011, 20012, 20013, 20014, 20015, 20016, 20017, 20018, 20020, 20021, 20022, 20025, 20027, 20028, 20029, 20030, 20031, 20032, 20036, 20037, 20040, 20043, 20044, 20045, 20046, 20047, 20048, 20049, 20050, 20051, 20052, 20053, 20055, 20056, 20057, 20058, 20059, 20060, 20061, 20062, 20063, 20064, 20065, 20066, 20067, 20068, 20069, 20070, 20074, 20075, 20076, 20077, 20080, 20081, 20082, 20083, 20084, 20085, 20086, 20087, 20088, 20089, 20090, 20093, 20094, 20095, 20097, 20098, 20099, 20110, 23000, 23001, 23002, 23003, 23005, 23006, 23008, 23010, 23011, 23012, 23015, 23016, 23017, 23018, 23019, 23020, 23023, 23024, 23025, 23026, 23027, 23028, 23029, 23031, 23032, 23033, 23035, 23038, 23039, 23040, 23041, 23042, 23043, 23044, 23053, 23054, 23056, 23057, 23060, 23061, 23062, 23063, 23067, 23068, 23069, 23072, 23073, 23074, 23075, 23079, 23080, 23081, 23082, 23083, 23084, 23085, 23086, 23087, 23088, 23089, 23090, 23091, 23092, 23095, 23096, 23097, 23099, 24061, 24098, 25006, 25007, 25011, 25014, 25016, 25017, 25021, 25022, 25023, 25024, 25025, 25027, 25033, 25058, 25057, 25049, 25053, 25029, 25020, 25043, 25044, 25063, 25062, 25050, 25080, 25079, 25083, 25055, 25085, 25088, 25089,23000, 23001, 23002, 23003, 23005, 23006, 23008, 23010, 23011, 23012, 23015, 23016, 23017, 23018, 23019, 23020, 23023, 23024, 23025, 23026, 23027, 23028, 23029, 23031, 23032, 23033, 23035, 23038, 23039, 23040, 23041, 23042, 23043, 23044, 23053, 23054, 23056, 23057, 23060, 23061, 23062, 23063, 23067, 23068, 23069, 23072, 23073, 23074, 23075, 23079, 23080, 23081, 23082, 23083, 23084, 23085, 23086, 23087, 23088, 23089, 23090, 23091, 23092, 23095, 23096, 23097, 23099, 25000, 25004, 25005, 25006, 25007, 25008, 25011, 25014, 25015, 25016, 25017, 25020, 25021, 25022, 25023, 25024, 25025, 25026, 25027, 25029, 25033, 25043, 25044, 25045, 25046, 25047, 25048, 25049, 25051, 25053, 25057, 25063, 25062];
var 해외남자성형 = [];
var 구남자성형 = [24001, 24002, 24003, 24004, 24007, 24008];

var 최신여자성형 = [21002, 21003, 21004, 21005, 21006, 21007, 21008, 21009, 21010, 21011, 21012, 21013, 21014, 21015, 21016, 21017, 21020, 21021, 21023, 21024, 21026, 21027, 21028, 21029, 21030, 21031, 21033, 21036, 21038, 21041, 21042, 21043, 21044, 21045, 21048, 21050, 21052, 21053, 21056, 21058, 21059, 21061, 21062, 21063, 21065, 21073, 21074, 21075, 21077, 21078, 21079, 21080, 21081, 21082, 21083, 21084, 21085, 21089, 21090, 21091, 21092, 21093, 21094, 21095, 21096, 21097, 21098, 24002, 24003, 24004, 24007, 24008, 24011, 24012, 24014, 24015, 24018, 24020, 24021, 24022, 24023, 24027, 24031, 24035, 24037, 24038, 24039, 24041, 24050, 24055, 24058, 24060, 24067, 24068, 24071, 24072, 24073, 24077, 24080, 24081, 24084, 24087, 24088, 24091, 24097, 24099, 25000, 25008, 25015, 25099, 26003, 26004, 26005, 26009, 26014, 26017, 26022, 26023, 26027, 26028, 26029, 26030, 26031, 26032, 26062, 26061, 26053, 26056, 26034, 26026, 26046, 26067, 26066, 26054, 26086, 26085, 25155, 26089, 26091, 26094, 26095,24001, 24002, 24003, 24004, 24007, 24008, 24011, 24012, 24014, 24015, 24018, 24020, 24021, 24023, 24027, 24031, 24035, 24038, 24039, 24041, 24050, 24058, 24060, 24068, 24071, 24072, 24073, 24077, 24084, 24087, 24088, 24091, 24097, 24099, 25006, 25008, 26003, 26004, 26005, 26008, 26009, 26014, 26017, 26022, 26023, 26026, 26027, 26028, 26029, 26030, 26031, 26032, 26034, 26046, 26047, 26048, 26049, 26050, 26051, 26053, 26056, 26058, 26764, 26763, 26762, 26761, 26760, 26759, 25057, 25157, 26066, 26067];
var 해외여자성형 = [];
var 구여자성형 = [24001, 24002, 24003, 24004, 24007, 24008];
function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, sel) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
		var msg = "#fs11##k#k#d#fs15##i1162003##e#r Hello AzureMS Beauty shop.#k#n#fs11##b"+enter;
		msg += "#k#d#fs11##L1##i5680222##e#r 헤어 샵#k#n #r(Hair shop)#k"+enter;
		msg += "#k#d#fs11##L2##i5680222##e#r 성형 샵#k#n #r(Plastic surgery)#k"+enter;
		msg += "#k#d#fs11##L3##i5680222##e#r 스킨 케어#k#n #r(Skin care)#k"+enter;
		msg += "#k#d#fs11##L4##i5680222##e#r 컬러 렌즈#k#n #r(Color lens)#k"+enter;
		msg += "#k#d#fs11##L5##i5680222##e#r 믹스 염색#k#n #r(Mix dye)#k"+enter;
		msg += "#k#d#fs11##L8##i5680222##e#r 일반 염색#k#n #r(Normal dye)#k"+enter;
		msg += "#k#d#fs11##L6##i5680222##e#r 성별 전환#k#n #r(Gender change)#k"+enter;
		msg += "#k#d#fs11##L7##i5680222##e#r 안드로이드 외형변경#k#n #r(Android dress)#k"+enter;

		cm.sendSimple(msg);
	} else if (status == 1) {
		seld = sel;

		switch (sel) {
			case 1:
				var msg = "#fs11##d#eAzureMS Hair Shop.#n#k#fs11#"+enter;
				msg += "#L1##e#rHair1#n#k I want to use a shop."+enter;
				msg += "#L2##r#eHair2#n#k I want to use a shop."+enter;
				msg += "#L3##e#rHair3#n#k I want to use a shop."+enter;
				msg += "#L4##e#rHair4#n#k I want to use a shop."+enter;
				msg += "#L5##e#rHair5#n#k I want to use a shop."+enter;
				msg += "#L6##e#rHair6#n#k I want to use a shop."+enter;
				cm.sendSimple(msg);
			break;
			case 2:
				var msg = "#fs11##d#eAzureMS Plastic Surgery Shop.#n#k#fs11#"+enter;
				msg += "#L1##e#rLatest#n#k I want to use a shop."+enter;
				msg += "#L3##e#r　Sphere Surgery#n#k I want to use a shop."+enter;
				//msg += "#L2##e해외 성형#n I want to use a shop."+enter;
				cm.sendSimple(msg);
			break;
			case 3: // skin care
				cm.askAvatar("Please choose your favorite skin color.", 피부);
			break;
			case 4: // lens
				if (cm.getPlayer().getGender() == 0) {
                			컬러 = cm.getPlayer().getFace() % 100 + 20000 + (cm.getPlayer().getFace() - 20000) - (cm.getPlayer().getFace() % 100);

                			렌즈D = [컬러, 컬러 + 100, 컬러 + 200, 컬러 + 300, 컬러 + 400, 컬러 + 500, 컬러 + 600, 컬러 + 700];
                			for (i = 0; i < 렌즈D.length; i++) {
                    				if (EHF(렌즈D[i]) && cm.getItemName(cm.getPlayer().getFace()).equals(cm.getItemName(렌즈D[i]))) 렌즈.push(cm.parseInt(렌즈D[i]));
					}
				} else {
					faceas = cm.getPlayer().getFace();
					plus = 0;
					if (faceas >= 24000 && faceas < 25000) {
						plus = 24000;
					} else if (faceas >= 26000 && faceas < 27000) {
						plus = 26000;
					} else {
						plus = 21000;
					}
                			컬러 = cm.getPlayer().getFace() % 100 + plus
					//cm.getPlayer().getFace() % 100 + 21000 + (cm.getPlayer().getFace() - 21000) - (cm.getPlayer().getFace() % 100);
                			렌즈D = [컬러, 컬러 + 100, 컬러 + 200, 컬러 + 300, 컬러 + 400, 컬러 + 500, 컬러 + 600, 컬러 + 700];
                			for (i = 0; i < 렌즈D.length; i++) {
                    				if (EHF(렌즈D[i]) && cm.getItemName(cm.getPlayer().getFace()).equals(cm.getItemName(렌즈D[i]))) 렌즈.push(cm.parseInt(렌즈D[i]));
                			}
				}

                		cm.askAvatar("Please choose your favorite lens.", 렌즈);
			break;
			case 5: // Mix Dyeing
            			cm.sendYesNo("Do you use mix dye? #r#e(May cause hair pauses that cannot be mixed dye.)");
			break;
			case 6: // Trans
            			var msg = "Your gender is";
            			if (cm.getPlayer().getGender() == 0) msg += " #b< Male >#kHey #r< Female >#kI will be a transgender?\r\n#d< #z37300# , #z21078# , Whitening >#kThis will apply.";
            			else msg += " #r< Female >#kHey #b< Male >#kI will be a transgender?\r\n#d< #z35290# , #z20047# , Whitening >#kThis will apply.";
            			cm.sendYesNo(msg);
			break;
			case 7:
        			if (cm.getPlayer().getAndroid() == null) {
            				cm.sendOk("If you don't have Android, you can't do Android makeup. Please come with Android.");
            				cm.dispose();
            				return;
        			}
				var msg = "#fs11##dAzureMS Android Looks Changes#k#fs11##b"+enter;
				msg += "#L1#I want to look around the hair shop."+enter;
				msg += "#L2#I want to look around the plastic shop. "+enter;
				msg += "#L3#I want to have skin care."+enter;
				msg += "#L4#I would like to receive a color lens."+enter;
				msg += "#L5#I want to get a hair dye."+enter;

				cm.sendSimple(msg);
			break;
			case 8: // Plain dyed
            			if (cm.getPlayer().getGender() == 0) {
                			컬러 = cm.parseInt(cm.getPlayer().getHair() / 10) * 10;
                			for (i = 0; i < 8; i++) 염색.push(cm.parseInt(컬러 + i));
                			cm.askAvatar("Please choose your favorite color."+염색, 염색);
            			} else {
                			컬러 = cm.parseInt(cm.getPlayer().getHair() / 10) * 10;
                			for (i = 0; i < 8; i++) 염색.push(cm.parseInt(컬러 + i));
                			cm.askAvatar("Please choose your favorite color."+염색, 염색);
            			}
			break;
		}
	} else if (status == 2) {
		seld2 = sel;

		switch (seld) {
			case 1: // Hair
				if (cm.getPlayer().getGender() == 0) {
					if (sel == 1) {
                				for (i = 0; i < 남자헤어1.length; i++) {
                    					if (EHF(남자헤어1[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어1[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 2) {
                				for (i = 0; i < 남자헤어2.length; i++) {
                    					if (EHF(남자헤어2[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어2[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
					            }
                                                            } else if (sel == 3) {
                				for (i = 0; i < 남자헤어3.length; i++) {
                    					if (EHF(남자헤어3[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어3[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 4) {
                				for (i = 0; i < 남자헤어4.length; i++) {
                    					if (EHF(남자헤어4[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어4[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						} 
					} else if (sel == 5) {
                				for (i = 0; i < 남자헤어5.length; i++) {
                    					if (EHF(남자헤어5[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어5[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						} 					
                                                                      } else {
                				for (i = 0; i < 남자헤어6.length; i++) {
                    					if (EHF(남자헤어6[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어6[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					}
				} else {
					if (sel == 1) {
                				for (i = 0; i < 여자헤어1.length; i++) {
                    					if (EHF(여자헤어1[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어1[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 2) {
                				for (i = 0; i < 여자헤어2.length; i++) {
                    					if (EHF(여자헤어2[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어2[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 3) {
                				for (i = 0; i < 여자헤어3.length; i++) {
                    					if (EHF(여자헤어3[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어3[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 4) {
                				for (i = 0; i < 여자헤어4.length; i++) {
                    					if (EHF(여자헤어4[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어4[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else if (sel == 5) {
                				for (i = 0; i < 여자헤어5.length; i++) {
                    					if (EHF(여자헤어5[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어5[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					} else {
                				for (i = 0; i < 여자헤어6.length; i++) {
                    					if (EHF(여자헤어6[i] + cm.parseInt(cm.getPlayer().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어6[i] + cm.parseInt(cm.getPlayer().getHair() % 10)));
						}
					}
				}
				cm.askAvatar("Please choose the hair you want.", 헤어);
			break;
			case 2: // Plastic Surgery
				if (cm.getPlayer().getGender() == 0) {
					if (sel == 1) {
                				for (i = 0; i < 최신남자성형.length; i++) {
                    					if (EHF(최신남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(최신남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}
					} else if (sel == 2) {
                				for (i = 0; i < 해외남자성형.length; i++) {
                    					if (EHF(해외남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(해외남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}
					} else {
                				for (i = 0; i < 구남자성형.length; i++) {
                    					if (EHF(구남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(구남자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}
					}
				} else {
					if (sel == 1) {
                				for (i = 0; i < 최신여자성형.length; i++) {
                    					if (EHF(최신여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(최신여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}
					} else if (sel == 2) {
                				for (i = 0; i < 해외여자성형.length; i++) {
                    					if (EHF(해외여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(해외여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}
					} else {
                				for (i = 0; i < 구여자성형.length; i++) {
                    					if (EHF(구여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100))) 성형.push(cm.parseInt(구여자성형[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100)));
						}

					}
				}
                		cm.askAvatar("Please choose your preferred Molding.", 성형);
			break;
			case 3: // skin care
				cm.sendOk("What do you think? I like the new skin?");
                		if (isBeta()) cm.setSecondSkin(피부[sel]);
                		else cm.setSkin(피부[sel]);

                		cm.dispose();
			break;
			case 4: // lens
				cm.sendOk("What do you think? I like the new lens?");
                		if (isBeta()) cm.setSecondFace(렌즈[sel]);
                		else cm.setFace(렌즈[sel]);

                		cm.dispose();
			break; 
			case 5: // Mix dyeing
                		cm.askCustomMixHairAndProb("Mix dyeing.");
			break;
			case 6: // Trans
                		if (cm.getPlayer().getGender() == 0) {
                    			cm.setHair(37300);
                    			cm.setFace(21078);
                    			cm.setSkin(3);
                    			cm.getPlayer().setGender(1);
                		} else {
                    			cm.setHair(35290);
                    			cm.setFace(20047);
                    			cm.setSkin(3);
                    			cm.getPlayer().setGender(0);
                		}
                   			cm.getPlayer().fakeRelog();
                    			//cm.updateChar();
                    			cm.dispose();
			break;
			case 8: // General Dyeing
				cm.sendOk("What do you think? I like the new dye?");
                		if (isBeta()) cm.setSecondHair(염색[sel]);
                		else cm.setHair(염색[sel]);
                		cm.dispose();
			break;
			case 7:
				android = true;
				switch (sel) {
					case 1: // hair
						var msg = "#fs11##d#eAzureMS Android Hair Shop.#n#k#fs11#"+enter;
				msg += "#L1##e#r헤어1#n#k I want to use a shop."+enter;
				msg += "#L2##r#e헤어2#n#k I want to use a shop."+enter;
				msg += "#L3##e#r헤어3#n#k I want to use a shop."+enter;
				msg += "#L4##e#r헤어4#n#k I want to use a shop."+enter;
				msg += "#L5##e#r헤어5#n#k I want to use a shop."+enter;
				msg += "#L6##e#r헤어6#n#k I want to use a shop."+enter;

						cm.sendSimple(msg);
					break;
					case 2: // Plastic Surgery
						var msg = "#fs11##d#eAzureMS Android Plastic Surgery Shop.#n#k#fs11#"+enter;
						msg += "#L1##e#rLatest molding#n#k I want to use a shop."+enter;
						//msg += "#L2##eOverseas molding#n I want to use a shop."+enter;
						msg += "#L3##e#r　Sphere molding#n#k I want to use a shop."+enter;

						cm.sendSimple(msg);
					break;
					case 3: // skin
						cm.askAvatar("Please choose your favorite skin.", 피부);
					break;
					case 4: // lens
            					if (cm.getAndroidGender() == 0) {
                					컬러 = cm.getPlayer().getAndroid().getFace() % 100 + 20000 + (cm.getPlayer().getAndroid().getFace() - 20000) - (cm.getPlayer().getAndroid().getFace() % 100);
                					렌즈D = [컬러, 컬러 + 100, 컬러 + 200, 컬러 + 300, 컬러 + 400, 컬러 + 500, 컬러 + 600, 컬러 + 700];
                					for (i = 0; i < 렌즈D.length; i++) {
                    						if (EHF(렌즈D[i]) && cm.getItemName(cm.getPlayer().getAndroid().getFace()).equals(cm.getItemName(렌즈D[i]))) 렌즈.push(cm.parseInt(렌즈D[i]));
                					}

                					cm.askAvatar("Please select your preferred lens.", 렌즈);
            					} else {
                					컬러 = cm.getPlayer().getAndroid().getFace() % 100 + 21000 + (cm.getPlayer().getAndroid().getFace() - 21000) - (cm.getPlayer().getAndroid().getFace() % 100);
                					렌즈D = [컬러, 컬러 + 100, 컬러 + 200, 컬러 + 300, 컬러 + 400, 컬러 + 500, 컬러 + 600, 컬러 + 700];
                					for (i = 0; i < 렌즈D.length; i++) {
                    						if (EHF(렌즈D[i]) && cm.getItemName(cm.getPlayer().getAndroid().getFace()).equals(cm.getItemName(렌즈D[i]))) 렌즈.push(cm.parseInt(렌즈D[i]));
                					}

                					cm.askAvatar("Please select your preferred lens.", 렌즈);
            					}
					break;
					case 5: // dyeing
            					if (cm.getAndroidGender() == 0) {
                					컬러 = cm.parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
                					for (i = 0; i < 8; i++) 염색.push(cm.parseInt(컬러 + i));
                					cm.askAvatar("Please choose your favorite color."+염색, 염색);
            					} else {
                					컬러 = cm.parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
                					for (i = 0; i < 8; i++) 염색.push(cm.parseInt(컬러 + i));
                					cm.askAvatar("Please choose your favorite color."+염색, 염색);
            					}
					break;
				}
			break;
		}
	} else if (status == 3) {
		if (!android) {
			switch (seld) {
				case 1:

					cm.sendOk("What do you think? I like the new hair?");
                			if (isBeta()) cm.setSecondHair(헤어[sel]);
                			else cm.setHair(헤어[sel]);
                			cm.dispose();
				break;
				case 2:

					cm.sendOk("What do you think? I like the new molding?");
                			if (isBeta()) cm.setSecondFace(성형[sel]);
                			else cm.setFace(성형[sel]);
                			cm.dispose();
				break;
				case 5:

					cm.sendOk("What do you think? I like the new hair?");
                			if (isBeta()) cm.setSecondHair(헤어[sel]);
                			else cm.setHair(헤어[sel]);
                			cm.dispose();
				break;
			}
		} else {
			androidseld = sel;
			switch (seld2) {
				case 1:
					andhair = true;
					if (cm.getAndroidGender() == 0) {
						if (sel == 1) {
                					for (i = 0; i < 남자헤어1.length; i++) {
                    						if (EHF(남자헤어1[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어1[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 2) {
                					for (i = 0; i < 남자헤어2.length; i++) {
                    						if (EHF(남자헤어2[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어2[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 3) {
                					for (i = 0; i < 남자헤어3.length; i++) {
                    						if (EHF(남자헤어3[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어3[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 4) {
                					for (i = 0; i < 남자헤어4.length; i++) {
                    						if (EHF(남자헤어4[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어4[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 5) {
                					for (i = 0; i < 남자헤어5.length; i++) {
                    						if (EHF(남자헤어5[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어5[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else {
                					for (i = 0; i < 남자헤어6.length; i++) {
                    						if (EHF(남자헤어6[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(남자헤어6[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						}
					} else {
						if (sel == 1) {
                					for (i = 0; i < 여자헤어1.length; i++) {
                    						if (EHF(여자헤어1[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어1[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 2) {
                					for (i = 0; i < 여자헤어2.length; i++) {
                    						if (EHF(여자헤어2[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어2[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 3) {
                					for (i = 0; i < 여자헤어3.length; i++) {
                    						if (EHF(여자헤어3[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어3[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 4) {
                					for (i = 0; i < 여자헤어4.length; i++) {
                    						if (EHF(여자헤어4[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어4[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else if (sel == 5) {
                					for (i = 0; i < 여자헤어5.length; i++) {
                    						if (EHF(여자헤어5[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어5[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						} else {
                					for (i = 0; i < 여자헤어6.length; i++) {
                    						if (EHF(여자헤어6[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10))) 헤어.push(cm.parseInt(여자헤어6[i] + cm.parseInt(cm.getPlayer().getAndroid().getHair() % 10)));
							}
						}
					}
					cm.askAvatar("Please choose the hair you want.", 헤어);
				break;
				case 2:
					if (cm.getAndroidGender() == 0) {
						if (sel == 1) {
                					for (i = 0; i < 최신남자성형.length; i++) {
                    						if (EHF(최신남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(최신남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}
						} else if (sel == 2) {
                					for (i = 0; i < 해외남자성형.length; i++) {
                    						if (EHF(해외남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(해외남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}
						} else {
                					for (i = 0; i < 구남자성형.length; i++) {
                    						if (EHF(구남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(구남자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}
						}
					} else {
						if (sel == 1) {
                					for (i = 0; i < 최신여자성형.length; i++) {
                    						if (EHF(최신여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(최신여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}
						} else if (sel == 2) {
                					for (i = 0; i < 해외여자성형.length; i++) {
                    						if (EHF(해외여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(해외여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}
						} else {
                					for (i = 0; i < 구여자성형.length; i++) {
                    						if (EHF(구여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100))) 성형.push(cm.parseInt(구여자성형[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100)));
							}

						}
					}

                			cm.askAvatar("Please choose your preferred molding.", 성형);
				break;
				case 3: // Skin
					cm.sendOk("What do you think? I like the new skin?");
					cm.setAndroidSkinColor(피부[sel]);
                			cm.dispose();
				break;
				case 4: // Lens
					cm.sendOk("What do you think? I like the new lens?");
					cm.setAndroidFace(렌즈[sel]);
                			cm.dispose();
				break;
				case 5: // Hair
					cm.sendOk("What do you think? I like the new hair color?");
					cm.setAndroidHair(염색[sel]);
                			cm.dispose();
				break;
			}
		}
	} else if (status == 4) {
		if (andhair) {
			cm.sendOk("What do you think? I like the new hair?");
			cm.setAndroidHair(헤어[sel]);
                	cm.dispose();
		} else {
			cm.sendOk("What do you think? I like the new face?");
			cm.setAndroidFace(성형[sel]);
                	cm.dispose();
		}
	}
}

function isBeta() {
	var isBeta = false;
	if (GameConstants.isZero(cm.getPlayer().getJob()) && cm.getPlayer().getGender() == 1) isBeta = true;
	return isBeta;
}

function EHF(i) {
    return ServerConstants.real_face_hair.contains("" + i);
}