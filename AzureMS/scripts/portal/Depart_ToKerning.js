


/*

	오딘 KMS 팀 소스의 스크립트 입니다.

	포탈이 있는 맵 : 커닝스퀘어역

	포탈 설명 : 커닝시티로 가는 지하철을 타는 포탈


*/


function enter(pi) {
    pi.timeMoveMap(103020000, 103020010, 14);
    pi.getPlayer().message(6, "다음 정차할 역은 커닝시티, 커닝시티 역입니다. 내리실 문은 오른쪽입니다.");
    return true;
}
