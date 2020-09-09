/**
 * @author: Eric
 * @script: elevator
 * @func: Helios Tower Elevator
*/

function init() {
    scheduleNew();
}

function scheduleNew() {
	em.setProperty("goingUp", "false");
	em.setProperty("goingDown", "false");
}

function goUp() {
	em.schedule("goingUpNow", 50000); // might be 60
}

function goDown() {
	em.schedule("goingDownNow", 50000); // might be 60
}

function goingUpNow() {
    em.warpAllPlayer(222020110, 222020111);
    em.setProperty("goingUp", "true");
    em.schedule("isUpNow", 55000);
    em.getChannelServer().getMapFactory().getMap(222020100).setReactorState();
}

function goingDownNow() {
    em.warpAllPlayer(222020210, 222020211);
    em.setProperty("goingDown", "true");
    em.schedule("isDownNow", 55000);
    em.getChannelServer().getMapFactory().getMap(222020200).setReactorState();
}

function isUpNow() {
    em.getChannelServer().getMapFactory().getMap(222020100).resetReactors();
    em.warpAllPlayer(222020111, 222020200);
    em.setProperty("goingUp", "false"); // clear
}

function isDownNow() {
    em.getChannelServer().getMapFactory().getMap(222020200).resetReactors();
    em.warpAllPlayer(222020211, 222020100);
    em.setProperty("goingDown", "false"); // clear
}

function cancelSchedule() {
	
}