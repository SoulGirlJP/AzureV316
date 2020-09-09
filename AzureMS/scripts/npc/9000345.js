/*
 *
 * SpiritStyle 게@임 스@크립트 입니다.
 * NPC : 9090008 - 헤라
 * Function : 웨딩빌리지 워프
 * Customize : 아이템 옵션 수정
 * @Author : 연이(ljw5528)
 * @Modify : 하요(ifhayo)
 * 해당 스크립트의 주석은 삭제해도 좋으나
 * 제작자 및 수정자는 삭제하지 말아주세요.
 * 2014 ⓒ SpiritStyle
 *
 */

importPackage(Packages.client.items);
importPackage(Packages.server.items);




function start() {
  cm.sendSimple("환영합니다 #b#h ##k운영자님! 아이템의 옵션을 바꿔드리고 있습니다. 원하시는 작업을 선택해주세요.#b\r\n"
    +"#L1#아이템 옵션 바꾸기\r\n"
    +"#L2#아이템 추가옵션 생성하기\r\n"
    +"#L3#잠재능력 설정하기\r\n"
    +"#L4#에디셔널 잠재능력 설정하기\r\n")
}




 var sel;
 var status = -1;




// !시작
function action(mode, type, selection) { if(mode == 1) { status++; } else { status--; cm.dispose(); return;}


 if(status == 0) {


  Sort = selection == 1 ? 1 : selection == 2 ? 2 : selection == 3 ? 3 : 4
  Name = selection == 1 ? "아이템 능력치를 수정하" : selection == 2 ? "아이템 추가옵션을 생성하" : "아이템 잠재능력을 설정하"
  if(cm.getPlayer().getGMLevel()>5) {
  var str = ""+Name+"시려면 먼저 #b#h ##k운영자님이 보유하신 아이템 목록 중 대상 아이템을 선택해주세요.\r\n";
  for(var i=1;i < 100; i++) {
   if(cm.getEquip(i)){
   str += "#L"+i+"#"; str += "#i"+cm.getEquip(i).getItemId()+"#"; str += " #b#e#z"+cm.getEquip(i).getItemId()+"#"; str += "#n\r\n";
   }
  }
  cm.sendSimple(str);
 } else { cm.sendOk("운영자가 아닌 사람은 이용하실 수 없습니다. #h #님의 이용기록이 저장됩니다."); cm.dispose();}
 }


 else if(status==1){


   SelectedItemID = selection;
   ItemNum        = cm.getEquip(SelectedItemID).getItemId()
   ItemInfo       = cm.getEquip(SelectedItemID);
   Origin         = ItemInformation.getInstance().getEquipById(ItemInfo.getItemId());
   ShowItemInfo   = "#e아이템 : #i"+ItemNum+"# #b#z"+ItemNum+"##k#n\r\n#fn돋움체#";
   NormalOption   = "원하시는 값을 입력하세요 #r(기본값 : 32768)#k#n";
   AddOption      = "#r(기본값 : 101)#k#n";




  switch(Sort) {
   case 1:
   cm.sendGetNumber(""+ShowItemInfo+"\r\nSTR: "+NormalOption+"",0,0,32768);
   break;


   case 2:
   cm.sendGetNumber(""+ShowItemInfo+"\r\n보스 공격시 데미지 무시: "+AddOption+"",0,0,101);
   break;


   case 3:
   P_17 = "#fUI/UIWindow2/AdditionalOptionTooltip/rare#"
   P_18 = "#fUI/UIWindow2/AdditionalOptionTooltip/epic#"
   P_19 = "#fUI/UIWindow2/AdditionalOptionTooltip/unique#"
   P_20 = "#fUI/UIWindow2/AdditionalOptionTooltip/legendary#"


   cm.sendSimple(""+ShowItemInfo+"\r\n선택한 아이템의 잠재 등급을 선택해주세요.\r\n"
     +"#L17#"+P_17+"#b레어\r\n"
     +"#L18#"+P_18+"#d에픽\r\n"
     +"#L19#"+P_19+"#Cyellow#유니크\r\n"
     +"#L20#"+P_20+"#g레전드리");
   break;
  }


 } else if (status == 2) {


  switch(Sort) {
   case 1:
   cStr = selection == 32768 ? Origin.getStr() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "#kDEX: +"+NormalOption+"",0,0,32768);
   break;


   case 2:
   cBossDamage = selection == 101 ? Origin.getBossDamage() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#g#e"
     + "보스 공격시 데미지: +"+cBossDamage+"%\r\n"
     + "#k#n몬스터 방어력 무시: "+AddOption+"",0,0,101);
   break;


   case 3:
   cState = selection
   ShowState = cState == 17 ? "#b레어" : cState == 18 ? "#d에픽" : cState == 19 ? "#Cyellow#유니크" : "#g레전드리"
   PictureiF = cState == 17 ? "#fUI/UIWindow2/AdditionalOptionTooltip/rare#" :
     cState == 18 ? "#fUI/UIWindow2/AdditionalOptionTooltip/epic#" :
     cState == 19 ? "#fUI/UIWindow2/AdditionalOptionTooltip/unique#" : "#fUI/UIWindow2/AdditionalOptionTooltip/legendary#"


   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "잠재등급 : "+PictureiF+""+ShowState+"#k\r\n"
     + "#k첫 번째 잠재능력을 입력해주세요.",0,0,42556);
   break;
  }




 } else if (status == 3) {


  switch(Sort) {
   case 1:
   cDex = selection == 32768 ? Origin.getDex() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "DEX: +"+cDex+"\r\n"
     + "#kINT: "+NormalOption+"",0,0,32768);
   break;


   case 2:
   cIgnoreWdef = selection == 101 ? Origin.getIgnoreWdef() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#g#e"
     + "보스 공격시 데미지: +"+cBossDamage+"%\r\n"
     + "몬스터 방어력 무시: +"+cIgnoreWdef+"%\r\n"
     + "#k#n총 데미지: "+AddOption+"",0,0,101);
   break;


   case 3:
   cPotential1 = selection;
   setPotential(cPotential1);
   nPotential1 = PotentialName
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "잠재등급 : "+PictureiF+""+ShowState+"#Cgray#\r\n"
     + "첫 번째 잠재능력 : "+nPotential1+"\r\n"
     + "#k두 번째 잠재능력을 입력해주세요.", 0,0,60002);
   break;
  }   


 } else if (status == 4) {


  switch(Sort) {
   case 1:
   cInt = selection == 32768 ? Origin.getInt() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "DEX: +"+cDex+"\r\n"
     + "INT: +"+cInt+"\r\n"
     + "#kLUK: "+NormalOption+"",0,0,32768);
   break;


   case 2:
   cAllDamageP = selection == 101 ? Origin.getAllDamageP() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#g#e"
     + "보스 공격시 데미지: +"+cBossDamage+"%\r\n"
     + "몬스터 방어력 무시: +"+cIgnoreWdef+"%\r\n"
     + "총 데미지: +"+cAllDamageP+"%\r\n"
     + "#k#n올스탯: "+AddOption+"",0,0,101);
   break;


   case 3:
   cPotential2 = selection;
   setPotential(cPotential2);
   nPotential2 = PotentialName
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "잠재등급 : "+PictureiF+""+ShowState+"#Cgray#\r\n"
     + "첫 번째 잠재능력 : "+nPotential1+"\r\n"
     + "두 번째 잠재능력 : "+nPotential2+"\r\n"
     + "#k세 번째 잠재능력을 입력해주세요.", 0,0,60002);
   break;
  }   




 } else if (status == 5) {
  switch(Sort) {
   case 1:
   cLuk = selection == 32768 ? Origin.getInt() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "DEX: +"+cDex+"\r\n"
     + "INT: +"+cInt+"\r\n"
     + "LUK: +"+cLuk+"\r\n"
     + "#kMaxHP  : "+NormalOption+"",0,0,32768);
   break;


   case 2:
   cAllStatP = selection == 101 ? Origin.getAllStatP() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#g#e"
     + "보스 공격시 데미지: +"+cBossDamage+"%\r\n"
     + "몬스터 방어력 무시: +"+cIgnoreWdef+"%\r\n"
     + "총 데미지: +"+cAllDamageP+"%\r\n"
     + "올스탯: +"+cAllStatP+"%\r\n"
     + "#k#n착용 레벨 감소: "+AddOption+"",0,0,101);
   break;


   case 3:
   cPotential3 = selection;
   setPotential(cPotential3);
   nPotential3 = PotentialName
   cm.sendYesNo(""+ShowItemInfo+"\r\n#Cgray#"
     + "잠재등급 : "+PictureiF+""+ShowState+"#Cgray#\r\n"
     + "첫 번째 잠재능력 : "+nPotential1+"\r\n"
     + "두 번째 잠재능력 : "+nPotential2+"\r\n"
     + "세 번째 잠재능력 : "+nPotential3+"\r\n"
     + "\r\n\r\n#e#r위 아이템을 정말로 만드시겠습니까?");
   break;
  }   


   
 } else if (status == 6) {
  switch(Sort) {
   case 1:
   cMaxHp = selection == 32768 ? Origin.getHp() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "DEX: +"+cDex+"\r\n"
     + "INT: +"+cInt+"\r\n"
     + "LUK: +"+cLuk+"\r\n"
     + "MaxHP: +"+cMaxHp+"\r\n"
     + "#kMaxMP: "+NormalOption+"",0,0,32768);
   break;


   case 2:
   cDownLevel = selection == 101 ? Origin.getDownLevel() : selection;
   cm.sendYesNo(""+ShowItemInfo+"\r\n#g#e"
     + "보스 공격시 데미지: +"+cBossDamage+"%\r\n"
     + "몬스터 방어력 무시: +"+cIgnoreWdef+"%\r\n"
     + "총 데미지: +"+cAllDamageP+"%\r\n"
     + "올스탯: +"+cAllStatP+"%\r\n"
     + "착용 레벨 감소: -"+cDownLevel+"\r\n"
     + "\r\n\r\n#e#r위 아이템을 정말로 만드시겠습니까?");
   break;


   case 3:
   ItemInfo.setState(cState);
   ItemInfo.setPotential1(cPotential1);
   ItemInfo.setPotential2(cPotential2);
   ItemInfo.setPotential3(cPotential3);
   cm.getChar().saveToDB(false,false);
   cm.fakeRelog();
   cm.updateChar();
   cm.dispose();
   break;
  }   




 } else if (status == 7) {


  switch(Sort) {
   case 1:
   cMaxMp = selection == 32768 ? Origin.getMp() : selection;
   cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
     + "STR: +"+cStr+"\r\n"
     + "DEX: +"+cDex+"\r\n"
     + "INT: +"+cInt+"\r\n"
     + "LUK: +"+cLuk+"\r\n"
     + "MaxHP: +"+cMaxHp+"\r\n"
     + "MaxMP: +"+cMaxMp+"\r\n"
     + "#k공격력: "+NormalOption+"",0,0,32768);
   break;


   case 2:
   ItemInfo.setBossDamage(cBossDamage);
   ItemInfo.setIgnoreWdef(cIgnoreWdef);
   ItemInfo.setAllDamageP(cAllDamageP);
   ItemInfo.setAllStatP(cAllStatP);
   ItemInfo.setDownLevel(cDownLevel);
   cm.getChar().saveToDB(false,false);
   cm.fakeRelog();
   cm.updateChar();
   cm.dispose();
   break;


   case 3:
   break;
  }


 } else if (status == 8) {
  cWatk = selection == 32768 ? Origin.getWatk() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "#k마력: "+NormalOption+"",0,0,32768);


 } else if (status == 9) {
  cMatk = selection == 32768 ? Origin.getMatk() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "#k물리방어력: "+NormalOption+"",0,0,32768);


 } else if (status == 10) {
  cWdef = selection == 32768 ? Origin.getWdef() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: "+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "#k마법방어력: +"+NormalOption+"",0,0,32768);


 } else if (status == 11) {
  cMdef = selection == 32768 ? Origin.getMdef() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "#k명중치: "+NormalOption+"",0,0,32768);


 } else if (status == 12) {
  cAcc = selection == 32768 ? Origin.getAcc() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "명중치: +"+cAcc+"\r\n"
   + "#k회피치: "+NormalOption+"",0,0,32768);


 } else if (status == 13) {
  cAvoid = selection == 32768 ? Origin.getAvoid() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "명중치: +"+cAcc+"\r\n"
   + "회피치: +"+cAvoid+"\r\n"
   + "#k이동속도: "+NormalOption+"",0,0,32768);


 } else if (status == 14) {
  cSpeed = selection == 32768 ? Origin.getSpeed() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "명중치: +"+cAcc+"\r\n"
   + "회피치: +"+cAvoid+"\r\n"
   + "이동속도: +"+cSpeed+"\r\n"
   + "#k점프력: "+NormalOption+"",0,0,32768);


 } else if (status == 15) {
  cJump = selection == 32768 ? Origin.getJump() : selection;
  cm.sendGetNumber(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "명중치: +"+cAcc+"\r\n"
   + "회피치: +"+cAvoid+"\r\n"
   + "이동속도: +"+cSpeed+"\r\n"
   + "점프력: "+cJump+"\r\n"
   + "#k업그레이드 가능 횟수: #r(기본값 : 126)#k#n",0,1,126);


 } else if (status == 16) {
  cUpgradeSlots = selection == 126 ? Origin.getUpgradeSlots() : selection;
  cm.sendYesNo(""+ShowItemInfo+"\r\n#Cgray#"
   + "STR: +"+cStr+"\r\n"
   + "DEX: +"+cDex+"\r\n"
   + "INT: +"+cInt+"\r\n"
   + "LUK: +"+cLuk+"\r\n"
   + "MaxHP: +"+cMaxHp+"\r\n"
   + "MaxMP: +"+cMaxMp+"\r\n"
   + "공격력: +"+cWatk+"\r\n"
   + "마력: +"+cMatk+"\r\n"
   + "물리방어력: +"+cWdef+"\r\n"
   + "마법방어력: +"+cMdef+"\r\n"
   + "명중치: +"+cAcc+"\r\n"
   + "회피치: +"+cAvoid+"\r\n"
   + "이동속도: +"+cSpeed+"\r\n"
   + "점프력: "+cSpeed+"\r\n"
   + "업그레이드 가능 횟수: "+cUpgradeSlots+"\r\n"
   + "\r\n\r\n#e#r위 아이템을 정말로 만드시겠습니까?");


 } else if (status == 17) {
  ItemInfo.setStr(cStr);
  ItemInfo.setDex(cDex);
  ItemInfo.setInt(cInt);
  ItemInfo.setLuk(cLuk);
  ItemInfo.setHp(cMaxHp);
  ItemInfo.setMp(cMaxMp);
  ItemInfo.setWatk(cWatk);
  ItemInfo.setMatk(cMatk);
  ItemInfo.setWdef(cWdef);
  ItemInfo.setMdef(cMdef);
  ItemInfo.setAcc(cAcc);
  ItemInfo.setAvoid(cAvoid);
  ItemInfo.setSpeed(cSpeed);
  ItemInfo.setJump(cJump);
  ItemInfo.setUpgradeSlots(cUpgradeSlots);
  cm.getChar().saveToDB(false,false);
  cm.fakeRelog();
  cm.updateChar();
  cm.dispose();


 }
}


function setPotential(selection) {
 LV = Origin.getItemLevel();


 if(LV > 0) {
 PotentialName =
 selection == 10041 ? "힘 1%" :
 selection == 20041 ? "힘 2%" :
 selection == 30041 ? "힘 3%" :
 selection == 40041 ? "힘 6%" :
 selection == 10042 ? "덱스 1%" :
 selection == 20042 ? "덱스 2%" :
 selection == 30042 ? "덱스 3%" :
 selection == 40042 ? "덱스 6%" :
 selection == 10043 ? "인트 1%" :
 selection == 20043 ? "인트 2%" :
 selection == 30043 ? "인트 3%" :
 selection == 40043 ? "인트 6%" :
 selection == 10044 ? "럭 1%" :
 selection == 20044 ? "럭 2%" :
 selection == 30044 ? "럭 3%" :
 selection == 40044 ? "럭 6%" :
 selection == 20086 ? "올스텟 1%" :
 selection == 30086 ? "올스텟 2%" :
 selection == 40086 ? "올스텟 3%" : 
 selection == 10045 ? "MaxHp 1%" :
 selection == 20045 ? "MaxHp 2%" :
 selection == 30045 ? "MaxHp 3%" :
 selection == 40045 ? "MaxHp 6%" :
 selection == 10046 ? "MaxMp 1%" :
 selection == 20046 ? "MaxMp 2%" :
 selection == 30046 ? "MaxMp 3%" :
 selection == 40046 ? "MaxMp 6%" :
 selection == 10047 ? "명중치 1%" :
 selection == 20047 ? "명중치 2%" :
 selection == 30047 ? "명중치 3%" :
 selection == 40047 ? "명중치 6%" :
 selection == 10048 ? "회피치 1%" :
 selection == 20048 ? "회피치 2%" :
 selection == 30048 ? "회피치 3%" :
 selection == 40048 ? "회피치 6%" :
 selection == 10051 ? "공격력 1%" :
 selection == 20051 ? "공격력 2%" :
 selection == 30051 ? "공격력 3%" :
 selection == 40051 ? "공격력 6%" :
 selection == 10052 ? "마력 1%" :
 selection == 20052 ? "마력 2%" :
 selection == 30052 ? "마력 3%" :
 selection == 40052 ? "마력 6%" :
 selection == 10070 ? "총 데미지 1%" :
 selection == 20070 ? "총 데미지 2%" :
 selection == 30070 ? "총 데미지 3%" :
 selection == 40070 ? "총 데미지 6%" :
 selection == 10053 ? "물리방어력 1%" :
 selection == 20053 ? "물리방어력 2%" :
 selection == 30053 ? "물리방어력 3%" :
 selection == 40053 ? "물리방어력 6%" :
 selection == 10054 ? "마법방어력 1%" :
 selection == 20054 ? "마법방어력 2%" :
 selection == 30054 ? "마법방어력 3%" :
 selection == 40054 ? "마법방어력 6%" :
 selection == 40650 ? "메소 획득량 10%" :
 selection == 40656 ? "아이템 획득확률 10%" :
 selection == 10055 ? "크리티컬 확률 1%" :
 selection == 20055 ? "크리티컬 확률 2%" :
 selection == 30055 ? "크리티컬 확률 3%" :
 selection == 40055 ? "크리티컬 확률 6%" :
 selection == 40056 ? "크리티컬 최소 데미지 3%" :
 selection == 40057 ? "크리티컬 최대 데미지 3%" : 
 selection == 40501 ? "모든 스킬의 MP 소모 -5%" :
 selection == 40502 ? "모든 스킬의 MP 소모 -10%" :
 selection == 60001 ? "총 데미지 20%" :
 selection == 40081 ? "올스텟 +12" :
 selection == 30106 ? "모든 스킬레벨 +1" :
 selection == 40106 ? "모든 스킬레벨 +2" :
 selection == 40107 ? "모든 스킬레벨 +3" :
 selection == 40111 ? "모든 속성 내성 10%" :
 selection == 40116 ? "상태 이상 내성 10%" :
 selection == 30291 ? "공격 시 몬스터의 방어율 무시 30%" : 
 selection == 40291 ? "공격 시 몬스터의 방어율 무시 35%" :
 selection == 40292 ? "공격 시 몬스터의 방어율 무시 40%" :
 selection == 30356 ? "피격 시 5% 확률로 데미지 20% 무시" :
 selection == 40356 ? "피격 시 10% 확률로 데미지 20% 무시" :
 selection == 40357 ? "피격 시 5% 확률로 데미지 40% 무시" :
 selection == 20366 ? "피격 후 무적시간 1초" :
 selection == 30366 ? "피격 후 무적시간 2초" :
 selection == 40366 ? "피격 후 무적시간 3초" : 
 selection == 40556 ? "모든 스킬의 재사용 대기시간 -1초" :
 selection == 40557 ? "모든 스킬의 재사용 대기시간 -2초" :
 selection == 42556 ? "모든 스킬의 재사용 대기시간 -1초" : ""
 return LV;




 }
 
 if (LV > 24) {
 PotentialName = 
 selection == 40056 ? "크리티컬 최소 데미지 6%" :
 selection == 40057 ? "크리티컬 최대 데미지 6%" : ""
 return LV;






 }


 if (LV > 44) {
 PotentialName =
 selection == 10041 ? "힘 2%" :
 selection == 20041 ? "힘 4%" :
 selection == 30041 ? "힘 6%" :
 selection == 40041 ? "힘 9%" :
 selection == 10042 ? "덱스 2%" :
 selection == 20042 ? "덱스 4%" :
 selection == 30042 ? "덱스 6%" :
 selection == 40042 ? "덱스 9%" :
 selection == 10043 ? "인트 2%" :
 selection == 20043 ? "인트 4%" :
 selection == 30043 ? "인트 6%" :
 selection == 40043 ? "인트 9%" :
 selection == 10044 ? "럭 2%" :
 selection == 20044 ? "럭 4%" :
 selection == 30044 ? "럭 6%" :
 selection == 40044 ? "럭 9%" :
 selection == 20086 ? "올스텟 2%" :
 selection == 30086 ? "올스텟 4%" :
 selection == 40086 ? "올스텟 6%" : 
 selection == 10045 ? "MaxHp 2%" :
 selection == 20045 ? "MaxHp 4%" :
 selection == 30045 ? "MaxHp 6%" :
 selection == 40045 ? "MaxHp 9%" :
 selection == 10046 ? "MaxMp 2%" :
 selection == 20046 ? "MaxMp 4%" :
 selection == 30046 ? "MaxMp 6%" :
 selection == 40046 ? "MaxMp 9%" :
 selection == 10047 ? "명중치 2%" :
 selection == 20047 ? "명중치 4%" :
 selection == 30047 ? "명중치 6%" :
 selection == 40047 ? "명중치 9%" :
 selection == 10048 ? "회피치 2%" :
 selection == 20048 ? "회피치 4%" :
 selection == 30048 ? "회피치 6%" :
 selection == 40048 ? "회피치 9%" :
 selection == 10051 ? "공격력 2%" :
 selection == 20051 ? "공격력 4%" :
 selection == 30051 ? "공격력 6%" :
 selection == 40051 ? "공격력 9%" :
 selection == 10052 ? "마력 2%" :
 selection == 20052 ? "마력 4%" :
 selection == 30052 ? "마력 6%" :
 selection == 40052 ? "마력 9%" :
 selection == 10070 ? "총 데미지 2%" :
 selection == 20070 ? "총 데미지 4%" :
 selection == 30070 ? "총 데미지 6%" :
 selection == 40070 ? "총 데미지 9%" :
 selection == 10053 ? "물리방어력 2%" :
 selection == 20053 ? "물리방어력 4%" :
 selection == 30053 ? "물리방어력 6%" :
 selection == 40053 ? "물리방어력 9%" :
 selection == 10054 ? "마법방어력 2%" :
 selection == 20054 ? "마법방어력 4%" :
 selection == 30054 ? "마법방어력 6%" :
 selection == 40054 ? "마법방어력 9%" :
 selection == 40650 ? "메소 획득량 15%" :
 selection == 40656 ? "아이템 획득확률 15%" :
 selection == 10055 ? "크리티컬 확률 2%" :
 selection == 20055 ? "크리티컬 확률 4%" :
 selection == 30055 ? "크리티컬 확률 6%" :
 selection == 40055 ? "크리티컬 확률 9%" : ""
 selection == 40056 ? "크리티컬 최소 데미지 9%" :
 selection == 40057 ? "크리티컬 최대 데미지 9%" : ""
 return LV;
 }


 if (LV > 54) {
 PotentailName =
 selection == 40501 ? "모든 스킬의 MP 소모 -10%" :
 selection == 40502 ? "모든 스킬의 MP 소모 -20%" : ""
 return LV;


 }


 if (LV > 64) {
 PotentailName =
 selection == 40056 ? "크리티컬 최소 데미지 12%" :
 selection == 40057 ? "크리티컬 최대 데미지 12%" : ""
 return LV;


 }
 if (LV > 75) {
 PotentialName =
 selection == 10041 ? "힘 3%" :
 selection == 20041 ? "힘 6%" :
 selection == 30041 ? "힘 9%" :
 selection == 40041 ? "힘 12%":
 selection == 10042 ? "덱스 3%":
 selection == 20042 ? "덱스 6%":
 selection == 30042 ? "덱스 9%":
 selection == 40042 ? "덱스 12%":
 selection == 10043 ? "인트 3%":
 selection == 20043 ? "인트 6%":
 selection == 30043 ? "인트 9%":
 selection == 40043 ? "인트 12%":
 selection == 10044 ? "럭 3%":
 selection == 20044 ? "럭 6%":
 selection == 30044 ? "럭 9%":
 selection == 40044 ? "럭 12%":
 selection == 20086 ? "올스텟 3%":
 selection == 30086 ? "올스텟 6%":
 selection == 40086 ? "올스텟 9%": 
 selection == 10045 ? "MaxHp 3%":
 selection == 20045 ? "MaxHp 6%":
 selection == 30045 ? "MaxHp 9%":
 selection == 40045 ? "MaxHp 12%":
 selection == 10046 ? "MaxMp 3%":
 selection == 20046 ? "MaxMp 6%":
 selection == 30046 ? "MaxMp 9%":
 selection == 40046 ? "MaxMp 12%":
 selection == 10047 ? "명중치 3%":
 selection == 20047 ? "명중치 6%":
 selection == 30047 ? "명중치 9%":
 selection == 40047 ? "명중치 12%":
 selection == 10048 ? "회피치 3%":
 selection == 20048 ? "회피치 6%":
 selection == 30048 ? "회피치 9%":
 selection == 40048 ? "회피치 12%":
 selection == 10051 ? "공격력 3%":
 selection == 20051 ? "공격력 6%":
 selection == 30051 ? "공격력 9%":
 selection == 40051 ? "공격력 12%":
 selection == 10052 ? "마력 3%":
 selection == 20052 ? "마력 6%":
 selection == 30052 ? "마력 9%":
 selection == 40052 ? "마력 12%":
 selection == 10070 ? "총 데미지 3%":
 selection == 20070 ? "총 데미지 6%":
 selection == 30070 ? "총 데미지 9%":
 selection == 40070 ? "총 데미지 12%":
 selection == 10053 ? "물리방어력 3%":
 selection == 20053 ? "물리방어력 6%":
 selection == 30053 ? "물리방어력 9%":
 selection == 40053 ? "물리방어력 12%":
 selection == 10054 ? "마법방어력 3%":
 selection == 20054 ? "마법방어력 6%":
 selection == 30054 ? "마법방어력 9%":
 selection == 40054 ? "마법방어력 12%":
 selection == 40650 ? "메소 획득량 20%":
 selection == 40656 ? "아이템 획득확률 20%":
 selection == 10055 ? "크리티컬 확률 3%":
 selection == 20055 ? "크리티컬 확률 6%":
 selection == 30055 ? "크리티컬 확률 9%":
 selection == 40055 ? "크리티컬 확률 12%": ""
 return LV;
 }


 if (LV > 84) {
 PotentailName =
 selection == 40056 ? "크리티컬 최소 데미지 15%" :
 selection == 40057 ? "크리티컬 최대 데미지 15%" : ""
 return LV;






 } else {
 return;
 }


}


