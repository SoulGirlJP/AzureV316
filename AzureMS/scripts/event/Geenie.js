importPackage(Packages.client);
importPackage(Packages.packet.creators);
importPackage(Packages.server.life);
importPackage(Packages.tools);
importPackage(java.lang);

//Time Setting is in millisecond
var closeTime = 240000; //The time to close the gate
var beginTime = 300000; //The time to begin the ride
var rideTime = 300000; //The time that require move to destination
var Orbis_btf;
var Geenie_to_Orbis;
var Orbis_docked;
var Ariant_btf;
var Ariant_docked;
var Orbis_Station;

function init() {
    Orbis_btf = em.getChannelServer().getMapFactory().getMap(200000152);
    Ariant_btf = em.getChannelServer().getMapFactory().getMap(260000110);
    Ariant_docked = em.getChannelServer().getMapFactory().getMap(260000100);
    Geenie_to_Orbis = em.getChannelServer().getMapFactory().getMap(200090410);
    Geenie_to_Ariant = em.getChannelServer().getMapFactory().getMap(200090400);
    Orbis_docked = em.getChannelServer().getMapFactory().getMap(200000151);
    Orbis_Station = em.getChannelServer().getMapFactory().getMap(200000100);
    em.setProperty("docked", "false");
    em.setProperty("entry", "false");
    var time = CurrentTime.getLeftTimeFromMinute(10) * 1000;
    em.schedule("scheduleNew", time);
}

function scheduleNew() {
    em.setProperty("docked", "true");
    em.setProperty("entry", "true");
    em.schedule("stopentry", closeTime);
    em.schedule("takeoff", beginTime);
    
    Ariant_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
    Orbis_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
}

function stopentry() {
    em.setProperty("entry","false");
}

function takeoff() {
    em.setProperty("docked","false");
    
    Geenie_to_Orbis.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    Geenie_to_Ariant.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    
    var temp1 = Orbis_btf.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Geenie_to_Ariant, Geenie_to_Ariant.getPortal(0));
    }
    var temp2 = Ariant_btf.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(Geenie_to_Orbis, Geenie_to_Orbis.getPortal(0));
    }
    em.schedule("arrived", rideTime);
}

function arrived() {
    var temp1 = Geenie_to_Orbis.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Orbis_Station, Orbis_Station.getPortal(0));
    }
    var temp3 = Geenie_to_Ariant.getCharacters().iterator();
    while(temp3.hasNext()) {
        temp3.next().changeMap(Ariant_docked, Ariant_docked.getPortal(0));
    }
    scheduleNew();
}

function cancelSchedule() {
}