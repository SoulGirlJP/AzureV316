/* Dawnveil
    How to Survive
	Dark Lord
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
	if (mode == 1)
	    status++;
	 else
	    status--;
	if (status == 0) {
		qm.sendYesNo("If you want to survive in Kerning City, you need to get stronger. Perhaps If you can complete my training, you'll stand a chance...");
    } else if (status == 1) {	   
        qm.sendYesNo("The training is simple enough. #bJust eliminate the Octopuses in the Thieves' Hideout. Shall we begin#k?");
    } else if (status == 2) {	
		qm.warp(910310100,0);
		qm.forceStartQuest();
		qm.dispose();
	}
}

function end(mode, type, selection) { 
    if (mode == 0 && type == 0) { 
        status--; 
    } else if (mode == -1) { 
        qm.dispose(); 
        return; 
    } else { 
        status++; 
    } 
    if (status == 0) {
	    qm.sendNext("Eliminate all the Octopuses here. Even you should be able to handle this...");
    } else if (status == 1) {
	    qm.sendOk("Nevermind, I killed them for ye.");
	} else if (status == 2) {	
		qm.warp(103000003);
		qm.gainExp(1242);
		qm.forceCompleteQuest();
		qm.dispose(); 
  } 
 }