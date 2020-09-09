/*

제작자 : ljw5992@naver.com / dbg_yeane@nate.com
기능 수정 : kkilook_adm@naver.com 

*/

 

var status = -1;

function start() {
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        /*
        상점 셀렉션은 0부터 99까지
        이동 셀렉션은 100부터 199까지
        컨텐츠 셀렉션은 200부터 299까지
        */
        var choose = "    #e#r @스마트온라인@#n#r의 낚시상점을 맡고있는 #n#e#g오즈#n#r 입니다.#n\r\n";
        choose += "#e#r#L2#몸치상점#k";
        choose += "#e#b#L1#음치상점";
        choose += "#e#g#L3#박치상점#n#k";
        choose += "#e#d#L4#종합상점#n#k";
        cm.sendSimple(choose);
    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 0) {
            cm.enterCS();
 } else if (s == 1) {
  cm.openNpc (11100);
 } else if (s == 2) {
  cm.openNpc (2120003);
 } else if (s == 3) {
  cm.openNpc (9000443);
 } else if (s == 4) {
  cm.openNpc (2003); 
 } else if (s == 5) {
  cm.openNpc (9072100);
 } else if (s == 6) {
  cm.openNpc (2040001); 
 } else if (s == 7) {
  cm.openNpc (2040002);
 } else if (s == 8) {
  cm.openNpc (2040028); 
 } else if (s == 9) {
  cm.openNpc (1096010); 
 } else if (s == 10) {
  cm.openNpc (9000008); 
 } else if (s == 11) {
  cm.openNpc (1012000); 
 } else if (s == 12) {
  cm.openNpc (1012102); 
 } else if (s == 13) {
cm.warp (262000300, 0); 
 } else if (s == 14) {
  cm.openNpc (9000131);
 } else if (s == 15) {
  cm.openNpc (9073008);
 } else if (s == 16) {
  cm.openNpc (1002100);
 } else if (s == 17) {
  cm.openNpc (2010011);
 } else if (s == 18) {
  cm.openNpc (9071003);
 } else if (s == 21) {
  cm.openNpc (1063016);
 } else if (s == 22) {
cm.warp (271000000, 0);
 } else if (s == 23) {
cm.warp (270000000, 0);
 } else if (s == 24) {
  cm.warp(100000001, 0);
 } else if (s == 25) {
cm.warp (910000000, 0);
 } else if (s == 26) {
  cm.openNpc (1063016);
 } else if (s == 27) {
cm.warp (109090000, 0);
 } else if (s == 28) {
cm.warp (910530000, 0);
 } else if (s == 31) {
  cm.openNpc (1033112);  
 } else if (s == 30) {
  cm.openNpc (9010022);
 } else if (s == 80) {
  cm.openNpc (1104313); 
 } else if (s == 81) {
  cm.openNpc (2300000); 
 } else if (s == 82) {
  cm.openNpc (9000155); 
 } else if (s == 83) {
  cm.openNpc (2191005);  
 } else if (s == 84) {
  cm.openNpc (9040011);
 } else if (s == 85) {
  cm.openNpc (1094000);
 } else if (s == 86) {
  cm.openNpc (2040052);
 } else if (s == 87) {
  cm.openNpc (3001108);
 } else if (s == 89) {
  cm.openNpc (2192002); 
 } else if (s == 90) {
  cm.openNpc (1012121); 
 } else if (s == 92) {
  cm.openNpc (9050009);
 } else if (s == 93) {
  cm.openNpc (9050009);
 } else if (s == 94) {
  cm.openNpc (1032201);
 } else if (s == 101) {
  cm.openNpc (9000100);
 } else if (s == 102) {
cm.warp (100000002, 0);
 } else if (s == 104) {
  cm.openNpc (3001108);
 } else if (s == 3010) {
  cm.openNpc (1402104);
 } else if (s == 105) {
  cm.openNpc (2180000);
 } else if (s == 107) {
  cm.warp(105200000, 0);
 } else if (s == 108) {
cm.warp (910028300, 0);
 } else if (s == 110) {
  cm.openNpc (1012115);
 } else if (s == 111) {
  cm.openNpc (1012107);
 } else if (s == 112) {
  cm.openNpc (2040029);
 } else if (s == 113) {
  cm.openNpc (2142001);
 } else if (s == 114) {
  cm.openNpc (9072200);
 } else if (s == 115) {
  cm.openNpc (2041022);
 } else if (s == 116) {
  cm.openNpc (1032212);
 } else if (s == 117) {
  cm.openNpc (2084000);
 } else if (s == 118) {
  cm.openNpc (9900003);
 } else if (s == 130) {
  cm.openNpc (1063016);
 } else if (s == 2000) {
  cm.openNpc(9330038);
 } else if (s == 3001) {
  cm.openNpc(2430024);
 } else if (s == 3002) {
  cm.openNpc(2144013);
 } else if (s == 119) {
  cm.openNpc(1404012);
 } else if (s == 3003) {
  cm.openNpc(1404012);
 } else if (s == 3007) {
  cm.openNpc(1104102);
 } else if (s == 3009) {
  cm.openNpc(9000266);
 } else if (s == 3006) {
  cm.openNpc(9000069);
 } else if (s == 3050) {
  cm.openNpc(1104205);
 } else if (s == 5000) {
  cm.openNpc(2540017);
 } else if (s == 50030) {
  cm.openNpc(9000069);
 } else if (s == 30060) {
  cm.openNpc(9010041);
 } else if (s == 5001) {
  cm.openNpc(2220000);
 } else if (s == 5002) {
  cm.openNpc(1104100);
 } else if (s == 50031) {
  cm.openNpc(2090100);
 } else if (s == 50032) {
  cm.openNpc(2084001);
 } else if (s == 58000) {
  cm.openNpc(1012104);
 } else if (s == 50000) {
  cm.openNpc(1012104);



 } else if (s == 350) {
 cm.sendOk ("상품권후원은 아래양식에 맞게 작성해서 메일로 보내주세요\r\n상품권이름 : \r\n상품권핀번호 : \r\n상품권발행일자(해피머니일경우) : \r\n상품권금액 : \r\n지급받을닉네임 : \r\n kki_looking@naver.com 으로 이렇게 양식에맞춰보내시면\r\n최대한 빨리지급해드리겠습니다.\r\n가격표는 www.coreple.kr.pe 접속후 통합공지보세요\r\n총운영자 외 다른운영자한테는 절대후원이불가능합니다.");
  
        }
    }
}
