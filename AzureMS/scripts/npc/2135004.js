
var status = -1;

function start() {
    status = -1;
    action (1, 0, 0);
}
           

function action(mode, type, selection) {
 rock = "#fUI/UIWindow.img/RpsGame/rock#"; 
 paper = "#fUI/UIWindow.img/RpsGame/paper#"; 
 scissor = "#fUI/UIWindow.img/RpsGame/scissor#"; 
 win = "#fUI/UIWindow.img/RpsGame/win#"; 
 lose = "#fUI/UIWindow.img/RpsGame/lose#"; 
 draw = "#fUI/UIWindow.img/RpsGame/draw#"; 
 상대가위바위보 = ["#fUI/UIWindow.img/RpsGame/Frock#","#fUI/UIWindow.img/RpsGame/Fscissor#","#fUI/UIWindow.img/RpsGame/Fpaper#"]
 자기가위바위보 = ["#fUI/UIWindow.img/RpsGame/rock#","#fUI/UIWindow.img/RpsGame/scissor#","#fUI/UIWindow.img/RpsGame/paper#"]
    if (mode == -1 || mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
        cm.sendYesNo("#b"+cm.getPlayer().getKeyValue("Rockgiver")+"#k님께서, 가위바위보 대결 신청을 하셨습니다.\r\n수락하시겠습니까?");
    } else if (status == 1) {
         cm.getPlayer().setKeyValue("Rockresult",null);
         cm.getPlayer().setKeyValue("RockOk",1)
         cm.sendSimple("아래에서 선택해주세요.\r\n\r\n"
                          +"#L0#"+rock+"#l#L1#"+scissor+"#L2#"+paper+"#l");
    } else if (status == 2) {
         if (selection != -1) {
         cm.getPlayer().setKeyValue("Rockresult",selection);
         }
         cm.sendSimple(cm.getPlayer().getKeyValue("Rockresult")+"결과발표를 하고 있습니다...\r\n#L0# #b결과가 발표되었는지 확인");
     } else if (status == 3) {
         target = cm.getClient().getChannelServer().getPlayerStorage().getCharacterByName(cm.getPlayer().getKeyValue("Rockgiver"))
         if (target.getKeyValue("Rockresult") != null) {
            Myresult = cm.getPlayer().getKeyValue("Rockresult")
            Yourresult = target.getKeyValue("Rockresult");
            if ((Myresult == 0 && Yourresult == 1) || (Myresult == 1 && Yourresult == 2) || (Myresult == 2 && Yourresult == 0)) {
                cm.sendOk("     #b상대#k"+상대가위바위보[Yourresult]+"VS"+자기가위바위보[Myresult]+"#r#h #\r\n"+win);
                
            } else if (Myresult == Yourresult) {
 		cm.sendOk("     #b상대#k"+상대가위바위보[Yourresult]+"VS"+자기가위바위보[Myresult]+"#r#h #\r\n"+draw);
                
            } else {
		cm.sendOk("     #b상대#k"+상대가위바위보[Yourresult]+"VS"+자기가위바위보[Myresult]+"#r#h #\r\n"+lose);
                
            }

            cm.dispose();
         } else {
            cm.sendOk("아직 결과가 집계되지 않았습니다.");
            status -=2;
         }
    }
}
