var status = 0;

importPackage(Packages.constants);

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
           var  chat = "          #fnSharing Ghotic Extrabold##fs17##fUI/FarmUI.img/objectStatus/star/whole#AzureMS Exchange System#fUI/FarmUI.img/objectStatus/star/whole#\r\n#fs11##Cgray#    #fUI/FarmUI.img/objectStatus/star/whole4##dWelcome to the AzureMS Exchange System.#fUI/FarmUI.img/objectStatus/star/whole4##k\r\n\r\n#fs11#";
                chat += "#fs11#Defeat the Boss and collect #bAzure Orbs!#k\r\nExchange them for unique items!\r\n\r\n#r*Warning* Blue Orbs are common.#k\r\n";
                chat += "-----------------------#fUI/FarmUI.img/objectStatus/star/whole5#---------------------------#l\r\n";
                chat += "#k#d#fs11##L1##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Zakum#d Orbs#k#l\r\n";
                chat += "#k#d#fs11##L2##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Horntail#d Orbs#k#l\r\n";
                chat += "#k#d#fs11##L3##fUI/FarmUI.img/objectStatus/star/whole# #r Fire Wolf#d Orbs#k#l\r\n";
                
                
                
                
                
                chat += "\r\n-----------------------#fUI/FarmUI.img/objectStatus/star/whole7#---------------------------#l\r\n";
                chat += "#k#d#fs11##L4##fUI/FarmUI.img/objectStatus/star/whole# #r Hilla#d Orbs#k#l\r\n"; 
                chat += "#k#d#fs11##L5##fUI/FarmUI.img/objectStatus/star/whole# #r Von Leon#d Orbs#k  #l\r\n";
                chat += "#k#d#fs11##L6##fUI/FarmUI.img/objectStatus/star/whole# #r Arkarium#d Orbs#k  #l\r\n";
                chat += "#k#d#fs11##L7##fUI/FarmUI.img/objectStatus/star/whole# #r Cygnus#d Orbs#k  #l\r\n";
                chat += "#k#d#fs11##L8##fUI/FarmUI.img/objectStatus/star/whole# #r Magnus#d Orbs#k  #l\r\n";
                chat += "#k#d#fs11##L9##fUI/FarmUI.img/objectStatus/star/whole# #r Omni-CLN#d Orbsk  #l\r\n";
		chat += "#k#d#fs11##L10##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Pinkbean#d Orbs#k  #l\r\n"; // /* < HERE
                
                
                
                
                chat += "\r\n-----------------------#fUI/FarmUI.img/objectStatus/star/whole6#---------------------------#l\r\n";
                
                chat += "#k#d#fs11##L11##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Pierre#d Orbs#k#l\r\n";
                chat += "#k#d#fs11##L12##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Von Bon#d Orbs#k#l\r\n";
                chat += "#k#d#fs11##L13##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Queen#d Orbs#k#l\r\n";
                chat += "#k#d#fs11##L14##fUI/FarmUI.img/objectStatus/star/whole# #r Chaos Vellum#d Orbs#k#l\r\n";
                
                
                
                chat += "\r\n-----------------------#fUI/FarmUI.img/objectStatus/star/whole15#---------------------------#l\r\n";
                
                chat += "#k#d#fs11##L15##fUI/FarmUI.img/objectStatus/star/whole# #r Lotus#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L16##fUI/FarmUI.img/objectStatus/star/whole# #r Damien#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L17##fUI/FarmUI.img/objectStatus/star/whole# #r Lucid#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L18##fUI/FarmUI.img/objectStatus/star/whole# #r Dorothy#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L19##fUI/FarmUI.img/objectStatus/star/whole# #r Cross#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L20##fUI/FarmUI.img/objectStatus/star/whole# #r Papulatus#d Star Orb#k  #l\r\n\r\n";
                
                
                chat += "\r\n-----------------------#fUI/FarmUI.img/objectStatus/star/whole16#---------------------------#l\r\n";
                
                chat += "#k#d#fs11##L21##fUI/FarmUI.img/objectStatus/star/whole# #r Will#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L22##fUI/FarmUI.img/objectStatus/star/whole# #r Jin Hilla#d Star Orb#k  #l\r\n";
                chat += "#k#d#fs11##L23##fUI/FarmUI.img/objectStatus/star/whole# #r Black Mage#d Star Orb#k  #l\r\n";
                
               
                
           
                
                
               

		cm.sendSimple(chat);

	} else if (status == 1) { 

	if (selection == 1) { // Zakum
	cm.dispose();
	cm.openNpc(9000247);
	
	} else if (selection == 2) { // Chaos Horntail
	cm.dispose();
	cm.openNpc(9020016);
        
        }else if (selection == 3) { // Fire Wolf
	cm.dispose();
	cm.openNpc(1052241);

	} else if (selection == 4) { // Hilla
	cm.dispose();
	cm.openNpc(2183001);

	} else if (selection == 5) { // Von
	cm.dispose();
	cm.openNpc(2161001);
        } else if (selection == 6) { // Ark
	cm.dispose();
	cm.openNpc(2490002);
        } else if (selection == 7) { // Cygnus
	cm.dispose();
	cm.openNpc(1540556);
        } else if (selection == 8) { // Magnus
	cm.dispose();
	cm.openNpc(3001000);
        } else if (selection == 9) { // Omni-CLN
	cm.dispose();
	cm.openNpc(2052032);
        } else if (selection == 10) { // Chaos Pink
	cm.dispose();
	cm.openNpc(9072302);
        
        }else if (selection == 11) { // Chaos Pierre
	cm.dispose();
	cm.openNpc(1530628);

	}else if (selection == 12) { // Chaos Von Bon
	cm.dispose();
	cm.openNpc(1530627);

	}else if (selection == 13) { // Chaos Queen
	cm.dispose();
	cm.openNpc(1530624);

	}else if (selection == 14) { // Chaos Vellum
	cm.dispose();
	cm.openNpc(1530625);

	}else if (selection == 15) { // Lotus
	cm.dispose();
	cm.openNpc(9075302);
        
        }else if (selection == 16) { // Damien
	cm.dispose();
	cm.openNpc(1540809);
        
        }else if (selection == 17) { // Lucid
	cm.dispose();
	cm.openNpc(3003272);
        
        }else if (selection == 18) { // Doroty
	cm.dispose();
	cm.openNpc(2540002);
        
        }else if (selection == 19) { // Cross
	cm.dispose();
	cm.openNpc(2144002);
        
        } else if (selection == 20) { // Papu
	cm.dispose();
	cm.openNpc(2470010);

        } else if (selection == 21) { // Will
	cm.dispose();
        cm.openNpc(3003551);
        
        } else if (selection == 22) { // Jin Hilla
	cm.dispose();
        cm.openNpc(2520000);
        
        } else if (selection == 23) { // Black Mage
	cm.dispose();
        cm.openNpc(1530668);
        }
        
            
            
            
            
            
            
            /* else if (selection == 30) {
	cm.dispose();
	cm.openNpc(2460018);
        } else if (selection == 100) {
	cm.dispose();
	cm.openNpc(1540809);
	} else if (selection == 50) {
	cm.dispose();
	cm.openNpc(1012117);
	} else if (selection == 60) {
	cm.dispose();
	cm.openNpc(9010041);

        } else if (selection == 7) {
	cm.dispose();
	cm.openNpc(1052226);
			}*/
		}
	}
       
}