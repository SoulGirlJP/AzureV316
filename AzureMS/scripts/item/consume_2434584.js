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

var sMax = 900; //0 box stat highest

var sMin2 = 1; //0st box minimum casting level

var sMax2 = 850; //0th Box Highest Horse Run



 

//0 box

var randomcode = new Array(1062165,1062166,1062167,1062168,1062169);

//List of items to come out

var itemcode = randomcode[Math.floor(Math.random() * randomcode.length)]; //Do not modify

 

var req = 2434584; //Need

var reqcount = 5; //Required number

 

var box = 2434584; //Box code in description

 

 

var status = -1;

var sel = -1;

 

function start() {

    status = -1;

    action(1, 0, 0);

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

 

    if (status == 0) {
 
        var text = "#fs11##dGood morning Azure #rCRA Random Exchange Shop.#k\r\n\r\n#r CRA Option Ornament :#k\r\n- All Stats 1 ~ 900 / ATT, M.ATT 1 ~ 850#k\r\n#d\r\n\r\n"

        text += "#L0##dCRA Pants #r(Pants)#k    #i1062169# #l\r\n"

        cm.sendSimple(text);

    } else if (status == 1) {

        var text = ""

        sel = selection;

        if (selection == 0) {

            text += "#fs11##i" + box + "# #b#t" + box + "##k#fs11# Would you like to open?\r\n\r\n"

            text += "#fs11##i" + box + "# #b#t" + box + "##k#fs11# <--- \r\n"

            text += "#fs11##i" + req + "# #t" + req + "# #b" + reqcount + "#k# Required."



        cm.sendYesNo(text);
    }
    } else if (status == 2) {

        var text = ""

        if (sel == 0) {

            if (cm.haveItem(req, reqcount)) {


                astat = Randomizer.rand(sMin, sMax);

                wmatk = Randomizer.rand(sMin2, sMax2);

		itemid = randomcode[Randomizer.rand(0, randomcode.length)];

		cm.gainItemAllStat(itemid, 1, astat, wmatk, 3);

                //cm.gainItem(itemcode, 1);

                cm.gainItem(req, -reqcount);

                text += "#fs11##i" + box + "# #b#t" + box + "##k in\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##k Came out."

            } else {

                text += "#fs11##i" + box + "# #b#t" + box + "##k Necessary to open\r\n"

                text += "#fs11##i" + req + "# #b#t" + req + "##k Is lacking."

            }


        cm.sendOk(text);

        cm.dispose();

    }
    }
    }