importPackage(Packages.handling.world)
importPackage(Packages.tools.packet);

등급 = [["등급없음",0], ["스카웃",20000], ["서전트",50000], ["가디언",100000], ["마스터",250000], ["커맨더",500000], ["슈프림",1000000], ["에러","없음"]];
등급2 = ["없음", "블로거", "유튜버", "파워블로거", "컨텐츠 스트리머"];

별 = "#fUI/FarmUI.img/objectStatus/star/whole#";
star = "#fUI/UIToolTip.img/Item/Equip/Star/Star#"
star0 = "#fUI/UIToolTip.img/Item/Equip/Star/Star0#"
star1 = "#fUI/UIToolTip.img/Item/Equip/Star/Star1#"

Btstar = "#fUI/UIWindow7.img/Medal_15th/BtStar/normal/0#"
count = true;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 1) {
	if (selection == 100 && status == 0) {
		if (count) {
			count = false;
		} else {
			count = true;
		}
	} else {
		status++;
	}
    } else {
	cm.dispose();
	return;
    }
	if (status == 0) {
		if (cm.getPlayer().getKeyValue(20190824, "hongbostack_blog") == -1) {
			cm.getPlayer().setKeyValue(20190824, "hongbostack_blog", 0);
		}
		if (cm.getPlayer().getKeyValue(20190824, "hongbostack_youtube") == -1) {
			cm.getPlayer().setKeyValue(20190824, "hongbostack_youtube", 0);
		}
		var a = "#fn나눔고딕#";
		a += count ? Btstar + "#fs16##fc0xFF7758A5# 도네이션#k" : Btstar + "#fs16##fc0xFF7758A5# 서포트#k"
		a += count ? "                                    #fs14##L100##fc0xFFF361A6#<스위치>#k#l\r\n" : "                                           #fs14##L100##fc0xFFF361A6#<스위치>#k#l\r\n"
		a += "       "+star+" #fs14##b#e#h0##k#n 님의 #rStatus#k\r\n#fs12#";
		a += count ? "               "+star1+" #fc0xFF6799FF#도네이션#k 포인트 : #r"+cm.getPlayer().getDPoint()+"#k\r\n" : "               "+star1+" #fc0xFF6799FF#서포트#k 포인트 : #r"+cm.getPlayer().getHPoint()+"#k\r\n"
		a += count ? "               "+star1+" #fc0xFF6799FF#루비니아#k 랭크 : #r"+등급[cm.getPlayer().getKeyValue(190823, "grade")][0]+"#k\r\n" : "               "+star1+" #fc0xFF6799FF#서포트#k 등급 : #r"+등급2[0]+"#k\r\n"
		a += count ? "               "+star1+" #fc0xFF6799FF#다음 승급#k에 필요한 포인트 : #r"+등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][1]+"#k\r\n" : "               "+star1+" #fc0xFF6799FF#블로그#k 누적 포스팅 : #r"+cm.getPlayer().getKeyValue(20190824, "hongbostack_blog")+" 일차#k\r\n"
		if (!count) {
		    a += "               "+star1+" #fc0xFF6799FF#유튜브#k 누적 스트리밍 : #r"+cm.getPlayer().getKeyValue(20190824, "hongbostack_youtube")+" 일차#k\r\n"
		}
		a += "#fs12##e─────────────────────────────#n#fs#\r\n";
		a += Btstar + "#fs16##fc0xFF7758A5# 메뉴#k\r\n";
		a += count ? "   #L0#"+star+" #fs14##fc0xFF9FC93C#도네이션 상점#k#l\r\n" : "   #L0#"+star+" #fs14##fc0xFF9FC93C#서포트 상점#k#l\r\n"
		a += count ? "   #L1#"+star+" #fs14##fc0xFF9FC93C#도네이션 랭크 승급#k#l\r\n" : "   #L10#"+star+" #fs14##fc0xFF9FC93C#서포트 누적보상#k#l\r\n"
		a += count ? "   #L2#"+star+" #fs14##fc0xFF9FC93C#랭크특전#k#l\r\n" : ""
		a += "\r\n#fs12##e─────────────────────────────#n#fs#";
		cm.sendSimpleS(a, 2);
	} else if (status == 1) {
		st = selection
		if (selection == 0) {
			cm.dispose();
			ccount = count ? 0 : 1;
			cm.openNpcCustom(cm.getClient(), 9001048, "DShop_"+ccount);
		} else if (selection == 1) {
			if (cm.getPlayer().getKeyValue(190823, "grade") >= 6) {
			    cm.sendOkS("최고 랭크에 도달해서 더 이상 승급이 불가능하다.",2);
			    cm.dispose();
			    return;
			}
			var text = "현재 내 랭크는 #fEtc/ZodiacEvent.img/0/icon/"+cm.getPlayer().getKeyValue(190823, "grade")+"/0# #r"+등급[cm.getPlayer().getKeyValue(190823, "grade")][0]+"#k 이다.\r\n";
			text += "다음 랭크인 #fEtc/ZodiacEvent.img/0/icon/"+(cm.getPlayer().getKeyValue(190823, "grade") + 1)+"/0# #b"+등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][0]+"#k 으(로) 승급하기 위해선 #r"+등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][1]+"#k #fc0xFF6799FF#도네이션 포인트#k가 필요하다\r\n";
			text += "#fc0xFF6799FF#도네이션 포인트#k를 사용해서 다음 랭크로 승급할까?";
			cm.sendYesNoS(text,0x26);
		} else if (selection == 2) {
			cm.dispose();
			cm.openNpc(9000226);
		} else if (selection == 10) {
			cm.dispose();
			cm.openNpcCustom(cm.getClient(), 9000226, "Suppoting");
		}
	} else if (status == 2) {
		if (st == 1) {
		    if (cm.getPlayer().getDPoint() < 등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][1]) {
			cm.sendOkS("#fc0xFF6799FF#도네이션 포인트#k 가 부족합니다.",2);
			cm.dispose();
			return;
		    }
		    var text = "#b#e[랭크승급]#k#n\r\n\r\n";
		    text += "#fEtc/ZodiacEvent.img/0/icon/"+cm.getPlayer().getKeyValue(190823, "grade")+"/0# #r"+등급[cm.getPlayer().getKeyValue(190823, "grade")][0]+"#k";
		    text += "  →  ";
		    text += "#fEtc/ZodiacEvent.img/0/icon/"+(cm.getPlayer().getKeyValue(190823, "grade") + 1)+"/0# #b"+등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][0]+"#k";
		    cm.sendOkS(text,2);
		    cm.getPlayer().gainDPoint(-등급[(cm.getPlayer().getKeyValue(190823, "grade") + 1)][1]);
		    cm.setZodiacGrade(cm.getPlayer().getKeyValue(190823, "grade") + 1);
		    World.Broadcast.broadcastMessage(CField.getGameMessage(9, "[루비니아 랭크] 축하해주세요. "+cm.getPlayer().getName()+" 님이 [ "+등급[cm.getPlayer().getKeyValue(190823, "grade")][0]+" ] 으(로) 승급하셨습니다!"));
		    cm.dispose();
		    return;
		} else {
		    cm.dispose();
		    return;
		}
	}
}