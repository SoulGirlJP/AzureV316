importPackage(Packages.constants);
importPackage(Packages.packet.creators);

var status = -1;

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
 sT = cm.getPlayer().getKeyValue("damageskin");
 showN = sT ==  1 ? "Digitalize" :
  sT ==  2 ? "Critias" :
  sT ==  3 ? "Party quests" :
  sT ==  4 ? "Impact" :
  sT ==  5 ? "Sweet traditional han" :
  sT ==  6 ? "Club Henesys" :
  sT ==  7 ? "Merry Christmas" :
  sT ==  8 ? "Snow blossom" :
  sT ==  9 ? "Alicia" :
  sT == 10 ? "Dorothy" :
  sT == 11 ? "Keyboard warrior" :
  sT == 12 ? "A breeze" :
  sT == 13 ? "Solo troops" : 
  sT == 14 ? "Reminence" :
  sT == 15 ? "Orange Mushroom" :
  sT == 16 ? "Crown" : "";

 sS = cm.getPlayer().getKeyValue2("DS_SAVE");
 showS = sS ==  1 ? "Digitalize" :
  sS ==  2 ? "Critias" :
  sS ==  3 ? "Party quests" :
  sS ==  4 ? "Impact" :
  sS ==  5 ? "Sweet traditional han" :
  sS ==  6 ? "Club Henesys" :
  sS ==  7 ? "Merry Christmas" :
  sS ==  8 ? "Snow blossom" :
  sS ==  9 ? "Alicia" :
  sS == 10 ? "Dorothy" :
  sS == 11 ? "Keyboard warrior" :
  sS == 12 ? "A breeze" :
  sS == 13 ? "Solo troops" :
  sS == 14 ? "Reminence" :
  sS == 15 ? "Orange Mushroom" :
  sS == 16 ? "Crown" : "";

 xX = sS != 0 ? "silver" : "this";
 yY = sS != 0 ? " Damage skin#k is." : "#kThere is not.";
 Xx = sT != 0 ? "silver" : "this";
 Yy = sT != 0 ? " Damage skin#kis." : "#kThere is not.";

  if(sT == null) { cm.getPlayer().setKeyValue("damageskin", "0") }
  if(sS == null) { cm.getPlayer().setKeyValue2("DS_SAVE", "0") }
 var text = "Please select your desired function.\r\n\r\n";
 text += "#L1##b Save the current damage skin.#k\r\n";
 text += "¡¡ - Currently applied damage skin"+Xx+" #b"+showN+""+Yy+"\r\n\r\n";
 text += "#L2##b Apply the saved damage skin.#k\r\n";
 text += "¡¡ - Currently saved damage skin"+xX+" #b"+showS+""+yY+"";
 cm.sendSimple(text);
  
 } else if (status == 1) {
  Sort = selection == 1 ? 1 : 2
  switch(selection) {
   case 1:
   sT != 0 ? cm.sendYesNo("Really #b"+showN+" Damage skin#k Do you want to save it? The existing damage skin information will be deleted.") :
      cm.sendNext("Default damage skins cannot be saved.");
   return;

   case 2:
   sS != 0 ? cm.sendYesNo("You're trying to apply a saved damage skin!\r\n#bCurrently applied "+showN+" Damage skin#k instead #b"+showS+" Damage skin#kDo you want to apply?") :
      cm.sendNext("There is no saved damage skin.");
   return;
  }
 } else if (status == 2) {
  switch(Sort) {
   case 1:
   if(sT != 0) {
   saveDS(sT);
   cm.sendNext("#b"+showN+" Damage skin#k Has been saved.");
   }
   cm.dispose();
   return;

   case 2:
   if(sS != 0) {
   ReloadDS();
   cm.sendNext("#b"+showS+" Damage skin#k Has been applied.");
   }
   cm.dispose();
   return;
  }
 }
}
}










function saveDS(i) {
 cm.getPlayer().setKeyValue2("DS_SAVE", i);
}

function ReloadDS() {
 switch(sS) {
  case 0:
  cm.getPlayer().setKeyValue("damageskin", 0);
  return;

  case 1:
  cm.getPlayer().setKeyValue("damageskin", 1);
  return;

  case 2:
  cm.getPlayer().setKeyValue("damageskin", 2);
  return;

  case 3:
  cm.getPlayer().setKeyValue("damageskin", 3);
  return;

  case 4:
  cm.getPlayer().setKeyValue("damageskin", 4);
  return;

  case 5:
  cm.getPlayer().setKeyValue("damageskin", 5);
  return;

  case 6:
  cm.getPlayer().setKeyValue("damageskin", 6);
  return;

  case 7:
  cm.getPlayer().setKeyValue("damageskin", 7);
  return;

  case 8:
  cm.getPlayer().setKeyValue("damageskin", 8);
  return;

  case 9:
  cm.getPlayer().setKeyValue("damageskin", 9);
  return;

  case 10:
  cm.getPlayer().setKeyValue("damageskin", 10);
  return;

  case 11:
  cm.getPlayer().setKeyValue("damageskin", 11);
  return;

  case 12:
  cm.getPlayer().setKeyValue("damageskin", 12);
  return;

  case 13:
  cm.getPlayer().setKeyValue("damageskin", 13);
  return;

  case 14:
  cm.getPlayer().setKeyValue("damageskin", 14);
  return;

  case 15:
  cm.getPlayer().setKeyValue("damageskin", 15);
  return;

  case 16:
  cm.getPlayer().setKeyValue("damageskin", 16);
  return;

 }
}

