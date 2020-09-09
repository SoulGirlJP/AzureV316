// Donation Shop
importPackage(Packages.constants);

var enter = "\r\n";


var need = cm.getPlayer().getRC();

var seld = -1;

function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		cm.dispose();
		return;
    	}
	if (status == 0) {
                if (!cm.getPlayer().isGM()){
                    
                    cm.sendOk("Revamping Shop");
                    
                    cm.dispose();
                }
            
		var msg = "#fs11##k#k#d#fs11##fUI/FarmUI.img/objectStatus/star/whole# #dGood morning. Azure Donation Point Shop.#k"+enter;
		msg += "#fs11##k#k#k#fs11##fUI/FarmUI.img/objectStatus/star/whole# Current DP : #r["+cm.getPlayer().getRC()+"P] #kAvailable."+enter;
                msg += "#k#d#fs11##L1##fUI/FarmUI.img/objectStatus/star/whole# Coin Shop\r\n"
                msg += "#k#d#fs11##L2##fUI/FarmUI.img/objectStatus/star/whole# Item Shop\r\n"
                msg += "#k#d#fs11##L3##fUI/FarmUI.img/objectStatus/star/whole# Scroll Shop\r\n"
		cm.sendSimple(msg);
                
                
	} else if (status == 1) {
            
            if (selection == 1){
                    cm.dispose();
                    cm.openNpc(1013355);
                    
                }
           if (selection == 2){
                    cm.dispose();
                    cm.openNpc(1013302);
                }
           if (selection == 3){
                    cm.dispose();
                    cm.openNpc(1013300);
                }  
            }
          
}