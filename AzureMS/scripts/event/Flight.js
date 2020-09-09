function init() {
    scheduleNew();
}

function scheduleNew() {
    em.setProperty("docked", "true");
    em.setProperty("entry", "true");
    em.schedule("stopEntry", 240000); //The time to close the gate
    em.schedule("takeoff", 180000); //The time to begin the ride
}

function stopEntry() {
    em.setProperty("entry","false");
}

function takeoff() {
    em.warpAllPlayer(200000132, 200090200);
    em.warpAllPlayer(240000111, 200090210);
    // em.broadcastShip(200000131, 3);
    // em.broadcastShip(240000110, 3);
    em.setProperty("docked","false");
    em.schedule("arrived", 300000); //The time that require move to destination
}

function arrived() {
    em.warpAllPlayer(200090200, 240000100);
    em.warpAllPlayer(200090210, 200000100);
    // em.broadcastShip(200000131, 1);
    // em.broadcastShip(240000110, 1);
    scheduleNew();
}

function cancelSchedule() {
}