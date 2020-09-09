importPackage(Packages.tools.RandomStream);
importPackage(java.lang);
importPackage(Packages.launch.world);
importPackage(Packages.packet.creators);


/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY || FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * NPCID : 2010000
 * ScriptName : carlie
 * NPCNameFunc : 찰리중사
 * Location : 200000000 (스카이로드 - 오르비스)
 * 
 * @author T-Sun
 *
 */

var status = 0;
var eQuestChoices = new Array (4000073,4000059,4000060,4000061,4000058,
    4000062,4000048,4000049,4000055,4000056,
    4000051,4000052,4000050,4000057,4000053,
    4000054,4000076,4000078,4000081,4000070,
    4000071,4000072,4000069,4000079,4000080);


var requiredItem  = 0;
var lastSelection = 0;
var prizeItem     = 0;
var prizeQuantity = 0;
var itemSet;

function start() {
    status = 0;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 0 && (status == 1 || status == 3)) {
	cm.sendNext("흠. 그렇다면 어쩔 수 없군.");
	cm.dispose();
	return;
    } else if (mode == 0 && status == 3) {
	cm.dispose();
    } else if (mode == 0 && status == 4) {
	cm.sendNext("흠. 그렇다면 어쩔 수 없군.");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 1) { // first interaction with NPC
	cm.sendNext("어이 자네, 시간 좀 있는가? 음. 난 이것저것을 수집하는게 취미라네. 내가 원하는 아이템을 가져오면 자네에게 필요할만한 물건으로 바꿔주지. 다만 내가 주는 물건은 만날때 마다 바뀔 수 있다네. 어떤가? 생각 있는가?");
    //cm.dispose();
	} else if (status == 2) {
		var eQuestChoice = "좋아! 먼저 자네가 교환하고자 하는 아이템을 선택하게. 더 좋은 전리품일수록 더 좋은 아이템을 얻을 수 있다네.";
		eQuestChoice += "\r\n\r\n#b#L0# #t4000073# 100개#l\r\n#L1# #t4000059# 100개#l\r\n#L2# #t4000076# 100개#l\r\n#L3# #t4000058# 100개#l\r\n#L4# #t4000078# 100개#l\r\n#L5# #t4000060# 100개#l\r\n#L6# #t4000062# 100개#l\r\n#L7# #t4000048# 100개#l\r\n#L8# #t4000081# 100개#l\r\n#L9# #t4000061# 100개#l\r\n#L10# #t4000070# 100개#l\r\n#L11# #t4000071# 100개#l\r\n#L12# #t4000072# 100개#l\r\n#L13# #t4000051# 100개#l\r\n#L14# #t4000055# 100개#l\r\n#L15# #t4000069# 100개#l\r\n#L16# #t4000052# 100개#l\r\n#L17# #t4000050# 100개#l\r\n#L18# #t4000057# 100개#l\r\n#L19# #t4000049# 100개#l\r\n#L20# #t4000056# 100개#l\r\n#L21# #t4000079# 100개#l\r\n#L22# #t4000053# 100개#l\r\n#L23# #t4000054# 100개#l\r\n#L24# #t4000080# 100개#l\r\n";
		cm.sendSimple(eQuestChoice);
    } else if (status == 3){
		v = selection;
		if ( v == 0 ) requiredItem =  4000073 ;
			else if ( v == 1 ) requiredItem =  4000059 ;
			else if ( v == 2 ) requiredItem =  4000076 ;
			else if ( v == 3 ) requiredItem =  4000058 ;
			else if ( v == 4 ) requiredItem =  4000078 ;
			else if ( v == 5 ) requiredItem =  4000060 ;
			else if ( v == 6 ) requiredItem =  4000062 ;
			else if ( v == 7 ) requiredItem =  4000048 ;
			else if ( v == 8 ) requiredItem =  4000081 ;
			else if ( v == 9 ) requiredItem =  4000061 ;
			else if ( v == 10 ) requiredItem =  4000070 ;
			else if ( v == 11 ) requiredItem =  4000071 ;
			else if ( v == 12 ) requiredItem =  4000072 ;
			else if ( v == 13 ) requiredItem =  4000051 ;
			else if ( v == 14 ) requiredItem =  4000055 ;
			else if ( v == 15 ) requiredItem =  4000069 ;
			else if ( v == 16 ) requiredItem =  4000052 ;
			else if ( v == 17 ) requiredItem =  4000050 ;
			else if ( v == 18 ) requiredItem =  4000057 ;
			else if ( v == 19 ) requiredItem =  4000049 ;
			else if ( v == 20 ) requiredItem =  4000056 ;
			else if ( v == 21 ) requiredItem =  4000079 ;
			else if ( v == 22 ) requiredItem =  4000053;
			else if ( v == 23 ) requiredItem =  4000054 ;
			else if ( v == 24 ) requiredItem =  4000080 ;
		cm.sendYesNo("어디보자, 정말 #b#t" + requiredItem + "# 100개#k를 교환하고 싶어? 교환하기 전에 인벤토리 공간이 충분히 있는지 확인하라구. 그래서 정말 교환하고싶나?");
    }else if (status == 4){	
		itemCode = requiredItem;
		if ( itemCode == 4000073 ) {
			rnum = Randomizer.rand(1,6);
			if ( rnum == 1 ) {
				nNewItemID = 2000005;
				nNewItemNum = 30;
			}
			else if ( rnum == 2 ) {
				nNewItemID = 2010017;
				nNewItemNum = 10;
			}
			else if ( rnum == 3 ) {
				nNewItemID = 2010018;
				nNewItemNum = 15;
			}
			else if ( rnum == 4 ) {
				nNewItemID = 4310119;
				nNewItemNum = 1;
			}
			else if ( rnum == 5 ) {
				nNewItemID = 4001832;
				nNewItemNum = 50;
			}
			else {
				nNewItemID = 4001832;
				nNewItemNum = 100;
			}
		}
		// Star Pixie's Piece of Star || Flying Eye's Wings
		else if ( itemCode == 4000059 || itemCode == 4000076 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000001;
				nNewItemNum = 30;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000003;
				nNewItemNum = 20;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2010001;
				nNewItemNum = 40;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003001;
				nNewItemNum = 20;
			}
			else {
				nNewItemID = 2040002;
				nNewItemNum = 1;
			}
		}
		// Nependeath's Seed
		else if ( itemCode == 4000058 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000002;
				nNewItemNum = 15;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000003;
				nNewItemNum = 25;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2010004;
				nNewItemNum = 15;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003001;
				nNewItemNum = 30;
			}
			else {
				nNewItemID = 2040302;
				nNewItemNum = 1;
			}
		}
		// Jr. Bulldog's Tooth
		else if ( itemCode == 4000078 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 20 ) {
				nNewItemID = 2000002;
				nNewItemNum = 15;
			}
			else if ( rnum <= 40 ) {
				nNewItemID = 2000003;
				nNewItemNum = 25;
			}
			else if ( rnum <= 60 ) {
				nNewItemID = 2010004;
				nNewItemNum = 15;
			}
			else if ( rnum <= 80 ) {
				nNewItemID = 4003001;
				nNewItemNum = 30;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 2050004;
				nNewItemNum = 15;
			}
			else {
				nNewItemID = 2040302;
				nNewItemNum = 1;
			}
		}
		// Lunar Pixie's Piece of Moon
		else if ( itemCode == 4000060 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000002;
				nNewItemNum = 25;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000006;
				nNewItemNum = 10;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2022000;
				nNewItemNum = 5;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4000030;
				nNewItemNum = 15;
			}
			else {
				nNewItemID = 2040902;
				nNewItemNum = 1;
			}
		}
		// Jr. Yeti's Skin || Dark Nependeath's Seed
		else if ( itemCode == 4000048 || itemCode == 4000062 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000002;
				nNewItemNum = 30;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000006;
				nNewItemNum = 15;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2020000;
				nNewItemNum = 20;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003000;
				nNewItemNum = 5;
			}
			else {
				nNewItemID = 2040402;
				nNewItemNum = 1;
			}
		}
		// Fireball's Flame
		else if ( itemCode == 4000081 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 15 ) {
				nNewItemID = 2000006;
				nNewItemNum = 25;
			}
			else if ( rnum <= 30 ) {
				nNewItemID = 2020006;
				nNewItemNum = 25;
			}
			else if ( rnum <= 45 ) {
				nNewItemID = 4010004;
				nNewItemNum = 8;
			}
			else if ( rnum <= 60 ) {
				nNewItemID = 4010005;
				nNewItemNum = 8;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 4010006;
				nNewItemNum = 3;
			}
			else if ( rnum <= 90 ) {
				nNewItemID = 4020008;
				nNewItemNum = 3;
			}
			else if ( rnum <= 95 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else {
				nNewItemID = 2040705;
				nNewItemNum = 1;
			}
		}
		// Luster Pixie's Piece of Sun
		else if ( itemCode == 4000061 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000002;
				nNewItemNum = 30;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000006;
				nNewItemNum = 15;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2020000;
				nNewItemNum = 20;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003000;
				nNewItemNum = 5;
			}
			else {
				nNewItemID = 2041016;
				nNewItemNum = 1;
			}
		}
		// Cellion's Tail || Lioner's Tail || Grupin's Tail
		else if ( itemCode == 4000070 || itemCode == 4000071 || itemCode == 4000072 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2000002;
				nNewItemNum = 30;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000006;
				nNewItemNum = 15;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2020000;
				nNewItemNum = 20;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003000;
				nNewItemNum = 5;
			}
			else {
				nNewItemID = 2041005;
				nNewItemNum = 1;
			}
		}
		// Hector's Tail 
		else if ( itemCode == 4000051 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 35 ) {
				nNewItemID = 2002004;
				nNewItemNum = 15;
			}
			else if ( rnum <= 70 ) {
				nNewItemID = 2002005;
				nNewItemNum = 15;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 2002003;
				nNewItemNum = 10;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4001005;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2040502;
				nNewItemNum = 1;
			}
		}
		// Dark Jr. Yeti's Skin
		else if ( itemCode == 4000055 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 35 ) {
				nNewItemID = 2022001;
				nNewItemNum = 30;
			}
			else if ( rnum <= 70 ) {
				nNewItemID = 2020006;
				nNewItemNum = 15;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 2020005;
				nNewItemNum = 30;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003003;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2040505;
				nNewItemNum = 1;
			}
		}
		// Zombie's Lost Tooth
		else if ( itemCode == 4000069 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 25 ) {
				nNewItemID = 2050004;
				nNewItemNum = 20;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 2000006;
				nNewItemNum = 20;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 2020006;
				nNewItemNum = 15;
			}
			else if ( rnum <= 98 ) {
				nNewItemID = 2020005;
				nNewItemNum = 30;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003003;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2041002;
				nNewItemNum = 1;
			}
		}
		// White Pang's Tail
		else if ( itemCode == 4000052 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 40 ) {
				nNewItemID = 2000006;
				nNewItemNum = 20;
			}
			else if ( rnum <= 60 ) {
				nNewItemID = 4010003;
				nNewItemNum = 7;
			}
			else if ( rnum <= 80 ) {
				nNewItemID = 4010004;
				nNewItemNum = 7;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 4010005;
				nNewItemNum = 7;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4003002;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2040602;
				nNewItemNum = 1;
			}
		}
		// Pepe's Beek
		else if ( itemCode == 4000050 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 30 ) {
				nNewItemID = 2000006;
				nNewItemNum = 20;
			}
			else if ( rnum <= 45 ) {
				nNewItemID = 4010000;
				nNewItemNum = 7;
			}
			else if ( rnum <= 60 ) {
				nNewItemID = 4010001;
				nNewItemNum = 7;
			}
			else if ( rnum <= 79 ) {
				nNewItemID = 4010002;
				nNewItemNum = 7;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4010006;
				nNewItemNum = 2;
			}
			else {
				nNewItemID = 2040702;
				nNewItemNum = 1;
			}
		}
		// Dark Pepe's Beek
		else if ( itemCode == 4000057 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 30 ) {
				nNewItemID = 2000006;
				nNewItemNum = 20;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 4010004;
				nNewItemNum = 7;
			}
			else if ( rnum <= 62 ) {
				nNewItemID = 4010005;
				nNewItemNum = 7;
			}
			else if ( rnum <= 74 ) {
				nNewItemID = 4010006;
				nNewItemNum = 3;
			}
			else if ( rnum <= 86 ) {
				nNewItemID = 4020008;
				nNewItemNum = 2;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else {
				nNewItemID = 2040705;
				nNewItemNum = 1;
			}
		}
		// Yeti's Horn
		else if ( itemCode == 4000049 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 30 ) {
				nNewItemID = 2000006;
				nNewItemNum = 25;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 4020000;
				nNewItemNum = 7;
			}
			else if ( rnum <= 65 ) {
				nNewItemID = 4020001;
				nNewItemNum = 7;
			}
			else if ( rnum <= 85 ) {
				nNewItemID = 4020002;
				nNewItemNum = 3;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else {
				nNewItemID = 2040708;
				nNewItemNum = 1;
			}
		}
		// Dark Yeti's Horn
		else if ( itemCode == 4000056 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 30 ) {
				nNewItemID = 2000006;
				nNewItemNum = 25;
			}
			else if ( rnum <= 50 ) {
				nNewItemID = 4020003;
				nNewItemNum = 7;
			}
			else if ( rnum <= 65 ) {
				nNewItemID = 4020004;
				nNewItemNum = 7;
			}
			else if ( rnum <= 85 ) {
				nNewItemID = 4020005;
				nNewItemNum = 7;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 4020008;
				nNewItemNum = 2;
			}
			else {
				nNewItemID = 2040802;
				nNewItemNum = 1;
			}
		}
		// Bulldog's Tooth
		else if ( itemCode == 4000079 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 15 ) {
				nNewItemID = 2000006;
				nNewItemNum = 25;
			}
			else if ( rnum <= 30 ) {
				nNewItemID = 2022001;
				nNewItemNum = 35;
			}
			else if ( rnum <= 45 ) {
				nNewItemID = 4020000;
				nNewItemNum = 8;
			}
			else if ( rnum <= 60 ) {
				nNewItemID = 4020001;
				nNewItemNum = 8;
			}
			else if ( rnum <= 75 ) {
				nNewItemID = 4020002;
				nNewItemNum = 8;
			}
			else if ( rnum <= 90 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 2050004;
				nNewItemNum = 30;
			}
			else {
				nNewItemID = 2041023;
				nNewItemNum = 1;
			}
		}
		// Werewolf's Toenail
		else if ( itemCode == 4000053 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 37 ) {
				nNewItemID = 2000006;
				nNewItemNum = 30;
			}
			else if ( rnum <= 57 ) {
				nNewItemID = 4020006;
				nNewItemNum = 7;
			}
			else if ( rnum <= 77 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 4020008;
				nNewItemNum = 2;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 2070010;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2040805;
				nNewItemNum = 1;
			}
		}
		// Lycanthrope's Toenail
		else if ( itemCode == 4000054 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 37 ) {
				nNewItemID = 2000006;
				nNewItemNum = 30;
			}
			else if ( rnum <= 57 ) {
				nNewItemID = 4020006;
				nNewItemNum = 7;
			}
			else if ( rnum <= 77 ) {
				nNewItemID = 4020007;
				nNewItemNum = 2;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 4020008;
				nNewItemNum = 2;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 2070010;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2041020;
				nNewItemNum = 1;
			}
		}
		// Firedawg's necklace
		else if ( itemCode == 4000080 ) {
			rnum = Randomizer.rand( 1, 100 );
			if ( rnum <= 37 ) {
				nNewItemID = 2000006;
				nNewItemNum = 35;
			}
			else if ( rnum <= 57 ) {
				nNewItemID = 4020006;
				nNewItemNum = 9;
			}
			else if ( rnum <= 77 ) {
				nNewItemID = 4020007;
				nNewItemNum = 4;
			}
			else if ( rnum <= 97 ) {
				nNewItemID = 4020008;
				nNewItemNum = 4;
			}
			else if ( rnum <= 99 ) {
				nNewItemID = 2070011;
				nNewItemNum = 1;
			}
			else {
				nNewItemID = 2041008;
				nNewItemNum = 1;
			}
		}
		else {
			nNewItemID = 0;
			nNewItemNum = 0;
		}
	if(!cm.haveItem(requiredItem,100)){
	    cm.sendOk("자네..아이템은 분명 제대로 갖고 있는건가?");
	} else if(!cm.canHold(nNewItemID)){
	    cm.sendNext("자네.. 인벤토리 공간이 부족하지는 않나?");
	} else {
	    cm.gainItem(requiredItem,-100);
	    cm.gainExp(500);
	    cm.gainItem(nNewItemID, nNewItemNum);
	    cm.sendOk("자, 교환이 다 되었다네. 어떤가? 마음에 드는가? 다음번에 올땐 더 좋은 아이템이 있을 수 있으니 기대하게나.");
	cm.getPlayer().send(MainPacketCreator.getGMText(6, "아이템을 획득하였습니다. ("+Packages.server.items.ItemInformation.getInstance().getName(nNewItemID)+" "+nNewItemNum+"개)"));

	}
	cm.dispose();
    }
}
