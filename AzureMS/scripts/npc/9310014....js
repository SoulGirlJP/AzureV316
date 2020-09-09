

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
		var sungwoo = "#k저는 서버 이용에 편리한 아이템들을 판매하고있는 할매요.\r\n\r\n#l";
		sungwoo += "#r#e     　　 　　[ 큐브 관련 아이템 ]#k#n\r\n";
		sungwoo += "　#b#L10##r1000만#k#b 메소로 #i2460003##k(#t2460003#)#b 10개 구매#k\r\n#l";
		sungwoo += "　#b#L0##r30만#k#b 메소로 #i5062000##k(#t5062000#)#b 30개 구매#k\r\n#l";
		sungwoo += "　#b#L1##r70만#k#b 메소로 #i5062002##k(#t5062000#)#b 30개 구매#k\r\n#l";
		sungwoo += "　#b#L2##r100만#k#b 메소로 #i5062005##k(#t5062005#)#b 30개 구매#k\r\n#l";
		sungwoo += "　#b#L3##r200만#k#b 메소로 #i5062006##k(#t5062006#)#b 30개 구매#k\r\n#l";
		sungwoo += "　#b#L4##r3000만#k#b 메소로 #i5062009##k(#t5062009#)#b 50개 구매#k\r\n#l";
		sungwoo += "  #b#L13##r3000만#k#b 메소로 #i5062010##k(#t5062010#)#b 50개 구매#k\r\n#l\r\n";
		sungwoo += "  #b#L14##r3000만#k#b 메소로 #i5062500##k(#t5062500#)#b 50개 구매#k\r\n#l\r\n";
		sungwoo += "#r#e      　　　　[ 주문서 관련 아이템 ]#k#n\r\n";
		sungwoo += "　#b#L5##r30만#k#b 메소로 #i2049408##k(#t2049408#)#b 1개 구매#k\r\n#l";
		sungwoo += "　#b#L6##r10만#k#b 메소로 #i2049301##k(#t2049301#)#b 1개 구매#k\r\n#l";
		sungwoo += "　#b#L7##r50만#k#b 메소로 #i2049303##k(#t2049303#)#b 1개 구매#k\r\n#l";
		sungwoo += "　#b#L8##r500만#k#b 메소로 #i2049152##k(#t2049152#)#b 1개 #k\r\n#l";
		sungwoo += "　#b#L11##r2500만#k#b 메소로 #i2049152##k(#t2049152#)#b 5개 #k\r\n#l";
		sungwoo += "　#b#L9##r700만#k#b 메소로 #i2049360###k(#t2049360#)#b 1개 #k\r\n#l";
		sungwoo += "　#b#L12##r3500만#k#b 메소로 #i2049360##k(#t2049360#)#b 5개 #k\r\n#l";
		cm.sendSimple(sungwoo);

	} else if (status == 1) {
             if (selection == 0) {
               if(cm.getMeso() >= 300000){
               cm.gainItem(5062000,30);
               cm.gainMeso(-300000);
               cm.sendOk("미라클 큐브 30개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               
               }
} else if (selection == 13) {
               if(cm.getMeso() >= 30000000){
               cm.gainItem(5062010,50);
               cm.gainMeso(-30000000);
               cm.sendOk("블랙 큐브 50개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 14) {
               if(cm.getMeso() >= 30000000){
               cm.gainItem(5062500,50);
               cm.gainMeso(-30000000);
               cm.sendOk("에디셔널 큐브 50개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 12) {
               if(cm.getMeso() >= 35000000){
               cm.gainItem(2049360,5);
               cm.gainMeso(-35000000);
               cm.sendOk("완료.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 11) {
               if(cm.getMeso() >= 25000000){
               cm.gainItem(2049152,5);
               cm.gainMeso(-25000000);
               cm.sendOk("혼줌");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 1) {
               if(cm.getMeso() >= 700000){
               cm.gainItem(5062002,30);
               cm.gainMeso(-700000);
               cm.sendOk("마스터 미라클 큐브 30개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 8) {
               if(cm.getMeso() >= 5000000){
               cm.gainItem(2049152,1);
               cm.gainMeso(-5000000);
               cm.sendOk("혼돈의 주문서를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               }
} else if (selection == 9) {
               if(cm.getMeso() >= 7000000){
               cm.gainItem(2049360,1);
               cm.gainMeso(-7000000);
               cm.sendOk("놀라운 장비강화 주문서를 구매했습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
}
} else if (selection == 10) {
               if(cm.getMeso() >= 10000000){
               cm.gainItem(2460003,10);
               cm.gainMeso(-10000000);
               cm.sendOk("돋보기 10개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 2) {
               if(cm.getMeso() >= 1000000){
               cm.gainItem(5062005,30);
               cm.gainMeso(-1000000);
               cm.sendOk("어메이징 미라클 큐브 30개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 3) {
               if(cm.getMeso() >= 2000000){
               cm.gainItem(5062006,30);
               cm.gainMeso(-2000000);
               cm.sendOk("플레티넘 미라클 큐브 30개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 4) {
               if(cm.getMeso() >= 30000000){
               cm.gainItem(5062009,50);
               cm.gainMeso(-30000000);
               cm.sendOk("레드 큐브 50개를 구매하셔습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 5) {
               if(cm.getMeso() >= 300000){
               cm.gainItem(2049408,1);
               cm.gainMeso(-300000);
               cm.sendOk("잠재능력 부여주문서 1장을 구매하셨습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 6) {
               if(cm.getMeso() >= 100000){
               cm.gainItem(2049301,1);
               cm.gainMeso(-100000); 
              cm.sendOk("장비강화 주문서 1장을 구매하셨습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
} else if (selection == 7) {
               if(cm.getMeso() >= 500000){
               cm.gainItem(2049303,1);
               cm.gainMeso(-500000);
               cm.sendOk("고급 장비강화 주문서 1장을 구매하셨습니다.");
               cm.dispose();
               } else {
               cm.sendOk("물건을 구매하기엔, 메소가 부족한거같은데?");
               cm.dispose();
               } 
}
}
}
}

