/*
	본 스크립트는 선히팩의 파일입니다.
	선히팩은 KMS 기준으로 만들어졌습니다.

	네이버 : 선우(seonwoo__@naver.com)
*/

var status = 0;

function start() {
	status = -1;
	action(1,0,0);
}


function action(mode , type , selection){
	if (mode == -1) {
		cm.dispose();
	} else {
	if (mode == 0 && (status == 0)) {
		cm.sendOk("안녕히 가세요.");
		cm.dispose();
	} 
	if (mode > 0)
	    status++;
	else
	    status--;
	if (status == 0) {
            cm.sendYesNo("정말 창고를 이용하시겠습니까?")
        } else if (status == 1){
    if (cm.getPlayer().getLevel() >= 120) {
                   cm.sendStorage();
                   cm.dispose();
                   
           } else {
               cm.sendOk("창고를 이용하실려면 120 레벨이상이 되셔야합니다.")
               cm.dispose();
           }
        }
        }
}