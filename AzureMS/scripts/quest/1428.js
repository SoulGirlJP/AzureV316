var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("캐논슈터가 어떤 직업인지는 지금까지 캐논을 다루며 익히 알고 있을 거야. 강력한 핸드캐논으로 원거리에 있는 적을 강한 한 방으로 때리는 직업이지. 한 방 한 방이 강하고 다수의 적을 공격할 수 있어 편리하면서도 화려하지. 그럼 진짜 캐논슈터의 스킬을 알려줄까?");
	} else if (status == 1) {
            qm.sendNextPrev("물론 역시 가장 중요한 건 기본기야. #b캐논 마스터리, 캐논 부스터, 크리티컬 파이어#k는 기본 중에 기본들이지. 캐논을 더 강하고 빠르고 정확하게 쏘는 것들이니까.");
        } else if (status == 2) {
            qm.sendNextPrev("하지만 진짜 재미는 공격 스킬에서 오지. 전방을 향해 여러 발의 소형 포탄을 발사하는 #b슬러그 샷#k은 다수의 적을 한 번에 공격하기도 해서 무척 유용하지.");
        } else if (status == 3) {
            qm.sendNextPrev("몸키와 함께하는 너를 위한 스킬들도 개발되어 있어. 폭탄이 가득 들어있는 오크통을 적에게 날려 밀어버리는 #b몽키 러쉬붐#k이나 몽키의 마법으로 버프를 거는 #b몽키 매직#k들은 오직 자네만을 위한 스킬이지.");
        } else if (status == 4) {
	    qm.sendNextPrev("설명이 너무 길었지? 그럼 캐논슈터가 되기 위한 시험을 시작할까? 시험 자체는 간단하다. 준비된 시험장에 들어가 준비된 몬스터를 모두 물리치고 #r그들이 떨어뜨리는 아이템#k을 가져오면 되니까. 다만 그 몬스터들은 방어력이 높으니 사냥하기 쉽지 않지. 꼭 기억해 둬라.");
        } else if (status == 5) {
            qm.askAcceptDecline("시험을 보다가 물약이 떨어지면 #b퀘스트를 포기하고 다시 시작#k하니 그런 일이 없도록 미리 충분한 물약을 준비해서들어가도록. 그럼 바로 시험을 시작하지. 수락하면 널 시험의 선실로 보내겠다.");
        } else if (status == 6) {
	    qm.warp(912040000,0);
            qm.forceStartQuest();
            qm.dispose();
        }
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendYesNo("검은 구슬을 모두 가져왔군. 나쁘지 않은 성적이야. 아니, 오히려 훌륭한 편이로군. 너라면 분명 좋은 스토너가 만족할만큼 좋은 캐논슈터가 될 거야. 그럼 바로 캐논슈터로 만들어 주지.");
        } else if (status == 1) {
	    qm.changeJob(530);
	    qm.removeAll(4031013);
            qm.forceCompleteQuest();
	    qm.gainItem(1142108,1);
	    qm.showinfoMessage("<주니어 모험가의 훈장> 칭호를 획득 하셨습니다.");
            qm.sendNext("좋아. 너는 이제부터 #b캐논슈터#k야. 캐논슈터는 핸드캐논을 이용해 멀리 있는 적을 시원하게 공격할 수 있는 자... 지금보다 강해지기 위해서는 수련에 힘써야 하지. 수련에 어려움이 있다면 나도 도와주겠어.");
        } else if (status == 2) {
            qm.sendNext("묵직한 캐논을 들고 다니려면 수납 공간이 많아야겠지? 수납의 달인 스킬을 더 올려 주었어. 전보다 확 늘어난 인벤토리를 확인해 보고 잘 사용하도록.");
        } else if (status == 3) {
            qm.sendPrev("캐논슈터는 강해져야 해. 하지만 그 강함을 약자에게 사용하는 것은 올바른 길이 아니야. 자신이 가지고 있는 힘을 옳은 일에 사용하는 것... 그것은 강해지는 것 이상으로 어려운 일이야.");
            qm.dispose();
        }
    }
}