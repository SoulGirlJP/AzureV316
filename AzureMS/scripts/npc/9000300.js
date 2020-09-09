var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
/* 세팅부분 시작 */
가격 = 0;
신규리스트 = [5000473, 5000290, 5000291, 5000292, 5000293, 5000294, 5000295, 5000296, 5000297, 5000298, 5000320, 5000321, 5000322, 5000330, 5000331, 5000332, 5000342, 5000343, 5000344, 5000352, 5000353, 5000354, 5000365, 5000366, 5000367, 5000385, 5000386, 5000387, 5000402, 5000403, 5000404, 5000405, 5000406, 5000407, 5000408, 5000409, 5000414, 5000415, 5000416, 5000417, 5000021]
/* 세팅부분 끝 */

    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    
    if (status == 0) {
    말 = "#fUI/UIPVP.img/DmgEffect/DmgRed/excellent#\r\n"
    말+= "#fn나눔고딕 Extrabold##fs23##e< 메이플스토리 #r최대이벤트#k!! >#fn나눔고딕 Extrabold##fs15#\r\n #d최신신규 메소 할인 판매중 남는게없는장사..#k\r\n#fn궁서체##fs20# #r최신신규들이 0원 !!#k#fn굴림##fs16#\r\n\r\n"
    for(var i=0; i<신규리스트.length; i++) {
    말+= "#L"+i+"# #i"+신규리스트[i]+"# #b[#z"+신규리스트[i]+"#]#k\r\n"
    }
    cm.sendSimple(말);
    } else if (status == 1) {
    if(cm.getPlayer().getMeso() >= 가격) {
    cm.gainMeso(-가격);
    cm.BuyPET(신규리스트[selection]);
    cm.sendOk("#i"+신규리스트[selection]+"#를 구매하셨습니다.");
    cm.dispose();
    } else {
    cm.sendOk("신규구매를 하기에는 메소가 부족합니다.");
    }
}
}