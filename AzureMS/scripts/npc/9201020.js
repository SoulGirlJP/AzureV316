


/*

하요님이 그리운 멜론K(melon_dev@nate.com)
나의 11월달 마지막 작품이랄까?

*/

var status = -1;
var star = "#fUI/UIToolTip/Item/Equip/Star/Star#"

function start() {
    status = -1;
    action (1, 0, 0);
}


function action(mode, type, selection) {
if(cm.getPlayer().getKeyValue("어빌리티") == null) {; cm.getPlayer().setKeyValue("어빌리티",3); }

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
}
    if (status == 0) {
        var ask = "#e#r< 새로운능력 :: 어빌리티개방!! >#n#k\r\n#b어빌리티#k를 개방하기 위해서는 #r1억 메소와 130 레벨제한#k 이 있지 이것을 감수하고도 #b어빌리티 능력#k을 개방시키겠어요?\r\n\r\n"
            ask+= "#L1##b 어빌리티 재설정은 어떻게 하는 것인가요?\r\n";
            ask+= "#L2##b 지금 어빌리티를 개방해 보겠습니다.";
        cm.sendSimple(ask);
        return;
     } else if (status == 1) {
      if (selection == 0) {
        var how = "#r새로운 힘#k을 받기 위해서는 아래와 같은 조건이 필요합니다.\r\n\r\n"; 
            how+= ""+star+" #b레벨#k이 #r130 이상#k이어야 됩니다.\r\n";
            how+= ""+star+" #b1억 메소#k가 필요합니다.";
            cm.sendOk(how);
            cm.dispose();
        } else if (selection == 1) {
        var reset = "#b어빌리티 재설정을 하려면 다음과 같은 아이템이 필요합니다.#k\r\n"
            reset+="#i2431174# #r[#t2431174#]#k\r\n이 아이템으로 명성치를 올러서 [S] 단축키에있는 능력치세팅창에서 재설정을 하실수있습니다.\r\n"
            cm.sendOk(reset);
            cm.dispose();
            return;
       } else if (selection == 2) {
         if (cm.getPlayer().getLevel() < 130) {
            cm.sendOk("레벨이 #b130 이상#k되지 않으므로 힘을 드릴 수 없습니다.");
            cm.dispose();
         } else if (cm.getPlayer().getMeso() < 100000000) {
            cm.sendOk("메소가 부족해 힘을 드릴 수 없습니다.");
            cm.dispose();
          } else if (cm.getPlayer().getKeyValue("어빌리티") <= 0) {
            cm.sendOk("이미 모든힘이 개방되었습니다.");
          } else {
            cm.getPlayer().innerLevelUp();
	    cm.getPlayer().dropMessage(6, "운영자에 의해 당신의 힘이 진화되었습니다.");
            cm.gainMeso(-100000000);
            cm.fakeRelog();
            cm.updateChar();
            cm.getPlayer().setKeyValue("어빌리티",Number(cm.getPlayer().getKeyValue("어빌리티")) - 1)
            cm.dispose();
        }
       
}
}

    }


    

