importPackage(Packages.client.items);

importPackage(Packages.server.items);
importPackage(Packages.tools);
importPackage(java.util);
importPackage(java.lang);
importPackage(java.io);
importPackage(java.awt);
importPackage(Packages.server);
importPackage(Packages.tools.packet);
importPackage(Packages.server.life);
importPackage(Packages.tools.RandomStream);

 

var sMin = 1; //Zero box stat minimum

var sMax = 3000; //0 box stat highest

var sMin2 = 1; //0st box minimum casting level

var sMax2 = 3000; //0th Box Highest Horse Run



 

//0 box

var randomcode = new Array(1212128,1222121,1232121,1242138,1242140,1262050,1272039,1282039,1302354,1312212,1322263,1332288,1362148,1372236,1382273,1402267,1412188,1422196,1432226,1442284,1452265,1462251,1472274,1482231,1492244,1522151,1532156,1562010,1572010,1582043,1592021);

//List of items to come out

var itemcode = randomcode[Math.floor(Math.random() * randomcode.length)]; //Do not modify

 

var req = 4033929; //Need

var reqcount = 10; //Required number

 

var box = 2435328; //Box code in description

 

 
var select = -1;
var status = -1;

var sel = -1;

 

function start() {

    status = -1;

    action(1, 1, 0);

}

 

function action(mode, type, selection) {

 

    if (mode == -1) {

        cm.dispose();

        return;

    }

    if (mode == 0) {

        status--;

        cm.dispose();

        return;

    }

    if (mode == 1) {

        status++;

    }

    sel = selection;
    
    
    if (status == 0) {
        if(cm.haveItem(req, reqcount)) {
            var text = "#fs11##i" + box + "# #b#t" + box + "##k#fs11# Would you like to open?\r\n\r\n"

            text += "#fs11##i" + box + "# #b#t" + box + "##k#fs11# <--- \r\n"

            text += "#fs11##i" + req + "# #t" + req + "# #b" + reqcount + "#k# Required.\r\n"
        
            cm.sendYesNo(text);
        
    }
        
        else {
            
            cm.sendOk("#e#rYou need 10 of those to proceed.\r\nCome Back When you have enough.")
            cm.dispose();
            
        }
        
        
    }
    
    
    if (status == 1) {
	    var text = "Please select the Genesis weapon you want to receive.\r\n\r\n#b";
		for (var i = 0; i < randomcode.length; i++) {
		    text+="#L"+i+"##i"+randomcode[i]+"# #z"+randomcode[i]+"##l\r\n";
		}
		cm.sendSimple(text);

    
        	 
}
    
    else if (status == 2) {
        
        
        var text = ""

            if (cm.haveItem(req, reqcount)) {


                astat = Randomizer.rand(sMin, sMax);

                wmatk = Randomizer.rand(sMin2, sMax2);

		cm.gainItemAllStat(randomcode[sel], 1, astat, wmatk, 3);

                //cm.gainItem(randomcode[sel], 1);

                cm.gainItem(req, -reqcount);

                text += "#fs11##i" + box + "# #b#t" + box + "##k in\r\n"

                text += "#fs11##i" + randomcode[sel] + "# #b#t" + randomcode[sel] + "##k Came out."

            } else {

                text += "#fs11##i" + box + "# #b#t" + box + "##k Necessary to open\r\n"

                text += "#fs11##i" + req + "# #b#t" + req + "##k Is lacking."

            }

        cm.gainItem(2435328, -1);
        cm.sendOk(text);

        cm.dispose();

        
    }
    
}