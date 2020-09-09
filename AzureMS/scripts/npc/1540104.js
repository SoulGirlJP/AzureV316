 /*
# 저작자 : 멜론K [melon_dev@nate.com]
# 기　능 : 자작 퀘스트
*/
var servername = "MelonStyle"
var selection = 0;
var status = 0;
reqitem = [4310119, 4310036, 4310038, 4310034, 4031476, 4032101];  qwan = [30000, 10000, 5000, 5000, 3000, 500]; 
// reqitem : [1,2,3,4,5,6] 차례대로 1번퀘스트~6번퀘스트 에서 필요한 아이템 코드 qwan : 그 아이템의 양
rewitem = [3994353, 3994354, 3994355, 3994356, 3994357, 3994358];
// rewitem : 퀘스트완료 증표 or 보상
var status = -1;
function start() {
    status = -1;
    action (1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }
    if (status == -1) {
       cm.dispose();
       return;
    }
    if (status == 0) {
    var hello = "Hi #bBoss title#k Quest NPC.\r\n";
         hello += "Please select a quest you would like to play.\r\n\r\n";
         hello += "#bComplete all quests#k Rewards will be given.\r\n\r\n";
         hello += "#fUI/UIWindow2.img/UtilDlgEx/list1#\r\n";
        if (cm.haveItem(1142110)) {
         hello += "You have already cleared all quests.";
         } else if (cm.haveItem(3994358)) {
         hello += "You have already cleared all quests.";
         } else if (cm.haveItem(rewitem[4], 1)) {
         hello += "#r#L5#Lv. 6#b  Lucid, defeat Damien#k\r\n";
         } else if (cm.haveItem(rewitem[3], 1)) {
         hello += "#r#L4#Lv. 5#b  Let's collect glass beads#k\r\n";
         } else if (cm.haveItem(rewitem[2], 1)) {
         hello += "#r#L3#Lv. 4#b  Let's collect the nose#k\r\n";
         } else if (cm.haveItem(rewitem[1], 1)) {
         hello += "#r#L2#Lv. 3#b  Insufficient coins 2#k\r\n"
         } else if (cm.haveItem(rewitem[0], 1)) {
         hello += "#r#L1#Lv. 2#b  Insufficient coins 1#k\r\n"
         } else {
         hello += "#r#L0#Lv. 1#b  Let's collect maple leaves #k\r\n"
     }
    cm.sendSimple(hello);
    return;
        } else if (status == 1) {
          if (selection == 0) {
         var say0 = "안녕하세요 #b#h0##k님!\r\n저희 고장은 단풍잎으로 유명하답니다.\r\n";
             say0 += "하지만, 요즘 단풍잎이 너무없어서 골치아파요.\r\n"
             say0 += "그래서 그런데, 아이스박스를까서 단풍잎좀 주실수있나요?\r\n만약 단풍잎을주시면, 보상은 충분히 드리죠!\r\n\r\n";
             say0 += "#b#L6#알겠어 가져 다 줄게!#k\r\n"
             say0 += "#b#L7#미안해, 못 가져다 줄 것 같아#k\r\n"
             say0 += "#b#L8#다 잡아 왔어!#k"
             cm.sendSimple(say0);
         } else if (selection == 1) {
         var say1 = "강화좀 하려는데, 코인이 모자라요.. \r\n#b#h0#씨! #k";
             say1 += "혹시 코인좀 가져다 줄 수 있나요?\r\n"
             say1 += "가져다주면 제가 뽀뽀를 해드리죠!\r\n\r\n";
             say1 += "#b#L10#그래, 알겠어#k\r\n"
             say1 += "#b#L11#지금은 시간이 없어#k\r\n"
             say1 += "#b#L12#다 가져 왔어!#k"
             cm.sendSimple(say1);
       } else if (selection == 2) {
         var say2 = "#b#h0#님! #k저가 요즘 새로운 취미가 생겼어요\r\n";
             say2 += "바로 엄청난 강화를 하는건데요\r\n"
             say2 += "코인이 부족해요.. 좀 가져다 주실수 있나요?\r\n\r\n";
             say2 += "#b#L20#당연하지, 가져다줄께~#k\r\n"
             say2 += "#b#L21#나중에 해줄게, 미안해#k\r\n"
             say2 += "#b#L22#다 가져 왔어!#k"
             cm.sendSimple(say2);
  } else if (selection == 3) {
         var say3 = "#b#h0#님! #k저희집은 보스잡기로 유명한데요,\r\n";
             say3 += "어떤 해적이 나타나 보스를 못잡게 길을 막았어요.\r\n"
             say3 += "혹시 보스를 잡아주실수있을까요..\r\n\r\n";
             say3 += "#b#L30#당연하지, 잡아줄께#k\r\n"
             say3 += "#b#L31#지금 바빠..#k\r\n"
             say3 += "#b#L32#다 가져 왔어!#k"
             cm.sendSimple(say3);
  } else if (selection == 4) {
         var say4 = "#b#h0#님! #k저희 옆 집에서 모카딘이라는 보스를잡네요!\r\n";
             say4 += "하지만, 저는 너무약해서 못잡습니다. \r\n"
             say4 += "저를위해서 보스를 잡아 주실 수 있을까요?\r\n\r\n";
             say4 += "#b#L40#그래, 조금만 기다려#k\r\n"
             say4 += "#b#L41#미안해.. 시간이 없어#k\r\n"
             say4 += "#b#L42#다 가져 왔어!#k"
             cm.sendSimple(say4);
  } else if (selection == 5) {
         var say5 = "#b#h0#님! #k마지막 시험 과제입니다.\r\n";
             say5 += "루시드.데미안을 잡고오는것입니다.\r\n"
             say5 += "하실 용기가 있으신가요? 파티를 짜고 해야 쉬울겁니다.\r\n\r\n";
             say5 += "#b#L50#지금 바로 시작하지#k\r\n"
             say5 += "#b#L51#아직 준비가 안?獰?#k\r\n"
             say5 += "#b#L52#다 가져 왔어!#k"
             cm.sendSimple(say5);
}
      } else if (status == 2) {
// sorry0~sorry6 = 자신이 가지고 있는 퀘스트1~6의 조건아이템 개수
// soso0~6 = 자신이 더 모아야 하는 조건 아이템 개수 
var sorry0 = cm.itemQuantity(4310119);  var sorry1 = cm.itemQuantity(4310036);  
var sorry2 = cm.itemQuantity(4310038);  var sorry3 = cm.itemQuantity(4310034);
var sorry4 = cm.itemQuantity(4031476);  var sorry5 = cm.itemQuantity(4000653);
var sorry101 = cm.itemQuantity(3994358); 
var soso0 = qwan[0] - sorry0;  var soso1 = qwan[1] - sorry1;
var soso2 = qwan[2] - sorry2;  var soso3 = qwan[3] - sorry3;
var soso4 = qwan[4] - sorry4;  var soso5 = qwan[5] - sorry5;
       if (selection == 6) {
         cm.sendOk("고마워~ 그러면 #i"+reqitem[0]+"##b[#z"+reqitem[0]+"#]#k #r"+qwan[0]+"개#k만 모아줘~");
         cm.dispose();
       } else if (selection == 7) {
          cm.sendOk("그럼 할수없네요.. 나중에라도 도와주세요~");
          cm.dispose();
       } else if (selection == 8) {
          if (cm.haveItem(reqitem[0], qwan[0])) {
          cm.gainItem(reqitem[0], -qwan[0]);
          cm.gainItem(rewitem[0], 1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[0]+"##z"+reqitem[0]+"##k은(는) #r"+sorry0+" 개#k 이며,\r\n"
          + "아직 #b"+ soso0 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
       } else if (selection == 10) {
         cm.sendOk("고마워~ 그러면 #i"+reqitem[1]+"##b[#z"+reqitem[1]+"#]#k #r"+qwan[1]+"개#k만 모아줘~");
         cm.dispose();
       } else if (selection == 11) {
          cm.sendOk("그럼 할수없네요.. 나중에라도 도와주세요~");
          cm.dispose();
       } else if (selection == 12) {
          if (cm.haveItem(reqitem[1], qwan[1])) {
          cm.gainItem(reqitem[1], -qwan[1]);
          cm.gainItem(rewitem[1], 1);
          cm.gainItem(rewitem[0], -1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[1]+"##z"+reqitem[1]+"##k은(는) #r"+sorry1+" 개#k 이며,\r\n"
          + "아직 #b"+ soso1 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
       } else if (selection == 20) {
         cm.sendOk("고마워~ 그러면 #i"+reqitem[2]+"##b[#z"+reqitem[2]+"#]#k #r"+qwan[2]+"개#k만 모아줘~");
         cm.dispose();
       } else if (selection == 21) {
          cm.sendOk("그럼 할수없네요.. 나중에라도 도와주세요~");
          cm.dispose();
       } else if (selection == 22) {
          if (cm.haveItem(reqitem[2], qwan[2])) {
          cm.gainItem(reqitem[2], -qwan[2]);
          cm.gainItem(rewitem[2], 1);
          cm.gainItem(rewitem[1], -1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[2]+"##z"+reqitem[2]+"##k은(는) #r"+sorry2+" 개#k 이며,\r\n"
          + "아직 #b"+ soso2 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
       } else if (selection == 30) {
         cm.sendOk("고마워~ 그러면 #i"+reqitem[3]+"##b[#z"+reqitem[3]+"#]#k #r"+qwan[3]+"개#k만 모아줘~");
         cm.dispose();
       } else if (selection == 31) {
          cm.sendOk("그럼 할수없네요.. 나중에라도 도와주세요~");
          cm.dispose();
       } else if (selection == 32) {
          if (cm.haveItem(reqitem[3], qwan[3])) {
          cm.gainItem(reqitem[3], -qwan[3]);
          cm.gainItem(rewitem[3], 1);
          cm.gainItem(rewitem[2], -1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[3]+"##z"+reqitem[3]+"##k은(는) #r"+sorry3+" 개#k 이며,\r\n"
          + "아직 #b"+ soso3 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
       } else if (selection == 40) {
         cm.sendOk("고마워~ 그러면 #i"+reqitem[4]+"##b[#z"+reqitem[4]+"#]#k #r"+qwan[4]+"개#k만 모아줘~");
         cm.dispose();
       } else if (selection == 41) {
          cm.sendOk("그럼 할수없네요.. 나중에라도 도와주세요~");
          cm.dispose();
       } else if (selection == 42) {
          if (cm.haveItem(reqitem[4], qwan[4])) {
          cm.gainItem(reqitem[4], -qwan[4]);
          cm.gainItem(rewitem[4], 1);
          cm.gainItem(rewitem[3], -1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[4]+"##z"+reqitem[4]+"##k은(는) #r"+sorry4+" 개#k 이며,\r\n"
          + "아직 #b"+ soso4 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
       } else if (selection == 50) {
         cm.sendOk("#i"+reqitem[5]+"##b[#z"+reqitem[5]+"#]#k #r"+qwan[5]+"개#k를 가져와 보세요!");
         cm.dispose();
       } else if (selection == 51) {
          cm.sendOk("나중에 도전하신다니, 알겠습니다.");
          cm.dispose();
       } else if (selection == 52) {
          if (cm.haveItem(reqitem[5], qwan[5])) {
          cm.gainItem(reqitem[5], -qwan[5]);
          cm.gainItem(rewitem[5], 1);
          cm.gainItem(rewitem[4], -1);
            cm.showEffect(true,"monsterPark/clear");
            cm.playSound(true,"Party1/Clear");
          cm.dispose();
         } else {
          cm.sendOk("아직 덜 가져 오신것 같아요. #h0#님! \r\n가지고 계신 #b#i"+reqitem[5]+"##z"+reqitem[5]+"##k은(는) #r"+sorry5+" 개#k 이며,\r\n"
          + "아직 #b"+ soso5 + "개#k가 부족합니다. 빨리 다 모아와 주세요!");
          cm.dispose();
}
}
}
}