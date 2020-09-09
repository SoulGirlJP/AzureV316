importPackage(Packages.packet.creators);
importPackage(Packages.launch.world);
importPackage(Packages.main.world);
 function start() { Status = -1; action(1, 0, 0); }

 function action(M, T, S) {

  if (M == -1) { cm.dispose(); } else {
  if (M == 0) {cm.dispose(); return; }
  if (M == 1) Status++; else Status--;    

 if(Status == 0) {
 cm.sendYesNo("메소 럭키백을 개봉하시겠습니까? 50만 메소에서 100억 메소 사이의 금액을 획득할 수 있습니다."
  +"\r\n\r\n#e#r인벤토리 기타 탭의 여유 공간을 2칸 이상 확보해주세요. 여유 공간이 없어서 아이템이 지급되지 않는 경우에 추가 지급이 어렵습니다.")
 }

 else if(Status == 1) {

  if(!cm.haveItem(2433019)) {
  cm.sendOk("#i2433019# #b#z2433019##k이 있다고 우겨서 되는게 아니야. 봐, 네 인벤토리에는 없잖아?");
  cm.dispose();
  } else {
  cm.gainItem(2433019, -1);
  Rullet();
  cm.sendOk("축하해 메소 럭키백에서 "+W+"메소를 얻었어. 지금 바로 인벤토리를 확인해 봐.#b"
   + "\r\n\r\n #fUI/UIWindow.img/QuestIcon/7/0# "+W+" 메소\r\n");
   if(N > 4001207 && N < 4001213) {
   WorldBroadcasting.broadcast(MainPacketCreator.getGMText(20, "[핫타임] "+cm.getPlayer().getName()+" 님이 메소 럭키백에서 "+W+" 메소를 획득했습니다. 모두 축하해주세요~"));
   cm.gainItem(N, Q);
   } else {
   cm.gainMeso(N);
   }
  cm.dispose();
  }
 }
 }
}



function Rullet() {
M = Math.floor(Math.random() * 10000);
 switch(M) {
 // 100억에서 20억씩 감소해서 지급
 case 0:    case 5000: N = 4001212; Q = 50; W = "100억"; break;
 case 1000: case 6000: N = 4001212; Q = 40; W = "80억"; break;
 case 2000: case 7000: N = 4001212; Q = 30; W = "60억"; break;
 case 3000: case 8000: N = 4001212; Q = 20; W = "40억"; break;
 case 4000: case 9000: N = 4001212; Q = 10; W = "20억"; break;

 // 90억에서 20억씩 감소해서 지급
 case 100:  case 5100: N = 4001212; Q = 45; W = "90억"; break;
 case 1100: case 6100: N = 4001212; Q = 35; W = "70억"; break;
 case 2100: case 7100: N = 4001212; Q = 25; W = "50억"; break;
 case 3100: case 8100: N = 4001212; Q = 15; W = "30억"; break;
 case 4100: case 9100: N = 4001212; Q = 5;  W = "10억"; break;

 // 10억에서 2억씩 감소해서 지급
 case 50:   case 5050: N = 4001212; Q = 5;  W = "10억"; break;
 case 1050: case 6050: N = 4001212; Q = 4;  W = "8억"; break;
 case 2050: case 7050: N = 4001212; Q = 3;  W = "6억"; break;
 case 3050: case 8050: N = 4001212; Q = 2;  W = "4억"; break;
 case 4050: case 9050: N = 4001212; Q = 1;  W = "2억"; break;

 // 9억에서 2억씩 감소해서 지급
 case 70:   case 5070: N = 4001211; Q = 9;  W = "9억"; break;
 case 1070: case 6070: N = 4001211; Q = 7;  W = "7억"; break;
 case 2070: case 7070: N = 4001211; Q = 5;  W = "5억"; break;
 case 3070: case 8070: N = 4001211; Q = 3;  W = "3억"; break;
 case 4070: case 9070: N = 4001211; Q = 1;  W = "1억"; break;

 // 1억에서 5천만씩 감소해서 지급
 case 60:   case 5060: N = 4001210; Q = 2;  W = "1억"; break;
 case 1060: case 6060: N = 4001210; Q = 1;  W = "5천만"; break;

 // 9천만에서 3천만씩 감소해서 지급
 case 145:  case 5145: N = 4001209; Q = 3;  W = "9천만"; break;
 case 1145: case 6145: N = 4001209; Q = 2;  W = "6천만"; break;
 case 2145: case 7145: N = 4001209; Q = 1;  W = "3천만"; break;

 // 1억에서 1천만씩 감소해서 지급
 case 474:  case 5474: N = 4001208; Q = 10; W = "1억"; break;
 case 1474: case 6474: N = 4001208; Q = 9;  W = "9천만"; break;
 case 2474: case 7474: N = 4001208; Q = 8;  W = "8천만"; break;
 case 3474: case 8474: N = 4001208; Q = 7;  W = "7천만"; break;
 case 4474: case 9474: N = 4001208; Q = 6;  W = "6천만"; break;
 case 873 : case 5873: N = 4001208; Q = 5;  W = "5천만"; break;
 case 1873: case 9873: N = 4001208; Q = 4;  W = "4천만"; break;
 case 2873: case 9873: N = 4001208; Q = 3;  W = "3천만"; break;
 case 3873: case 9873: N = 4001208; Q = 2;  W = "2천만"; break;
 case 4873: case 9873: N = 4001208; Q = 1;  W = "천만"; break;

 default: N = M*1000; W = N; break;
 }
}