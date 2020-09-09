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
var Cabin_to_Orbis;
var Orbis_docked;
var Lefrea_btf;
var Lefrea_docked;
var Orbis_Station;

function init() {
    Orbis_btf = em.getChannelServer().getMapFactory().getMap(200000132);
    Lefrea_btf = em.getChannelServer().getMapFactory().getMap(240000111);
    Lefrea_docked = em.getChannelServer().getMapFactory().getMap(240000110);
    Cabin_to_Orbis = em.getChannelServer().getMapFactory().getMap(200090210);
    Cabin_to_Lefrea = em.getChannelServer().getMapFactory().getMap(200090200);
    Orbis_docked = em.getChannelServer().getMapFactory().getMap(200000131);
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
    
    Lefrea_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
    Orbis_btf.setMapTimerNotDB(System.currentTimeMillis() + beginTime);
}

function stopentry() {
    em.setProperty("entry","false");
}

function takeoff() {
    em.setProperty("docked","false");
    
    Cabin_to_Orbis.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    Cabin_to_Lefrea.setMapTimerNotDB(System.currentTimeMillis() + rideTime);
    
    var temp1 = Orbis_btf.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Cabin_to_Lefrea, Cabin_to_Lefrea.getPortal(0));
    }
    var temp2 = Lefrea_btf.getCharacters().iterator();
    while(temp2.hasNext()) {
        temp2.next().changeMap(Cabin_to_Orbis, Cabin_to_Orbis.getPortal(0));
    }
    em.schedule("arrived", rideTime);
}

function arrived() {
    var temp1 = Cabin_to_Orbis.getCharacters().iterator();
    while(temp1.hasNext()) {
        temp1.next().changeMap(Orbis_Station, Orbis_Station.getPortal(0));
    }
    var temp3 = Cabin_to_Lefrea.getCharacters().iterator();
    while(temp3.hasNext()) {
        temp3.next().changeMap(Lefrea_docked, Lefrea_docked.getPortal(0));
    }
    scheduleNew();
}

function cancelSchedule() {
}