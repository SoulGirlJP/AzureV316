var status = -1;
var BoxID = 2437025;
var num = 1;

var item = new Array(new Array(3010519, 1), new Array(3010520, 1), new Array(3010760, 1), new Array(3010659, 1), new Array(3010721, 1), new Array(3010503, 1), new Array(3010703, 1), new Array(3010319, 1), new Array(3010685, 1), new Array(3010721, 1), new Array(3010702, 1), new Array(3010722, 1), new Array(3010574, 1), new Array(3010556, 1), new Array(3010543, 1), new Array(3010547, 1), new Array(3010548, 1), new Array(3010539, 1), new Array(3010540, 1), new Array(3010542, 1), new Array(3010544, 1), new Array(3010545, 1), new Array(3010546, 1), new Array(3010549, 1), new Array(3010550, 1), new Array(3010551, 1), new Array(3010552, 1), new Array(3010553, 1), new Array(3010554, 1), new Array(3010555, 1), new Array(3010562, 1), new Array(3010572, 1), new Array(3010561, 1), new Array(3010457, 1), new Array(3010458, 1), new Array(3010459, 1), new Array(3010493, 1), new Array(3010516, 1), new Array(3010517, 1), new Array(3010518, 1), new Array(3010521, 1), new Array(3010522, 1), new Array(3010523, 1), new Array(3010524, 1), new Array(3010525, 1), new Array(3010526, 1), new Array(3010532, 1), new Array(3010581, 1), new Array(3010585, 1), new Array(3010587, 1), new Array(3010589, 1), new Array(3010590, 1), new Array(3010512, 1), new Array(3010513, 1), new Array(3010514, 1), new Array(3010515, 1), new Array(3010592, 1), new Array(3010601, 1), new Array(3010611, 1), new Array(3010612, 1), new Array(3010798, 1), new Array(3012017, 1), new Array(3010622, 1), new Array(3010623, 1), new Array(3010624, 1), new Array(3010636, 1), new Array(3010637, 1), new Array(3010641, 1), new Array(3010642, 1), new Array(3010640, 1), new Array(3010643, 1), new Array(3010644, 1), new Array(3010086, 1), new Array(3010672, 1), new Array(3010673, 1), new Array(3010674, 1), new Array(3010677, 1), new Array(3010676, 1), new Array(3010679, 1), new Array(3010680, 1), new Array(3010682, 1), new Array(3010683, 1), new Array(3010691, 1), new Array(3010692, 1), new Array(3010693, 1), new Array(3010694, 1), new Array(3010695, 1), new Array(3010697, 1), new Array(3010704, 1), new Array(3010705, 1), new Array(3010719, 1), new Array(3010720, 1), new Array(3010723, 1), new Array(3010733, 1), new Array(3010734, 1), new Array(3010735, 1), new Array(3010742, 1), new Array(3010743, 1), new Array(3010744, 1), new Array(3010757, 1), new Array(3010761, 1), new Array(3010766, 1), new Array(3010810, 1), new Array(3010835, 1), new Array(3010767, 1), new Array(3010215, 1), new Array(3010216, 1), new Array(3010537, 1), new Array(3010224, 1), new Array(3010582, 1), new Array(3010365, 1), new Array(3010370, 1), new Array(3010369, 1), new Array(3010563, 1), new Array(3010536, 1), new Array(3010541, 1), new Array(3010568, 1), new Array(3010577, 1), new Array(3010576, 1), new Array(3010575, 1), new Array(3010206, 1), new Array(3010578, 1), new Array(3010579, 1), new Array(3010565, 1), new Array(3010580, 1), new Array(3010208, 1), new Array(3010564, 1), new Array(3010320, 1), new Array(3010207, 1), new Array(3010205, 1), new Array(3010844, 1), new Array(3010851, 1), new Array(3015064, 1), new Array(3010852, 1), new Array(3010854, 1), new Array(3010862, 1), new Array(3010863, 1), new Array(3010836, 1), new Array(3010837, 1), new Array(3010838, 1), new Array(3010811, 1), new Array(3010812, 1), new Array(3010946, 1), new Array(3010978, 1), new Array(3010979, 1), new Array(3010980, 1), new Array(3010976, 1), new Array(3015000, 1), new Array(3015003, 1), new Array(3015004, 1), new Array(3015032, 1), new Array(3015050, 1), new Array(3015061, 1), new Array(3015062, 1), new Array(3015048, 1), new Array(3015049, 1), new Array(3015075, 1), new Array(3010956, 1), new Array(3010957, 1), new Array(3010958, 1), new Array(3010959, 1), new Array(3010960, 1), new Array(3010961, 1), new Array(3010962, 1), new Array(3015091, 1), new Array(3015092, 1), new Array(3015089, 1), new Array(3015111, 1), new Array(3015112, 1), new Array(5204007, 1), new Array(3015109, 1), new Array(5204012, 1), new Array(3015408, 1), new Array(3015387, 1), new Array(3015388, 1), new Array(3015389, 1), new Array(3015390, 1), new Array(3015391, 1), new Array(3015392, 1), new Array(3015155, 1), new Array(3015367, 1), new Array(3015368, 1), new Array(3015369, 1), new Array(3015330, 1), new Array(3015331, 1), new Array(3015312, 1), new Array(3015303, 1), new Array(3015315, 1), new Array(3015296, 1), new Array(3015236, 1), new Array(3015263, 1), new Array(3015272, 1), new Array(3015264, 1), new Array(3015223, 1), new Array(3015235, 1), new Array(3015234, 1), new Array(3015210, 1), new Array(3015172, 1), new Array(3015173, 1), new Array(3015174, 1), new Array(3015175, 1), new Array(3015156, 1), new Array(3015157, 1), new Array(3015354, 1), new Array(3015310, 1), new Array(3015311, 1), new Array(3015297, 1), new Array(3015301, 1), new Array(3015309, 1), new Array(3015325, 1), new Array(3015326, 1), new Array(3015327, 1), new Array(3015339, 1), new Array(3015340, 1), new Array(3015350, 1), new Array(3015394, 1), new Array(3010651, 1), new Array(6010652, 1), new Array(3010653, 1), new Array(3010654, 1), new Array(3010655, 1), new Array(3010656, 1), new Array(3010681, 1), new Array(3010783, 1), new Array(3010700, 1), new Array(3010690, 1), new Array(3010662, 1), new Array(3010288, 1), new Array(3017000, 1), new Array(3017001, 1), new Array(3017002, 1), new Array(3017003, 1), new Array(3017004, 1), new Array(3017005, 1), new Array(3017006, 1), new Array(3017007, 1), new Array(3017008, 1), new Array(3017009, 1), new Array(3017010, 1), new Array(3017011, 1), new Array(3017012, 1), new Array(3017013, 1), new Array(3017014, 1), new Array(3017015, 1), new Array(3017016, 1), new Array(3017017, 1), new Array(3017018, 1), new Array(3017019, 1), new Array(3017020, 1), new Array(3017021, 1), new Array(3015314, 1), new Array(3015431, 1), new Array(3015432, 1), new Array(3015447, 1), new Array(3015448, 1), new Array(3015472, 1), new Array(3015473, 1), new Array(3015474, 1), new Array(3015517, 1), new Array(3015516, 1), new Array(3015544, 1), new Array(3015547, 1), new Array(3015545, 1), new Array(3015586, 1), new Array(3015587, 1), new Array(3016200, 1), new Array(3015633, 1), new Array(3015276, 1), new Array(3015277, 1), new Array(3015278, 1), new Array(3015438, 1), new Array(3010505, 1), new Array(3010531, 1), new Array(3010584, 1), new Array(3010000, 1), new Array(3010009, 1), new Array(3010018, 1), new Array(3010021, 1), new Array(3010134, 1), new Array(3010064, 1), new Array(3010065, 1), new Array(3010066, 1), new Array(3010092, 1), new Array(3010097, 1), new Array(3010451, 1), new Array(3010452, 1), new Array(3010109, 1), new Array(3010130, 1), new Array(3010191, 1), new Array(3010131, 1), new Array(3010132, 1), new Array(3010133, 1), new Array(3010157, 1), new Array(3010194, 1), new Array(3010297, 1), new Array(3010360, 1), new Array(3010373, 1), new Array(3010406, 1), new Array(3010432, 1), new Array(3010797, 1), new Array(3010598, 1), new Array(3010613, 1), new Array(3010708, 1), new Array(3010815, 1), new Array(3015116, 1), new Array(3010867, 1), new Array(3010868, 1), new Array(3010869, 1), new Array(3010870, 1), new Array(3010871, 1), new Array(3010872, 1), new Array(3010873, 1), new Array(3010874, 1), new Array(3010875, 1), new Array(3014000, 1), new Array(3014001, 1), new Array(3014002, 1), new Array(3014003, 1), new Array(3014004, 1), new Array(3014006, 1), new Array(3014019, 1), new Array(3014011, 1), new Array(3015015, 1), new Array(3015016, 1), new Array(3015017, 1), new Array(3015018, 1), new Array(3015019, 1), new Array(3015020, 1), new Array(3015021, 1), new Array(3015022, 1), new Array(3015023, 1), new Array(3015024, 1), new Array(3015025, 1), new Array(3015026, 1), new Array(3015031, 1), new Array(3015033, 1), new Array(3015083, 1), new Array(3015090, 1), new Array(3015107, 1), new Array(3015114, 1), new Array(3015115, 1), new Array(3015117, 1), new Array(3015119, 1), new Array(3015120, 1), new Array(3015159, 1), new Array(3015167, 1), new Array(3015238, 1), new Array(3015240, 1), new Array(3015241, 1), new Array(3015243, 1), new Array(3015245, 1), new Array(3015246, 1), new Array(3015247, 1), new Array(3015248, 1), new Array(3015279, 1), new Array(3015305, 1), new Array(3015306, 1), new Array(3015332, 1), new Array(3015333, 1), new Array(3015341, 1), new Array(3015416, 1), new Array(3015420, 1), new Array(3015421, 1), new Array(3015422, 1), new Array(3015468, 1), new Array(3015481, 1), new Array(3015520, 1), new Array(3015429, 1), new Array(5204013, 1), new Array(3015644, 1), new Array(3015645, 1), new Array(3015646, 1), new Array(3015030, 1), new Array(3015564, 1), new Array(3015565, 1), new Array(3015566, 1), new Array(3015567, 1), new Array(3015568, 1), new Array(3015569, 1), new Array(3015570, 1), new Array(3015571, 1), new Array(3015572, 1), new Array(3015602, 1), new Array(3015603, 1), new Array(3015633, 1), new Array(3015637, 1), new Array(3015638, 1), new Array(3015615, 1), new Array(3015616, 1), new Array(3015617, 1), new Array(3015618, 1), new Array(3015610, 1), new Array(3015611, 1), new Array(3015612, 1), new Array(3015613, 1), new Array(3015614, 1), new Array(3015636, 1), new Array(3015642, 1), new Array(3015643, 1), new Array(5204015, 1), new Array(5204016, 1), new Array(3015518, 1), new Array(3010080, 1), new Array(3015648, 1), new Array(3015649, 1), new Array(3015703, 1), new Array(3015710, 1), new Array(3015711, 1), new Array(3017028, 1), new Array(3017029, 1), new Array(3017030, 1), new Array(3017031, 1), new Array(3017032, 1), new Array(3017708, 1), new Array(3015703, 1), new Array(3015704, 1), new Array(3015705, 1), new Array(3015706, 1), new Array(3015707, 1), new Array(3015708, 1), new Array(3015710, 1), new Array(3015711, 1), new Array(3015745, 1), new Array(3015747, 1), new Array(3015748, 1), new Array(3015749, 1), new Array(3015750, 1), new Array(3015753, 1), new Array(3015761, 1), new Array(3015762, 1), new Array(3015764, 1), new Array(3015767, 1), new Array(3015768, 1), new Array(3015769, 1), new Array(3015770, 1)
, new Array(3015786, 1), new Array(3015788, 1), new Array(3015789, 1), new Array(3015790, 1), new Array(3015791, 1), new Array(3015792, 1), new Array(3015798, 1), new Array(5204014, 1), new Array(3015801, 1), new Array(3015802, 1), new Array(3015803, 1), new Array(3015804, 1), new Array(3015810, 1), new Array(3015812, 1), new Array(3015813, 1), new Array(3014026, 1), new Array(3015821, 1), new Array(3015822, 1));


importPackage(Packages.constants);

function start(){
    action(1,0,0);
}

 

item.sort(function(){
    return Math.random() - Math.random();
});

 


function action(mode,type,selection){
    if(mode == 1){
        status++;
    }else{
        status--;
        cm.dispose();
    }
    if (status == 0) {
if (cm.getPlayer().getLevel() >= 200) {
    cm.sendNext("#fnSharing Gothic Extrabold#now #z2437025#Would you like to open? \r\n#L0##bYes, I will open it right now.#k\r\n#L1##rNo, I'll come back later.#k");
} else {
cm.sendOk("#fnSharing Gothic Extrabold##r#z2437025##kAvailable from level 200.",9062004);
cm.dispose();
}
	} else if (selection == 0) {
        if (cm.haveItem(BoxID, num)){
            if (cm.canHold(item[0][0])){
                if (item[0][1] != 0) {
                    cm.gainItem(item[0][0], item[0][1]);
                    cm.gainItem(BoxID, -num);
                    cm.sendOk("#fnSharing Gothic Extrabold##i" + item[0][0] + "# #b(#z" + item[0][0] + "#) #k#r" + item[0][1] + " QTY#k Earned.");
                    cm.dispose();
                } else {
                    cm.sendOk("#fnSharing Gothic Extrabold##rUnfortunately, Quang came out.#k");
                    cm.gainItem(BoxID, -num);
                    cm.dispose();
                }
            } else {
                cm.sendOk("#fnSharing Gothic Extrabold##rOut of inventory space.\r\nPlease check your inventory space first.#k");
                cm.dispose();
            }
        } else {
            cm.sendOk("#i" + BoxID + "# #b#z" + BoxID + "##k I think this is lacking..");
            cm.dispose();
    }
}  else if (selection == 1) {
            cm.dispose();
}
}