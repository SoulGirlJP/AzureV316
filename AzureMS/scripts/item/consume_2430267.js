/*

	만든 이 : Rian Story Team

	아이템 아이디 : consume_2430267

 	아이템 이름 : 프리미엄PC방 시공석
	
	아이템 설명 : 프리미엄PC방에서만 사용할 수 있는 시공석이다. 대륙구분 없이 원하는 마을로 이동할 수 있다. 로그아웃 시 사라진다. 30분에 1번씩 사용할 수 있다.

*/

var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
         cm.askMapSelection("#0#A six-way road#1# Henesys#2# Ellinia#3# Perion#4# Kerning City#5# Lease Port#6# 슬리피우드#7# 노틸러스#8# 에레브#9# 리엔#10# 오르비스#11# 엘나스#12# 루디브리엄#15# 아쿠아리움#16# 리프레#17# 무릉#18# 백초마을#19# 아리안트#20# 마가티아#21# 에델슈타인#22# 에우렐#23# 크리티아스");
	    if (status == 0) {
		cm.warp(104020000);
		cm.dispose();

	    } else if(selection == 1) {
		cm.warp(100000000);
		cm.dispose();

	    } else if(selection == 2) {
		cm.warp(101000000);
		cm.dispose();

	    } else if(selection == 3) {
		cm.warp(102000000);
		cm.dispose();

	    } else if(selection == 4) {
		cm.warp(103000000);
		cm.dispose();

	    } else if(selection == 5) {
		cm.warp(104000000);
		cm.dispose();

	    } else if(selection == 6) {
		cm.warp(105000000);
		cm.dispose();

	    } else if(selection == 7) {
		cm.warp(120000000);
		cm.dispose();

	    } else if(selection == 8) {
		cm.warp(130000000);
		cm.dispose();

	    } else if(selection == 9) {
		cm.warp(140000000);
		cm.dispose();

	    } else if(selection == 10) {
		cm.warp(200000000);
		cm.dispose();

	    } else if(selection == 11) {
		cm.warp(211000000);
		cm.dispose();

	    } else if(selection == 12) {
		cm.warp(220000000);
		cm.dispose();

	    } else if(selection == 15) {
		cm.warp(230000000);
		cm.dispose();

	    } else if(selection == 16) {
		cm.warp(240000000);
		cm.dispose();

	    } else if(selection == 17) {
		cm.warp(250000000);
		cm.dispose();

	    } else if(selection == 18) {
		cm.warp(251000000);
		cm.dispose();

	    } else if(selection == 19) {
		cm.warp(260000000);
		cm.dispose();

	    } else if(selection == 20) {
		cm.warp(261000000);
		cm.dispose();

	    } else if(selection == 21) {
		cm.warp(310000000);
		cm.dispose();

	    } else if(selection == 22) {
		cm.warp(910150300);
		cm.dispose();

	    } else if(selection == 23) {
		cm.warp(241000000);
		cm.dispose();
	}
}
}
