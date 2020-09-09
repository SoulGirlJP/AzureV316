/* Dawnveil
	Kanna Tutorial
	Player
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    qm.forceStartQuest();
	qm.forceStartQuest(57402);
	qm.forceCompleteQuest(57402);
    qm.dispose();
}

function end(mode, type, selection) {
	qm.dispose();
}