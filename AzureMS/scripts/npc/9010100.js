importPackage(Packages.tools.RandomStream);

var status = -1;
var time = "#fUI/UIToolTip/Item/Equip/Star/Star#"
function start() {
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
  
        var choose = "   #i3120000##fnSharing Ghotic Extrabold##fs13# Versatile in charge of AzureMS YO MAN!#i3120000##n#k\r\n#fs11##Cgray#                   Have fun at AzureMS.#k";
           choose += "\r\n#fs12#                                  #dver. 1.2. 316"
           choose += "\r\n                                   PLAY ! AzureMS!#k"
           choose += "\r\n#fnSharing Ghotic Extrabold##fs13# #bSponsor Point#k : " + cm.getPlayer().getRC()+ "#d P#n#k"
           choose += "\r\n#fnSharing Ghotic Extrabold##fs13# #bAdditional damage#k : " + cm.getPlayer().getAddDamage() + " #d#n#k"
           choose += "\r\n#fnSharing Ghotic Extrabold##fs13# #bDamage1#k : " + cm.getPlayer().getDamageHit() + "%#d#n#k"
           choose += "#fnSharing Ghotic Extrabold##fs13# #b Damage2#k : " + cm.getPlayer().getDamageHit2() + "%#d#n#k"
           choose += "\r\n#fnSharing Ghotic Extrabold##fs13# #bRebirth#k : " + cm.getPlayer().getReborns() +" #n#k"
           choose += "\r\n#L1##fs 13##i5190013##e#r  Shop Systems#d ( Equipment, Consumption, Sponsorship, Drawing )#k";
           choose += "\r\n#L3##fs 13##s4001005##e#r  Warp System#d ( Hunting, boss, fishing  )#n#l#k";
           choose += "\r\n#L2##fs 13##i2022617##e#r  Rebirth System#d ( Rebirth, Rebirth System )#n#l#k";
           choose += "\r\n#L6##fs 13##s1121000##e#r  Administration system#d ( AzureMS administration, title quest )#n#l#k";
           choose += "\r\n#L5##fs 13##i2023027##e#r  AzureMS Content#d ( Content, guild, gambling, etc. )#n#l#k";
           choose += "\r\n#L7##fs 13##s80001445##e#r  Sponsorship system#d ( AzureMS Sponsorship )#n#l#k";
           choose += "\r\n#L4##fs 13##s10001003##e#r  Strengthening system#d ( Strengthen, chudem, side )#n#l#k";
       if (cm.getPlayer().hasGmLevel(6)){
        choose += "#fnSharing Ghotic Extrabold##fs15#\r\n\r\n\r\n#k#n#e¡¶¡¶¡º GM System ¡»¡·¡·#k#n #r(Menu only visible to moderators)#k#n\r\n";
        choose += "#r#L30#"+time+" Sponsored Production#k\r\n";
        choose += "#b#L31#"+time+" Recovery production#k\r\n";
        choose += "#g#L15#"+time+" Gift#k\r\n";
        choose += "#d#L7#"+time+" Operator map#k\r\n";
        choose += "#d#L8#"+time+" Summon All#K\r\n";
        choose += "#d#L9#"+time+" Unset Mapping#K\r\n";        
        choose += "#d#L10#"+time+" Ending#k\r\n";
        choose += "#d#L11#"+time+" Total message#k\r\n"; 
        

       
       
  }
        cm.sendSimple(choose);

    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 0) {
            cm.openNpc (1022107);
 } else if (s == 1) {
  cm.openNpc (9001144);
 } else if (s == 2) {
  cm.openNpc (1404015);
 } else if (s == 3) {
  cm.openNpc (1530706);
 } else if (s == 4) {
  cm.openNpc (1064003);
 } else if (s == 5) {
  cm.openNpc (1540106);
 } else if (s == 6) {
  cm.openNpc (2120032);
 } else if (s == 7) {
  cm.openNpc (1540010);
 } else if (s == 8) {
  cm.openNpc (9010044);
 } else if (s == 9) {
  cm.openNpc (9072302);
 } else if (s == 10) {
  cm.openNpc (2144020);
 } else if (s == 11) {
  cm.openNpc (9010017);
 } else if (s == 12) {
  cm.openNpc (3003200);
 } else if (s == 13) {
  cm.openNpc (9000271);
 } else if (s == 14) {
  cm.openNpc (3003200);
 } else if (s == 15) {
  cm.openNpc (9310001);
 } else if (s == 30) {
  cm.openNpc (9090008);
 } else if (s == 31) {
  cm.openNpc (9900008);
        }
    }
}
