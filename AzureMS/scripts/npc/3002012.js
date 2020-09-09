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
            cm.sendYesNo("정말 창고를 이용하시겠습니까? 창고는 130이상만 사용가능\r\n#r주의 이창고는 10억이상은 넣어서는 안돼며 창고아이템이 증발하여도 지엠은 책임지지않습니다.#k")
        } else if (status == 1){
           if (cm.getPlayer().getLevel() > 129) {
                   cm.sendStorage();
                   cm.dispose();
                   
           } else {
               cm.sendOk("창고를 이용하실려면 #b레벨 130#k 이후부터 가능합니다.")
               cm.dispose();
           }
        }
        }
}