var status = -1;
    

// 건들이시면 안됩니다.
lineUseArray = [];
Howmany = 5; //

coincode = 4310198;
coinqty = 30;

앙 = null;


function start() {
    status = -1;
    action (1, 0, null);
}

function action(mode, type, selection) {
    ItemArray = [1012029, 1012038, 1012041, 1012042, 1012043, 1012049, 1012051, 1012057, 1012081, 1012090, 1012099, 1012100, 1012112, 1012113, 1012114, 1012121, 1012122, 1012123, 1012124, 1012125, 1012126, 1012127, 1012128, 1012129, 1012131, 1012133, 1012134, 1012147, 1012159, 1012166, 1012179, 1012180, 1012208, 1012253, 1012275, 1012298, 1012315, 1012390, 1012413, 1012462, 1012474, 1012486, 1012487, 1012488, 1012509, 1012510, 1012511, 1012534, 1012552, 1012572, 1012603, 1012608, 1012609, 1012619, 1012623, 1022046, 1022047, 1022048, 1022057, 1022065, 1022066, 1022081, 1022084, 1022085, 1022087, 1022090, 1022095, 1022104, 1022121, 1022122, 1022173, 1022194, 1022201, 1022227, 1022249, 1022250, 1022257, 1022258, 1022266, 1022269, 1022270, 1022275, 1032071, 1032072, 1032073, 1032074, 1032138, 1032145, 1032175, 1032204, 1032233, 1032234, 1032255, 1032260, 1032262, 1122121, 1122210, 1122268, 1122303, 1132183, 1152101]
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
      if (status == 0) {
        앙 = selection;
        if (앙 != null) {
           for (i=0; i<ItemArray.length; i++) {
            말+= "#L"+i+"# #i"+ItemArray[i]+"#"
            if (lineUseArray.some(function(ok,index,lineUseArray) {
                 return ok == 앙;
                  }) == false) { 
           lineUseArray.push(앙);
            }
          }
        }
      }
    }    
    if (status == 0) {
       if (lineUseArray.length != Howmany) {
        말 = "#fn나눔고딕 Extrabold# 골라요! 골라! #b떡국코인30개#k로 자신이 #b원하는 아이템#k만 넣어 그 중 #b한가지#k를 얻을 수 있는 절호의 기회! 이 #b멋진 기회#k를 놓치진 않겠죠?\r\n\r\n"
        말+= "#r선택한 아이템 개수#k : "+lineUseArray.length+"\r\n"
        말+= "#r남은 선택 회수#k : "+(Howmany-lineUseArray.length)+"\r\n";
        for (i=0; i<ItemArray.length; i++) {
            말+= "#L"+i+"# #i"+ItemArray[i]+"#"
            if (lineUseArray.some(function(ok,index,lineUseArray) {
                 return ok == i;
                  }) == true) { 
                말+="#b#e#z"+ItemArray[i]+"##n#k\r\n"
            } else {
                말+="#z"+ItemArray[i]+"#\r\n";
            }
        }
        cm.sendSimple(말);
        status --;
       } else {
          말 = "현재 선택된 리스트의 아이템을 넣고 뽑기를 시작하시겠습니까?\r\n\r\n"
          for (i=0; i<lineUseArray.length; i++) {
             말+= "#i"+ItemArray[lineUseArray[i]]+"# #b#e#z"+ItemArray[lineUseArray[i]]+"##k#n\r\n";
          }
          cm.sendYesNo(말)
       }
      } else if (status == 1) {
         if (cm.itemQuantity(coincode) >= coinqty) {
          랜덤 = ItemArray[lineUseArray[Math.floor(Math.random() * lineUseArray.length)]]
          cm.gainItem(랜덤,1);
          cm.gainItem(coincode,-coinqty)
          cm.sendOk("어머어머! 좋은 아이템도 나오셨네요!\r\n\r\n#i"+랜덤+"# #b#z"+랜덤+"##k");
        } else {
          cm.sendOk("코인이 부족합니다.")
        }
          cm.dispose();
    }
}
