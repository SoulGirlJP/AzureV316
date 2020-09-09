/* Dawnveil
    [Maple Castle] Cygnus in love
	Cygnus
    Made by Daenerys
*/
var status = -1; 

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
        qm.forceCompleteQuest();
		qm.gainItem(2431132,1);
		qm.gainItem(3994650,1);
        qm.dispose(); 
    } 
}  