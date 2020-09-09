/*

Copyright ⓒ 2013 Spirit Corporaion. All Rights Reserved.

leader.스피릿온라인 (terams_@nate.com)
member.UnknownStar (rhduddlr6996@nate.com)
member.우비 (guri__s@nate.com)
member.공석 (iureal@nate.com)
member.블라인드 ()


이 스크립트는 스피릿온라인에만 사용됩니다.
만약 유출이 되더라도 이 주석은 삭제하지 않으셨으면 좋겠습니다.

*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
	if (status == 0) {
		var chat = "안녕하세요!! 저는 #b스피릿팀#k에서 팀장직을 담당하고있는 \r\n#b스피릿매니저#k라고 합니다! ";
		chat += "저가 이렇게 #b피릿시스#k에 있는 이유는!! 바로바로 #d에픽아이템#k을 판매하기 위해서 왓답니다~\r\n";
		chat += "#d에픽아이템#k이 뭐냐구요? #b스피릿스타일#k에서만 볼수있는 엄청 #e강력한 아이템#n을 말하는 거에요 ";
		chat += "하지만 #d에픽아이템#k은 손쉽게 획득 할수는 없죠!! 저에게 #b(10억)1,000,000,000#k메소를 주시면";
	        chat += " #d에픽아이템#k을 획득할수있는 룰렛을 돌려 드릴게요!";
		chat += "\r\n\r\n#e[에픽 반지]#n\r\n\r\n";
		chat += "#i1112315##b#t1112315#";
		chat += "\r\n#i1112316##t1112316#";
		chat += "\r\n#i1112317##t1112317#";
		chat += "\r\n#i1112318##t1112318#";
		chat += "\r\n#i1112319##t1112319#";
		chat += "\r\n#i1112320##t1112320#";
		chat += "\r\n\r\n#k#e[에픽 펜던트]#n\r\n\r\n";
		chat += "#i1122088##b#t1122088#";
		chat += "\r\n#i1122089##t1122089#";
		chat += "\r\n#i1122090##t1122090#";
		chat += "\r\n#i1122091##t1122091#";
		chat += "\r\n#i1122092##t1122092#";
		chat += "\r\n#i1122093##t1122093#";
		chat += "\r\n\r\n#k#e[롤링 롤링 룰렛]#n#b";
		chat += "\r\n#L0#10억 메소로 룰렛을 돌리겠습니다.#l";
		cm.sendSimple(chat);
        } else if (status == 1) {
		if (cm.getMeso() >= 1000000000) {
			cm.sendOk("자~ 두근두근 #d에픽아이템#k을 위해 룰렛을 돌립니다!!");
	   	} else {
			cm.sendOk("룰렛을 돌리기에는 메소가 부족한거 같아요~");
			cm.dispose();
		}
	} else if (status == 2) {
		cm.gainMeso(-1000000000);
		cm.getPlayer().에픽아이템();
		cm.dispose();
	} else {
		cm.dispose();
	}
    }
}