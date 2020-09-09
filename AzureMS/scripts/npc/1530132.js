/* 
 Áö¾Æ¾¾(wksthfl111,takedawon,bolanic) Has produced. 
*/ 

var status = -1; 
var coin = 4310185; // Write down your desired coin

var mtE0 = new  Array(5000930,5000931,5000932,5000933,5000934,5000935,5000936,5000937,5000938,5000751,5000752,5000753,5000721,5000722,5000723,5000736,5000737,5000738,5000979,5000980,5000981,5000433,5000434,5000435,5000707,5000708,5000709,5000963,5000964,5000965,5000960,5000961,5000962,5000918,5000919,5000920,5000790,5000791,5000792,5000460,5000461,5000462,5000762,5000763,5000764,5000939,5000940,5001009,5001010,5001011,5000469,5000470,5000471,5000415,5000416,5000417,5000405,5000406,5000407,5000342,5000343,5000344,5000921,5000922,5000923,5000793,5000794,5000795,5000641,5000642,5000643,5000620,5000621,5000622,5000000,5000001,5000002,5000003,5000004,5000005,5000006,5000007,5000008,5000009,5000010,5000011,5000012,5000013,5000014,5000015,5000017,5000020,5000021,5000022,5000023,5000024,5000026,5000040,5000041,5000042,5000043,5000046,5000054,5000064,5000065,5000068,5000072,5000083,5000084,5000087,5000088,5000089,5000090,5000091,5000092,5000093,5000152,5000171,5000201,5000202,5000206,5000207,5000210,5000211,5000215,5000216,5000217,5000221,5000225,5000228,5000229,5000230,5000231,5000232,5000233,5000237,5000238,5000239,5000240,5000243,5000244,5000245,5000249,5000250,5000251,5000256,5000257,5000258,5000261,5000269,5000270,5000271,5000272,5000273,5000275,5000276,5000277,5000281,5000282,5000283,5000290,5000291,5000292,5000293,5000294,5000295,5000296,5000297,5000298,5000299,5000309,5000310,5000311,5000314,5000316,5000317,5000320,5000321,5000322,5000328,5000330,5000331,5000332,5000341,5000342,5000343,5000344,5000352,5000353,5000354,5000365,5000366,5000367,5000368,5000375,5000376,5000377,5000381,5000383,5000385,5000386,5000387,5000402,5000403,5000404,5000405,5000406,5000407,5000408,5000409,5000414,5000415,5000416,5000417,5000428,5000429,5000430,5000431,5000432,5000433,5000434,5000435,5000437,5000443,5000444,5000445,5000446,5000447,5000448,5000452,5000459,5000460,5000461,5000462,5000466,5000468,5000469,5000470,5000471,5000473,5000474,5000475,5000476,5000479,5000480,5000482,5000484,5000485,5000486,5000490,5000491,5000492,5000493,5000494,5000495,5000496,5000600,5000601,5000602,5000620,5000621,5000622,5000624,5000633,5000634,5000635,5000641,5000642,5000643,5000672,5000673,5000674,5001000,5001001,5001002,5001003,5001004,5001005,5001006,5001007,5001008,5001009,5001010,5001011,5000497,5000498,5000565,5000566,5000567,5000640,5001018,5001019,5001020,5001021,5001022,5001023,5000595,5000685,5000696,5000707,5000708,5000709,5000740,5000751,5000752,5000753,5000736,5000737,5000738, 5000762, 5000763, 5000764, 5000765, 5000766, 5000767, 5000768, 5000769, 5000772, 5000773, 5000774, 5000790, 5000791, 5000792, 5000793, 5000794, 5000795, 5000797, 5000796, 5000798, 5000918, 5000919, 5000920, 5000921, 5000922, 5000923, 5000924, 5000925); // Pet

var mtE1 = new Array(1802000,1802001,1802002,1802003,1802004,1802005,1802006,1802007,1802008,1802009,1802010,1802011,1802012,1802013,1802014,1802015,1802016,1802017,1802018,1802019,1802020,1802021,1802022,1802023,1802024,1802025,1802100,1802026,1802027,1802028,1802029,1802030,1802031,1802032,1802033,1802034,1802035,1802037,1802038,1802039,1802042,1802045,1802047,1802054,1802065,1802067,1802068,1802072,1802078,1802080,1802081,1802338,1802339,1802343,1802344,1802345,1802350,1802351,1802352,1802353,1802354,1802365,1802366,1802369,1802370,1802371,1802375,1802378,1802380,1802381,1802382,1802384,1802385,1802386,1802389,1802390,1802391,1802394,1802395,1802396,1802418,1802419,1802420,1802424,1802425,1802426,1802427,1802428,1802429,1802433,1802434,1802435,1802436,1802444,1802445,1802446,1802447,1802448,1802450,1802451,1802452,1802458,1802459,1802460,1802461,1802463,1802464,1802465,1802466,1802471,1802472,1802473,1802475,1802476,1802477,1802478,1802482,1802483,1802484,1802490,1802491,1802493,1802497,1802500,1802501,1802502,1802503,1802504,1802505,1802509,1802510,1802511,1802512,1802524,1802526,1802527,1802528,1802529,1802530,1802531,1802532,1802534,1802535,1802536,1802539,1802540,1802541,1802542,1802543,1802544,1802545,1802548,1802549,1802550,1802551,1802553,1802554,1802555,1802556,1802557,1802558,1802559,1802560,1802561,1802562,1802563,1802564,1802565,1802566,1802567,1802572,1802573,1802574,1802575,1802578,1802579,1802580,1802585,1802586,1802587,1802589,1802590,1802591,1802592,1802594,1802595,1802597,1802598,1802599,1802603,1802604,1802605,1802613,1802615); // Pet
//var mtE2 = new Array(5170000); // Pet Supplies

var mtE3 = new Array(5190000,5190001,5190002,5190003,5190004,5190006,5190010,5170000); // Pet Skill

var mtE4 = new  Array(5000451, 5000483, 5000505, 5000508, 5000509, 5000510, 5000514,5000515,5000516,5000517,5000518,5000519,5000520,5000521,5000524,5000528,5000529,5000591,5000592,5000593,5000594,5000607,5000608,5000609,5000618,5000629,5000630,5000631,5000632,5000636,5000637,5000638,5000639,5000640,5000644,5000645,5000646,5000668,5000682,5000683,5000684,5000730,5000731,5000732,5000812,5000813,5000814,5000821,5000822,5000823,5000824,5000828,5000829,5000830,5000831,5000835,5000836,5000903,5000904,5000905,5000909,5000910,5000911,5000926,5000927,5000928); // Overseas Pets

var number = Array (1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1); // This is the number of weapons or accessories 
var pr = Array (5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5); // Gupet 
var pr1 = Array (5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,55,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5); // Pet
var pr2 = Array (1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1); // ÆêÀåºñ
var pr3 = Array (300,300,300,300,300,300,300,300,300,300,300); // Pet Equipment   
 
function start() { 
    status = -1; 
    action(1, 0, 0); 
} 

function action(mode, type, selection) { 
    if (mode == 1) { 
        status++; 
    } else { 
        cm.dispose(); 
        return; 
    } 
	if (status == 0) { 
		cm.sendSimple("#fs11#Hi. Meow!\r\nPlease adopt us. Yes?#k\r\n#L0##r #i" + coin + "# #t" + coin + "# pet purchase. #k"); 
	} else if(status == 1) { 
      if (selection == 0){  
        cm.sendSimple("#fs11#A lot of us are waiting.#k\r\n[Current #i" + coin + "# #t" + coin + "#  QTY : " + cm.itemQuantity(coin) + "]\n\
        #b\r\n#L10##i5000635#  Regular Pets (#i4310185# Purple Orbs 5QTY)\r\n#L11##i1802578#\n\  Pet Equips (#i4310185# Purple Orbs 5QTY)\r\n#L13##i5190010#\n\  Pet Skills (#i4310185# Purple Orbs 5QTY)\r\n"); 
      } 
	} else if(status == 2) { 
		zia = selection; 
		if(zia == 10) { 
			var where = "#fs11#Which one would you like to take home?\r\n"; 
			for (var i = 0; i < mtE0.length; i++) { 
				where += "\r\n#L" + i + "##b#i" + mtE0[i] + ":# #t" + mtE0[i] + "# (#i" + coin + "# = " + 5 + "QTY)";
 			} 
        	cm.sendSimple(where); 
		} else if(zia == 15) { 
			var where = "#fs11#Which one would you like to take home?\r\n"; 
			for (var i = 0; i < mtE4.length; i++) { 
				where += "\r\n#L" + i + "##b#i" + mtE4[i] + ":# #t" + mtE4[i] + "# (#i" + coin + "# = " + 5 + "QTY)";
 			} 
        	cm.sendSimple(where); 
} else if(zia == 11) { 
var where = "#fs11#What pretty clothing.\r\n"; 
for (var i = 0; i < mtE1.length; i++) { 
where += "\r\n#L" + i + "##b#i" + mtE1[i] + ":# #t" + mtE1[i] + "# (#i" + coin + "# = " + 5 + "°³)";
 } 
        cm.sendSimple(where); 
} else if(zia == 12) { 
var where = "#fs11# #dwhich #rfriend#k#dI'll take? \r\n"; 
for (var i = 0; i < mtE2.length; i++) { 
where += "\r\n#L" + i + "##b#i" + mtE2[i] + ":# #t" + mtE2[i] + "# (#i" + coin + "# = " + 5 + "°³)";
 } 
        cm.sendSimple(where); 
} else if(zia == 13) { 
var where = "#fs11#Friends are smart and can learn skills.\r\n"; 
for (var i = 0; i < mtE3.length; i++) { 
where += "\r\n#L" + i + "##b#i" + mtE3[i] + ":# #t" + mtE3[i] + "# (#i" + coin + "# = " + pr3[i] + "°³)";
 } 
	cm.sendSimple(where);
} else if(zia == 14) { 
var where = "#fs11# Pet Skill Description\r\n"; 
where += "\r\n#i5190000# #t5190000#";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to";
where += "\r\n#i5190001# #t5190001#";
where += "\r\nAfter purchase, double click to choose which pet to apply this skill to #rEkey#kPress to open the equipment window and select #rPet#kIf you see there #rPET HP#k There is a square called 'grey' #rHP Potion#k Upload it. Then there's a button called Use Potion #r(E key-> pet)#k Press to open the option window #rHP Warning Display#kin #b#e-#k#n Pets will automatically give you a potion if you have some health.";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to";
where += "\r\n#i5190003# #t5190003#";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to";
where += "\r\n#i5190004# #t5190004#";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to";
where += "\r\n#i5190006# #t5190006#";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to next #rE key#kPress to open the equipment window and select #rPet#kIf you see there #bPET MP#k There is a square called 'grey' #bMP Potion#k Upload it. Then there's a button called Use Potion #r(E key-> pet)#k Press to open the option window #bMP warning display#kin #b#e-#k#n If you have some mana with a bar, the pet will automatically feed the potion.";
where += "\r\n#i5190010# #t5190010#";
where += "\r\nAfter purchase, double click to select which pet to apply this skill to";
        cm.sendSimple(where); 
} 

} else if(status == 3) { 
zia1 = selection; 
if(zia == 10) { 
cm.sendYesNo(" #v" + coin + "# #b#t" + coin + "##k " + 5 +"I need a dock.\r\n#b  [possesion #t" + coin + "#  amount : " + cm.itemQuantity(coin) + "]");
 } else if(zia == 11) { 
cm.sendYesNo(" #v" + coin + "# #b#t" + coin + "##k " + 5 +"I need a dock.\r\n#b  [possesion #t" + coin + "#  amount : " + cm.itemQuantity(coin) + "]");
 } else if(zia == 12) { 
cm.sendYesNo(" #v" + coin + "# #b#t" + coin + "##k " + pr2[zia1] +"I need a dock.\r\n#b  [possesion #t" + coin + "#  amount : " + cm.itemQuantity(coin) + "]");  
 } else if(zia == 13) { 
cm.sendYesNo(" #v" + coin + "# #b#t" + coin + "##k " + pr3[zia1] +"I need a dock.\r\n#b  [possesion #t" + coin + "#  amount : " + cm.itemQuantity(coin) + "]");
 } else if(zia == 15) { 
cm.sendYesNo(" #v" + coin + "# #b#t" + coin + "##k " + 5 +"I need a Dock.\r\n#b  [possesion #t" + coin + "#  amount : " + cm.itemQuantity(coin) + "]");
 }

} else if(status == 4) { 
if(zia == 10) { 
if (cm.haveItem(coin,5) && cm.canHold(mtE0[zia1])) { 
cm.gainItem(coin,-5); 
cm.BuyPET(mtE0[zia1]); 
cm.sendOk("#b#v" + mtE0[zia1] + "##t" + mtE0[zia1] + "##k in #bexchange#kBeen."); 
cm.dispose(); 
} else { 
cm.sendOk("Purple Flower Orb #rFriend#kI can take you away.#k"); 
cm.dispose(); 
} 
} else if(zia == 15) { 
if (cm.haveItem(coin,5) && cm.canHold(mtE4[zia1])) { 
cm.gainItem(coin,-5); 
cm.BuyPET(mtE4[zia1]); 
cm.sendOk("#b#v" + mtE4[zia1] + "##t" + mtE4[zia1] + "##k in #bexchange#kBeen."); 
cm.dispose(); 
} else { 
cm.sendOk("Purple Flower Orb #rFriend#kI can take you away.#k"); 
cm.dispose(); 
}
} else if(zia == 11) { 
if (cm.haveItem(coin,5) && cm.canHold(mtE1[zia1])) { 
cm.gainItem(coin,-5); 
cm.gainItem(mtE1[zia1], 1);
cm.sendOk("#b#v" + mtE1[zia1] + "##t" + mtE1[zia1] + "##k in #bexchange#kBeen."); 
cm.dispose(); 
} else { 
cm.sendOk("Purple Flower Orb #Friend#kI can take you away.#k"); 
cm.dispose(); 
}
} else if(zia == 12) { 
if (cm.haveItem(coin,pr2[zia1]) && cm.canHold(mtE2[zia1])) { 
cm.gainItem(); 
cm.gainItem(); 
cm.sendOk("#b#v" + mtE2[zia1] + "##t" + mtE2[zia1] + "##k in #bexchange#kBeen."); 
cm.dispose(); 
} else { 
cm.sendOk("#bThe coin does not exist or#k #rItem window is full.#k"); 
cm.dispose(); 
}
} else if(zia == 13) { 
if (cm.haveItem(coin,pr3[zia1]) && cm.canHold(mtE3[zia1])) { 
cm.gainItem(coin,-pr3[zia1]); 
cm.gainItem(mtE3[zia1],1); 
cm.sendOk("#b#v" + mtE3[zia1] + "##t" + mtE3[zia1] + "##k in #bexchange#kBeen."); 
cm.dispose(); 
} else { 
cm.sendOk("#bThe coin does not exist or#k #rItem window is full.#k"); 
cm.dispose(); 
}    
} 
} 
}
