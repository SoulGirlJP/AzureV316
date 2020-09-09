/*




Corporation.Spirit

leader.스피릿온라인 (terams_@nate.com)

member.UnknownStar (rhduddlr6996@nate.com)

member.우비 (guri__s@nate.com)

Make.공석 (iureal@nate.com)




이 스크립트는 스피릿온라인에만 사용됩니다.

만약 유출이 되더라도 이 주석은 삭제하지 않으셨으면 좋겠습니다.




*/




var status = 0;

var jessica1 = Math.floor(Math.random() * 20);

var jessica2 = Math.floor(Math.random() * 20);

var jessica3 = Math.floor(Math.random() * 20);

var jessica4 = Math.floor(Math.random() * 20);

var jessica5 = Math.floor(Math.random() * 20);

var jessica6 = Math.floor(Math.random() * 20);

var Meso = 100000000;




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

 if (cm.getMeso() > Meso) {

  cm.sendYesNo("로또에 참여하시겠습니까?\r\n로또 참여금은 #b" + Meso + "원#k(1억) 입니다.\r\n\r\n#e#r[로또 당첨금은 500억]#k#d입니다 하는방법은 뽑힌 로또번호와 자신의 닉네임나오도록 스샷으로 찍어서 카페 로또\r\n게시판에 올려주시면 수시로 확인해드리겠습니다.\r\n#e#r로또 당첨번호 확인은 카페 공지사항에서 확인");

 } else {

  cm.sendOk("로또에 참여하시려면 #b" + Meso + "원#k(1억) 이 필요합니다.");

  cm.dispose();

  } 

 } else if (status == 1) {

  cm.gainMeso(-Meso);

  var jessica = "#r메이플스토리 로또 번호 뽑기 시스템#k 입니다.\r\n";

  jessica += "\r\n#b첫번째 숫자로 " + jessica1 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 2) {

  first = jessica1;

  jessica = "#r첫번째 숫자는 " + first + "입니다.\r\n";

  jessica += "\r\n#b두번째 숫자로는 " + jessica2 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 3) {

  first = jessica1;

  second = jessica2;

  var jessica = "#r첫번째 숫자는 " + first + "입니다.";

  jessica += "\r\n두번째 숫자는 " + second + "입니다.\r\n";

  jessica += "\r\n#b세번째 숫자로는 " + jessica3 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 4) {

  first = jessica1;

  second = jessica2;

  third = jessica3;

  var jessica = "#r첫번째 숫자는 " + first + "입니다.";

  jessica += "\r\n두번째 숫자는 " + second + "입니다.";

  jessica += "\r\n세번째 숫자는 " + third + "입니다.\r\n";

  jessica += "\r\n#b네번째 숫자로는 " + jessica4 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 5) {

  first = jessica1;

  second = jessica2;

  third = jessica3;

  fourth = jessica4;

  var jessica = "#r첫번째 숫자는 " + first + "입니다.";

  jessica += "\r\n두번째 숫자는 " + second + "입니다.";

  jessica += "\r\n세번째 숫자는 " + third + "입니다.";

  jessica += "\r\n네번째 숫자는 " + fourth + "입니다.\r\n";

  jessica += "\r\n#b다섯번째 숫자로는 " + jessica5 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 6) {

  first = jessica1;

  second = jessica2;

  third = jessica3;

  fourth = jessica4;

  fifth = jessica5;

  var jessica = "#r첫번째 숫자는 " + first + "입니다.";

  jessica += "\r\n두번째 숫자는 " + second + "입니다.";

  jessica += "\r\n세번째 숫자는 " + third + "입니다.";

  jessica += "\r\n네번째 숫자는 " + fourth + "입니다.";

  jessica += "\r\n다섯번째 숫자는 " + fifth + "입니다.\r\n";

  jessica += "\r\n#b여섯번째 숫자로는 " + jessica6 + "가 나왔습니다.";

  cm.sendNext(jessica);




 } else if (status == 7) {

  first = jessica1;

  second = jessica2;

  third = jessica3;

  fourth = jessica4;

  fifth = jessica5;

  sixth = jessica6;

  var jessica = "지금까지 총 나온 숫자는\r\n";

  jessica += "#r" + first + "\r\n";

  jessica += "" + second + "\r\n";

  jessica += "" + third + "\r\n";

  jessica += "" + fourth + "\r\n";

  jessica += "" + fifth + "\r\n";

  jessica += "" + sixth + "#k";

  jessica += " 입니다.\r\n";

  cm.sendNext(jessica);

  cm.dispose();

}

}

}