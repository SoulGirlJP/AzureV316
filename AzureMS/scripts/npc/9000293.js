var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
	if (cm.getPlayer().getLevel() >= 160 && cm.getPlayer().getMapId() == 100030301) {
        var jump = "헛~둘!헛~둘! 응? 뭐야!? 너도 나처럼 점프를 좋아하는구나!?\r\n좋아! 그렇다면 너가 나를 도와주면 좋은 아이템을 줄게!#k\r\n어디보자.. #b#h ##k, 너가 나를 도와줄 수 있는 목록은…\r\n\r\n";
        jump += "#fUI/UIWindow2.img/UtilDlgEx/list1#\r\n";
        if (cm.getQuestStatus(500) == 1 && cm.getQuestStatus(501) == 1 && cm.getQuestStatus(502) == 1 && cm.getQuestStatus(503) == 1) {
        jump += "#L100##r#i4001308# 슈미의 잃어버린 동전 (퀘스트 완료)\r\n";
        jump += "#L200##i4001308# 존의 진심이 담겨있는 꽃 (퀘스트 완료)\r\n";
        jump += "#L300##i4001308# 크리슈라마의 만드라고라 (퀘스트 완료)\r\n";
        jump += "#L400##i4001308# 찰리중사의 폐광 탐사 작전 (퀘스트 완료)#k\r\n\r\n";
        jump += "#L5##b#h ##k : #r전부 도와주고 왔으니 이제 보상을 주세요.#k";
        } else if (cm.getQuestStatus(500) == 1 && cm.getQuestStatus(501) == 1 && cm.getQuestStatus(502) == 1) {
        jump += "#L100##r#i4001308# 슈미의 잃어버린 동전 (퀘스트 완료)\r\n";
        jump += "#L200##i4001308# 존의 진심이 담겨있는 꽃 (퀘스트 완료)\r\n";
        jump += "#L300##i4001308# 크리슈라마의 만드라고라 (퀘스트 완료)#k\r\n";
        jump += "#L4##b → 찰리중사의 폐광 탐사 작전 (시작가능)#k\r\n";
        } else if (cm.getQuestStatus(500) == 1 && cm.getQuestStatus(501) == 1) {
        jump += "#L100##r#i4001308# 슈미의 잃어버린 동전 (퀘스트 완료)\r\n";
        jump += "#L200##i4001308# 존의 진심이 담겨있는 꽃 (퀘스트 완료)#k\r\n";
        jump += "#L3##b → 크리슈라마의 만드라고라 (시작가능)#k\r\n";
        jump += "#L40##r찰리중사의 폐광 탐사 작전 (시작불가)#k\r\n";
        } else if (cm.getQuestStatus(500) == 1) {
        jump += "#L100##r#i4001308# 슈미의 잃어버린 동전 (퀘스트 완료)#k\r\n";
        jump += "#L2#존의 진심이 담겨있는 꽃 (시작가능)\r\n";
        jump += "#L30##r크리슈라마의 만드라고라 (시작불가)\r\n";
        jump += "#L40#찰리중사의 폐광 탐사 작전 (시작불가)#k\r\n";
        } else {
        jump += "#L1##b슈미의 잃어버린 동전 (시작가능)#k\r\n";
        jump += "#L20##r존의 진심이 담겨있는 꽃 (시작불가)\r\n";
        jump += "#L30#크리슈라마의 만드라고라 (시작불가)\r\n";
        jump += "#L40#찰리중사의 폐광 탐사 작전 (시작불가)#k\r\n";
        }
	} else {
	cm.sendOk("#fn나눔고딕 Extrabold##r* 플레이 조건#k\r\n\r\n#d- 레벨 160 이상 의 캐릭터\r\n- 퀘스트의 전당 에서 플레이 가능#k",9062004);
	cm.dispose();
        }
	cm.sendFriendsSimple(jump,true);
    } if (selection == 1) {
        cm.dispose();
        cm.openNpc(1052102);
    } else if (selection == 2) {
        cm.dispose();
        cm.openNpc(20000);
    } else if (selection == 3) {
        cm.dispose();
        cm.openNpc(1061000);
    } else if (selection == 4) {
        cm.dispose();
        cm.openNpc(2010000);
    } else if (selection == 5) {
        if (cm.getQuestStatus(505) == 0) {
	if (cm.canHold(1112750) && cm.canHold(4310129)) {
        cm.setAllStat(1112750,400,100,0);
        cm.gainItem(4310129, 30);
        cm.forfeitQuest(500);
        cm.forfeitQuest(501);
        cm.forfeitQuest(502);
        cm.forfeitQuest(503);
        cm.forfeitQuest(504);
	cm.forceStartQuest(505);
        cm.sendOk("#fn나눔고딕 Extrabold#오우~! 너도 한 #b점프#k 하는데!?\r\n좋아 수고했어 이건 내가주는 보상이야!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i1112750# #b#z1112750##k #r[올스탯 400 / 공, 마 100]#k\r\n#i4310129# #b썸머리밋 코인#k #r30 개#k");
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
        cm.dispose();
               } else {
               cm.sendOk("#fn나눔고딕 Extrabold##r장비 또는 기타 창을 한 칸 이상 비워주세요..#k");
               cm.dispose();
               }
      } else {
	if (cm.canHold(4310129)) {
        cm.gainItem(4310129, 10);
        cm.forfeitQuest(500);
        cm.forfeitQuest(501);
        cm.forfeitQuest(502);
        cm.forfeitQuest(503);
        cm.forfeitQuest(504);
	cm.sendOk("#fn나눔고딕 Extrabold#오우~! 너도 한 #d점프#k 하는데!?\r\n좋아 수고했어 이건 내가주는 보상이야!\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#i4310129# #b썸머리밋 코인#k #r10 개#k");
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
        cm.dispose();
               } else {
               cm.sendOk("#fn나눔고딕 Extrabold##r기타 창을 한 칸 이상 비워주세요..#k");
               cm.dispose();
               }
     }
    } else if (selection == 20) {
        cm.sendOk("#fn나눔고딕 Extrabold##b#h ##k 님 은 #d슈미의 잃어버린 동전#k 을 완료하지 못하여\r\n#d존의 진심이 담겨있는 꽃#k 퀘스트 진행이 #r불가능#k 합니다.",9062004);
        cm.dispose();
    } else if (selection == 30) {
        cm.sendOk("#fn나눔고딕 Extrabold##b#h ##k님은 #d존의 진심이 담겨있는 꽃#k 을 완료하지 못하여\r\n#d크리슈라마의 만드라고라#k 퀘스트 진행이 #r불가능#k 합니다.",9062004);
        cm.dispose();
    } else if (selection == 40) {
        cm.sendOk("#fn나눔고딕 Extrabold##b#h ##k님은 #d크리슈라마의 만드라고라#k 를 완료하지 못하여\r\n#d찰리중사의 폐광 탐사 작전#k 퀘스트 진행이 #r불가능#k 합니다.",9062004);
        cm.dispose();
    } else if (selection == 100) {
        cm.sendOk("#fn나눔고딕 Extrabold#어!? #b#h ##k !? 저번에는 정말 고마웠어!\r\n다음에 도움이 필요하면 또 부탁할게~>_<",1052102);
        cm.dispose();
    } else if (selection == 200) {
        cm.sendOk("#fn나눔고딕 Extrabold#나를 위해 #b꽃#k 을 가져다 주었던 젊은이로군..\r\n자네의 도움은 잊지 않고 있지..\r\n부디 이 곳에서 편히 쉬다 가도록 하게나..",20000);
        cm.dispose();
    } else if (selection == 300) {
        cm.sendOk("#fn나눔고딕 Extrabold#그때.. #b만드라고라#k 의 여부를 확인해 주셔서 감사드립니다.",1061000);
        cm.dispose();
    } else if (selection == 400) {
        cm.sendOk("#fn나눔고딕 Extrabold#나.. 대신.. #b이지병장#k 을 찾아줘서 고맙군..!",2010000);
        cm.dispose();
    }  
}
