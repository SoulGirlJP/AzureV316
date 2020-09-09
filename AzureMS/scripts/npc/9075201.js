


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	엔피시아이디 : 1012123
	
	엔피시 이름 : 써니

	엔피시가 있는 맵 : 헤네시스헤어샵

	엔피시 설명 : 안드로이드메이크업


*/






/*

	성형 엔피시

*/

var status = 0;
var beauty = 0;
var facenew;
var colors;
var hairnew;
var haircolor;
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
    if (status >= 0 && mode == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
        if (cm.getPlayer().getAndroid() == null) {
            cm.sendOk("안드로이드가 없는 분은 안드로이드 메이크업을 하실 수 없습니다. 안드로이드와 함께 찾아와 주세요.");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getAndroid().getItemId() != 1662002 && cm.getPlayer().getAndroid().getItemId() != 1662003) {
            cm.sendOk("고급형 안드로이드만 성형과 머리손질이 가능합니다.");
            cm.dispose();
            return;
        }
	    cm.sendSimple("모든 성형과 머리손질은#로 이용하실 수 있습니다. 원하시는 안드로이드 코디를 해보세요!\r\n\r\n#e#r※ 주의 : 눈 색 변경이나, 성형시 게임이 종료되는 경우는 다른 눈으로 성형을 하시기 바랍니다. (해당 성형에 맞는 눈색깔 이미지가 없을 경우 튕깁니다.)#e#n\r\n\r\n#L1##b머리#k#l\r\n#L2##b머리색#k#l\r\n#L3##b얼굴#k#l\r\n#L4##b눈색깔#k#l");
    } else if (status == 1) {
	if (cm.getAndroidGender() == 0) {if (selection == 1) {
		beauty = 2;
		hairnew = Array();
		for (var i = 0; i < mhair.length; i++) {
		    if (mhair[i] == 30100 || mhair[i] == 30010) {
			hairnew.push(mhair[i]);
		    } else {
			hairnew.push(mhair[i] + parseInt(cm.getPlayer().getAndroid().getHair() % 10));
		    }
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", hairnew);
	    } else if (selection == 2) {
		beauty = 3;
		haircolor = Array();
		var current = parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
		if (current == 30100) {
		    haircolor = Array(current, current + 1, current + 2, current + 3, current + 4, current + 5, current + 6, current + 7);
		} else if (current == 30010) {
		    haircolor = Array(current);
		} else {
		    for (var i = 0; i < 8; i++) {
			haircolor.push(current + i);
		    }
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", haircolor);
	    } else if (selection == 3) {
		beauty = 4;
		facenew = Array();
		for (var i = 0; i < mface.length; i++) {
		    if (mface[i] == 20021 || mface[i] == 20022) {
			facenew.push(mface[i]);
		    } else {
			facenew.push(mface[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100));
		    }
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", facenew);
	    } else if (selection == 4) {
		beauty = 5;
		var current = cm.getPlayer().getAndroid().getFace() % 100 + 20000;
		colors = Array();
		if (current == 20021 || current == 20022) {
		    colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 600, current + 700);
		} else if (current == 20041 || current == 20042) {
		    colors = Array(current, current + 100, current + 200, current + 300);
		} else {
		    colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 500, current + 600, current + 700, current + 800);
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", colors);
	    }
	} else {
            if (selection == 1) {
		beauty = 2;
		hairnew = Array();
		for (var i = 0; i < fhair.length; i++) {
		    hairnew.push(fhair[i] + parseInt(cm.getPlayer().getAndroid().getHair() % 10));
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", hairnew);
	    } else if (selection == 2) {
		beauty = 3;
		haircolor = Array();
		var current = parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
		for (var i = 0; i < 8; i++) {
		    haircolor.push(current + i);
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", haircolor);
	    } else if (selection == 3) {
		beauty = 4;
		facenew = Array();
		for (var i = 0; i < fface.length; i++) {
		    facenew.push(fface[i] + cm.getPlayer().getAndroid().getFace() % 1000 - (cm.getPlayer().getAndroid().getFace() % 100));
		}
		cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", facenew);
	    } else if (selection == 4) {
		beauty = 5;
		var current = cm.getPlayer().getAndroid().getFace() % 100 + 21000;
		colors = Array();
 		if (current == 21139 || current == 21140) {
		    colors = Array(current, current + 100, current + 200);
		} else {
		colors = Array(current, current + 100, current + 200, current + 300, current + 400, current + 500, current + 600, current + 700, current + 800);
		} cm.askAvatarAndroid("원하는 아바타를 선택해주세요.", colors);
	    }
	}
    } else if (status == 2) {
        selection = selection & 0xFF;
            cm.sendOk("아이템을 제대로 갖고 계신것이 맞는지 확인해 주세요.");
            cm.dispose();
            return;
        }
        else if (beauty == 2 || beauty == 6)
	    cm.setHairAndroid(hairnew[selection]);
        else if (beauty == 3)
	    cm.setHairAndroid(haircolor[selection]);
        else if (beauty == 4 || beauty == 7)
	    cm.setFaceAndroid(facenew[selection]);
        else if (beauty == 5)
	    cm.setFaceAndroid(colors[selection]);
        cm.dispose();
	   
    }
