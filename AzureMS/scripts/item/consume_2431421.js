/*

성 우 (mysqld@nate.com)

메이플 로얄 스타일 

*/
//imddddportPackage(java.lang);
importPackage(Packages.handling.world);
importPackage(Packages.packet.creators);

var status = -1;

var royalstyle = 5680157; //Maple Royal Style Coupon





var item = new Array(new Array(1702456, 1), new Array(1050299, 1), new Array(1051366, 1), new Array(1072860, 1), new Array(1082555, 1), new Array(1003955, 1), new Array(1050300, 1), new Array(1051367,1), new Array(1003957, 1), new Array(1003958, 1), new Array(1702457, 1), new Array(1072862, 1), new Array(1003867, 1), new Array(1042264, 1), new Array(1060182, 1), new Array(1061206,1), new Array(1072823, 1), new Array(1082527, 1), new Array(1003909, 1), new Array(1050291,1), new Array(1051357,1), new Array(1072836,1), new Array(1102593,1), new Array(1702442,1), new Array(1003945,1), new Array(1050296,1), new Array(1051362,1), new Array(1072852,1), new Array(1102608,1), new Array(1003971,1), new Array(1003972,1), new Array(1050302,1), new Array(1072868,1), new Array(1702464,1), new Array(1004002,1), new Array(11026324,1), new Array(1070057,1), new Array(1071074,1), new Array(1702473,1), new Array(1702424,1), new Array(1702486,1), new Array(1001092,1), new Array(1072901,1));




function start(){
    action(1,0,0);
}

 

item.sort(function(){
    return Math.random() - Math.random();
});

 

function action(mode,type,selection){
    if(mode == 1){
        status++;
    }else{
        status--;
        cm.dispose();
    }
    if (status == 0){
        cm.sendSimple("Hello? I'm Big Header, the second prince of the Big Head Bureau.\r\n#i5680157##r#t5680157##k If you have a coupon, I'll change one of the latest style cache items. If you are lucky #rAmazing options#k You can get this cash outfit item!\r\nHow about using coupons right now?\r\n\r\n#L1##bUse Maple Royal Style Coupon Right Now!");
    }else if (status == 1){
        if (cm.haveItem(royalstyle, 1)){
            if (cm.canHold(item[0][0])){
                if (item[0][1] != 0) {
                    cm.gainItem(item[0][0], item[0][1]);
                    cm.gainItem(royalstyle, -1);
                    cm.sendOk("What do you think?#i" + item[0][0] + "# #r(#z" + item[0][0] + "#)#k Did you get the item well? Are you really amazing? Next time again #bMaple Royal Style#k Find me when you have a coupon!");
                    WorldBroadcasting.broadcast(MainPacketCreator.serverNotice(6,""+ cm.getPlayer().getName()+ " Has obtained a cash item using Maple Royal Style Coupon ~ Congratulations !"));
                    cm.dispose();
                } else {
                    cm.sendOk("Unfortunately, nothing was pulled.");
                    cm.gainItem(royalstyle, -1);
                    cm.dispose();
                }
            } else {
                cm.sendOk("Not enough inventory space.");
                cm.dispose();
            }
        } else {
            cm.sendOk("#i"+royalstyle+"##rMaple Royal Style#k I don't have a coupon.");
            cm.dispose();
        }    
    }    
}