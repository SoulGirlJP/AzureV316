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
  
        var choose = "#fnSharing Ghotic Extrabold##fs13# Shandi on charge of drawing for AzureMS !#k";
           choose += "\r\n#L1##fs 13##i3010097##e#b  Random chair draw#d ( Let's get a pretty chair!)#k";
           //choose += "\r\n#L5##fs 13##i2432291##e#b  Overseas Cash Drawer#d (Even more beautiful overseas cash?)#n#l#k";
       if (cm.getPlayer().hasGmLevel(6)){
        choose += "#fnSharing Ghotic Extrabold##fs15#\r\n\r\n\r\n#k#n#e¡¶¡¶¡º GM System ¡»¡·¡·#k#n #r(Moderator Menu)#k#n\r\n";
        choose += "#r#L5#"+time+" Sponsored Production#k\r\n";
        choose += "#b#L6#"+time+" Recovery production#k\r\n";
        choose += "#g#L15#"+time+" Gift#k\r\n";
        choose += "#d#L7#"+time+" Operator map#k\r\n";
        choose += "#d#L8#"+time+" Summon All#K\r\n";
        choose += "#d#L9#"+time+" Unset Mapping#K\r\n";        
        choose += "#d#L10#"+time+" Termination#k\r\n";
        choose += "#d#L11#"+time+" Backup NPC#k\r\n"; 
        

       
       
  }
        cm.sendSimple(choose);

    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 0) {
            cm.openNpc (1022107);
 } else if (s == 1) {
  cm.openNpc (9110010);
 } else if (s == 2) {
  cm.openNpc (9001174);
 } else if (s == 3) {
  cm.openNpc (9031001);
 } else if (s == 4) {
  cm.openNpc (1064003);
 } else if (s == 5) {
  cm.openNpc (9001132);
 } else if (s == 6) {
  cm.openNpc (9000153);
 } else if (s == 7) {
  cm.openNpc (9300000);
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
        }
    }
}
