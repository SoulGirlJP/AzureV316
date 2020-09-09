var select = -1;

var 별 = "#fUI/FarmUI.img/objectStatus/star/whole#";

importPackage(Packages.constants);

function start() {
    status = -1;
    action (1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
	if (cm.getPlayer().getMapId() == 100000000) {
	cm.sendYesNo("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                              원하시는 진행 여부를 선택해주세요.#k\r\n#fs13#\r\n#b보조 무기#k 는 구매시 바로 #r교체 장착#k 되므로 #r주의#k 바랍니다.\r\n\r\n30 레벨#k : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨#k : #r100 만 메소#k\r\n100 레벨 : #r200 만 메소#k\r\n\r\n#b보조 무기#k 구입을 계속 진행 하시겠습니까.?");
	} else {
		cm.sendOk("#fn나눔고딕 Extrabold##r보조무기 상점은 광장에서만 이용이 가능합니다.#k");
		cm.dispose();
	}
      } else if (status == 1) {
	var bojo = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs11#";
	bojo += "#L1##v1352203# #b모험가#k 보조 무기"
	bojo += "#L2##v1353203# #b키네시스#k 보조 무기\r\n";
	bojo += "#L3##v1353103# #b영웅#k 보조 무기";
	bojo += "#L4##v1352973# #b시그너스#k 보조 무기\r\n";
	bojo += "#L5##v1352503# #b노바#k 보조 무기";
	bojo += "#L6##v1352953# #b레지스탕스#k 보조 무기";
	cm.sendSimple(bojo);
      } else if (selection == 1) {
	var Adventurer = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Adventurer += "#L11##v1352202# #b전사#k 보조 무기\r\n"
	Adventurer += "#L12##v1352251# #b마법사#k 보조 무기\r\n";
	Adventurer += "#L13##v1352273# #b궁수#k 보조 무기\r\n";
	Adventurer += "#L14##v1352293# #b도적#k 보조 무기\r\n#l ";
	Adventurer += "#L15##v1352913# #b해적#k 보조 무기";
	cm.sendSimple(Adventurer);
      } else if (selection == 2) {
	var Kinesys = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Kinesys += "#L2100##v1353203# #b키네시스#k 보조 무기\r\n"
	cm.sendSimple(Kinesys);
      } else if (selection == 3) {
	var Hero = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs11#";
	Hero += "#L31##v1352933# #b아란#k 보조 무기";
	Hero += "#L32##v1352943# #b에반#k 보조 무기\r\n";
	Hero += "#L33##v1352103# #b팬텀#k 보조 무기";
	Hero += "#L34##v1353103# #b은월#k 보조 무기\r\n";
	Hero += "#L35##v1352403# #b루미너스#k 보조 무기";
	Hero += "#L36##v1352003# #b메르세데스#k 보조 무기\r\n";
	cm.sendSimple(Hero);
      } else if (selection == 4) {
	var Cygnus = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Cygnus += "#L41##v1352972# #b시그너스#k 보조 무기\r\n"
	Cygnus += "#L4100##v1098002# #b미하일#k 보조 무기\r\n"
	cm.sendSimple(Cygnus);
      }  else if (selection == 5) {
	var Nova = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Nova += "#L51##v1352503# #b카이저#k 보조 무기\r\n#l "
	Nova += "#L52##v1352604# #b엔젤릭버스터#k 보조 무기\r\n";
	cm.sendSimple(Nova);
      }  else if (selection == 6) {
	var Resistance = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Resistance += "#L61##v1099009# #b데몬 슬레이어 & 어벤져#k 보조 무기\r\n"
	Resistance += "#L62##v1352952# #b배틀메이지#k 보조 무기\r\n";
	Resistance += "#L63##v1352962# #b와일드헌터#k 보조 무기\r\n";
	Resistance += "#L64##v1352702# #b메카닉#k 보조 무기\r\n#l ";
	Resistance += "#L65##v1353004# #b제논#k 보조 무기\r\n#l ";
	Resistance += "#L66##v1353403# #b블래스터#k 보조 무기\r\n#l ";
	cm.sendSimple(Resistance);
      } else if (selection == 11) {
	var Warrior = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Warrior += "#L100##v1352202# #b히어로#k 보조 무기\r\n"
	Warrior += "#L200##v1352213# #b팔라딘#k 보조 무기\r\n";
	Warrior += "#L300##v1352223# #b다크나이트#k 보조 무기\r\n";
	cm.sendSimple(Warrior);
      } else if (selection == 12) {
	var Magician = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Magician += "#L400##v1352233# #b아크메이지(불,독)#k 보조 무기\r\n"
	Magician += "#L500##v1352243# #b아크메이지(썬,콜)#k 보조 무기\r\n";
	Magician += "#L600##v1352253# #b비숍#k 보조 무기\r\n";
	cm.sendSimple(Magician);
      } else if (selection == 13) {
	var Archer = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Archer += "#L700##v1352262# #b보우마스터#k 보조 무기\r\n"
	Archer += "#L800##v1352272# #b신궁#k 보조 무기\r\n";
	cm.sendSimple(Archer);
      } else if (selection == 14) {
	var Thief = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Thief += "#L900##v1352292# #b나이트로드#k 보조 무기\r\n"
	Thief += "#L1000##v1352283# #b섀도어#k 보조 무기\r\n";
	cm.sendSimple(Thief);
      } else if (selection == 15) {
	var Pirate = "           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n#fs12#";
	Pirate += "#L1100##v1352913# #b캡틴#k 보조 무기\r\n"
	Pirate += "#L1200##v1352902# #b바이퍼#k 보조 무기\r\n";
	Pirate += "#L1300##v1352923# #b캐논슈터#k 보조 무기\r\n";
	cm.sendSimple(Pirate);
      } else if (selection == 100) { //히어로
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 110) {
	cm.addEquip(-10, 1352200);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 111) {
	cm.addEquip(-10, 1352201);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 112) {
	cm.addEquip(-10, 1352202);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 200) { //팔라딘
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 120) {
	cm.addEquip(-10, 1352210);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 121) {
	cm.addEquip(-10, 1352211);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 122) {
	cm.addEquip(-10, 1352212);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 300) { //다크나이트
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 130) {
	cm.addEquip(-10, 1352220);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 131) {
	cm.addEquip(-10, 1352221);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 132) {
	cm.addEquip(-10, 1352222);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
	//모험가 : 전사 끝
	//모험가 : 마법사 시작
      } else if (selection == 400) { //아크메이지(불,독)
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 210) {
	cm.addEquip(-10, 1352230);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 211) {
	cm.addEquip(-10, 1352231);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 212) {
	cm.addEquip(-10, 1352232);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 500) { //아크메이지(썬,콜)
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 220) {
	cm.addEquip(-10, 1352240);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 221) {
	cm.addEquip(-10, 1352241);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 222) {
	cm.addEquip(-10, 1352242);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 600) { //비숍
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 230) {
	cm.addEquip(-10, 1352250);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 231) {
	cm.addEquip(-10, 1352251);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 232) {
	cm.addEquip(-10, 1352252);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
	//모험가 : 마법사 끝
	//모험가 : 궁수 시작
      } else if (selection == 700) { //보우마스터
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 310) {
	cm.addEquip(-10, 1352260);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 311) {
	cm.addEquip(-10, 1352261);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 312) {
	cm.addEquip(-10, 1352262);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 800) { //신궁
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 320) {
	cm.addEquip(-10, 1352270);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 321) {
	cm.addEquip(-10, 1352271);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 322) {
	cm.addEquip(-10, 1352272);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
	//모험가 : 궁수 끝
	//모험가 : 도적 시작
      } else if (selection == 900) { //나이트로드
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 410) {
	cm.addEquip(-10, 1352290);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 411) {
	cm.addEquip(-10, 1352291);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 412) {
	cm.addEquip(-10, 1352292);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 1000) { //섀도어
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 420) {
	cm.addEquip(-10, 1352280);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 421) {
	cm.addEquip(-10, 1352281);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 422) {
	cm.addEquip(-10, 1352282);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
	//모험가 : 도적 끝
	//모험가 : 해적 시작
      } else if (selection == 1100) { //캡틴
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 520) {
	cm.addEquip(-10, 1352910);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 521) {
	cm.addEquip(-10, 1352911);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 522) {
	cm.addEquip(-10, 1352912);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 1200) { //바이퍼
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 510) {
	cm.addEquip(-10, 1352900);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 511) {
	cm.addEquip(-10, 1352901);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 512) {
	cm.addEquip(-10, 1352902);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 1300) { //캐논슈터
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 530) {
	cm.addEquip(-10, 1352920);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 531) {
	cm.addEquip(-10, 1352921);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 532) {
	cm.addEquip(-10, 1352922);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 2100) { //키네시스
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() >= 14200) {
   	cm.addEquip(-10, 1353200);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 14210) {
	cm.addEquip(-10, 1353201);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 14211) {
	cm.addEquip(-10, 1353202);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 14212) {
	cm.addEquip(-10, 1353203);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs12#죄송하지만, #h #님 #r직업#k이 맞지 않거나 #r메소가 부족#k하신 것 같습니다, 다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n보조무기의 가격은 아래와 같습니다.\r\n#fs13#30레벨#k : #b50만#k 메소\r\n60레벨#k : #b100만#k 메소\r\n100레벨 : #b200만#k 메소#k");
	cm.dispose();
      } 
	//영웅 시작
      } else if (selection == 31) { //아란
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2110) {
	cm.addEquip(-10, 1352930);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2111) {
	cm.addEquip(1352931,-10);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 2112) {
	cm.addEquip(-10, 1352932);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      }
      } else if (selection == 32) { //에반
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2211) {
	cm.addEquip(-10, 1352940);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2214) {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.addEquip(-10, 1352941);
	cm.gainMeso(-1000000);
	cm.getPlayer().fakeRelog();
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000&& cm.getJob() >= 2217) {
	cm.addEquip(-10, 1352942);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 33) { //팬텀
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 2400) {
   	cm.addEquip(-10, 1352100);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2410) {
	cm.addEquip(-10, 1352101);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2411) {
	cm.addEquip(-10, 1352102);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 2412) {
	cm.addEquip(-10, 1352103);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 34) { //은월
	if (cm.getPlayer().getLevel() >= 20 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 2500) {
   	cm.addEquip(-10, 1353100);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2510) {
	cm.addEquip(-10, 1353101);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2511) {
	cm.addEquip(-10, 1353102);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 2512) {
	cm.addEquip(-10, 1353103);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 35) { //루미너스
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 2700) {
   	cm.addEquip(-10, 1352400);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose(); 
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2710) {
	cm.addEquip(-10, 1352401);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2711) {
	cm.addEquip(-10, 1352402);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose(); 
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 2712) {
	cm.addEquip(-10, 1352403);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 36) { //메르세데스
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 2300) {
   	cm.addEquip(-10, 1352000);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 2310) {
	cm.addEquip(-10, 1352001);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 2311) {
	cm.addEquip(-10, 1352002);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 2312) {
	cm.addEquip(-10, 1352003);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
	//시그너스 시작
      } else if (selection == 41) { //시그너스
        if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getPlayer().getJob() >= 1110 && cm.getPlayer().getJob() <= 1510 && cm.getPlayer().getJob() == 5110) {
	cm.addEquip(-10, 1352970);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getPlayer().getJob() >= 1111 && cm.getPlayer().getJob() <= 1511 && cm.getPlayer().getJob() <= 5111) {
	cm.addEquip(-10, 1352971);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getPlayer().getJob() >= 1112 && cm.getPlayer().getJob() <= 1512 && cm.getPlayer().getJob() <= 5112) {
	cm.addEquip(-10, 1352972);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 4100) { //미하일
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 5100) {
   	cm.addEquip(-10, 1098000);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 5110) {
	cm.addEquip(-10, 1098001);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 5111) {
	cm.addEquip(-10, 1098002);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 5112) {
	cm.addEquip(-10, 1098003);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs13#               #r보조무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#건의사항은 게임 내 Q&A게시판 또는 카페의 게시판에 건의 해주시길 바랍니다.\r\n\r\n#k#fs12#죄송하지만, #h #님 #r직업#k이 맞지 않거나 #r메소가 부족#k하신 것 같습니다, 다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n보조무기의 가격은 아래와 같습니다.\r\n#fs13#30레벨#k : #b50만#k 메소\r\n60레벨#k : #b100만#k 메소\r\n100레벨 : #b200만#k 메소#k");
	cm.dispose();
      } 
	//시그너스 끝
	//노바 시작
      } else if (selection == 51) { //카이저
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 6100) {
   	cm.addEquip(-10, 1352500);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 6110) {
	cm.addEquip(-10, 1352501);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 6111) {
	cm.addEquip(-10, 1352502);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 6112) {
	cm.addEquip(-10, 1352503);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 52) { //엔젤릭버스터
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getJob() == 6500) {
   	cm.addEquip(-10, 1352601);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose(); 
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 6510) {
	cm.addEquip(-10, 1352602);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 6511) {
	cm.addEquip(-10, 1352603);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 6512) {
	cm.addEquip(-10, 1352604);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
	//노바 끝
	//레지스탕스 시작
      } else if (selection == 61) { //데몬슬레이어 / 데몬어벤져
	if (cm.getPlayer().getLevel() >= 10 && cm.getPlayer().getLevel() < 30 && cm.getPlayer().getJob() >= 3100 && cm.getPlayer().getJob() <= 3101) {
   	cm.addEquip(-10, 1099001);
	cm.getPlayer().fakeRelog();
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getPlayer().getJob() >= 3110 && cm.getPlayer().getJob() <= 3120) {
	cm.addEquip(-10, 1099002);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getPlayer().getJob() >= 3111 && cm.getPlayer().getJob() <= 3121) {
	cm.addEquip(-10, 1099003);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getPlayer().getJob() >= 3112 && cm.getPlayer().getJob() <= 3122) {
	cm.addEquip(-10, 1099004);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 62) { //배틀메이지
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 3210) {
	cm.addEquip(-10, 1352950);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 3211) {
	cm.addEquip(-10, 1352951);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 3212) {
	cm.addEquip(-10, 1352952);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 63) { //와일드헌터
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 3310) {
	cm.addEquip(-10, 1352960);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 3311) {
	cm.addEquip(-10, 1352961);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 3312) {
	cm.addEquip(-10, 1352962);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 64) { //메카닉
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 3510) {
	cm.addEquip(-10, 1352701);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 3511) {
	cm.addEquip(-10, 1352702);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 3512) {
	cm.addEquip(-10, 1352703);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 65) { //제논
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 3610) {
	cm.addEquip(-10, 1353002);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 3611) {
	cm.addEquip(-10, 1353003);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 3612) {
	cm.addEquip(-10, 1353004);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } 
      } else if (selection == 66) { //블래스터
	if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getLevel() < 60 && cm.getMeso() >= 500000 && cm.getJob() == 3710) {
	cm.addEquip(-10, 1353401);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-500000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 60 && cm.getPlayer().getLevel() < 100 && cm.getMeso() >= 1000000 && cm.getJob() == 3711) {
	cm.addEquip(-10, 1353402);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-1000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
      } if (cm.getPlayer().getLevel() >= 100 && cm.getPlayer().getLevel() < 251 && cm.getMeso() >= 2000000 && cm.getJob() == 3712) {
	cm.addEquip(-10, 1353403);
	cm.getPlayer().fakeRelog();
	cm.gainMeso(-2000000);
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                  원하시는 무기를 선택해주세요.#k\r\n\r\n#fs13#               #r보조 무기#k 교환이 완료 되었습니다.");
	cm.dispose();
      } else {
	cm.sendOk("           #fn나눔고딕 Extrabold##fs17#"+별+" "+ServerConstants.serverName+" 보조 무기 상점 "+별+" \r\n#fs10##Cgray#                                원하시는 요청이 처리 불가합니다.#k\r\n\r\n#fs12#죄송하지만, #d직업#k 이 맞지 않거나 #r메소#k 가 부족하신 것 같습니다.\rn다시 확인 후 구입을 시도 해주시길 바랍니다.\r\n\r\n#b보조 무기#k 의 가격은 아래와 같습니다.\r\n\r\n#fs13#30 레벨 : #r50 만 메소#k #d(일부 직업 무료)#k\r\n60 레벨 : #r100만 메소#k\r\n100 레벨 : #r200 만 메소#k");
	cm.dispose();
			
		} 
	}
}