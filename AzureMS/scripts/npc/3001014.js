/*

 

Nfisia ID:

 

Nfish name:

 

Map with nfish:: ()

 

NPC Description: Box Exchange

 

 

*/

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

var sMax = 200; //0 box stat highest

var sMin2 = 1; //0st box minimum casting level

var sMax2 = 200; //0th Box Highest Horse Run

 

var ssMin = 1; //No. 1 box stat

var ssMax = 200; //No. 1 box stat high

var ssMin2 = 1; //Box No. 1

var ssMax2 = 200; //Box 1 highest horse run

 

var sssMin = 1; //No. 2 box stat

var sssMax = 200; //Box No. 2 stat highest

var sssMin2 = 1; //Minimum number of box 2 casting

var sssMax2 = 200; //Box 2 highest

 

var ssssMin = 1; //No. 3 box stat

var ssssMax = 200; //Box 3 stat high

var ssssMin2 = 1; //3 box minimum casting

var ssssMax2 = 200; //Box 3 highest


 

//0 box

var randomcode = new Array(1102481, 1102482, 1102483, 1102484, 1102485);

//List of items to come out

var itemcode = randomcode[Math.floor(Math.random() * randomcode.length)]; //Do not modify

 

var req = 2433453; //Need

var reqcount = 10; //Required number

 

var box = 2433453; //Box code in description

 

 

//1st box

var randomcode1 = new Array(1082543, 1082544, 1082545, 1082546, 1082547);

//List of items to come out

var itemcode1 = randomcode1[Math.floor(Math.random() * randomcode1.length)]; //Do not modify

 

//var req1 = 2433453; //Need

var reqcount1 = 10; //Required number

 

var box1 = 2433453; //Box code in description

 

 

//Box 2

var randomcode2 = new Array(1072743, 1072744, 1072745, 1072746, 1072747);

//List of items to come out

var itemcode2 = randomcode2[Math.floor(Math.random() * randomcode2.length)]; //Do not modify

 

var req2 = 2433453; //Need

var reqcount2 = 10; //Required number

 

var box2 = 2433453; //Box code in description

 

 

//Box 3

var randomcode3 = new Array(1132174, 1132175, 1132176, 1132177, 1132178);

//List of items to come out

var itemcode3 = randomcode3[Math.floor(Math.random() * randomcode3.length)]; //Do not modify

 

var req3 = 2433453; //Need

var reqcount3 = 10; //Required number

 

var box3 = 2433453; //Box code in description




// Box 4

var randomcode4 = new Array(1052882, 1052887, 1052888, 1052889, 1052890);

//List of items to come out

var itemcode4 = randomcode4[Math.floor(Math.random() * randomcode4.length)]; //Do not modify

 

var req4 = 4310156; //Need

var reqcount4 = 500; //Required number

 

var box4 = 4310156; //Box code in description



// Box 5

var randomcode5 = new Array(1073030, 1703032, 1073033, 1073034, 1073035);

//List of items to come out

var itemcode5 = randomcode5[Math.floor(Math.random() * randomcode5.length)]; //Do not modify

 

var req5 = 4310156; //Need

var reqcount5 = 500; //Required number

 

var box5 = 4310156; //Box code in description


 

 

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
 
        var text = "#fs11##dGood morning Azure #rTyrant Random Exchange Shop#k #dis#k\r\n\r\n#rTyrant#k Max min numerical ball, t: #r1 ~ 200#k  All Stats : #r1 ~ 200#k\r\n#d\r\n\r\n"

        text += "#L0##dTyrant Cloak #r(Cloak)#k    #i1102481# #l\r\n"

        text += "#L1##dTyrant Gloves #r(Gloves)#k   #i1082543# #l\r\n"

        text += "#L2##dTyrant Shoes #r(Shoes)#k    #i1072743# #l\r\n"

        text += "#L3##dTyrant Belt #r(Belt)#k   #i1132174# #l\r\n"

        //text += "#i1052887##L4#      We change Absolab suit.#l\r\n"

        //text += "#i1073030##L5#      Replace Absolab's Shoes.#l\r\n"

        cm.sendSimple(text);

    } else if (status == 1) {

        var text = ""

        sel = selection;

        if (selection == 0) {

            text += "#fs11##i" + box + "# #b#t" + box + "##k#fs11#Would you like to open?\r\n\r\n"

            text += "#fs11##i" + box + "# #b#t" + box + "##k#fs11#To open\r\n"

            text += "#fs11##i" + req + "# #t" + req + "# #b" + reqcount + "#k#I need a Dock."

        } else if (selection == 1) {

            text += "#fs11##i" + box1 + "# #b#t" + box1 + "##k#fs11#Would you like to open?\r\n\r\n"

            text += "#fs11##i" + box1 + "# #b#t" + box1 + "##k#fs11#To open\r\n"

            text += "#fs11##i" + req1 + "# #t" + req1 + "# #b" + reqcount1 + "#kI need a Dock."

        } else if (selection == 2) {

            text += "#fs11##i" + box2 + "# #b#t" + box2 + "##kWould you like to open?\r\n\r\n"

            text += "#fs11##i" + box2 + "# #b#t" + box2 + "##kTo open\r\n"

            text += "#fs11##i" + req2 + "# #t" + req2 + "# #b" + reqcount2 + "#kI need a Dock."

        } else if (selection == 3) {

            text += "#fs11##i" + box3 + "# #b#t" + box3 + "##kWould you like to open?\r\n\r\n"

            text += "#fs11##i" + box3 + "# #b#t" + box3 + "##kTo open\r\n"

            text += "#fs11##i" + req3 + "# #t" + req3 + "# #b" + reqcount3 + "#kI need a Dock."
       
        } else if (selection == 4) {

            text += "#fs11##i" + box4 + "# #b#t" + box4 + "##kWould you like to open?\r\n\r\n"

            text += "#fs11##i" + box4 + "# #b#t" + box4 + "##kTo open\r\n"

            text += "#fs11##i" + req4 + "# #t" + req4 + "# #b" + reqcount4 + "#kI need a Dock."

        } else if (selection == 5) {

            text += "#fs11##i" + box5 + "# #b#t" + box5 + "##kWould you like to open?\r\n\r\n"

            text += "#fs11##i" + box5 + "# #b#t" + box5 + "##kTo open\r\n"

            text += "#fs11##i" + req5 + "# #t" + req5 + "# #b" + reqcount5 + "#kI need a Dock."

        }

        cm.sendYesNo(text);

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

                text += "#fs11##i" + box + "# #b#t" + box + "##kin\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##kCame out."

            } else {

                text += "#fs11##i" + box + "# #b#t" + box + "##kNecessary to open\r\n"

                text += "#fs11##i" + req + "# #b#t" + req + "##kIs lacking."

            }

        } else if (sel == 1) {

            if (cm.haveItem(req1, reqcount1)) {


                astat = Randomizer.rand(ssMin, ssMax);

                wmatk = Randomizer.rand(ssMin2, ssMax2);

		itemid = randomcode1[Randomizer.rand(0, randomcode1.length)];

		cm.gainItemAllStat(itemid, 1, astat, wmatk, 3);

                //cm.gainItem(itemcode1, 1);

                cm.gainItem(req1, -reqcount1);

                text += "#fs11##i" + box1 + "# #b#t" + box1 + "##k#fs11#in\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##kCame out."

            } else {

                text += "#fs11##i" + box1 + "# #b#t" + box1 + "##kNecessary to open\r\n"

                text += "#fs11##i" + req1 + "# #b#t" + req1 + "##kIs lacking."

            }

        } else if (sel == 2) {

            if (cm.haveItem(req2, reqcount2)) {

                astat = Randomizer.rand(sssMin, sssMax);

                wmatk = Randomizer.rand(sssMin2, sssMax2);

		itemid = randomcode2[Randomizer.rand(0, randomcode2.length)];

		cm.gainItemAllStat(itemid, 1, astat, wmatk, 3);

                //cm.gainItem(itemcode2, 1);

                cm.gainItem(req2, -reqcount2);

                text += "#fs11##i" + box2 + "# #b#t" + box2 + "##kin\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##k Came out."

            } else {

                text += "#fs11##i" + box2 + "# #b#t" + box2 + "##kNecessary to open\r\n"

                text += "#fs11##i" + req2 + "# #b#t" + req2 + "##kIs lacking."

            }

        } else if (sel == 3) {

            if (cm.haveItem(req3, reqcount3)) {

                astat = Randomizer.rand(ssssMin, ssssMax);

                wmatk = Randomizer.rand(ssssMin2, ssssMax2);

		itemid = randomcode3[Randomizer.rand(0, randomcode3.length)];

		cm.gainItemAllStat(itemid, 1, astat, wmatk, 3);

                //cm.gainItem(itemcode3, 1);

                cm.gainItem(req3, -reqcount3);

                text += "#fs11##i" + box3 + "# #b#t" + box3 + "##kin\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##k Came out."

            } else {

                text += "#fs11##i" + box3 + "# #b#t" + box3 + "##kNecessary to open\r\n"

                text += "#fs11##i" + req3 + "# #b#t" + req3 + "##kIs lacking."

                }
           
            } else if (sel == 4) {

            if (cm.haveItem(req4, reqcount4)) {

                astat = Randomizer.rand(sssssMin, sssssMax);

                wmatk = Randomizer.rand(sssssMin2, sssssMax2);

		itemid = randomcode4[Randomizer.rand(0, randomcode4.length)];

		cm.gainItemAllStat(itemid, 1, astat, wmatk, 3);

                //cm.gainItem(itemcode4, 1);

                cm.gainItem(req4, -reqcount4);

                text += "#fs11##i" + box4 + "# #b#t" + box4 + "##kin\r\n"

                text += "#fs11##i" + itemid + "# #b#t" + itemid + "##k Came out."

            } else {

                text += "#fs11##i" + box4 + "# #b#t" + box4 + "##kNecessary to open\r\n"

                text += "#fs11##i" + req4 + "# #b#t" + req4 + "##kIs lacking."

            }

        } else if (sel == 5) {

            if (cm.haveItem(req5, reqcount5)) {

                ItemInfo = BOWN29.getInstance().getEquipById(itemcode5);

                str = Randomizer.rand(ssssMin, ssssMax);

                dex = Randomizer.rand(ssssMin, ssssMax);

                intt = Randomizer.rand(ssssMin, ssssMax);

                luk = Randomizer.rand(ssssMin, ssssMax);

                watk = Randomizer.rand(ssssMin2, ssssMax2);

                matk = Randomizer.rand(ssssMin2, ssssMax2);

                ItemInfo.setStr(str);

                ItemInfo.setDex(dex);

                ItemInfo.setInt(intt);

                ItemInfo.setLuk(luk);

                ItemInfo.setWatk(watk);

                ItemInfo.setMatk(matk);

                MapleInventoryManipulator.addFromDrop(cm.getClient(), ItemInfo, false);

                //cm.gainItem(itemcode5, 1);

                cm.gainItem(req5, -reqcount5);

                text += "#fs11##i" + box5 + "# #b#t" + box5 + "##kin\r\n"

                text += "#fs11##i" + itemcode5 + "# #b#t" + itemcode5 + "##k Came out."

            } else {

                text += "#fs11##i" + box5 + "# #b#t" + box5 + "##kNecessary to open\r\n"

                text += "#fs11##i" + req5 + "# #b#t" + req5 + "##kIs lacking."

            }

        }

        cm.sendOk(text);

        cm.dispose();

    }

}