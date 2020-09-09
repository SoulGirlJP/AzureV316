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

var sMax = 1100; //0 box stat highest

var sMin2 = 1; //0st box minimum casting level

var sMax2 = 1050; //0th Box Highest Horse Run



 

//0 box

var randomcode = new Array(1212063,1222058,1232057,1242060,1262016,1272037,1282015,1302275,1312153,1322203,1332225,1342082,1362090,1372177,1382208,1402196,1412135,1422140,1432167,1442223,1452205,1462193,1472214,1482168,1492179,1522094,1532098,1582016,1592018);

//List of items to come out

var itemcode = randomcode[Math.floor(Math.random() * randomcode.length)]; //Do not modify

 

var req = 2434587; //Need

var reqcount = 10; //Required number

 

var box = 2434587; //Box code in description

 

 
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
	    var text = "Please select the Fafnir weapon you want to receive.\r\n\r\n#b";
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


        cm.sendOk(text);

        cm.dispose();

        
    }
    
}

    
