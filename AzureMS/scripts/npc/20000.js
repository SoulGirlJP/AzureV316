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
    if (cm.getPlayer().getMapId() == 100030301) {
        var john = "#fn나눔고딕 Extrabold#소피아.. 요즘 그녀가 너무나도 좋은걸 흐음..\r\n어떻게 하면 그녀가 내 마음을 알아줄까?\r\n\r\n";
    if (!cm.haveItem(4033970,1)) {
        john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list1#\r\n#fn굴림##L1##d존의 진심이 담겨있는 꽃";
    } else {
        john += "#fs11##fUI/UIWindow2.img/UtilDlgEx/list3#\r\n#fn굴림##L2##d존의 진심이 담겨있는 꽃";
    }
        cm.sendSimple(john);
    } else {
        cm.dispose();
    }
    } else if (status == 1) {
            if (selection == 2 ) {
	cm.sendOk("#fn나눔고딕 Extrabold#정말 고맙네 젊은이! 이것은 내 작은 보답이야..\r\n사양말고 받도록 하게!..\r\n\\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n\r\n#i4310129# #b썸머리밋 코인#k #r1 개#k");
	cm.gainItem(4033970,-1);
	cm.gainItem(4310129, 1);
	cm.forceStartQuest(501);
	cm.showEffect(false,"monsterPark/clear");
        cm.playSound(false,"Field.img/Party1/Clear");
	cm.dispose();
        } else {
        cm.sendNextS("#fn나눔고딕 Extrabold##b슈미를 도와주다가 떨어져 죽는줄 알았어 으으..\r\n다음은... #fs14#존#fs12# 이라는 사람이였지..?",2);
        }
    } else if (status == 2) {
        cm.sendNextPrevS("#fn나눔고딕 Extrabold##b안녕하세요! #fs14#존#fs12# 님 맞으신가요??\r\n저는 #fs14##h ##fs12# (이)라고 합니다.#k",2);
    } else if (status == 3) {
        cm.sendNextPrev("#fn나눔고딕 Extrabold#응? 못보던 젊은이로군…그래, 무슨일로 날 찾아왔지?..");
    } else if (status == 4) {
        cm.sendNextS("#fn나눔고딕 Extrabold##b아! 저는 #fs14#존#fs12# 님을 … \r\n\r\n#fs13##L1#진심으로 돕고 싶어 소문듣고 찾아왔습니다.\r\n#L2#도와드리라고 의뢰를 받아서 왔습니다.",2);
    } else if (status == 5) {
            if (selection == 2) {
	cm.sendYesNo("#fn나눔고딕 Extrabold#아, 맞아! 저번에 한번 도와달라고 의뢰 했었던 적이 있엇던가..?\r\n어쨋든 반가워!.. 그렇다면 내 이야기를 들을 준비는 되었어?");
	} else {
        	cm.sendYesNo("#fn나눔고딕 Extrabold#응? 내 소문이 언제 그렇게 퍼졌지...?\r\n자세한 얘기는 모를테니 내 이야기를 들어 보겠어?");
    }
    } else if (status == 6) { 
       cm.sendNext("#fn나눔고딕 Extrabold#부끄럽지만, 나는 이 나이에 좋아하는 여인이 한명 있어!\r\n그녀의 이름은 소피아..야, 이름만 들어도 아름답지?\r\n하지만 그녀에게 어떻게 고백 해야할지 고민 하던 중…");
    } else if (status == 7) { 
       cm.sendYesNo("#fn나눔고딕 Extrabold#문득 좋은 방법이 떠올랐지! 바로 인내의 숲 꼭대기에 있는\r\n#i4033970# #b#z4033970##k 을 주는거야! 이름 마저도 예쁘군..\r\n하지만 내 힘으로는 도저히.. 구하기 쉽지가 않아..\r\n만약 대신 구해다 준다면 너에게 도움이 되는 걸 주도록 할게!\r\n\r\n#fUI/UIWindow2.img/QuestIcon/3/0#\r\n#i4310129# #b썸머리밋 코인#k #r1 개#k\r\n\r\n#d그럼 지금 바로 인내의 숲으로 갈래..??#k");
    } else if (status == 8) { 
        cm.warp(910130101,0);
        cm.sendOk("#fn나눔고딕 Extrabold##b꽃#k 을 구하면 다시 나에게 와줘! 그럼 부탁해 #b#h ##k !!");
      }
    }
