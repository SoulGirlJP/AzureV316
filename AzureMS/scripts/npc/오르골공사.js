/*
제작 : 엘리뚜(skymin0307)
*/
/**
배경음악 변경 NPC
**/
var status = 0;
var musicplayer

function start() {
 status = -1;
 action(1, 0, 0);
}

function action(mode, type, selection) {
 if (mode == -1) {
  cm.dispose();
 } else {
 if (mode == 0) {
  cm.sendOk("브금 변경 하시는 부분임?");
  cm.dispose();
 }
  status++;
  if(status == 0) {
   cm.sendSimple ("#h ##k새끼야 반갑다.\r\n나는 BGM DJ#n#k  #b엔피시#k#n라고 한다. \r\n 브금 변경하려면 1,000,000 내놔야 한다, 음악을 재생할경우에는 그 맵에 현재 있는 사람만 듣게 된다. 새로 입장하는 사람은 새로 재생해야 들을수 있다. \r\n#e원하는 배경음악을 골라봐라. \r\n#I#L44# [iKON - 사랑을 했다 (LOVE SCENARIO)] \r\n#I#L45# [장덕철 - 그날처럼] \r\n#I#L46# [수지 - 다른사랑을 사랑하고 있어] \r\n#I#L47# [선미 - 주인공] \r\n#I#L48# [볼빨간사춘기 - 첫사랑] \r\n#I#L49# [문문 - 비행운] \r\n#I#L51# [김동률 - 답장] \r\n#I#L52# [멜로망스 - 선물] \r\n#I#L53# [DEAN - instagram] \r\n#I#L54# [EXO - Universe] \r\n#I#L55# [Camila Cabello - Havana] \r\n#I#L56# [TWICE - Heart Shaker] \r\n#I#L57# [청하 - Roller Coaster] \r\n#I#L58# [펀치 - 밤이 되니까] \r\n#I#L59# [종현 - 빛이 나] \r\n#I#L60# [종현- Lonely] \r\n#I#L61# [방탄소년단 - DNA] \r\n#I#L62# [로꼬 - 주지마] \r\n#I#L63# [여자친구 - 밤] \r\n#I#L64# [닐로 - 지나오다] \r\n#I#L65# [김하온 - 붕붕] \r\n#I#L66# [마마무 - 별이 빛나는밤] \r\n#I#L67# [Jessie J -Price Tag] \r\n#I#L68# [시아준수 - 나비] \r\n#I#L69# [Sia - Chandelier] \r\n#I#L70# [Coldplay - Something Just Like This] \r\n#I#L71# [Ed Sheeran - Shape of you] \r\n#I#L72# [Justin Bieber ? Despacito ] \r\n#I#L73# [BTOB - Only one for me] \r\n#I#L74# [이병재 - 전혀 (Feat. 우원재) ] \r\n#I#L75# [V.O.S - 나 이젠] \r\n#I#L76# [Wanna One - 캥거루 ] \r\n#I#L77# [박지헌(V.O.S) - 보고싶은날엔] \r\n#I#L78# [블랙핑크 - 뚜두뚜두] \r\n#I#L79# [기리보이 - flex] \r\n#I#L80# [nafla - wu]  \r\n#I#L81# [펑티모 - 고양이송]");
  } else if(status == 1) {
musicplayer = cm.getChar().getName();
    if (selection == 44) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/1");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e iKON - 사랑을 했다 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 027 iKON - 사랑을 했다으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 45) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/2");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 장덕철 - 그날처럼 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 장덕철 - 그날처럼으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 46) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/3");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 수지 - 다른사랑을 사랑하고 있어 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 수지 - 다른사랑을 사랑하고 있어으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 47) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/4");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 선미 - 주인공 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 선미 - 주인공으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 48) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/5");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 볼빨간사춘기 - 첫사랑 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 볼빨간사춘기 - 첫사랑으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 49) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/6");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 문문 - 비행운 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 문문 - 비행운으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 51) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/8");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 김동률 - 답장 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 김동률 - 답장으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 52) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/9");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 멜로망스 - 선물 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 멜로망스 - 선물으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 53) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/10");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e DEAN - instagram 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 DEAN - instagram으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 54) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/11");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e EXO - Universe 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 EXO - Universe으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 55) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/12");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Camila Cabello - Havana 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Camila Cabello - Havana 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 56) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/13");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e TWICE (트와이스) - Heart Shaker 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 TWICE (트와이스) - Heart Shaker으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 57) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/14");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 청하 - Roller Coaster 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 청하 - Roller Coaster으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 58) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/15");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 펀치(Punch) - 밤이 되니까 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 펀치(Punch) - 밤이 되니까으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 59) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/16");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 종현 - 빛이 나 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 종현 - 빛이 나 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 60) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/17");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 종현 - Lonely (Feat. 태연) 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 종현 - Lonely (Feat. 태연)으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 61) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/18");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 방탄소년단 - DNA 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 방탄소년단 - DNA으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 62) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/19");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 로꼬 - 주지마 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 로꼬 - 주지마으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 63) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/20");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 여자친구 - 밤 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 여자친구 - 밤으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 64) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/21");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 닐로 - 지나오다 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 닐로 - 지나오다으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 65) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/22");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 김하온 - 붕붕 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 김하온 - 붕붕으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 66) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/23");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 마마무 - 별이 빛나는밤 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 마마무 - 별이 빛나는밤으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 67) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/24");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Jessie J -Price Tag 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Jessie J -Price Tag으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 68) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/25");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 시아준수 - 나비 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 시아준수 - 나비으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 69) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/26");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Sia - Chandelier 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Sia - Chandelier으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 70) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/27");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Coldplay - Something Just Like This 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Coldplay - Something Just Like This 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
    }
    } else if (selection == 71) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/28");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Ed Sheeran - Shape of you 음악이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Ed Sheeran - Shape of you으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 72) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/29");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e Justin Bieber ? Despacito이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 Justin Bieber ? Despacito으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 73) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/30");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e [MV] 비투비 - Only one for me이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 비투비 - Only one for me으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 74) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/31");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e [고등래퍼2 Final] 이병재 - 전혀 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 이병재 - 전혀 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 75) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/32");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e V.O.S - 나 이젠 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 V.O.S - 나 이젠 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 76) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/33");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 워너원 - 캥거루 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 워너원 - 캥거루 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 77) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/34");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e V.O.S - 보고싶은날엔이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 V.O.S - 보고싶은날엔으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 78) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/35");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 블랙핑크 - 뚜두뚜두이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 블랙핑크 - 뚜두뚜두으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 79) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/36");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 기리보이 - flex 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 기리보이 - flex으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 80) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/37");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e nafla - Wu 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 nafla - Wu 으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
    } else if (selection == 81) {
    if(cm.getMeso() > 1000000 == true) { 
     cm.gainMeso(-1000000);
     cm.changeMusic("Jukebox/38");
     cm.sendOk("#fMap/MapHelper.img/mark/Orbis##e 펑티모-고양이송 이 재생됩니다.");
     cm.mapMessage("[BGM변경] "+ musicplayer +"님이 배경음악을 펑티모-고양이송  으로 바꾸셨습니다.");
     cm.dispose();
    } else {
     cm.sendOk("메소를 지불하지 않으면 배경음악을 바꾸실수 없습니다.");
     cm.dispose();
}
   }
  }
 }
}