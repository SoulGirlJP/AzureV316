/*
 
  최성우
  sqlstyle...

*/

importPackage(java.lang);
importPackage(Packages.handling.world);
importPackage(Packages.packet.creators);

var status = 0;
var beauty = 0;
var facenew;
var colors;
var hairnew;
var haircolor;
var skin = Array(0, 1, 2, 3, 4, 9, 10, 11, 12, 13);
var mface = Array(20000,20001,20002,20003,20004,20005,20006,20007,20008,20009,20010,20011,20012,20013,20014,20015,20016,20017,20018,20019,20020,20021,20022,20024,20025,20027,20028,20029,20030,20031,20032,20036,20037,20040,20043,20044,20045,20046,20047,20048,20049,20050,20053,20055,20056,20057,20058,20059,20060,20061,20062,20063,20064,20065,20066,20067,20068,20069);
var mhair = Array(30000,30020,30030,30040,30050,30060,30100,30110,30120,30130,30140,30150,30160,30170,30180,30190,30200,30210,30220,30230,30240,30250,30260,30270,30280,30290,30300,30310,30320,30330,30340,30350,30360,30370,30400,30410,30420,30440,30450,30460,30470,30480,30490,30510,30520,30530,30540,30560,30570,30590,30610,30620,30630,30640,30650,30660,30670,30680,30700,30710,30730,30760,30770,30790,30800,30810,30820,30830,30840,30850,30860,30870,30880,30910,30930,30940,30950,33030,33060,33070,33080,33090,33110,33120,33130,33150,33170,33180,33190,33210,33220,33250,33260,33270,33280,33310,33330,33350,33360,33370,33380,33390,33400,33410,33430,33440,33450,33460,33480,33500,33510,33520,33530,33550,33580,33590,33600,33610,33620,33630,33640,33660,33670,33680,33690,33700,33710,33720,33730,33740,33750,33760,33770,33780,33790,33800,33810,33820,33830,33930,33940,33950,33960);
var fface = Array(21000,21001,21002,21003,21004,21005,21006,21007,21008,21009,21010,21011,21012,21013,21014,21015,21016,21017,21018,21019,21020,21021,21023,21024,21026,21027,21028,21029,21030,21031,21033,21035,21036,21038,21041,21042,21043,21044,21045,21046,21047,21048,21052,21053,21054,21055,21056,21057,21058,21059,21060,21061,21062,21063,21064);
var fhair = Array(31000,31010,31020,31030,31040,31050,31060,31070,31080,31090,31100,31110,31120,31130,31140,31150,31160,31170,31180,31190,31200,31210,31220,31230,31240,31250,31260,31270,31280,31290,31300,31310,31320,31330,31340,31350,31400,31410,31420,31440,31450,31460,31470,31480,31490,31510,31520,31530,31540,31550,31560,31590,31610,31620,31630,31640,31650,31670,31680,31690,31700,31710,31720,31740,31750,31780,31790,31800,31810,31820,31840,31850,31860,31880,31890,31910,31920,31930,31940,31950,31990,34040,34070,34080,34090,34100,34110,34120,34130,34140,34150,34160,34170,34180,34190,34210,34220,34230,34240,34250,34260,34270,34310,34320,34330,34340,34360,34370,34380,34400,34410,34420,34430,34440,34450,34470,34480,34490,34510,34540,34560,34590,34600,34610,34620,34630,34640,34660,34670,34680,34690,34700,34710,34720,34730,34740,34750,34760,34770,34780,34790,34800,34810,34820,34830,34840,34850,34860,34870);
var select = -1;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var chat= "#fUI/UIWindow2.img/characterCard/BtHelp/normal/0#   TIP : [파티를 해야 도전하실수있습니다.]#l#e\r\n\r\n";
	    //chat += "#fs17##fUI/UIWindow2.img/MobGage/Mob/8800000# \#L0# 심연속 폐광의 군주 자쿰\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8810018# #k\#L1# 용의 군주라 불리는 혼테일\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8840000# #k\#L2# 검은마법사의 수문장 반 레온\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8870000# #k\#L3# 아스완의 망령을 다스리는 힐라\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8820001# #k\#L4# 시간의 신비 핑크빈\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8850011# #k\#L5# 미래의 여제 시그너스\r\n#l\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8880000# #k\#L6# 폭군의 왕 매그너스#l#e\r\n\r\n";
	    chat += "#fUI/UIWindow2.img/MobGage/Mob/8860000# #k\#L7# 시간을 탈취한자 아카이럼\r\n#l\r\n";
	    //chat += "#i3015155##L9#군단장 폭주한 스우\r\n#l\r\n";
	    //chat += "#fUI/UIWindow2.img/MobGage/Mob/8900100##fUI/UIWindow2.img/MobGage/Mob/8910100##fUI/UIWindow2.img/MobGage/Mob/8920100##fUI/UIWindow2.img/MobGage/Mob/8930100# #k\#L8# 루타비스 4대보스#l#e\r\n\r\n";
	    cm.sendSimple(chat);

                } else if (selection == 0) { //자쿰
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		chat += "#L10##fUI/UIWindow2.img/MobGage/Mob/8800000# #r#e[Lv60]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L11##fUI/UIWindow2.img/MobGage/Mob/8800000# #r#e[Lv100]#k#n#b#e노말모드#k#n#l\r\n";
		chat += "#L12##fUI/UIWindow2.img/MobGage/Mob/8800000# #r#e[Lv130]#k#n#b#e카오스모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 10) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(280030200).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(280030200,true);
		cm.removeNpc(2030010);
		cm.spawnNpc(2030010,-523,-420); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 11) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(280030000).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(280030000,true);
		cm.removeNpc(2030010);
		cm.spawnNpc(2030010,-523,-420); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 12) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(280030100).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(280030100,true);
		cm.removeNpc(2030010);
		cm.spawnNpc(2030010,-523,-420); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 1) { //혼테일
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		//chat += "#L13##fUI/UIWindow2.img/MobGage/Mob/8810018# #r#e[Lv130]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L14##fUI/UIWindow2.img/MobGage/Mob/8810018# #r#e[Lv130]#k#n#b#e노말모드#k#n#l\r\n";
		chat += "#L15##fUI/UIWindow2.img/MobGage/Mob/8810018# #r#e[Lv135]#k#n#b#e카오스모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 13) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(240050400).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(240050400,true);
		cm.removeNpc(2083002);
		cm.spawnNpc(2083002,540,22); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 14) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(240050400).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(240050400,true);
		cm.removeNpc(2083002);
		cm.spawnNpc(2083002,540,22);  
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 15) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(240050400).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(240050400,true);
		cm.removeNpc(2083002);
		cm.spawnNpc(2083002,540,22);  
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 2) { //반레온
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		chat += "#L23##fUI/UIWindow2.img/MobGage/Mob/8840000# #r#e[Lv125]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L24##fUI/UIWindow2.img/MobGage/Mob/8840000# #r#e[Lv125]#k#n#b#e노말모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 23) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(211070100).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(211070100,true);
		cm.killAllMob();
		cm.removeNpc(9000112);
		cm.spawnNpc(9000112,-59,-181);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 24) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(211070102).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(211070102,true); 
		cm.killAllMob();
		cm.removeNpc(9000112);
		cm.spawnNpc(9000112,-59,-181);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 3) { //힐라
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		chat += "#L16##fUI/UIWindow2.img/MobGage/Mob/8870000# #r#e[Lv120]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L17##fUI/UIWindow2.img/MobGage/Mob/8870000# #r#e[Lv170]#k#n#b#e하드모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 16) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(262030000).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(262030000,true); 
	        //cm.spawnMob(8870100, 5, -41);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 17) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(262030000).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(262030000,true);
		//cm.spawnMob(8870200, 5, -41); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 4) { //핑크빈
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		chat += "#L26##fUI/UIWindow2.img/MobGage/Mob/8820001# #r#e[Lv200]#k#n#b#e카오스hell 모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 25) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(270050100).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(270050100,true); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 26) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(330005200).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(330005200,true); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }


                } else if (selection == 5) { //시그너스
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		chat += "#L18##fUI/UIWindow2.img/MobGage/Mob/8850011# #r#e[Lv170]#k#n#b#e노말모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 18) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(262031300).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.resetMap(271040100);
		cm.killAllMob();
		cm.allPartyWarp(271040100,true);
		cm.removeNpc(1540729);
		cm.spawnNpc(1540729, -181, -100);
		//cm.spawnMob(8850000, -181, -100); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 6) { //매그너스
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		//chat += "#L18##fUI/UIWindow2.img/MobGage/Mob/8880000# #r#e[Lv115]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L19##fUI/UIWindow2.img/MobGage/Mob/8880000# #r#e[Lv155]#k#n#b#e노말모드#k#n#l\r\n";
		chat += "#L20##fUI/UIWindow2.img/MobGage/Mob/8880000# #r#e[Lv175]#k#n#b#e하드모드#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 19) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(401060200).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(401060200,true); 
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 20) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(401060300).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(401060300,true);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 7) { //아카이럼
		var chat= "#r#e난이도를 선택해 주세요.#k#n#l\r\n";
		//chat += "#L21##fUI/UIWindow2.img/MobGage/Mob/8860000# #r#e[Lv140]#k#n#b#e이지모드#k#n#l\r\n";
		chat += "#L22##fUI/UIWindow2.img/MobGage/Mob/8860000# #r#e[Lv140]#k#n#b#e입장신청#k#n#l\r\n";
		cm.sendSimple(chat);

                } else if (selection == 21) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(272020200).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(272020200,true);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }
                } else if (selection == 22) {
             if (cm.getClient().getChannelServer().getMapFactory().getMap(272020110).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	     if (cm.getPlayer().getParty() != null) {
		cm.allPartyWarp(272020110,true);
		cm.dispose();
            } else {
            cm.sendOk("파티를 만드셔야 입장하실수 있습니다.");
            cm.dispose();
        }

                } else if (selection == 8) { //루타비스
		cm.warp(105200000);
		cm.dispose();

                } else if (selection == 9) { //스우
             if (cm.getClient().getChannelServer().getMapFactory().getMap(350060160).getCharactersSize() > 0) {
             cm.sendOk("이미 다른 모험가가 입장하였습니다. 다른채널을 이용해주시거나 잠시후에 다시 찾아와 주십시오.");
             cm.dispose();
             return;
}
	    if (cm.getPlayer().getLevel() >= 140 && cm.getPlayer().getParty() != null) {
	    cm.resetMap(350060160);
            cm.allPartyWarp(350060160, true);
	    cm.spawnMob(8240097,-24,-16);
            cm.dispose();
            } else {
            cm.sendOk("레벨이 140이상이 아니시거나 파티가없으시면 도전을 못합니다.");
            cm.dispose();
        }

}
}
}