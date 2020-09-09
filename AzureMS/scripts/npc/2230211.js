


/*

	* 단문엔피시 자동제작 스크립트를 통해 만들어진 스크립트 입니다.

	* (Guardian Project Development Source Script)

	★총운영자★ 에 의해 만들어 졌습니다.

	엔피시아이디 : 2230211

	엔피시 이름 : 쿠엘베른

	엔피시가 있는 맵 : 크리티아스 왕국 : 왕궁 트뤼에페 (924040000)

	엔피시 설명 : 보상 상점


*/

var status = -1;var k = "#fNpc/9000000/stand/0#";
var k1 = "#fNpc/9000000/stand/0#";
function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
	cm.dispose();
	return;
    }
    if (status == 0) {
	var chat = "#i3992025# #g보통 난이도 출력";
chat += "\r\n#b#L4##fUI/UIWindow2.img/MobGage/Mob/8810018#혼테일#l #r#L5##fUI/UIWindow2.img/MobGage/Mob/8810018# 카오스혼테일#l ";
chat += "\r\n#b#L6##fUI/UIWindow2.img/MobGage/Mob/8870000#힐라#l     #r#L7##fUI/UIWindow2.img/MobGage/Mob/8870000# 하드힐라#l  ";
chat += "\r\n#b#L8##fUI/UIWindow2.img/MobGage/Mob/8900100#피에르#l #r#L9##fUI/UIWindow2.img/MobGage/Mob/8900100# 카오스피에르#l  ";
chat += "\r\n#b#L10##fUI/UIWindow2.img/MobGage/Mob/8910100#반반#l     #r#L11##fUI/UIWindow2.img/MobGage/Mob/8910100# 카오스반반#l";
chat += "\r\n#b#L12##fUI/UIWindow2.img/MobGage/Mob/8920100#블러디퀸#l #r#L13##fUI/UIWindow2.img/MobGage/Mob/8920100#카오스블러디퀸#l  ";
chat += "\r\n#b#L16##fUI/UIWindow2.img/MobGage/Mob/8840000#반레온#l     #r#L17##fUI/UIWindow2.img/MobGage/Mob/8840000#노말반레온#l ";
chat += "\r\n#b#L21##fUI/UIWindow2.img/MobGage/Mob/8880000#노말매그너스#l  #r#L22##fUI/UIWindow2.img/MobGage/Mob/8880000#하드매그너스#l  ";
chat += "\r\n#d#L29# 대화를 그만 한다.#l";







	cm.sendSpirit(chat,true,0);
    } else if (status == 1) {
	if (selection == 0) {
		cm.dispose();
		cm.openNpc(1064024);
	} else if (selection == 1) {
                cm.dispose();
		cm.warp(350060300);

         } else if (selection == 3000) {
                cm.dispose();
		cm.openNpc(2155005);
} else if (selection == 3050) {
                cm.dispose();
		cm.warp(330003000);
         } else if (selection == 3001) {
                cm.dispose();
		cm.openNpc(1061000);

 } else if (selection == 500) {
                cm.dispose();
		cm.openNpc(1064022);
} else if (selection == 3038) {
                cm.dispose();
		cm.openNpc(9000288);

           } else if (selection == 3002) {
                cm.dispose();
		cm.openNpc(1061005);

} else if (selection == 3006) {
                cm.dispose();
		cm.openNpc(9000025);

           } else if (selection == 3003) {
                cm.dispose();
		cm.openNpc(2080002);

} else if (selection == 3007) {
                cm.dispose();
		cm.openNpc(2411021);
           } else if (selection == 3004) {
                cm.dispose();
		cm.openNpc(2100007);
} else if (selection == 3005) {
                cm.dispose();
		cm.openNpc(1033002);
		
	} else if (selection == 2) {
		cm.dispose();
		cm.warp(280030100);
	} else if (selection == 3) {
		cm.dispose();
		cm.warp(280030000);
        } else if (selection == 4) {
		cm.dispose();
		cm.warp(240060200);

        } else if (selection == 5) {
		cm.dispose();
		cm.warp(240060201);

	} else if (selection == 6) {
                cm.dispose();
		cm.openNpc(2134007);

        } else if (selection == 7) {
                cm.dispose();
		cm.openNpc(2134006);

} else if (selection == 7000) {
                cm.dispose();
		cm.openNpc(1012002);

        } else if (selection == 8) {
                cm.dispose();
		cm.openNpc(2134004);

       
 } else if (selection == 9) {
                cm.dispose();
		cm.openNpc(2134010);

 } else if (selection == 10) {
                cm.dispose();
		cm.openNpc(2134005);

 } else if (selection == 11) {
                cm.dispose();
		cm.openNpc(2134009);

 } else if (selection == 12) {
                cm.dispose();
		cm.openNpc(2134001);

 } else if (selection == 13) {
                cm.dispose();
		cm.openNpc(2134002);

 } else if (selection == 14) {
                cm.dispose();
		cm.openNpc(2134011);

 } else if (selection == 15) {
                cm.dispose();
		cm.openNpc(2134003);

 } else if (selection == 16) {
                cm.dispose();
		cm.openNpc(2134000);

 } else if (selection == 17) {
                cm.dispose();
		cm.openNpc(2134008);

 } else if (selection == 18) {
                cm.dispose();
		cm.openNpc(2155008);

 } else if (selection == 19) {
                cm.dispose();
		cm.openNpc(2155006);

 } else if (selection == 20) {
                cm.dispose();
		cm.openNpc(2155004);

 } else if (selection == 21) {
                cm.dispose();
		cm.openNpc(2155002);

} else if (selection == 22) {
                cm.dispose();
		cm.openNpc(2155003);

} else if (selection == 23) {
                cm.dispose();
		cm.openNpc(2155010);

} else if (selection == 24) {
                cm.dispose();
		cm.openNpc(2155012);

} else if (selection == 25) {
                cm.dispose();
		cm.openNpc(2155013);

} else if (selection == 26) {
                cm.dispose();
		cm.openNpc(2134007);

} else if (selection == 3000) {
                cm.dispose();
		cm.openNpc(2134007);

} else if (selection == 27) {
                cm.dispose();
		cm.openNpc(2134007);


} else if (selection == 28) {
                cm.dispose();
		cm.openNpc(2134007);


	} else if (selection == 29) {
		cm.dispose();
	}
    } else if (status == 2) {
	cm.dispose();
	cm.openShop(selection);
    }
}
