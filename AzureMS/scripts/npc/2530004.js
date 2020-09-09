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
        cm.sendNext("#fn나눔고딕 Extrabold#\r\n#r검은 마녀 ::#k 어머.. 이게 누구야~!.. 가소로운 것!~\r\n너도 보기보다 참 미련하군.. 여기까지 쫓아오다니..!!\r\n오면서.. 내 훌륭한 조각품은 잘 봤겠지!~\r\n실망하진마!.. 너도 곧 그렇게 될거니까!...\r\n");
    } else if (status == 1) {
	cm.sendNextPrevS("#fn나눔고딕 Extrabold#\r\n#b#h?# ::#k 오냐.. 너 잘 만났다..\r\n내가 너때매 고생한 걸 생각하면.. #d(부들부들..)#k\r\n말 나온김에 그 가소로운 맛 이 먼지 보면되겠네~^^",2);
    } else if (status == 2) {
	cm.sendNextPrev("#fn나눔고딕 Extrabold#\r\n#r검은 마녀 ::#k 후훗.. 월래 무식하면 용감한 법이지...!~\r\n곧 철저히 후회하게 만들어주겠어!~ 가소로운 것!!");
    } else if (status == 3) {
	cm.sendNextPrevS("#fn나눔고딕 Extrabold##b#h?# ::#k 뭐.. 무.. 무식..?\r\n그래.. 무식한게 먼지 제대로 보여줄게~^^\r\n\r\n#fs15##r덤벼!!.. 이야아!!#k#fs12#\r\n\r\n#d(다음을 누르면 검은마녀와 전투를 시작합니다.)#k",2);
    } else if (status == 4) {
	if (cm.getPlayerCount(304070100) > 0) {
        cm.sendOk("#fn나눔고딕 Extrabold##r흠.. 이미 누군가 나에게 도전하고 있군..\r\n넌.. 그 다음에 상대해주지..#k");
        cm.dispose();
        } else {
        cm.warp(304070100,0);
	cm.killAllMob();
	cm.spawnMob(9001010, 0, -100);
	cm.showEffect(false,"grandBattle/attack");
	cm.showEffect(false,"adventureStory/screenMsg/1");
        cm.playSound(false,"Field.img/flowervioleta/thunder");
        cm.dispose();
        }
    }
}