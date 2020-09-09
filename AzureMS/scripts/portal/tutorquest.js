/*

	오딘 KMS 팀 소스의 스크립트 입니다. (제작 : 엘도라도)
        수정 : OnS온 라인

	포탈이 있는 맵 : 130030001

	포탈 설명 : 시작하는곳 퀘스트 체크 (20010 ~ 20014)


*/


function enter(pi) {
    var Qid = 20010;
    var Wmid = 130030002;
    var Nname = "키무";
    if (pi.getPlayer().getMapId() == 130030002) {
        Qid = 20011;
        Wmid = 130030003;
        Nname = "키잔";
    } else if (pi.getPlayer().getMapId() == 130030003) {
        Qid = 20012;
        Wmid = 130030004;
        Nname = "키누";
    } else if (pi.getPlayer().getMapId() == 130030004) {
        Qid = 20013;
        Wmid = 130030005;
        Nname = "키아";
    }
    if (pi.getQuestStatus(Qid) == (Qid == 20010 ? 1:2)) {
        pi.warp(Wmid, 0);
        return true;
    } else {
        pi.getPlayer().dropMessage(5, Nname+"에게 퀘스트를 받고 진행해 주십시오.");
        return false;
    }
}