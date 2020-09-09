importPackage(java.util);
importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client.items);

var status = 0;
var selectedType = -1;
var selectedItem = -1;
var item;
var mats;
var matQty;
var cost;
var qty;
var equip;

function start()
{
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection)
{
	if (mode == 1)
	status++;
	else
	{
		cm.dispose();
		return;
	}

	if (status == 0 && mode == 1)
	{
	var k = "#fUI/UIToolTip/Item/Equip/Star/Star#"
	var chat ="제련 및 아이템을 제작할 수 있는 것 같다….#b\r\n\r\n"
	chat += "#r#e[원석 제작 및 제련]#b#n\r\n";
	chat += "#L2#"+k+" #e광석#n, #e보석#n 및 #e크리스탈#n을 제련한다.#l\r\n\r\n\r\n"

	chat += "#r#e[150 레벨 무기 및 방어구 제작]#b#n\r\n";
	chat += "#L10#"+k+" #e150 레벨 루타비스 무기#n를 제작한다.#l\r\n"
	chat += "#L11#"+k+" #e150 레벨 루타비스 방어구#n를 제작한다.#l\r\n\r\n\r\n"
	cm.sendSimpleS(chat, 2)
	}

	else if (status == 1 && mode == 1)
	{
	selectedType = selection;

	if (selectedType == 2)
	{
	var items = new Array (4011000, 4011001, 4011002, 4011003, 4011004, 4011005, 4011006, 4011008, 4021000, 4021001, 4021002, 4021003, 4021004, 4021005, 4021006, 4021007, 4021008, 4005000, 4005001, 4005002, 4005003, 4005004);
	var selStr = "어떤 종류의 광석, 보석 또는 크리스탈을 제련할지 선택하자.\r\n\r\n#b";
	selStr += "#e#r[광석 제련]#k#n\r\n";
	for (i = 0; i < 8; selStr += "#b#L"+i+"##i"+items[i]+":# #t"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[보석 제련]#k#n\r\n";
	for (i = 8; i < 17; selStr += "#b#L"+i+"##i"+items[i]+":# #t"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[크리스탈 제련]#k#n\r\n";
	for (i = 17; i < 22; selStr += "#b#L"+i+"##i"+items[i]+":# #t"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	cm.sendSimpleS(selStr, 2)
	equip = false;
	}


	if (selectedType == 10)
	{
	var items = new Array (1302275, 1312153, 1322203, 1232057, 1402196, 1412135, 1422140, 1432167, 1442223, 1582016, 1372177, 1382208, 1212063, 1262016, 1452205, 1462193, 1522094, 1332225, 1342082, 1362090, 1242060, 1472214, 1222058, 1242061, 1482168, 1492179, 1532098);
	var selStr = "어떤 무기를 만들지 선택하자.\r\n\r\n#b";
	selStr += "#e#r[150 레벨 루타비스 전사 무기]#k#n\r\n";
	for (i = 0; i < 10; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 마법사 무기]#k#n\r\n";
	for (i = 10; i < 14; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 궁수 무기]#k#n\r\n";
	for (i = 14; i < 17; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 도적 무기]#k#n\r\n";
	for (i = 17; i < 22; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 해적 무기]#k#n\r\n";
	for (i = 22; i < 27; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	cm.sendSimpleS(selStr, 2);
	equip = true;
	}

	if (selectedType == 11){
	var items = new Array (1003797, 1003798, 1003799, 1003800, 1003801, 1042254, 1042255, 1042256, 1042257, 1042258, 1062165, 1062166, 1062167, 1062168, 1062169);
	var selStr = "어떤 방어구를 만들지 선택하자.\r\n\r\n#b";
	selStr += "#e#r[150 레벨 루타비스 모자]#k#n\r\n";
	for (i = 0; i < 5; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 상의]#k#n\r\n";
	for (i = 5; i < 10; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[150 레벨 루타비스 하의]#k#n\r\n";
	for (i = 10; i < 15; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	cm.sendSimpleS(selStr, 2);
	equip = true;
	}

	if (selectedType == 12)
	{
	var items = new Array (1302333, 1312199, 1322250, 1232109, 1402251, 1412177, 1422184, 1432214, 1442268, 1582017, 1372222, 1382259, 1212115, 1262017, 1452252, 1462239, 1522138, 1332274, 1342101, 1362135, 1242116, 1472261, 1222109, 1242120, 1482216, 1492231, 1532144);
	var selStr = "어떤 무기를 만들지 선택하자.\r\n\r\n#b";
	selStr += "#e#r[160 레벨 앱솔랩스 전사 무기]#k#n\r\n";
	for (i = 0; i < 10; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 마법사 무기]#k#n\r\n";
	for (i = 10; i < 14; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 궁수 무기]#k#n\r\n";
	for (i = 14; i < 17; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 도적 무기]#k#n\r\n";
	for (i = 17; i < 22; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 해적 무기]#k#n\r\n";
	for (i = 22; i < 27; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	cm.sendSimpleS(selStr, 2);
	equip = true;
	}

	if (selectedType == 13){
	var items = new Array (1004422, 1004423, 1004424, 1004425, 1004426, 1052882, 1052887, 1052888, 1052889, 1052890, 1073030, 1073032, 1073033, 1073034, 1073035, 1082636, 1082637, 1082638, 1082639, 1082640, 1102775, 1102794, 1102795, 1102796, 1102797, 1152174, 1152176, 1152177, 1152178, 1152179);
	var selStr = "어떤 방어구를 만들지 선택하자.\r\n\r\n#b";
	selStr += "#e#r[160 레벨 앱솔랩스 모자]#k#n\r\n";
	for (i = 0; i < 5; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 전신]#k#n\r\n";
	for (i = 5; i < 10; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 신발]#k#n\r\n";
	for (i = 10; i < 15; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 장갑]#k#n\r\n";
	for (i = 15; i < 20; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 망토]#k#n\r\n";
	for (i = 20; i < 25; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	selStr += "#e#r[160 레벨 앱솔랩스 어깨장식]#k#n\r\n";
	for (i = 25; i < 30; selStr += "#b#L"+i+"##z"+items[i]+"##l\r\n",i++); selStr += "\r\n\r\n";
	cm.sendSimpleS(selStr, 2);
	equip = true;
	}

	if (equip)
		status++;
	}
	else if (status == 2 && mode == 1) {
	selectedItem = selection;

		/* 제련 시작 */

	if (selectedType == 2){
		var itemSet   = new Array (4011000, 4011001, 4011002, 4011003, 4011004, 4011005, 4011006, 4011008, 4021000, 4021001, 4021002, 4021003, 4021004, 4021005, 4021006, 4021007, 4021008, 4005000, 4005001, 4005002, 4005003, 4005004);
		var matSet    = new Array (4010000, 4010001, 4010002, 4010003, 4010004, 4010005, 4010006, 4010007, 4020000, 4020001, 4021002, 4020003, 4020004, 4020005, 4020006, 4020007, 4020008, 4004000, 4004001, 4004002, 4004003, 4004004);
		var matQtySet = new Array (3,       3,       3,       3,       3,       3,       3,       6,       5,       5,       5,       5,       5,       5,       5,       7,       7,       6,       6,       6,       6,       10);
		var costSet   = new Array (10000,   10000,   10000,   10000,   10000,   10000,   10000,   100000,  10000,   10000,   10000,   10000,   10000,   10000,   10000,   100000,  100000,  80000,   80000,   80000,   80000,   200000)
		item = itemSet[selectedItem];
		mats = matSet[selectedItem];
		matQty = matQtySet[selectedItem];
		cost = costSet[selectedItem];
	}
	cm.sendGetNumber("#b#i"+item+"# #z"+item+"##k을 만드실껀가요? 그렇다면 몇 개를 만드시겠어요?",1,1,1000)
	}
	else if (status == 3 && mode == 1) {
	if (equip)
	{
		selectedItem = selection;
		qty = 1;
	}
	else
		qty = selection;

	if (selectedType == 10)
	{
		var itemSet   = new Array(1302275, 1312153, 1322203, 1232057, 1402196, 1412135, 1422140, 1432167, 1442223, 1582016, 1372177, 1382208, 1212063, 1262016, 1452205, 1462193, 1522094, 1332225, 1342082, 1362090, 1242060, 1472214, 1222058, 1242061, 1482168, 1492179, 1532098);
		var matSet    = new Array(
					new Array(1302152, 4005000, 4007005, 4021016), new Array(1312065, 4005000, 4007005, 4021016),
					new Array(1322096, 4005000, 4007005, 4021016), new Array(1232014, 4005000, 4007005, 4021016),
					new Array(1402095, 4005000, 4007005, 4021016), new Array(1412065, 4005000, 4007005, 4021016),
					new Array(1422066, 4005000, 4007005, 4021016), new Array(1432086, 4005000, 4007005, 4021016),
					new Array(1442116, 4005000, 4007005, 4021016), new Array(1582015, 4005000, 4007005, 4021016),

					new Array(1372084, 4005001, 4007005, 4021016), new Array(1382104, 4005001, 4007005, 4021016),
					new Array(1212014, 4005001, 4007005, 4021016), new Array(1262015, 4005001, 4007005, 4021016),

					new Array(1452111, 4005002, 4007005, 4021016), new Array(1462099, 4005002, 4007005, 4021016),
					new Array(1522018, 4005002, 4007005, 4021016),

					new Array(1332130, 4005003, 4007005, 4021016), new Array(1342036, 4005003, 4007005, 4021016),
					new Array(1362019, 4005003, 4007005, 4021016), new Array(1242042, 4005003, 4007005, 4021016),
					new Array(1472122, 4005003, 4007005, 4021016), 

					new Array(1222014, 4005002, 4007005, 4021016), new Array(1242014, 4005002, 4007005, 4021016),
					new Array(1482084, 4005002, 4007005, 4021016), new Array(1492085, 4005002, 4007005, 4021016),
					new Array(1532018, 4005002, 4007005, 4021016)

				)

		var matQtySet = new Array(
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10)
				);

		var costSet   = new Array(1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000, 1000000, 1000000
	
				);
		item = itemSet[selectedItem];
		mats = matSet[selectedItem];
		matQty = matQtySet[selectedItem];
		cost = costSet[selectedItem];
	}


	if (selectedType == 11)
	{
		var itemSet   = new Array(1003797, 1003798, 1003799, 1003800, 1003801, 1042254, 1042255, 1042256, 1042257, 1042258, 1062165, 1062166, 1062167, 1062168, 1062169);
		var matSet    = new Array(
					new Array(1003172, 4005000, 4007005, 4021016), new Array(1003173, 4005001, 4007005, 4021016), 
					new Array(1003174, 4005002, 4007005, 4021016), new Array(1003175, 4005003, 4007005, 4021016), 
					new Array(1003176, 4005002, 4007005, 4021016), 

					new Array(1052314, 4005000, 4007005, 4021016), new Array(1052315, 4005001, 4007005, 4021016), 
					new Array(1052316, 4005002, 4007005, 4021016), new Array(1052317, 4005003, 4007005, 4021016), 
					new Array(1052318, 4005002, 4007005, 4021016),

					new Array(1052314, 4005000, 4007005, 4021016), new Array(1052315, 4005001, 4007005, 4021016), 
					new Array(1052316, 4005002, 4007005, 4021016), new Array(1052317, 4005003, 4007005, 4021016), 
					new Array(1052318, 4005002, 4007005, 4021016)
				);

		var matQtySet = new Array(
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10),

					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10), new Array(1, 100, 50, 10),
					new Array(1, 100, 50, 10)
				);
		var costSet   = new Array(1000000, 1000000, 1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000, 1000000, 1000000,
					  1000000, 1000000, 1000000, 1000000, 1000000
	
				);
		item = itemSet[selectedItem];
		mats = matSet[selectedItem];
		matQty = matQtySet[selectedItem];
		cost = costSet[selectedItem];
	}


	if (selectedType == 12)
	{
		var itemSet   = new Array(1302333, 1312199, 1322250, 1232109, 1402251, 1412177, 1422184, 1432214, 1442268, 1582017, 1372222, 1382259, 1212115, 1262017, 1452252, 1462239, 1522138, 1332274, 1342101, 1362135, 1242116, 1472261, 1222109, 1242120, 1482216, 1492231, 1532144);
		var matSet    = new Array(
					new Array(1302275, 4005000, 4005004, 4007005, 4021016), new Array(1312153, 4005000, 4005004, 4007005, 4021016),
					new Array(1322203, 4005000, 4005004, 4007005, 4021016), new Array(1232057, 4005000, 4005004, 4007005, 4021016),
					new Array(1402196, 4005000, 4005004, 4007005, 4021016), new Array(1412135, 4005000, 4005004, 4007005, 4021016),
					new Array(1422140, 4005000, 4005004, 4007005, 4021016), new Array(1432167, 4005000, 4005004, 4007005, 4021016),
					new Array(1442223, 4005000, 4005004, 4007005, 4021016), new Array(1582016, 4005000, 4005004, 4007005, 4021016),

					new Array(1372177, 4005001, 4005004, 4007005, 4021016), new Array(1382208, 4005001, 4005004, 4007005, 4021016),
					new Array(1212063, 4005001, 4005004, 4007005, 4021016), new Array(1262016, 4005001, 4005004, 4007005, 4021016),

					new Array(1452205, 4005002, 4005004, 4007005, 4021016), new Array(1462193, 4005002, 4005004, 4007005, 4021016),
					new Array(1522094, 4005002, 4005004, 4007005, 4021016),

					new Array(1332225, 4005003, 4005004, 4007005, 4021016), new Array(1342082, 4005003, 4005004, 4007005, 4021016),
					new Array(1362090, 4005003, 4005004, 4007005, 4021016), new Array(1242060, 4005003, 4005004, 4007005, 4021016),
					new Array(1472214, 4005003, 4005004, 4007005, 4021016), 

					new Array(1222058, 4005002, 4005004, 4007005, 4021016), new Array(1242061, 4005002, 4005004, 4007005, 4021016),
					new Array(1482168, 4005002, 4005004, 4007005, 4021016), new Array(1492179, 4005002, 4005004, 4007005, 4021016),
					new Array(1532098, 4005002, 4005004, 4007005, 4021016)

				)

		var matQtySet = new Array(
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30)
				);

		var costSet   = new Array(10000000, 10000000, 10000000, 10000000, 10000000, 10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000
	
				);
		item = itemSet[selectedItem];
		mats = matSet[selectedItem];
		matQty = matQtySet[selectedItem];
		cost = costSet[selectedItem];
	}

	if (selectedType == 13)
	{
		var itemSet   = new Array(1004422, 1004423, 1004424, 1004425, 1004426, 1052882, 1052887, 1052888, 1052889, 1052890, 1073030, 1073032, 1073033, 1073034, 1073035, 1082636, 1082637, 1082638, 1082639, 1082640, 1102775, 1102794, 1102795, 1102796, 1102797, 1152174, 1152176, 1152177, 1152178, 1152179);
		var matSet    = new Array(
					new Array(1003797, 4005000, 4005004, 4007005, 4021016), new Array(1003798, 4005001, 4005004, 4007005, 4021016),
					new Array(1003799, 4005002, 4005004, 4007005, 4021016), new Array(1003800, 4005003, 4005004, 4007005, 4021016),
					new Array(1003801, 4005002, 4005004, 4007005, 4021016),

					new Array(1042254, 4005000, 4005004, 4007005, 4021016), new Array(1042255, 4005001, 4005004, 4007005, 4021016),
					new Array(1042256, 4005002, 4005004, 4007005, 4021016), new Array(1042257, 4005003, 4005004, 4007005, 4021016),
					new Array(1042258, 4005002, 4005004, 4007005, 4021016),

					new Array(1042254, 4005000, 4005004, 4007005, 4021016), new Array(1042255, 4005001, 4005004, 4007005, 4021016),
					new Array(1042256, 4005002, 4005004, 4007005, 4021016), new Array(1042257, 4005003, 4005004, 4007005, 4021016),
					new Array(1042258, 4005002, 4005004, 4007005, 4021016),

					new Array(1062165, 4005000, 4005004, 4007005, 4021016), new Array(1062166, 4005001, 4005004, 4007005, 4021016),
					new Array(1062167, 4005002, 4005004, 4007005, 4021016), new Array(1062168, 4005003, 4005004, 4007005, 4021016),
					new Array(1062169, 4005002, 4005004, 4007005, 4021016),

					new Array(1062165, 4005000, 4005004, 4007005, 4021016), new Array(1062166, 4005001, 4005004, 4007005, 4021016),
					new Array(1062167, 4005002, 4005004, 4007005, 4021016), new Array(1062168, 4005003, 4005004, 4007005, 4021016),
					new Array(1062169, 4005002, 4005004, 4007005, 4021016),

					new Array(1152108, 4005000, 4005004, 4007005, 4021016), new Array(1152110, 4005001, 4005004, 4007005, 4021016),
					new Array(1152111, 4005002, 4005004, 4007005, 4021016), new Array(1152112, 4005003, 4005004, 4007005, 4021016),
					new Array(1152113, 4005002, 4005004, 4007005, 4021016)
				)

		var matQtySet = new Array(
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30),

					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30), new Array(1, 200, 100, 100, 30),
					new Array(1, 200, 100, 100, 30)

				);

		var costSet   = new Array(10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000,
					  10000000, 10000000, 10000000, 10000000, 10000000

				);
		item = itemSet[selectedItem];
		mats = matSet[selectedItem];
		matQty = matQtySet[selectedItem];
		cost = costSet[selectedItem];
	}

	var prompt = "어디보자…. #b#z"+item+"# "+qty+"개#k를 만드려면 아래에 있는 재료와 수수료를 준비해야 하는군. 모두 준비가 되어있나?\r\n#b";
	if (mats instanceof Array)
	{
		for(var i = 0; i < mats.length; i++)
		{
			if(cm.itemQuantity(mats[i]) < matQty[i])
			{
				prompt += "\r\n#i"+mats[i]+":# #t"+mats[i]+"# (#r"+cm.itemQuantity(mats[i])+"개 #k/#b "+ matQty[i] * qty +"개)";
			}
			else
			{
				prompt += "\r\n#i"+mats[i]+":# #t"+mats[i]+"# (#b#e"+cm.itemQuantity(mats[i])+"개 #k#n/#b "+ matQty[i] * qty +"개)";
			}
		}
	}
	else {
		prompt += "\r\n#i"+mats+"# #z"+mats+"# " + matQty * qty + "개";
	}
		
	if (cost > 0)
			if(cost*qty != 0) {
		prompt += "\r\n#i4031138# #b"+cost * qty / 10000+"만 메소";
			}

	cm.sendYesNoS(prompt, 2);
	} else if (status == 4 && mode == 1) {
	var complete = false;
		
	if (cm.getMeso() < cost * qty) {
		cm.sendOk("메소#k 는 제대로 갖고 있는건가? 다시 한번 확인해보게.")
		cm.dispose();
		return;
	} else {
		if (mats instanceof Array) {
		for (var i = 0; i < mats.length; i++) {
			complete = cm.haveItem(mats[i], matQty[i] * qty);
			if (!complete) {
			break;
			}
		}
		} else {
		complete = cm.haveItem(mats, matQty * qty);
		}	
	}
			
	if (!cm.canHold(item)) {
		complete = false;
	}
	if (!complete)
		cm.sendOkS("재료가 모두 없거나, 인벤토리 공간이 부족한 것 같다. 수수료가 부족해도 만들 수 없다고 하는군.", 2);
	else {
		if (mats instanceof Array) {
		for (var i = 0; i < mats.length; i++){
			cm.gainItem(mats[i], -matQty[i] * qty);
		}
		}
		else
		cm.gainItem(mats, -matQty * qty);
					
		if (cost > 0)
		cm.gainMeso(-cost * qty);
				
		if (item == 4003000)//screws
		cm.gainItem(4003000, 15 * qty);
		else
		cm.gainItem(item, qty);
		cm.sendOk("자아.. 다 됐다구. 역시 완벽한 아이템이 탄생했잖아? 다른 작업도 필요하다면 다시 나에게 찾아오라구.");
	}
	cm.dispose();
	}
}