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
  
        var choose = "#fnSharing Ghotic Extrabold##fs13# I'm Oko, who is in charge of the spring online reinforcement system #k";
           choose += "                     #fnSharing Ghotic Extrabold##fs13# #bAdditional damage#k : " + cm.getPlayer().getAddDamage() + "#d#n#k"
           choose += "\r\n#L20##fs 13##s1100000##e#r Chudem system#d ( Additional Damage UP )#n#l#k";
           //choose += "\r\n#L2##fs 13##s3101004##e#r  Strengthening system#d ( Weapon enhancement system )#n#l#k";
           //choose += "\r\n#L3##fs 13##s3120018##e#r  High statUP#d ( Hyperstat UP )#n#l#k";
           //choose += "\r\n#L21##fs 13##s1121052##e#r  Super jumpUP#d ( Double jump job )#n#l#k";
             //choose += "\r\n#L50##fs 13##s1121054##e#r  Job change#d ( My own side! )#n#l#k";
           //choose += "\r\n#L6##fs 13##s1100000##e#r  Additional statsUP#d ( Stat Stats UP )#n#l#k";
           //choose += "\r\n#L4##fs 13##s1121052##e#r  Extreme system#d ( Extreme Transformation Advancement )#n#l#k";
           //choose += "\r\n#L0##fs 13##s1121054##e#r  Subsystem#d ( Sub occupation )#n#l#k";

       if (cm.getPlayer().hasGmLevel(6)){
        choose += "#fnSharing Ghotic Extrabold##fs15#\r\n\r\n\r\n#k#n#e《《『 지 엠 시 스 템 』》》#k#n #r(운영자만 보이는 메뉴)#k#n\r\n";
        choose += "#r#L5#"+time+" 후원제작#k";
        choose += "#b#L6#"+time+" 복구제작#k";
        choose += "#g#L15#"+time+" 선물하기#k\r\n";
        choose += "#d#L7#"+time+" 운영자맵#k";
        choose += "#d#L8#"+time+" 모두소환#K";
        choose += "#d#L9#"+time+" 팅맵해제#K\r\n";        
        choose += "#d#L10#"+time+" 현접종료#k";
        choose += "#d#L11#"+time+" 총메세지#k"; 
        

       
       
  }
        cm.sendSimple(choose);

    } else if (status == 1) {
        var s = selection;
        cm.dispose();
        if (s == 0) {
            cm.openNpc (1022107);
 } else if (s == 1) {
  cm.openNpc (1052208);
 } else if (s == 2) {
  cm.openNpc (9300005);
 } else if (s == 3) {
  cm.openNpc (1101000);
 } else if (s == 4) {
  cm.warp (9001137);
 } else if (s == 5) {
  cm.openNpc (1540106);
 } else if (s == 6) {
  cm.openNpc (2161005);
 } else if (s == 7) {
  cm.warp (180000000); 
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
 } else if (s == 20) {
  cm.openNpc (1052107);
 } else if (s == 21) {
  cm.openNpc (1002009);
 } else if (s == 50) {
  cm.openNpc (1052004);
        }
    }
}
