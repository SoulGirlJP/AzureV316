/* Dawnveil
    Simpler Scrolls and Better Cubes 
	Maple Administrator
    Made by Daenerys
*/
var status = -1;

function end(mode, type, selection) {
if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.gainExp(500);
	    qm.dispose();		
	}
  }
}