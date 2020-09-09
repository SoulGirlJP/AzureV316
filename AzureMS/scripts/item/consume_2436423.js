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

 

var sMin = 300; //Zero box stat minimum

var sMax = 1400; //0 box stat highest

var sMin2 = 290; //0st box minimum casting level

var sMax2 = 1350; //0th Box Highest Horse Run



 

//0 box

var randomcode = new Array(1212120,1222113,1232113,1242121,1262039,1272017,1302343,1312203,1322255,1332279,1342104,1362140,1372228,1382265,1402259,1412181,1422189,1432218,1442274,1452257,1462243,1472265,1482221,1492235,1522143,1532150,1582023,1592020);

//List of items to come out

var itemcode = randomcode[Math.floor(Math.random() * randomcode.length)]; //Do not modify

 

var req = 4001895; //Need

var reqcount = 10; //Required number

 

var box = 2436423; //Box code in description

 

 
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
	    var text = "Please select the Arcane weapon you want to receive.\r\n\r\n#b";
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

        cm.gainItem(2436423, -1);
        cm.sendOk(text);

        cm.dispose();

        
    }
    
}