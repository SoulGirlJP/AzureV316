var status = -1;

/** Open the monster collection UI. **/
function start(mode, type, selection) {
    //qm.OpenUI(1105);
    //   qm.sendOk("dd");
    //qm.dispose();
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("안녕하세요. #h #님. 새롭게 추가된 #b몬스터 컬렉션#k에 대해 알고 계신가요? 간단히 소개해드리고, 도움이 될 만한 선물을 드리겠습니다.");
        } else if (status == 1) {
            qm.sendNext("몬스터 컬렉션은 #b메이플 월드에 존재하는 몬스터들을 수집할 수 있는 컬렉션 북#k입니다. 일반 맵에서 나타나는 몬스터부터 엘리트 몬스터, 파티퀘스트 몬스터, 보스 몬스터 등 다양한 몬스터가 등록되어 있습니다.");
        } else if (status == 2) {
            qm.sendNext("몬스터 컬렉션은 #b단축키#k를 설정하면 컬렉션북을 확인할 수 있어요.\r\n\r\n내가 모은 몬스터를 확인할 수도 있고, 컬렉션을 완성할 때마다 다양한 아이템을 획득할 수 있습니다.\r\n\r\n그리고, 완성된 컬렉션의 몬스터는 탐험에서 돌아올 때 멋진 선물을 가지고 돌아옵니다. 탐험 시간이 길어질수록 더 좋은 보상을 얻을 수 있다는 것을 기억해주세요.");
            qm.forceStartQuest(7268);
        } 
    }
}
